package lowering.utils;

import codegen.Variable;
import codegen.defuzzification.Defuzzifier;
import codegen.evaluation.EvaluationMethod;

import java.util.List;
import java.util.Map;

public class ControllerModelState {
    public int bitWidth;
    public List<Variable> inputs;
    public List<Variable> outputs;
    public EvaluationMethod evaluationMethod;
    public Map<String, Defuzzifier> defuzzifiers;

    public ControllerModelState() {
        this(-1, null, null, null, null);
    }

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