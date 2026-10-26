package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;
import java.util.Objects;

public abstract class OperatorNode extends AbstractAstNode {
    private final Operator operator;

    protected OperatorNode(final Operator operator, final List<? extends AbstractAstNode> operands) {
        super(operands);
        this.operator = Objects.requireNonNull(operator, "operator");
    }

    public final Operator operator() {
        return operator;
    }

    @Override
    public final String label() {
        return operator.name();
    }
}
