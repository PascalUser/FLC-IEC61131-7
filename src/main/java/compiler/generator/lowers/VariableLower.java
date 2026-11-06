package compiler.generator.lowers;

import compiler.generator.Variable;
import compiler.generator.fuzzysets.FuzzySet;
import compiler.generator.fuzzysets.SingletonFuzzySet;
import compiler.generator.lowers.contexts.TermLoweringContext;
import compiler.generator.lowers.contexts.VarLoweringContext;
import compiler.generator.lowers.facades.IntegerDomainResolver;
import compiler.generator.lowers.utils.IntegerDomain;
import compiler.repository.LexemeInfo;
import compiler.repository.diagnostics.GeneratorWarning;
import compiler.repository.diagnostics.UnsupportedFeature;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;

import java.math.BigInteger;
import java.util.*;

/**
 * Lowers a controller variable into the {@link Variable} used by the code generator.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class VariableLower implements Lower<Variable> {
    private final VarLoweringContext ctx;

    /**
     * Creates the lowering of one controller variable.
     *
     * @param initContext context with the variable key and the bit-width resolver
     */
    public VariableLower(VarLoweringContext initContext) {
        this.ctx = initContext;
    }

    /**
     * Builds the variable and its fuzzy sets.
     *
     * @return the variable, or empty if it is not supported by the generator
     */
    public Optional<Variable> adapt() {
        String ioVarName = ctx.keys.shortName(ctx.varKey);
        LexemeInfo ioVarInfo = ctx.symbolTable.get(ctx.varKey);

        boolean isInputVariable = ioVarInfo.source == Source.IN;
        String componentName = isInputVariable ? ctx.keys.fuzzifier(ctx.varKey) : ctx.keys.defuzzifier(ctx.varKey);
        LexemeInfo componentInfo = ctx.symbolTable.get(componentName);

        if (hasNoFuzzifierOrDefuzzifier(componentInfo)) {
            return Optional.empty();
        }
        if (doesNotHaveASupportedType(ioVarName, ioVarInfo)) {
            return Optional.empty();
        }
        IntegerDomain varAdaptedValueRange = getIntegerDomain(ioVarInfo);
        if (!isInputVariable && hasUnsupportedDeffuzMethod(componentInfo)) {
            return Optional.empty();
        }
        boolean notAllTermsCovered = false;
        List<String> terms = componentInfo.parameters;
        List<FuzzySet> termsSet = new ArrayList<>();
        for (String termName : terms) {
            String termKey = ctx.keys.term(ctx.varKey, termName);
            TermLoweringContext termCtx = new TermLoweringContext(ctx, termKey, varAdaptedValueRange);
            TermLower termLower = new TermLower(termCtx);
            Optional<FuzzySet> varTermsSet = termLower.adapt();
            if (varTermsSet.isPresent()) {
                termsSet.add(varTermsSet.get());
            } else {
                notAllTermsCovered = true;
            }
        }
        if (notAllTermsCovered) {
            return Optional.empty();
        }
        Variable ioVar = new Variable(ioVarName, termsSet);
        if (!isInputVariable && doesNotHaveOnlySingletons(ioVar)) {
            return Optional.empty();
        }
        return Optional.of(ioVar);
    }

    private boolean doesNotHaveASupportedType(String varName, final LexemeInfo varInfo) {
        Optional<IntegerDomain> domain = IntegerDomainResolver.resolve(varInfo, ctx.symbolTable);
        if (!domain.isPresent()) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_TYPE(varName, varInfo.subtype.name())));
            return true;
        }
        if (domain.get().isShifted()) {
            ctx.diagnosticsHandler.add(new GeneratorWarning(WAR_SHIFTED(varName, domain.get().offset())));
        }
        return false;
    }

    private IntegerDomain getIntegerDomain(final LexemeInfo ioVarInfo) {
        Optional<IntegerDomain> domain = IntegerDomainResolver.resolve(ioVarInfo, ctx.symbolTable);
        if (!domain.isPresent()) {
            throw new RuntimeException("VariableLower: unexpected argument");
        }
        return domain.get();
    }

    private boolean hasNoFuzzifierOrDefuzzifier(final LexemeInfo componentInfo) {
        String varName = ctx.keys.shortName(ctx.varKey);
        if (componentInfo != null) {
            return false;
        }
        String sourceType;
        String expectedBlock;
        final boolean isInput = ctx.symbolTable.get(ctx.varKey).source == Source.IN;
        if (isInput) {
            sourceType = "input";
            expectedBlock = "FUZZIFY";
        } else {
            sourceType = "output";
            expectedBlock = "DEFUZZIFY";
        }
        ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_MISSING_COMPONENT(varName, sourceType, expectedBlock)));
        return true;
    }

    private boolean hasUnsupportedDeffuzMethod(final LexemeInfo componentInfo) {
        String varName = ctx.keys.shortName(ctx.varKey);
        ctx.diagnosticsHandler.add(new GeneratorWarning(WAR_WITH_DEFAULT(varName)));
        if (componentInfo.subtype == Subtype.COGS) {
            return false;
        }
        String blockName = componentInfo.subtype.name();
        ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_METHOD(varName, blockName)));
        return true;
    }


    private boolean doesNotHaveOnlySingletons(final Variable outVar) {
        for (FuzzySet termsSet : outVar.sets()) {
            if (!(termsSet instanceof SingletonFuzzySet)) {
                String outVarName = outVar.name();
                String termName = termsSet.name();
                ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_FUZZ(outVarName, termName)));
                return true;
            }
        }
        return false;
    }

    private static String ERR_UNSUPPORTED_TYPE(String varName, String varType) {
        return "only unsigned integer types or integer subranges are supported " +
                "but variable " + varName + " is " + varType;
    }

    private static String WAR_SHIFTED(String varName, BigInteger lowerBound) {
        return "variable " + varName + " is a subrange starting at " + lowerBound + ": " +
                "the generated controller works with " + varName + " - " + lowerBound;
    }

    private static String ERR_MISSING_COMPONENT(String varName, String sourceType, String expectedBlock) {
        return sourceType + " " + varName + " has no " + expectedBlock + " block";
    }

    private static String WAR_WITH_DEFAULT(String varName) {
        return "DEFAULT of " + varName + " is ignored: if no rule fires, the centroid divides by zero";
    }

    private static String ERR_UNSUPPORTED_METHOD(String varName, String defuzzMethod) {
        return "METHOD " + defuzzMethod + " of " + varName + " is not supported (only CoGS)";
    }

    private static String ERR_UNSUPPORTED_FUZZ(String outVarName, String termName) {
        return "term " + termName + " of the output " + outVarName + " is not supported (only singleton outputs)";
    }
}
