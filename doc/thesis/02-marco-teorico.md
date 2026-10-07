# Marco teórico

## IEC 61131-7 y Fuzzy Control Language

FCL organiza un programa en un `FUNCTION_BLOCK` con tres tipos de secciones: variables (`VAR_INPUT`, `VAR_OUTPUT`, `VAR`), bloques de lógica difusa (`FUZZIFY`, `DEFUZZIFY`, `RULEBLOCK`) y bloques de opciones de compilación (`OPTION`). Sobre esta base, el estándar reutiliza el sistema de tipos de IEC 61131-3 Anexo B: tipos elementales (`BOOL`, enteros con/sin signo de 8 a 64 bits, `REAL`/`LREAL`, cadenas de bits, temporales, `STRING`/`WSTRING`) y tipos derivados (`STRUCT`, arreglos, subrangos y enumerados anónimos o nombrados).

## Análisis léxico basado en autómatas finitos

El analizador léxico se generó con **JFlex**, que compila un conjunto de expresiones regulares a un autómata finito determinista (DFA) tabulado. Cada regla léxica dispara una acción semántica que produce un token para el analizador sintáctico. En este proyecto, esa acción semántica se separó explícitamente en dos etapas (ver Capítulo 4):

1. **Preprocesamiento** del lexema crudo mediante una cadena de objetos `Transformer` (patrón *Chain of Responsibility*), que normaliza el texto antes de interpretarlo (remoción de guiones bajos separadores, normalización de mayúsculas, eliminación de ceros no significativos).
2. **Análisis semántico** del lexema ya normalizado mediante un `SemanticAnalyzer`, que determina su subtipo concreto, valida rangos y lo registra en la tabla de símbolos.

## Análisis sintáctico LALR1

El analizador sintáctico se generó con **GNU Bison** (`esqueleto lalr1.java`), a partir de una gramática libre de contexto anotada con acciones semánticas en Java. Bison construye una tabla de estados LALR(1) que resuelve la gramática con una pila de estados y una pila de valores semánticos, sin necesidad de retroceso (*backtracking*).

## Patrones de diseño aplicados

| Patrón                  | Dónde se usa                                          | Motivo                                                                                       |
|-------------------------|-------------------------------------------------------|----------------------------------------------------------------------------------------------|
| Chain of Responsibility | `lexer.transformers.Transformer`                      | Componer normalizaciones léxicas independientes y reordenables                               |
| Builder                 | `utils.builders.LexemeInfoBuilder`                    | Construir `LexemeInfo` de forma incremental durante la reducción de reglas gramaticales      |
| Repository              | `utils.SymbolTable`                                   | Punto único de verdad para toda la información semántica resuelta                            |
| Strategy                | `lexer.semantics.numbers.NumbersAnalyzer` y subclases | Un algoritmo de parseo/rango distinto por familia numérica (natural, entero, real, con base) |
| Template Method         | `lexer.semantics.numbers.NumbersAnalyzer` y subclases | Estructura común de parseo/validación con pasos especializados por subclase                  |
| Publisher               | `parser.facades.Publisher`                              | Separar construcción incremental de metadatos de su publicación atómica                      |
| Composite               | `parser.initializations.Initialization` y subclases   | Tratar inicializaciones simples y compuestas uniformemente                                   |
| Name Mangling           | `parser.utils.NameMangler`                        | Resolver nombres compuestos en tabla plana sin colisiones                                    |

Estos patrones se retoman con más detalle, y con las clases concretas involucradas, en los Capítulos 4 y 5.
