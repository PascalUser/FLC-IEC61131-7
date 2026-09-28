package lexer.transformers.hex_resolvers;

import lexer.transformers.Transformer;

/**
 * Hex escape resolver for STRING literals (IEC 61131-7).
 * <p>
 * STRING hex escapes use 2 hex digits per character: {@code $HH}.
 * Extends {@link HexResolver} with {@code hexDigits = 2}.
 * </p>
 * <p>
 * Examples:
 * <ul>
 *   <li>{@code $41} → 'A'</li>
 *   <li>{@code $0A} → newline</li>
 *   <li>{@code $$} → literal '$' (preserved for {@link lexer.transformers.StringEscapeResolver})</li>
 * </ul>
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see HexResolver
 * @see WStringHexResolver
 * @see lexer.transformers.StringEscapeResolver
 */
public final class StringHexResolver extends HexResolver {

    /**
     * Creates a new STRING hex resolver.
     *
     * @param next the next transformer in the chain
     */
    public StringHexResolver(Transformer next) {
        super(next);
    }

    @Override
    protected int getHexDigits() {
        return 2;
    }
}