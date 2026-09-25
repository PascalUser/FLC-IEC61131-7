package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Test-support utility. This class belongs to the test sources (not
 * production): its only job is turning a mismatch between an expected and an
 * actual {@link LexemeInfo} into a human-readable message for a failed
 * assertion — that's a testing concern, so it lives here instead of on the
 * domain model.
 * <p>
 * It does not use reflection and does not reimplement equality: it reads
 * {@link LexemeInfo}'s public fields directly (compiler-checked — renaming a
 * field breaks the build, not a silent no-op), and for nested values (an
 * {@code Initialization}, a nested struct, etc.) it defers to that object's
 * own {@code equals()}, which is where that notion of equality belongs.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 2.1
 * @since 1.0
 */
public class LexemeInfoComparator {

    /**
     * Looks up {@code key} in {@code symbolTable} and returns a list of
     * human-readable diffs against {@code expected}. An empty list means
     * the actual value matches the expected one.
     */
    public static List<String> compare(SymbolTable symbolTable, String key, LexemeInfo expected) {
        LexemeInfo actual = symbolTable.get(key);

        if (actual == null) {
            List<String> diffs = new ArrayList<>();
            diffs.add("key '" + key + "' not found in symbol table");
            return diffs;
        }

        return diff(expected, actual);
    }

    private static List<String> diff(LexemeInfo expected, LexemeInfo actual) {
        List<String> diffs = new ArrayList<>();

        if (expected.type != actual.type) {
            diffs.add(mismatch("type", expected.type, actual.type));
        }
        if (expected.subtype != actual.subtype) {
            diffs.add(mismatch("subtype", expected.subtype, actual.subtype));
        }
        if (!Objects.equals(expected.customType, actual.customType)) {
            diffs.add(mismatch("customType", expected.customType, actual.customType));
        }
        if (expected.use != actual.use) {
            diffs.add(mismatch("use", expected.use, actual.use));
        }
        if (expected.source != actual.source) {
            diffs.add(mismatch("source", expected.source, actual.source));
        }
        if (!Objects.equals(expected.inferiorLimits, actual.inferiorLimits)) {
            diffs.add(mismatch("inferiorLimit", expected.inferiorLimits, actual.inferiorLimits));
        }
        if (!Objects.equals(expected.superiorLimits, actual.superiorLimits)) {
            diffs.add(mismatch("superiorLimit", expected.superiorLimits, actual.superiorLimits));
        }
        if (!Objects.equals(expected.parameters, actual.parameters)) {
            diffs.add(mismatch("parameters", expected.parameters, actual.parameters));
        }
        if (!Objects.equals(expected.initialValue, actual.initialValue)) {
            diffs.add(mismatch("initialValue", expected.initialValue, actual.initialValue));
        }

        return diffs;
    }

    private static String mismatch(String field, Object expected, Object actual) {
        return String.format("%s (\n\texpected=%s,\n\tactual=%s\n)", field, expected, actual);
    }
}