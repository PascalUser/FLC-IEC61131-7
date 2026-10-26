package unit.ast.ruleblock;

import ast.ruleblock.IdentifierNode;
import ast.ruleblock.IsNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class IsNodeTest {

    @Test
    void Label_ReturnsIs() {
        IsNode node = new IsNode(new IdentifierNode("temperature"), new IdentifierNode("high"));

        assertEquals("IS", node.label());
    }

    @Test
    void Children_AreVariableAndTermInOrder() {
        IdentifierNode variable = new IdentifierNode("temperature");
        IdentifierNode term = new IdentifierNode("high");
        IsNode node = new IsNode(variable, term);

        assertEquals(2, node.children().size());
        assertSame(variable, node.children().get(0));
        assertSame(term, node.children().get(1));
        assertSame(variable, node.variable());
        assertSame(term, node.term());
    }

    @Test
    void Children_AreUnmodifiable() {
        IsNode node = new IsNode(new IdentifierNode("temperature"), new IdentifierNode("high"));

        assertChildrenUnmodifiable(node);
    }
}
