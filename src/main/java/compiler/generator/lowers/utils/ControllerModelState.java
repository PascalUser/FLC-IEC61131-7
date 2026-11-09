package compiler.generator.lowers.utils;

import compiler.generator.ControllerModel;
import compiler.generator.Variable;
import compiler.generator.defuzzification.Defuzzifier;
import compiler.generator.evaluation.EvaluationMethod;
import compiler.generator.lowers.builders.ControllerModelBuilder;

import java.util.List;
import java.util.Map;

/**
 * Mutable state accumulated by {@link ControllerModelBuilder} before building a
 * {@link ControllerModel}.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class ControllerModelState {
    /** Bit-width of every controller value. */
    public int bitWidth;
    /** Input variables in signature order. */
    public List<Variable> inputs;
    /** Output variables in packing order. */
    public List<Variable> outputs;
    /** Rule evaluation method. */
    public EvaluationMethod evaluationMethod;
    /** Defuzzifier keyed by output name. */
    public Map<String, Defuzzifier> defuzzifiers;

    /**
     * Creates an empty state with no bit width, variables, evaluation method or defuzzifiers.
     */
    public ControllerModelState() {
        this(-1, null, null, null, null);
    }

    /**
     * Creates a state with all its values.
     *
     * @param bitWidth bit-width of every controller value
     * @param inputs input variables in signature order
     * @param outputs output variables in packing order
     * @param evaluationMethod rule evaluation method
     * @param defuzzifiers defuzzifier keyed by output name
     */
    public ControllerModelState(
            int bitWidth,
            List<Variable> inputs,
            List<Variable> outputs,
            EvaluationMethod evaluationMethod,
            Map<String, Defuzzifier> defuzzifiers
    ) {
        this.bitWidth = bitWidth;
        this.inputs = inputs;
        this.outputs = outputs;
        this.evaluationMethod = evaluationMethod;
        this.defuzzifiers = defuzzifiers;
    }
}
