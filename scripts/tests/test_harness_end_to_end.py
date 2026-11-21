"""End-to-end regression of the orchestration boundary in a disposable Git repo.

The real documentation inventory and real git apply --check run in the sandbox.
OpenCode and unrelated heavyweight generators are replaced with deterministic
subprocess responses. This does NOT claim to test live OpenCode or Maven.
"""
from __future__ import annotations

import contextlib
import io
import json
import shutil
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))

from run_all_agents import build_pipeline, main  # noqa: E402


class HarnessEndToEndTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.real_run = subprocess.run
        self.real_run(["git", "init", "-q"], cwd=self.root, check=True)
        for directory in (
            "doc/modules", "doc/thesis", "scripts", "src/main/java/compiler/parser"
        ):
            (self.root / directory).mkdir(parents=True, exist_ok=True)
        # Exercise the real auditor from patch 0031 in the disposable workspace.
        shutil.copyfile(
            ROOT / "scripts/audit_documentation_evidence.py",
            self.root / "scripts/audit_documentation_evidence.py",
        )
        # OpenCode v2 resolves agent templates from the project configuration.
        # The disposable repository must include that same configuration.
        shutil.copyfile(ROOT / "opencode.json", self.root / "opencode.json")
        self.original = (
            "Use `src/main/java/parser/Parser.y`.\n"
            "Run `./gradlew test`.\n"
        )
        (self.root / "doc/modules/parser.md").write_text(self.original, encoding="utf-8")
        (self.root / "README.md").write_text("# FLC\n", encoding="utf-8")
        (self.root / "doc/HARNESS.md").write_text("# Harness\n", encoding="utf-8")
        (self.root / "doc/thesis/08-testing.md").write_text("# Testing\n", encoding="utf-8")
        (self.root / "src/main/java/compiler/parser/Parser.y").write_text("%%\n", encoding="utf-8")
        self.real_run(["git", "add", "README.md", "doc", "src", "scripts"],
                      cwd=self.root, check=True)
        self.state = self.root / "output/state.json"
        self.patches = self.root / "output/proposed_patches"

    def proposal(self, invalid=False):
        before = "-Use `src/main/java/parser/Parser.y`." if not invalid else "-Text not present."
        diff = (
            "diff --git a/doc/modules/parser.md b/doc/modules/parser.md\n"
            "--- a/doc/modules/parser.md\n"
            "+++ b/doc/modules/parser.md\n"
            "@@ -1,2 +1,2 @@\n"
            + before + "\n"
            "-Run `./gradlew test`.\n"
            "+Use `src/main/java/compiler/parser/Parser.y`.\n"
            "+Run `mvn test`.\n"
        )
        # A finding is only a candidate; the agent must cite a checked source.
        return self.result("PATCH_PROPOSED", source="doc/modules/parser.md",
                           metadata={key: "verified" for key in (
                               "id", "objective", "motivation", "scope",
                               "changes", "risks", "validation", "dependencies",
                           )}) + diff

    def result(self, outcome="NO_CHANGES", source="src/main/java/compiler/parser/Parser.y",
               metadata=None):
        report = {
            "outcome": outcome,
            "reason": "Inspected the repository fixture",
            "inspected_sources": [source],
            "evidence": [{"path": source, "detail": "Source was inspected"}],
        }
        if metadata is not None:
            report["metadata"] = metadata
        return "HARNESS_RESULT_JSON: " + json.dumps(report) + "\n"

    def run_harness(self, doc_response):
        seen = []
        def fake_run(command, **kwargs):
            if command[0] == "git":
                return self.real_run(command, **kwargs)
            if command[0] == "opencode":
                seen.append(tuple(command))
                output = doc_response if (
                    "--agent" in command
                    and command[command.index("--agent") + 1] == "doc-writer"
                    and "Document module parser" in command[-1]
                ) else self.result()
                return subprocess.CompletedProcess(command, 0, stdout=output, stderr="")
            if "scripts/audit_documentation_evidence.py" in command:
                # The documentation scanner really executes against the fixture.
                return self.real_run([sys.executable, "scripts/audit_documentation_evidence.py"],
                                     **kwargs)
            # Heavier generators/validators are synthetic; their plumbing is tested.
            return subprocess.CompletedProcess(command, 0, stdout="fixture script\n", stderr="")

        with patch("run_all_agents.ROOT", self.root), \
             patch("run_all_agents.DEFAULT_LOGS_DIR", self.root / "output/logs"), \
             patch("run_all_agents.DEFAULT_PATCHES_DIR", self.patches), \
             patch("run_all_agents.shutil.which", return_value="/usr/bin/opencode"), \
             patch("run_all_agents.subprocess.run", side_effect=fake_run), \
             contextlib.redirect_stdout(io.StringIO()), \
             contextlib.redirect_stderr(io.StringIO()):
            code = main(["--modules", "parser", "--state-file", str(self.state)])
        return code, json.loads(self.state.read_text(encoding="utf-8")), seen

    def test_detects_stale_docs_proposes_patch_without_applying_it(self):
        code, manifest, seen = self.run_harness(self.proposal())
        self.assertEqual(code, 0)
        self.assertTrue(seen, "The orchestration must invoke OpenCode steps")
        scan = json.loads((self.root / "output/documentation_evidence.json").read_text())
        categories = {item["category"] for item in scan["findings"]}
        self.assertIn("missing-source-reference", categories)
        self.assertIn("gradle-command-in-maven-repository", categories)
        steps = {item["step_id"]: item for item in manifest["steps"]}
        self.assertEqual(steps["docs_update_parser"]["agent_outcome"], "PATCH_PROPOSED")
        self.assertEqual(steps["docs_update_parser"]["state"], "SUCCEEDED")
        self.assertEqual(steps["build_thesis"]["reason"], "pending-human-approval")
        self.assertTrue(manifest["review_required"])
        self.assertTrue(all(
            area["status"] == "EXECUTED"
            for area in manifest["functional_coverage"].values()
        ))
        proposed = self.patches / "docs_update_parser.patch"
        self.assertTrue(proposed.is_file())
        self.assertEqual(self.real_run(
            ["git", "apply", "--check", "--whitespace=error", str(proposed)],
            cwd=self.root, capture_output=True,
        ).returncode, 0)
        self.assertEqual((self.root / "doc/modules/parser.md").read_text(), self.original)
        self.assertEqual(self.real_run(
            ["git", "diff", "--exit-code"], cwd=self.root, capture_output=True,
        ).returncode, 0)

    def test_invalid_patch_fails_and_is_never_saved(self):
        code, manifest, _ = self.run_harness(self.proposal(invalid=True))
        self.assertEqual(code, 1)
        steps = {item["step_id"]: item for item in manifest["steps"]}
        self.assertEqual(steps["docs_update_parser"]["state"], "FAILED")
        self.assertIn("invalid-agent-result", steps["docs_update_parser"]["reason"])
        self.assertFalse((self.patches / "docs_update_parser.patch").exists())
        self.assertEqual((self.root / "doc/modules/parser.md").read_text(), self.original)
        self.assertEqual(manifest["functional_coverage"]["technical_documentation"]["status"],
                         "FAILED")

    def test_missing_result_contract_fails_even_on_zero_exit(self):
        code, manifest, _ = self.run_harness("The docs are fine.\n")
        self.assertEqual(code, 1)
        steps = {item["step_id"]: item for item in manifest["steps"]}
        self.assertEqual(steps["docs_update_parser"]["state"], "FAILED")
        self.assertIn("HARNESS_RESULT_JSON", steps["docs_update_parser"]["reason"])
        self.assertFalse((self.patches / "docs_update_parser.patch").exists())

    def test_pipeline_exposes_all_six_responsibilities(self):
        specs = build_pipeline(["parser"])
        commands = {spec.step_id: spec.command for spec in specs}
        self.assertIn("documentation_inventory", commands)
        self.assertIn("javadoc_inventory", commands)
        self.assertIn("thesis_update", commands)
        self.assertIn("readme_update", commands)
        self.assertIn("general_statistics", commands)
        self.assertIn("generate_diagrams", commands)


if __name__ == "__main__":
    unittest.main()
