package compiler.repository.diagnostics.pinpointed.outofrange;

import compiler.repository.diagnostics.pinpointed.OutOfRangeError;

public final class TimeOfDayOutOfRange extends OutOfRangeError {

    public TimeOfDayOutOfRange(int line, String outOfBoundTime) {
        super(line, outOfBoundTime);
    }

    @Override
    protected String getLower() {
        return "00:00:00";
    }

    @Override
    protected String getUpper() {
        return "23:59:59.999999999";
    }
}
