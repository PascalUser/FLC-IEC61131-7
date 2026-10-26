package unit.ast.ruleblock;

import ast.ruleblock.RuleBlockNode;
import ast.ruleblock.operators.MaxNode;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class RuleBlockNodeTest {

    @Test
    void Label_ReturnsRuleBlockName() {
        RuleBlockNode node = new RuleBlockNode(
                "rule_temperature",
                new MaxNode(Collections.emptyList())
        );

        assertEquals("rule_temperature", node.label());
        assertEquals("rule_temperature", node.name());
    }

    @Test
    void Children_ContainsOnlyTheAccumulationOperator() {
        MaxNode accumulation = new MaxNode(Collections.emptyList());
        RuleBlockNode node = new RuleBlockNode("rule_temperature", accumulation);

        assertEquals(1, node.children().size());
        assertSame(accumulation, node.children().get(0));
        assertSame(accumulation, node.accumulation());
    }

    @Test
    void Children_AreUnmodifiable() {
        RuleBlockNode node = new RuleBlockNode(
                "rule_temperature",
                new MaxNode(Collections.emptyList())
        );

        assertChildrenUnmodifiable(node);
    }
}
