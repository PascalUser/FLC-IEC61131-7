
package utils.simulation;

import compiler.generator.ControllerModel;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinMaxEvaluationTest extends utils.supports.EvaluationTestSupport {

    @Test
    void Evaluate_UnusedInput_DoesNotChangeActivation() {
        ControllerModel model = buildMinMaxModel(
                2,
                new int[]{0, UNUSED, 1}
        );

        ReferenceSimulator simulator = new ReferenceSimulator(model);
        QuantizedSimulator quantizedSimulator = new QuantizedSimulator(model);

        double[] firstEvaluation = simulator.evaluate(0, 0);
        double[] secondEvaluation = simulator.evaluate(0, 10);
        OptionalInt[] quantizedEvaluation = quantizedSimulator.evaluate(0, 10);
        double[] noRuleEvaluation = simulator.evaluate(10, 0);

        assertEquals(255.0, firstEvaluation[0], 1e-9);
        assertEquals(255.0, secondEvaluation[0], 1e-9);
        assertEquals(OptionalInt.of(255), quantizedEvaluation[0]);
        assertTrue(Double.isNaN(noRuleEvaluation[0]));
    }
}
