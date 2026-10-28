package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.LexemeInfo;
import utils.ParserTestSupport;
import utils.ParsingResults;
import utils.SourceCode;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Arrays;
import java.util.List;

/**
 * Integration tests for FUZZIFY blocks, written against the "Fuzzifier" page of the symbol table
 * documentation.
 * <p>
 * Requires the enum constants {@code Type.FUNCTION}, {@code Type.NONE}, {@code Subtype.LINEAR},
 * {@code Use.COMPONENT} and {@code Use.TERM}. Every test that depends on behavior not yet implemented is
 * marked {@link NotYetImplemented}.
 * </p>
 */
public class FuzzifierIT extends ParserTestSupport {

    private static final String HUMIDITY = String.join("\n",
            "FUZZIFY humidity",
            "    TERM low    := (30,1) (50,0);",
            "    TERM middle := (30,0) (50,1) (70,1) (80,0);",
            "    TERM high   := (70,0) (80,1);",
            "END_FUZZIFY");

    /** Key as written in the documentation: scope, '@' and the lower-case marker, without '#'. */
    private static final String ANONYMOUS_FUZZIFIER = "HUMIDITY";
    private static final String FUZZ_SUFFIX = "@fuzzifier";

    private static List<String> list(String... values) {
        return Arrays.asList(values);
    }

    /** Two-point ramp: no redundant point, so its parameters are its x values. */
    private static LexemeInfoBuilder term(List<String> inferior, List<String> superior) {
        return new LexemeInfoBuilder()
                .type(Type.FUNCTION)
                .subtype(Subtype.LINEAR)
                .use(Use.TERM)
                .source(Source.FUZZIFY)
                .inferiorLimits(inferior)
                .superiorLimits(superior)
                .parameters(inferior);
    }

    private static LexemeInfoBuilder fuzzifierComponent(List<String> terms) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.COMPONENT)
                .source(Source.FUZZIFY)
                .parameters(terms);
    }

    @Nested
    class AsFuzzifier {

        @Test
        void Fuzzify_block_with_point_list_terms_is_accepted() throws Exception {
            parse(
                    SourceCode.functionBlock()
                            .body(HUMIDITY)
            );
        }

        @Test
        void Anonymous_fuzzifier_is_published_with_the_names_of_its_terms() throws Exception {
            String symbol = SourceCode.key(ANONYMOUS_FUZZIFIER) + FUZZ_SUFFIX;
            LexemeInfo expectedInfo = fuzzifierComponent(list("LOW", "MIDDLE", "HIGH")).build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(HUMIDITY)
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }
    }

    @Nested
    class AsTerms {

        /** The documentation specifies LINEAR for "low"; "high" also has two points and is assumed alike. */
        @Test
        void Term_with_two_points_is_linear_and_keeps_its_points_as_limits() throws Exception {
            String symbolLow = SourceCode.key("HUMIDITY#LOW");
            LexemeInfo expectedInfoLow = term(list("30", "50"), list("1", "0")).build();

            String symbolHigh = SourceCode.key("HUMIDITY#HIGH");
            LexemeInfo expectedInfoHigh = term(list("70", "80"), list("0", "1")).build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(HUMIDITY)
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbolLow, expectedInfoLow);
            assertSymbol(symbolTable, symbolHigh, expectedInfoHigh);
        }

        /**
         * The documentation does not give the subtype of a four-point term, so only the common attributes and
         * the limits (x values as inferior, y values as superior, as in "low") are asserted.
         */
        @Test
        void Term_with_four_points_is_published_with_its_points_as_limits() throws Exception {
            String symbol = SourceCode.key("HUMIDITY#MIDDLE");
            LexemeInfo expectedInfo = new LexemeInfoBuilder()
                    .type(Type.FUNCTION)
                    .subtype(Subtype.TRAPEZOID)
                    .use(Use.TERM)
                    .source(Source.FUZZIFY)
                    .inferiorLimits(list("30", "50", "70", "80"))
                    .superiorLimits(list("0", "1", "1", "0"))
                    .parameters(list("30", "50", "70", "80"))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(HUMIDITY)
            );
            SymbolTable symbolTable = results.symbolTable();

            assertSymbol(symbolTable, symbol, expectedInfo);
        }

        /**
         * Outside its points a membership function keeps the degree of the first/last point (IEC 61131-7, 5.2.2),
         * so three points can be a ramp ("shoulder") and not a triangle. The limits keep the points as written;
         * the parameters are the points that define the ramp.
         */
        @Test
        void Three_point_shoulder_is_linear_and_keeps_only_its_defining_points_as_parameters() throws Exception {
            String symbol = SourceCode.key("TEMP#COLD");
            LexemeInfo expectedInfo = new LexemeInfoBuilder()
                    .type(Type.FUNCTION)
                    .subtype(Subtype.LINEAR)
                    .use(Use.TERM)
                    .source(Source.FUZZIFY)
                    .inferiorLimits(list("0", "5", "10"))
                    .superiorLimits(list("1", "1", "0"))
                    .parameters(list("5", "10"))
                    .build();

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body("FUZZIFY temp TERM cold := (0,1) (5,1) (10,0); END_FUZZIFY")
            );

            assertSymbol(results.symbolTable(), symbol, expectedInfo);
        }
    }
}