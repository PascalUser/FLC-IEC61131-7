package utils.diagnostics;

/**
 * Base class for all diagnostic messages (errors, warnings).
 * <p>
 * Associates a diagnostic with a source line number and provides
 * a formatted message prefix.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class Diagnostic {
    /** Source line on which the diagnostic was reported. */
    protected final int line;

    Diagnostic(int diagnosticLine) {
        this.line = diagnosticLine;
    }

    /**
     * Formats the source line prefix for a diagnostic message.
     *
     * @return a line-prefixed diagnostic message
     */
    public String getMessage() {
        return "Line " + line;
    }

    /**
     * Reports whether this diagnostic prevents compilation from proceeding.
     *
     * @return {@code true} if the diagnostic is fatal to compilation
     */
    abstract public boolean fatalForCompilation();
}