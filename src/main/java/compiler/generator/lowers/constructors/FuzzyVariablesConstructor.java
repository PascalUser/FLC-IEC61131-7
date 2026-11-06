package compiler.generator.lowers.constructors;

import compiler.generator.Variable;
import compiler.generator.defuzzification.CentroidDefuzzifier;
import compiler.generator.defuzzification.Defuzzifier;
import compiler.generator.lowers.BitWidthLower;
import compiler.generator.lowers.builders.ControllerModelBuilder;
import compiler.generator.lowers.contexts.VarLoweringContext;
import compiler.generator.lowers.VariableLower;
import compiler.generator.lowers.contexts.LoweringContext;
import compiler.repository.LexemeInfo;
import compiler.repository.diagnostics.UnsupportedFeature;
import compiler.repository.enums.Source;

import java.util.*;

/**
 * Builds the input and output variables of the controller and their defuzzifiers.
 * <p>
 * Every {@code IN} parameter becomes an input and every other parameter an output with a
 * {@link CentroidDefuzzifier}. At least one input and one output are required.
 * </p>
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class FuzzyVariablesConstructor implements ModelConstructor {
    private final LoweringContext ctx;
    private final BitWidthLower bitWidthLower;

    private static String ERR_MISSING_IOVARS() {
        return "the controller needs at least one input with FUZZIFY and one output with DEFUZZIFY";
    }

    /**
     * Creates the stage that lowers the controller variables.
     *
     * @param initContext lowering context shared by the stages
     * @param bitWidthLower resolver of the controller bit width
     */
    public FuzzyVariablesConstructor(LoweringContext initContext, BitWidthLower bitWidthLower) {
        this.ctx = initContext;
        this.bitWidthLower = bitWidthLower;
    }

    /** {@inheritDoc} */
    @Override
    public boolean construct(ControllerModelBuilder controller) {
        List<String> ioVarKeys = ctx.ioVarKeys();

        boolean invalidController = false;
        List<Variable> inputs = new ArrayList<>();
        List<Variable> outputs = new ArrayList<>();
        Map<String, Defuzzifier> defuzzifiers = new LinkedHashMap<>();
        for (String ioVarKey : ioVarKeys) {
            LexemeInfo ioVarInfo = ctx.symbolTable.get(ioVarKey);
            boolean isInputVariable = ioVarInfo.source == Source.IN;

            VarLoweringContext varCtx = new VarLoweringContext(ctx, ioVarKey, bitWidthLower);
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
            return false;
        }
        if (invalidController) {
            return false;
        }
        controller.inputs(inputs).outputs(outputs).defuzzifiers(defuzzifiers);
        return true;
    }
}
