package parser.initializations;

import java.util.Map;
import java.util.HashMap;

public final class Struct implements Initialization {
    private String status = null;
    private final Map<String, String> map = new HashMap<>();

    public String add(final String key, final String value) {
        if (status == null) {
            status = key;
        }
        return map.put(key, value);
    }

    @Override
    public Initialization set(final String value) {
        if (!map.containsKey(value)) {
            throw new RuntimeException("Invalid use of Struct Initialization");
        }
        status = value;
        return this;
    }

    @Override
    public String get() {
        if (status == null) {
            throw new RuntimeException("Invalid use of Struct Initialization");
        }
        return map.get(status);
    }
}