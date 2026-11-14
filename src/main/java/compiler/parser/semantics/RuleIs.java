package compiler.parser.semantics;

import compiler.ast.AbstractAstNode;
import compiler.ast.ruleblock.IfNode;
import compiler.ast.ruleblock.IsNode;
import compiler.ast.ruleblock.RuleBlockNode;
import compiler.ast.ruleblock.WithNode;
import compiler.parser.utils.IdentifierResolver;
import compiler.repository.enums.Source;

import java.util.ArrayList;
import java.util.List;

public final class RuleIs {
    final String block;
    final String rule;
    final Source role;
    final IsNode is;

    public RuleIs(String block, String rule, Source role, IsNode is) {
        this.block = block;
        this.rule = rule;
        this.role = role;
        this.is = is;
    }

    static List<RuleIs> allOf(RuleBlockNode ruleBlock, IdentifierResolver idResolver) {
        List<RuleIs> result = new ArrayList<>();
        String blockName = idResolver.name(ruleBlock.label());
        int number = 0;
        for (AbstractAstNode rule : ruleBlock.accumulation().children()) {
            IfNode ifNode = (rule instanceof WithNode) ? ((WithNode) rule).ifNode() : (IfNode) rule;
            String id = String.valueOf(++number);
            collect(ifNode.condition(), Source.IN, blockName, id, result);
            collect(ifNode.body(), Source.OUT, blockName, id, result);
        }
        return result;
    }

    private static void collect(AbstractAstNode node, Source role, String block, String rule, List<RuleIs> out) {
        if (node instanceof IsNode) {
            out.add(new RuleIs(block, rule, role, (IsNode) node));
            return;
        }
        for (AbstractAstNode child : node.children()) collect(child, role, block, rule, out);
    }
}
