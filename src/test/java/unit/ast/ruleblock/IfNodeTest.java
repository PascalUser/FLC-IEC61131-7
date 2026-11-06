package unit.ast.ruleblock;

import compiler.ast.ruleblock.BodyNode;
import compiler.ast.ruleblock.CondNode;
import compiler.ast.ruleblock.IdentifierNode;
import compiler.ast.ruleblock.IfNode;
import compiler.ast.ruleblock.IsNode;
import compiler.ast.ruleblock.operators.MinNode;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static utils.supports.AstTestSupport.assertChildrenUnmodifiable;

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
