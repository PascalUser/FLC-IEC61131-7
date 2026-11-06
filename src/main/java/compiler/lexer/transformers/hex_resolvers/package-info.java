/**
 * Hex escape resolvers for STRING and WSTRING literals.
 * <p>
 * IEC 61131-7 string literals support hex escape sequences:
 * </p>
 * <ul>
 *   <li>STRING: {@code $HH} (2 hex digits per character)</li>
 *   <li>WSTRING: {@code $HHHH} (4 hex digits per character)</li>
 * </ul>
 * <p>
 * Both resolvers preserve {@code $$} as literal {@code $} and delegate
 * standard escapes to {@link compiler.lexer.transformers.StringEscapeResolver}.
 * </p>
 *
 * @see compiler.lexer.transformers.hex_resolvers.StringHexResolver
 * @see compiler.lexer.transformers.hex_resolvers.WStringHexResolver
 * @see compiler.lexer.transformers.hex_resolvers.HexResolver
 * @see compiler.lexer.transformers.StringEscapeResolver
 * @since 1.0
 */
package compiler.lexer.transformers.hex_resolvers;