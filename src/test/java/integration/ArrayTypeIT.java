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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for ARRAY variable declarations with structured types,
 * multidimensional bounds, inline struct enumerations, and repeated initializers.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class ArrayTypeIT extends ParserTestSupport {

    @ParameterizedTest
    @CsvSource({
            "VAR, INTERNAL",
            "VAR_INPUT, IN",
            "VAR_OUTPUT, OUT"
    })
    public void Parse_WithArrayVariable_PopulatesSymbolTableCorrectly(String block, Source expectedSource) throws Exception {
        String sourceCode = "TYPE\n"
                + "    color_type : \n"
                + "        STRUCT \n"
                + "            classification : (WHITE, GRAY, BLACK);\n"
                + "            gamma: REAL := 0.5;\n"
                + "        END_STRUCT;\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + block + "\n"
                + "    pixels : ARRAY [0..1, 1..10, 3..4] OF color_type := [ \n"
                + "       20 ((classification := WHITE, gamma := 1)), \n"
                + "           (classification := BLACK, gamma := 0) ,\n"
                + "       10 ((classification := WHITE, gamma := 1)),\n"
                + "        8 ((classification := BLACK, gamma := 0)),\n"
                + "        2 ((classification := GRAY))\n"
                + "    ];\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK\n";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        // Assert STRUCT type declaration
        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("CLASSIFICATION", "GAMMA"))
                .initialValue(createMap("CLASSIFICATION", "COLOR_TYPE#CLASSIFICATION", "GAMMA", "COLOR_TYPE#GAMMA"))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        // Assert STRUCT field classification (ENUMERATE)
        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#CLASSIFICATION", new LexemeInfoBuilder()
                .type(Type.ENUMERATE)
                .subtype(Subtype.INT)
                .use(Use.FIELD)
                .source(Source.NONE)
                .parameters(Arrays.asList("WHITE", "GRAY", "BLACK"))
                .initialValue("0")
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        // Assert ENUM MACRO constants
        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#CLASSIFICATION#WHITE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue("0")
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#CLASSIFICATION#GRAY", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue("1")
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#CLASSIFICATION#BLACK", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue("2")
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        // Assert STRUCT field gamma (REAL)
        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#GAMMA", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue("0.5")
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        // Assert ARRAY variable
        List<Object> expectedArrayInitialValue = Arrays.asList(
                Arrays.asList(20, createMap("CLASSIFICATION", "COLOR_TYPE#CLASSIFICATION#WHITE", "GAMMA", "1.0")),
                Arrays.asList(1, createMap("CLASSIFICATION", "COLOR_TYPE#CLASSIFICATION#BLACK", "GAMMA", "0.0")),
                Arrays.asList(10, createMap("CLASSIFICATION", "COLOR_TYPE#CLASSIFICATION#WHITE", "GAMMA", "1.0")),
                Arrays.asList(8, createMap("CLASSIFICATION", "COLOR_TYPE#CLASSIFICATION#BLACK", "GAMMA", "0.0")),
                Arrays.asList(2, createMap("CLASSIFICATION", "COLOR_TYPE#CLASSIFICATION#GRAY", "GAMMA", "COLOR_TYPE#GAMMA"))
        );

        diffs = LexemeInfoComparator.compare(st, "PIXELS", new LexemeInfoBuilder()
                .type(Type.ARRAY)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(expectedSource)
                .inferiorLimit(Arrays.asList("0", "1", "3"))
                .superiorLimit(Arrays.asList("1", "10", "4"))
                .initialValue(expectedArrayInitialValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    private static Map<String, Object> createMap(String k1, Object v1, String k2, Object v2) {
        Map<String, Object> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }
}