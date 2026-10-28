package codegen;

import java.util.List;

/**
 * Emits the fuzzification stage: slope constants, the {@code <input>Fuzz} buffers and the {@code fuzzifier}
 * function that fills them.
 * <p>
 * Port of {@code code_generation.fuzz.Fuzzifier} (Luca Sarramone, Generador-FLC {@code c304c1d}).
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class Fuzzifier {
    private final ControllerModel model;

    /**
     * Creates the fuzzifier of a controller.
     *
     * @param model the controller
     */
    public Fuzzifier(final ControllerModel model) {
        this.model = model;
    }

    /**
     * Emits the slope constants of every input.
     *
     * @param out destination of the code
     */
    public void compileFunctionsSlope(final CppWriter out) {
        for (Variable input : model.inputs()) {
            input.compileFunctionsSlope(out, model.converterRange());
            out.write("\n");
        }
    }

    /**
     * Emits the {@code <input>Fuzz} buffers.
     *
     * @param out destination of the code
     */
    public void compileInputVariablesBuffers(final CppWriter out) {
        for (Variable input : model.inputs()) {
            input.compileBuffer(out, "Fuzz");
        }
    }

    /**
     * Emits the {@code fuzzifier} function.
     *
     * @param out destination of the code
     */
    public void compileFuzzifier(final CppWriter out) {
        List<Variable> inputs = model.inputs();
        writeSignature(out, inputs);
        out.write(") { \n");
        out.write(" #pragma HLS PIPELINE \n");
        for (Variable input : inputs) {
            out.write(" #pragma HLS ARRAY_PARTITION variable=" + input.name() + "Fuzz complete dim=1 \n");
        }
        out.write("\n");
        for (Variable input : inputs) {
            out.write(" //" + input.name() + "------------------------------------\n\n");
            input.compile(out, model.converterRange());
        }
        out.write("} \n \n");
    }

    /**
     * Emits the declaration of the {@code fuzzifier} function for the header.
     *
     * @param out destination of the code
     */
    public void compileHeader(final CppWriter out) {
        writeSignature(out, model.inputs());
        out.write("); \n");
    }

    private static void writeSignature(final CppWriter out, final List<Variable> inputs) {
        out.write("void fuzzifier (fixed_int " + inputs.get(0).name());
        for (int i = 1; i < inputs.size(); i++) {
            out.write(", fixed_int " + inputs.get(i).name());
        }
    }
}
