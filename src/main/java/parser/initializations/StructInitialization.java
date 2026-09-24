package parser.initializations;

import java.util.*;

public final class StructInitialization implements Initialization {
    private final Map<String, Initialization> map = new HashMap<>();
    private String selectedVariable = null;

    public Initialization setFieldInitialization(final String fieldName, final Initialization initialization) {
        if (selectedVariable == null) {
            selectedVariable = fieldName;
        }
        String key1 = fieldName;
        int octothorpeIdx = fieldName.indexOf('#');
        if (octothorpeIdx != -1) {
            key1 = fieldName.substring(0, octothorpeIdx);
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
}