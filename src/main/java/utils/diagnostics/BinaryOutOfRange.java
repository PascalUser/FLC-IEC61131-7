package utils.diagnostics;

/**
 * Warning for binary literals exceeding the 64-bit range.
 * <p>
 * The lexer uses a fallback value (LWORD max) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class BinaryOutOfRange extends Warning {
    private final String binLexeme;

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param outOfBoundBinary the supplied out of bound binary
     */
    public BinaryOutOfRange(int line, String outOfBoundBinary) {
        super(line);
        this.binLexeme = outOfBoundBinary;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + binLexeme + " binary out of range";
    }
}