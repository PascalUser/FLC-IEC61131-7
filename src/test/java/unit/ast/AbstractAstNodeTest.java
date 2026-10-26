package unit.ast;

import ast.AbstractAstNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class AbstractAstNodeTest {

    @Test
    void Label_ReturnsConcreteLabel() {
        StubNode node = new StubNode("STUB", Collections.emptyList());

        assertEquals("STUB", node.label());
    }

    @Test
    void Children_AreCopiedDefensively() {
        List<AbstractAstNode> children = new ArrayList<>();
        children.add(new StubNode("A", Collections.emptyList()));
        StubNode node = new StubNode("ROOT", children);

        children.add(new StubNode("B", Collections.emptyList()));

        assertEquals(1, node.children().size());
        assertEquals("A", node.children().get(0).label());
    }

    @Test
    void Children_AreUnmodifiable() {
        StubNode node = new StubNode(
                "ROOT",
                Collections.<AbstractAstNode>singletonList(new StubNode("A", Collections.emptyList()))
        );

        assertChildrenUnmodifiable(node);
    }

    @Test
    void Constructor_WithNullChildren_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StubNode("ROOT", null));
    }

    @Test
    void Constructor_WithNullChild_ThrowsNullPointerException() {
        List<AbstractAstNode> children = new ArrayList<>();
        children.add(null);

        assertThrows(NullPointerException.class, () -> new StubNode("ROOT", children));
    }

    private static final class StubNode extends AbstractAstNode {
        private final String label;

        private StubNode(final String label, final List<? extends AbstractAstNode> children) {
            super(children);
            this.label = label;
        }

        @Override
        public String label() {
            return label;
        }
    }
}
