"""The documentation validator reports only the final status of each Mermaid."""
import contextlib
import io
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import validate_docs


class SinglePassDocsTests(unittest.TestCase):
    def test_one_status_per_diagram_and_correct_exit_code(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            with patch.object(validate_docs, 'ROOT', root), \
                 patch.object(validate_docs, 'find_markdown_files', return_value=[]), \
                 patch.object(validate_docs, 'find_mmd_files', return_value=[root / 'doc/diagrams/a.mmd']):
                def inspect(on_result):
                    rows = [('a', 'OK', ''), ('b', 'FAILED', 'missing source')]
                    for row in rows:
                        on_result(*row)
                    return rows
                output = io.StringIO()
                with patch.object(validate_docs, 'inspect_diagrams', side_effect=inspect) as spy, \
                     contextlib.redirect_stdout(output):
                    result = validate_docs.main()
        text = output.getvalue()
        self.assertEqual(result, 1)
        self.assertEqual(text.count('doc/diagrams/a.mmd'), 1)
        self.assertEqual(text.count('doc/diagrams/b.mmd'), 1)
        self.assertIn('  FAILED: doc/diagrams/b.mmd: missing source', text)
        self.assertIn('Total violations: 1', text)
        spy.assert_called_once()


if __name__ == '__main__':
    unittest.main()
