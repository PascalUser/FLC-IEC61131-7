"""Compiler facade package graph follows the production architecture inventory."""
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class CompilerPackageDiagramTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        main = self.root / "src/main/java/Main.java"
        main.parent.mkdir(parents=True)
        main.write_text("import api.Application;\nclass Main { void run(String[] args) { "
                        "Application.run(args, System.out, System.err); } }\n",
                        encoding="utf-8")
        self.inventory = {
            "packages": [
                {"scope": "production", "name": name}
                for name in ("api", "compiler", "compiler.generator", "compiler.parser")
            ] + [{"scope": "test", "name": "utils.simulation"}],
            "dependencies": [
                self.edge("api", "compiler"),
                self.edge("compiler", "compiler.generator"),
                self.edge("compiler", "compiler.parser"),
                self.edge("utils.simulation", "compiler.generator", "test"),
            ],
        }

    @staticmethod
    def edge(source, target, scope="production"):
        return {"from_scope": scope, "from_package": source,
                "to_scope": "production", "to_package": target}

    def render(self):
        with patch.object(diagrams, "ROOT", self.root), \
                patch.object(diagrams, "SRC_DIR", self.root / "src/main/java"), \
                patch.object(diagrams, "build_inventory", return_value=self.inventory):
            return diagrams.generate_compiler_package_flowchart()

    def test_main_api_compiler_flow_uses_real_import_evidence(self):
        result = self.render()
        self.assertIn("main --> api", result)
        self.assertIn("api --> compiler", result)
        self.assertIn("compiler --> compiler_generator", result)
        self.assertNotIn("utils.simulation", result)
        self.assertNotIn("compiler_CommandLine", result)
        self.assertEqual(result, self.render())

    def test_missing_api_dependency_fails(self):
        self.inventory["dependencies"] = [
            item for item in self.inventory["dependencies"]
            if item["from_package"] != "api"
        ]
        with self.assertRaisesRegex(ValueError, "no longer imports compiler"):
            self.render()

    def test_changed_main_delegation_fails(self):
        (self.root / "src/main/java/Main.java").write_text(
            "import api.Application;\nclass Main {}\n", encoding="utf-8"
        )
        with self.assertRaisesRegex(ValueError, "no longer delegates"):
            self.render()


if __name__ == "__main__":
    unittest.main()
