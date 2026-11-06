package integration.lexer.parser.lowers;

import compiler.generator.ControllerModel;
import compiler.generator.Variable;
import compiler.generator.fuzzysets.FuzzySet;
import compiler.generator.fuzzysets.SShapeFuzzySet;
import compiler.generator.fuzzysets.SingletonFuzzySet;
import compiler.generator.fuzzysets.TriangularFuzzySet;
import compiler.generator.fuzzysets.ZShapeFuzzySet;
import compiler.generator.lowers.ControllerLower;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.Block;
import compiler.repository.DiagnosticsHandler;
import utils.supports.ParserTestSupport;
import utils.ParsingResults;
import utils.builders.SourceCode;
import compiler.repository.diagnostics.Diagnostic;
import compiler.repository.diagnostics.GeneratorWarning;
import compiler.repository.diagnostics.UndeclaredReference;
import compiler.repository.diagnostics.UnsupportedFeature;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class ControllerLowerIT extends ParserTestSupport {

    private static final String FUZZIFY_A =
            "FUZZIFY a " +
                    "TERM lo := (0,1) (100,0); " +
                    "TERM hi := (0,0) (100,1); " +
                    "END_FUZZIFY";
    private static final String FUZZIFY_B =
            "FUZZIFY b " +
                    "TERM lo := (0,1) (100,0); " +
                    "TERM hi := (0,0) (100,1); " +
                    "END_FUZZIFY";
    private static final String DEFUZZIFY =
            "DEFUZZIFY y " +
                    "TERM off := 0; " +
                    "TERM on := 255; " +
                    "METHOD : CoGS; " +
                    "DEFAULT := 0; " +
                    "END_DEFUZZIFY";
    private static final String RULES =
            "RULEBLOCK r AND : MIN; ACCU : MAX;\n" +
                    "RULE 1 : IF a IS lo AND b IS lo THEN y IS off;\n" +
                    "RULE 2 : IF a IS hi AND b IS hi THEN y IS on;\n" +
                    "END_RULEBLOCK";
    private static final String OPTION =
            "OPTION " +
                    "PRAGMA bit_width 8; " +
                    "END_OPTION";

    private final DiagnosticsHandler diagnostics = new DiagnosticsHandler();

    /**
     * Retorna un SourceCode base con las variables estándar declaradas.
     */
    private static SourceCode baseProgram() {
        return SourceCode.functionBlock()
                .var(Block.VAR_INPUT, "a : USINT")
                .var(Block.VAR_INPUT, "b : USINT")
                .var(Block.VAR_OUTPUT, "y : USINT");
    }

    private static String completeSourceCode() {
        return baseProgram()
                .body(FUZZIFY_A)
                .body(FUZZIFY_B)
                .body(DEFUZZIFY)
                .body(RULES)
                .body(OPTION)
                .build();
    }

    private static SourceCode variables(String a, String b, String y) {
        return SourceCode.functionBlock()
                .var(Block.VAR_INPUT, "a : " + a)
                .var(Block.VAR_INPUT, "b : " + b)
                .var(Block.VAR_OUTPUT, "y : " + y);
    }

    private static String fuzzify(String name, String lo, String hi) {
        return "FUZZIFY " + name + " TERM lo := (" + lo + ",1) (" + hi + ",0); TERM hi := (" + lo + ",0) (" + hi + ",1); END_FUZZIFY";
    }

    private static String defuzzify(String on) {
        return "DEFUZZIFY y TERM off := " + "0" + "; TERM on := " + on + "; METHOD : CoGS; DEFAULT := 0; END_DEFUZZIFY";
    }

    private Optional<ControllerModel> lower(String source) throws Exception {
        ParsingResults results = parse(new StringReader(source));
        return ControllerLower.newInstance(results.symbolTable(), results.abstractSyntaxTree(), diagnostics).adapt();
    }

    /**
     * Resuelve el warning del IDE. Falla con un mensaje claro (incluyendo diagnósticos)
     * si el modelo no puede ser generado, lo cual es útil para depurar.
     */
    private ControllerModel lowerAndGet(String source) throws Exception {
        return lower(source).orElseThrow(() ->
                new AssertionError("Expected model to be built, but it failed. Diagnostics: " +
                        diagnostics.getDiagnostics().stream()
                                .map(Diagnostic::getMessage)
                                .collect(Collectors.joining(", "))));
    }

    private List<String> messagesOf(Class<? extends Diagnostic> kind) {
        return diagnostics.getDiagnostics().stream()
                .filter(kind::isInstance)
                .map(Diagnostic::getMessage)
                .collect(Collectors.toList());
    }

    private void assertRejectedWith(Class<? extends Diagnostic> kind, String fragment, String source) throws Exception {
        Optional<ControllerModel> model = lower(source);
        assertFalse(model.isPresent(), "No model must be built");

        List<String> messages = messagesOf(kind);
        boolean hasMatch = messages.stream().anyMatch(message -> message.contains(fragment));

        assertTrue(hasMatch, () -> String.format(
                "Expected a %s containing '%s' but found:\n%s",
                kind.getSimpleName(),
                fragment,
                String.join("\n", messages)
        ));
    }

    @Nested
    class Supported {

        @Test
        void Inputs_and_outputs_keep_their_declaration_order_and_their_sets() throws Exception {
            ControllerModel model = lowerAndGet(completeSourceCode());

            assertEquals(8, model.converterSize());
            assertEquals(Arrays.asList("A", "B"), names(model.inputs()));
            assertEquals(Collections.singletonList("Y"), names(model.outputs()));
            assertSets(model.inputs().get(0), ZShapeFuzzySet.class, SShapeFuzzySet.class);
            assertSets(model.outputs().get(0), SingletonFuzzySet.class, SingletonFuzzySet.class);
            assertTrue(messagesOf(UnsupportedFeature.class).isEmpty());
        }

        @Test
        void Each_rule_is_a_row_with_the_term_index_of_every_input_and_output() throws Exception {
            ControllerModel model = lowerAndGet(completeSourceCode());

            assertEquals(2, model.evaluationMethod().rows());
            assertRow(model, 0, 0, 0, 0);
            assertRow(model, 1, 1, 1, 1);
        }

        @Test
        void Rule_may_name_the_inputs_in_any_order() throws Exception {
            String rules =
                    "RULEBLOCK r AND : MIN; ACCU : MAX;\n" +
                            "RULE 1 : IF b IS hi AND a IS lo THEN y IS on;\n" +
                            "END_RULEBLOCK";
            String source = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(rules)
                    .body(OPTION)
                    .build();

            ControllerModel model = lowerAndGet(source);

            assertRow(model, 0, 0, 1, 1);
        }

        @Test
        void Three_point_term_with_a_peak_is_a_triangle() throws Exception {
            String fuzzifyA =
                    "FUZZIFY a " +
                            "TERM mid := (0,0) (50,1) (100,0); " +
                            "TERM hi := (0,0) (100,1); " +
                            "END_FUZZIFY";
            String rules =
                    "RULEBLOCK r AND : MIN; ACCU : MAX;\n" +
                            "RULE 1 : IF a IS mid AND b IS lo THEN y IS on;\n" +
                            "END_RULEBLOCK";
            String source = baseProgram()
                    .body(fuzzifyA)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(rules)
                    .body(OPTION)
                    .build();

            ControllerModel model = lowerAndGet(source);

            assertSets(model.inputs().get(0), TriangularFuzzySet.class, SShapeFuzzySet.class);
        }

        @Test
        void Default_value_is_ignored_with_a_warning() throws Exception {
            lowerAndGet(completeSourceCode());

            assertEquals(1, messagesOf(GeneratorWarning.class).size());
            assertTrue(messagesOf(GeneratorWarning.class).get(0).contains("DEFAULT of Y is ignored"));
        }

        @Test
        void Without_pragma_the_width_is_the_one_of_the_widest_type() throws Exception {
            String source = SourceCode.functionBlock()
                    .var(Block.VAR_INPUT, "a : UINT")
                    .var(Block.VAR_INPUT, "b : USINT")
                    .var(Block.VAR_OUTPUT, "y : USINT")
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .build();

            ControllerModel model = lowerAndGet(source);

            assertEquals(16, model.converterSize());
            assertTrue(messagesOf(GeneratorWarning.class).stream().noneMatch(m -> m.contains("bit_width")));
        }

        @Test
        void Without_pragma_and_only_usint_the_width_is_eight() throws Exception {
            String source = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .build();

            ControllerModel model = lowerAndGet(source);

            assertEquals(8, model.converterSize());
        }

        @Test
        void Pragma_wins_over_the_types() throws Exception {
            String source = SourceCode.functionBlock()
                    .var(Block.VAR_INPUT, "a : UINT")
                    .var(Block.VAR_INPUT, "b : USINT")
                    .var(Block.VAR_OUTPUT, "y : USINT")
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .body(OPTION)
                    .build();

            ControllerModel model = lowerAndGet(source);

            assertEquals(8, model.converterSize());
        }

        @Test
        void Subrange_far_from_zero_with_two_values_needs_one_bit() throws Exception {
            String source = variables("UDINT (10000000..10000001)", "USINT (0..1)", "USINT (0..1)")
                    .body(fuzzify("a", "10000000", "10000001"))
                    .body(fuzzify("b", "0", "1"))
                    .body(defuzzify("1"))
                    .body(RULES)
                    .build();

            ControllerModel model = lowerAndGet(source);

            Stream<String> errMssgs = messagesOf(GeneratorWarning.class).stream();
            boolean hasExpectedMssg = errMssgs.anyMatch(m -> m.contains("starting at 10000000"));
            FuzzySet terms = model.inputs().get(0).sets().get(0);

            assertEquals(1, model.converterSize());
            assertEquals(1.0, terms.degree(0), 1e-12);
            assertEquals(0.0, terms.degree(1), 1e-12);
            assertTrue(hasExpectedMssg);
        }

        @Test
        void Signed_subrange_is_supported_through_its_offset() throws Exception {
            String source = variables("INT (-5..5)", "USINT (0..15)", "USINT (0..15)")
                    .body(fuzzify("a", "-5", "5"))
                    .body(fuzzify("b", "0", "15"))
                    .body(defuzzify("15"))
                    .body(RULES)
                    .build();

            assertEquals(4, lowerAndGet(source).converterSize());
        }

        @Test
        void Pragma_wins_over_the_span_of_a_subrange() throws Exception {
            String source = variables("UDINT (10000000..10000001)", "USINT (0..1)", "USINT (0..1)")
                    .body(fuzzify("a", "10000000", "10000001"))
                    .body(fuzzify("b", "0", "1"))
                    .body(defuzzify("1"))
                    .body(RULES)
                    .body("OPTION PRAGMA bit_width 4; END_OPTION")
                    .build();

            assertEquals(4, lowerAndGet(source).converterSize());
        }
    }

    @Nested
    class Unsupported {

        @Test
        void Bit_width_out_of_range() throws Exception {
            String errMssg = "PRAGMA bit_width must be an integer between 1 and 30";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .body("OPTION PRAGMA bit_width 40; END_OPTION")
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Real_variable() throws Exception {
            String errMssg = "only unsigned integer types or integer subranges are supported but variable A is REAL";
            String sourceCode = SourceCode.functionBlock()
                    .var(Block.VAR_INPUT, "a : REAL")
                    .var(Block.VAR_INPUT, "b : USINT")
                    .var(Block.VAR_OUTPUT, "y : USINT")
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Non_integer_point() throws Exception {
            String errMssg = "non-integer point 17.5";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A.replace("(100,0)", "(17.5,0)"))
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Point_outside_the_bit_width() throws Exception {
            String errMssg = "the point 300 cannot be representable with 8 bits";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A.replace("(100,0)", "(300,0)"))
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Shape_with_two_peaks() throws Exception {
            String errMssg = "term LO of A has a shape the generator does not support";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A.replace("(0,1) (100,0)", "(0,1) (50,0) (100,1)"))
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Points_given_by_variables() throws Exception {
            String errMssg = "points given by variables";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A.replace("(0,1) (100,0)", "(p,1) (100,0)"))
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Input_without_fuzzify_block() throws Exception {
            String errMssg = "input B has no FUZZIFY block";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Defuzzification_method_other_than_cogs() throws Exception {
            String errMssg = "METHOD COG of Y";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY.replace("CoGS", "CoG"))
                    .body(RULES)
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Output_term_that_is_not_a_singleton() throws Exception {
            String errMssg = "term ON of the output Y is not supported (only singleton outputs)";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY.replace("TERM on := 255;", "TERM on := (0,0) (255,1);"))
                    .body(RULES)
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Or_in_a_rule() throws Exception {
            String errMssg = "rule 1 of R uses OR";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES.replace("a IS lo AND b IS lo", "a IS lo OR b IS lo"))
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Not_in_a_rule() throws Exception {
            String errMssg = "rule 2 of R uses NOT";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES.replace("a IS hi AND b IS hi", "a IS hi AND b IS NOT hi"))
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Rule_that_does_not_use_every_input() throws Exception {
            String errMssg = "B is not used in rule 1 of R";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES.replace("a IS lo AND b IS lo", "a IS lo"))
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Weighted_rule() throws Exception {
            String errMssg = "weighting factor WITH .5";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES.replace("THEN y IS off;", "THEN y IS off WITH 0.5;"))
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void More_than_one_rule_block() throws Exception {
            String errMssg = "exactly one RULEBLOCK";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES + "\n" + RULES.replace("RULEBLOCK r", "RULEBLOCK s"))
                    .body(OPTION)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Every_problem_is_reported_at_once() throws Exception {
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A.replace("(100,0)", "(300,0)"))
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY.replace("CoGS", "CoG"))
                    .body(RULES.replace("a IS lo AND b IS lo", "a IS lo"))
                    .body(OPTION)
                    .build();

            lower(sourceCode);

            int numberOfProblemsFound = (messagesOf(UnsupportedFeature.class).size());
            String errMssg = messagesOf(UnsupportedFeature.class).toString();
            assertEquals(3, numberOfProblemsFound, errMssg);
        }

        @Test
        void Signed_variable() throws Exception {
            String errMssg = "only unsigned integer types or integer subranges are supported but variable A is INT";
            String sourceCode = variables("INT", "USINT", "USINT")
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Subrange_wider_than_the_supported_width() throws Exception {
            String errMssg =
                    "the bit width is taken from the widest variable (A needs 33 bits), " +
                    "but at most 30 bits are supported: declare PRAGMA bit_width or use a narrower type or subrange";
            String sourceCode = variables("ULINT (0..4294967296)", "USINT", "USINT")
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }

        @Test
        void Point_below_the_lower_bound_of_a_subrange() throws Exception {
            String errMssg = "the point 5 cannot be representable";
            String sourceCode = variables("USINT (10..20)", "USINT", "USINT")
                    .body(fuzzify("a", "5", "20"))
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES)
                    .build();

            assertRejectedWith(UnsupportedFeature.class, errMssg, sourceCode);
        }
    }

    @Nested
    class Undeclared {

        @Test
        void Term_used_in_a_rule() throws Exception {
            String errMssg = "undeclared in rule 1 of R, the MID is not from A";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES.replace("a IS lo AND b IS lo", "a IS mid AND b IS lo"))
                    .body(OPTION)
                    .build();

            assertRejectedWith(UndeclaredReference.class, errMssg, sourceCode);
        }

        @Test
        void Variable_used_in_a_rule() throws Exception {
            String errMssg =
                    "undeclared conditions must have inputs variables and conclusion outputs, " +
                    "but rule 2 of R uses C";
            String sourceCode = baseProgram()
                    .body(FUZZIFY_A)
                    .body(FUZZIFY_B)
                    .body(DEFUZZIFY)
                    .body(RULES.replace("b IS hi THEN", "c IS hi THEN"))
                    .body(OPTION)
                    .build();

            assertRejectedWith(UndeclaredReference.class, errMssg, sourceCode);
        }
    }

    private static List<String> names(List<Variable> variables) {
        return variables.stream().map(Variable::name).collect(Collectors.toList());
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