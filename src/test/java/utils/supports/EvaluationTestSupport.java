package utils.supports;

import compiler.generator.ControllerModel;
import compiler.generator.CppWriter;
import compiler.generator.Variable;
import compiler.generator.defuzzification.CentroidDefuzzifier;
import compiler.generator.defuzzification.Defuzzifier;
import compiler.generator.evaluation.EvaluationMethod;
import compiler.generator.evaluation.MinMaxMethod;
import compiler.generator.fuzzysets.SShapeFuzzySet;
import compiler.generator.fuzzysets.SingletonFuzzySet;
import compiler.generator.fuzzysets.ZShapeFuzzySet;
import compiler.generator.lowers.utils.ControllerModelState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class EvaluationTestSupport {
    protected static final int UNUSED = EvaluationMethod.UNUSED;

    protected static ControllerModel buildMinMaxModel(int inputCount, int[]... rules) {
        List<Variable> inputs = new ArrayList<>();

        for (int i = 0; i < inputCount; i++) {
            inputs.add(new Variable(
                    "x" + i,
                    Arrays.asList(
                            new ZShapeFuzzySet("lo", 0, 10),
                            new SShapeFuzzySet("hi", 0, 10)
                    )
            ));
        }

        Variable output = new Variable(
                "y",
                Arrays.asList(
                        new SingletonFuzzySet("off", 0),
                        new SingletonFuzzySet("on", 255)
                )
        );

        Map<String, Defuzzifier> defuzzifiers = new LinkedHashMap<>();
        defuzzifiers.put("y", new CentroidDefuzzifier());

        return new ControllerModel(
                new ControllerModelState(
                        8,
                        inputs,
                        Collections.singletonList(output),
                        new MinMaxMethod(Arrays.asList(rules)),
                        defuzzifiers
                )
        );
    }

    protected static String compile(ControllerModel model) {
        CppWriter writer = new CppWriter();
        model.evaluationMethod().compileEvalMethod(writer, model);
        return writer.toString();
    }
}

