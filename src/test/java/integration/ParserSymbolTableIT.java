package integration;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import utils.DiagnosticsHandler;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.*;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for parser and symbol table interaction.
 * <p>
 * Tests that the parser correctly populates the symbol table with
 * variable declarations, type definitions, and initial values.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class ParserSymbolTableIT {

    @Test
    public void parse_WithOutputVariables_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "FUNCTION_BLOCK main\n"
            + "VAR_OUTPUT\n"
            + "    power1 : REAL;\n"
            + "    power2 : REAL := 0.3e10;\n"
            + "END_VAR\n"
            + "END_FUNCTION_BLOCK\n";

        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st, new DiagnosticsHandler());
        Parser parser = new Parser(lexer, st);
        assertTrue(parser.parse());

        LexemeInfo power1Info = st.get("POWER1");
        assertNotNull(power1Info, "Variable 'POWER1' should exist in the symbol table.");
        assertAll("Properties of 'POWER1' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, power1Info.type),
                () -> assertEquals(Subtype.REAL, power1Info.subtype),
                () -> assertEquals(Use.VARIABLE, power1Info.use),
                () -> assertEquals(Source.OUT, power1Info.source)
                // TODO: hacer que el parser cargue un valor default para una variable REAL sin inicializar
                // TODO: precargar las constantes en la tabla de simbolos.
                // () -> assertEquals("0.0", power1Info.initialValue)
        );

        LexemeInfo power2Info = st.get("POWER2");
        assertNotNull(power2Info, "Variable 'POWER2' should exist in the symbol table.");
        assertAll("Properties of 'POWER2' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, power2Info.type),
                () -> assertEquals(Subtype.REAL, power2Info.subtype),
                () -> assertEquals(Use.VARIABLE, power2Info.use),
                () -> assertEquals(Source.OUT, power2Info.source),
                () -> assertEquals("0.3e10", power2Info.initialValue)
        );
    }

    @Test
    public void parse_WithSubrangeVariables_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "TYPE\n"
            + "    TANK_LEVEL : INT (0..100);\n"
            + "    FREQUENCY_HZ : INT (10..60) := 25;\n"
            + "END_TYPE\n"
            + "FUNCTION_BLOCK main\n"
            + "VAR\n"
            + "    Level1 : TANK_LEVEL;\n"
            + "    Level2 : TANK_LEVEL := 50;\n"
            + "    Freq1  : FREQUENCY_HZ;\n"
            + "    Freq2  : FREQUENCY_HZ := 20;\n"
            + "END_VAR\n"
            + "END_FUNCTION_BLOCK\n";

        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st, new DiagnosticsHandler());
        Parser parser = new Parser(lexer, st);
        assertTrue(parser.parse());

        LexemeInfo tankLevelInfo = st.get("TANK_LEVEL");
        assertNotNull(tankLevelInfo, "Type 'TANK_LEVEL' should exist in the symbol table.");
        assertAll("Properties of 'TANK_LEVEL' LexemeInfo",
                () -> assertEquals(Type.SUBRANGE, tankLevelInfo.type),
                () -> assertEquals(Subtype.INT, tankLevelInfo.subtype),
                () -> assertEquals(Use.TYPE, tankLevelInfo.use),
                () -> assertEquals(Source.UNKNOWN, tankLevelInfo.source),
                () -> assertEquals("0", tankLevelInfo.inferiorLimit),
                () -> assertEquals("100", tankLevelInfo.superiorLimit),
                () -> assertEquals("0", tankLevelInfo.initialValue)
        );

        LexemeInfo frequencyHZInfo = st.get("FREQUENCY_HZ");
        assertNotNull(frequencyHZInfo, "Type 'FREQUENCY_HZ' should exist in the symbol table.");
        assertAll("Properties of 'frequencyHZInfo' LexemeInfo",
                () -> assertEquals(Type.SUBRANGE, frequencyHZInfo.type),
                () -> assertEquals(Subtype.INT, frequencyHZInfo.subtype),
                () -> assertEquals(Use.TYPE, frequencyHZInfo.use),
                () -> assertEquals(Source.UNKNOWN, frequencyHZInfo.source),
                () -> assertEquals("10", frequencyHZInfo.inferiorLimit),
                () -> assertEquals("60", frequencyHZInfo.superiorLimit),
                () -> assertEquals("25", frequencyHZInfo.initialValue)
        );

        LexemeInfo level1Info = st.get("Level1");
        assertNotNull(level1Info, "Variable 'Level1' should exist in the symbol table.");
        assertAll("Properties of 'Level1' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, level1Info.type),
                () -> assertEquals(Subtype.CUSTOM, level1Info.subtype),
                () -> assertEquals("TANK_LEVEL", level1Info.customType),
                () -> assertEquals(Use.VARIABLE, level1Info.use),
                () -> assertEquals(Source.NONE, level1Info.source),
                () -> assertEquals("0", level1Info.initialValue)
        );

        LexemeInfo level2Info = st.get("Level2");
        assertNotNull(level2Info, "Variable 'Level2' should exist in the symbol table.");
        assertAll("Properties of 'Level2' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, level2Info.type),
                () -> assertEquals(Subtype.CUSTOM, level2Info.subtype),
                () -> assertEquals("TANK_LEVEL", level2Info.customType),
                () -> assertEquals(Use.VARIABLE, level2Info.use),
                () -> assertEquals(Source.NONE, level2Info.source),
                () -> assertEquals("50", level2Info.initialValue)
        );

        LexemeInfo freq1Info = st.get("Freq1");
        assertNotNull(freq1Info, "Variable 'Freq1' should exist in the symbol table.");
        assertAll("Properties of 'Freq1' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, freq1Info.type),
                () -> assertEquals(Subtype.CUSTOM, freq1Info.subtype),
                () -> assertEquals("FREQUENCY_HZ", freq1Info.customType),
                () -> assertEquals(Use.VARIABLE, freq1Info.use),
                () -> assertEquals(Source.NONE, freq1Info.source),
                () -> assertEquals("25", freq1Info.initialValue)
        );

        LexemeInfo freq2Info = st.get("Freq2");
        assertNotNull(freq2Info, "Variable 'Freq2' should exist in the symbol table.");
        assertAll("Properties of 'Freq2' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, freq2Info.type),
                () -> assertEquals(Subtype.CUSTOM, freq2Info.subtype),
                () -> assertEquals("FREQUENCY_HZ", freq2Info.customType),
                () -> assertEquals(Use.VARIABLE, freq2Info.use),
                () -> assertEquals(Source.NONE, freq2Info.source),
                () -> assertEquals("20", freq2Info.initialValue)
        );
    }
}