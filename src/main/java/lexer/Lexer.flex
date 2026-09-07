package lexer;

import parser.Parser;
import utils.SymbolTable;
import utils.DiagnosticsHandler;
import org.jspecify.annotations.NonNull;

import lexer.transformers.Transformer;
import lexer.semantics.SemanticAnalyzer;
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

    private LexicalPreprocessors preprocessor;
    private LexicalAnalyzers analyzer;

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
    this.preprocessor = new LexicalPreprocessors();
    this.analyzer = new LexicalAnalyzers();
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

SINGLE_BYTE_STRING      = \'({COMMON_CHARACTER}|\"|\$\'|\${HEX_DIGIT}{2})*\'
DOUBLE_BYTE_STRING      = \"({COMMON_CHARACTER}|\'|\$\"|\${HEX_DIGIT}{4})*\"

%%

{DATE_AND_TIME}         {
                            final int token = processAndSaveYylval(preprocessor.DATE_AND_TIMES, analyzer.DATE_AND_TIMES);
                            if (token != Lexer.YYerror) return Lexer.TIME_LITERAL;
                            yybegin(YYINITIAL);
                        }
{DAYTIME}               {
                            final int token = processAndSaveYylval(preprocessor.DAYTIMES, analyzer.DAYTIMES);
                            if (token != Lexer.YYerror) return Lexer.TIME_LITERAL;
                            yybegin(YYINITIAL);
                        }
{DATE}                  {
                            final int token = processAndSaveYylval(preprocessor.DATES, analyzer.DATES);
                            if (token != Lexer.YYerror) return Lexer.TIME_LITERAL;
                            yybegin(YYINITIAL);
                        }
{INTERVAL}              {
                            final int token = processAndSaveYylval(preprocessor.INTERVALS, analyzer.INTERVALS);
                            if (token != Lexer.YYerror) return Lexer.TIME_LITERAL;
                            yybegin(YYINITIAL);
                        }

{NATURAL_NUMBER}        {
                            final int token = processAndSaveYylval(preprocessor.NATURALS, analyzer.NATURALS);
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{INTEGER_NUMBER}        {
                            final int token = processAndSaveYylval(preprocessor.INTEGERS, analyzer.INTEGERS);
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{REAL_NUMBER}           {
                            final int token = processAndSaveYylval(preprocessor.REALS, analyzer.REALS);
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{BINARY}                {
                            final int token = processAndSaveYylval(preprocessor.BINARY, analyzer.BINARY);
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{OCTAL}                 {
                            final int token = processAndSaveYylval(preprocessor.OCTAL, analyzer.OCTAL);
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }
{HEXADECIMAL}           {
                            final int token = processAndSaveYylval(preprocessor.HEXADECIMAL, analyzer.HEXADECIMAL);
                            if (token != Lexer.YYerror) return Lexer.NUMERIC_LITERAL;
                            yybegin(YYINITIAL);
                        }

{SINGLE_BYTE_STRING}    {
                            final int token = processAndSaveYylval(preprocessor.STRINGS, analyzer.STRINGS);
                            if (token != Lexer.YYerror) return Lexer.STRING_LITERAL;
                            yybegin(YYINITIAL);
                        }
{DOUBLE_BYTE_STRING}    {
                            final int token = processAndSaveYylval(preprocessor.WSTRINGS, analyzer.WSTRINGS);
                            if (token != Lexer.YYerror) return Lexer.STRING_LITERAL;
                            yybegin(YYINITIAL);
                        }

{IDENTIFIER}            {
                            final int token = processAndSaveYylval(preprocessor.IDENTIFIERS, analyzer.IDENTIFIERS);
                            if (token != Lexer.YYerror) return token;
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
