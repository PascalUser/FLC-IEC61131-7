"""Deterministic patch assembly and read-only contract regression."""
from __future__ import annotations

import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))
from scripts.agent_edits import build_patch_from_edits  # noqa: E402


class DeterministicAgentEditsTests(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.addCleanup(self.tmp.cleanup)
        self.root = Path(self.tmp.name)
        subprocess.run(["git", "init", "-q"], cwd=self.root, check=True)
        (self.root / "doc/modules").mkdir(parents=True)
        self.source = self.root / "doc/modules/parser.md"
        self.original = ("# Parser\n\n"
                         "| parser | src/main/java/parser/ |\n"
                         "| utils | src/main/java/utils/ |\n"
                         "\n## More\n\nSee src/main/java/parser/Parser.y\n")
        self.source.write_text(self.original, encoding="utf-8")
        subprocess.run(["git", "add", "doc/modules/parser.md"], cwd=self.root, check=True)

    def test_multiple_edits_produce_correct_hunks_without_mutation(self):
        edits = [
            {"path": "doc/modules/parser.md",
             "old": "| parser | src/main/java/parser/ |",
             "new": "| parser | src/main/java/compiler/parser/ |"},
            {"path": "doc/modules/parser.md",
             "old": "src/main/java/parser/Parser.y",
             "new": "src/main/java/compiler/parser/Parser.y"},
        ]
        patch = build_patch_from_edits(edits, self.root)
        self.assertIn("@@ -", patch)
        self.assertEqual(subprocess.run(
            ["git", "apply", "--check", "--whitespace=error", "-"],
            input=patch, text=True, capture_output=True, cwd=self.root,
        ).returncode, 0)
        self.assertEqual(self.source.read_text(), self.original)

    def test_rejects_missing_ambiguous_and_overlapping_anchors(self):
        template = {"path": "doc/modules/parser.md", "old": "missing", "new": "new"}
        with self.assertRaisesRegex(ValueError, "occurs 0 times"):
            build_patch_from_edits([template], self.root)
        with self.assertRaisesRegex(ValueError, "occurs 3 times"):
            build_patch_from_edits([{**template, "old": "parser"}], self.root)
        with self.assertRaisesRegex(ValueError, "overlapping"):
            build_patch_from_edits([
                {**template, "old": "| parser |", "new": "| module |"},
                {**template, "old": "parser | src/main", "new": "module | src/main"},
            ], self.root)

    def test_rejects_unsafe_paths_and_noops(self):
        for path in ("../secret", "/etc/passwd", ".git/config", "bad name"):
            with self.subTest(path=path), self.assertRaises(ValueError):
                build_patch_from_edits([{"path": path, "old": "A", "new": "B"}], self.root)
        with self.assertRaisesRegex(ValueError, "distinct"):
            build_patch_from_edits([{
                "path": "doc/modules/parser.md", "old": "# Parser", "new": "# Parser",
            }], self.root)

    def test_parser_like_diff_does_not_depend_on_model_hunk_counts(self):
        patch = build_patch_from_edits([{
            "path": "doc/modules/parser.md",
            "old": "| parser | src/main/java/parser/ |",
            "new": "| parser | src/main/java/compiler/parser/ |",
        }], self.root)
        self.assertNotIn("@@ -81", patch)
        self.assertEqual(subprocess.run(
            ["git", "apply", "--check", "--whitespace=error", "-"],
            input=patch, text=True, capture_output=True, cwd=self.root,
        ).returncode, 0)


if __name__ == "__main__":
    unittest.main()
