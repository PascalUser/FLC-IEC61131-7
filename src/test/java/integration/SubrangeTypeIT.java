package integration;

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
                + "    Dia : INT (0..31);\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    fecha_trabajo : DIA := 21;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);

        assertTrue(LexemeInfoComparator.compare(st, "DIA", new LexemeInfoBuilder()
                .type(Type.SUBRANGE)
                .subtype(Subtype.INT)
                .use(Use.TYPE)
                .source(Source.NONE)
                .inferiorLimit(Collections.singletonList("0"))
                .superiorLimit(Collections.singletonList("31"))
                .initialValue("0")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "FECHA_TRABAJO", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("DIA")
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .initialValue("21")
                .build()).isEmpty());
    }

    @Test
    public void Parse_WithInlineSubrangeVariable_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    Porcentaje : INT (0..100) := 50;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);

        assertTrue(LexemeInfoComparator.compare(st, "PORCENTAJE", new LexemeInfoBuilder()
                .type(Type.SUBRANGE)
                .subtype(Subtype.INT)
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .inferiorLimit(Collections.singletonList("0"))
                .superiorLimit(Collections.singletonList("100"))
                .initialValue("50")
                .build()).isEmpty());
    }
}