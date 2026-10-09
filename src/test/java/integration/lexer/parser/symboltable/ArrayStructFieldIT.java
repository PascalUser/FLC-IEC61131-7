package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import parser.initializations.leafs.EnumeratedInitialization;
import parser.initializations.leafs.MacroInitialization;
import parser.initializations.leafs.VariableInitialization;
import utils.ParserTestSupport;

import parser.initializations.*;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Arrays;


/**
 * Integration tests for ARRAY struct field declarations (inline enums, initialized fields).
 */
public class ArrayStructFieldIT extends ParserTestSupport {

    @Test
    public void Declaring_Struct_Field_With_Inline_Enum_Registers_Enumerators_And_Macros() throws Exception {
        String sourceCode = "TYPE\n"
                + "    color_type : \n"
                + "        STRUCT \n"
                + "            classification : (WHITE, GRAY, BLACK);\n"
                + "        END_STRUCT;\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK\n";

        SymbolTable st = parse(sourceCode);
        Initialization classificationInit = new EnumeratedInitialization(Arrays.asList("WHITE", "GRAY", "BLACK"));
        assertSymbol(st, "COLOR_TYPE#CLASSIFICATION", new LexemeInfoBuilder()
                .type(Type.ENUMERATE)
                .subtype(Subtype.INT)
                .use(Use.FIELD)
                .source(Source.NONE)
                .parameters(Arrays.asList("WHITE", "GRAY", "BLACK"))
                .initialValue(classificationInit)
                .build());

        assertSymbol(st, "COLOR_TYPE#WHITE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(st, 0))
                .build());

        assertSymbol(st, "COLOR_TYPE#GRAY", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(st, 1))
                .build());

        assertSymbol(st, "COLOR_TYPE#BLACK", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(st, 2))
                .build());
    }

    @Test
    public void Declaring_Struct_Field_With_Initialized_Field_Uses_Default() throws Exception {
        String sourceCode = "TYPE\n"
                + "    color_type : \n"
                + "        STRUCT \n"
                + "            gamma: REAL := 0.5;\n"
                + "        END_STRUCT;\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK\n";

        SymbolTable st = parse(sourceCode);
        Initialization gammaInit = new VariableInitialization(".5");
        assertSymbol(st, "COLOR_TYPE#GAMMA", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(gammaInit)
                .build());
    }
}