package utils.diagnostics;

public class RealOutOfRange extends Error {
    private final String realLexeme;

    public RealOutOfRange(int line, String outOfBoundReal) {
        super(line);
        this.realLexeme = outOfBoundReal;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + realLexeme + " real out of range";
    }
}