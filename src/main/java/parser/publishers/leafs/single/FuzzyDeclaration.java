package parser.publishers.leafs.single;

import parser.publishers.nodes.FuzzyBlock;
import utils.SymbolTable;
import utils.enums.Source;

/**
 * Declaration of a fuzzy variable.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class FuzzyDeclaration extends SingleDeclaration {
    private final FuzzyBlock termDeclarations;
    private String suffix;

    private final static String ERR = "FuzzyDeclaration: the component source was not FUZZIFY or DEFUZZIFY";

    public FuzzyDeclaration(SymbolTable initTable, FuzzyBlock terms) {
        super(initTable);
        termDeclarations = terms;
        suffix = "";
    }

    @Override
    public FuzzyDeclaration source(Source newSource) {
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
                throw new IllegalArgumentException(ERR);
        }
        return this;
    }

    @Override
    public void publish() {
        if (termDeclarations != null) {
            termDeclarations.scope(super.key()).publish();
        }
        super.publish();
    }

    @Override
    protected String key() {
        return super.key() + suffix;
    }
}
