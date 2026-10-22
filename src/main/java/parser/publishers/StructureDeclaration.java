package parser.publishers;

import utils.SymbolTable;

public final class StructureDeclaration extends SingleDeclaration {
    private final StructureBlock structBlock;

    public StructureDeclaration(SymbolTable initTable, StructureBlock initStructBlock) {
        super(initTable);
        structBlock = initStructBlock;
    }

    @Override
    public StructureDeclaration identifier(String newIdentifier) {
        super.identifier(newIdentifier);
        structBlock.setDeclarationScope(newIdentifier);
        return this;
    }

    @Override
    public void publish() {
        structBlock.publish();
        super.publish();
    }
}
