package unit.ast.ruleblock;

import ast.ruleblock.BodyNode;
import ast.ruleblock.CondNode;
import ast.ruleblock.IdentifierNode;
import ast.ruleblock.IfNode;
import ast.ruleblock.IsNode;
import ast.ruleblock.WithNode;
import ast.ruleblock.operators.MinNode;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class WithNodeTest {

    @Test
    void Label_ForNumericWeight_ContainsWeightValue() {
        WithNode node = new WithNode(".5", newIfNode());

        assertEquals("WITH 0.5", node.label());
    }

    @Test
    void Label_ForIdentifierWeight_ContainsWeightValue() {
        WithNode node = new WithNode("WEIGHT", newIfNode());

        assertEquals("WITH WEIGHT", node.label());
    }

    @Test
    void Children_ContainsOnlyTheIfNode() {
        IfNode ifNode = newIfNode();
        String weight = ".5";
        WithNode node = new WithNode(weight, ifNode);

        assertEquals(1, node.children().size());
        assertSame(ifNode, node.children().get(0));
        assertSame(ifNode, node.ifNode());
        assertSame(weight, node.weight());
    }

    @Test
    void Children_AreUnmodifiable() {
        WithNode node = new WithNode(".5", newIfNode());

        assertChildrenUnmodifiable(node);
    }

    private static IfNode newIfNode() {
        return new IfNode(
                new CondNode(new IsNode(new IdentifierNode("temperature"), new IdentifierNode("high"))),
                new BodyNode(new MinNode(Collections.singletonList(
                        new IsNode(new IdentifierNode("dT1"), new IdentifierNode("zero"))
                )))
        );
    }
}
