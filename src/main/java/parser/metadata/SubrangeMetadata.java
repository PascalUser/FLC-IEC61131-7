package parser.metadata;

import parser.initializations.leafs.SubrangeInitialization;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Collections;
import java.util.List;

public final class SubrangeMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final String ilimit;
    private final String slimit;
    private final SubrangeInitialization initialValue;

    public SubrangeMetadata(Subtype initSubtype, String initIlimit, String initSlimit) {
        subtype = initSubtype;
        ilimit = initIlimit;
        slimit = initSlimit;
        initialValue = new SubrangeInitialization(initIlimit, initSlimit);
    }

    @Override
    public Type type() {
        return Type.SUBRANGE;
    }

    public Subtype subtype() {
        return subtype;
    }

    @Override
    public String customType() {
        return null;
    }

    @Override
    public Source source() {
        return Source.UNKNOWN;
    }

    @Override
    public Use use() {
        return Use.UNKNOWN;
    }

    @Override
    public List<String> inferiorLimits() {
        return Collections.singletonList(ilimit);
    }

    @Override
    public List<String> superiorLimits() {
        return Collections.singletonList(slimit);
    }

    @Override
    public List<String> parameters() {
        return null;
    }

    public SubrangeInitialization initialValue() {
        return initialValue;
    }
}
