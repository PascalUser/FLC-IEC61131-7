"""The overview diagram is derived from production import dependencies."""
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class GeneralPackageDiagramTests(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        self.root = Path(self.temporary.name)
        self.write("src/main/java/compiler/FlcCompiler.java", """
            package compiler;
            import compiler.ast.Tree;
            public class FlcCompiler { }
        """)
        self.write("src/main/java/compiler/ast/Tree.java", """
            package compiler.ast;
            public class Tree { }
        """)
        self.write("src/main/java/compiler/lexer/Lexer.java", """
            package compiler.lexer;
            import compiler.repository.SymbolTable;
            public class Lexer { }
        """)
        self.write("src/main/java/compiler/parser/Parser.java", """
            package compiler.parser;
            import compiler.lexer.Lexer;
            public class Parser { }
        """)
        self.write("src/main/java/compiler/parser/facades/Facade.java", """
            package compiler.parser.facades;
            import compiler.repository.SymbolTable;
            public class Facade { }
        """)
        self.write("src/main/java/compiler/generator/lowers/Lower.java", """
            package compiler.generator.lowers;
            import compiler.parser.Parser;
            public class Lower { }
        """)
        self.write("src/main/java/compiler/repository/SymbolTable.java", """
            package compiler.repository;
            public class SymbolTable { }
        """)
        self.write("src/test/java/utils/simulation/Simulation.java", """
            package utils.simulation;
            import compiler.generator.lowers.Lower;
            public class Simulation { }
        """)

    def write(self, filename, contents):
        file = self.root / filename
        file.parent.mkdir(parents=True, exist_ok=True)
        file.write_text(contents, encoding="utf-8")

    def test_module_edges_come_from_production_imports_only(self):
        with patch.object(diagrams, "ROOT", self.root):
            actual = diagrams.generate_package_flowchart()
            self.assertEqual(actual, diagrams.generate_package_flowchart())
        self.assertIn('compiler_generator["compiler.generator"]', actual)
        self.assertIn('compiler["compiler"]', actual)
        self.assertIn('compiler --> compiler_ast', actual)
        self.assertIn('compiler_parser --> compiler_lexer', actual)
        self.assertIn('compiler_parser --> compiler_repository', actual)
        self.assertIn('compiler_generator --> compiler_parser', actual)
        self.assertIn('compiler_lexer --> compiler_repository', actual)
        self.assertNotIn("compiler_parser_facades", actual)
        self.assertNotIn("utils_simulation", actual)
        self.assertNotIn("compiler_generator --> compiler_generator", actual)
        self.assertNotIn("|tokens|", actual)

    def test_missing_compiler_production_package_fails(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            (root / "src/main/java/api").mkdir(parents=True)
            (root / "src/test/java").mkdir(parents=True)
            (root / "src/main/java/api/Application.java").write_text(
                "package api; public class Application {}", encoding="utf-8"
            )
            with patch.object(diagrams, "ROOT", root):
                with self.assertRaisesRegex(ValueError, "No compiler production packages"):
                    diagrams.generate_package_flowchart()


if __name__ == "__main__":
    unittest.main()
