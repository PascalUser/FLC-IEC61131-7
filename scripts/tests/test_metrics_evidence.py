"""Source-backed statistics and integration contract regression tests."""
import json
from pathlib import Path
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))
sys.path.insert(0, str(ROOT / "scripts"))
from metrics_evidence import input_fingerprint, provenance, validate_statistics
from run_all_agents import build_pipeline
import synthesize_thesis_context


class MetricsEvidenceTests(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.addCleanup(self.tmp.cleanup)
        self.root = Path(self.tmp.name)
        for relative in ("src/main/java/compiler", "src/test/java", "doc"):
            (self.root / relative).mkdir(parents=True)
        self.source = self.root / "src/main/java/compiler/lexer/A.java"
        self.source.parent.mkdir(parents=True)
        self.source.write_text("package compiler.lexer; class A {}\n", encoding="utf-8")
        self.stats = {
            "modules": {name: {"java_files": int(name == "lexer"), "non_comment_lines": int(name == "lexer")}
                        for name in ("lexer", "parser", "ast", "generator", "repository")},
            "tests": {"java_files": 0, "unit_test_classes": 0, "integration_test_classes": 0},
            "jacoco_line_coverage": None,
            **provenance(self.root),
        }

    def test_valid_source_backed_snapshot(self):
        self.assertEqual(validate_statistics(self.stats, self.root), self.stats)
        self.assertEqual(len(input_fingerprint(self.root)), 64)

    def test_stale_snapshot_is_rejected(self):
        self.source.write_text("package compiler.lexer; class B {}\n", encoding="utf-8")
        with self.assertRaisesRegex(ValueError, "stale"):
            validate_statistics(self.stats, self.root)

    def test_legacy_and_invented_counts_are_rejected(self):
        legacy = dict(self.stats, schema_version=1)
        with self.assertRaisesRegex(ValueError, "schema_version"):
            validate_statistics(legacy, self.root)
        invented = json.loads(json.dumps(self.stats))
        invented["modules"]["lexer"]["java_files"] = 99
        with self.assertRaisesRegex(ValueError, "disagree"):
            validate_statistics(invented, self.root)
        invented["modules"]["lexer"]["java_files"] = 1
        invented["modules"]["lexer"]["non_comment_lines"] = 900
        with self.assertRaisesRegex(ValueError, "disagree"):
            validate_statistics(invented, self.root)

    def test_coverage_requires_report_and_consistent_counts(self):
        stale = dict(self.stats, jacoco_line_coverage={"covered_lines": 3,
                       "missed_lines": 1, "overall_percent": 75.0})
        with self.assertRaisesRegex(ValueError, "JaCoCo XML"):
            validate_statistics(stale, self.root)

    def test_synthesis_rejects_legacy_and_uses_canonical_file(self):
        path = self.root / "doc/stats_general.json"
        path.write_text(json.dumps(self.stats), encoding="utf-8")
        original = synthesize_thesis_context.STATS_FILE
        original_root = synthesize_thesis_context.ROOT
        try:
            synthesize_thesis_context.STATS_FILE = path
            synthesize_thesis_context.ROOT = self.root
            self.assertEqual(synthesize_thesis_context.load_stats(), self.stats)
            path.write_text(json.dumps({"test_coverage": {"overall": 89}}), encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "schema_version"):
                synthesize_thesis_context.load_stats()
        finally:
            synthesize_thesis_context.STATS_FILE = original
            synthesize_thesis_context.ROOT = original_root

    def test_default_pipeline_never_runs_legacy_estimates(self):
        steps = build_pipeline(["parser"])
        self.assertIn("general_statistics", [s.step_id for s in steps])
        self.assertNotIn("extract_stats", [s.step_id for s in steps])
        self.assertIn("extract_stats", [s.step_id for s in
                                     build_pipeline(["parser"], include_legacy_stats=True)])


if __name__ == "__main__":
    unittest.main()
