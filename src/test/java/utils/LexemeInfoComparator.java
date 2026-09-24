package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Generic reflection-based comparator for test assertions.
 * <p>
 * Compares two objects of the same type field by field using reflection,
 * including nested custom objects by value, so it does not need to be
 * updated when new fields are added to the target class. Intended for use
 * in unit tests where a plain {@code equals()} check is not descriptive
 * enough about which fields actually differ.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class LexemeInfoComparator {

    /**
     * Convenience overload that looks up the actual {@link LexemeInfo} in a
     * {@link SymbolTable} before comparing it against the expected value.
     * <p>
     * If the given key is not present in the symbol table, this is reported
     * as a distinct mismatch rather than throwing an exception.
     * </p>
     *
     * @param symbolTable the symbol table to look up the actual value in
     * @param key         the lexeme key to look up in the symbol table
     * @param expected    the expected {@link LexemeInfo}
     * @return a list of human-readable descriptions of mismatched fields;
     *         empty if all fields are equal
     */
    public static List<String> compare(SymbolTable symbolTable, String key, LexemeInfo expected) {
        LexemeInfo actual = symbolTable.get(key);

        if (actual == null) {
            List<String> diffs = new ArrayList<>();
            diffs.add("key '" + key + "' not found in symbol table");
            return diffs;
        }

        return compare(expected, actual);
    }

    /**
     * Compares two objects field by field using reflection.
     * <p>
     * Only fields declared directly in the object's class are compared;
     * inherited fields from superclasses are not included.
     * </p>
     *
     * @param expected the expected object
     * @param actual   the actual object produced by the code under test
     * @return a list of human-readable descriptions of mismatched fields;
     *         empty if all fields are equal
     */
    private static List<String> compare(Object expected, Object actual) {
        List<String> diffs = new ArrayList<>();

        // Handle null cases up front to avoid NPEs below
        if (expected == null || actual == null) {
            if (expected != actual) {
                diffs.add("whole object (one of them is null)");
            }
            return diffs;
        }

        for (Field field : expected.getClass().getDeclaredFields()) {
            // Allows access to private fields
            field.setAccessible(true);

            try {
                Object expectedValue = field.get(expected);
                Object actualValue = field.get(actual);

                if (!areEqual(expectedValue, actualValue)) {
                    diffs.add(String.format(
                            "%s (expected=%s, actual=%s)",
                            field.getName(), unwrapToString(expectedValue), unwrapToString(actualValue)
                    ));
                }
            } catch (IllegalAccessException e) {
                // Should not normally happen since setAccessible(true) was called,
                // but reported explicitly so the failure isn't silently swallowed
                diffs.add(field.getName() + " (could not be read: " + e.getMessage() + ")");
            }
        }
        return diffs;
    }

    /**
     * Determines whether two objects are equal by first attempting a standard equality check,
     * followed by a deep reflective comparison for custom classes, or an unwrapped value comparison.
     *
     * @param expected the expected object
     * @param actual   the actual object
     * @return {@code true} if both objects are considered equal, {@code false} otherwise
     */
    private static boolean areEqual(Object expected, Object actual) {
        if (Objects.equals(expected, actual)) {
            return true;
        }
        if (expected == null || actual == null) {
            return false;
        }

        // 1. If they are exactly the same custom class -> perform deep reflective comparison
        if (expected.getClass().equals(actual.getClass()) && isCustomClass(expected.getClass())) {
            return compare(expected, actual).isEmpty();
        }

        // 2. If they are different classes (e.g., String vs VariableInitialization)
        // Extract their internal values and compare if they represent the same data
        return Objects.equals(unwrapToString(expected), unwrapToString(actual));
    }

    /**
     * Checks if the given class is a custom domain class (not a native Java class or Enum).
     *
     * @param clazz the class to check
     * @return {@code true} if it is a custom class, {@code false} otherwise
     */
    private static boolean isCustomClass(Class<?> clazz) {
        return !clazz.getName().startsWith("java.") && !clazz.isEnum();
    }

    /**
     * Extracts the internal value of domain objects that lack a valid {@code toString()}
     * implementation to facilitate cross-comparisons and error messaging.
     *
     * @param obj the object to unwrap
     * @return a string representation of the object's internal value, or its default {@code toString()}
     */
    private static String unwrapToString(Object obj) {
        if (obj == null) {
            return "null";
        }

        if (!isCustomClass(obj.getClass())) {
            return obj.toString();
        }

        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            if (fields.length > 0) {
                fields[0].setAccessible(true);
                return String.valueOf(fields[0].get(obj));
            }
        } catch (Exception ignored) {
            // Fall through to default toString if reflection fails
        }

        return obj.toString();
    }
}