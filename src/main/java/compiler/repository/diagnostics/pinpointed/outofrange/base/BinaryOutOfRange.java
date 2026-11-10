package compiler.repository.diagnostics.pinpointed.outofrange.base;

import compiler.repository.diagnostics.pinpointed.OutOfRangeWarning;

/**
 * Warning for binary literals exceeding the 64-bit range.
 * <p>
 * The lexer uses a fallback value (LWORD max) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class BinaryOutOfRange extends OutOfRangeWarning {
    private final String correctedBinary;

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     * @param outOfBoundBinary the supplied out of bound binary
     */
    public BinaryOutOfRange(int line, String outOfBoundBinary, String correctedBinary) {
        super(line, outOfBoundBinary);
        this.correctedBinary = correctedBinary;
    }

    @Override
    protected String getLower() {
        return "2#0";
    }

    @Override
    protected String getUpper() {
        return "2#1111111111111111111111111111111111111111111111111111111111111111";
    }

    @Override
    protected String getCorrection() {
        return correctedBinary;
    }
}
