package compiler.repository.diagnostics.pinpointed.outofrange;

import compiler.repository.diagnostics.pinpointed.OutOfRangeError;

public final class IntervalOutOfRange extends OutOfRangeError {

    public IntervalOutOfRange(int line, String outOfBoundInterval) {
        super(line, outOfBoundInterval);
    }

    @Override
    protected String getLower() {
        return -Long.MAX_VALUE + "ns";
    }

    @Override
    protected String getUpper() {
        return Long.MAX_VALUE + "ns";
    }
}
