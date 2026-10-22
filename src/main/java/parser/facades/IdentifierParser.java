package parser.facades;

import parser.utils.ParsedIdentifier;

public final class IdentifierParser {
    public static ParsedIdentifier parse(String identifier, String possibleScope) {
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
