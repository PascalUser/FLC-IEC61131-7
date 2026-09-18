package lexer.transformers;

/**
 * Identity transformer that passes the lexeme through unchanged.
 * <p>
 * Used as a no-op transformer when no transformation is needed for a given
 * lexeme category. Acts as the end of the transformer chain.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class Nothing extends Transformer {
    public Nothing(Transformer next) {
        super(next);
    }

    public Nothing() {
        this(null);
    }

    @Override
    public String transform(String lexeme) {
        return lexeme;
    }
}
