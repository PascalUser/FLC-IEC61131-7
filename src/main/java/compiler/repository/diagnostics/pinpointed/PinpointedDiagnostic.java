package compiler.repository.diagnostics.pinpointed;

import compiler.repository.diagnostics.Diagnostic;

public abstract class PinpointedDiagnostic implements Diagnostic {
    /** Source line on which the diagnostic was reported. */
    protected final int line;

    public PinpointedDiagnostic(int diagnosticLine) {
        this.line = diagnosticLine;
    }

    public String getMessage() {
        return "Line " + line;
    }

    abstract public boolean fatalForCompilation();
}