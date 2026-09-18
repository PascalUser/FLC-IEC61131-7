package utils.diagnostics;

/**
 * Error for TIME_OF_DAY literals with invalid time components.
 * <p>
 * Examples: 25:00:00 (hour > 23), 12:60:00 (minute > 59), 12:30:60 (second > 59).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class TimeOfDayOutOfRange extends Error {
    private final String timeLexeme;

    public TimeOfDayOutOfRange(int line, String outOfBoundTime) {
        super(line);
        this.timeLexeme = outOfBoundTime;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + timeLexeme + " time of day out of range";
    }
}