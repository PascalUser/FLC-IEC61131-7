package integration;

import parser.initializations.RealInitialization;
import parser.initializations.StructInitialization;
import parser.initializations.VariableInitialization;
// Asumiendo que existe una clase similar para booleanos basada en tu estructura:
import parser.initializations.BooleanInitialization;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for STRUCT type declarations: field resolution,
 * default initial values, explicit initial values, and nested structs.
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

        StructInitialization colorTypeValue = new StructInitialization();
        colorTypeValue.setFieldInitialization("BROWN", new RealInitialization(st));
        colorTypeValue.setFieldInitialization("LIGHT", new RealInitialization(st));

        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("BROWN", "LIGHT"))
                .initialValue(colorTypeValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#BROWN", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#LIGHT", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "MAIN#COLOR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(colorTypeValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
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

        StructInitialization colorTypeValue = new StructInitialization();
        colorTypeValue.setFieldInitialization("BROWN", new RealInitialization(st));
        colorTypeValue.setFieldInitialization("LIGHT", new RealInitialization(st));

        StructInitialization colorValue = new StructInitialization();
        colorValue.setFieldInitialization("BROWN", new RealInitialization(st));
        colorValue.setFieldInitialization("LIGHT", new VariableInitialization("1.0"));

        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("BROWN", "LIGHT"))
                .initialValue(colorTypeValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#BROWN", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#LIGHT", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "MAIN#COLOR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(colorValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    @Test
    public void Parse_WithNestedStructTypes_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "TYPE\n"
                + "    rgb_type: STRUCT\n"
                + "        gamma_r: REAL;\n"
                + "        gamma_g: REAL;\n"
                + "        gamma_b: REAL;\n"
                + "    END_STRUCT;\n"
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

        StructInitialization rgbTypeValue = new StructInitialization();
        rgbTypeValue.setFieldInitialization("GAMMA_R", new RealInitialization(st));
        rgbTypeValue.setFieldInitialization("GAMMA_G", new RealInitialization(st));
        rgbTypeValue.setFieldInitialization("GAMMA_B", new RealInitialization(st));

        StructInitialization colorTypeValue = new StructInitialization();
        colorTypeValue.setFieldInitialization("WHITE", new BooleanInitialization(st));
        colorTypeValue.setFieldInitialization("RGB", new StructInitialization());
        colorTypeValue.setFieldInitialization("RGB#GAMMA_R", new RealInitialization(st));
        colorTypeValue.setFieldInitialization("RGB#GAMMA_G", new VariableInitialization("3.0"));
        colorTypeValue.setFieldInitialization("RGB#GAMMA_B", new RealInitialization(st));

        StructInitialization mainColorValue = new StructInitialization();
        mainColorValue.setFieldInitialization("WHITE", new VariableInitialization("TRUE"));
        mainColorValue.setFieldInitialization("RGB", new StructInitialization());
        mainColorValue.setFieldInitialization("RGB#GAMMA_R", new VariableInitialization("10.2"));
        mainColorValue.setFieldInitialization("RGB#GAMMA_G", new VariableInitialization("3.0"));
        mainColorValue.setFieldInitialization("RGB#GAMMA_B", new RealInitialization(st));

        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "RGB_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("GAMMA_R", "GAMMA_G", "GAMMA_B"))
                .initialValue(rgbTypeValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "RGB_TYPE#GAMMA_R", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "RGB_TYPE#GAMMA_G", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "RGB_TYPE#GAMMA_B", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("WHITE", "RGB"))
                .initialValue(colorTypeValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#WHITE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.BOOL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new BooleanInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "MAIN#COLOR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(mainColorValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }
}