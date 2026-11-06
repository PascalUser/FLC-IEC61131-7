package compiler.parser.metadata;

import compiler.parser.initializations.leafs.SubrangeInitialization;
import compiler.repository.builders.LexemeInfoView;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

import java.util.Collections;
import java.util.List;

/**
 * Metadata of a subrange type.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class SubrangeMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final String ilimit;
    private final String slimit;
    private final SubrangeInitialization initialValue;

    /**
     * Creates the metadata of a subrange type.
     *
     * @param initSubtype subtype of the subrange
     * @param initIlimit inferior limit
     * @param initSlimit superior limit
     */
    public SubrangeMetadata(Subtype initSubtype, String initIlimit, String initSlimit) {
        subtype = initSubtype;
        ilimit = initIlimit;
        slimit = initSlimit;
        initialValue = new SubrangeInitialization(initIlimit, initSlimit);
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.SUBRANGE;
    }

    /**
     * Returns the subtype of this subrange.
     *
     * @return the subtype of the subrange
     */
    public Subtype subtype() {
        return subtype;
    }

    /** {@inheritDoc} */
    @Override
    public String customType() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Source source() {
        return Source.UNKNOWN;
    }

    /** {@inheritDoc} */
    @Override
    public Use use() {
        return Use.UNKNOWN;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> inferiorLimits() {
        return Collections.singletonList(ilimit);
    }

    /** {@inheritDoc} */
    @Override
    public List<String> superiorLimits() {
        return Collections.singletonList(slimit);
    }

    /** {@inheritDoc} */
    @Override
    public List<String> parameters() {
        return null;
    }

    /**
     * Returns the initialization of this subrange.
     *
     * @return the initialization of the subrange
     */
    public SubrangeInitialization initialValue() {
        return initialValue;
    }
}
