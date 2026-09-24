package parser.internals;

import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

import java.util.ArrayList;
import java.util.List;

public final class ParsingContext {
    private final SymbolTable symbolTable;

    private final List<String> declaredIdentifiers;
    private final LexemeInfoBuilder metadataBuilder;

    private final NameMangler outerScopes;
    private final NameMangler searchScope;
    private final NameMangler nestedFields;

    public ParsingContext(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.declaredIdentifiers = new ArrayList<>();
        this.metadataBuilder = new LexemeInfoBuilder();
        this.outerScopes = new NameMangler();
        this.searchScope = new NameMangler();
        this.nestedFields = new NameMangler();
    }

    public SymbolTable symbolTable() {
        return symbolTable;
    }

    public List<String> declaredIdentifiers() {
        return declaredIdentifiers;
    }

    public LexemeInfoBuilder metadataBuilder() {
        return metadataBuilder;
    }

    public NameMangler outerScopes() {
        return outerScopes;
    }

    public NameMangler searchScope() {
        return searchScope;
    }

    public NameMangler nestedFields() {
        return nestedFields;
    }
}
