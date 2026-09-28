package parser.internals;

/**
 * Generates qualified (mangled) names for symbols in nested scopes.
 * <p>
 * IEC 61131-7 uses a flat namespace for all declarations, but the language supports
 * nested structures (structs, enums within types, arrays of structs). This class
 * generates globally unique keys by prefixing identifiers with their enclosing
 * scopes using {@code #} as a separator (not valid in IEC identifiers).
 * </p>
 * <p>
 * Example mangled names:
 * <ul>
 *   <li>{@code COLOR_TYPE} (top-level type)</li>
 *   <li>{@code COLOR_TYPE#CLASSIFICATION} (struct field)</li>
 *   <li>{@code COLOR_TYPE#CLASSIFICATION#WHITE} (enum value)</li>
 *   <li>{@code PIXELS} (array variable)</li>
 * </ul>
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class NameMangler {
    private final StringBuilder prefix = new StringBuilder();

    /**
     * Appends a scope to the current prefix.
     *
     * @param scope the scope name to append (e.g., type name, field name)
     */
    public void addScope(final String scope) {
        prefix.append(scope).append('#');
    }

    /**
     * Returns the current fully qualified scope prefix (without trailing {@code #}).
     *
     * @return the current scope, or empty string if none
     */
    public String getCurrentScope() {
        int length = prefix.length();
        if (length == 0) return "";
        return prefix.substring(0, length - 1);
    }

    /**
     * Removes and returns the last scope from the prefix.
     *
     * @return the removed scope name, or empty string if none
     */
    public String popScope() {
        int length = prefix.length();
        if (length == 0) return "";

        int prevHash = prefix.lastIndexOf("#", length - 2);
        String removed = prefix.substring(prevHash + 1, length - 1);

        prefix.setLength(prevHash + 1);

        return removed;
    }

    /**
     * Returns the fully mangled name for an identifier in the current scope.
     *
     * @param name the base identifier
     * @return the mangled name (e.g., {@code TYPE#FIELD#VALUE})
     */
    public String getNameMangled(final String name) {
        return prefix + name;
    }
}
