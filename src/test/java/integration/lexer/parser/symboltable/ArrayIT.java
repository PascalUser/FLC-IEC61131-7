package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import parser.initializations.Initialization;
import parser.initializations.leafs.EnumeratedInitialization;
import parser.initializations.leafs.VariableInitialization;
import parser.initializations.nodes.RepeatedInitialization;
import parser.initializations.nodes.StructInitialization;
import utils.Block;
import utils.ParserTestSupport;
import utils.SourceCode;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Arrays;
import java.util.List;

/**
 * Integration tests for ARRAY declarations: types, variables, struct members, multidimensional bounds,
 * element lists and repetition factors ({@code N(value)}).
 * <p>
 * The initial value of an array is a {@link RepeatedInitialization}: the total dimension plus one
 * interval per initializer element (a repetition factor widens that element's interval).
 * </p>
 */
public class ArrayIT extends ParserTestSupport {

    private static final String COLOR_TYPE = String.join("\n",
            "color_type : STRUCT",
            "    classification : (WHITE, GRAY, BLACK);",
            "    gamma : REAL := 0.5;",
            "END_STRUCT");

    private static List<String> list(String... values) {
        return Arrays.asList(values);
    }

    private static RepeatedInitialization repeated(int dimension, Initialization defaultValue) {
        return new RepeatedInitialization(dimension, defaultValue);
    }

    private static StructInitialization color(Initialization classification, String gamma) {
        StructInitialization color = new StructInitialization();
        color.put("CLASSIFICATION", classification);
        color.put("GAMMA", new VariableInitialization(gamma));
        return color;
    }

    private static StructInitialization colorWithDefaultClassification(String gamma) {
        return color(new EnumeratedInitialization(list("WHITE", "GRAY", "BLACK")), gamma);
    }

    private static LexemeInfoBuilder array(Use use, Source source, Subtype subtype, List<String> low, List<String> high) {
        return new LexemeInfoBuilder()
                .type(Type.ARRAY)
                .subtype(subtype)
                .use(use)
                .source(source)
                .inferiorLimits(low)
                .superiorLimits(high);
    }

    @Nested
    class AsTypeDeclaration {
        @Test
        void Array_type_of_a_primitive_publishes_its_bounds_and_repeats_the_default() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock().type("samples_type : ARRAY [0..2] OF INT"));

            assertSymbol(st, "SAMPLES_TYPE", array(Use.TYPE, Source.NONE, Subtype.INT, list("0"), list("2"))
                    .initialValue(repeated(3, intDefault()))
                    .build());
        }

        @Test
        void Array_type_of_a_struct_repeats_the_struct_default() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(COLOR_TYPE)
                    .type("pixels_type : ARRAY [0..1] OF color_type"));

            assertSymbol(st, "PIXELS_TYPE", array(Use.TYPE, Source.NONE, Subtype.CUSTOM, list("0"), list("1"))
                    .customType("COLOR_TYPE")
                    .initialValue(repeated(2, colorWithDefaultClassification(".5")))
                    .build());
        }
    }

    @Nested
    class AsVariable {
        @ParameterizedTest
        @EnumSource(Block.class)
        void Array_of_a_primitive_publishes_its_bounds_and_repeats_the_default(Block block) throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock().var(block, "readings : ARRAY [1..3] OF REAL"));

            assertSymbol(st, SourceCode.key("readings"),
                    array(Use.VARIABLE, block.source(), Subtype.REAL, list("1"), list("3"))
                            .initialValue(repeated(3, realDefault()))
                            .build());
        }

        @Test
        void Multidimensional_array_dimension_is_the_product_of_its_ranges() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock().var(Block.VAR, "grid : ARRAY [0..1, 0..2] OF INT"));

            assertSymbol(st, SourceCode.key("grid"),
                    array(Use.VARIABLE, Source.INTERNAL, Subtype.INT, list("0", "0"), list("1", "2"))
                            .initialValue(repeated(6, intDefault()))
                            .build());
        }

        @ParameterizedTest
        @EnumSource(Block.class)
        void Array_of_a_struct_type_repeats_the_struct_default(Block block) throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(COLOR_TYPE)
                    .var(block, "pixels : ARRAY [0..1] OF color_type"));

            assertSymbol(st, SourceCode.key("pixels"),
                    array(Use.VARIABLE, block.source(), Subtype.CUSTOM, list("0"), list("1"))
                            .customType("COLOR_TYPE")
                            .initialValue(repeated(2, colorWithDefaultClassification(".5")))
                            .build());
        }

        @Test
        void Each_initializer_element_gets_its_own_interval() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .var(Block.VAR, "values1 : ARRAY [0..3] OF INT := [1, 2, 3, 4]"));

            RepeatedInitialization expected = repeated(4, intDefault());
            expected.put("0", new VariableInitialization("1"));
            expected.put("1", new VariableInitialization("2"));
            expected.put("2", new VariableInitialization("3"));
            expected.put("3", new VariableInitialization("4"));

            assertSymbol(st, SourceCode.key("values1"),
                    array(Use.VARIABLE, Source.INTERNAL, Subtype.INT, list("0"), list("3"))
                            .initialValue(expected)
                            .build());
        }

        @Test
        void Array_element_with_a_repetition_factor_covers_that_many_positions() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .var(Block.VAR, "values1 : ARRAY [0..3] OF INT := [3(7), 9]"));

            RepeatedInitialization expected = repeated(4, intDefault());
            expected.put("0..2", new VariableInitialization("7"));
            expected.put("3", new VariableInitialization("9"));

            assertSymbol(st, SourceCode.key("values1"),
                    array(Use.VARIABLE, Source.INTERNAL, Subtype.INT, list("0"), list("3"))
                            .initialValue(expected)
                            .build());
        }

        /**
         * Replaces the old {@code ArrayVariableIT} case, whose initializer had 41 elements for an array of 40
         * and expected {@code Interval(39, 40)}, i.e. it asserted an out-of-range index as correct.
         * Here the counts add up to exactly 2*10*2 = 40.
         */
        @ParameterizedTest
        @EnumSource(Block.class)
        void Repeated_struct_overrides_fill_exactly_the_array_dimension(Block block)
                throws Exception {
            String initializer = String.join("\n",
                    "[",
                    "    20 ((gamma := 1.0)),",
                    "        (gamma := 2.0),",
                    "    10 ((gamma := 3.0)),",
                    "     7 ((gamma := 4.0)),",
                    "     2 ((gamma := 5.0))",
                    "]");
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(COLOR_TYPE)
                    .var(block, "pixels : ARRAY [0..1, 1..10, 3..4] OF color_type := " + initializer));

            RepeatedInitialization expected = repeated(40, colorWithDefaultClassification(".5"));
            expected.put("0..19", colorWithDefaultClassification("1.0"));
            expected.put("20", colorWithDefaultClassification("2.0"));
            expected.put("21..30", colorWithDefaultClassification("3.0"));
            expected.put("31..37", colorWithDefaultClassification("4.0"));
            expected.put("38..39", colorWithDefaultClassification("5.0"));

            assertSymbol(st, SourceCode.key("pixels"),
                    array(Use.VARIABLE, block.source(), Subtype.CUSTOM, list("0", "1", "3"), list("1", "10", "4"))
                            .customType("COLOR_TYPE")
                            .initialValue(expected)
                            .build());
        }

        /**
         * Known inconsistency: inside an array element the override is stored unqualified ("BLACK"), while
         * the same override on a plain struct variable is qualified ("COLOR_TYPE#BLACK"), because the element
         * sub-context does not inherit the search scope. This test states the intended behavior.
         */
        @Test
        @NotYetImplemented
        void Enumerator_override_inside_an_array_element_is_qualified_with_the_struct_type_scope() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(COLOR_TYPE)
                    .var(Block.VAR, "pixels : ARRAY [0..0] OF color_type := [(classification := BLACK)]"));

            RepeatedInitialization expected = repeated(1, colorWithDefaultClassification(".5"));
            expected.put("0", color(new VariableInitialization("COLOR_TYPE#BLACK"), ".5"));

            assertSymbol(st, SourceCode.key("pixels"),
                    array(Use.VARIABLE, Source.INTERNAL, Subtype.CUSTOM, list("0"), list("0"))
                            .customType("COLOR_TYPE")
                            .initialValue(expected)
                            .build());
        }
    }

    @Nested
    class AsStructField {
        @Test
        void Array_field_publishes_its_bounds_and_repeats_the_default() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type("signal_type : STRUCT samples : ARRAY [0..2] OF REAL; END_STRUCT"));

            assertSymbol(st, "SIGNAL_TYPE#SAMPLES", array(Use.FIELD, Source.NONE, Subtype.REAL, list("0"), list("2"))
                    .initialValue(repeated(3, realDefault()))
                    .build());
        }
    }

    @Nested
    class Errors {
        @Test
        @NotYetImplemented
        void Initializer_with_more_elements_than_the_dimension_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock()
                    .var(Block.VAR, "values1 : ARRAY [1..2] OF INT := [1, 2, 3]"));
        }

        @Test
        @NotYetImplemented
        void Initializer_element_of_the_wrong_type_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock()
                    .var(Block.VAR, "values1 : ARRAY [1..2] OF INT := ['a', 'b']"));
        }

        @Test
        @NotYetImplemented
        void Reversed_bounds_are_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock()
                    .var(Block.VAR, "values1 : ARRAY [5..1] OF INT"));
        }
    }
}