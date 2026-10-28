package simulation;

import codegen.ControllerModel;
import codegen.Variable;
import codegen.fuzzysets.FuzzySet;
import codegen.fuzzysets.SingletonFuzzySet;

import java.util.List;

/**
 * Evaluates the controller with the ideal semantics of IEC 61131-7, in real numbers: degrees from 0 to 1 linear
 * between the points of each term ({@link FuzzySet#degree}), AND: MIN, ACCU: MAX and the centroid of singletons
 * (CoGS) without rounding. It is the reference to measure the error of the generated controller.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class ReferenceSimulator {
    private final ControllerModel model;

    /**
     * Creates the simulator of a controller.
     *
     * @param model the controller
     * @throws IllegalArgumentException if it uses a method that is not simulated
     */
    public ReferenceSimulator(final ControllerModel model) {
        MinMaxEvaluation.requireSimulable(model);
        this.model = model;
    }

    /**
     * Evaluates the controller.
     *
     * @param inputs one value per input, in signature order
     * @return one value per output; {@code NaN} for an output whose terms all have degree 0 (no rule fires)
     */
    public double[] evaluate(final double... inputs) {
        if (inputs.length != model.inputs().size()) {
            throw new IllegalArgumentException("Expected " + model.inputs().size() + " inputs");
        }
        double[][] inputDegrees = new double[inputs.length][];
        for (int input = 0; input < inputs.length; input++) {
            List<FuzzySet> sets = model.inputs().get(input).sets();
            inputDegrees[input] = new double[sets.size()];
            for (int term = 0; term < sets.size(); term++) {
                inputDegrees[input][term] = sets.get(term).degree(inputs[input]);
            }
        }
        double[][] outputDegrees = MinMaxEvaluation.outputDegrees(model, inputDegrees);
        double[] outputs = new double[outputDegrees.length];
        for (int output = 0; output < outputs.length; output++) {
            outputs[output] = centroid(model.outputs().get(output), outputDegrees[output]);
        }
        return outputs;
    }

    private static double centroid(final Variable output, final double[] degrees) {
        double numerator = 0;
        double denominator = 0;
        for (int term = 0; term < degrees.length; term++) {
            numerator += degrees[term] * ((SingletonFuzzySet) output.sets().get(term)).value();
            denominator += degrees[term];
        }
        return (denominator == 0) ? Double.NaN : numerator / denominator;
    }
}
