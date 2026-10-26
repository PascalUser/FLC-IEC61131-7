package ast.ruleblock;

import ast.AbstractAstNode;

import java.util.Collections;
import java.util.Objects;

public final class NotNode extends AbstractAstNode {
    private final AbstractAstNode operand;

    public NotNode(final AbstractAstNode operand) {
        super(Collections.singletonList(Objects.requireNonNull(operand, "operand")));
        this.operand = operand;
    }

    public AbstractAstNode operand() {
        return operand;
    }

    @Override
    public String label() {
        return "NOT";
    }
}
