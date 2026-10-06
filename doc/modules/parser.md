# parser

Módulo de análisis sintáctico generado con **GNU Bison 3.8.2** (LALR(1)) a partir de `src/main/java/parser/Parser.y`; implementa la gramática de **IEC 61131-7** y el **Anexo B de IEC 61131-3**.

## Diagrama de paquetes

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

```mermaid
classDiagram
    namespace parser {
        class Parser {
            +parse()
        }
    }
    namespace parser_internals {
        class ContextHandler {
            +stack: Stack~ParsingContext~
            +add()
            +pop()
            +current()
        }
        class ParsingContext {
            +declaredIdentifiers: List
            +metadataBuilder: LexemeInfoBuilder
            +outerScopes: NameMangler
            +searchScope: NameMangler
            +nestedFields: NameMangler
        }
        class NameMangler {
            +prefix: StringBuilder
            +addScope()
            +popScope()
            +getNameMangled()
        }
    }
    namespace parser_utils {
        class Publisher {
            +publish(ParsingContext)
        }
        class Factory {
            +createPrimitiveInitialization()
        }
        class DimensionCalculator {
            +calculate()
        }
        class UnderlyingScopeSearcher {
            +search()
        }
    }
    namespace parser_initializations {
        class Initialization {
            +selectVariable()
            +getVariableValue()
            +copy()
        }
        class VariableInitialization
        class StringInitialization {
            +symbolTable: SymbolTable
            +subtype: Subtype
        }
        class BooleanInitialization
        class RealInitialization
        class EnumeratedInitialization
        class MacroInitialization
        class SubrangeInitialization
        class StructInitialization
        class RepeatedInitialization
    }
    Initialization <|-- VariableInitialization
    Initialization <|-- StringInitialization
    Initialization <|-- BooleanInitialization
    Initialization <|-- RealInitialization
    Initialization <|-- EnumeratedInitialization
    Initialization <|-- MacroInitialization
    Initialization <|-- SubrangeInitialization
    Initialization <|-- StructInitialization
    Initialization <|-- RepeatedInitialization
    Parser --> ContextHandler : uses
    ContextHandler --> ParsingContext : manages
    ParsingContext --> NameMangler : uses
    ParsingContext --> LexemeInfoBuilder : uses
    Publisher --> SymbolTable : publishes
    Publisher --> ParsingContext : reads
```

Diagrama de clases: `doc/diagrams/parser_class_diagram.mmd` (generado por `scripts/generate_diagrams.py parser`).

## Tabla de clases

| Clase                                           | Responsabilidad                                                                                       | Patrón                  |
|-------------------------------------------------|-------------------------------------------------------------------------------------------------------|-------------------------|
| `parser.Parser`                                 | Analizador LALR(1) generado por Bison; punto de entrada `parse()`                                     | Generated Parser        |
| `parser.internals.ContextHandler`               | Pila LIFO de contextos de análisis anidados; operaciones `add`, `pop`, `current`                       | Stack / Context Manager |
| `parser.internals.ParsingContext`               | Contexto mutable por ámbito: identificadores declarados, builder semántico, tres `NameMangler` e índice de array | Context Object   |
| `parser.internals.NameMangler`                  | Prefijos jerárquicos con separador `#` (`FB#TYPE#FIELD`) para resolución de nombres                    | Name Mangling           |
| `parser.utils.Publisher`                        | Publica los `LexemeInfo` construidos en `SymbolTable` a partir de `ParsingContext`                     | Publisher               |
| `parser.utils.Factory`                          | Crea inicializaciones por defecto de tipos primitivos; actualmente solo `Subtype.REAL`                 | Factory                 |
| `parser.utils.DimensionCalculator`              | Calcula la cardinalidad total de arrays multidimensionales desde `inferiorLimits`/`superiorLimits`     | Calculator              |
| `parser.utils.UnderlyingScopeSearcher`          | Sigue la cadena de tipos custom hasta el ámbito raíz que contiene campos/enums                          | Searcher                |
| `parser.initializations.Initialization`         | Interfaz de valores iniciales polimórficos (`selectVariable`, `getVariableValue`, `copy`)              | Composite / Strategy    |
| `parser.initializations.VariableInitialization` | Envuelve un literal o identificador como valor inicial explícito                                       | Value Object            |
| `parser.initializations.StringInitialization`   | Valor por defecto de STRING/WSTRING registrado perezosamente en `SymbolTable`                          | Value Object            |
| `parser.initializations.BooleanInitialization`  | Valor por defecto BOOL `FALSE` registrado perezosamente                                                | Value Object            |
| `parser.initializations.RealInitialization`     | Valor por defecto REAL `0.0` registrado perezosamente                                                  | Value Object            |
| `parser.initializations.EnumeratedInitialization` | Primer valor de un enumerado como valor por defecto                                                  | Value Object            |
| `parser.initializations.MacroInitialization`    | Valor ordinal de un literal de enumerado registrado como `Subtype.INT`                                 | Value Object            |
| `parser.initializations.SubrangeInitialization` | Cota inferior como valor por defecto; conserva inferior y superior                                     | Value Object            |
| `parser.initializations.StructInitialization`   | Mapa `field → Initialization` con claves compuestas para structs anidados                              | Composite               |
| `parser.initializations.RepeatedInitialization` | Partición en intervalos `[start, end] → Initialization` para arrays con repetición                     | Interval Map            |

Todos los tipos de inicialización salvo `Initialization` residen en `src/main/java/parser/initializations/`; `StringInitialization` fue incorporado recientemente y ya aparece en el diagrama de clases y en la jerarquía.

## Gramática soportada — resumen

### Métricas de la gramática

| Métrica              | Valor | Origen                                                  |
|----------------------|-------|---------------------------------------------------------|
| Tokens declarados    | 82    | directivas `%token` de `src/main/java/parser/Parser.y`   |
| No-terminales        | 231   | `enum SymbolKind` de `src/main/java/parser/Parser.java`  |
| Tipos de inicialización | 9  | archivos de `src/main/java/parser/initializations/`      |

Chart: `assets/parser_grammar_stats.png` — tokens declarados, no-terminales y tipos de inicialización de la gramática Bison.

### Bloques principales IEC 61131-7

```
program → opt_data_type_declaration function_block_declaration

function_block_declaration → FUNCTION_BLOCK function_block_name
    opt_fb_io_var_declarations_list   (VAR_INPUT / VAR_OUTPUT)
    opt_other_var_declarations_list   (VAR)
    opt_function_block_body
    END_FUNCTION_BLOCK

opt_function_block_body → opt_fuzzify_block_list
    opt_defuzzify_block_list
    opt_rule_block_list
    opt_option_block_list
```

### Fuzzify / Defuzzify

```
fuzzify_block → FUZZIFY IDENTIFIER linguistic_term_list END_FUZZIFY
linguistic_term → TERM IDENTIFIER ASSIGN_OP IDENTIFIER ';'
                | TERM IDENTIFIER ASSIGN_OP membership_function ';'
membership_function → singleton | point_list
point_list → point | point_list point
point → '(' numeric_constant ',' numeric_constant ')'
      | '(' IDENTIFIER ',' numeric_constant ')'

defuzzify_block → DEFUZZIFY IDENTIFIER
    opt_range
    opt_linguistic_term_list
    defuzzification_method
    default_value
    END_DEFUZZIFY
defuzzification_method → METHOD ':' defuzz_method ';'
defuzz_method → COG | COGS | COA | LM | RM
default_value → DEFAULT ASSIGN_OP default_val ';'
default_val → numeric_constant | NC
opt_range → empty | RANGE '(' numeric_constant RANGE_OP numeric_constant ')' ';'
```

### Rule Block

```
rule_block → RULEBLOCK IDENTIFIER
    operator_definition
    activation_method_opt
    accumulation_method
    rule_list
    END_RULEBLOCK

operator_definition → opt_operator_or operator_and_opt ';'
opt_operator_or → empty | OR ':' or_type
operator_and_opt → empty | AND ':' and_type
or_type → MAX | ASUM | BSUM
and_type → MIN | PROD | BDIF
activation_method → ACT ':' act_type ';'
act_type → PROD | MIN
accumulation_method → ACCU ':' accu_type ';'
accu_type → MAX | BSUM | NSUM

rule → RULE numeric_constant ':' IF condition THEN conclusion_list opt_weighting ';'
condition → x condition_tail | IDENTIFIER condition_tail
x → NOT x | NOT IDENTIFIER | subcondition | '(' condition ')'
subcondition → IDENTIFIER IS IDENTIFIER | IDENTIFIER IS NOT IDENTIFIER
conclusion_list → IDENTIFIER IS IDENTIFIER | IDENTIFIER
                | conclusion_list ',' IDENTIFIER IS IDENTIFIER
                | conclusion_list ',' IDENTIFIER
```

### Declaraciones de variables — Anexo B IEC 61131-3

```
var_declarations → var_id_decl var_constant_spec var_init_decl_list ';' END_VAR
var_id_decl → VAR { source=INTERNAL, use=VARIABLE }
io_var_decl → VAR_INPUT { source=IN, use=VARIABLE }
           | VAR_OUTPUT { source=OUT, use=VARIABLE }

var_init_decl → identifier_list ':' var_spec_init { Publisher.publish(ctx) }
             | identifier_list ':' standard_function_block_spec_init
var_spec_init → custom_spec_init | boolean_spec_init | simple_spec_init
             | subrange_spec_init | enumerated_spec_init | array_spec_init | string_spec_init
```

### Tipos de datos

```
simple_specification → elementary_type_name  { subtype ∈ {SINT..ULINT, REAL, LREAL, TIME, DATE, ...} }
custom_specification → IDENTIFIER  { subtype=CUSTOM, customType=IDENTIFIER }
subrange_specification → subrange_type_decl '(' range ')'
range → numeric_constant RANGE_OP numeric_constant
enumerated_specification → '(' enumerated_values ')'
array_specification → ARRAY '[' range_list ']' OF IDENTIFIER
                    | ARRAY '[' range_list ']' OF non_generic_type_name
structure_specification → STRUCT structure_field_declaration_list END_STRUCT
type_string_specification → STRING { subtype=STRING } | WSTRING { subtype=WSTRING }
string_specification → type_string_specification
                     | type_string_specification '[' numeric_constant ']'
```

### Inicializaciones

```
string_spec_init → string_specification | initialized_string
initialized_string → string_specification ASSIGN_OP string_constant

initialized_simple → simple_specification ASSIGN_OP constant
initialized_custom → custom_type_name ASSIGN_OP constant
                   | custom_type_name ASSIGN_OP identifier_with_opt_mangling
                   | custom_type_name ASSIGN_OP structure_initialization
initialized_boolean → boolean_specification edge
                    | boolean_specification ASSIGN_OP boolean_constant
initialized_subrange → subrange_specification ASSIGN_OP numeric_constant
initialized_enumerated → enumerated_specification ASSIGN_OP identifier_with_opt_mangling
initialized_array → array_specification ASSIGN_OP array_initialization
array_initialization → array_init_open_square_bracket array_initial_elements_list ']'
array_initial_elements → constant | identifier_with_opt_mangling
                       | structure_initialization | array_initialization
repeated_initial_element → numeric_constant '(' array_initial_element ')'
structure_initialization → struct_init_open_parenthesis structure_field_initialization_list ')'
initialized_structure_field → nested_field ASSIGN_OP constant
                            | nested_field ASSIGN_OP identifier_with_opt_mangling
                            | nested_field ASSIGN_OP array_initialization
                            | nested_field ASSIGN_OP structure_initialization
nested_field → IDENTIFIER
identifier_with_opt_mangling → IDENTIFIER | IDENTIFIER '#' IDENTIFIER
```

### Declaración de tipos de datos

```
data_type_declaration → type_id_decl type_declaration_list END_TYPE
type_id_decl → TYPE { use=TYPE, source=NONE }
type_declaration → type_name_declaration ':' type_spec_init
type_spec_init → custom_spec_init | simple_spec_init | enumerated_spec_init
               | subrange_spec_init | array_spec_init | structure_specification | string_spec_init
```

## Acciones semánticas clave

Extracto de `src/main/java/parser/Parser.y`:

| Regla                              | Acción semántica                                                                                                     |
|------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| `function_block_name`              | Crea `ParsingContext`, añade el nombre a `outerScopes`, `contexts.add(ctx)`                                          |
| `function_block_declaration`       | Al reducir `END_FUNCTION_BLOCK`: `contexts.pop()`                                                                    |
| `io_var_decl` (VAR_INPUT)          | `ctx.metadataBuilder().source(Source.IN).use(Use.VARIABLE)`                                                          |
| `io_var_decl` (VAR_OUTPUT)         | `ctx.metadataBuilder().source(Source.OUT).use(Use.VARIABLE)`                                                         |
| `var_id_decl` (VAR)                | `ctx.metadataBuilder().source(Source.INTERNAL).use(Use.VARIABLE)`                                                    |
| `var_init_decl`                    | `Publisher.publish(ctx); ctx.declaredIdentifiers().clear()`                                                          |
| `custom_type_name`                 | Busca `LexemeInfo`, fija `type=SIMPLE, subtype=CUSTOM` y añade el ámbito subyacente a `searchScope`                   |
| `custom_specification`             | Fija `type=SIMPLE, subtype=CUSTOM, customType=$1, initialValue` tomado del `SymbolTable`                              |
| `simple_specification`             | `type=SIMPLE, subtype=$1, initialValue=Factory.createPrimitiveInitialization(...)`                                   |
| `range`                            | Acumula límites en `inferiorLimits` / `superiorLimits` del builder                                                   |
| `enumerated_values_list`           | Crea contexto hijo por valor enum, `use=MACRO, source=NONE`, publica cada literal                                    |
| `array_specification`              | `type=ARRAY, initialValue=RepeatedInitialization(dimension, defaultInit)` para elemento custom o primitivo            |
| `array_initial_elements`           | Consolida intervalos con `RepeatedInitialization.addInterval(start, end, init)` y reinicia el índice                  |
| `structure_field_declaration`      | Nuevo contexto por campo, `use=FIELD, source=NONE`, publica y hace `pop` al reducir                                  |
| `structure_specification`          | Construye `StructInitialization` con el `initialValue` de cada campo                                                 |
| `string_specification`             | `type=SIMPLE`, `subtype` ya fijado por `type_string_specification`, `initialValue=new StringInitialization(...)`     |
| `initialized_string`               | `initialValue=new VariableInitialization($3)` a partir de `string_constant`                                          |
| `type_declaration`                 | `outerScopes.popScope(); Publisher.publish(ctx); declaredIdentifiers().clear()`                                      |

Nota: `Factory.createPrimitiveInitialization` solo soporta `Subtype.REAL`; para el resto lanza `IllegalArgumentException` (`src/main/java/parser/utils/Factory.java`).

## Inicializaciones — jerarquía y uso

```mermaid
classDiagram
    class Initialization {
        <<interface>>
        +selectVariable(String) Initialization
        +getVariableValue() String
        +copy() Initialization
    }
    class VariableInitialization {
        +value: String
    }
    class StringInitialization {
        +symbolTable: SymbolTable
        +subtype: Subtype
    }
    class BooleanInitialization {
        +symbolTable: SymbolTable
    }
    class RealInitialization {
        +symbolTable: SymbolTable
    }
    class EnumeratedInitialization {
        +enumeratedValue: String
    }
    class MacroInitialization {
        +symbolTable: SymbolTable
        +replaceValue: String
    }
    class SubrangeInitialization {
        +ilimit: String
        +slimit: String
    }
    class StructInitialization {
        +map: Map
        +setFieldInitialization(String, Initialization)
        +selectVariable(String) Initialization
        +copy() Initialization
    }
    class RepeatedInitialization {
        +dimension: int
        +intervals: List
        +addInterval(int, int, Initialization)
        +getRepetitionsList() List
    }
    Initialization <|-- VariableInitialization
    Initialization <|-- StringInitialization
    Initialization <|-- BooleanInitialization
    Initialization <|-- RealInitialization
    Initialization <|-- EnumeratedInitialization
    Initialization <|-- MacroInitialization
    Initialization <|-- SubrangeInitialization
    Initialization <|-- StructInitialization
    Initialization <|-- RepeatedInitialization
```

Comportamiento por defecto de `getVariableValue`:

| Clase                        | Valor por defecto                                | Registro en `SymbolTable`                                |
|------------------------------|--------------------------------------------------|----------------------------------------------------------|
| `VariableInitialization`     | literal o identificador asignado                 | no registra (solo envuelve)                              |
| `StringInitialization`       | literal por defecto STRING o WSTRING              | `putIfAbsent` vía `Director.makeDefaultString/WString`   |
| `BooleanInitialization`      | `FALSE`                                          | `putIfAbsent` vía `Director.makeDefaultBoolean`          |
| `RealInitialization`         | `0.0`                                            | `putIfAbsent` vía `Director.makeDefaultReal`             |
| `EnumeratedInitialization`   | primer valor del enumerado                       | no registra (selecciona el valor)                        |
| `MacroInitialization`        | ordinal del literal de enum                      | `putIfAbsent` con `subtype=INT`                          |
| `SubrangeInitialization`     | cota inferior                                    | no registra                                              |
| `StructInitialization`       | mapa campo → `Initialization`                    | no registra                                              |
| `RepeatedInitialization`     | partición de intervalos `[start, end]`            | no registra                                              |

Todos los valores por defecto perezosos usan un campo `static DEFAULT` y `SymbolTable.putIfAbsent`, de modo que una misma tabla no duplica la entrada del literal.

## Flujo Lexer → Parser → SymbolTable

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

Diagrama de secuencia: `doc/diagrams/lexer_parser_sequence.mmd`.

## Secuencia de contexto de análisis

```mermaid
sequenceDiagram
    participant Parser as Parser (Bison)
    participant CH as ContextHandler
    participant PC as ParsingContext
    participant NM as NameMangler
    participant Builder as LexemeInfoBuilder
    participant Pub as Publisher
    participant ST as SymbolTable

    Parser->>CH: add(ctx)
    CH->>PC: new ParsingContext()
    CH->>NM: new NameMangler() x3
    CH->>Builder: new LexemeInfoBuilder()

    loop Reducciones de la gramática
        Parser->>Builder: configure attributes
        Parser->>PC: declaredIdentifiers().add(...)
    end

    Parser->>Pub: publish(ParsingContext)
    Pub->>PC: build() LexemeInfo
    loop Por cada identificador declarado
        Pub->>NM: getNameMangled(identifier)
        Pub->>ST: put(mangledName, LexemeInfo)
    end

    Parser->>CH: pop()
```

Diagrama de secuencia: `doc/diagrams/context_handler_sequence.mmd`.

## Tabla de símbolos — población desde el Parser

```mermaid
classDiagram
    class SymbolTable {
        +table: Map
        +get()
        +put()
        +putIfAbsent()
    }
    class LexemeInfo {
        +type: Type
        +subtype: Subtype
        +customType: String
        +use: Use
        +source: Source
        +inferiorLimits: List
        +superiorLimits: List
        +parameters: List
        +initialValue: Object
    }
    SymbolTable --> "0..*" LexemeInfo : contains
```

Diagrama de almacenamiento: `doc/diagrams/symboltable_storage.mmd`.

| Tipo                  | Cuándo se publica                      | Clave en SymbolTable | LexemeInfo destacado                                                                         |
|-----------------------|----------------------------------------|----------------------|----------------------------------------------------------------------------------------------|
| Variable simple       | `var_init_decl` (simple_spec_init)     | `FB#varName`         | type=SIMPLE, subtype=INT/REAL/BOOL..., initialValue=según `Factory`                            |
| Variable string       | `var_init_decl` (string_spec_init)     | `FB#varName`         | type=SIMPLE, subtype=STRING/WSTRING, initialValue=StringInitialization                        |
| Variable custom       | `var_init_decl` (custom_spec_init)     | `FB#varName`         | type=SIMPLE, subtype=CUSTOM, customType=TypeName, initialValue=Type.initialValue              |
| Variable inicializada | `initialized_simple/custom/boolean`    | `FB#varName`         | initialValue=VariableInitialization(literal)                                                  |
| Array                 | `var_init_decl` (array_spec_init)      | `FB#arrName`         | type=ARRAY, subtype=elementType, initialValue=RepeatedInitialization(dimension, defaultInit)  |
| Array inicializado    | `initialized_array`                    | `FB#arrName`         | initialValue=RepeatedInitialization con intervalos                                            |
| Struct                | `structure_specification` (END_STRUCT) | `TypeName`           | type=STRUCT, parameters=[field1..], initialValue=StructInitialization                         |
| Campo struct          | `structure_field_declaration`          | `TypeName#fieldName` | use=FIELD, type/subtype del campo                                                             |
| Enum (tipo)           | `enumerated_specification`             | `FB#enumName`        | type=ENUMERATE, subtype=INT, parameters=[A,B,C]                                               |
| Enum valor (macro)    | `enumerated_values_list`               | `TypeName#VALUE`     | use=MACRO, initialValue=MacroInitialization(index)                                            |
| Subrange              | `subrange_specification`               | `FB#varName`         | type=SUBRANGE, inferiorLimits/superiorLimits                                                  |
| Tipo declarado        | `type_declaration`                     | `TypeName`           | use=TYPE, type/subtype/parameters/initialValue según spec                                     |

## Tests

Tests de integración en `src/test/java/unit/parser/ParserTest.java`:

* `Parse_ForSyntacticallyValidPrograms_IsTrue` — prueba parametrizada sobre los cuatro programas de `src/test/resources/examples/` (`program01.txt` … `program04.txt`); verifica `parse() == true` y ausencia de errores de diagnóstico.

Tests unitarios en `src/test/java/unit/parser/initializations/RepeatedInitializationTest.java`:

* Ocho métodos `@Test` que cubren el constructor, la inserción de intervalos (medio, primero, fuera de rango, recorte, fusión de adyacentes, cobertura múltiple) y `copy()`.

Base de soporte en `src/test/java/utils/ParserTestSupport.java`:

* `parse(Reader)` / `parse(String)` — crea `SymbolTable`, `DiagnosticsHandler`, `Lexer` y `Parser`; asegura éxito y ausencia de errores; retorna el `SymbolTable` poblado.

Ejecución:

```bash
mvn test -Dtest=ParserTest
mvn test -Dtest=RepeatedInitializationTest
```

Cobertura del paquete `parser`: **80%** (`doc/stats.json` — `test_coverage.packages.parser`).

Chart: `assets/test_coverage.png` — cobertura JaCoCo por módulo; el paquete `parser` aparece en 80%.

Detalle por subpaquete desde `target/site/jacoco/jacoco.xml`:

| Subpaquete               | Instrucciones cubiertas | Líneas cubiertas | Ramas cubiertas |
|--------------------------|-------------------------|------------------|-----------------|
| `parser`                 | 96.6%                   | 79.1%            | 40.8%           |
| `parser.internals`       | 98.8%                   | 100.0%           | 75.0%           |
| `parser.utils`           | 82.0%                   | 86.4%            | 70.0%           |
| `parser.initializations` | 55.9%                   | 64.9%            | 50.7%           |

Métricas adicionales (`doc/stats.json`): 3994 LOC en 18 archivos; complejidad ciclomática promedio 5.9 (total 432, 73 métodos estimados); acoplamiento afferent 1 / efferent 1, inestabilidad 0.5.

Chart: `assets/loc_per_module.png` — líneas de código por módulo.

Chart: `assets/cyclomatic_complexity.png` — complejidad ciclomática promedio por paquete.

Chart: `assets/package_coupling.png` — acoplamiento afferent/efferent por paquete.

## Archivos fuente

```
src/main/java/parser/
├── package-info.java
├── Parser.java            (generado desde Parser.y — 3211 líneas)
├── Parser.y               (gramática Bison — 1553 líneas)
├── internals/
│   ├── package-info.java
│   ├── ContextHandler.java
│   ├── ParsingContext.java
│   └── NameMangler.java
├── utils/
│   ├── package-info.java
│   ├── Publisher.java
│   ├── Factory.java
│   ├── DimensionCalculator.java
│   └── UnderlyingScopeSearcher.java
└── initializations/
    ├── package-info.java
    ├── Initialization.java
    ├── VariableInitialization.java
    ├── StringInitialization.java
    ├── BooleanInitialization.java
    ├── RealInitialization.java
    ├── EnumeratedInitialization.java
    ├── MacroInitialization.java
    ├── SubrangeInitialization.java
    ├── StructInitialization.java
    └── RepeatedInitialization.java
```

## Diagramas de apoyo — assets

| Diagrama                | Archivo                                        | Descripción                                    |
|-------------------------|------------------------------------------------|------------------------------------------------|
| Clases parser           | `doc/diagrams/parser_class_diagram.mmd`        | Estructura interna completa                     |
| Secuencia Lexer↔Parser  | `doc/diagrams/lexer_parser_sequence.mmd`       | Flujo de tokens y publicación                   |
| Contexto de análisis    | `doc/diagrams/context_handler_sequence.mmd`    | `add`/`pop` de `ParsingContext` y publicación   |
| Almacenamiento símbolos | `doc/diagrams/symboltable_storage.mmd`         | `SymbolTable` y `LexemeInfo`                    |
| Dependencias de paquetes| `doc/diagrams/package_dependencies.mmd`        | Aristas lexer → parser → utils                  |

## Capítulos de tesis que consumen este módulo

| Capítulo | Enfoque                                                                                              |
|----------|------------------------------------------------------------------------------------------------------|
| 06       | Análisis sintáctico — gramática, Publisher, jerarquía de inicializaciones, almacenamiento            |
| 07       | Mantenibilidad — métricas (CYCLO, LOC, cobertura) y acoplamiento `parser → utils` (efferent=1)        |

## Regeneración del parser

```bash
# Requiere bison 3.8.2 y flex
cd src/main/java/parser
bison -o Parser.java Parser.y
# Lexer.java se genera aparte desde Lexer.flex
```

*Nota: `Parser.java` está versionado; no editar a mano — modificar `Parser.y` y regenerar.*
