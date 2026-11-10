package compiler.repository.diagnostics.pinpointed.outofrange.base;

import compiler.repository.diagnostics.pinpointed.OutOfRangeWarning;

/**
 * Warning for octal literals exceeding the 64-bit range.
 * <p>
 * The lexer uses a fallback value (LWORD max) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class OctalOutOfRange extends OutOfRangeWarning {
    private final String correctedOctal;

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param outOfBoundOctal the supplied out of bound octal
     */
    public OctalOutOfRange(int line, String outOfBoundOctal, String correctedOctal) {
        super(line, outOfBoundOctal);
        this.correctedOctal = correctedOctal;
    }

    @Override
    protected String getLower() {
        return "8#0";
    }

    @Override
    protected String getUpper() {
        return "8#1777777777777777777777";
    }

    @Override
    protected String getCorrection() {
        return correctedOctal;
    }
}
