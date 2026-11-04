package lowering.builders;

import codegen.ControllerModel;
import codegen.Variable;
import codegen.defuzzification.Defuzzifier;
import codegen.evaluation.EvaluationMethod;
import lowering.utils.ControllerModelState;

import java.util.List;
import java.util.Map;


/**
 * Builder that accumulates the state of a controller model and creates an immutable
 * {@link codegen.ControllerModel} when all lowering stages have run.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class ControllerModelBuilder implements ControllerModelSchema {
    private final ControllerModelState state = new ControllerModelState();

    /** {@inheritDoc} */
    @Override
    public ControllerModelBuilder bitWidth(int newBitWidth) {
        state.bitWidth = newBitWidth;
        return this;
    }

    /**
     * Returns the bit width configured so far.
     *
     * @return the bit width
     */
    public int bitWidth() {
        return state.bitWidth;
    }

    /**
     * Sets the input variables of the controller.
     *
     * @param inputs input variables in signature order
     * @return this builder
     */
    public ControllerModelBuilder inputs(List<Variable> inputs) {
        state.inputs = inputs;
        return this;
    }

    /**
     * Returns the input variables of the controller.
     *
     * @return the input variables
     */
    public List<Variable> inputs() {
        return state.inputs;
    }

    /**
     * Sets the output variables of the controller.
     *
     * @param outputs output variables in packing order
     * @return this builder
     */
    public ControllerModelBuilder outputs(List<Variable> outputs) {
        state.outputs = outputs;
        return this;
    }

    /**
     * Returns the output variables of the controller.
     *
     * @return the output variables
     */
    public List<Variable> outputs() {
        return state.outputs;
    }

    /**
     * Sets the rule evaluation method.
     *
     * @param evaluationMethod method that owns the rule matrix
     * @return this builder
     */
    public ControllerModelBuilder evaluationMethod(EvaluationMethod evaluationMethod) {
        state.evaluationMethod = evaluationMethod;
        return this;
    }

    /**
     * Returns the rule evaluation method.
     *
     * @return the evaluation method
     */
    public EvaluationMethod evaluationMethod() {
        return state.evaluationMethod;
    }

    /**
     * Sets the defuzzifier of every output.
     *
     * @param defuzzifiers defuzzifier keyed by output name
     * @return this builder
     */
    public ControllerModelBuilder defuzzifiers(Map<String, Defuzzifier> defuzzifiers) {
        state.defuzzifiers = defuzzifiers;
        return this;
    }

    /**
     * Returns the defuzzifier of every output.
     *
     * @return the defuzzifiers keyed by output name
     */
    public Map<String, Defuzzifier> defuzzifiers() {
        return state.defuzzifiers;
    }

    /**
     * Builds the controller model from the accumulated state.
     *
     * @return a new controller model
     * @throws IllegalArgumentException if the accumulated state is not a valid controller
     */
    public ControllerModel build() {
        return new ControllerModel(state);
    }
}
