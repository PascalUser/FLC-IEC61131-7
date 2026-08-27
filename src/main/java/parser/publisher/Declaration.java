package parser.publisher;

import utils.SymbolTable;
import utils.builders.*;
import utils.enums.*;

import java.util.List;

/**
 * Publisher for variable and constant declarations.
 * <p>
 * Collects a list of identifiers sharing the same type and attributes,
 * and publishes them to the symbol table when {@link #publish()} is called.
 * Used by the parser to handle declarations like {@code VAR x, y : INT;}.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public class Declaration implements Publisher {
    private final SymbolTable symbolTable;
    private final List<String> variables;
    private final LexemeInfoBuilder builder;

    /**
     * Creates a new declaration publisher.
     *
     * @param initTable   the symbol table to publish to
     * @param identifiers list of variable names
     * @param builder     the builder with shared attributes
     */
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
    public LexemeInfoSchema inferiorLimit(String inferiorLimit) {
        builder.inferiorLimit(inferiorLimit);
        return this;
    }

    @Override
    public LexemeInfoSchema superiorLimit(String superiorLimit) {
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

    // TODO: Verificar si hay que poner putIfAbsent en este método
    // TODO: Ver cómo se chequean las redeclaraciones

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
