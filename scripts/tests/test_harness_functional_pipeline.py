"""Regression contract for all six HARNESS functional responsibilities."""
import json
from pathlib import Path
import sys
import tempfile
import unittest
from unittest.mock import patch

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))
from run_all_agents import (  # noqa: E402
    FUNCTIONAL_AREAS, StepRecord, StepState, build_pipeline,
    functional_coverage, main,
)


class HarnessFunctionalPipelineTests(unittest.TestCase):
    def test_six_areas_have_executable_steps(self):
        specs = build_pipeline(["compiler", "parser"])
        ids = {spec.step_id for spec in specs}
        self.assertEqual(len(FUNCTIONAL_AREAS), 6)
        for area, prefixes in FUNCTIONAL_AREAS.items():
            self.assertTrue(any(
                name == prefix or (prefix.endswith("_") and name.startswith(prefix))
                for name in ids for prefix in prefixes
            ), area)
        for required in ("harness_documentation", "thesis_update",
                         "javadoc_fix", "parser_actions_audit"):
            self.assertIn(required, ids)

    def test_command_configuration_matches_agent_steps(self):
        config = json.loads((ROOT / "opencode.json").read_text(encoding="utf-8"))
        specs = build_pipeline(["compiler"])
        for spec in specs:
            if spec.kind == "opencode":
                self.assertIn(spec.opencode_command, config["command"])
                agent = config["command"][spec.opencode_command]["agent"]
                self.assertIn(agent, config["agent"])

    def test_coverage_does_not_confuse_skips_with_success(self):
        specs = build_pipeline(["parser"])
        records = [StepRecord(s.step_id, s.description, s.kind, s.blocking,
                              s.command, state=StepState.SUCCEEDED) for s in specs]
        covered = functional_coverage(records)
        self.assertTrue(all(v["status"] == "EXECUTED" for v in covered.values()))
        for item in records:
            if item.step_id == "thesis_update":
                item.state = StepState.SKIPPED
        self.assertEqual(functional_coverage(records)["thesis"]["status"], "INCOMPLETE")
        for item in records:
            if item.step_id == "javadoc_fix":
                item.state = StepState.FAILED
        self.assertEqual(functional_coverage(records)["javadocs_and_parser_actions"]["status"], "FAILED")

    def test_skip_thesis_excludes_proposal_and_compilation(self):
        with tempfile.TemporaryDirectory() as tmp:
            state = Path(tmp) / "run.json"
            self.assertEqual(main(["--dry-run", "--skip-thesis", "--modules", "parser",
                                   "--state-file", str(state)]), 0)
            records = {r["step_id"]: r for r in json.loads(state.read_text())["steps"]}
            for name in ("thesis_update", "build_thesis"):
                self.assertEqual(records[name]["reason"], "skipped-by-flag:--skip-thesis")

    def test_unapproved_proposal_prevents_thesis_build(self):
        with tempfile.TemporaryDirectory() as tmp:
            state = Path(tmp) / "run.json"
            def fake_step(spec, rec, **kwargs):
                rec.transition_to(StepState.RUNNING)
                if spec.step_id == "thesis_update":
                    rec.agent_outcome = "PATCH_PROPOSED"
                elif spec.kind == "opencode":
                    rec.agent_outcome = "NO_CHANGES"
                rec.transition_to(StepState.SUCCEEDED, reason="ok", returncode=0)

            with patch("run_all_agents.execute_step", side_effect=fake_step), \
                 patch("run_all_agents.shutil.which", return_value="/usr/bin/opencode"):
                self.assertEqual(main(["--modules", "parser", "--state-file", str(state)]), 0)
            payload = json.loads(state.read_text())
            records = {r["step_id"]: r for r in payload["steps"]}
            self.assertTrue(payload["review_required"])
            self.assertEqual(records["build_thesis"]["state"], "SKIPPED")
            self.assertEqual(records["build_thesis"]["reason"], "pending-human-approval")
            self.assertEqual(payload["functional_coverage"]["thesis"]["status"], "EXECUTED")


if __name__ == "__main__":
    unittest.main()
