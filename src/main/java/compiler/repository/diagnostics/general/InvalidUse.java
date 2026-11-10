package compiler.repository.diagnostics.general;

public final class InvalidUse extends GeneralError {
    private final String detail;

    /**
     * Creates the diagnostic.
     *
     * @param detail what is not supported and where, e.g. {@code "rule 3 of MAIN#RULES uses OR"}
     */
    public InvalidUse(final String detail) {
        super();
        this.detail = detail;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return super.getMessage() + "invalid use: " + detail;
    }
}
