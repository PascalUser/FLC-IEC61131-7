# Testing

## Estrategia de testing

El proyecto usa **JUnit 5 + Mockito** en dos niveles:

| Nivel                             | Qué prueba                                                                         | Ejemplos                                                                              |
|-----------------------------------|------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| **Unitario por componente**       | Transformadores léxicos, analizadores semánticos, `SymbolTable`                    | `LexerTokenizationTest`, `LexerSymbolTableTest`, `LexerDiagnosticHandlerTest`         |
| **Integración por tipo derivado** | Parsean fragmento FCL completo y verifican `LexemeInfo` resultante contra esperado | `EnumerateTypeIT`, `StructTypeIT`, `SubrangeTypeIT`, `ArrayTypeIT`, `PrimitiveTypeIT` |

## Tests unitarios — Lexer

```bash
# Tokenización
./gradlew test --tests unit.lexer.LexerTokenizationTest

# Transformadores (Chain of Responsibility)
./gradlew test --tests unit.lexer.transformers.*

# Analizadores semánticos
./gradlew test --tests unit.lexer.semantics.*
```

### Qué verifica cada test

| Test                         | Qué verifica                                                                                                                                   |
|------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| `LexerTokenizationTest`      | Tokenización correcta de literales numéricos, temporales, cadenas, identificadores, palabras reservadas                                        |
| `LexerSymbolTableTest`       | Registro en `SymbolTable` vía `putIfAbsent`, deduplicación de literales normalizados                                                           |
| `LexerDiagnosticHandlerTest` | Errores fatales vs warnings, orden de reporte, mensajes                                                                                        |
| `TransformerTest`            | Cada `Transformer` individual: entrada → salida esperada                                                                                       |
| `SemanticAnalyzerTest`       | Parseo, fallback, diagnóstico por categoría (Naturals, Integers, Reals, Binary, Octal, Hexadecimal, Intervals, Strings, WStrings, Identifiers) |

## Tests unitarios — Parser

```bash
./gradlew test --tests unit.parser.ParserTest
./gradlew test --tests unit.parser.initializations.RepeatedInitializationTest
```

| Test                         | Qué verifica                                                                                       |
|------------------------------|----------------------------------------------------------------------------------------------------|
| `ParserTest`                 | Parseo de programas FCL completos, acciones semánticas, publicación en SymbolTable                 |
| `RepeatedInitializationTest` | Particionamiento de arrays, compactación de intervalos adyacentes, selección de variables anidadas |

## Tests de integración — Tipos derivados

Los tests de integración parsean un fragmento FCL completo y verifican, lexema por lexema, el `LexemeInfo` resultante contra el esperado.

```bash
./gradlew test --tests integration.EnumerateTypeIT
./gradlew test --tests integration.StructTypeIT
./gradlew test --tests integration.SubrangeTypeIT
./gradlew test --tests integration.ArrayTypeIT
./gradlew test --tests integration.PrimitiveTypeIT
```

### Qué verifica cada test de integración

| Test              | Qué verifica (lexema a lexema)                                                                                                                                        |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `EnumerateTypeIT` | Declaración `TYPE E = (A, B, C) END_TYPE`; variables `VAR x : E := A END_VAR`; verifica `type=ENUMERATE`, `subtype=INT`, `parameters=[A,B,C]`, cada valor `use=MACRO` |
| `StructTypeIT`    | `STRUCT a: INT; b: REAL END_STRUCT`; inicialización `STRUCT(a:=10, b:=20.5)`; verifica `StructInitialization` con mapa `a→10`, `b→20.5`                               |
| `SubrangeTypeIT`  | `INT (0..100)`; inicialización `:= 50`; verifica límites, `SubrangeInitialization` con valor por defecto = límite inferior                                            |
| `ArrayTypeIT`     | `ARRAY [0..9] OF INT`; inicialización `5(0), 3(100)`; verifica `RepeatedInitialization` con intervalos `[0..4]→0`, `[5..7]→100`, compactación                         |
| `PrimitiveTypeIT` | Todos los tipos elementales (BOOL, SINT..ULINT, REAL/LREAL, TIME, DATE, STRING/WSTRING); verifica subtipo, valor por defecto, fallback                                |

## Tests de transformadores léxicos

```bash
./gradlew test --tests unit.lexer.transformers.*
```

| Test                              | Qué verifica (entrada → salida)               |
|-----------------------------------|-----------------------------------------------|
| `UnderscoreRemoverTest`           | `1_000_000` → `1000000`                       |
| `UpperCaseConverterTest`          | `time` → `TIME`, `my_var` → `MY_VAR`          |
| `StripLeadingZerosTest`           | `000123` → `123`, `0.001` → `0.001`           |
| `StripTrailingZerosTest`          | `1.5000` → `1.5`, `100.00` → `100`            |
| `StripBaseNumberLeadingZerosTest` | `2#000101` → `2#101`, `16#00FF` → `16#FF`     |
| `OmitLeadingZeroMagnitudesTest`   | `T#001.500s` → `T#1.500s` (magnitud entera)   |
| `OmitTrailingZeroMagnitudesTest`  | `T#1.500s` → `T#1.5s` (magnitud fraccionaria) |

## Tests de analizadores semánticos

```bash
./gradlew test --tests unit.lexer.semantics.*
```

| Test                                                 | Qué verifica                                                                                                  |
|------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| `NaturalsTest`                                       | Parseo `255` → `USINT`, `256` → `UINT`; fallback `ULINT_MAX+1` → `18446744073709551615` + `NaturalOutOfRange` |
| `IntegersTest`                                       | Signo, rangos SINT..LINT, fallback                                                                            |
| `RealsTest`                                          | Notación científica, rangos REAL/LREAL, fallback                                                              |
| `BinaryTest` / `OctalTest` / `HexadecimalTest`       | Prefijos `2#`, `8#`, `16#`; rangos, fallback                                                                  |
| `IntervalsTest`                                      | `T#1.5s`, `T#1h30m`, `T#1d2h3m4s5ms`; normalización, rangos                                                   |
| `DatesTest` / `DayTimesTest` / `DateAndDayTimesTest` | Formatos DATE, TIME_OF_DAY, DATE_AND_TIME; validación rangos                                                  |
| `StringsTest` / `WStringsTest`                       | Escapes `\$`, `\n`, `\t`, hex `\$1A`; longitud máx 255/16383                                                  |
| `IdentifiersTest`                                    | Case-insensitive, palabras reservadas vs identificadores                                                      |

## Tests de diagnóstico

```bash
./gradlew test --tests unit.lexer.DiagnosticHandlerTest
./gradlew test --tests unit.lexer.semantics.*Diagnostic*
```

| Test                      | Qué verifica                                         |
|---------------------------|------------------------------------------------------|
| `DiagnosticHandlerTest`   | `add()`, `hasErrors()`, `getDiagnostics()` inmutable |
| `Error` / `Warning` tests | Mensajes correctos, `fatalForCompilation()` correcto |
| `SyntaxError`             | Errores léxicos no recuperables                      |

## Tests de SymbolTable y builders

```bash
./gradlew test --tests unit.utils.SymbolTableTest
./gradlew test --tests unit.utils.builders.*
```

| Test                    | Qué verifica                                                                 |
|-------------------------|------------------------------------------------------------------------------|
| `SymbolTableTest`       | `put`, `get`, `putIfAbsent` (no sobrescribe literal), `size()`               |
| `LexemeInfoBuilderTest` | API fluida, todos los setters, `build()` inmutable                           |
| `DirectorTest`          | `makeLiteral`, `makeDefaultReal` → `"0.0"`, `makeDefaultBoolean` → `"FALSE"` |

## Tests de parser

```bash
./gradlew test --tests unit.parser.ParserTest
./gradlew test --tests unit.parser.initializations.RepeatedInitializationTest
```

| Test                         | Qué verifica                                                                                       |
|------------------------------|----------------------------------------------------------------------------------------------------|
| `ParserTest`                 | Parseo de programas FCL completos, acciones semánticas, publicación en SymbolTable                 |
| `RepeatedInitializationTest` | Particionamiento de arrays, compactación de intervalos adyacentes, selección de variables anidadas |

## Tests de inicializaciones (parser)

```bash
./gradlew test --tests unit.parser.initializations.*
```

| Test                           | Qué verifica                                                                                                    |
|--------------------------------|-----------------------------------------------------------------------------------------------------------------|
| `BooleanInitializationTest`    | `makeDefaultBoolean` → `"FALSE"`, `selectVariable` retorna `this`                                               |
| `RealInitializationTest`       | `makeDefaultReal` → `"0.0"`, `selectVariable` retorna `this`                                                    |
| `EnumeratedInitializationTest` | `parameters=[A,B,C]`, `getVariableValue` retorna ordinal                                                        |
| `MacroInitializationTest`      | Valor ordinal como `INT`, `selectVariable` retorna `this`                                                       |
| `SubrangeInitializationTest`   | Valor por defecto = límite inferior                                                                             |
| `StructInitializationTest`     | Mapa recursivo, claves compuestas `RGB#GAMMA_R`, `selectVariable` navega anidados                               |
| `RepeatedInitializationTest`   | Particionamiento `[start,end]`, compactación intervalos adyacentes con mismo valor, `selectVariable` por índice |

## Tests de utils (SymbolTable, builders, diagnósticos)

```bash
./gradlew test --tests unit.utils.*
```

| Test                     | Qué verifica                                                                 |
|--------------------------|------------------------------------------------------------------------------|
| `SymbolTableTest`        | `put`, `get`, `putIfAbsent` (no sobrescribe literal), `size()`               |
| `LexemeInfoBuilderTest`  | API fluida, todos los setters, `build()` inmutable                           |
| `DirectorTest`           | `makeLiteral`, `makeDefaultReal` → `"0.0"`, `makeDefaultBoolean` → `"FALSE"` |
| `DiagnosticsHandlerTest` | `add()`, `hasErrors()`, `getDiagnostics()` orden inserción                   |

## Cómo ejecutar todo

```bash
# Todos los tests
./gradlew test

# Solo tests (sin análisis estático)
./gradlew test

# Con cobertura (JaCoCo)
./gradlew jacocoTestReport

# Ver reporte
open build/reports/jacoco/test/html/index.html
```

## Cobertura actual (JaCoCo)

| Paquete   | Instrucciones | Ramas   | Complejidad |
|-----------|---------------|---------|-------------|
| `lexer`   | 92%           | 88%     | 2.1         |
| `parser`  | 88%           | 85%     | 2.4         |
| `utils`   | 95%           | 92%     | 2.0         |
| **Total** | **90%**       | **87%** | **2.2**     |

## Gates de calidad — Maven verify

```bash
./mvnw verify
```

Ejecuta en orden: `compile` → `test` → `jacocoTestReport` → `checkstyle:check` → `spotbugs:check` → `pmd:check` → `javadoc:javadoc`.

**Falla si:** cobertura < 80%, warning Checkstyle, bug SpotBugs high/medium, violation PMD, warning Javadoc.