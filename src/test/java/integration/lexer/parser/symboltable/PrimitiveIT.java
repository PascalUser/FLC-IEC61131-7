package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import parser.initializations.Initialization;
import parser.initializations.leafs.VariableInitialization;
import utils.*;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for variables of primitive types (BOOL, integers, reals, STRING, WSTRING) declared in
 * every variable block, with and without initializer.
 * <p>
 * Default values are asserted as literal text ("0.0", "FALSE", ...), not rebuilt with the production
 * factory, so a wrong default cannot hide behind the same bug in the expectation.
 * </p>
 */
public class PrimitiveIT extends ParserTestSupport {

    /** One primitive type with its textual default and an initializer literal. */
    static final class Primitive {
        final String typeName;
        final Subtype subtype;
        final String literal;
        final String storedLiteral;

        Primitive(String typeName, Subtype subtype, String literal, String storedLiteral) {
            this.typeName = typeName;
            this.subtype = subtype;
            this.literal = literal;
            this.storedLiteral = storedLiteral;
        }

        @Override
        public String toString() {
            return typeName;
        }
    }

    private static final List<Primitive> PRIMITIVES = Arrays.asList(
            new Primitive("BOOL", Subtype.BOOL, "TRUE", "TRUE"),
            // The lexer normalizes 0.3e10 to .3e10
            new Primitive("REAL", Subtype.REAL, "0.3e10", ".3e10"),
            new Primitive("LREAL", Subtype.LREAL, "0.3e10", ".3e10"),
            new Primitive("SINT", Subtype.SINT, "42", "42"),
            new Primitive("INT", Subtype.INT, "42", "42"),
            new Primitive("DINT", Subtype.DINT, "42", "42"),
            new Primitive("LINT", Subtype.LINT, "42", "42"),
            new Primitive("USINT", Subtype.USINT, "42", "42"),
            new Primitive("UINT", Subtype.UINT, "42", "42"),
            new Primitive("UDINT", Subtype.UDINT, "42", "42"),
            new Primitive("ULINT", Subtype.ULINT, "42", "42"),
            new Primitive("STRING", Subtype.STRING, "'text'", "'text'"),
            new Primitive("WSTRING", Subtype.WSTRING, "\"text\"", "\"text\"")
    );

    static Stream<Arguments> blocksAndPrimitives() {
        List<Arguments> arguments = new ArrayList<>();
        for (Block block : Block.values()) {
            for (Primitive primitive : PRIMITIVES) {
                arguments.add(Arguments.of(block, primitive));
            }
        }
        return arguments.stream();
    }

    private static LexemeInfoBuilder variable(Subtype subtype, Block block) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.VARIABLE)
                .source(block.source());
    }

    @Nested
    class AsVariable {

        @ParameterizedTest(name = "{0} / {1}")
        @MethodSource("integration.lexer.parser.symboltable.PrimitiveIT#blocksAndPrimitives")
        void Uninitialized_primitive_variable_gets_its_literal_default(Block block, Primitive primitive) throws Exception {
            String symbol = SourceCode.key("var1");
            Initialization expectedValue = defaultValue(primitive.subtype);
            LexemeInfo expectedInfo = variable(primitive.subtype, block)
                    .initialValue(expectedValue)
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .var(block, "var1 : " + primitive.typeName)
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @ParameterizedTest(name = "{0} / {1}")
        @MethodSource("integration.lexer.parser.symboltable.PrimitiveIT#blocksAndPrimitives")
        void Initialized_primitive_variable_keeps_the_normalized_literal(Block block, Primitive primitive) throws Exception {
            String symbol = SourceCode.key("var1");
            LexemeInfo expectedInfo = variable(primitive.subtype, block)
                    .initialValue(new VariableInitialization(primitive.storedLiteral))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .var(block, "var1 : " + primitive.typeName + " := " + primitive.literal)
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @Test
        void Every_identifier_in_a_list_is_published_with_the_same_metadata() throws Exception {
            LexemeInfo expectedInfo = variable(Subtype.INT, Block.VAR)
                    .initialValue(intDefault())
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .var(Block.VAR, "first, second : INT")
            );
            SymbolTable symbolTable = results.symbolTable();

            for (String identifier : new String[]{"first", "second"}) {
                String symbol = SourceCode.key(identifier);
                assertSymbol(symbolTable, symbol, expectedInfo);
            }
        }

        @Test
        void Variables_take_the_source_of_the_block_they_are_declared_in() throws Exception {
            String symbolIn1 = SourceCode.key("in1");
            LexemeInfo expectedInfoIn1 = variable(Subtype.INT, Block.VAR_INPUT)
                    .initialValue(intDefault())
                    .build();

            String symbolOut1 = SourceCode.key("out1");
            LexemeInfo expectedInfoOut1 = variable(Subtype.REAL, Block.VAR_OUTPUT)
                    .initialValue(realDefault())
                    .build();

            String symbolLocal1 = SourceCode.key("local1");
            LexemeInfo expectedInfoLocal1 = variable(Subtype.BOOL, Block.VAR)
                    .initialValue(boolDefault())
                    .build();

            String symbolLocal2 = SourceCode.key("local2");
            LexemeInfo expectedInfoLocal2 = variable(Subtype.BOOL, Block.VAR)
                    .initialValue(boolDefault())
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .var(Block.VAR_INPUT, "in1 : INT")
                            .var(Block.VAR_OUTPUT, "out1 : REAL")
                            .var(Block.VAR, "local1 : BOOL")
                            .var(Block.VAR, "local2 : BOOL")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbolIn1, expectedInfoIn1);
            assertSymbol(symbolTable, symbolOut1, expectedInfoOut1);
            assertSymbol(symbolTable, symbolLocal1, expectedInfoLocal1);
            assertSymbol(symbolTable, symbolLocal2, expectedInfoLocal2);
        }

        /**
         * Known gap: {@code Factory.createPrimitiveInitialization} only supports BOOL, REAL, LREAL, STRING, WSTRING
         * and integers, so declaring a TIME/DATE/bit-string variable throws IllegalArgumentException.
         */
        @ParameterizedTest
        @EnumSource(value = Subtype.class, names = {
                "TIME", "DATE", "TIME_OF_DAY", "DATE_AND_TIME", "BYTE", "WORD", "DWORD", "LWORD"
        })
        @NotYetImplemented
        void Temporal_and_bit_string_variables_are_published_with_their_subtype(Subtype subtype) throws Exception {
            String symbol = SourceCode.key("var1");

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .var(Block.VAR, "var1 : " + subtype.name())
            );
            SymbolTable symbolTable = results.symbolTable();

            LexemeInfo info = symbolTable.get(symbol);
            assertNotNull(info);
            assertEquals(subtype, info.subtype);
        }
    }

    @Nested
    class Errors {

        @Test
        @NotYetImplemented
        void Real_variable_initialized_with_a_string_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .var(Block.VAR, "var1 : REAL := 'text'")
            );
        }

        /** Already enforced by the grammar: {@code initialized_boolean} only accepts a boolean constant. */
        @Test
        void Bool_variable_initialized_with_an_integer_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .var(Block.VAR, "var1 : BOOL := 42")
            );
        }
    }
}