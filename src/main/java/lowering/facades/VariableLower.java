package lowering.facades;

import codegen.Variable;
import codegen.fuzzysets.FuzzySet;
import codegen.fuzzysets.SingletonFuzzySet;
import lowering.contexts.LoweringContext;
import utils.LexemeInfo;
import utils.diagnostics.GeneratorWarning;
import utils.diagnostics.UnsupportedFeature;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;

import java.util.*;

public class VariableLower {
    private final LoweringContext ctx;
    private final TermLower termLower;

    private static final Set<Subtype> INTEGER_TYPES = EnumSet.of(
            Subtype.SINT,
            Subtype.INT,
            Subtype.DINT,
            Subtype.LINT,
            Subtype.USINT,
            Subtype.UINT,
            Subtype.UDINT,
            Subtype.ULINT
    );

    private static String ERR_UNSUPPORTED_TYPE(String varName, String varType) {
        return "only integer types are supported but variable " + varName + " is " + varType;
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

    public VariableLower(LoweringContext initContext, TermLower termLower) {
        this.ctx = initContext;
        this.termLower = termLower;
    }

    public Optional<Variable> adapt(String ioVarKey) {
        String ioVarName = ctx.keys.shortName(ioVarKey);
        LexemeInfo ioVarInfo = ctx.symbolTable.get(ioVarKey);

        boolean isInputVariable = ioVarInfo.source == Source.IN;
        String componentName = isInputVariable ? ctx.keys.fuzzifier(ioVarKey) : ctx.keys.defuzzifier(ioVarKey);
        LexemeInfo componentInfo = ctx.symbolTable.get(componentName);

        if (hasNoFuzzifierOrDefuzzifier(ioVarKey, componentInfo)) {
            return Optional.empty();
        }
        if (doesNotHaveIntegerVariable(ioVarName, ioVarInfo)) {
            return Optional.empty();
        }
        if (!isInputVariable && hasUnsupportedDeffuzMethod(ioVarKey, componentInfo)) {
            return Optional.empty();
        }
        boolean notAllTermsCovered = false;
        List<String> terms = componentInfo.parameters;
        List<FuzzySet> termsSet = new ArrayList<>();
        for (String termName : terms) {
            String termKey = ctx.keys.term(ioVarKey, termName);
            LexemeInfo termInfo = ctx.symbolTable.get(termKey);
            Optional<FuzzySet> varTermsSet = termLower.adapt(ioVarKey, termKey, termInfo);
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

    private boolean doesNotHaveIntegerVariable(String varName, final LexemeInfo ioVarInfo) {
        boolean simpleOrSubrange = ioVarInfo.type == Type.SIMPLE || ioVarInfo.type == Type.SUBRANGE;
        if (simpleOrSubrange && INTEGER_TYPES.contains(ioVarInfo.subtype)) {
            return false;
        }
        ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_TYPE(varName, ioVarInfo.subtype.name())));
        return true;
    }

    private boolean hasNoFuzzifierOrDefuzzifier(final String key, final LexemeInfo componentInfo) {
        String varName = ctx.keys.shortName(key);
        if (componentInfo != null) {
            return false;
        }
        String sourceType;
        String expectedBlock;
        final boolean isInput = ctx.symbolTable.get(key).source == Source.IN;
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

    private boolean hasUnsupportedDeffuzMethod(final String key, final LexemeInfo componentInfo) {
        String varName = ctx.keys.shortName(key);
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
}
