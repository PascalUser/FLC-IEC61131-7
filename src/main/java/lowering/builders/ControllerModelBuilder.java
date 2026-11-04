package lowering.builders;

import codegen.ControllerModel;
import codegen.Variable;
import codegen.defuzzification.Defuzzifier;
import codegen.evaluation.EvaluationMethod;
import lowering.utils.ControllerModelState;

import java.util.List;
import java.util.Map;


public class ControllerModelBuilder implements ControllerModelSchema {
    private final ControllerModelState state = new ControllerModelState();

    @Override
    public ControllerModelBuilder bitWidth(int newBitWidth) {
        state.bitWidth = newBitWidth;
        return this;
    }

    public int bitWidth() {
        return state.bitWidth;
    }

    public ControllerModelBuilder inputs(List<Variable> inputs) {
        state.inputs = inputs;
        return this;
    }

    public List<Variable> inputs() {
        return state.inputs;
    }

    public ControllerModelBuilder outputs(List<Variable> outputs) {
        state.outputs = outputs;
        return this;
    }

    public List<Variable> outputs() {
        return state.outputs;
    }

    public ControllerModelBuilder evaluationMethod(EvaluationMethod evaluationMethod) {
        state.evaluationMethod = evaluationMethod;
        return this;
    }

    public EvaluationMethod evaluationMethod() {
        return state.evaluationMethod;
    }

    public ControllerModelBuilder defuzzifiers(Map<String, Defuzzifier> defuzzifiers) {
        state.defuzzifiers = defuzzifiers;
        return this;
    }

    public Map<String, Defuzzifier> defuzzifiers() {
        return state.defuzzifiers;
    }

    public ControllerModel build() {
        return new ControllerModel(state);
    }
}
