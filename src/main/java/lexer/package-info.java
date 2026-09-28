/**
 * Lexical analyzer package.
 * <p>
 * Contains the JFlex-generated {@link lexer.Lexer} and supporting classes for
 * lexical analysis of IEC 61131-7 (FCL) source code.
 * </p>
 * <p>
 * The lexer recognizes:
 * <ul>
 *   <li>Identifiers and reserved words</li>
 *   <li>Numeric literals (natural, integer, real, binary, octal, hexadecimal)</li>
 *   <li>Temporal literals (DATE, TIME_OF_DAY, DATE_AND_TIME, TIME intervals)</li>
 *   <li>String literals (STRING, WSTRING)</li>
 * </ul>
 * </p>
 * <p>
 * Processing follows a two-phase pipeline per token:
 * </p>
 * <ol>
 *   <li>Preprocessing: {@link lexer.internals.LexicalPreprocessors} - transformer chains</li>
 *   <li>Semantic analysis: {@link lexer.internals.LexicalAnalyzers} - validation and symbol table registration</li>
 * </ol>
 *
 * @see lexer.Lexer
 * @see lexer.internals.LexicalPreprocessors
 * @see lexer.internals.LexicalAnalyzers
 * @since 1.0
 */
package lexer;