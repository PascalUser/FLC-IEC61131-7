package compiler.ast.ruleblock;

import compiler.ast.AbstractAstNode;

import java.util.Collections;
import java.util.Objects;

/**
 * Not: the logical negation of an operand.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class NotNode extends AbstractAstNode {
    private final AbstractAstNode operand;

    /**
     * Creates a negation node.
     *
     * @param operand operand to negate
     */
    public NotNode(final AbstractAstNode operand) {
        super(Collections.singletonList(Objects.requireNonNull(operand, "operand")));
        this.operand = operand;
    }

    /**
     * Returns the operand of this not node.
     *
     * @return the negated operand
     */
    public AbstractAstNode operand() {
        return operand;
    }

    /**
     * Returns the label of this node: the negation of the operand.
     *
     * @return the label of the not node
     */
    @Override
    public String label() {
        return "NOT";
    }
}
