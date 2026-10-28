package codegen.defuzzification;

import codegen.ControllerModel;
import codegen.CppWriter;
import codegen.Variable;

/**
 * Defuzzification stage of one output: emits {@code defuzzifier<output>}.
 * <p>
 * Port of {@code code_generation.defuzz.Defuzzifier} (Luca Sarramone, Generador-FLC {@code c304c1d}).
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class Defuzzifier {

    /**
     * Emits the {@code <output>MembershipValues} buffers of every output, filled by the rule evaluation.
     *
     * @param out   destination of the code
     * @param model the controller
     */
    public static void compileOutputVariablesBuffers(final CppWriter out, final ControllerModel model) {
        for (Variable output : model.outputs()) {
            output.compileBuffer(out, "MembershipValues");
        }
    }

    /**
     * Emits the defuzzification function of an output.
     *
     * @param out         destination of the code
     * @param model       the controller
     * @param outputIndex index of the output in {@link ControllerModel#outputs()}
     */
    public abstract void compileDefuzz(CppWriter out, ControllerModel model, int outputIndex);

    /**
     * Emits the declaration of the defuzzification function for the header.
     *
     * @param out         destination of the code
     * @param model       the controller
     * @param outputIndex index of the output in {@link ControllerModel#outputs()}
     */
    public abstract void compileHeader(CppWriter out, ControllerModel model, int outputIndex);
}
