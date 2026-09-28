# Architecture Diagrams (PlantUML)

This document contains PlantUML diagrams for the FLC-IEC61131-7 compiler architecture. Render with any PlantUML viewer (VS Code extension, IntelliJ plugin, or online at plantuml.com).

---

## 1. Compiler Pipeline (Sequence Diagram)

```plantuml
@startuml
actor User
participant "Main" as Main
participant "Lexer\n(JFlex)" as Lexer
participant "Parser\n(Bison)" as Parser
participant "SymbolTable" as ST
participant "DiagnosticsHandler" as DH
participant "NameMangler" as NM

User -> Main: compile(file.fcl)
Main -> Lexer: new Lexer(symbolTable, diagnostics)
Main -> Parser: new Parser(lexer, symbolTable, nameMangler)
Main -> Parser: yyparse()

loop For each token
    Parser -> Lexer: yylex()
    Lexer -> Lexer: Match regex rule
    Lexer -> Lexer: transformer.transform(yytext())
    Lexer -> Lexer: analyzer.analyze(context)
    Lexer --> Parser: token + semantic value
    Parser -> Parser: Shift/Reduce (LALR(1))
    alt Semantic action
        Parser -> Parser: Create Publisher/Compound
        Parser -> Parser: Configure attributes (source, type, use)
        Parser -> ST: publisher.publish()
        ST -> ST: put(mangledName, LexemeInfo)
    end
end

alt Syntax Error
    Parser -> DH: yyerror(msg)
    DH -> DH: add(SyntaxError)
end

Parser --> Main: 0 (success) / 1 (error)
Main -> ST: Access declarations
Main -> DH: Check errors/warnings
Main --> User: Result
@enduml
```

---

## 2. Initialization Class Hierarchy (Class Diagram)

```plantuml
@startuml
interface Initialization {
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
}

class Constant {
    - value: String
    + Constant(String)
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
    + set(String): Constant
    + get(): String
}

class VariableInitialization {
    - value: String
    + VariableInitialization(String)
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
}

class BooleanInitialization {
    - DEFAULT: String (static)
    - symbolTable: SymbolTable
    + BooleanInitialization(SymbolTable)
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
}

class EnumeratedInitialization {
    - values: List<String>
    + EnumeratedInitialization(List<String>)
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
}

class MacroInitialization {
    - ordinal: String
    + MacroInitialization(SymbolTable, String)
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
}

class RealInitialization {
    - DEFAULT: String (static)
    - symbolTable: SymbolTable
    + RealInitialization(SymbolTable)
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
}

class SubrangeInitialization {
    - inferiorLimit: String
    - superiorLimit: String
    + SubrangeInitialization(String, String)
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
}

class StructInitialization {
    - fields: HashMap<String, Initialization>
    + StructInitialization()
    + setFieldInitialization(String, Initialization)
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
}

class RepeatedInitialization {
    - intervals: List<Interval>
    - dimension: int
    + RepeatedInitialization(int, Initialization)
    + addInterval(int, int, Initialization)
    + selectVariable(String): Initialization
    + getVariableValue(): String
    + copy(): Initialization
    + getDimension(): int
    + getRepetitionsList(): List<Integer>
    + getInitializationsList(): List<Initialization>
}

class Interval {
    - start: int
    - end: int
    - initialization: Initialization
    + length(): int
    + overlaps(int, int): boolean
}

Initialization <|-- Constant
Initialization <|-- VariableInitialization
Initialization <|-- BooleanInitialization
Initialization <|-- EnumeratedInitialization
Initialization <|-- MacroInitialization
Initialization <|-- RealInitialization
Initialization <|-- SubrangeInitialization
Initialization <|-- StructInitialization
Initialization <|-- RepeatedInitialization

RepeatedInitialization *-- Interval
StructInitialization *-- Initialization : fields
@enduml
```

---

## 3. Diagnostic Class Hierarchy (Class Diagram)

```plantuml
@startuml
abstract class Diagnostic {
    # line: int
    + Diagnostic(int)
    + getMessage(): String
    + {abstract} fatalForCompilation(): boolean
}

class Error {
    + Error(int)
    + fatalForCompilation(): boolean
}

class Warning {
    + Warning(int)
    + fatalForCompilation(): boolean
}

class IntegerOutOfRange
class RealOutOfRange
class OctalOutOfRange
class HexadecimalOutOfRange
class BinaryOutOfRange
class NaturalOutOfRange
class IntervalOutOfRange
class IntervalConstructionError
class TimeOfDayOutOfRange
class DateAndTimeOutOfRange
class DateOutOfRange

class StringLengthWarning
class SyntaxError

Diagnostic <|-- Error
Diagnostic <|-- Warning

Error <|-- IntegerOutOfRange
Error <|-- RealOutOfRange
Error <|-- OctalOutOfRange
Error <|-- HexadecimalOutOfRange
Error <|-- BinaryOutOfRange
Error <|-- NaturalOutOfRange
Error <|-- IntervalOutOfRange
Error <|-- IntervalConstructionError
Error <|-- TimeOfDayOutOfRange
Error <|-- DateAndTimeOutOfRange
Error <|-- DateOutOfRange
Error <|-- SyntaxError

Warning <|-- StringLengthWarning
@enduml
```

---

## 4. Package Dependencies (Package Diagram)

```plantuml
@startuml
package "lexer" {
    [Lexer.flex]
    [Lexer.java]
    [LexicalPreprocessors]
    [LexicalAnalyzers]
}

package "lexer.transformers" {
    [Transformer] <<abstract>>
    [UnderscoreRemover]
    [UpperCaseConverter]
    [StripLeadingZeros]
    [StripTrailingZeros]
    [StripBaseNumberLeadingZeros]
    [OmitLeadingZeroMagnitudes]
    [OmitTrailingZeroMagnitudes]
    [OmitLeadingZerosInMagnitudes]
    [OmitTrailingZerosInMagnitudes]
    [StringEscapeResolver]
    [Nothing]
}

package "lexer.transformers.hex_resolvers" {
    [HexResolver] <<abstract>>
    [StringHexResolver]
    [WStringHexResolver]
}

package "lexer.transformers.utils" {
    [ExponentFinder]
}

package "lexer.semantics" {
    [SemanticAnalyzer] <<interface>>
    [Identifiers]
    [Dates]
    [DayTimes]
    [DateAndDayTimes]
    [Intervals]
}

package "lexer.semantics.numbers" {
    [NumbersAnalyzer] <<abstract>>
    [Naturals]
    [Integers]
    [Reals]
}

package "lexer.semantics.numbers.bases" {
    [BaseNumbersAnalyzer] <<abstract>>
    [Binary]
    [Octal]
    [Hexadecimal]
}

package "lexer.semantics.strings" {
    [StringsAnalyzer] <<abstract>>
    [Strings]
    [WStrings]
}

package "lexer.semantics.utils" {
    [ReservedWords]
}

package "lexer.internals" {
    [LexicalPreprocessors]
    [LexicalAnalyzers]
}

package "parser" {
    [Parser.y]
    [Parser.java]
}

package "parser.internals" {
    [ParsingContext]
    [NameMangler]
    [ContextHandler]
}

package "parser.initializations" {
    [Initialization] <<interface>>
    [Constant]
    [VariableInitialization]
    [BooleanInitialization]
    [EnumeratedInitialization]
    [MacroInitialization]
    [RealInitialization]
    [SubrangeInitialization]
    [StructInitialization]
    [RepeatedInitialization]
}

package "parser.utils" {
    [Publisher]
    [Factory]
    [DimensionCalculator]
    [UnderlyingScopeSearcher]
}

package "utils" {
    [SymbolTable]
    [LexemeInfo]
    [DiagnosticsHandler]
    [LucaInfo]
}

package "utils.builders" {
    [LexemeInfoSchema] <<interface>>
    [LexemeInfoBuilder]
    [Director]
}

package "utils.diagnostics" {
    [Diagnostic] <<abstract>>
    [Error]
    [Warning]
    [IntegerOutOfRange]
    [RealOutOfRange]
    [OctalOutOfRange]
    [HexadecimalOutOfRange]
    [BinaryOutOfRange]
    [NaturalOutOfRange]
    [IntervalOutOfRange]
    [IntervalConstructionError]
    [TimeOfDayOutOfRange]
    [DateAndTimeOutOfRange]
    [DateOutOfRange]
    [StringLengthWarning]
    [SyntaxError]
}

package "utils.enums" {
    [Type] <<enum>>
    [Subtype] <<enum>>
    [Use] <<enum>>
    [Source] <<enum>>
}

' Dependencies
lexer --> lexer.transformers : uses
lexer --> lexer.transformers.hex_resolvers : uses
lexer --> lexer.transformers.utils : uses
lexer --> lexer.semantics : uses
lexer --> lexer.semantics.numbers : uses
lexer --> lexer.semantics.numbers.bases : uses
lexer --> lexer.semantics.strings : uses
lexer --> lexer.semantics.utils : uses
lexer --> lexer.internals : uses

lexer.transformers.hex_resolvers --> lexer.transformers : extends
lexer.transformers.utils --> lexer.transformers : uses

lexer.semantics.numbers --> lexer.semantics : uses
lexer.semantics.numbers.bases --> lexer.semantics.numbers : extends
lexer.semantics.strings --> lexer.semantics : uses

parser --> parser.internals : uses
parser --> parser.initializations : uses
parser --> parser.utils : uses
parser --> utils : uses

parser.internals --> utils : uses
parser.initializations --> utils : uses
parser.utils --> parser.internals : uses
parser.utils --> utils : uses

utils --> utils.builders : uses
utils --> utils.diagnostics : uses
utils --> utils.enums : uses

utils.builders --> utils.enums : uses
utils.diagnostics --> utils : uses
@enduml
```

---

## 5. Symbol Table Entry Structure (Object Diagram)

```plantuml
@startuml
object "SymbolTable" as ST {
    table: HashMap<String, LexemeInfo>
}

object "COLOR_TYPE" as CT {
    type: STRUCT
    subtype: NONE
    customType: null
    use: TYPE
    source: NONE
    parameters: [CLASSIFICATION, GAMMA]
    initialization: StructInitialization
}

object "COLOR_TYPE#CLASSIFICATION" as CC {
    type: ENUMERATE
    subtype: INT
    customType: null
    use: TYPE
    source: NONE
    parameters: [WHITE, GRAY, BLACK]
    initialization: EnumeratedInitialization
}

object "COLOR_TYPE#CLASSIFICATION#WHITE" as CW {
    type: SIMPLE
    subtype: NONE
    customType: null
    use: MACRO
    source: NONE
    parameters: null
    initialization: Constant("0")
}

object "COLOR_TYPE#CLASSIFICATION#GRAY" as CG {
    type: SIMPLE
    subtype: NONE
    customType: null
    use: MACRO
    source: NONE
    parameters: null
    initialization: Constant("1")
}

object "COLOR_TYPE#CLASSIFICATION#BLACK" as CB {
    type: SIMPLE
    subtype: NONE
    customType: null
    use: MACRO
    source: NONE
    parameters: null
    initialization: Constant("2")
}

object "PIXELS" as PX {
    type: ARRAY
    subtype: CUSTOM
    customType: "COLOR_TYPE"
    use: VARIABLE
    source: INTERNAL
    inferiorLimit: [0, 1, 3]
    superiorLimit: [1, 10, 4]
    parameters: null
    initialization: RepeatedInitialization
}

ST *-- CT : put()
ST *-- CC : put()
ST *-- CW : put()
ST *-- CG : put()
ST *-- CB : put()
ST *-- PX : put()

CT ..> CC : outer scope
CC ..> CW : enum value
CC ..> CG : enum value
CC ..> CB : enum value
PX ..> CT : customType ref
@enduml
```

---

## 6. Publisher Pattern (Class Diagram)

```plantuml
@startuml
interface LexemeInfoSchema {
    + type(Type): LexemeInfoSchema
    + subtype(Subtype): LexemeInfoSchema
    + customType(String): LexemeInfoSchema
    + source(Source): LexemeInfoSchema
    + use(Use): LexemeInfoSchema
    + inferiorLimits(List<String>): LexemeInfoSchema
    + superiorLimits(List<String>): LexemeInfoSchema
    + parameters(List<String>): LexemeInfoSchema
    + initialization(Object): LexemeInfoSchema
}

interface Publisher extends LexemeInfoSchema {
    + publish(): List<String>
}

class Declaration {
    - symbolTable: SymbolTable
    - identifiers: List<String>
    - builder: LexemeInfoBuilder
    - use: Use
    + Declaration(SymbolTable, List<String>, LexemeInfoBuilder)
    + use(Use): Declaration
    + publish(): List<String>
}

class Compound {
    - publishers: List<Publisher>
    + Compound(List<Publisher>)
    + publish(): List<String>
}

class ParsingContext {
    - declaredIdentifiers: List<String>
    - metadataBuilder: LexemeInfoBuilder
    - outerScopes: NameMangler
    - searchScope: NameMangler
    - nestedFields: NameMangler
    - index: int
    + ParsingContext(SymbolTable)
    + metadataBuilder(): LexemeInfoBuilder
    + declaredIdentifiers(): List<String>
    + outerScopes(): NameMangler
    + searchScope(): NameMangler
    + nestedFields(): NameMangler
}

LexemeInfoSchema <|.. Publisher
Publisher <|.. Declaration
Publisher <|.. Compound

ParsingContext *-- LexemeInfoBuilder
ParsingContext *-- NameMangler : outerScopes
ParsingContext *-- NameMangler : searchScope
ParsingContext *-- NameMangler : nestedFields

Declaration ..> SymbolTable : uses
Compound ..> Publisher : delegates to
@enduml
```

---

## 7. Transformer Chain (Decorator Pattern)

```plantuml
@startuml
abstract class Transformer {
    # next: Transformer
    + Transformer(Transformer)
    + {abstract} transform(String): String
    # giveToNext(String): String
}

class UnderscoreRemover
class UpperCaseConverter
class StripLeadingZeros
class StripTrailingZeros
class StripBaseNumberLeadingZeros
class OmitLeadingZeroMagnitudes
class OmitTrailingZeroMagnitudes
class OmitLeadingZerosInMagnitudes
class OmitTrailingZerosInMagnitudes
class StringEscapeResolver
class Nothing

abstract class HexResolver extends Transformer {
    # getHexDigits(): int
    + transform(String): String
}

class StringHexResolver
class WStringHexResolver

class ExponentFinder

Transformer <|-- UnderscoreRemover
Transformer <|-- UpperCaseConverter
Transformer <|-- StripLeadingZeros
Transformer <|-- StripTrailingZeros
Transformer <|-- StripBaseNumberLeadingZeros
Transformer <|-- OmitLeadingZeroMagnitudes
Transformer <|-- OmitTrailingZeroMagnitudes
Transformer <|-- OmitLeadingZerosInMagnitudes
Transformer <|-- OmitTrailingZerosInMagnitudes
Transformer <|-- StringEscapeResolver
Transformer <|-- Nothing

HexResolver <|-- StringHexResolver
HexResolver <|-- WStringHexResolver

Transformer <|-- ExponentFinder

' Chain composition (example: INTERVALS)
note right of UnderscoreRemover
INTERVALS chain:
UnderscoreRemover
  → UpperCaseConverter
    → OmitLeadingZeroMagnitudes
      → OmitTrailingZeroMagnitudes
        → OmitLeadingZerosInMagnitudes
          → OmitTrailingZerosInMagnitudes
            → Nothing
end note
@enduml
```

---

## 8. Name Mangling (Component Diagram)

```plantuml
@startuml
class NameMangler {
    - prefix: StringBuilder
    + addScope(String)
    + getCurrentScope(): String
    + popScope(): String
    + getNameMangled(String): String
}

class ParsingContext {
    - outerScopes: NameMangler
    - searchScope: NameMangler
    - nestedFields: NameMangler
    + outerScopes(): NameMangler
    + searchScope(): NameMangler
    + nestedFields(): NameMangler
}

ParsingContext *-- NameMangler : outerScopes
ParsingContext *-- NameMangler : searchScope
ParsingContext *-- NameMangler : nestedFields

note right of NameMangler
Examples:
- addScope("COLOR_TYPE") → prefix="COLOR_TYPE#"
- addScope("CLASSIFICATION") → prefix="COLOR_TYPE#CLASSIFICATION#"
- getNameMangled("WHITE") → "COLOR_TYPE#CLASSIFICATION#WHITE"
- popScope() → "CLASSIFICATION", prefix="COLOR_TYPE#"
end note
@enduml
```

---

## 9. Type System (Enum Diagram)

```plantuml
@startuml
enum Type {
    UNKNOWN
    SIMPLE
    ENUMERATE
    SUBRANGE
    ARRAY
    STRUCT
}

enum Subtype {
    UNKNOWN
    BOOL
    SINT, INT, DINT, LINT
    USINT, UINT, UDINT, ULINT
    REAL, LREAL
    TIME, DATE, TIME_OF_DAY, DATE_AND_TIME
    BYTE, WORD, DWORD, LWORD
    STRING, WSTRING
    CUSTOM
    NONE
}

enum Use {
    UNKNOWN
    VARIABLE
    FIELD
    TYPE
    MACRO
    LITERAL
    FUNCTION
    RULE
    OPTION
}

enum Source {
    UNKNOWN
    IN
    OUT
    INTERNAL
    FUZZIFY
    DEFUZZIFY
    NONE
}

note right of Type
Type + Subtype combinations:

SIMPLE + (INT, REAL, BOOL, TIME, ...)
ENUMERATE + INT (implicit)
SUBRANGE + (INT, UINT, SINT, ...)
ARRAY + CUSTOM (customType = element type)
STRUCT + NONE (parameters = field names)
end note
@enduml
```

---

## 10. Lexical Analysis Pipeline (Activity Diagram)

```plantuml
@startuml
start

:Read character stream;
:Match regex rule (JFlex);

if (Token has semantic value?) then (yes)
    :Create Transformer chain;
    :preprocessed = transformer.transform(yytext());
    
    if (SemanticAnalyzer exists?) then (yes)
        :Create LexicalContext(preprocessed, line, symbolTable, diagnostics);
        :result = analyzer.analyze(context);
        
        if (result.token == YYerror?) then (yes)
            :diagnostics.add(Error);
            :return YYerror;
        else (no)
            :yylval = result.lexeme;
            :return result.token;
        endif
    else (no)
        :return token;
    endif
else (no)
    :return token;
endif

:Continue scanning;

stop
@enduml
```

---

## Rendering Instructions

### VS Code
1. Install "PlantUML" extension
2. Open this file
3. Press `Alt+D` to preview

### IntelliJ IDEA
1. Install "PlantUML Integration" plugin
2. Open this file
3. Click the PlantUML icon in toolbar

### Command Line
```bash
# Install plantuml
npm install -g @plantuml/cli

# Render all diagrams to SVG
plantuml -tsvg doc/architecture.md

# Or use Java directly
java -jar plantuml.jar doc/architecture.md
```

### Online
Copy any `@startuml` ... `@enduml` block to:
- https://www.plantuml.com/plantuml/uml/
- https://plantuml-editor.kkeisuke.com/