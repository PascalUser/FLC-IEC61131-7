package utils.diagnostics;

/**
 * Error for octal literals exceeding the 64-bit range.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class OctalOutOfRange extends Error {
    private final String octLexeme;

    public OctalOutOfRange(int line, String outOfBoundOctal) {
        super(line);
        this.octLexeme = outOfBoundOctal;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + octLexeme + " octal out of range";
    }
}