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
public final class DiagnosticsHandler {
    private boolean hasErrors;
    private final List<Diagnostic> diagnostics;

    /** Creates an empty diagnostics collector. */
    public DiagnosticsHandler() {
        hasErrors = false;
        diagnostics = new ArrayList<>();
    }

    /**
     * Records a diagnostic and tracks whether it is fatal to compilation.
     *
     * @param diagnostic the diagnostic to record
     */
    public void add(Diagnostic diagnostic){
        hasErrors = hasErrors || diagnostic.fatalForCompilation();
        diagnostics.add(diagnostic);
    }

    /**
     * Reports whether any recorded diagnostic is fatal to compilation.
     *
     * @return {@code true} if a fatal diagnostic has been added
     */
    public boolean hasErrors(){
        return hasErrors;
    }

    /**
     * Returns a read-only view of recorded diagnostics in insertion order.
     *
     * @return an unmodifiable view of the diagnostics
     */
    public List<Diagnostic> getDiagnostics() {
        return Collections.unmodifiableList(diagnostics);
    }
}
