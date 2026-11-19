"""Regression tests for the compiler class diagram's current Java API."""
import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class CompilerClassDiagramTests(unittest.TestCase):
    def test_current_cli_and_compiler_signatures(self):
        content = diagrams.generate_compiler_class_diagram()
        self.assertTrue(content.startswith("classDiagram\n"))
        self.assertIn("namespace api {", content)
        self.assertIn("class Application {", content)
        self.assertIn("+FlcCompiler(String, Reader)", content)
        self.assertIn("+compile() List~GeneratedFile~", content)
        self.assertIn("Application ..> FlcCompiler : creates", content)
        self.assertIn("namespace compiler_repository {", content)
        self.assertNotIn("compiler.CommandLine", content)
        self.assertEqual(content, diagrams.generate_compiler_class_diagram())

    def test_missing_java_class_is_rejected(self):
        original = diagrams.SRC_DIR
        from tempfile import TemporaryDirectory
        with TemporaryDirectory() as temporary:
            try:
                diagrams.SRC_DIR = Path(temporary)
                with self.assertRaisesRegex(ValueError, "Missing compiler type"):
                    diagrams.generate_compiler_class_diagram()
            finally:
                diagrams.SRC_DIR = original


if __name__ == "__main__":
    unittest.main()
