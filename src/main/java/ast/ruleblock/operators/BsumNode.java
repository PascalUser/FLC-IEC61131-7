package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

/**
 * Boolean sum fuzzy operator node.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class BsumNode extends OperatorNode {
    /**
     * Creates a fuzzy operator node that applies the boolean sum operation.
     *
     * @param operands the operand nodes of the boolean sum
     */
    public BsumNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.BSUM, operands);
    }
}
