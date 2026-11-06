package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import compiler.parser.initializations.leafs.VariableInitialization;
import compiler.repository.LexemeInfo;
import utils.supports.ParserTestSupport;
import utils.ParsingResults;
import utils.builders.SourceCode;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoBuilder;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for OPTION blocks. Each {@code PRAGMA} is published under
 * {@code <function block>@option#<NAME>} with the key of its value literal as initial value; a pragma
 * without value is a flag equal to {@code TRUE}.
 */
public class OptionIT extends ParserTestSupport {

    private static String pragmaKey(String name) {
        return "MAIN@option#" + name.toUpperCase();
    }

    private static LexemeInfo pragma(Subtype subtype, String value) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.PRAGMA)
                .source(Source.NONE)
                .initialValue(new VariableInitialization(value))
                .build();
    }

    @Nested
    class Declarations {

        @Test
        void Pragma_with_a_value_is_published_with_its_literal() throws Exception {
            String symbol = pragmaKey("bit_width");
            LexemeInfo expectedInfo = pragma(Subtype.USINT, "16");

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body("OPTION PRAGMA bit_width 16; END_OPTION")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        @Test
        void Pragma_without_a_value_is_a_true_flag() throws Exception {
            String symbol = pragmaKey("unroll");
            LexemeInfo expectedInfo = pragma(Subtype.BOOL, "TRUE");

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body("OPTION PRAGMA unroll; END_OPTION")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
            assertEquals(Boolean.TRUE, symbolTable.get("TRUE").initialValue, "The TRUE literal is registered");
        }

        @Test
        void Every_pragma_of_every_option_block_is_published() throws Exception {
            String symbolBitWidth = pragmaKey("bit_width");
            LexemeInfo expectedBitWidth = pragma(Subtype.USINT, "8");

            String symbolUnroll = pragmaKey("unroll");
            LexemeInfo expectedUnroll = pragma(Subtype.BOOL, "TRUE");

            String symbolDepth = pragmaKey("depth");
            LexemeInfo expectedDepth = pragma(Subtype.USINT, "3");

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body("OPTION PRAGMA bit_width 8; PRAGMA unroll; END_OPTION")
                            .body("OPTION PRAGMA depth 3; END_OPTION")
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbolBitWidth, expectedBitWidth);
            assertSymbol(symbolTable, symbolUnroll, expectedUnroll);
            assertSymbol(symbolTable, symbolDepth, expectedDepth);
        }
    }
}