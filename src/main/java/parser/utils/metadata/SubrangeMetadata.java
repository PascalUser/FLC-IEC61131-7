package parser.utils.metadata;

import utils.enums.Subtype;

public final class SubrangeMetadata {
    private final Subtype subtype;
    private final String ilimit;
    private final String slimit;

    public SubrangeMetadata(Subtype initSubtype, String initIlimit, String initSlimit) {
        this.subtype = initSubtype;
        this.ilimit = initIlimit;
        this.slimit = initSlimit;
    }

    public Subtype subtype() {
        return subtype;
    }

    public String inferiorLimit() {
        return ilimit;
    }

    public String superiorLimit() {
        return slimit;
    }
}
