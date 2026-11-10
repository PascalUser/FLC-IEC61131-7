package compiler.repository.diagnostics.pinpointed.outofrange.base;

import compiler.repository.diagnostics.pinpointed.OutOfRangeWarning;

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
public final class HexadecimalOutOfRange extends OutOfRangeWarning {
    private final String correctedHexadecimal;

    public HexadecimalOutOfRange(int line, String outOfBoundHexadecimal, String correctedHexadecimal) {
        super(line, outOfBoundHexadecimal);
        this.correctedHexadecimal = correctedHexadecimal;
    }

    /** {@inheritDoc} */
    @Override
    protected String getLower() {
        return "16#0";
    }

    /** {@inheritDoc} */
    @Override
    protected String getUpper() {
        return "16#FFFFFFFFFFFFFFFF";
    }

    @Override
    protected String getCorrection() {
        return correctedHexadecimal;
    }
}
