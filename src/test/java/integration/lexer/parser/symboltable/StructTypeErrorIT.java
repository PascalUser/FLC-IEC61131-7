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
    void Struct_Type_With_Duplicate_Field_Rejected() {
        // STRUCT a: INT; a: REAL; END_STRUCT
    }

    @Test
    @NotYetImplemented
    void Struct_Variable_Initialized_With_Unknown_Field_Rejected() {
        // STRUCT a: INT; END_STRUCT; VAR v : T := (b := 1); END_VAR
    }

    @Test
    @NotYetImplemented
    void Struct_Variable_Initialized_With_Type_Mismatch_Rejected() {
        // STRUCT a: INT; END_STRUCT; VAR v : T := (a := 'str'); END_VAR
    }
}