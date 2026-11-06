package unit.ast.ruleblock;

import compiler.ast.ruleblock.BodyNode;
import compiler.ast.ruleblock.IdentifierNode;
import compiler.ast.ruleblock.IsNode;
import compiler.ast.ruleblock.operators.MinNode;
import compiler.ast.ruleblock.operators.ProdNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static utils.supports.AstTestSupport.assertChildrenUnmodifiable;

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
