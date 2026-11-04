package lowering.contexts;

import ast.AbstractSyntaxTree;
import lowering.constructors.BitWidthConstructor;
import lowering.utils.SymbolKeys;
import utils.DiagnosticsHandler;
import utils.SymbolTable;

/**
 * Lowering context of a term: adds the term key to the variable context.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class TermLoweringContext extends VarLoweringContext {
    /** Symbol table key of the term being lowered. */
    public String termKey;

    /**
     * Creates a term context from an existing variable context.
     *
     * @param initContext variable context to copy the shared state from
     * @param termKey symbol table key of the term
     */
    public TermLoweringContext(
            VarLoweringContext initContext,
            String termKey
    ) {
        this(
                initContext.symbolTable,
                initContext.diagnosticsHandler,
                initContext.keys,
                initContext.ast,
                initContext.varKey,
                initContext.bitWidthConstructor,
                termKey
        );
    }

    /**
     * Creates a term context from all its parts.
     *
     * @param symbolTable symbol table produced by the parser
     * @param diagnosticsHandler collector of lowering diagnostics
     * @param keys mangled symbol keys of the function block
     * @param ast syntax tree of the program
     * @param varKey symbol table key of the variable
     * @param bitWidthConstructor resolver of the controller bit width
     * @param termKey symbol table key of the term
     */
    public TermLoweringContext(
            SymbolTable symbolTable,
            DiagnosticsHandler diagnosticsHandler,
            SymbolKeys keys,
            AbstractSyntaxTree ast,
            String varKey,
            BitWidthConstructor bitWidthConstructor,
            String termKey
    ) {
        super(symbolTable, diagnosticsHandler, keys, ast, varKey, bitWidthConstructor);
        this.termKey = termKey;
    }
}
