package ast.ruleblock;

import ast.AbstractAstNode;

import java.util.Collections;
import java.util.Objects;

public final class WithNode extends AbstractAstNode {
    private final Weight weight;
    private final IfNode ifNode;

    public WithNode(final Weight weight, final IfNode ifNode) {
        super(Collections.singletonList(Objects.requireNonNull(ifNode, "ifNode")));
        this.weight = Objects.requireNonNull(weight, "weight");
        this.ifNode = ifNode;
    }

    public Weight weight() {
        return weight;
    }

    public IfNode ifNode() {
        return ifNode;
    }

    @Override
    public String label() {
        return "WITH " + weight.value();
    }
}
