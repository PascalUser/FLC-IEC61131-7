package utils.diagnostics;

/**
 * Error for DATE_AND_TIME literals with invalid date or time components.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class DateAndTimeOutOfRange extends Error {
    private final String dateTimeLexeme;

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param outOfBoundDateTime the supplied out of bound date time
     */
    public DateAndTimeOutOfRange(int line, String outOfBoundDateTime) {
        super(line);
        this.dateTimeLexeme = outOfBoundDateTime;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + dateTimeLexeme + " date and time out of range";
    }
}