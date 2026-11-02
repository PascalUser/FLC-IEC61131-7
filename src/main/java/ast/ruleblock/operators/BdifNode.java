package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

/**
 * Arithmetic difference fuzzy operator node.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class BdifNode extends OperatorNode {
    /**
     * Creates a fuzzy operator node that applies the difference operation.
     *
     * @param operands the operand nodes of the difference
     */
    public BdifNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.BDIF, operands);
    }
}
