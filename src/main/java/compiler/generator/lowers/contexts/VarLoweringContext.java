package compiler.generator.lowers.contexts;

import compiler.ast.AbstractSyntaxTree;
import compiler.generator.lowers.BitWidthLower;
import compiler.generator.lowers.utils.SymbolKeys;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.SymbolTable;

/**
 * Lowering context of a variable: adds the variable key and the bit-width resolver.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class VarLoweringContext extends LoweringContext {
    /** Symbol table key of the variable being lowered. */
    public String varKey;
    /** Resolver of the controller bit width. */
    public BitWidthLower bitWidthLower;

    /**
     * Creates a variable context from an existing context.
     *
     * @param initContext context to copy the shared state from
     * @param varKey symbol table key of the variable
     * @param bitWidthLower resolver of the controller bit width
     */
    public VarLoweringContext(
            LoweringContext initContext,
            String varKey,
            BitWidthLower bitWidthLower
    ) {
        this(
                initContext.symbolTable,
                initContext.diagnosticsHandler,
                initContext.keys,
                initContext.ast,
                varKey,
                bitWidthLower
        );
    }

    /**
     * Creates a variable context from all its parts.
     *
     * @param symbolTable symbol table produced by the parser
     * @param diagnosticsHandler collector of lowering diagnostics
     * @param keys mangled symbol keys of the function block
     * @param ast syntax tree of the program
     * @param varKey symbol table key of the variable
     * @param bitWidthLower resolver of the controller bit width
     */
    public VarLoweringContext(
            SymbolTable symbolTable,
            DiagnosticsHandler diagnosticsHandler,
            SymbolKeys keys,
            AbstractSyntaxTree ast,
            String varKey,
            BitWidthLower bitWidthLower
    ) {
        super(symbolTable, diagnosticsHandler, keys, ast);
        this.varKey = varKey;
        this.bitWidthLower = bitWidthLower;
    }
}
