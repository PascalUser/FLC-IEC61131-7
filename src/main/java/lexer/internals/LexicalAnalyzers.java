package lexer.internals;

import lexer.semantics.*;
import lexer.semantics.strings.*;
import lexer.semantics.numbers.*;
import lexer.semantics.numbers.bases.*;

/**
 * Registry of semantic analyzers for each lexical category.
 * <p>
 * Maps each token category to its {@link SemanticAnalyzer} implementation.
 * Analyzers perform validation, range checking, and symbol table registration.
 * </p>
 * <p>Analyzers:</p>
 * <ul>
 *   <li>{@code DATE_AND_TIMES}: {@link lexer.semantics.DateAndDayTimes}</li>
 *   <li>{@code DAYTIMES}: {@link lexer.semantics.DayTimes}</li>
 *   <li>{@code DATES}: {@link lexer.semantics.Dates}</li>
 *   <li>{@code INTERVALS}: {@link lexer.semantics.Intervals}</li>
 *   <li>{@code NATURALS}: {@link lexer.semantics.numbers.Naturals}</li>
 *   <li>{@code INTEGERS}: {@link lexer.semantics.numbers.Integers}</li>
 *   <li>{@code REALS}: {@link lexer.semantics.numbers.Reals}</li>
 *   <li>{@code BINARY}: {@link lexer.semantics.numbers.bases.Binary}</li>
 *   <li>{@code OCTAL}: {@link lexer.semantics.numbers.bases.Octal}</li>
 *   <li>{@code HEXADECIMAL}: {@link lexer.semantics.numbers.bases.Hexadecimal}</li>
 *   <li>{@code STRINGS}: {@link lexer.semantics.strings.Strings}</li>
 *   <li>{@code WSTRINGS}: {@link lexer.semantics.strings.WStrings}</li>
 *   <li>{@code IDENTIFIERS}: {@link lexer.semantics.Identifiers}</li>
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
    public static final SemanticAnalyzer DATE_AND_TIMES  = new DateAndDayTimes();
    public static final SemanticAnalyzer DAYTIMES        = new DayTimes();
    public static final SemanticAnalyzer DATES           = new Dates();
    public static final SemanticAnalyzer INTERVALS       = new Intervals();
    // Numeric Literals
    public static final SemanticAnalyzer NATURALS    = new Naturals();
    public static final SemanticAnalyzer INTEGERS    = new Integers();
    public static final SemanticAnalyzer REALS       = new Reals();
    public static final SemanticAnalyzer BINARY      = new Binary();
    public static final SemanticAnalyzer OCTAL       = new Octal();
    public static final SemanticAnalyzer HEXADECIMAL = new Hexadecimal();
    // String Literals
    public static final SemanticAnalyzer STRINGS  = new Strings();
    public static final SemanticAnalyzer WSTRINGS = new WStrings();
    // Identifiers
    public static final SemanticAnalyzer IDENTIFIERS = new Identifiers();
}
