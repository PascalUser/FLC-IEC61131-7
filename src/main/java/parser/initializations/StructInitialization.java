package parser.initializations;

import java.util.*;

public final class StructInitialization implements Initialization {
    private String selectedVariable = null;
    private final Map<String, Initialization> map = new HashMap<>();

    public Initialization addFieldInitialization(final String fieldName, final Initialization initialization) {
        if (selectedVariable == null) {
            selectedVariable = fieldName;
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
        if (!map.containsKey(selectedVariable)) {
            return "";
        }
        return map.get(selectedVariable).getVariableValue();
    }
}