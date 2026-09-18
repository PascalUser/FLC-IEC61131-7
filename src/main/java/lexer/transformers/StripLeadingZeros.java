package lexer.transformers;

/**
 * Transformer that removes leading zeros from numeric literals.
 * <p>
 * Strips leading zeros while preserving at least one digit (to avoid
 * empty strings). This normalizes numeric literals before parsing.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class StripLeadingZeros extends Transformer {
    public StripLeadingZeros(Transformer next) {
        super(next);
    }

    @Override
    public String transform(String lexeme) {
        int i = 0;
        int lexemeLength = lexeme.length();

        String sign = "";
        if (hasToSkipSign(lexeme.charAt(0))) {
            sign = String.valueOf(lexeme.charAt(0));
            i++;
        }
        while (uselessZero(i, lexemeLength, lexeme.charAt(i)))
            i++;

        return super.giveToNext(sign + lexeme.substring(i));
    }

    private boolean hasToSkipSign(char firstChar) {
        return (firstChar == '+' || firstChar == '-');
    }

    private boolean uselessZero(int position, int strLength, char character) {
        return (position < strLength - 1 && character == '0');
    }
}
