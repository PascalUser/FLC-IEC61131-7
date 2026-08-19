package utils.diagnostics;

public class OctalOutOfRange extends Error {
    private final String octLexeme;

    public OctalOutOfRange(int line, String outOfBoundOctal) {
        super(line);
        this.octLexeme = outOfBoundOctal;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + octLexeme + " octal out of range";
    }
}