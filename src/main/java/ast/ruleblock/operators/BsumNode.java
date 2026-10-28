package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

public final class BsumNode extends OperatorNode {
    public BsumNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.BSUM, operands);
    }
}
