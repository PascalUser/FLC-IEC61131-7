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
 * Integration tests for STRUCT variable declarations with initial values.
 */
public class StructVariableIT extends ParserTestSupport {

    @Test
    public void Declaring_Variable_Of_Struct_Type_Inherits_Default_Field_Values() throws Exception {
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
        colorTypeValue.put("BROWN", DefaultInitialization.real(st));
        colorTypeValue.put("LIGHT", DefaultInitialization.real(st));

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
    public void Declaring_Initialized_Struct_Variable_Stores_Field_Overrides() throws Exception {
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
        colorTypeValue.put("BROWN", DefaultInitialization.real(st));
        colorTypeValue.put("LIGHT", DefaultInitialization.real(st));

        StructInitialization colorValue = new StructInitialization();
        colorValue.put("BROWN", DefaultInitialization.real(st));
        colorValue.put("LIGHT", new VariableInitialization("1.0"));

        assertSymbol(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("BROWN", "LIGHT"))
                .initialValue(colorTypeValue)
                .build());

        assertSymbol(st, "COLOR_TYPE#BROWN", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(DefaultInitialization.real(st))
                .build());

        assertSymbol(st, "COLOR_TYPE#LIGHT", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(DefaultInitialization.real(st))
                .build());

        assertSymbol(st, "MAIN#COLOR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(colorValue)
                .build());
    }
}