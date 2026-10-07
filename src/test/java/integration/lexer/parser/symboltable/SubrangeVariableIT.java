package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;
import parser.initializations.VariableInitialization;
import utils.LexemeInfoComparator;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for SUBRANGE variable declarations.
 */
public class SubrangeVariableIT extends ParserTestSupport {

    @Test
    public void Declaring_Variable_Of_Subrange_Type_Inherits_Bounds() throws Exception {
        String sourceCode = "TYPE\n"
                + "    Day : INT (0..31);\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    work_day : DAY := 21;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "MAIN#WORK_DAY", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("DAY")
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .initialValue(new VariableInitialization("21"))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    @Test
    public void Declaring_Inline_Subrange_Variable_Registers_Anonymous_Type() throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    percentage : INT (0..100) := 50;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "MAIN#PERCENTAGE", new LexemeInfoBuilder()
                .type(Type.SUBRANGE)
                .subtype(Subtype.INT)
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .inferiorLimits(Collections.singletonList("0"))
                .superiorLimits(Collections.singletonList("100"))
                .initialValue("50")
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }
}