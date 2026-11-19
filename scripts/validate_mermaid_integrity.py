#!/usr/bin/env python3
"""Check Mermaid generator evidence, freshness, and Mermaid CLI syntax.

A definitive status is emitted once for each registered diagram. When a
source generator fails or the saved file is stale, skip costly rendering.
All rendering artifacts are temporary; tracked files are never modified.
"""
import os
import shutil
import subprocess
import tempfile
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path

import generate_diagrams
from render_mermaid import find_chrome_executable


def _render_mermaid(content, cli, chrome):
    """Return an error string or None when Mermaid renders successfully."""
    try:
        with tempfile.TemporaryDirectory(prefix='flc-mermaid-') as tmp:
            source = Path(tmp) / 'diagram.mmd'
            target = Path(tmp) / 'diagram.svg'
            config = Path(tmp) / 'puppeteer.json'
            source.write_text(content, encoding='utf-8')
            config.write_text('{"args":["--no-sandbox","--disable-setuid-sandbox"]}',
                              encoding='utf-8')
            env = os.environ.copy()
            env['PUPPETEER_EXECUTABLE_PATH'] = str(chrome)
            result = subprocess.run(
                [cli, '-i', str(source), '-o', str(target), '-p', str(config)],
                capture_output=True, text=True, timeout=60, env=env,
            )
            if result.returncode:
                detail = (result.stderr or result.stdout).strip().replace('\n', ' ')
                return f'Mermaid render FAILED: {detail[:350]}'
            if not target.is_file():
                return 'Mermaid CLI succeeded but did not produce SVG'
    except (OSError, subprocess.TimeoutExpired) as exc:
        return f'Mermaid validation unavailable: {exc}'
    return None


def inspect_diagrams(root=None, diagrams=None, cli=None, chrome=None, on_result=None):
    """Inspect diagram sources once and return (name, status, detail) tuples.

    If ``on_result`` is provided, call it immediately as each definitive
    status becomes available; rendering uses at most two parallel workers.
    """
    root = Path(root) if root is not None else generate_diagrams.ROOT
    diagrams = generate_diagrams.DIAGRAMS if diagrams is None else diagrams
    cli = shutil.which('mmdc') if cli is None else cli
    chrome = find_chrome_executable() if chrome is None else chrome
    results = {}

    def record(name, status, detail=''):
        results[name] = (status, detail)
        if on_result is not None:
            on_result(name, status, detail)

    with ThreadPoolExecutor(max_workers=2) as executor:
        pending = {}
        for name, generator in sorted(diagrams.items()):
            path = root / 'doc' / 'diagrams' / (name + '.mmd')
            try:
                expected = generator()
                if not isinstance(expected, str) or not expected.strip():
                    record(name, 'FAILED', 'generator produced empty or non-text content')
                    continue
                if not path.is_file():
                    record(name, 'MISSING', str(path))
                    continue
                actual = path.read_text(encoding='utf-8')
                if actual != expected:
                    record(name, 'STALE', 'differs from current source')
                    continue
                if not cli or not chrome:
                    record(name, 'UNAVAILABLE', 'Mermaid CLI or headless Chromium missing')
                    continue
                pending[executor.submit(_render_mermaid, actual, cli, chrome)] = name
            except Exception as exc:
                record(name, 'FAILED', f'{type(exc).__name__}: {exc}')

        for future in as_completed(pending):
            name = pending[future]
            try:
                detail = future.result()
                record(name, 'INVALID' if detail else 'OK', detail or '')
            except Exception as exc:
                record(name, 'UNAVAILABLE', f'{type(exc).__name__}: {exc}')

    return [(name, *results[name]) for name in sorted(results)]


def validate_diagrams(root=None, diagrams=None, cli=None, chrome=None):
    """Backward-compatible error-list API for existing test callers."""
    return [f'{name}: {status}: {detail}' for name, status, detail in
            inspect_diagrams(root, diagrams, cli, chrome) if status != 'OK']
