package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import parser.initializations.Initialization;
import parser.initializations.leafs.EnumeratedInitialization;
import parser.initializations.leafs.MacroInitialization;
import parser.initializations.leafs.VariableInitialization;
import utils.*;
import utils.ParsingResults;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Arrays;
import java.util.List;

/**
 * Integration tests for ENUMERATED declarations: named types, inline variables and struct members.
 * Every enumerator is also published as a MACRO symbol holding its ordinal; the default value of an
 * enumeration is its first enumerator.
 */
public class EnumerateIT extends ParserTestSupport {

    private static final String METHOD_TYPE = "MethodType : (CENTROID, AVERAGE)";
    private static final List<String> METHODS = Arrays.asList("CENTROID", "AVERAGE");

    /** Built on a throw-away table so it does not register literals in the table under test. */
    private static Initialization ordinal(int value) {
        return new MacroInitialization(new SymbolTable(), value);
    }

    private static LexemeInfoBuilder macro(int value) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(ordinal(value));
    }

    private static LexemeInfoBuilder enumeration(Use use, Source source, List<String> enumerators) {
        return new LexemeInfoBuilder()
                .type(Type.ENUMERATE)
                .subtype(Subtype.INT)
                .use(use)
                .source(source)
                .parameters(enumerators);
    }

    private static LexemeInfoBuilder variableOfMethodType(Block block) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("METHODTYPE")
                .use(Use.VARIABLE)
                .source(block.source());
    }

    @Nested
    class AsTypeDeclaration {
        @Test
        void Enum_type_publishes_one_macro_per_enumerator() throws Exception {
            String enumSymbol = "METHODTYPE";
            String centroidSymbol = "METHODTYPE#CENTROID";
            String averageSymbol = "METHODTYPE#AVERAGE";
            LexemeInfo enumExpectedInfo = enumeration(Use.TYPE, Source.NONE, METHODS)
                    .initialValue(new EnumeratedInitialization(METHODS))
                    .build();
            LexemeInfo centroidExpectedInfo = macro(0).build();
            LexemeInfo averageExpectedInfo = macro(1).build();


            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type(METHOD_TYPE)
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, enumSymbol, enumExpectedInfo);
            assertSymbol(symbolTable, centroidSymbol, centroidExpectedInfo);
            assertSymbol(symbolTable, averageSymbol, averageExpectedInfo);
        }
    }

    @Nested
    class AsVariable {
        @ParameterizedTest
        @EnumSource(Block.class)
        void Variable_of_an_enum_type_defaults_to_the_first_enumerator(Block block) throws Exception {
            String symbol = SourceCode.key("method1");
            LexemeInfo expectedInfo = variableOfMethodType(block)
                    .initialValue(new EnumeratedInitialization(METHODS))
                    .build();

            ParsingResults results = parse(SourceCode.functionBlock()
                    .type(METHOD_TYPE)
                    .var(block, "method1 : MethodType"));
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @ParameterizedTest
        @EnumSource(Block.class)
        void Enum_variable_initializer_is_stored_as_the_qualified_enumerator(Block block) throws Exception {
            String symbol = SourceCode.key("method1");
            LexemeInfo expectedInfo = variableOfMethodType(block)
                    .initialValue(new VariableInitialization("METHODTYPE#AVERAGE"))
                    .build();

            ParsingResults results = parse(SourceCode.functionBlock()
                    .type(METHOD_TYPE)
                    .var(block, "method1 : MethodType := AVERAGE")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @Test
        void Already_qualified_enumerator_keeps_its_qualification() throws Exception {
            String symbol = SourceCode.key("method1");
            LexemeInfo expectedInfo = variableOfMethodType(Block.VAR)
                    .initialValue(new VariableInitialization("METHODTYPE#AVERAGE"))
                    .build();

            ParsingResults results = parse(SourceCode.functionBlock()
                    .type(METHOD_TYPE)
                    .var(Block.VAR, "method1 : MethodType := MethodType#AVERAGE"));
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @ParameterizedTest
        @EnumSource(Block.class)
        void Inline_enum_publishes_its_macros_in_the_function_block_scope(Block block) throws Exception {
            String enumSymbol = SourceCode.key("method1");
            String centroidSymbol = SourceCode.key("CENTROID");
            String averageSymbol = SourceCode.key("AVERAGE");
            LexemeInfo enumExpectedInfo = enumeration(Use.VARIABLE, block.source(), METHODS)
                    .initialValue(new VariableInitialization("MAIN#CENTROID"))
                    .build();
            LexemeInfo centroidExpectedInfo = macro(0).build();
            LexemeInfo averageExpectedInfo = macro(1).build();

            ParsingResults results = parse(SourceCode.functionBlock()
                    .var(block, "method1 : (CENTROID, AVERAGE) := CENTROID"));
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, enumSymbol, enumExpectedInfo);
            assertSymbol(symbolTable, centroidSymbol, centroidExpectedInfo);
            assertSymbol(symbolTable, averageSymbol, averageExpectedInfo);
        }

        @Test
        void Inline_enum_without_initializer_defaults_to_the_first_enumerator() throws Exception {
            String symbol = SourceCode.key("method1");
            LexemeInfo expectedInfo = enumeration(Use.VARIABLE, Source.INTERNAL, METHODS)
                    .initialValue(new EnumeratedInitialization(METHODS))
                    .build();

            ParsingResults results = parse(SourceCode.functionBlock()
                    .var(Block.VAR, "method1 : (CENTROID, AVERAGE)"));
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }
    }

    @Nested
    class AsStructField {
        @Test
        void Inline_enum_field_publishes_its_macros_in_the_struct_scope() throws Exception {
            String enumSymbol  = "COLOR_TYPE#CLASSIFICATION";
            String whiteSymbol = "COLOR_TYPE#WHITE";
            String graySymbol  = "COLOR_TYPE#GRAY";
            String blackSymbol = "COLOR_TYPE#BLACK";
            List<String> colors = Arrays.asList("WHITE", "GRAY", "BLACK");
            LexemeInfo enumExpectedInfo = enumeration(Use.FIELD, Source.NONE, colors)
                    .initialValue(new EnumeratedInitialization(colors))
                    .build();
            LexemeInfo whiteExpectedInfo = macro(0).build();
            LexemeInfo grayExpectedInfo  = macro(1).build();
            LexemeInfo blackExpectedInfo = macro(2).build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                        .type("color_type : STRUCT classification : (WHITE, GRAY, BLACK); END_STRUCT")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, enumSymbol,  enumExpectedInfo);
            assertSymbol(symbolTable, whiteSymbol, whiteExpectedInfo);
            assertSymbol(symbolTable, graySymbol,  grayExpectedInfo);
            assertSymbol(symbolTable, blackSymbol, blackExpectedInfo);
        }
    }

    @Nested
    class Errors {
        @Test
        @NotYetImplemented
        void Duplicate_enumerator_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .type("dup_type : (A1, A1)")
            );
        }

        @Test
        @NotYetImplemented
        void Undefined_enumerator_as_initializer_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                        .type("pair_type : (A1, B1)")
                        .var(Block.VAR, "v1 : pair_type := C1")
            );
        }

        @Test
        @NotYetImplemented
        void Enum_variable_initialized_with_a_number_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                        .type("pair_type : (A1, B1)")
                        .var(Block.VAR, "v1 : pair_type := 42")
            );
        }
    }
}
