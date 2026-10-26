package unit.ast.ruleblock;

import ast.ruleblock.IdentifierNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class IdentifierNodeTest {

    @Test
    void Label_ReturnsName() {
        IdentifierNode node = new IdentifierNode("temperature");

        assertEquals("temperature", node.label());
        assertEquals("temperature", node.name());
    }

    @Test
    void Children_IsEmptyAndUnmodifiable() {
        IdentifierNode node = new IdentifierNode("temperature");

        assertTrue(node.children().isEmpty());
        assertChildrenUnmodifiable(node);
    }

    @Test
    void Constructor_WithNullName_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IdentifierNode(null));
    }
}
