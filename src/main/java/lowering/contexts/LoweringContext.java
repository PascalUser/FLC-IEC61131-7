package lowering.contexts;

import ast.AbstractSyntaxTree;
import lowering.utils.SymbolKeys;
import utils.DiagnosticsHandler;
import utils.SymbolTable;

/**
 * State shared by the lowering stages: the symbol table produced by the parser, the symbol keys of
 * the function block, the diagnostics handler and the syntax tree.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class LoweringContext {
    /** Symbol table produced by the parser. */
    public final SymbolTable symbolTable;
    /** Mangled symbol keys of the function block. */
    public final SymbolKeys keys;
    /** Collector of lowering diagnostics. */
    public final DiagnosticsHandler diagnosticsHandler;
    /** Syntax tree of the program. */
    public final AbstractSyntaxTree ast;

    /**
     * Creates a lowering context.
     *
     * @param symbolTable symbol table produced by the parser
     * @param diagnosticsHandler collector of lowering diagnostics
     * @param keys mangled symbol keys of the function block
     * @param ast syntax tree of the program
     */
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
