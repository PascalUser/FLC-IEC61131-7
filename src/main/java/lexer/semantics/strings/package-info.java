/**
 * Semantic analyzers for string literals (STRING, WSTRING).
 * <p>
 * Handles content extraction, escape sequence resolution (delegated to
 * transformers), and length validation (max 255 characters per IEC 61131-7).
 * </p>
 *
 * @see StringsAnalyzer
 * @see Strings
 * @see WStrings
 * @since 1.0
 */
package lexer.semantics.strings;