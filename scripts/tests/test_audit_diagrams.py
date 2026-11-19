"""Behavior tests for the read-only Mermaid generator audit."""
import io
import json
import sys
import tempfile
import unittest
from contextlib import redirect_stdout
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import audit_diagrams


class AuditDiagramsTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.directory = Path(self.temp.name)

    def test_success_is_deterministic_without_writing(self):
        generators = {"z": lambda: "flowchart LR\n    A --> B", "a": lambda: "classDiagram\n    class A"}
        report = audit_diagrams.audit(generators, self.directory)
        self.assertEqual([item["diagram"] for item in report["results"]], ["a", "z"])
        self.assertEqual(report["summary"], {"ok": 2, "failed": 0, "missing": 0, "stale": 0})
        self.assertEqual(list(self.directory.iterdir()), [])

    def test_failed_generator_and_empty_output_are_reported(self):
        def broken():
            raise FileNotFoundError("Parser.y")
        report = audit_diagrams.audit({"broken": broken, "empty": lambda: " "}, self.directory)
        self.assertEqual(report["summary"]["failed"], 2)
        self.assertIn("Parser.y", report["results"][0]["detail"])

    def test_generated_file_drift_is_opt_in_and_read_only(self):
        target = self.directory / "a.mmd"
        target.write_text("flowchart LR\n    A --> B", encoding="utf-8")
        source = {"a": lambda: "flowchart LR\n    A --> C", "b": lambda: "sequenceDiagram\n    A->>B: call"}
        report = audit_diagrams.audit(source, self.directory, check_generated=True)
        self.assertEqual(report["summary"], {"ok": 0, "failed": 0, "missing": 1, "stale": 1})
        self.assertEqual(target.read_text(encoding="utf-8"), "flowchart LR\n    A --> B")
        self.assertFalse((self.directory / "b.mmd").exists())

    def test_cli_json_status_codes(self):
        with patch.object(audit_diagrams.generate_diagrams, "DIAGRAMS", {"a": lambda: "classDiagram\n    class A"}), \
             patch.object(audit_diagrams.generate_diagrams, "DIAGRAMS_DIR", self.directory):
            buffer = io.StringIO()
            with redirect_stdout(buffer):
                self.assertEqual(audit_diagrams.main(["--json"]), 0)
            self.assertEqual(json.loads(buffer.getvalue())["total"], 1)
            with redirect_stdout(io.StringIO()):
                self.assertEqual(audit_diagrams.main(["--check-generated"]), 1)


if __name__ == "__main__":
    unittest.main()
