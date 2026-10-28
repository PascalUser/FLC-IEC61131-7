package lowering;

import ast.AbstractSyntaxTree;
import codegen.ControllerModel;
import codegen.Variable;
import codegen.defuzzification.CentroidDefuzzifier;
import codegen.defuzzification.Defuzzifier;
import codegen.evaluation.MinMaxMethod;
import codegen.fuzzysets.FuzzySet;
import codegen.fuzzysets.SingletonFuzzySet;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import parser.initializations.Initialization;
import utils.DiagnosticsHandler;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.diagnostics.GeneratorWarning;
import utils.diagnostics.UnsupportedFeature;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

/**
 * Builds the {@link ControllerModel} of the code generator from what the parser produced: the symbol table (the
 * named elements: variables, FUZZIFY/DEFUZZIFY blocks, terms, pragmas), the rule block AST and the order of the
 * inputs and outputs.
 * <p>
 * It is also the check of what the generator supports. The generated controller works with unsigned integers of
 * N bits, so:
 * </p>
 * <ul>
 *   <li>N comes from {@code PRAGMA bit_width N;} in an OPTION block (1..30), or is 8 with a warning;</li>
 *   <li>inputs and outputs must be of an integer type (REAL is not supported) and every point an integer
 *       in {@code 0 .. 2^N - 1};</li>
 *   <li>each input needs a FUZZIFY block and each output a DEFUZZIFY block with {@code METHOD: CoGS} and only
 *       singleton terms; {@code DEFAULT} is ignored with a warning (the generator has no default value);</li>
 *   <li>the rules must follow {@link RuleLowering}.</li>
 * </ul>
 * <p>
 * Every problem is reported to the {@link DiagnosticsHandler}; the model is built only if there was no error.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class ControllerLowering {
    /** Bit width used when the program has no {@code PRAGMA bit_width}. */
    public static final int DEFAULT_BIT_WIDTH = 8;

    private static final Set<Subtype> INTEGER_TYPES = EnumSet.of(
            Subtype.SINT, Subtype.INT, Subtype.DINT, Subtype.LINT,
            Subtype.USINT, Subtype.UINT, Subtype.UDINT, Subtype.ULINT);

    private final SymbolTable symbolTable;
    private final SymbolKeys keys;
    private final LoweringReport report;

    /**
     * Creates the lowering of a function block.
     *
     * @param symbolTable   the table filled by the lexer and the parser
     * @param functionBlock name of the function block
     * @param diagnostics   where problems are reported
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "The symbol table is shared by every phase by design; the lowering only reads it"
    )
    public ControllerLowering(final SymbolTable symbolTable, final String functionBlock,
                              final DiagnosticsHandler diagnostics) {
        this.symbolTable = symbolTable;
        this.keys = new SymbolKeys(functionBlock);
        this.report = new LoweringReport(diagnostics);
    }

    /**
     * Builds the controller model.
     *
     * @param ast                the rule blocks
     * @param interfaceVariables keys of the inputs and outputs in declaration order
     * @return the model, or empty if the program has something the generator does not support (reported)
     */
    public Optional<ControllerModel> lower(final AbstractSyntaxTree ast, final List<String> interfaceVariables) {
        OptionalInt bitWidth = bitWidth();
        if (!bitWidth.isPresent()) {
            return Optional.empty();
        }
        FuzzySetLowering sets = new FuzzySetLowering((1 << bitWidth.getAsInt()) - 1, report);

        List<String> inputKeys = new ArrayList<>();
        List<String> outputKeys = new ArrayList<>();
        Map<String, List<String>> termsByVariable = new LinkedHashMap<>();
        List<Variable> inputs = new ArrayList<>();
        List<Variable> outputs = new ArrayList<>();
        Map<String, Defuzzifier> defuzzifiers = new LinkedHashMap<>();
        for (String key : interfaceVariables) {
            boolean isInput = symbolTable.get(key).source == Source.IN;
            LexemeInfo block = symbolTable.get(isInput ? keys.fuzzifier(key) : keys.defuzzifier(key));
            boolean integer = isIntegerVariable(key);
            if (!hasBlock(key, block, isInput)) {
                continue;
            }
            // Registered even if it cannot be lowered, so that the rules that use it are still checked.
            termsByVariable.put(key, block.parameters);
            (isInput ? inputKeys : outputKeys).add(key);
            boolean supportedMethod = isInput || isCentroid(key, block);
            Optional<Variable> variable = variable(key, block, sets);
            if (!integer || !supportedMethod || !variable.isPresent()) {
                continue;
            }
            if (isInput) {
                inputs.add(variable.get());
            } else if (hasOnlySingletons(variable.get())) {
                outputs.add(variable.get());
                defuzzifiers.put(variable.get().name(), new CentroidDefuzzifier());
            }
        }
        if (inputKeys.isEmpty() || outputKeys.isEmpty()) {
            report.error(new UnsupportedFeature("the controller needs at least one input with FUZZIFY and one "
                    + "output with DEFUZZIFY"));
        }
        List<int[]> rules = new RuleLowering(keys, inputKeys, outputKeys, termsByVariable, report).lower(ast);

        if (report.failed()) {
            return Optional.empty();
        }
        return Optional.of(new ControllerModel(bitWidth.getAsInt(), inputs, outputs, new MinMaxMethod(rules),
                defuzzifiers));
    }

    private OptionalInt bitWidth() {
        LexemeInfo pragma = symbolTable.get(keys.pragma("bit_width"));
        if (pragma == null) {
            report.warning(new GeneratorWarning("no PRAGMA bit_width in an OPTION block, using "
                    + DEFAULT_BIT_WIDTH + " bits"));
            return OptionalInt.of(DEFAULT_BIT_WIDTH);
        }
        String value = ((Initialization) pragma.initialValue).variableValue();
        try {
            int width = Integer.parseInt(value);
            if (width >= ControllerModel.MIN_BIT_WIDTH && width <= ControllerModel.MAX_BIT_WIDTH) {
                return OptionalInt.of(width);
            }
        } catch (NumberFormatException e) {
            // reported below
        }
        report.error(new UnsupportedFeature("PRAGMA bit_width " + value + " (it must be an integer between "
                + ControllerModel.MIN_BIT_WIDTH + " and " + ControllerModel.MAX_BIT_WIDTH + ")"));
        return OptionalInt.empty();
    }

    private boolean isIntegerVariable(final String key) {
        LexemeInfo variable = symbolTable.get(key);
        boolean simpleOrSubrange = variable.type == Type.SIMPLE || variable.type == Type.SUBRANGE;
        if (simpleOrSubrange && INTEGER_TYPES.contains(variable.subtype)) {
            return true;
        }
        report.error(new UnsupportedFeature("variable " + keys.shortName(key) + " is " + variable.subtype
                + " (only integer types are supported)"));
        return false;
    }

    private boolean hasBlock(final String key, final LexemeInfo block, final boolean isInput) {
        if (block != null) {
            return true;
        }
        report.error(new UnsupportedFeature((isInput ? "input " : "output ") + keys.shortName(key) + " has no "
                + (isInput ? "FUZZIFY" : "DEFUZZIFY") + " block"));
        return false;
    }

    private Optional<Variable> variable(final String key, final LexemeInfo block, final FuzzySetLowering sets) {
        List<FuzzySet> fuzzySets = new ArrayList<>();
        boolean complete = true;
        for (String termName : block.parameters) {
            LexemeInfo term = symbolTable.get(keys.term(key, termName));
            Optional<FuzzySet> set = sets.lower(termName, term, "term " + termName + " of " + keys.shortName(key));
            if (set.isPresent()) {
                fuzzySets.add(set.get());
            } else {
                complete = false;
            }
        }
        return complete ? Optional.of(new Variable(keys.shortName(key), fuzzySets)) : Optional.empty();
    }

    /** The defuzzification method must be CoGS; DEFAULT (mandatory in FCL) is not implemented by the generator. */
    private boolean isCentroid(final String key, final LexemeInfo block) {
        String name = keys.shortName(key);
        report.warning(new GeneratorWarning("DEFAULT of " + name + " is ignored: if no rule fires, the centroid "
                + "divides by zero"));
        if (block.subtype == Subtype.COGS) {
            return true;
        }
        report.error(new UnsupportedFeature("METHOD " + block.subtype + " of " + name + " (only CoGS)"));
        return false;
    }

    private boolean hasOnlySingletons(final Variable output) {
        boolean supported = true;
        for (FuzzySet set : output.sets()) {
            if (!(set instanceof SingletonFuzzySet)) {
                report.error(new UnsupportedFeature("term " + set.name() + " of the output " + output.name()
                        + " is not a singleton (only singleton outputs)"));
                supported = false;
            }
        }
        return supported;
    }
}
