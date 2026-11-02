package parser.publishers.leafs;

import parser.initializations.Initialization;
import parser.publishers.Publisher;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

/**
 * Base declaration of a symbol that is published to the symbol table.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public abstract class Declaration implements Publisher {
    protected String declarationScope;
    protected final LexemeInfoBuilder builder;

    /**
     * Creates a new declaration publisher.
     */
    public Declaration() {
        this(new LexemeInfoBuilder());
    }

    public Declaration(LexemeInfoBuilder initBuilder) {
        builder = initBuilder;
        declarationScope = "";
    }

    public String scope() {
        return (declarationScope.isEmpty())
                ? ""
                : declarationScope + '#';
    }

    public Declaration scope(String newScope) {
        declarationScope = newScope;
        return this;
    }

    @Override
    public Declaration type(Type newType) {
        builder.type(newType);
        return this;
    }

    @Override
    public Declaration subtype(Subtype newSubtype) {
        builder.subtype(newSubtype);
        return this;
    }

    @Override
    public Declaration customType(String newCustomType) {
        builder.customType(newCustomType);
        return this;
    }

    public String customType() {
        return builder.customType();
    }

    @Override
    public Declaration source(Source newSource){
        builder.source(newSource);
        return this;
    }

    @Override
    public Declaration use(Use newUse) {
        builder.use(newUse);
        return this;
    }

    @Override
    public Declaration inferiorLimits(List<String> newInferiorLimits) {
        builder.inferiorLimits(newInferiorLimits);
        return this;
    }

    @Override
    public Declaration superiorLimits(List<String> newSuperiorLimits) {
        builder.superiorLimits(newSuperiorLimits);
        return this;
    }

    @Override
    public Declaration parameters(List<String> newParameters) {
        builder.parameters(newParameters);
        return this;
    }

    public List<String> parameters() {
        return builder.parameters();
    }

    @Override
    public Declaration initialValue(Object newInitialValue){
        builder.initialValue(newInitialValue);
        return this;
    }

    public Initialization initialValue() {
        return (Initialization) builder.initialValue();
    }
}
