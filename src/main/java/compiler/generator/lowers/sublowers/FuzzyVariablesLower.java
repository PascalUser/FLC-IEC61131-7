package compiler.generator.lowers.sublowers;

import compiler.generator.Variable;
import compiler.generator.fuzzysets.FuzzySet;
import compiler.generator.lowers.Lower;
import compiler.generator.lowers.contexts.LoweringContext;
import compiler.generator.lowers.contexts.TermLoweringContext;
import compiler.generator.lowers.contexts.VarLoweringContext;
import compiler.generator.lowers.facades.IntegerDomainResolver;
import compiler.generator.lowers.utils.IntegerDomain;
import compiler.repository.LexemeInfo;
import compiler.repository.diagnostics.GeneratorWarning;
import compiler.repository.diagnostics.UnsupportedFeature;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class FuzzyVariablesLower implements Lower<List<Variable>> {
    protected final LoweringContext ctx;
    private final BitWidthLower bitWidthLower;

    protected FuzzyVariablesLower(LoweringContext ctx, BitWidthLower bitWidthLower) {
        this.ctx = ctx; this.bitWidthLower = bitWidthLower;
    }

    protected abstract List<String> variableKeys();
    protected abstract String componentKey(String variableKey);
    protected abstract String kind();
    protected abstract String block();
    protected boolean acceptsComponent(String varName, LexemeInfo component) { return true; }
    protected boolean acceptsSets(Variable variable) { return true; }

    @Override
    public final Optional<List<Variable>> adapt() {
        List<String> keys = variableKeys();
        if (keys.isEmpty()) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_MISSING_IOVARS(kind(), block())));
            return Optional.empty();
        }
        boolean valid = true;
        List<Variable> variables = new ArrayList<>();
        for (String key : keys) {
            Optional<Variable> variable = lowerVariable(key);
            if (variable.isPresent()) {
                variables.add(variable.get());
                continue;
            }
            valid = false;
        }
        return valid ? Optional.of(variables) : Optional.empty();
    }

    private Optional<Variable> lowerVariable(String varKey) {
        String name = ctx.keys.shortName(varKey);
        LexemeInfo varInfo = ctx.symbolTable.get(varKey);
        Objects.requireNonNull(varInfo);
        LexemeInfo component = ctx.symbolTable.get(componentKey(varKey));
        if (component == null) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_MISSING_COMPONENT(name, kind(), block())));
            return Optional.empty();
        }
        Optional<IntegerDomain> domain = IntegerDomainResolver.resolve(varInfo, ctx.symbolTable);
        if (!domain.isPresent()) {
            String subtype = varInfo.subtype.name();
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_TYPE(name, subtype)));
            return Optional.empty();
        }
        if (domain.get().isShifted()) {
            ctx.diagnosticsHandler.add(new GeneratorWarning(WAR_SHIFTED(name, domain.get().offset())));
        }
        if (!acceptsComponent(name, component)) return Optional.empty();

        VarLoweringContext varCtx = new VarLoweringContext(ctx, varKey, bitWidthLower);
        List<FuzzySet> sets = new ArrayList<>();
        boolean allCovered = true;
        for (String termName : component.parameters) {
            String termKey = ctx.keys.term(varKey, termName);
            TermLoweringContext termCtx = new TermLoweringContext(varCtx, termKey, domain.get());
            Optional<FuzzySet> set = new TermLower(termCtx).adapt();
            if (set.isPresent()) sets.add(set.get()); else allCovered = false;
        }
        if (!allCovered) return Optional.empty();
        Variable variable = new Variable(name, sets);

        return acceptsSets(variable) ? Optional.of(variable) : Optional.empty();
    }

    private static String ERR_MISSING_IOVARS(String kind, String block) {
        return "the controller needs at least one " + kind + " with " + block;
    }

    private static String ERR_MISSING_COMPONENT(String varName, String sourceType, String expectedBlock) {
        return sourceType + " " + varName + " has no " + expectedBlock + " block";
    }

    private static String ERR_UNSUPPORTED_TYPE(String varName, String varType) {
        return "only unsigned integer types or INT/SINT or integer subranges are supported " +
                "but variable " + varName + " is " + varType;
    }

    private static String WAR_SHIFTED(String varName, BigInteger lowerBound) {
        return "variable " + varName + " is a subrange starting at " + lowerBound + ": " +
                "the generated controller works with " + varName + " - " + lowerBound;
    }
}
