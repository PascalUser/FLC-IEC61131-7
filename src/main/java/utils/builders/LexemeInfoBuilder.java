package utils.builders;

import utils.LexemeInfo;
import utils.enums.ElementaryType;
import utils.enums.Source;
import utils.enums.Use;

public class LexemeInfoBuilder implements LexemeInfoSchema {
    private ElementaryType type = ElementaryType.UNKNOWN;
    private String customType   = null;
    private Use use             = Use.UNKNOWN;
    private Source source       = Source.UNKNOWN;
    private Object value        = null;

    public LexemeInfoBuilder type(ElementaryType type) {
        this.type = type;
        return this;
    }

    public LexemeInfoBuilder customType(String customType) {
        this.customType = customType;
        return this;
    }

    public LexemeInfoBuilder source(Source source) {
        this.source = source;
        return this;
    }

    public LexemeInfoBuilder use(Use use) {
        this.use = use;
        return this;
    }

    public LexemeInfoBuilder value(Object value) {
        this.value = value;
        return this;
    }

    public LexemeInfo build() {
        return new LexemeInfo(this.type, this.customType, this.use, this.source, this.value);
    }
}