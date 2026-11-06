package compiler.parser.publishers.nodes;

import compiler.parser.publishers.leafs.Declaration;

/**
 * Block that groups declarations of regular variables.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class DeclarationBlock extends Block<Declaration> {

    /**
     * Creates an empty block of regular variable declarations.
     */
    public DeclarationBlock() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public void add(Declaration newDeclaration) {
        declarations.add(newDeclaration);
    }
}
