package compiler.parser.semantics;

import compiler.repository.DiagnosticsHandler;
import compiler.repository.SymbolTable;
import compiler.repository.diagnostics.general.SemanticError;
import compiler.repository.enums.Type;

import java.util.Objects;

public final class ConstantAcceptingType implements SemanticAnalyzer<String> {
    private final SymbolTable symbolTable;
    private final DiagnosticsHandler diagnostics;

    public ConstantAcceptingType(SymbolTable symbolTable, DiagnosticsHandler diagnostics) {
        this.symbolTable = symbolTable;
        this.diagnostics = diagnostics;
    }

    @Override
    public boolean check(String typeName) {
        Type kind = Objects.requireNonNull(symbolTable.get(typeName)).type;
        if (kind != Type.ENUMERATE && kind != Type.STRUCT && kind != Type.ARRAY) {
            return true;
        }
        diagnostics.add(new SemanticError("type " + typeName + " cannot be initialized with a constant"));
        return false;
    }
}
