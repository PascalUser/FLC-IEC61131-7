package utils;

import utils.diagnostics.Diagnostic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collects and manages diagnostic messages (errors, warnings) during compilation.
 * <p>
 * Provides a central repository for diagnostics reported by the lexer, parser,
 * and semantic analyzers. Diagnostics are stored in insertion order and
 * can be retrieved as an unmodifiable list.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class DiagnosticsHandler {
    private boolean hasErrors;
    private final List<Diagnostic> diagnostics;

    public DiagnosticsHandler() {
        hasErrors = false;
        diagnostics = new ArrayList<>();
    }

    public void add(Diagnostic diagnostic){
        hasErrors = hasErrors || diagnostic.fatalForCompilation();
        diagnostics.add(diagnostic);
    }

    public boolean hasErrors(){
        return hasErrors;
    }

    public List<Diagnostic> getDiagnostics() {
        return Collections.unmodifiableList(diagnostics);
    }
}
