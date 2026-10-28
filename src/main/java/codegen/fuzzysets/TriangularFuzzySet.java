package codegen.fuzzysets;

import codegen.CppWriter;

/**
 * Triangle with feet {@code a}, {@code c} and peak {@code b}: the FCL term {@code (a, 0) (b, 1) (c, 0)}.
 * Port of {@code TriangularFS} (Luca Sarramone, Generador-FLC {@code c304c1d}).
 * <p>
 * Corrected with respect to the original, which applied the rising slope ({@code <slope>1}) above the peak and
 * the falling slope ({@code <slope>2}) below it; see the README of the reference files.
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class TriangularFuzzySet extends FuzzySet {
    private final int a;
    private final int b;
    private final int c;

    /**
     * Creates a triangle.
     *
     * @param name name of the linguistic term
     * @param a    left foot (degree 0)
     * @param b    peak (maximum degree)
     * @param c    right foot (degree 0)
     */
    public TriangularFuzzySet(final String name, final int a, final int b, final int c) {
        super(name);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void compileFuzzSet(final CppWriter out, final int converterRange, final String varName,
                               final int setNumber) {
        out.write("\tif (" + varName + " < " + a + " || " + varName + " > " + c + ") \n");
        out.write("\t\t" + varName + "Fuzz[" + setNumber + "] = 0;\n");
        out.write("\telse \n");
        out.write("\t\tif (" + varName + " > " + b + ") \n");
        out.write("\t\t\t" + varName + "Fuzz[" + setNumber + "] = function" + varName + setNumber + "2"
                + " * (" + c + " - " + varName + "); \n");
        out.write("\t\telse \n");
        out.write("\t\t\t" + varName + "Fuzz[" + setNumber + "] = function" + varName + setNumber + "1"
                + " * (" + varName + "-" + a + ");\n");
    }

    @Override
    public void compileFunctionSlope(final CppWriter out, final int converterRange, final String slopeName) {
        out.write("const fixed_int " + slopeName + "1 = ");
        out.write(converterRange + "/(" + b + " - " + a + "); \n");
        out.write("const fixed_int " + slopeName + "2 = ");
        out.write(converterRange + "/(" + c + " - " + b + "); \n");
    }

    @Override
    public int quantizedDegree(final int x, final int converterRange) {
        if (x < a || x > c) {
            return 0;
        }
        if (x > b) {
            return truncate((long) slope(converterRange, c - b) * (c - x), converterRange);
        }
        return truncate((long) slope(converterRange, b - a) * (x - a), converterRange);
    }

    @Override
    public double degree(final double x) {
        if (x <= a || x >= c) {
            return 0;
        }
        return (x <= b) ? (x - a) / (b - a) : (c - x) / (c - b);
    }
}
