package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import parser.initializations.Initialization;
import parser.initializations.leafs.EnumeratedInitialization;
import parser.initializations.leafs.MacroInitialization;
import parser.initializations.leafs.VariableInitialization;
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
            SymbolTable st = parse(SourceCode.functionBlock().type(METHOD_TYPE));

            assertSymbol(st, "METHODTYPE", enumeration(Use.TYPE, Source.NONE, METHODS)
                    .initialValue(new EnumeratedInitialization(METHODS))
                    .build());
            assertSymbol(st, "METHODTYPE#CENTROID", macro(0).build());
            assertSymbol(st, "METHODTYPE#AVERAGE", macro(1).build());
        }
    }

    @Nested
    class AsVariable {
        @ParameterizedTest
        @EnumSource(Block.class)
        void Variable_of_an_enum_type_defaults_to_the_first_enumerator(Block block) throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(METHOD_TYPE)
                    .var(block, "method1 : MethodType"));

            assertSymbol(st, SourceCode.key("method1"), variableOfMethodType(block)
                    .initialValue(new EnumeratedInitialization(METHODS))
                    .build());
        }

        @ParameterizedTest
        @EnumSource(Block.class)
        void Enum_variable_initializer_is_stored_as_the_qualified_enumerator(Block block) throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(METHOD_TYPE)
                    .var(block, "method1 : MethodType := AVERAGE"));

            assertSymbol(st, SourceCode.key("method1"), variableOfMethodType(block)
                    .initialValue(new VariableInitialization("METHODTYPE#AVERAGE"))
                    .build());
        }

        @Test
        void Already_qualified_enumerator_keeps_its_qualification() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(METHOD_TYPE)
                    .var(Block.VAR, "method1 : MethodType := MethodType#AVERAGE"));

            assertSymbol(st, SourceCode.key("method1"), variableOfMethodType(Block.VAR)
                    .initialValue(new VariableInitialization("METHODTYPE#AVERAGE"))
                    .build());
        }

        @ParameterizedTest
        @EnumSource(Block.class)
        void Inline_enum_publishes_its_macros_in_the_function_block_scope(Block block) throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .var(block, "method1 : (CENTROID, AVERAGE) := CENTROID"));

            assertSymbol(st, SourceCode.key("method1"), enumeration(Use.VARIABLE, block.source(), METHODS)
                    .initialValue(new VariableInitialization("MAIN#CENTROID"))
                    .build());
            assertSymbol(st, "MAIN#CENTROID", macro(0).build());
            assertSymbol(st, "MAIN#AVERAGE", macro(1).build());
        }

        @Test
        void Inline_enum_without_initializer_defaults_to_the_first_enumerator() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .var(Block.VAR, "method1 : (CENTROID, AVERAGE)"));

            assertSymbol(st, SourceCode.key("method1"), enumeration(Use.VARIABLE, Source.INTERNAL, METHODS)
                    .initialValue(new EnumeratedInitialization(METHODS))
                    .build());
        }
    }

    @Nested
    class AsStructField {
        @Test
        void Inline_enum_field_publishes_its_macros_in_the_struct_scope() throws Exception {
            List<String> colors = Arrays.asList("WHITE", "GRAY", "BLACK");
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type("color_type : STRUCT classification : (WHITE, GRAY, BLACK); END_STRUCT"));

            assertSymbol(st, "COLOR_TYPE#CLASSIFICATION", enumeration(Use.FIELD, Source.NONE, colors)
                    .initialValue(new EnumeratedInitialization(colors))
                    .build());
            assertSymbol(st, "COLOR_TYPE#WHITE", macro(0).build());
            assertSymbol(st, "COLOR_TYPE#GRAY", macro(1).build());
            assertSymbol(st, "COLOR_TYPE#BLACK", macro(2).build());
        }
    }

    @Nested
    class Errors {
        @Test
        @NotYetImplemented
        void Duplicate_enumerator_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock().type("dup_type : (A1, A1)"));
        }

        @Test
        @NotYetImplemented
        void Undefined_enumerator_as_initializer_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock()
                    .type("pair_type : (A1, B1)")
                    .var(Block.VAR, "v1 : pair_type := C1"));
        }

        @Test
        @NotYetImplemented
        void Enum_variable_initialized_with_a_number_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock()
                    .type("pair_type : (A1, B1)")
                    .var(Block.VAR, "v1 : pair_type := 42"));
        }
    }
}
