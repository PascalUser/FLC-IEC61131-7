package utils.diagnostics;

/**
 * The code is generated, but something of the program is ignored or assumed (e.g. {@code DEFAULT} is not
 * implemented, the bit width was not given). Not fatal.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class GeneratorWarning extends Warning {
    private final String detail;

    /**
     * Creates the diagnostic.
     *
     * @param detail what is ignored or assumed
     */
    public GeneratorWarning(final String detail) {
        super(0);
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return "WARNING: code generator: " + detail;
    }
}
