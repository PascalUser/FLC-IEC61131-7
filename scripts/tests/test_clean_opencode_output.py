"""Regression tests for clean OpenCode logs without mutating result contracts."""
from __future__ import annotations

import contextlib
import io
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))

from run_all_agents import (  # noqa: E402
    StepRecord, StepSpec, StepState, clean_terminal_output, execute_step,
)


class CleanOpenCodeOutputTests(unittest.TestCase):
    def test_csi_osc_and_carriage_returns(self):
        raw = ("\x1b[0m> doc-writer\n\x1b[93m\x1b[1m! \x1b[0mretrying\r"
               "\x1b]8;;https://example.test\x07link\x1b]8;;\x07")
        result = clean_terminal_output(raw)
        self.assertNotIn("\x1b", result)
        self.assertEqual(result, "> doc-writer\n! retrying\nlink")

    def test_failed_agent_prints_and_saves_clean_stderr(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            spec = StepSpec("docs_update_parser", "agent", ["opencode", "run"], "opencode")
            record = StepRecord(spec.step_id, spec.description, spec.kind, True, spec.command)
            completed = subprocess.CompletedProcess(
                spec.command, 1, stdout="", stderr="\x1b[0m\n\x1b[91m\x1b[1mError: \x1b[0mFree usage limit reached.\n"
            )
            stderr = io.StringIO()
            with patch("run_all_agents.ROOT", root), patch(
                "run_all_agents.subprocess.run", return_value=completed
            ), contextlib.redirect_stderr(stderr), contextlib.redirect_stdout(io.StringIO()):
                execute_step(spec, record, logs_dir=root / "output/logs",
                             patches_dir=root / "output/patches", capture_logs=True)
            self.assertEqual(record.state, StepState.FAILED)
            self.assertNotIn("\x1b", stderr.getvalue())
            self.assertIn("Error: Free usage limit reached.", stderr.getvalue())
            log = (root / "output/logs/docs_update_parser.log").read_text()
            self.assertNotIn("\x1b", log)
            self.assertIn("--- STDERR ---", log)

    def test_validation_receives_raw_stdout_not_display_copy(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            spec = StepSpec("docs_update_parser", "agent", ["opencode", "run"], "opencode")
            record = StepRecord(spec.step_id, spec.description, spec.kind, True, spec.command)
            raw = "\x1b[32magent message\x1b[0m\nHARNESS_RESULT_JSON: placeholder\n"
            completed = subprocess.CompletedProcess(spec.command, 0, stdout=raw, stderr="")
            validated = {"outcome": "NO_CHANGES", "reason": "checked", "inspected_sources": [],
                         "evidence": [], "patch": None}
            shown = io.StringIO()
            with patch("run_all_agents.ROOT", root), patch(
                "run_all_agents.subprocess.run", return_value=completed
            ), patch("run_all_agents.validate_agent_output", return_value=validated) as validate, \
                    contextlib.redirect_stdout(shown):
                execute_step(spec, record, logs_dir=root / "output/logs",
                             patches_dir=root / "output/patches", capture_logs=True)
            self.assertEqual(record.state, StepState.SUCCEEDED)
            validate.assert_called_once_with(raw, root)
            self.assertNotIn("\x1b", shown.getvalue())
            self.assertNotIn("\x1b", (root / "output/logs/docs_update_parser.log").read_text())

    def test_agent_still_displays_errors_without_log_capture(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            spec = StepSpec("docs_update_parser", "agent", ["opencode", "run"], "opencode")
            record = StepRecord(spec.step_id, spec.description, spec.kind, True, spec.command)
            completed = subprocess.CompletedProcess(spec.command, 1,
                                                    stdout="", stderr="\x1b[31mError\x1b[0m\n")
            stderr = io.StringIO()
            with patch("run_all_agents.ROOT", root), patch(
                "run_all_agents.subprocess.run", return_value=completed
            ), contextlib.redirect_stderr(stderr), contextlib.redirect_stdout(io.StringIO()):
                execute_step(spec, record, logs_dir=root / "output/logs",
                             patches_dir=root / "output/patches", capture_logs=False)
            self.assertEqual(record.state, StepState.FAILED)
            self.assertEqual(stderr.getvalue(), "Error\n")
            self.assertFalse((root / "output/logs").exists())


if __name__ == "__main__":
    unittest.main()
