package parser.utils;

import parser.initializations.Initialization;

/**
 * Information about an initialization: the name of the field and the value assigned to it.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class PathInitializationInfo {
    private final String fieldName;
    private final Initialization initialValue;

    /**
     * Creates the information of an initialization: the field name and its value.
     *
     * @param initFieldName name of the field being initialized
     * @param initializer    the initialization assigned to the field
     */
    public PathInitializationInfo(final String initFieldName, final Initialization initializer) {
        fieldName = initFieldName;
        initialValue = initializer;
    }

    /**
     * Returns the name of the initialized field.
     *
     * @return the field name
     */
    public String name() {
        return fieldName;
    }

    /**
     * Returns the initialization assigned to the field.
     *
     * @return the initialization of the field
     */
    public Initialization initialValue() {
        return initialValue;
    }
}
