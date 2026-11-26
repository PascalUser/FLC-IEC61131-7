"""Offline tests for the read-only repository baseline inventory."""
import json
import subprocess
import tempfile
import unittest
from pathlib import Path

from scripts.audit_repository_baseline import audit, main


class RepositoryBaselineTests(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.addCleanup(self.tmp.cleanup)
        self.root = Path(self.tmp.name)
        subprocess.run(["git", "init", "-q", str(self.root)], check=True)

    def add(self, name, content):
        dest = self.root / name
        dest.parent.mkdir(parents=True, exist_ok=True)
        dest.write_text(content, encoding="utf-8")
        subprocess.run(["git", "-C", str(self.root), "add", "--", name], check=True)

    def test_findings_and_no_source_mutation(self):
        self.add("README.md", "`scripts/obsolete.py`\n`src/main/java/compiler/parser/Parser.y`\n")
        self.add("doc/HARNESS.md", "python3 scripts/obsolete.py\n")
        self.add("scripts/generate_charts.py", "import runpy\nrunpy.run_path('scripts/extract_stats.py')\n")
        self.add("requirements.txt", "matplotlib\n")
        self.add("Dockerfile", "RUN pip install matplotlib\n")
        self.add("docker-compose.yml", "    env_file: .env\n    volumes:\n      - ./.env:/workspace/.env:ro\n")
        self.add(".env.example", "API_KEY=SECRET_SHOULD_NOT_APPEAR\nUID=1000\n")
        self.add("doc/stats.json", "{}\n")
        self.add("doc/stats_general.json", "{}\n")
        self.add("scripts/solo.py", "# dynamic entry possible\n")
        self.add("src/main/java/compiler/parser/Parser.y", "grammar\n")
        before = (self.root / "README.md").read_bytes()
        report = audit(self.root)
        self.assertEqual((self.root / "README.md").read_bytes(), before)
        codes = {x["code"] for x in report["findings"]}
        self.assertTrue({"MISSING_DOCUMENTED_PATH", "MISSING_SCRIPT_REFERENCE",
                         "BROKEN_CHART_WRAPPER", "DEPENDENCY_SOURCE_DUPLICATION",
                         "REDUNDANT_ENV_MOUNT_CANDIDATE", "DOCKERIGNORE_ABSENT",
                         "MULTIPLE_STATISTICS_SNAPSHOTS"}.issubset(codes))
        self.assertIn("scripts/solo.py", [x["path"] for x in report["cleanup_candidates"]])
        self.assertNotIn("SECRET_SHOULD_NOT_APPEAR", json.dumps(report))
        self.assertTrue(all(x["status"] == "por_verificar" for x in report["cleanup_candidates"]))

    def test_is_deterministic_and_ignores_untracked_secrets(self):
        self.add("README.md", "No references.\n")
        self.add(".env.example", "TOKEN=placeholder\n")
        (self.root / ".env").write_text("SECRET=PRIVATE_VALUE", encoding="utf-8")
        a = audit(self.root)
        b = audit(self.root)
        self.assertEqual(a, b)
        self.assertNotIn("PRIVATE_VALUE", json.dumps(a))
        self.assertNotIn("placeholder", json.dumps(a))

    def test_cli_output_is_confined_to_output(self):
        self.add("README.md", "Hello\n")
        with self.assertRaises(SystemExit):
            main(["--root", str(self.root), "--output", "README.md"])
        self.assertEqual(main(["--root", str(self.root)]), 0)
        self.assertTrue((self.root / "output/repository_baseline.json").exists())
        self.assertFalse((self.root / "output/repository_baseline.json").is_symlink())

    def test_no_false_positive_for_existing_path(self):
        self.add("README.md", "`src/main/java/compiler/parser/Parser.y`\n")
        self.add("src/main/java/compiler/parser/Parser.y", "parser\n")
        report = audit(self.root)
        self.assertFalse(any(x["code"] == "MISSING_DOCUMENTED_PATH" for x in report["findings"]))


if __name__ == "__main__":
    unittest.main()
