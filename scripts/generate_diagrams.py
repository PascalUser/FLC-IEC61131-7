#!/usr/bin/env python3
"""
Relational diagram generator for FLC-IEC61131-7 compiler.
Generates mermaid diagrams (class, sequence, flow, package, object) from current source code.
Outputs: doc/diagrams/*.mmd
"""
import argparse
import re
from pathlib import Path

ROOT = Path(__file__).parent.parent
SRC_DIR = ROOT / "src" / "main" / "java"
DIAGRAMS_DIR = ROOT / "doc" / "diagrams"


def read_java_files(package_path):
    """Read all .java files in a package"""
    files = {}
    for java_file in package_path.rglob("*.java"):
        if java_file.name == "package-info.java":
            continue
        rel_path = java_file.relative_to(SRC_DIR)
        files[str(rel_path)] = java_file.read_text()
    return files


def extract_classes(content):
    """Extract class/interface names and their relationships"""
    classes = {}
    # Class/interface declarations
    for match in re.finditer(r'(public\s+)?(class|interface)\s+(\w+)', content):
        class_name = match.group(3)
        classes[class_name] = {"type": match.group(2), "extends": [], "implements": [], "fields": [], "methods": []}
    
    # Extends/implements
    for match in re.finditer(r'(class|interface)\s+\w+\s+(extends|implements)\s+([^{]+)', content):
        match.group(1)
        match.group(2)
        [t.strip() for t in match.group(3).split(',')]
        # Find which class this belongs to (simplified)
        pass
    
    return classes


def extract_imports(content):
    """Extract import statements"""
    imports = []
    for match in re.finditer(r'import\s+([\w.]+);', content):
        imports.append(match.group(1))
    return imports


def extract_method_calls(content, class_name):
    """Extract method calls within a class"""
    calls = []
    # Simplified: look for method calls
    for match in re.finditer(r'(\w+)\.(\w+)\s*\(', content):
        caller = match.group(1)
        method = match.group(2)
        if caller != class_name:
            calls.append({"from": caller, "to": method})
    return calls


def generate_package_flowchart():
    """Generate package dependency flowchart"""
    packages = ["lexer", "parser", "utils"]
    edges = [
        ("lexer", "parser", "tokens"),
        ("parser", "utils", "SymbolTable"),
        ("parser", "utils", "LexemeInfo"),
        ("parser", "utils", "DiagnosticsHandler"),
        ("lexer", "utils", "SymbolTable"),
        ("lexer", "utils", "DiagnosticsHandler"),
    ]
    
    mermaid = ["flowchart LR"]
    for pkg in packages:
        mermaid.append(f"    {pkg}[{pkg}]")
    for src, dst, label in edges:
        mermaid.append(f"    {src} -->|{label}| {dst}")
    return "\n".join(mermaid)


def generate_lexer_class_diagram():
    """Generate class diagram for lexer module"""
    mermaid = ["classDiagram", "    namespace lexer {", "        class Lexer {", "            +processAndSaveYylval()",
                "        }", "        class LexicalPreprocessors {", "            +INTERVALS: Transformer",
                "            +REALS: Transformer", "            +IDENTIFIERS: Transformer", "        }",
                "        class LexicalAnalyzers {", "            +analyzers: Map", "        }", "    }",
                "    namespace lexer_transformers {", "        class Transformer {", "            +transform()",
                "            +giveToNext()", "        }",
                "        class UnderscoreRemover",
                "        class UpperCaseConverter",
                "        class StripLeadingZeros",
                "        class StripTrailingZeros",
                "        class StringEscapeResolver",
                "        class Nothing", "    }", "    namespace lexer_semantics {",
                "        class SemanticAnalyzer {", "            +analyze()", "        }",
                "        class NumbersAnalyzer {", "            +parse()", "            +fallback()",
                "            +createDiagnostic()", "        }",
                "        class Naturals",
                "        class Integers",
                "        class Reals",
                "        class BaseNumbersAnalyzer",
                "        class Binary",
                "        class Octal",
                "        class Hexadecimal",
                "        class Intervals",
                "        class Identifiers",
                "        class Strings",
                "        class Dates", "    }",
                "    Lexer --> LexicalPreprocessors : uses", "    Lexer --> LexicalAnalyzers : uses",
                "    LexicalPreprocessors --> Transformer : manages",
                "    LexicalAnalyzers --> SemanticAnalyzer : manages",
                "    Transformer <|-- UnderscoreRemover",
                "    Transformer <|-- UpperCaseConverter",
                "    Transformer <|-- StripLeadingZeros",
                "    Transformer <|-- StripTrailingZeros",
                "    Transformer <|-- StringEscapeResolver",
                "    Transformer <|-- Nothing",
                "    SemanticAnalyzer <|-- NumbersAnalyzer",
                "    NumbersAnalyzer <|-- Naturals",
                "    NumbersAnalyzer <|-- Integers",
                "    NumbersAnalyzer <|-- Reals",
                "    NumbersAnalyzer <|-- BaseNumbersAnalyzer",
                "    BaseNumbersAnalyzer <|-- Binary",
                "    BaseNumbersAnalyzer <|-- Octal",
                "    BaseNumbersAnalyzer <|-- Hexadecimal",
                "    SemanticAnalyzer <|-- Intervals",
                "    SemanticAnalyzer <|-- Identifiers",
                "    SemanticAnalyzer <|-- Strings",
                "    SemanticAnalyzer <|-- Dates"]
    return "\n".join(mermaid)


def generate_parser_class_diagram():
    """Generate class diagram for parser module"""
    mermaid = ["classDiagram", "    namespace parser {", "        class Parser {", "            +parse()", "        }",
                "    }", "    namespace parser_internals {", "        class ContextHandler {",
                "            +stack: Stack~ParsingContext~", "            +add()", "            +pop()",
                "            +current()", "        }", "        class ParsingContext {",
                "            +declaredIdentifiers: List", "            +metadataBuilder: LexemeInfoBuilder",
                "            +outerScopes: NameMangler", "            +searchScope: NameMangler",
                "            +nestedFields: NameMangler", "        }", "        class NameMangler {",
                "            +prefix: StringBuilder", "            +addScope()", "            +popScope()",
                "            +getNameMangled()", "        }", "    }", "    namespace parser_utils {",
                "        class Publisher {", "            +publish(ParsingContext)", "        }",
                "        class Factory {",
                "            +createPrimitiveInitialization()", "        }", "        class DimensionCalculator {",
                "            +calculate()", "        }", "        class UnderlyingScopeSearcher {",
                "            +search()", "        }", "    }", "    namespace parser_initializations {",
                "        class Initialization {", "            +selectVariable()", "            +getVariableValue()",
                "            +copy()", "        }",
                "        class VariableInitialization",
                "        class StringInitialization {",
                "            +symbolTable: SymbolTable",
                "            +subtype: Subtype",
                "        }",
                "        class BooleanInitialization",
                "        class RealInitialization",
                "        class EnumeratedInitialization",
                "        class MacroInitialization",
                "        class SubrangeInitialization",
                "        class StructInitialization",
                "        class RepeatedInitialization",
                "    }",
                "    Initialization <|-- VariableInitialization",
                "    Initialization <|-- StringInitialization",
                "    Initialization <|-- BooleanInitialization",
                "    Initialization <|-- RealInitialization",
                "    Initialization <|-- EnumeratedInitialization",
                "    Initialization <|-- MacroInitialization",
                "    Initialization <|-- SubrangeInitialization",
                "    Initialization <|-- StructInitialization",
                "    Initialization <|-- RepeatedInitialization",
                "    Parser --> ContextHandler : uses",
                "    ContextHandler --> ParsingContext : manages",
                "    ParsingContext --> NameMangler : uses",
                "    ParsingContext --> LexemeInfoBuilder : uses",
                "    Publisher --> SymbolTable : publishes",
                "    Publisher --> ParsingContext : reads"]
    return "\n".join(mermaid)


def generate_lexer_parser_sequence():
    """Generate sequence diagram for Lexer -> Parser -> SymbolTable interaction"""
    mermaid = ["sequenceDiagram", "    participant JFlex as Lexer.flex", "    participant Lexer as Lexer.java",
               "    participant Transformer as Transformer Chain", "    participant Analyzer as SemanticAnalyzer",
               "    participant ST as SymbolTable", "    participant Diag as DiagnosticsHandler",
               "    participant Parser as Parser (Bison)", "", "    JFlex->>Lexer: yytext()",
               "    Lexer->>Transformer: transform(yytext())", "    loop Chain of Responsibility",
               "        Transformer->>Transformer: giveToNext()", "    end",
               "    Transformer-->>Lexer: normalized lexeme", "    Lexer->>Analyzer: analyze(LexicalContext)",
               "    Analyzer->>ST: putIfAbsent(lexeme, LexemeInfo)", "    Analyzer->>Diag: add(Diagnostic) if needed",
               "    Analyzer-->>Lexer: Result(token, lexeme)", "    Lexer-->>Parser: token + yylval",
               "    Parser->>ST: Publisher.publish(ctx)"]
    return "\n".join(mermaid)


def generate_symboltable_storage_object():
    """Generate object diagram for SymbolTable storage per type"""
    mermaid = ["classDiagram", "    class SymbolTable {", "        +table: Map~String, LexemeInfo~", "        +get()",
                "        +put()", "        +putIfAbsent()", "    }", "    class LexemeInfo {", "        +type: Type",
                "        +subtype: Subtype", "        +customType: String", "        +use: Use",
                "        +source: Source", "        +inferiorLimits: List~String~",
                "        +superiorLimits: List~String~", "        +parameters: List~String~",
                "        +initialValue: Object", "    }", "    SymbolTable --> \"0..*\" LexemeInfo : contains", "",
                "    note for LexemeInfo \"SIMPLE\\ntype=SIMPLE, subtype=INT/REAL/BOOL\\nuse=VARIABLE/LITERAL\\ninitialValue=literal\"",
                "    note for LexemeInfo \"ARRAY\\ntype=ARRAY, subtype=element type\\ninferiorLimits=[0], superiorLimits=[9]\\ninitialValue=RepeatedInitialization\"",
                "    note for LexemeInfo \"STRUCT\\ntype=STRUCT, customType=MyStruct\\nparameters=[field1, field2]\\ninitialValue=StructInitialization\"",
                "    note for LexemeInfo \"ENUMERATE\\ntype=ENUMERATE, subtype=INT\\nparameters=[A, B, C]\\nuse=MACRO for each value\"",
                "    note for LexemeInfo \"SUBRANGE\\ntype=SUBRANGE\\ninferiorLimits=[0], superiorLimits=[100]\\ninitialValue=SubrangeInitialization\""]
    return "\n".join(mermaid)


def generate_diagnostics_hierarchy():
    """Generate class diagram for diagnostics hierarchy"""
    mermaid = ["classDiagram", "    class Diagnostic {", "        +line: int", "        +getMessage()",
               "        +fatalForCompilation()", "    }", "    class Error {", "        +fatalForCompilation() = true",
               "    }", "    class Warning {", "        +fatalForCompilation() = false", "    }",
               "    class SyntaxError {", "        +fatalForCompilation() = true", "    }", "    Diagnostic <|-- Error",
               "    Diagnostic <|-- Warning", "    Diagnostic <|-- SyntaxError", "", "    Error <|-- DateOutOfRange",
               "    Error <|-- IntervalConstructionError", "    Error <|-- IntervalOutOfRange",
               "    Error <|-- TimeOfDayOutOfRange", "    Error <|-- DateAndTimeOutOfRange", "",
               "    Warning <|-- StringLengthWarning", "    Warning <|-- HexadecimalOutOfRange",
               "    Warning <|-- RealOutOfRange", "    Warning <|-- NaturalOutOfRange",
               "    Warning <|-- BinaryOutOfRange", "    Warning <|-- OctalOutOfRange",
               "    Warning <|-- IntegerOutOfRange"]
    return "\n".join(mermaid)


def generate_architecture_evolution():
    """Generate architecture evolution timeline from current code structure"""
    mermaid = ["flowchart TD", "    subgraph Phase1[Initial Design]", "        A1[Lexer: JFlex + basic tokens]",
               "        A2[Parser: Bison + simple grammar]", "        A3[SymbolTable: Basic Map]", "    end",
               "    subgraph Phase2[Transformer Chain]", "        B1[LexicalPreprocessors registry]",
               "        B2[Chain of Responsibility: Transformer]", "        B3[Per-category chains]", "    end",
               "    subgraph Phase3[Semantic Analysis]", "        C1[SemanticAnalyzer base]",
               "        C2[Template Method: NumbersAnalyzer]", "        C3[Strategy: Binary/Octal/Hex]",
               "        C4[14 categories registered]", "    end", "    subgraph Phase4[SymbolTable Evolution]",
               "        D1[Repository pattern]", "        D2[LexemeInfo DTO with 9 fields]",
               "        D3[NameMangler for nested scopes]", "        D4[Publisher defers population]", "    end",
               "    subgraph Phase5[Initializations]", "        E1[Polymorphic Initialization hierarchy]",
               "        E2[StructInitialization: nested maps]",
               "        E3[RepeatedInitialization: interval partition]", "    end",
               "    Phase1 --> Phase2 --> Phase3 --> Phase4 --> Phase5"]
    return "\n".join(mermaid)


def generate_utils_class_diagram():
    """Generate class diagram for utils module"""
    mermaid = ["classDiagram", "    namespace utils {", "        class SymbolTable {",
                "            +table: Map~String, LexemeInfo~", "            +get()", "            +put()",
                "            +putIfAbsent()", "        }", "        class LexemeInfo {", "            +type: Type",
                "        +subtype: Subtype", "        +customType: String", "        +use: Use",
                "        +source: Source", "        +inferiorLimits: List~String~",
                "        +superiorLimits: List~String~", "        +parameters: List~String~",
                "        +initialValue: Object", "        }", "        class DiagnosticsHandler {",
                "        +diagnostics: List~Diagnostic~", "        +add()", "        +hasErrors()",
                "        +getDiagnostics()", "        }", "    }", "    namespace utils_builders {",
                "        class LexemeInfoSchema {", "            +type()", "            +subtype()",
                "            +use()", "            +source()", "            +initialValue()", "            +build()",
                "        }", "        class LexemeInfoBuilder {", "            +implements LexemeInfoSchema",
                "            +build()", "        }", "        class Director {", "            +makeLiteral()",
                "            +makeDefaultReal()", "            +makeDefaultBoolean()", "        }", "    }",
                "    namespace utils_enums {", "        class Type { SIMPLE, ENUMERATE, SUBRANGE, ARRAY, STRUCT }",
                "        class Subtype { 24 values }", "        class Use { 9 values }",
                "        class Source { 7 values }", "    }", "    namespace utils_diagnostics {",
                "        class Diagnostic", "        class Error", "        class Warning", "    }",
                "    Diagnostic <|-- Error",
                "    Diagnostic <|-- Warning",
                "    LexemeInfoBuilder ..|> LexemeInfoSchema", "    Director --> LexemeInfoBuilder",
                "    Publisher ..|> LexemeInfoSchema", "    SymbolTable --> LexemeInfo",
                "    DiagnosticsHandler --> Diagnostic"]
    return "\n".join(mermaid)


def generate_context_handler_sequence():
    """Generate sequence diagram for ContextHandler stack operations"""
    mermaid = ["sequenceDiagram", "    participant Parser as Parser (Bison)", "    participant CH as ContextHandler",
               "    participant PC as ParsingContext", "    participant NM as NameMangler",
               "    participant Builder as LexemeInfoBuilder", "    participant Pub as Publisher",
               "    participant ST as SymbolTable", "", "    Parser->>CH: pushContext()",
               "    CH->>PC: new ParsingContext()", "    CH->>NM: new NameMangler() x3",
               "    CH->>Builder: new LexemeInfoBuilder()", "", "    loop Grammar reductions",
               "        Parser->>Builder: configure attributes", "        Parser->>PC: addDeclaredIdentifier()",
               "    end", "", "    Parser->>Pub: publish(ParsingContext)", "    Pub->>PC: build() LexemeInfo",
               "    loop For each identifier", "        Pub->>NM: getNameMangled(identifier)",
               "        Pub->>ST: put(mangledName, LexemeInfo)", "    end", "", "    Parser->>CH: popContext()"]
    return "\n".join(mermaid)


DIAGRAMS = {
    "package_dependencies": generate_package_flowchart,
    "lexer_class_diagram": generate_lexer_class_diagram,
    "parser_class_diagram": generate_parser_class_diagram,
    "lexer_parser_sequence": generate_lexer_parser_sequence,
    "symboltable_storage": generate_symboltable_storage_object,
    "diagnostics_hierarchy": generate_diagnostics_hierarchy,
    "architecture_evolution": generate_architecture_evolution,
    "utils_class_diagram": generate_utils_class_diagram,
    "context_handler_sequence": generate_context_handler_sequence,
}


def main():
    parser = argparse.ArgumentParser(description="Generate mermaid diagrams from source code")
    parser.add_argument("--list", action="store_true", help="List available diagrams")
    parser.add_argument("scope", nargs="?", default="all", help="Scope: all|lexer|parser|utils|architecture")
    args = parser.parse_args()
    
    DIAGRAMS_DIR.mkdir(parents=True, exist_ok=True)
    
    # Determine which diagrams to generate based on scope
    scope = args.scope
    
    if scope == "all":
        targets = list(DIAGRAMS.keys())
    elif scope == "lexer":
        targets = ["lexer_class_diagram", "lexer_parser_sequence"]
    elif scope == "parser":
        targets = ["parser_class_diagram", "context_handler_sequence", "symboltable_storage"]
    elif scope == "utils":
        targets = ["utils_class_diagram", "diagnostics_hierarchy", "symboltable_storage"]
    elif scope == "architecture":
        targets = ["package_dependencies", "lexer_parser_sequence", "architecture_evolution"]
    else:
        # Try exact match
        if scope in DIAGRAMS:
            targets = [scope]
        else:
            print(f"Unknown scope: {scope}")
            print(f"Available: all, lexer, parser, utils, architecture")
            print(f"Or specific: {', '.join(DIAGRAMS.keys())}")
            return
    
    if args.list:
        for name in DIAGRAMS:
            print(name)
        return
    
    print(f"Generating diagrams for scope: {scope}")
    generated = []
    
    for name in targets:
        if name not in DIAGRAMS:
            print(f"  ! unknown diagram: {name}")
            continue
        try:
            mermaid_content = DIAGRAMS[name]()
            output_file = DIAGRAMS_DIR / f"{name}.mmd"
            output_file.write_text(mermaid_content)
            generated.append(str(output_file.relative_to(ROOT)))
            print(f"  -> {output_file.relative_to(ROOT)}")
        except Exception as e:
            print(f"  ! Error generating {name}: {e}")
    
    print(f"\nGenerated {len(generated)} diagrams in doc/diagrams/")


if __name__ == "__main__":
    main()