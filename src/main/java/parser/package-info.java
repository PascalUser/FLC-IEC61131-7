/**
 * Syntactic analyzer package.
 * <p>
 * Contains the Bison-generated {@link parser.Parser} and supporting classes
 * for syntactic analysis of IEC 61131-7 (FCL) source code.
 * </p>
 * <p>
 * The parser recognizes the full declaration grammar including:
 * </p>
 * <ul>
 *   <li>Function block declarations ({@code FUNCTION_BLOCK ... END_FUNCTION_BLOCK})</li>
 *   <li>Variable sections ({@code VAR_INPUT}, {@code VAR_OUTPUT}, {@code VAR}, {@code VAR CONSTANT})</li>
 *   <li>Type declarations ({@code TYPE ... END_TYPE}) with STRUCT, ENUMERATED, SUBRANGE, ARRAY</li>
 *   <li>Complex initialization expressions for all supported types</li>
 *   <li>Fuzzy blocks: FUZZIFY, DEFUZZIFY, RULEBLOCK, OPTION (syntactic recognition only)</li>
 * </ul>
 * <p>
 * Semantic actions use the {@link parser.facades.Publisher} pattern to defer
 * symbol table population until all contextual attributes are known.
 * </p>
 *
 * @see parser.Parser
 * @see parser.internals.ParsingContext
 * @see parser.facades.Publisher
 * @since 1.0
 */
package parser;