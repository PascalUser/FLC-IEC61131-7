package compiler.parser.publishers.leafs.single;

import compiler.parser.publishers.nodes.StructureBlock;
import compiler.repository.SymbolTable;

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

    /**
     * Creates the declaration of a structure type.
     *
     * @param initTable symbol table that receives the published symbols
     * @param initStructBlock block with the fields of the structure
     */
    public StructureDeclaration(SymbolTable initTable, StructureBlock initStructBlock) {
        super(initTable);
        structBlock = initStructBlock;
    }

    /** {@inheritDoc} */
    @Override
    public StructureDeclaration identifier(String newIdentifier) {
        super.identifier(newIdentifier);
        structBlock.scope(newIdentifier);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void publish() {
        structBlock.publish();
        super.publish();
    }
}
