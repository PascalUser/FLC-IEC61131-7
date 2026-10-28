package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import utils.ParserTestSupport;
import utils.ParsingResults;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the interface of the function block: the VAR_INPUT and VAR_OUTPUT variables in
 * declaration order. The symbol table is a map, so the parser exposes that order separately; the code
 * generator needs it for the signature of the controller.
 */
public class FunctionBlockInterfaceIT extends ParserTestSupport {

    private ParsingResults parseProgram(String... lines) throws Exception {
        return parse(new StringReader(String.join("\n", lines)));
    }

    @Test
    void Inputs_and_outputs_keep_their_declaration_order_across_blocks() throws Exception {
        ParsingResults results = parseProgram(
                "FUNCTION_BLOCK main",
                "VAR_INPUT speed : INT; angle : INT; END_VAR",
                "VAR_OUTPUT force : INT; END_VAR",
                "VAR_INPUT bias : INT; END_VAR",
                "END_FUNCTION_BLOCK");

        assertEquals(Arrays.asList("MAIN#SPEED", "MAIN#ANGLE", "MAIN#FORCE", "MAIN#BIAS"),
                results.interfaceVariables());
    }

    @Test
    void Identifier_list_keeps_its_order() throws Exception {
        ParsingResults results = parseProgram(
                "FUNCTION_BLOCK main",
                "VAR_INPUT z, a, m : INT; END_VAR",
                "END_FUNCTION_BLOCK");

        assertEquals(Arrays.asList("MAIN#Z", "MAIN#A", "MAIN#M"), results.interfaceVariables());
    }

    @Test
    void Internal_variables_are_not_part_of_the_interface() throws Exception {
        ParsingResults results = parseProgram(
                "FUNCTION_BLOCK main",
                "VAR_INPUT x : INT; END_VAR",
                "VAR internal : INT; END_VAR",
                "VAR CONSTANT k : INT := 1; END_VAR",
                "END_FUNCTION_BLOCK");

        assertEquals(Collections.singletonList("MAIN#X"), results.interfaceVariables());
    }

    @Test
    void Function_block_without_inputs_or_outputs_has_an_empty_interface() throws Exception {
        ParsingResults results = parseProgram("FUNCTION_BLOCK main", "END_FUNCTION_BLOCK");

        assertEquals(Collections.emptyList(), results.interfaceVariables());
    }
}
