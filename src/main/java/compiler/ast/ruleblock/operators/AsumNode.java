package compiler.ast.ruleblock.operators;

import compiler.ast.AbstractAstNode;

import java.util.List;

/**
 * Arithmetic sum fuzzy operator node.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class AsumNode extends OperatorNode {
    /**
     * Creates a fuzzy operator node that applies the sum operation.
     *
     * @param operands the operand nodes of the sum
     */
    public AsumNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.ASUM, operands);
    }
}
