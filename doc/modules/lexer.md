# Lexical Analyzer

Recognizes FCL lexemes, normalizes them, and communicates tokens and associated values to the parser.

**Sources:** `src/main/java/lexer/Lexer.flex`, `src/main/java/lexer/Lexer.java`, `src/main/java/lexer/internals/LexicalPreprocessors.java`, `src/main/java/lexer/internals/LexicalAnalyzers.java`, and `src/main/java/lexer/semantics/SemanticAnalyzer.java`. The generated `Lexer.java` carries a JFlex 1.8.2 header; `pom.xml` pins the JFlex plugin to 1.9.1.

## Package Dependencies

Directed edges from the importing package to the imported one, extracted from the `import` statements of `src/main/java/lexer/**/*.java` using `scripts/generate_diagrams.py lexer`. Wildcard imports are attributed to the imported package; the chart does not represent runtime calls.

```mermaid
flowchart LR
    lexer["lexer"]
    lexer_internals["lexer.internals"]
    lexer_semantics["lexer.semantics"]
    lexer_semantics_numbers["lexer.semantics.numbers"]
    lexer_semantics_numbers_bases["lexer.semantics.numbers.bases"]
    lexer_semantics_strings["lexer.semantics.strings"]
    lexer_semantics_utils["lexer.semantics.utils"]
    lexer_transformers["lexer.transformers"]
    lexer_transformers_hex_resolvers["lexer.transformers.hex_resolvers"]
    lexer_transformers_utils["lexer.transformers.utils"]
    parser["parser"]
    utils["utils"]
    utils_builders["utils.builders"]
    utils_diagnostics["utils.diagnostics"]
    utils_enums["utils.enums"]
    lexer --> lexer_internals
    lexer --> lexer_semantics
    lexer --> lexer_transformers
    lexer --> parser
    lexer --> utils
    lexer --> utils_diagnostics
    lexer_internals --> lexer_semantics
    lexer_internals --> lexer_semantics_numbers
    lexer_internals --> lexer_semantics_numbers_bases
    lexer_internals --> lexer_semantics_strings
    lexer_internals --> lexer_transformers
    lexer_internals --> lexer_transformers_hex_resolvers
    lexer_semantics --> lexer
    lexer_semantics --> lexer_semantics_utils
    lexer_semantics --> parser
    lexer_semantics --> utils
    lexer_semantics --> utils_builders
    lexer_semantics --> utils_diagnostics
    lexer_semantics --> utils_enums
    lexer_semantics_numbers --> lexer_semantics
    lexer_semantics_numbers --> parser
    lexer_semantics_numbers --> utils_builders
    lexer_semantics_numbers --> utils_diagnostics
    lexer_semantics_numbers --> utils_enums
    lexer_semantics_numbers_bases --> lexer_semantics_numbers
    lexer_semantics_numbers_bases --> utils_diagnostics
    lexer_semantics_numbers_bases --> utils_enums
    lexer_semantics_strings --> lexer_semantics
    lexer_semantics_strings --> parser
    lexer_semantics_strings --> utils_builders
    lexer_semantics_strings --> utils_diagnostics
    lexer_semantics_strings --> utils_enums
    lexer_semantics_utils --> lexer
    lexer_transformers --> lexer_transformers_utils
    lexer_transformers_hex_resolvers --> lexer_transformers

```

## Class Structure

Declarations, inheritance, and interface implementation extracted from the Java files in `src/main/java/lexer/`; the two associations of the scanner with the registries come from the `preprocessor` and `analyzer` fields in `src/main/java/lexer/Lexer.flex`.

```mermaid
classDiagram
    namespace lexer {
        class Lexer
    }
    namespace lexer_internals {
        class LexicalPreprocessors
        class LexicalAnalyzers
    }
    namespace lexer_transformers {
        class Transformer
        class UnderscoreRemover
        class UpperCaseConverter
        class StripLeadingZeros
        class StripTrailingZeros
        class StripBaseNumberLeadingZeros
        class StringEscapeResolver
        class Nothing
        class OmitLeadingZeroMagnitudes
        class OmitTrailingZeroMagnitudes
        class OmitLeadingZerosInMagnitudes
        class OmitTrailingZerosInMagnitudes
    }
    namespace lexer_transformers_hex_resolvers {
        class HexResolver
        class StringHexResolver
        class WStringHexResolver
    }
    namespace lexer_transformers_utils {
        class ExponentFinder
    }
    namespace lexer_semantics {
        class SemanticAnalyzer
        class Identifiers
        class Intervals
        class Dates
        class DayTimes
        class DateAndDayTimes
    }
    namespace lexer_semantics_numbers {
        class NumbersAnalyzer
        class Naturals
        class Integers
        class Reals
    }
    namespace lexer_semantics_numbers_bases {
        class BaseNumbersAnalyzer
        class Binary
        class Octal
        class Hexadecimal
    }
    namespace lexer_semantics_strings {
        class StringsAnalyzer
        class Strings
        class WStrings
    }
    namespace lexer_semantics_utils {
        class ReservedWords
    }
    SemanticAnalyzer <|-- DateAndDayTimes
    SemanticAnalyzer <|-- Dates
    SemanticAnalyzer <|-- DayTimes
    SemanticAnalyzer <|-- Identifiers
    SemanticAnalyzer <|-- Intervals
    NumbersAnalyzer <|-- Integers
    NumbersAnalyzer <|-- Naturals
    SemanticAnalyzer <|-- NumbersAnalyzer
    NumbersAnalyzer <|-- Reals
    NumbersAnalyzer <|-- BaseNumbersAnalyzer
    BaseNumbersAnalyzer <|-- Binary
    BaseNumbersAnalyzer <|-- Hexadecimal
    BaseNumbersAnalyzer <|-- Octal
    StringsAnalyzer <|-- Strings
    SemanticAnalyzer <|-- StringsAnalyzer
    StringsAnalyzer <|-- WStrings
    Transformer <|-- Nothing
    Transformer <|-- OmitLeadingZeroMagnitudes
    Transformer <|-- OmitLeadingZerosInMagnitudes
    Transformer <|-- OmitTrailingZeroMagnitudes
    Transformer <|-- OmitTrailingZerosInMagnitudes
    Transformer <|-- StringEscapeResolver
    Transformer <|-- StripBaseNumberLeadingZeros
    Transformer <|-- StripLeadingZeros
    Transformer <|-- StripTrailingZeros
    Transformer <|-- UnderscoreRemover
    Transformer <|-- UpperCaseConverter
    Transformer <|-- HexResolver
    HexResolver <|-- StringHexResolver
    HexResolver <|-- WStringHexResolver
    Lexer --> LexicalPreprocessors : uses
    Lexer --> LexicalAnalyzers : uses

```

| Class                                                 | Responsibility                                                                                                | Pattern or role         |
|-------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|-------------------------|
| `lexer_Lexer`                                         | Executes JFlex rules, applies the category preprocessor and analyzer, stores `yylval`, and returns the token. | Generated scanner       |
| `lexer_internals_LexicalPreprocessors`                | Declares static transformer chains by lexical category.                                                       | Chain registry          |
| `lexer_internals_LexicalAnalyzers`                    | Declares static analyzer instances by lexical category.                                                       | Strategy registry       |
| `lexer_transformers_Transformer`                      | Defines `transform` and delegates with `giveToNext`.                                                          | Chain of responsibility |
| `lexer_transformers_UnderscoreRemover`                | Removes underscore separators from the lexeme.                                                                | Transformer             |
| `lexer_transformers_UpperCaseConverter`               | Converts the lexeme to uppercase.                                                                             | Transformer             |
| `lexer_transformers_StripLeadingZeros`                | Removes leading zeros in the decimal integer part.                                                            | Transformer             |
| `lexer_transformers_StripTrailingZeros`               | Removes trailing zeros from reals.                                                                            | Transformer             |
| `lexer_transformers_StripBaseNumberLeadingZeros`      | Removes leading zeros after the base prefix.                                                                  | Transformer             |
| `lexer_transformers_OmitLeadingZeroMagnitudes`        | Omits null leading magnitudes of intervals.                                                                   | Transformer             |
| `lexer_transformers_OmitTrailingZeroMagnitudes`       | Omits null trailing magnitudes of intervals.                                                                  | Transformer             |
| `lexer_transformers_OmitLeadingZerosInMagnitudes`     | Removes leading zeros within interval magnitudes.                                                             | Transformer             |
| `lexer_transformers_OmitTrailingZerosInMagnitudes`    | Removes trailing zeros from the fraction of interval magnitudes.                                              | Transformer             |
| `lexer_transformers_Nothing`                          | Returns the lexeme unmodified.                                                                                | Identity transformer    |
| `lexer_transformers_StringEscapeResolver`             | Substitutes control and quote escapes.                                                                        | Transformer             |
| `lexer_transformers_hex_resolvers_HexResolver`        | Resolves hexadecimal escapes according to the width indicated by subclasses.                                  | Transformation template |
| `lexer_transformers_hex_resolvers_StringHexResolver`  | Configures STRING hexadecimal escapes (2 digits).                                                             | Specialization          |
| `lexer_transformers_hex_resolvers_WStringHexResolver` | Configures WSTRING hexadecimal escapes (4 digits).                                                            | Specialization          |
| `lexer_transformers_utils_ExponentFinder`             | Locates the exponent of a real literal.                                                                       | Utility                 |
| `lexer_semantics_SemanticAnalyzer`                    | Defines `analyze`, the lexical context, and result.                                                           | Strategy interface      |
| `lexer_semantics_numbers_NumbersAnalyzer`             | Shares analysis, warning, recovery, and publication of numbers.                                               | Template method         |
| `lexer_semantics_numbers_Naturals`                    | Determines subtype and value of decimal naturals.                                                             | Specialization          |
| `lexer_semantics_numbers_Integers`                    | Determines subtype and value of signed decimal integers.                                                      | Specialization          |
| `lexer_semantics_numbers_Reals`                       | Determines subtype and value of real literals.                                                                | Specialization          |
| `lexer_semantics_numbers_bases_BaseNumbersAnalyzer`   | Interprets numbers with base prefix and assigns subtype by value.                                             | Template method         |
| `lexer_semantics_numbers_bases_Binary`                | Defines base, maximum length, and diagnostics for binary numbers.                                             | Specialization          |
| `lexer_semantics_numbers_bases_Octal`                 | Defines base, maximum length, and diagnostics for octal numbers.                                              | Specialization          |
| `lexer_semantics_numbers_bases_Hexadecimal`           | Defines base, maximum length, and diagnostics for hexadecimal numbers.                                        | Specialization          |
| `lexer_semantics_Intervals`                           | Validates durations and stores a `Duration`.                                                                  | Strategy                |
| `lexer_semantics_Dates`                               | Validates dates and stores a `LocalDate`.                                                                     | Strategy                |
| `lexer_semantics_DayTimes`                            | Validates times and stores a `LocalTime`.                                                                     | Strategy                |
| `lexer_semantics_DateAndDayTimes`                     | Validates date-times and stores a `LocalDateTime`.                                                            | Strategy                |
| `lexer_semantics_strings_StringsAnalyzer`             | Truncates strings beyond 255 characters, issues a warning, and publishes the value.                           | Template method         |
| `lexer_semantics_strings_Strings`                     | Selects the STRING subtype.                                                                                   | Specialization          |
| `lexer_semantics_strings_WStrings`                    | Selects the WSTRING subtype.                                                                                  | Specialization          |
| `lexer_semantics_Identifiers`                         | Distinguishes reserved word, boolean, and identifier.                                                         | Strategy                |
| `lexer_semantics_utils_ReservedWords`                 | Associates reserved words with token identifiers.                                                             | Registry                |

**Table sources:** `src/main/java/lexer/internals/LexicalPreprocessors.java`, `src/main/java/lexer/internals/LexicalAnalyzers.java`, `src/main/java/lexer/transformers/`, `src/main/java/lexer/semantics/` and `src/main/java/lexer/Lexer.flex`.

## Normalization and Validation

| Category                              | Transformation Sequence                                                                                                                                          | Analyzer                               |
|---------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------|
| `DATES`, `DAYTIMES`, `DATE_AND_TIMES` | `Nothing`                                                                                                                                                        | `Dates`, `DayTimes`, `DateAndDayTimes` |
| `INTERVALS`                           | `UnderscoreRemover → UpperCaseConverter → OmitLeadingZeroMagnitudes → OmitTrailingZeroMagnitudes → OmitLeadingZerosInMagnitudes → OmitTrailingZerosInMagnitudes` | `Intervals`                            |
| `NATURALS`, `INTEGERS`                | `UnderscoreRemover → StripLeadingZeros`                                                                                                                          | `Naturals`, `Integers`                 |
| `BINARY`, `OCTAL`, `HEXADECIMAL`      | `UnderscoreRemover → StripBaseNumberLeadingZeros`                                                                                                                | `Binary`, `Octal`, `Hexadecimal`       |
| `REALS`                               | `UnderscoreRemover → StripTrailingZeros → StripLeadingZeros`                                                                                                     | `Reals`                                |
| `STRINGS`, `WSTRINGS`                 | `StringHexResolver` or `WStringHexResolver → StringEscapeResolver`                                                                                               | `Strings`, `WStrings`                  |
| `IDENTIFIERS`                         | `UpperCaseConverter`                                                                                                                                             | `Identifiers`                          |

**Source:** `src/main/java/lexer/internals/LexicalPreprocessors.java` and `src/main/java/lexer/internals/LexicalAnalyzers.java`.

Chart: `assets/transformer_chain_lengths.png` — number of constructor calls in each static chain of `src/main/java/lexer/internals/LexicalPreprocessors.java`, including the `Nothing` node in temporal categories; generated by `scripts/extract_stats.py`.

### Chain Object for Intervals

Instances and order extracted from the `INTERVALS` declaration in `src/main/java/lexer/internals/LexicalPreprocessors.java`:

```mermaid
flowchart LR
    root["LexicalPreprocessors.INTERVALS"] --> node0["UnderscoreRemover"]
    node0 --> node1["UpperCaseConverter"]
    node1 --> node2["OmitLeadingZeroMagnitudes"]
    node2 --> node3["OmitTrailingZeroMagnitudes"]
    node3 --> node4["OmitLeadingZerosInMagnitudes"]
    node4 --> node5["OmitTrailingZerosInMagnitudes"]
    node5 --> endNode["null"]

```

## Results and Errors

* `SemanticAnalyzer.LexicalContext` carries `preprocessedLexeme`, line, `SymbolTable`, and `DiagnosticsHandler`; `SemanticAnalyzer.Result` carries **`lexeme`** and **`token`**, in that order in the constructor. `processAndSaveYylval` saves `result.lexeme` in `yylval` and returns `result.token`. Sources: `src/main/java/lexer/semantics/SemanticAnalyzer.java`, `src/main/java/lexer/Lexer.flex`.
* `NumbersAnalyzer` returns `NUMERIC_LITERAL`; when the subtype is `UNKNOWN`, it adds a warning diagnostic and stores the recovery `ParsedValue`, whose key may differ from the original lexeme. `Naturals`, `Integers`, `Reals`, and base analyzers provide their respective recoveries. Sources: `src/main/java/lexer/semantics/numbers/NumbersAnalyzer.java`, `src/main/java/lexer/semantics/numbers/Naturals.java`, `src/main/java/lexer/semantics/numbers/Integers.java`, `src/main/java/lexer/semantics/numbers/Reals.java`, `src/main/java/lexer/semantics/numbers/bases/BaseNumbersAnalyzer.java`.
* `Dates`, `DayTimes`, and `DateAndDayTimes` store `LocalDate`, `LocalTime`, and `LocalDateTime`; `Intervals` stores `Duration` and verifies limits of subordinate magnitudes and accumulated nanoseconds. In their error paths, a diagnostic is added and `YYerror` is returned. The temporal actions of `Lexer.flex` only return `TIME_LITERAL` if there is no `YYerror`; otherwise, they reset the state and continue scanning. Sources: `src/main/java/lexer/semantics/Dates.java`, `src/main/java/lexer/semantics/DayTimes.java`, `src/main/java/lexer/semantics/DateAndDayTimes.java`, `src/main/java/lexer/semantics/Intervals.java`, `src/main/java/lexer/Lexer.flex`.
* `StringsAnalyzer` applies **`MAX_STRING_LENGTH = 255` to STRING and WSTRING** alike, measures Java's `content.length()` after resolving escapes, truncates content and key, issues `StringLengthWarning`, and returns `STRING_LITERAL`. Hexadecimal escapes are interpreted via `StringHexResolver` with two digits and `WStringHexResolver` with four. Sources: `src/main/java/lexer/semantics/strings/StringsAnalyzer.java`, `src/main/java/lexer/semantics/strings/Strings.java`, `src/main/java/lexer/semantics/strings/WStrings.java`, `src/main/java/lexer/transformers/hex_resolvers/StringHexResolver.java`, `src/main/java/lexer/transformers/hex_resolvers/WStringHexResolver.java`.
* `Identifiers` returns the direct token for a reserved word other than `TRUE` or `FALSE`; for booleans it registers `Subtype.BOOL` and returns `BOOLEAN_LITERAL`; for identifiers it registers a default `LexemeInfo` and returns `IDENTIFIER`. Sources: `src/main/java/lexer/semantics/Identifiers.java`, `src/main/java/lexer/semantics/utils/ReservedWords.java`.
* Analyzer diagnostics: numeric analyzers emit `NaturalOutOfRange`, `IntegerOutOfRange`, `RealOutOfRange`, `BinaryOutOfRange`, `OctalOutOfRange`, and `HexadecimalOutOfRange`; temporal analyzers emit `DateOutOfRange`, `TimeOfDayOutOfRange`, `DateAndTimeOutOfRange`, `IntervalConstructionError`, and `IntervalOutOfRange`; `StringsAnalyzer` emits `StringLengthWarning`; `Lexer.yyerror` emits `SyntaxError`. Sources: `src/main/java/lexer/semantics/numbers/`, `src/main/java/lexer/semantics/numbers/bases/`, `src/main/java/lexer/semantics/Dates.java`, `src/main/java/lexer/semantics/DayTimes.java`, `src/main/java/lexer/semantics/DateAndDayTimes.java`, `src/main/java/lexer/semantics/Intervals.java`, `src/main/java/lexer/semantics/strings/StringsAnalyzer.java`, `src/main/java/lexer/Lexer.flex`, `src/main/java/utils/diagnostics/`.

## Analysis Sequence

Flow verified with `src/main/java/lexer/Lexer.flex`, `src/main/java/lexer/internals/LexicalPreprocessors.java`, `src/main/java/lexer/internals/LexicalAnalyzers.java`, `src/main/java/lexer/semantics/SemanticAnalyzer.java`, `src/main/java/lexer/semantics/numbers/NumbersAnalyzer.java`, `src/main/java/lexer/semantics/strings/StringsAnalyzer.java`, and `src/main/java/parser/Parser.java`:

```mermaid
sequenceDiagram
    participant P as Parser (Bison)
    participant L as Lexer.java
    participant T as Transformer
    participant A as SemanticAnalyzer
    participant S as SymbolTable
    participant D as DiagnosticsHandler
    P->>L: yylex()
    L->>T: transform(yytext())
    loop Chained Transformers
        T->>T: giveToNext(lexeme)
    end
    T-->>L: preprocessedLexeme
    L->>A: analyze(LexicalContext)
    opt Semantic warning or error
        A->>D: add(Diagnostic)
    end
    opt Valid literal or numeric fallback
        A->>S: putIfAbsent(lexeme, LexemeInfo)
    end
    A-->>L: Result(lexeme, token)
    L->>L: yylval = result.lexeme
    alt token != YYerror
        L-->>P: token
        P->>L: getLVal()
        L-->>P: yylval
    else token == YYerror
        L->>L: yybegin(YYINITIAL)
    end

```

## How to Test

| Class in `src/test/java`                                                                                    | Verification                                                                                        |
|-------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| `unit/lexer/LexerTokenizationTest.java`                                                                     | Reserved words, operators, comments, identifiers, strings, numeric literals, and temporal literals. |
| `unit/lexer/LexerSymbolTableTest.java`                                                                      | Subtypes, values, canonical keys, boolean and identifier entries, and string truncation.            |
| `unit/lexer/LexerDiagnosticHandlerTest.java`                                                                | Numeric range warnings, temporal errors, and string-length warnings.                                |
| `unit/lexer/transformers/StringEscapeResolverTest.java`                                                     | Interpretation of standard escapes.                                                                 |
| `unit/lexer/transformers/StringHexResolverTest.java`, `unit/lexer/transformers/WStringHexResolverTest.java` | String hexadecimal escapes with two and four digits.                                                |
| `unit/lexer/transformers/OmitLeadingZeroMagnitudesTest.java`                                                | Removal of zero-valued leading magnitudes in intervals.                                             |
| `unit/lexer/transformers/OmitTrailingZeroMagnitudesTest.java`                                               | Removal of zero-valued trailing magnitudes in intervals.                                            |
| `unit/lexer/transformers/OmitLeadingZerosInMagnitudesTest.java`                                             | Removal of leading zeros within interval magnitudes.                                                |
| `unit/lexer/transformers/OmitTrailingZerosInMagnitudesTest.java`                                            | Removal of trailing zeros from the fraction of interval magnitudes.                                 |

```bash
mvn -q -Dtest=LexerTokenizationTest,LexerSymbolTableTest,LexerDiagnosticHandlerTest,StringEscapeResolverTest,StringHexResolverTest,WStringHexResolverTest,OmitLeadingZeroMagnitudesTest,OmitTrailingZeroMagnitudesTest,OmitLeadingZerosInMagnitudesTest,OmitTrailingZerosInMagnitudesTest test

```

Chart: `assets/test_coverage.png` — line coverage per package from the JaCoCo report; `doc/stats.json` records 89 percent for the lexer package.

## File Tree

Generation file and versioned source classes; the local backup `Lexer.java~` is omitted. Source: `src/main/java/lexer/`.

```text
src/main/java/lexer/
    internals/
        LexicalAnalyzers.java
        LexicalPreprocessors.java
        package-info.java
    semantics/
        numbers/
            bases/
                BaseNumbersAnalyzer.java
                Binary.java
                Hexadecimal.java
                Octal.java
                package-info.java
            Integers.java
            Naturals.java
            NumbersAnalyzer.java
            Reals.java
            package-info.java
        strings/
            Strings.java
            StringsAnalyzer.java
            WStrings.java
            package-info.java
        utils/
            ReservedWords.java
            package-info.java
        DateAndDayTimes.java
        Dates.java
        DayTimes.java
        Identifiers.java
        Intervals.java
        SemanticAnalyzer.java
        package-info.java
    transformers/
        hex_resolvers/
            HexResolver.java
            StringHexResolver.java
            WStringHexResolver.java
            package-info.java
        utils/
            ExponentFinder.java
            package-info.java
        Nothing.java
        OmitLeadingZeroMagnitudes.java
        OmitLeadingZerosInMagnitudes.java
        OmitTrailingZeroMagnitudes.java
        OmitTrailingZerosInMagnitudes.java
        StringEscapeResolver.java
        StripBaseNumberLeadingZeros.java
        StripLeadingZeros.java
        StripTrailingZeros.java
        Transformer.java
        UnderscoreRemover.java
        UpperCaseConverter.java
        package-info.java
    Lexer.flex
    Lexer.java
    package-info.java

```

## Usage in Thesis

According to `.opencode/agents/thesis-writer.md`, this module provides transformers, analyzers, and diagnostics to the chapter `doc/thesis/05-analisis-lexico.md`, and maintainability and testing decisions to the chapter `doc/thesis/07-mantenibilidad.md`. Coverage in `doc/stats.json` is read from the JaCoCo report at `target/site/jacoco/jacoco.xml` by `scripts/extract_stats.py`; it is not a hard-coded default.
