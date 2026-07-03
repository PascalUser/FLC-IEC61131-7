package lexer;

import lexer.semantics.*;
import lexer.semantics.SemanticAnalyzer.Result;
import parser.Parser;
import utils.enums.Subtype;
import utils.SymbolTable;

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
    private SemanticAnalyzer identifiers;
    private SemanticAnalyzer generic;

    public Object getLVal() {
        return this.yylval;
    }

    public void yyerror(String msg) {
        System.err.println("Line " + (yyline + 1) + ": " + msg);
    }

    public int saveYylval(String text, Subtype type, SemanticAnalyzer analyzer) {
        Result result = analyzer.analyze(text, type);
        this.yylval = result.value;
        return result.tokenNumber;
    }
%}

%ctorarg SymbolTable symbolTable

%init{
    this.identifiers = new Identifiers(symbolTable);
    this.generic     = new Default(symbolTable);
%init}

COMMENT           = \(\*.*\*\)

LETTER            = [a-zA-Z]
DIGIT             = [0-9]
BIT               = [01]
OCT_DIGIT         = [0-7]
HEX_DIGIT         = [a-fA-F0-9]
COMMON_CHARACTER  = [^\'\$\"]|\$\$|\$L|\$N|\$P|\$R|\$T|\$l|\$n|\$p|\$r|\$t

NUM                = {DIGIT}+
FIXED              = {NUM}(\.{NUM})?

MS                 = {FIXED}[mM][sS]
SEC                = ({FIXED}[sS]|{NUM}[sS]_?{MS})
MIN                = ({FIXED}[mM]|{NUM}[mM]_?{SEC})
HOUR               = ({FIXED}[hH]|{NUM}[hH]_?{MIN})
DAY                = ({FIXED}[dD]|{NUM}[dD]_?{HOUR})

INTERVAL           = -?({DAY}|{HOUR}|{MIN}|{SEC}|{MS})
DATE               = {NUM}-{NUM}-{NUM}
DAYTIME            = {NUM}:{NUM}:{FIXED}
DATE_AND_TIME      = {DATE}-{DAYTIME}

NATURAL_NUMBER     = {DIGIT}(_?{DIGIT})*
INTEGER_NUMBER     = [\+\-]{NATURAL_NUMBER}
REAL_NUMBER        = [\+\-]?{NATURAL_NUMBER}\.({NATURAL_NUMBER}|({NATURAL_NUMBER}?[eE][\+\-]?{NATURAL_NUMBER}))
BINARY             = 2#{BIT}(_?{BIT})*
OCTAL              = 8#{OCT_DIGIT}(_?{OCT_DIGIT})*
HEXADECIMAL        = 16#{HEX_DIGIT}(_?{HEX_DIGIT})*

IDENTIFIER         = ({LETTER}|_({LETTER}|{DIGIT}))(_?[a-zA-Z0-9])*

// TODO: Hacer el manejo de errores para mostrar error/warning en el Anexo B
// TODO: Hacer el casting de tipos a numeros naturales (number de Luca) para poder hacer
// las funciones fuzzificadoras con entrada positiva (los conversores manejan rangos personalizados)

SINGLE_BYTE_STRING = \'({COMMON_CHARACTER}|\"|\$\'|\${HEX_DIGIT}{2})*\'
DOUBLE_BYTE_STRING = \"({COMMON_CHARACTER}|\'|\$\"|\${HEX_DIGIT}{4})*\"

%%

{DATE_AND_TIME}       { return saveYylval(yytext(), Subtype.DATE_AND_TIME, this.generic); }
{DAYTIME}             { return saveYylval(yytext(), Subtype.TIME_OF_DAY  , this.generic); }
{DATE}                { return saveYylval(yytext(), Subtype.DATE         , this.generic); }
{INTERVAL}            { return saveYylval(yytext(), Subtype.TIME         , this.generic); }

// TODO: hacer que el lexico agregue los initialValue de las constantes literales. Por ejemplo
// para el lexema "50E1" el initialValue es 500.
{NATURAL_NUMBER}      { return saveYylval(yytext(), Subtype.UINT         , this.generic); }
{INTEGER_NUMBER}      { return saveYylval(yytext(), Subtype.SINT         , this.generic); }
{REAL_NUMBER}         { return saveYylval(yytext(), Subtype.REAL         , this.generic); }
{BINARY}              { return saveYylval(yytext(), Subtype.BYTE         , this.generic); }
{OCTAL}               { return saveYylval(yytext(), Subtype.WORD         , this.generic); }
{HEXADECIMAL}         { return saveYylval(yytext(), Subtype.DWORD        , this.generic); }

{SINGLE_BYTE_STRING}  { return saveYylval(yytext(), Subtype.STRING       , this.generic); }
{DOUBLE_BYTE_STRING}  { return saveYylval(yytext(), Subtype.WSTRING      , this.generic); }

{IDENTIFIER}          { return saveYylval(yytext(), Subtype.UNKNOWN      , this.identifiers); }

":"                   { return ':'; }
"#"                   { return '#'; }
";"                   { return ';'; }
","                   { return ','; }
"("                   { return '('; }
")"                   { return ')'; }
"["                   { return '['; }
"]"                   { return ']'; }
"="                   { return '='; }
"."                   { return '.'; }
"'"                   { return '\''; }
"\""                  { return '\"'; }
":="                  { return Parser.Lexer.ASSIGN_OP; }
".."                  { return Parser.Lexer.RANGE_OP;  }

[ \t\r\n]             { }
{COMMENT}             { }

.                     { yyerror("Invalid Character: " + yytext()); }

