package utils.builders;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import utils.LexemeInfo;
import utils.enums.*;

import java.util.List;

/**
 * Builder for constructing {@link LexemeInfo} instances using a fluent API.
 * <p>
 * Allows step-by-step configuration of semantic attributes before building
 * the final immutable {@code LexemeInfo} object.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */

@SuppressFBWarnings(
    value = {"EI_EXPOSE_REP2"},
    justification = "LexemeInfoBuilder is a builder of a DTO with direct collection references by design"
)
/**
 * Builder that assembles the lexical information of a symbol.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class LexemeInfoBuilder implements LexemeInfoMutator, LexemeInfoView {
    private Type type                   = Type.UNKNOWN;
    private Subtype subtype             = Subtype.UNKNOWN;
    private String customType           = null;
    private Use use                     = Use.UNKNOWN;
    private Source source               = Source.UNKNOWN;
    private List<String> inferiorLimit  = null;
    private List<String> superiorLimit  = null;
    private List<String> parameters     = null;
    private Object initialValue         = null;

    /** {@inheritDoc} */
    @Override
    public LexemeInfoBuilder type(Type type) {
        this.type = type;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return type;
    }

    /** {@inheritDoc} */
    @Override
    public LexemeInfoBuilder subtype(Subtype subtype) {
        this.subtype = subtype;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Subtype subtype() {
        return subtype;
    }

    /** {@inheritDoc} */
    @Override
    public LexemeInfoBuilder customType(String customType) {
        this.customType = customType;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public String customType() {
        return customType;
    }

    /** {@inheritDoc} */
    @Override
    public LexemeInfoBuilder source(Source source) {
        this.source = source;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Source source() {
        return source;
    }

    /** {@inheritDoc} */
    @Override
    public LexemeInfoBuilder use(Use use) {
        this.use = use;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Use use() {
        return use;
    }

    /** {@inheritDoc} */
    @Override
    public LexemeInfoBuilder inferiorLimits(List<String> inferiorLimits) {
        this.inferiorLimit = inferiorLimits;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> inferiorLimits() {
        return inferiorLimit;
    }

    /** {@inheritDoc} */
    @Override
    public LexemeInfoBuilder superiorLimits(List<String> superiorLimits) {
        this.superiorLimit = superiorLimits;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> superiorLimits() {
        return superiorLimit;
    }

    /** {@inheritDoc} */
    @Override
    public LexemeInfoBuilder parameters(List<String> parameters) {
        this.parameters = parameters;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> parameters() {
        return parameters;
    }

    /** {@inheritDoc} */
    @Override
    public LexemeInfoBuilder initialValue(Object initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Object initialValue() {
        return initialValue;
    }

    /**
     * Builds the {@link LexemeInfo} from the configured attributes.
     *
     * @return a new LexemeInfo instance
     */
    public LexemeInfo build() {
        return new LexemeInfo(
            this.type,
            this.subtype,
            this.customType,
            this.use,
            this.source,
            this.inferiorLimit,
            this.superiorLimit,
            this.parameters,
            this.initialValue
        );
    }
}
