import sys
import tempfile
import unittest
from pathlib import Path
sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from parser_class_diagram import build_parser_class_diagram

class ParserClassDiagramTests(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.addCleanup(self.tmp.cleanup)
        self.root = Path(self.tmp.name)

    def java(self, relative, content):
        path = self.root / 'compiler/parser' / relative
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content)

    def test_current_package_relationships(self):
        self.java('Parser.java', 'package compiler.parser; public class Parser {}')
        self.java('publishers/Publisher.java', 'package compiler.parser.publishers; public interface Publisher {}')
        self.java('publishers/leafs/Declaration.java', 'package compiler.parser.publishers.leafs;\nimport compiler.parser.publishers.Publisher;\npublic abstract class Declaration implements Publisher {}')
        diagram = build_parser_class_diagram(self.root)
        self.assertIn('namespace compiler_parser_publishers_leafs', diagram)
        self.assertIn('Publisher <|.. Declaration', diagram)
        self.assertNotIn('parser.Parser', diagram)

    def test_missing_required_type_fails(self):
        self.java('Parser.java', 'public class Parser {}')
        with self.assertRaisesRegex(ValueError, 'Publisher'):
            build_parser_class_diagram(self.root)

    def test_missing_declared_java_type_fails(self):
        self.java('Parser.java', 'public class Parser {}')
        self.java('publishers/Publisher.java', 'public interface Publisher {}')
        self.java('publishers/leafs/Declaration.java', 'class OtherType {}')
        with self.assertRaisesRegex(ValueError, 'Declaration'):
            build_parser_class_diagram(self.root)

if __name__ == '__main__':
    unittest.main()
