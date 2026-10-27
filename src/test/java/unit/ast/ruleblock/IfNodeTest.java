package unit.ast.ruleblock;

import ast.ruleblock.BodyNode;
import ast.ruleblock.CondNode;
import ast.ruleblock.IdentifierNode;
import ast.ruleblock.IfNode;
import ast.ruleblock.IsNode;
import ast.ruleblock.operators.MinNode;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class IfNodeTest {

    @Test
    void Label_ReturnsIfAndRuleNumber() {
        IfNode node = newNode();

        assertEquals("IF", node.label());
    }

    @Test
    void Children_AreConditionAndBodyInOrder() {
        CondNode condition = new CondNode(new IsNode(
                new IdentifierNode("temperature"),
                new IdentifierNode("high")
        ));
        BodyNode body = new BodyNode(new MinNode(Collections.singletonList(
                new IsNode(new IdentifierNode("dT1"), new IdentifierNode("zero"))
        )));
        IfNode node = new IfNode(condition, body);

        assertEquals(2, node.children().size());
        assertSame(condition, node.children().get(0));
        assertSame(body, node.children().get(1));
        assertSame(condition, node.condition());
        assertSame(body, node.body());
    }

    @Test
    void Children_AreUnmodifiable() {
        assertChildrenUnmodifiable(newNode());
    }

    private static IfNode newNode() {
        return new IfNode(
                new CondNode(new IsNode(new IdentifierNode("temperature"), new IdentifierNode("high"))),
                new BodyNode(new MinNode(Collections.singletonList(
                        new IsNode(new IdentifierNode("dT1"), new IdentifierNode("zero"))
                )))
        );
    }
}
