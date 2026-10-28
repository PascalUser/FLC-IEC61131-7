package utils.diagnostics;

/**
 * A rule or block names a variable or term that is not declared (IEC 61131-7 requires every name used in a
 * rule block to be declared). Fatal.
 * <p>
 * Detected after parsing, while building the controller model, because the parser does not check references
 * yet; the message names the element instead of a source line.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class UndeclaredReference extends Error {
    private final String detail;

    /**
     * Creates the diagnostic.
     *
     * @param detail what is undeclared and where, e.g. {@code "term HOT of TEMP in rule 2 of MAIN#RULES"}
     */
    public UndeclaredReference(final String detail) {
        super(0);
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return "ERROR: undeclared " + detail;
    }
}
