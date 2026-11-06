/**
 * Lexer internal coordination classes.
 * <p>
 * Contains {@link compiler.lexer.internals.LexicalPreprocessors} (transformer chain registry) and
 * {@link compiler.lexer.internals.LexicalAnalyzers} (semantic analyzer registry). These classes
 * define the two-phase pipeline for each token category.
 * </p>
 *
 * @see compiler.lexer.internals.LexicalPreprocessors
 * @see compiler.lexer.internals.LexicalAnalyzers
 * @since 1.0
 */
package compiler.lexer.internals;