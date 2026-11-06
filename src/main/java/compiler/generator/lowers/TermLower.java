package compiler.generator.lowers;

import compiler.generator.fuzzysets.FuzzySet;
import compiler.generator.lowers.contexts.TermLoweringContext;
import compiler.generator.lowers.facades.TermSetFactory;
import compiler.generator.lowers.utils.IntegerDomain;
import compiler.repository.LexemeInfo;
import compiler.repository.diagnostics.UnsupportedFeature;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static compiler.repository.enums.Subtype.COMPLEX;
import static compiler.repository.enums.Subtype.VARIABLE;

/**
 * Lowers a linguistic term into the {@link FuzzySet} used by the code generator.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class TermLower implements Lower<FuzzySet> {
    private final TermLoweringContext ctx;

    /**
     * Creates the lowering of one linguistic term.
     *
     * @param initContext context with the term key and the bit-width resolver
     */
    public TermLower(TermLoweringContext initContext) {
        this.ctx = initContext;
    }

    /**
     * Builds the fuzzy set of the term.
     *
     * @return the fuzzy set, or empty if the term is not supported by the generator
     */
    public Optional<FuzzySet> adapt() {
        LexemeInfo termInfo = ctx.symbolTable.get(ctx.termKey);
        String varName = ctx.keys.shortName(ctx.varKey);
        String termName = ctx.keys.shortName(ctx.termKey);

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
        BigInteger lowerBound = ctx.varAdaptedValueRange.offset();
        return TermSetFactory.create(termName, termInfo, lowerBound);
    }

    /**
     * Checks that every point of a membership function is a non-negative integer that fits in the
     * configured bit width.
     *
     * @param varName name of the variable that owns the term
     * @param termName name of the term
     * @param X points of the membership function
     * @return {@code true} if at least one point is invalid (a diagnostic is recorded)
     */
    public boolean invalidMembershipFunction(String varName, String termName, List<String> X) {
        BigDecimal lowerBound = new BigDecimal(ctx.varAdaptedValueRange.offset());
        for (String x : X) {
            BigDecimal value = new BigDecimal(x);
            if (value.signum() != 0 && value.stripTrailingZeros().scale() > 0) {
                ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_POINT(varName, termName, x)));
                return true;
            }
            BigDecimal relative = value.subtract(lowerBound);
            boolean cantBeRepresentedWithCurrentBits = !ctx.bitWidthLower.analyzeIfRepresentable(relative);
            if (cantBeRepresentedWithCurrentBits) {
                String strBitWidth = String.valueOf(ctx.bitWidthLower.getBitWidth());
                String errMssg = ERR_INVALID_POINT(x, strBitWidth, ctx.varAdaptedValueRange, varName);
                ctx.diagnosticsHandler.add(new UnsupportedFeature(errMssg));
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

    private static String ERR_INVALID_POINT(String x, String bitWidth, IntegerDomain domain, String varName) {
        String hint = domain.isShifted()
                ? " (relative to the lower bound " + domain.offset() + " of " + varName + ")"
                : "";
        return "the point " + x + " cannot be representable with " + bitWidth + " bits" + hint;
    }
}

