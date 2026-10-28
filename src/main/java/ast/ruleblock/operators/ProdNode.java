package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

public final class ProdNode extends OperatorNode {
    public ProdNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.PROD, operands);
    }
}
