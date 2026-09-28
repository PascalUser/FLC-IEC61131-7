/**
 * Parser utility classes.
 * <p>
 * Contains supporting classes for semantic actions:
 * </p>
 * <ul>
 *   <li>{@link Publisher} - static publishing utility and base for Declaration/Compound pattern</li>
 *   <li>{@link Factory} - creates default initializations for primitive types</li>
 *   <li>{@link DimensionCalculator} - computes total array dimension from bounds</li>
 *   <li>{@link UnderlyingScopeSearcher} - resolves type alias chains to root definition</li>
 * </ul>
 *
 * @see Publisher
 * @see Factory
 * @see DimensionCalculator
 * @see UnderlyingScopeSearcher
 * @since 1.0
 */
package parser.utils;