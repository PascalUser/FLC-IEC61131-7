package utils;

import utils.enums.ElementaryType;
import utils.enums.Source;
import utils.enums.Use;

public class LexemeInfo {
    public ElementaryType type;
    public String customType;
    public Use use;
    public Source source;
    public Object value;

    public LexemeInfo(
        ElementaryType type,
        String customType,
        Use use,
        Source source,
        Object value
    ) {
        this.type = type;
        this.customType = customType;
        this.use  = use;
        this.source = source;
        this.value = value;
    }
}
