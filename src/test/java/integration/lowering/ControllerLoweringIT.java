package integration.lowering;

import codegen.ControllerModel;
import codegen.Variable;
import codegen.fuzzysets.FuzzySet;
import codegen.fuzzysets.SShapeFuzzySet;
import codegen.fuzzysets.SingletonFuzzySet;
import codegen.fuzzysets.TriangularFuzzySet;
import codegen.fuzzysets.ZShapeFuzzySet;
import lowering.ControllerLowering;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.DiagnosticsHandler;
import utils.ParserTestSupport;
import utils.ParsingResults;
import utils.diagnostics.Diagnostic;
import utils.diagnostics.GeneratorWarning;
import utils.diagnostics.UndeclaredReference;
import utils.diagnostics.UnsupportedFeature;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests of the lowering: FCL source, parsed, to the controller model of the code generator, and
 * the report of what the generator does not support.
 * <p>
 * Each test starts from a supported program (two inputs with an S and a Z ramp, one singleton output, two rules)
 * and changes one part.
 * </p>
 */
public class ControllerLoweringIT extends ParserTestSupport {

    private static final String VARIABLES = "VAR_INPUT a : USINT; b : USINT; END_VAR\nVAR_OUTPUT y : USINT; END_VAR";
    private static final String FUZZIFY_A = "FUZZIFY a TERM lo := (0,1) (100,0); TERM hi := (0,0) (100,1); END_FUZZIFY";
    private static final String FUZZIFY_B = "FUZZIFY b TERM lo := (0,1) (100,0); TERM hi := (0,0) (100,1); END_FUZZIFY";
    private static final String DEFUZZIFY = "DEFUZZIFY y TERM off := 0; TERM on := 255; METHOD : CoGS; "
            + "DEFAULT := 0; END_DEFUZZIFY";
    private static final String RULES = "RULEBLOCK r AND : MIN; ACCU : MAX;\n"
            + "RULE 1 : IF a IS lo AND b IS lo THEN y IS off;\n"
            + "RULE 2 : IF a IS hi AND b IS hi THEN y IS on;\n"
            + "END_RULEBLOCK";
    private static final String OPTION = "OPTION PRAGMA bit_width 8; END_OPTION";

    private final DiagnosticsHandler diagnostics = new DiagnosticsHandler();

    private static String program(String variables, String fuzzify, String defuzzify, String rules, String option) {
        return String.join("\n", "FUNCTION_BLOCK main", variables, fuzzify, defuzzify, rules, option,
                "END_FUNCTION_BLOCK");
    }

    private static String supported() {
        return program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY, RULES, OPTION);
    }

    private Optional<ControllerModel> lower(String source) throws Exception {
        ParsingResults results = parse(new StringReader(source));
        return new ControllerLowering(results.symbolTable(), "main", diagnostics)
                .lower(results.abstractSyntaxTree());
    }

    private List<String> messagesOf(Class<? extends Diagnostic> kind) {
        List<String> messages = new ArrayList<>();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            if (kind.isInstance(diagnostic)) {
                messages.add(diagnostic.getMessage());
            }
        }
        return messages;
    }

    private void assertRejectedWith(Class<? extends Diagnostic> kind, String fragment, String source)
            throws Exception {
        Optional<ControllerModel> model = lower(source);

        assertFalse(model.isPresent(), "No model must be built");
        List<String> messages = messagesOf(kind);
        assertTrue(messages.stream().anyMatch(message -> message.contains(fragment)),
                "Expected a " + kind.getSimpleName() + " containing '" + fragment + "' in " + messages);
    }

    @Nested
    class Supported {

        @Test
        void Inputs_and_outputs_keep_their_declaration_order_and_their_sets() throws Exception {
            ControllerModel model = lower(supported()).get();

            assertEquals(8, model.converterSize());
            assertEquals(Arrays.asList("A", "B"), names(model.inputs()));
            assertEquals(Arrays.asList("Y"), names(model.outputs()));
            assertSets(model.inputs().get(0), ZShapeFuzzySet.class, SShapeFuzzySet.class);
            assertSets(model.outputs().get(0), SingletonFuzzySet.class, SingletonFuzzySet.class);
            assertTrue(messagesOf(UnsupportedFeature.class).isEmpty());
        }

        @Test
        void Each_rule_is_a_row_with_the_term_index_of_every_input_and_output() throws Exception {
            ControllerModel model = lower(supported()).get();

            assertEquals(2, model.evaluationMethod().rows());
            assertRow(model, 0, 0, 0, 0);
            assertRow(model, 1, 1, 1, 1);
        }

        @Test
        void Rule_may_name_the_inputs_in_any_order() throws Exception {
            String rules = "RULEBLOCK r AND : MIN; ACCU : MAX;\n"
                    + "RULE 1 : IF b IS hi AND a IS lo THEN y IS on;\nEND_RULEBLOCK";

            ControllerModel model = lower(program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY, rules,
                    OPTION)).get();

            assertRow(model, 0, 0, 1, 1);
        }

        @Test
        void Three_point_term_with_a_peak_is_a_triangle() throws Exception {
            String fuzzify = "FUZZIFY a TERM mid := (0,0) (50,1) (100,0); TERM hi := (0,0) (100,1); END_FUZZIFY";
            String rules = "RULEBLOCK r AND : MIN; ACCU : MAX;\n"
                    + "RULE 1 : IF a IS mid AND b IS lo THEN y IS on;\nEND_RULEBLOCK";

            ControllerModel model = lower(program(VARIABLES, fuzzify + "\n" + FUZZIFY_B, DEFUZZIFY, rules,
                    OPTION)).get();

            assertSets(model.inputs().get(0), TriangularFuzzySet.class, SShapeFuzzySet.class);
        }

        @Test
        void Default_value_is_ignored_with_a_warning() throws Exception {
            lower(supported());

            assertEquals(1, messagesOf(GeneratorWarning.class).size());
            assertTrue(messagesOf(GeneratorWarning.class).get(0).contains("DEFAULT of Y is ignored"));
        }

        @Test
        void Without_bit_width_pragma_eight_bits_are_used_with_a_warning() throws Exception {
            ControllerModel model = lower(program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY, RULES,
                    "")).get();

            assertEquals(ControllerLowering.DEFAULT_BIT_WIDTH, model.converterSize());
            assertTrue(messagesOf(GeneratorWarning.class).stream().anyMatch(m -> m.contains("bit_width")));
        }
    }

    @Nested
    class Unsupported {

        @Test
        void Bit_width_out_of_range() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "PRAGMA bit_width 40",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY, RULES,
                            "OPTION PRAGMA bit_width 40; END_OPTION"));
        }

        @Test
        void Real_variable() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "variable A is REAL",
                    program("VAR_INPUT a : REAL; b : USINT; END_VAR\nVAR_OUTPUT y : USINT; END_VAR",
                            FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY, RULES, OPTION));
        }

        @Test
        void Non_integer_point() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "non-integer point 17.5",
                    program(VARIABLES, FUZZIFY_A.replace("(100,0)", "(17.5,0)") + "\n" + FUZZIFY_B, DEFUZZIFY,
                            RULES, OPTION));
        }

        @Test
        void Point_outside_the_bit_width() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "point 300 outside 0..255",
                    program(VARIABLES, FUZZIFY_A.replace("(100,0)", "(300,0)") + "\n" + FUZZIFY_B, DEFUZZIFY,
                            RULES, OPTION));
        }

        @Test
        void Shape_with_two_peaks() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "term LO of A has a shape the generator does not support",
                    program(VARIABLES, FUZZIFY_A.replace("(0,1) (100,0)", "(0,1) (50,0) (100,1)") + "\n"
                            + FUZZIFY_B, DEFUZZIFY, RULES, OPTION));
        }

        @Test
        void Points_given_by_variables() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "points given by variables",
                    program(VARIABLES, FUZZIFY_A.replace("(0,1) (100,0)", "(p,1) (100,0)") + "\n" + FUZZIFY_B,
                            DEFUZZIFY, RULES, OPTION));
        }

        @Test
        void Input_without_fuzzify_block() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "input B has no FUZZIFY block",
                    program(VARIABLES, FUZZIFY_A, DEFUZZIFY, RULES, OPTION));
        }

        @Test
        void Defuzzification_method_other_than_cogs() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "METHOD COG of Y",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY.replace("CoGS", "CoG"), RULES,
                            OPTION));
        }

        @Test
        void Output_term_that_is_not_a_singleton() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "term ON of the output Y is not a singleton",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B,
                            DEFUZZIFY.replace("TERM on := 255;", "TERM on := (0,0) (255,1);"), RULES, OPTION));
        }

        @Test
        void Or_in_a_rule() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "rule 1 of MAIN#R uses OR",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY,
                            RULES.replace("a IS lo AND b IS lo", "a IS lo OR b IS lo"), OPTION));
        }

        @Test
        void Not_in_a_rule() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "rule 2 of MAIN#R uses NOT",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY,
                            RULES.replace("a IS hi AND b IS hi", "a IS hi AND b IS NOT hi"), OPTION));
        }

        @Test
        void Rule_that_does_not_use_every_input() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "rule 1 of MAIN#R does not use the input B",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY,
                            RULES.replace("a IS lo AND b IS lo", "a IS lo"), OPTION));
        }

        @Test
        void Weighted_rule() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "weighting factor WITH .5",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY,
                            RULES.replace("THEN y IS off;", "THEN y IS off WITH 0.5;"), OPTION));
        }

        @Test
        void More_than_one_rule_block() throws Exception {
            assertRejectedWith(UnsupportedFeature.class, "exactly one RULEBLOCK",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY,
                            RULES + "\n" + RULES.replace("RULEBLOCK r", "RULEBLOCK s"), OPTION));
        }

        @Test
        void Every_problem_is_reported_at_once() throws Exception {
            lower(program(VARIABLES, FUZZIFY_A.replace("(100,0)", "(300,0)") + "\n" + FUZZIFY_B,
                    DEFUZZIFY.replace("CoGS", "CoG"), RULES.replace("a IS lo AND b IS lo", "a IS lo"), OPTION));

            assertEquals(3, messagesOf(UnsupportedFeature.class).size(), messagesOf(UnsupportedFeature.class)
                    .toString());
        }
    }

    @Nested
    class Undeclared {

        @Test
        void Term_used_in_a_rule() throws Exception {
            assertRejectedWith(UndeclaredReference.class, "term MID of A in rule 1 of MAIN#R",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY,
                            RULES.replace("a IS lo AND b IS lo", "a IS mid AND b IS lo"), OPTION));
        }

        @Test
        void Variable_used_in_a_rule() throws Exception {
            assertRejectedWith(UndeclaredReference.class, "variable C in rule 2 of MAIN#R",
                    program(VARIABLES, FUZZIFY_A + "\n" + FUZZIFY_B, DEFUZZIFY,
                            RULES.replace("b IS hi THEN", "c IS hi THEN"), OPTION));
        }
    }

    private static List<String> names(List<Variable> variables) {
        List<String> names = new ArrayList<>();
        for (Variable variable : variables) {
            names.add(variable.name());
        }
        return names;
    }

    @SafeVarargs
    private static void assertSets(Variable variable, Class<? extends FuzzySet>... kinds) {
        assertEquals(kinds.length, variable.size());
        for (int i = 0; i < kinds.length; i++) {
            assertInstanceOf(kinds[i], variable.sets().get(i), variable.name() + " set " + i);
        }
    }

    private static void assertRow(ControllerModel model, int row, int... cells) {
        for (int column = 0; column < cells.length; column++) {
            assertEquals(cells[column], model.evaluationMethod().set(row, column),
                    "rule " + row + ", column " + column);
        }
    }
}
