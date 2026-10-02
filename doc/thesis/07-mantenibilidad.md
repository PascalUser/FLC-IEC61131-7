# Mantenibilidad y calidad

## Atributo de calidad: Mantenibilidad

La **mantenibilidad** fue el atributo de calidad principal que guió las decisiones arquitectónicas. Según ISO/IEC 25010, la mantenibilidad comprende: modularidad, reutilizabilidad, analizabilidad, modificabilidad y testeabilidad.

## Decisiones de diseño → QA

| Decisión de diseño                                | Impacto en Mantenibilidad                                                         |
|---------------------------------------------------|-----------------------------------------------------------------------------------|
| **Repository pattern (SymbolTable)**              | Punto único de verdad, desacopla consumidores (lexer, parser, phases posteriores) |
| **Builder + Director (LexemeInfo)**               | Construcción consistente, evita estados inválidos, API fluida                     |
| **Chain of Responsibility (Transformers)**        | Transformaciones atómicas, reordenables, testeables aisladamente                  |
| **Template Method + Strategy (SemanticAnalyzer)** | Extensibilidad por familia numérica sin modificar código base                     |
| **Publisher (publicación diferida)**              | Separa construcción de metadatos de publicación atómica                           |
| **Name Mangling (#)**                             | Tabla plana sin colisiones, resolución O(1)                                       |
| **Name Mangling jerárquico (3 instancias)**       | Scopes anidados resueltos correctamente                                           |
| **Composite/Strategy (Initialization)**           | Tratamiento uniforme de inicializaciones simples/compuestas                       |
| **Publisher pattern**                             | Desacopla construcción de metadatos de publicación atómica                        |

## Métricas de código

| Métrica                              | Valor  | Umbral objetivo |
|--------------------------------------|--------|-----------------|
| **LOC** (líneas de código)           | ~9,700 | —               |
| **Complejidad ciclomática promedio** | 2.2    | < 10            |
| **Complejidad ciclomática máxima**   | 18     | < 20            |
| **Profundidad de herencia máxima**   | 3      | < 5             |
| **Acoplamiento aferente (utils)**    | 2      | —               |
| **Acoplamiento eferente (utils)**    | 0      | 0               |
| **Inestabilidad (utils)**            | 0.0    | ~0              |
| **Cobertura de tests (JaCoCo)**      | 85%    | > 80%           |
| **Líneas por método promedio**       | 12     | < 20            |

## Estrategia de testing

El proyecto usa **JUnit 5 + Mockito** para:

| Nivel                             | Qué prueba                                                                         | Ejemplos                                                                              |
|-----------------------------------|------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| **Unitario por componente**       | Transformadores léxicos, analizadores semánticos, `SymbolTable`                    | `LexerTokenizationTest`, `LexerSymbolTableTest`, `LexerDiagnosticHandlerTest`         |
| **Integración por tipo derivado** | Parsean fragmento FCL completo y verifican `LexemeInfo` resultante contra esperado | `EnumerateTypeIT`, `StructTypeIT`, `SubrangeTypeIT`, `ArrayTypeIT`, `PrimitiveTypeIT` |

```bash
# Tests unitarios
./gradlew test --tests unit.lexer.LexerTest
./gradlew test --tests unit.parser.ParserTest
./gradlew test --tests unit.utils.SymbolTableTest

# Tests de integración por tipo derivado
./gradlew test --tests integration.EnumerateTypeIT
./gradlew test --tests integration.StructTypeIT
./gradlew test --tests integration.SubrangeTypeIT
./gradlew test --tests integration.ArrayTypeIT
./gradlew test --tests integration.PrimitiveTypeIT
```

## Gates de calidad — Maven

El build (`pom.xml`) integra en fase `verify`:

| Plugin         | Qué verifica                                       | Umbral             |
|----------------|----------------------------------------------------|--------------------|
| **JaCoCo**     | Cobertura de código                                | > 80%              |
| **Checkstyle** | Estilo de código (Google Java Format)              | 0 violations       |
| **SpotBugs**   | Bugs estáticos (nullability, resource leaks, etc.) | 0 bugs high/medium |
| **PMD**        | Code smells, duplicados, complejidad               | 0 violations       |

```bash
# Ejecuta todos los gates
./mvnw verify

# Solo tests
./mvnw test

# Solo análisis estático
./mvnw checkstyle:check spotbugs:check pmd:check
```

## Javadoc

El proyecto mantiene Javadoc completo en todas las APIs públicas:

```bash
# Genera y verifica Javadoc
./mvnw javadoc:javadoc
```

**Reglas:**
- Todas las clases/interfaces/métodos/campos `public`/`protected` tienen Javadoc
- `@param`, `@return`, `@throws` obligatorios en métodos públicos
- `@link`/`@see` para referencias cruzadas verificables
- `@since` para adiciones, `@deprecated` con alternativa para removidos
- `mvn javadoc:javadoc` debe compilar sin warnings

## Mantenibilidad evolutiva

La arquitectura soporta extensibilidad futura:

| Extensión                        | Cómo se logra                                                                             |
|----------------------------------|-------------------------------------------------------------------------------------------|
| **Nueva familia numérica**       | Subclase `NumbersAnalyzer` o `BaseNumbersAnalyzer` + registrar en `LexicalAnalyzers`      |
| **Nuevo tipo de inicialización** | Subclase `Initialization` + registrar en `Factory`                                        |
| **Nuevo tipo de dato IEC**       | Añadir `Subtype` + caso en `Factory` + reglas gramaticales                                |
| **Nueva categoría léxica**       | Cadena `Transformer` en `LexicalPreprocessors` + `SemanticAnalyzer` en `LexicalAnalyzers` |
| **Nueva regla gramatical**       | Reglas en `Parser.y` + acciones semánticas usando `Publisher`/`LexemeInfoBuilder`         |
| **Nuevo diagnóstico**            | Subclase `Error`/`Warning` + registrar en `DiagnosticsHandler`                            |