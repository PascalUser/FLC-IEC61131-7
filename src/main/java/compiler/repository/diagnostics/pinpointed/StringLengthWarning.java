package compiler.repository.diagnostics.pinpointed;

import compiler.repository.enums.Subtype;

/**
 * Warning for string literal length exceeding IEC 61131-7 maximum (255 characters).
 * <p>
 * IEC 61131-7 limits STRING literals to 255 characters. This warning is emitted
 * when a string literal exceeds this limit but does not halt compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see Warning
 */
public final class StringLengthWarning extends PinpointedWarning {
    private final Subtype strSubtype;
    private final int maxLength;

    /**
     * Creates a string length warning.
     *
     * @param line    the source line number
     */
    public StringLengthWarning(int line, Subtype strSubtype, int maxLength) {
        super(line);
        this.strSubtype = strSubtype;
        this.maxLength = maxLength;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return super.getMessage() +
                strSubtype + " literal exceeds maximum length of " + maxLength + " characters. " +
                "It was truncated.";
    }
}
