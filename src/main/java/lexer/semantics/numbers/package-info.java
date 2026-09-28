/**
 * Semantic analyzers for decimal numeric literals.
 * <p>
 * Provides {@link NumbersAnalyzer} base class and concrete implementations
 * for natural (unsigned), integer (signed), and real (floating-point) literals.
 * Range-based subtype determination (SINT..ULINT, REAL/LREAL) with fallback
 * for out-of-range values.
 * </p>
 *
 * @see NumbersAnalyzer
 * @see Naturals
 * @see Integers
 * @see Reals
 * @since 1.0
 */
package lexer.semantics.numbers;