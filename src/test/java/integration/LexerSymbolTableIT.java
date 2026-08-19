package integration;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.*;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class LexerSymbolTableIT {

    /**
     * Helper method to consume all lexer tokens,
     * simulating the parser behavior.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private void consumeAllTokens(Lexer lexer) throws IOException {
        while (lexer.yylex() != Lexer.YYEOF);
    }

    @Test
    public void lex_WithAllNumericLiterals_PopulatesSymbolTableWithConstants() throws Exception {
        String sourceCode = """
            VAR
                v1  : INT := 42;
                v2  : INT := +15;
                v3  : INT := -99;
                v4  : REAL := 3.14;
                v5  : REAL := +2.71;
                v6  : REAL := -9.81;
                v7  : REAL := 1.5e10;
                v8  : REAL := -2.5E-3;
                v9  : INT := 2#1010;
                v10 : INT := 8#77;
                v11 : INT := 16#FF;
            END_VAR
            """;

        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st);
        consumeAllTokens(lexer);

        LexemeInfo intUnsigned = st.get("42");
        assertNotNull(intUnsigned, "Literal '42' should exist in the symbol table.");
        assertAll("Properties of '42' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, intUnsigned.type),
                () -> assertEquals(Subtype.INT, intUnsigned.subtype),
                () -> assertNull(intUnsigned.customType),
                () -> assertEquals(Use.LITERAL, intUnsigned.use),
                () -> assertEquals(Source.UNKNOWN, intUnsigned.source),
                () -> assertNull(intUnsigned.inferiorLimit),
                () -> assertNull(intUnsigned.superiorLimit),
                () -> assertNull(intUnsigned.parameters),
                () -> assertEquals(42, intUnsigned.initialValue)
        );

        LexemeInfo intPositive = st.get("+15");
        assertNotNull(intPositive, "Literal '+15' should exist in the symbol table.");
        assertAll("Properties of '+15' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, intPositive.type),
                () -> assertEquals(Subtype.INT, intPositive.subtype),
                () -> assertNull(intPositive.customType),
                () -> assertEquals(Use.LITERAL, intPositive.use),
                () -> assertEquals(Source.UNKNOWN, intPositive.source),
                () -> assertNull(intPositive.inferiorLimit),
                () -> assertNull(intPositive.superiorLimit),
                () -> assertNull(intPositive.parameters),
                () -> assertEquals(15, intPositive.initialValue)
        );

        LexemeInfo intNegative = st.get("-99");
        assertNotNull(intNegative, "Literal '-99' should exist in the symbol table.");
        assertAll("Properties of '-99' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, intNegative.type),
                () -> assertEquals(Subtype.INT, intNegative.subtype),
                () -> assertNull(intNegative.customType),
                () -> assertEquals(Use.LITERAL, intNegative.use),
                () -> assertEquals(Source.UNKNOWN, intNegative.source),
                () -> assertNull(intNegative.inferiorLimit),
                () -> assertNull(intNegative.superiorLimit),
                () -> assertNull(intNegative.parameters),
                () -> assertEquals(-99, intNegative.initialValue)
        );

        LexemeInfo realUnsigned = st.get("3.14");
        assertNotNull(realUnsigned, "Literal '3.14' should exist in the symbol table.");
        assertAll("Properties of '3.14' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, realUnsigned.type),
                () -> assertEquals(Subtype.REAL, realUnsigned.subtype),
                () -> assertNull(realUnsigned.customType),
                () -> assertEquals(Use.LITERAL, realUnsigned.use),
                () -> assertEquals(Source.UNKNOWN, realUnsigned.source),
                () -> assertNull(realUnsigned.inferiorLimit),
                () -> assertNull(realUnsigned.superiorLimit),
                () -> assertNull(realUnsigned.parameters),
                () -> assertEquals(3.14, realUnsigned.initialValue)
        );

        LexemeInfo realPositive = st.get("+2.71");
        assertNotNull(realPositive, "Literal '+2.71' should exist in the symbol table.");
        assertAll("Properties of '+2.71' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, realPositive.type),
                () -> assertEquals(Subtype.REAL, realPositive.subtype),
                () -> assertNull(realPositive.customType),
                () -> assertEquals(Use.LITERAL, realPositive.use),
                () -> assertEquals(Source.UNKNOWN, realPositive.source),
                () -> assertNull(realPositive.inferiorLimit),
                () -> assertNull(realPositive.superiorLimit),
                () -> assertNull(realPositive.parameters),
                () -> assertEquals(2.71, realPositive.initialValue)
        );

        LexemeInfo realNegative = st.get("-9.81");
        assertNotNull(realNegative, "Literal '-9.81' should exist in the symbol table.");
        assertAll("Properties of '-9.81' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, realNegative.type),
                () -> assertEquals(Subtype.REAL, realNegative.subtype),
                () -> assertNull(realNegative.customType),
                () -> assertEquals(Use.LITERAL, realNegative.use),
                () -> assertEquals(Source.UNKNOWN, realNegative.source),
                () -> assertNull(realNegative.inferiorLimit),
                () -> assertNull(realNegative.superiorLimit),
                () -> assertNull(realNegative.parameters),
                () -> assertEquals(-9.81, realNegative.initialValue)
        );

        LexemeInfo realExp = st.get("1.5e10");
        assertNotNull(realExp, "Literal '1.5e10' should exist in the symbol table.");
        assertAll("Properties of '1.5e10' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, realExp.type),
                () -> assertEquals(Subtype.REAL, realExp.subtype),
                () -> assertNull(realExp.customType),
                () -> assertEquals(Use.LITERAL, realExp.use),
                () -> assertEquals(Source.UNKNOWN, realExp.source),
                () -> assertNull(realExp.inferiorLimit),
                () -> assertNull(realExp.superiorLimit),
                () -> assertNull(realExp.parameters),
                () -> assertEquals(150_000_000_000D, realExp.initialValue)
        );

        LexemeInfo realExpNegative = st.get("-2.5E-3");
        assertNotNull(realExpNegative, "Literal '-2.5E-3' should exist in the symbol table.");
        assertAll("Properties of '-2.5E-3' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, realExpNegative.type),
                () -> assertEquals(Subtype.REAL, realExpNegative.subtype),
                () -> assertNull(realExpNegative.customType),
                () -> assertEquals(Use.LITERAL, realExpNegative.use),
                () -> assertEquals(Source.UNKNOWN, realExpNegative.source),
                () -> assertNull(realExpNegative.inferiorLimit),
                () -> assertNull(realExpNegative.superiorLimit),
                () -> assertNull(realExpNegative.parameters),
                () -> assertEquals(0.00025, realExpNegative.initialValue)
        );

        LexemeInfo binInfo = st.get("2#1010");
        assertNotNull(binInfo, "Literal '2#1010' should exist in the symbol table.");
        assertAll("Properties of '2#1010' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, binInfo.type),
                () -> assertEquals(Subtype.INT, binInfo.subtype),
                () -> assertNull(binInfo.customType),
                () -> assertEquals(Use.LITERAL, binInfo.use),
                () -> assertEquals(Source.UNKNOWN, binInfo.source),
                () -> assertNull(binInfo.inferiorLimit),
                () -> assertNull(binInfo.superiorLimit),
                () -> assertNull(binInfo.parameters),
                () -> assertEquals(10, binInfo.initialValue)
        );

        LexemeInfo octInfo = st.get("8#77");
        assertNotNull(octInfo, "Literal '8#77' should exist in the symbol table.");
        assertAll("Properties of '8#77' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, octInfo.type),
                () -> assertEquals(Subtype.INT, octInfo.subtype),
                () -> assertNull(octInfo.customType),
                () -> assertEquals(Use.LITERAL, octInfo.use),
                () -> assertEquals(Source.UNKNOWN, octInfo.source),
                () -> assertNull(octInfo.inferiorLimit),
                () -> assertNull(octInfo.superiorLimit),
                () -> assertNull(octInfo.parameters),
                () -> assertEquals(63, octInfo.initialValue)
        );

        LexemeInfo hexInfo = st.get("16#FF");
        assertNotNull(hexInfo, "Literal '16#FF' should exist in the symbol table.");
        assertAll("Properties of '16#FF' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, hexInfo.type),
                () -> assertEquals(Subtype.INT, hexInfo.subtype),
                () -> assertNull(hexInfo.customType),
                () -> assertEquals(Use.LITERAL, hexInfo.use),
                () -> assertEquals(Source.UNKNOWN, hexInfo.source),
                () -> assertNull(hexInfo.inferiorLimit),
                () -> assertNull(hexInfo.superiorLimit),
                () -> assertNull(hexInfo.parameters),
                () -> assertEquals(255, hexInfo.initialValue)
        );
    }

    @Test
    public void lex_WithStringLiterals_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = """
            VAR
                statusMsg1 : STRING  := 'System OK';
                statusMsg2 : WSTRING := "System OK";
            END_VAR
            """;

        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st);
        consumeAllTokens(lexer);

        LexemeInfo stringInfo = st.get("'System OK'");
        assertNotNull(stringInfo, "String literal 'System OK' should exist in the symbol table.");
        assertAll("Properties of string literal LexemeInfo",
                () -> assertEquals(Type.SIMPLE, stringInfo.type),
                () -> assertEquals(Subtype.STRING, stringInfo.subtype),
                () -> assertNull(stringInfo.customType),
                () -> assertEquals(Use.LITERAL, stringInfo.use),
                () -> assertEquals(Source.UNKNOWN, stringInfo.source),
                () -> assertNull(stringInfo.inferiorLimit),
                () -> assertNull(stringInfo.superiorLimit),
                () -> assertNull(stringInfo.parameters),
                () -> assertEquals("'System OK'", stringInfo.initialValue)
        );

        LexemeInfo wstringInfo = st.get("\"System OK\"");
        assertNotNull(wstringInfo, "String literal \"System OK\" should exist in the symbol table.");
        assertAll("Properties of wstring literal LexemeInfo",
                () -> assertEquals(Type.SIMPLE, wstringInfo.type),
                () -> assertEquals(Subtype.WSTRING, wstringInfo.subtype),
                () -> assertNull(wstringInfo.customType),
                () -> assertEquals(Use.LITERAL, wstringInfo.use),
                () -> assertEquals(Source.UNKNOWN, wstringInfo.source),
                () -> assertNull(wstringInfo.inferiorLimit),
                () -> assertNull(wstringInfo.superiorLimit),
                () -> assertNull(wstringInfo.parameters),
                () -> assertEquals("\"System OK\"", wstringInfo.initialValue)
        );
    }

    @Test
    public void lex_WithBooleanLiterals_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = """
            VAR
                isActive  : BOOL := TRUE;
                isError   : BOOL := FALSE;
            END_VAR
            """;

        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st);
        consumeAllTokens(lexer);

        LexemeInfo trueInfo = st.get("TRUE");
        assertNotNull(trueInfo, "Literal 'TRUE' should exist in the symbol table.");
        assertAll("Properties of 'TRUE' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, trueInfo.type),
                () -> assertEquals(Subtype.BOOL, trueInfo.subtype),
                () -> assertNull(trueInfo.customType),
                () -> assertEquals(Use.LITERAL, trueInfo.use),
                () -> assertEquals(Source.UNKNOWN, trueInfo.source),
                () -> assertNull(trueInfo.inferiorLimit),
                () -> assertNull(trueInfo.superiorLimit),
                () -> assertNull(trueInfo.parameters),
                () -> assertEquals(true, trueInfo.initialValue)
        );

        LexemeInfo falseInfo = st.get("FALSE");
        assertNotNull(falseInfo, "Literal 'FALSE' should exist in the symbol table.");
        assertAll("Properties of 'FALSE' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, falseInfo.type),
                () -> assertEquals(Subtype.BOOL, falseInfo.subtype),
                () -> assertNull(falseInfo.customType),
                () -> assertEquals(Use.LITERAL, falseInfo.use),
                () -> assertEquals(Source.UNKNOWN, falseInfo.source),
                () -> assertNull(falseInfo.inferiorLimit),
                () -> assertNull(falseInfo.superiorLimit),
                () -> assertNull(falseInfo.parameters),
                () -> assertEquals(false, falseInfo.initialValue)
        );
    }

    @Test
    public void lex_WithTimeAndDateLiterals_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = """
            VAR
                delayTime : TIME := 150ms;
                maxWait   : TIME := 1d_12h;
                startDate : DATE := 2026-07-03;
                logTime   : TIME_OF_DAY := 14:30:00.0;
            END_VAR
            """;

        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st);
        consumeAllTokens(lexer);

        LexemeInfo delayInfo = st.get("150ms");
        assertNotNull(delayInfo, "Time literal '150ms' should exist in the symbol table.");
        assertAll("Properties of '150ms' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, delayInfo.type),
                () -> assertEquals(Subtype.TIME, delayInfo.subtype),
                () -> assertNull(delayInfo.customType),
                () -> assertEquals(Use.LITERAL, delayInfo.use),
                () -> assertEquals(Source.UNKNOWN, delayInfo.source),
                () -> assertNull(delayInfo.inferiorLimit),
                () -> assertNull(delayInfo.superiorLimit),
                () -> assertNull(delayInfo.parameters),
                () -> assertEquals("150ms", delayInfo.initialValue)
        );

        LexemeInfo maxWaitInfo = st.get("1d_12h");
        assertNotNull(maxWaitInfo, "Time literal '1d_12h' should exist in the symbol table.");
        assertAll("Properties of '1d_12h' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, maxWaitInfo.type),
                () -> assertEquals(Subtype.TIME, maxWaitInfo.subtype),
                () -> assertNull(maxWaitInfo.customType),
                () -> assertEquals(Use.LITERAL, maxWaitInfo.use),
                () -> assertEquals(Source.UNKNOWN, maxWaitInfo.source),
                () -> assertNull(maxWaitInfo.inferiorLimit),
                () -> assertNull(maxWaitInfo.superiorLimit),
                () -> assertNull(maxWaitInfo.parameters),
                () -> assertEquals("1d_12h", maxWaitInfo.initialValue)
        );

        LexemeInfo dateInfo = st.get("2026-07-03");
        assertNotNull(dateInfo, "Date literal '2026-07-03' should exist in the symbol table.");
        assertAll("Properties of '2026-07-03' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, dateInfo.type),
                () -> assertEquals(Subtype.DATE, dateInfo.subtype),
                () -> assertNull(dateInfo.customType),
                () -> assertEquals(Use.LITERAL, dateInfo.use),
                () -> assertEquals(Source.UNKNOWN, dateInfo.source),
                () -> assertNull(dateInfo.inferiorLimit),
                () -> assertNull(dateInfo.superiorLimit),
                () -> assertNull(dateInfo.parameters),
                () -> assertEquals("2026-07-03", dateInfo.initialValue)
        );

        LexemeInfo timeOfDayInfo = st.get("14:30:00.0");
        assertNotNull(timeOfDayInfo, "Time_Of_Day literal '14:30:00.0' should exist in the symbol table.");
        assertAll("Properties of '14:30:00.0' LexemeInfo",
                () -> assertEquals(Type.SIMPLE, timeOfDayInfo.type),
                () -> assertEquals(Subtype.TIME_OF_DAY, timeOfDayInfo.subtype),
                () -> assertNull(timeOfDayInfo.customType),
                () -> assertEquals(Use.LITERAL, timeOfDayInfo.use),
                () -> assertEquals(Source.UNKNOWN, timeOfDayInfo.source),
                () -> assertNull(timeOfDayInfo.inferiorLimit),
                () -> assertNull(timeOfDayInfo.superiorLimit),
                () -> assertNull(timeOfDayInfo.parameters),
                () -> assertEquals("14:30:00.0", timeOfDayInfo.initialValue)
        );
    }

    @Test
    public void lex_WithIdentifiers_PopulatesSymbolTableWithUnknownUse() throws Exception {
        String sourceCode = """
            VAR
                Sensor_Temp : REAL;
            END_VAR
            """;

        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st);
        consumeAllTokens(lexer);

        LexemeInfo sensorInfo = st.get("Sensor_Temp");
        assertNotNull(sensorInfo, "Identifier 'Sensor_Temp' should exist in the symbol table.");
        assertAll("Properties of 'Sensor_Temp' LexemeInfo after Lexing",
                () -> assertEquals(Type.UNKNOWN, sensorInfo.type),
                () -> assertEquals(Subtype.UNKNOWN, sensorInfo.subtype),
                () -> assertNull(sensorInfo.customType),
                () -> assertEquals(Use.UNKNOWN, sensorInfo.use),
                () -> assertEquals(Source.UNKNOWN, sensorInfo.source),
                () -> assertNull(sensorInfo.inferiorLimit),
                () -> assertNull(sensorInfo.superiorLimit),
                () -> assertNull(sensorInfo.parameters),
                () -> assertNull(sensorInfo.initialValue)
        );
    }

    // TODO: Verificar que dos identificadores con mismos caracteres son agregados de igual forma (Case Insensitive)
    // TODO: Verificar que los guiones son ignorados
}