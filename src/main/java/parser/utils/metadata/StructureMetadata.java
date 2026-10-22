package parser.utils.metadata;

import parser.publishers.StructureBlock;

public final class StructureMetadata {
    private final StructureBlock block;

    public StructureMetadata(StructureBlock initBlock) {
        block = initBlock;
    }

    public StructureBlock block() {
        return block;
    }
}
