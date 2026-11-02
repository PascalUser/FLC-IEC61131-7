package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

/**
 * Fuzzy minimum operator node.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class MinNode extends OperatorNode {
    /**
     * Creates a fuzzy operator node that applies the minimum operation.
     *
     * @param operands the operand nodes of the minimum
     */
    public MinNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.MIN, operands);
    }
}
