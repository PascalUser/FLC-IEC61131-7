package utils.builders;

import utils.LexemeInfo;
import utils.enums.*;

import java.util.List;

public class LexemeInfoBuilder implements LexemeInfoSchema {
    private Type type               = Type.UNKNOWN;
    private Subtype subtype         = Subtype.UNKNOWN;
    private String customType       = null;
    private Use use                 = Use.UNKNOWN;
    private Source source           = Source.UNKNOWN;
    private Integer inferiorLimit   = null;
    private Integer superiorLimit   = null;
    private List<String> parameters = null;
    private Object initialValue     = null;

    @Override
    public LexemeInfoBuilder type(Type type) {
        this.type = type;
        return this;
    }

    @Override
    public LexemeInfoBuilder subtype(Subtype subtype) {
        this.subtype = subtype;
        return this;
    }

    @Override
    public LexemeInfoBuilder customType(String customType) {
        this.customType = customType;
        return this;
    }

    @Override
    public LexemeInfoBuilder source(Source source) {
        this.source = source;
        return this;
    }

    @Override
    public LexemeInfoBuilder use(Use use) {
        this.use = use;
        return this;
    }

    @Override
    public LexemeInfoBuilder inferiorLimit(Integer inferiorLimit) {
        this.inferiorLimit = inferiorLimit;
        return this;
    }

    @Override
    public LexemeInfoBuilder superiorLimit(Integer superiorLimit) {
        this.superiorLimit = superiorLimit;
        return this;
    }

    @Override
    public LexemeInfoBuilder parameters(List<String> parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public LexemeInfoBuilder initialValue(Object initialValue) {
        this.initialValue = initialValue;
        return this;
    }


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