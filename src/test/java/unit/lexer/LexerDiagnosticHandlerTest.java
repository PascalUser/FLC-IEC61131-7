package unit.lexer;

import lexer.Lexer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.diagnostics.BinaryOutOfRange;
import utils.diagnostics.DateAndTimeOutOfRange;
import utils.diagnostics.DateOutOfRange;
import utils.diagnostics.HexadecimalOutOfRange;
import utils.diagnostics.IntegerOutOfRange;
import utils.diagnostics.IntervalConstructionError;
import utils.diagnostics.IntervalOutOfRange;
import utils.diagnostics.NaturalOutOfRange;
import utils.diagnostics.OctalOutOfRange;
import utils.diagnostics.RealOutOfRange;
import utils.diagnostics.TimeOfDayOutOfRange;
import utils.diagnostics.Warning;
import utils.DiagnosticsHandler;
import utils.SymbolTable;
import utils.diagnostics.Diagnostic;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Integration tests for lexer diagnostic handling.
 * <p>
 * Tests that the lexer correctly handles out-of-range literals,
 * invalid string lengths, and boundary conditions.
 * <p>
 * Numeric literals (natural, integer, real, based) emit {@link utils.diagnostics.Warning}
 * and use fallback values. Date/time/interval literals emit {@link utils.diagnostics.Error}
 * and halt token recognition for the invalid lexeme.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
@Tag("lexer")
@ExtendWith(MockitoExtension.class)
public class LexerDiagnosticHandlerTest {
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

    @Mock
    DiagnosticsHandler mockDiagnostics;

    @Captor
    ArgumentCaptor<Diagnostic> diagnosticCaptor;


    @Test
    void Yylex_ForPlusMaxULINT_RecordsDiagnosticAndUsesFallback() {
        String invalidLiteral = "+18446744073709551615";

        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());

        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(IntegerOutOfRange.class, recorded);
        assertInstanceOf(Warning.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "18446744073709551616",
            "999999999999999999999999"
    })
    void Yylex_ForOutOfRangeNaturals_RecordsDiagnosticAndUsesFallback(String invalidLiteral) {
        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());

        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(NaturalOutOfRange.class, recorded);
        assertInstanceOf(Warning.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "+9223372036854775808",
            "-9223372036854775809"
    })
    void Yylex_ForOutOfRangeIntegers_RecordsDiagnosticAndUsesFallback(String invalidLiteral) {
        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());

        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(IntegerOutOfRange.class, recorded);
        assertInstanceOf(Warning.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @Test
    void Yylex_ForOutOfRangeBinary_RecordsDiagnosticAndUsesFallback() {
        String invalidLiteral = "2#1" + repeatChar('0', 64);

        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());

        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(BinaryOutOfRange.class, recorded);
        assertInstanceOf(Warning.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @Test
    void Yylex_ForOutOfRangeOctal_RecordsDiagnosticAndUsesFallback() {
        String invalidLiteral = "8#2"+ repeatChar('0', 21);

        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());

        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(OctalOutOfRange.class, recorded);
        assertInstanceOf(Warning.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @Test
    void Yylex_ForOutOfRangeHexadecimal_RecordsDiagnosticAndUsesFallback() {
        String invalidLiteral = "16#1"+ repeatChar('0', 16);

        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());

        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(HexadecimalOutOfRange.class, recorded);
        assertInstanceOf(Warning.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1.0E400",
            "-1.0E400"
    })
    void Yylex_ForOutOfRangeReals_RecordsDiagnosticAndUsesFallback(String invalidLiteral) {
        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());

        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(RealOutOfRange.class, recorded);
        assertInstanceOf(Warning.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2023-02-29",
            "2026-13-01",
            "2026-04-31",
            "2026-00-15",
            "2026-06-00"
    })
    void Yylex_ForInvalidDate_RecordsDiagnostic(String invalidLiteral) {
        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());
        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(DateOutOfRange.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "25:00:00",
            "12:60:00",
            "12:30:60",
            "24:00:00",
            "12:59:60"
    })
    void Yylex_ForInvalidTimeOfDay_RecordsDiagnostic(String invalidLiteral) {
        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());
        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(TimeOfDayOutOfRange.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @Test
    void Yylex_ForInvalidDateAndTime_RecordsDiagnostic() {
        String invalidLiteral = "2026-07-03-25:00:00";
        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());
        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(DateAndTimeOutOfRange.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1d_25h",
            "2h_65m",
            "3m_65s",
            "5s_1500ms",
            "1d_24h",
            "10h_60m"
    })
    void Yylex_ForIntervalConstructionError_RecordsDiagnostic(String invalidLiteral) {
        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());
        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(IntervalConstructionError.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral.toUpperCase().replaceAll("_", "")));
    }

    @ParameterizedTest(name = "{index}: Yylex(\"{0}\") exceeds max duration range")
    @ValueSource(strings = {
            "106752d",
            "2562048h",
            "153722868m",
            "9223372037s",
            "9223372036855ms",
            "106751d23h47m16s855ms"
    })
    void Yylex_ForIntervalOutOfRange_PositiveOverflow_RecordsDiagnostic(String invalidLiteral) {
        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());
        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(IntervalOutOfRange.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral.toUpperCase()));
    }

    @ParameterizedTest(name = "{index}: Yylex(\"{0}\") exceeds max duration range")
    @ValueSource(strings = {
            "-106752d",
            "-2562048h",
            "-153722868m",
            "-9223372037s",
            "-9223372036855ms",
            "-106751d23h47m16s855ms"
    })
    void Yylex_ForIntervalOutOfRange_NegativeOverflow_RecordsDiagnostic(String invalidLiteral) {
        SymbolTable symbolTable = new SymbolTable();
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, symbolTable, mockDiagnostics);

        assertDoesNotThrow(() -> {
            lexer.yylex();
        });

        verify(mockDiagnostics).add(diagnosticCaptor.capture());
        Diagnostic recorded = diagnosticCaptor.getValue();
        assertInstanceOf(IntervalOutOfRange.class, recorded);
        assertTrue(recorded.getMessage().contains(invalidLiteral.toUpperCase()));
    }
}
