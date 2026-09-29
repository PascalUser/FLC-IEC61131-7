# Módulo `parser`

Analizador sintáctico (LALR(1)) generado con **GNU Bison 3.8.2** a partir de `src/main/java/parser/Parser.y`. Reconoce la gramática completa de declaración IEC 61131-7 (FCL) y construye la tabla de símbolos mediante acciones semánticas diferidas.

---

## Flujo principal

```mermaid
flowchart TD
    A[Lexer (JFlex)] -->|Tokens| B[Parser (Bison)]
    B -->|Acciones semánticas| C[ContextHandler\n(Pila de ParsingContext)]
    C --> D[Publisher]
    D --> E[SymbolTable]
    C --> F[UnderlyingScopeSearcher]
    C --> G[DimensionCalculator]
    C --> H[Factory]
    C --> I[Initialization hierarchy]
```

---

## Clases principales

| Clase | Responsabilidad | Patrón |
|-------|-----------------|--------|
| `parser.Parser` | Analizador LALR(1) generado; coordina lexer, pila de contextos y tabla de símbolos | — |
| `parser.internals.ContextHandler` | Pila LIFO de `ParsingContext` para scopes anidados | Stack |
| `parser.internals.ParsingContext` | Estado por scope: identificadores, `LexemeInfoBuilder`, 3 `NameMangler`, índice de array | Context Object |
| `parser.internals.NameMangler` | Genera claves calificadas (`TYPE#FIELD#VALUE`) con separador `#` | — |
| `parser.utils.Publisher` | Publica identificadores declarados en la `SymbolTable` con metadatos completos | Publisher |
| `parser.utils.UnderlyingScopeSearcher` | Resuelve cadena de alias de tipo custom hasta la definición raíz | — |
| `parser.utils.DimensionCalculator` | Calcula dimensión total de arrays multi-dimensionales | — |
| `parser.utils.Factory` | Crea inicializaciones por defecto para tipos primitivos (solo `REAL` hoy) | Factory Method |

---

## Jerarquía de inicializaciones

Todas implementan `parser.initializations.Initialization`:

| Clase | Uso | Valor por defecto |
|-------|-----|-------------------|
| `BooleanInitialization` | `BOOL` sin inicializar | `FALSE` |
| `RealInitialization` | `REAL`/`LREAL` sin inicializar | `0.0` |
| `SubrangeInitialization` | `SUBRANGE` sin inicializar | Límite inferior |
| `EnumeratedInitialization` | `ENUMERATE` sin inicializar | Primer valor del enum |
| `MacroInitialization` | Literal de enum explícito (`color := RED`) | Ordinal como `INT` |
| `VariableInitialization` | Asignación explícita (`x := 10`) | Valor literal |
| `StructInitialization` | Inicialización de `STRUCT` con campos nombrados | Mapa `campo → Initialization` |
| `RepeatedInitialization` | Arrays con factores de repetición (`10(0), 5(3.14)`) | Lista de intervalos `[inicio..fin] → Initialization` |

---

## Gráficos generados

### Estadísticas de la gramática

![parser_grammar_stats](assets/parser_grammar_stats.png)

*Fuente: `src/main/java/parser/Parser.y` (tokens), `src/main/java/parser/Parser.java` (enum `SymbolKind` = 116 no-terminales), `parser/initializations/*.java` (8 tipos).*

---

## Cobertura de la gramática (IEC 61131-7)

| Sección | Reglas clave | Estado |
|---------|--------------|--------|
| **Programa** | `program → opt_data_type_declaration function_block_declaration` | ✅ |
| **Function Block** | `FUNCTION_BLOCK … END_FUNCTION_BLOCK` con secciones `VAR_INPUT`, `VAR_OUTPUT`, `VAR`, `VAR CONSTANT` | ✅ |
| **Tipos de datos** | `TYPE … END_TYPE` con `STRUCT`, `ENUMERATE`, `SUBRANGE`, `ARRAY`, `STRING`/`WSTRING` | ✅ |
| **Inicializaciones** | Simples, struct, array con repetición, enum, subrange, custom | ✅ |
| **Bloques difusos** | `FUZZIFY`, `DEFUZZIFY`, `RULEBLOCK`, `OPTION` (reconocimiento sintáctico) | ✅ |
| **Pragmas** | `PRAGMA identifier [numeric_constant]` | ✅ |

---

## Cómo probarlo

```bash
# Tests unitarios del parser (ejemplos FCL en src/test/resources/examples)
./gradlew test --tests unit.parser.ParserTest

# Tests de RepeatedInitialization
./gradlew test --tests unit.parser.initializations.RepeatedInitializationTest
```

Clase de soporte: `utils.ParserTestSupport` (configura lexer + parser + symbol table).

---

## Archivos fuente

```
src/main/java/parser/
├── Parser.java                 # Generado por Bison (≈3000 líneas)
├── Parser.y                    # Gramática Bison (1438 líneas)
├── package-info.java
├── internals/
│   ├── ContextHandler.java     # Pila de contextos
│   ├── ParsingContext.java     # Estado por scope
│   └── NameMangler.java        # Mangling de nombres
├── utils/
│   ├── Publisher.java          # Publicación en SymbolTable
│   ├── UnderlyingScopeSearcher.java
│   ├── DimensionCalculator.java
│   └── Factory.java
└── initializations/
    ├── Initialization.java          # Interfaz
    ├── BooleanInitialization.java
    ├── RealInitialization.java
    ├── SubrangeInitialization.java
    ├── EnumeratedInitialization.java
    ├── MacroInitialization.java
    ├── VariableInitialization.java
    ├── StructInitialization.java
    └── RepeatedInitialization.java
```