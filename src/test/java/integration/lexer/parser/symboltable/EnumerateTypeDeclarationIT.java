package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import utils.ParserTestSupport;

import parser.initializations.EnumeratedInitialization;
import parser.initializations.MacroInitialization;
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
 * Integration tests for ENUMERATED type declarations: custom enum type resolution,
 * enumerators registration, and macro constant generation within FUNCTION_BLOCK scope.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class EnumerateTypeDeclarationIT extends ParserTestSupport {
    @Test
    public void Declaring_Valid_Enum_Type_Registers_Enumerators_And_Macros() throws Exception {
        String sourceCode = "TYPE\n"
                + "    MethodType : (CENTROID, AVERAGE);\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "END_FUNCTION_BLOCK\n";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "METHODTYPE", new LexemeInfoBuilder()
                .type(Type.ENUMERATE)
                .subtype(Subtype.INT)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("CENTROID", "AVERAGE"))
                .initialValue(new EnumeratedInitialization(Arrays.asList("CENTROID", "AVERAGE")))
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "METHODTYPE#CENTROID", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(st, "0"))
                .build()
        );
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "METHODTYPE#AVERAGE", new LexemeInfoBuilder()
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