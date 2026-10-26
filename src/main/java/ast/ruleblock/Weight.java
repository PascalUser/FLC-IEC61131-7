package ast.ruleblock;

import java.util.Objects;

public final class Weight {
    public enum Kind {
        NUMERIC,
        IDENTIFIER
    }

    private final String value;
    private final Kind kind;

    private Weight(final String value, final Kind kind) {
        this.value = requireValue(value);
        this.kind = Objects.requireNonNull(kind, "kind");
    }

    public static Weight numeric(final String value) {
        return new Weight(value, Kind.NUMERIC);
    }

    public static Weight identifier(final String value) {
        return new Weight(value, Kind.IDENTIFIER);
    }

    private static String requireValue(final String value) {
        Objects.requireNonNull(value, "value");
        if (value.isEmpty()) {
            throw new IllegalArgumentException("value must not be empty");
        }
        return value;
    }

    public String value() {
        return value;
    }

    public Kind kind() {
        return kind;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Weight)) {
            return false;
        }
        Weight weight = (Weight) other;
        return value.equals(weight.value) && kind == weight.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, kind);
    }

    @Override
    public String toString() {
        return "Weight{kind=" + kind + ", value='" + value + "'}";
    }
}
