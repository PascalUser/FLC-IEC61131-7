package unit.ast.ruleblock;

import ast.ruleblock.IdentifierNode;
import ast.ruleblock.IsNode;
import ast.ruleblock.NotNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class NotNodeTest {

    @Test
    void Label_ReturnsNot() {
        NotNode node = new NotNode(new IdentifierNode("temperature"));

        assertEquals("NOT", node.label());
    }

    @Test
    void Children_ContainsOnlyTheOperand() {
        IdentifierNode operand = new IdentifierNode("temperature");
        NotNode node = new NotNode(operand);

        assertEquals(1, node.children().size());
        assertSame(operand, node.children().get(0));
        assertSame(operand, node.operand());
    }

    @Test
    void IsNot_IsRepresentedAsNotOverIs() {
        IsNode isNode = new IsNode(new IdentifierNode("color"), new IdentifierNode("brown"));
        NotNode node = new NotNode(isNode);

        assertEquals("NOT", node.label());
        assertEquals("IS", node.children().get(0).label());
        assertSame(isNode, node.operand());
    }

    @Test
    void Children_AreUnmodifiable() {
        NotNode node = new NotNode(new IdentifierNode("temperature"));

        assertChildrenUnmodifiable(node);
    }
}
