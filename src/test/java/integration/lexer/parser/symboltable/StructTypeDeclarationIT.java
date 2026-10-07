package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import org.junit.jupiter.api.Test;
import parser.initializations.RealInitialization;
import parser.initializations.StructInitialization;
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
 * Integration tests for STRUCT type declarations: field resolution,
 * default initial values.
 */
public class StructTypeDeclarationIT extends ParserTestSupport {

    @Test
    public void Declaring_Valid_Struct_Type_Registers_Fields() throws Exception {
        String sourceCode = "TYPE\n"
                + "    color_type : STRUCT\n"
                + "        brown: REAL;\n"
                + "        light: REAL;\n"
                + "    END_STRUCT;\n"
                + "END_TYPE\n"
                + "FUNCTION_BLOCK main\n"
                + "VAR_INPUT\n"
                + "    color : color_type;\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);

        StructInitialization colorTypeValue = new StructInitialization();
        colorTypeValue.setFieldInitialization("BROWN", new RealInitialization(st));
        colorTypeValue.setFieldInitialization("LIGHT", new RealInitialization(st));

        List<String> diffs;

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE", new LexemeInfoBuilder()
                .type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .use(Use.TYPE)
                .source(Source.NONE)
                .parameters(Arrays.asList("BROWN", "LIGHT"))
                .initialValue(colorTypeValue)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#BROWN", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        diffs = LexemeInfoComparator.compare(st, "COLOR_TYPE#LIGHT", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.FIELD)
                .source(Source.NONE)
                .initialValue(new RealInitialization(st))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }
}