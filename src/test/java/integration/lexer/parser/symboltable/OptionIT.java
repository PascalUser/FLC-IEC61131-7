package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import parser.initializations.leafs.VariableInitialization;
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

    @Test
    void Pragma_with_a_value_is_published_with_its_literal() throws Exception {
        ParsingResults results = parse(
                SourceCode.functionBlock()
                        .body("OPTION PRAGMA bit_width 16; END_OPTION")
        );

        assertSymbol(results.symbolTable(), pragmaKey("bit_width"), pragma(Subtype.USINT, "16"));
    }

    @Test
    void Pragma_without_a_value_is_a_true_flag() throws Exception {
        ParsingResults results = parse(
                SourceCode.functionBlock()
                        .body("OPTION PRAGMA unroll; END_OPTION")
        );
        SymbolTable symbolTable = results.symbolTable();

        assertSymbol(symbolTable, pragmaKey("unroll"), pragma(Subtype.BOOL, "TRUE"));
        assertEquals(Boolean.TRUE, symbolTable.get("TRUE").initialValue, "The TRUE literal is registered");
    }

    @Test
    void Every_pragma_of_every_option_block_is_published() throws Exception {
        ParsingResults results = parse(
                SourceCode.functionBlock()
                        .body("OPTION PRAGMA bit_width 8; PRAGMA unroll; END_OPTION")
                        .body("OPTION PRAGMA depth 3; END_OPTION")
        );
        SymbolTable symbolTable = results.symbolTable();

        assertSymbol(symbolTable, pragmaKey("bit_width"), pragma(Subtype.USINT, "8"));
        assertSymbol(symbolTable, pragmaKey("unroll"), pragma(Subtype.BOOL, "TRUE"));
        assertSymbol(symbolTable, pragmaKey("depth"), pragma(Subtype.USINT, "3"));
    }
}
