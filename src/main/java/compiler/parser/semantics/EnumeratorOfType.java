package compiler.parser.semantics;

import compiler.repository.DiagnosticsHandler;
import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.diagnostics.general.SemanticError;
import compiler.repository.enums.Type;

public final class EnumeratorOfType implements SemanticAnalyzer<String> {
    private final SymbolTable symbolTable;
    private final DiagnosticsHandler diagnostics;
    private final String typeName;

    public EnumeratorOfType(SymbolTable symbolTable, DiagnosticsHandler diagnostics, String typeName) {
        this.symbolTable = symbolTable;
        this.diagnostics = diagnostics;
        this.typeName = typeName;
    }

    @Override
    public boolean check(String enumerator) {
        LexemeInfo type = symbolTable.get(typeName);
        if (type != null && type.type == Type.ENUMERATE && type.parameters.contains(enumerator)) {
            return true;
        }
        diagnostics.add(new SemanticError(enumerator + " is not an enumerator of " + typeName));
        return false;
    }
}