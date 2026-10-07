package utils.diagnostics;

/**
 * Error for TIME (interval) literals where a minor magnitude exceeds its natural limit
 * while a major magnitude is present.
 * <p>
 * IEC 61131-3 Rule: Only the highest-order non-zero magnitude may exceed its natural limit.
 * Examples: {@code 1d_25h}
 * (days present -&gt; hours must be &lt;24), {@code 2h_65m} (hours present -&gt; minutes must be &lt;60).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class IntervalConstructionError extends Error {
    private final String intervalLexeme;

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param invalidInterval the supplied invalid interval
     */
    public IntervalConstructionError(int line, String invalidInterval) {
        super(line);
        this.intervalLexeme = invalidInterval;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + intervalLexeme + " interval construction error: minor magnitude exceeds limit";
    }
}