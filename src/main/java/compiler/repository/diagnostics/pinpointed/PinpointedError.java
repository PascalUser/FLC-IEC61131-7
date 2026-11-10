package compiler.repository.diagnostics.pinpointed;

public abstract class PinpointedError extends PinpointedDiagnostic {

    public PinpointedError(int line) {
        super(line);
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
         return "ERROR:" + super.getMessage() + ", ";
    }

    /** {@inheritDoc} */
    @Override
    public boolean fatalForCompilation() {
        return true;
    }
}
