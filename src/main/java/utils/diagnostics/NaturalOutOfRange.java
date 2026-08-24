package utils.diagnostics;

/**
 * Error for unsigned decimal literals exceeding 2^64 - 1.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
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
