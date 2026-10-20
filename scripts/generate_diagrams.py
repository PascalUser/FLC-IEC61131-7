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
    packages = ["lexer", "parser", "parser_publishers", "parser_facades", "parser_initializations", "utils"]
    edges = [
        ("lexer", "parser", "tokens"),
        ("parser", "utils", "SymbolTable"),
        ("parser", "utils", "LexemeInfo"),
        ("parser", "utils", "DiagnosticsHandler"),
        ("parser", "parser_publishers", "publishers"),
        ("parser_publishers", "utils", "Director"),
        ("parser_publishers", "parser_initializations", "Factory"),
        ("parser", "parser_facades", "facades"),
        ("parser_facades", "utils", "DimensionCalculator"),
        ("parser_facades", "utils", "RangesParser"),
        ("parser_facades", "utils", "MacrosPublisher"),
        ("lexer", "utils", "SymbolTable"),
        ("lexer", "utils", "DiagnosticsHandler"),
    ]

    mermaid = ["flowchart LR"]
    for pkg in packages:
        mermaid.append(f"    {pkg}[{pkg}]")
    for src, dst, label in edges:
        mermaid.append(f"    {src} -->|{label}| {dst}")
    return "\n".join(mermaid)


def generate_lexer_package_flowchart():
    """Import dependencies between lexer packages, read from Java sources."""
    sources = read_java_files(SRC_DIR / "lexer")
    packages = {"lexer", "lexer.internals", "lexer.semantics",
                "lexer.semantics.numbers", "lexer.semantics.numbers.bases",
                "lexer.semantics.strings", "lexer.semantics.utils",
                "lexer.transformers", "lexer.transformers.hex_resolvers",
                "lexer.transformers.utils", "parser", "utils", "utils.builders",
                "utils.enums", "utils.diagnostics"}
    edges = set()
    for path, content in sources.items():
        origin = ".".join(Path(path).parts[:-1])
        for imp, wildcard in re.findall(r'^\s*import\s+([\w.]+?)(\.\*)?;', content, re.MULTILINE):
            destination = imp if wildcard else imp.rsplit(".", 1)[0]
            if origin in packages and destination in packages and origin != destination:
                edges.add((origin, destination))
    used = sorted({p for edge in edges for p in edge})
    ids = {pkg: pkg.replace(".", "_") for pkg in used}
    lines = ["flowchart LR"]
    lines += [f'    {ids[pkg]}["{pkg}"]' for pkg in used]
    lines += [f"    {ids[src]} --> {ids[dst]}" for src, dst in sorted(edges)]
    return "\n".join(lines)


def generate_lexer_class_diagram():
    """Selected class/interface inheritance and registry uses verified in Java/Flex."""
    sources = read_java_files(SRC_DIR / "lexer")
    selected = {
        "lexer": ["Lexer"],
        "lexer_internals": ["LexicalPreprocessors", "LexicalAnalyzers"],
        "lexer_transformers": ["Transformer", "UnderscoreRemover", "UpperCaseConverter",
                               "StripLeadingZeros", "StripTrailingZeros", "StripBaseNumberLeadingZeros",
                               "StringEscapeResolver", "Nothing",
                               "OmitLeadingZeroMagnitudes", "OmitTrailingZeroMagnitudes",
                               "OmitLeadingZerosInMagnitudes", "OmitTrailingZerosInMagnitudes"],
        "lexer_transformers_hex_resolvers": ["HexResolver", "StringHexResolver", "WStringHexResolver"],
        "lexer_transformers_utils": ["ExponentFinder"],
        "lexer_semantics": ["SemanticAnalyzer", "Identifiers", "Intervals", "Dates",
                            "DayTimes", "DateAndDayTimes"],
        "lexer_semantics_numbers": ["NumbersAnalyzer", "Naturals", "Integers", "Reals"],
        "lexer_semantics_numbers_bases": ["BaseNumbersAnalyzer", "Binary", "Octal", "Hexadecimal"],
        "lexer_semantics_strings": ["StringsAnalyzer", "Strings", "WStrings"],
        "lexer_semantics_utils": ["ReservedWords"],
    }
    lines = ["classDiagram"]
    known = {name for group in selected.values() for name in group}
    for namespace, names in selected.items():
        lines.append(f"    namespace {namespace} {{")
        for name in names:
            matches = [(path, content) for path, content in sources.items()
                       if Path(path).stem == name]
            if len(matches) != 1 or not re.search(r'\b(class|interface)\s+' + name + r'\b', matches[0][1]):
                raise ValueError(f"Missing lexer type: {name}")
            lines.append(f"        class {name}")
        lines.append("    }")
    for path, content in sorted(sources.items()):
        name = Path(path).stem
        if name not in known:
            continue
        match = re.search(r'\b(?:class|interface)\s+' + re.escape(name)
                          + r'\s+(?:extends|implements)\s+(\w+)', content)
        if match and match.group(1) in known:
            lines.append(f"    {match.group(1)} <|-- {name}")
    flex = (SRC_DIR / "lexer" / "Lexer.flex").read_text()
    for registry in ("LexicalPreprocessors", "LexicalAnalyzers"):
        if re.search(r'private\s+' + registry + r'\s+', flex):
            lines.append(f"    Lexer --> {registry} : uses")
    return "\n".join(lines)


def generate_lexer_intervals_object():
    """Object chain instantiated for INTERVALS in LexicalPreprocessors.java."""
    source = (SRC_DIR / "lexer" / "internals" / "LexicalPreprocessors.java").read_text()
    match = re.search(r'public static final Transformer\s+INTERVALS\s*=\s*(.*?);', source, re.DOTALL)
    if not match:
        raise ValueError("INTERVALS chain not found")
    types = re.findall(r'\bnew\s+(\w+)\s*\(', match.group(1))
    if not types or 'null' not in match.group(1):
        raise ValueError("Invalid INTERVALS chain")
    lines = ["flowchart LR", '    root["LexicalPreprocessors.INTERVALS"] --> node0["' + types[0] + '"]']
    lines += [f'    node{i-1} --> node{i}["{name}"]' for i, name in enumerate(types[1:], 1)]
    lines.append(f'    node{len(types)-1} --> endNode["null"]')
    return "\n".join(lines)


def generate_parser_class_diagram():
    """Generate class diagram for parser module (current Publisher/Director architecture)"""
    mermaid = ["classDiagram",
                "    namespace parser {",
                "        class Parser {",
                "            +parse()",
                "        }",
                "    }",
                "    namespace parser_publishers {",
                "        class Publisher {",
                "            <<interface>>",
                "            +setDeclarationScope(String)",
                "            +publish()",
                "        }",
                "        class LexemeInfoSchema {",
                "            <<interface>>",
                "            +type(Type)",
                "            +subtype(Subtype)",
                "            +customType(String)",
                "            +source(Source)",
                "            +use(Use)",
                "            +inferiorLimits(List~String~)",
                "            +superiorLimits(List~String~)",
                "            +parameters(List~String~)",
                "            +initialValue(Object)",
                "        }",
                "        class DeclarationGroup {",
                "            +declarations: List~Declaration~",
                "            +publish()",
                "            +setDeclarationScope(String)",
                "        }",
                "        class Declaration {",
                "            #builder: LexemeInfoBuilder",
                "            #declarationScope: String",
                "            +type(Type)",
                "            +subtype(Subtype)",
                "            +customType(String)",
                "            +source(Source)",
                "            +use(Use)",
                "            +inferiorLimits(List~String~)",
                "            +superiorLimits(List~String~)",
                "            +parameters(List~String~)",
                "            +initialValue(Object)",
                "            +publish()",
                "        }",
                "        class MultipleDeclaration {",
                "            +identifiers: List~String~",
                "            +identifiers(List~String~)",
                "            +publish()",
                "        }",
                "        class SingleDeclaration {",
                "            +identifier: String",
                "            +identifier(String)",
                "            +publish()",
                "        }",
                "        class DeclarationBlock {",
                "            +addDeclaration(Declaration)",
                "        }",
                "        class StructureBlock {",
                "            +addField(SingleDeclaration)",
                "            +getStructInitializationInfo() List~PathInitializationInfo~",
                "        }",
                "        class StructureDeclaration {",
                "            +structBlock: StructureBlock",
                "            +identifier(String)",
                "            +publish()",
                "        }",
                "        class AnnonymousEnumeratedVariable {",
                "            +publish()",
                "        }",
                "        class AnnonymousEnumeratedField {",
                "            +publish()",
                "        }",
                "        class EnumeratedTypeDeclaration {",
                "            +publish()",
                "        }",
                "    }",
                "    namespace parser_facades {",
                "        class DimensionCalculator {",
                "            +calculate(List~String~, List~String~) int",
                "        }",
                "        class RangesParser {",
                "            +parse(List~String~, List~String~, List~String~)",
                "        }",
                "        class MacrosPublisher {",
                "            +publish(SymbolTable, List~String~, String)",
                "        }",
                "        class UnderlyingScopeSearcher {",
                "            +search(SymbolTable, String) String",
                "        }",
                "    }",
                "    namespace parser_initializations {",
                "        class Factory {",
                "            +createPrimitiveInitialization(SymbolTable, Subtype) Initialization",
                "        }",
                "        class Initialization {",
                "            <<interface>>",
                "            +find(String) Optional~Initialization~",
                "            +variableValue() String",
                "            +copy() Initialization",
                "        }",
                "        class NodeInitialization {",
                "            <<abstract>>",
                "            +put(String, Initialization) Initialization",
                "            +find(String) Optional~Initialization~",
                "            #breakStringPath(String) String[]",
                "        }",
                "        class LeafInitialization {",
                "            <<abstract>>",
                "            +find(String) Optional~Initialization~",
                "            +copy() Initialization",
                "        }",
                "    }",
                "    namespace parser_initializations_leafs {",
                "        class VariableInitialization",
                "        class EnumeratedInitialization",
                "        class MacroInitialization",
                "        class SubrangeInitialization",
                "        class DefaultInitialization",
                "        class LeafInitialization",
                "    }",
                "    namespace parser_initializations_nodes {",
                "        class StructInitialization",
                "        class RepeatedInitialization",
                "        class NodeInitialization",
                "    }",
                "    namespace utils {",
                "        class SymbolTable {",
                "            +table: Map~String, LexemeInfo~",
                "            +get()",
                "            +put()",
                "            +putIfAbsent()",
                "        }",
                "        class LexemeInfo {",
                "            +type: Type",
                "            +subtype: Subtype",
                "            +customType: String",
                "            +use: Use",
                "            +source: Source",
                "            +inferiorLimits: List~String~",
                "            +superiorLimits: List~String~",
                "            +parameters: List~String~",
                "            +initialValue: Object",
                "        }",
                "        class LexemeInfoBuilder {",
                "            +type(Type)",
                "            +subtype(Subtype)",
                "            +customType(String)",
                "            +source(Source)",
                "            +use(Use)",
                "            +inferiorLimits(List~String~)",
                "            +superiorLimits(List~String~)",
                "            +parameters(List~String~)",
                "            +initialValue(Object)",
                "            +build() LexemeInfo",
                "        }",
                "        class Director {",
                "            +makePrimitive(LexemeInfoSchema, SymbolTable, Subtype)",
                "            +makeCustom(LexemeInfoSchema, SymbolTable, String)",
                "            +makeMacro(LexemeInfoSchema, SymbolTable, Integer)",
                "            +makeEnumerated(LexemeInfoSchema, List~String~)",
                "            +makeStructure(LexemeInfoSchema, StructureBlock)",
                "            +makeArray(LexemeInfoSchema, SymbolTable, ArrayMetadata)",
                "            +makeLiteral(LexemeInfoSchema)",
                "            +makeDefaultInteger()",
                "            +makeDefaultReal()",
                "            +makeDefaultBoolean()",
                "            +makeDefaultString()",
                "            +makeDefaultWString()",
                "        }",
                "    }",
                "    DeclarationGroup ..|> Publisher",
                "    Declaration ..|> Publisher",
                "    Declaration ..|> LexemeInfoSchema",
                "    DeclarationGroup ..|> LexemeInfoSchema",
                "    MultipleDeclaration --|> Declaration",
                "    SingleDeclaration --|> Declaration",
                "    DeclarationBlock --|> DeclarationGroup",
                "    StructureBlock --|> DeclarationGroup",
                "    StructureDeclaration --|> SingleDeclaration",
                "    AnnonymousEnumeratedVariable --|> MultipleDeclaration",
                "    AnnonymousEnumeratedField --|> SingleDeclaration",
                "    EnumeratedTypeDeclaration --|> SingleDeclaration",
                "    Initialization <|.. NodeInitialization",
                "    Initialization <|.. LeafInitialization",
                "    LeafInitialization <|-- VariableInitialization",
                "    LeafInitialization <|-- EnumeratedInitialization",
                "    LeafInitialization <|-- MacroInitialization",
                "    LeafInitialization <|-- SubrangeInitialization",
                "    LeafInitialization <|-- DefaultInitialization",
                "    NodeInitialization <|-- StructInitialization",
                "    NodeInitialization <|-- RepeatedInitialization",
                "    Factory ..> DefaultInitialization : creates",
                "    Parser --> Declaration : uses",
                "    Parser --> Director : uses",
                "    Parser --> Factory : uses",
                "    Parser --> DimensionCalculator : uses",
                "    Parser --> RangesParser : uses",
                "    Parser --> MacrosPublisher : uses",
                "    Director ..> Factory : uses",
                "    Director ..> LexemeInfoBuilder : configures",
                "    MultipleDeclaration ..> LexemeInfoBuilder : uses",
                "    SingleDeclaration ..> LexemeInfoBuilder : uses",
                "    StructureDeclaration ..> StructureBlock : uses",
                "    AnnonymousEnumeratedVariable ..> MacrosPublisher : uses",
                "    AnnonymousEnumeratedField ..> MacrosPublisher : uses",
                "    EnumeratedTypeDeclaration ..> MacrosPublisher : uses"]
    return "\n".join(mermaid)


def generate_lexer_parser_sequence():
    """Scanner to parser flow checked against Lexer.flex and SemanticAnalyzer.java."""
    flex = (SRC_DIR / "lexer" / "Lexer.flex").read_text()
    semantics = (SRC_DIR / "lexer" / "semantics" / "SemanticAnalyzer.java").read_text()
    numbers = (SRC_DIR / "lexer" / "semantics" / "numbers" / "NumbersAnalyzer.java").read_text()
    strings = (SRC_DIR / "lexer" / "semantics" / "strings" / "StringsAnalyzer.java").read_text()
    for snippet, text in [("transformer.transform(yytext())", flex),
                          ("analyzer.analyze(lexicalContext)", flex),
                          ("this.yylval = result.lexeme", flex),
                          ("return result.token", flex),
                          ("final String lexeme;", semantics),
                          ("final int token;", semantics),
                          ("putIfAbsent(parsed.lexeme", numbers),
                          ("putIfAbsent(lexeme", strings)]:
        if snippet not in text:
            raise ValueError(f"Lexer sequence source changed: {snippet}")
    return "\n".join([
        "sequenceDiagram",
        "    participant P as Parser (Bison)",
        "    participant L as Lexer.java",
        "    participant T as Transformer",
        "    participant A as SemanticAnalyzer",
        "    participant S as SymbolTable",
        "    participant D as DiagnosticsHandler",
        "    P->>L: yylex()",
        "    L->>T: transform(yytext())",
        "    loop Transformers encadenados",
        "        T->>T: giveToNext(lexeme)",
        "    end",
        "    T-->>L: preprocessedLexeme",
        "    L->>A: analyze(LexicalContext)",
        "    opt Advertencia o error semantico",
        "        A->>D: add(Diagnostic)",
        "    end",
        "    opt Literal valido o fallback numerico",
        "        A->>S: putIfAbsent(lexeme, LexemeInfo)",
        "    end",
        "    A-->>L: Result(lexeme, token)",
        "    L->>L: yylval = result.lexeme",
        "    alt token != YYerror",
        "        L-->>P: token",
        "        P->>L: getLVal()",
        "        L-->>P: yylval",
        "    else token == YYerror",
        "        L->>L: yybegin(YYINITIAL)",
        "    end",
    ])


def generate_symboltable_storage_object():
    """Generate object diagram for SymbolTable storage per type"""
    mermaid = ["classDiagram", "    class SymbolTable {", "        +table: Map~String, LexemeInfo~", "        +get()",
                "        +put()", "        +putIfAbsent()", "    }", "    class LexemeInfo {", "        +type: Type",
                "        +subtype: Subtype", "        +customType: String", "        +use: Use",
                "        +source: Source", "        +inferiorLimits: List~String~",
                "        +superiorLimits: List~String~", "        +parameters: List~String~",
                "        +initialValue: Object", "    }", "    SymbolTable --> \"0..*\" LexemeInfo : contains", "",
                # Notes reflect metadataBuilder() calls in src/main/java/parser/Parser.y
                "    note for LexemeInfo \"SIMPLE\\ntype=SIMPLE, subtype=REAL/BOOL/STRING/WSTRING/CUSTOM\\nuse=VARIABLE/TYPE/LITERAL\\ninitialValue=Initialization\"",
                "    note for LexemeInfo \"ARRAY\\ntype=ARRAY, subtype=element type\\ninferiorLimits=[0], superiorLimits=[9]\\ninitialValue=RepeatedInitialization\"",
                "    note for LexemeInfo \"STRUCT\\ntype=STRUCT, subtype=NONE\\nparameters=[field1, field2]\\ninitialValue=parser.initializations.nodes.StructInitialization\"",
                "    note for LexemeInfo \"ENUMERATE\\ntype=ENUMERATE, subtype=INT\\nparameters=[A, B, C]\\nuse=MACRO for each value\"",
                "    note for LexemeInfo \"SUBRANGE\\ntype=SUBRANGE\\ninferiorLimits=[0], superiorLimits=[100]\\ninitialValue=SubrangeInitialization\""]
    return "\n".join(mermaid)


def generate_diagnostics_hierarchy():
    """Generate class diagram for diagnostics hierarchy"""
    mermaid = ["classDiagram", "    class Diagnostic {", "        +line: int", "        +getMessage()",
               "        +fatalForCompilation()", "    }", "    class Error {", "        +fatalForCompilation() = true",
               "    }", "    class Warning {", "        +fatalForCompilation() = false", "    }",
               "    class SyntaxError {", "        +fatalForCompilation() = true", "    }", "    Diagnostic <|-- Error",
               "    Diagnostic <|-- Warning", "    Error <|-- SyntaxError", "", "    Error <|-- DateOutOfRange",
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
               "        E2[parser.initializations.nodes.StructInitialization: nested maps]",
               "        E3[RepeatedInitialization: interval partition]", "    end",
               "    Phase1 --> Phase2 --> Phase3 --> Phase4 --> Phase5"]
    return "\n".join(mermaid)


def generate_utils_class_diagram():
    """Generate class diagram for utils module"""
    mermaid = """classDiagram
    namespace utils {
        class SymbolTable {
            -table: Map~String, LexemeInfo~
            +get()
            +put()
            +putIfAbsent()
            +size()
        }
        class LexemeInfo {
            +type: Type
            +subtype: Subtype
            +customType: String
            +use: Use
            +source: Source
            +inferiorLimits: List~String~
            +superiorLimits: List~String~
            +parameters: List~String~
            +initialValue: Object
        }
        class DiagnosticsHandler {
            -diagnostics: List~Diagnostic~
            -hasErrors: boolean
            +add()
            +hasErrors()
            +getDiagnostics()
        }
    }
    namespace utils_builders {
        class LexemeInfoSchema {
            +type()
            +subtype()
            +customType()
            +source()
            +use()
            +inferiorLimits()
            +superiorLimits()
            +parameters()
            +initialValue()
        }
        class LexemeInfoBuilder {
            +build()
        }
        class Director {
            +makeLiteral()
            +makeType()
            +makeMacro()
            +makeDefaultInteger()
            +makeDefaultReal()
            +makeDefaultBoolean()
            +makeDefaultString()
            +makeDefaultWString()
        }
    }
    namespace utils_enums {
        class Type { 6 values }
        class Subtype { 24 values }
        class Use { 9 values }
        class Source { 7 values }
    }
    namespace utils_diagnostics {
        class Diagnostic
        class Error
        class Warning
        class SyntaxError
    }
    Diagnostic <|-- Error
    Diagnostic <|-- Warning
    Error <|-- SyntaxError
    LexemeInfoBuilder ..|> LexemeInfoSchema
    Director ..> LexemeInfoSchema
    SymbolTable --> LexemeInfo
    DiagnosticsHandler --> Diagnostic"""
    return mermaid


def generate_publisher_sequence():
    """Generate sequence diagram for Publisher → SymbolTable flow"""
    mermaid = ["sequenceDiagram",
               "    participant Parser as Parser (Bison)",
               "    participant Pub as Publisher (Declaration*)",
               "    participant Builder as LexemeInfoBuilder",
               "    participant Director as Director",
               "    participant ST as SymbolTable",
               "    participant MacroPub as MacrosPublisher",
               "    participant Factory as Factory",
               "",
               "    Parser->>Builder: new LexemeInfoBuilder()",
               "    Parser->>Director: makePrimitive/makeCustom/makeArray/etc.",
               "    Director->>Factory: createPrimitiveInitialization()",
               "    Factory-->>Director: DefaultInitialization",
               "    Director->>Builder: configure type, subtype, initialValue",
               "    Parser->>Pub: new Declaration*(symbolTable, builder)",
               "    Pub->>Pub: identifiers(list) / identifier(name)",
               "    Pub->>Pub: setDeclarationScope(programName)",
               "    Pub->>Pub: source/use/type/subtype/limits/params",
               "    Pub->>ST: put(key, builder.build())",
               "",
               "    alt Enum declaration",
               "        Pub->>MacroPub: publish(symbolTable, macroNames, prefix)",
               "        MacroPub->>Director: makeMacro(builder, symbolTable, ordinal)",
               "        MacroPub->>ST: put(prefix + macroName, builder.build())",
               "    end"]
    return "\n".join(mermaid)


DIAGRAMS = {
    "package_dependencies": generate_package_flowchart,
    "lexer_package_flowchart": generate_lexer_package_flowchart,
    "lexer_class_diagram": generate_lexer_class_diagram,
    "lexer_intervals_object": generate_lexer_intervals_object,
    "parser_class_diagram": generate_parser_class_diagram,
    "lexer_parser_sequence": generate_lexer_parser_sequence,
    "symboltable_storage": generate_symboltable_storage_object,
    "diagnostics_hierarchy": generate_diagnostics_hierarchy,
    "architecture_evolution": generate_architecture_evolution,
    "utils_class_diagram": generate_utils_class_diagram,
    "publisher_sequence": generate_publisher_sequence,
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
        targets = ["lexer_package_flowchart", "lexer_class_diagram", "lexer_intervals_object", "lexer_parser_sequence"]
    elif scope == "parser":
        targets = ["parser_class_diagram", "publisher_sequence", "symboltable_storage"]
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
