package utils.diagnostics;

/**
 * Base class for warning diagnostics.
 * <p>
 * Extends {@link Diagnostic} with a "WARNING - " prefix for warning messages.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class Warning extends Diagnostic {

    public Warning(int line) {
        super(line);
    }

    @Override
    public String getMessage() {
        return "WARNING: " + super.getMessage() + ", ";
    }

    @Override
    public boolean fatalForCompilation() {
        return false;
    }
}