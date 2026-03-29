package lexer;

import parser.Parser;
import utils.ElementaryType;
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

    public int saveYyval(String text, ElementaryType type) {
        SemanticAnalyzer.Result r = this.generic.analyze(yytext(), type);
        this.yylval = r.value;
        return r.tokenNumber;
    }
%}

%ctorarg SymbolTable symbolTable

%init{
    this.identifiers = new IdentifierAnalyzer(symbolTable);
    this.generic     = new GenericAnalyzer(symbolTable);
%init}

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

INTERVAL           = ({DAY}|{HOUR}|{MIN}|{SEC}|{MS})
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

{DATE_AND_TIME}       { return saveYyval(yytext(), ElementaryType.DATE_AND_TIME); }
{DAYTIME}             { return saveYyval(yytext(), ElementaryType.TIME_OF_DAY); }
{DATE}                { return saveYyval(yytext(), ElementaryType.DATE); }
{INTERVAL}            { return saveYyval(yytext(), ElementaryType.TIME); }

{NATURAL_NUMBER}      { return saveYyval(yytext(), ElementaryType.UINT); }
{INTEGER_NUMBER}      { return saveYyval(yytext(), ElementaryType.SINT); }
{REAL_NUMBER}         { return saveYyval(yytext(), ElementaryType.REAL); }
{BINARY}              { return saveYyval(yytext(), ElementaryType.BYTE); } //todo checkear que sea este elementarytype y no otro
{OCTAL}               { return saveYyval(yytext(), ElementaryType.WORD); }
{HEXADECIMAL}         { return saveYyval(yytext(), ElementaryType.DWORD); }

{SINGLE_BYTE_STRING}  { return saveYyval(yytext(), ElementaryType.STRING ); }
{DOUBLE_BYTE_STRING}  { return saveYyval(yytext(), ElementaryType.WSTRING ); }

{IDENTIFIER}          { SemanticAnalyzer.Result r = this.identifiers.analyze(yytext(), ElementaryType.UNKNOWN);
                        this.yylval = r.value;
                        return r.tokenNumber; }

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
".."                  { return Parser.Lexer.RANGE_OP; }

[ \t\r\n]+            { }

.                     { yyerror("Invalid Character: " + yytext()); }

