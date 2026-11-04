package lowering.facades;

import codegen.fuzzysets.*;
import lowering.semantics.BitWidthAnalyzer;
import lowering.contexts.LoweringContext;
import utils.LexemeInfo;
import utils.diagnostics.UnsupportedFeature;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static utils.enums.Subtype.*;

public class TermLower {
    private final LoweringContext ctx;
    private final BitWidthAnalyzer bitWidthAnalyzer;

    public TermLower(LoweringContext initContext, BitWidthAnalyzer bitWidthAnalyzer) {
        this.ctx = initContext;
        this.bitWidthAnalyzer = bitWidthAnalyzer;
    }

    public Optional<FuzzySet> adapt(String varKey, String termKey, LexemeInfo termInfo) {
        String varName = ctx.keys.shortName(varKey);
        String termName = ctx.keys.shortName(termKey);

        if (termInfo.subtype == VARIABLE) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_VARIABLE(varName, termName)));
            return Optional.empty();
        }
        if (termInfo.subtype == COMPLEX) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_FUNCTION(varName, termName)));
            return Optional.empty();
        }
        List<String> membershipFunctionXValues = termInfo.parameters;
        if (invalidMembershipFunction(varName, termName, membershipFunctionXValues)) {
            return Optional.empty();
        }
        return TermSetFactory.create(termName, termInfo);
    }

    public boolean invalidMembershipFunction(String varName, String termName, List<String> X) {
        for (String x : X) {
            BigDecimal value = new BigDecimal(x);
            if (value.signum() != 0 && value.stripTrailingZeros().scale() > 0) {
                ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_POINT(varName, termName, x)));
                return true;
            }
            boolean cantBeRepresentedWithCurrentBits = !bitWidthAnalyzer.analyzeIfRepresentable(value);
            if (value.signum() < 0 || cantBeRepresentedWithCurrentBits) {
                String strBitWidth = String.valueOf(bitWidthAnalyzer.getBitWidth());
                ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_INVALID_POINT(x, strBitWidth)));
                return true;
            }
        }
        return false;
    }

    private static String ERR_UNSUPPORTED_VARIABLE(String varName, String termName) {
        return "term " + termName + " of " + varName + " has points given by variables";
    }

    private static String ERR_UNSUPPORTED_FUNCTION(String varName, String termName) {
        return "term " + termName + " of " + varName + " has a shape the generator does not support";
    }

    private static String ERR_UNSUPPORTED_POINT(String varName, String termName, String x) {
        return "term " + termName + " of " + varName + " has the non-integer point " + x;
    }

    private static String ERR_INVALID_POINT(String x, String bitWidth) {
        return "the point " + x + " cannot be representable with " + bitWidth + " bits";
    }
}

