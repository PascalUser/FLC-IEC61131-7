package compiler.repository.diagnostics;

public class InvalidUse extends Error {
    private final String detail;

    /**
     * Creates the diagnostic.
     *
     * @param detail what is not supported and where, e.g. {@code "rule 3 of MAIN#RULES uses OR"}
     */
    public InvalidUse(final String detail) {
        super(0);
        this.detail = detail;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return "ERROR: invalid use: " + detail;
    }
}
