package lexer.transformers;

/**
 * Transformer that removes the type prefix from based literals.
 * <p>
 * Based literals in IEC 61131-7 have the format {@code BASE#VALUE#}
 * (e.g., {@code 2#1010#}). This transformer extracts the lexeme part
 * after the {@code #} delimiter for numeric parsing.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class StripTypePrefix extends Transformer {
    public StripTypePrefix(Transformer next) {
        super(next);
    }

    public StripTypePrefix() {
        this(null);
    }

    @Override
    public String transform(String lexeme) {
        final int index = lexeme.indexOf('#');
        return super.giveToNext(lexeme.substring(index + 1));
    }
}
