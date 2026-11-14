package integration.lexer.parser.semantics;

import compiler.repository.diagnostics.general.UndeclaredReference;
import org.junit.jupiter.api.Test;
import utils.builders.Block;
import utils.builders.SourceCode;
import utils.supports.ParserTestSupport;

public class RuleSemanticsIT extends ParserTestSupport {
    private static SourceCode program(String rule) {
        return SourceCode.functionBlock()
                .var(Block.VAR_INPUT, "a : USINT").var(Block.VAR_OUTPUT, "y : USINT")
                .body("FUZZIFY a TERM lo := (0,1) (100,0); END_FUZZIFY")
                .body("DEFUZZIFY y TERM off := 0; METHOD : CoGS; DEFAULT := 0; END_DEFUZZIFY")
                .body("RULEBLOCK r AND : MIN; ACCU : MAX;\n" + rule + "\nEND_RULEBLOCK");
    }

    @Test
    void Valid_rule_is_accepted() throws Exception { parse(program("RULE 1 : IF a IS lo THEN y IS off;")); }

    @Test
    void Undeclared_variable_is_reported_by_the_parser() throws Exception {
        assertDiagnostic(
                UndeclaredReference.class,
                "undeclared reference: conditions must have inputs in conditions and outputs in conclusions, " +
                        "but rule 1 of R uses C",
                program("RULE 1 : IF c IS lo THEN y IS off;")
        );
    }

    @Test void Term_that_is_not_from_the_variable_is_reported_by_the_parser() throws Exception {
        assertDiagnostic(
                UndeclaredReference.class,
                "undeclared reference: in rule 1 of R, the MID is not from A",
                program("RULE 1 : IF a IS mid THEN y IS off;")
        );
    }
    @Test void Every_rule_error_is_reported_not_only_the_first() throws Exception {
        assertDiagnostic(
                UndeclaredReference.class,
                "undeclared reference: in rule 1 of R, the MID is not from A",
                program("RULE 1 : IF a IS mid THEN y IS off;\nRULE 2 : IF a IS lo THEN y IS on;")
        );
        assertDiagnostic(
                UndeclaredReference.class,
                "undeclared reference: in rule 2 of R, the ON is not from Y",
                program("RULE 1 : IF a IS mid THEN y IS off;\nRULE 2 : IF a IS lo THEN y IS on;")
        );
    }
}