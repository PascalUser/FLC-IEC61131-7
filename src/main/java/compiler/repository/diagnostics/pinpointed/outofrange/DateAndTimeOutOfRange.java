package compiler.repository.diagnostics.pinpointed.outofrange;

import compiler.repository.diagnostics.pinpointed.OutOfRangeError;

/**
 * Error for DATE_AND_TIME literals with invalid date or time components.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class DateAndTimeOutOfRange extends OutOfRangeError {

    public DateAndTimeOutOfRange(int line, String outOfBoundDateTime) {
        super(line, outOfBoundDateTime);
    }

    @Override
    protected String getLower() {
        return "0001-01-01-00:00:00";
    }

    @Override
    protected String getUpper() {
        return "9999-12-31-23:59:59.999999999";
    }
}
