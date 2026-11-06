package compiler.lexer.internals;

import compiler.lexer.semantics.*;
import compiler.lexer.semantics.numbers.Integers;
import compiler.lexer.semantics.numbers.Naturals;
import compiler.lexer.semantics.numbers.Reals;
import compiler.lexer.semantics.numbers.bases.Binary;
import compiler.lexer.semantics.numbers.bases.Hexadecimal;
import compiler.lexer.semantics.numbers.bases.Octal;
import compiler.lexer.semantics.strings.Strings;
import compiler.lexer.semantics.strings.WStrings;

/**
 * Registry of semantic analyzers for each lexical category.
 * <p>
 * Maps each token category to its {@link SemanticAnalyzer} implementation.
 * Analyzers perform validation, range checking, and symbol table registration.
 * </p>
 * <p>Analyzers:</p>
 * <ul>
 *   <li>{@code DATE_AND_TIMES}: {@link DateAndDayTimes}</li>
 *   <li>{@code DAYTIMES}: {@link DayTimes}</li>
 *   <li>{@code DATES}: {@link Dates}</li>
 *   <li>{@code INTERVALS}: {@link Intervals}</li>
 *   <li>{@code NATURALS}: {@link Naturals}</li>
 *   <li>{@code INTEGERS}: {@link Integers}</li>
 *   <li>{@code REALS}: {@link Reals}</li>
 *   <li>{@code BINARY}: {@link Binary}</li>
 *   <li>{@code OCTAL}: {@link Octal}</li>
 *   <li>{@code HEXADECIMAL}: {@link Hexadecimal}</li>
 *   <li>{@code STRINGS}: {@link Strings}</li>
 *   <li>{@code WSTRINGS}: {@link WStrings}</li>
 *   <li>{@code IDENTIFIERS}: {@link Identifiers}</li>
 * </ul>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see SemanticAnalyzer
 * @see LexicalPreprocessors
 */
public final class LexicalAnalyzers  {
    // Time Literals
    /** Analyzer of date and time literals. */
    public static final SemanticAnalyzer DATE_AND_TIMES  = new DateAndDayTimes();
    /** Analyzer of daytime literals. */
    public static final SemanticAnalyzer DAYTIMES        = new DayTimes();
    /** Analyzer of date literals. */
    public static final SemanticAnalyzer DATES           = new Dates();
    /** Analyzer of interval literals. */
    public static final SemanticAnalyzer INTERVALS       = new Intervals();
    // Numeric Literals
    /** Analyzer of natural (unsigned) literals. */
    public static final SemanticAnalyzer NATURALS    = new Naturals();
    /** Analyzer of integer literals. */
    public static final SemanticAnalyzer INTEGERS    = new Integers();
    /** Analyzer of real literals. */
    public static final SemanticAnalyzer REALS       = new Reals();
    /** Analyzer of binary literals. */
    public static final SemanticAnalyzer BINARY      = new Binary();
    /** Analyzer of octal literals. */
    public static final SemanticAnalyzer OCTAL       = new Octal();
    /** Analyzer of hexadecimal literals. */
    public static final SemanticAnalyzer HEXADECIMAL = new Hexadecimal();
    // String Literals
    /** Analyzer of single-quoted string literals. */
    public static final SemanticAnalyzer STRINGS  = new Strings();
    /** Analyzer of double-quoted wide string literals. */
    public static final SemanticAnalyzer WSTRINGS = new WStrings();
    // Identifiers
    /** Analyzer of identifiers. */
    public static final SemanticAnalyzer IDENTIFIERS = new Identifiers();
}
