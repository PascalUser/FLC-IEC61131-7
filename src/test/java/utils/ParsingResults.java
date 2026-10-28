package utils;


import ast.AbstractSyntaxTree;

import java.util.List;

public final class ParsingResults {
    private final SymbolTable symbolTable;
    private final AbstractSyntaxTree abstractSyntaxTree;
    private final List<String> interfaceVariables;

    public ParsingResults(
            SymbolTable symbolTable, AbstractSyntaxTree abstractSyntaxTree, List<String> interfaceVariables
    ) {
        this.symbolTable = symbolTable;
        this.abstractSyntaxTree = abstractSyntaxTree;
        this.interfaceVariables = interfaceVariables;
    }

    public SymbolTable symbolTable() {
        return symbolTable;
    }

    public AbstractSyntaxTree abstractSyntaxTree() {
        return abstractSyntaxTree;
    }

    /** Keys of the VAR_INPUT / VAR_OUTPUT variables in declaration order. */
    public List<String> interfaceVariables() {
        return interfaceVariables;
    }
}
