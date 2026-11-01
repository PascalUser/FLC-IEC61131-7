package parser.publishers.nodes;

import parser.publishers.leafs.Declaration;

public final class DeclarationBlock extends Block<Declaration> {

    public DeclarationBlock() {
        super();
    }

    @Override
    public void add(Declaration newDeclaration) {
        declarations.add(newDeclaration);
    }
}
