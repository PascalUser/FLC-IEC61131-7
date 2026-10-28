package codegen;

import codegen.defuzzification.Defuzzifier;

import java.util.List;

/**
 * Emits the Vivado HLS C++ of a fuzzy controller: implementation, header and test bench.
 * <p>
 * Port of {@code code_generation.CodeGenerator} of Luca Sarramone's generator (Generador-FLC, commit
 * {@code c304c1d}), used with his permission. The generated code is the same; the differences are internal:
 * the model is passed explicitly instead of living in static fields, and the code is returned as text instead
 * of being written to files. The test bench keeps the fixed {@code ap_uint<8>} and {@code ap_uint<18>} types of
 * the original.
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class CodeGenerator {
    private final ControllerModel model;
    private final Fuzzifier fuzzifier;

    /**
     * Creates the generator of a controller.
     *
     * @param model the controller to emit
     */
    public CodeGenerator(final ControllerModel model) {
        this.model = model;
        this.fuzzifier = new Fuzzifier(model);
    }

    /**
     * Generates the implementation ({@code <base>_out.cpp}).
     *
     * @return the C++ source
     */
    public String generateCpp() {
        CppWriter out = new CppWriter();
        out.write("#include <iostream> \n");
        out.write("#include <ap_int.h> \n \n");
        writeTypes(out);
        fuzzifier.compileFunctionsSlope(out);
        fuzzifier.compileInputVariablesBuffers(out);
        Defuzzifier.compileOutputVariablesBuffers(out, model);
        out.write("\n \n");
        fuzzifier.compileFuzzifier(out);
        model.evaluationMethod().compileEvalMethod(out, model);
        List<Variable> outputs = model.outputs();
        for (int i = 0; i < outputs.size(); i++) {
            model.defuzzifier(outputs.get(i).name()).compileDefuzz(out, model, i);
        }
        out.write("\n");
        writeControllerSignature(out);
        out.write("){ \n");
        out.write(" #pragma HLS DATAFLOW \n");
        out.write("\n");
        out.write("\tfuzzifier(" + inputNames() + ");\n");
        out.write("\trulesEvaluation(); \n");
        out.write("\n");
        StringBuilder returnExpression = new StringBuilder("output0");
        for (int i = 0; i < outputs.size(); i++) {
            out.write("\tfixed_out output" + i + " = defuzzifier" + outputs.get(i).name() + "()");
            if (i <= 0) {
                out.write(";\n");
            } else {
                out.write(" <<" + model.converterSize() * i + ";\n");
                returnExpression.append(" + output").append(i);
            }
        }
        out.write("\treturn " + returnExpression + ";\n");
        out.write("}\n");
        return out.toString();
    }

    /**
     * Generates the header ({@code <base>_out.h}).
     *
     * @return the C++ header
     */
    public String generateHeader() {
        CppWriter out = new CppWriter();
        out.write("#include <iostream> \n" + "#include <ap_int.h> \n \n");
        writeTypes(out);
        fuzzifier.compileHeader(out);
        model.evaluationMethod().compileHeader(out);
        for (int i = 0; i < model.outputs().size(); i++) {
            model.defuzzifier(model.outputs().get(i).name()).compileHeader(out, model, i);
        }
        writeControllerSignature(out);
        out.write("); \n");
        return out.toString();
    }

    /**
     * Generates the test bench ({@code <base>_outTB.cpp}), which calls the controller with every input at half
     * of the converter range.
     *
     * @param headerFile name of the header to include, e.g. {@code pendulo_invertido_out.h}
     * @return the C++ test bench
     */
    public String generateTestBench(final String headerFile) {
        CppWriter out = new CppWriter();
        out.write("#include <iostream> \n"
                + "#include \"" + headerFile + "\" \n"
                + "#include <ap_int.h> \n"
                + "\n"
                + "typedef ap_uint<8> fixed_int;\n"
                + "typedef ap_uint<18> fixed_aux;\n"
                + "\n"
                + "\n"
                + "using namespace std;\n"
                + "\n"
                + "int main(int argc, char **argv){ \n\n");
        for (Variable input : model.inputs()) {
            out.write("\tfixed_int " + input.name() + " = " + (model.converterRange() / 2) + "; \n");
        }
        out.write("\n");
        out.write("\tcout <<  fuzzyController(" + inputNames() + "); \n\n");
        out.write("\treturn 0;\n}");
        return out.toString();
    }

    private void writeTypes(final CppWriter out) {
        out.write("typedef ap_uint<" + model.converterSize() + "> fixed_int; \n");
        for (int i = 0; i < model.outputs().size(); i++) {
            out.write("typedef ap_uint<" + model.auxTypeSize(i) + "> fixed_aux_" + model.outputs().get(i).name()
                    + "; \n");
        }
        out.write("typedef ap_uint<" + model.outTypeSize() + "> fixed_out; \n");
        out.write("using namespace std; \n \n");
    }

    private void writeControllerSignature(final CppWriter out) {
        List<Variable> inputs = model.inputs();
        out.write("fixed_out fuzzyController(fixed_int " + inputs.get(0).name());
        for (int i = 1; i < inputs.size(); i++) {
            out.write(", fixed_int " + inputs.get(i).name());
        }
    }

    private String inputNames() {
        StringBuilder names = new StringBuilder(model.inputs().get(0).name());
        for (int i = 1; i < model.inputs().size(); i++) {
            names.append(", ").append(model.inputs().get(i).name());
        }
        return names.toString();
    }
}
