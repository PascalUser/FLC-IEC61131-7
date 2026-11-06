package compiler.ast.ruleblock;

import compiler.ast.AbstractAstNode;

import java.util.Collections;
import java.util.Objects;

/**
 * Condition of an if or a with: an expression that evaluates to a boolean condition.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class CondNode extends AbstractAstNode {
    private final AbstractAstNode condition;

    /**
     * Creates a condition node.
     *
     * @param condition expression that evaluates to a boolean condition
     */
    public CondNode(final AbstractAstNode condition) {
        super(Collections.singletonList(Objects.requireNonNull(condition, "condition")));
        this.condition = condition;
    }

    /**
     * Returns the condition of this node.
     *
     * @return the condition of the if or with
     */
    public AbstractAstNode condition() {
        return condition;
    }

    /**
     * Returns the label of this node: the condition name.
     *
     * @return the label of the condition
     */
    @Override
    public String label() {
        return "COND";
    }
}
