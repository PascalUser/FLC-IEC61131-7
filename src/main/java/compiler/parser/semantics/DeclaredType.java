package compiler.parser.semantics;

import compiler.repository.DiagnosticsHandler;
import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.diagnostics.general.SemanticError;
import compiler.repository.enums.Use;

public final class DeclaredType implements SemanticAnalyzer<String> {
    private final SymbolTable symbolTable;
    private final DiagnosticsHandler diagnostics;

    public DeclaredType(SymbolTable symbolTable, DiagnosticsHandler diagnostics) {
        this.symbolTable = symbolTable;
        this.diagnostics = diagnostics;
    }

    @Override
    public boolean check(String typeName) {
        LexemeInfo info = symbolTable.get(typeName);
        if (info != null && info.use == Use.TYPE) {
            return true;
        }
        diagnostics.add(new SemanticError("undeclared type " + typeName));
        return false;
    }
}