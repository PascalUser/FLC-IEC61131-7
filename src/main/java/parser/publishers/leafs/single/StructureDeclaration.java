package parser.publishers.leafs.single;

import parser.publishers.nodes.StructureBlock;
import utils.SymbolTable;

/**
 * Declaration of a structure type.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class StructureDeclaration extends SingleDeclaration {
    private final StructureBlock structBlock;

    public StructureDeclaration(SymbolTable initTable, StructureBlock initStructBlock) {
        super(initTable);
        structBlock = initStructBlock;
    }

    @Override
    public StructureDeclaration identifier(String newIdentifier) {
        super.identifier(newIdentifier);
        structBlock.scope(newIdentifier);
        return this;
    }

    @Override
    public void publish() {
        structBlock.publish();
        super.publish();
    }
}
