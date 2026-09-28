/**
 * Builder pattern for LexemeInfo construction.
 * <p>
 * Provides the fluent API for configuring semantic attributes:
 * </p>
 * <ul>
 *   <li>{@link LexemeInfoSchema} - interface defining the builder contract</li>
 *   <li>{@link LexemeInfoBuilder} - concrete implementation with {@code build()}</li>
 *   <li>{@link Director} - predefined recipes for common patterns (literals, defaults)</li>
 * </ul>
 * <p>
 * Both {@link parser.utils.Publisher} implementations ({@link parser.utils.Declaration},
 * {@link parser.utils.Compound}) extend {@code LexemeInfoSchema}, enabling them
 * to participate in the fluent configuration chain before publishing.
 * </p>
 *
 * @see LexemeInfoSchema
 * @see LexemeInfoBuilder
 * @see Director
 * @since 1.0
 */
package utils.builders;