"""Regression checks for source-backed lowering class diagram."""
import sys
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class LoweringClassCurrentSourcesTests(unittest.TestCase):
    def test_current_types_and_relationships(self):
        diagram = diagrams.generate_lowering_class_diagram()
        self.assertTrue(diagram.startswith("classDiagram\n"))
        self.assertIn("namespace lowering_sublowers {", diagram)
        self.assertIn("class RuleBlockLower", diagram)
        self.assertIn("Lower <|.. ControllerLower", diagram)
        self.assertIn("ControllerLower --> RuleBlockLower : uses", diagram)
        self.assertIn("RuleBlockLower --> RuleLower : uses", diagram)
        self.assertNotIn("RulesConstructor", diagram)
        self.assertNotIn("ModelConstructor", diagram)
        self.assertEqual(diagram, diagrams.generate_lowering_class_diagram())

    def test_missing_type_fails(self):
        original = Path.read_text
        def read_text(path, *args, **kwargs):
            if path.name == "RuleBlockLower.java":
                return "package compiler.generator.lowers.sublowers;"
            return original(path, *args, **kwargs)
        with patch.object(Path, "read_text", read_text):
            with self.assertRaisesRegex(ValueError, "Missing lowers type: RuleBlockLower"):
                diagrams.generate_lowering_class_diagram()

    def test_removed_relationship_fails(self):
        original = Path.read_text
        def read_text(path, *args, **kwargs):
            result = original(path, *args, **kwargs)
            if path.name == "ControllerLower.java":
                return result.replace("new RuleBlockLower(ctx)", "somethingElse(ctx)")
            return result
        with patch.object(Path, "read_text", read_text):
            with self.assertRaisesRegex(ValueError, "Lowering relationship changed"):
                diagrams.generate_lowering_class_diagram()


if __name__ == "__main__":
    unittest.main()
