package utils.diagnostics;

/**
 * Warning for signed integer literals exceeding the 64-bit range (LINT).
 * <p>
 * The lexer uses a fallback value (LINT max/min) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see utils.diagnostics.Warning
 */
public final class IntegerOutOfRange extends Warning {
    private final String intLexeme;

    /**
     * Creates an integer out-of-range warning.
     *
     * @param line              the source line number
     * @param outOfBoundInteger the offending integer lexeme
     */
    public IntegerOutOfRange(int line, String outOfBoundInteger) {
        super(line);
        this.intLexeme = outOfBoundInteger;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + intLexeme + " integer out of range";
    }
}
