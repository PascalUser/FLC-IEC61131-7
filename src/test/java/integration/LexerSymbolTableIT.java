package integration;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import utils.DiagnosticsHandler;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.*;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for lexer and symbol table interaction.
 * <p>
 * Tests that the lexer correctly populates the symbol table with
 * semantic information for various literal types and identifiers.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class LexerSymbolTableIT {
    /**
     * Creates a string by repeating a character.
     *
     * @param c     the character to repeat
     * @param count the number of repetitions
     * @return a string consisting of the character repeated count times
     */
    private String repeatChar(char c, int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, c);
        return new String(chars);
    }

    /**
     * Executes the lexer on the given source code and returns the populated symbol table.
     *
     * @param sourceCode the IEC 61131-7 source code to lex
     * @return the symbol table after lexing
     * @throws IOException if an I/O error occurs
     */
    private SymbolTable executeLexer(String sourceCode) throws IOException {
        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, st, new DiagnosticsHandler());
        //noinspection StatementWithEmptyBody
        while (lexer.yylex() != Lexer.EOF);
        return st;
    }

    /**
     * Asserts that the given literal exists in the symbol table with the expected properties.
     *
     * @param st              the symbol table to check
     * @param lexeme          the literal lexeme to look up (as stored in symbol table, after transformations)
     * @param expectedSubtype the expected subtype
     * @param expectedValue   the expected initial value
     */
    private void assertLiteral(SymbolTable st, String lexeme, Subtype expectedSubtype, Object expectedValue) {
        LexemeInfo info = st.get(lexeme);
        assertNotNull(info, () -> "The literal '" + lexeme + "' should exist in the symbol table.");
        assertAll("LexemeInfo properties for '" + lexeme + "'",
                () -> assertEquals(Type.SIMPLE, info.type, "Main type is incorrect"),
                () -> assertEquals(expectedSubtype, info.subtype, "Subtype (Table 10) is incorrect"),
                () -> assertNull(info.customType, "customType should be null"),
                () -> assertEquals(Use.LITERAL, info.use, "Use should be LITERAL"),
                () -> assertEquals(Source.UNKNOWN, info.source, "Source should be UNKNOWN"),
                () -> assertNull(info.inferiorLimit, "inferiorLimit should be null"),
                () -> assertNull(info.superiorLimit, "superiorLimit should be null"),
                () -> assertNull(info.parameters, "parameters should be null"),
                () -> assertEquals(expectedValue, info.initialValue, "Initial value does not match")
        );
    }

    @Test
    public void Lex_SignedIntegerBoundaries_PopulatesSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : SINT := -128;\n"
                + "    v2 : SINT := -1;\n"
                + "    v3 : INT  := -32768;\n"
                + "    v4 : INT  := -32769;\n"
                + "    v5 : DINT := -2147483648;\n"
                + "    v6 : DINT := -2147483649;\n"
                + "    v7 : LINT := -9223372036854775808;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "-128", Subtype.SINT, -128L);
        assertLiteral(result, "-1", Subtype.SINT, -1L);
        assertLiteral(result, "-32768", Subtype.INT, -32768L);
        assertLiteral(result, "-32769", Subtype.DINT, -32769L);
        assertLiteral(result, "-2147483648", Subtype.DINT, -2147483648L);
        assertLiteral(result, "-2147483649", Subtype.LINT, -2147483649L);
        assertLiteral(result, "-9223372036854775808", Subtype.LINT, -9223372036854775808L);
    }

    @Test
    public void Lex_PositiveIntegerBoundaries_PopulatesSymbolTableWithCorrectSubtype() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : SINT := +127;\n"
                + "    v2 : INT := +32767;\n"
                + "    v3 : DINT := +2147483647;\n"
                + "    v4 : LINT := +9223372036854775807;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "+127", Subtype.SINT, 127L);
        assertLiteral(result, "+32767", Subtype.INT, 32767L);
        assertLiteral(result, "+2147483647", Subtype.DINT, 2147483647L);
        assertLiteral(result, "+9223372036854775807", Subtype.LINT, 9223372036854775807L);
    }

    @Test
    public void Lex_UnsignedIntegerBoundaries_PopulatesSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : USINT := 0;\n"
                + "    v2 : USINT := 255;\n"
                + "    v3 : UINT  := 65535;\n"
                + "    v4 : UDINT := 4294967295;\n"
                + "    v5 : ULINT := 18446744073709551615;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "0", Subtype.USINT, BigInteger.valueOf(0));
        assertLiteral(result, "255", Subtype.USINT, BigInteger.valueOf(255));
        assertLiteral(result, "65535", Subtype.UINT, BigInteger.valueOf(65535));
        assertLiteral(result, "4294967295", Subtype.UDINT, new BigInteger("4294967295"));
        assertLiteral(result, "18446744073709551615", Subtype.ULINT, new BigInteger("18446744073709551615"));
    }

    @Test
    public void Lex_NegativeRealAndLRealBoundaries_PopulatesSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : REAL  := -1.0;\n"
                + "    v2 : REAL  := -1.0E38;\n"
                + "    v3 : LREAL := -1.0E40;\n"
                + "    v4 : LREAL := " + (-Double.MAX_VALUE) + ";\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "-1.0", Subtype.REAL, -1.0);
        assertLiteral(result, "-1.0E38", Subtype.REAL, -1.0E38);
        assertLiteral(result, "-1.0E40", Subtype.LREAL, -1.0E40);
        assertLiteral(result, String.valueOf(-Double.MAX_VALUE), Subtype.LREAL, -Double.MAX_VALUE);
    }

    @Test
    public void Lex_PositiveRealAndLRealBoundaries_PopulatesSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : REAL  := 1.0;\n"
                + "    v2 : REAL  := 100.0;\n"
                + "    v3 : LREAL := 1.0E40;\n"
                + "    v4 : LREAL := " + Double.MAX_VALUE + ";\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "1.0", Subtype.REAL, 1.0);
        assertLiteral(result, "100.0", Subtype.REAL, 100.0);
        assertLiteral(result, "1.0E40", Subtype.LREAL, 1E40);
        assertLiteral(result, String.valueOf(Double.MAX_VALUE), Subtype.LREAL, Double.MAX_VALUE);
    }

    @Test
    public void Lex_ForBinaryBoundaries_PopulatesSymbolTableWithCorrectSubtype() throws Exception {
        String sourceCode = "VAR\n"
                + "    bByte : BYTE  := 2#" + repeatChar('1', 8) + ";\n"
                + "    bWord : WORD  := 2#" + repeatChar('1', 16) + ";\n"
                + "    bDWord: DWORD := 2#" + repeatChar('1', 32) + ";\n"
                + "    bLWord: LWORD := 2#" + repeatChar('1', 64) + ";\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "2#" + repeatChar('1',  8), Subtype.BYTE, BigInteger.valueOf(255));
        assertLiteral(result, "2#" + repeatChar('1', 16), Subtype.WORD, BigInteger.valueOf(65535));
        assertLiteral(result, "2#" + repeatChar('1', 32), Subtype.DWORD, new BigInteger("4294967295"));
        assertLiteral(result, "2#" + repeatChar('1', 64), Subtype.LWORD, new BigInteger("18446744073709551615"));
    }

    @Test
    public void Lex_ForOctalBoundaries_PopulatesSymbolTableWithCorrectSubtype() throws Exception {
        String sourceCode = "VAR\n"
                + "    oByte : BYTE  := 8#3" + repeatChar('7', 2) + ";\n"
                + "    oWord : WORD  := 8#1" + repeatChar('7', 5) + ";\n"
                + "    oDWord: DWORD := 8#3" + repeatChar('7', 10) + ";\n"
                + "    oLWord: LWORD := 8#1" + repeatChar('7', 21) + ";\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "8#3" + repeatChar('7', 2), Subtype.BYTE, BigInteger.valueOf(255));
        assertLiteral(result, "8#1" + repeatChar('7', 5), Subtype.WORD, BigInteger.valueOf(65535));
        assertLiteral(result, "8#3" + repeatChar('7', 10), Subtype.DWORD, new BigInteger("4294967295"));
        assertLiteral(result, "8#1" + repeatChar('7', 21), Subtype.LWORD, new BigInteger("18446744073709551615"));
    }

    @Test
    public void Lex_ForHexadecimalBoundaries_PopulatesSymbolTableWithCorrectSubtype() throws Exception {
        String sourceCode = "VAR\n"
                // Hex max values
                + "    hByte : BYTE  := 16#" + repeatChar('F', 2) + ";\n"
                + "    hWord : WORD  := 16#" + repeatChar('F', 4) + ";\n"
                + "    hDWord: DWORD := 16#" + repeatChar('F', 8) + ";\n"
                + "    hLWord: LWORD := 16#" + repeatChar('F', 16) + ";\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "16#" + repeatChar('F', 2), Subtype.BYTE, BigInteger.valueOf(255));
        assertLiteral(result, "16#" + repeatChar('F', 4), Subtype.WORD, BigInteger.valueOf(65535));
        assertLiteral(result, "16#" + repeatChar('F', 8), Subtype.DWORD, new BigInteger("4294967295"));
        assertLiteral(result, "16#" + repeatChar('F', 16), Subtype.LWORD, new BigInteger("18446744073709551615"));
    }

    @Test
    public void Lex_UnderscoresInNumericLiterals_PopulatesSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : UDINT := 1_000_000;\n"
                + "    v2 : UDINT := 4_294_967_295;\n"
                + "    v3 : REAL  := 3_141_592.65;\n"
                + "    v4 : DWORD := 16#FFFFFF_FF;\n"
                + "    v5 : WORD  := 2#1111_11111111111_1;\n"
                + "    v6 : DWORD := 8#177_777_7777;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "1000000", Subtype.UDINT, new BigInteger("1000000"));
        assertLiteral(result, "4294967295", Subtype.UDINT, new BigInteger("4294967295"));
        assertLiteral(result, "3141592.65", Subtype.REAL, 3141592.65);
        assertLiteral(result, "16#FFFFFFFF", Subtype.DWORD, new BigInteger("4294967295"));
        assertLiteral(result, "2#1111111111111111", Subtype.WORD, BigInteger.valueOf(65535));
        assertLiteral(result, "8#1777777777", Subtype.DWORD, BigInteger.valueOf(268435455));
    }

    @Test
    public void Lex_LeadingZerosInNumericLiterals_PopulatesSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : USINT := 00042;\n"
                + "    v2 : DWORD := 16#00FF;\n"
                + "    v3 : WORD  := 2#0000_1010;\n"
                + "    v4 : BYTE  := 8#0077;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "42", Subtype.USINT, BigInteger.valueOf(42));
        assertLiteral(result, "16#FF", Subtype.BYTE, BigInteger.valueOf(255));
        assertLiteral(result, "2#1010", Subtype.BYTE, BigInteger.valueOf(10));
        assertLiteral(result, "8#77", Subtype.BYTE, BigInteger.valueOf(63));
    }

    @Test
    public void Lex_PlusSignVsNoSign_DifferentSubtypesInSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : SINT := +121;\n"
                + "    v2 : USINT := 121;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "+121", Subtype.SINT, 121L);
        assertLiteral(result, "121", Subtype.USINT, BigInteger.valueOf(121));
    }

    @Test
    public void Lex_MaxULINT_WithPlusSign_OverflowsInSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : LINT := +18446744073709551615;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);
        assertLiteral(result, "9223372036854775807", Subtype.LINT, 9223372036854775807L);
    }

    @Test
    public void Lex_MaxULINT_WithoutPlusSign_ValidInSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : ULINT := 18446744073709551615;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);
        assertLiteral(result, "18446744073709551615", Subtype.ULINT, new BigInteger("18446744073709551615"));
    }

    @Test
    public void Lex_OutOfRangeNaturals_StoresFallbackInSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : ULINT := 18446744073709551616;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "18446744073709551615", Subtype.ULINT, new BigInteger("18446744073709551615"));
    }

    @Test
    public void Lex_OutOfRangeIntegers_StoresFallbackInSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : LINT := +9223372036854775808;\n"
                + "    v2 : LINT := -9223372036854775809;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "9223372036854775807", Subtype.LINT, 9223372036854775807L);
        assertLiteral(result, "-9223372036854775808", Subtype.LINT, -9223372036854775808L);
    }

    @Test
    public void Lex_OutOfRangeBinary_StoresFallbackInSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : LWORD := 2#" + repeatChar('1', 65) + ";\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "18446744073709551615", Subtype.LWORD, new BigInteger("18446744073709551615"));
    }

    @Test
    public void Lex_OutOfRangeOctal_StoresFallbackInSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : LWORD := 8#2" + repeatChar('0', 21) + ";\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "18446744073709551615", Subtype.LWORD, new BigInteger("18446744073709551615"));
    }

    @Test
    public void Lex_OutOfRangeHexadecimal_StoresFallbackInSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : LWORD := 16#" + repeatChar('F', 17) + ";\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "18446744073709551615", Subtype.LWORD, new BigInteger("18446744073709551615"));
    }

    @Test
    public void Lex_OutOfRangeReals_StoresFallbackInSymbolTable() throws Exception {
        String sourceCode = "VAR\n"
                + "    v1 : LREAL := 1.0E400;\n"
                + "    v2 : LREAL := -1.0E400;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        String maxDouble = String.valueOf(Double.MAX_VALUE);
        assertLiteral(result, maxDouble, Subtype.LREAL, Double.MAX_VALUE);
        String minDouble = String.valueOf(-Double.MAX_VALUE);
        assertLiteral(result, minDouble, Subtype.LREAL, -Double.MAX_VALUE);
    }

    @Disabled("Default analyzer not yet implemented for STRING/WSTRING")
    @Test
    public void Lex_StringLiterals_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "VAR\n"
                + "    statusMsg1 : STRING  := 'System OK';\n"
                + "    statusMsg2 : WSTRING := \"System OK\";\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "'System OK'", Subtype.STRING, "'System OK'");
        assertLiteral(result, "\"System OK\"", Subtype.WSTRING, "\"System OK\"");
    }

    @Disabled("Default analyzer not yet implemented for BOOL")
    @Test
    public void Lex_BooleanLiterals_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "VAR\n"
                + "    isActive  : BOOL := TRUE;\n"
                + "    isError   : BOOL := FALSE;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "TRUE", Subtype.BOOL, true);
        assertLiteral(result, "FALSE", Subtype.BOOL, false);
    }

    @Disabled("Default analyzer not yet implemented for TIME/DATE types")
    @Test
    public void Lex_TimeAndDateLiterals_PopulatesSymbolTableCorrectly() throws Exception {
        String sourceCode = "VAR\n"
                + "    delayTime : TIME := 150ms;\n"
                + "    maxWait   : TIME := 1d_12h;\n"
                + "    startDate : DATE := 2026-07-03;\n"
                + "    logTime   : TIME_OF_DAY := 14:30:00.0;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        assertLiteral(result, "150ms", Subtype.TIME, "150ms");
        assertLiteral(result, "1d_12h", Subtype.TIME, "1d_12h");
        assertLiteral(result, "2026-07-03", Subtype.DATE, "2026-07-03");
        assertLiteral(result, "14:30:00.0", Subtype.TIME_OF_DAY, "14:30:00.0");
    }

    @Disabled("Identifiers analyzer not yet fully implemented")
    @Test
    public void Lex_Identifiers_PopulatesSymbolTableWithUnknownUse() throws Exception {
        String sourceCode = "VAR\n"
                + "    Sensor_Temp : REAL;\n"
                + "END_VAR\n";

        SymbolTable result = executeLexer(sourceCode);

        LexemeInfo sensorInfo = result.get("Sensor_Temp");
        assertNotNull(sensorInfo, "The identifier 'Sensor_Temp' should exist.");
        assertAll("Identifier Properties",
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
