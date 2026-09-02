package lexer.transformers;

import lexer.utils.ExponentFinder;

/**
 * Transformer that normalizes real numeric literals by applying
 * leading and trailing zero removal independently to the mantissa
 * and exponent.
 *
 * <p>
 * For scientific notation, the mantissa and exponent are transformed
 * separately, preserving the exponent marker.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class StripRealZeros extends Transformer {

    private final Transformer mantissaTransformer;
    private final Transformer exponentTransformer;

    public StripRealZeros(Transformer next) {
        super(next);

        this.mantissaTransformer =
                new StripTrailingZeros(
                        new StripLeadingZeros(null)
                );

        this.exponentTransformer =
                new StripLeadingZeros(null);
    }

    @Override
    public String transform(String lexeme) {
        int exponent = ExponentFinder.find(lexeme);

        if (exponent == -1) {
            return super.giveToNext(
                    mantissaTransformer.transform(lexeme)
            );
        }

        String mantissa = lexeme.substring(0, exponent);
        String exponentPart = lexeme.substring(exponent + 1);

        mantissa = mantissaTransformer.transform(mantissa);
        exponentPart = exponentTransformer.transform(exponentPart);

        String result = mantissa
                + lexeme.charAt(exponent)
                + exponentPart;

        return super.giveToNext(result);
    }


}