package lowering;

import ast.AbstractAstNode;
import ast.AbstractSyntaxTree;
import ast.ruleblock.IdentifierNode;
import ast.ruleblock.IfNode;
import ast.ruleblock.IsNode;
import ast.ruleblock.NotNode;
import ast.ruleblock.RuleBlockNode;
import ast.ruleblock.WithNode;
import ast.ruleblock.operators.Operator;
import ast.ruleblock.operators.OperatorNode;
import utils.diagnostics.UndeclaredReference;
import utils.diagnostics.UnsupportedFeature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Turns the rule block AST into the rule matrix of the generator (one row per rule, one column per input and
 * then per output, each cell the index of a term of that variable).
 * <p>
 * The generator evaluates every rule as the minimum of one term of every input and concludes one term of every
 * output, accumulated with the maximum. So a rule is supported if it is an {@code AND} with {@code MIN} of
 * {@code input IS term} using every input once, concludes {@code output IS term} for every output, and has no
 * weight other than 1. There must be exactly one {@code RULEBLOCK} (Basic Level) with {@code ACCU: MAX}. The
 * activation method does not matter because outputs are singletons. Anything else is reported.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
final class RuleLowering {
    private static final int UNUSED = -1;
    private static final List<Operator> OR_OPERATORS = Arrays.asList(Operator.MAX, Operator.ASUM, Operator.BSUM);

    private final SymbolKeys keys;
    private final List<String> inputKeys;
    private final List<String> outputKeys;
    private final Map<String, List<String>> termsByVariable;
    private final LoweringReport report;

    /**
     * Creates the lowering of rules for a set of variables.
     *
     * @param keys            names of the symbols of the function block
     * @param inputKeys       keys of the inputs, in column order
     * @param outputKeys      keys of the outputs, in column order
     * @param termsByVariable term names of each variable, in the order of its sets
     * @param report          where problems are reported
     */
    RuleLowering(final SymbolKeys keys, final List<String> inputKeys, final List<String> outputKeys,
                 final Map<String, List<String>> termsByVariable, final LoweringReport report) {
        this.keys = keys;
        this.inputKeys = inputKeys;
        this.outputKeys = outputKeys;
        this.termsByVariable = termsByVariable;
        this.report = report;
    }

    /**
     * Lowers the rules of a function block.
     *
     * @param ast the rule blocks
     * @return the rows of the supported rules; the unsupported ones are reported
     */
    List<int[]> lower(final AbstractSyntaxTree ast) {
        List<AbstractAstNode> blocks = ast.children();
        if (blocks.size() != 1) {
            unsupported("exactly one RULEBLOCK is supported (Basic Level), found " + blocks.size());
            return Collections.emptyList();
        }
        RuleBlockNode block = (RuleBlockNode) blocks.get(0);
        OperatorNode accumulation = block.accumulation();
        if (accumulation.operator() != Operator.MAX) {
            unsupported("ACCU: " + accumulation.operator() + " in " + block.name() + " (only MAX)");
        }
        if (accumulation.children().isEmpty()) {
            unsupported(block.name() + " has no rules");
        }
        List<int[]> rows = new ArrayList<>();
        int number = 0;
        for (AbstractAstNode rule : accumulation.children()) {
            number++;
            Optional<int[]> row = row(rule, "rule " + number + " of " + block.name());
            row.ifPresent(rows::add);
        }
        return rows;
    }

    private Optional<int[]> row(final AbstractAstNode rule, final String where) {
        IfNode ifNode;
        if (rule instanceof WithNode) {
            WithNode weighted = (WithNode) rule;
            if (!isOne(weighted.weight())) {
                return unsupported(where + " has the weighting factor WITH " + weighted.weight());
            }
            ifNode = weighted.ifNode();
        } else {
            ifNode = (IfNode) rule;
        }

        int[] row = new int[inputKeys.size() + outputKeys.size()];
        Arrays.fill(row, UNUSED);
        List<IsNode> conditions = new ArrayList<>();
        if (!collectConditions(ifNode.condition().condition(), conditions, where)) {
            return Optional.empty();
        }
        for (IsNode condition : conditions) {
            if (!place(row, condition, inputKeys, 0, where, "condition")) {
                return Optional.empty();
            }
        }
        for (AbstractAstNode conclusion : ifNode.body().act().children()) {
            if (!(conclusion instanceof IsNode)) {
                return unsupported(where + " concludes " + conclusion.label() + " without IS");
            }
            if (!place(row, (IsNode) conclusion, outputKeys, inputKeys.size(), where, "conclusion")) {
                return Optional.empty();
            }
        }
        for (int i = 0; i < inputKeys.size(); i++) {
            if (row[i] == UNUSED) {
                return unsupported(where + " does not use the input " + keys.shortName(inputKeys.get(i))
                        + " (every rule must use every input)");
            }
        }
        for (int i = 0; i < outputKeys.size(); i++) {
            if (row[inputKeys.size() + i] == UNUSED) {
                return unsupported(where + " does not conclude the output " + keys.shortName(outputKeys.get(i))
                        + " (every rule must conclude every output)");
            }
        }
        return Optional.of(row);
    }

    /** Flattens a chain of AND: MIN into its subconditions; false, after reporting, if something else is used. */
    private boolean collectConditions(final AbstractAstNode node, final List<IsNode> conditions,
                                      final String where) {
        if (node instanceof IsNode) {
            conditions.add((IsNode) node);
            return true;
        }
        if (node instanceof OperatorNode) {
            Operator operator = ((OperatorNode) node).operator();
            if (OR_OPERATORS.contains(operator)) {
                unsupported(where + " uses OR");
                return false;
            }
            if (operator != Operator.MIN) {
                unsupported(where + " uses AND: " + operator + " (only AND: MIN)");
                return false;
            }
            for (AbstractAstNode child : node.children()) {
                if (!collectConditions(child, conditions, where)) {
                    return false;
                }
            }
            return true;
        }
        if (node instanceof NotNode) {
            unsupported(where + " uses NOT");
        } else if (node instanceof IdentifierNode) {
            unsupported(where + " uses the variable " + node.label() + " as a subcondition");
        } else {
            unsupported(where + " has an unexpected condition " + node.label());
        }
        return false;
    }

    /** Puts the term index of {@code variable IS term} in its column; false, after reporting, on a problem. */
    private boolean place(final int[] row, final IsNode is, final List<String> variables, final int offset,
                          final String where, final String part) {
        String variableKey = keys.variable(is.variable().name());
        int index = variables.indexOf(variableKey);
        if (index < 0) {
            if (termsByVariable.containsKey(variableKey)) {
                unsupported(where + " uses " + is.variable().name() + " in the " + part
                        + " but it is not an " + ("condition".equals(part) ? "input" : "output"));
            } else {
                report.error(new UndeclaredReference("variable " + is.variable().name() + " in " + where));
            }
            return false;
        }
        int term = termsByVariable.get(variableKey).indexOf(is.term().name());
        if (term < 0) {
            report.error(new UndeclaredReference("term " + is.term().name() + " of " + is.variable().name()
                    + " in " + where));
            return false;
        }
        if (row[offset + index] != UNUSED) {
            unsupported(where + " uses " + is.variable().name() + " more than once");
            return false;
        }
        row[offset + index] = term;
        return true;
    }

    private static boolean isOne(final String weight) {
        try {
            return new BigDecimal(weight).compareTo(BigDecimal.ONE) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private <T> Optional<T> unsupported(final String detail) {
        report.error(new UnsupportedFeature(detail));
        return Optional.empty();
    }
}
