package utils.diagnostics;

/**
 * Error for TIME (interval) literals where total milliseconds exceed Long range.
 * <p>
 * Long.MAX_VALUE ms ≈ 106751991 days, Long.MIN_VALUE ms ≈ -106751992 days.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class IntervalOutOfRange extends Error {
    private final String intervalLexeme;

    public IntervalOutOfRange(int line, String outOfBoundInterval) {
        super(line);
        this.intervalLexeme = outOfBoundInterval;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + intervalLexeme + " interval out of range (exceeds Long nanoseconds)";
    }
}