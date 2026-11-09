package compiler.generator.lowers;

import compiler.ast.AbstractSyntaxTree;
import compiler.generator.ControllerModel;
import compiler.generator.Variable;
import compiler.generator.defuzzification.CentroidDefuzzifier;
import compiler.generator.defuzzification.Defuzzifier;
import compiler.generator.evaluation.EvaluationMethod;
import compiler.generator.lowers.sublowers.*;
import compiler.generator.lowers.builders.ControllerModelBuilder;
import compiler.generator.lowers.contexts.LoweringContext;
import compiler.generator.lowers.utils.SymbolKeys;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.SymbolTable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public final class ControllerLower implements Lower<ControllerModel> {
    private final LoweringContext ctx;

    /**
     * Creates the lowering of a whole program.
     *
     * @param symbolTable symbol table produced by the parser
     * @param root syntax tree of the program
     * @param diagnosticsHandler collector of lowering diagnostics
     * @return the lowering of the function block
     */
    public static ControllerLower newInstance(
            SymbolTable symbolTable,
            AbstractSyntaxTree root,
            DiagnosticsHandler diagnosticsHandler
    )  {
        return new ControllerLower(new LoweringContext(
                symbolTable,
                diagnosticsHandler,
                new SymbolKeys(root.label()),
                root
        ));
    }

    /**
     * Creates the lowering of a function block.
     */
    private ControllerLower(final LoweringContext initContext) {
        this.ctx = initContext;
    }

    /**
     * Builds the controller model.
     * <p>
     * Inputs and outputs are taken in declaration order (the {@code parameters} of the function block entry):
     * it fixes the signature of the controller and the bits of each output in its result.
     *
     * @return the model, or empty if the program has something the generator does not support (reported)
     */
    public Optional<ControllerModel> adapt() {
        boolean validModel = true;
        ControllerModelBuilder controller = new ControllerModelBuilder();

        // 1. Checks the controller bit width
        BitWidthLower bitWidthLower = new BitWidthLower(ctx);
        Optional<Integer> intObj = bitWidthLower.adapt();
        if (!intObj.isPresent()) {
            validModel = false;
        } else {
            controller.bitWidth(intObj.get());
        }

        // 2. Checks the input variables
        InputsLower inputsLower = new InputsLower(ctx, bitWidthLower);
        Optional<List<Variable>> inputVarsObj = inputsLower.adapt();
        if (!inputVarsObj.isPresent()) {
            validModel = false;
        } else {
            controller.inputs(inputVarsObj.get());
        }

        // 3. Checks the output variables
        OutputsLower outputsLower = new OutputsLower(ctx, bitWidthLower);
        Optional<List<Variable>> outputVarsObj = outputsLower.adapt();
        if (!outputVarsObj.isPresent()) {
            validModel = false;
        } else {
            List<Variable> outputVars = outputVarsObj.get();
            controller.outputs(outputVars);

            // If they are valid, the defuzzifiers map for the controller model is built
            Map<String, Defuzzifier> defuzzifiers = new LinkedHashMap<>();
            for (Variable output : outputVars) {
                defuzzifiers.put(output.name(), new CentroidDefuzzifier());
            }
            controller.defuzzifiers(defuzzifiers);
        }

        // 4. Checks the internal variables
        InternalVariablesLower varsLower = new InternalVariablesLower(ctx);
        Optional<List<String>> varsObj = varsLower.adapt();
        if (!varsObj.isPresent()) {
            validModel = false;
        }
        // The controller doesn't use local variables, so there's nothing to build

        // 5. Checks the rules
        RuleBlockLower ruleBlockLower = new RuleBlockLower(ctx);
        Optional<EvaluationMethod> evalMethodObj = ruleBlockLower.adapt();
        if (!evalMethodObj.isPresent()) {
            validModel = false;
        } else {
            controller.evaluationMethod(evalMethodObj.get());
        }

        // 6. If everything is OK, the model is built
        if (validModel) {
            return Optional.of(controller.build());
        }
        return Optional.empty();
    }
}
