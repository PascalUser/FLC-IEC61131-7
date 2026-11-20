"""Regression checks for the current compiler.generator class diagram."""
import sys
import unittest
from pathlib import Path
from unittest.mock import patch
from tempfile import TemporaryDirectory

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class CodegenClassDiagramTests(unittest.TestCase):
    def test_uses_current_generator_sources_and_namespaces(self):
        diagram = diagrams.generate_codegen_class_diagram()
        self.assertTrue(diagram.startswith("classDiagram\n"))
        for namespace in (
            "compiler_generator", "compiler_generator_defuzzification",
            "compiler_generator_evaluation", "compiler_generator_fuzzysets",
        ):
            self.assertIn(f"namespace {namespace} {{", diagram)
        self.assertIn("Defuzzifier <|-- CentroidDefuzzifier", diagram)
        self.assertIn("EvaluationMethod <|-- MinMaxMethod", diagram)
        self.assertIn("FuzzySet <|-- SingletonFuzzySet", diagram)
        self.assertEqual(diagram, diagrams.generate_codegen_class_diagram())

    def test_missing_generator_sources_are_rejected(self):
        with TemporaryDirectory() as tmp:
            with patch.object(diagrams, "SRC_DIR", Path(tmp)):
                with self.assertRaisesRegex(ValueError, "Missing generator type: CodeGenerator"):
                    diagrams.generate_codegen_class_diagram()


if __name__ == "__main__":
    unittest.main()
