package ast.ruleblock.operators;

import ast.AbstractAstNode;

import java.util.List;

public final class NsumNode extends OperatorNode {
    public NsumNode(final List<? extends AbstractAstNode> operands) {
        super(Operator.NSUM, operands);
    }
}
