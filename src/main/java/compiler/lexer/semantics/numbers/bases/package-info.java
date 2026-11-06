/**
 * Semantic analyzers for based numeric literals (binary, octal, hexadecimal).
 * <p>
 * Extends {@link compiler.lexer.semantics.numbers.NumbersAnalyzer} to handle explicit base prefixes
 * (2#, 8#, 16#). Determines bit-string subtype (BYTE, WORD, DWORD, LWORD)
 * based on digit count.
 * </p>
 *
 * @see compiler.lexer.semantics.numbers.bases.BaseNumbersAnalyzer
 * @see compiler.lexer.semantics.numbers.bases.Binary
 * @see compiler.lexer.semantics.numbers.bases.Octal
 * @see compiler.lexer.semantics.numbers.bases.Hexadecimal
 * @since 1.0
 */
package compiler.lexer.semantics.numbers.bases;