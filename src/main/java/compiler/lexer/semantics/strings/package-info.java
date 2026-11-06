/**
 * Semantic analyzers for string literals (STRING, WSTRING).
 * <p>
 * Handles content extraction, escape sequence resolution (delegated to
 * transformers), and length validation (max 255 characters per IEC 61131-7).
 * </p>
 *
 * @see compiler.lexer.semantics.strings.StringsAnalyzer
 * @see compiler.lexer.semantics.strings.Strings
 * @see compiler.lexer.semantics.strings.WStrings
 * @since 1.0
 */
package compiler.lexer.semantics.strings;