package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Generic reflection-based comparator for test assertions.
 * <p>
 * Compares two objects of the same type field by field using reflection,
 * so it does not need to be updated when new fields are added to the
 * target class. Intended for use in unit tests where a plain
 * {@code equals()} check is not descriptive enough about which fields
 * actually differ.
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
            // allows access to private fields
            field.setAccessible(true);

            try {
                Object expectedValue = field.get(expected);
                Object actualValue = field.get(actual);
                if (!Objects.equals(expectedValue, actualValue)) {
                    diffs.add(String.format(
                            "%s (expected=%s, actual=%s)",
                            field.getName(), expectedValue, actualValue
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
}