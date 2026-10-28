package codegen.defuzzification;

import codegen.ControllerModel;
import codegen.CppWriter;
import codegen.Variable;

/**
 * Centroid of singletons (FCL {@code METHOD: CoGS}): sum of degree times value over sum of degrees.
 * <p>
 * Port of {@code code_generation.defuzz.CentroidDefuzz} (Luca Sarramone, Generador-FLC {@code c304c1d}). As in
 * the original there is no {@code DEFAULT}: if no rule fires, the denominator is 0.
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class CentroidDefuzzifier extends Defuzzifier {

    @Override
    public void compileDefuzz(final CppWriter out, final ControllerModel model, final int outputIndex) {
        Variable output = model.outputs().get(outputIndex);
        output.compileOutputSetsBuffer(out);
        String varName = output.name();
        out.write("fixed_int defuzzifier" + varName + "() {\n");
        out.write(" #pragma HLS PIPELINE \n");
        out.write(" #pragma HLS ARRAY_PARTITION variable=" + varName + "MembershipValues complete dim=1 \n");
        out.write(" #pragma HLS ARRAY_PARTITION variable=outputValues" + varName + " complete dim=1 \n");
        out.write("\n");
        out.write("\tfixed_aux_" + varName + " numerator = 0; \n ");
        out.write("\tfixed_aux_" + varName + " denominator = 0; \n ");
        out.write("\tfor(int i=0; i<" + output.size() + "; i++){ \n");
        out.write("\t\tnumerator = numerator + " + varName + "MembershipValues[i] * outputValues" + varName
                + "[i]; \n");
        out.write("\t\tdenominator = denominator + " + varName + "MembershipValues[i]; \n");
        out.write("\t} \n");
        out.write("\treturn numerator/denominator; \n");
        out.write("}\n");
    }

    @Override
    public void compileHeader(final CppWriter out, final ControllerModel model, final int outputIndex) {
        out.write("fixed_int defuzzifier" + model.outputs().get(outputIndex).name() + "();\n");
    }
}
