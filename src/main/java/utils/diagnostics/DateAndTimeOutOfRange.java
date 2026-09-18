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

    public DateAndTimeOutOfRange(int line, String outOfBoundDateTime) {
        super(line);
        this.dateTimeLexeme = outOfBoundDateTime;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + dateTimeLexeme + " date and time out of range";
    }
}