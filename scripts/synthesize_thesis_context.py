#!/usr/bin/env python3
"""
Synthesizes context from doc/modules/*.md into a structured JSON
for consumption by thesis-writer and readme-writer agents.

Output: doc/_synthesis_context.json
"""
import json
import re
import os
from pathlib import Path
from typing import Dict, List, Any, Optional

ROOT = Path(__file__).parent.parent
MODULES_DIR = ROOT / "doc" / "modules"
OUTPUT_FILE = ROOT / "doc" / "_synthesis_context.json"


def read_module_file(module_path: Path) -> str:
    """Read a module markdown file."""
    return module_path.read_text(encoding="utf-8")


def extract_mermaid_diagrams(content: str) -> List[Dict[str, str]]:
    """Extract Mermaid diagrams with their preceding context."""
    diagrams = []
    # Find mermaid blocks with optional preceding heading
    pattern = r"(?:^###?\s+(.+?)\n)?\s*```mermaid\n(.+?)\n```"
    for match in re.finditer(pattern, content, re.MULTILINE | re.DOTALL):
        heading = match.group(1).strip() if match.group(1) else ""
        diagram = match.group(2).strip()
        diagrams.append({"title": heading, "content": diagram})
    return diagrams


def extract_class_table(content: str) -> List[Dict[str, str]]:
    """Extract class tables (markdown tables with Class/Responsibility/Pattern columns)."""
    tables = []
    # Find markdown tables - match any 3-column table with header separator
    pattern = r"\|?\s*[^|]+\|[^|]+\|[^|]+\|\n\|?\s*[-:|]+\s*\n((?:\|.*?\|.*?\|.*?\n)+)"
    for match in re.finditer(pattern, content, re.DOTALL):
        # Verify it's a class-like table by checking header
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
                rows.append({"class": cells[0], "responsibility": cells[1], "pattern": cells[2]})
        if rows:
            tables.append({"type": "class_table", "rows": rows})
    return tables


def extract_chart_references(content: str) -> List[Dict[str, str]]:
    """Extract chart/image references with captions."""
    charts = []
    # Find ![alt](path) patterns
    pattern = r"!\[([^\]]*)\]\(([^)]+)\)"
    for match in re.finditer(pattern, content):
        alt = match.group(1).strip()
        path = match.group(2).strip()
        # Get preceding line as caption context
        start = max(0, match.start() - 200)
        context = content[start:match.start()].strip().split("\n")[-1] if start > 0 else ""
        charts.append({"alt": alt, "path": path, "caption": context})
    return charts


def extract_section_prose(content: str) -> Dict[str, str]:
    """Extract prose content by section heading."""
    sections = {}
    # Split by headings
    parts = re.split(r"\n(#{1,3}\s+.+?)\n", content)
    for i in range(1, len(parts), 2):
        heading = parts[i].strip()
        body = parts[i + 1] if i + 1 < len(parts) else ""
        # Clean heading
        heading_clean = re.sub(r"^#+\s*", "", heading)
        sections[heading_clean] = body.strip()
    return sections


def extract_file_tree(content: str) -> List[str]:
    """Extract file tree from code blocks."""
    trees = []
    # Match code blocks that look like file trees (contain box drawing chars or indentation patterns)
    pattern = r"```(?:text|bash|plaintext)?\n((?:[│├└─\s\w./#]+\n){3,})```"
    for match in re.finditer(pattern, content):
        trees.append(match.group(1).strip())
    return trees


def extract_how_to_test(content: str) -> List[str]:
    """Extract 'How to test' section content."""
    tests = []
    # Find section starting with "## How to test" or similar
    pattern = r"##\s+How to test\s*\n(.*?)(?=\n## |\n---|\Z)"
    match = re.search(pattern, content, re.DOTALL | re.IGNORECASE)
    if match:
        tests.append(match.group(1).strip())
    return tests


def extract_init_hierarchy(content: str) -> List[Dict[str, str]]:
    """Extract initialization hierarchy table (parser module specific)."""
    inits = []
    # Match the specific header pattern for initialization hierarchy
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
                coverage.append({"section": cells[0], "rules": cells[1], "status": cells[2]})
    return coverage


def process_module(module_name: str, content: str) -> Dict[str, Any]:
    """Process a single module file and extract all structured data."""
    return {
        "module": module_name,
        "mermaid_diagrams": extract_mermaid_diagrams(content),
        "class_tables": extract_class_table(content),
        "chart_references": extract_chart_references(content),
        "sections": extract_section_prose(content),
        "file_trees": extract_file_tree(content),
        "how_to_test": extract_how_to_test(content),
        "initialization_hierarchy": extract_init_hierarchy(content),
        "grammar_coverage": extract_grammar_coverage(content),
    }


def build_readme_architecture_summary(modules: Dict[str, Any]) -> Dict[str, Any]:
    """Build condensed architecture summary for README from module data."""
    # Extract key components from utils and parser modules
    utils = modules.get("utils", {})
    parser = modules.get("parser", {})
    lexer = modules.get("lexer", {})

    # Build simple pipeline mermaid
    pipeline = """flowchart LR
    SRC[Source .fcl] --> LEX[Lexer\nJFlex]
    LEX -- tokens --> PAR[Parser\nBison LALR1]
    PAR -- publishes --> ST[(SymbolTable)]
    PAR -- reports --> DIAG[DiagnosticsHandler]"""

    # Key classes summary
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
        "key_classes": key_classes[:10],  # Limit for README
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
        print(f"Processed: {module_name}")

    # Build output structure
    output = {
        "thesis_view": {
            "modules": modules_data,
            "module_chapter_mapping": {
                "utils": ["03-architecture"],
                "parser": ["05-syntactic-analysis"],
                "lexer": ["04-lexical-analysis"]
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
    main()