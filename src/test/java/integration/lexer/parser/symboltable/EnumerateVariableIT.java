package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import parser.initializations.MacroInitialization;
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
 * Integration tests for ENUMERATED variable declarations (inline and custom type).
 */
public class EnumerateVariableIT extends ParserTestSupport {

    @ParameterizedTest
    @CsvSource({
            "VAR, INTERNAL",
            "VAR_INPUT, IN",
            "VAR_OUTPUT, OUT"
    })
    public void declaring_variable_of_enum_type_uses_fully_qualified_initializer(
            String block,
            Source expectedSource
    ) throws Exception {
        String sourceCode = "TYPE\n"
                + "    MethodType : (CENTROID, AVERAGE);\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + block + "\n"
                + "    defuzz_method : MethodType := CENTROID;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK\n";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "MAIN#DEFUZZ_METHOD", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("METHODTYPE")
                .use(Use.VARIABLE)
                .source(expectedSource)
                .initialValue(new VariableInitialization("METHODTYPE#CENTROID"))
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "VAR, INTERNAL",
            "VAR_INPUT, IN",
            "VAR_OUTPUT, OUT"
    })
    public void declaring_inline_enum_variable_registers_anonymous_type(
            String block,
            Source expectedSource
    ) throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + block + "\n"
                + "    defuzz_method : (CENTROID, AVERAGE) := CENTROID;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK\n";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "MAIN#DEFUZZ_METHOD", new LexemeInfoBuilder()
                .type(Type.ENUMERATE)
                .subtype(Subtype.INT)
                .use(Use.VARIABLE)
                .source(expectedSource)
                .parameters(Arrays.asList("CENTROID", "AVERAGE"))
                .initialValue(new VariableInitialization("MAIN#CENTROID"))
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "MAIN#CENTROID", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(st, "0"))
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "MAIN#AVERAGE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(st, "1"))
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());
    }
}