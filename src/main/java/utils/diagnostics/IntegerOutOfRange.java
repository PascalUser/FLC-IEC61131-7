package utils.diagnostics;

public class IntegerOutOfRange extends Error {
    private final String intLexeme;

    public IntegerOutOfRange(int line, String outOfBoundInteger) {
        super(line);
        this.intLexeme = outOfBoundInteger;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + intLexeme + " integer out of range";
    }
}
