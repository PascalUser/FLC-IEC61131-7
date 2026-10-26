package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

public final class AsumNode extends OperatorNode {
    public AsumNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.ASUM, operands);
    }
}
