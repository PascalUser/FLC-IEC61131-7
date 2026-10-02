#!/usr/bin/env python3
"""
Documentation validator for FLC-IEC61131-7 compiler.
Validates all Markdown files in doc/ against style rules.
Called by doc-validator agent and integrated into markdown-producing agents.
"""
import re
import sys
from pathlib import Path

ROOT = Path(__file__).parent.parent
DOC_DIR = ROOT / "doc"
ASSETS_DIR = DOC_DIR / "assets"
DIAGRAMS_DIR = DOC_DIR / "diagrams"
MODULES_DIR = DOC_DIR / "modules"
THESIS_DIR = DOC_DIR / "thesis"


def find_markdown_files():
    """Find all .md files in doc/"""
    md_files = []
    for pattern in ["*.md", "modules/*.md", "thesis/*.md"]:
        md_files.extend(DOC_DIR.glob(pattern))
    return sorted(md_files)


def find_mmd_files():
    """Find all .mmd files in doc/diagrams/"""
    if not DIAGRAMS_DIR.exists():
        return []
    return sorted(DIAGRAMS_DIR.glob("*.mmd"))


def validate_no_markdown_images(filepath, content):
    """Check for ![...](...) Markdown image syntax"""
    violations = []
    # Skip validation for thesis chapters (they are processed by render_mermaid.py)
    if 'thesis' in str(filepath):
        return violations
    for i, line in enumerate(content.split('\n'), 1):
        if re.search(r'!\[.*]\(.*\)', line):
            violations.append(f"  Line {i}: Markdown image syntax found: {line.strip()[:80]}")
    return violations


def validate_no_parentheses_in_headings(content):
    """Check for parentheses, brackets, braces in headings"""
    violations = []
    for i, line in enumerate(content.split('\n'), 1):
        if re.match(r'^#+\s', line):
            if '(' in line or ')' in line:
                violations.append(f"  Line {i}: Parentheses in heading: {line.strip()}")
            if '[' in line or ']' in line:
                violations.append(f"  Line {i}: Brackets in heading: {line.strip()}")
            if '{' in line or '}' in line:
                violations.append(f"  Line {i}: Braces in heading: {line.strip()}")
    return violations


def validate_png_refs(filepath, content):
    """Check that all PNG references in assets/ exist"""
    violations = []
    # Find assets/*.png references
    for match in re.finditer(r'assets/([\w-]+\.png)', content):
        png_name = match.group(1)
        png_path = ASSETS_DIR / png_name
        if not png_path.exists():
            violations.append(f"  Missing PNG: {png_name} referenced in {filepath.name}")
    return violations


def validate_mermaid_blocks(content):
    """Validate mermaid blocks have valid syntax (basic check)"""
    violations = []
    in_mermaid = False
    mermaid_lines = []
    start_line = 0
    
    for i, line in enumerate(content.split('\n'), 1):
        if line.strip().startswith('```mermaid'):
            in_mermaid = True
            mermaid_lines = []
            start_line = i
        elif in_mermaid and line.strip() == '```':
            in_mermaid = False
            # Basic validation
            mermaid_text = '\n'.join(mermaid_lines)
            if not mermaid_text.strip():
                violations.append(f"  Lines {start_line}-{i}: Empty mermaid block")
            # Check for common mermaid diagram types
            first_line = mermaid_lines[0].strip() if mermaid_lines else ""
            valid_starts = ['flowchart', 'graph', 'sequenceDiagram', 'classDiagram', 'stateDiagram', 
                          'erDiagram', 'journey', 'gantt', 'pie', 'bar', 'timeline', 'mindmap',
                          'quadrantChart', 'requirementDiagram', 'gitgraph', 'C4Context']
            if first_line and not any(first_line.startswith(v) for v in valid_starts):
                violations.append(f"  Lines {start_line}-{i}: Unknown mermaid type: {first_line[:50]}")
        elif in_mermaid:
            mermaid_lines.append(line)
    
    if in_mermaid:
        violations.append(f"  Unclosed mermaid block starting at line {start_line}")
    
    return violations


def validate_no_lucainfo(content):
    """Check for LucaInfo references (should be excluded)"""
    violations = []
    if 'LucaInfo' in content and 'deprecated' not in content.lower() and 'filtered' not in content.lower():
        # Allow if mentioned as deprecated/filtered
        for i, line in enumerate(content.split('\n'), 1):
            if 'LucaInfo' in line and 'deprecated' not in line.lower() and 'filtered' not in line.lower():
                violations.append(f"  Line {i}: LucaInfo reference (should be excluded): {line.strip()[:80]}")
    return violations


def validate_heading_order(content):
    """Check that headings follow logical order (no skipping levels)"""
    violations = []
    prev_level = 0
    for i, line in enumerate(content.split('\n'), 1):
        match = re.match(r'^(#+)\s', line)
        if match:
            level = len(match.group(1))
            if level > prev_level + 1 and prev_level > 0:
                violations.append(f"  Line {i}: Heading level jumps from {prev_level} to {level}: {line.strip()}")
            prev_level = level
    return violations


def validate_mmd_file(filepath):
    """Validate a single .mmd file - check first non-empty line is valid diagram type"""
    content = filepath.read_text(encoding='utf-8')
    violations = []
    
    # Find first non-empty line
    first_line = ""
    for line in content.split('\n'):
        stripped = line.strip()
        if stripped:
            first_line = stripped
            break
    
    if not first_line:
        violations.append(f"  Empty .mmd file")
        return violations
    
    valid_starts = ['flowchart', 'graph', 'sequenceDiagram', 'classDiagram', 'stateDiagram', 
                      'erDiagram', 'journey', 'gantt', 'pie', 'bar', 'timeline', 'mindmap',
                      'quadrantChart', 'requirementDiagram', 'gitgraph', 'C4Context']
    
    if not any(first_line.startswith(v) for v in valid_starts):
        violations.append(f"  Invalid diagram type: {first_line[:50]}")
    
    # Ensure no Markdown fences in .mmd file
    if content.strip().startswith('```'):
        violations.append(f"  .mmd file should not contain markdown fences (```)")
    if content.strip().endswith('```'):
        violations.append(f"  .mmd file should not contain markdown fences (```)")
    
    return violations


def validate_file(filepath):
    """Validate a single Markdown file"""
    content = filepath.read_text(encoding='utf-8')
    all_violations = []
    
    all_violations.extend(validate_no_markdown_images(filepath, content))
    all_violations.extend(validate_no_parentheses_in_headings(content))
    all_violations.extend(validate_png_refs(filepath, content))
    all_violations.extend(validate_mermaid_blocks(content))
    all_violations.extend(validate_no_lucainfo(content))
    all_violations.extend(validate_heading_order(content))
    
    return all_violations


def main():
    md_files = find_markdown_files()
    mmd_files = find_mmd_files()
    
    if not md_files and not mmd_files:
        print("No markdown or mermaid files found in doc/")
        return 0
    
    total_violations = 0
    
    if md_files:
        print(f"Validating {len(md_files)} markdown files...")
        for filepath in md_files:
            rel_path = filepath.relative_to(ROOT)
            violations = validate_file(filepath)
            if violations:
                print(f"\n{rel_path}:")
                for v in violations:
                    print(v)
                total_violations += len(violations)
            else:
                print(f"  OK: {rel_path}")
    
    if mmd_files:
        print(f"\nValidating {len(mmd_files)} mermaid diagram files...")
        for filepath in mmd_files:
            rel_path = filepath.relative_to(ROOT)
            violations = validate_mmd_file(filepath)
            if violations:
                print(f"\n{rel_path}:")
                for v in violations:
                    print(v)
                total_violations += len(violations)
            else:
                print(f"  OK: {rel_path}")
    
    print(f"\n{'='*50}")
    if total_violations == 0:
        print("All validation checks passed!")
        return 0
    else:
        print(f"Total violations: {total_violations}")
        return 1


if __name__ == "__main__":
    sys.exit(main())