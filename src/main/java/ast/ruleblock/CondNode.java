package ast.ruleblock;

import ast.AbstractAstNode;

import java.util.Collections;
import java.util.Objects;

public final class CondNode extends AbstractAstNode {
    private final AbstractAstNode condition;

    public CondNode(final AbstractAstNode condition) {
        super(Collections.singletonList(Objects.requireNonNull(condition, "condition")));
        this.condition = condition;
    }

    public AbstractAstNode condition() {
        return condition;
    }

    @Override
    public String label() {
        return "COND";
    }
}
