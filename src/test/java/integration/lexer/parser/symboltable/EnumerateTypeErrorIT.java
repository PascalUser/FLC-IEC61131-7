package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;

/**
 * Error case integration tests for ENUMERATED types.
 * All tests are marked as Not Yet Implemented.
 */
public class EnumerateTypeErrorIT extends ParserTestSupport {

    @Test
    @NotYetImplemented
    void Enum_Type_With_Duplicate_Enumerator_Rejected() {
        // TYPE T : (A, A); END_TYPE
    }

    @Test
    @NotYetImplemented
    void Enum_Variable_Initialized_With_Undefined_Enumerator_Rejected() {
        // TYPE T : (A, B); VAR v : T := C; END_VAR
    }

    @Test
    @NotYetImplemented
    void Enum_Variable_Initialized_With_Wrong_Type_Rejected() {
        // TYPE T : (A, B); VAR v : T := 42; END_VAR
    }
}