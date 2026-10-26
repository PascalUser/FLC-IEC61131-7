package ast.ruleblock;

import ast.AbstractAstNode;
import ast.ruleblock.operators.OperatorNode;

import java.util.Collections;
import java.util.Objects;

public final class RuleBlockNode extends AbstractAstNode {
    private final String name;
    private final OperatorNode accumulation;

    public RuleBlockNode(final String name, final OperatorNode accumulation) {
        super(Collections.singletonList(Objects.requireNonNull(accumulation, "accumulation")));
        this.name = Objects.requireNonNull(name, "name");
        this.accumulation = accumulation;
    }

    public String name() {
        return name;
    }

    public OperatorNode accumulation() {
        return accumulation;
    }

    @Override
    public String label() {
        return name;
    }
}
