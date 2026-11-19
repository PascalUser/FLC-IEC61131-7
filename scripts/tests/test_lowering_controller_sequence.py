"""Regression tests for source-backed lowering controller sequence."""
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch
import sys

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class LoweringControllerSequenceTests(unittest.TestCase):
    def test_current_sources_and_stages(self):
        diagram = diagrams.generate_lowering_controller_sequence()
        self.assertTrue(diagram.startswith("sequenceDiagram\n"))
        for stage in ("BitWidthLower", "InputsLower", "OutputsLower", "InternalVariablesLower", "RuleBlockLower", "RuleLower", "TermLower", "ControllerModelBuilder"):
            self.assertIn(stage, diagram)
        self.assertIn("Optional.empty()", diagram)
        self.assertIn("Optional.of(model)", diagram)
        self.assertNotIn("RulesConstructor", diagram)
        self.assertEqual(diagram, diagrams.generate_lowering_controller_sequence())

    def test_missing_source_fails_instead_of_generating_stale_diagram(self):
        with tempfile.TemporaryDirectory() as tmp:
            with patch.object(diagrams, "SRC_DIR", Path(tmp)):
                with self.assertRaises(FileNotFoundError):
                    diagrams.generate_lowering_controller_sequence()

    def test_missing_evidence_is_reported(self):
        original = Path.read_text
        def changed(path, *args, **kwargs):
            content = original(path, *args, **kwargs)
            if path.name == "ControllerLower.java":
                return content.replace("new RuleBlockLower(ctx)", "new OtherLower(ctx)")
            return content
        with patch.object(Path, "read_text", changed):
            with self.assertRaisesRegex(ValueError, "new RuleBlockLower"):
                diagrams.generate_lowering_controller_sequence()


if __name__ == "__main__":
    unittest.main()
