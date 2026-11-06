package compiler.ast.ruleblock.operators;

import compiler.ast.AbstractAstNode;

import java.util.List;

/**
 * Fuzzy nominal sum operator node.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class NsumNode extends OperatorNode {
    /**
     * Creates a fuzzy operator node that applies the nominal sum operation.
     *
     * @param operands the operand nodes of the nominal sum
     */
    public NsumNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.NSUM, operands);
    }
}
