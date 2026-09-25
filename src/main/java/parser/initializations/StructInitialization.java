package parser.initializations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the initialization value of a {@code STRUCT} instance: a map
 * from field name to that field's own {@link Initialization}. Nested structs
 * are addressed with a compound key (e.g. {@code "RGB#GAMMA_R"} sets field
 * {@code GAMMA_R} inside the nested struct stored under {@code "RGB"}).
 */
public final class StructInitialization implements Initialization {
    private final Map<String, Initialization> map = new HashMap<>();
    private String selectedVariable = null;

    public Initialization setFieldInitialization(final String fieldName, final Initialization initialization) {
        if (selectedVariable == null) {
            selectedVariable = fieldName;
        }

        int octothorpeIdx = fieldName.indexOf('#');
        if (octothorpeIdx != -1) {
            String key1 = fieldName.substring(0, octothorpeIdx);
            String key2 = fieldName.substring(octothorpeIdx + 1);

            if (!map.containsKey(key1)) {
                return null;
            }

            StructInitialization structInit = (StructInitialization) map.get(key1);
            return structInit.setFieldInitialization(key2, initialization);
        }

        return map.put(fieldName, initialization);
    }

    @Override
    public StructInitialization selectVariable(final String variable) {
        if (!map.containsKey(variable)) {
            return this;
        }
        selectedVariable = variable;
        return this;
    }

    @Override
    public String getVariableValue() {
        if (selectedVariable == null) {
            return "";
        }

        String key1 = selectedVariable;
        String key2 = "";

        int octothorpeIdx = selectedVariable.indexOf('#');
        if (octothorpeIdx != -1) {
            key1 = selectedVariable.substring(0, octothorpeIdx);
            key2 = selectedVariable.substring(octothorpeIdx + 1);
        }

        if (!map.containsKey(key1)) {
            return "";
        }
        return map.get(key1).selectVariable(key2).getVariableValue();
    }

    @Override
    public StructInitialization copy() {
        StructInitialization copy = new StructInitialization();
        for (Map.Entry<String, Initialization> entry : map.entrySet()) {
            copy.setFieldInitialization(entry.getKey(), entry.getValue().copy());
        }
        return copy;
    }

    /**
     * Structural equality based solely on the field map. {@code
     * selectedVariable} is deliberately excluded: it's just a cursor used by
     * {@link #getVariableValue()}, not part of the struct's value. Because
     * {@link Map#equals(Object)} already compares entries deeply (calling
     * {@code equals} on each {@link Initialization} value), nested structs
     * are compared correctly with no extra logic here.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StructInitialization)) {
            return false;
        }
        StructInitialization other = (StructInitialization) o;
        return Objects.equals(this.map, other.map);
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