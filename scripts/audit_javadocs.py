#!/usr/bin/env python3
"""Read-only, source-backed documentation audit for handwritten Java and Bison actions.

The scanner is intentionally conservative: it reports candidate declarations and
complex methods for human review, not guaranteed Java semantic understanding.
Use Maven Javadoc for link/tag resolution and an agent for documenting behavior.
"""
from __future__ import annotations

import argparse
import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
SOURCE_ROOTS = (Path('src/main/java'), Path('src/test/java'))
GENERATED = {'src/main/java/compiler/parser/Parser.java',
             'src/main/java/compiler/lexer/Lexer.java'}
PARSER_GRAMMAR = Path('src/main/java/compiler/parser/Parser.y')
# A declaration header starts on its own line. Constructors are recognized when
# their identifier matches the source type; this is a heuristic, not a parser.
METHOD = re.compile(
    r'(?m)^[ \t]*(?:(?:public|protected|private|static|final|synchronized|'
    r'abstract|native|default|strictfp)\s+)*'
    r'(?:<[^\n{};]+>\s+)?'
    r'(?:(?:[\w.$?<>\[\],]+)\s+)?'
    r'(?P<name>[A-Za-z_$][\w$]*)\s*\('
)
TYPE = re.compile(r'\b(?:class|interface|enum|record)\s+([A-Za-z_$][\w$]*)\b')
CONTROL = {'if', 'for', 'while', 'switch', 'catch', 'synchronized', 'return',
           'throw', 'new', 'assert', 'super', 'this'}


def mask_non_code(source: str) -> str:
    """Preserve offsets/newlines but mask comments, literals and characters."""
    out = list(source)
    i = 0
    while i < len(source):
        start = i
        if source.startswith('//', i):
            j = source.find('\n', i)
            i = len(source) if j < 0 else j
        elif source.startswith('/*', i):
            j = source.find('*/', i + 2)
            i = len(source) if j < 0 else j + 2
        elif source[i] in ('"', "'"):
            quote = source[i]
            i += 1
            while i < len(source):
                if source[i] == '\\':
                    i += 2
                elif source[i] == quote:
                    i += 1
                    break
                else:
                    i += 1
            i = min(i, len(source))
        else:
            i += 1
            continue
        for pos in range(start, i):
            if source[pos] not in '\n\r':
                out[pos] = ' '
    return ''.join(out)


def matching(text: str, start: int, opening: str, closing: str) -> int:
    depth = 0
    for i in range(start, len(text)):
        if text[i] == opening:
            depth += 1
        elif text[i] == closing:
            depth -= 1
            if depth == 0:
                return i
    return -1


def line_number(source: str, offset: int) -> int:
    return source.count('\n', 0, offset) + 1


def documented(source: str, offset: int) -> bool:
    """Accept adjacent Javadoc, allowing ordinary Java annotations after it."""
    before = source[:offset]
    while True:
        before = before.rstrip()
        ann = re.search(r'(?m)^\s*@\w+(?:\([^\n]*\))?\s*$', before)
        if ann and ann.end() == len(before):
            before = before[:ann.start()]
        else:
            break
    return before.rstrip().endswith('*/') and bool(
        re.search(r'/\*\*[^*]*\*+(?:[^/*][^*]*\*+)*/\s*$', before, re.DOTALL)
    )


def scan_java(source: str, path: str, max_method_lines: int = 45) -> list[dict]:
    clean = mask_non_code(source)
    findings = []
    declared = TYPE.search(clean)
    class_name = declared.group(1) if declared else Path(path).stem
    if declared and not documented(source, source.rfind('\n', 0, declared.start()) + 1):
        findings.append({'path': path, 'line': line_number(source, declared.start()),
                         'code': 'TYPE_JAVADOC', 'subject': class_name,
                         'detail': 'Handwritten top-level type lacks adjacent Javadoc'})
    for match in METHOD.finditer(clean):
        name = match.group('name')
        if name in CONTROL or name.startswith('$'):
            continue
        open_paren = clean.find('(', match.start(), match.end() + 1)
        end_paren = matching(clean, open_paren, '(', ')')
        if end_paren < 0:
            continue
        tail = clean[end_paren + 1:]
        after = re.match(r'\s*(?:throws\s+[\w.$, \t\n]+\s*)?\{', tail)
        if not after:
            continue
        brace = end_paren + 1 + after.end() - 1
        end = matching(clean, brace, '{', '}')
        if end < 0:
            continue
        # Skip call-shaped headers and nested expressions. In particular,
        # annotations and chained calls are not method declarations.
        header = clean[match.start():open_paren].strip()
        if re.search(r'[=.;()]', header) or name in {'class', 'interface'}:
            continue
        if not documented(source, match.start()):
            findings.append({'path': path, 'line': line_number(source, match.start()),
                             'code': 'METHOD_JAVADOC', 'subject': name,
                             'detail': 'Method/constructor candidate lacks adjacent Javadoc'})
        body = clean[brace + 1:end]
        logical_lines = sum(bool(line.strip()) for line in body.splitlines())
        if logical_lines > max_method_lines:
            findings.append({'path': path, 'line': line_number(source, match.start()),
                             'code': 'REFACTOR_REVIEW', 'subject': name,
                             'detail': f'{logical_lines} non-comment code lines; review for a justified TODO and simplification'})
    return findings


def scan_parser_actions(source: str, path: str) -> list[dict]:
    """Find standalone Bison rule actions within %% grammar sections.

    Excludes nested Java braces using balanced blocks; recognizes standalone
    opening braces only. Nonstandard inline/mid-rule layouts need agent review.
    """
    delimiters = list(re.finditer(r'(?m)^\s*%%\s*$', source))
    if not delimiters:
        raise ValueError(f'{path}: missing Bison grammar %% delimiter')
    begin = delimiters[0].end()
    end = delimiters[1].start() if len(delimiters) > 1 else len(source)
    clean = mask_non_code(source)
    findings = []
    offset = begin
    for match in re.finditer(r'(?m)^[^\n{}]*\{[ \t]*(?:\r?\n|$)', clean[begin:end]):
        prefix = match.group().split('{', 1)[0].strip()
        if prefix.startswith('%') or re.search(r'\b(?:if|else|for|while|switch|try|catch|finally)\b', prefix):
            continue
        brace = begin + match.group().index('{') + match.start()
        if brace < offset:
            continue
        closing = matching(clean, brace, '{', '}')
        if closing < 0 or closing > end:
            findings.append({'path': path, 'line': line_number(source, brace),
                             'code': 'ACTION_PARSE_REVIEW', 'subject': 'semantic action',
                             'detail': 'Action braces could not be safely matched'})
            continue
        offset = closing + 1
        inner = source[brace + 1:closing].strip()
        if not inner:
            continue
        if not re.match(r'/\*\*[\s\S]*?\*/', inner):
            findings.append({'path': path, 'line': line_number(source, brace),
                             'code': 'PARSER_ACTION_JAVADOC', 'subject': 'semantic action',
                             'detail': 'Add explanatory /** ... */ as the first content INSIDE the action braces'})
    return findings


def audit(root: Path, max_method_lines: int = 45) -> dict:
    findings = []
    inspected = []
    excluded = []
    for rel in SOURCE_ROOTS:
        directory = root / rel
        if not directory.is_dir():
            raise ValueError(f'Missing Java source directory: {rel}')
        for path in sorted(directory.rglob('*.java')):
            filename = path.relative_to(root).as_posix()
            if filename in GENERATED:
                excluded.append(filename)
                continue
            inspected.append(filename)
            content = path.read_text(encoding='utf-8')
            if path.name in {'package-info.java', 'module-info.java'}:
                if not re.search(r'/\*\*[\s\S]*?\*/', content):
                    findings.append({'path': filename, 'line': 1,
                                     'code': 'PACKAGE_JAVADOC', 'subject': path.name,
                                     'detail': 'Package/module descriptor lacks documentation'})
                continue
            findings.extend(scan_java(content, filename, max_method_lines))
    grammar = root / PARSER_GRAMMAR
    if not grammar.is_file():
        raise ValueError(f'Missing Bison grammar: {PARSER_GRAMMAR}')
    inspected.append(PARSER_GRAMMAR.as_posix())
    findings.extend(scan_parser_actions(grammar.read_text(encoding='utf-8'), PARSER_GRAMMAR.as_posix()))
    findings.sort(key=lambda f: (f['path'], f['line'], f['code'], f['subject']))
    return {'schema_version': 1, 'scope': 'handwritten Java, tests, Bison semantic action candidates',
            'method': 'conservative syntax scanning; Maven Javadoc and agent review still required',
            'limitations': ['Java scanner is not a full Java AST parser; review false positives and negatives',
                            'Parser.y scanner recognizes standalone actions within %% sections',
                            'REFACTOR_REVIEW is a suggestion, not proof of a code smell',
                            'Link/tag resolution and comment quality require separate checks'],
            'inspected_sources': inspected, 'excluded_sources': excluded,
            'findings': findings, 'finding_counts': {code: sum(f['code'] == code for f in findings)
                                                   for code in sorted({f['code'] for f in findings})}}


def main(argv=None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root', type=Path, default=ROOT)
    parser.add_argument('--output', type=Path, default=Path('output/javadoc_audit.json'))
    parser.add_argument('--max-method-lines', type=int, default=45)
    parser.add_argument('--check', action='store_true',
                        help='Fail if any finding exists; never write an audit report')
    args = parser.parse_args(argv)
    if args.max_method_lines < 1:
        parser.error('--max-method-lines must be positive')
    try:
        report = audit(args.root, args.max_method_lines)
    except (OSError, UnicodeError, ValueError) as exc:
        parser.exit(2, f'Javadoc audit failed: {exc}\n')
    print(f"Inspected {len(report['inspected_sources'])} sources; "
          f"{len(report['findings'])} findings")
    if args.check:
        return 1 if report['findings'] else 0
    output = args.output if args.output.is_absolute() else args.root / args.output
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(json.dumps(report, indent=2, ensure_ascii=False) + '\n', encoding='utf-8')
    print(f'Wrote {output}')
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
