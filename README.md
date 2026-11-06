# FLC IEC 61131-7 Compiler

Compiler for the IEC 61131-7 (Fuzzy Control Language, FCL) standard. It performs lexical analysis (JFlex), LALR(1) syntactic analysis (GNU Bison, lalr1.java skeleton), semantic type resolution into a symbol table, and generation of a fuzzy controller as Vivado HLS C++. It covers the declarative and initializer subset of the standard: function blocks, user-defined types (structures, enumerations, subranges, arrays), and fuzzification/defuzzification/rule blocks. A lowers phase turns the symbol table and rule-block AST into the controller model consumed by the generator, and simulators validate the generated controllers without Vivado.

## Requirements

* Java 8 (source and target locked to 1.8 in `pom.xml`)
* Maven 3.x
* JFlex 1.9.1 via `jflex-maven-plugin` (configured in `pom.xml`, no separate installation needed)
* GNU Bison 3.8.2: used offline to regenerate `Parser.java` from `Parser.y` (skeleton `lalr1.java`); the generated code is versioned, so Bison is not required for Maven builds

## Dependencies

| Dependency                       | Version | Scope    |
|----------------------------------|---------|----------|
| JUnit Jupiter (API and Engine)   | 5.11.4  | test     |
| Mockito Inline and JUnit Jupiter | 4.11.0  | test     |
| JSpecify nullability annotations | 1.0.0   | compile  |
| SpotBugs Annotations             | 4.8.3   | provided |

## Build and Test Commands

Full build with quality gates (lexer generation, compilation, unit and integration tests, coverage, Checkstyle, SpotBugs):

```bash
mvn clean verify
```

Compile and run all tests with coverage reporting:

```bash
mvn test
```

Unit tests only (skip the Failsafe integration tests):

```bash
mvn test -DskipITs
```

Run a specific unit test class and skip integration tests:

```bash
mvn test -Dtest=ParserTest -DskipITs
mvn test -Dtest=RepeatedInitializationTest -DskipITs
mvn test -Dtest=SymbolTableTest -DskipITs
mvn test -Dtest=LexerTokenizationTest -DskipITs
```

Run a specific integration test class; unit tests also run in the same invocation:

```bash
mvn test -Dit.test=CommandLineIT
mvn test -Dit.test=ControllerSimulationIT
```

Generate Javadoc (excludes generated `Parser.java` and `Lexer.java`):

```bash
mvn javadoc:javadoc
```

Regenerate the parser (requires Bison 3.8.2 installed locally):

```bash
cd src/main/java/parser
bison -o Parser.java Parser.y
```

Regenerate the lexer through Maven:

```bash
mvn jflex:generate
```

## Usage

Build the executable jar and compile an FCL program:

```bash
mvn package
java -jar target/flc-iec61131-7-compiler-1.0-SNAPSHOT.jar <program.fcl> [output directory]
```

The generated files are `<base>_out.cpp`, `<base>_out.h`, and `<base>_outTB.cpp`, where `<base>` is the program name without extension and with spaces replaced by underscores. By default, they are written next to the program. The exit status is 0 when code was generated, 1 for compilation errors or unsupported features, 2 for usage or I/O errors, and 3 for an internal compiler error.

## Architecture

```mermaid
flowchart LR
    SRC[Source .fcl] --> LEX[Lexer<br/>JFlex]
    LEX -- tokens --> PAR[Parser<br/>Bison LALR1]
    PAR -- publishes --> ST[SymbolTable]
    PAR -- reports --> DIAG[DiagnosticsHandler]
    PAR -- builds --> AST[Rule block AST]
    ST --> LOW[Lowering<br/>ControllerModel]
    AST --> LOW
    LOW --> GEN[Code generator<br/>Vivado HLS C++]
    GEN --> OUT[.cpp, .h, test bench]
```

The parser orchestrates the front end: it invokes the lexer, executes grammar semantic actions, and publishes results to a shared `SymbolTable` (Repository pattern). The lexer normalizes input text through transformer chains (`lexer/transformers/`, Chain of Responsibility) and validates literals with per-category semantic analyzers (`lexer/semantics/`). Each resolved lexeme becomes a `LexemeInfo` (type, subtype, use, source, limits, parameters, initialization), built incrementally with `LexemeInfoBuilder` and predefined `Director` recipes (`utils/builders/`), and stored in the symbol table. Semantic classification relies on the `Type`, `Subtype`, `Use`, and `Source` enums (`utils/enums/`). Errors and warnings are collected in a central `DiagnosticsHandler`, preserving insertion order.

The back end lowers the symbol table and the rule-block AST into a `ControllerModel` and emits the C++ implementation, header, and test bench. The lowers phase also reports features the generator cannot produce as unsupported. Two simulators read the same `ControllerModel`: `QuantizedSimulator` reproduces the integer arithmetic of the generated code, while `ReferenceSimulator` computes the ideal IEC 61131-7 controller in real numbers.

### Key Classes

| Module         | Class                              | Responsibility                                                                                                                       |
|----------------|------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| compiler       | `compiler_FlcCompiler`             | Chains lexer, parser, lowers, and code generation; returns generated files                                                         |
| compiler       | `compiler_CommandLine`             | Command-line entry point; writes generated files and maps failures to exit statuses                                                  |
| lowers       | `lowering_lowers_ControllerLower`  | Builds the `ControllerModel` from the symbol table and rule-block AST; reports unsupported features                                  |
| generator        | `codegen_ControllerModel`          | Immutable input model consumed by the code generator                                                                                 |
| generator        | `codegen_CodeGenerator`            | Emits the Vivado HLS C++ implementation, header, and test bench                                                                      |
| ast            | `ast_AbstractSyntaxTree`           | Root of the rule-block tree produced by the parser and consumed by lowers                                                          |
| simulation     | `simulation_QuantizedSimulator`    | Computes the integer arithmetic of the generated C++                                                                                 |
| simulation     | `simulation_ReferenceSimulator`    | Computes the ideal IEC 61131-7 controller in real numbers                                                                            |
| utils          | `utils_SymbolTable`                | Stores and retrieves `LexemeInfo` by lexeme name using a `HashMap`                                                                   |
| utils          | `utils_LexemeInfo`                 | Mutable DTO with public fields for type, subtype, custom type, use, source, limits, and initialization                               |
| utils          | `utils_DiagnosticsHandler`         | Collects diagnostics in insertion order and tracks whether any diagnostic is fatal                                                   |
| utils_builders | `utils_builders_LexemeInfoMutator` | Fluent setter contract for every `LexemeInfo` attribute                                                                              |
| utils_builders | `utils_builders_LexemeInfoView`    | Read-only getter contract for every `LexemeInfo` attribute                                                                           |
| utils_builders | `utils_builders_LexemeInfoBuilder` | Concrete builder implementing both contracts and exposing `build()`                                                                  |
| utils_builders | `utils_builders_Director`          | Static recipes for literals, defaults, and copying metadata from a view into a mutable DTO                                           |
| utils_enums    | `utils_enums_Type`                 | General classification: `ARRAY`, `ENUMERATE`, `FUNCTION`, `NONE`, `SIMPLE`, `STRUCT`, `SUBRANGE`, `UNKNOWN`                          |
| utils_enums    | `utils_enums_Subtype`              | Specific primitive, temporal, bit-string, fuzzy membership-function, and sentinel types                                              |
| utils_enums    | `utils_enums_Use`                  | Usage context: `COMPONENT`, `CONSTANT`, `FIELD`, `MAIN`, `LITERAL`, `MACRO`, `PRAGMA`, `RULE`, `TERM`, `TYPE`, `UNKNOWN`, `VARIABLE` |
| utils_enums    | `utils_enums_Source`               | Declaration block: `IN`, `OUT`, `INTERNAL`, `FUZZIFY`, `DEFUZZIFY`, `NONE`, `UNKNOWN`                                                |

Full technical documentation: [doc/modules/lexer.md](doc/modules/lexer.md), [doc/modules/parser.md](doc/modules/parser.md), [doc/modules/ast.md](doc/modules/ast.md), [doc/modules/lowers.md](doc/modules/lowering.md), [doc/modules/generator.md](doc/modules/codegen.md), [doc/modules/simulation.md](doc/modules/simulation.md), [doc/modules/compiler.md](doc/modules/compiler.md), [doc/modules/utils.md](doc/modules/utils.md).

## Repository Structure

| Folder                       | Contents                                                                                                                                                                                                                                                                                                                                                        |
|------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| src/main/java/lexer/         | Lexer.flex and generated Lexer.java, internals/ (lexical analyzers and preprocessors), transformers/ (Chain of Responsibility for lexical normalization), semantics/ (semantic analyzers per literal family: numbers, dates, strings, identifiers, intervals)                                                                                                   |
| src/main/java/parser/        | Parser.y and generated Parser.java (Luca.y is a legacy grammar not part of the build), facades/ (rule-block AST builder, dimension, range, macro, and identifier helpers), initializations/ (polymorphic initialization hierarchy: leafs/ and nodes/), metadata/ (type metadata views), publishers/ (symbol table publishers), tree/ (parse tree nodes), utils/ |
| src/main/java/ast/           | Immutable rule-block AST: AbstractSyntaxTree, AbstractAstNode, ruleblock/ nodes, ruleblock/operators/ aggregation operator nodes                                                                                                                                                                                                                                |
| src/main/java/lowers/      | ControllerLower (entry point), RuleLower, TermLower, VariableLower, builders/ (ControllerModelBuilder, ControllerModelSchema), constructors/ (bit width, fuzzy variables, rules), contexts/, facades/ (TermSetFactory), utils/ (ControllerModelState, SymbolKeys)                                                                                               |
| src/main/java/generator/       | ControllerModel, CodeGenerator, CppWriter, Fuzzifier, Variable; fuzzysets/ (membership functions), evaluation/ (min-max method), defuzzification/ (centroid defuzzifier)                                                                                                                                                                                        |
| src/main/java/compiler/      | FlcCompiler (pipeline facade), api.Application (CLI), GeneratedFile                                                                                                                                                                                                                                                                                                 |
| src/main/java/simulation/    | QuantizedSimulator, ReferenceSimulator, SimulationComparison, OutputComparison, MinMaxEvaluation                                                                                                                                                                                                                                                                |
| src/main/java/utils/         | SymbolTable (Repository pattern), LexemeInfo (mutable DTO), DiagnosticsHandler, builders/ (LexemeInfoBuilder, LexemeInfoMutator, LexemeInfoView, Director), enums/ (Type, Subtype, Use, Source), diagnostics/ (Error/Warning/SyntaxError/UnsupportedFeature hierarchy), LucaInfo (@deprecated)                                                                  |
| src/main/java/Main.java      | Compiler entry point                                                                                                                                                                                                                                                                                                                                            |
| src/test/java/               | Unit tests by component (unit/ast/, unit/generator/, unit/lexer/, unit/parser/, unit/utils/), integration tests (integration/compiler/, integration/lexer/parser/, integration/lowers/, integration/simulation/), test doubles (doubles/), support classes (utils/)                                                                                             |
| src/test/resources/examples/ | Example FCL programs used by parser and integration tests                                                                                                                                                                                                                                                                                                       |
| src/test/resources/golden/   | Reference outputs used to characterize the code generator against Luca Sarramone's generator                                                                                                                                                                                                                                                                    |
| doc/                         | Modular technical docs (doc/modules/), thesis chapters (doc/thesis/), versioned Mermaid diagrams (doc/diagrams/), code-generated charts (doc/assets/)                                                                                                                                                                                                           |
| scripts/                     | Documentation utilities: generate_charts.py, generate_diagrams.py, render_mermaid.py, synthesize_thesis_context.py, validate_docs.py, format_markdown.py, extract_stats.py, build_thesis.sh                                                                                                                                                                     |
| config/                      | Quality config: config/checkstyle/checkstyle.xml and config/spotbugs/excludeFilter.xml (used by mvn verify gates)                                                                                                                                                                                                                                               |
| .opencode/                   | OpenCode agents for docs, charts, and thesis (see doc/HARNESS.md)                                                                                                                                                                                                                                                                                               |

## Agent-Assisted Documentation

This repository includes an OpenCode agent harness to keep docs, charts, and thesis in sync with actual code (never invented data). See [doc/HARNESS.md](doc/HARNESS.md).

## Authors

* Matías Ortiz
* Victoriano Etcheverría

***

Generated and maintained by OpenCode agents. See [doc/HARNESS.md](doc/HARNESS.md) for details.
