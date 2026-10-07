package utils.diagnostics;

/**
 * Base class for error diagnostics.
 * <p>
 * Extends {@link Diagnostic} with an "ERROR - " prefix for error messages.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class Error extends Diagnostic {

    /**
     * Creates a diagnostic for the specified source line.
     *
     * @param line the one-based source line of the diagnostic
     */
    public Error(int line) {
        super(line);
    }

    @Override
    public String getMessage() {
         return "ERROR:" + super.getMessage() + ", ";
    }

    @Override
    public boolean fatalForCompilation() {
        return true;
    }
}