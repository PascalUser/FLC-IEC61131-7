package parser.initializations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnumeratedInitialization implements Initialization {
    private final String enumeratedValue;

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

    @Override
    public Initialization copy() {
        List<String> values = new ArrayList<>();
        values.add(enumeratedValue);
        return new EnumeratedInitialization(values);
    }

    /**
     * Value-object equality based on the wrapped value. Unlike
     * {@link EnumeratedInitialization} (which has no state of its own and is
     * always "the default value"), a {@code EnumeratedInitialization}'s
     * identity as a value *is* {@code ilimit} and {@code ulimit} — two instances
     * are equal only when they hold the same valueS.
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
