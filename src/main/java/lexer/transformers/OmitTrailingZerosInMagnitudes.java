package lexer.transformers;

/**
 * Transformer that removes trailing zeros from fractional numeric magnitudes in interval literals.
 * <p>
 * This transformer processes IEC 61131-3 interval literals and removes unneeded trailing zeros
 * after a decimal point (e.g., {@code 1.500s} becomes {@code 1.5s}). If all fractional digits
 * are zeros, the decimal point is also removed (e.g., {@code 1.00s} becomes {@code 1s}).
 * Integer trailing zeros are strictly preserved (e.g., {@code 100s} remains {@code 100s}).
 * </p>
 * <p>
 * Examples:
 * <pre>
 * "1.500s"         -> "1.5s"
 * "1.000s"         -> "1s"
 * "0.050ms"        -> "0.05ms"
 * "100s"           -> "100s"       (integer trailing zeros preserved)
 * "-1.50h"         -> "-1.5h"
 * </pre>
 * </p>

 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public class OmitTrailingZerosInMagnitudes extends Transformer {

    /**
     * Creates a transformer with the next handler in the chain.
     *
     * @param next the next transformer, or {@code null} if this is the last
     */
    public OmitTrailingZerosInMagnitudes(Transformer next) {
        super(next);
    }

    /**
     * Transforms the given lexeme by removing trailing zeros from decimal parts of magnitudes.
     *
     * @param lexeme the input interval literal (e.g., {@code 1.500s2.00m})
     * @return the transformed lexeme with fractional trailing zeros removed
     */
    @Override
    public String transform(String lexeme) {
        if (lexeme == null || lexeme.isEmpty()) {
            return giveToNext(lexeme);
        }

        StringBuilder result = new StringBuilder();
        StringBuilder numBuf = new StringBuilder();
        boolean hasDecimalPoint = false;

        for (int i = 0; i < lexeme.length(); i++) {
            char c = lexeme.charAt(i);

            if (this.isUnit(c)) {
                if (hasDecimalPoint) {
                    result.append(this.stripTrailingZerosFromDecimal(numBuf.toString()));
                } else {
                    result.append(numBuf);
                }
                numBuf.setLength(0);
                hasDecimalPoint = false;

                result.append(c);
                while (i + 1 < lexeme.length() && this.isUnit(lexeme.charAt(i + 1))) {
                    result.append(lexeme.charAt(++i));
                }
            } else {
                if (c == '.') {
                    hasDecimalPoint = true;
                }
                numBuf.append(c);
            }
        }

        if (numBuf.length() > 0) {
            if (hasDecimalPoint) {
                result.append(this.stripTrailingZerosFromDecimal(numBuf.toString()));
            } else {
                result.append(numBuf);
            }
        }

        return giveToNext(result.toString());
    }

    /**
     * Removes trailing zeros from a numeric string containing a decimal point.
     * If no fractional digits remain, the decimal point is also removed.
     *
     * @param numStr string representation of a decimal number (e.g., {@code "1.500"})
     * @return cleaned string (e.g., {@code "1.5"})
     */
    private String stripTrailingZerosFromDecimal(String numStr) {
        int end = numStr.length() - 1;

        while (end >= 0 && numStr.charAt(end) == '0') {
            end--;
        }
        if (end >= 0 && numStr.charAt(end) == '.') {
            end--; // Remueve el punto decimal si no quedan dígitos fraccionarios
        }
        return numStr.substring(0, end + 1);
    }

    /**
     * Checks if the character is a unit suffix character.
     *
     * @param c the character to check
     * @return {@code true} if {@code c} is a unit character, {@code false} otherwise
     */
    private boolean isUnit(char c) {
        return c != '.' && c != '-' && !Character.isDigit(c);
    }
}