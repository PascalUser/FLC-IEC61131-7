package parser.initializations;

import java.util.Objects;

/**
 * Default initialization for SUBRANGE types (IEC 61131-7).
 * <p>
 * Represents the lower bound as the default value for uninitialized subrange
 * variables. Stores both lower and upper bounds for validation purposes.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see Initialization
 */
public final class SubrangeInitialization implements Initialization {
    private final String ilimit;
    private final String slimit;

    /**
     * Creates a subrange default initialization.
     *
     * @param ilimit lower bound (inclusive)
     * @param slimit upper bound (inclusive)
     */
    public SubrangeInitialization(String ilimit, String slimit) {
        this.ilimit = ilimit;
        this.slimit = slimit;
    }

    @Override
    public Initialization selectVariable(String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return ilimit;
    }

    @Override
    public Initialization copy() {
        return new SubrangeInitialization(ilimit, slimit);
    }

    /**
     * Value-object equality based on both bounds.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubrangeInitialization)) {
            return false;
        }
        SubrangeInitialization other = (SubrangeInitialization) o;
        return Objects.equals(this.ilimit, other.ilimit)
            && Objects.equals(this.slimit, other.slimit);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ilimit + slimit);
    }

    @Override
    public String toString() {
        return "SubrangeInitialization{" + ilimit + ".." + slimit + "}";
    }
}
