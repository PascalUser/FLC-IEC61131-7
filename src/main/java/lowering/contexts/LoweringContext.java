package lowering.contexts;

import ast.AbstractSyntaxTree;
import lowering.SymbolKeys;
import utils.DiagnosticsHandler;
import utils.SymbolTable;

public class LoweringContext {
    public final SymbolTable symbolTable;
    public final SymbolKeys keys;
    public final DiagnosticsHandler diagnosticsHandler;
    public final AbstractSyntaxTree ast;

    public LoweringContext(
            SymbolTable symbolTable,
            DiagnosticsHandler diagnosticsHandler,
            SymbolKeys keys,
            AbstractSyntaxTree ast
    ) {
        this.symbolTable = symbolTable;
        this.diagnosticsHandler = diagnosticsHandler;
        this.keys = keys;
        this.ast = ast;
    }
}
