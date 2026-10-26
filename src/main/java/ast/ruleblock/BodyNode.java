package ast.ruleblock;

import ast.AbstractAstNode;
import ast.ruleblock.operators.OperatorNode;

import java.util.Collections;
import java.util.Objects;

public final class BodyNode extends AbstractAstNode {
    private final OperatorNode act;

    public BodyNode(final OperatorNode act) {
        super(Collections.singletonList(Objects.requireNonNull(act, "act")));
        this.act = act;
    }

    public OperatorNode act() {
        return act;
    }

    @Override
    public String label() {
        return "BODY";
    }
}
