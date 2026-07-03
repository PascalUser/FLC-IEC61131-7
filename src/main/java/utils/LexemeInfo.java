package utils;

import utils.enums.*;

import java.util.List;

public class LexemeInfo {
    public Type type;
    public Subtype subtype;
    public String customType;
    public Use use;
    public Source source;
    public String inferiorLimit;
    public String superiorLimit;
    public List<String> parameters;
    public Object initialValue;

    public LexemeInfo(
        Type type,
        Subtype subtype,
        String customType,
        Use use,
        Source source,
        String inferiorLimit,
        String superiorLimit,
        List<String> parameters,
        Object initialValue
    ) {
        this.type = type;
        this.subtype = subtype;
        this.customType = customType;
        this.use = use;
        this.source = source;
        this.inferiorLimit = inferiorLimit;
        this.superiorLimit = superiorLimit;
        this.parameters = parameters;
        this.initialValue = initialValue;
    }
}
