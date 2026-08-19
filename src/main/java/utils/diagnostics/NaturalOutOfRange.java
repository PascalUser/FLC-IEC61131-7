package utils.diagnostics;

public class NaturalOutOfRange extends Error {
    private final String natLexeme;

    public NaturalOutOfRange(int line, String outOfBoundNatural) {
        super(line);
        this.natLexeme = outOfBoundNatural;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + natLexeme + " is a natural bigger than 2^64 - 1";
    }
}
