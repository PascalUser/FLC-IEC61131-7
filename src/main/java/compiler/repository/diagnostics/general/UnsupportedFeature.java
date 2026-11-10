package compiler.repository.diagnostics.general;

/**
 * The program is valid FCL but uses something the code generator cannot produce (e.g. {@code OR} in a rule,
 * a {@code REAL} point, a rule that does not use every input). Fatal: no code is generated.
 * <p>
 * Reported after parsing, from the symbol table and the AST, which do not keep source lines; the message
 * names the element instead.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class UnsupportedFeature extends GeneralError {
    private final String detail;

    /**
     * Creates the diagnostic.
     *
     * @param detail what is not supported and where, e.g. {@code "rule 3 of MAIN#RULES uses OR"}
     */
    public UnsupportedFeature(final String detail) {
        super();
        this.detail = detail;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return super.getMessage() + "not supported by the code generator: " + detail;
    }
}
