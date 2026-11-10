package compiler.repository.diagnostics.pinpointed.outofrange;

import compiler.repository.diagnostics.pinpointed.OutOfRangeWarning;

/**
 * Warning for signed integer literals exceeding the 64-bit range (LINT).
 * <p>
 * The lexer uses a fallback value (LINT max/min) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see Warning
 */
public final class IntegerOutOfRange extends OutOfRangeWarning {
    private final String correctedInteger;

    /**
     * Creates an integer out-of-range warning.
     *
     * @param line              the source line number
     * @param outOfBoundInteger the offending integer lexeme
     */
    public IntegerOutOfRange(int line, String outOfBoundInteger, String correctedInteger) {
        super(line, outOfBoundInteger);
        this.correctedInteger = correctedInteger;
    }

    @Override
    protected String getLower() {
        return String.valueOf(Long.MIN_VALUE);
    }

    @Override
    protected String getUpper() {
        return String.valueOf(Long.MAX_VALUE);
    }

    @Override
    protected String getCorrection() {
        return correctedInteger;
    }
}
