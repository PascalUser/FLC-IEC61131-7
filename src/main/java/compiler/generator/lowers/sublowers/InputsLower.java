package compiler.generator.lowers.sublowers;

import compiler.generator.lowers.contexts.LoweringContext;

import java.util.List;

public final class InputsLower extends FuzzyVariablesLower {

    public InputsLower(LoweringContext ctx, BitWidthLower w) { super(ctx, w); }

    @Override
    protected List<String> variableKeys() {
        return ctx.inVarKeys();
    }

    @Override
    protected String componentKey(String k) {
        return ctx.keys.fuzzifier(k);
    }

    @Override
    protected String kind() {
        return "input";
    }

    @Override
    protected String block() {
        return "FUZZIFY";
    }
}
