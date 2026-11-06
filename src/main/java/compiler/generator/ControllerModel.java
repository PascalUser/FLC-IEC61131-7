package compiler.generator;

import compiler.generator.defuzzification.Defuzzifier;
import compiler.generator.evaluation.EvaluationMethod;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import compiler.generator.lowers.utils.ControllerModelState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Everything the code generator needs to emit one fuzzy controller.
 * <p>
 * Replaces the global state of Luca Sarramone's generator (Generador-FLC {@code c304c1d}): {@code IOVars}
 * (converter size and ordered input/output variables) and the {@code varColumn} of {@code Compiler.table}.
 * Here the rule matrix has a fixed column layout: one column per input in {@link #inputs()} order, then one per
 * output in {@link #outputs()} order.
 * </p>
 * <p>
 * Every value of the controller is an unsigned integer of {@code N} bits ({@code ap_uint<N>}), so degrees and
 * coordinates go from 0 to {@link #converterRange()}.
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class ControllerModel {
    /** Smallest supported bit width. */
    public static final int MIN_BIT_WIDTH = 1;
    /** Largest supported bit width: {@code 2^N - 1} must fit in an {@code int}. */
    public static final int MAX_BIT_WIDTH = 30;

    private final int converterSize;
    private final List<Variable> inputs;
    private final List<Variable> outputs;
    private final EvaluationMethod evaluationMethod;
    private final Map<String, Defuzzifier> defuzzifiers;

    /**
     * Creates the model of a controller.
     *
     * @param state values accumulated by the lowering stages
     * @throws IllegalArgumentException if the state is not a valid controller
     */
    public ControllerModel(ControllerModelState state) {
        if (state.bitWidth < MIN_BIT_WIDTH || state.bitWidth > MAX_BIT_WIDTH) {
            throw new IllegalArgumentException("Bit width " + state.bitWidth + " out of range "
                    + MIN_BIT_WIDTH + ".." + MAX_BIT_WIDTH);
        }
        if (state.inputs.isEmpty() || state.outputs.isEmpty()) {
            throw new IllegalArgumentException("A controller needs at least one input and one output");
        }
        for (Variable output : state.outputs) {
            if (!state.defuzzifiers.containsKey(output.name())) {
                throw new IllegalArgumentException("Output " + output.name() + " has no defuzzifier");
            }
        }
        this.converterSize = state.bitWidth;
        this.inputs = Collections.unmodifiableList(new ArrayList<>(state.inputs));
        this.outputs = Collections.unmodifiableList(new ArrayList<>(state.outputs));
        this.evaluationMethod = Objects.requireNonNull(state.evaluationMethod, "evaluationMethod");
        this.defuzzifiers = Collections.unmodifiableMap(new LinkedHashMap<>(state.defuzzifiers));
    }

    /**
     * Returns the bit width N of every value.
     *
     * @return the converter size
     */
    public int converterSize() {
        return converterSize;
    }

    /**
     * Returns the maximum value of N bits, used as maximum membership degree.
     *
     * @return {@code 2^N - 1}
     */
    public int converterRange() {
        return (1 << converterSize) - 1;
    }

    /**
     * Returns the bit width of the centroid accumulators of an output: {@code 2N + number of its sets}.
     *
     * @param outputIndex index of the output in {@link #outputs()}
     * @return the width of {@code fixed_aux_<output>}
     */
    public int auxTypeSize(final int outputIndex) {
        return (2 * converterSize) + outputs.get(outputIndex).size();
    }

    /**
     * Returns the bit width of the controller result: all outputs packed together.
     *
     * @return {@code N * number of outputs}
     */
    public int outTypeSize() {
        return outputs.size() * converterSize;
    }

    /**
     * Returns the input variables in signature order.
     *
     * @return unmodifiable list of inputs
     */
    public List<Variable> inputs() {
        return inputs;
    }

    /**
     * Returns the output variables in packing order.
     *
     * @return unmodifiable list of outputs
     */
    public List<Variable> outputs() {
        return outputs;
    }

    /**
     * Returns the rule evaluation method.
     *
     * @return the evaluation method
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "EvaluationMethod is immutable: it copies the rule matrix and has no setters"
    )
    public EvaluationMethod evaluationMethod() {
        return evaluationMethod;
    }

    /**
     * Returns the defuzzifier of an output.
     *
     * @param outputName name of the output variable
     * @return its defuzzifier
     */
    public Defuzzifier defuzzifier(final String outputName) {
        return defuzzifiers.get(outputName);
    }

    /**
     * Returns the rule matrix column of an input.
     *
     * @param inputIndex index of the input in {@link #inputs()}
     * @return its column
     */
    public int inputColumn(final int inputIndex) {
        return inputIndex;
    }

    /**
     * Returns the rule matrix column of an output.
     *
     * @param outputIndex index of the output in {@link #outputs()}
     * @return its column
     */
    public int outputColumn(final int outputIndex) {
        return inputs.size() + outputIndex;
    }
}
