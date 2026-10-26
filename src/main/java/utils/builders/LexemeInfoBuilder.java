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

    @Override
    public LexemeInfoBuilder type(Type type) {
        this.type = type;
        return this;
    }

    @Override
    public Type type() {
        return type;
    }

    @Override
    public LexemeInfoBuilder subtype(Subtype subtype) {
        this.subtype = subtype;
        return this;
    }

    @Override
    public Subtype subtype() {
        return subtype;
    }

    @Override
    public LexemeInfoBuilder customType(String customType) {
        this.customType = customType;
        return this;
    }

    @Override
    public String customType() {
        return customType;
    }

    @Override
    public LexemeInfoBuilder source(Source source) {
        this.source = source;
        return this;
    }

    @Override
    public Source source() {
        return source;
    }

    @Override
    public LexemeInfoBuilder use(Use use) {
        this.use = use;
        return this;
    }

    @Override
    public Use use() {
        return use;
    }

    @Override
    public LexemeInfoBuilder inferiorLimits(List<String> inferiorLimits) {
        this.inferiorLimit = inferiorLimits;
        return this;
    }

    @Override
    public List<String> inferiorLimits() {
        return inferiorLimit;
    }

    @Override
    public LexemeInfoBuilder superiorLimits(List<String> superiorLimits) {
        this.superiorLimit = superiorLimits;
        return this;
    }

    @Override
    public List<String> superiorLimits() {
        return superiorLimit;
    }

    @Override
    public LexemeInfoBuilder parameters(List<String> parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public List<String> parameters() {
        return parameters;
    }

    @Override
    public LexemeInfoBuilder initialValue(Object initialValue) {
        this.initialValue = initialValue;
        return this;
    }

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
