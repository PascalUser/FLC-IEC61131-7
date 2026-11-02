package parser.publishers.nodes;

import parser.publishers.leafs.Declaration;
import parser.publishers.Publisher;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.ArrayList;
import java.util.List;

/**
 * Base of the blocks that group declarations.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public abstract class Block<ConcreteDecl extends Declaration> implements Publisher {
    protected final List<ConcreteDecl> declarations;

    public Block() {
        this.declarations = new ArrayList<>();
    }

    public abstract void add(ConcreteDecl declaration);

    @Override
    public Block<ConcreteDecl> scope(String newScope) {
        for (Declaration declaration : declarations) {
            declaration.scope(newScope);
        }
        return this;
    }

    @Override
    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        for (ConcreteDecl declaration : declarations) {
            keys.addAll(declaration.keys());
        }
        return keys;
    }

    @Override
    public void publish() {
        for (Declaration declaration : declarations) {
            declaration.publish();
        }
    }

    @Override
    public Block<ConcreteDecl> type(Type newType) {
        for (Declaration declaration : declarations) {
            declaration.type(newType);
        }
        return this;
    }

    @Override
    public Block<ConcreteDecl> subtype(Subtype newSubtype) {
        for (Declaration declaration : declarations) {
            declaration.subtype(newSubtype);
        }
        return this;
    }

    @Override
    public Block<ConcreteDecl> customType(String newCustomType) {
        for (Declaration declaration : declarations) {
            declaration.customType(newCustomType);
        }
        return this;
    }

    @Override
    public Block<ConcreteDecl> source(Source newSource){
        for (Declaration declaration : declarations){
            declaration.source(newSource);
        }
        return this;
    }

    @Override
    public Block<ConcreteDecl> use(Use newUse) {
        for (Declaration declaration : declarations){
            declaration.use(newUse);
        }
        return this;
    }

    @Override
    public Block<ConcreteDecl> inferiorLimits(List<String> newInferiorLimits) {
        for (Declaration declaration : declarations){
            declaration.inferiorLimits(newInferiorLimits);
        }
        return this;
    }

    @Override
    public Block<ConcreteDecl> superiorLimits(List<String> newSuperiorLimits) {
        for (Declaration declaration : declarations){
            declaration.superiorLimits(newSuperiorLimits);
        }
        return this;
    }

    @Override
    public Block<ConcreteDecl> parameters(List<String> newParameters) {
        for (Declaration declaration : declarations){
            declaration.parameters(newParameters);
        }
        return this;
    }

    @Override
    public Block<ConcreteDecl> initialValue(Object newInitialValue){
        for (Declaration declaration : declarations){
            declaration.initialValue(newInitialValue);
        }
        return this;
    }
}
