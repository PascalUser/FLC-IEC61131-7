package unit.ast.ruleblock.operators;

import ast.AbstractAstNode;
import ast.ruleblock.IdentifierNode;
import ast.ruleblock.operators.AsumNode;
import ast.ruleblock.operators.BdifNode;
import ast.ruleblock.operators.BsumNode;
import ast.ruleblock.operators.MaxNode;
import ast.ruleblock.operators.MinNode;
import ast.ruleblock.operators.NsumNode;
import ast.ruleblock.operators.Operator;
import ast.ruleblock.operators.OperatorNode;
import ast.ruleblock.operators.ProdNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.AstTestSupport.assertChildrenUnmodifiable;

class OperatorNodeTest {

    private static final List<AbstractAstNode> OPERANDS = Arrays.asList(
            new IdentifierNode("a"),
            new IdentifierNode("b")
    );

    private static Stream<Arguments> operatorNodes() {
        return Stream.of(
                Arguments.of(new MaxNode(OPERANDS), Operator.MAX, "MAX"),
                Arguments.of(new MinNode(OPERANDS), Operator.MIN, "MIN"),
                Arguments.of(new ProdNode(OPERANDS), Operator.PROD, "PROD"),
                Arguments.of(new AsumNode(OPERANDS), Operator.ASUM, "ASUM"),
                Arguments.of(new BsumNode(OPERANDS), Operator.BSUM, "BSUM"),
                Arguments.of(new BdifNode(OPERANDS), Operator.BDIF, "BDIF"),
                Arguments.of(new NsumNode(OPERANDS), Operator.NSUM, "NSUM")
        );
    }

    @ParameterizedTest
    @MethodSource("operatorNodes")
    void LabelAndOperator_ReturnConfiguredValues(
            final OperatorNode node,
            final Operator expectedOperator,
            final String expectedLabel
    ) {
        assertAll(
                () -> assertEquals(expectedOperator, node.operator()),
                () -> assertEquals(expectedLabel, node.label())
        );
    }

    @Test
    void Children_ArePreservedInOrder() {
        IdentifierNode first = new IdentifierNode("a");
        IdentifierNode second = new IdentifierNode("b");
        IdentifierNode third = new IdentifierNode("c");
        MaxNode node = new MaxNode(Arrays.asList(first, second, third));

        assertEquals(3, node.children().size());
        assertSame(first, node.children().get(0));
        assertSame(second, node.children().get(1));
        assertSame(third, node.children().get(2));
    }

    @Test
    void Children_CanBeEmpty() {
        MaxNode node = new MaxNode(Collections.emptyList());

        assertTrue(node.children().isEmpty());
    }

    @Test
    void Children_AreUnmodifiable() {
        MaxNode node = new MaxNode(OPERANDS);

        assertChildrenUnmodifiable(node);
    }

    @Test
    void Constructor_WithNullOperands_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MaxNode(null));
    }

    @Test
    void Constructor_WithNullOperand_ThrowsNullPointerException() {
        List<AbstractAstNode> operands = Arrays.asList(new IdentifierNode("a"), null);

        assertThrows(NullPointerException.class, () -> new MaxNode(operands));
    }
}
