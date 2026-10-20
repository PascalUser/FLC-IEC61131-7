package parser.publishers;

import parser.initializations.Initialization;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

public abstract class Declaration implements Publisher {
    protected String declarationScope;
    protected final LexemeInfoBuilder builder;

    /**
     * Creates a new declaration publisher.
     */
    public Declaration() {
        builder = new LexemeInfoBuilder();
    }

    public Declaration(LexemeInfoBuilder initBuilder) {
        builder = initBuilder;
    }

    public void setDeclarationScope(String newScope) {
        declarationScope = newScope;
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
