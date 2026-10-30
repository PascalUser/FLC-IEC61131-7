package lowering;

import codegen.fuzzysets.FuzzySet;
import codegen.fuzzysets.SShapeFuzzySet;
import codegen.fuzzysets.SingletonFuzzySet;
import codegen.fuzzysets.TrapezoidFuzzySet;
import codegen.fuzzysets.TriangularFuzzySet;
import codegen.fuzzysets.ZShapeFuzzySet;
import utils.LexemeInfo;
import utils.diagnostics.UnsupportedFeature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Turns a linguistic term of the symbol table into a fuzzy set of the generator.
 * <p>
 * The parser already recognized the shape ({@code parser.facades.MembershipFunctionClassifier}): the subtype
 * says which one and {@code parameters} has the x coordinates that define it. A {@code LINEAR} term is a
 * decreasing ramp (Z) if its first degree is 1 and an increasing one (S) otherwise. Every coordinate must be an
 * integer between 0 and {@code 2^N - 1}, because the generated controller works with {@code ap_uint<N>}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
final class FuzzySetLowering {
    private final int converterRange;
    private final LoweringReport report;

    /**
     * Creates the lowering of terms for a bit-width.
     *
     * @param converterRange largest coordinate, {@code 2^N - 1}
     * @param report         where problems are reported
     */
    FuzzySetLowering(final int converterRange, final LoweringReport report) {
        this.converterRange = converterRange;
        this.report = report;
    }

    /**
     * Lowers a term.
     *
     * @param name  term name, used in the generated code
     * @param term  its entry in the symbol table
     * @param where description of the term for the messages, e.g. {@code "term NL of MAIN#ANGLE"}
     * @return the fuzzy set, or empty if it is not supported (already reported)
     */
    Optional<FuzzySet> lower(final String name, final LexemeInfo term, final String where) {
        switch (term.subtype) {
            case SINGLETON:
            case LINEAR:
            case TRIANGLE:
            case TRAPEZOID:
                break;
            case VARIABLE:
                return unsupported(where + " has points given by variables");
            default:
                return unsupported(where + " has a shape the generator does not support (only singleton, ramp, "
                        + "triangle and trapezoid with degrees 0 or 1)");
        }
        Optional<List<Integer>> coordinates = coordinates(term.parameters, where);
        if (!coordinates.isPresent()) {
            return Optional.empty();
        }
        List<Integer> p = coordinates.get();
        switch (term.subtype) {
            case SINGLETON:
                return Optional.of(new SingletonFuzzySet(name, p.get(0)));
            case LINEAR:
                return Optional.of(startsAtOne(term)
                        ? new ZShapeFuzzySet(name, p.get(0), p.get(1))
                        : new SShapeFuzzySet(name, p.get(0), p.get(1)));
            case TRIANGLE:
                return Optional.of(new TriangularFuzzySet(name, p.get(0), p.get(1), p.get(2)));
            default:
                return Optional.of(new TrapezoidFuzzySet(name, p.get(0), p.get(1), p.get(2), p.get(3)));
        }
    }

    /** The degree of the first point decides the direction of a ramp (it survives the simplification). */
    private static boolean startsAtOne(final LexemeInfo term) {
        return new BigDecimal(term.superiorLimits.get(0)).compareTo(BigDecimal.ONE) == 0;
    }

    /** Parses the coordinates; empty, after reporting, if any of them is not representable. */
    private Optional<List<Integer>> coordinates(final List<String> literals, final String where) {
        List<Integer> values = new ArrayList<>();
        for (String literal : literals) {
            BigDecimal value = new BigDecimal(literal);
            if (value.signum() != 0 && value.stripTrailingZeros().scale() > 0) {
                unsupported(where + " has the non-integer point " + literal + " (REAL is not supported)");
                return Optional.empty();
            }
            if (value.signum() < 0 || value.compareTo(BigDecimal.valueOf(converterRange)) > 0) {
                unsupported(where + " has the point " + literal + " outside 0.." + converterRange
                        + " (the bit width of the controller)");
                return Optional.empty();
            }
            values.add(value.intValueExact());
        }
        return Optional.of(values);
    }

    private Optional<FuzzySet> unsupported(final String detail) {
        report.error(new UnsupportedFeature(detail));
        return Optional.empty();
    }
}
