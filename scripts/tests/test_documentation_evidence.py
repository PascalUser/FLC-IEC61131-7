"""The documentation inventory must expose repair targets without touching docs."""
import json
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from audit_documentation_evidence import audit_documentation, main  # noqa: E402


class DocumentationEvidenceTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        (self.root / 'doc/modules').mkdir(parents=True)
        (self.root / 'doc/thesis').mkdir(parents=True)
        (self.root / 'src/main/java/compiler/parser').mkdir(parents=True)
        (self.root / 'src/main/java/compiler/parser/Parser.y').write_text('%%\n')
        (self.root / 'README.md').write_text('# README\n')
        (self.root / 'doc/HARNESS.md').write_text('# HARNESS\n')

    def test_detects_real_missing_java_path_and_gradle_command(self):
        source = (self.root / 'doc/modules/parser.md')
        source.write_text('Use `src/main/java/parser/Parser.y`.\n'
                          'Real `src/main/java/compiler/parser/Parser.y`.\n'
                          'Run `./gradlew test`.\n')
        before = source.read_bytes()
        report = audit_documentation(self.root)
        categories = [f['category'] for f in report['findings']]
        self.assertEqual(categories.count('missing-source-reference'), 1)
        self.assertEqual(categories.count('gradle-command-in-maven-repository'), 1)
        self.assertEqual(report['findings'][0]['line'], 1)
        self.assertEqual(source.read_bytes(), before)

    def test_ignores_wildcards_and_reports_checked_files(self):
        (self.root / 'README.md').write_text('Inspect `src/main/java/compiler/**/*.java`.\n')
        report = audit_documentation(self.root)
        self.assertEqual(report['findings'], [])
        self.assertIn('README.md', report['sources'])

    def test_machine_report_check_mode_is_read_only(self):
        (self.root / 'doc/thesis/08-testing.md').write_text('Use ./gradlew test\n')
        destination = self.root / 'output/report.json'
        with patch('audit_documentation_evidence.ROOT', self.root):
            self.assertEqual(main(['--output', str(destination)]), 0)
            before = destination.read_bytes()
            self.assertEqual(main(['--output', str(destination), '--check']), 0)
            self.assertEqual(destination.read_bytes(), before)
            (self.root / 'doc/thesis/08-testing.md').write_text('Use mvn test\n')
            self.assertEqual(main(['--output', str(destination), '--check']), 1)
            self.assertEqual(destination.read_bytes(), before)
            self.assertEqual(len(json.loads(before)['findings']), 1)


    def test_pipeline_and_agents_reference_the_inventory(self):
        repo = Path(__file__).resolve().parents[2]
        runner = (repo / 'run_all_agents.py').read_text(encoding='utf-8')
        self.assertIn('step_id="documentation_inventory"', runner)
        self.assertIn('scripts/audit_documentation_evidence.py', runner)
        self.assertLess(runner.index('step_id="documentation_inventory"'),
                        runner.index('    for pkg in modules:'))
        for name in ('doc-writer', 'readme-writer', 'thesis-writer',
                     'harness-documenter'):
            policy = (repo / '.opencode/agents' / (name + '.md')).read_text(encoding='utf-8')
            self.assertIn('output/documentation_evidence.json', policy, name)
            self.assertIn('HARNESS_RESULT_JSON:', policy, name)


if __name__ == '__main__':
    unittest.main()
