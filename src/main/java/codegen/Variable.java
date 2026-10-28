package codegen;

import codegen.fuzzysets.FuzzySet;
import codegen.fuzzysets.SingletonFuzzySet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Input or output variable of the controller with its fuzzy sets, in declaration order (the index of a set is
 * the value the rule matrix uses for it).
 * <p>
 * Port of {@code code_generation.variables.Variable} (Luca Sarramone, Generador-FLC {@code c304c1d}).
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class Variable {
    private final String name;
    private final List<FuzzySet> sets;

    /**
     * Creates a variable.
     *
     * @param name name used in the generated code
     * @param sets its fuzzy sets, in order
     */
    public Variable(final String name, final List<? extends FuzzySet> sets) {
        this.name = Objects.requireNonNull(name, "name");
        this.sets = Collections.unmodifiableList(new ArrayList<FuzzySet>(sets));
    }

    /**
     * Returns the name used in the generated code.
     *
     * @return the variable name
     */
    public String name() {
        return name;
    }

    /**
     * Returns the fuzzy sets in order.
     *
     * @return unmodifiable list of sets
     */
    public List<FuzzySet> sets() {
        return sets;
    }

    /**
     * Returns the number of fuzzy sets.
     *
     * @return the number of sets
     */
    public int size() {
        return sets.size();
    }

    /**
     * Emits the slope constants of every set ({@code function<name><i>...}).
     *
     * @param out            destination of the code
     * @param converterRange maximum degree, {@code 2^N - 1}
     */
    public void compileFunctionsSlope(final CppWriter out, final int converterRange) {
        String slopeName = "function" + name;
        for (int i = 0; i < sets.size(); i++) {
            sets.get(i).compileFunctionSlope(out, converterRange, slopeName + i);
        }
    }

    /**
     * Emits the fuzzification of every set into {@code <name>Fuzz}.
     *
     * @param out            destination of the code
     * @param converterRange maximum degree, {@code 2^N - 1}
     */
    public void compile(final CppWriter out, final int converterRange) {
        for (int i = 0; i < sets.size(); i++) {
            sets.get(i).compileFuzzSet(out, converterRange, name, i);
            out.write("\n\n");
        }
    }

    /**
     * Emits a zero-initialized buffer with one degree per set, named {@code <name><bufferType>}.
     *
     * @param out        destination of the code
     * @param bufferType suffix of the buffer, e.g. {@code Fuzz} or {@code MembershipValues}
     */
    public void compileBuffer(final CppWriter out, final String bufferType) {
        out.write("fixed_int " + name + bufferType + "[" + size() + "] = {0");
        for (int j = 0; j < size() - 1; j++) {
            out.write(", 0");
        }
        out.write("}; \n");
    }

    /**
     * Emits the constant array with the values of the output singletons ({@code outputValues<name>}).
     *
     * @param out destination of the code
     * @throws IllegalStateException if a set is not a singleton
     */
    public void compileOutputSetsBuffer(final CppWriter out) {
        out.write("const fixed_int outputValues" + name + "[" + size() + "] = ");
        out.write("{" + singleton(0).value());
        for (int i = 1; i < size(); i++) {
            out.write(", " + singleton(i).value());
        }
        out.write("}; \n");
    }

    private SingletonFuzzySet singleton(final int index) {
        FuzzySet set = sets.get(index);
        if (!(set instanceof SingletonFuzzySet)) {
            throw new IllegalStateException("Output variable " + name + ": term " + set.name()
                    + " is not a singleton; the centroid of this generator needs singleton outputs");
        }
        return (SingletonFuzzySet) set;
    }
}
