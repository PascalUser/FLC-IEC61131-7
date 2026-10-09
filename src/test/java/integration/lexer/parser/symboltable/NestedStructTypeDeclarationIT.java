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

import java.util.Arrays;


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
        rgbTypeValue.put("GAMMA_R", DefaultInitialization.real(st));
        rgbTypeValue.put("GAMMA_G", DefaultInitialization.real(st));
        rgbTypeValue.put("GAMMA_B", DefaultInitialization.real(st));

        StructInitialization colorTypeValue = new StructInitialization();
        colorTypeValue.put("WHITE", DefaultInitialization.bool(st));
        colorTypeValue.put("RGB", rgbTypeValue);

        assertSymbol(st, "RGB_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("GAMMA_R", "GAMMA_G", "GAMMA_B"))
                .initialValue(rgbTypeValue)
                .build());

        assertSymbol(st, "RGB_TYPE#GAMMA_R", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(DefaultInitialization.real(st))
                .build());

        assertSymbol(st, "RGB_TYPE#GAMMA_G", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(DefaultInitialization.real(st))
                .build());

        assertSymbol(st, "RGB_TYPE#GAMMA_B", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(DefaultInitialization.real(st))
                .build());

        assertSymbol(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("WHITE", "RGB"))
                .initialValue(colorTypeValue)
                .build());
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
        rgbTypeValue.put("GAMMA_R", DefaultInitialization.real(st));
        rgbTypeValue.put("GAMMA_G", DefaultInitialization.real(st));
        rgbTypeValue.put("GAMMA_B", DefaultInitialization.real(st));

        StructInitialization colorTypeValue = new StructInitialization();
        colorTypeValue.put("WHITE", DefaultInitialization.bool(st));
        colorTypeValue.put("RGB", rgbTypeValue.copy());
        colorTypeValue.put("RGB#GAMMA_G", new VariableInitialization("3.0"));

        assertSymbol(st, "RGB_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("GAMMA_R", "GAMMA_G", "GAMMA_B"))
                .initialValue(rgbTypeValue)
                .build());

        assertSymbol(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("WHITE", "RGB"))
                .initialValue(colorTypeValue)
                .build());
    }
}