package parser.publishers;

import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.ArrayList;
import java.util.List;

public class DeclarationGroup implements Publisher {
    protected final List<Declaration> declarations;

    public DeclarationGroup() {
        this.declarations = new ArrayList<>();
    }

    @Override
    public void setDeclarationScope(String newScope) {
        for (Declaration declaration : declarations) {
            declaration.setDeclarationScope(newScope);
        }
    }

    @Override
    public void publish() {
        for (Declaration declaration : declarations) {
            declaration.publish();
        }
    }

    @Override
    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        for (Declaration declaration : declarations) {
            keys.addAll(declaration.keys());
        }
        return keys;
    }

    @Override
    public DeclarationGroup type(Type newType) {
        for (Declaration declaration : declarations) {
            declaration.type(newType);
        }
        return this;
    }

    @Override
    public DeclarationGroup subtype(Subtype newSubtype) {
        for (Declaration declaration : declarations) {
            declaration.subtype(newSubtype);
        }
        return this;
    }

    @Override
    public DeclarationGroup customType(String newCustomType) {
        for (Declaration declaration : declarations) {
            declaration.customType(newCustomType);
        }
        return this;
    }

    @Override
    public DeclarationGroup source(Source newSource){
        for (Declaration declaration : declarations){
            declaration.source(newSource);
        }
        return this;
    }

    @Override
    public DeclarationGroup use(Use newUse) {
        for (Declaration declaration : declarations){
            declaration.use(newUse);
        }
        return this;
    }

    @Override
    public DeclarationGroup inferiorLimits(List<String> newInferiorLimits) {
        for (Declaration declaration : declarations){
            declaration.inferiorLimits(newInferiorLimits);
        }
        return this;
    }

    @Override
    public DeclarationGroup superiorLimits(List<String> newSuperiorLimits) {
        for (Declaration declaration : declarations){
            declaration.superiorLimits(newSuperiorLimits);
        }
        return this;
    }

    @Override
    public DeclarationGroup parameters(List<String> newParameters) {
        for (Declaration declaration : declarations){
            declaration.parameters(newParameters);
        }
        return this;
    }

    @Override
    public DeclarationGroup initialValue(Object newInitialValue){
        for (Declaration declaration : declarations){
            declaration.initialValue(newInitialValue);
        }
        return this;
    }
}
