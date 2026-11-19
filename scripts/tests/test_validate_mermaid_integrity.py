"""Regression tests: false-positive Mermaid validations must be rejected."""
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from validate_mermaid_integrity import validate_diagrams


class MermaidIntegrityTests(unittest.TestCase):
    def setUp(self):
        temporary = tempfile.TemporaryDirectory()
        self.addCleanup(temporary.cleanup)
        self.root = Path(temporary.name)
        self.path = self.root / 'doc/diagrams/example.mmd'
        self.path.parent.mkdir(parents=True)
        self.diagrams = {'example': lambda: 'sequenceDiagram\n    A->>B: hello'}

    def test_missing_diagram_is_reported(self):
        result = validate_diagrams(self.root, self.diagrams, cli='mmdc', chrome='chrome')
        self.assertTrue(any('MISSING' in error for error in result))

    def test_stale_diagram_is_reported(self):
        self.path.write_text('sequenceDiagram\n    A->>B: old')
        with patch('validate_mermaid_integrity.subprocess.run') as run:
            run.return_value = subprocess.CompletedProcess([], 1, '', 'Parse error')
            result = validate_diagrams(self.root, self.diagrams, cli='mmdc', chrome='chrome')
        self.assertTrue(any('STALE' in error for error in result))
        self.assertTrue(any('render FAILED' in error for error in result))

    def test_generator_failure_is_reported(self):
        def fail():
            raise ValueError('old source path')
        result = validate_diagrams(self.root, {'broken': fail}, cli='mmdc', chrome='chrome')
        self.assertTrue(any('generator FAILED' in error for error in result))

    def test_missing_renderer_is_not_silently_accepted(self):
        self.path.write_text(self.diagrams['example']())
        result = validate_diagrams(self.root, self.diagrams, cli='', chrome='')
        self.assertTrue(any('cannot run' in error for error in result))


if __name__ == '__main__':
    unittest.main()
