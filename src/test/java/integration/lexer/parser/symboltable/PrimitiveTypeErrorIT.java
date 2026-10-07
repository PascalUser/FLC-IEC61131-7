package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;

/**
 * Error case integration tests for PRIMITIVE types.
 * All tests are marked as Not Yet Implemented.
 */
public class PrimitiveTypeErrorIT extends ParserTestSupport {

    @Test
    @NotYetImplemented
    void duplicate_type_declaration_rejected() {
        // TYPE block with duplicate type name
    }

    @Test
    @NotYetImplemented
    void duplicate_variable_declaration_rejected() {
        // VAR block with duplicate variable name
    }

    @Test
    @NotYetImplemented
    void real_variable_with_string_initializer_rejected() {
        // REAL var := 'string'
    }

    @Test
    @NotYetImplemented
    void bool_variable_with_int_initializer_rejected() {
        // BOOL var := 42
    }
}