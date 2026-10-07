package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;
import parser.initializations.SubrangeInitialization;
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
 * Integration tests for SUBRANGE type declarations: custom subrange type resolution,
 * subrange variable declarations, bounds evaluation, and default/explicit initial values.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class SubrangeTypeDeclarationIT extends ParserTestSupport {

    @Test
    public void Declaring_Valid_Subrange_Type_Registers_Bounds() throws Exception {
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
}