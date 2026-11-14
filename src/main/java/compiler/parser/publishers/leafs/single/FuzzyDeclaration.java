package compiler.parser.publishers.leafs.single;

import compiler.parser.facades.ComponentResolver;
import compiler.parser.publishers.nodes.FuzzyBlock;
import compiler.repository.SymbolTable;
import compiler.repository.enums.Source;

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

    /**
     * Creates the declaration of a fuzzy variable.
     *
     * @param initTable symbol table that receives the published symbols
     * @param terms terms declared in the FUZZIFY or DEFUZZIFY block
     */
    public FuzzyDeclaration(SymbolTable initTable, FuzzyBlock terms) {
        super(initTable);
        termDeclarations = terms;
    }

    /** {@inheritDoc} */
    @Override
    public FuzzyDeclaration source(Source newSource) {
        if (termDeclarations != null) {
            termDeclarations.source(newSource);
        }
        super.source(newSource);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void publish() {
        if (termDeclarations != null) {
            termDeclarations.scope(super.key()).publish();
        }
        super.publish();
    }

    /** {@inheritDoc} */
    @Override
    protected String key() {
        return ComponentResolver.resolve(symbolTable, super.key());
    }
}
