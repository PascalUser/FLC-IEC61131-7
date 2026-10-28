package codegen.fuzzysets;

import codegen.CppWriter;

/**
 * Decreasing linear ramp: maximum degree below {@code c}, 0 above {@code d}, linear in between. It is the FCL
 * term {@code (c, 1) (d, 0)}. Port of {@code ZShapeFS} (Luca Sarramone, Generador-FLC {@code c304c1d}).
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class ZShapeFuzzySet extends FuzzySet {
    private final int c;
    private final int d;

    /**
     * Creates a decreasing ramp.
     *
     * @param name name of the linguistic term
     * @param c    last value with maximum degree
     * @param d    first value with degree 0
     */
    public ZShapeFuzzySet(final String name, final int c, final int d) {
        super(name);
        this.c = c;
        this.d = d;
    }

    @Override
    public void compileFuzzSet(final CppWriter out, final int converterRange, final String varName,
                               final int setNumber) {
        out.write("\tif (" + varName + " < " + c + ") \n");
        out.write("\t\t" + varName + "Fuzz[" + setNumber + "] = " + converterRange + ";\n");
        out.write("\telse \n");
        out.write("\t\tif (" + varName + " > " + d + ") \n");
        out.write("\t\t\t" + varName + "Fuzz[" + setNumber + "] = 0; \n");
        out.write("\t\telse \n");
        out.write("\t\t\t" + varName + "Fuzz[" + setNumber + "] = function" + varName + setNumber
                + " * (" + d + " - " + varName + ");\n");
    }

    @Override
    public void compileFunctionSlope(final CppWriter out, final int converterRange, final String slopeName) {
        out.write("const fixed_int " + slopeName + " = ");
        out.write(converterRange + "/(" + d + " - " + c + "); \n");
    }
}
