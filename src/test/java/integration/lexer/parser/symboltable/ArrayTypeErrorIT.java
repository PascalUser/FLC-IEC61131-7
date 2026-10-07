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
    void array_initializer_exceeds_dimension_rejected() {
        // ARRAY [1..2] OF INT := [1, 2, 3]
    }

    @Test
    @NotYetImplemented
    void array_initializer_with_type_mismatch_rejected() {
        // ARRAY [1..2] OF INT := ['a', 'b']
    }

    @Test
    @NotYetImplemented
    void array_with_invalid_bounds_rejected() {
        // ARRAY [5..1] OF INT
    }
}