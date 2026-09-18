package lexer.transformers;

/**
 * Transformer that removes leading zeros from the numeric portion of based literals.
 * <p>
 * Based literals in IEC 61131-7 have the format {@code BASE#VALUE} (e.g., {@code 16#00FF}).
 * This transformer preserves the base prefix (e.g., {@code 16#}) and strips leading zeros
 * from the numeric value portion (e.g., {@code 16#00FF} → {@code 16#FF}).
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public final class StripBaseNumberLeadingZeros extends Transformer {
    public StripBaseNumberLeadingZeros(Transformer next) {
        super(next);
    }

    @Override
    public String transform(String lexeme) {
        final int index = lexeme.indexOf('#');

        String prefix = lexeme.substring(0, index + 1);
        String number = lexeme.substring(index + 1);

        String cleanNumber = number.replaceFirst("^0+(?!$)", "");
        return super.giveToNext(prefix + cleanNumber);
    }
}
