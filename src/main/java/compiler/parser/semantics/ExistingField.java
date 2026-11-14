package compiler.parser.semantics;

import compiler.parser.initializations.Initialization;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.diagnostics.general.SemanticError;

public final class ExistingField implements SemanticAnalyzer<String> {
    private final DiagnosticsHandler diagnostics;
    private final Initialization target;

    public ExistingField(DiagnosticsHandler diagnostics, Initialization target) {
        this.diagnostics = diagnostics;
        this.target = target;
    }

    @Override
    public boolean check(String path) {
        if (target.find(path).isPresent()) {
            return true;
        }
        diagnostics.add(new SemanticError("unknown field " + path));
        return false;
    }
}
