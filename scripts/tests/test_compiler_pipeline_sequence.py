"""The Compiler pipeline sequence must reflect the current Java entry points."""
import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class CompilerPipelineSequenceTests(unittest.TestCase):
    def test_pipeline_uses_application_and_current_compiler_api(self):
        content = diagrams.generate_compiler_pipeline_sequence()
        self.assertTrue(content.startswith("sequenceDiagram\n"))
        self.assertIn("participant CLI as api.Application", content)
        self.assertIn("participant C as compiler.FlcCompiler", content)
        self.assertIn("CLI->>C: compile()", content)
        self.assertIn("new Parser(lexer, symbolTable, diagnostics)", content)
        self.assertIn("CLI->>C: log()", content)
        self.assertIn("CODE_COMPILER_ERR", content)
        self.assertIn("CODE_ARGUMENT_ERR", content)
        self.assertNotIn("compiler_CommandLine", content)
        self.assertNotIn("compile(source, baseName)", content)
        self.assertEqual(content, diagrams.generate_compiler_pipeline_sequence())

    def test_pipeline_rejects_stale_java_evidence(self):
        from tempfile import TemporaryDirectory
        from unittest.mock import patch
        with TemporaryDirectory() as temp:
            root = Path(temp)
            for relative in ("Main.java", "api/Application.java", "compiler/FlcCompiler.java"):
                dest = root / relative
                dest.parent.mkdir(parents=True, exist_ok=True)
                dest.write_text("class Empty {}", encoding="utf-8")
            with patch.object(diagrams, "SRC_DIR", root):
                with self.assertRaisesRegex(ValueError, "Main.java"):
                    diagrams.generate_compiler_pipeline_sequence()


if __name__ == "__main__":
    unittest.main()
