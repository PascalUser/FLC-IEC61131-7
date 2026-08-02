package utils;

import utils.diagnostics.Diagnostic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagnosticsHandler {
    private final List<Diagnostic> diagnostics;

    public DiagnosticsHandler(){
        diagnostics = new ArrayList<>();
    }

    public void add(Diagnostic diagnostic){
        diagnostics.addLast(diagnostic);
    }

    public boolean hasErrors(){
        return !diagnostics.isEmpty();
    }

    public List<Diagnostic> getDiagnostics() {
        return Collections.unmodifiableList(diagnostics);
    }
}
