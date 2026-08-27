package utils.diagnostics;

/**
 * Error for DATE literals with invalid calendar dates.
 * <p>
 * Examples: 2023-02-29 (non-leap year), 2026-13-01 (invalid month), 2026-04-31 (invalid day).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class DateOutOfRange extends Error {
    private final String dateLexeme;

    public DateOutOfRange(int line, String outOfBoundDate) {
        super(line);
        this.dateLexeme = outOfBoundDate;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + dateLexeme + " date out of range";
    }
}