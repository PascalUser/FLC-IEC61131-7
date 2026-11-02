package parser.facades;

import parser.utils.ParsedIdentifier;

/**
 * Parser of identifiers that may carry a scope suffix after a hash character.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class IdentifierParser {

    private IdentifierParser() {
        // Utility class - not instantiable
    }

    /**
     * Creates a parsed identifier from a name that may carry a scope suffix.
     *
     * @param identifier   the identifier, optionally suffixed with a scope after a hash character
     * @param possibleScope the scope of the identifier, or {@code null} when it is unknown
     * @return the parsed identifier
     */
    public static ParsedIdentifier parse(final String identifier, final String possibleScope) {
        int octothorpeIdx = identifier.indexOf('#');
        if (-1 == octothorpeIdx) {
            if (possibleScope != null && !possibleScope.isEmpty()) {
                return new ParsedIdentifier(possibleScope + '#' + identifier, possibleScope, identifier);
            }
            return new ParsedIdentifier(identifier, "", identifier);
        }
        String name = identifier.substring(octothorpeIdx + 1);
        String scope = identifier.substring(0, octothorpeIdx);
        return new ParsedIdentifier(identifier, scope, name);
    }
}
