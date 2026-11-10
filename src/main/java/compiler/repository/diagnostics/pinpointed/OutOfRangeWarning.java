package compiler.repository.diagnostics.pinpointed;

public abstract class OutOfRangeWarning extends PinpointedWarning {
    private final String outOfRangeLexeme;

    protected OutOfRangeWarning(int line, String outOfRangeLexeme) {
        super(line);
        this.outOfRangeLexeme = outOfRangeLexeme;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        String min = getLower();
        String max = getUpper();
        String corrected = getCorrection();
        return super.getMessage() + outOfRangeLexeme + " was out of range [" + min + ".." + max + "] " +
                "but it was corrected to " + corrected;
    }

    protected abstract String getLower();
    protected abstract String getUpper();
    protected abstract String getCorrection();
}
