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
    void String_Literal_Exceeds_Capacity_Rejected() {
        // STRING[5] := 'toolong'
    }

    @Test
    @NotYetImplemented
    void String_Variable_Initialized_With_Wrong_Type_Rejected() {
        // STRING[10] := 42
    }
}