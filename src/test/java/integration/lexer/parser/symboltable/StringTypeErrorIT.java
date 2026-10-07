package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;

/**
 * Error case integration tests for STRING types.
 * All tests are marked as Not Yet Implemented.
 */
public class StringTypeErrorIT extends ParserTestSupport {

    @Test
    @NotYetImplemented
    void string_literal_exceeds_capacity_rejected() {
        // STRING[5] := 'toolong'
    }

    @Test
    @NotYetImplemented
    void string_variable_initialized_with_wrong_type_rejected() {
        // STRING[10] := 42
    }
}