package parser.initializations.leafs;

import java.util.Objects;

/**
 * Represents the lower bound initialization for a subrange type.
 * <p>
 * Stores the inferior limit (lower bound) of a subrange declaration.
 * The superior limit is accepted in the constructor for compatibility but
 * not stored, as only the lower bound is needed for initialization purposes.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class SubrangeInitialization extends LeafInitialization {
    private final String ilimit;

    /**
     * Creates an initialization for a subrange.
     *
     * @param ilimit inferior limit of the subrange
     * @param ignoredSlimit superior limit; accepted for compatibility but not stored
     */
    public SubrangeInitialization(String ilimit, String ignoredSlimit) {
        this.ilimit = ilimit;
    }

    /** {@inheritDoc} */
    @Override
    public String variableValue() {
        return ilimit;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubrangeInitialization)) return false;
        SubrangeInitialization other = (SubrangeInitialization) o;
        return ilimit.equals(other.ilimit);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(ilimit);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "SubrangeInitialization{" + ilimit + "}";
    }
}
