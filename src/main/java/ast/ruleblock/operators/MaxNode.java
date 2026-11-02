package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

/**
 * Fuzzy maximum operator node.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class MaxNode extends OperatorNode {
    /**
     * Creates a fuzzy operator node that applies the maximum operation.
     *
     * @param operands the operand nodes of the maximum
     */
    public MaxNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.MAX, operands);
    }
}
