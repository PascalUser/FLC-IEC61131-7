/**
 * Semantic analyzers for based numeric literals (binary, octal, hexadecimal).
 * <p>
 * Extends {@link NumbersAnalyzer} to handle explicit base prefixes
 * (2#, 8#, 16#). Determines bit-string subtype (BYTE, WORD, DWORD, LWORD)
 * based on digit count.
 * </p>
 *
 * @see BaseNumbersAnalyzer
 * @see Binary
 * @see Octal
 * @see Hexadecimal
 * @since 1.0
 */
package lexer.semantics.numbers.bases;