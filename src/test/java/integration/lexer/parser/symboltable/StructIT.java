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
import utils.LexemeInfo;
import utils.ParserTestSupport;
import utils.ParsingResults;
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
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.FIELD)
                .source(Source.NONE);
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
            String symbolType = "COLOR_TYPE";
            LexemeInfo expectedType = structType("BROWN", "LIGHT")
                    .initialValue(struct("BROWN", realDefault(), "LIGHT", realDefault()))
                    .build();

            String symbolBrown = "COLOR_TYPE#BROWN";
            LexemeInfo expectedBrown = field(Subtype.REAL)
                    .initialValue(realDefault())
                    .build();

            String symbolLight = "COLOR_TYPE#LIGHT";
            LexemeInfo expectedLight = field(Subtype.REAL)
                    .initialValue(realDefault())
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(COLOR_TYPE)
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbolType, expectedType);
            assertSymbol(symbolTable, symbolBrown, expectedBrown);
            assertSymbol(symbolTable, symbolLight, expectedLight);
        }

        @Test
        void Struct_field_defaults_are_the_first_enumerator_or_the_declared_literal() throws Exception {
            String symbolType = "PALETTE_TYPE";
            LexemeInfo expectedType = structType("CLASSIFICATION", "GAMMA")
                    .initialValue(struct(
                            "CLASSIFICATION", new EnumeratedInitialization(Arrays.asList("WHITE", "GRAY", "BLACK")),
                            "GAMMA", new VariableInitialization(".5")))
                    .build();

            String symbolGamma = "PALETTE_TYPE#GAMMA";
            LexemeInfo expectedGamma = field(Subtype.REAL)
                    .initialValue(new VariableInitialization(".5"))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(PALETTE_TYPE)
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbolType, expectedType);
            assertSymbol(symbolTable, symbolGamma, expectedGamma);
        }

        @Test
        void Nested_struct_type_publishes_the_whole_hierarchy_of_fields() throws Exception {
            String symbolRgbType = "RGB_TYPE";
            LexemeInfo expectedRgbType = structType("GAMMA_R", "GAMMA_G", "GAMMA_B")
                    .initialValue(rgbDefault())
                    .build();

            String symbolColorType = "COLOR_TYPE";
            LexemeInfo expectedColorType = structType("WHITE", "RGB")
                    .initialValue(struct("WHITE", boolDefault(), "RGB", rgbDefault()))
                    .build();

            String symbolWhite = "COLOR_TYPE#WHITE";
            LexemeInfo expectedWhite = field(Subtype.BOOL)
                    .initialValue(boolDefault())
                    .build();

            String symbolRgb = "COLOR_TYPE#RGB";
            LexemeInfo expectedRgb = field(Subtype.CUSTOM)
                    .customType("RGB_TYPE")
                    .initialValue(rgbDefault())
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(RGB_TYPE)
                            .type("color_type : STRUCT white : BOOL; rgb : rgb_type; END_STRUCT")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbolRgbType, expectedRgbType);
            assertSymbol(symbolTable, symbolColorType, expectedColorType);
            assertSymbol(symbolTable, symbolWhite, expectedWhite);
            assertSymbol(symbolTable, symbolRgb, expectedRgb);
        }

        @Test
        void Override_on_a_nested_struct_field_applies_only_inside_the_enclosing_type() throws Exception {
            StructInitialization colorDefault = struct("WHITE", boolDefault(), "RGB", rgbDefault().copy());
            colorDefault.put("RGB#GAMMA_G", new VariableInitialization("3.0"));

            String symbolRgbType = "RGB_TYPE";
            LexemeInfo expectedRgbType = structType("GAMMA_R", "GAMMA_G", "GAMMA_B")
                    .initialValue(rgbDefault())
                    .build();

            String symbolColorType = "COLOR_TYPE";
            LexemeInfo expectedColorType = structType("WHITE", "RGB")
                    .initialValue(colorDefault)
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(RGB_TYPE)
                            .type("color_type : STRUCT white : BOOL; rgb : rgb_type := (gamma_g := 3.0); END_STRUCT")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbolRgbType, expectedRgbType);
            assertSymbol(symbolTable, symbolColorType, expectedColorType);
        }
    }

    @Nested
    class AsVariable {

        @ParameterizedTest
        @EnumSource(Block.class)
        void Variable_of_a_struct_type_inherits_the_field_defaults(Block block) throws Exception {
            String symbol = SourceCode.key("color1");
            LexemeInfo expectedInfo = variableOf("COLOR_TYPE", block)
                    .initialValue(struct("BROWN", realDefault(), "LIGHT", realDefault()))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(COLOR_TYPE)
                            .var(block, "color1 : color_type")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @ParameterizedTest
        @EnumSource(Block.class)
        void Overriding_a_struct_field_leaves_the_type_defaults_untouched(Block block) throws Exception {
            String symbolColor1 = SourceCode.key("color1");
            LexemeInfo expectedColor1 = variableOf("COLOR_TYPE", block)
                    .initialValue(struct("BROWN", realDefault(), "LIGHT", new VariableInitialization("1.0")))
                    .build();

            String symbolType = "COLOR_TYPE";
            LexemeInfo expectedType = structType("BROWN", "LIGHT")
                    .initialValue(struct("BROWN", realDefault(), "LIGHT", realDefault()))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(COLOR_TYPE)
                            .var(block, "color1 : color_type := (light := 1.0)")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbolColor1, expectedColor1);
            assertSymbol(symbolTable, symbolType, expectedType);
        }

        @Test
        void Enumerator_override_of_anonymous_enum_is_not_qualified() throws Exception {
            String symbol = SourceCode.key("pixel");
            LexemeInfo expectedInfo = variableOf("PALETTE_TYPE", Block.VAR)
                    .initialValue(struct(
                            "CLASSIFICATION", new VariableInitialization("BLACK"),
                            "GAMMA", new VariableInitialization(".5")))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(PALETTE_TYPE)
                            .var(Block.VAR, "pixel : palette_type := (classification := BLACK)")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @Test
        void Variable_of_a_nested_struct_type_inherits_the_whole_hierarchy_of_defaults() throws Exception {
            String symbol = SourceCode.key("color1");
            LexemeInfo expectedInfo = variableOf("COLOR_TYPE", Block.VAR_INPUT)
                    .initialValue(struct("WHITE", boolDefault(), "RGB", rgbDefault()))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(RGB_TYPE)
                            .type("color_type : STRUCT white : BOOL; rgb : rgb_type; END_STRUCT")
                            .var(Block.VAR_INPUT, "color1 : color_type")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @Test
        void Deep_override_combines_the_type_override_and_the_variable_override() throws Exception {
            StructInitialization expectedRgb = struct(
                    "GAMMA_R", new VariableInitialization("10.2"),
                    "GAMMA_G", new VariableInitialization("3.0"),
                    "GAMMA_B", realDefault());

            String symbolColor1 = SourceCode.key("color1");
            LexemeInfo expectedColor1 = variableOf("COLOR_TYPE", Block.VAR_INPUT)
                    .initialValue(struct("WHITE", new VariableInitialization("TRUE"), "RGB", expectedRgb))
                    .build();

            String symbolRgbType = "RGB_TYPE";
            LexemeInfo expectedRgbType = structType("GAMMA_R", "GAMMA_G", "GAMMA_B")
                    .initialValue(rgbDefault())
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(RGB_TYPE)
                            .type("color_type : STRUCT white : BOOL; rgb : rgb_type := (gamma_g := 3.0); END_STRUCT")
                            .var(Block.VAR_INPUT, "color1 : color_type := (white := TRUE, rgb := (gamma_r := 10.2))")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbolColor1, expectedColor1);
            assertSymbol(symbolTable, symbolRgbType, expectedRgbType);
        }

        /**
         * Known gap: {@code initialized_field_with_array} only pops the nested scope and never writes the
         * array into the struct, so the override is silently dropped.
         */
        @Test
        @NotYetImplemented
        void Array_field_override_is_stored_in_the_struct_field() throws Exception {
            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type("signal_type : STRUCT samples : ARRAY [0..1] OF INT; END_STRUCT")
                            .var(Block.VAR, "signal1 : signal_type := (samples := [5, 6])")
            );
            SymbolTable symbolTable = results.symbolTable();

            RepeatedInitialization samples = new RepeatedInitialization(2, DefaultInitialization.integer(symbolTable));
            samples.put("0..0", new VariableInitialization("5"));
            samples.put("1..1", new VariableInitialization("6"));

            String symbol = SourceCode.key("signal1");
            LexemeInfo expectedInfo = variableOf("SIGNAL_TYPE", Block.VAR)
                    .initialValue(struct("SAMPLES", samples))
                    .build();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }
    }

    @Nested
    class Errors {

        @Test
        @NotYetImplemented
        void Duplicate_struct_field_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .type("item_type : STRUCT qty : INT; qty : REAL; END_STRUCT")
            );
        }

        @Test
        @NotYetImplemented
        void Override_of_an_unknown_field_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .type("item_type : STRUCT qty : INT; END_STRUCT")
                            .var(Block.VAR, "item1 : item_type := (price := 1)")
            );
        }

        @Test
        @NotYetImplemented
        void Override_with_a_value_of_the_wrong_type_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .type("item_type : STRUCT qty : INT; END_STRUCT")
                            .var(Block.VAR, "item1 : item_type := (qty := 'text')")
            );
        }
    }
}