package lexer;

import org.jspecify.annotations.NonNull;
import parser.Parser;
import lexer.semantics.*;
import lexer.transformers.*;
import lexer.semantics.SemanticAnalyzer.*;
import utils.SymbolTable;
import utils.DiagnosticsHandler;

%%

%class Lexer
%public
%implements Parser.Lexer
%int
%line

%eofval{
    return Parser.Lexer.YYEOF;
%eofval}

%{
    private Object yylval;
    private SymbolTable symbolTable;
    private DiagnosticsHandler diagnosticsHandler;

    public Object getLVal() {
        return this.yylval;
    }

    public void yyerror(String msg) {
        // TODO: Agregar al DiagnosticHandler un error generico.
        System.err.println("Line " + (yyline + 1) + ": " + msg);
    }

    /**
     * @param  transformer the lexeme preprocessor that transforms the lexeme before is analyzed
     * @param  analyzer    the lexeme's semantic analyzer
     * @return the lexeme's corresponded token number, or YYERROR if semantic analysis failed
     */
    public int processAndSaveYylval(@NonNull Transformer transformer, @NonNull SemanticAnalyzer analyzer) {
        String preprocessedLexeme = transformer.transform(yytext());
        LexicalContext lexicalContext = new LexicalContext(
                preprocessedLexeme,
                yyline,
                this.symbolTable,
                this.diagnosticsHandler
            );
        Result result = analyzer.analyze(lexicalContext);
        this.yylval = result.lexeme;
        return result.token;
    }
%}

%ctorarg SymbolTable symbolTable
%ctorarg DiagnosticsHandler diagnosticsHandler

%init{
    this.symbolTable = symbolTable;
    this.diagnosticsHandler = diagnosticsHandler;
%init}

COMMENT                 = \(\*.*\*\)

LETTER                  = [a-zA-Z]
DIGIT                   = [0-9]
BIT                     = [01]
OCT_DIGIT               = [0-7]
HEX_DIGIT               = [a-fA-F0-9]
COMMON_CHARACTER        = [^\'\$\"]|\$\$|\$L|\$N|\$P|\$R|\$T|\$l|\$n|\$p|\$r|\$t

NUM                     = {DIGIT}+
FIXED                   = {NUM}(\.{NUM})?

MS                      = {FIXED}[mM][sS]
SEC                     = ({FIXED}[sS]|{NUM}[sS]_?{MS})
MIN                     = ({FIXED}[mM]|{NUM}[mM]_?{SEC})
HOUR                    = ({FIXED}[hH]|{NUM}[hH]_?{MIN})
DAY                     = ({FIXED}[dD]|{NUM}[dD]_?{HOUR})

INTERVAL                = -?({DAY}|{HOUR}|{MIN}|{SEC}|{MS})
DATE                    = {NUM}-{NUM}-{NUM}
DAYTIME                 = {NUM}:{NUM}:{FIXED}
DATE_AND_TIME           = {DATE}-{DAYTIME}

NATURAL_NUMBER          = {DIGIT}(_?{DIGIT})*
INTEGER_NUMBER          = [\+\-]{NATURAL_NUMBER}
REAL_NUMBER             = [\+\-]?{NATURAL_NUMBER}\.({NATURAL_NUMBER}|({NATURAL_NUMBER}?[eE][\+\-]?{NATURAL_NUMBER}))
BINARY                  = 2#{BIT}(_?{BIT})*
OCTAL                   = 8#{OCT_DIGIT}(_?{OCT_DIGIT})*
HEXADECIMAL             = 16#{HEX_DIGIT}(_?{HEX_DIGIT})*

IDENTIFIER              = ({LETTER}|_({LETTER}|{DIGIT}))(_?[a-zA-Z0-9])*

// TODO: Hacer el casting de tipos a numeros naturales (number de Luca) para poder hacer
// las funciones fuzzificadoras con entrada positiva (los conversores manejan rangos personalizados)

SINGLE_BYTE_STRING      = \'({COMMON_CHARACTER}|\"|\$\'|\${HEX_DIGIT}{2})*\'
DOUBLE_BYTE_STRING      = \"({COMMON_CHARACTER}|\'|\$\"|\${HEX_DIGIT}{4})*\"

%%

// TODO: Se puede usar patrón de Factory para evitar crear los transformers acá.

{DATE_AND_TIME}         {
                            int token = processAndSaveYylval(
                                new Nothing(null), new DateAndDayTimes()
                            );
                            if (token != Lexer.YYerror) return Lexer.TIME_LITERAL;
                            yybegin(YYINITIAL);
                        }
{DAYTIME}               {
                            int token = processAndSaveYylval(
                                new Nothing(null), new DayTimes()
                            );
                            if (token != Lexer.YYerror) return Lexer.TIME_LITERAL;
                            yybegin(YYINITIAL);
                        }
{DATE}                  {
                            int token = processAndSaveYylval(
                                new Nothing(null), new Dates()
                            );
                            if (token != Lexer.YYerror) return Lexer.TIME_LITERAL;
                            yybegin(YYINITIAL);
                        }
{INTERVAL}              {
                            int token = processAndSaveYylval(
                                new UnderscoreRemover(new UpperCaseConverter(
                                    new OmitLeadingZeroMagnitudes(new OmitTrailingZeroMagnitudes(
                                            new OmitLeadingZerosInMagnitudes(new OmitTrailingZerosInMagnitudes(null))
                                    ))
                                )),
                                new Intervals()
                            );
                            if (token != Lexer.YYerror) return Lexer.TIME_LITERAL;
                            yybegin(YYINITIAL);
                        }

{NATURAL_NUMBER}        {
                            int token = processAndSaveYylval(
                                new UnderscoreRemover(new StripLeadingZeros(null)), new Naturals()
                            );
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{INTEGER_NUMBER}        {
                            int token = processAndSaveYylval(
                                new UnderscoreRemover(null), new Integers()
                            );
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{REAL_NUMBER}           {
                            int token = processAndSaveYylval(
                                new UnderscoreRemover(null), new Reals()
                            );
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{BINARY}                {
                            int token = processAndSaveYylval(
                                new UnderscoreRemover(new StripBaseNumberLeadingZeros(null)), new Binary()
                            );
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{OCTAL}                 {
                            int token = processAndSaveYylval(
                                new UnderscoreRemover(new StripBaseNumberLeadingZeros(null)), new Octal()
                            );
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{HEXADECIMAL}           {
                            int token = processAndSaveYylval(
                                new UnderscoreRemover(new StripBaseNumberLeadingZeros(null)), new Hexadecimal()
                            );
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }

{SINGLE_BYTE_STRING}    {
                            int token = processAndSaveYylval(
                                new StringHexResolver(new StringEscapeResolver(null)),
                                new Strings()
                            );
                            if (token != Lexer.YYerror) return Lexer.STRING_LITERAL;
                            yybegin(YYINITIAL);
                        }
{DOUBLE_BYTE_STRING}    {
                            int token = processAndSaveYylval(
                                new WStringHexResolver(new StringEscapeResolver(null)),
                                new WStrings()
                            );
                            if (token != Lexer.YYerror) return Lexer.STRING_LITERAL;
                            yybegin(YYINITIAL);
                        }

{IDENTIFIER}            {
                            final int resultToken = processAndSaveYylval(
                                new UpperCaseConverter(null), new Identifiers()
                            );
                            if (resultToken != Lexer.YYerror) return resultToken;
                            yybegin(YYINITIAL);
                        }

":"                     { return ':';  }
"#"                     { return '#';  }
";"                     { return ';';  }
","                     { return ',';  }
"("                     { return '(';  }
")"                     { return ')';  }
"["                     { return '[';  }
"]"                     { return ']';  }
"="                     { return '=';  }
"."                     { return '.';  }
"'"                     { return '\''; }
"\""                    { return '\"'; }
":="                    { return Lexer.ASSIGN_OP; }
".."                    { return Lexer.RANGE_OP;  }

[ \t\r\n]               { }
{COMMENT}               { }

.                       { yyerror("Invalid Character: " + yytext()); }
