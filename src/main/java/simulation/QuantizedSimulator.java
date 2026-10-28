package simulation;

import codegen.ControllerModel;
import codegen.Variable;
import codegen.fuzzysets.FuzzySet;
import codegen.fuzzysets.SingletonFuzzySet;

import java.util.List;
import java.util.OptionalInt;

/**
 * Computes what the generated controller computes, without Vivado: the same integer arithmetic of
 * {@code ap_uint<N>} (integer slopes, truncation to N bits, MIN-MAX, centroid with integer division).
 * <p>
 * It follows the generated code step by step: fuzzification with {@link FuzzySet#quantizedDegree}, rule
 * evaluation as {@link MinMaxEvaluation}, and for each output
 * {@code sum(degree * value) / sum(degree)}. When no rule fires the generated code divides by zero; here the
 * result of that output is empty.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class QuantizedSimulator {
    private final ControllerModel model;
    /** Degree of every term of every input for every input value: {@code [input][value][term]}. */
    private final double[][][] degreeTables;

    /**
     * Creates the simulator of a controller.
     *
     * @param model the controller
     * @throws IllegalArgumentException if it uses a method that is not simulated
     */
    public QuantizedSimulator(final ControllerModel model) {
        MinMaxEvaluation.requireSimulable(model);
        this.model = model;
        int range = model.converterRange();
        List<Variable> inputs = model.inputs();
        degreeTables = new double[inputs.size()][range + 1][];
        for (int input = 0; input < inputs.size(); input++) {
            List<FuzzySet> sets = inputs.get(input).sets();
            for (int x = 0; x <= range; x++) {
                double[] degrees = new double[sets.size()];
                for (int term = 0; term < sets.size(); term++) {
                    degrees[term] = sets.get(term).quantizedDegree(x, range);
                }
                degreeTables[input][x] = degrees;
            }
        }
    }

    /**
     * Evaluates the controller.
     *
     * @param inputs one value per input, in signature order, each in {@code 0 .. 2^N - 1}
     * @return one value per output; empty for an output whose terms all have degree 0 (division by zero)
     * @throws IllegalArgumentException if the number of inputs is wrong or a value is out of range
     */
    public OptionalInt[] evaluate(final int... inputs) {
        if (inputs.length != model.inputs().size()) {
            throw new IllegalArgumentException("Expected " + model.inputs().size() + " inputs");
        }
        double[][] inputDegrees = new double[inputs.length][];
        for (int input = 0; input < inputs.length; input++) {
            if (inputs[input] < 0 || inputs[input] > model.converterRange()) {
                throw new IllegalArgumentException("Input " + inputs[input] + " out of 0.." + model.converterRange());
            }
            inputDegrees[input] = degreeTables[input][inputs[input]];
        }
        double[][] outputDegrees = MinMaxEvaluation.outputDegrees(model, inputDegrees);
        OptionalInt[] outputs = new OptionalInt[outputDegrees.length];
        for (int output = 0; output < outputs.length; output++) {
            outputs[output] = centroid(model.outputs().get(output), outputDegrees[output]);
        }
        return outputs;
    }

    private OptionalInt centroid(final Variable output, final double[] degrees) {
        long numerator = 0;
        long denominator = 0;
        for (int term = 0; term < degrees.length; term++) {
            long degree = (long) degrees[term];
            numerator += degree * ((SingletonFuzzySet) output.sets().get(term)).value();
            denominator += degree;
        }
        if (denominator == 0) {
            return OptionalInt.empty();
        }
        return OptionalInt.of((int) ((numerator / denominator) & model.converterRange()));
    }
}
