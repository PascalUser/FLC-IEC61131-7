package integration;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.SymbolTable;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class LexerErrorHandlerIT {

    @Test
    void Yylex_ForStringLiteralExceedingMaxLength_ThrowsIllegalArgumentException() {
        // Generamos un string de 256 caracteres ('A') + 2 comillas = 258 caracteres totales
        String longBody = "A".repeat(256);
        String invalidStringLiteral = "'" + longBody + "'";

        Reader reader = new StringReader(invalidStringLiteral);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        assertThrows(IllegalArgumentException.class, lexer::yylex);
    }

    @Test
    void Yylex_ForStringLiteralAtMaxLengthBoundary_IsFullyMatched() {
        // Generamos un string en el límite exacto del estándar: 255 caracteres útiles
        String standardBody = "A".repeat(255);
        String validStringLiteral = "'" + standardBody + "'";

        Reader reader = new StringReader(validStringLiteral);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        assertDoesNotThrow(() -> {
            int token = lexer.yylex();
            assertEquals(Lexer.STRING_LITERAL, token);
        });
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
        Lexer lexer = new Lexer(reader, new SymbolTable());
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
        Lexer lexer = new Lexer(reader, new SymbolTable());
        // No debería lanzar excepción, está justo en el borde permitido
        assertDoesNotThrow(() -> {
            int token = lexer.yylex();
            assertEquals(Lexer.NUMERIC_LITERAL, token);
        });
    }
}
