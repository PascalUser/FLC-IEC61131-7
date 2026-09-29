/**
 * Lexical transformers (Decorator pattern).
 * <p>
 * Provides atomic {@link lexer.transformers.Transformer} implementations that form chains for
 * preprocessing raw lexemes before semantic analysis. Each transformer
 * performs a single normalization operation and delegates to the next in chain.
 * </p>
 * <p>
 * Transformers:
 * </p>
 * <ul>
 *   <li>{@link lexer.transformers.UnderscoreRemover} - removes underscores from numeric literals</li>
 *   <li>{@link lexer.transformers.UpperCaseConverter} - normalizes to uppercase (IEC 61131-7 is case-insensitive)</li>
 *   <li>{@link lexer.transformers.StripLeadingZeros} - strips leading zeros from decimal numbers</li>
 *   <li>{@link lexer.transformers.StripTrailingZeros} - strips trailing zeros from real number mantissas</li>
 *   <li>{@link lexer.transformers.StripBaseNumberLeadingZeros} - strips leading zeros from based numbers (after prefix)
 *   </li>
 *   <li>{@link lexer.transformers.OmitLeadingZeroMagnitudes} - removes leading zero magnitudes in intervals</li>
 *   <li>{@link lexer.transformers.OmitTrailingZeroMagnitudes} - removes trailing zero magnitudes in intervals</li>
 *   <li>{@link lexer.transformers.OmitLeadingZerosInMagnitudes} - strips leading zeros within interval magnitudes</li>
 *   <li>{@link lexer.transformers.OmitTrailingZerosInMagnitudes} - strips trailing zeros from fractional magnitudes
 *   </li>
 *   <li>{@link lexer.transformers.StringEscapeResolver} - resolves IEC 61131-7 string escape sequences</li>
 *   <li>{@link lexer.transformers.Nothing} - identity transformer (end of chain)</li>
 * </ul>
 *
 * @see lexer.transformers.Transformer
 * @see lexer.internals.LexicalPreprocessors
 * @since 1.0
 */
package lexer.transformers;