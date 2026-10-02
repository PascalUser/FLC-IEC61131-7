#!/usr/bin/env python3
"""
Synthesizes context from doc/modules/*.md + stats.json + diagrams/*.mmd
into a structured JSON for thesis-writer and readme-writer agents.

Filters out deprecated/local-only content (LucaInfo, Luca.y).

Output: doc/_synthesis_context.json
"""
import re
from pathlib import Path
from typing import Dict, List, Any

ROOT = Path(__file__).parent.parent
MODULES_DIR = ROOT / "doc" / "modules"
DIAGRAMS_DIR = ROOT / "doc" / "diagrams"
STATS_FILE = ROOT / "doc" / "stats.json"
OUTPUT_FILE = ROOT / "doc" / "_synthesis_context.json"

# Classes/files to exclude (deprecated or local-only)
EXCLUDE_PATTERNS = [
    r'LucaInfo',
    r'Luca\.y',
    r'@deprecated',
    r'deprecated',
    r'legacy',
]


def should_exclude(text: str) -> bool:
    """Check if text contains excluded patterns"""
    text_lower = text.lower()
    for pattern in EXCLUDE_PATTERNS:
        if re.search(pattern, text_lower):
            return True
    return False


def read_module_file(module_path: Path) -> str:
    """Read a module Markdown file."""
    return module_path.read_text(encoding="utf-8")


def read_diagram_file(diagram_path: Path) -> str:
    """Read a diagram mermaid file."""
    return diagram_path.read_text(encoding="utf-8")


def extract_mermaid_diagrams(content: str) -> List[Dict[str, str]]:
    """Extract Mermaid diagrams with their preceding context."""
    diagrams = []
    pattern = r"(?:^###?\s+(.+?)\n)?\s*```mermaid\n(.+?)\n```"
    for match in re.finditer(pattern, content, re.MULTILINE | re.DOTALL):
        heading = match.group(1).strip() if match.group(1) else ""
        diagram = match.group(2).strip()
        if not should_exclude(diagram):
            diagrams.append({"title": heading, "content": diagram})
    return diagrams


def extract_class_table(content: str) -> List[Dict[str, str]]:
    """Extract class tables, filtering excluded rows."""
    tables = []
    pattern = r"\|?\s*[^|]+\|[^|]+\|[^|]+\|\n\|?\s*[-:|]+\s*\n((?:\|.*?\|.*?\|.*?\n)+)"
    for match in re.finditer(pattern, content, re.DOTALL):
        header_line = content[match.start():match.start()+200].split('\n')[0]
        header_lower = header_line.lower()
        if not any(kw in header_lower for kw in ["class", "clase", "responsibility", "responsabilidad", "pattern", "patrón", "patron"]):
            continue
        table_rows = match.group(1).strip().split("\n")
        rows = []
        for row in table_rows:
            if not row.strip():
                continue
            cells = [c.strip() for c in row.split("|") if c.strip() or c == ""]
            if cells and not cells[0]:
                cells = cells[1:]
            if cells and not cells[-1]:
                cells = cells[:-1]
            if len(cells) >= 3:
                row_text = " | ".join(cells)
                if not should_exclude(row_text):
                    rows.append({"class": cells[0], "responsibility": cells[1], "pattern": cells[2]})
        if rows:
            tables.append({"type": "class_table", "rows": rows})
    return tables


def extract_chart_references(content: str) -> List[Dict[str, str]]:
    """Extract chart/image references (PNGs from assets/)."""
    charts = []
    pattern = r"!\[([^\]]*)\]\(([^)]+)\)"
    for match in re.finditer(pattern, content):
        alt = match.group(1).strip()
        path = match.group(2).strip()
        start = max(0, match.start() - 200)
        context = content[start:match.start()].strip().split("\n")[-1] if start > 0 else ""
        if not should_exclude(alt) and not should_exclude(path):
            charts.append({"alt": alt, "path": path, "caption": context})
    return charts


def extract_section_prose(content: str) -> Dict[str, str]:
    """Extract prose content by section heading, filtering excluded sections."""
    sections = {}
    parts = re.split(r"\n(#{1,3}\s+.+?)\n", content)
    for i in range(1, len(parts), 2):
        heading = parts[i].strip()
        body = parts[i + 1] if i + 1 < len(parts) else ""
        heading_clean = re.sub(r"^#+\s*", "", heading)
        if not should_exclude(heading_clean) and not should_exclude(body):
            sections[heading_clean] = body.strip()
    return sections


def extract_file_tree(content: str) -> List[str]:
    """Extract file tree from code blocks, filtering excluded entries."""
    trees = []
    pattern = r"```(?:text|bash|plaintext)?\n((?:[│├└─\s\w./#]+\n){3,})```"
    for match in re.finditer(pattern, content):
        tree = match.group(1).strip()
        if not should_exclude(tree):
            trees.append(tree)
    return trees


def extract_how_to_test(content: str) -> List[str]:
    """Extract 'How to test' section content."""
    tests = []
    pattern = r"##\s+How to test\s*\n(.*?)(?=\n## |\n---|\Z)"
    match = re.search(pattern, content, re.DOTALL | re.IGNORECASE)
    if match:
        test_content = match.group(1).strip()
        if not should_exclude(test_content):
            tests.append(test_content)
    return tests


def extract_init_hierarchy(content: str) -> List[Dict[str, str]]:
    """Extract initialization hierarchy table (parser module specific)."""
    inits = []
    pattern = r"\|?\s*Clase\s*\|.*?Uso\s*\|.*?Valor\s*por\s*defecto\s*\|.*?\n\|?\s*[-:|]+\s*\n((?:\|.*?\|.*?\|.*?\n)+)"
    for match in re.finditer(pattern, content, re.IGNORECASE | re.DOTALL):
        table_rows = match.group(1).strip().split("\n")
        for row in table_rows:
            if not row.strip():
                continue
            cells = [c.strip() for c in row.split("|") if c.strip() or c == ""]
            if cells and not cells[0]:
                cells = cells[1:]
            if cells and not cells[-1]:
                cells = cells[:-1]
            if len(cells) >= 3:
                row_text = " | ".join(cells)
                if not should_exclude(row_text):
                    inits.append({"class": cells[0], "use": cells[1], "default": cells[2]})
    return inits


def extract_grammar_coverage(content: str) -> List[Dict[str, str]]:
    """Extract grammar coverage table (parser module specific)."""
    coverage = []
    pattern = r"\|?\s*[^|]+\|[^|]+\|[^|]+\|\n\|?\s*[-:|]+\s*\n((?:\|.*?\|.*?\|.*?\n)+)"
    for match in re.finditer(pattern, content, re.DOTALL):
        header_line = content[match.start():match.start()+200].split('\n')[0]
        header_lower = header_line.lower()
        if not any(kw in header_lower for kw in ["sección", "section", "reglas", "rules", "estado", "status", "cobertura", "coverage"]):
            continue
        table_rows = match.group(1).strip().split("\n")
        for row in table_rows:
            if not row.strip():
                continue
            cells = [c.strip() for c in row.split("|") if c.strip() or c == ""]
            if cells and not cells[0]:
                cells = cells[1:]
            if cells and not cells[-1]:
                cells = cells[:-1]
            if len(cells) >= 3:
                row_text = " | ".join(cells)
                if not should_exclude(row_text):
                    coverage.append({"section": cells[0], "rules": cells[1], "status": cells[2]})
    return coverage


def process_module(module_name: str, content: str) -> Dict[str, Any]:
    """Process a single module file and extract all structured data."""
    # Filter out excluded content from the entire module
    filtered_content = content
    if should_exclude(content):
        # Remove excluded sections (simplified - in practice would need more sophisticated filtering)
        pass
    
    return {
        "module": module_name,
        "mermaid_diagrams": extract_mermaid_diagrams(filtered_content),
        "class_tables": extract_class_table(filtered_content),
        "chart_references": extract_chart_references(filtered_content),
        "sections": extract_section_prose(filtered_content),
        "file_trees": extract_file_tree(filtered_content),
        "how_to_test": extract_how_to_test(filtered_content),
        "initialization_hierarchy": extract_init_hierarchy(filtered_content),
        "grammar_coverage": extract_grammar_coverage(filtered_content),
    }


def load_diagrams() -> Dict[str, str]:
    """Load all mermaid diagrams from doc/diagrams/ and wrap with fences for thesis_view"""
    diagrams = {}
    if DIAGRAMS_DIR.exists():
        for diagram_file in DIAGRAMS_DIR.glob("*.mmd"):
            raw = read_diagram_file(diagram_file)
            # Wrap raw mermaid content with Markdown fences for thesis_view
            diagrams[diagram_file.stem] = f"```mermaid\n{raw.strip()}\n```"
    return diagrams


def load_stats() -> Dict[str, Any]:
    """Load statistics from stats.json"""
    if STATS_FILE.exists():
        with open(STATS_FILE) as f:
            return json.load(f)
    return {}


def build_readme_architecture_summary(modules: Dict[str, Any]) -> Dict[str, Any]:
    """Build condensed architecture summary for README from module data."""
    utils = modules.get("utils", {})
    parser = modules.get("parser", {})
    lexer = modules.get("lexer", {})

    pipeline = """flowchart LR
    SRC[Source .fcl] --> LEX[Lexer\nJFlex]
    LEX -- tokens --> PAR[Parser\nBison LALR1]
    PAR -- publishes --> ST[(SymbolTable)]
    PAR -- reports --> DIAG[DiagnosticsHandler]"""

    key_classes = []
    for mod_name, mod_data in [("utils", utils), ("parser", parser), ("lexer", lexer)]:
        for table in mod_data.get("class_tables", []):
            for row in table.get("rows", []):
                resp = row["responsibility"]
                key_classes.append({
                    "module": mod_name,
                    "class": row["class"],
                    "responsibility": resp[:80] + "..." if len(resp) > 80 else resp
                })

    return {
        "pipeline_mermaid": pipeline,
        "key_classes": key_classes[:10],
        "modules": list(modules.keys())
    }


def main():
    modules_data = {}

    # Process all module files
    if not MODULES_DIR.exists():
        print(f"Modules directory not found: {MODULES_DIR}")
        return

    for module_file in MODULES_DIR.glob("*.md"):
        module_name = module_file.stem
        content = read_module_file(module_file)
        modules_data[module_name] = process_module(module_name, content)
        print(f"Processed module: {module_name}")

    # Load diagrams and stats
    diagrams = load_diagrams()
    stats = load_stats()

    # Build output structure with new thesis chapter mapping
    output = {
        "thesis_view": {
            "modules": modules_data,
            "diagrams": diagrams,
            "stats": stats,
            "module_chapter_mapping": {
                "utils": ["04-arquitectura-implementada", "07-mantenibilidad"],
                "parser": ["06-analisis-sintactico", "07-mantenibilidad"],
                "lexer": ["05-analisis-lexico", "07-mantenibilidad"]
            }
        },
        "readme_view": build_readme_architecture_summary(modules_data),
        "generated_at": __import__("datetime").datetime.now().isoformat()
    }

    # Write output
    OUTPUT_FILE.parent.mkdir(parents=True, exist_ok=True)
    OUTPUT_FILE.write_text(json.dumps(output, indent=2, ensure_ascii=False))
    print(f"Synthesis written to: {OUTPUT_FILE}")


if __name__ == "__main__":
    import json
    main()