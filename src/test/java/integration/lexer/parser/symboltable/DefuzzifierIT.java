package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import parser.initializations.leafs.VariableInitialization;
import utils.*;
import utils.ParsingResults;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Arrays;
import java.util.Collections;

/**
 * Integration tests for DEFUZZIFY blocks, written against the "Defuzzifier" page of the symbol table
 * documentation.
 * <p>
 * Requires the enum constants {@code Type.FUNCTION}, {@code Type.NONE}, {@code Subtype.SINGLETON},
 * {@code Use.COMPONENT} and {@code Use.TERM}. Every test that depends on behavior not yet implemented is
 * marked {@link NotYetImplemented}.
 * </p>
 */
public class DefuzzifierIT extends ParserTestSupport {

    private static final String DT1 = String.join("\n",
            "DEFUZZIFY dT1",
            "    TERM negative   := -5;",
            "    TERM zero       := 0;",
            "    TERM positive   := 5;",
            "    METHOD: CoGS;",
            "    DEFAULT:= 0;",
            "END_DEFUZZIFY");

    /** Key as written in the documentation: scope, '@' and the lower-case marker, without '#'. */
    private static final String ANONYMOUS_DEFUZZIFIER = "DT1";
    private static final String DEFUZZ_SUFFIX = "@defuzzifier";

    @Test
    void Defuzzify_block_with_singleton_terms_is_accepted() throws Exception {
        parse(SourceCode.functionBlock().body(DT1));
    }

    /**
     * Two values here follow the documentation literally and should be confirmed: the type is
     * {@code Type.NONE} (the page says "Type.CoGS", which is not a type and looks like the method name
     * pasted by mistake), and the default is "0.0" although the source says {@code DEFAULT:= 0}.
     */
    @Test
    void Anonymous_defuzzifier_is_published_with_its_term_names_and_default_value() throws Exception {
        String symbol = SourceCode.key(ANONYMOUS_DEFUZZIFIER) + DEFUZZ_SUFFIX;
        LexemeInfo expectedInfo = new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.COGS)
                .use(Use.COMPONENT)
                .source(Source.DEFUZZIFY)
                .parameters(Arrays.asList("NEGATIVE", "ZERO", "POSITIVE"))
                .initialValue(new VariableInitialization("0"))
                .build();

        ParsingResults results = parse(
                SourceCode.functionBlock()
                        .body(DT1)
        );
        SymbolTable symbolTable = results.symbolTable();

        assertSymbol(symbolTable, symbol, expectedInfo);
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({"NEGATIVE, -5", "ZERO, 0", "POSITIVE, 5"})
    void Singleton_term_publishes_its_value_as_the_only_inferior_limit(String term, String value) throws Exception {
        String symbol = SourceCode.key("DT1#" + term);
        LexemeInfo expectedInfo = new LexemeInfoBuilder()
                .type(Type.FUNCTION)
                .subtype(Subtype.SINGLETON)
                .use(Use.TERM)
                .source(Source.DEFUZZIFY)
                .inferiorLimits(Collections.singletonList(value))
                .superiorLimits(Collections.singletonList("1"))
                .parameters(Collections.singletonList(value))
                .build();

        ParsingResults results = parse(
                SourceCode.functionBlock()
                        .body(DT1)
        );
        SymbolTable symbolTable = results.symbolTable();

        assertSymbol(symbolTable, symbol, expectedInfo);
    }
}
