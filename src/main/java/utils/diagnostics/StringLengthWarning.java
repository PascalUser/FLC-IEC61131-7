package utils.diagnostics;

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
 * @see utils.diagnostics.Warning
 */
public final class StringLengthWarning extends Warning {

    private final String message;

    /**
     * Creates a string length warning.
     *
     * @param line    the source line number
     * @param message the warning detail message
     */
    public StringLengthWarning(int line, String message) {
        super(line);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "WARNING: Line " + line + ", " + message;
    }
}