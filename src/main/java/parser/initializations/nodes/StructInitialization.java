package parser.initializations.nodes;

import parser.initializations.Initialization;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a struct initialization with named fields.
 * <p>
 * Holds a map of field names to their {@link Initialization} values.
 * Supports nested struct initialization via path-based access using
 * {@code #} as a separator (e.g., {@code "RGB#R"}). The {@link #variableValue()}
 * method returns a textual representation in the form {@code {field1=value1, field2=value2}}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class StructInitialization extends NodeInitialization {
    private final Map<String, Initialization> map = new HashMap<>();

    /**
     * Replaces a field's initialization, traversing nested structs for compound names.
     *
     * @throws ClassCastException if an intermediate field is not a struct initialization
     */
    public Initialization put(final String path, final Initialization initialization) {
        String[] brokenPath = breakStringPath(path);
        if (brokenPath[0].isEmpty()) {
            throw new RuntimeException("StructInitialization: cannot insert an element in the root");
        }
        if (brokenPath[1].isEmpty()) {
            return map.put(brokenPath[0], initialization);
        }
        if (!map.containsKey(brokenPath[0])) {
            throw new RuntimeException("StructInitialization: first field does not exists");
        }
        Initialization child = map.get(brokenPath[0]);
        if (child == null) {
            throw new RuntimeException("StructInitialization: second field does not exists");
        }
        if (!child.find(brokenPath[1]).isPresent()) {
            throw new RuntimeException("StrucInitialization: second field does not contain the path");
        }
        NodeInitialization childNode = (NodeInitialization) child;
        return childNode.put(brokenPath[1], initialization);
    }

    @Override
    public Optional<Initialization> find(final String path) {
        String[] brokenPath = breakStringPath(path);
        Initialization child = map.get(brokenPath[0]);
        return child == null ? Optional.empty() : child.find(brokenPath[1]);
    }

    /**
     * Returns every field with its value, in insertion order, e.g.
     * {@code {X=0.0, RGB={R=1}}}.
     *
     * @return the textual form of this struct's value
     */
    @Override
    public String variableValue() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<String, Initialization> entry : map.entrySet()) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append('=').append(entry.getValue().variableValue());
        }
        return sb.append('}').toString();
    }

    @Override
    public StructInitialization copy() {
        StructInitialization copy = new StructInitialization();
        for (Map.Entry<String, Initialization> entry : this.map.entrySet()) {
            copy.map.put(entry.getKey(), entry.getValue().copy());
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StructInitialization)) return false;
        StructInitialization other = (StructInitialization) o;
        return map.equals(other.map);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(map);
    }

    @Override
    public String toString() {
        return "StructInitialization" + map;
    }
}