package utils.diagnostics;

public class RealOutOfRange extends Error {
    private final String realLexeme;

    public RealOutOfRange(int line, String outOfBoundInteger) {
        super(line);
        this.realLexeme = outOfBoundInteger;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + realLexeme + " real out of range";
    }
}