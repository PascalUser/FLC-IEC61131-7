package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import parser.initializations.Initialization;
import parser.initializations.leafs.DefaultInitialization;
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

/**
 * Integration tests for STRUCT declarations: types, nested types, variables inheriting field defaults,
 * and field overrides in initializers.
 */
public class StructIT extends ParserTestSupport {

    private static final String COLOR_TYPE = String.join("\n",
            "color_type : STRUCT",
            "    brown : REAL;",
            "    light : REAL;",
            "END_STRUCT");

    private static final String RGB_TYPE = String.join("\n",
            "rgb_type : STRUCT",
            "    gamma_r : REAL;",
            "    gamma_g : REAL;",
            "    gamma_b : REAL;",
            "END_STRUCT");

    private static final String PALETTE_TYPE = String.join("\n",
            "palette_type : STRUCT",
            "    classification : (WHITE, GRAY, BLACK);",
            "    gamma : REAL := 0.5;",
            "END_STRUCT");

    private static StructInitialization struct(Object... namesAndValues) {
        StructInitialization struct = new StructInitialization();
        for (int i = 0; i < namesAndValues.length; i += 2) {
            struct.put((String) namesAndValues[i], (Initialization) namesAndValues[i + 1]);
        }
        return struct;
    }

    private static StructInitialization rgbDefault() {
        return struct("GAMMA_R", realDefault(), "GAMMA_G", realDefault(), "GAMMA_B", realDefault());
    }

    private static LexemeInfoBuilder structType(String... fields) {
        return new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList(fields));
    }

    private static LexemeInfoBuilder field(Subtype subtype) {
        return new LexemeInfoBuilder().type(Type.SIMPLE).subtype(subtype).use(Use.FIELD).source(Source.NONE);
    }

    private static LexemeInfoBuilder variableOf(String typeName, Block block) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType(typeName)
                .use(Use.VARIABLE)
                .source(block.source());
    }

    @Nested
    class AsTypeDeclaration {
        @Test
        void Struct_type_publishes_each_field_with_its_default() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock().type(COLOR_TYPE));

            assertSymbol(st, "COLOR_TYPE", structType("BROWN", "LIGHT")
                    .initialValue(struct("BROWN", realDefault(), "LIGHT", realDefault()))
                    .build());
            assertSymbol(st, "COLOR_TYPE#BROWN",
                    field(Subtype.REAL).initialValue(realDefault()).build());
            assertSymbol(st, "COLOR_TYPE#LIGHT",
                    field(Subtype.REAL).initialValue(realDefault()).build());
        }

        @Test
        void Struct_field_defaults_are_the_first_enumerator_or_the_declared_literal()
                throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock().type(PALETTE_TYPE));

            assertSymbol(st, "PALETTE_TYPE", structType("CLASSIFICATION", "GAMMA")
                    .initialValue(struct(
                            "CLASSIFICATION", new EnumeratedInitialization(Arrays.asList("WHITE", "GRAY", "BLACK")),
                            "GAMMA", new VariableInitialization(".5")))
                    .build());
            assertSymbol(st, "PALETTE_TYPE#GAMMA",
                    field(Subtype.REAL).initialValue(new VariableInitialization(".5")).build());
        }

        @Test
        void Nested_struct_type_publishes_the_whole_hierarchy_of_fields() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(RGB_TYPE)
                    .type("color_type : STRUCT white : BOOL; rgb : rgb_type; END_STRUCT"));

            assertSymbol(st, "RGB_TYPE", structType("GAMMA_R", "GAMMA_G", "GAMMA_B")
                    .initialValue(rgbDefault())
                    .build());
            assertSymbol(st, "COLOR_TYPE", structType("WHITE", "RGB")
                    .initialValue(struct("WHITE", boolDefault(), "RGB", rgbDefault()))
                    .build());
            assertSymbol(st, "COLOR_TYPE#WHITE",
                    field(Subtype.BOOL).initialValue(boolDefault()).build());
            assertSymbol(st, "COLOR_TYPE#RGB",
                    field(Subtype.CUSTOM).customType("RGB_TYPE").initialValue(rgbDefault()).build());
        }

        @Test
        void Override_on_a_nested_struct_field_applies_only_inside_the_enclosing_type() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(RGB_TYPE)
                    .type("color_type : STRUCT white : BOOL; rgb : rgb_type := (gamma_g := 3.0); END_STRUCT"));

            StructInitialization colorDefault = struct("WHITE", boolDefault(), "RGB", rgbDefault().copy());
            colorDefault.put("RGB#GAMMA_G", new VariableInitialization("3.0"));

            assertSymbol(st, "RGB_TYPE", structType("GAMMA_R", "GAMMA_G", "GAMMA_B")
                    .initialValue(rgbDefault())
                    .build());
            assertSymbol(st, "COLOR_TYPE", structType("WHITE", "RGB")
                    .initialValue(colorDefault)
                    .build());
        }
    }

    @Nested
    class AsVariable {
        @ParameterizedTest
        @EnumSource(Block.class)
        void Variable_of_a_struct_type_inherits_the_field_defaults(Block block) throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(COLOR_TYPE)
                    .var(block, "color1 : color_type"));

            assertSymbol(st, SourceCode.key("color1"), variableOf("COLOR_TYPE", block)
                    .initialValue(struct("BROWN", realDefault(), "LIGHT", realDefault()))
                    .build());
        }

        @ParameterizedTest
        @EnumSource(Block.class)
        void Overriding_a_struct_field_leaves_the_type_defaults_untouched(Block block)
                throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(COLOR_TYPE)
                    .var(block, "color1 : color_type := (light := 1.0)"));

            assertSymbol(st, SourceCode.key("color1"), variableOf("COLOR_TYPE", block)
                    .initialValue(struct("BROWN", realDefault(), "LIGHT", new VariableInitialization("1.0")))
                    .build());
            assertSymbol(st, "COLOR_TYPE", structType("BROWN", "LIGHT")
                    .initialValue(struct("BROWN", realDefault(), "LIGHT", realDefault()))
                    .build());
        }

        @Test
        void Enumerator_override_of_anonymous_enum_is_not_qualified() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(PALETTE_TYPE)
                    .var(Block.VAR, "pixel : palette_type := (classification := BLACK)"));

            assertSymbol(st, SourceCode.key("pixel"), variableOf("PALETTE_TYPE", Block.VAR)
                    .initialValue(struct(
                            "CLASSIFICATION", new VariableInitialization("BLACK"), // Se guarda sin cualificar
                            "GAMMA", new VariableInitialization(".5")))
                    .build());
        }

        @Test
        void Variable_of_a_nested_struct_type_inherits_the_whole_hierarchy_of_defaults() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(RGB_TYPE)
                    .type("color_type : STRUCT white : BOOL; rgb : rgb_type; END_STRUCT")
                    .var(Block.VAR_INPUT, "color1 : color_type"));

            assertSymbol(st, SourceCode.key("color1"), variableOf("COLOR_TYPE", Block.VAR_INPUT)
                    .initialValue(struct("WHITE", boolDefault(), "RGB", rgbDefault()))
                    .build());
        }

        @Test
        void Deep_override_combines_the_type_override_and_the_variable_override()
                throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(RGB_TYPE)
                    .type("color_type : STRUCT white : BOOL; rgb : rgb_type := (gamma_g := 3.0); END_STRUCT")
                    .var(Block.VAR_INPUT, "color1 : color_type := (white := TRUE, rgb := (gamma_r := 10.2))"));

            StructInitialization expectedRgb = struct(
                    "GAMMA_R", new VariableInitialization("10.2"),
                    "GAMMA_G", new VariableInitialization("3.0"),
                    "GAMMA_B", realDefault());

            assertSymbol(st, SourceCode.key("color1"), variableOf("COLOR_TYPE", Block.VAR_INPUT)
                    .initialValue(struct("WHITE", new VariableInitialization("TRUE"), "RGB", expectedRgb))
                    .build());
            // The override in the variable must not leak into the type it was copied from.
            assertSymbol(st, "RGB_TYPE", structType("GAMMA_R", "GAMMA_G", "GAMMA_B")
                    .initialValue(rgbDefault())
                    .build());
        }

        /**
         * Known gap: {@code initialized_field_with_array} only pops the nested scope and never writes the
         * array into the struct, so the override is silently dropped.
         */
        @Test
        @NotYetImplemented
        void Array_field_override_is_stored_in_the_struct_field() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type("signal_type : STRUCT samples : ARRAY [0..1] OF INT; END_STRUCT")
                    .var(Block.VAR, "signal1 : signal_type := (samples := [5, 6])"));

            RepeatedInitialization samples = new RepeatedInitialization(2, DefaultInitialization.integer(st));
            samples.put("0..0", new VariableInitialization("5"));
            samples.put("1..1", new VariableInitialization("6"));
            assertSymbol(st, SourceCode.key("signal1"), variableOf("SIGNAL_TYPE", Block.VAR)
                    .initialValue(struct("SAMPLES", samples))
                    .build());
        }
    }

    @Nested
    class Errors {
        @Test
        @NotYetImplemented
        void Duplicate_struct_field_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock()
                    .type("item_type : STRUCT qty : INT; qty : REAL; END_STRUCT"));
        }

        @Test
        @NotYetImplemented
        void Override_of_an_unknown_field_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock()
                    .type("item_type : STRUCT qty : INT; END_STRUCT")
                    .var(Block.VAR, "item1 : item_type := (price := 1)"));
        }

        @Test
        @NotYetImplemented
        void Override_with_a_value_of_the_wrong_type_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock()
                    .type("item_type : STRUCT qty : INT; END_STRUCT")
                    .var(Block.VAR, "item1 : item_type := (qty := 'text')"));
        }
    }
}
