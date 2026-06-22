package unit.lexer;

import lexer.Lexer;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import unit.utils.FakeReservedWords;
import utils.SymbolTable;

import java.io.StringReader;
import java.io.Reader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    private void assertNextToken(@NonNull Lexer lexer, int expectedToken, String expectedText) throws IOException {
        int token = lexer.yylex();
        assertEquals(expectedToken, token, "Token type mismatch");
        assertEquals(expectedText, lexer.yytext(), "Token text mismatch");
    }

    static Stream<Arguments> provideReservedWords() {
        FakeReservedWords reservedWords = new FakeReservedWords();
        Stream.Builder<Arguments> builder = Stream.builder();
        for (Map.Entry<String, Integer> entry : reservedWords) {
            builder.add(Arguments.of(entry.getKey(), entry.getValue()));
        }
        return builder.build();
    }

    @ParameterizedTest(name = "Testing lower case: {0} -> {1}")
    @MethodSource("provideReservedWords")
    void Yylex_ForLowerCaseReservedWord_IsReservedWord(String word, Integer token) throws IOException {
        String reservedWord = word.toLowerCase();
        Reader reader = new StringReader(reservedWord);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, token, reservedWord);
    }

    @ParameterizedTest(name = "Testing upper case: {0} -> {1}")
    @MethodSource("provideReservedWords")
    void Yylex_ForUpperCaseReservedWord_IsReservedWord(String word, Integer token) throws IOException {
        String reservedWord = word.toUpperCase();
        Reader reader = new StringReader(reservedWord);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, token, reservedWord);
    }

    @ParameterizedTest
    @ValueSource(chars = {'=', ':', ';', ',', '.', '(', ')', '[', ']', '\"', '\'', '#'})
    void Yylex_ForSpecialCharacters_IsAsciiValue(char specialChar) throws IOException {
        String stringChar = String.valueOf(specialChar);
        Reader reader = new StringReader(stringChar);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, specialChar, stringChar);
    }

    @Test
    void Yylex_ForTwoDots_IsRangeOperator() throws IOException {
        String twoDots = "..";
        Reader reader = new StringReader(twoDots);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.RANGE_OP, twoDots);
    }

    @Test
    void Yylex_ForDotEqual_IsAssignOperator() throws IOException{
        String dotEqual = ":=";
        Reader reader = new StringReader(dotEqual);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.ASSIGN_OP, dotEqual);
    }

    @ParameterizedTest
    @ValueSource(chars = {' ', '\t', '\r', '\n'})
    void Yylex_ForBlankCharacters_IsEndOfFile(char blank) throws IOException {
        String blankChar = String.valueOf(blank);
        Reader reader = new StringReader(blankChar);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.EOF, "");
    }

    @Test
    void Yylex_ForComments_IsSkipped() throws IOException{
        String comment = "(* comment *)";
        Reader reader = new StringReader(comment);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.EOF, "");
    }

    @Test
    void Yylex_ForCommentsWithSpaces_IsNotAComment() throws IOException{
        String comment = "( * * * )";
        Reader reader = new StringReader(comment);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, '(', "(");
        this.assertNextToken(lexer, ')', ")");
    }

    @ParameterizedTest
    @ValueSource(strings = {"TRUE", "true"})
    void Yylex_ForTrueWords_IsTrue(String trueWord) throws IOException {
        Reader reader = new StringReader(trueWord);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.TRUE, trueWord);
    }

    @ParameterizedTest
    @ValueSource(strings = {"FALSE", "false"})
    void Yylex_ForFalseWords_IsFalse(String falseWord) throws IOException {
        Reader reader = new StringReader(falseWord);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.FALSE, falseWord);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "variable",
        "var123",
        "camelCaseVar",
        "snake_case",
        "_privateVar",
        "_123var",
        "a",
        "Z",
        "_0",
        "_a",
        "x_1_y_2"
    })
    void Yylex_ForValidIdentifier_IsIdentifier(String identifier) throws IOException {
        Reader reader = new StringReader(identifier);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.IDENTIFIER, identifier);
    }

    @Test
    void Yylex_ForSeparatedWordsWithDoubleUnderscore_IsNotIdentifier() throws IOException {
        String text = "snake__case";
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        lexer.yylex();

        this.assertNextToken(lexer, Lexer.IDENTIFIER, "_case");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "variable-name",
        "variable name",
    })
    void Yylex_ForSeparatedWordsWithoutUnderscore_IsNotOnlyOneIdentifier(String text) throws IOException {
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.IDENTIFIER, "variable");
        this.assertNextToken(lexer, Lexer.IDENTIFIER, "name");
    }

    @Test
    void Yylex_ForSeparatedWordsWithDot_IsNotOnlyOneIdentifier() throws IOException {
        String text = "variable.name";
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.IDENTIFIER, "variable");
        this.assertNextToken(lexer, '.', ".");
        this.assertNextToken(lexer, Lexer.IDENTIFIER, "name");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "1",
        "1_",
        "1variable",
    })
    void Yylex_ForWordsThatStartsWithDigit_IsNumericLiteral(String text) throws IOException {
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "1");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "variable_",
        "_",
        "_1_"
    })
    void Yylex_ForWordsThatEndsWithUnderscore_IsNotIdentifier(String text) throws IOException {
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        lexer.yylex();

        assertFalse(lexer.yytext().endsWith("_"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "_",
        "__",
    })
    void Yylex_ForUnderscore_IsNotIdentifier(String text) throws IOException {
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.EOF, "");
    }

    @Test
    void Yylex_ForWordsThatStartsWithDoubleUnderscore_IsIdentifierWithOneUnderscore() throws IOException {
        String text = "__var";
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());

        this.assertNextToken(lexer, Lexer.IDENTIFIER, "_var");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "''",
            "'hola'",
            "'hola mundo'",
            "'abc$0a'",
            "'Don$'t'",
            "'texto \" entre comillas'",
            "'linea$nueva'",
    })
    void Yylex_ForValidSingleByteStringLiterals_IsStringLiteral(String literal) throws IOException {
        Lexer lexer = new Lexer(new StringReader(literal), new SymbolTable());
        assertNextToken(lexer, Lexer.STRING_LITERAL, literal);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "\"\"",
            "\"hola\"",
            "\"$0041\"",
            "\"He said $\"hello$\"\"",
            "\"texto ' simple\"",
            "\"linea$nueva\""
    })
    void Yylex_ForValidDoubleByteStringLiterals_IsStringLiteral(String literal) throws IOException {
        Lexer lexer = new Lexer(new StringReader(literal), new SymbolTable());
        assertNextToken(lexer, Lexer.STRING_LITERAL, literal);
    }

    //todo: Strings invalidos


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
}