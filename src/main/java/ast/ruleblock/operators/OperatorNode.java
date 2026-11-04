package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;
import java.util.Objects;

/**
 * Fuzzy operator applied to its operand nodes.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class OperatorNode extends AbstractAstNode {
    private final Operator operator;

    /**
     * Creates an operator node.
     *
     * @param operator fuzzy operator applied to the operands
     * @param operands operands of the operator
     */
    public OperatorNode(final Operator operator, final List<? extends AbstractAstNode> operands) {
        super(operands);
        this.operator = Objects.requireNonNull(operator, "operator");
    }

    /**
     * Returns the operator of this node.
     *
     * @return the fuzzy operator applied to the operands
     */
    public final Operator operator() {
        return operator;
    }

    /**
     * Returns the label of this node: the name of the operator.
     *
     * @return the label of the operator
     */
    @Override
    public final String label() {
        return operator.name();
    }
}
