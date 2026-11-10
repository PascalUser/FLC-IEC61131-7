package compiler.repository.diagnostics.pinpointed.outofrange;

import compiler.repository.diagnostics.pinpointed.OutOfRangeError;

public final class DateOutOfRange extends OutOfRangeError {

    public DateOutOfRange(int line, String outOfBoundDate) {
        super(line, outOfBoundDate);
    }

    @Override
    protected String getLower() {
        return "0001-01-01";
    }

    @Override
    protected String getUpper() {
        return "9999-12-31";
    }
}
