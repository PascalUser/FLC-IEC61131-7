package compiler.generator.lowers.sublowers;

import compiler.ast.AbstractAstNode;
import compiler.ast.ruleblock.*;
import compiler.ast.ruleblock.operators.Operator;
import compiler.ast.ruleblock.operators.OperatorNode;
import compiler.generator.lowers.Lower;
import compiler.generator.lowers.contexts.RuleLoweringContext;
import compiler.repository.LexemeInfo;
import compiler.repository.diagnostics.UndeclaredReference;
import compiler.repository.diagnostics.UnsupportedFeature;
import compiler.repository.enums.Source;

import java.math.BigDecimal;
import java.util.*;

/**
 * Lowers the rules of one rule block into rows of the rule matrix.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class RuleLower implements Lower<int[]> {
    private final static int UNUSED = -1;
    private static final List<Operator> INVALID_OPERATORS = Arrays.asList(
            Operator.MAX,
            Operator.ASUM,
            Operator.BSUM
    );
    private final RuleLoweringContext ctx;

    /**
     * Creates the lowering of one rule block.
     *
     * @param initContext context with the controller builder, rule block and diagnostics handler
     */
    public RuleLower(RuleLoweringContext initContext) {
        this.ctx = initContext;
    }

    /**
     * Builds the rule matrix rows for the rules of the block.
     *
     * @return the rows, or empty if a rule cannot be lowered
     */
    public Optional<int[]> adapt() {
        Optional<IfNode> ifNodeObj = parseIfNode();
        if (!ifNodeObj.isPresent()) {
            return Optional.empty();
        }
        IfNode ifNode = ifNodeObj.get();

        List<String> inputKeys = ctx.inVarKeys();
        List<String> outputKeys = ctx.outVarKeys();

        int[] row = new int[inputKeys.size() + outputKeys.size()];
        Arrays.fill(row, UNUSED);

        Optional<List<IsNode>> conditionsObj = parseConditions(ifNode.condition().condition());
        if (!conditionsObj.isPresent()) {
            return Optional.empty();
        }
        List<IsNode> conditions = conditionsObj.get();

        for (IsNode condition : conditions) {
            if (couldntPlaceInRow(row, condition, inputKeys, 0)) {
                return Optional.empty();
            }
        }
        for (AbstractAstNode conclusion : ifNode.body().act().children()) {
            if (!(conclusion instanceof IsNode)) {
                // TODO: check if grammar forbids this. If yes then this should be a runtime error
                String mssg = ERR_UNSUPPORTED_CONC(ctx.ruleblockName, ctx.ruleNumber);
                ctx.diagnosticsHandler.add(new UnsupportedFeature(mssg));
                return Optional.empty();
            }
            if (couldntPlaceInRow(row, (IsNode) conclusion, outputKeys, inputKeys.size())) {
                return Optional.empty();
            }
        }
        for (int i = 0; i < inputKeys.size(); i++) {
            if (row[i] == UNUSED) {
                String varName =  ctx.keys.shortName(inputKeys.get(i));
                String mssg = ERR_USELESS_IO(ctx.ruleblockName, ctx.ruleNumber, varName);
                ctx.diagnosticsHandler.add(new UnsupportedFeature(mssg));
                return Optional.empty();
            }
        }
        for (int i = 0; i < outputKeys.size(); i++) {
            if (row[inputKeys.size() + i] == UNUSED) {
                String varName = ctx.keys.shortName(outputKeys.get(i));
                String mssg = ERR_USELESS_IO(ctx.ruleblockName, ctx.ruleNumber, varName);
                ctx.diagnosticsHandler.add(new UnsupportedFeature(mssg));
                return Optional.empty();
            }
        }
        return Optional.of(row);
    }

    private Optional<IfNode> parseIfNode() {
        if (ctx.ruleNode instanceof WithNode) {
            WithNode weighted = (WithNode) ctx.ruleNode;
            String weight = weighted.weight();
            if (!isOne(weight)) {
                String mssg = ERR_UNSUPPORTED_WITH(ctx.ruleblockName, ctx.ruleNumber, weight);
                ctx.diagnosticsHandler.add(new UnsupportedFeature(mssg));
                return Optional.empty();
            }
            return Optional.of(weighted.ifNode());
        } else {
            return Optional.of((IfNode) ctx.ruleNode);
        }
    }

    private Optional<List<IsNode>> parseConditions(final AbstractAstNode node) {
        if (node instanceof IsNode) {
            return Optional.of(Collections.singletonList((IsNode) node));
        }
        if (node instanceof OperatorNode) {
            Operator operator = ((OperatorNode) node).operator();
            if (INVALID_OPERATORS.contains(operator)) {
                String mssg = ERR_UNSUPPORTED_OR(ctx.ruleblockName, ctx.ruleNumber);
                ctx.diagnosticsHandler.add(new UnsupportedFeature(mssg));
                return Optional.empty();
            }
            if (operator != Operator.MIN) {
                String mssg = ERR_UNSUPPORTED_AND(ctx.ruleblockName, ctx.ruleNumber, operator.name());
                ctx.diagnosticsHandler.add(new UnsupportedFeature(mssg));
                return Optional.empty();
            }
            List<IsNode> nodeConditions = new ArrayList<>();
            for (AbstractAstNode child : node.children()) {
                Optional<List<IsNode>> childConditionsObj = parseConditions(child);
                if (!childConditionsObj.isPresent()) {
                    return Optional.empty();
                }
                List<IsNode> childConditions = childConditionsObj.get();
                nodeConditions.addAll(childConditions);
            }
            return Optional.of(nodeConditions);
        }
        String mssg;
        if (node instanceof NotNode) {
            mssg = ERR_UNSUPPORTED_NOT(ctx.ruleblockName, ctx.ruleNumber);
        } else if (node instanceof IdentifierNode) {
            mssg = ERR_UNSUPPORTED_VAR(ctx.ruleblockName, ctx.ruleNumber, node.label());
        } else {
            mssg = ERR_UNEXPECTED_COND(ctx.ruleblockName, ctx.ruleNumber, node.label());
        }
        ctx.diagnosticsHandler.add(new UnsupportedFeature(mssg));
        return Optional.empty();
    }

    private boolean couldntPlaceInRow(final int[] row, final IsNode is, final List<String> ioVars, final int offset) {
        Map<String, List<String>> varWithTermsTable = new LinkedHashMap<>();
        for (String ioVar : ioVars) {
            LexemeInfo ioVarInfo = ctx.symbolTable.get(ioVar);
            Objects.requireNonNull(ioVarInfo);
            boolean isInput =  ioVarInfo.source == Source.IN;
            String componentKey = (isInput) ? ctx.keys.fuzzifier(ioVar) : ctx.keys.defuzzifier(ioVar);
            LexemeInfo componentInfo = ctx.symbolTable.get(componentKey);
            // This can happen after the variable lower reports there's no component associated to the id
            if (componentInfo == null) {
                return true;
            }
            varWithTermsTable.put(ioVar, componentInfo.parameters);
        }
        String ioVarFromIsNode = ctx.keys.variable(is.variable().name());
        int positionOfVarKeyInsideDeclarations = ioVars.indexOf(ioVarFromIsNode);
        if (positionOfVarKeyInsideDeclarations < 0) {
            String mssg = ERR_UNEXPECTED_VAR(ctx.ruleblockName, ctx.ruleNumber, is.variable().name());
            ctx.diagnosticsHandler.add(new UndeclaredReference(mssg));
            return true;
        }
        String termFromIsNode = is.term().name();
        List<String> termsFromComponent = varWithTermsTable.get(ioVarFromIsNode);
        int positionOfTermInComponentDeclaration = termsFromComponent.indexOf(termFromIsNode);
        if (positionOfTermInComponentDeclaration < 0) {
            // TODO: this should be a parser check because it's not related with the code generator
            String mssg = ERR_INVALID_TERM(ctx.ruleblockName, ctx.ruleNumber, is.variable().name(), is.term().name());
            ctx.diagnosticsHandler.add(new UndeclaredReference(mssg));
            return true;
        }
        if (row[offset + positionOfVarKeyInsideDeclarations] != UNUSED) {
            // TODO: check if the standard admits variable repetition. If it does, this should be a parser check
            String mssg = ERR_VARIABLE_REP(ctx.ruleblockName, ctx.ruleNumber, is.variable().name());
            ctx.diagnosticsHandler.add(new UndeclaredReference(mssg));
            return true;
        }
        row[offset + positionOfVarKeyInsideDeclarations] = positionOfTermInComponentDeclaration;
        return false;
    }

    private static boolean isOne(final String weight) {
        try {
            return new BigDecimal(weight).compareTo(BigDecimal.ONE) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String ERR_UNSUPPORTED_WITH(String ruleblockName, String ruleNumber, String strWeight) {
        return "rule " + ruleNumber + " of " + ruleblockName + " has the weighting factor WITH " + strWeight;
    }

    private static String ERR_UNSUPPORTED_OR(String ruleblockName, String ruleNumber) {
        return "rule " + ruleNumber + " of " + ruleblockName + " uses OR";
    }

    private static String ERR_UNSUPPORTED_AND(String ruleblockName, String ruleNumber, String operator) {
        return "rule " + ruleNumber + " of " + ruleblockName +  " uses AND: " + operator + " (only AND: MIN)";
    }

    private static String ERR_UNSUPPORTED_NOT(String ruleblockName, String ruleNumber) {
        return "rule " + ruleNumber + " of " + ruleblockName + " uses NOT";
    }

    private static String ERR_UNSUPPORTED_VAR(String ruleblockName, String ruleNumber, String varName) {
        return "rule " + ruleNumber + " of " + ruleblockName + " uses the variable " + varName + " as a condition";
    }

    private static String ERR_UNEXPECTED_COND(String ruleblockName, String ruleNumber, String label) {
        return "rule " + ruleNumber + " of " + ruleblockName + " has an unexpected condition " + label;
    }

    private static String ERR_UNEXPECTED_VAR(String ruleblockName, String ruleNumber, String ioVarName) {
        String preamble = "conditions must have inputs variables and conclusion outputs, but rule ";
        return  preamble + ruleNumber + " of " + ruleblockName + " uses " + ioVarName;
    }

    private static String ERR_INVALID_TERM(String ruleblockName, String ruleNumber, String ioVarName, String termName) {
        return "in rule " + ruleNumber + " of " + ruleblockName + ", the " + termName + " is not from " + ioVarName;
    }

    private static String ERR_VARIABLE_REP(String ruleblockName, String ruleNumber, String ioVarName) {
        return "rule " + ruleNumber + " of " + ruleblockName + " uses " + ioVarName + " more than once";
    }

    private static String ERR_UNSUPPORTED_CONC(String ruleblockName, String ruleNumber) {
        return "rule " + ruleNumber + " of " + ruleblockName + " has a conclusion that is not an IS";
    }

    private static String ERR_USELESS_IO(String ruleblockName, String ruleNumber, String ioVarName) {
        return ioVarName + " is not used in rule " + ruleNumber + " of " + ruleblockName;
    }
}
