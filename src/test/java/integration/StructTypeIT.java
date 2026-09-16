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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for STRUCT type declarations: field resolution,
 * default initial values, explicit initial values, and nested structs.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class StructTypeIT extends ParserTestSupport {

    @Test
    public void Parse_WithStructTypeDeclaration_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "TYPE\n"
                + "    color_type : STRUCT\n"
                + "        brown: REAL;\n"
                + "        light: REAL;\n"
                + "    END_STRUCT;\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "VAR_INPUT\n"
                + "    color : color_type;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);

        assertTrue(LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("BROWN", "LIGHT"))
                .initialValue(mapOf(
                        "BROWN", "COLOR_TYPE#BROWN",
                        "LIGHT", "COLOR_TYPE#LIGHT"))
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "COLOR_TYPE#BROWN", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue("0.0")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "COLOR_TYPE#LIGHT", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue("0.0")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "COLOR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(mapOf(
                        "BROWN", "COLOR_TYPE#BROWN",
                        "LIGHT", "COLOR_TYPE#LIGHT"))
                .build()).isEmpty());
    }

    @Test
    public void Parse_WithStructTypeAndInitialValue_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "TYPE\n"
                + "    color_type : STRUCT\n"
                + "        brown: REAL;\n"
                + "        light: REAL;\n"
                + "    END_STRUCT;\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "VAR_INPUT\n"
                + "    color : color_type := (light := 1.0);\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);

        assertTrue(LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("BROWN", "LIGHT"))
                .initialValue(mapOf(
                        "BROWN", "COLOR_TYPE#BROWN",
                        "LIGHT", "COLOR_TYPE#LIGHT"))
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "COLOR_TYPE#BROWN", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue("0.0")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "COLOR_TYPE#LIGHT", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue("0.0")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "COLOR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(mapOf("BROWN", "COLOR_TYPE#BROWN", "LIGHT", "1.0"))
                .build()).isEmpty());
    }

    @Test
    public void Parse_WithNestedStructTypes_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "TYPE\n"
                + "    rgb_type: STRUCT\n"
                + "        gamma_r: REAL;\n"
                + "        gamma_g: REAL;\n"
                + "        gamma_b: REAL;\n"
                + "    END_STRUCT\n"
                + "    color_type: STRUCT\n"
                + "        white: BOOL;\n"
                + "        rgb: rgb_type := (gamma_g := 3.0);\n"
                + "    END_STRUCT;\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "VAR_INPUT\n"
                + "    color : color_type := (white := TRUE, rgb := (gamma_r := 10.2));\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);

        assertTrue(LexemeInfoComparator.compare(st, "RGB_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("GAMMA_R", "GAMMA_G", "GAMMA_B"))
                .initialValue(mapOf(
                        "GAMMA_R", "RGB_TYPE#GAMMA_R",
                        "GAMMA_G", "RGB_TYPE#GAMMA_G",
                        "GAMMA_B", "RGB_TYPE#GAMMA_B"))
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "RGB_TYPE#GAMMA_R", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue("0.0")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "RGB_TYPE#GAMMA_G", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue("0.0")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "RGB_TYPE#GAMMA_B", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue("0.0")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("WHITE", "RGB#GAMMA_R", "RGB#GAMMA_G", "RGB#GAMMA_B"))
                .initialValue(mapOf(
                        "WHITE", "COLOR_TYPE#WHITE",
                        "RGB#GAMMA_R", "RGB_TYPE#GAMMA_R",
                        "RGB#GAMMA_G", "3.0",
                        "RGB#GAMMA_B", "RGB_TYPE#GAMMA_B"))
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "COLOR_TYPE#WHITE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.BOOL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue("FALSE")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "COLOR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(mapOf(
                        "WHITE", "TRUE",
                        "RGB#GAMMA_R", "10.2",
                        "RGB#GAMMA_G", "3.0",
                        "RGB#GAMMA_B", "RGB_TYPE#GAMMA_B"))
                .build()).isEmpty());
    }
}