package integration;

import parser.initializations.SubrangeInitialization;
import parser.initializations.VariableInitialization;
import utils.ParserTestSupport;
import org.junit.jupiter.api.Test;
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
 * Integration tests for SUBRANGE type declarations: custom subrange type resolution,
 * subrange variable declarations, bounds evaluation, and default/explicit initial values.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class SubrangeTypeIT extends ParserTestSupport {

    @Test
    public void Parse_WithSubrangeTypeDeclaration_PopulatesSymbolTableCorrectly() throws Exception {
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

        diffs = LexemeInfoComparator.compare(st, "DAY", new LexemeInfoBuilder()
                .type(Type.SUBRANGE)
                .subtype(Subtype.INT)
                .use(Use.TYPE)
                .source(Source.NONE)
                .inferiorLimits(Collections.singletonList("0"))
                .superiorLimits(Collections.singletonList("31"))
                .initialValue(new SubrangeInitialization("0", "31"))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

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
    public void Parse_WithInlineSubrangeVariable_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    percentage : INT (0..100) := 50;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);

        assertTrue(LexemeInfoComparator.compare(st, "MAIN#PERCENTAGE", new LexemeInfoBuilder()
                .type(Type.SUBRANGE)
                .subtype(Subtype.INT)
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .inferiorLimits(Collections.singletonList("0"))
                .superiorLimits(Collections.singletonList("100"))
                .initialValue("50")
                .build()).isEmpty());
    }
}