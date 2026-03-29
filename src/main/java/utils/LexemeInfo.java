package utils;

public class LexemeInfo {
    public ElementaryType type;
    public Use use;

    public LexemeInfo() {
        this.type = ElementaryType.UNKNOWN;
        this.use = Use.UNKNOWN;
    }

    public LexemeInfo(Use use) {
        this.type = ElementaryType.UNKNOWN;
        this.use = use;
    }
}
