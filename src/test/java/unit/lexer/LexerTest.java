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
    @ValueSource(strings = {"True", "TrUe", "tRUE"})
    void Yylex_ForMixedCaseTrueWord_IsTrue(String trueWord) throws IOException {
        Reader reader = new StringReader(trueWord);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        this.assertNextToken(lexer, Lexer.TRUE, trueWord);
    }

    @ParameterizedTest
    @ValueSource(strings = {"False", "FaLsE", "fALSE"})
    void Yylex_ForMixedCaseFalseWord_IsFalse(String falseWord) throws IOException {
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
            "'$$'",
            "'hola mundo'",
            "'abc$0a'",
            "'Don$'t'",
            "'texto \" entre comillas'",
            "'linea$nueva'",
    })
    void Yylex_ForValidSingleByteStringLiterals_IsStringLiteral(String literal) throws IOException {
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        assertNextToken(lexer, Lexer.STRING_LITERAL, literal);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "\"\"",
            "\"hola\"",
            "\"$$\"",
            "\"$0041\"",
            "\"He said $\"hello$\"\"",
            "\"texto ' simple\"",
            "\"linea$nueva\""
    })
    void Yylex_ForValidDoubleByteStringLiterals_IsStringLiteral(String literal) throws IOException {
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        assertNextToken(lexer, Lexer.STRING_LITERAL, literal);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "'test $0'",
            "'$'",
            "'$\"'",
            "'$Z'",
            "'$x'",
            "'$0g'",
            "'$$$'"
    })
    void Yylex_ForInvalidSingleByteString_StringNotRecognised(String literal) throws IOException{
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        int token = lexer.yylex();
        assertNotEquals(Lexer.STRING_LITERAL,token);
        assertEquals('\'', token);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "\"$123\"",
            "\"$\"",
            "\"$'\"",
            "\"$Z\"",
            "\"$x\"",
            "\"$123u\"",
            "\"$$$\"",
    })
    void Yylex_ForInvalidDoubleByteString_StringNotRecognised(String literal) throws IOException{
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        int token = lexer.yylex();
        assertNotEquals(Lexer.STRING_LITERAL,token);
        assertEquals('\"', token);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0",
            "123",
            "+123",
            "-123",
            "1_000",
            "123_456_789",
            "+1_000",
            "-1_000",
            "+0",
            "-0",
            "007"
    })
    void Yylex_ForValidIntegerLiteral_IsNumericLiteral(String literal) throws IOException {
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, literal);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1__000",
            "1000_",
    })
    void Yylex_ForIntegerLiteralWithInvalidUnderscorePlacement_IsNotFullyMatched(String literal) throws IOException {
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        int token = lexer.yylex();
        assertEquals(Lexer.NUMERIC_LITERAL, token);
        assertNotEquals(literal, lexer.yytext());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0.0",
            "3.14",
            "+3.14",
            "-3.14",
            "1_000.5",
            "1.0E10",
            "1.0e10",
            "1.0E+10",
            "1.0E-10",
            "3.14e-2",
            "1_000.0E+5",
            "1.E10",
            "-1.E-10",
            "1.0E1_000",
            "+0.0",
            "-0.0",
            "007.5",
            "123.456_789"
    })
    void Yylex_ForValidRealLiteral_IsNumericLiteral(String literal) throws IOException {
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, literal);
    }

    @Test
    void Yylex_ForRealLiteralWithoutDecimalDigits_IsNotARealLiteral() throws IOException {
        String text = "1.";
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "1");
        this.assertNextToken(lexer, '.', ".");
    }

    @Test
    void Yylex_ForDotWithoutIntegerPart_IsNotARealLiteral() throws IOException {
        String text = ".5";
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        this.assertNextToken(lexer, '.', ".");
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, "5");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1.0E",
            "1.E",
            "1.E+",
            "1.E-",
            "1.0E+",
            "1.0E-"
    })
    void Yylex_ForRealLiteralWithIncompleteExponent_ExponentIsNotConsumed(String realLiteral) throws IOException {
        Reader reader = new StringReader(realLiteral);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        int token = lexer.yylex();
        assertEquals(Lexer.NUMERIC_LITERAL, token);
        assertNotEquals(realLiteral, lexer.yytext());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2#0",
            "2#1010",
            "2#1111_0000",
            "8#17",
            "8#777",
            "16#1F",
            "16#ff",
            "16#DEAD_BEEF",
    })
    void Yylex_ForValidBasedIntegerLiteral_IsNumericLiteral(String literal) throws IOException {
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        this.assertNextToken(lexer, Lexer.NUMERIC_LITERAL, literal);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2#12",
            "2#1012",
            "8#8",
            "8#89",
            "16#G",
            "16#1G",
    })
    void Yylex_ForBasedLiteralWithDigitInvalidForBase_IsNotFullyMatched(String literal) throws IOException {
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        int token = lexer.yylex();
        assertEquals(Lexer.NUMERIC_LITERAL, token);
        assertNotEquals(literal, lexer.yytext());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "16#_FF",
            "16#FF_",
            "16#F__F",
            "2#1__0"
    })
    void Yylex_ForBasedLiteralWithInvalidUnderscorePlacement_IsNotFullyMatched(String literal) throws IOException {
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        int token = lexer.yylex();
        assertEquals(Lexer.NUMERIC_LITERAL, token);
        assertNotEquals(literal, lexer.yytext());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1#0",
            "9#0",
            "3#12",
    })
    void Yylex_ForUnsupportedBase_IsNotABasedLiteral(String literal) throws IOException {
        Reader reader = new StringReader(literal);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        lexer.yylex();
        assertNotEquals(literal, lexer.yytext());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0001-01-01-00:00:00",
            "00:00:00",
            "0001-01-01",
            "00-00-1111",
            "1100-00-00",
            "0S",
            "5d14h12m18s3.5ms",
            "5d_14h_12m_18s_3.5ms",
            "2026-07-03",
            "14:30:00",
            "14:30:45.5",
            "2026-07-03-14:30:00.0",
            "5ms",
            "10.5s",
            "-12h",
            "1d_2h",
            "10m_30.5s",
            "0d"
    })
    void Yylex_ForValidTimeLiteral_IsTimeLiteral(String timeLiteral) throws IOException {
        Reader reader = new StringReader(timeLiteral);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        this.assertNextToken(lexer, Lexer.TIME_LITERAL, timeLiteral);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "00:00",
            "00-01",
            "5x14h",
            "d14h",
    })
    void Yylex_ForInvalidTimeLiteral_IsNotATimeLiteral(String text) throws IOException {
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        assertNotEquals(Lexer.TIME_LITERAL, lexer.yylex());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "14h5d",
            "5m13h"
    })
    void Yylex_ForTimeLiteralWithIncorrectOrder_AreMultipleTimeLiterals(String text) throws IOException {
        Reader reader = new StringReader(text);
        Lexer lexer = new Lexer(reader, new SymbolTable());
        assertEquals(Lexer.TIME_LITERAL, lexer.yylex());
        assertEquals(Lexer.TIME_LITERAL, lexer.yylex());
    }
}