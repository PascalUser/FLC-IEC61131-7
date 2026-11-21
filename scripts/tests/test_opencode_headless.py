"""Headless OpenCode v2 invocation preserves configured agent and task text."""
import json
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))
from run_all_agents import build_opencode_argv, build_pipeline  # noqa: E402


class HeadlessOpenCodeTests(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.addCleanup(self.tmp.cleanup)
        self.root = Path(self.tmp.name)
        self.config = {
            "agent": {"doc-writer": {}, "readme-writer": {}},
            "command": {
                "docs-update": {
                    "agent": "doc-writer",
                    "template": "Document module $ARGUMENTS using current Java sources",
                },
                "readme-update": {
                    "agent": "readme-writer",
                    "template": "Check README against pom.xml",
                },
            },
        }
        (self.root / "opencode.json").write_text(json.dumps(self.config))

    def test_expands_template_and_selects_agent_in_both_legacy_modes(self):
        with patch("run_all_agents.ROOT", self.root):
            for mode in ("slash", "command"):
                argv = build_opencode_argv("docs-update", ["parser"], mode)
                self.assertEqual(argv[:4], ["opencode", "run", "--agent", "doc-writer"])
                self.assertIn("Document module parser", argv[4])
                self.assertIn("HARNESS_RESULT_JSON", argv[4])
                self.assertNotIn("--command", argv)
                self.assertNotIn("/docs-update", argv)

    def test_prompt_without_argument_placeholder_appends_arguments(self):
        with patch("run_all_agents.ROOT", self.root):
            argv = build_opencode_argv("readme-update", ["extra"], "slash")
        self.assertIn("Check README against pom.xml\n\nextra", argv[-1])

    def test_missing_or_malformed_agent_configuration_fails_closed(self):
        with patch("run_all_agents.ROOT", self.root):
            with self.assertRaisesRegex(ValueError, "not configured"):
                build_opencode_argv("thesis-build", [], "slash")
            self.config["command"]["docs-update"]["agent"] = "unknown"
            (self.root / "opencode.json").write_text(json.dumps(self.config))
            with self.assertRaisesRegex(ValueError, "Invalid OpenCode"):
                build_opencode_argv("docs-update", [], "slash")

    def test_real_pipeline_uses_agent_prompts_not_literal_slash_commands(self):
        specs = build_pipeline(["parser"])
        self.assertTrue(any(spec.kind == "opencode" for spec in specs))
        for spec in specs:
            if spec.kind == "opencode":
                self.assertEqual(spec.command[:3], ["opencode", "run", "--agent"])
                self.assertIn("HARNESS_RESULT_JSON", spec.command[-1])


if __name__ == "__main__":
    unittest.main()
