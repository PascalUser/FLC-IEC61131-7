"""Package diagram wrappers must use the shared architecture inventory."""
import sys
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class GeneratorLoweringPackageDiagramsTests(unittest.TestCase):
    def test_generator_includes_lowering_subtree(self):
        with patch.object(diagrams, "generate_import_dependency_flowchart", return_value="flowchart LR") as draw:
            self.assertEqual(diagrams.generate_codegen_package_flowchart(), "flowchart LR")
        draw.assert_called_once_with("compiler.generator")

    def test_lowering_uses_actual_nested_package(self):
        with patch.object(diagrams, "generate_import_dependency_flowchart", return_value="flowchart LR") as draw:
            self.assertEqual(diagrams.generate_lowering_package_flowchart(), "flowchart LR")
        draw.assert_called_once_with("compiler.generator.lowers")


if __name__ == "__main__":
    unittest.main()
