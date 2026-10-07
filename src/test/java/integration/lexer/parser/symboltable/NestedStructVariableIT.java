package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;
import parser.initializations.BooleanInitialization;
import parser.initializations.RealInitialization;
import parser.initializations.StructInitialization;
import parser.initializations.VariableInitialization;
import utils.LexemeInfoComparator;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for nested STRUCT variable declarations with initial values.
 */
public class NestedStructVariableIT extends ParserTestSupport {

    @Test
    public void declaring_variable_of_nested_struct_type_inherits_full_hierarchy() throws Exception {
        String sourceCode = "TYPE\n"
                + "    rgb_type: STRUCT\n"
                + "        gamma_r: REAL;\n"
                + "        gamma_g: REAL;\n"
                + "        gamma_b: REAL;\n"
                + "    END_STRUCT;\n"
                + "    color_type: STRUCT\n"
                + "        white: BOOL;\n"
                + "        rgb: rgb_type;\n"
                + "    END_STRUCT;\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "VAR_INPUT\n"
                + "    color : color_type;\n"
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
        colorTypeValue.setFieldInitialization("RGB#GAMMA_G", new RealInitialization(st));
        colorTypeValue.setFieldInitialization("RGB#GAMMA_B", new RealInitialization(st));

        List<String> diffs;

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
    public void declaring_initialized_nested_struct_variable_stores_deep_field_overrides() throws Exception {
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