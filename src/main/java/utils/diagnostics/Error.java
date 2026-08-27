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

    public Error(int line) {
        super(line);
    }

    @Override
    public String getMessage() {
         return "ERROR:" + super.getMessage() + ", ";
    }

}