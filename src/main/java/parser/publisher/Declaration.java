package parser.publisher;

import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;

import java.util.List;

public class Declaration implements Publisher {
    private final SymbolTable symbolTable;
    private final List<String> variables;
    private final LexemeInfoBuilder builder;

    public Declaration(SymbolTable initTable, List<String> identifiers, LexemeInfoBuilder builder) {
        this.symbolTable = initTable;
        this.variables = identifiers;
        this.builder = builder;
    }

    @Override
    public void publish(Source source) {
        for (String variable : variables) {
            symbolTable.put(
                variable,
                builder.source(source).build()
            );

        }
    }
}
