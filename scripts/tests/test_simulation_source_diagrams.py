"""Integration checks for simulation diagrams backed by actual test sources."""
import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class SimulationSourceDiagramsTests(unittest.TestCase):
    def test_class_diagram_reads_simulation_from_test_tree(self):
        result = diagrams.generate_simulation_class_diagram()
        self.assertTrue(result.startswith("classDiagram\n"))
        self.assertIn("namespace utils_simulation", result)
        self.assertIn("namespace compiler_generator", result)
        self.assertIn("SimulationComparison --> QuantizedSimulator : creates", result)

    def test_sequence_reads_simulation_from_test_tree(self):
        result = diagrams.generate_simulation_comparison_sequence()
        self.assertTrue(result.startswith("sequenceDiagram\n"))
        self.assertIn("utils.simulation.SimulationComparison", result)
        self.assertIn("compiler.generator.fuzzysets.FuzzySet", result)
        self.assertIn("C->>Q: evaluate(inputs)", result)
        self.assertEqual(result, diagrams.generate_simulation_comparison_sequence())


if __name__ == "__main__":
    unittest.main()
