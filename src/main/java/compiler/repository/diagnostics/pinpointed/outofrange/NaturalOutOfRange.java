package compiler.repository.diagnostics.pinpointed.outofrange;

import compiler.repository.diagnostics.pinpointed.OutOfRangeWarning;

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
public final class NaturalOutOfRange extends OutOfRangeWarning {
    private final String correctedNatural;

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param outOfBoundNatural the supplied out of bound natural
     */
    public NaturalOutOfRange(int line, String outOfBoundNatural, String correctedNatural) {
        super(line, outOfBoundNatural);
        this.correctedNatural = correctedNatural;
    }

    @Override
    protected String getLower() {
        return "0";
    }

    @Override
    protected String getUpper() {
        return "18446744073709551615";
    }

    @Override
    protected String getCorrection() {
        return correctedNatural;
    }
}
