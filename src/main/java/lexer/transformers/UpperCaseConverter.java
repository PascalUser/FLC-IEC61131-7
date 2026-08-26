package lexer.transformers;

/**
 * Transformer that converts lexemes to uppercase.
 * <p>
 * IEC 61131-7 is case-insensitive for keywords and identifiers, so this
 * transformer normalizes all input to uppercase before further processing.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class UpperCaseConverter extends Transformer {
    public UpperCaseConverter(Transformer next) {
        super(next);
    }

    @Override
    public String transform(String lexeme) {
        return super.giveToNext(lexeme.toUpperCase());
    }
}
