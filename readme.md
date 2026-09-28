# FLC-IEC61131-7 Compiler

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![Java](https://img.shields.io/badge/Java-17+-blue)]()
[![Maven](https://img.shields.io/badge/Maven-3.8+-red)]()
[![License](https://img.shields.io/badge/License-Academic-yellow)]()

A **Domain Specific Language (DSL) compiler** for the **IEC 61131-7** standard (Fuzzy Control Language - FCL). This compiler implements the declaration and initialization subsets of the standard, translating FCL source code into a semantically validated symbol table.

## Overview

The IEC 61131-7 standard defines **FCL (Fuzzy Control Language)** for programming fuzzy logic controllers in industrial automation. This compiler provides a complete frontend implementation including:

- **Lexical Analysis** - JFlex-generated lexer with semantic validation for all IEC 61131-7 literal types
- **Syntactic Analysis** - Bison-generated LALR(1) parser for the full declaration grammar
- **Symbol Table** - Centralized repository pattern with polymorphic semantic information model
- **Initialization System** - Polymorphic hierarchy supporting arrays, structs, repetition factors, and nested initializers
- **Diagnostics** - Hierarchical error/warning system with precise source location tracking

## Supported Language Features

| Category | Features |
|----------|----------|
| **Function Blocks** | `FUNCTION_BLOCK` ... `END_FUNCTION_BLOCK` |
| **Variable Sections** | `VAR_INPUT`, `VAR_OUTPUT`, `VAR`, `VAR CONSTANT` |
| **Elementary Types** | `BOOL`, `SINT`/`INT`/`DINT`/`LINT`, `USINT`/`UINT`/`UDINT`/`ULINT`, `REAL`/`LREAL`, `TIME`/`DATE`/`TIME_OF_DAY`/`DATE_AND_TIME`, `BYTE`/`WORD`/`DWORD`/`LWORD`, `STRING`/`WSTRING` |
| **User-Defined Types** | `ENUMERATED`, `SUBRANGE`, `ARRAY` (multi-dimensional), `STRUCT` (nested) |
| **Initialization** | Literals, repetition factors `N(value)`, named struct init `(field := value)`, array of structs |
| **Fuzzy Blocks** | `FUZZIFY`, `DEFUZZIFY`, `RULEBLOCK`, `OPTION` (syntactic recognition) |

## Architecture

```
Source Code (.fcl)
       │
       ▼
┌─────────────┐    Tokens (LexemeInfo)    ┌─────────────────┐
│   LEXER     │ ─────────────────────────▶ │    PARSER       │
│  (JFlex)    │                            │   (Bison)       │
└─────────────┘                            └────────┬────────┘
                                                     │
                                                     ▼
                                            ┌─────────────────┐
                                            │  SYMBOL TABLE   │
                                            │   (Repository)  │
                                            └────────┬────────┘
                                                     │
                                                     ▼
                                            ┌─────────────────┐
                                            │  DIAGNOSTICS    │
                                            │   HANDLER       │
                                            └─────────────────┘
```

**Key Patterns:**
- **Monolithic Architecture** - Parser orchestrates entire compilation pipeline
- **Centralized Repository** - Single `SymbolTable` as source of truth
- **Publisher Pattern** - Deferred symbol table population via semantic actions
- **Decorator Pattern** - Composable transformer chains for lexical preprocessing
- **Builder + Fluent Interface** - Safe construction of complex `LexemeInfo` objects

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+

### Build
```bash
mvn clean compile
```

This generates:
- `src/main/java/lexer/Lexer.java` (from `Lexer.flex` via JFlex)
- `src/main/java/parser/Parser.java` (from `Parser.y` via Bison)

### Run Tests
```bash
mvn test
```

### Usage
```java
import utils.SymbolTable;
import utils.DiagnosticsHandler;
import parser.internals.NameMangler;
import lexer.Lexer;
import parser.Parser;

public class Example {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        DiagnosticsHandler diagnostics = new DiagnosticsHandler();
        NameMangler nameMangler = new NameMangler();

        Lexer lexer = new Lexer(symbolTable, diagnostics);
        Parser parser = new Parser(lexer, symbolTable, nameMangler);

        int result = parser.yyparse();  // 0 = success, 1 = error

        if (result == 0) {
            // Access symbolTable for declarations
            // Access diagnostics for warnings
        }
    }
}
```

## Project Structure

```
src/main/java/
├── lexer/
│   ├── Lexer.flex              # JFlex specification
│   ├── internals/              # LexicalPreprocessors, LexicalAnalyzers
│   ├── transformers/           # Transformer chain (Decorator pattern)
│   │   └── hex_resolvers/      # String/WString hex escape resolvers
│   └── semantics/              # Semantic analyzers per literal type
│       ├── numbers/            # Naturals, Integers, Reals, Binary, Octal, Hex
│       ├── strings/            # Strings, WStrings
│       └── utils/              # ReservedWords
├── parser/
│   ├── Parser.y                # Bison grammar specification
│   ├── internals/              # ParsingContext, NameMangler, ContextHandler
│   ├── initializations/        # Polymorphic initialization hierarchy
│   └── utils/                  # Publisher, Factory, DimensionCalculator
└── utils/
    ├── SymbolTable.java        # Centralized symbol repository
    ├── LexemeInfo.java         # Semantic DTO
    ├── DiagnosticsHandler.java # Error/warning collector
    ├── builders/               # LexemeInfoBuilder, Director
    ├── diagnostics/            # Hierarchical diagnostic classes
    └── enums/                  # Type, Subtype, Use, Source
```

## Documentation

| Document | Description |
|----------|-------------|
| [Technical Documentation](doc/documentation.md) | Complete architecture, algorithms, and API reference |
| [Thesis (Spanish)](doc/tesis.md) | Academic thesis with formal analysis |
| [Architecture Diagrams](doc/architecture.md) | PlantUML diagrams for key subsystems |

## Testing

The project includes:
- **Unit tests** - Lexer tokenization, transformer chains, symbol table operations
- **Integration tests** - Array types, struct types, full parsing scenarios

```bash
mvn test                    # All tests
mvn test -Dtest=LexerTokenizationTest  # Specific test
```

## Standards Compliance

Implements the **declaration and initialization subsets** of:
- **IEC 61131-7:2000** - Fuzzy Control Language
- **IEC 61131-3 Annex B** - Common elements (variable declarations, data types)

## Authors

- **Matías Ortiz** - Architecture, parser, symbol table, initialization system
- **Victoriano Etcheverría** - Lexer, semantic analyzers, transformers, diagnostics

## License

Academic project - UNICEN (Universidad Nacional del Centro de la Provincia de Buenos Aires)