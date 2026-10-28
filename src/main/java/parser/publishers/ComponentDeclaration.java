package parser.publishers;

import utils.SymbolTable;
import utils.enums.Source;

public final class ComponentDeclaration extends SingleDeclaration {
    private final TermBlock termDeclarations;
    private String suffix;

    public ComponentDeclaration(SymbolTable initTable, TermBlock terms) {
        super(initTable);
        termDeclarations = terms;
        suffix = "";
    }

    @Override
    public ComponentDeclaration source(Source newSource) {
        if (termDeclarations != null) {
            termDeclarations.source(newSource);
        }
        super.source(newSource);
        switch (newSource) {
            case FUZZIFY:
                suffix = "@fuzzifier";
                break;
            case DEFUZZIFY:
                suffix = "@defuzzifier";
                break;
            default:
                break;

        }
        return this;
    }

    @Override
    public void publish() {
        if (termDeclarations != null) {
            termDeclarations.setDeclarationScope(identifier);
            termDeclarations.publish();
        }
        super.publish();
    }

    @Override
    protected String key() {
        return super.key() + suffix;
    }
}
