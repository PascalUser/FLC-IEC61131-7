package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import parser.initializations.leafs.VariableInitialization;
import utils.*;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Collections;
import java.util.List;

/**
 * Integration tests for STRING and WSTRING, with and without declared length, as type, variable and
 * struct member.
 */
public class StringIT extends ParserTestSupport {
    private static final List<String> LENGTH_8 = Collections.singletonList("8");

    private static String emptyLiteral(Subtype subtype) {
        return subtype == Subtype.STRING ? "''" : "\"\"";
    }

    private static String literal(Subtype subtype) {
        return subtype == Subtype.STRING ? "'text'" : "\"text\"";
    }

    private static LexemeInfoBuilder stringType(Subtype subtype, List<String> length) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.TYPE)
                .source(Source.NONE)
                .inferiorLimits((length == null) ? null : Collections.singletonList("0"))
                .superiorLimits(length);
    }

    private static LexemeInfoBuilder stringVariable(Subtype subtype, Block block) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.VARIABLE)
                .source(block.source())
                .inferiorLimits(Collections.singletonList("0"))
                .superiorLimits(LENGTH_8);
    }

    @Nested
    class AsTypeDeclaration {

        @ParameterizedTest
        @EnumSource(value = Subtype.class, names = {"STRING", "WSTRING"})
        void Fixed_length_string_type_publishes_its_length_and_defaults_to_empty(Subtype subtype) throws Exception {
            String symbol = "TEXT_TYPE";
            LexemeInfo expectedInfo = stringType(subtype, LENGTH_8)
                    .initialValue(new VariableInitialization(emptyLiteral(subtype)))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type("text_type : " + subtype.name() + " [ 8 ]")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @ParameterizedTest
        @EnumSource(value = Subtype.class, names = {"STRING", "WSTRING"})
        void String_type_without_length_has_no_limit(Subtype subtype) throws Exception {
            String symbol = "TEXT_TYPE";
            LexemeInfo expectedInfo = stringType(subtype, null)
                    .initialValue(new VariableInitialization(emptyLiteral(subtype)))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type("text_type : " + subtype.name())
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @ParameterizedTest
        @EnumSource(value = Subtype.class, names = {"STRING", "WSTRING"})
        void Initialized_string_type_stores_the_literal(Subtype subtype) throws Exception {
            String symbol = "TEXT_TYPE";
            LexemeInfo expectedInfo = stringType(subtype, LENGTH_8)
                    .initialValue(new VariableInitialization(literal(subtype)))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .type("text_type : " + subtype.name() + "[8] := " + literal(subtype))
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }
    }

    @Nested
    class AsVariable {

        @ParameterizedTest
        @EnumSource(Block.class)
        void Fixed_length_string_variable_publishes_its_length_and_defaults_to_empty_in_every_block(Block block) throws Exception {
            String symbol = SourceCode.key("text1");
            LexemeInfo expectedInfo = stringVariable(Subtype.STRING, block)
                    .initialValue(new VariableInitialization("''"))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .var(block, "text1 : STRING[8]")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @ParameterizedTest
        @EnumSource(value = Subtype.class, names = {"STRING", "WSTRING"})
        void Initialized_string_variable_stores_the_literal(Subtype subtype) throws Exception {
            String symbol = SourceCode.key("text1");
            LexemeInfo expectedInfo = stringVariable(subtype, Block.VAR)
                    .initialValue(new VariableInitialization(literal(subtype)))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .var(Block.VAR, "text1 : " + subtype.name() + "[8] := " + literal(subtype))
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }
    }

    @Nested
    class Errors {

        @Test
        @NotYetImplemented
        void Literal_longer_than_the_declared_length_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .var(Block.VAR, "text1 : STRING[5] := 'toolong'")
            );
        }

        /** Already enforced by the grammar: {@code initialized_string} only accepts a string constant. */
        @Test
        void String_initialized_with_an_integer_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .var(Block.VAR, "text1 : STRING[10] := 42")
            );
        }
    }
}