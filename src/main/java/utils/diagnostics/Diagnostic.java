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
    private final int line;

    Diagnostic(int diagnosticLine) {
        this.line = diagnosticLine;
    }

    public String getMessage() {
        return "Line " + line;
    }
}