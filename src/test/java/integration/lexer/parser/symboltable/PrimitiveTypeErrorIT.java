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
    void Duplicate_Type_Declaration_Rejected() {
        // TYPE block with duplicate type name
    }

    @Test
    @NotYetImplemented
    void Duplicate_Variable_Declaration_Rejected() {
        // VAR block with duplicate variable name
    }

    @Test
    @NotYetImplemented
    void Real_Variable_With_String_Initializer_Rejected() {
        // REAL var := 'string'
    }

    @Test
    @NotYetImplemented
    void Bool_Variable_With_Int_Initializer_Rejected() {
        // BOOL var := 42
    }
}