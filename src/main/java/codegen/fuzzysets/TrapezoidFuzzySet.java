package codegen.fuzzysets;

import codegen.CppWriter;

/**
 * Trapezoid with feet {@code a}, {@code d} and plateau {@code b..c}: the FCL term
 * {@code (a, 0) (b, 1) (c, 1) (d, 0)}. Port of {@code TrapezoidFS} (Luca Sarramone, Generador-FLC
 * {@code c304c1d}).
 * <p>
 * Corrected with respect to the original, which computed the slopes when the value was outside {@code [a, d]},
 * 0 inside it, and used 1 instead of the maximum degree on the plateau; see the README of the reference files.
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class TrapezoidFuzzySet extends FuzzySet {
    private final int a;
    private final int b;
    private final int c;
    private final int d;

    /**
     * Creates a trapezoid.
     *
     * @param name name of the linguistic term
     * @param a    left foot (degree 0)
     * @param b    start of the plateau (maximum degree)
     * @param c    end of the plateau (maximum degree)
     * @param d    right foot (degree 0)
     */
    public TrapezoidFuzzySet(final String name, final int a, final int b, final int c, final int d) {
        super(name);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public void compileFuzzSet(final CppWriter out, final int converterRange, final String varName,
                               final int setNumber) {
        out.write("\tif (" + varName + " < " + a + " || " + varName + " > " + d + ") \n");
        out.write("\t\t" + varName + "Fuzz[" + setNumber + "] = 0;\n");
        out.write("\telse \n");
        out.write("\t\tif (" + varName + " < " + b + ") \n");
        out.write("\t\t\t" + varName + "Fuzz[" + setNumber + "] = function" + varName + setNumber + "1"
                + " * (" + varName + "-" + a + "); \n");
        out.write("\t\telse \n");
        out.write("\t\t\tif (" + varName + " > " + c + ") \n");
        out.write("\t\t\t\t" + varName + "Fuzz[" + setNumber + "] = function" + varName + setNumber + "2"
                + " * (" + d + " - " + varName + ");\n");
        out.write("\t\t\telse \n");
        out.write("\t\t\t\t" + varName + "Fuzz[" + setNumber + "] = " + converterRange + ";\n");
    }

    @Override
    public void compileFunctionSlope(final CppWriter out, final int converterRange, final String slopeName) {
        out.write("const fixed_int " + slopeName + "1 = ");
        out.write(converterRange + "/(" + b + " - " + a + "); \n");
        out.write("const fixed_int " + slopeName + "2 = ");
        out.write(converterRange + "/(" + d + " - " + c + "); \n");
    }

    @Override
    public int quantizedDegree(final int x, final int converterRange) {
        if (x < a || x > d) {
            return 0;
        }
        if (x < b) {
            return truncate((long) slope(converterRange, b - a) * (x - a), converterRange);
        }
        if (x > c) {
            return truncate((long) slope(converterRange, d - c) * (d - x), converterRange);
        }
        return converterRange;
    }

    @Override
    public double degree(final double x) {
        if (x <= a || x >= d) {
            return 0;
        }
        if (x < b) {
            return (x - a) / (b - a);
        }
        return (x <= c) ? 1 : (d - x) / (d - c);
    }
}
