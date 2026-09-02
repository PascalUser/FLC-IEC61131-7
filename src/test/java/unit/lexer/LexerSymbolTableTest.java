package unit.lexer;

import lexer.Lexer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.DiagnosticsHandler;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.*;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Lexer} interaction with {@link SymbolTable}.
 * <p>
 * Verifies that scanning individual literals populates the symbol table
 * with correct semantic type, subtype, and initial values.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 2.0
 * @since 1.0
 */
@Tag("lexer")
public class LexerSymbolTableTest {

    private SymbolTable executeLexer(String literal) throws IOException {
        SymbolTable st = new SymbolTable();
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, st, new DiagnosticsHandler());
        lexer.yylex();
        return st;
    }

    private void assertLiteral(SymbolTable st, String lexeme, Subtype expectedSubtype, Object expectedValue) {
        LexemeInfo info = st.get(lexeme);
        assertNotNull(info, () -> "The literal '" + lexeme + "' should exist in the symbol table.");
        assertAll("LexemeInfo properties for '" + lexeme + "'",
                () -> assertEquals(Type.SIMPLE, info.type, "Main type is incorrect"),
                () -> assertEquals(expectedSubtype, info.subtype, "Subtype is incorrect"),
                () -> assertNull(info.customType, "customType should be null"),
                () -> assertEquals(Use.LITERAL, info.use, "Use should be LITERAL"),
                () -> assertEquals(Source.UNKNOWN, info.source, "Source should be UNKNOWN"),
                () -> assertNull(info.inferiorLimit, "inferiorLimit should be null"),
                () -> assertNull(info.superiorLimit, "superiorLimit should be null"),
                () -> assertNull(info.parameters, "parameters should be null"),
                () -> assertEquals(expectedValue, info.initialValue, "Initial value does not match")
        );
    }

    private static String repeatChar(char c, int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, c);
        return new String(chars);
    }

    // =========================================================================
    // NUMERIC LITERALS (PARAMETERIZED)
    // =========================================================================

    private static Stream<Arguments> provideSignedIntegers() {
        return Stream.of(
                Arguments.of("-128", Subtype.SINT, -128L),
                Arguments.of("-1", Subtype.SINT, -1L),
                Arguments.of("+127", Subtype.SINT, 127L),
                Arguments.of("-32768", Subtype.INT, -32768L),
                Arguments.of("+32767", Subtype.INT, 32767L),
                Arguments.of("-32769", Subtype.DINT, -32769L),
                Arguments.of("-2147483648", Subtype.DINT, -2147483648L),
                Arguments.of("+2147483647", Subtype.DINT, 2147483647L),
                Arguments.of("-2147483649", Subtype.LINT, -2147483649L),
                Arguments.of("-9223372036854775808", Subtype.LINT, -9223372036854775808L),
                Arguments.of("+9223372036854775807", Subtype.LINT, 9223372036854775807L)
        );
    }

    @ParameterizedTest(name = "{index}: Lex(\"{0}\") -> {1} ({2})")
    @MethodSource("provideSignedIntegers")
    void Yylex_SignedIntegerBoundaries_PopulatesSymbolTable(
            String input, Subtype expectedSubtype, Object expectedValue)
    throws Exception {
        SymbolTable result = executeLexer(input);
        assertLiteral(result, input, expectedSubtype, expectedValue);
    }

    private static Stream<Arguments> provideUnsignedIntegers() {
        return Stream.of(
                Arguments.of("0", Subtype.USINT, BigInteger.valueOf(0)),
                Arguments.of("121", Subtype.USINT, BigInteger.valueOf(121)),
                Arguments.of("255", Subtype.USINT, BigInteger.valueOf(255)),
                Arguments.of("65535", Subtype.UINT, BigInteger.valueOf(65535)),
                Arguments.of("4294967295", Subtype.UDINT, new BigInteger("4294967295")),
                Arguments.of("18446744073709551615", Subtype.ULINT, new BigInteger("18446744073709551615"))
        );
    }

    @ParameterizedTest(name = "{index}: Lex(\"{0}\") -> {1} ({2})")
    @MethodSource("provideUnsignedIntegers")
    void Yylex_UnsignedIntegerBoundaries_PopulatesSymbolTable(
            String input, Subtype expectedSubtype, Object expectedValue
    ) throws Exception {
        SymbolTable result = executeLexer(input);
        assertLiteral(result, input, expectedSubtype, expectedValue);
    }

    private static Stream<Arguments> provideReals() {
        return Stream.of(
                Arguments.of("1.0", Subtype.REAL, 1.0),
                Arguments.of("-1.0", Subtype.REAL, -1.0),
                Arguments.of("100.0", Subtype.REAL, 100.0),
                Arguments.of("-1.0E38", Subtype.REAL, -1.0E38),
                Arguments.of("1.0E40", Subtype.LREAL, 1E40),
                Arguments.of("-1.0E40", Subtype.LREAL, -1.0E40),
                Arguments.of(String.valueOf(Double.MAX_VALUE), Subtype.LREAL, Double.MAX_VALUE),
                Arguments.of(String.valueOf(-Double.MAX_VALUE), Subtype.LREAL, -Double.MAX_VALUE)
        );
    }

    @ParameterizedTest(name = "{index}: Lex(\"{0}\") -> {1}")
    @MethodSource("provideReals")
    void Yylex_RealBoundaries_PopulatesSymbolTable(String input, Subtype expectedSubtype, Object expectedValue)
            throws Exception {
        SymbolTable result = executeLexer(input);
        assertLiteral(result, input, expectedSubtype, expectedValue);
    }

    private static Stream<Arguments> provideBaseNLiterals() {
        return Stream.of(
                // Binary
                Arguments.of("2#" + repeatChar('1', 8), Subtype.BYTE, BigInteger.valueOf(255)),
                Arguments.of("2#" + repeatChar('1', 16), Subtype.WORD, BigInteger.valueOf(65535)),
                Arguments.of("2#" + repeatChar('1', 32), Subtype.DWORD, new BigInteger("4294967295")),
                Arguments.of("2#" + repeatChar('1', 64), Subtype.LWORD, new BigInteger("18446744073709551615")),
                // Octal
                Arguments.of("8#3" + repeatChar('7', 2), Subtype.BYTE, BigInteger.valueOf(255)),
                Arguments.of("8#1" + repeatChar('7', 5), Subtype.WORD, BigInteger.valueOf(65535)),
                Arguments.of("8#3" + repeatChar('7', 10), Subtype.DWORD, new BigInteger("4294967295")),
                Arguments.of("8#1" + repeatChar('7', 21), Subtype.LWORD, new BigInteger("18446744073709551615")),
                // Hexadecimal
                Arguments.of("16#" + repeatChar('F', 2), Subtype.BYTE, BigInteger.valueOf(255)),
                Arguments.of("16#" + repeatChar('F', 4), Subtype.WORD, BigInteger.valueOf(65535)),
                Arguments.of("16#" + repeatChar('F', 8), Subtype.DWORD, new BigInteger("4294967295")),
                Arguments.of("16#" + repeatChar('F', 16), Subtype.LWORD, new BigInteger("18446744073709551615"))
        );
    }

    @ParameterizedTest(name = "{index}: Lex(\"{0}\") -> {1}")
    @MethodSource("provideBaseNLiterals")
    void Yylex_BaseNBoundaries_PopulatesSymbolTable(String input, Subtype expectedSubtype, Object expectedValue)
            throws Exception {
        SymbolTable result = executeLexer(input);
        assertLiteral(result, input, expectedSubtype, expectedValue);
    }

    // =========================================================================
    // TEMPORAL & DATE LITERALS (PARAMETERIZED)
    // =========================================================================

    private static Stream<Arguments> provideDateAndTimeLiterals() {
        return Stream.of(
                Arguments.of("2026-07-03", Subtype.DATE, LocalDate.of(2026, 7, 3)),
                Arguments.of("2024-02-29", Subtype.DATE, LocalDate.of(2024, 2, 29)),
                Arguments.of("14:30:00.0", Subtype.TIME_OF_DAY, LocalTime.of(14, 30, 0)),
                Arguments.of("23:59:59.123456789", Subtype.TIME_OF_DAY, LocalTime.of(23, 59, 59, 123456789)),
                Arguments.of("2026-07-03-14:30:00.0", Subtype.DATE_AND_TIME, LocalDateTime.of(2026, 7, 3, 14, 30, 0))
        );
    }

    @ParameterizedTest(name = "{index}: Lex(\"{0}\") -> {1}")
    @MethodSource("provideDateAndTimeLiterals")
    void Yylex_DateAndTimeLiterals_PopulatesSymbolTable(String input, Subtype expectedSubtype, Object expectedValue)
            throws Exception {
        SymbolTable result = executeLexer(input);

        LexemeInfo info = result.get(input);
        assertNotNull(info);
        assertAll("Date/Time Properties",
                () -> assertEquals(Type.SIMPLE, info.type),
                () -> assertEquals(expectedSubtype, info.subtype),
                () -> assertEquals(Use.LITERAL, info.use),
                () -> assertEquals(expectedValue, info.initialValue)
        );
    }

    private static Stream<Arguments> provideIntervalLiterals() {
        return Stream.of(
                Arguments.of("500MS", "500MS", Duration.ofMillis(500)),
                Arguments.of("1d_2h_30m", "1D2H30M", Duration.ofDays(1).plusHours(2).plusMinutes(30)),
                Arguments.of("25h_30m", "25H30M", Duration.ofHours(25).plusMinutes(30)),
                Arguments.of("-12h_30m", "-12H30M", Duration.ofHours(-12).minusMinutes(30)),
                // 1_000_000 NS == 1 MS so 500_000 NS == 0.5 MS
                Arguments.of("5d_14h_12m_18s_3.500ms", "5D14H12M18S3.5MS",
                        Duration.ofDays(5)
                                .plusHours(14)
                                .plusMinutes(12)
                                .plusSeconds(18)
                                .plusMillis(3)
                                .plusNanos(500_000))
        );
    }

    @ParameterizedTest(name = "{index}: Lex(\"{0}\") -> Stored as \"{1}\"")
    @MethodSource("provideIntervalLiterals")
    void Yylex_IntervalLiterals_PopulatesSymbolTable(String input, String expectedLexemeKey, Duration expectedValue)
            throws Exception {
        SymbolTable result = executeLexer(input);

        LexemeInfo info = result.get(expectedLexemeKey);
        assertNotNull(info, () -> "Expected key '" + expectedLexemeKey + "' in symbol table.");
        assertAll("TIME Literal Properties",
                () -> assertEquals(Type.SIMPLE, info.type),
                () -> assertEquals(Subtype.TIME, info.subtype),
                () -> assertEquals(Use.LITERAL, info.use),
                () -> assertEquals(expectedValue, info.initialValue)
        );
    }

    // =========================================================================
    // FALLBACKS & CORNER CASES
    // =========================================================================

    private static Stream<Arguments> provideOverflowFallbacks() {
        return Stream.of(
                Arguments.of("+18446744073709551615", "9223372036854775807", Subtype.LINT, 9223372036854775807L),
                Arguments.of("+9223372036854775808", "9223372036854775807",  Subtype.LINT, 9223372036854775807L),
                Arguments.of("-9223372036854775809", "-9223372036854775808", Subtype.LINT, -9223372036854775808L),
                Arguments.of("1.0E400", String.valueOf(Double.MAX_VALUE),   Subtype.LREAL, Double.MAX_VALUE),
                Arguments.of("-1.0E400", String.valueOf(-Double.MAX_VALUE), Subtype.LREAL, -Double.MAX_VALUE),
                Arguments.of("18446744073709551616", "18446744073709551615", Subtype.ULINT,
                        new BigInteger("18446744073709551615")
                ),
                Arguments.of("2#" + repeatChar('1', 65), "18446744073709551615", Subtype.LWORD,
                        new BigInteger("18446744073709551615")
                ),
                Arguments.of("8#2" + repeatChar('0', 21), "18446744073709551615", Subtype.LWORD,
                        new BigInteger("18446744073709551615")
                ),
                Arguments.of("16#" + repeatChar('F', 17), "18446744073709551615", Subtype.LWORD,
                        new BigInteger("18446744073709551615")
                )
        );
    }

    @ParameterizedTest(name = "{index}: Overflow Lex(\"{0}\") -> Stores fallback key \"{1}\"")
    @MethodSource("provideOverflowFallbacks")
    void Yylex_OutOfRangeLiterals_StoresFallbackInSymbolTable(
            String input, String expectedKey, Subtype expectedSubtype, Object expectedValue
    ) throws Exception {
        SymbolTable result = executeLexer(input);
        assertLiteral(result, expectedKey, expectedSubtype, expectedValue);
    }

    // =========================================================================
    // BOOLEAN LITERALS
    // =========================================================================

    @Test
    void Yylex_BooleanLiterals_PopulatesSymbolTableCorrectly() throws Exception {
        SymbolTable resultTrue = executeLexer("TRUE");
        assertLiteral(resultTrue, "TRUE", Subtype.BOOL, true);

        SymbolTable resultFalse = executeLexer("FALSE");
        assertLiteral(resultFalse, "FALSE", Subtype.BOOL, false);
    }

    // =========================================================================
    // STRING LITERAL TESTS
    // =========================================================================

    @Test
    void Yylex_StringLiterals_PopulatesSymbolTableCorrectly() throws Exception {
        SymbolTable resultSingle = executeLexer("'System OK'");
        assertLiteral(resultSingle, "'System OK'", Subtype.STRING, "System OK");

        SymbolTable resultDouble = executeLexer("\"System OK\"");
        assertLiteral(resultDouble, "\"System OK\"", Subtype.WSTRING, "System OK");
    }

    @Test
    void Yylex_StringLiterals_EquivalenceThroughEscapes() throws Exception {
        // 'hello' and 'he$6c$6co' should map to the same symbol table entry
        SymbolTable result1 = executeLexer("'hello'");
        SymbolTable result2 = executeLexer("'he$6c$6co'");

        LexemeInfo info1 = result1.get("'hello'");
        LexemeInfo info2 = result2.get("'he$6c$6co'");

        assertNotNull(info1);
        assertNull(info2);
        assertEquals("hello", info1.initialValue);
    }

    @Test
    void Yylex_WStringLiterals_EquivalenceThroughHexEscapes() throws Exception {
        // "world" and "$0077$006F$0072$006C$0064" should map to the same entry
        SymbolTable result1 = executeLexer("\"world\"");
        SymbolTable result2 = executeLexer("\"$0077$006F$0072$006C$0064\"");

        LexemeInfo info1 = result1.get("\"world\"");
        LexemeInfo info2 = result2.get("\"$0077$006F$0072$006C$0064\"");

        assertNotNull(info1);
        assertNull(info2);
        assertEquals("world", info1.initialValue);
    }

    @Test
    void Yylex_StringAndWStringAreSeparateEntries() throws Exception {
        // 'test' (STRING) and "test" (WSTRING) should be DIFFERENT entries
        SymbolTable result1 = executeLexer("'test'");
        SymbolTable result2 = executeLexer("\"test\"");

        LexemeInfo info1 = result1.get("'test'");
        LexemeInfo info2 = result2.get("\"test\"");

        assertNotNull(info1);
        assertNotNull(info2);
        assertNotSame(info1, info2, "STRING and WSTRING should be separate entries");
        assertEquals(Subtype.STRING, info1.subtype);
        assertEquals(Subtype.WSTRING, info2.subtype);
    }

    @Test
    void Yylex_StringLiterals_WithComplexEscapes() throws Exception {
        SymbolTable result = executeLexer("'It$'s $41$62$63'");

        LexemeInfo info = result.get("'It's Abc'");
        assertNotNull(info);
        assertEquals("It's Abc", info.initialValue);
        assertEquals(Subtype.STRING, info.subtype);
    }

    @Test
    void Yylex_WStringLiterals_WithUnicodeHex() throws Exception {
        SymbolTable result = executeLexer("\"$0041$0062$0063$00E9\"");

        LexemeInfo info = result.get("\"Abcé\"");
        assertNotNull(info);
        assertEquals("Abcé", info.initialValue);
        assertEquals(Subtype.WSTRING, info.subtype);
    }

    @Test
    void Yylex_StringLiterals_ExceedingMaxLength_TruncatedWithWarning() throws Exception {
        String longContent = repeatChar('A', 300);
        String sourceCode = "'" + longContent + "'";

        SymbolTable result = executeLexer(sourceCode);

        LexemeInfo info = result.get("'" + repeatChar('A', 255) + "'");
        assertNotNull(info);
        assertEquals(255, ((String) info.initialValue).length());
    }

    @Test
    void Yylex_WStringLiterals_ExceedingMaxLength_TruncatedWithWarning() throws Exception {
        String longContent = repeatChar('B', 300);
        String sourceCode = "\"" + longContent + "\"";

        SymbolTable result = executeLexer(sourceCode);

        LexemeInfo info = result.get("\"" + repeatChar('B', 255) + "\"");
        assertNotNull(info);
        assertEquals(255, ((String) info.initialValue).length());
    }

    @Test
    void Yylex_Identifiers_PopulatesSymbolTableWithUnknownUseAndUpperCaseLetters() throws Exception {
        SymbolTable result = executeLexer("Sensor_Temp");

        LexemeInfo sensorInfo = result.get("SENSOR_TEMP");
        assertNotNull(sensorInfo, "The identifier 'Sensor_Temp' should exist.");
        assertAll("Identifier Properties",
                () -> assertEquals(Type.UNKNOWN, sensorInfo.type),
                () -> assertNull(null, "customType should be null"),
                () -> assertEquals(Subtype.UNKNOWN, sensorInfo.subtype),
                () -> assertEquals(Use.UNKNOWN, sensorInfo.use),
                () -> assertEquals(Source.UNKNOWN, sensorInfo.source, "Source should be UNKNOWN"),
                () -> assertNull(sensorInfo.inferiorLimit, "inferiorLimit should be null"),
                () -> assertNull(sensorInfo.superiorLimit, "superiorLimit should be null"),
                () -> assertNull(sensorInfo.parameters, "parameters should be null"),
                () -> assertNull(sensorInfo.initialValue, "Initial value should be null")
        );
    }
    @Test
    void Yylex_IdentifiersWithDifferentCases_AreAddedCaseInsensitivelyToSymbolTable() throws Exception {
        String text = "sensor_temp SENSOR_TEMP";

        SymbolTable result = new SymbolTable();
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, result, new DiagnosticsHandler());

        for (int i = 0; i < 3; i++) {
            lexer.yylex();
        }

        LexemeInfo infoUpper = result.get("SENSOR_TEMP");
        LexemeInfo infoLower = result.get("sensor_temp");

        assertNotNull(infoUpper, "The canonical identifier 'SENSOR_TEMP' should exist in the symbol table.");
        assertAll("Case Insensitivity Checks",
                () -> assertEquals(1, result.size(),
                        "Symbol table should contain only one entry for case-equivalent identifiers"
                ),
                () -> assertNotSame(infoUpper, infoLower,
                        "Lookups with different casing should reference the exact same LexemeInfo instance"
                )
        );
    }

}
