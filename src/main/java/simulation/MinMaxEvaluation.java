package simulation;

import codegen.ControllerModel;
import codegen.defuzzification.CentroidDefuzzifier;
import codegen.evaluation.EvaluationMethod;
import codegen.evaluation.MinMaxMethod;

/**
 * Rule evaluation shared by both simulators: each rule activates its output terms with the minimum of the degrees
 * of its input terms (AND: MIN), and each output term keeps the maximum over the rules that conclude it
 * (ACCU: MAX). It is what {@link MinMaxMethod} emits; terms that no rule concludes stay at 0, as the zeroed
 * buffers of the generated code.
 * <p>
 * Only the evaluation and defuzzification methods the generator has are simulated (MIN-MAX and centroid of
 * singletons); a new method needs its simulation here.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
final class MinMaxEvaluation {

    private MinMaxEvaluation() {
        // Utility class - not instantiable
    }

    /**
     * Checks that the controller uses the methods simulated here.
     *
     * @param model the controller
     * @throws IllegalArgumentException if it uses another evaluation or defuzzification method
     */
    static void requireSimulable(final ControllerModel model) {
        if (!(model.evaluationMethod() instanceof MinMaxMethod)) {
            throw new IllegalArgumentException("Only MIN-MAX rule evaluation is simulated");
        }
        for (int i = 0; i < model.outputs().size(); i++) {
            if (!(model.defuzzifier(model.outputs().get(i).name()) instanceof CentroidDefuzzifier)) {
                throw new IllegalArgumentException("Only the centroid of singletons is simulated");
            }
        }
    }

    /**
     * Computes the degree of every output term.
     *
     * @param model        the controller
     * @param inputDegrees degree of every term of every input: {@code [input][term]}
     * @return degree of every term of every output: {@code [output][term]}
     */
    static double[][] outputDegrees(final ControllerModel model, final double[][] inputDegrees) {
        EvaluationMethod rules = model.evaluationMethod();
        int inputs = model.inputs().size();
        double[][] outputDegrees = new double[model.outputs().size()][];
        for (int output = 0; output < outputDegrees.length; output++) {
            outputDegrees[output] = new double[model.outputs().get(output).size()];
        }
        for (int row = 0; row < rules.rows(); row++) {
            double activation = Double.POSITIVE_INFINITY;
            for (int input = 0; input < inputs; input++) {
                activation = Math.min(activation, inputDegrees[input][rules.set(row, model.inputColumn(input))]);
            }
            for (int output = 0; output < outputDegrees.length; output++) {
                int term = rules.set(row, model.outputColumn(output));
                outputDegrees[output][term] = Math.max(outputDegrees[output][term], activation);
            }
        }
        return outputDegrees;
    }
}
