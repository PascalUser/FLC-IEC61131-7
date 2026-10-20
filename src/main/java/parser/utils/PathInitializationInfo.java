package parser.utils;

import parser.initializations.Initialization;

public final class PathInitializationInfo {
    private final String fieldName;
    private final Initialization initialValue;

    public PathInitializationInfo(String initFieldName, Initialization initializer) {
        fieldName = initFieldName;
        initialValue = initializer;
    }

    public String name() {
        return fieldName;
    }

    public Initialization initialValue() {
        return initialValue;
    }
}
