# Módulo `lexer`

Analizador léxico generado con **JFlex 1.9.1** a partir de `src/main/java/lexer/Lexer.flex`. Reconoce tokens de FCL (IEC 61131-7) y delega la normalización y análisis semántico a cadenas de transformadores y analizadores especializados.

---

## Flujo principal

```mermaid
flowchart TD
    A[Lexer.flex (JFlex)] --> B[Lexer.java (generado)]
    B -->|yytext()| C[Transformer Chain\n(LexicalPreprocessors)]
    C -->|lexema normalizado| D[SemanticAnalyzer\n(LexicalAnalyzers)]
    D -->|LexemeInfo| E[SymbolTable]
    D -->|Diagnósticos| F[DiagnosticsHandler]
```

---

## Clases principales

| Clase | Responsabilidad | Patrón |
|-------|-----------------|--------|
| `lexer.Lexer` | Escáner JFlex; implementa `Parser.Lexer`; coordina preprocesadores y analizadores | — |
| `lexer.internals.LexicalPreprocessors` | Registry de cadenas `Transformer` por familia de lexema | Chain of Responsibility + Registry |
| `lexer.internals.LexicalAnalyzers` | Registry de `SemanticAnalyzer` por categoría léxica (14 total) | Registry |
| `lexer.transformers.Transformer` | Interfaz base para transformaciones atómicas de lexemas | Chain of Responsibility |
| `lexer.semantics.SemanticAnalyzer` | Analiza lexema normalizado, registra en SymbolTable, retorna token | Template Method + Strategy |
| `lexer.semantics.numbers.NumbersAnalyzer` | Base abstracta para numéricos decimales (`Naturals`, `Integers`, `Reals`) | Template Method |
| `lexer.semantics.numbers.bases.BaseNumbersAnalyzer` | Base para literales con base (`Binary`, `Octal`, `Hexadecimal`) | Template Method |
| `lexer.semantics.strings.StringsAnalyzer` | Base abstracta para cadenas (`Strings`, `WStrings`) | Template Method |
| `lexer.semantics.utils.ReservedWords` | Tabla de palabras reservadas IEC 61131-7 → tokens | Lookup Table |
| `lexer.transformers.utils.ExponentFinder` | Utilidad para detectar notación científica en reales | — |

---

## Cadena de transformadores por familia de lexema

Cada categoría de literal tiene su propia cadena de `Transformer` (en `LexicalPreprocessors`):

| Categoría | Transformadores (en orden) |
|-----------|----------------------------|
| `INTERVALS` | `UnderscoreRemover` → `UpperCaseConverter` → `OmitLeadingZeroMagnitudes` → `OmitTrailingZeroMagnitudes` → `OmitLeadingZerosInMagnitudes` → `OmitTrailingZerosInMagnitudes` |
| `REALS` | `UnderscoreRemover` → `StripTrailingZeros` → `StripLeadingZeros` |
| `NATURALS`, `INTEGERS` | `UnderscoreRemover` → `StripLeadingZeros` |
| `BINARY`, `OCTAL`, `HEXADECIMAL` | `UnderscoreRemover` → `StripBaseNumberLeadingZeros` |
| `STRINGS` | `StringHexResolver` → `StringEscapeResolver` |
| `WSTRINGS` | `WStringHexResolver` (4 dígitos) → `StringEscapeResolver` |
| `IDENTIFIERS` | `UpperCaseConverter` |
| `DATES`, `DAYTIMES`, `DATE_AND_TIMES` | *(ninguno — `Nothing`)* |

El patrón **Chain of Responsibility** permite componer transformaciones atómicas y reutilizables. Cada `Transformer` decide si modifica el lexema y si delega al siguiente eslabón vía `giveToNext()`.

---

## Analizadores semánticos (14 categorías)

Registrados en `LexicalAnalyzers`:

| Categoría | Clase analizador | Token de retorno |
|-----------|------------------|------------------|
| `NATURALS` | `Naturals` | `NUMERIC_LITERAL` |
| `INTEGERS` | `Integers` | `NUMERIC_LITERAL` |
| `REALS` | `Reals` | `NUMERIC_LITERAL` |
| `BINARY` | `Binary` | `NUMERIC_LITERAL` |
| `OCTAL` | `Octal` | `NUMERIC_LITERAL` |
| `HEXADECIMAL` | `Hexadecimal` | `NUMERIC_LITERAL` |
| `INTERVALS` | `Intervals` | `TIME_LITERAL` |
| `DATES` | `Dates` | `TIME_LITERAL` |
| `DAYTIMES` | `DayTimes` | `TIME_LITERAL` |
| `DATE_AND_TIMES` | `DateAndDayTimes` | `TIME_LITERAL` |
| `STRINGS` | `Strings` | `STRING_LITERAL` |
| `WSTRINGS` | `WStrings` | `STRING_LITERAL` |
| `IDENTIFIERS` | `Identifiers` | Palabra clave o `IDENTIFIER` |

Cada `SemanticAnalyzer` implementa:
- `analyze(LexicalContext)` → `Result(lexema, token)`
- `parse(lexema)`: interpreta y determina subtipo más chico (ej. `255` → `USINT`, `256` → `UINT`)
- `fallback(lexema)`: valor de reemplazo si excede rango (ej. `> ULINT_MAX` → `18446744073709551615` + `NaturalOutOfRange`)
- `createDiagnostic(line, lexema)`: diagnóstico concreto

---

## Diagnósticos: error vs. warning

| Severidad | Diagnósticos |
|-----------|--------------|
| **Error** (6) | `DateOutOfRange`, `IntervalConstructionError`, `IntervalOutOfRange`, `TimeOfDayOutOfRange`, `DateAndTimeOutOfRange`, `SyntaxError` |
| **Warning** (7) | `StringLengthWarning`, `HexadecimalOutOfRange`, `RealOutOfRange`, `NaturalOutOfRange`, `BinaryOutOfRange`, `OctalOutOfRange`, `IntegerOutOfRange` |

- **Error (fatal)**: detiene la compilación. No hay valor de reemplazo semánticamente razonable (literales temporales mal formados).
- **Warning (no fatal)**: se reporta y se sigue con valor de reemplazo bien definido (desbordamientos numéricos).

---

## Punto de integración único

`Lexer.processAndSaveYylval(Transformer, SemanticAnalyzer)` es el punto único por el que pasa todo lexema no trivial:

```java
public int processAndSaveYylval(@NonNull Transformer transformer, @NonNull SemanticAnalyzer analyzer) {
    String preprocessedLexeme = transformer.transform(yytext());
    SemanticAnalyzer.LexicalContext lexicalContext = new SemanticAnalyzer.LexicalContext(
            preprocessedLexeme, yyline + 1, this.symbolTable, this.diagnosticsHandler);
    SemanticAnalyzer.Result result = analyzer.analyze(lexicalContext);
    this.yylval = result.lexeme;
    return result.token;
}
```

1. **Transforma** el lexema crudo (`yytext()`) con la cadena de `Transformer` correspondiente
2. **Analiza semánticamente** con el `SemanticAnalyzer` registrado
3. **Registra** `LexemeInfo` en `SymbolTable` (vía `putIfAbsent`)
4. **Retorna** token al parser; guarda lexema normalizado en `yylval`

---

## Gráficos generados

### Longitud de la cadena de Transformer por tipo de lexema

![Longitud de la cadena de Transformer por tipo de lexema](../assets/transformer_chain_lengths.png)

*Los literales de intervalo (`INTERVALS`) requieren más normalización: remoción de guiones bajos, conversión a mayúsculas, y 4 pasos de eliminación de ceros no significativos.*

### Diagnósticos por severidad

![Diagnósticos por severidad](../assets/diagnostics_error_vs_warning.png)

*Desbordamientos numéricos son `Warning`; literales temporales inválidos son `Error`.*

---

## Archivos fuente

```
src/main/java/lexer/
├── Lexer.java                    # Generado por JFlex (≈970 líneas)
├── Lexer.flex                    # Especificación JFlex (fuente)
├── package-info.java
├── internals/
│   ├── LexicalPreprocessors.java # Registry de Transformer chains
│   ├── LexicalAnalyzers.java     # Registry de SemanticAnalyzers
│   └── package-info.java
├── transformers/
│   ├── Transformer.java          # Interfaz base (Chain of Responsibility)
│   ├── UnderscoreRemover.java
│   ├── UpperCaseConverter.java
│   ├── StripLeadingZeros.java
│   ├── StripTrailingZeros.java
│   ├── StripBaseNumberLeadingZeros.java
│   ├── OmitLeadingZeroMagnitudes.java
│   ├── OmitTrailingZeroMagnitudes.java
│   ├── OmitLeadingZerosInMagnitudes.java
│   ├── OmitTrailingZerosInMagnitudes.java
│   ├── StringEscapeResolver.java
│   ├── StringHexResolver.java
│   ├── WStringHexResolver.java
│   ├── Nothing.java              # No-op transformer
│   ├── hex_resolvers/
│   │   ├── HexResolver.java
│   │   ├── StringHexResolver.java
│   │   └── WStringHexResolver.java
│   └── utils/
│       └── ExponentFinder.java
└── semantics/
    ├── SemanticAnalyzer.java     # Base abstracta + LexicalContext + Result
    ├── Identifiers.java
    ├── Intervals.java
    ├── Dates.java
    ├── DayTimes.java
    ├── DateAndDayTimes.java
    ├── strings/
    │   ├── Strings.java
    │   ├── WStrings.java
    │   └── StringsAnalyzer.java  # Base Template Method para cadenas
    └── numbers/
        ├── NumbersAnalyzer.java  # Template Method para decimales
        ├── Naturals.java
        ├── Integers.java
        ├── Reals.java
        └── bases/
            ├── BaseNumbersAnalyzer.java  # Template Method para bases
            ├── Binary.java
            ├── Octal.java
            └── Hexadecimal.java
```

---

## Cómo probarlo

```bash
# Tests unitarios del lexer (ejemplos FCL en src/test/resources/examples)
mvn test -Dtest="*Lexer*"

# Tests de transformadores
mvn test -Dtest="unit.lexer.transformers.*"

# Tests de analizadores semánticos
mvn test -Dtest="unit.lexer.semantics.*"
```

Clase de soporte: `utils.ParserTestSupport` (configura lexer + parser + symbol table + diagnostics handler).

---

## Referencias

- **Especificación JFlex**: `src/main/java/lexer/Lexer.flex`
- **Chain of Responsibility (Transformers)**: `transformers/package-info.java`
- **Template Method + Strategy (NumbersAnalyzer)**: `semantics/numbers/NumbersAnalyzer.java`
- **Template Method (StringsAnalyzer)**: `semantics/strings/StringsAnalyzer.java`
- **Registry de analizadores**: `internals/LexicalAnalyzers.java`
- **Palabras reservadas**: `semantics/utils/ReservedWords.java`
- **Capítulo tesis — Análisis léxico**: `doc/thesis/04-analisis-lexico.md`