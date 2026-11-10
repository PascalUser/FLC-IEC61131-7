package compiler.repository.diagnostics.pinpointed.outofrange;

import compiler.repository.diagnostics.pinpointed.OutOfRangeWarning;

/**
 * Warning for real literals exceeding the floating-point range.
 * <p>
 * The lexer uses a fallback value (Double.MAX_VALUE/-Double.MAX_VALUE) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class RealOutOfRange extends OutOfRangeWarning {
    private final String correctedReal;

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param outOfBoundReal the supplied out of bound real
     */
    public RealOutOfRange(int line, String outOfBoundReal, String correctedReal) {
        super(line, outOfBoundReal);
        this.correctedReal = correctedReal;
    }

    @Override
    protected String getLower() {
        return String.valueOf(-Double.MAX_VALUE);
    }

    @Override
    protected String getUpper() {
        return String.valueOf(Double.MAX_VALUE);
    }

    @Override
    protected String getCorrection() {
        return correctedReal;
    }
}
