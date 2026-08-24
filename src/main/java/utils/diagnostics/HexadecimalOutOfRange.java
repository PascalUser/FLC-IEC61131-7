package utils.diagnostics;

/**
 * Error for hexadecimal literals exceeding the 64-bit range.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
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
