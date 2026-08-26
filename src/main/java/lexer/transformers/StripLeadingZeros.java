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
public class StripLeadingZeros extends Transformer {
    public StripLeadingZeros(Transformer next) {
        super(next);
    }

    @Override
    public String transform(String lexeme) {
        int i = 0;
        while (i < lexeme.length() - 1 && lexeme.charAt(i) == '0')
            i++;

        return super.giveToNext(lexeme.substring(i));
    }
}
