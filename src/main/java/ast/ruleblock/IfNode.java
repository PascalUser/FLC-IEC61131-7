package ast.ruleblock;

import ast.AbstractAstNode;

import java.util.Arrays;
import java.util.Objects;

public final class IfNode extends AbstractAstNode {
    private final String ruleNumber;
    private final CondNode condition;
    private final BodyNode body;

    public IfNode(final String ruleNumber, final CondNode condition, final BodyNode body) {
        super(Arrays.asList(
                Objects.requireNonNull(condition, "condition"),
                Objects.requireNonNull(body, "body")
        ));
        this.ruleNumber = Objects.requireNonNull(ruleNumber, "ruleNumber");
        this.condition = condition;
        this.body = body;
    }

    public String ruleNumber() {
        return ruleNumber;
    }

    public CondNode condition() {
        return condition;
    }

    public BodyNode body() {
        return body;
    }

    @Override
    public String label() {
        return "IF " + ruleNumber;
    }
}
