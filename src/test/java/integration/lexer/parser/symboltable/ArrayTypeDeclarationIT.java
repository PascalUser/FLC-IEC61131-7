package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import parser.initializations.leafs.EnumeratedInitialization;
import parser.initializations.leafs.VariableInitialization;
import parser.initializations.nodes.StructInitialization;
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
 * Integration tests for ARRAY type declarations with structured types.
 */
public class ArrayTypeDeclarationIT extends ParserTestSupport {

    @Test
    public void Declaring_Multidimensional_Array_Of_Struct_Type_Registers_Bounds() throws Exception {
        String sourceCode = "TYPE\n"
                + "    color_type : \n"
                + "        STRUCT \n"
                + "            classification : (WHITE, GRAY, BLACK);\n"
                + "            gamma: REAL := 0.5;\n"
                + "        END_STRUCT;\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK\n";

        SymbolTable st = parse(sourceCode);
        Initialization classificationInit = new EnumeratedInitialization(Arrays.asList("WHITE", "GRAY", "BLACK"));
        Initialization gammaInit = new VariableInitialization(".5");

        assertSymbol(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("CLASSIFICATION", "GAMMA"))
                .initialValue(createStructInit(classificationInit, gammaInit))
                .build());
    }

    /**
     * Helper to create a parser.initializations.nodes.StructInitialization surrogate.
     */
    private static Initialization createStructInit(Initialization v1, Initialization v2) {
        StructInitialization structInit = new StructInitialization();
        structInit.put("CLASSIFICATION", v1);
        structInit.put("GAMMA", v2);
        return structInit;
    }
}