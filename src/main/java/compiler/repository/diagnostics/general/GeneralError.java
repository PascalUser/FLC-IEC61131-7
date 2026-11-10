package compiler.repository.diagnostics.general;

import compiler.repository.diagnostics.Diagnostic;

public class GeneralError implements Diagnostic {
    @Override
    public String getMessage() {
        return "ERROR: ";
    }

    @Override
    public boolean fatalForCompilation() {
        return true;
    }
}
