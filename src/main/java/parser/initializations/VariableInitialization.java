package parser.initializations;

import java.util.Objects;

public final class VariableInitialization implements Initialization {
    private final String value;

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

    @Override
    public VariableInitialization copy() {
        return new VariableInitialization(value);
    }

    /**
     * Value-object equality based on the wrapped value. Unlike
     * {@link RealInitialization} (which has no state of its own and is
     * always "the default value"), a {@code VariableInitialization}'s
     * identity as a value *is* {@code value} — two instances are equal only
     * when they hold the same value.
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