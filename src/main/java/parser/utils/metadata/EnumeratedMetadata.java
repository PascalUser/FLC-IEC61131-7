package parser.utils.metadata;

import java.util.List;

public final class EnumeratedMetadata {
    private final List<String> values;

    public EnumeratedMetadata(List<String> initValues) {
        values = initValues;
    }

    public List<String> values() {
        return values;
    }
}
