package utils.diagnostics;

/**
 * Warning for unsigned decimal literals exceeding 2^64 - 1.
 * <p>
 * The lexer uses a fallback value (ULINT max) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class NaturalOutOfRange extends Warning {
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
