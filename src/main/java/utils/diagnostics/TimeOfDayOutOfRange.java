package utils.diagnostics;

/**
 * Error for TIME_OF_DAY literals with invalid time components.
 * <p>
 * Examples: {@code 25:00:00} (hour &gt; 23), {@code 12:60:00} (minute &gt; 59), {@code 12:30:60} (second &gt; 59).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class TimeOfDayOutOfRange extends Error {
    private final String timeLexeme;

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param outOfBoundTime the supplied out of bound time
     */
    public TimeOfDayOutOfRange(int line, String outOfBoundTime) {
        super(line);
        this.timeLexeme = outOfBoundTime;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + timeLexeme + " time of day out of range";
    }
}