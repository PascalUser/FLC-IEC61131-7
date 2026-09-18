package utils.diagnostics;

/**
 * Warning for hexadecimal literals exceeding the 64-bit range.
 * <p>
 * The lexer uses a fallback value (LWORD max) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class HexadecimalOutOfRange extends Warning {
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
