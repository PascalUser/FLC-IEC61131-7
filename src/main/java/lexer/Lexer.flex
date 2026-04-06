package lexer;

import lexer.semantics.*;
import lexer.semantics.SemanticAnalyzer.Result;
import parser.Parser;
import utils.enums.ElementaryType;

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

    public int saveYylval(String text, ElementaryType type, SemanticAnalyzer analyzer) {
        Result result = analyzer.analyze(yytext(), type);
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
HEX_DIGIT         = [a-fA-F0-9]
BIT               = 0|1
COMMON_CHARACTER  = [^\'\$\"]|\$\$|\$L|\$N|\$P|\$R|\$T|\$l|\$n|\$p|\$r|\$t

IDENTIFIER         = ({LETTER}|_({LETTER}|{DIGIT}))(_?[a-zA-Z0-9])*

NUM                = {DIGIT}+
FIXED              = {NUM}(\.{NUM})?
SEP                = _?

MS                 = {FIXED}[mM][sS]
SEC                = ({FIXED}[sS]|{NUM}[sS]{SEP}{MS})
MIN                = ({FIXED}[mM]|{NUM}[mM]{SEP}{SEC})
HOUR               = ({FIXED}[hH]|{NUM}[hH]{SEP}{MIN})
DAY                = ({FIXED}[dD]|{NUM}[dD]{SEP}{HOUR})

INTERVAL           = -?({DAY}|{HOUR}|{MIN}|{SEC}|{MS})
DATE               = {NUM}-{NUM}-{NUM}
DAYTIME            = {NUM}:{NUM}:{FIXED}
DATE_AND_TIME      = {DATE}-{DAYTIME}

NATURAL_NUMBER     = {DIGIT}(_?{DIGIT})*
INTEGER_NUMBER     = [\+\-]{NATURAL_NUMBER}
REAL_NUMBER        = -?{NATURAL_NUMBER}\.{NATURAL_NUMBER}
BINARY             = 2#{BIT}+
OCTAL              = 8#[0-7]+
HEXADECIMAL        = 16#{HEX_DIGIT}+

// TODO: Hacer el manejo de errores para mostrar error/warning en el Anexo B
// TODO: Hacer el casting de tipos a numeros naturales (number de Luca) para poder hacer
// las funciones fuzzificadoras con entrada positiva (los conversores manejan rangos personalizados)

SINGLE_BYTE_STRING = \'({COMMON_CHARACTER}*|\"|\$\'|\${HEX_DIGIT}{2})*\'
DOUBLE_BYTE_STRING = \"({COMMON_CHARACTER}*|\'|\$\"|\${HEX_DIGIT}{4})*\"

%%

{DATE_AND_TIME}       { return saveYylval(yytext(), ElementaryType.DATE_AND_TIME, this.generic); }
{DAYTIME}             { return saveYylval(yytext(), ElementaryType.TIME_OF_DAY  , this.generic); }
{DATE}                { return saveYylval(yytext(), ElementaryType.DATE         , this.generic); }
{INTERVAL}            { return saveYylval(yytext(), ElementaryType.TIME         , this.generic); }

//todo checkear que sea este elementarytype y no otro
{NATURAL_NUMBER}      { return saveYylval(yytext(), ElementaryType.UINT         , this.generic); }
{INTEGER_NUMBER}      { return saveYylval(yytext(), ElementaryType.SINT         , this.generic); }
{REAL_NUMBER}         { return saveYylval(yytext(), ElementaryType.REAL         , this.generic); }
{BINARY}              { return saveYylval(yytext(), ElementaryType.BYTE         , this.generic); }
{OCTAL}               { return saveYylval(yytext(), ElementaryType.WORD         , this.generic); }
{HEXADECIMAL}         { return saveYylval(yytext(), ElementaryType.DWORD        , this.generic); }

{SINGLE_BYTE_STRING}  { return saveYylval(yytext(), ElementaryType.STRING       , this.generic); }
{DOUBLE_BYTE_STRING}  { return saveYylval(yytext(), ElementaryType.WSTRING      , this.generic); }

{IDENTIFIER}          { return saveYylval(yytext(), ElementaryType.UNKNOWN      , this.identifiers); }

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

