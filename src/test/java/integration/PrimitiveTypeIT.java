package integration;

import parser.initializations.RealInitialization;
import parser.initializations.VariableInitialization;
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

import java.util.List;

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
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "MAIN#POWER1", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.VARIABLE)
                .source(source)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "MAIN#POWER2", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.VARIABLE)
                .source(source)
                .initialValue(new VariableInitialization(".3e10"))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }
}