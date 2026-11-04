package ast.ruleblock;

import ast.AbstractAstNode;

import java.util.Arrays;
import java.util.Objects;

/**
 * If: a condition together with the body that is executed when the condition holds.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class IfNode extends AbstractAstNode {
    private final CondNode condition;
    private final BodyNode body;

    /**
     * Creates an if node.
     *
     * @param condition condition of the if
     * @param body body executed when the condition holds
     */
    public IfNode(final CondNode condition, final BodyNode body) {
        super(Arrays.asList(
                Objects.requireNonNull(condition, "condition"),
                Objects.requireNonNull(body, "body")
        ));
        this.condition = condition;
        this.body = body;
    }

    /**
     * Returns the condition of this if node.
     *
     * @return the condition of the if
     */
    public CondNode condition() {
        return condition;
    }

    /**
     * Returns the body of this if node.
     *
     * @return the body executed when the condition holds
     */
    public BodyNode body() {
        return body;
    }

    /**
     * Returns the label of this node: the condition and body it wraps.
     *
     * @return the label of the if node
     */
    @Override
    public String label() {
        return "IF";
    }
}
