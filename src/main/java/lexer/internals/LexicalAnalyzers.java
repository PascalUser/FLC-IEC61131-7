package lexer.internals;

import lexer.semantics.*;
import lexer.semantics.strings.*;
import lexer.semantics.numbers.*;
import lexer.semantics.numbers.bases.*;

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
