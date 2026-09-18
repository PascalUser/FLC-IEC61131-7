package lexer.transformers;

/**
 * Transformer that removes leading zero magnitudes from interval literals.
 * <p>
 * This transformer skips zero-valued magnitude components (e.g., {@code 0d}, {@code 0h})
 * at the start of an interval literal until it encounters the first non-zero magnitude.
 * If all magnitudes are zero, it returns {@code 0ms}.
 * </p>
 * <p>
 * Examples:
 * <pre>
 * "0000d0001h"        -> "1h"
 * "0000d0000h0001m"   -> "1m"
 * "0000d0000h0000m0001s" -> "1s"
 * "0000d"             -> "0ms"
 * </pre>
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public final class OmitLeadingZeroMagnitudes extends Transformer {
    /**
     * Creates a transformer with the next handler in the chain.
     *
     * @param next the next transformer, or {@code null} if this is the last
     */
    public OmitLeadingZeroMagnitudes(Transformer next) {
        super(next);
    }

    @Override
    public String transform(String lexeme) {
        StringBuilder result = new StringBuilder();
        StringBuilder numBuf = new StringBuilder();

        boolean isNegative = !lexeme.isEmpty() && lexeme.charAt(0) == '-';
        int startIdx = isNegative ? 1 : 0;
        String prefix = isNegative ? "-" : "";

        for (int i = startIdx; i < lexeme.length(); i++) {
            char currentChar = lexeme.charAt(i);

            if (this.isUnit(currentChar)) {
                StringBuilder unitBuf = new StringBuilder();
                unitBuf.append(currentChar);

                if (this.nextIsAlsoUnit(lexeme, i)) {
                    unitBuf.append(lexeme.charAt(++i));
                }
                if (Double.parseDouble(numBuf.toString()) != 0D) {
                    return this.giveToNext(
                            result.append(prefix)
                                .append(numBuf)
                                .append(unitBuf)
                                .toString()
                                .concat(lexeme.substring(i + 1))
                    );
                }
                numBuf.setLength(0);
            } else {
                numBuf.append(currentChar);
            }
        }
        return this.giveToNext("0ms");
    }

    private boolean nextIsAlsoUnit(String currentLexeme, int currPos) {
        return (currPos + 1 < currentLexeme.length() && !Character.isDigit(currentLexeme.charAt(currPos + 1)));
    }

    private boolean isUnit(char c) {
        return c != '.' && !Character.isDigit(c);
    }
}
