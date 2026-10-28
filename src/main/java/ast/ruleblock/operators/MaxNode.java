package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

public final class MaxNode extends OperatorNode {
    public MaxNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.MAX, operands);
    }
}
