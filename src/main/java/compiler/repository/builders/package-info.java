/**
 * Builder pattern for LexemeInfo construction.
 * <p>
 * Provides the fluent API for configuring semantic attributes:
 * </p>
 * <ul>
 *   <li>{@link compiler.repository.builders.LexemeInfoMutator} - interface defining the builder contract</li>
 *   <li>{@link compiler.repository.builders.LexemeInfoBuilder} - concrete implementation with {@code build()}</li>
 *   <li>{@link compiler.repository.builders.Director} - predefined recipes for common patterns (literals, defaults)</li>
 * </ul>
 *
 * @see compiler.repository.builders.LexemeInfoMutator
 * @see compiler.repository.builders.LexemeInfoBuilder
 * @see compiler.repository.builders.Director
 * @since 1.0
 */
package compiler.repository.builders;
