package lexer.transformers;

/**
 * Transformer that removes leading zeros from numeric magnitudes in interval literals.
 * <p>
 * This transformer processes IEC 61131-7 interval literals (e.g., {@code 0001d0002h0003m})
 * and removes leading zeros from each numeric magnitude while preserving:
 * <ul>
 *   <li>All magnitude components (including zero-valued ones)</li>
 *   <li>Unit suffixes ({@code d}, {@code h}, {@code m}, {@code s}, {@code ms})</li>
 *   <li>Decimal points and fractional digits (zeros after decimal are preserved)</li>
 *   <li>Negative signs (leading zeros removed after the sign)</li>
 * </ul>
 * </p>
 * <p>
 * Examples:
 * <pre>
 * "0001d0002h"        -> "1d2h"
 * "0000d0001h"        -> "0d1h"       (zero magnitudes preserved)
 * "001.5d"            -> "1.5d"       (decimal zeros preserved)
 * "-001d002h"         -> "-1d2h"      (negative sign handled)
 * "0000d"             -> "0d"         (all-zero magnitude becomes single zero)
 * </pre>
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public final class OmitLeadingZerosInMagnitudes extends Transformer {

    /**
     * Creates a transformer with the next handler in the chain.
     *
     * @param next the next transformer, or {@code null} if this is the last
     */
    public OmitLeadingZerosInMagnitudes(Transformer next) {
        super(next);
    }

    /**
     * Transforms the given lexeme by removing leading zeros from each numeric magnitude.
     *
     * @param lexeme the input interval literal (e.g., {@code 0001d0002h0003m})
     * @return the transformed lexeme with leading zeros removed from each magnitude
     */
    @Override
    public String transform(String lexeme) {
        StringBuilder result = new StringBuilder();

        boolean canBeDiscardedIfZero = true;
        for (int i = 0; i < lexeme.length(); i++) {
            char c = lexeme.charAt(i);

            if (canBeDiscardedIfZero && this.isZero(c) && !this.isNextToFloatingPointOrUnit(lexeme, i)) {
                continue;
            }
            if (this.isUnit(c)) {
                result.append(c);
                canBeDiscardedIfZero = true;
                continue;
            }
            result.append(c);
            canBeDiscardedIfZero = false;
        }
        return giveToNext(result.toString());
    }

    /**
     * Checks if the character is a zero digit.
     *
     * @param c the character to check
     * @return {@code true} if {@code c} is '0', {@code false} otherwise
     */
    private boolean isZero(char c) {
        return c == '0';
    }

    /**
     * Checks if the character is a unit suffix character.
     * A unit is any character that is not a digit and not a decimal point.
     *
     * @param c the character to check
     * @return {@code true} if {@code c} is a unit character, {@code false} otherwise
     */
    private boolean isUnit(char c) {
        return c != '.' && !Character.isDigit(c);
    }

    /**
     * Checks if the next character indicates a floating-point number or unit suffix.
     * Used to determine if a zero should be preserved (e.g., in {@code 0.5} or {@code 0h}).
     *
     * @param lexeme the input string
     * @param currentPosition the current character position
     * @return {@code true} if the next character is not a digit (indicating decimal point or unit),
     *         {@code false} otherwise
     */
    private boolean isNextToFloatingPointOrUnit(String lexeme, int currentPosition) {
        if (currentPosition + 1 < lexeme.length()) {
            char nextChar = lexeme.charAt(currentPosition + 1);
            return !Character.isDigit(nextChar);
        }
        return false;
    }
}