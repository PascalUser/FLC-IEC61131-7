"""Build a Mermaid class diagram from current parser Java declarations.

Only inheritance and implementation relations explicitly present in source
are shown; imports and names alone are not treated as class relationships.
"""
import re
from pathlib import Path

IMPORT = re.compile(r'^\s*import\s+([\w.]+)\s*;', re.MULTILINE)


def _strip_comments(text):
    # Strip comments and strings before looking for Java type declarations.
    return re.sub(r'/\*.*?\*/|//[^\n]*|"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'', ' ', text, flags=re.DOTALL)


def build_parser_class_diagram(src_dir: Path):
    root = Path(src_dir) / 'compiler' / 'parser'
    if not root.is_dir():
        raise ValueError(f'Missing parser Java source directory: {root}')
    files = sorted(p for p in root.rglob('*.java') if p.name != 'package-info.java')
    if not files:
        raise ValueError('No parser Java sources found')

    types = {}
    declarations = {}
    for path in files:
        content = _strip_comments(path.read_text(encoding='utf-8'))
        name = path.stem
        if not re.search(r'\b(?:class|interface|enum)\s+' + re.escape(name) + r'\b', content):
            raise ValueError(f'Missing parser type declaration: {path.relative_to(src_dir)}')
        pkg = '.'.join(path.relative_to(src_dir).parts[:-1])
        qualified = pkg + '.' + name
        types[qualified] = (pkg, name)
        declarations[qualified] = content

    if 'compiler.parser.Parser' not in types or 'compiler.parser.publishers.Publisher' not in types:
        raise ValueError('Missing essential parser types: Parser or Publisher')

    lines = ['classDiagram']
    packages = sorted({pkg for pkg, _ in types.values()})
    for pkg in packages:
        namespace = pkg.replace('.', '_')
        lines.append(f'    namespace {namespace} {{')
        for _, name in sorted((key, n) for key, (p, n) in types.items() if p == pkg):
            lines.append(f'        class {name}')
        lines.append('    }')

    relations = set()
    for qualified, (pkg, name) in sorted(types.items()):
        content = declarations[qualified]
        imported = {q.rsplit('.', 1)[-1]: q for q in IMPORT.findall(content) if q in types}
        for match in re.finditer(r'\b(?:class|interface)\s+' + re.escape(name) + r'\b([^\{;]*)\{', content):
            header = match.group(1)
            # Java generic arguments are not parent types; strip them.
            header = re.sub(r'<[^<>]*>', '', header)
            for keyword, kind in (('extends', '<|--'), ('implements', '<|..')):
                parent_match = re.search(r'\b' + keyword + r'\s+(.+?)(?=\s+implements\b|$)', header)
                if not parent_match:
                    continue
                for item in parent_match.group(1).split(','):
                    parent = item.strip().split()[0] if item.strip() else ''
                    target = imported.get(parent, pkg + '.' + parent)
                    if target in types and target != qualified:
                        # Mermaid class identifiers are simple names, not dotted Java names.
                        parent_name = types[target][1]
                        relations.add((parent_name, kind, name))
    for parent, arrow, child in sorted(relations):
        lines.append(f'    {parent} {arrow} {child}')
    if not relations:
        raise ValueError('No parser inheritance or implementation relationships found')
    return '\n'.join(lines)
