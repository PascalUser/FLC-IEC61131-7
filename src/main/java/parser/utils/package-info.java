/**
 * Parser utility classes.
 * <p>
 * Contains supporting classes for semantic actions:
 * </p>
 * <ul>
 *   <li>{@link parser.initializations.Factory} - creates default initializations for primitive types</li>
 *   <li>{@link parser.facades.DimensionCalculator} - computes total array dimension from bounds</li>
 *   <li>{@link parser.facades.UnderlyingScopeSearcher} - resolves type alias chains to root definition</li>
 * </ul>
 *
 * @see parser.initializations.Factory
 * @see parser.facades.DimensionCalculator
 * @see parser.facades.UnderlyingScopeSearcher
 * @since 1.0
 */
package parser.utils;