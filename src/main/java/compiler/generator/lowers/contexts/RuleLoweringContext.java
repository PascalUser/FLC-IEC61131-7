package compiler.generator.lowers.contexts;

import compiler.ast.AbstractAstNode;
import compiler.ast.AbstractSyntaxTree;
import compiler.generator.lowers.utils.SymbolKeys;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.SymbolTable;

/**
 * Lowering context of a rule: adds the controller builder, the rule block, the rule number and its AST node.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class RuleLoweringContext extends LoweringContext {
    /** Name of the rule block that contains the rule. */
    public final String ruleblockName;
    /** Number of the rule inside its block. */
    public final String ruleNumber;
    /** Syntax tree node of the rule. */
    public final AbstractAstNode ruleNode;

    /**
     * Creates a rule context from an existing context.
     *
     * @param initContext context to copy the shared state from
     * @param ruleblockName name of the rule block that contains the rule
     * @param ruleNumber number of the rule inside its block
     * @param ruleNode syntax tree node of the rule
     */
    public RuleLoweringContext(
            LoweringContext initContext,
            String ruleblockName,
            String ruleNumber,
            AbstractAstNode ruleNode
    ) {
        this(
                initContext.symbolTable,
                initContext.diagnosticsHandler,
                initContext.keys,
                initContext.ast,
                ruleblockName,
                ruleNumber,
                ruleNode
        );
    }

    /**
     * Creates a rule context from all its parts.
     *
     * @param symbolTable symbol table produced by the parser
     * @param diagnosticsHandler collector of lowering diagnostics
     * @param keys mangled symbol keys of the function block
     * @param ast syntax tree of the program
     * @param ruleblockName name of the rule block that contains the rule
     * @param ruleNumber number of the rule inside its block
     * @param ruleNode syntax tree node of the rule
     */
    public RuleLoweringContext(
            SymbolTable symbolTable,
            DiagnosticsHandler diagnosticsHandler,
            SymbolKeys keys,
            AbstractSyntaxTree ast,
            String ruleblockName,
            String ruleNumber,
            AbstractAstNode ruleNode
    ) {
        super(symbolTable, diagnosticsHandler, keys, ast);
        this.ruleblockName = ruleblockName;
        this.ruleNumber = ruleNumber;
        this.ruleNode = ruleNode;
    }
}
