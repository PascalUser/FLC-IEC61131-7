package parser.publisher;

import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.ElementaryType;
import utils.enums.Source;
import utils.enums.Use;

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

    public Declaration type(ElementaryType type) {
        builder.type(type);
        return this;
    }

    public Declaration customType(String customType) {
        builder.customType(customType);
        return this;
    }

    public Declaration source(Source source){
        builder.source(source);
        return this;
    }

    public Declaration use(Use use) {
        builder.use(use);
        return this;
    }

    public Declaration value(Object value){
        builder.value(value);
        return this;
    }

    @Override
    public void publish() {
        for (String variable : variables) {
            symbolTable.put(
                variable,
                builder.build()
            );

        }
    }
}
