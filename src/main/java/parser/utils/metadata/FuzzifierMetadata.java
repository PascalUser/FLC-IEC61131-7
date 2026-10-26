package parser.utils.metadata;

import parser.publishers.TermBlock;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

public final class FuzzifierMetadata implements LexemeInfoView {
    List<String> terms;

    public FuzzifierMetadata(TermBlock termBlock) {
        this.terms = termBlock.getTermNames();
    }

    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    @Override
    public Subtype subtype() {
        return Subtype.NONE;
    }

    @Override
    public String customType() {
        return null;
    }

    @Override
    public Source source() {
        return Source.FUZZIFY;
    }

    @Override
    public Use use() {
        return Use.COMPONENT;
    }

    @Override
    public List<String> inferiorLimits() {
        return null;
    }

    @Override
    public List<String> superiorLimits() {
        return null;
    }

    public List<String> parameters() {
        return terms;
    }

    @Override
    public Object initialValue() {
        return null;
    }
}
