package utils.diagnostics;

public abstract class Diagnostic {
    private final int line;

    Diagnostic(int diagnosticLine) {
        this.line = diagnosticLine;
    }

    public String getMessage() {
        return "Line " + line;
    }
}