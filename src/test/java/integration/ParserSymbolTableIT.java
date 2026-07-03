package integration;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.*;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class ParserSymbolTableIT {

    @Test
    public void parse_WithOutputVariables_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = """
            FUNCTION_BLOCK main
            VAR_OUTPUT
                power1 : REAL;
                power2 : REAL := 0.3e10;
            END_VAR
            END_FUNCTION_BLOCK
            """;

        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st);
        Parser parser = new Parser(lexer, st);
        assertTrue(parser.parse());

        LexemeInfo power1Info = st.get("power1");
        assertNotNull(power1Info, "Variable 'power1' should exist in the symbol table.");
        assertAll("Properties of 'power1' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, power1Info.type),
                () -> assertEquals(Subtype.REAL, power1Info.subtype),
                () -> assertEquals(Use.VARIABLE, power1Info.use),
                () -> assertEquals(Source.OUT, power1Info.source)
                // TODO: hacer que el parser cargue un valor default para una variable REAL sin inicializar
                // TODO: precargar las constantes en la tabla de simbolos.
                // () -> assertEquals("0.0", power1Info.initialValue)
        );

        LexemeInfo power2Info = st.get("power2");
        assertNotNull(power2Info, "Variable 'power2' should exist in the symbol table.");
        assertAll("Properties of 'power2' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, power2Info.type),
                () -> assertEquals(Subtype.REAL, power2Info.subtype),
                () -> assertEquals(Use.VARIABLE, power2Info.use),
                () -> assertEquals(Source.OUT, power2Info.source),
                () -> assertEquals("0.3e10", power2Info.initialValue)
        );
    }

    @Test
    public void parse_WithSubrangeVariables_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = """
            TYPE
                TANK_LEVEL : INT (0..100);
                FREQUENCY_HZ : INT (10..60) := 25;
            END_TYPE
            FUNCTION_BLOCK main
            VAR
                Level1 : TANK_LEVEL;
                Level2 : TANK_LEVEL := 50;
                Freq1  : FREQUENCY_HZ;
                Freq2  : FREQUENCY_HZ := 20;
            END_VAR
            END_FUNCTION_BLOCK
            """;

        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st);
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