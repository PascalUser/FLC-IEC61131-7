package compiler.generator.lowers.contexts;

import compiler.ast.AbstractSyntaxTree;
import compiler.generator.lowers.utils.SymbolKeys;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public final List<String> inVars;
    public final List<String> outVars;
    public final List<String> localVars;

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

        this.inVars = new ArrayList<>();
        this.outVars = new ArrayList<>();
        this.localVars = new ArrayList<>();

        List<String> orderedKeys = symbolTable.orderedKeysBasedOnInsertion();
        for (String key : orderedKeys) {
            LexemeInfo keyInfo = symbolTable.get(key);
            switch (Objects.requireNonNull(keyInfo).source) {
                case IN:
                    inVars.add(key);
                    break;
                case OUT:
                    outVars.add(key);
                    break;
                case INTERNAL:
                    localVars.add(key);
                    break;
                default:
                    break;
            }
        }
    }

    public List<String> inVarKeys() { return  inVars; }
    public List<String> outVarKeys() { return outVars; }
    public List<String> localVarKeys() { return localVars; }
}
