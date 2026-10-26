package unit.ast.ruleblock;

import ast.ruleblock.CondNode;
import ast.ruleblock.IdentifierNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class CondNodeTest {

    @Test
    void Label_ReturnsCond() {
        CondNode node = new CondNode(new IdentifierNode("temperature"));

        assertEquals("COND", node.label());
    }

    @Test
    void Children_ContainsOnlyTheCondition() {
        IdentifierNode condition = new IdentifierNode("temperature");
        CondNode node = new CondNode(condition);

        assertEquals(1, node.children().size());
        assertSame(condition, node.children().get(0));
        assertSame(condition, node.condition());
    }

    @Test
    void Children_AreUnmodifiable() {
        CondNode node = new CondNode(new IdentifierNode("temperature"));

        assertChildrenUnmodifiable(node);
    }
}
