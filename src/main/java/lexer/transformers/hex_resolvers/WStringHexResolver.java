package lexer.transformers.hex_resolvers;

import lexer.transformers.Transformer;

/**
 * Hex escape resolver for WSTRING literals (IEC 61131-7).
 * <p>
 * WSTRING hex escapes use 4 hex digits per character: {@code $HHHH}.
 * Extends {@link HexResolver} with {@code hexDigits = 4}.
 * </p>
 * <p>
 * Examples:
 * <ul>
 *   <li>{@code $0041} → 'A'</li>
 *   <li>{@code $000A} → newline</li>
 *   <li>{@code $$} → literal '$' (preserved for {@link lexer.transformers.StringEscapeResolver})</li>
 * </ul>
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see HexResolver
 * @see StringHexResolver
 * @see lexer.transformers.StringEscapeResolver
 */
public final class WStringHexResolver extends HexResolver {

    /**
     * Creates a new WSTRING hex resolver.
     *
     * @param next the next transformer in the chain
     */
    public WStringHexResolver(Transformer next) {
        super(next);
    }

    @Override
    protected int getHexDigits() {
        return 4;
    }
}