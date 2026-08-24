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
    private final List<Diagnostic> diagnostics;

    public DiagnosticsHandler(){
        diagnostics = new ArrayList<>();
    }

    public void add(Diagnostic diagnostic){
        diagnostics.add(diagnostic);
    }

    // TODO: no todos los diagnósticos son errores!!
    public boolean hasErrors(){
        return !diagnostics.isEmpty();
    }

    public List<Diagnostic> getDiagnostics() {
        return Collections.unmodifiableList(diagnostics);
    }
}
