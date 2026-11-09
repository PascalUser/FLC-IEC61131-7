package compiler.generator.lowers.sublowers;

import compiler.generator.Variable;
import compiler.generator.fuzzysets.FuzzySet;
import compiler.generator.fuzzysets.SingletonFuzzySet;
import compiler.generator.lowers.contexts.LoweringContext;
import compiler.repository.LexemeInfo;
import compiler.repository.diagnostics.GeneratorWarning;
import compiler.repository.diagnostics.UnsupportedFeature;
import compiler.repository.enums.Subtype;

import java.util.List;

public final class OutputsLower extends FuzzyVariablesLower {

    public OutputsLower(LoweringContext ctx, BitWidthLower w) {
        super(ctx, w);
    }

    @Override
    protected List<String> variableKeys() {
        return ctx.outVarKeys();
    }

    @Override
    protected String componentKey(String k) {
        return ctx.keys.defuzzifier(k);
    }

    @Override
    protected String kind() {
        return "output";
    }

    @Override
    protected String block() {
        return "DEFUZZIFY";
    }

    @Override
    protected boolean acceptsComponent(String varName, LexemeInfo component) {
        ctx.diagnosticsHandler.add(new GeneratorWarning(WAR_WITH_DEFAULT(varName)));
        if (component.subtype == Subtype.COGS) {
            return true;
        }
        ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_METHOD(varName, component.subtype.name())));
        return false;
    }

    @Override
    protected boolean acceptsSets(Variable output) {
        for (FuzzySet set : output.sets()) {
            if (!(set instanceof SingletonFuzzySet)) {
                ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_TERM(output.name(), set.name())));
                return false;
            }
        }
        return true;
    }

    private static String WAR_WITH_DEFAULT(String varName) {
        return "DEFAULT of " + varName + " is ignored: if no rule fires, the centroid divides by zero";
    }

    private static String ERR_UNSUPPORTED_METHOD(String varName, String defuzzMethod) {
        return "METHOD " + defuzzMethod + " of " + varName + " is not supported (only CoGS)";
    }

    private static String ERR_UNSUPPORTED_TERM(String outVarName, String termName) {
        return "term " + termName + " of the output " + outVarName + " is not supported (only singleton outputs)";
    }
}
