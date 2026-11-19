"""Regression tests for Mermaid-safe AST construction sequence labels."""
import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import generate_diagrams as diagrams


class AstConstructionSequenceTests(unittest.TestCase):
    def test_messages_do_not_contain_mermaid_statement_separators(self):
        sequence = diagrams.generate_ast_construction_sequence()
        self.assertTrue(sequence.startswith("sequenceDiagram\n"))
        for line in sequence.splitlines():
            if "->" in line and ":" in line:
                self.assertNotIn(";", line, line)
        self.assertIn("C->>R: new RuleBlockLower(ctx), then adapt()", sequence)
        self.assertIn("R->>L: new RuleLower(ruleCtx), then adapt()", sequence)


if __name__ == "__main__":
    unittest.main()
