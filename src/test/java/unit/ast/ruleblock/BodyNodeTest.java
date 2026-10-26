package unit.ast.ruleblock;

import ast.ruleblock.BodyNode;
import ast.ruleblock.IdentifierNode;
import ast.ruleblock.IsNode;
import ast.ruleblock.operators.MinNode;
import ast.ruleblock.operators.ProdNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class BodyNodeTest {

    @Test
    void Label_ReturnsBody() {
        BodyNode node = new BodyNode(new MinNode(Collections.singletonList(
                new IsNode(new IdentifierNode("dT1"), new IdentifierNode("zero"))
        )));

        assertEquals("BODY", node.label());
    }

    @Test
    void Children_ContainsOnlyTheActOperator() {
        ProdNode act = new ProdNode(Arrays.asList(
                new IsNode(new IdentifierNode("dT1"), new IdentifierNode("zero")),
                new IsNode(new IdentifierNode("dT2"), new IdentifierNode("zero"))
        ));
        BodyNode node = new BodyNode(act);

        assertEquals(1, node.children().size());
        assertSame(act, node.children().get(0));
        assertSame(act, node.act());
    }

    @Test
    void Children_AreUnmodifiable() {
        BodyNode node = new BodyNode(new MinNode(Collections.singletonList(
                new IsNode(new IdentifierNode("dT1"), new IdentifierNode("zero"))
        )));

        assertChildrenUnmodifiable(node);
    }
}
