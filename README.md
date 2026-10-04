# FLC-IEC61131-7 Compiler

Compilador del lenguaje estándar para controladores difusos **IEC 61131-7 (Fuzzy Control Language, FCL)**. Implementa análisis léxico (JFlex), análisis sintáctico LALR(1) (GNU Bison, esqueleto `lalr1.java`) y resolución semántica de tipos hacia una tabla de símbolos, para el subconjunto declarativo e inicializador del estándar: bloques de función, tipos definidos por el usuario (estructuras, enumerados, subrangos, arreglos) y bloques de fuzzificación/defuzzificación/reglas.

## Requisitos

- Java 8 (el build está fijado a `source`/`target` 1.8 en `pom.xml`)
- Maven
- JFlex 1.9.1 vía `jflex-maven-plugin` (configurado en `pom.xml`, no requiere instalación aparte)
- GNU Bison: se usa offline para regenerar `Parser.java` desde `Parser.y` (esqueleto `lalr1.java`); el código generado está versionado, por lo que Bison no es necesario para compilar con Maven.

## Compilar y correr los tests

```bash
mvn clean verify
```

Esto ejecuta, en orden: generación del lexer (JFlex) a partir de `src/main/java/lexer/Lexer.flex`, compilación, tests con JUnit 5 + Mockito, cobertura con JaCoCo, y los gates de calidad Checkstyle y SpotBugs (`mvn verify` falla si se supera el umbral configurado en cada plugin).

Solo tests:

```bash
mvn test
```

## Arquitectura

```mermaid
flowchart LR
    SRC[Source .fcl] --> LEX[Lexer\nJFlex]
    LEX -- tokens --> PAR[Parser\nBison LALR1]
    PAR -- publishes --> ST[(SymbolTable)]
    PAR -- reports --> DIAG[DiagnosticsHandler]
```

El analizador sintáctico orquesta la compilación: invoca al lexer, ejecuta las acciones semánticas de la gramática y publica el resultado en una `SymbolTable` compartida (patrón Repository). El lexer normaliza el texto de entrada con una cadena de transformadores (`lexer/transformers/`, Chain of Responsibility) y valida los literales con analizadores semánticos por familia (`lexer/semantics/`). Cada lexema resuelto queda representado como un `LexemeInfo` (tipo, subtipo, uso, fuente, límites, parámetros e inicialización), construido incrementalmente con `LexemeInfoBuilder` y las recetas predefinidas de `Director` (`utils/builders/`), y publicado atómicamente por `parser.utils.Publisher`. La clasificación semántica se apoya en los enums `Type`, `Subtype`, `Use` y `Source` (`utils/enums/`). Los errores y warnings se recolectan en un `DiagnosticsHandler` central, en orden de inserción.

Documentación técnica completa: [`doc/modules/lexer.md`](doc/modules/lexer.md), [`doc/modules/parser.md`](doc/modules/parser.md), [`doc/modules/utils.md`](doc/modules/utils.md).

## Estructura del repositorio

| Carpeta                            | Contenido                                                                                                                                                                                                                                                                                                                                                                             |
|------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `src/main/java/lexer/`             | `Lexer.flex`/`Lexer.java` (generado), `internals/` (`LexicalAnalyzers`, `LexicalPreprocessors`), `transformers/` (Chain of Responsibility de normalización léxica), `semantics/` (analizadores semánticos por familia de literal: números, fechas, strings, identificadores, intervalos)                                                                                              |
| `src/main/java/parser/`            | `Parser.y`/`Parser.java` (generado), `internals/` (contexto de parseo: `ContextHandler`, `ParsingContext`, `NameMangler`), `initializations/` (jerarquía polimórfica de inicializaciones: `BooleanInitialization`, `RealInitialization`, `StructInitialization`, `RepeatedInitialization`, etc.), `utils/` (`Publisher`, `Factory`, `UnderlyingScopeSearcher`, `DimensionCalculator`) |
| `src/main/java/utils/`             | `SymbolTable` (Repository pattern), `LexemeInfo` (DTO inmutable), `DiagnosticsHandler`, `builders/` (`LexemeInfoBuilder`, `Director`), `enums/` (`Type`, `Subtype`, `Use`, `Source`), `diagnostics/` (jerarquía `Error`/`Warning`/`SyntaxError`), `LucaInfo` (@deprecated)                                                                                                            |
| `src/test/java/`                   | Tests unitarios por componente (`unit/lexer/`, `unit/parser/`, `unit/utils/`), integración por tipo derivado (`integration/`), dobles de test (`doubles/`) y clases de soporte (`utils/`)                                                                                                                                                                                             |
| `src/test/resources/examples/`     | Programas FCL de ejemplo usados por `ParserTest` y los tests de integración                                                                                                                                                                                                                                                                                                           |
| `doc/`                             | Documentación técnica modular (`doc/modules/`), capítulos de tesis (`doc/thesis/`), diagramas Mermaid versionados (`doc/diagrams/`) y gráficos generados por código (`doc/assets/`)                                                                                                                                                                                                   |
| `scripts/`                         | Utilidades de documentación: `generate_charts.py`, `generate_diagrams.py`, `render_mermaid.py`, `synthesize_thesis_context.py`, `validate_docs.py`, `build_thesis.sh`                                                                                                                                                                                                                 |
| `Dockerfile`, `docker-compose.yml` | Entorno reproducible de build y documentación (Maven 3.9 + JDK 8, pandoc, python3)                                                                                                                                                                                                                                                                                                    |
| `.opencode/`                       | Agentes de [OpenCode](https://opencode.ai) para mantener documentación, gráficos y tesis (ver `doc/HARNESS.md`)                                                                                                                                                                                                                                                                       |

## Documentación asistida por agentes OpenCode

Este repo incluye un harness de agentes de OpenCode para mantener la documentación, los gráficos y la tesis actualizados contra el código real (nunca contra datos inventados). Ver [`doc/HARNESS.md`](doc/HARNESS.md).

## Autores

- Matias Ortiz
- Victoriano Etcheverría
