package parser.utils;

public final class ParsedIdentifier {
    private final String complete;
    private final String scope;
    private final String identifier;


    public ParsedIdentifier(String complete, String scope, String identifier) {
        this.complete = complete;
        this.scope = scope;
        this.identifier = identifier;
    }

    public String mangled() {
        return complete;
    }

    public String scope() {
        return scope;
    }

    public String name() {
        return identifier;
    }
}
