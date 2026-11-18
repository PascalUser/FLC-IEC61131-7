"""Behavior tests for the evidence-backed architecture extractor."""
import json
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import extract_architecture as architecture


class ArchitectureInventoryTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.write("src/main/java/compiler/ast/Tree.java", """
            package compiler.ast;
            public class Tree {
                class Nested { }
                String text = "class Fake {}";
            }
        """)
        self.write("src/main/java/compiler/parser/Parser.java", """
            package compiler.parser;
            import compiler.ast.Tree;
            import java.util.List;
            // import compiler.fake.Ghost;
            /* class Phantom {} */
            public final class Parser { }
        """)
        self.write("src/test/java/utils/simulation/Check.java", """
            package utils.simulation;
            import compiler.parser.Parser;
            public interface Check { }
        """)

    def write(self, name, contents):
        path = self.root / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(contents, encoding="utf-8")

    def test_scopes_types_and_internal_imports(self):
        data = architecture.build_inventory(self.root)
        self.assertEqual(len(data["files"]), 3)
        self.assertEqual([t["name"] for t in data["files"][0]["types"]], ["Tree"])
        self.assertEqual(len(data["dependencies"]), 2)
        self.assertEqual(
            {(edge["from_package"], edge["to_package"]) for edge in data["dependencies"]},
            {("compiler.parser", "compiler.ast"), ("utils.simulation", "compiler.parser")},
        )
        self.assertEqual(
            [p["scope"] for p in data["packages"] if p["name"] == "utils.simulation"],
            ["test"],
        )
        self.assertEqual(data, architecture.build_inventory(self.root))

    def test_comments_strings_and_annotations_are_not_types(self):
        clean = architecture.without_comments_and_literals(
            'package x; /* class No {} */ @interface Mark {} '
            'class Real { String x = "interface Fake {}"; class Nested {} }'
        )
        self.assertEqual(
            [(t["kind"], t["name"]) for t in architecture.top_level_types(clean, "x")],
            [("annotation", "Mark"), ("class", "Real")],
        )

    def test_package_path_mismatch_fails(self):
        self.write("src/main/java/compiler/parser/Wrong.java", "package other; class Wrong {}")
        with self.assertRaisesRegex(ValueError, "Package/path mismatch"):
            architecture.build_inventory(self.root)

    def test_check_mode_is_read_only_and_detects_stale_json(self):
        out = self.root / "output/inventory.json"
        with patch.object(architecture, "ROOT", self.root):
            self.assertEqual(architecture.main(["--check", "--output", str(out)]), 1)
            self.assertFalse(out.exists())
            self.assertEqual(architecture.main(["--output", str(out)]), 0)
            self.assertEqual(architecture.main(["--check", "--output", str(out)]), 0)
            contents = json.loads(out.read_text(encoding="utf-8"))
            self.assertEqual(contents["schema_version"], 1)
            self.write("src/main/java/compiler/ast/More.java", "package compiler.ast; class More {}")
            self.assertEqual(architecture.main(["--check", "--output", str(out)]), 1)
            self.assertEqual(contents, json.loads(out.read_text(encoding="utf-8")))


if __name__ == "__main__":
    unittest.main()
