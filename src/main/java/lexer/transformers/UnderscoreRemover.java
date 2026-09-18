package lexer.transformers;

/**
 * Transformer that removes underscores from numeric literals.
 * <p>
 * IEC 61131-7 allows underscores as digit separators in numeric literals
 * (e.g., {@code 1_000_000}). This transformer strips them before parsing.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class UnderscoreRemover extends Transformer {
    public UnderscoreRemover(Transformer next) {
        super(next);
    }

    @Override
    public String transform(String lexeme) {
        return super.giveToNext(lexeme.replace("_", ""));
    }
}
