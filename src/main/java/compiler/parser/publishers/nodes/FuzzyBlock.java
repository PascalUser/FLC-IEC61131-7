package compiler.parser.publishers.nodes;

import compiler.parser.publishers.leafs.single.SingleDeclaration;

import java.util.ArrayList;
import java.util.List;

/**
 * Block that groups declarations of fuzzy variables.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class FuzzyBlock extends Block<SingleDeclaration> {

    /** {@inheritDoc} */
    @Override
    public void add(SingleDeclaration declaration) {
        declarations.add(declaration);
    }

    /**
     * Returns the names of the terms declared in the block.
     *
     * @return the term names in source order
     */
    public List<String> getTermNames() {
        List<String> copy = new ArrayList<>();
        for (SingleDeclaration declaration : declarations) {
            copy.add(declaration.identifier());
        }
        return copy;
    }
}
