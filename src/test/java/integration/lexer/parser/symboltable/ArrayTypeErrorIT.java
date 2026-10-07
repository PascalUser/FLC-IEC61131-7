package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;

/**
 * Error case integration tests for ARRAY types.
 * All tests are marked as Not Yet Implemented.
 */
public class ArrayTypeErrorIT extends ParserTestSupport {

    @Test
    @NotYetImplemented
    void Array_Initializer_Exceeds_Dimension_Rejected() {
        // ARRAY [1..2] OF INT := [1, 2, 3]
    }

    @Test
    @NotYetImplemented
    void Array_Initializer_With_Type_Mismatch_Rejected() {
        // ARRAY [1..2] OF INT := ['a', 'b']
    }

    @Test
    @NotYetImplemented
    void Array_With_Invalid_Bounds_Rejected() {
        // ARRAY [5..1] OF INT
    }
}