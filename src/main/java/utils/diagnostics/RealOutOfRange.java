package utils.diagnostics;

/**
 * Error for real literals exceeding the floating-point range.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class RealOutOfRange extends Error {
    private final String realLexeme;

    public RealOutOfRange(int line, String outOfBoundReal) {
        super(line);
        this.realLexeme = outOfBoundReal;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + realLexeme + " real out of range";
    }
}