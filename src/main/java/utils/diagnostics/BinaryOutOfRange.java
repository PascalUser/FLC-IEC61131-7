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
public class BinaryOutOfRange extends Warning {
    private final String binLexeme;

    public BinaryOutOfRange(int line, String outOfBoundBinary) {
        super(line);
        this.binLexeme = outOfBoundBinary;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + binLexeme + " binary out of range";
    }
}