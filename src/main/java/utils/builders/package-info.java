/**
 * Builder pattern for LexemeInfo construction.
 * <p>
 * Provides the fluent API for configuring semantic attributes:
 * </p>
 * <ul>
 *   <li>{@link utils.builders.LexemeInfoMutator} - interface defining the builder contract</li>
 *   <li>{@link utils.builders.LexemeInfoBuilder} - concrete implementation with {@code build()}</li>
 *   <li>{@link utils.builders.Director} - predefined recipes for common patterns (literals, defaults)</li>
 * </ul>
 *
 * @see utils.builders.LexemeInfoMutator
 * @see utils.builders.LexemeInfoBuilder
 * @see utils.builders.Director
 * @since 1.0
 */
package utils.builders;
