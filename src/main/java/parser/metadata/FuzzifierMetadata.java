package parser.metadata;

import parser.publishers.nodes.FuzzyBlock;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

/**
 * Metadata of a fuzzifier.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class FuzzifierMetadata implements LexemeInfoView {
    List<String> terms;

    public FuzzifierMetadata(FuzzyBlock fuzzyBlock) {
        this.terms = fuzzyBlock.getTermNames();
    }

    @Override
    public Type type() {
        return Type.FUNCTION;
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

    /**
     * Returns the list of terms of the fuzzifier.
     *
     * @return the terms used by the fuzzifier
     */
    public List<String> parameters() {
        return terms;
    }

    @Override
    public Object initialValue() {
        return null;
    }
}
