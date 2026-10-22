package parser.utils.metadata;

public final class MacroMetadata {
    private final Integer replaceValue;

    public MacroMetadata(Integer initValue) {
        replaceValue = initValue;
    }

    public Integer replaceValue() {
        return replaceValue;
    }
}
