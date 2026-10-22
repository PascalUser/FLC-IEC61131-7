package parser.utils.metadata;

import utils.enums.Subtype;

import java.util.List;

public final class ArrayMetadata {
    private final Subtype subtype;
    private final String customType;
    private final List<String> ranges;

    public ArrayMetadata(Subtype subtype, String customType, List<String> ranges) {
        this.subtype = subtype;
        this.customType = customType;
        this.ranges = ranges;
    }


    public Subtype subtype() {
        return subtype;
    }

    public String customType() {
        return customType;
    }

    public List<String> ranges() {
        return ranges;
    }
}
