package compiler.generator.lowers.sublowers;

import compiler.generator.lowers.Lower;
import compiler.generator.lowers.contexts.LoweringContext;
import compiler.repository.LexemeInfo;
import compiler.repository.diagnostics.general.UnsupportedFeature;
import compiler.repository.enums.Use;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InternalVariablesLower implements Lower<List<String>> {
    private final LoweringContext ctx;

    public InternalVariablesLower(LoweringContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public Optional<List<String>> adapt() {
        boolean allVariablesAreConstants = true;
        for (String localVarKey : ctx.localVarKeys()) {
            LexemeInfo localVarKeyInfo = ctx.symbolTable.get(localVarKey);
            Objects.requireNonNull(localVarKeyInfo);
            if (localVarKeyInfo.use != Use.CONSTANT) {
                String varName = ctx.keys.shortName(localVarKey);
                ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_VAR(varName)));
                allVariablesAreConstants = false;
            }
        }
        return (allVariablesAreConstants) ? Optional.of(ctx.localVarKeys()) : Optional.empty();
    }

    private static String ERR_UNSUPPORTED_VAR(String varName) {
        return "internal variable " + varName + " is not supported (only VAR CONSTANT)";
    }
}
