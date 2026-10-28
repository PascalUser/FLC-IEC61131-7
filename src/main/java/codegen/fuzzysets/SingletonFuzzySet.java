package codegen.fuzzysets;

import codegen.CppWriter;

/**
 * Singleton: maximum degree at one value, 0 elsewhere. Output variables use only singletons; their values
 * feed the centroid. Port of {@code SingletonFS} (Luca Sarramone, Generador-FLC {@code c304c1d}).
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class SingletonFuzzySet extends FuzzySet {
    private final int value;

    /**
     * Creates a singleton.
     *
     * @param name  name of the linguistic term
     * @param value the only value with non-zero degree
     */
    public SingletonFuzzySet(final String name, final int value) {
        super(name);
        this.value = value;
    }

    /**
     * Returns the value of the singleton.
     *
     * @return the value
     */
    public int value() {
        return value;
    }

    @Override
    public void compileFuzzSet(final CppWriter out, final int converterRange, final String varName,
                               final int setNumber) {
        out.write("\tif (" + varName + " == " + value + ") \n");
        out.write("\t\t" + varName + "Fuzz[" + setNumber + "] = " + converterRange + ";\n");
        out.write("\telse \n");
        out.write("\t\t" + varName + "Fuzz[" + setNumber + "] = 0;\n");
    }

    @Override
    public void compileFunctionSlope(final CppWriter out, final int converterRange, final String slopeName) {
        // A singleton has no slope.
    }

    @Override
    public int quantizedDegree(final int x, final int converterRange) {
        return (x == value) ? converterRange : 0;
    }

    @Override
    public double degree(final double x) {
        return (x == value) ? 1 : 0;
    }
}
