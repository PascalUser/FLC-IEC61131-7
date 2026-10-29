# Resumen de trabajo — 2026-10-28: del FCL al C++ del generador de Luca

Rama `feature/generador-luca` (13 commits sobre `03e794a`). Escrito para Matias y como entrada del harness de
documentación. Todo lo que se afirma acá se puede verificar en el código o en los tests que se citan.

## 1. Resultado en una línea

El compilador ya funciona de punta a punta: **un programa FCL (IEC 61131-7) se compila al C++ Vivado HLS del
generador de Luca Sarramone** (`<base>_out.cpp`, `<base>_out.h`, `<base>_outTB.cpp`). Los tres ejemplos de Luca
escritos en FCL generan código equivalente a sus salidas de referencia.

```
mvn package -DskipTests
java -jar target/flc-iec61131-7-compiler-1.0-SNAPSHOT.jar programa.fcl [directorio de salida]
```

## 2. Decisiones tomadas (para no volver a discutirlas)

| Tema | Decisión |
|---|---|
| Backend | Portar el generador de Luca (Generador-FLC, commit `c304c1d`) a Java dentro del proyecto, sin estado estático. Hay permiso de Luca para usar su código. |
| Nivel de soporte | Objetivo: **Basic Level** de IEC 61131-7 (Tabla 8, p. 53). Lo que excede se lee y se reporta como no soportado. |
| Tabla de símbolos | No cambia de estructura (sigue siendo un `HashMap`). Las reglas viven en el AST. |
| Claves | Con prefijo del FB: `MAIN#HUMIDITY@fuzzifier`, `MAIN#HUMIDITY#LOW`, `MAIN#DT1@defuzzifier`, `MAIN@option#BIT_WIDTH`. |
| Ancho de bits | `PRAGMA bit_width N;` en `OPTION` (1..30); si falta, 8 con warning. |
| REAL | No soportado por ahora: entradas/salidas enteras, puntos enteros en `0..2^N-1`. |
| Formas de los términos | Se clasifican en el parser por **puntos y grados** (no solo por cantidad). Nombres: `SINGLETON`, `LINEAR`, `TRIANGLE`, `TRAPEZOID`, `COMPLEX`, `VARIABLE`. `LINEAR` no se divide: si es rampa Z o S lo decide el lowering por el primer grado. |
| Orden de E/S | El de declaración. El parser lo publica en la tabla: entrada del FB bajo su nombre (`MAIN`, `Use.FUNCTION_BLOCK`) con las claves de `VAR_INPUT`/`VAR_OUTPUT` en orden en `parameters`. Define la firma del controlador y los bits de cada salida. |
| Comparación con Luca | Equivalencia semántica, no texto idéntico: tokens de C++ sin espacios, comentarios ni mayúsculas. |
| Bugs de Luca | Se corrigen en el port (ver §5). |

## 3. Arquitectura del pipeline

```
programa.fcl
  └─ Lexer + Parser ──► tabla de símbolos (con el orden de E/S en la entrada del FB) + AST de RULEBLOCK
       └─ lowering.ControllerLowering ──► codegen.ControllerModel   (y diagnósticos de lo no soportado)
            └─ codegen.CodeGenerator ──► <base>_out.cpp / _out.h / _outTB.cpp
  compiler.FlcCompiler encadena todo; compiler.CommandLine + Main son la línea de comandos.
  simulation.* evalúa el ControllerModel (hardware cuantizado vs. ideal) para validar sin Vivado.
```

| Paquete | Qué contiene |
|---|---|
| `compiler` | `FlcCompiler` (pipeline completo, devuelve `GeneratedFile` en memoria), `CommandLine` (argumentos, diagnósticos por stderr, escritura de archivos, códigos de salida 0/1/2/3). |
| `codegen` | Port del generador de Luca: `CodeGenerator`, `ControllerModel` (reemplaza `IOVars` y `Compiler.table`), `CppWriter`, `Fuzzifier`, `Variable`, `fuzzysets.*` (`Singleton`, `ZShape`, `SShape`, `Triangular`, `Trapezoid`), `evaluation.{EvaluationMethod, MinMaxMethod}`, `defuzzification.{Defuzzifier, CentroidDefuzzifier}`. Matriz de reglas: una columna por entrada y después por salida. |
| `lowering` | `ControllerLowering` (entrada; también es el chequeo de soporte), `FuzzySetLowering`, `RuleLowering`, `SymbolKeys` (único lugar que arma claves de la tabla), `LoweringReport`. |
| `simulation` | `QuantizedSimulator` (lo que calcula el C++ generado, aritmética `ap_uint<N>`), `ReferenceSimulator` (controlador ideal del estándar), `SimulationComparison`, `OutputComparison`. |
| `utils.diagnostics` | Nuevos: `UnsupportedFeature` (error), `UndeclaredReference` (error), `GeneratorWarning`. |

Qué acepta hoy el generador (todo lo demás da `UnsupportedFeature`): E/S enteras con FUZZIFY/DEFUZZIFY; salidas
`METHOD: CoGS` solo con singletons; un solo `RULEBLOCK` con `ACCU: MAX`; cada regla es `AND: MIN` de `IS` sobre
**todas** las entradas y concluye **todas** las salidas; sin `OR`, `NOT` ni `WITH` distinto de 1. `DEFAULT` se
ignora con warning (el generador no lo implementa).

## 4. Cambios en código existente (Matias)

| Dónde | Cambio |
|---|---|
| `Parser.y` | Acciones de `pragma` (OPTION a la tabla); al cerrar el FB publica su entrada (`MAIN`) con las E/S en orden; `getFunctionBlockName()`. Bison sin conflictos. |
| `parser.publishers` | `Publisher.keys()` (Composite: `SingleDeclaration`, `MultipleDeclaration`, `DeclarationGroup`). `ComponentDeclaration` agrega el sufijo `@fuzzifier/@defuzzifier` en `key()` en vez de modificar `identifier` en `publish()`; las claves publicadas no cambian. |
| `parser.facades` | `FunctionTypeCalculator` se reemplazó por `MembershipFunctionClassifier` (+ `parser.utils.MembershipFunctionShape`). Los términos guardan en `parameters` las x que definen la forma (p. ej. `(0,1)(5,1)(10,0)` → `LINEAR`, `[5, 10]`). |
| `parser.metadata` | Nuevos `PragmaMetadata` y `FunctionBlockMetadata`; `TermMetadata` usa el clasificador. |
| `utils.enums` | `Use.PRAGMA`, `Use.FUNCTION_BLOCK`, `Subtype.VARIABLE`. `utils.builders.Director.makeTrueBoolean`. |
| Tests | `FunctionBlockInterfaceIT` verifica la entrada del FB; `FuzzifierIT`/`DefuzzifierIT` esperan `parameters`; se quitó un `@NotYetImplemented` que ya pasaba. |

## 5. Bugs del generador original de Luca (corregidos en el port)

- **Triángulo** (`TriangularFS`): las pendientes estaban cruzadas (la de subida del lado que baja y viceversa).
  En el péndulo, `nm = TRIANGULAR(0, 99, 113)` daba 242 en x = 0 y 226 en x = 113, donde corresponde 0.
- **Trapecio** (`TrapezoidFS`): condición externa invertida y meseta `= 1` en vez de `2^N - 1`.
- Las rampas Z y S y los singletons estaban bien.

Las referencias quedan en `src/test/resources/golden/luca/<ejemplo>/`: `original/` (salida de Luca sin tocar) y
`expected/` (con los triángulos corregidos por un reemplazo mecánico). El `README.md` de esa carpeta documenta la
procedencia, la traducción DSL → FCL, la corrección y cómo regenerar sin la GUI de Luca.

## 6. Validación

- **Port fiel**: con las entradas DSL de Luca, el port genera el mismo texto que su herramienta
  (`LucaGeneratorCharacterizationTest`; una mutación de un carácter hace fallar el test).
- **Punta a punta**: los tres ejemplos en FCL generan C++ equivalente a `expected/` (`LucaGoldenIT`).
- **Simulador vs. C++ generado**: los grados de `QuantizedSimulator` coinciden con la ejecución del C++ generado
  (traducido a Python) en los 8960 valores de los tres ejemplos.
- **Error del hardware generado contra el controlador ideal** (todas las entradas, salida en escala 0..255):

| Controlador | Error máximo | Error medio | Con el triángulo original de Luca |
|---|---|---|---|
| Péndulo invertido | 4,66 | 0,84 | 52,4 |
| Estacionamiento del camión | 4,39 | 0,23 | 71,6 |
| Autoenfoque (3 salidas, 16,7 M entradas) | ≤ 4,46 | ≤ 0,37 | — |

  El error restante viene de las pendientes enteras (`255/(b-a)`: el pico no siempre llega a 255).
- **Hallazgo**: en el camión hay 512 entradas (anguloCamion = 0 o 255) donde no se activa ninguna regla: el
  centroide divide por cero. Es el caso que cubre `DEFAULT` del estándar, hoy ignorado.

Tests al cierre: **835 unit y 256 integración (`-Dtest=*IT`), 0 fallos, 19 omitidos** (`@NotYetImplemented`).
`mvn verify` falla por problemas **que ya estaban en `main`**: Checkstyle (2 líneas > 120 en
`RuleblockASTBuilder.java:38` y `parser/utils/package-info.java:7`) y 24 avisos de SpotBugs en clases previas.
El código nuevo no agrega ninguno.

## 7. Pendiente / decisiones abiertas

- **Brechas hacia el Basic Level**: `DEFAULT` (valor y `NC`), reglas que no usan todas las entradas, una sola
  conclusión por regla con varias salidas, formas de ≤ 3 puntos que no son triángulo/rampa (p. ej. la V),
  precisión de las pendientes enteras. Propuesta más chica: `DEFAULT` con valor (una línea en el defuzzificador).
- Hoy se rechazan (revisables): `METHOD: CoG` (equivale a CoGS con singletons) y varios `RULEBLOCK` (se habló de
  fusionarlos).
- **Parser**: no recibe `DiagnosticsHandler`, así que los errores del estándar en el cuerpo FCL (variable o término
  inexistente, duplicados) los detecta recién el lowering. Bugs anotados: `A AND B OR C` da syntax error (es el
  ejemplo del estándar), OR por defecto no se aparea con AND (De Morgan), singleton con variable
  (`TERM a := k;`) se trata como tipo custom, `DEFAULT := NC` queda como `null`.
- Archivo de referencia: el detalle de decisiones y hallazgos está en el `CLAUDE.md` local (no versionado).

## 8. Para el agente de documentación

El código nuevo todavía no tiene documentación generada. Sugerido:

1. `/docs-update compiler`, `/docs-update codegen`, `/docs-update lowering`, `/docs-update simulation` (módulos
   nuevos) y `/docs-update parser` (OPTION, orden de E/S, clasificador de formas).
2. `/diagram-refresh` del pipeline completo (FCL → tabla + AST → `ControllerModel` → C++) y de `codegen`.
3. `/chart-refresh` (hay paquetes y tests nuevos).
4. `/readme-update`: el compilador ahora es ejecutable (`java -jar … programa.fcl [dir]`).
5. Para la tesis (`/thesis-build`): generación del controlador (port y corrección del generador de Luca),
   validación (referencias, caracterización, simulador) y los resultados de la tabla de §6. La procedencia de las
   referencias está en `src/test/resources/golden/luca/README.md`.
