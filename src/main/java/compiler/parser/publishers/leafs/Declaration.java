package compiler.parser.publishers.leafs;

import compiler.parser.initializations.Initialization;
import compiler.parser.publishers.Publisher;
import compiler.repository.builders.LexemeInfoBuilder;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

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
    /** Scope in which the declaration is published, or empty for the global one. */
    protected String declarationScope;
    /** Builder of the metadata that is published for the symbol. */
    protected final LexemeInfoBuilder builder;

    /**
     * Creates a new declaration publisher.
     */
    public Declaration() {
        this(new LexemeInfoBuilder());
    }

    /**
     * Creates a declaration that uses the given metadata builder.
     *
     * @param initBuilder builder of the metadata that is published
     */
    public Declaration(LexemeInfoBuilder initBuilder) {
        builder = initBuilder;
        declarationScope = "";
    }

    /**
     * Returns the scope prefix of the declaration, including the trailing {@code #}.
     *
     * @return the scope prefix, or an empty string for the global scope
     */
    public String scope() {
        return (declarationScope.isEmpty())
                ? ""
                : declarationScope + '#';
    }

    /**
     * Sets the scope of the declaration.
     *
     * @param newScope name of the scope
     * @return this declaration
     */
    public Declaration scope(String newScope) {
        declarationScope = newScope;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Declaration type(Type newType) {
        builder.type(newType);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Declaration subtype(Subtype newSubtype) {
        builder.subtype(newSubtype);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Declaration customType(String newCustomType) {
        builder.customType(newCustomType);
        return this;
    }

    /**
     * Returns the custom type name configured for the declaration.
     *
     * @return the custom type name, or {@code null} if none
     */
    public String customType() {
        return builder.customType();
    }

    /** {@inheritDoc} */
    @Override
    public Declaration source(Source newSource){
        builder.source(newSource);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Declaration use(Use newUse) {
        builder.use(newUse);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Declaration inferiorLimits(List<String> newInferiorLimits) {
        builder.inferiorLimits(newInferiorLimits);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Declaration superiorLimits(List<String> newSuperiorLimits) {
        builder.superiorLimits(newSuperiorLimits);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Declaration parameters(List<String> newParameters) {
        builder.parameters(newParameters);
        return this;
    }

    /**
     * Returns the parameters configured for the declaration.
     *
     * @return the parameters, or {@code null} if none
     */
    public List<String> parameters() {
        return builder.parameters();
    }

    /** {@inheritDoc} */
    @Override
    public Declaration initialValue(Object newInitialValue){
        builder.initialValue(newInitialValue);
        return this;
    }

    /**
     * Returns the initial value configured for the declaration.
     *
     * @return the initial value
     */
    public Initialization initialValue() {
        return (Initialization) builder.initialValue();
    }
}
