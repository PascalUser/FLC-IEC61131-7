package parser.publisher;

import utils.SymbolTable;
import utils.builders.*;
import utils.enums.*;

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
    public Declaration type(Type type) {
        builder.type(type);
        return this;
    }

    @Override
    public Declaration subtype(Subtype subtype) {
        builder.subtype(subtype);
        return this;
    }

    @Override
    public Declaration customType(String customType) {
        builder.customType(customType);
        return this;
    }

    @Override
    public Declaration source(Source source){
        builder.source(source);
        return this;
    }

    @Override
    public Declaration use(Use use) {
        builder.use(use);
        return this;
    }

    @Override
    public LexemeInfoSchema inferiorLimit(Integer inferiorLimit) {
        builder.inferiorLimit(inferiorLimit);
        return this;
    }

    @Override
    public LexemeInfoSchema superiorLimit(Integer superiorLimit) {
        builder.superiorLimit(superiorLimit);
        return this;
    }

    @Override
    public LexemeInfoSchema parameters(List<String> parameters) {
        builder.parameters(parameters);
        return this;
    }

    @Override
    public Declaration initialValue(Object initialValue){
        builder.initialValue(initialValue);
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
