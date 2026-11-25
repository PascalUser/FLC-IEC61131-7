"""Opt-in bounded repair submits a new turn, never reuses the old answer."""
from __future__ import annotations

import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))
from scripts.agent_repair import repair_agent_turn  # noqa: E402


class AgentRepairTests(unittest.TestCase):
    def test_repair_uses_same_session_and_current_turn_only(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            sid = "ses_abc123456789"
            report = 'HARNESS_RESULT_JSON: {"outcome":"BLOCKED","reason":"test"}'
            events = [
                {"type": "text", "sessionID": sid, "part": {"text": report}},
            ]
            calls = []
            def fake_run(command, **kwargs):
                calls.append(command)
                return subprocess.CompletedProcess(command, 0,
                    stdout="\n".join(json.dumps(e) for e in events), stderr="")
            with patch("scripts.agent_repair.subprocess.run", side_effect=fake_run):
                text = repair_agent_turn(
                    ["opencode", "run", "--agent", "doc-writer", "original"],
                    "docs_update_parser", root, sid, "google/gemini-3.6-flash",
                    "expected exactly one HARNESS_RESULT_JSON line",
                    logs_dir=root / "output/logs", capture_logs=True,
                )
            self.assertEqual(text, report)
            self.assertEqual(len(calls), 1)
            self.assertIn("--session", calls[0])
            self.assertEqual(calls[0][calls[0].index("--session") + 1], sid)
            self.assertEqual(calls[0][calls[0].index("--model") + 1], "google/gemini-3.6-flash")
            self.assertTrue((root / "output/logs/docs_update_parser.log").exists())

    def test_fails_closed_on_provider_error_or_session_mismatch(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            cases = [
                (1, {"type": "error", "sessionID": "ses_abc123456789",
                     "error": {"message": "Rate limit exceeded: 429"}}, "repair request failed"),
                (0, {"type": "text", "sessionID": "ses_wrong1234567",
                     "part": {"text": "fake"}}, "different OpenCode session"),
            ]
            for code, event, pattern in cases:
                with self.subTest(pattern=pattern), patch(
                    "scripts.agent_repair.subprocess.run",
                    return_value=subprocess.CompletedProcess(["opencode"], code,
                        stdout=json.dumps(event), stderr=""),
                ), self.assertRaisesRegex(ValueError, pattern):
                    repair_agent_turn(
                        ["opencode", "run", "--agent", "doc-writer", "task"],
                        "docs_update_parser", root, "ses_abc123456789", None,
                        "bad output", logs_dir=root / "logs", capture_logs=False,
                    )


if __name__ == "__main__":
    unittest.main()
