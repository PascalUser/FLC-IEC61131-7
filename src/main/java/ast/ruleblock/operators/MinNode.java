package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

public final class MinNode extends OperatorNode {
    public MinNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.MIN, operands);
    }
}
