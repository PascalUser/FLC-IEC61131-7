package integration.lexer.parser.symboltable;

import compiler.repository.diagnostics.general.SemanticError;
import org.junit.jupiter.api.Test;
import utils.builders.Block;
import utils.supports.ParserTestSupport;
import utils.builders.SourceCode;

/**
 * Error cases that apply to any kind of declaration (duplicates, undeclared types).
 * Construction-specific errors live in the {@code Errors} nested class of each construction's test.
 */
public class DeclarationErrorsIT extends ParserTestSupport {

    /** Today this crashes with a NullPointerException in {@code custom_specification}. */
    @Test
    void Variable_of_an_undeclared_type_is_rejected_with_a_diagnostic() throws Exception {
        assertRejected(
                SourceCode.functionBlock()
                        .var(Block.VAR, "x : undeclared_type")
        );
    }

    @Test
    void Duplicate_variable_is_rejected() throws Exception {
        assertRejected(
                SourceCode.functionBlock()
                        .var(Block.VAR, "x : INT")
                        .var(Block.VAR, "x : REAL")
        );
    }

    @Test 
    void Undeclared_type_is_reported_without_crashing() throws Exception {
        assertDiagnostic(
                SemanticError.class,
                "undeclared type UNDECLARED_TYPE",
                SourceCode.functionBlock().var(Block.VAR, "x : undeclared_type")
        );
    }
    @Test 
    void Duplicate_variable_names_the_key() throws Exception {
        assertDiagnostic(
                SemanticError.class,
                "duplicate name MAIN#X",
                SourceCode.functionBlock().var(Block.VAR, "x : INT").var(Block.VAR, "x : REAL")
        );
    }
    @Test void Duplicate_in_the_same_identifier_list() throws Exception {
        assertDiagnostic(
                SemanticError.class,
                "duplicate name MAIN#X",
                SourceCode.functionBlock().var(Block.VAR, "x, x : INT")
        );
    }

    @Test
    void Duplicate_type_declaration_is_rejected() throws Exception {
        assertRejected(
                SourceCode.functionBlock()
                        .type("level_type : INT (0..10)")
                        .type("level_type : INT (0..20)")
        );
    }
}
