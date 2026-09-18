package parser.initializations;

import java.util.Map;
import java.util.HashMap;

public final class Struct implements Initialization {
    private String status = null;
    private final Map<String, Initialization> map = new HashMap<>();

    public Initialization add(final String key, final Initialization initialValue) {
        if (status == null) {
            status = key;
        }
        return map.put(key, initialValue);
    }

    @Override
    public Struct setVariableName(final String value) {
        if (!map.containsKey(value)) {
            throw new RuntimeException("Invalid use of Struct Initialization");
        }
        status = value;
        return this;
    }

    @Override
    public String getInitialValue() {
        if (status == null) {
            throw new RuntimeException("Invalid use of Struct Initialization");
        }
        return map.get(status).getInitialValue();
    }
}