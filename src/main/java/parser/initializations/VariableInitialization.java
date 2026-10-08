package parser.initializations;

import java.util.Objects;

/**
 * Initialization for explicit variable/constant assignments (IEC 61131-7).
 * <p>
 * Represents a user-provided initial value (e.g., {@code x := 10}, {@code y := 3.14}).
 * Wraps the literal value as a string for storage in the symbol table.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see Initialization
 */
public final class VariableInitialization implements Initialization {
    private final String value;

    /**
     * Creates a variable initialization with the given literal value.
     *
     * @param value the literal value as string (e.g., "10", "3.14", "TRUE")
     */
    public VariableInitialization(final String value) {
        this.value = value;
    }

    @Override
    public VariableInitialization selectVariable(final String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VariableInitialization copy() {
        return new VariableInitialization(value);
    }

    /**
     * Value-object equality based on the wrapped value.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariableInitialization)) {
            return false;
        }
        VariableInitialization other = (VariableInitialization) o;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "VariableInitialization{" + value + "}";
    }
}