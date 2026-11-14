package compiler.parser.semantics;

import compiler.repository.DiagnosticsHandler;
import compiler.repository.diagnostics.general.SemanticError;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DistinctNames implements SemanticAnalyzer<List<String>> {
    private final DiagnosticsHandler diagnostics;

    public DistinctNames(DiagnosticsHandler diagnostics) {
        this.diagnostics = diagnostics;
    }

    @Override
    public boolean check(List<String> names) {
        Set<String> seen = new HashSet<>();
        boolean distinct = true;
        for (String name : names) {
            if (!seen.add(name)) {
                diagnostics.add(new SemanticError("duplicate name " + name));
                distinct = false;
            }
        }
        return distinct;
    }
}