package utils.diagnostics;

public class NaturalOutOfRange extends Error {
    private final String intLexeme;

    public NaturalOutOfRange(int line, String outOfBoundInteger) {
        super(line);
        this.intLexeme = outOfBoundInteger;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + intLexeme + " is a natural bigger than 2^64 - 1";
    }
}
