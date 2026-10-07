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
    void enum_type_with_duplicate_enumerator_rejected() {
        // TYPE T : (A, A); END_TYPE
    }

    @Test
    @NotYetImplemented
    void enum_variable_initialized_with_undefined_enumerator_rejected() {
        // TYPE T : (A, B); VAR v : T := C; END_VAR
    }

    @Test
    @NotYetImplemented
    void enum_variable_initialized_with_wrong_type_rejected() {
        // TYPE T : (A, B); VAR v : T := 42; END_VAR
    }
}