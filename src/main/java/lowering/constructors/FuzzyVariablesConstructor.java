package lowering.constructors;

import codegen.Variable;
import codegen.defuzzification.CentroidDefuzzifier;
import codegen.defuzzification.Defuzzifier;
import lowering.builders.ControllerModelBuilder;
import lowering.contexts.VarLoweringContext;
import lowering.lowers.VariableLower;
import lowering.contexts.LoweringContext;
import utils.LexemeInfo;
import utils.diagnostics.UnsupportedFeature;
import utils.enums.Source;

import java.util.*;

/**
 * Builds the input and output variables of the controller and their defuzzifiers.
 * <p>
 * Every {@code IN} parameter becomes an input and every other parameter an output with a
 * {@link codegen.defuzzification.CentroidDefuzzifier}. At least one input and one output are required.
 * </p>
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class FuzzyVariablesConstructor implements ModelConstructor {
    private final LoweringContext ctx;
    private final BitWidthConstructor bitWidthConstructor;

    private static String ERR_MISSING_IOVARS() {
        return "the controller needs at least one input with FUZZIFY and one output with DEFUZZIFY";
    }

    /**
     * Creates the stage that lowers the controller variables.
     *
     * @param initContext lowering context shared by the stages
     * @param bitWidthConstructor resolver of the controller bit width
     */
    public FuzzyVariablesConstructor(LoweringContext initContext, BitWidthConstructor bitWidthConstructor) {
        this.ctx = initContext;
        this.bitWidthConstructor = bitWidthConstructor;
    }

    /** {@inheritDoc} */
    @Override
    public boolean construct(ControllerModelBuilder controller) {
        String program = ctx.keys.functionBlock();
        List<String> ioVars = ctx.symbolTable.get(program).parameters;

        boolean invalidController = false;
        List<Variable> inputs = new ArrayList<>();
        List<Variable> outputs = new ArrayList<>();
        Map<String, Defuzzifier> defuzzifiers = new LinkedHashMap<>();
        for (String ioVarKey : ioVars) {
            LexemeInfo ioVarInfo = ctx.symbolTable.get(ioVarKey);
            boolean isInputVariable = ioVarInfo.source == Source.IN;

            VarLoweringContext varCtx = new VarLoweringContext(ctx, ioVarKey, bitWidthConstructor);
            VariableLower varLower = new VariableLower(varCtx);
            Optional<Variable> variable = varLower.adapt();
            if (!variable.isPresent()) {
                invalidController = true;
                continue;
            }
            if (isInputVariable) {
                inputs.add(variable.get());
            } else {
                outputs.add(variable.get());
                defuzzifiers.put(variable.get().name(), new CentroidDefuzzifier());
            }
        }
        if (inputs.isEmpty() || outputs.isEmpty()) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_MISSING_IOVARS()));
        }
        if (invalidController) {
            return false;
        }
        controller.inputs(inputs).outputs(outputs).defuzzifiers(defuzzifiers);
        return true;
    }
}
