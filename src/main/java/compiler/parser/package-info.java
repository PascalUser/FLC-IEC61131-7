/**
 * Syntactic analyzer package.
 * <p>
 * Contains the Bison-generated {@link compiler.parser.Parser} and supporting classes
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
 * Semantic actions use the {@link compiler.parser.publishers.Publisher} hierarchy to defer
 * symbol table population until all contextual attributes are known.
 * </p>
 *
 * @see compiler.parser.Parser
 * @see compiler.parser.publishers.Publisher
 * @see compiler.parser.publishers.leafs.Declaration
 * @since 1.0
 */
package compiler.parser;
