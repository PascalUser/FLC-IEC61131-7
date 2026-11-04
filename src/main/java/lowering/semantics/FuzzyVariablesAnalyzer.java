package lowering.semantics;

import codegen.Variable;
import codegen.defuzzification.CentroidDefuzzifier;
import codegen.defuzzification.Defuzzifier;
import lowering.builders.ControllerModelBuilder;
import lowering.facades.TermLower;
import lowering.facades.VariableLower;
import lowering.contexts.LoweringContext;
import utils.LexemeInfo;
import utils.diagnostics.UnsupportedFeature;
import utils.enums.Source;

import java.util.*;

public class FuzzyVariablesAnalyzer implements SemanticAnalyzer {
    private final LoweringContext ctx;
    private final BitWidthAnalyzer bitWidthAnalyzer;

    private static String ERR_MISSING_IOVARS() {
        return "the controller needs at least one input with FUZZIFY and one output with DEFUZZIFY";
    }

    public FuzzyVariablesAnalyzer(LoweringContext initContext, BitWidthAnalyzer bitWidthAnalyzer) {
        this.ctx = initContext;
        this.bitWidthAnalyzer = bitWidthAnalyzer;
    }

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

            TermLower termLower = new TermLower(ctx, bitWidthAnalyzer);
            VariableLower varLower = new VariableLower(ctx, termLower);
            Optional<Variable> variable = varLower.adapt(ioVarKey);
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
