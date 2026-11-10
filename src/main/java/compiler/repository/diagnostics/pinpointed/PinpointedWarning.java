package compiler.repository.diagnostics.pinpointed;

public abstract class PinpointedWarning extends PinpointedDiagnostic {

    public PinpointedWarning(int line) {
        super(line);
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return "WARNING: " + super.getMessage() + ", ";
    }

    /** {@inheritDoc} */
    @Override
    public boolean fatalForCompilation() {
        return false;
    }
}
