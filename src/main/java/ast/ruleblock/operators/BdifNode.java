package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

public final class BdifNode extends OperatorNode {
    public BdifNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.BDIF, operands);
    }
}
