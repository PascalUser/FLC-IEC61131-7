package parser.publishers.nodes;

import parser.publishers.leafs.Declaration;

/**
 * Block that groups declarations of regular variables.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class DeclarationBlock extends Block<Declaration> {

    public DeclarationBlock() {
        super();
    }

    @Override
    public void add(Declaration newDeclaration) {
        declarations.add(newDeclaration);
    }
}
