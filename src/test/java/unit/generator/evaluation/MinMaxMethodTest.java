package unit.generator.evaluation;

import compiler.generator.ControllerModel;
import org.junit.jupiter.api.Test;
import utils.supports.EvaluationTestSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MinMaxMethodTest extends EvaluationTestSupport {

    @Test
    void CompileEvalMethod_CompleteRule_GeneratesNestedMinimum() {
        ControllerModel model = buildMinMaxModel(3, new int[]{0, 1, 0, 1});
        String expected =
                "void rulesEvaluation(){ \n" +
                " #pragma HLS PIPELINE \n" +
                " #pragma HLS ARRAY_PARTITION variable=x0Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=x1Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=x2Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=yMembershipValues complete dim=1 \n" +
                "\n" +
                "\tfixed_int aux = 0; \n" +
                "\tyMembershipValues[1] = min(x0Fuzz[0], min(x1Fuzz[1], x2Fuzz[0])); \n" +
                "\n" +
                "} \n\n";

        String actual = compile(model);

        assertEquals(expected, actual);
    }

    @Test
    void CompileEvalMethod_UnusedInput_ExcludesItFromMinimum() {
        ControllerModel model = buildMinMaxModel(3, new int[]{0, UNUSED, 1, 1});
        String expected =
                "void rulesEvaluation(){ \n" +
                " #pragma HLS PIPELINE \n" +
                " #pragma HLS ARRAY_PARTITION variable=x0Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=x1Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=x2Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=yMembershipValues complete dim=1 \n" +
                "\n" +
                "\tfixed_int aux = 0; \n" +
                "\tyMembershipValues[1] = min(x0Fuzz[0], x2Fuzz[1]); \n" +
                "\n" +
                "} \n\n";

        String actual = compile(model);

        assertEquals(expected, actual);
    }

    @Test
    void CompileEvalMethod_SingleInputRule_GeneratesDirectAssignment() {
        ControllerModel model = buildMinMaxModel(3, new int[]{UNUSED, UNUSED, 1, 0});
        String expected =
                "void rulesEvaluation(){ \n" +
                " #pragma HLS PIPELINE \n" +
                " #pragma HLS ARRAY_PARTITION variable=x0Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=x1Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=x2Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=yMembershipValues complete dim=1 \n" +
                "\n" +
                "\tfixed_int aux = 0; \n" +
                "\tyMembershipValues[0] = x2Fuzz[1]; \n" +
                "\n" +
                "} \n\n";

        String actual = compile(model);

        assertEquals(expected, actual);
    }

    @Test
    void CompileEvalMethod_UnusedConclusion_GeneratesNoAssignment() {
        ControllerModel model = buildMinMaxModel(2, new int[]{0, 0, UNUSED});
        String expected =
                "void rulesEvaluation(){ \n" +
                " #pragma HLS PIPELINE \n" +
                " #pragma HLS ARRAY_PARTITION variable=x0Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=x1Fuzz complete dim=1 \n" +
                " #pragma HLS ARRAY_PARTITION variable=yMembershipValues complete dim=1 \n" +
                "\n" +
                "\tfixed_int aux = 0; \n" +
                "} \n\n";

        String actual = compile(model);

        assertEquals(expected, actual);
    }

    @Test
    void CompileEvalMethod_RuleWithoutInputs_ThrowsIllegalStateException() {
        ControllerModel model = buildMinMaxModel(2, new int[]{UNUSED, UNUSED, 1});

        assertThrows(
                IllegalStateException.class,
                () -> compile(model)
        );
    }
}

