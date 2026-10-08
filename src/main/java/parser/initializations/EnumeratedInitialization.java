package parser.initializations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default initialization for enumerated types (IEC 61131-7).
 * <p>
 * Represents the first enumerated value (ordinal 0) as the default for
 * uninitialized enum variables. The constructor receives the list of all
 * enum values and selects the first one.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see Initialization
 * @see MacroInitialization
 */
public final class EnumeratedInitialization implements Initialization {
    private final String enumeratedValue;

    /**
     * Creates an enumerated default initialization.
     *
     * @param enumeratedValues list of all enum values; first element is used as default
     */
    public EnumeratedInitialization(List<String> enumeratedValues) {
        this.enumeratedValue = enumeratedValues.get(0);
    }

    @Override
    public Initialization selectVariable(String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return enumeratedValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Initialization copy() {
        List<String> values = new ArrayList<>();
        values.add(enumeratedValue);
        return new EnumeratedInitialization(values);
    }

    /**
     * Value-object equality based on the wrapped enumerated value.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnumeratedInitialization)) {
            return false;
        }
        EnumeratedInitialization other = (EnumeratedInitialization) o;
        return Objects.equals(this.enumeratedValue, other.enumeratedValue);
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
