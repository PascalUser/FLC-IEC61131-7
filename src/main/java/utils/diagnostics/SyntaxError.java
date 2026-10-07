package utils.diagnostics;

/**
 * Syntax error reported by the lexer for invalid characters.
 * <p>
 * Generated when the lexer encounters a character that doesn't match any
 * defined lexical rule. The lexer continues in {@code YYINITIAL} state
 * to attempt recovery.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see utils.diagnostics.Error
 */
public class SyntaxError extends Error {
    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     */
    public SyntaxError(int line) {
        super(line);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " unexpected syntax error";
    }
}
