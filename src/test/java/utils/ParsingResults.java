package utils;


import ast.AbstractSyntaxTree;

public final class ParsingResults {
    private final SymbolTable symbolTable;
    private final AbstractSyntaxTree abstractSyntaxTree;

    public ParsingResults(SymbolTable symbolTable, AbstractSyntaxTree abstractSyntaxTree) {
        this.symbolTable = symbolTable;
        this.abstractSyntaxTree = abstractSyntaxTree;
    }

    public SymbolTable symbolTable() {
        return symbolTable;
    }

    public AbstractSyntaxTree abstractSyntaxTree() {
        return abstractSyntaxTree;
    }
}
