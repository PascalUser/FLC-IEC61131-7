package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import utils.LexemeInfo;
import utils.ParserTestSupport;
import utils.ParsingResults;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Integration tests for the interface of the function block: its entry under its name ({@code MAIN}) has the
 * keys of the VAR_INPUT and VAR_OUTPUT variables in declaration order as parameters. The symbol table is a
 * map and does not keep that order; the code generator needs it for the signature of the controller.
 */
public class FunctionBlockInterfaceIT extends ParserTestSupport {

    private ParsingResults parseProgram(String... lines) throws Exception {
        return parse(new StringReader(String.join("\n", lines)));
    }

    private static LexemeInfo functionBlock(List<String> interfaceVariables) {
        return new LexemeInfoBuilder()
                .type(Type.NONE)
                .subtype(Subtype.NONE)
                .use(Use.FUNCTION_BLOCK)
                .source(Source.NONE)
                .parameters(interfaceVariables)
                .build();
    }

    @Test
    void Inputs_and_outputs_keep_their_declaration_order_across_blocks() throws Exception {
        ParsingResults results = parseProgram(
                "FUNCTION_BLOCK main",
                "VAR_INPUT speed : INT; angle : INT; END_VAR",
                "VAR_OUTPUT force : INT; END_VAR",
                "VAR_INPUT bias : INT; END_VAR",
                "END_FUNCTION_BLOCK");

        assertSymbol(results.symbolTable(), "MAIN",
                functionBlock(Arrays.asList("MAIN#SPEED", "MAIN#ANGLE", "MAIN#FORCE", "MAIN#BIAS")));
    }

    @Test
    void Identifier_list_keeps_its_order() throws Exception {
        ParsingResults results = parseProgram(
                "FUNCTION_BLOCK main",
                "VAR_INPUT z, a, m : INT; END_VAR",
                "END_FUNCTION_BLOCK");

        assertSymbol(results.symbolTable(), "MAIN", functionBlock(Arrays.asList("MAIN#Z", "MAIN#A", "MAIN#M")));
    }

    @Test
    void Internal_variables_are_not_part_of_the_interface() throws Exception {
        ParsingResults results = parseProgram(
                "FUNCTION_BLOCK main",
                "VAR_INPUT x : INT; END_VAR",
                "VAR internal : INT; END_VAR",
                "VAR CONSTANT k : INT := 1; END_VAR",
                "END_FUNCTION_BLOCK");

        assertSymbol(results.symbolTable(), "MAIN", functionBlock(Collections.singletonList("MAIN#X")));
    }

    @Test
    void Function_block_without_inputs_or_outputs_has_an_empty_interface() throws Exception {
        ParsingResults results = parseProgram("FUNCTION_BLOCK main", "END_FUNCTION_BLOCK");

        assertSymbol(results.symbolTable(), "MAIN", functionBlock(Collections.emptyList()));
    }

    @Test
    void Function_block_is_published_under_its_own_name() throws Exception {
        ParsingResults results = parseProgram(
                "FUNCTION_BLOCK pendulum",
                "VAR_INPUT angle : INT; END_VAR",
                "END_FUNCTION_BLOCK");

        assertSymbol(results.symbolTable(), "PENDULUM",
                functionBlock(Collections.singletonList("PENDULUM#ANGLE")));
    }
}
