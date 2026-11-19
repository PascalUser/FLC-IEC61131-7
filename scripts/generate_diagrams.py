#!/usr/bin/env python3
"""
Relational diagram generator for FLC-IEC61131-7 compiler.
Generates mermaid diagrams (class, sequence, flow, package, object) from current source code.
Outputs: doc/diagrams/*.mmd
"""
import argparse
import re
from pathlib import Path

from extract_architecture import build_inventory

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
    """Aggregate explicit production imports into compiler module dependencies.

    Each node is the ``compiler`` facade or a direct ``compiler.<module>``
    subtree. The inventory is derived from Java sources at generation time.
    This graph does not infer runtime calls or dependencies without imports.
    """
    inventory = build_inventory(ROOT)
    production_packages = {
        item["name"] for item in inventory["packages"]
        if item["scope"] == "production"
        and (item["name"] == "compiler" or item["name"].startswith("compiler."))
    }
    if not production_packages:
        raise ValueError("No compiler production packages found")

    def module(package):
        parts = package.split(".")
        return ".".join(parts[:2]) if len(parts) > 1 else package

    modules = sorted({module(name) for name in production_packages})
    edges = {
        (module(item["from_package"]), module(item["to_package"]))
        for item in inventory["dependencies"]
        if item["from_scope"] == "production"
        and item["to_scope"] == "production"
        and item["from_package"] in production_packages
        and item["to_package"] in production_packages
        and module(item["from_package"]) != module(item["to_package"])
    }
    identifiers = {name: name.replace(".", "_") for name in modules}
    lines = ["flowchart LR"]
    lines += [f'    {identifiers[name]}["{name}"]' for name in modules]
    lines += [f"    {identifiers[source]} --> {identifiers[target]}"
              for source, target in sorted(edges)]
    return "\n".join(lines)


def generate_import_dependency_flowchart(source_prefix):
    """Draw production import edges backed by the live architecture inventory.

    An edge is included when its source is the requested package or a child
    package. This does not infer implicit dependencies or Java call graphs.
    """
    inventory = build_inventory(ROOT)
    production_packages = {
        item["name"] for item in inventory["packages"]
        if item["scope"] == "production"
    }
    if source_prefix not in production_packages:
        raise ValueError(f"Production package not found: {source_prefix}")

    edges = {
        (edge["from_package"], edge["to_package"])
        for edge in inventory["dependencies"]
        if edge["from_scope"] == "production"
        and edge["to_scope"] == "production"
        and (edge["from_package"] == source_prefix
             or edge["from_package"].startswith(source_prefix + "."))
    }
    if not edges:
        raise ValueError(f"No production import dependencies found for {source_prefix}")

    used = sorted({package for edge in edges for package in edge})
    ids = {package: package.replace(".", "_") for package in used}
    lines = ["flowchart LR"]
    lines += [f'    {ids[package]}["{package}"]' for package in used]
    lines += [f"    {ids[src]} --> {ids[dst]}" for src, dst in sorted(edges)]
    return "\n".join(lines)


def generate_lexer_package_flowchart():
    """Production lexer package dependencies from the architecture inventory."""
    return generate_import_dependency_flowchart("compiler.lexer")


def generate_lexer_class_diagram():
    """Selected class/interface inheritance and registry uses verified in Java/Flex."""
    sources = read_java_files(SRC_DIR / "compiler" / "lexer")
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
    flex = (SRC_DIR / "compiler" / "lexer" / "Lexer.flex").read_text()
    for registry in ("LexicalPreprocessors", "LexicalAnalyzers"):
        if re.search(r'private\s+' + registry + r'\s+', flex):
            lines.append(f"    Lexer --> {registry} : uses")
    return "\n".join(lines)


def generate_lexer_intervals_object():
    """Object chain instantiated for INTERVALS in LexicalPreprocessors.java."""
    source = (SRC_DIR / "compiler" / "lexer" / "internals" / "LexicalPreprocessors.java").read_text()
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
    """Production parser package dependencies from the architecture inventory."""
    return generate_import_dependency_flowchart("compiler.parser")


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
            "Publisher", "Declaration", "Block", "DeclarationBlock",
            "MultipleDeclaration", "SingleDeclaration", "FuzzyDeclaration",
            "StructureDeclaration", "StructureBlock", "FuzzyBlock",
            "EnumeratedTypeDeclaration", "AnnonymousEnumeratedVariable",
            "AnnonymousEnumeratedField",
        ],
        "parser_facades": [
            "DimensionCalculator", "IdentifierParser", "MacrosPublisher",
            "MembershipFunctionClassifier", "RangesParser", "RuleblockASTBuilder",
            "SymbolTableCleaner",
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
        "parser_tree": ["ParserTreeNode", "ParserLabel"],
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
            "+scope(String) Publisher",
            "+keys() List~String~",
            "+publish() void",
        ],
        "Declaration": [
            "<<abstract>>",
            "#declarationScope String",
            "#builder LexemeInfoBuilder",
            "+scope(String) Declaration",
            "+type(Type) Declaration",
            "+subtype(Subtype) Declaration",
            "+customType(String) Declaration",
            "+source(Source) Declaration",
            "+use(Use) Declaration",
            "+inferiorLimits(List~String~) Declaration",
            "+superiorLimits(List~String~) Declaration",
            "+parameters(List~String~) Declaration",
            "+initialValue(Object) Declaration",
        ],
        "Block": [
            "<<abstract>>",
            "#declarations List~ConcreteDecl~",
            "+add(ConcreteDecl) void",
            "+scope(String) Block",
            "+keys() List~String~",
            "+publish() void",
        ],
        "DeclarationBlock": ["+add(Declaration) void"],
        "MultipleDeclaration": [
            "#identifiers List~String~",
            "+identifiers(List~String~) MultipleDeclaration",
            "+publish() void",
            "+keys() List~String~",
        ],
        "SingleDeclaration": [
            "#identifier String",
            "+identifier(String) SingleDeclaration",
            "+publish() void",
            "+keys() List~String~",
        ],
        "FuzzyDeclaration": ["+source(Source) FuzzyDeclaration", "+publish() void"],
        "StructureDeclaration": ["+identifier(String) StructureDeclaration", "+publish() void"],
        "StructureBlock": ["+add(SingleDeclaration) void", "+getInitializationInfo() List~PathInitializationInfo~"],
        "FuzzyBlock": ["+add(SingleDeclaration) void", "+getTermNames() List~String~"],
        "EnumeratedTypeDeclaration": ["+publish()"],
        "AnnonymousEnumeratedVariable": ["+publish()"],
        "AnnonymousEnumeratedField": ["+publish()"],
        "DimensionCalculator": ["<<utility>>", "+calculate(List~String~, List~String~) int"],
        "IdentifierParser": ["+parse(String, String) ParsedIdentifier"],
        "MacrosPublisher": ["+publish(SymbolTable, List~String~, String)"],
        "MembershipFunctionClassifier": ["+classify(List~String~, List~String~) MembershipFunctionShape"],
        "RangesParser": ["<<utility>>", "+parse(List~String~, List~String~, List~String~)"],
        "RuleblockASTBuilder": ["+build(ParserTreeNode, List~Operator~) RuleBlockNode"],
        "SymbolTableCleaner": ["+clean(SymbolTable) void"],
        "Initialization": [
            "<<interface>>",
            "+find(String) Optional~Initialization~",
            "+variableValue() String",
            "+copy() Initialization",
        ],
        "Factory": ["<<utility>>", "+createPrimitiveInitialization(SymbolTable, Subtype) DefaultInitialization"],
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
        "ParserTreeNode": ["+label() ParserLabel", "+symbolReference() String", "+children() List~ParserTreeNode~"],
        "ParsedIdentifier": ["+mangled() String", "+scope() String", "+name() String"],
        "PathInitializationInfo": ["+name() String", "+initialValue() Initialization"],
        "ConditionParsingContext": ["+addOperator(ParserLabel)", "+addOperand(ParserTreeNode)", "+getConditionNode() ParserTreeNode"],
        "ArrayParsingContext": ["+getMaxValue() int", "+getPathsInfo() List~PathInitializationInfo~"],
        "MembershipFunctionShape": ["+subtype() Subtype", "+parameters() List~String~"],
        "SymbolTable": ["+put(String, LexemeInfo) LexemeInfo", "+putIfAbsent(String, LexemeInfo) LexemeInfo", "+get(String) LexemeInfo", "+size() int"],
        "LexemeInfoMutator": ["<<interface>>", "+type(Type)", "+subtype(Subtype)", "+initialValue(Object)"],
        "LexemeInfoView": ["<<interface>>", "+type() Type", "+subtype() Subtype", "+initialValue() Object"],
        "LexemeInfoBuilder": ["+build() LexemeInfo"],
        "Director": [
            "<<utility>>",
            "+makeWithMetadata(LexemeInfoMutator, LexemeInfoView) void",
            "+makeLiteral(LexemeInfoMutator) void",
            "+makeIntegerZeroConstant(LexemeInfoMutator) String",
            "+makeBooleanTrueConstant(LexemeInfoMutator) String",
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
        "    parser_publishers_Publisher <|.. parser_publishers_Block",
        "    parser_publishers_Declaration <|-- parser_publishers_MultipleDeclaration",
        "    parser_publishers_Declaration <|-- parser_publishers_SingleDeclaration",
        "    parser_publishers_Block <|-- parser_publishers_DeclarationBlock",
        "    parser_publishers_Block <|-- parser_publishers_StructureBlock",
        "    parser_publishers_Block <|-- parser_publishers_FuzzyBlock",
        "    parser_publishers_SingleDeclaration <|-- parser_publishers_FuzzyDeclaration",
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
        "    parser_publishers_FuzzyDeclaration --> parser_publishers_FuzzyBlock : publishes terms",
        "    parser_publishers_StructureDeclaration --> parser_publishers_StructureBlock : publishes fields",
        "    parser_publishers_StructureBlock ..> parser_utils_PathInitializationInfo : exposes",
        "    parser_publishers_EnumeratedTypeDeclaration ..> parser_facades_MacrosPublisher : registers macros",
        "    parser_publishers_AnnonymousEnumeratedField ..> parser_facades_MacrosPublisher : registers macros",
        "    parser_publishers_AnnonymousEnumeratedVariable ..> parser_facades_MacrosPublisher : registers macros",
        "    parser_facades_MacrosPublisher ..> parser_metadata_MacroMetadata : builds",
        "    parser_facades_MacrosPublisher --> utils_SymbolTable : writes",
        "    parser_facades_IdentifierParser ..> parser_utils_ParsedIdentifier : returns",
        "    parser_facades_MembershipFunctionClassifier ..> parser_utils_MembershipFunctionShape : returns",
        "    parser_facades_RuleblockASTBuilder ..> parser_tree_ParserTreeNode : traverses",
        "    parser_facades_RuleblockASTBuilder ..> ast_ruleblock_RuleBlockNode : returns",
        "    parser_facades_RuleblockASTBuilder ..> ast_ruleblock_operators_Operator : configured by",
        "    parser_initializations_Factory ..> parser_initializations_leafs_DefaultInitialization : creates",
        "    parser_initializations_nodes_StructInitialization --> parser_initializations_Initialization : fields",
        "    parser_initializations_nodes_RepeatedInitialization --> parser_initializations_Initialization : intervals",
        "    parser_metadata_TermMetadata ..> parser_facades_MembershipFunctionClassifier : classifies",
        "    parser_metadata_ArrayMetadata ..> parser_facades_DimensionCalculator : sizes",
        "    parser_metadata_ArrayMetadata ..> parser_facades_RangesParser : splits ranges",
        "    parser_metadata_ArrayMetadata ..> parser_initializations_nodes_RepeatedInitialization : creates",
        "    parser_utils_ConditionParsingContext --> parser_tree_ParserTreeNode : builds",
    ]
    for name in selected["parser_metadata"]:
        lines.append(f"    utils_builders_LexemeInfoView <|.. parser_metadata_{name}")
    return "\n".join(lines)


def generate_lexer_parser_sequence():
    """Scanner to parser flow checked against Lexer.flex and SemanticAnalyzer.java."""
    flex = (SRC_DIR / "compiler" / "lexer" / "Lexer.flex").read_text()
    semantics = (SRC_DIR / "compiler" / "lexer" / "semantics" / "SemanticAnalyzer.java").read_text()
    numbers = (SRC_DIR / "compiler" / "lexer" / "semantics" / "numbers" / "NumbersAnalyzer.java").read_text()
    strings = (SRC_DIR / "compiler" / "lexer" / "semantics" / "strings" / "StringsAnalyzer.java").read_text()
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
        "    loop Chained transformers",
        "        T->>T: giveToNext(lexeme)",
        "    end",
        "    T-->>L: preprocessedLexeme",
        "    L->>A: analyze(LexicalContext)",
        "    opt Semantic warning or error",
        "        A->>D: add(Diagnostic)",
        "    end",
        "    opt Valid literal or numeric fallback",
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
                "        +put()", "        +putIfAbsent()", "        +iterator()", "        +size()", "    }", "    class LexemeInfo {", "        +type: Type",
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
            +iterator()
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
            +makeBooleanTrueConstant(LexemeInfoMutator)
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
        "    Parser->>Publisher: scope(programName)",
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
    """Generator subtree dependencies based on production Java imports.

    Includes compiler.generator.lowers because lowering belongs to the
    generator module; no synthetic consumer edges are added.
    """
    return generate_import_dependency_flowchart("compiler.generator")


def generate_codegen_class_diagram():
    """Class diagram of the generator package: generator stages, controller model, fuzzy sets."""
    sources = read_java_files(SRC_DIR / "generator")
    selected = {
        "generator": ["CodeGenerator", "ControllerModel", "CppWriter", "Fuzzifier", "Variable"],
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
                raise ValueError(f"Missing generator type: {name}")
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


def generate_lowering_package_flowchart():
    """Lowering subtree dependencies based on production Java imports."""
    return generate_import_dependency_flowchart("compiler.generator.lowers")


def generate_lowering_class_diagram():
    """Current lowering types and source-verified relationships."""
    root = SRC_DIR / "compiler" / "generator" / "lowers"
    selected = {
        "lowering_builders": {
            "ControllerModelBuilder": "builders/ControllerModelBuilder.java",
            "ControllerModelSchema": "builders/ControllerModelSchema.java",
        },
        "lowering_contexts": {
            "LoweringContext": "contexts/LoweringContext.java",
            "RuleLoweringContext": "contexts/RuleLoweringContext.java",
            "TermLoweringContext": "contexts/TermLoweringContext.java",
            "VarLoweringContext": "contexts/VarLoweringContext.java",
        },
        "lowering_facades": {
            "TermSetFactory": "facades/TermSetFactory.java",
        },
        "lowering_lowers": {
            "ControllerLower": "ControllerLower.java",
            "Lower": "Lower.java",
        },
        "lowering_sublowers": {
            "BitWidthLower": "sublowers/BitWidthLower.java",
            "FuzzyVariablesLower": "sublowers/FuzzyVariablesLower.java",
            "InputsLower": "sublowers/InputsLower.java",
            "InternalVariablesLower": "sublowers/InternalVariablesLower.java",
            "OutputsLower": "sublowers/OutputsLower.java",
            "RuleBlockLower": "sublowers/RuleBlockLower.java",
            "RuleLower": "sublowers/RuleLower.java",
            "TermLower": "sublowers/TermLower.java",
        },
        "lowering_utils": {
            "ControllerModelState": "utils/ControllerModelState.java",
            "SymbolKeys": "utils/SymbolKeys.java",
        },
    }
    sources = {}
    for names in selected.values():
        for name, relative_path in names.items():
            source = (root / relative_path).read_text(encoding="utf-8")
            if not re.search(r"\b(?:class|interface)\s+" + re.escape(name) + r"\b", source):
                raise ValueError(f"Missing lowers type: {name}")
            sources[name] = source

    # Relationship evidence is checked, not inferred from historical diagrams.
    relationships = [
        ("ControllerModelBuilder", "implements ControllerModelSchema", "ControllerModelSchema <|.. ControllerModelBuilder"),
        ("ControllerLower", "implements Lower<ControllerModel>", "Lower <|.. ControllerLower"),
        ("BitWidthLower", "implements Lower<Integer>", "Lower <|.. BitWidthLower"),
        ("FuzzyVariablesLower", "implements Lower<List<Variable>>", "Lower <|.. FuzzyVariablesLower"),
        ("RuleBlockLower", "implements Lower<EvaluationMethod>", "Lower <|.. RuleBlockLower"),
        ("RuleLower", "implements Lower<int[]>", "Lower <|.. RuleLower"),
        ("TermLower", "implements Lower<FuzzySet>", "Lower <|.. TermLower"),
        ("InputsLower", "extends FuzzyVariablesLower", "FuzzyVariablesLower <|-- InputsLower"),
        ("OutputsLower", "extends FuzzyVariablesLower", "FuzzyVariablesLower <|-- OutputsLower"),
        ("RuleLoweringContext", "extends LoweringContext", "LoweringContext <|-- RuleLoweringContext"),
        ("VarLoweringContext", "extends LoweringContext", "LoweringContext <|-- VarLoweringContext"),
        ("TermLoweringContext", "extends VarLoweringContext", "VarLoweringContext <|-- TermLoweringContext"),
        ("ControllerLower", "new ControllerModelBuilder()", "ControllerLower --> ControllerModelBuilder : builds"),
        ("ControllerLower", "new BitWidthLower(ctx)", "ControllerLower --> BitWidthLower : uses"),
        ("ControllerLower", "new InputsLower(ctx, bitWidthLower)", "ControllerLower --> InputsLower : uses"),
        ("ControllerLower", "new OutputsLower(ctx, bitWidthLower)", "ControllerLower --> OutputsLower : uses"),
        ("ControllerLower", "new RuleBlockLower(ctx)", "ControllerLower --> RuleBlockLower : uses"),
        ("RuleBlockLower", "new RuleLower(ruleCtx).adapt()", "RuleBlockLower --> RuleLower : uses"),
        ("FuzzyVariablesLower", "new TermLower(termCtx).adapt()", "FuzzyVariablesLower --> TermLower : uses"),
        ("TermLower", "TermSetFactory.create(", "TermLower --> TermSetFactory : creates"),
        ("ControllerModelBuilder", "new ControllerModelState()", "ControllerModelBuilder --> ControllerModelState : holds"),
    ]
    for name, snippet, _ in relationships:
        if snippet not in sources[name]:
            raise ValueError(f"Lowering relationship changed: {name}: {snippet}")

    lines = ["classDiagram"]
    for namespace, names in selected.items():
        lines.append(f"    namespace {namespace} {{")
        lines.extend(f"        class {name}" for name in names)
        lines.append("    }")
    lines.extend(f"    {edge}" for _, _, edge in relationships)
    return "\n".join(lines)


def generate_lowering_controller_sequence():
    """Source-checked lowering stages and their Optional result."""
    lower_dir = SRC_DIR / "compiler" / "generator" / "lowers"
    sources = {
        "controller": lower_dir / "ControllerLower.java",
        "variables": lower_dir / "sublowers" / "FuzzyVariablesLower.java",
        "term": lower_dir / "sublowers" / "TermLower.java",
        "rules": lower_dir / "sublowers" / "RuleBlockLower.java",
    }
    evidence = {
        "controller": [
            "new ControllerModelBuilder()",
            "new BitWidthLower(ctx)", "bitWidthLower.adapt()",
            "new InputsLower(ctx, bitWidthLower)", "inputsLower.adapt()",
            "controller.inputs(inputVarsObj.get())",
            "new OutputsLower(ctx, bitWidthLower)", "outputsLower.adapt()",
            "controller.outputs(outputVars)", "controller.defuzzifiers(defuzzifiers)",
            "new InternalVariablesLower(ctx)", "varsLower.adapt()",
            "new RuleBlockLower(ctx)", "ruleBlockLower.adapt()",
            "controller.evaluationMethod(evalMethodObj.get())",
            "if (validModel)", "Optional.of(controller.build())", "Optional.empty()",
        ],
        "variables": ["new TermLower(termCtx).adapt()"],
        "term": ["TermSetFactory.create(termName, termInfo, lowerBound)"],
        "rules": ["new RuleLower(ruleCtx).adapt()", "new MinMaxMethod(rows)"],
    }
    for stage, path in sources.items():
        source = path.read_text(encoding="utf-8")
        for snippet in evidence[stage]:
            if snippet not in source:
                raise ValueError(f"Lowering sequence source changed ({path.name}): {snippet}")

    return "\n".join([
        "sequenceDiagram",
        "    participant C as compiler.generator.lowers.ControllerLower",
        "    participant B as compiler.generator.lowers.sublowers.BitWidthLower",
        "    participant I as compiler.generator.lowers.sublowers.InputsLower",
        "    participant O as compiler.generator.lowers.sublowers.OutputsLower",
        "    participant V as compiler.generator.lowers.sublowers.FuzzyVariablesLower",
        "    participant T as compiler.generator.lowers.sublowers.TermLower",
        "    participant F as compiler.generator.lowers.facades.TermSetFactory",
        "    participant N as compiler.generator.lowers.sublowers.InternalVariablesLower",
        "    participant R as compiler.generator.lowers.sublowers.RuleBlockLower",
        "    participant U as compiler.generator.lowers.sublowers.RuleLower",
        "    participant M as compiler.generator.lowers.builders.ControllerModelBuilder",
        "",
        "    C->>M: new ControllerModelBuilder()",
        "    C->>B: new BitWidthLower(ctx), adapt()",
        "    B-->>C: Optional bit width",
        "    opt bit width valid",
        "        C->>M: bitWidth(width)",
        "    end",
        "    C->>I: new InputsLower(ctx, bitWidthLower), adapt()",
        "    I->>V: inherited adapt()",
        "    loop each declared input term",
        "        V->>T: new TermLower(termCtx), adapt()",
        "        T->>F: create(termName, termInfo, lowerBound)",
        "        F-->>T: Optional FuzzySet",
        "    end",
        "    I-->>C: Optional input variables",
        "    opt inputs valid",
        "        C->>M: inputs(inputVars)",
        "    end",
        "    C->>O: new OutputsLower(ctx, bitWidthLower), adapt()",
        "    O->>V: inherited adapt()",
        "    O-->>C: Optional output variables",
        "    opt outputs valid",
        "        C->>M: outputs(outputVars)",
        "        C->>M: defuzzifiers(centroid map)",
        "    end",
        "    C->>N: new InternalVariablesLower(ctx), adapt()",
        "    N-->>C: Optional local variable keys",
        "    C->>R: new RuleBlockLower(ctx), adapt()",
        "    loop each rule in rule block",
        "        R->>U: new RuleLower(ruleCtx), adapt()",
        "        U-->>R: Optional rule row",
        "    end",
        "    R-->>C: Optional MinMaxMethod(rows)",
        "    opt evaluation method valid",
        "        C->>M: evaluationMethod(evalMethod)",
        "    end",
        "    alt all stages valid",
        "        C->>M: build()",
        "        M-->>C: ControllerModel",
        "        C-->>C: Optional.of(model)",
        "    else any stage invalid",
        "        C-->>C: Optional.empty()",
        "    end",
    ])


def generate_ast_package_flowchart():
    """Import dependencies between AST subpackages and their direct consumers."""
    sources = read_java_files(SRC_DIR)
    ast_packages = {"ast", "ast.ruleblock", "ast.ruleblock.operators"}
    consumers = {
        "compiler", "lowers", "lowers.constructors", "lowers.contexts",
        "lowers.lowers", "parser", "parser.facades",
    }
    packages = ast_packages | consumers
    edges = set()
    for path, content in sources.items():
        origin = ".".join(Path(path).parts[:-1])
        if origin not in packages:
            continue
        for imp, wildcard in re.findall(r'^\s*import\s+([\w.]+?)(\.\*)?;', content, re.MULTILINE):
            destination = imp if wildcard else imp.rsplit(".", 1)[0]
            if (origin != destination and destination in packages
                    and (origin in ast_packages or destination in ast_packages)):
                edges.add((origin, destination))
    used = sorted({p for edge in edges for p in edge})
    ids = {pkg: pkg.replace(".", "_") for pkg in used}
    lines = ["flowchart LR"]
    lines += [f'    {ids[pkg]}["{pkg}"]' for pkg in used]
    lines += [f"    {ids[src]} --> {ids[dst]}" for src, dst in sorted(edges)]
    return "\n".join(lines)


def generate_ast_class_diagram():
    """Class hierarchy and final-field associations read from the AST sources."""
    sources = read_java_files(SRC_DIR / "compiler" / "ast")
    selected = {
        "ast": ["AbstractAstNode", "AbstractSyntaxTree"],
        "ast_ruleblock": [
            "BodyNode", "CondNode", "IdentifierNode", "IfNode", "IsNode",
            "NotNode", "RuleBlockNode", "WithNode",
        ],
        "ast_ruleblock_operators": [
            "Operator", "OperatorFactory", "OperatorNode", "AsumNode", "BdifNode",
            "BsumNode", "MaxNode", "MinNode", "NsumNode", "ProdNode",
        ],
    }
    members = {
        "AbstractAstNode": [
            "-children List~AbstractAstNode~",
            "+label() String",
            "+children() List~AbstractAstNode~",
        ],
        "AbstractSyntaxTree": ["-programName String", "+label() String"],
        "BodyNode": ["-act OperatorNode", "+act() OperatorNode", "+label() String"],
        "CondNode": ["-condition AbstractAstNode", "+condition() AbstractAstNode", "+label() String"],
        "IdentifierNode": ["-name String", "+name() String", "+label() String"],
        "IfNode": [
            "-condition CondNode", "-body BodyNode",
            "+condition() CondNode", "+body() BodyNode", "+label() String",
        ],
        "IsNode": [
            "-variable IdentifierNode", "-term IdentifierNode",
            "+variable() IdentifierNode", "+term() IdentifierNode", "+label() String",
        ],
        "NotNode": ["-operand AbstractAstNode", "+operand() AbstractAstNode", "+label() String"],
        "RuleBlockNode": [
            "-name String", "-accumulation OperatorNode",
            "+accumulation() OperatorNode", "+label() String",
        ],
        "WithNode": [
            "-weight String", "-ifNode IfNode",
            "+weight() String", "+ifNode() IfNode", "+label() String",
        ],
        "Operator": ["<<enumeration>>", "+ASUM", "+BDIF", "+BSUM", "+MAX", "+MIN", "+NSUM", "+PROD"],
        "OperatorFactory": [
            "<<utility>>", "+orDefault() Operator", "+andDefault() Operator",
            "+actDefault() Operator", "+accuDefault() Operator",
        ],
        "OperatorNode": ["-operator Operator", "+operator() Operator", "+label() String"],
    }
    known = {name for names in selected.values() for name in names}
    for name in known:
        matches = [(path, content) for path, content in sources.items() if Path(path).stem == name]
        if len(matches) != 1 or not re.search(
                r'\b(?:class|interface|enum)\s+' + re.escape(name) + r'\b', matches[0][1]):
            raise ValueError(f"Missing AST type: {name}")

    lines = ["classDiagram"]
    for namespace, names in selected.items():
        lines.append(f"    namespace {namespace} {{")
        for name in names:
            if members.get(name):
                lines.append(f"        class {name} {{")
                lines += [f"            {member}" for member in members[name]]
                lines.append("        }")
            else:
                lines.append(f"        class {name}")
        lines.append("    }")

    inheritances = set()
    for path, content in sorted(sources.items()):
        name = Path(path).stem
        if name not in known:
            continue
        match = re.search(
            r'\b(?:class|interface)\s+' + re.escape(name) + r'\s+extends\s+(\w+)', content)
        if match and match.group(1) in known:
            inheritances.add((match.group(1), name))
    for parent, child in sorted(inheritances):
        lines.append(f"    {parent} <|-- {child}")

    associations = {}
    by_name = {Path(path).stem: content for path, content in sources.items()}
    for path, content in sorted(sources.items()):
        name = Path(path).stem
        if name not in known:
            continue
        for field_type, field_name in re.findall(
                r'private\s+final\s+([A-Z]\w*)\s+(\w+)\s*;', content):
            if field_type in known:
                associations.setdefault((name, field_type), []).append(field_name)
    for source, target, label in [
            ("AbstractSyntaxTree", "RuleBlockNode", "ruleBlocks"),
            ("OperatorNode", "AbstractAstNode", "operands"),
    ]:
        if not re.search(
                r'public\s+' + source + r'\s*\([^)]*' + target + r'[^)]*\)',
                by_name[source]):
            raise ValueError(f"AST constructor changed: {source} -> {target}")
        associations.setdefault((source, target), []).append(label)
    for (source, target), labels in sorted(associations.items()):
        lines.append(f"    {source} --> {target} : {', '.join(labels)}")
    return "\n".join(lines)


def generate_ast_construction_sequence():
    """Parser-to-lowers construction flow checked against the Java sources."""
    parser_y = (SRC_DIR / "compiler" / "parser" / "Parser.y").read_text()
    builder = (SRC_DIR / "compiler" / "parser" / "facades" / "RuleblockASTBuilder.java").read_text()
    controller_lower = (SRC_DIR / "compiler" / "generator" / "lowers" / "ControllerLower.java").read_text()
    ruleblock_lower = (SRC_DIR / "compiler" / "generator" / "lowers" / "sublowers" / "RuleBlockLower.java").read_text()
    rule_lower = (SRC_DIR / "compiler" / "generator" / "lowers" / "sublowers" / "RuleLower.java").read_text()
    for snippet, text in [
            ("RuleblockASTBuilder.build(componentNode, configuration)", parser_y),
            ("new AbstractSyntaxTree(this.programName, $5)", parser_y),
            ("new RuleBlockNode", builder),
            ("new OperatorNode", builder),
            ("RuleBlockLower ruleBlockLower = new RuleBlockLower(ctx)", controller_lower),
            ("ruleBlockLower.adapt()", controller_lower),
            ("new RuleLower(ruleCtx).adapt()", ruleblock_lower),
            ("new MinMaxMethod(rows)", ruleblock_lower),
            ("public Optional<int[]> adapt", rule_lower),
    ]:
        if snippet not in text:
            raise ValueError(f"AST sequence source changed: {snippet}")
    return "\n".join([
        "sequenceDiagram",
        "    participant P as compiler.parser.Parser",
        "    participant B as compiler.parser.facades.RuleblockASTBuilder",
        "    participant T as compiler.ast.AbstractSyntaxTree",
        "    participant C as compiler.generator.lowers.ControllerLower",
        "    participant R as compiler.generator.lowers.sublowers.RuleBlockLower",
        "    participant L as compiler.generator.lowers.sublowers.RuleLower",
        "    participant M as compiler.generator.evaluation.MinMaxMethod",
        "",
        "    P->>B: build(componentNode, configuration)",
        "    B->>B: traverse ParserTreeNode",
        "    B->>B: new RuleBlockNode and OperatorNode",
        "    B-->>P: RuleBlockNode",
        "    P->>T: new AbstractSyntaxTree(programName, ruleBlocks)",
        "    P-->>C: getAST()",
        "    C->>R: new RuleBlockLower(ctx), then adapt()",
        "    R->>L: new RuleLower(ruleCtx), then adapt()",
        "    L-->>R: Optional<int[]> row",
        "    R->>M: new MinMaxMethod(rows)",
        "    M-->>R: EvaluationMethod",
        "    R-->>C: Optional<EvaluationMethod>",
    ])


def generate_simulation_package_flowchart():
    """Show test-only simulation imports into production generator packages.

    Edges come from the architecture inventory; this does not imply that the
    production compiler depends on simulation or that imports are runtime calls.
    """
    inventory = build_inventory(ROOT)
    simulation = "utils.simulation"
    test_packages = {p["name"] for p in inventory["packages"] if p["scope"] == "test"}
    if simulation not in test_packages:
        raise ValueError("Simulation test package not found: utils.simulation")

    # A production-to-test import would violate the intended module boundary.
    if any(edge["from_scope"] == "production"
           and edge["to_scope"] == "test"
           and edge["to_package"] == simulation
           for edge in inventory["dependencies"]):
        raise ValueError("Production code must not import utils.simulation")

    edges = {
        (edge["from_package"], edge["to_package"])
        for edge in inventory["dependencies"]
        if edge["from_scope"] == "test"
        and edge["from_package"] == simulation
        and edge["to_scope"] == "production"
        and (edge["to_package"] == "compiler.generator"
             or edge["to_package"].startswith("compiler.generator."))
    }
    if not edges:
        raise ValueError("Simulation test package has no production generator imports")

    used = sorted({pkg for edge in edges for pkg in edge})
    ids = {pkg: pkg.replace(".", "_") for pkg in used}
    lines = ["flowchart LR"]
    lines += [f'    {ids[pkg]}["{pkg}"]' for pkg in used]
    lines += [f"    {ids[src]} --> {ids[dst]}" for src, dst in sorted(edges)]
    return "\n".join(lines)


def generate_simulation_class_diagram():
    """Simulator, comparison, and MIN-MAX helper associations read from the Java sources."""
    simulation_dir = ROOT / "src" / "test" / "java" / "utils" / "simulation"
    sources = {str(path): path.read_text(encoding="utf-8")
               for path in simulation_dir.glob("*.java") if path.name != "package-info.java"}
    all_sources = {str(path): path.read_text(encoding="utf-8")
                   for source_root in (SRC_DIR, ROOT / "src" / "test" / "java")
                   for path in source_root.rglob("*.java") if path.name != "package-info.java"}
    selected = {
        "utils_simulation": ["ReferenceSimulator", "QuantizedSimulator", "SimulationComparison",
                             "OutputComparison", "MinMaxEvaluation"],
        "compiler_generator": ["ControllerModel", "Variable"],
        "compiler_generator_evaluation": ["EvaluationMethod", "MinMaxMethod"],
        "compiler_generator_defuzzification": ["CentroidDefuzzifier"],
        "compiler_generator_fuzzysets": ["FuzzySet", "SingletonFuzzySet"],
    }
    members = {
        "ReferenceSimulator": [
            "-model ControllerModel",
            "+ReferenceSimulator(ControllerModel)",
            "+evaluate(double...) double[]",
            "-centroid(Variable, double[]) double",
        ],
        "QuantizedSimulator": [
            "-model ControllerModel",
            "-degreeTables double[][][]",
            "+QuantizedSimulator(ControllerModel)",
            "+evaluate(int...) OptionalInt[]",
            "-centroid(Variable, double[]) OptionalInt",
        ],
        "SimulationComparison": [
            "<<utility>>",
            "+compare(ControllerModel, int) List~OutputComparison~",
            "-next(int[], int, int) boolean",
        ],
        "OutputComparison": [
            "-output String",
            "-evaluated long",
            "-maxError double",
            "-worstInputs int[]",
            "-meanError double",
            "-undefinedInHardware long",
            "-undefinedInReference long",
            "+output() String",
            "+evaluated() long",
            "+maxError() double",
            "+worstInputs() int[]",
            "+meanError() double",
            "+undefinedInHardware() long",
            "+undefinedInReference() long",
        ],
        "MinMaxEvaluation": [
            "<<utility>>",
            "~requireSimulable(ControllerModel) void",
            "~outputDegrees(ControllerModel, double[][]) double[][]",
        ],
    }
    evidence = {
        "ReferenceSimulator": [
            "private final ControllerModel model;",
            "MinMaxEvaluation.requireSimulable(model);",
            "MinMaxEvaluation.outputDegrees(model, inputDegrees);",
            "sets.get(term).degree(inputs[input])",
        ],
        "QuantizedSimulator": [
            "private final ControllerModel model;",
            "MinMaxEvaluation.requireSimulable(model);",
            "MinMaxEvaluation.outputDegrees(model, inputDegrees);",
            "sets.get(term).quantizedDegree(x, range)",
        ],
        "SimulationComparison": [
            "new QuantizedSimulator(model)",
            "new ReferenceSimulator(model)",
            "new OutputComparison(output, evaluated",
        ],
        "OutputComparison": [
            "private final double maxError;",
            "public long undefinedInHardware()",
        ],
        "MinMaxEvaluation": [
            "model.evaluationMethod() instanceof MinMaxMethod",
            "model.defuzzifier(model.outputs().get(i).name()) instanceof CentroidDefuzzifier",
            "rules.set(row, model.inputColumn(input))",
        ],
    }
    known = {name for names in selected.values() for name in names}
    for name in known:
        matches = [(path, content) for path, content in all_sources.items()
                   if Path(path).stem == name]
        if len(matches) != 1 or not re.search(
                r'\b(?:class|interface|enum)\s+' + re.escape(name) + r'\b', matches[0][1]):
            raise ValueError(f"Missing simulation type: {name}")
    by_name = {Path(path).stem: content for path, content in sources.items()}
    for name, snippets in evidence.items():
        for snippet in snippets:
            if snippet not in by_name[name]:
                raise ValueError(f"Simulation class source changed: {name}: {snippet}")

    lines = ["classDiagram"]
    for namespace, names in selected.items():
        lines.append(f"    namespace {namespace} {{")
        for name in names:
            if members.get(name):
                lines.append(f"        class {name} {{")
                lines += [f"            {member}" for member in members[name]]
                lines.append("        }")
            else:
                lines.append(f"        class {name}")
        lines.append("    }")

    lines += [
        "    QuantizedSimulator --> ControllerModel : model",
        "    ReferenceSimulator --> ControllerModel : model",
        "    QuantizedSimulator --> Variable : inputs and outputs",
        "    ReferenceSimulator --> Variable : inputs and outputs",
        "    SimulationComparison --> QuantizedSimulator : creates",
        "    SimulationComparison --> ReferenceSimulator : creates",
        "    SimulationComparison ..> OutputComparison : returns comparisons",
        "    SimulationComparison ..> ControllerModel : compares",
        "    QuantizedSimulator ..> MinMaxEvaluation : evaluates rules",
        "    ReferenceSimulator ..> MinMaxEvaluation : evaluates rules",
        "    QuantizedSimulator ..> FuzzySet : quantizedDegree",
        "    ReferenceSimulator ..> FuzzySet : degree",
        "    QuantizedSimulator ..> SingletonFuzzySet : centroid values",
        "    ReferenceSimulator ..> SingletonFuzzySet : centroid values",
        "    MinMaxEvaluation --> ControllerModel : reads inputs and outputs",
        "    MinMaxEvaluation ..> EvaluationMethod : iterates rule rows",
        "    MinMaxEvaluation ..> MinMaxMethod : requires",
        "    MinMaxEvaluation ..> CentroidDefuzzifier : requires",
    ]
    return "\n".join(lines)


def generate_simulation_comparison_sequence():
    """SimulationComparison.compare flow checked against the simulation Java sources."""
    simulation_dir = ROOT / "src" / "test" / "java" / "utils" / "simulation"
    comparison = (simulation_dir / "SimulationComparison.java").read_text(encoding="utf-8")
    quantized = (simulation_dir / "QuantizedSimulator.java").read_text(encoding="utf-8")
    reference = (simulation_dir / "ReferenceSimulator.java").read_text(encoding="utf-8")
    evaluation = (simulation_dir / "MinMaxEvaluation.java").read_text(encoding="utf-8")
    for snippet, text in [
            ("new QuantizedSimulator(model)", comparison),
            ("new ReferenceSimulator(model)", comparison),
            ("hardware.evaluate(inputs)", comparison),
            ("reference.evaluate(realInputs)", comparison),
            ("accumulators[output].add(inputs, generated[output], ideal[output]);", comparison),
            ("} while (next(inputs, step, model.converterRange()));", comparison),
            ("accumulators[output].result(model.outputs().get(output).name())", comparison),
            ("return new OutputComparison(output, evaluated", comparison),
            ("MinMaxEvaluation.requireSimulable(model);", quantized),
            ("MinMaxEvaluation.outputDegrees(model, inputDegrees);", quantized),
            ("sets.get(term).quantizedDegree(x, range)", quantized),
            ("(numerator / denominator) & model.converterRange()", quantized),
            ("MinMaxEvaluation.requireSimulable(model);", reference),
            ("MinMaxEvaluation.outputDegrees(model, inputDegrees);", reference),
            ("sets.get(term).degree(inputs[input])", reference),
            ("model.evaluationMethod() instanceof MinMaxMethod", evaluation),
    ]:
        if snippet not in text:
            raise ValueError(f"Simulation sequence source changed: {snippet}")
    return "\n".join([
        "sequenceDiagram",
        "    participant C as utils.simulation.SimulationComparison",
        "    participant Q as utils.simulation.QuantizedSimulator",
        "    participant R as utils.simulation.ReferenceSimulator",
        "    participant M as utils.simulation.MinMaxEvaluation",
        "    participant F as compiler.generator.fuzzysets.FuzzySet",
        "    participant A as SimulationComparison.Accumulator",
        "    participant O as utils.simulation.OutputComparison",
        "",
        "    C->>Q: new QuantizedSimulator(model)",
        "    Q->>M: requireSimulable(model)",
        "    Q->>F: quantizedDegree(x, range)",
        "    C->>R: new ReferenceSimulator(model)",
        "    R->>M: requireSimulable(model)",
        "    loop Input grid advanced like an odometer",
        "        C->>Q: evaluate(inputs)",
        "        Q->>M: outputDegrees(model, inputDegrees)",
        "        Q-->>C: OptionalInt[] generated",
        "        C->>R: evaluate(realInputs)",
        "        R->>F: degree(inputValue)",
        "        R->>M: outputDegrees(model, inputDegrees)",
        "        R-->>C: double[] ideal",
        "        loop Every output",
        "            C->>A: add(inputs, generated, ideal)",
        "        end",
        "        C->>C: next(inputs, step, converterRange)",
        "    end",
        "    loop Every output",
        "        C->>A: result(outputName)",
        "        A->>O: new OutputComparison(...)",
        "        A-->>C: OutputComparison",
        "    end",
        "    C-->>C: List of OutputComparison",
    ])


def generate_compiler_package_flowchart():
    """Production imports of the compiler facade and its CLI entry points.

    The default-package Main.java is not represented by the package inventory,
    so its delegation to api.Application is checked directly against source.
    """
    inventory = build_inventory(ROOT)
    packages = {
        item["name"] for item in inventory["packages"]
        if item["scope"] == "production"
    }
    if not {"api", "compiler"}.issubset(packages):
        raise ValueError("Missing production entry-point packages: api or compiler")

    edges = {
        (item["from_package"], item["to_package"])
        for item in inventory["dependencies"]
        if item["from_scope"] == "production"
        and item["to_scope"] == "production"
        and item["from_package"] in {"api", "compiler"}
        and item["to_package"] != item["from_package"]
    }
    if ("api", "compiler") not in edges:
        raise ValueError("api.Application no longer imports compiler")
    if not any(source == "compiler" for source, _ in edges):
        raise ValueError("compiler.FlcCompiler has no production imports")

    main_source = (SRC_DIR / "Main.java").read_text(encoding="utf-8")
    if ("import api.Application;" not in main_source
            or "Application.run(args, System.out, System.err)" not in main_source):
        raise ValueError("Main.java no longer delegates to api.Application")

    used = sorted({package for edge in edges for package in edge})
    ids = {package: package.replace(".", "_") for package in used}
    lines = ["flowchart LR"]
    lines += [f'    {ids[package]}["{package}"]' for package in used]
    lines += ['    main["Main.java"]']
    lines += [f"    {ids[source]} --> {ids[target]}"
              for source, target in sorted(edges)]
    lines.append("    main --> api")
    return "\n".join(lines)


def generate_compiler_class_diagram():
    """Classes in the current CLI-to-compiler pipeline, verified against Java."""
    selected = {
        "api": {"Application": "api/Application.java"},
        "compiler": {
            "FlcCompiler": "compiler/FlcCompiler.java",
            "GeneratedFile": "compiler/GeneratedFile.java",
        },
        "compiler_ast": {"AbstractSyntaxTree": "compiler/ast/AbstractSyntaxTree.java"},
        "compiler_generator": {
            "CodeGenerator": "compiler/generator/CodeGenerator.java",
            "ControllerModel": "compiler/generator/ControllerModel.java",
        },
        "compiler_lexer": {"Lexer": "compiler/lexer/Lexer.java"},
        "compiler_generator_lowers": {
            "ControllerLower": "compiler/generator/lowers/ControllerLower.java",
        },
        "compiler_parser": {"Parser": "compiler/parser/Parser.java"},
        "compiler_repository": {
            "DiagnosticsHandler": "compiler/repository/DiagnosticsHandler.java",
            "SymbolTable": "compiler/repository/SymbolTable.java",
        },
    }
    members = {
        "Application": [
            "+CODE_OK int", "+CODE_COMPILER_ERR int",
            "+CODE_ARGUMENT_ERR int", "+CODE_INTERNAL_ERR int",
            "+run(String[], PrintStream, PrintStream) int",
            "-getProgramName(Path) String",
        ],
        "FlcCompiler": [
            "-sourceName String", "-sourceCode Reader", "-report String",
            "+FlcCompiler(String, Reader)",
            "+compile() List~GeneratedFile~", "+log() String",
        ],
        "GeneratedFile": [
            "-name String", "-content String", "+name() String",
            "+content() String", "+equals(Object) boolean",
            "+hashCode() int",
        ],
    }
    evidence = {
        "Application": [
            "public static int run(final String[] args",
            "new FlcCompiler(sourceName, sourceCode)",
            "files = compiler.compile()",
            "Files.write(target, file.content().getBytes(StandardCharsets.UTF_8))",
        ],
        "FlcCompiler": [
            "public FlcCompiler(final String sourceName, final Reader sourceCode)",
            "public List<GeneratedFile> compile()",
            "new SymbolTable()", "new DiagnosticsHandler()",
            "new Lexer(sourceCode, symbolTable, diagnostics)",
            "new Parser(lexer, symbolTable, diagnostics)",
            "AbstractSyntaxTree root = parser.getAST()",
            "ControllerLower.newInstance(symbolTable, root, diagnostics).adapt()",
            "new CodeGenerator(model.get())",
            'new GeneratedFile(sourceName + "_out.cpp"',
            "public String log()",
        ],
        "GeneratedFile": [
            "public String name()", "public String content()",
            "public boolean equals(final Object other)",
        ],
    }
    sources = {}
    for classes in selected.values():
        for name, relative_path in classes.items():
            source_path = SRC_DIR / relative_path
            if not source_path.is_file():
                raise ValueError(f"Missing compiler type: {relative_path}")
            source = source_path.read_text(encoding="utf-8")
            if not re.search(r'\b(?:class|interface|enum)\s+' + re.escape(name) + r'\b', source):
                raise ValueError(f"Missing compiler type declaration: {relative_path}")
            sources[name] = source
    for name, snippets in evidence.items():
        for snippet in snippets:
            if snippet not in sources[name]:
                raise ValueError(f"Compiler class source changed: {name}: {snippet}")

    lines = ["classDiagram"]
    for namespace, classes in selected.items():
        lines.append(f"    namespace {namespace} {{")
        for name in classes:
            if name in members:
                lines.append(f"        class {name} {{")
                lines.extend(f"            {member}" for member in members[name])
                lines.append("        }")
            else:
                lines.append(f"        class {name}")
        lines.append("    }")

    lines += [
        "    Application ..> FlcCompiler : creates",
        "    Application ..> GeneratedFile : writes",
        "    FlcCompiler ..> DiagnosticsHandler : creates and reports",
        "    FlcCompiler ..> Lexer : creates",
        "    FlcCompiler ..> Parser : creates",
        "    FlcCompiler ..> SymbolTable : creates",
        "    FlcCompiler ..> AbstractSyntaxTree : reads",
        "    FlcCompiler ..> ControllerLower : lowers",
        "    FlcCompiler ..> ControllerModel : consumes",
        "    FlcCompiler ..> CodeGenerator : generates",
        "    FlcCompiler ..> GeneratedFile : returns",
        "    Parser --> Lexer : reads tokens",
        "    Parser --> SymbolTable : publishes declarations",
        "    ControllerLower ..> ControllerModel : builds",
        "    CodeGenerator --> ControllerModel : uses",
    ]
    return "\n".join(lines)


def generate_compiler_pipeline_sequence():
    """CLI-to-C++ pipeline, with call sites checked against the current Java API."""
    main = (SRC_DIR / "Main.java").read_text(encoding="utf-8")
    application = (SRC_DIR / "api" / "Application.java").read_text(encoding="utf-8")
    compiler = (SRC_DIR / "compiler" / "FlcCompiler.java").read_text(encoding="utf-8")
    evidence = [
        ("Main.java", main, [
            "import api.Application;",
            "Application.run(args, System.out, System.err)",
        ]),
        ("api/Application.java", application, [
            "Files.newBufferedReader(program, StandardCharsets.UTF_8)",
            "new FlcCompiler(sourceName, sourceCode)",
            "files = compiler.compile()",
            "compiler.log()",
            "if (files.isEmpty())",
            "Files.createDirectories(outputDirectory)",
            "Files.write(target, file.content().getBytes(StandardCharsets.UTF_8))",
            "catch (IOException e)",
        ]),
        ("compiler/FlcCompiler.java", compiler, [
            "new SymbolTable()",
            "new DiagnosticsHandler()",
            "new Lexer(sourceCode, symbolTable, diagnostics)",
            "new Parser(lexer, symbolTable, diagnostics)",
            "!parser.parse() || diagnostics.hasErrors()",
            "AbstractSyntaxTree root = parser.getAST()",
            "ControllerLower.newInstance(symbolTable, root, diagnostics).adapt()",
            "if (!model.isPresent())",
            "new CodeGenerator(model.get())",
            "generator.generateCpp()",
            "generator.generateHeader()",
            "generator.generateTestBench(header)",
            "new GeneratedFile(sourceName + \"_out.cpp\"",
        ]),
    ]
    for source_name, content, snippets in evidence:
        for snippet in snippets:
            if snippet not in content:
                raise ValueError(f"Compiler sequence source changed: {source_name}: {snippet}")

    return "\n".join([
        "sequenceDiagram",
        "    participant Main as Main",
        "    participant CLI as api.Application",
        "    participant C as compiler.FlcCompiler",
        "    participant S as compiler.repository.SymbolTable",
        "    participant D as compiler.repository.DiagnosticsHandler",
        "    participant L as compiler.lexer.Lexer",
        "    participant P as compiler.parser.Parser",
        "    participant R as compiler.generator.lowers.ControllerLower",
        "    participant G as compiler.generator.CodeGenerator",
        "    participant F as compiler.GeneratedFile",
        "",
        "    Main->>CLI: run(args, out, err)",
        "    alt invalid arguments or source cannot be read",
        "        CLI-->>Main: CODE_ARGUMENT_ERR",
        "    else source opened",
        "        CLI->>C: new FlcCompiler(sourceName, sourceCode)",
        "        CLI->>C: compile()",
        "        C->>S: new SymbolTable()",
        "        C->>D: new DiagnosticsHandler()",
        "        C->>L: new Lexer(sourceCode, symbolTable, diagnostics)",
        "        C->>P: new Parser(lexer, symbolTable, diagnostics)",
        "        C->>P: parse()",
        "        alt parse failed or diagnostics has errors",
        "            C-->>CLI: empty List",
        "        else parser succeeded",
        "            C->>P: getAST()",
        "            P-->>C: AbstractSyntaxTree",
        "            C->>R: newInstance(symbolTable, root, diagnostics).adapt()",
        "            alt model absent",
        "                C-->>CLI: empty List",
        "            else model present",
        "                C->>G: new CodeGenerator(model)",
        "                C->>G: generateCpp(), generateHeader(), generateTestBench(header)",
        "                G-->>C: generated C++ content",
        "                C->>F: new GeneratedFile(name, content) for each output",
        "                C-->>CLI: List of GeneratedFile",
        "            end",
        "        end",
        "        CLI->>C: log()",
        "        alt no generated files",
        "            CLI-->>Main: CODE_COMPILER_ERR",
        "        else generated files available",
        "            CLI->>CLI: createDirectories and write files",
        "            alt write failed",
        "                CLI-->>Main: CODE_ARGUMENT_ERR",
        "            else write succeeded",
        "                CLI-->>Main: CODE_OK",
        "            end",
        "        end",
        "    end",
    ])


DIAGRAMS = {
    "package_dependencies": generate_package_flowchart,
    "ast_package_flowchart": generate_ast_package_flowchart,
    "ast_class_diagram": generate_ast_class_diagram,
    "ast_construction_sequence": generate_ast_construction_sequence,
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
    "lowering_package_flowchart": generate_lowering_package_flowchart,
    "lowering_class_diagram": generate_lowering_class_diagram,
    "lowering_controller_sequence": generate_lowering_controller_sequence,
    "simulation_package_flowchart": generate_simulation_package_flowchart,
    "simulation_class_diagram": generate_simulation_class_diagram,
    "simulation_comparison_sequence": generate_simulation_comparison_sequence,
    "compiler_package_flowchart": generate_compiler_package_flowchart,
    "compiler_class_diagram": generate_compiler_class_diagram,
    "compiler_pipeline_sequence": generate_compiler_pipeline_sequence,
}


def main():
    parser = argparse.ArgumentParser(description="Generate mermaid diagrams from source code")
    parser.add_argument("--list", action="store_true", help="List available diagrams")
    parser.add_argument("scope", nargs="?", default="all",
                        help="Scope: all|lexer|parser|ast|utils|generator|lowers|simulation|compiler|architecture")
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
    elif scope == "ast":
        targets = ["ast_package_flowchart", "ast_class_diagram", "ast_construction_sequence"]
    elif scope == "utils":
        targets = ["utils_class_diagram", "diagnostics_hierarchy", "symboltable_storage"]
    elif scope == "generator":
        targets = ["codegen_package_flowchart", "codegen_class_diagram", "codegen_controller_sequence"]
    elif scope == "lowers":
        targets = ["lowering_package_flowchart", "lowering_class_diagram", "lowering_controller_sequence"]
    elif scope == "simulation":
        targets = ["simulation_package_flowchart", "simulation_class_diagram",
                   "simulation_comparison_sequence"]
    elif scope == "compiler":
        targets = ["compiler_package_flowchart", "compiler_class_diagram",
                   "compiler_pipeline_sequence"]
    elif scope == "architecture":
        targets = ["package_dependencies", "lexer_parser_sequence", "architecture_evolution"]
    else:
        # Try exact match
        if scope in DIAGRAMS:
            targets = [scope]
        else:
            print(f"Unknown scope: {scope}")
            print(f"Available: all, lexer, parser, ast, utils, generator, lowers, simulation, compiler, architecture")
            print(f"Or specific: {', '.join(DIAGRAMS.keys())}")
            return 2

    if args.list:
        for name in DIAGRAMS:
            print(name)
        return 0

    print(f"Generating diagrams for scope: {scope}")
    generated = []
    failed = []

    for name in targets:
        if name not in DIAGRAMS:
            print(f"  ! unknown diagram: {name}")
            failed.append(name)
            continue
        try:
            mermaid_content = DIAGRAMS[name]()
            output_file = DIAGRAMS_DIR / f"{name}.mmd"
            output_file.write_text(mermaid_content)
            generated.append(str(output_file.relative_to(ROOT)))
            print(f"  -> {output_file.relative_to(ROOT)}")
        except Exception as e:
            failed.append(name)
            print(f"  ! Error generating {name}: {e}")

    print(f"\nGenerated {len(generated)}/{len(targets)} diagrams in doc/diagrams/")
    if failed:
        print(f"Failed diagrams ({len(failed)}): {', '.join(failed)}")
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
