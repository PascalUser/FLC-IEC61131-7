package compiler.repository.diagnostics.general;

import compiler.repository.diagnostics.Diagnostic;

public class GeneralWarning implements Diagnostic {
    @Override
    public String getMessage() {
        return "WARNING: ";
    }

    @Override
    public boolean fatalForCompilation() {
        return false;
    }
}
