package compiler.repository.diagnostics.pinpointed;

public abstract class OutOfRangeError extends PinpointedError {
    private final String outOfRangeLexeme;

    public OutOfRangeError(int line, String outOfRangeLexeme) {
        super(line);
        this.outOfRangeLexeme = outOfRangeLexeme;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        String min = getLower();
        String max = getUpper();
        return super.getMessage() + outOfRangeLexeme + " is out of range [" + min + ".." + max + "]";
    }

    protected abstract String getLower();
    protected abstract String getUpper();
}
