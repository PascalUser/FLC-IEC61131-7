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


def generate_parser_package_flowchart():
    """Import dependencies between parser subpackages and their direct consumers."""
    sources = read_java_files(SRC_DIR / "parser")
    packages = {
        "parser", "parser.publishers", "parser.facades", "parser.initializations",
        "parser.initializations.leafs", "parser.initializations.nodes",
        "parser.metadata", "parser.tree", "parser.utils",
        "ast", "ast.ruleblock", "ast.ruleblock.operators",
        "utils", "utils.builders", "utils.enums",
    }
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


def generate_parser_class_diagram():
    """Class diagram of publishers, facades, initializations, metadata, tree, and helpers."""
    sources = read_java_files(SRC_DIR / "parser")
    for extra in ("utils", "ast/ruleblock"):
        for java_file in (SRC_DIR / extra).rglob("*.java"):
            if java_file.name != "package-info.java":
                sources[str(java_file.relative_to(SRC_DIR))] = java_file.read_text()

    selected = {
        "parser": ["Parser"],
        "parser_publishers": [
            "Publisher", "Declaration", "DeclarationGroup", "DeclarationBlock",
            "MultipleDeclaration", "SingleDeclaration", "ComponentDeclaration",
            "StructureDeclaration", "StructureBlock", "TermBlock",
            "EnumeratedTypeDeclaration", "AnnonymousEnumeratedVariable",
            "AnnonymousEnumeratedField",
        ],
        "parser_facades": [
            "DimensionCalculator", "IdentifierParser", "MacrosPublisher",
            "MembershipFunctionClassifier", "RangesParser", "RuleblockASTBuilder",
        ],
        "parser_initializations": ["Initialization", "Factory"],
        "parser_initializations_leafs": [
            "LeafInitialization", "DefaultInitialization", "VariableInitialization",
            "EnumeratedInitialization", "SubrangeInitialization", "MacroInitialization",
        ],
        "parser_initializations_nodes": [
            "NodeInitialization", "StructInitialization", "RepeatedInitialization",
        ],
        "parser_metadata": [
            "FunctionBlockMetadata", "PrimitiveMetadata", "ArrayMetadata",
            "StringMetadata", "EnumeratedMetadata", "SubrangeMetadata",
            "StructureMetadata", "MacroMetadata", "CustomMetadata", "TermMetadata",
            "FuzzifierMetadata", "DefuzzifierMetadata", "PragmaMetadata",
        ],
        "parser_tree": ["TreeNode", "Label"],
        "parser_utils": [
            "ParsedIdentifier", "PathInitializationInfo", "ConditionParsingContext",
            "ArrayParsingContext", "MembershipFunctionShape",
        ],
        "utils": ["SymbolTable", "LexemeInfo"],
        "utils_builders": ["LexemeInfoMutator", "LexemeInfoView", "LexemeInfoBuilder", "Director"],
        "ast_ruleblock": ["RuleBlockNode"],
        "ast_ruleblock_operators": ["Operator"],
    }

    members = {
        "Parser": [
            "<<Bison 3.8.2>>",
            "+Parser(Lexer, SymbolTable)",
            "+parse() boolean",
            "+getNumberOfErrors() int",
        ],
        "Publisher": [
            "<<interface>>",
            "+setDeclarationScope(String)",
            "+publish()",
            "+keys() List~String~",
        ],
        "Declaration": [
            "<<abstract>>",
            "#declarationScope String",
            "#builder LexemeInfoBuilder",
            "+setDeclarationScope(String)",
            "+publish()",
            "+keys() List~String~",
        ],
        "DeclarationGroup": [
            "#declarations List~Declaration~",
            "+publish()",
            "+keys() List~String~",
        ],
        "DeclarationBlock": ["+addDeclaration(Declaration)"],
        "MultipleDeclaration": [
            "#identifiers List~String~",
            "+identifiers(List~String~)",
            "+publish()",
            "+keys() List~String~",
        ],
        "SingleDeclaration": [
            "#identifier String",
            "+identifier(String)",
            "+publish()",
            "+keys() List~String~",
        ],
        "ComponentDeclaration": ["+source(Source)", "+publish()"],
        "StructureDeclaration": ["+identifier(String)", "+publish()"],
        "StructureBlock": ["+addField(SingleDeclaration)", "+getInitializationInfo() List~PathInitializationInfo~"],
        "TermBlock": ["+addTerm(SingleDeclaration)", "+getTermNames() List~String~"],
        "EnumeratedTypeDeclaration": ["+publish()"],
        "AnnonymousEnumeratedVariable": ["+publish()"],
        "AnnonymousEnumeratedField": ["+publish()"],
        "DimensionCalculator": ["+calculate(List~String~, List~String~) int"],
        "IdentifierParser": ["+parse(String, String) ParsedIdentifier"],
        "MacrosPublisher": ["+publish(SymbolTable, List~String~, String)"],
        "MembershipFunctionClassifier": ["+classify(List~String~, List~String~) MembershipFunctionShape"],
        "RangesParser": ["+parse(List~String~, List~String~, List~String~)"],
        "RuleblockASTBuilder": ["+build(TreeNode, List~Operator~) RuleBlockNode"],
        "Initialization": [
            "<<interface>>",
            "+find(String) Optional~Initialization~",
            "+variableValue() String",
            "+copy() Initialization",
        ],
        "Factory": ["+createPrimitiveInitialization(SymbolTable, Subtype) DefaultInitialization"],
        "LeafInitialization": ["<<abstract>>", "+find(String) Optional~Initialization~"],
        "DefaultInitialization": [
            "+bool(SymbolTable) DefaultInitialization",
            "+real(SymbolTable) DefaultInitialization",
            "+integer(SymbolTable) DefaultInitialization",
            "+string(SymbolTable) DefaultInitialization",
            "+wstring(SymbolTable) DefaultInitialization",
        ],
        "VariableInitialization": ["+variableValue() String"],
        "EnumeratedInitialization": ["+variableValue() String"],
        "SubrangeInitialization": ["+variableValue() String"],
        "MacroInitialization": ["+variableValue() String"],
        "NodeInitialization": ["<<abstract>>", "+put(String, Initialization) Initialization"],
        "StructInitialization": ["+put(String, Initialization) Initialization", "+find(String) Optional~Initialization~"],
        "RepeatedInitialization": ["+put(String, Initialization) Initialization", "+find(String) Optional~Initialization~"],
        "FunctionBlockMetadata": [],
        "PrimitiveMetadata": [],
        "ArrayMetadata": [],
        "StringMetadata": [],
        "EnumeratedMetadata": [],
        "SubrangeMetadata": [],
        "StructureMetadata": [],
        "MacroMetadata": [],
        "CustomMetadata": [],
        "TermMetadata": [],
        "FuzzifierMetadata": [],
        "DefuzzifierMetadata": [],
        "PragmaMetadata": [],
        "TreeNode": ["+label() Label", "+symbolReference() String", "+children() List~TreeNode~"],
        "ParsedIdentifier": ["+mangled() String", "+scope() String", "+name() String"],
        "PathInitializationInfo": ["+name() String", "+initialValue() Initialization"],
        "ConditionParsingContext": ["+addOperator(Label)", "+addOperand(TreeNode)", "+getConditionNode() TreeNode"],
        "ArrayParsingContext": ["+getMaxValue() int", "+getPathsInfo() List~PathInitializationInfo~"],
        "MembershipFunctionShape": ["+subtype() Subtype", "+parameters() List~String~"],
        "SymbolTable": ["+put(String, LexemeInfo) LexemeInfo", "+putIfAbsent(String, LexemeInfo) LexemeInfo", "+get(String) LexemeInfo", "+size() int"],
        "LexemeInfoMutator": ["<<interface>>", "+type(Type)", "+subtype(Subtype)", "+initialValue(Object)"],
        "LexemeInfoView": ["<<interface>>", "+type() Type", "+subtype() Subtype", "+initialValue() Object"],
        "LexemeInfoBuilder": ["+build() LexemeInfo"],
        "Director": [
            "+makeWithMetadata(LexemeInfoMutator, LexemeInfoView)",
            "+makeLiteral(LexemeInfoMutator)",
            "+makeDefaultInteger(LexemeInfoMutator) String",
            "+makeDefaultReal(LexemeInfoMutator) String",
            "+makeDefaultBoolean(LexemeInfoMutator) String",
            "+makeDefaultString(LexemeInfoMutator) String",
            "+makeDefaultWString(LexemeInfoMutator) String",
        ],
    }

    for namespace, names in selected.items():
        for name in names:
            matches = [(path, content) for path, content in sources.items() if Path(path).stem == name]
            if len(matches) != 1 or not re.search(r'\b(class|interface|enum)\s+' + name + r'\b', matches[0][1]):
                raise ValueError(f"Missing parser type: {namespace}.{name}")

    lines = ["classDiagram"]
    for namespace, names in selected.items():
        lines.append(f"    namespace {namespace} {{")
        for name in names:
            body = members.get(name)
            if body:
                lines.append(f"        class {name} {{")
                for line in body:
                    if line.startswith("<<") and line.endswith(">>"):
                        inner_text = line[2:-2]
                        sanitized = re.sub(r'[^a-zA-Z0-9_]', '_', inner_text)
                        lines.append(f"            <<{sanitized}>>")
                    else:
                        safe_line = line.replace("<", "~").replace(">", "~")
                        lines.append(f"            {safe_line}")
                lines.append("        }")
            else:
                lines.append(f"        class {name}")
        lines.append("    }")

    lines += [
        "    utils_builders_LexemeInfoMutator <|-- parser_publishers_Publisher",
        "    parser_publishers_Publisher <|.. parser_publishers_Declaration",
        "    parser_publishers_Publisher <|.. parser_publishers_DeclarationGroup",
        "    parser_publishers_Declaration <|-- parser_publishers_MultipleDeclaration",
        "    parser_publishers_Declaration <|-- parser_publishers_SingleDeclaration",
        "    parser_publishers_DeclarationGroup <|-- parser_publishers_DeclarationBlock",
        "    parser_publishers_DeclarationGroup <|-- parser_publishers_StructureBlock",
        "    parser_publishers_DeclarationGroup <|-- parser_publishers_TermBlock",
        "    parser_publishers_SingleDeclaration <|-- parser_publishers_ComponentDeclaration",
        "    parser_publishers_SingleDeclaration <|-- parser_publishers_StructureDeclaration",
        "    parser_publishers_SingleDeclaration <|-- parser_publishers_EnumeratedTypeDeclaration",
        "    parser_publishers_SingleDeclaration <|-- parser_publishers_AnnonymousEnumeratedField",
        "    parser_publishers_MultipleDeclaration <|-- parser_publishers_AnnonymousEnumeratedVariable",
        "    parser_initializations_Initialization <|.. parser_initializations_leafs_LeafInitialization",
        "    parser_initializations_Initialization <|.. parser_initializations_nodes_NodeInitialization",
        "    parser_initializations_leafs_LeafInitialization <|-- parser_initializations_leafs_DefaultInitialization",
        "    parser_initializations_leafs_LeafInitialization <|-- parser_initializations_leafs_VariableInitialization",
        "    parser_initializations_leafs_LeafInitialization <|-- parser_initializations_leafs_EnumeratedInitialization",
        "    parser_initializations_leafs_LeafInitialization <|-- parser_initializations_leafs_SubrangeInitialization",
        "    parser_initializations_leafs_LeafInitialization <|-- parser_initializations_leafs_MacroInitialization",
        "    parser_initializations_nodes_NodeInitialization <|-- parser_initializations_nodes_StructInitialization",
        "    parser_initializations_nodes_NodeInitialization <|-- parser_initializations_nodes_RepeatedInitialization",
        "    utils_builders_LexemeInfoMutator <|.. utils_builders_LexemeInfoBuilder",
        "    utils_builders_LexemeInfoView <|.. utils_builders_LexemeInfoBuilder",
        "    utils_SymbolTable --> utils_LexemeInfo : stores",
        "    utils_builders_LexemeInfoBuilder ..> utils_LexemeInfo : builds",
        "    utils_builders_Director ..> utils_builders_LexemeInfoMutator : configures",
        "    utils_builders_Director ..> utils_builders_LexemeInfoView : reads",
        "    parser_Parser --> parser_publishers_Publisher : creates",
        "    parser_Parser --> parser_facades_RuleblockASTBuilder : builds AST",
        "    parser_Parser --> utils_SymbolTable : writes",
        "    parser_publishers_Declaration --> utils_builders_LexemeInfoBuilder : holds",
        "    parser_publishers_ComponentDeclaration --> parser_publishers_TermBlock : publishes terms",
        "    parser_publishers_StructureDeclaration --> parser_publishers_StructureBlock : publishes fields",
        "    parser_publishers_StructureBlock ..> parser_utils_PathInitializationInfo : exposes",
        "    parser_publishers_EnumeratedTypeDeclaration ..> parser_facades_MacrosPublisher : registers macros",
        "    parser_publishers_AnnonymousEnumeratedField ..> parser_facades_MacrosPublisher : registers macros",
        "    parser_publishers_AnnonymousEnumeratedVariable ..> parser_facades_MacrosPublisher : registers macros",
        "    parser_facades_MacrosPublisher ..> parser_metadata_MacroMetadata : builds",
        "    parser_facades_MacrosPublisher --> utils_SymbolTable : writes",
        "    parser_facades_IdentifierParser ..> parser_utils_ParsedIdentifier : returns",
        "    parser_facades_MembershipFunctionClassifier ..> parser_utils_MembershipFunctionShape : returns",
        "    parser_facades_RuleblockASTBuilder ..> parser_tree_TreeNode : traverses",
        "    parser_facades_RuleblockASTBuilder ..> ast_ruleblock_RuleBlockNode : returns",
        "    parser_facades_RuleblockASTBuilder ..> ast_ruleblock_operators_Operator : configured by",
        "    parser_initializations_Factory ..> parser_initializations_leafs_DefaultInitialization : creates",
        "    parser_initializations_nodes_StructInitialization --> parser_initializations_Initialization : fields",
        "    parser_initializations_nodes_RepeatedInitialization --> parser_initializations_Initialization : intervals",
        "    parser_metadata_TermMetadata ..> parser_facades_MembershipFunctionClassifier : classifies",
        "    parser_metadata_ArrayMetadata ..> parser_facades_DimensionCalculator : sizes",
        "    parser_metadata_ArrayMetadata ..> parser_facades_RangesParser : splits ranges",
        "    parser_metadata_ArrayMetadata ..> parser_initializations_nodes_RepeatedInitialization : creates",
        "    parser_utils_ConditionParsingContext --> parser_tree_TreeNode : builds",
    ]
    for name in selected["parser_metadata"]:
        lines.append(f"    utils_builders_LexemeInfoView <|.. parser_metadata_{name}")
    return "\n".join(lines)


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
    mermaid = ["classDiagram", "    class SymbolTable {", "        -table: Map~String, LexemeInfo~", "        +get()",
                "        +put()", "        +putIfAbsent()", "        +size()", "    }", "    class LexemeInfo {", "        +type: Type",
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
               "    class SyntaxError {", "        +fatalForCompilation() = true", "    }",
               "    class DateOutOfRange", "    class IntervalConstructionError", "    class IntervalOutOfRange",
               "    class TimeOfDayOutOfRange", "    class DateAndTimeOutOfRange", "    class UndeclaredReference",
               "    class UnsupportedFeature", "    class StringLengthWarning", "    class HexadecimalOutOfRange",
               "    class RealOutOfRange", "    class NaturalOutOfRange", "    class BinaryOutOfRange",
               "    class OctalOutOfRange", "    class GeneratorWarning", "    Diagnostic <|-- Error",
               "    Diagnostic <|-- Warning", "    Error <|-- SyntaxError", "    Error <|-- DateOutOfRange",
               "    Error <|-- IntervalConstructionError", "    Error <|-- IntervalOutOfRange",
               "    Error <|-- TimeOfDayOutOfRange", "    Error <|-- DateAndTimeOutOfRange",
               "    Error <|-- UndeclaredReference", "    Error <|-- UnsupportedFeature", "",
               "    Warning <|-- StringLengthWarning", "    Warning <|-- HexadecimalOutOfRange",
               "    Warning <|-- RealOutOfRange", "    Warning <|-- NaturalOutOfRange",
               "    Warning <|-- BinaryOutOfRange", "    Warning <|-- OctalOutOfRange",
               "    Warning <|-- IntegerOutOfRange", "    Warning <|-- GeneratorWarning"]
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
        class LexemeInfoMutator {
            <<interface>>
            +type(Type)
            +subtype(Subtype)
            +customType(String)
            +source(Source)
            +use(Use)
            +inferiorLimits(List~String~)
            +superiorLimits(List~String~)
            +parameters(List~String~)
            +initialValue(Object)
        }
        class LexemeInfoView {
            <<interface>>
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
            +makeWithMetadata(LexemeInfoMutator, LexemeInfoView)
            +makeLiteral(LexemeInfoMutator)
            +makeIntegerZeroConstant(LexemeInfoMutator)
            +makeDefaultInteger(LexemeInfoMutator)
            +makeDefaultReal(LexemeInfoMutator)
            +makeDefaultBoolean(LexemeInfoMutator)
            +makeTrueBoolean(LexemeInfoMutator)
            +makeDefaultString(LexemeInfoMutator)
            +makeDefaultWString(LexemeInfoMutator)
        }
    }
    namespace utils_enums {
        class Type { 8 values }
        class Subtype { 35 values }
        class Use { 12 values }
        class Source { 7 values }
    }
    namespace utils_diagnostics {
        class Diagnostic
        class Error
        class Warning
        class SyntaxError
        class UndeclaredReference
        class UnsupportedFeature
        class DateOutOfRange
        class DateAndTimeOutOfRange
        class TimeOfDayOutOfRange
        class IntervalOutOfRange
        class IntervalConstructionError
        class GeneratorWarning
        class IntegerOutOfRange
        class RealOutOfRange
        class BinaryOutOfRange
        class OctalOutOfRange
        class HexadecimalOutOfRange
        class NaturalOutOfRange
        class StringLengthWarning
    }
    Diagnostic <|-- Error
    Diagnostic <|-- Warning
    Error <|-- SyntaxError
    Error <|-- UndeclaredReference
    Error <|-- UnsupportedFeature
    Error <|-- DateOutOfRange
    Error <|-- DateAndTimeOutOfRange
    Error <|-- TimeOfDayOutOfRange
    Error <|-- IntervalOutOfRange
    Error <|-- IntervalConstructionError
    Warning <|-- GeneratorWarning
    Warning <|-- IntegerOutOfRange
    Warning <|-- RealOutOfRange
    Warning <|-- BinaryOutOfRange
    Warning <|-- OctalOutOfRange
    Warning <|-- HexadecimalOutOfRange
    Warning <|-- NaturalOutOfRange
    Warning <|-- StringLengthWarning
    LexemeInfoBuilder ..|> LexemeInfoMutator
    LexemeInfoBuilder ..|> LexemeInfoView
    Director ..> LexemeInfoMutator
    Director ..> LexemeInfoView
    SymbolTable --> LexemeInfo
    DiagnosticsHandler --> Diagnostic"""
    return mermaid


def generate_publisher_sequence():
    """Sequence diagram for Parser to Publisher to SymbolTable publication."""
    return "\n".join([
        "sequenceDiagram",
        "    participant Parser as parser.Parser",
        "    participant Publisher as parser.publishers.*",
        "    participant Director as utils.builders.Director",
        "    participant Metadata as Metadata View",
        "    participant Builder as utils.builders.LexemeInfoBuilder",
        "    participant ST as utils.SymbolTable",
        "    participant MacroPub as parser.facades.MacrosPublisher",
        "    participant Factory as parser.initializations.Factory",
        "",
        "    Parser->>Publisher: new SingleDeclaration / MultipleDeclaration / DeclarationBlock",
        "    Parser->>Director: makeWithMetadata(publisher, metadata)",
        "    Director->>Metadata: read type, subtype, use, source, limits, parameters, initialValue",
        "    Director->>Builder: copy attributes into LexemeInfoBuilder",
        "    Parser->>Publisher: identifier(name) or identifiers(list)",
        "    Parser->>Publisher: setDeclarationScope(programName)",
        "    Publisher->>Builder: build()",
        "    Publisher->>ST: put(scope#identifier, LexemeInfo)",
        "    ST-->>Publisher: previous LexemeInfo or null",
        "    Publisher-->>Parser: keys()",
        "",
        "    opt Array or STRING metadata",
        "        Metadata->>Factory: createPrimitiveInitialization(symbolTable, subtype)",
        "        Factory-->>Metadata: DefaultInitialization",
        "    end",
        "",
        "    opt Enumeration declaration",
        "        Publisher->>MacroPub: publish(symbolTable, macroNames, prefix)",
        "        MacroPub->>Director: makeWithMetadata(builder, new MacroMetadata(...))",
        "        MacroPub->>ST: put(prefix + macroName, LexemeInfo)",
        "    end",
    ])


def generate_codegen_package_flowchart():
    """Import dependencies of the codegen package and its consumers."""
    sources = read_java_files(SRC_DIR / "codegen")
    packages = {"codegen", "codegen.defuzzification", "codegen.evaluation", "codegen.fuzzysets"}
    edges = set()
    for path, content in sources.items():
        origin = ".".join(Path(path).parts[:-1])
        for imp, wildcard in re.findall(r'^\s*import\s+([\w.]+?)(\.\*)?;', content, re.MULTILINE):
            destination = imp if wildcard else imp.rsplit(".", 1)[0]
            if origin in packages and destination in packages and origin != destination:
                edges.add((origin, destination))
    for consumer in ("compiler", "lowering", "simulation"):
        edges.add((consumer, "codegen"))
    used = sorted({p for edge in edges for p in edge})
    ids = {pkg: pkg.replace(".", "_") for pkg in used}
    lines = ["flowchart LR"]
    lines += [f'    {ids[pkg]}["{pkg}"]' for pkg in used]
    lines += [f"    {ids[src]} --> {ids[dst]}" for src, dst in sorted(edges)]
    return "\n".join(lines)


def generate_codegen_class_diagram():
    """Class diagram of the codegen package: generator stages, controller model, fuzzy sets."""
    sources = read_java_files(SRC_DIR / "codegen")
    selected = {
        "codegen": ["CodeGenerator", "ControllerModel", "CppWriter", "Fuzzifier", "Variable"],
        "codegen_defuzzification": ["Defuzzifier", "CentroidDefuzzifier"],
        "codegen_evaluation": ["EvaluationMethod", "MinMaxMethod"],
        "codegen_fuzzysets": ["FuzzySet", "SingletonFuzzySet", "SShapeFuzzySet", "TrapezoidFuzzySet", "TriangularFuzzySet", "ZShapeFuzzySet"],
    }
    lines = ["classDiagram"]
    for namespace, names in selected.items():
        lines.append(f"    namespace {namespace} {{")
        for name in names:
            matches = [(path, content) for path, content in sources.items() if Path(path).stem == name]
            if len(matches) != 1 or not re.search(r'\b(class|interface)\s+' + name + r'\b', matches[0][1]):
                raise ValueError(f"Missing codegen type: {name}")
            lines.append(f"        class {name}")
        lines.append("    }")
    lines += [
        "    EvaluationMethod <|-- MinMaxMethod",
        "    Defuzzifier <|-- CentroidDefuzzifier",
        "    FuzzySet <|-- SingletonFuzzySet",
        "    FuzzySet <|-- SShapeFuzzySet",
        "    FuzzySet <|-- TrapezoidFuzzySet",
        "    FuzzySet <|-- TriangularFuzzySet",
        "    FuzzySet <|-- ZShapeFuzzySet",
        "    CodeGenerator --> ControllerModel : uses",
        "    CodeGenerator --> Fuzzifier : uses",
        "    CodeGenerator --> EvaluationMethod : uses",
        "    CodeGenerator --> Defuzzifier : uses",
        "    Fuzzifier --> Variable : uses",
        "    Variable --> FuzzySet : uses",
    ]
    return "\n".join(lines)


def generate_codegen_controller_sequence():
    """Sequence of the controller generation stages."""
    return "\n".join([
        "sequenceDiagram",
        "    participant M as ControllerModel",
        "    participant G as CodeGenerator",
        "    participant F as Fuzzifier",
        "    participant E as EvaluationMethod",
        "    participant D as Defuzzifier",
        "    participant S as FuzzySet",
        "",
        "    G->>M: new ControllerModel",
        "    M-->>G: inputs outputs converterRange evaluationMethod defuzzifiers",
        "    G->>F: compileFunctionsSlope out converterRange",
        "    G->>F: compileInputVariablesBuffers out",
        "    G->>F: compileFuzzifier out",
        "    F->>S: compileFuzzSet out converterRange varName setNumber",
        "    G->>E: compileEvalMethod out model",
        "    G->>D: compileDefuzz out model outputIndex",
        "    D->>S: compileOutputSetsBuffer out",
        "    G->>M: generateCpp",
        "    G->>M: generateHeader",
        "    G->>M: generateTestBench headerFile",
    ])


DIAGRAMS = {
    "package_dependencies": generate_package_flowchart,
    "lexer_package_flowchart": generate_lexer_package_flowchart,
    "lexer_class_diagram": generate_lexer_class_diagram,
    "lexer_intervals_object": generate_lexer_intervals_object,
    "parser_package_flowchart": generate_parser_package_flowchart,
    "parser_class_diagram": generate_parser_class_diagram,
    "lexer_parser_sequence": generate_lexer_parser_sequence,
    "symboltable_storage": generate_symboltable_storage_object,
    "diagnostics_hierarchy": generate_diagnostics_hierarchy,
    "architecture_evolution": generate_architecture_evolution,
    "utils_class_diagram": generate_utils_class_diagram,
    "publisher_sequence": generate_publisher_sequence,
    "codegen_package_flowchart": generate_codegen_package_flowchart,
    "codegen_class_diagram": generate_codegen_class_diagram,
    "codegen_controller_sequence": generate_codegen_controller_sequence,
}


def main():
    parser = argparse.ArgumentParser(description="Generate mermaid diagrams from source code")
    parser.add_argument("--list", action="store_true", help="List available diagrams")
    parser.add_argument("scope", nargs="?", default="all", help="Scope: all|lexer|parser|utils|codegen|architecture")
    args = parser.parse_args()

    DIAGRAMS_DIR.mkdir(parents=True, exist_ok=True)

    # Determine which diagrams to generate based on scope
    scope = args.scope

    if scope == "all":
        targets = list(DIAGRAMS.keys())
    elif scope == "lexer":
        targets = ["lexer_package_flowchart", "lexer_class_diagram", "lexer_intervals_object", "lexer_parser_sequence"]
    elif scope == "parser":
        targets = ["parser_package_flowchart", "parser_class_diagram", "publisher_sequence", "symboltable_storage"]
    elif scope == "utils":
        targets = ["utils_class_diagram", "diagnostics_hierarchy", "symboltable_storage"]
    elif scope == "codegen":
        targets = ["codegen_package_flowchart", "codegen_class_diagram", "codegen_controller_sequence"]
    elif scope == "architecture":
        targets = ["package_dependencies", "lexer_parser_sequence", "architecture_evolution"]
    else:
        # Try exact match
        if scope in DIAGRAMS:
            targets = [scope]
        else:
            print(f"Unknown scope: {scope}")
            print(f"Available: all, lexer, parser, utils, codegen, architecture")
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
