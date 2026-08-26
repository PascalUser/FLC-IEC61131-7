package lexer.transformers;

/**
 * Abstract base class for lexeme transformers using the Chain of Responsibility pattern.
 * <p>
 * Each transformer can modify the lexeme and optionally pass it to the next
 * transformer in the chain. Used for case conversion, underscore removal,
 * prefix stripping, and zero stripping.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class Transformer {
    private final Transformer next;

    /**
     * Creates a transformer with the next handler in the chain.
     *
     * @param next the next transformer, or {@code null} if this is the last
     */
    protected Transformer(Transformer next) {
        this.next = next;
    }

    /**
     * Transforms the given lexeme.
     *
     * @param lexeme the input lexeme
     * @return the transformed lexeme
     */
    public abstract String transform(String lexeme);

    /**
     * Passes the lexeme to the next transformer in the chain.
     *
     * @param lexeme the lexeme to pass
     * @return the result from the next transformer, or the lexeme unchanged if no next
     */
    protected String giveToNext(String lexeme) {
        if (next == null) {
            return lexeme;
        }
        return next.transform(lexeme);
    }
}
