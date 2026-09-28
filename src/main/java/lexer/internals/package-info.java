/**
 * Lexer internal coordination classes.
 * <p>
 * Contains {@link LexicalPreprocessors} (transformer chain registry) and
 * {@link LexicalAnalyzers} (semantic analyzer registry). These classes
 * define the two-phase pipeline for each token category.
 * </p>
 *
 * @see LexicalPreprocessors
 * @see LexicalAnalyzers
 * @since 1.0
 */
package lexer.internals;