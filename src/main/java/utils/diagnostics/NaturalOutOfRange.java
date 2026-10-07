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

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param outOfBoundNatural the supplied out of bound natural
     */
    public NaturalOutOfRange(int line, String outOfBoundNatural) {
        super(line);
        this.natLexeme = outOfBoundNatural;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + natLexeme + " is a natural bigger than 2^64 - 1";
    }
}
