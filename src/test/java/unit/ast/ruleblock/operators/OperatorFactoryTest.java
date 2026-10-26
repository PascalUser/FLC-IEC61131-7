package unit.ast.ruleblock.operators;

import ast.ruleblock.operators.Operator;
import ast.ruleblock.operators.OperatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperatorFactoryTest {

    @Test
    void OrDefault_ReturnsMax() {
        assertEquals(Operator.MAX, OperatorFactory.orDefault());
    }

    @Test
    void AndDefault_ReturnsMin() {
        assertEquals(Operator.MIN, OperatorFactory.andDefault());
    }

    @Test
    void ActDefault_ReturnsMin() {
        assertEquals(Operator.MIN, OperatorFactory.actDefault());
    }

    @Test
    void AccuDefault_ReturnsMax() {
        assertEquals(Operator.MAX, OperatorFactory.accuDefault());
    }
}
