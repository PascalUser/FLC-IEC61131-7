package parser.initializations.leafs;

import java.util.List;
import java.util.Objects;

/**
 * Represents an initialization for an enumerated type value.
 * <p>
 * Holds the selected enumeration literal (the first value from the provided
 * list of enumerated values). Used for variables of enumerated types.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class EnumeratedInitialization extends LeafInitialization {
    private final String enumeratedValue;

    public EnumeratedInitialization(List<String> enumeratedValues) {
        this.enumeratedValue = enumeratedValues.get(0);
    }

    @Override
    public String variableValue() {
        return enumeratedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnumeratedInitialization)) return false;
        EnumeratedInitialization other = (EnumeratedInitialization) o;
        return enumeratedValue.equals(other.enumeratedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(enumeratedValue);
    }

    @Override
    public String toString() {
        return "EnumeratedInitialization{" + enumeratedValue + "}";
    }
}