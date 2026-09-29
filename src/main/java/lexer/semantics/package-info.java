/**
 * Semantic analyzers for lexical tokens.
 * <p>
 * Each analyzer implements {@link lexer.semantics.SemanticAnalyzer} to validate
 * and convert preprocessed lexemes into semantic values. Analyzers register
 * validated symbols in the {@link utils.SymbolTable} and report diagnostics
 * via {@link utils.DiagnosticsHandler}.
 * </p>
 * <p>
 * Categories:
 * </p>
 * <ul>
 *   <li>{@link lexer.semantics.Identifiers} - keywords vs user identifiers</li>
 *   <li>{@link lexer.semantics.Dates}, {@link lexer.semantics.DayTimes},
 *       {@link lexer.semantics.DateAndDayTimes}, {@link lexer.semantics.Intervals} - temporal literals</li>
 *   <li>{@link lexer.semantics.numbers.Naturals}, {@link lexer.semantics.numbers.Integers},
 *       {@link lexer.semantics.numbers.Reals} - decimal numbers</li>
 *   <li>{@link lexer.semantics.numbers.bases.Binary}, {@link lexer.semantics.numbers.bases.Octal},
 *       {@link lexer.semantics.numbers.bases.Hexadecimal} - based numbers</li>
 *   <li>{@link lexer.semantics.strings.Strings}, {@link lexer.semantics.strings.WStrings} - string literals</li>
 * </ul>
 *
 * @see lexer.semantics.SemanticAnalyzer
 * @see lexer.internals.LexicalAnalyzers
 * @since 1.0
 */
package lexer.semantics;