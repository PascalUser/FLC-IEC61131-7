package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import utils.LexemeInfo;
import utils.ParserTestSupport;
import utils.SourceCode;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    private static final String ANONYMOUS_FUZZIFIER = "HUMIDITY@fuzzifier";

    private static List<String> list(String... values) {
        return Arrays.asList(values);
    }

    private static LexemeInfoBuilder term(List<String> inferior, List<String> superior) {
        return new LexemeInfoBuilder()
                .type(Type.FUNCTION)
                .subtype(Subtype.LINEAR)
                .use(Use.TERM)
                .source(Source.FUZZIFY)
                .inferiorLimits(inferior)
                .superiorLimits(superior);
    }

    @Test
    void Fuzzify_block_with_point_list_terms_is_accepted() throws Exception {
        parse(SourceCode.functionBlock().body(HUMIDITY));
    }

    @Test
    @NotYetImplemented
    void Anonymous_fuzzifier_is_published_with_the_names_of_its_terms() throws Exception {
        SymbolTable st = parse(SourceCode.functionBlock().body(HUMIDITY));

        assertSymbol(st, ANONYMOUS_FUZZIFIER, new LexemeInfoBuilder()
                .type(Type.NONE)
                .subtype(Subtype.NONE)
                .use(Use.COMPONENT)
                .source(Source.FUZZIFY)
                .parameters(list("LOW", "MIDDLE", "HIGH"))
                .build());
    }

    /** The documentation specifies LINEAR for "low"; "high" also has two points and is assumed alike. */
    @Test
    @NotYetImplemented
    void Term_with_two_points_is_linear_and_keeps_its_points_as_limits() throws Exception {
        SymbolTable st = parse(SourceCode.functionBlock().body(HUMIDITY));

        assertSymbol(st, "HUMIDITY#LOW", term(list("30", "50"), list("1", "0")).build());
        assertSymbol(st, "HUMIDITY#HIGH", term(list("70", "80"), list("0", "1")).build());
    }

    /**
     * The documentation does not give the subtype of a four-point term, so only the common attributes and
     * the limits (x values as inferior, y values as superior, as in "low") are asserted.
     */
    @Test
    @NotYetImplemented
    void Term_with_four_points_is_published_with_its_points_as_limits() throws Exception {
        SymbolTable st = parse(SourceCode.functionBlock().body(HUMIDITY));

        LexemeInfo middle = st.get("HUMIDITY#MIDDLE");
        assertNotNull(middle, "HUMIDITY#MIDDLE was not published");
        assertEquals(Type.FUNCTION, middle.type);
        assertEquals(Use.TERM, middle.use);
        assertEquals(Source.FUZZIFY, middle.source);
        assertEquals(list("30", "50", "70", "80"), middle.inferiorLimits);
        assertEquals(list("0", "1", "1", "0"), middle.superiorLimits);
    }
}
