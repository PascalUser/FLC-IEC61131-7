package codegen.fuzzysets;

import codegen.CppWriter;

/**
 * A fuzzy set of a controller variable that knows how to emit its C++ code.
 * <p>
 * Ported from {@code code_generation.variables.fuzzy_set.FuzzySet} of Luca Sarramone's generator
 * (Generador-FLC, commit {@code c304c1d}), keeping the generated code; the static state was replaced by
 * parameters. All coordinates are integers in the converter range {@code 0 .. 2^N - 1}.
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class FuzzySet {
    private final String name;

    /**
     * Creates a fuzzy set.
     *
     * @param name name of the linguistic term
     */
    protected FuzzySet(final String name) {
        this.name = name;
    }

    /**
     * Returns the name of the linguistic term.
     *
     * @return the term name
     */
    public final String name() {
        return name;
    }

    /**
     * Emits the code that computes the membership degree of this set into {@code <var>Fuzz[setNumber]}.
     *
     * @param out            destination of the code
     * @param converterRange maximum degree, {@code 2^N - 1}
     * @param varName        name of the input variable
     * @param setNumber      index of this set in its variable
     */
    public abstract void compileFuzzSet(CppWriter out, int converterRange, String varName, int setNumber);

    /**
     * Emits the constant slopes this set needs, named after {@code slopeName}.
     *
     * @param out            destination of the code
     * @param converterRange maximum degree, {@code 2^N - 1}
     * @param slopeName      base name of the slope constants, {@code function<var><setNumber>}
     */
    public abstract void compileFunctionSlope(CppWriter out, int converterRange, String slopeName);
}
