package integration;

import utils.ParserTestSupport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
 * Integration tests for ENUMERATED type declarations and variables: custom enum type resolution,
 * inline enumerated variables, and macro constant generation within FUNCTION_BLOCK scope.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class EnumerateTypeIT extends ParserTestSupport {

    @ParameterizedTest
    @CsvSource({
            "VAR, INTERNAL",
            "VAR_INPUT, IN",
            "VAR_OUTPUT, OUT"
    })
    public void Parse_WithCustomEnumTypeDeclaration_PopulatesSymbolTableCorrectly(String block, Source expectedSource) throws Exception {
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

        diffs = LexemeInfoComparator.compare(st, "METHODTYPE", new LexemeInfoBuilder()
                .type(Type.ENUMERATE)
                .subtype(Subtype.INT)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("CENTROID", "AVERAGE"))
                .initialValue("CENTROID")
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "MAIN#DEFUZZ_METHOD", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("METHODTYPE")
                .use(Use.VARIABLE)
                .source(expectedSource)
                .initialValue("METHODTYPE#CENTROID")
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "METHODTYPE#CENTROID", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue("0")
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "METHODTYPE#AVERAGE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue("1")
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
    public void Parse_WithInlineEnumVariable_PopulatesSymbolTableCorrectly(String block, Source expectedSource) throws Exception {
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
                .initialValue("0")
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "MAIN#CENTROID", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue("0")
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "MAIN#AVERAGE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue("1")
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());
    }
}