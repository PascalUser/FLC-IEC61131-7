package lexer.transformers;

/**
 * Transformer that removes trailing zero-valued magnitudes from an interval literal.
 * <p>
 * For example: {@code "1h5m0s0ms"} becomes {@code "1h5m"}, and {@code "-1h0m0s"} becomes {@code "-1h"}.
 * If all magnitudes are zero, it defaults to {@code "0ms"}.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.1
 * @since 1.0
 */
public final class OmitTrailingZeroMagnitudes extends Transformer {

    public OmitTrailingZeroMagnitudes(Transformer next) {
        super(next);
    }

    @Override
    public String transform(String lexeme) {
        if (lexeme == null || lexeme.isEmpty()) {
            return this.giveToNext(lexeme);
        }

        boolean isNegative = lexeme.charAt(0) == '-';
        int minIdx = isNegative ? 1 : 0;
        int i = lexeme.length() - 1;

        while (i >= minIdx) {
            int unitEnd = i;

            while (i >= minIdx && this.isUnit(lexeme.charAt(i))) {
                i--;
            }
            int unitStart = i + 1;
            if (unitStart > unitEnd) break;

            int numEnd = i;
            while (i >= minIdx && (Character.isDigit(lexeme.charAt(i)) || lexeme.charAt(i) == '.')) {
                i--;
            }
            int numStart = i + 1;
            if (numStart > numEnd) break;

            double value = Double.parseDouble(lexeme.substring(numStart, numEnd + 1));
            if (value != 0D) {
                return this.giveToNext(lexeme.substring(0, unitEnd + 1));
            }
        }
        return this.giveToNext("0ms");
    }

    private boolean isUnit(char c) {
        return c != '.' && !Character.isDigit(c);
    }
}