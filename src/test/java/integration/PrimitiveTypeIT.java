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

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for how the parser resolves and initializes
 * primitive (PRIMITIVE) variable declarations, such as REAL, INT and BOOL.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class PrimitiveTypeIT extends ParserTestSupport {

    @ParameterizedTest
    @CsvSource({
            "VAR, INTERNAL",
            "VAR_INPUT, IN",
            "VAR_OUTPUT, OUT"
    })
    public void Parse_WithRealVariables_PopulatesSymbolTableCorrectly(String block, Source source) throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
                + block + "\n"
                + "    power1 : REAL;\n"
                + "    power2 : REAL := 0.3e10;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);

        assertTrue(LexemeInfoComparator.compare(st, "POWER1", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.VARIABLE)
                .source(source)
                .initialValue("0.0")
                .build()).isEmpty());

        assertTrue(LexemeInfoComparator.compare(st, "POWER2", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.VARIABLE)
                .source(source)
                .initialValue(".3e10")
                .build()).isEmpty());
    }
}