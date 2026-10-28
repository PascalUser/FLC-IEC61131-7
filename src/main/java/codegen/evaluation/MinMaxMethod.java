package codegen.evaluation;

import codegen.ControllerModel;
import codegen.CppWriter;
import codegen.Variable;

import java.util.List;

/**
 * MIN-MAX evaluation: each rule is the minimum of the degrees of its input sets (AND: MIN), and each output set
 * keeps the maximum over the rules that conclude it (ACCU: MAX).
 * <p>
 * Port of {@code code_generation.evaluation_method.MinMaxMethod} (Luca Sarramone, Generador-FLC
 * {@code c304c1d}). Outputs are visited from the last to the first, as in the original.
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class MinMaxMethod extends EvaluationMethod {

    /**
     * Creates a MIN-MAX evaluation.
     *
     * @param rules rule matrix (see {@link EvaluationMethod})
     */
    public MinMaxMethod(final List<int[]> rules) {
        super(rules);
    }

    @Override
    public void compileEvalMethod(final CppWriter out, final ControllerModel model) {
        List<Variable> inputs = model.inputs();
        List<Variable> outputs = model.outputs();
        out.write("void rulesEvaluation(){ \n");
        out.write(" #pragma HLS PIPELINE \n");
        for (Variable input : inputs) {
            out.write(" #pragma HLS ARRAY_PARTITION variable=" + input.name() + "Fuzz complete dim=1 \n");
        }
        for (Variable output : outputs) {
            out.write(" #pragma HLS ARRAY_PARTITION variable=" + output.name()
                    + "MembershipValues complete dim=1 \n");
        }
        out.write("\n");
        out.write("\tfixed_int aux = 0; \n");
        for (int outputIndex = outputs.size() - 1; outputIndex >= 0; outputIndex--) {
            int column = model.outputColumn(outputIndex);
            for (int set = 0; set < outputs.get(outputIndex).size(); set++) {
                boolean isFirst = true;
                for (int row = 0; row < rows(); row++) {
                    if (set(row, column) == set) {
                        compileRow(out, model, isFirst, row, outputIndex, set);
                        out.write("\n");
                        isFirst = false;
                    }
                }
            }
        }
        out.write("} \n");
        out.write("\n");
    }

    private void compileRow(final CppWriter out, final ControllerModel model, final boolean isFirst,
                            final int row, final int outputIndex, final int setNumber) {
        String membership = model.outputs().get(outputIndex).name() + "MembershipValues" + "[" + setNumber + "]";
        out.write("\t");
        if (isFirst) {
            out.write(membership + " = min(");
            writeMinimumOfInputs(out, model, row);
            out.write("; \n");
        } else {
            out.write("aux = min(");
            writeMinimumOfInputs(out, model, row);
            out.write("; \n");
            out.write("\tif (" + membership + " < aux) \n");
            out.write("\t\t " + membership + " = aux; \n");
        }
    }

    /** Writes the arguments of the nested {@code min(...)} over every input, with its closing parentheses. */
    private void writeMinimumOfInputs(final CppWriter out, final ControllerModel model, final int row) {
        List<Variable> inputs = model.inputs();
        int varsLeft = inputs.size();
        int minOpened = 1;
        for (int input = 0; input < inputs.size(); input++) {
            out.write(inputs.get(input).name() + "Fuzz[" + set(row, model.inputColumn(input)) + "]");
            if (input != inputs.size() - 1) {
                if (varsLeft > 2) {
                    out.write(", min(");
                    varsLeft--;
                    minOpened++;
                } else {
                    out.write(", ");
                }
            }
        }
        for (int i = 0; i < minOpened; i++) {
            out.write(")");
        }
    }

    @Override
    public void compileHeader(final CppWriter out) {
        out.write("void rulesEvaluation(); \n");
    }
}
