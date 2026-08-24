package integration;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.DiagnosticsHandler;
import utils.SymbolTable;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for lexer error handling.
 * <p>
 * Tests that the lexer correctly reports errors for out-of-range literals,
 * invalid string lengths, and boundary conditions.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class LexerErrorHandlerIT {

    @Test
    void Yylex_ForStringLiteralExceedingMaxLength_ThrowsIllegalArgumentException() {
        // Generamos un string de 256 caracteres ('A') + 2 comillas = 258 caracteres totales
        String longBody = repeatChar('A', 256);
        String invalidStringLiteral = "'" + longBody + "'";

        Reader reader = new StringReader(invalidStringLiteral);
        Lexer lexer = new Lexer(reader, new SymbolTable(), new DiagnosticsHandler());

        assertThrows(IllegalArgumentException.class, lexer::yylex);
    }

    @Test
    void Yylex_ForStringLiteralAtMaxLengthBoundary_IsFullyMatched() {
        // Generamos un string en el límite exacto del estándar: 255 caracteres útiles
        String standardBody = repeatChar('A', 255);
        String validStringLiteral = "'" + standardBody + "'";

        Reader reader = new StringReader(validStringLiteral);
        Lexer lexer = new Lexer(reader, new SymbolTable(), new DiagnosticsHandler());

        assertDoesNotThrow(() -> {
            int token = lexer.yylex();
            assertEquals(Lexer.STRING_LITERAL, token);
        });
    }

    private String repeatChar(char c, int count) {
        char[] chars = new char[count];
        for (int i = 0; i < count; i++) {
            chars[i] = c;
        }
        return new String(chars);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "9223372036854775808",          // Long.MAX_VALUE + 1
            "-9223372036854775809",         // Long.MIN_VALUE - 1
            "999999999999999999999999",     // Super overflow decimal
            "16#10000000000000000",         // Overflow Hexadecimal (> 64 bits)
            "2#10000000000000000000000000000000000000000000000000000000000000000" // Binario > 64 bits
    })
    void Yylex_ForNumericLiteralOutOfRange_ThrowsArithmeticException(String invalidLiteral) {
        Reader reader = new StringReader(invalidLiteral);
        Lexer lexer = new Lexer(reader, new SymbolTable(), new DiagnosticsHandler());
        // Verificamos que el lexer falle controladamente debido al rango numérico
        assertThrows(ArithmeticException.class, lexer::yylex);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "9223372036854775807",          // Long.MAX_VALUE (Límite superior exacto)
            "-9223372036854775808",         // Long.MIN_VALUE (Límite inferior exacto)
            "16#FFFF_FFFF_FFFF_FFFF"        // Máximo valor representable en 64 bits sin signo / con signo según se interprete
    })
    void Yylex_ForNumericLiteralAtExactBoundaries_IsFullyMatched(String extremeLiteral) {
        Reader reader = new StringReader(extremeLiteral);
        Lexer lexer = new Lexer(reader, new SymbolTable(), new DiagnosticsHandler());
        // No debería lanzar excepción, está justo en el borde permitido
        assertDoesNotThrow(() -> {
            int token = lexer.yylex();
            assertEquals(Lexer.NUMERIC_LITERAL, token);
        });
    }
}
