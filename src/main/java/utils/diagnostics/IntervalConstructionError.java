package utils.diagnostics;

/**
 * Error for TIME (interval) literals where a minor magnitude exceeds its natural limit
 * while a major magnitude is present.
 * <p>
 * IEC 61131-3 Rule: Only the highest-order non-zero magnitude may exceed its natural limit.
 * Examples: 1d_25h (days present -> hours must be <24), 2h_65m (hours present -> minutes must be <60).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class IntervalConstructionError extends Error {
    private final String intervalLexeme;

    public IntervalConstructionError(int line, String invalidInterval) {
        super(line);
        this.intervalLexeme = invalidInterval;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + intervalLexeme + " interval construction error: minor magnitude exceeds limit";
    }
}