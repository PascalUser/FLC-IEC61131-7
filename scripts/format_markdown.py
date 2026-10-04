#!/usr/bin/env python3
"""Format a single markdown file with remark-cli (uses remark-gfm for tables)"""
import subprocess
import sys
import re
from pathlib import Path

ROOT = Path(__file__).parent.parent

# Pattern to match table separator rows: | --- | :---: | ---: |
SEP_PATTERN = re.compile(r'^\s*\|(\s*:?-+:?\s*\|)+\s*$')

def tighten_separators(md: str) -> str:
    """Convert table separators to compact format while preserving column widths.
    E.g., | ---- | :----: | ----: |  ->  |------|:------:|------:|
    """
    out = []
    for line in md.splitlines():
        if SEP_PATTERN.match(line):
            cells = line.strip().strip('|').split('|')
            new_cells = []
            for c in cells:
                raw = c.strip()
                left = ':' if raw.startswith(':') else ''
                right = ':' if raw.endswith(':') else ''
                dashes = '-' * (len(c) - len(left) - len(right))
                new_cells.append(left + dashes + right)
            line = '|' + '|'.join(new_cells) + '|'
        out.append(line)
    return "\n".join(out) + "\n"

def format_file(filepath):
    # Run remark with gfm for table formatting (with proper alignment)
    result = subprocess.run(
        ["npx", "remark", "--use=remark-gfm", "--output", str(filepath), str(filepath)],
        cwd=ROOT,
        capture_output=True,
        text=True
    )
    if result.returncode != 0:
        print(f"remark failed on {filepath}: {result.stderr}")
        return False
    
    # Post-process: tighten table separators while preserving column widths
    content = filepath.read_text(encoding='utf-8')
    fixed_content = tighten_separators(content)
    if fixed_content != content:
        filepath.write_text(fixed_content, encoding='utf-8')
    
    return True

def main():
    if len(sys.argv) < 2:
        print("Usage: format_markdown.py <file.md>")
        sys.exit(1)
    for f in sys.argv[1:]:
        if not format_file(Path(f)):
            sys.exit(1)
    print(f"✅ Formatted {len(sys.argv)-1} file(s)")

if __name__ == "__main__":
    main()