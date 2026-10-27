package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import utils.Block;
import utils.ParserTestSupport;
import utils.SourceCode;

/**
 * Error cases that apply to any kind of declaration (duplicates, undeclared types).
 * Construction-specific errors live in the {@code Errors} nested class of each construction's test.
 */
public class DeclarationErrorsIT extends ParserTestSupport {

    /** Today this crashes with a NullPointerException in {@code custom_specification}. */
    @Test
    @NotYetImplemented
    void Variable_of_an_undeclared_type_is_rejected_with_a_diagnostic() throws Exception {
        assertRejected(
                SourceCode.functionBlock()
                        .var(Block.VAR, "x : undeclared_type")
        );
    }

    @Test
    @NotYetImplemented
    void Duplicate_variable_is_rejected() throws Exception {
        assertRejected(
                SourceCode.functionBlock()
                        .var(Block.VAR, "x : INT")
                        .var(Block.VAR, "x : REAL")
        );
    }

    @Test
    @NotYetImplemented
    void Duplicate_type_declaration_is_rejected() throws Exception {
        assertRejected(
                SourceCode.functionBlock()
                        .type("level_type : INT (0..10)")
                        .type("level_type : INT (0..20)")
        );
    }
}
