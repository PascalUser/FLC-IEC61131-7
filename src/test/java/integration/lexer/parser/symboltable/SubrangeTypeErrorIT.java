package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;

/**
 * Error case integration tests for SUBRANGE types.
 * All tests are marked as Not Yet Implemented.
 */
public class SubrangeTypeErrorIT extends ParserTestSupport {

    @Test
    @NotYetImplemented
    void subrange_with_invalid_bounds_rejected() {
        // INT (10..5) - lower > upper
    }

    @Test
    @NotYetImplemented
    void subrange_with_non_constant_bounds_rejected() {
        // INT (?..10) - bounds not constants
    }

    @Test
    @NotYetImplemented
    void subrange_variable_initialized_out_of_bounds_rejected() {
        // INT (0..10) var := 15
    }
}