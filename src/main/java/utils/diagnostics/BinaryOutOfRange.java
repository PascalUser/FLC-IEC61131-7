package utils.diagnostics;

/**
 * Error for binary literals exceeding the 64-bit range.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class BinaryOutOfRange extends Error {
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