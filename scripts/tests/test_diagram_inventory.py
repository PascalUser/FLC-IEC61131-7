"""Verify Mermaid package diagrams use production import evidence."""
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class PackageDiagramsTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.write("src/main/java/compiler/lexer/Lexer.java", """
            package compiler.lexer;
            import compiler.repository.SymbolTable;
            public class Lexer { }
        """)
        self.write("src/main/java/compiler/parser/Parser.java", """
            package compiler.parser;
            import compiler.lexer.Lexer;
            import compiler.repository.SymbolTable;
            public class Parser { }
        """)
        self.write("src/main/java/compiler/repository/SymbolTable.java", """
            package compiler.repository;
            public class SymbolTable { }
        """)
        self.write("src/test/java/utils/simulation/Simulation.java", """
            package utils.simulation;
            import compiler.parser.Parser;
            public class Simulation { }
        """)

    def write(self, name, contents):
        path = self.root / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(contents, encoding="utf-8")

    def test_lexer_uses_production_imports(self):
        with patch.object(diagrams, "ROOT", self.root):
            result = diagrams.generate_lexer_package_flowchart()
        self.assertIn("compiler_lexer --> compiler_repository", result)
        self.assertNotIn("utils.simulation", result)

    def test_parser_uses_same_inventory_and_is_deterministic(self):
        with patch.object(diagrams, "ROOT", self.root):
            first = diagrams.generate_parser_package_flowchart()
            second = diagrams.generate_parser_package_flowchart()
        self.assertEqual(first, second)
        self.assertIn("compiler_parser --> compiler_lexer", first)
        self.assertIn("compiler_parser --> compiler_repository", first)
        self.assertNotIn("utils.simulation", first)

    def test_missing_production_package_is_error(self):
        with patch.object(diagrams, "ROOT", self.root):
            with self.assertRaisesRegex(ValueError, "Production package not found"):
                diagrams.generate_import_dependency_flowchart("compiler.unknown")

    def test_no_edges_is_error(self):
        with patch.object(diagrams, "ROOT", self.root):
            with self.assertRaisesRegex(ValueError, "No production import dependencies"):
                diagrams.generate_import_dependency_flowchart("compiler.repository")


if __name__ == "__main__":
    unittest.main()
