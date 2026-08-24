package utils.diagnostics;

/**
 * Error for signed integer literals exceeding the 64-bit range.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class IntegerOutOfRange extends Error {
    private final String intLexeme;

    public IntegerOutOfRange(int line, String outOfBoundInteger) {
        super(line);
        this.intLexeme = outOfBoundInteger;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + intLexeme + " integer out of range";
    }
}
