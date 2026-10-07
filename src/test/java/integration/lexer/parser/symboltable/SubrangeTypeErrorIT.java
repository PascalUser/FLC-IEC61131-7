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
    void Subrange_With_Invalid_Bounds_Rejected() {
        // INT (10..5) - lower > upper
    }

    @Test
    @NotYetImplemented
    void Subrange_With_Non_Constant_Bounds_Rejected() {
        // INT (?..10) - bounds not constants
    }

    @Test
    @NotYetImplemented
    void Subrange_Variable_Initialized_Out_Of_Bounds_Rejected() {
        // INT (0..10) var := 15
    }
}