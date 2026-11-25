"""Agent structured edits go through the existing evidence and Git checks."""
from __future__ import annotations

import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))
from run_all_agents import validate_agent_output  # noqa: E402


class StructuredAgentContractTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        subprocess.run(["git", "init", "-q"], cwd=self.root, check=True)
        (self.root / "parser.md").write_text("Old parser path\n", encoding="utf-8")
        subprocess.run(["git", "add", "parser.md"], cwd=self.root, check=True)
        self.report = {
            "outcome": "PATCH_PROPOSED",
            "reason": "Checked parser documentation",
            "inspected_sources": ["parser.md"],
            "evidence": [{"path": "parser.md", "detail": "Read the current source"}],
            "metadata": {key: "checked" for key in (
                "id", "objective", "motivation", "scope", "changes",
                "risks", "validation", "dependencies",
            )},
            "edits": [{"path": "parser.md", "old": "Old parser path", "new": "New parser path"}],
        }

    def message(self, report=None):
        return "HARNESS_RESULT_JSON: " + json.dumps(report or self.report) + "\n"

    def test_structured_report_builds_valid_git_patch_without_mutation(self):
        before = (self.root / "parser.md").read_text()
        result = validate_agent_output(self.message(), self.root)
        self.assertEqual(result["outcome"], "PATCH_PROPOSED")
        self.assertIn("+New parser path", result["patch"])
        self.assertEqual((self.root / "parser.md").read_text(), before)
        self.assertEqual(subprocess.run(
            ["git", "apply", "--check", "--whitespace=error", "-"],
            cwd=self.root, input=result["patch"], text=True, capture_output=True,
        ).returncode, 0)

    def test_rejects_structured_and_raw_diff_mixed(self):
        diff = ("diff --git a/parser.md b/parser.md\n"
                "--- a/parser.md\n+++ b/parser.md\n"
                "@@ -1 +1 @@\n-Old parser path\n+New parser path\n")
        with self.assertRaisesRegex(ValueError, "no raw diff"):
            validate_agent_output(self.message() + diff, self.root)

    def test_rejects_multiple_result_markers_and_stale_anchor(self):
        with self.assertRaisesRegex(ValueError, "exactly one"):
            validate_agent_output(self.message() * 2, self.root)
        report = json.loads(json.dumps(self.report))
        report["edits"][0]["old"] = "Missing stale path"
        with self.assertRaisesRegex(ValueError, "occurs 0 times"):
            validate_agent_output(self.message(report), self.root)


if __name__ == "__main__":
    unittest.main()
