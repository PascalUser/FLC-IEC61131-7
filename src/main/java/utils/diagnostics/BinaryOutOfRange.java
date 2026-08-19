package utils.diagnostics;

public class BinaryOutOfRange extends Error {
    private final String binLexeme;

    public BinaryOutOfRange(int line, String outOfBoundBinary) {
        super(line);
        this.binLexeme = outOfBoundBinary;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + binLexeme + " binary out of range";
    }
}