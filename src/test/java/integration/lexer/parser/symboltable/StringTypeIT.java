package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import parser.initializations.StringInitialization;
import parser.initializations.VariableInitialization;
import utils.LexemeInfoComparator;
import utils.ParserTestSupport;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringTypeIT extends ParserTestSupport {

    @ParameterizedTest
    @EnumSource(value = Subtype.class, names = {"STRING", "WSTRING"})
    void declaring_valid_fixed_length_string_type_registers_in_symbol_table(Subtype subtype) throws Exception {
        String sourceCode = "TYPE\n"
                + "    StringType : " + subtype.name() + " [ 8 ];\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "STRINGTYPE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.TYPE)
                .source(Source.NONE)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new StringInitialization(st, subtype))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    @Test
    void declaring_initialized_fixed_length_string_type_stores_initial_value() throws Exception {
        String sourceCode = "TYPE\n"
                + "    StringType : STRING[8] := 'STRING';\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "STRINGTYPE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.STRING)
                .use(Use.TYPE)
                .source(Source.NONE)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new VariableInitialization("'STRING'"))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    @Test
    void declaring_initialized_fixed_length_wstring_type_stores_initial_value() throws Exception {
        String sourceCode = "TYPE\n"
                + "    StringType : WSTRING[8] := \"WSTRING\";\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "STRINGTYPE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.WSTRING)
                .use(Use.TYPE)
                .source(Source.NONE)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new VariableInitialization("\"WSTRING\""))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    @ParameterizedTest
    @EnumSource(value = Subtype.class, names = {"STRING", "WSTRING"})
    void declaring_valid_fixed_length_string_variable_registers_in_symbol_table(Subtype subtype) throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    strVar : " + subtype.name() + "[8];\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "MAIN#STRVAR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new StringInitialization(st, subtype))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    @Test
    void declaring_initialized_fixed_length_string_variable_stores_initial_value() throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    strVar : STRING[8] := 'STRING';\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "MAIN#STRVAR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.STRING)
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new VariableInitialization("'STRING'"))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    @Test
    void declaring_initialized_fixed_length_wstring_variable_stores_initial_value() throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + "VAR\n"
                + "    strVar : WSTRING[8] := \"WSTRING\";\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "MAIN#STRVAR", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.WSTRING)
                .use(Use.VARIABLE)
                .source(Source.INTERNAL)
                .inferiorLimits(null)
                .superiorLimits(Collections.singletonList("8"))
                .initialValue(new VariableInitialization("\"WSTRING\""))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }
}