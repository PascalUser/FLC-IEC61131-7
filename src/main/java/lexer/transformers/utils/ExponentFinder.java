package lexer.transformers.utils;

/**
 * Utility for locating the exponent character in real number literals.
 * <p>
 * IEC 61131-7 real literals may use uppercase {@code E} or lowercase {@code e}
 * for scientific notation (e.g., {@code 1.5E10}, {@code 3.14e-5}).
 * This class finds the first occurrence of either.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class ExponentFinder {

    private ExponentFinder() {
        // Utility class - not instantiable
    }

    /**
     * Returns the index of the exponent character ('E' or 'e').
     *
     * @param lexeme the real number lexeme
     * @return the index of 'E' or 'e', or -1 if neither present
     */
    public static int find(String lexeme) {
        int upper = lexeme.indexOf('E');
        int lower = lexeme.indexOf('e');

        if (upper == -1) {
            return lower;
        }
        if (lower == -1) {
            return upper;
        }
        return Math.min(upper, lower);
    }
}
