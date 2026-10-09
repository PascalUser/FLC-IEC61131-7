package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import parser.initializations.leafs.VariableInitialization;
import parser.initializations.Factory;
import utils.ParserTestSupport;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Collections;

/**
 * Integration tests for STRING and WSTRING type declarations and variable declarations
 * with fixed lengths and initial values.
 * <p>
 * Verifies that the symbol table correctly registers string types with their length
 * constraints and initial values for both type declarations and variable declarations.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class StringTypeIT extends ParserTestSupport {

    @ParameterizedTest
    @EnumSource(value = Subtype.class, names = {"STRING", "WSTRING"})
    void Declaring_Fixed_Length_String_Type_Registers_Correctly(Subtype subtype) throws Exception {
        String sourceCode = "TYPE\n"
                + "    StringType : " + subtype.name() + " [ 8 ];\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        assertSymbol(st, "STRINGTYPE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.TYPE)
                .source(Source.NONE)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(Factory.createPrimitiveInitialization(st, subtype))
                .build());
    }

    @Test
    void Declaring_Initialized_Fixed_Length_String_Type_Stores_Initial_Value() throws Exception {
        String sourceCode = "TYPE\n"
                + "    StringType : STRING[8] := 'STRING';\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        assertSymbol(st, "STRINGTYPE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.STRING)
                .use(Use.TYPE)
                .source(Source.NONE)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new VariableInitialization("'STRING'"))
                .build());
    }

    @Test
    void Declaring_Initialized_Fixed_Length_WString_Type_Stores_Initial_Value() throws Exception {
        String sourceCode = "TYPE\n"
                + "    StringType : WSTRING[8] := \"WSTRING\";\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        assertSymbol(st, "STRINGTYPE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.WSTRING)
                .use(Use.TYPE)
                .source(Source.NONE)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new VariableInitialization("\"WSTRING\""))
                .build());
    }

    @ParameterizedTest
    @EnumSource(value = Subtype.class, names = {"STRING", "WSTRING"})
    void Declaring_Fixed_Length_String_Variable_Registers_Correctly(Subtype subtype) throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    strVar : " + subtype.name() + "[8];\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        assertSymbol(st, "MAIN#STRVAR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(Factory.createPrimitiveInitialization(st, subtype))
                .build());
    }

    @Test
    void Declaring_Initialized_Fixed_Length_String_Variable_Stores_Initial_Value() throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    strVar : STRING[8] := 'STRING';\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        assertSymbol(st, "MAIN#STRVAR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.STRING)
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new VariableInitialization("'STRING'"))
                .build());
    }

    @Test
    void Declaring_Initialized_Fixed_Length_WString_Variable_Stores_Initial_Value() throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    strVar : WSTRING[8] := \"WSTRING\";\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        assertSymbol(st, "MAIN#STRVAR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.WSTRING)
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new VariableInitialization("\"WSTRING\""))
                .build());
    }
}