package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;
import parser.initializations.primitives.BooleanInitialization;
import parser.initializations.primitives.RealInitialization;
import parser.initializations.StructInitialization;
import parser.initializations.VariableInitialization;
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
 * Integration tests for nested STRUCT type declarations.
 */
public class NestedStructTypeDeclarationIT extends ParserTestSupport {

    @Test
    public void Declaring_Nested_Struct_Type_Registers_Hierarchical_Fields() throws Exception {
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
    }

    @Test
    public void Declaring_Nested_Struct_With_Initialized_Inner_Field_Uses_Default() throws Exception {
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
        colorTypeValue.setFieldInitialization("RGB#GAMMA_G", new VariableInitialization("3.0"));
        colorTypeValue.setFieldInitialization("RGB#GAMMA_B", new RealInitialization(st));

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

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("WHITE", "RGB"))
                .initialValue(colorTypeValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }
}