package utils.diagnostics;

/**
 * Warning for octal literals exceeding the 64-bit range.
 * <p>
 * The lexer uses a fallback value (LWORD max) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class OctalOutOfRange extends Warning {
    private final String octLexeme;

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param outOfBoundOctal the supplied out of bound octal
     */
    public OctalOutOfRange(int line, String outOfBoundOctal) {
        super(line);
        this.octLexeme = outOfBoundOctal;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + octLexeme + " octal out of range";
    }
}