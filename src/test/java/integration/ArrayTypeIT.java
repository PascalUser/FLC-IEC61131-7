package integration;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import parser.initializations.*;
import utils.LexemeInfoComparator;
import utils.ParserTestSupport;
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
 * Integration tests for ARRAY variable declarations with structured types,
 * multidimensional bounds, inline struct enumerations, and repeated initializers.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.1
 * @since 1.0
 */
public class ArrayTypeIT extends ParserTestSupport {

    @ParameterizedTest
    @CsvSource({
            "VAR, INTERNAL",
            "VAR_INPUT, IN",
            "VAR_OUTPUT, OUT"
    })
    public void parse_WithArrayDeclaration_PopulatesSymbolTableCorrectly(String block, Source expectedSource) throws Exception {
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
                + "       20 ((classification := WHITE, gamma := 1.0)), \n"
                + "           (classification := BLACK, gamma := 0.0) ,\n"
                + "       10 ((classification := WHITE, gamma := 1.0)),\n"
                + "        8 ((classification := BLACK, gamma := 0.0)),\n"
                + "        2 ((classification := GRAY))\n"
                + "    ];\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK\n";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        Initialization classificationInit = new EnumeratedInitialization(Arrays.asList("WHITE", "GRAY", "BLACK"));
        Initialization gammaInit = new VariableInitialization(".5");

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("CLASSIFICATION", "GAMMA"))
                .initialValue(createStructInit(classificationInit, gammaInit))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#CLASSIFICATION", new LexemeInfoBuilder()
                .type(Type.ENUMERATE)
                .subtype(Subtype.INT)
                .use(Use.FIELD)
                .source(Source.NONE)
                .parameters(Arrays.asList("WHITE", "GRAY", "BLACK"))
                .initialValue(classificationInit)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#WHITE", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(st, "0"))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#GRAY", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(st, "1"))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#BLACK", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(st, "2"))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#GAMMA", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(gammaInit)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        RepeatedInitialization expectedArrayInitialValue = new RepeatedInitialization(40, null);
        expectedArrayInitialValue.addInterval(
                0, 19, createStructInit(
                        new VariableInitialization("COLOR_TYPE#WHITE"),
                        new VariableInitialization("1.0")
                )
        );
        expectedArrayInitialValue.addInterval(
                20, 20, createStructInit(
                        new VariableInitialization("COLOR_TYPE#BLACK"),
                        new VariableInitialization(".0")
                )
        );
        expectedArrayInitialValue.addInterval(
                21, 30, createStructInit(
                        new VariableInitialization("COLOR_TYPE#WHITE"),
                        new VariableInitialization("1.0")
                )
        );
        expectedArrayInitialValue.addInterval(
                31, 38, createStructInit(
                        new VariableInitialization("COLOR_TYPE#BLACK"),
                        new VariableInitialization(".0")
                )
        );
        expectedArrayInitialValue.addInterval(
                39, 39, createStructInit(
                        new VariableInitialization("COLOR_TYPE#GRAY"),
                        gammaInit
                )
        );

        diffs = LexemeInfoComparator.compare(st, "MAIN#PIXELS", new LexemeInfoBuilder()
                .type(Type.ARRAY)
                .subtype(Subtype.CUSTOM)
                .customType("COLOR_TYPE")
                .use(Use.VARIABLE)
                .source(expectedSource)
                .inferiorLimits(Arrays.asList("0", "1", "3"))
                .superiorLimits(Arrays.asList("1", "10", "4"))
                .initialValue(expectedArrayInitialValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    /**
     * Helper to create a StructInitialization surrogate for LexemeInfoComparator deep reflection.
     */
    private static Initialization createStructInit(Initialization v1, Initialization v2) {
        StructInitialization structInit = new StructInitialization();
        structInit.setFieldInitialization("CLASSIFICATION", v1);
        structInit.setFieldInitialization("GAMMA", v2);
        return structInit;
    }
}