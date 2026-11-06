/**
 * Semantic analyzers for lexical tokens.
 * <p>
 * Each analyzer implements {@link compiler.lexer.semantics.SemanticAnalyzer} to validate
 * and convert preprocessed lexemes into semantic values. Analyzers register
 * validated symbols in the {@link compiler.repository.SymbolTable} and report diagnostics
 * via {@link compiler.repository.DiagnosticsHandler}.
 * </p>
 * <p>
 * Categories:
 * </p>
 * <ul>
 *   <li>{@link compiler.lexer.semantics.Identifiers} - keywords vs user identifiers</li>
 *   <li>{@link compiler.lexer.semantics.Dates}, {@link compiler.lexer.semantics.DayTimes},
 *       {@link compiler.lexer.semantics.DateAndDayTimes}, {@link compiler.lexer.semantics.Intervals} - temporal literals</li>
 *   <li>{@link compiler.lexer.semantics.numbers.Naturals}, {@link compiler.lexer.semantics.numbers.Integers},
 *       {@link compiler.lexer.semantics.numbers.Reals} - decimal numbers</li>
 *   <li>{@link compiler.lexer.semantics.numbers.bases.Binary}, {@link compiler.lexer.semantics.numbers.bases.Octal},
 *       {@link compiler.lexer.semantics.numbers.bases.Hexadecimal} - based numbers</li>
 *   <li>{@link compiler.lexer.semantics.strings.Strings}, {@link compiler.lexer.semantics.strings.WStrings} - string literals</li>
 * </ul>
 *
 * @see compiler.lexer.semantics.SemanticAnalyzer
 * @see compiler.lexer.internals.LexicalAnalyzers
 * @since 1.0
 */
package compiler.lexer.semantics;