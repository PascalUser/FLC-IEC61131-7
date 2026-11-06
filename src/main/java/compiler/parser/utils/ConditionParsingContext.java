package compiler.parser.utils;

import compiler.parser.tree.ParserLabel;
import compiler.parser.tree.ParserTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Context that accumulates the operands of a boolean condition.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class ConditionParsingContext {
    private ParserTreeNode condition;
    private ParserLabel operator;
    private final List<ParserTreeNode> operands;

    /**
     * Creates a condition context with no operator and no operands.
     */
    public ConditionParsingContext() {
        condition = new ParserTreeNode(ParserLabel.NONE, Collections.emptyList());
        operator = ParserLabel.NONE;
        operands = new ArrayList<>();
    }

    /**
     * Sets the logical operator of the condition.
     *
     * @param newOperator the operator to apply to the operands
     */
    public void addOperator(final ParserLabel newOperator) {
        operator = newOperator;
    }

    /**
     * Adds an operand to the condition, combining operands when the condition is complete.
     *
     * @param operand the operand to add to the condition
     */
    public void addOperand(final ParserTreeNode operand) {
        operands.add(0, operand);
        if (operands.size() == 2) {
            condition = new ParserTreeNode(operator, new ArrayList<>(operands));
            operands.remove(0);
            operands.set(0, condition);
        }
    }

    /**
     * Returns the condition node built so far.
     *
     * @return the condition node, or the pending operand when only one is present
     */
    public ParserTreeNode getConditionNode() {
        if (operands.size() == 1) {
            return operands.get(0);
        }
        return condition;
    }
}
