#!/usr/bin/env python3
"""Read-only, deterministic evidence inventory for maintained Markdown.

Flags mechanically verifiable issues for agents to examine; this is NOT a
semantic validator. Reports are written only to output/, never to doc/ or README.
"""
from __future__ import annotations

import argparse
import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
OUTPUT = Path('output/documentation_evidence.json')
DOCUMENTS = (Path('README.md'), Path('doc/HARNESS.md'))
SOURCE_REF = re.compile(r'(?<![\w/])(?:src/(?:main|test)/java/)[\w./*{}-]+')
LEGACY_COMMAND = re.compile(r'(?<![\w./])(?:\./)?gradlew\b')


def documentation_files(root: Path) -> list[Path]:
    """Discover README, HARNESS, module docs and thesis chapters explicitly."""
    paths = [root / path for path in DOCUMENTS]
    paths += sorted((root / 'doc/modules').glob('*.md'))
    paths += sorted((root / 'doc/thesis').glob('*.md'))
    return sorted(p for p in paths if p.is_file())


def _normalize_reference(token: str) -> str:
    # Markdown punctuation is commonly attached to textual path references.
    return token.rstrip('.,;:!?)]}').rstrip('/')


def _reference_issue(root: Path, ref: str) -> str | None:
    """Return a bounded diagnostic; don't infer source semantics from names."""
    if any(marker in ref for marker in ('*', '{', '}')):
        return None  # patterns aren't literal file references
    if (root / ref).exists():
        return None
    # Directory references with Java package roots are actionable too.
    return 'referenced-source-path-not-found'


def audit_documentation(root: Path) -> dict:
    root = Path(root)
    files = documentation_files(root)
    issues: list[dict] = []
    for path in files:
        relative = path.relative_to(root).as_posix()
        for line_no, line in enumerate(path.read_text(encoding='utf-8').splitlines(), 1):
            for match in SOURCE_REF.finditer(line):
                ref = _normalize_reference(match.group(0))
                if _reference_issue(root, ref):
                    issues.append({
                        'path': relative, 'line': line_no,
                        'category': 'missing-source-reference',
                        'detail': ref,
                    })
            if LEGACY_COMMAND.search(line) and not line.lstrip().startswith(('<!--', '#')):
                issues.append({
                    'path': relative, 'line': line_no,
                    'category': 'gradle-command-in-maven-repository',
                    'detail': 'gradlew (verify build commands against pom.xml)',
                })
    return {
        'schema_version': 1,
        'method': 'Literal source-path existence and Gradle-command checks; not semantic verification',
        'sources': [p.relative_to(root).as_posix() for p in files],
        'findings': sorted(issues, key=lambda x: (x['path'], x['line'], x['category'], x['detail'])),
        'limitations': [
            'Wildcard paths and symbolic source patterns are not checked.',
            'Java package references without src/ paths and historical prose are not inferred.',
            'Existing paths do not prove that the associated narrative is correct.',
            'Source-line checks cannot certify claims about maintainability or thesis evidence.',
        ],
    }


def main(argv=None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--output', type=Path, default=OUTPUT)
    parser.add_argument('--check', action='store_true', help='Check if an existing report matches without writing')
    args = parser.parse_args(argv)
    destination = args.output if args.output.is_absolute() else ROOT / args.output
    try:
        report = audit_documentation(ROOT)
        encoded = json.dumps(report, indent=2, ensure_ascii=False) + '\n'
        if args.check:
            if not destination.is_file() or destination.read_text(encoding='utf-8') != encoded:
                print(f'Documentation evidence report missing or stale: {destination}')
                return 1
        else:
            destination.parent.mkdir(parents=True, exist_ok=True)
            destination.write_text(encoded, encoding='utf-8')
        print(f'Documentation evidence: {len(report["sources"])} documents, '
              f'{len(report["findings"])} candidate findings')
        return 0
    except (OSError, UnicodeError, ValueError) as exc:
        print(f'Documentation evidence audit failed: {exc}')
        return 2


if __name__ == '__main__':
    raise SystemExit(main())
