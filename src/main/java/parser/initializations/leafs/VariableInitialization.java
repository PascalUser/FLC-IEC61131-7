package parser.initializations.leafs;

import java.util.Objects;

/**
 * Represents an initialization for a variable reference.
 * <p>
 * Holds the name of a variable that serves as an initialization value.
 * Used when a variable is initialized with another variable's value.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class VariableInitialization extends LeafInitialization {
    private final String value;

    public VariableInitialization(String value) {
        this.value = value;
    }

    @Override
    public String variableValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariableInitialization)) return false;
        VariableInitialization other = (VariableInitialization) o;
        return value.equals(other.value);
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