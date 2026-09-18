package lexer.transformers;
import lexer.utils.ExponentFinder;
/**
 * Transformer that removes trailing zeros from numeric literals.
 * <p>
 * Strips trailing zeros from the mantissa while preserving at least
 * one digit. The exponent of scientific notation is left unchanged.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class StripTrailingZeros extends Transformer {

    public StripTrailingZeros(Transformer next) {
        super(next);
    }

    @Override
    public String transform(String lexeme) {
        int end = lexeme.length();

        int exponent = ExponentFinder.find(lexeme);

        int mantissaEnd = exponent == -1 ? end : exponent;

        int dot = lexeme.indexOf('.');

        if (dot != -1 && dot < mantissaEnd) {
            while (mantissaEnd > dot + 2 && lexeme.charAt(mantissaEnd - 1) == '0') {
                mantissaEnd--;
            }
        }
        String mantissa = lexeme.substring(0, mantissaEnd);

        if (exponent != -1) {
            mantissa += lexeme.substring(exponent);
        }

        return super.giveToNext(mantissa);
    }

}