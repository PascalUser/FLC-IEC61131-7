#!/usr/bin/env python3
"""Check Mermaid generator evidence, file freshness, and actual CLI rendering.

Read-only: no tracked files are written. Requires the Docker image's mmdc and
headless Chromium; failures are reported rather than silently skipped.
"""
import os
import shutil
import subprocess
import tempfile
from pathlib import Path

import generate_diagrams
from render_mermaid import find_chrome_executable


def validate_diagrams(root=None, diagrams=None, cli=None, chrome=None):
    """Return diagnostic strings for each generator, file, or rendering failure."""
    root = Path(root) if root is not None else generate_diagrams.ROOT
    diagrams = generate_diagrams.DIAGRAMS if diagrams is None else diagrams
    cli = shutil.which('mmdc') if cli is None else cli
    chrome = find_chrome_executable() if chrome is None else chrome
    errors = []
    if not cli:
        errors.append('Mermaid CLI (mmdc) not found; syntax checks cannot run')
    if not chrome:
        errors.append('Headless Chromium not found; Mermaid syntax checks cannot run')

    for name, generator in sorted(diagrams.items()):
        path = root / 'doc' / 'diagrams' / (name + '.mmd')
        try:
            expected = generator()
        except Exception as exc:
            errors.append(f'{name}: generator FAILED: {type(exc).__name__}: {exc}')
            continue
        if not isinstance(expected, str) or not expected.strip():
            errors.append(f'{name}: generator produced empty or non-text content')
            continue
        if not path.is_file():
            errors.append(f'{name}: generated Mermaid file MISSING: {path}')
            continue
        try:
            actual = path.read_text(encoding='utf-8')
        except (OSError, UnicodeError) as exc:
            errors.append(f'{name}: cannot read diagram: {exc}')
            continue
        if actual != expected:
            errors.append(f'{name}: generated Mermaid file STALE (differs from current source)')
        if not cli or not chrome:
            continue
        try:
            with tempfile.TemporaryDirectory(prefix='flc-mermaid-') as tmp:
                source = Path(tmp) / 'diagram.mmd'
                target = Path(tmp) / 'diagram.svg'
                config = Path(tmp) / 'puppeteer.json'
                source.write_text(actual, encoding='utf-8')
                config.write_text('{"args":["--no-sandbox","--disable-setuid-sandbox"]}',
                                  encoding='utf-8')
                env = os.environ.copy()
                env['PUPPETEER_EXECUTABLE_PATH'] = str(chrome)
                result = subprocess.run(
                    [cli, '-i', str(source), '-o', str(target), '-p', str(config)],
                    capture_output=True, text=True, timeout=60, env=env,
                )
                if result.returncode != 0:
                    detail = (result.stderr or result.stdout).strip().replace('\n', ' ')
                    errors.append(f'{name}: Mermaid render FAILED: {detail[:350]}')
                elif not target.is_file():
                    errors.append(f'{name}: Mermaid CLI succeeded but did not produce SVG')
        except (OSError, subprocess.TimeoutExpired) as exc:
            errors.append(f'{name}: Mermaid validation unavailable: {exc}')
    return errors
