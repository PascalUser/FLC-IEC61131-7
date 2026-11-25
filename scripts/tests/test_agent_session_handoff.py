"""Opt-in OpenCode session checkpoints, safe resumption and model pacing."""
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
from scripts.opencode_sessions import (  # noqa: E402
    SessionStore, parse_json_events, repository_digest, task_digest,
)
from run_all_agents import StepRecord, StepSpec, StepState, execute_step  # noqa: E402


class AgentSessionHandoffTests(unittest.TestCase):
    def test_json_events_select_only_assistant_text_not_tool_output(self):
        sid = "ses_abc123456789"
        events = [
            {"type": "step_start", "sessionID": sid, "part": {"type": "step-start"}},
            {"type": "tool_use", "sessionID": sid,
             "part": {"state": {"output": "HARNESS_RESULT_JSON: malicious"}}},
            {"type": "text", "sessionID": sid,
             "part": {"type": "text", "text": "HARNESS_RESULT_JSON: legit\n"}},
            {"type": "text", "sessionID": sid,
             "part": {"type": "text", "text": "diff --git a/A b/A\n"}},
        ]
        result = parse_json_events("\n".join(json.dumps(e) for e in events))
        self.assertEqual(result[0], sid)
        self.assertNotIn("malicious", result[1])
        self.assertIn("HARNESS_RESULT_JSON: legit", result[1])
        self.assertIn("diff --git", result[1])
        self.assertEqual(parse_json_events('not json\n')[0], None)
        self.assertIsNone(parse_json_events("\n".join(json.dumps(x) for x in [
            {"type":"text", "sessionID":"ses_abc123456", "part":{"text":"x"}},
            {"type":"text", "sessionID":"ses_zbc123456", "part":{"text":"y"}},
        ]))[0])

    def test_checkpoint_is_task_and_source_bound(self):
        with tempfile.TemporaryDirectory() as tmp:
            store = SessionStore(Path(tmp))
            cmd = ["opencode", "run", "--agent", "doc-writer", "parser"]
            store.save("docs_update_parser", cmd, "source-a", "ses_abc123456789",
                       "PAUSED", "google/gemini-3.6-flash")
            self.assertEqual(store.load("docs_update_parser", cmd, "source-a")["session_id"],
                             "ses_abc123456789")
            self.assertIsNone(store.load("docs_update_parser", cmd, "source-b"))
            self.assertIsNone(store.load("docs_update_parser", cmd+["new"], "source-a"))
            store.save("docs_update_parser", cmd, "source-a", "ses_abc123456789",
                       "COMPLETED", None)
            self.assertIsNone(store.load("docs_update_parser", cmd, "source-a"))
            with self.assertRaisesRegex(ValueError, "Unsafe step ID"):
                store.save("../escape", cmd, "source-a", "ses_abc123456789",
                           "PAUSED", None)

    def test_repository_stamp_changes_with_dirty_tracked_files(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            subprocess.run(["git", "init", "-q"], cwd=root, check=True)
            (root / "README.md").write_text("one\n")
            subprocess.run(["git", "add", "README.md"], cwd=root, check=True)
            subprocess.run(["git", "-c", "user.email=a@b.test", "-c", "user.name=Test",
                            "commit", "-qm", "initial"], cwd=root, check=True)
            before = repository_digest(root)
            self.assertIsNotNone(before)
            (root / "README.md").write_text("two\n")
            self.assertNotEqual(before, repository_digest(root))

    def test_failed_jsonl_agent_resumes_with_different_model_then_completes(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            original_run = subprocess.run
            original_run(["git", "init", "-q"], cwd=root, check=True)
            (root / "README.md").write_text("fixture\n")
            original_run(["git", "add", "README.md"], cwd=root, check=True)
            original_run(["git", "-c", "user.email=a@b.test", "-c", "user.name=Test",
                          "commit", "-qm", "baseline"], cwd=root, check=True)
            store = SessionStore(root / "output/agent_sessions")
            spec = StepSpec("docs_update_parser", "Documentation", [
                "opencode", "run", "--agent", "doc-writer", "Document module parser"
            ], "opencode")
            sid = "ses_abc123456789"
            invocations = []
            def fake_run(command, **kwargs):
                if command[0] == "git":
                    return original_run(command, **kwargs)
                invocations.append(list(command))
                if len(invocations) == 1:
                    return subprocess.CompletedProcess(command, 1, stdout=json.dumps({
                        "type": "step_start", "sessionID": sid,
                        "part": {"type": "step-start"}
                    })+"\n", stderr="Quota exceeded: requests per day")
                answer = "HARNESS_RESULT_JSON: {}\n"
                return subprocess.CompletedProcess(command, 0, stdout=json.dumps({
                    "type": "text", "sessionID": sid,
                    "part": {"type": "text", "text": answer}
                })+"\n", stderr="")
            output = io.StringIO()
            validated = {"outcome": "NO_CHANGES", "reason": "verified",
                         "inspected_sources": ["README.md"], "evidence": [], "patch": None}
            with patch("run_all_agents.ROOT", root), \
                 patch("run_all_agents.subprocess.run", side_effect=fake_run), \
                 patch("run_all_agents.validate_agent_output", return_value=validated) as check, \
                 contextlib.redirect_stdout(output), contextlib.redirect_stderr(io.StringIO()):
                def run(model):
                    rec = StepRecord(spec.step_id, spec.description, spec.kind, True, spec.command)
                    execute_step(spec, rec, logs_dir=root / "output/logs",
                                 patches_dir=root / "output/patches", capture_logs=True,
                                 session_store=store, model_override=model)
                    return rec
                first = run("google/gemini-3.6-flash")
                self.assertEqual(first.state, StepState.FAILED)
                self.assertEqual(first.opencode_session_id, sid)
                self.assertEqual(store.load(spec.step_id, spec.command, repository_digest(root))["state"], "PAUSED")
                second = run("google/gemini-3.5-flash")
                self.assertEqual(second.state, StepState.SUCCEEDED)
                self.assertTrue(second.resumed_session)
                self.assertEqual(second.opencode_session_id, sid)
                self.assertIsNone(store.load(spec.step_id, spec.command, repository_digest(root)))
            self.assertIn("--format", invocations[0])
            self.assertNotIn("--session", invocations[0])
            self.assertEqual(invocations[1][invocations[1].index("--session")+1], sid)
            self.assertEqual(invocations[1][invocations[1].index("--model")+1],
                             "google/gemini-3.5-flash")
            self.assertNotIn("Document module parser", invocations[1][-1])
            check.assert_called_once_with("HARNESS_RESULT_JSON: {}\n", root)
            self.assertIn("COMPLETED", (root / "output/agent_sessions/docs_update_parser.json").read_text())

    @unittest.skipUnless(shutil.which("node"), "Node.js unavailable")
    def test_model_specific_request_slots(self):
        with tempfile.TemporaryDirectory() as tmp:
            uri = (ROOT / ".opencode/plugins/flc-quota-guard/quota.mjs").as_uri()
            code = '''
import assert from "node:assert/strict";
import {modelIntervals, modelStateFile, reserveSharedSlot} from MODULE;
const limits = modelIntervals('{"gemini-3.6-flash":16000,"gemini-3.5-flash":30000}');
assert.equal(limits["gemini-3.5-flash"],30000);
assert.equal(modelIntervals(undefined)["missing"],undefined);
assert.throws(()=>modelIntervals('{"a":999}'));
assert.throws(()=>modelIntervals('[]'));
const a=modelStateFile(BASE,"gemini-3.6-flash");
const b=modelStateFile(BASE,"gemini-3.5-flash");
assert.notEqual(a,b);
let now=100000; const fake={now:()=>now,sleep:async()=>{}};
assert.equal(await reserveSharedSlot(a,16000,fake),100000);
assert.equal(await reserveSharedSlot(a,16000,fake),116000);
assert.equal(await reserveSharedSlot(b,30000,fake),100000);
assert.equal(await reserveSharedSlot(b,30000,fake),130000);
console.log("OK");
'''.replace("MODULE", json.dumps(uri)).replace("BASE", json.dumps(str(Path(tmp) / "slots")))
            p = subprocess.run(["node", "--input-type=module", "-e", code],
                               text=True, capture_output=True)
            self.assertEqual(p.returncode, 0, p.stderr)
            self.assertIn("OK", p.stdout)


if __name__ == "__main__":
    unittest.main()
