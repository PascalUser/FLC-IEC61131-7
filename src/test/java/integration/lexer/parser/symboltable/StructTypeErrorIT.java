package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;

/**
 * Error case integration tests for STRUCT types.
 * All tests are marked as Not Yet Implemented.
 */
public class StructTypeErrorIT extends ParserTestSupport {

    @Test
    @NotYetImplemented
    void struct_type_with_duplicate_field_rejected() {
        // STRUCT a: INT; a: REAL; END_STRUCT
    }

    @Test
    @NotYetImplemented
    void struct_variable_initialized_with_unknown_field_rejected() {
        // STRUCT a: INT; END_STRUCT; VAR v : T := (b := 1); END_VAR
    }

    @Test
    @NotYetImplemented
    void struct_variable_initialized_with_type_mismatch_rejected() {
        // STRUCT a: INT; END_STRUCT; VAR v : T := (a := 'str'); END_VAR
    }
}