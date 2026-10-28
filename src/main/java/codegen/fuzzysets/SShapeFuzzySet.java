package codegen.fuzzysets;

import codegen.CppWriter;

/**
 * Increasing linear ramp: 0 below {@code a}, maximum degree above {@code b}, linear in between. It is the FCL
 * term {@code (a, 0) (b, 1)}. Port of {@code SShapeFS} (Luca Sarramone, Generador-FLC {@code c304c1d}).
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class SShapeFuzzySet extends FuzzySet {
    private final int a;
    private final int b;

    /**
     * Creates an increasing ramp.
     *
     * @param name name of the linguistic term
     * @param a    last value with degree 0
     * @param b    first value with maximum degree
     */
    public SShapeFuzzySet(final String name, final int a, final int b) {
        super(name);
        this.a = a;
        this.b = b;
    }

    @Override
    public void compileFuzzSet(final CppWriter out, final int converterRange, final String varName,
                               final int setNumber) {
        out.write("\tif (" + varName + " < " + a + ") \n");
        out.write("\t\t" + varName + "Fuzz[" + setNumber + "] = 0;\n");
        out.write("\telse \n");
        out.write("\t\tif (" + varName + " > " + b + ") \n");
        out.write("\t\t\t" + varName + "Fuzz[" + setNumber + "] = " + converterRange + ";\n");
        out.write("\t\telse \n");
        out.write("\t\t\t" + varName + "Fuzz[" + setNumber + "] = function" + varName + setNumber
                + " * (" + varName + " - " + a + ");\n");
    }

    @Override
    public void compileFunctionSlope(final CppWriter out, final int converterRange, final String slopeName) {
        out.write("const fixed_int " + slopeName + " = ");
        out.write(converterRange + "/(" + b + " - " + a + "); \n");
    }

    @Override
    public int quantizedDegree(final int x, final int converterRange) {
        if (x < a) {
            return 0;
        }
        if (x > b) {
            return converterRange;
        }
        return truncate((long) slope(converterRange, b - a) * (x - a), converterRange);
    }

    @Override
    public double degree(final double x) {
        if (x <= a) {
            return 0;
        }
        return (x >= b) ? 1 : (x - a) / (b - a);
    }
}
