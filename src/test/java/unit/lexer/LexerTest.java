package unit.lexer;

import lexer.Lexer;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import utils.SymbolTable;

import java.io.StringReader;
import java.io.Reader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void testLexerExample() throws IOException {
        String sourceCode = """
            FUNCTION_BLK JAJA
                    fuzzify TRUE R_EDGE
                            = : , := 1 2 +1 +1 -2 2.3 -2.3 .. 2#10 16#2 8#24
                    "hello world 'a'" 'aaa'
        """;

        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.IDENTIFIER, "FUNCTION_BLK");
        this.assertNextToken(lexer, Lexer.IDENTIFIER, "JAJA");
        this.assertNextToken(lexer, Lexer.FUZZIFY, "fuzzify");
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
        this.assertNextToken(lexer, Lexer.STRING_LITERAL, "\"hello world 'a'\"");
        this.assertNextToken(lexer, Lexer.STRING_LITERAL, "'aaa'");
        this.assertNextToken(lexer, Lexer.EOF, "");
    }

    @Test
    void testLexerFuzzifySentence() throws IOException {
        String sourceCode = """
            FUZZIFY distance
                TERM too_far := (-5, 1) (0, 0);
            END_FUZZIFY
        """;

        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, new SymbolTable());

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

    @Test
    void testLexerTimeLiterals() throws IOException {
        String sourceCode = """
            dt #0001-01-01-00:00:00
            Date_and_Time# 0001-01-01-00:00:00
            time_of_day# 00:00:00
            tod #00:00:00
            D #0001-01-01
            d# 00-00-1111
            date# 1100-00-00
            t# 0S
            time #5d14h12m18s3.5ms
            tImE#5d_14h_12m_18s_3.5ms
        """;

        Reader reader = new StringReader(sourceCode);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.DATE_AND_TIME, "dt");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "0001-01-01-00:00:00");
        this.assertNextToken(lexer, Lexer.DATE_AND_TIME, "Date_and_Time");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "0001-01-01-00:00:00");
        this.assertNextToken(lexer, Lexer.TIME_OF_DAY, "time_of_day");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "00:00:00");
        this.assertNextToken(lexer, Lexer.TIME_OF_DAY, "tod");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "00:00:00");
        this.assertNextToken(lexer, Lexer.DATE, "D");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "0001-01-01");
        this.assertNextToken(lexer, Lexer.DATE, "d");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "00-00-1111");
        this.assertNextToken(lexer, Lexer.DATE, "date");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "1100-00-00");
        this.assertNextToken(lexer, Lexer.TIME, "t");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "0S");
        this.assertNextToken(lexer, Lexer.TIME, "time");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "5d14h12m18s3.5ms");
        this.assertNextToken(lexer, Lexer.TIME, "tImE");
        this.assertNextToken(lexer, '#', "#");
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, "5d_14h_12m_18s_3.5ms");
        this.assertNextToken(lexer, Lexer.EOF, "");
    }

    private void assertNextToken(@NonNull Lexer lexer, int expectedToken, String expectedText) throws IOException {
        int token = lexer.yylex();
        assertEquals(expectedToken, token, "Token type mismatch");
        assertEquals(expectedText, lexer.yytext(), "Token text mismatch");
    }
}