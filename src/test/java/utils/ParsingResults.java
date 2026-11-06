package utils;


import compiler.ast.AbstractSyntaxTree;
import compiler.repository.SymbolTable;

/**
 * Symbol table and syntax tree produced by a parser test.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class ParsingResults {
    private final SymbolTable symbolTable;
    private final AbstractSyntaxTree abstractSyntaxTree;

    /**
     * Creates the result of a parse.
     *
     * @param symbolTable symbol table produced by the parser
     * @param abstractSyntaxTree syntax tree produced by the parser
     */
    public ParsingResults(SymbolTable symbolTable, AbstractSyntaxTree abstractSyntaxTree) {
        this.symbolTable = symbolTable;
        this.abstractSyntaxTree = abstractSyntaxTree;
    }

    /**
     * Returns the symbol table produced by the parser.
     *
     * @return the symbol table
     */
    public SymbolTable symbolTable() {
        return symbolTable;
    }

    /**
     * Returns the syntax tree produced by the parser.
     *
     * @return the syntax tree
     */
    public AbstractSyntaxTree abstractSyntaxTree() {
        return abstractSyntaxTree;
    }
}
