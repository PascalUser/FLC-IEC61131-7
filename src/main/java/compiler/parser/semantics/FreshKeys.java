package compiler.parser.semantics;

import compiler.repository.DiagnosticsHandler;
import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.diagnostics.general.SemanticError;
import compiler.repository.enums.Use;

import java.util.List;

public final class FreshKeys implements SemanticAnalyzer<List<String>> {
    private final SymbolTable symbolTable;
    private final DiagnosticsHandler diagnostics;

    public FreshKeys(SymbolTable symbolTable, DiagnosticsHandler diagnostics) {
        this.symbolTable = symbolTable;
        this.diagnostics = diagnostics;
    }

    @Override
    public boolean check(List<String> keys) {
        boolean fresh = true;
        for (String key : keys) {
            LexemeInfo existing = symbolTable.get(key);
            if (existing != null && existing.use != Use.UNKNOWN) {
                diagnostics.add(new SemanticError("duplicate declaration of " + key));
                fresh = false;
            }
        }
        return fresh;
    }
}
