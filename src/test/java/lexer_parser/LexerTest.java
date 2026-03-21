package lexer_parser;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.Reader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void testLexerExample() throws IOException {
        String codigoFuente = """
            FUNCTION_BLOK JAJA
                    FUZZIFY TRUE R_EDGE
                            = : , := 1 2 +1 +1 -2 2.3 -2.3 .. 2#10 16#2 8#24
                    "hola mundo 'a'" 'aaa'
        """;

        Reader reader = new StringReader(codigoFuente);
        Lexer lexer = new Lexer(reader);

        this.assertNextToken(lexer, Lexer.IDENTIFIER, "FUNCTION_BLOK");
        this.assertNextToken(lexer, Lexer.IDENTIFIER, "JAJA");
        this.assertNextToken(lexer, Lexer.FUZZIFY, "FUZZIFY");
        this.assertNextToken(lexer, Lexer.TRUE, "TRUE");
        this.assertNextToken(lexer, Lexer.R_EDGE, "R_EDGE");
        this.assertNextToken(lexer, '=', "=");
        this.assertNextToken(lexer, ':', ":");
        this.assertNextToken(lexer, ',', ",");
        this.assertNextToken(lexer, Lexer.ASSIGN_OP, ":=");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "1");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "2");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "+1");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "+1");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "-2");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "2.3");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "-2.3");
        this.assertNextToken(lexer, Lexer.RANGE_OP, "..");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "2#10");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "16#2");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "8#24");
        this.assertNextToken(lexer, Lexer.CHARACTER_STRING, "\"hola mundo 'a'\"");
        this.assertNextToken(lexer, Lexer.CHARACTER_STRING, "'aaa'");
        this.assertNextToken(lexer, Lexer.EOF, "");
    }

    @Test
    void testLexerFuzzifySentence() throws IOException {
        String codigoFuente = """
            FUZZIFY distance
                TERM too_far := (-5, 1) (0, 0);
            END_FUZZIFY
        """;

        Reader reader = new StringReader(codigoFuente);
        Lexer lexer = new Lexer(reader);

        this.assertNextToken(lexer, Lexer.FUZZIFY, "FUZZIFY");
        this.assertNextToken(lexer, Lexer.IDENTIFIER, "distance");
        this.assertNextToken(lexer, Lexer.TERM, "TERM");
        this.assertNextToken(lexer, Lexer.IDENTIFIER, "too_far");
        this.assertNextToken(lexer, Lexer.ASSIGN_OP, ":=");
        this.assertNextToken(lexer, '(', "(");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "-5");
        this.assertNextToken(lexer, ',', ",");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "1");
        this.assertNextToken(lexer, ')', ")");
        this.assertNextToken(lexer, '(', "(");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "0");
        this.assertNextToken(lexer, ',', ",");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "0");
        this.assertNextToken(lexer, ')', ")");
        this.assertNextToken(lexer, ';', ";");
        this.assertNextToken(lexer, Lexer.END_FUZZIFY, "END_FUZZIFY");
        this.assertNextToken(lexer, Lexer.EOF, "");
    }

    private void assertNextToken(Lexer lexer, int expectedToken, String expectedText) throws IOException {
        int token = lexer.yylex();
        assertEquals(expectedToken, token, "Token type mismatch");
        assertEquals(expectedText, lexer.yytext(), "Token text mismatch");
    }
}