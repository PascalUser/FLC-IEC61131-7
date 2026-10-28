package parser.facades;

import parser.utils.MembershipFunctionShape;
import utils.enums.Subtype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Recognizes the shape of a membership function from its points (x and degree), not only from how many there
 * are.
 * <p>
 * IEC 61131-7 defines a membership function as a singleton or a piece-wise linear function given by points in
 * ascending order of x; outside the points the degree keeps the value of the first/last point (5.2.2). So
 * {@code (0,1) (5,1) (10,0)} is a ramp, not a triangle. The classification:
 * </p>
 * <ol>
 *   <li>Any x given by a variable: {@code VARIABLE} (the shape is only known at run time).</li>
 *   <li>One point with degree 1: {@code SINGLETON}.</li>
 *   <li>Invalid points (x not strictly ascending, degree other than 0 or 1): {@code COMPLEX}. Degrees between
 *       0 and 1 are an Open Level feature (table 10); Basic and Extension levels require 0 or 1.</li>
 *   <li>Redundant points are removed: interior points on a flat segment and extreme points that repeat the
 *       degree of their neighbour.</li>
 *   <li>The remaining degrees give the shape: {@code 1,0} or {@code 0,1} is {@code LINEAR} (decreasing or
 *       increasing ramp; the direction is read from the degrees), {@code 0,1,0} is {@code TRIANGLE},
 *       {@code 0,1,1,0} is {@code TRAPEZOID}, anything else (constant, V, W...) is {@code COMPLEX}.</li>
 * </ol>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class MembershipFunctionClassifier {

    private MembershipFunctionClassifier() {
        // Utility class - not instantiable
    }

    /**
     * Classifies a membership function.
     *
     * @param xs      x coordinates as written in the table (literal keys or identifiers)
     * @param degrees membership degrees as literal keys, one per x
     * @return the recognized shape and its defining x coordinates
     * @throws IllegalArgumentException if the lists are empty or have different sizes
     */
    public static MembershipFunctionShape classify(final List<String> xs, final List<String> degrees) {
        if (xs.isEmpty() || xs.size() != degrees.size()) {
            throw new IllegalArgumentException("MembershipFunctionClassifier: expected one degree per point, got "
                    + xs.size() + " x and " + degrees.size() + " degrees");
        }
        List<BigDecimal> xValues = numbers(xs);
        if (xValues == null) {
            return new MembershipFunctionShape(Subtype.VARIABLE, null);
        }
        List<BigDecimal> degreeValues = numbers(degrees);
        // EXT: invalid points should be a diagnostic of the standard once the parser has a DiagnosticsHandler.
        if (degreeValues == null || !strictlyAscending(xValues) || !onlyZeroOrOne(degreeValues)) {
            return new MembershipFunctionShape(Subtype.COMPLEX, null);
        }
        if (xs.size() == 1) {
            return isOne(degreeValues.get(0))
                    ? new MembershipFunctionShape(Subtype.SINGLETON, xs)
                    : new MembershipFunctionShape(Subtype.COMPLEX, null);
        }

        List<Integer> kept = withoutRedundantPoints(degreeValues);
        Subtype subtype = shapeOf(degreesPattern(degreeValues, kept));
        if (subtype == Subtype.COMPLEX) {
            return new MembershipFunctionShape(Subtype.COMPLEX, null);
        }
        List<String> parameters = new ArrayList<>();
        for (int index : kept) {
            parameters.add(xs.get(index));
        }
        return new MembershipFunctionShape(subtype, parameters);
    }

    private static Subtype shapeOf(final String pattern) {
        switch (pattern) {
            case "10":
            case "01":
                return Subtype.LINEAR;
            case "010":
                return Subtype.TRIANGLE;
            case "0110":
                return Subtype.TRAPEZOID;
            default:
                return Subtype.COMPLEX;
        }
    }

    /** Indexes of the points that define the function; the others lie on a flat segment or flat extension. */
    private static List<Integer> withoutRedundantPoints(final List<BigDecimal> degrees) {
        List<Integer> kept = new ArrayList<>();
        int last = degrees.size() - 1;
        for (int i = 0; i <= last; i++) {
            boolean sameAsPrevious = i > 0 && same(degrees.get(i), degrees.get(i - 1));
            boolean sameAsNext = i < last && same(degrees.get(i), degrees.get(i + 1));
            boolean flatInterior = sameAsPrevious && sameAsNext;
            boolean flatExtreme = (i == 0 && sameAsNext) || (i == last && sameAsPrevious);
            if (!flatInterior && !flatExtreme) {
                kept.add(i);
            }
        }
        return kept;
    }

    private static String degreesPattern(final List<BigDecimal> degrees, final List<Integer> kept) {
        StringBuilder pattern = new StringBuilder();
        for (int index : kept) {
            pattern.append(isOne(degrees.get(index)) ? '1' : '0');
        }
        return pattern.toString();
    }

    /** Parses every literal key; {@code null} if any of them is not a number (an identifier). */
    private static List<BigDecimal> numbers(final List<String> literals) {
        List<BigDecimal> values = new ArrayList<>();
        for (String literal : literals) {
            try {
                values.add(new BigDecimal(literal));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return values;
    }

    private static boolean strictlyAscending(final List<BigDecimal> values) {
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i).compareTo(values.get(i - 1)) <= 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean onlyZeroOrOne(final List<BigDecimal> degrees) {
        for (BigDecimal degree : degrees) {
            if (degree.signum() != 0 && !isOne(degree)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isOne(final BigDecimal degree) {
        return degree.compareTo(BigDecimal.ONE) == 0;
    }

    private static boolean same(final BigDecimal a, final BigDecimal b) {
        return a.compareTo(b) == 0;
    }
}
