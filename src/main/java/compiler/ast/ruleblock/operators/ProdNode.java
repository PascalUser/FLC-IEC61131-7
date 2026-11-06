package compiler.ast.ruleblock.operators;

import compiler.ast.AbstractAstNode;

import java.util.List;

/**
 * Fuzzy product operator node.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class ProdNode extends OperatorNode {
    /**
     * Creates a fuzzy operator node that applies the product operation.
     *
     * @param operands the operand nodes of the product
     */
    public ProdNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.PROD, operands);
    }
}
