package parser.utils.metadata;

import utils.enums.Subtype;

public final class StringMetadata {
    private final Subtype subtype;
    private final String length;

    public StringMetadata(Subtype initSubtype, String initLength) {
        subtype = initSubtype;
        length = initLength;
    }

    public Subtype subtype() {
        return subtype;
    }

    public String length() {
        return length;
    }
}
