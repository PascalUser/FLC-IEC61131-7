package parser.utils;

/**
 * Parsed identifier: the complete name, its scope and its plain name.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class ParsedIdentifier {
    private final String complete;
    private final String scope;
    private final String identifier;


    /**
     * Creates a parsed identifier: the complete name, its scope and its plain name.
     *
     * @param complete  the complete identifier name
     * @param scope     the scope of the identifier, empty when it has none
     * @param identifier the plain identifier name
     */
    public ParsedIdentifier(final String complete, final String scope, final String identifier) {
        this.complete = complete;
        this.scope = scope;
        this.identifier = identifier;
    }

    /**
     * Returns the complete identifier name, used as the mangled key.
     *
     * @return the complete identifier name
     */
    public String mangled() {
        return complete;
    }

    /**
     * Returns the scope of the identifier, empty when it has none.
     *
     * @return the scope of the identifier
     */
    public String scope() {
        return scope;
    }

    /**
     * Returns the plain identifier name.
     *
     * @return the plain identifier name
     */
    public String name() {
        return identifier;
    }
}
