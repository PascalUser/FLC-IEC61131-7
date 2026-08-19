package utils.diagnostics;

public class HexadecimalOutOfRange extends Error {
    private final String hexLexeme;

    public HexadecimalOutOfRange(int line, String outOfBoundHexadecimal) {
        super(line);
        this.hexLexeme = outOfBoundHexadecimal;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + hexLexeme + " hexadecimal out of range";
    }
}
