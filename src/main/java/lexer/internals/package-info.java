/**
 * Lexer internal coordination classes.
 * <p>
 * Contains {@link lexer.internals.LexicalPreprocessors} (transformer chain registry) and
 * {@link lexer.internals.LexicalAnalyzers} (semantic analyzer registry). These classes
 * define the two-phase pipeline for each token category.
 * </p>
 *
 * @see lexer.internals.LexicalPreprocessors
 * @see lexer.internals.LexicalAnalyzers
 * @since 1.0
 */
package lexer.internals;