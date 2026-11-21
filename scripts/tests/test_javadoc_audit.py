"""Regression tests for source-backed Javadoc/Parser.y audit."""
import json
import sys
import tempfile
import unittest
from pathlib import Path

SCRIPTS = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(SCRIPTS))
from audit_javadocs import audit, documented, main, scan_java, scan_parser_actions  # noqa: E402


class JavadocAuditTests(unittest.TestCase):
    def test_documented_javadoc_with_annotation(self):
        sample = '/** Explains behavior. */\n@Override\npublic String name() { return "x"; }'
        self.assertTrue(documented(sample, sample.index('public')))
        self.assertFalse(documented('/* not Javadoc */\npublic void run() {}',
                                    len('/* not Javadoc */\n')))

    def test_handwritten_java_and_private_helpers_are_checked(self):
        java = '''/** Type documentation. */
public class Sample {
    /** Returns a result. */
    public int good() { return 1; }
    private int helper() { return 2; }
    /** Calls existing code. */
    private int caller() { return helper(); }
}
'''
        issues = scan_java(java, 'src/test/java/Sample.java')
        self.assertEqual([(f['code'], f['subject']) for f in issues],
                         [('METHOD_JAVADOC', 'helper')])

    def test_java_strings_comments_and_calls_do_not_count_as_methods(self):
        java = '''/** Class. */
public class Sample {
    // public void phantom() { }
    String s = "public void imaginary() { }";
    /** Real. */
    void run() { helper(); }
}
'''
        issues = scan_java(java, 'src/main/java/Sample.java')
        self.assertFalse(any(i['subject'] in {'phantom', 'imaginary', 'helper', 'run'} for i in issues))

    def test_complex_method_is_review_candidate_not_auto_todo(self):
        body = '\n'.join('        int value%d = %d;' % (i, i) for i in range(8))
        source = '/** Class. */\nclass Sample {\n/** Method. */\nvoid work() {\n' + body + '\n}\n}\n'
        issues = scan_java(source, 'src/main/java/Sample.java', max_method_lines=5)
        self.assertEqual([i['code'] for i in issues], ['REFACTOR_REVIEW'])
        self.assertNotIn('TODO', source)

    def test_bison_comments_must_be_inside_action(self):
        grammar = '''%language "Java"
%define api.parser.class {Parser}
%%
/** Preceding text is insufficient. */
program:
    TOKEN
    {
        publish();
        if (x) {
            helper();
        }
    }
;
other:
    TOKEN
    {
        /** Document the semantic action and side effects. */
        publish();
    }
;
%%
'''
        issues = scan_parser_actions(grammar, 'src/main/java/compiler/parser/Parser.y')
        self.assertEqual([i['code'] for i in issues], ['PARSER_ACTION_JAVADOC'])

    def test_generated_java_is_excluded_but_test_sources_included(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            for rel in ('src/main/java/compiler/parser', 'src/main/java/compiler/lexer',
                        'src/test/java/unit'):
                (root / rel).mkdir(parents=True)
            (root / 'src/main/java/compiler/parser/Parser.java').write_text('public class Parser {}')
            (root / 'src/main/java/compiler/lexer/Lexer.java').write_text('public class Lexer {}')
            (root / 'src/main/java/compiler/parser/Parser.y').write_text('%%\nrule: TOKEN {\n act();\n};\n%%\n')
            (root / 'src/test/java/unit/package-info.java').write_text('package unit;\n')
            (root / 'src/test/java/unit/SampleTest.java').write_text(
                'class SampleTest {\nvoid check() {}\n}\n')
            report = audit(root)
            self.assertEqual(len(report['excluded_sources']), 2)
            self.assertIn('src/test/java/unit/SampleTest.java', report['inspected_sources'])
            codes = {f['code'] for f in report['findings']}
            self.assertIn('METHOD_JAVADOC', codes)
            self.assertIn('PACKAGE_JAVADOC', codes)
            self.assertIn('PARSER_ACTION_JAVADOC', codes)
            output = root / 'output' / 'audit.json'
            self.assertEqual(main(['--root', str(root), '--output', 'output/audit.json']), 0)
            self.assertEqual(json.loads(output.read_text())['schema_version'], 1)
            self.assertEqual(main(['--root', str(root), '--check']), 1)


if __name__ == '__main__':
    unittest.main()
