package simulation;

import codegen.ControllerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

/**
 * Compares the generated controller ({@link QuantizedSimulator}) with the ideal one ({@link ReferenceSimulator})
 * over a grid of inputs: every combination of input values {@code 0, step, 2*step, ... <= 2^N - 1}.
 * With {@code step = 1} it is exhaustive ({@code 2^(N * inputs)} combinations).
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class SimulationComparison {

    private SimulationComparison() {
        // Utility class - not instantiable
    }

    /**
     * Compares both controllers.
     *
     * @param model the controller
     * @param step  distance between evaluated values of each input (1 = every value)
     * @return one comparison per output, in output order
     */
    public static List<OutputComparison> compare(final ControllerModel model, final int step) {
        if (step < 1) {
            throw new IllegalArgumentException("step must be positive");
        }
        QuantizedSimulator hardware = new QuantizedSimulator(model);
        ReferenceSimulator reference = new ReferenceSimulator(model);
        int outputs = model.outputs().size();
        Accumulator[] accumulators = new Accumulator[outputs];
        for (int output = 0; output < outputs; output++) {
            accumulators[output] = new Accumulator();
        }

        int[] inputs = new int[model.inputs().size()];
        double[] realInputs = new double[inputs.length];
        do {
            for (int i = 0; i < inputs.length; i++) {
                realInputs[i] = inputs[i];
            }
            OptionalInt[] generated = hardware.evaluate(inputs);
            double[] ideal = reference.evaluate(realInputs);
            for (int output = 0; output < outputs; output++) {
                accumulators[output].add(inputs, generated[output], ideal[output]);
            }
        } while (next(inputs, step, model.converterRange()));

        List<OutputComparison> comparisons = new ArrayList<>();
        for (int output = 0; output < outputs; output++) {
            comparisons.add(accumulators[output].result(model.outputs().get(output).name()));
        }
        return comparisons;
    }

    /** Advances the inputs like an odometer; false after the last combination. */
    private static boolean next(final int[] inputs, final int step, final int range) {
        for (int i = inputs.length - 1; i >= 0; i--) {
            if (inputs[i] + step <= range) {
                inputs[i] += step;
                return true;
            }
            inputs[i] = 0;
        }
        return false;
    }

    private static final class Accumulator {
        private long evaluated;
        private long defined;
        private double maxError = -1;
        private int[] worstInputs = new int[0];
        private double errorSum;
        private long undefinedInHardware;
        private long undefinedInReference;

        void add(final int[] inputs, final OptionalInt generated, final double ideal) {
            evaluated++;
            if (!generated.isPresent()) {
                undefinedInHardware++;
            }
            if (Double.isNaN(ideal)) {
                undefinedInReference++;
            }
            if (!generated.isPresent() || Double.isNaN(ideal)) {
                return;
            }
            double error = Math.abs(generated.getAsInt() - ideal);
            defined++;
            errorSum += error;
            if (error > maxError) {
                maxError = error;
                worstInputs = inputs.clone();
            }
        }

        OutputComparison result(final String output) {
            double mean = (defined == 0) ? 0 : errorSum / defined;
            return new OutputComparison(output, evaluated, Math.max(maxError, 0), worstInputs, mean,
                    undefinedInHardware, undefinedInReference);
        }
    }
}
