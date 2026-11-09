package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import compiler.repository.LexemeInfo;
import utils.supports.ParserTestSupport;
import utils.ParsingResults;
import compiler.repository.builders.LexemeInfoBuilder;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

import java.io.StringReader;

/**
 * Integration tests for the interface of the function block: its entry under its name ({@code MAIN}) has the
 * keys of the VAR_INPUT and VAR_OUTPUT variables in declaration order as parameters. The symbol table is a
 * map and does not keep that order; the code generator needs it for the signature of the controller.
 */
public class FunctionBlockInterfaceIT extends ParserTestSupport {

    private ParsingResults parseProgram() throws Exception {
        return parse(new StringReader(String.join(
                "\n",
                "FUNCTION_BLOCK pendulum",
                "VAR_INPUT " +
                        "angle : INT; " +
                "END_VAR",
                "END_FUNCTION_BLOCK"
        )));
    }

    private static LexemeInfo functionBlock() {
        return new LexemeInfoBuilder()
                .type(Type.NONE)
                .subtype(Subtype.NONE)
                .use(Use.MAIN)
                .source(Source.NONE)
                .build();
    }

    @Test
    void Function_block_is_published_under_its_own_name() throws Exception {
        ParsingResults results = parseProgram(
        );

        assertSymbol(results.symbolTable(), "PENDULUM", functionBlock());
    }
}
