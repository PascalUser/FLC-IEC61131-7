"""Simulation package graph is derived from test-to-production import evidence."""
import sys
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class SimulationPackageDiagramTests(unittest.TestCase):
    def inventory(self, edges, include_simulation=True):
        packages = [{"scope": "production", "name": "compiler.generator"},
                    {"scope": "production", "name": "compiler.generator.evaluation"}]
        if include_simulation:
            packages.append({"scope": "test", "name": "utils.simulation"})
        return {"packages": packages, "dependencies": edges}

    def edge(self, source_scope="test", source="utils.simulation",
             target_scope="production", target="compiler.generator"):
        return {"from_scope": source_scope, "from_package": source,
                "to_scope": target_scope, "to_package": target,
                "evidence_files": ["src/test/java/utils/simulation/Simulation.java"]}

    def render(self, inventory):
        with patch.object(diagrams, "build_inventory", return_value=inventory) as extract:
            result = diagrams.generate_simulation_package_flowchart()
        extract.assert_called_once_with(diagrams.ROOT)
        return result

    def test_only_test_to_generator_imports_are_rendered(self):
        inventory = self.inventory([
            self.edge(),
            self.edge(target="compiler.generator.evaluation"),
            self.edge(source="utils.other"),
            self.edge(target="compiler.ast"),
        ])
        graph = self.render(inventory)
        self.assertIn('utils_simulation["utils.simulation"]', graph)
        self.assertIn("utils_simulation --> compiler_generator", graph)
        self.assertIn("utils_simulation --> compiler_generator_evaluation", graph)
        self.assertNotIn("utils_other", graph)
        self.assertNotIn("compiler_ast", graph)
        self.assertEqual(graph, self.render(inventory))

    def test_missing_simulation_test_package_is_error(self):
        with patch.object(diagrams, "build_inventory", return_value=self.inventory([], False)):
            with self.assertRaisesRegex(ValueError, "Simulation test package not found"):
                diagrams.generate_simulation_package_flowchart()

    def test_no_generator_imports_is_error(self):
        with patch.object(diagrams, "build_inventory", return_value=self.inventory([self.edge(target="compiler.ast")])):
            with self.assertRaisesRegex(ValueError, "no production generator imports"):
                diagrams.generate_simulation_package_flowchart()

    def test_production_to_simulation_boundary_violation_is_error(self):
        reverse = self.edge("production", "compiler.generator", "test", "utils.simulation")
        with patch.object(diagrams, "build_inventory", return_value=self.inventory([self.edge(), reverse])):
            with self.assertRaisesRegex(ValueError, "Production code must not import"):
                diagrams.generate_simulation_package_flowchart()


if __name__ == "__main__":
    unittest.main()
