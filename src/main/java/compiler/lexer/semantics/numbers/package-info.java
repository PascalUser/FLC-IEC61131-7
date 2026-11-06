/**
 * Semantic analyzers for decimal numeric literals.
 * <p>
 * Provides {@link compiler.lexer.semantics.numbers.NumbersAnalyzer} base class and concrete implementations
 * for natural (unsigned), integer (signed), and real (floating-point) literals.
 * Range-based subtype determination (SINT..ULINT, REAL/LREAL) with fallback
 * for out-of-range values.
 * </p>
 *
 * @see compiler.lexer.semantics.numbers.NumbersAnalyzer
 * @see compiler.lexer.semantics.numbers.Naturals
 * @see compiler.lexer.semantics.numbers.Integers
 * @see compiler.lexer.semantics.numbers.Reals
 * @since 1.0
 */
package compiler.lexer.semantics.numbers;