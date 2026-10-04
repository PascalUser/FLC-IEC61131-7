#!/usr/bin/env python3
"""
Documentation validator for FLC-IEC61131-7 compiler.
Validates all Markdown files in doc/ against style rules.
Called by doc-validator agent and integrated into markdown-producing agents.
"""
import re
import sys
import subprocess
from pathlib import Path

ROOT = Path(__file__).parent.parent
DOC_DIR = ROOT / "doc"
ASSETS_DIR = DOC_DIR / "assets"
DIAGRAMS_DIR = DOC_DIR / "assets" / "rendered_diagrams"
DIAGRAMS_SRC_DIR = DOC_DIR / "diagrams"
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
    if not DIAGRAMS_SRC_DIR.exists():
        return []
    return sorted(DIAGRAMS_SRC_DIR.glob("*.mmd"))


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


def validate_no_parentheses_in_headings(content, filepath):
    """Check for parentheses, brackets, braces in headings (skip thesis chapters)"""
    violations = []
    # Skip validation for thesis chapters - they may have parentheses in headings
    if 'thesis' in str(filepath):
        return violations
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


def validate_mermaid_blocks(content, filepath):
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
    
    # Enhanced validation for specific diagram types
    violations.extend(validate_mermaid_class_diagram(content, filepath))
    violations.extend(validate_mermaid_flowchart(content, filepath))
    violations.extend(validate_thesis_image_paths(content, filepath))
    violations.extend(validate_mermaid_relationships(content, filepath))
    
    return violations


def validate_no_lucainfo(content):
    """Check for LucaInfo references (should be excluded)"""
    violations = []
    # Allow if mentioned as deprecated/filtered in documentation
    if 'LucaInfo' in content and 'deprecated' not in content.lower() and 'filtered' not in content.lower():
        # Allow if mentioned as deprecated/filtered in documentation tables
        lines = content.split('\n')
        for i, line in enumerate(lines, 1):
            if 'LucaInfo' in line and 'deprecated' not in line.lower() and 'filtered' not in line.lower():
                # Check if it's in a documentation table (contains | and |)
                if '|' in line and line.count('|') >= 2:
                    continue  # It's in a documentation table, allow it
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


def validate_mermaid_class_diagram(content, filepath):
    """Validate classDiagram specific syntax rules"""
    violations = []
    lines = content.split('\n')
    in_class_diagram = False
    in_namespace = False
    namespace_stack = []
    mermaid_start_line = 0
    is_mmd_file = filepath.suffix == '.mmd'
    
    for i, line in enumerate(lines, 1):
        stripped = line.strip()
        
        # For .mmd files, the whole file is the diagram
        if is_mmd_file and i == 1:
            if stripped.startswith('classDiagram'):
                in_class_diagram = True
                mermaid_start_line = 1
        else:
            # For Markdown files, detect mermaid blocks
            if stripped.startswith('```mermaid'):
                in_class_diagram = False
                mermaid_start_line = i
                continue
            elif stripped == '```' and mermaid_start_line > 0:
                mermaid_start_line = 0
                continue
                
            if mermaid_start_line == 0:
                continue
                
            # Detect classDiagram type
            if stripped.startswith('classDiagram'):
                in_class_diagram = True
                continue
        
        if not in_class_diagram:
            continue
            
        # Track namespace blocks
        ns_match = re.match(r'^\s*namespace\s+([\w.]+)\s*\{', stripped)
        if ns_match:
            ns_name = ns_match.group(1)
            # Check for dots in namespace name
            if '.' in ns_name:
                violations.append(f"  Line {i}: Namespace name contains dots: '{ns_name}' - use underscores (e.g., '{ns_name.replace('.', '_')}')")
            in_namespace = True
            namespace_stack.append(ns_name)
            continue
            
        if stripped == '}' and in_namespace:
            in_namespace = False
            namespace_stack.pop()
            continue
            
        # Check for angle-bracket generics (List<String>, Map<String, Type>, etc.)
        if re.search(r'\b(List|Map|Set|Collection|ArrayList|HashMap|LinkedList)\s*<[^>]+>', stripped):
            violations.append(f"  Line {i}: Angle-bracket generics not allowed in classDiagram - use tildes (e.g., List~String~): {stripped[:80]}")
            
        # Check for inheritance/relationships inside namespace
        if in_namespace and re.search(r'<\|--|\*--|o--|--\*|--o|\.\.>', stripped):
            violations.append(f"  Line {i}: Inheritance/relationship inside namespace - move outside with qualified names: {stripped[:80]}")
            
        # Check for class/enum/interface declarations without keyword
        # Pattern: "ClassName {" or "ClassName {"
        if in_namespace and re.match(r'^\s+[A-Z][a-zA-Z0-9_]*\s*\{', stripped):
            # Check if it has class/enum/interface keyword
            if not re.match(r'^\s+(class|enum|interface)\s+', stripped):
                class_name = stripped.split('{')[0].strip()
                violations.append(f"  Line {i}: Missing 'class'/'enum'/'interface' keyword before '{class_name}'")
                
    return violations


def validate_mermaid_flowchart(content, filepath):
    """Validate flowchart/graph specific syntax rules"""
    violations = []
    lines = content.split('\n')
    in_flowchart = False
    mermaid_start_line = 0
    is_mmd_file = filepath.suffix == '.mmd'
    
    for i, line in enumerate(lines, 1):
        stripped = line.strip()
        
        # For .mmd files, the whole file is the diagram
        if is_mmd_file and i == 1:
            if stripped.startswith('flowchart') or stripped.startswith('graph '):
                in_flowchart = True
                mermaid_start_line = 1
        else:
            # For Markdown files, detect mermaid blocks
            if stripped.startswith('```mermaid'):
                in_flowchart = False
                mermaid_start_line = i
                continue
            elif stripped == '```' and mermaid_start_line > 0:
                mermaid_start_line = 0
                continue
                
            if mermaid_start_line == 0:
                continue
                
            # Detect flowchart/graph type
            if stripped.startswith('flowchart') or stripped.startswith('graph '):
                in_flowchart = True
                continue
            
        if not in_flowchart:
            continue
            
        # Check for node labels with literal newlines inside quoted strings
        # Pattern: ["text\ntext"] - should use <br>
        quoted_bracket_matches = list(re.finditer(r'\["([^"]*)"]', stripped))
        for match in quoted_bracket_matches:
            label = match.group(1)
            if '\n' in label:
                violations.append(f"  Line {i}: Flowchart node label contains literal newline - use <br> instead: {match.group(0)[:80]}")
            
        # Check for node labels with parentheses that are not quoted
        # Pattern: A[Label (with parens)] - should be A["Label (with parens)"]
        bracket_matches = list(re.finditer(r'\[([^]]*)]', stripped))
        for match in bracket_matches:
            label = match.group(1)
            # Check if label contains parentheses and is not already quoted
            if '(' in label and ')' in label:
                # Check if the whole [...] is already quoted like ["..."]
                full_match = match.group(0)
                if not (full_match.startswith('["') and full_match.endswith('"]')):
                    violations.append(f"  Line {i}: Flowchart node label with parentheses must be quoted: {full_match} -> use {{\"Label (text)\"}}")
                    
    return violations


def validate_thesis_image_paths(content, filepath):
    """Validate that thesis chapters use relative paths for assets"""
    violations = []
    if 'thesis' not in str(filepath):
        return violations
        
    lines = content.split('\n')
    for i, line in enumerate(lines, 1):
        # Check for Markdown image syntax with doc/assets/ (should be ../assets/)
        # Pattern: ![...](doc/assets/...)
        if re.search(r'!\[.*]\(doc/assets/', line):
            violations.append(f"  Line {i}: Thesis image path uses 'doc/assets/' - should use '../assets/': {line.strip()[:100]}")
            
    return violations


def validate_mermaid_relationships(content, filepath):
    """Validate classDiagram relationships - no dots allowed in relationship identifiers"""
    violations = []
    lines = content.split('\n')
    in_class_diagram = False
    mermaid_start_line = 0
    is_mmd_file = filepath.suffix == '.mmd'
    
    for i, line in enumerate(lines, 1):
        stripped = line.strip()
        
        # For .mmd files, the whole file is the diagram
        if is_mmd_file and i == 1:
            if stripped.startswith('classDiagram'):
                in_class_diagram = True
                mermaid_start_line = 1
        else:
            # For Markdown files, detect mermaid blocks
            if stripped.startswith('```mermaid'):
                in_class_diagram = False
                mermaid_start_line = i
                continue
            elif stripped == '```' and mermaid_start_line > 0:
                mermaid_start_line = 0
                continue
                
            if mermaid_start_line == 0:
                continue
                
            # Detect classDiagram type
            if stripped.startswith('classDiagram'):
                in_class_diagram = True
                continue
        
        if not in_class_diagram:
            continue
            
        # Check for dots in relationship identifiers
        # Pattern: namespace.Class <|-- namespace.Class or Class <-- namespace.Class etc.
        # Match any identifier with a dot followed by relationship operator
        rel_pattern = r'\b\w+\.\w+\s+(<\|--|-->\|\.\.>|--\*|--o|o--|<--|-->|--)\s+\w+\b'
        matches = list(re.finditer(rel_pattern, stripped))
        for match in matches:
            violations.append(f"  Line {i}: Mermaid classDiagram relationship contains dots (not allowed): {match.group(0).strip()} -> use bare class names (e.g., 'Diagnostic <|-- Error')")
            
        # Also check for qualified names on right side only
        rel_pattern2 = r'\b\w+\s+(<\|--|-->\|\.\.>|--\*|--o|o--|<--|-->|--)\s+\w+\.\w+\b'
        matches2 = list(re.finditer(rel_pattern2, stripped))
        for match in matches2:
            violations.append(f"  Line {i}: Mermaid classDiagram relationship contains dots (not allowed): {match.group(0).strip()} -> use bare class names (e.g., 'Diagnostic <|-- Error')")
            
    return violations


def validate_markdown_tables(content):
    """Validate Markdown tables are well-formed"""
    violations = []
    lines = content.split('\n')
    in_table = False
    table_start_line = 0
    expected_cols = 0
    
    for i, line in enumerate(lines, 1):
        stripped = line.strip()
        
        # Detect table start (line with pipes)
        if '|' in stripped and not in_table:
            # Check if next line is a separator row
            if i + 1 < len(lines):
                next_line = lines[i].strip()
                # Separator row has multiple segments: |---|---|---|
                if re.match(r'^\|([\s\-:]+\|)+$', next_line):
                    # Check for blank line before table (required by Markdown spec)
                    if i > 1 and lines[i-2].strip() != '':
                        violations.append(f"  Line {i}: Table missing blank line before it - add empty line before table header")
                    in_table = True
                    table_start_line = i
                    expected_cols = stripped.count('|') - 1 if stripped.startswith('|') and stripped.endswith('|') else stripped.count('|')
                    continue
        
        if in_table:
            # Check for end of table (blank line or non-table line)
            if not stripped or '|' not in stripped:
                in_table = False
                continue
                
            # Count columns in this row
            if stripped.startswith('|') and stripped.endswith('|'):
                cols = stripped.count('|') - 1
            else:
                cols = stripped.count('|')
                
            if cols != expected_cols:
                violations.append(f"  Line {i}: Table row has {cols} columns, expected {expected_cols} (table started at line {table_start_line})")
                
            # Check separator row format - allow multiple column segments
            if i == table_start_line + 1:
                if not re.match(r'^\|([\s\-:]+\|)+$', stripped):
                    violations.append(f"  Line {i}: Table separator row malformed: {stripped[:80]}")
                    
    return violations


def validate_mermaid_with_mmdc(content):
    """Validate mermaid syntax by attempting to render with mmdc (comprehensive check)"""
    violations = []
    # Extract mermaid blocks
    mermaid_blocks = []
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
            mermaid_text = '\n'.join(mermaid_lines)
            if mermaid_text.strip():
                mermaid_blocks.append((mermaid_text, start_line, i))
        elif in_mermaid:
            mermaid_lines.append(line)
            
    if not mermaid_blocks:
        return violations
        
    # Try to validate each block with mmdc
    for mermaid_text, start_line, end_line in mermaid_blocks:
        try:
            # Write to temp file and run mmdc
            import tempfile
            with tempfile.NamedTemporaryFile(mode='w', suffix='.mmd', delete=False) as f:
                f.write(mermaid_text)
                temp_file = f.name
            
            result = subprocess.run(
                ['./node_modules/.bin/mmdc', '-i', temp_file, '-o', '/dev/null'],
                capture_output=True,
                text=True,
                timeout=30,
                cwd=ROOT
            )
            
            Path(temp_file).unlink(missing_ok=True)
            
            if result.returncode != 0:
                # Extract error message
                error_msg = result.stderr.strip()
                if 'Parse error' in error_msg:
                    # Extract line number from error if possible
                    violations.append(f"  Lines {start_line}-{end_line}: Mermaid parse error: {error_msg.split('Error:')[-1].strip()[:200]}")
                else:
                    violations.append(f"  Lines {start_line}-{end_line}: Mermaid render failed: {error_msg[:200]}")
                    
        except subprocess.TimeoutExpired:
            violations.append(f"  Lines {start_line}-{end_line}: Mermaid validation timeout")
        except Exception as e:
            # If mmdc not available or something else fails, skip but warn
            print(f"Failed to validate with mmdc: {e}")
            pass
            
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
    
    # Enhanced validation for .mmd files (treat as single mermaid block)
    violations.extend(validate_mermaid_class_diagram(content, filepath))
    violations.extend(validate_mermaid_flowchart(content, filepath))
    
    return violations


def validate_file(filepath):
    """Validate a single Markdown file"""
    content = filepath.read_text(encoding='utf-8')
    all_violations = []
    
    all_violations.extend(validate_no_markdown_images(filepath, content))
    all_violations.extend(validate_no_parentheses_in_headings(content, filepath))
    all_violations.extend(validate_png_refs(filepath, content))
    all_violations.extend(validate_mermaid_blocks(content, filepath))
    all_violations.extend(validate_no_lucainfo(content))
    all_violations.extend(validate_heading_order(content))
    all_violations.extend(validate_markdown_tables(content))
    
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