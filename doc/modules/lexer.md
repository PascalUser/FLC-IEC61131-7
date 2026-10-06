# lexer

Módulo de análisis léxico: generado con **JFlex 1.8.2** a partir de `src/main/java/lexer/Lexer.flex`. Implementa el escáner léxico completo para el estándar **IEC 61131-7** (Function Blocks de Lógica Difusa) y el **Anexo B de IEC 61131-3** (literales numéricos, temporales, cadenas, identificadores y palabras reservadas).

El lexer sigue una arquitectura de dos fases:

1. **Preprocesamiento** — Cadenas de transformadores (Chain of Responsibility) que normalizan el léxico crudo
2. **Análisis semántico** — Validadores por categoría que verifican rangos, construyen metadatos y poblan la tabla de símbolos

## Diagrama de paquetes

Dependencias entre paquetes del proyecto, generado por `scripts/generate_diagrams.py` a partir de los imports reales:

```mermaid
flowchart LR
    lexer[lexer]
    parser[parser]
    utils[utils]
    lexer -->|tokens| parser
    parser -->|SymbolTable| utils
    parser -->|LexemeInfo| utils
    parser -->|DiagnosticsHandler| utils
    lexer -->|SymbolTable| utils
    lexer -->|DiagnosticsHandler| utils
```

## Diagrama de clases

Generado por `scripts/generate_diagrams.py lexer` en `doc/diagrams/lexer_class_diagram.mmd`:

```mermaid
classDiagram
    namespace lexer {
        class Lexer {
            +processAndSaveYylval()
        }
        class LexicalPreprocessors {
            +INTERVALS: Transformer
            +REALS: Transformer
            +IDENTIFIERS: Transformer
        }
        class LexicalAnalyzers {
            +analyzers: Map
        }
    }
    namespace lexer_transformers {
        class Transformer {
            +transform()
            +giveToNext()
        }
        class UnderscoreRemover
        class UpperCaseConverter
        class StripLeadingZeros
        class StripTrailingZeros
        class StringEscapeResolver
        class Nothing
    }
    namespace lexer_semantics {
        class SemanticAnalyzer {
            +analyze()
        }
        class NumbersAnalyzer {
            +parse()
            +fallback()
            +createDiagnostic()
        }
        class Naturals
        class Integers
        class Reals
        class BaseNumbersAnalyzer
        class Binary
        class Octal
        class Hexadecimal
        class Intervals
        class Identifiers
        class Strings
        class Dates
    }
    Lexer --> LexicalPreprocessors : uses
    Lexer --> LexicalAnalyzers : uses
    LexicalPreprocessors --> Transformer : manages
    LexicalAnalyzers --> SemanticAnalyzer : manages
    Transformer <|-- UnderscoreRemover
    Transformer <|-- UpperCaseConverter
    Transformer <|-- StripLeadingZeros
    Transformer <|-- StripTrailingZeros
    Transformer <|-- StringEscapeResolver
    Transformer <|-- Nothing
    SemanticAnalyzer <|-- NumbersAnalyzer
    NumbersAnalyzer <|-- Naturals
    NumbersAnalyzer <|-- Integers
    NumbersAnalyzer <|-- Reals
    NumbersAnalyzer <|-- BaseNumbersAnalyzer
    BaseNumbersAnalyzer <|-- Binary
    BaseNumbersAnalyzer <|-- Octal
    BaseNumbersAnalyzer <|-- Hexadecimal
    SemanticAnalyzer <|-- Intervals
    SemanticAnalyzer <|-- Identifiers
    SemanticAnalyzer <|-- Strings
    SemanticAnalyzer <|-- Dates
```

## Tabla de clases

| Clase                                                 | Responsabilidad                                                                                | Patrón                             |
|-------------------------------------------------------|------------------------------------------------------------------------------------------------|------------------------------------|
| `lexer.Lexer`                                         | Escáner generado por JFlex; implementa `Parser.Lexer`; coordina preprocesadores y analizadores | Generated Lexer / Facade           |
| `lexer.internals.LexicalPreprocessors`                | Registro de cadenas de transformadores por categoría léxica                                    | Registry / Chain of Responsibility |
| `lexer.internals.LexicalAnalyzers`                    | Registro de analizadores semánticos por categoría léxica                                       | Registry                           |
| `lexer.transformers.Transformer`                      | Base abstracta para transformadores encadenados                                                | Chain of Responsibility            |
| `lexer.transformers.UnderscoreRemover`                | Elimina guiones bajos de literales numéricos y temporales                                      | Transform                          |
| `lexer.transformers.UpperCaseConverter`               | Convierte identificadores a mayúsculas (IEC case-insensitive)                                  | Transform                          |
| `lexer.transformers.StripLeadingZeros`                | Elimina ceros iniciales en números decimales                                                   | Transform                          |
| `lexer.transformers.StripTrailingZeros`               | Elimina ceros finales en parte fraccionaria de reales                                          | Transform                          |
| `lexer.transformers.OmitLeadingZeroMagnitudes`        | Elimina ceros iniciales en cada magnitud de intervalo                                          | Transform                          |
| `lexer.transformers.OmitTrailingZeroMagnitudes`       | Elimina ceros finales en cada magnitud de intervalo                                            | Transform                          |
| `lexer.transformers.OmitLeadingZerosInMagnitudes`     | Elimina ceros iniciales solo en magnitudes internas                                            | Transform                          |
| `lexer.transformers.OmitTrailingZerosInMagnitudes`    | Elimina ceros finales solo en magnitudes internas                                              | Transform                          |
| `lexer.transformers.StripBaseNumberLeadingZeros`      | Elimina ceros iniciales tras prefijo base (2#, 8#, 16#)                                        | Transform                          |
| `lexer.transformers.StringEscapeResolver`             | Resuelve escapes estándar (`$L`, `$N`, `$P`, `$R`, `$T`, `$$`, `$'`, `$"`)                     | Transform                          |
| `lexer.transformers.hex_resolvers.HexResolver`        | Base abstracta para resolución de escapes hex en cadenas                                       | Chain of Responsibility            |
| `lexer.transformers.hex_resolvers.StringHexResolver`  | Resuelve escapes hexadecimales de 2 dígitos (`$XX`) en STRING                                  | Transform                          |
| `lexer.transformers.hex_resolvers.WStringHexResolver` | Resuelve escapes hexadecimales de 4 dígitos (`$XXXX`) en WSTRING                               | Transform                          |
| `lexer.transformers.utils.ExponentFinder`             | Utilidad para localizar exponentes en literales reales                                         | Utility                            |
| `lexer.transformers.Nothing`                          | Transformador identidad (sin preprocesamiento)                                                 | Null Object                        |
| `lexer.semantics.SemanticAnalyzer`                    | Interfaz para análisis semántico con contexto léxico                                           | Strategy                           |
| `lexer.semantics.numbers.NumbersAnalyzer`             | Base para literales numéricos: parseo, validación de rango, fallback, diagnóstico              | Template Method                    |
| `lexer.semantics.numbers.Naturals`                    | Enteros sin signo; determina USINT/UINT/UDINT/ULINT por rango con `BigInteger`                 | Template Method                    |
| `lexer.semantics.numbers.Integers`                    | Enteros con signo; determina SINT/INT/DINT/LINT por rango con `Long`                            | Template Method                    |
| `lexer.semantics.numbers.Reals`                       | Punto flotante IEEE 754; REAL si la magnitud cabe en float, LREAL en caso contrario            | Template Method                    |
| `lexer.semantics.numbers.bases.Binary`                | Literales binarios (2#...) con hasta 64 dígitos; determina BYTE/WORD/DWORD/LWORD                | Template Method                    |
| `lexer.semantics.numbers.bases.Octal`                 | Literales octales (8#...) con hasta 22 dígitos; determina BYTE/WORD/DWORD/LWORD                 | Template Method                    |
| `lexer.semantics.numbers.bases.Hexadecimal`           | Literales hexadecimales (16#...) con hasta 16 dígitos; determina BYTE/WORD/DWORD/LWORD          | Template Method                    |
| `lexer.semantics.Intervals`                           | Literales TIME: validación magnitudes (D,H,M,S,MS) y total ≤ Long.MAX_VALUE                    | Strategy                           |
| `lexer.semantics.Dates`                               | Literales DATE: validación calendario gregoriano                                               | Strategy                           |
| `lexer.semantics.DayTimes`                            | Literales TIME_OF_DAY (TOD): validación 00:00:00.000..23:59:59.999                             | Strategy                           |
| `lexer.semantics.DateAndDayTimes`                     | Literales DATE_AND_TIME (DT): combinación DATE + TOD                                           | Strategy                           |
| `lexer.semantics.strings.StringsAnalyzer`             | Base para cadenas: validación longitud (255/16383), escapes                                    | Template Method                    |
| `lexer.semantics.strings.Strings`                     | STRING (single-byte, máx 255 chars)                                                            | Template Method                    |
| `lexer.semantics.strings.WStrings`                    | WSTRING (double-byte, máx 16383 chars)                                                         | Template Method                    |
| `lexer.semantics.Identifiers`                         | Identificadores y palabras reservadas; case-insensitive via UpperCaseConverter                 | Strategy                           |
| `lexer.semantics.utils.ReservedWords`                 | Conjunto de palabras reservadas IEC; consulta `isReserved`                                     | Registry                           |

## Cadenas de preprocesamiento — Transformer Chains

Cada categoría léxica tiene una cadena dedicada definida en `LexicalPreprocessors`:

| Categoría                             | Cadena de transformadores                                                                                                                                        | Descripción                                                        |
|---------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------|
| `DATE_AND_TIMES`, `DAYTIMES`, `DATES` | `Nothing`                                                                                                                                                        | Sin preprocesamiento (formato fijo)                                |
| `INTERVALS`                           | `UnderscoreRemover → UpperCaseConverter → OmitLeadingZeroMagnitudes → OmitTrailingZeroMagnitudes → OmitLeadingZerosInMagnitudes → OmitTrailingZerosInMagnitudes` | Normaliza magnitudes de intervalo TIME                             |
| `NATURALS`, `INTEGERS`                | `UnderscoreRemover → StripLeadingZeros`                                                                                                                          | Limpia guiones bajos y ceros iniciales                             |
| `BINARY`, `OCTAL`, `HEXADECIMAL`      | `UnderscoreRemover → StripBaseNumberLeadingZeros`                                                                                                                | Limpia guiones bajos y ceros tras prefijo base                     |
| `REALS`                               | `UnderscoreRemover → StripTrailingZeros → StripLeadingZeros`                                                                                                     | Limpia guiones bajos, ceros finales fraccionarios, ceros iniciales |
| `STRINGS`                             | `StringHexResolver → StringEscapeResolver`                                                                                                                       | Resuelve escapes hex (2 dígitos) y estándar                        |
| `WSTRINGS`                            | `WStringHexResolver → StringEscapeResolver`                                                                                                                      | Resuelve escapes hex (4 dígitos) y estándar                        |
| `IDENTIFIERS`                         | `UpperCaseConverter`                                                                                                                                             | Convierte a mayúsculas (case-insensitive)                          |

Chart: `assets/transformer_chain_lengths.png` — longitud de cada cadena de transformadores por categoría.

### Diagrama de objetos — cadena INTERVALS

Instancia concreta registrada en `LexicalPreprocessors.INTERVALS` (`src/main/java/lexer/internals/LexicalPreprocessors.java`):

```mermaid
flowchart LR
    INTERVALS["LexicalPreprocessors.INTERVALS\n(Transformer)"] --> T1[UnderscoreRemover]
    T1 --> T2[UpperCaseConverter]
    T2 --> T3[OmitLeadingZeroMagnitudes]
    T3 --> T4[OmitTrailingZeroMagnitudes]
    T4 --> T5[OmitLeadingZerosInMagnitudes]
    T5 --> T6[OmitTrailingZerosInMagnitudes]
    T6 --> T7["null"]
```

## Análisis semántico por categoría

### Literales numéricos — NumbersAnalyzer

Todos heredan de `lexer.semantics.numbers.NumbersAnalyzer` que implementa el **Template Method**:

1. `parse(lexeme)` → `ParsedValue(lexeme, Subtype, value)` — subclase define parseo y rango
2. Si `subtype == UNKNOWN` → `createDiagnostic()` + `fallback()` para valor corregido
3. Construye `LexemeInfo` via `Director.makeLiteral()` y publica en `SymbolTable`
4. Retorna `Result(lexeme, NUMERIC_LITERAL)`

**Subtipos determinados por rango de valor:**

| Analizador    | Rango válido                                       | Subtipos posibles       | Fallback           |
|---------------|----------------------------------------------------|-------------------------|--------------------|
| `Naturals`    | 0 .. 18,446,744,073,709,551,615 (2^64 - 1)         | `USINT`..`ULINT`        | MAX_ULINT (`ULINT`)|
| `Integers`    | -9,223,372,036,854,775,808 .. 9,223,372,036,854,775,807 | `SINT`..`LINT`    | ±Long.MAX (`LINT`) |
| `Reals`       | IEEE 754 binary32/64                               | `REAL`, `LREAL`         | ±Double.MAX (`LREAL`) |
| `Binary`      | hasta 64 dígitos; determina por valor               | `BYTE`..`LWORD`         | MAX_ULINT (`LWORD`)|
| `Octal`       | hasta 22 dígitos; determina por valor               | `BYTE`..`LWORD`         | MAX_ULINT (`LWORD`)|
| `Hexadecimal` | hasta 16 dígitos; determina por valor               | `BYTE`..`LWORD`         | MAX_ULINT (`LWORD`)|

Los tres analizadores de base comparten `BaseNumbersAnalyzer` (`src/main/java/lexer/semantics/numbers/bases/`): cada subclase define `getBase()` y `getMaxDigits()` y el rango se resuelve por valor con `BigInteger`.

**Diagnósticos de warning (no fatal, con fallback):**

* `NaturalOutOfRange`, `IntegerOutOfRange`, `RealOutOfRange`
* `BinaryOutOfRange`, `OctalOutOfRange`, `HexadecimalOutOfRange`

### Literales temporales

| Analizador        | Formato IEC                    | Validaciones                                                                                                         | Valor almacenado          |
|-------------------|--------------------------------|----------------------------------------------------------------------------------------------------------------------|---------------------------|
| `Intervals`       | `[-]NdNhNmNsNms`               | Magnitudes: solo la mayor no-cero puede exceder límite natural (H≤23, M≤59, S≤59, MS≤999); total ≤ Long.MAX_VALUE ns | `Duration` (nanosegundos) |
| `Dates`           | `YYYY-MM-DD`                   | Calendario gregoriano válido                                                                                         | `LocalDate`               |
| `DayTimes`        | `HH:MM:SS[.mmm]`               | 00:00:00.000 .. 23:59:59.999                                                                                         | `LocalTime`               |
| `DateAndDayTimes` | `YYYY-MM-DD-HH:MM:SS[.mmm]`    | Combinación DATE + TOD válida                                                                                        | `LocalDateTime`           |

El prefijo de tipo (`T#`, `TIME#`, `D#`, `DATE#`, `TOD#`, `DT#`) es un token independiente consumido por el parser; el lexer recibe únicamente el cuerpo del literal.

**Diagnósticos de error (fatal):**

* `IntervalConstructionError` — magnitud no-mayor excede límite natural
* `IntervalOutOfRange` — duración total excede Long.MAX_VALUE
* `DateOutOfRange` — fecha inválida
* `TimeOfDayOutOfRange` — hora inválida
* `DateAndTimeOutOfRange` — fecha/hora inválida

Chart: `assets/diagnostics_error_vs_warning.png` — proporción de diagnósticos de error frente a warning en `src/main/java/utils/diagnostics/`.

### Literales de cadena — StringsAnalyzer

| Analizador | Tipo IEC  | Longitud máx | Escape hex          | Valor almacenado |
|------------|-----------|--------------|---------------------|------------------|
| `Strings`  | `STRING`  | 255 chars    | `$XX` (2 dígitos)   | `String`         |
| `WStrings` | `WSTRING` | 255 chars    | `$XXXX` (4 dígitos) | `String`         |

Ambos analizadores comparten `MAX_STRING_LENGTH = 255` en `StringsAnalyzer`; el excedente se trunca y se emite `StringLengthWarning`.

**Diagnósticos de warning (no fatal):**

* `StringLengthWarning` — longitud excede máximo permitido

Escapes estándar soportados: `$L` (LF), `$N` (LF), `$P` (FF), `$R` (CR), `$T` (TAB), `$$` ($), `$'` ('), `$"` (").

### Identificadores y palabras reservadas

`Identifiers` delega en `ReservedWords.isReserved(lexeme)`:

* Si es reservada (excepto `TRUE`/`FALSE`) → retorna token directo (ej. `VAR`, `IF`, `THEN`)
* Si es `TRUE`/`FALSE` → publica `LexemeInfo(subtype=BOOL, initialValue=Boolean)` + `BOOLEAN_LITERAL`
* Sino → publica `LexemeInfo` vacía + `IDENTIFIER`

Los subtipos asignados por los analizadores numéricos pertenecen al enum `Subtype` (`src/main/java/utils/enums/`).

Chart: `assets/enum_sizes.png` — tamaño de los enums auxiliares (`Type`, `Source`, `Use`, `Subtype`) usados por el lexer.

Chart: `assets/lexemeinfo_field_population.png` — campos de `LexemeInfo` poblados por los analizadores léxicos.

## Flujo Lexer → Parser → SymbolTable

Secuencia generada por `scripts/generate_diagrams.py lexer` en `doc/diagrams/lexer_parser_sequence.mmd`:

```mermaid
sequenceDiagram
    participant JFlex as Lexer.flex
    participant Lexer as Lexer.java
    participant Transformer as Transformer Chain
    participant Analyzer as SemanticAnalyzer
    participant ST as SymbolTable
    participant Diag as DiagnosticsHandler
    participant Parser as Parser (Bison)

    JFlex->>Lexer: yytext()
    Lexer->>Transformer: transform(yytext())
    loop Chain of Responsibility
        Transformer->>Transformer: giveToNext()
    end
    Transformer-->>Lexer: normalized lexeme
    Lexer->>Analyzer: analyze(LexicalContext)
    Analyzer->>ST: putIfAbsent(lexeme, LexemeInfo)
    Analyzer->>Diag: add(Diagnostic) if needed
    Analyzer-->>Lexer: Result(token, lexeme)
    Lexer-->>Parser: token + yylval
    Parser->>ST: Publisher.publish(ctx)
```

## Expresiones regulares clave — Lexer.flex

```flex
NUM                     = {DIGIT}+
FIXED                   = {NUM}(\.{NUM})?

MS                      = {FIXED}[mM][sS]
SEC                     = ({FIXED}[sS]|{NUM}[sS]_?{MS})
MIN                     = ({FIXED}[mM]|{NUM}[mM]_?{SEC})
HOUR                    = ({FIXED}[hH]|{NUM}[hH]_?{MIN})
DAY                     = ({FIXED}[dD]|{NUM}[dD]_?{HOUR})
INTERVAL                = -?({DAY}|{HOUR}|{MIN}|{SEC}|{MS})

DATE                    = {NUM}-{NUM}-{NUM}
DAYTIME                 = {NUM}:{NUM}:{FIXED}
DATE_AND_TIME           = {DATE}-{DAYTIME}

NATURAL_NUMBER          = {DIGIT}(_?{DIGIT})*
INTEGER_NUMBER          = [\+\-]{NATURAL_NUMBER}
REAL_NUMBER             = [\+\-]?{NATURAL_NUMBER}\.({NATURAL_NUMBER}|({NATURAL_NUMBER}?[eE][\+\-]?{NATURAL_NUMBER}))
BINARY                  = 2#{BIT}(_?{BIT})*
OCTAL                   = 8#{OCT_DIGIT}(_?{OCT_DIGIT})*
HEXADECIMAL             = 16#{HEX_DIGIT}(_?{HEX_DIGIT})*

IDENTIFIER              = ({LETTER}|_({LETTER}|{DIGIT}))(_?[a-zA-Z0-9])*

SINGLE_BYTE_STRING      = \'({COMMON_CHARACTER}|\"|\$\'|\${HEX_DIGIT}{2})*\'
DOUBLE_BYTE_STRING      = \"({COMMON_CHARACTER}|\'|\$\"|\${HEX_DIGIT}{4})*\"
```

## Tabla de tokens principales — desde Parser.y / Lexer.flex

| Token               | Tipo                                        | Categoría semántica                                   |
|---------------------|---------------------------------------------|-------------------------------------------------------|
| `NUMERIC_LITERAL`   | Literal numérico                            | NATURALS, INTEGERS, REALS, BINARY, OCTAL, HEXADECIMAL |
| `TIME_LITERAL`      | Literal temporal                            | INTERVALS, DATES, DAYTIMES, DATE_AND_TIMES            |
| `STRING_LITERAL`    | Cadena                                      | STRINGS, WSTRINGS                                     |
| `BOOLEAN_LITERAL`   | `TRUE` / `FALSE`                            | IDENTIFIERS (reservadas)                              |
| `IDENTIFIER`        | Identificador usuario                       | IDENTIFIERS                                           |
| Palabras reservadas | `VAR`, `FUNCTION_BLOCK`, `IF`, `THEN`, etc. | IDENTIFIERS (reservadas)                              |

## Tests

Tests unitarios en `src/test/java/unit/lexer/`:

* `LexerTokenizationTest.java` — tokenización correcta de todos los tipos de literal
* `LexerSymbolTableTest.java` — población de SymbolTable con LexemeInfo correcto
* `LexerDiagnosticHandlerTest.java` — generación de warnings/errors para valores fuera de rango
* `transformers/OmitLeadingZeroMagnitudesTest.java`, `OmitTrailingZeroMagnitudesTest.java` — normalización de magnitudes internas
* `transformers/OmitLeadingZerosInMagnitudesTest.java`, `OmitTrailingZerosInMagnitudesTest.java` — normalización de magnitudes internas
* `transformers/StringEscapeResolverTest.java`, `StringHexResolverTest.java`, `WStringHexResolverTest.java` — resolución de escapes en cadenas

Ejecución:

```bash
mvn test -Dtest='Lexer*Test,unit.lexer.transformers.**'
```

Chart: `assets/test_coverage.png` — cobertura JaCoCo por módulo; lexer limitado por código generado JFlex.

Cobertura actual: **90%** (JaCoCo — fuente: `doc/stats.json`, clave `test_coverage.packages.lexer`).

## Archivos fuente

```
src/main/java/lexer/
├── package-info.java
├── Lexer.flex              (especificación JFlex)
├── Lexer.java              (generado, no editar a mano)
├── internals/
│   ├── package-info.java
│   ├── LexicalPreprocessors.java
│   └── LexicalAnalyzers.java
├── transformers/
│   ├── package-info.java
│   ├── Transformer.java
│   ├── UnderscoreRemover.java
│   ├── UpperCaseConverter.java
│   ├── StripLeadingZeros.java
│   ├── StripTrailingZeros.java
│   ├── OmitLeadingZeroMagnitudes.java
│   ├── OmitTrailingZeroMagnitudes.java
│   ├── OmitLeadingZerosInMagnitudes.java
│   ├── OmitTrailingZerosInMagnitudes.java
│   ├── StripBaseNumberLeadingZeros.java
│   ├── StringEscapeResolver.java
│   ├── Nothing.java
│   ├── hex_resolvers/
│   │   ├── package-info.java
│   │   ├── HexResolver.java
│   │   ├── StringHexResolver.java
│   │   └── WStringHexResolver.java
│   └── utils/
│       ├── package-info.java
│       └── ExponentFinder.java
├── semantics/
│   ├── package-info.java
│   ├── SemanticAnalyzer.java
│   ├── Intervals.java
│   ├── Identifiers.java
│   ├── Dates.java
│   ├── DayTimes.java
│   ├── DateAndDayTimes.java
│   ├── strings/
│   │   ├── package-info.java
│   │   ├── StringsAnalyzer.java
│   │   ├── Strings.java
│   │   └── WStrings.java
│   ├── numbers/
│   │   ├── package-info.java
│   │   ├── NumbersAnalyzer.java
│   │   ├── Naturals.java
│   │   ├── Integers.java
│   │   ├── Reals.java
│   │   └── bases/
│   │       ├── package-info.java
│   │       ├── BaseNumbersAnalyzer.java
│   │       ├── Binary.java
│   │       ├── Octal.java
│   │       └── Hexadecimal.java
│   └── utils/
│       ├── package-info.java
│       └── ReservedWords.java
```

## Diagramas de apoyo — assets

| Diagrama               | Archivo                                  | Descripción                                        |
|------------------------|------------------------------------------|----------------------------------------------------|
| Clases lexer           | `doc/diagrams/lexer_class_diagram.mmd`   | Estructura completa preprocesadores + analizadores |
| Secuencia Lexer↔Parser | `doc/diagrams/lexer_parser_sequence.mmd` | Flujo de tokens y publicación                      |
| Dependencias paquetes  | `doc/diagrams/package_dependencies.mmd`  | Acoplamiento lexer → parser → utils                |

## Capítulos de tesis que consumen este módulo

| Capítulo | Enfoque                                                                                     |
|----------|---------------------------------------------------------------------------------------------|
| 04       | Arquitectura implementada — Lexer JFlex, Transformer Chain, Semantic Analyzers, SymbolTable |
| 05       | Validación — Cobertura léxica IEC 61131-7 + Anexo B, tests de literales                     |
| 07       | Mantenibilidad — métricas (CYCLO, LOC, coverage), acoplamiento `lexer → utils` (efferent=1) |

## Regeneración del lexer

```bash
# Requiere JFlex 1.8.2
cd src/main/java/lexer
jflex Lexer.flex
# Genera Lexer.java — no editar a mano, modificar Lexer.flex y regenerar
```

*Nota: `Lexer.java` está versionado; no editar a mano — modificar `Lexer.flex` y regenerar con `jflex`.*
