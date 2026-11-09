package compiler.generator.lowers.facades;

import compiler.generator.fuzzysets.*;
import compiler.repository.LexemeInfo;
import compiler.repository.enums.Subtype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Creates the fuzzy set that corresponds to the membership function of a linguistic term.
 * <p>
 * The shape is taken from the term subtype and its points from the inferior limits of the symbol table
 * entry: singleton, linear, triangle or trapezoid.
 * </p>
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class TermSetFactory {
    /**
     * Creates the fuzzy set of a linguistic term.
     *
     * @param termName name of the term
     * @param termInfo symbol table entry with the subtype and points of the term
     * @return the matching fuzzy set
     */
    public static Optional<FuzzySet> create(String termName, LexemeInfo termInfo, BigInteger lowerBound) {
        List<String> membershipFunctionXValues = termInfo.parameters;
        List<Integer> X = toIntegerCollection(membershipFunctionXValues, lowerBound);
        Subtype termSubtype = termInfo.subtype;
        switch (termSubtype) {
            case SINGLETON:
                return Optional.of(new SingletonFuzzySet(termName, X.get(0)));
            case LINEAR:
                return Optional.of(membershipFunctionStartsAtOne(termInfo.superiorLimits)
                        ? new ZShapeFuzzySet(termName, X.get(0), X.get(1))
                        : new SShapeFuzzySet(termName, X.get(0), X.get(1)));
            case TRIANGLE:
                return Optional.of(new TriangularFuzzySet(termName, X.get(0), X.get(1), X.get(2)));
            case TRAPEZOID:
                return Optional.of(new TrapezoidFuzzySet(termName, X.get(0), X.get(1), X.get(2), X.get(3)));
            default:
                throw new IllegalArgumentException("TermSetFactory: there's no class for the given subtype");
        }
    }

    private static List<Integer> toIntegerCollection(final List<String> X, final BigInteger lowerBound) {
        List<Integer> result = new ArrayList<>();
        for (String x : X) {
            try {
                result.add(new BigDecimal(x).subtract(new BigDecimal(lowerBound)).intValueExact());
            } catch (ArithmeticException e) {
                throw new RuntimeException("TermSetFactory: illegal membership function x values");
            }
        }
        return result;
    }

    private static boolean membershipFunctionStartsAtOne(final List<String> membershipFunctionYValues) {
        return new BigDecimal(membershipFunctionYValues.get(0)).compareTo(BigDecimal.ONE) == 0;
    }
}
