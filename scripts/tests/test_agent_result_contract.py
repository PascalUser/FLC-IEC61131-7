import json
import subprocess
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

from run_all_agents import (
    StepRecord, StepSpec, StepState, execute_step, validate_agent_output,
)


class AgentOutputContractTests(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.addCleanup(self.tmp.cleanup)
        self.root = Path(self.tmp.name)
        subprocess.run(["git", "init", "-q"], cwd=self.root, check=True)
        (self.root / "sample.txt").write_text("before\n", encoding="utf-8")
        subprocess.run(["git", "add", "sample.txt"], cwd=self.root, check=True)

    def report(self, outcome="NO_CHANGES", **extra):
        data = {
            "outcome": outcome,
            "reason": "Observed checked source",
            "inspected_sources": ["sample.txt"],
            "evidence": [{"path": "sample.txt", "detail": "Verified current content"}],
            **extra,
        }
        return "HARNESS_RESULT_JSON: " + json.dumps(data) + "\n"

    def test_no_changes_requires_existing_source_and_evidence(self):
        result = validate_agent_output(self.report(), self.root)
        self.assertEqual(result["outcome"], "NO_CHANGES")
        with self.assertRaisesRegex(ValueError, "outside repository"):
            validate_agent_output(self.report(inspected_sources=["../outside"]), self.root)
        with self.assertRaisesRegex(ValueError, "evidence"):
            validate_agent_output(self.report(evidence=[]), self.root)

    def test_empty_or_ambiguous_output_fails(self):
        with self.assertRaisesRegex(ValueError, "exactly one"):
            validate_agent_output("All good!", self.root)
        with self.assertRaisesRegex(ValueError, "exactly one"):
            validate_agent_output(self.report() * 2, self.root)
        with self.assertRaisesRegex(ValueError, "cannot include"):
            validate_agent_output(self.report() + self.valid_patch(), self.root)

    def valid_patch(self):
        return ("diff --git a/sample.txt b/sample.txt\n"
                "--- a/sample.txt\n+++ b/sample.txt\n"
                "@@ -1 +1 @@\n-before\n+after\n")

    def metadata(self):
        return {key: "verified" for key in (
            "id", "objective", "motivation", "scope", "changes",
            "risks", "validation", "dependencies",
        )}

    def test_patch_is_checked_but_not_applied(self):
        before = (self.root / "sample.txt").read_text()
        content = self.report("PATCH_PROPOSED", metadata=self.metadata()) + self.valid_patch()
        result = validate_agent_output(content, self.root)
        self.assertEqual(result["outcome"], "PATCH_PROPOSED")
        self.assertEqual((self.root / "sample.txt").read_text(), before)
        with self.assertRaisesRegex(ValueError, "does not apply"):
            validate_agent_output(content.replace("-before", "-missing"), self.root)

    def test_missing_metadata_and_blocked(self):
        with self.assertRaisesRegex(ValueError, "metadata"):
            validate_agent_output(self.report("PATCH_PROPOSED") + self.valid_patch(), self.root)
        blocked = validate_agent_output(self.report("BLOCKED", inspected_sources=[], evidence=[]), self.root)
        self.assertEqual(blocked["outcome"], "BLOCKED")


    def test_agent_step_does_not_accept_empty_stdout(self):
        spec = StepSpec("agent", "check", ["opencode", "run"], "opencode")
        record = StepRecord("agent", "check", "opencode", True, spec.command)
        completed = subprocess.CompletedProcess(spec.command, 0, stdout="", stderr="")
        with patch("run_all_agents.ROOT", self.root), patch(
            "run_all_agents.subprocess.run", return_value=completed
        ) as run:
            execute_step(spec, record, logs_dir=self.root / "logs",
                         patches_dir=self.root / "patches", capture_logs=False)
            self.assertTrue(run.call_args.kwargs["capture_output"])
        self.assertEqual(record.state, StepState.FAILED)
        self.assertIn("invalid-agent-result", record.reason)
        self.assertFalse((self.root / "patches").exists())

    def test_agent_step_succeeds_with_evidenced_no_changes(self):
        spec = StepSpec("agent", "check", ["opencode", "run"], "opencode")
        record = StepRecord("agent", "check", "opencode", True, spec.command)
        completed = subprocess.CompletedProcess(spec.command, 0, stdout=self.report(), stderr="")
        with patch("run_all_agents.ROOT", self.root), patch(
            "run_all_agents.subprocess.run", return_value=completed
        ):
            execute_step(spec, record, logs_dir=self.root / "logs",
                         patches_dir=self.root / "patches", capture_logs=False)
        self.assertEqual(record.state, StepState.SUCCEEDED)
        self.assertEqual(record.agent_outcome, "NO_CHANGES")
        self.assertEqual(record.inspected_sources, ["sample.txt"])
        self.assertEqual(record.proposed_patch_file, None)


if __name__ == "__main__":
    unittest.main()
