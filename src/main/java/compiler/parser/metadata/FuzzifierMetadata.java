package compiler.parser.metadata;

import compiler.parser.publishers.nodes.FuzzyBlock;
import compiler.repository.builders.LexemeInfoView;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

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

    /**
     * Creates the metadata of a fuzzifier.
     *
     * @param fuzzyBlock block with the terms of the fuzzifier
     */
    public FuzzifierMetadata(FuzzyBlock fuzzyBlock) {
        this.terms = fuzzyBlock.getTermNames();
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.FUNCTION;
    }

    /** {@inheritDoc} */
    @Override
    public Subtype subtype() {
        return Subtype.NONE;
    }

    /** {@inheritDoc} */
    @Override
    public String customType() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Source source() {
        return Source.FUZZIFY;
    }

    /** {@inheritDoc} */
    @Override
    public Use use() {
        return Use.COMPONENT;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> inferiorLimits() {
        return null;
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public Object initialValue() {
        return null;
    }
}
