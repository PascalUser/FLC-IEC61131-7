package ast;

import ast.ruleblock.RuleBlockNode;

import java.util.List;

public class AbstractSyntaxTree extends AbstractAstNode {

    public AbstractSyntaxTree(List<RuleBlockNode> ruleBlocks) {
        super(ruleBlocks);
    }

    @Override
    public String label() {
        return "root";
    }
}
