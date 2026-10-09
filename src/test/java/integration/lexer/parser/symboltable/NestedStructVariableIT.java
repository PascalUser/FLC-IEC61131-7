package integration.lexer.parser.symboltable;

import parser.initializations.leafs.DefaultInitialization;
import parser.initializations.nodes.StructInitialization;
import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;
import parser.initializations.leafs.VariableInitialization;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;



/**
 * Integration tests for nested STRUCT variable declarations with initial values.
 */
public class NestedStructVariableIT extends ParserTestSupport {

    @Test
    public void Declaring_Variable_Of_Nested_Struct_Type_Inherits_Full_Hierarchy() throws Exception {
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
        rgbTypeValue.put("GAMMA_R", DefaultInitialization.real(st));
        rgbTypeValue.put("GAMMA_G", DefaultInitialization.real(st));
        rgbTypeValue.put("GAMMA_B", DefaultInitialization.real(st));

        StructInitialization colorTypeValue = new StructInitialization();
        colorTypeValue.put("WHITE", DefaultInitialization.bool(st));
        colorTypeValue.put("RGB", rgbTypeValue);

        assertSymbol(st, "MAIN#COLOR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(colorTypeValue)
                .build());
    }

    @Test
    public void Declaring_Initialized_Nested_Struct_Variable_Stores_Deep_Field_Overrides() throws Exception {
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
        rgbTypeValue.put("GAMMA_R", DefaultInitialization.real(st));
        rgbTypeValue.put("GAMMA_G", DefaultInitialization.real(st));
        rgbTypeValue.put("GAMMA_B", DefaultInitialization.real(st));

        StructInitialization colorTypeValue = new StructInitialization();
        colorTypeValue.put("WHITE", DefaultInitialization.bool(st));
        colorTypeValue.put("RGB", rgbTypeValue);

        StructInitialization mainColorValue = new StructInitialization();
        mainColorValue.put("WHITE", new VariableInitialization("TRUE"));
        mainColorValue.put("RGB", rgbTypeValue.copy());

        colorTypeValue.put("RGB#GAMMA_G", new VariableInitialization("3.0"));
        mainColorValue.put("RGB#GAMMA_R", new VariableInitialization("10.2"));
        mainColorValue.put("RGB#GAMMA_G", new VariableInitialization("3.0"));

        assertSymbol(st, "MAIN#COLOR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(mainColorValue)
                .build());
    }
}