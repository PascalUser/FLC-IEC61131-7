package compiler.parser.utils;

public final class IdentifierResolver {
    private final String programName;
    private final int octothorpeIdx;

    public IdentifierResolver(String programName) {
        this.programName = programName;
        this.octothorpeIdx = programName.length();
    }

    public String key(String identifier) {
        return programName + "#" + identifier;
    }

    public String name(String identifier) {
        return identifier.substring(octothorpeIdx + 1);
    }
}
