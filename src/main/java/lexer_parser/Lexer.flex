package lexer_parser;

import java.io.*;
import utils.Compiler;
import utils.LexemeInfo;

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

  public Object getLVal() {
    return this.yylval;
  }

  public void yyerror(String msg) {
    System.err.println("Line " + (yyline + 1) + ": " + msg);
  }

  /* Funcion para devolver el token correspondiente a un IDENTIFIER del lexico */
  public static int IdentifierSemanticAction(String ID) {
    LexemeInfo info = Compiler.table.get(ID.toUpperCase());
    if (info != null) {
        if (info.tokenType.equals("reserved")) {
            return info.tokenNumber;
        } else {
            // yylval
            return Parser.Lexer.IDENTIFIER;
        }
    } else {
        // yylval
        Compiler.table.put(ID, new LexemeInfo(Parser.Lexer.IDENTIFIER, "id"));
        return Parser.Lexer.IDENTIFIER;
    }
  }

  /* Funcion para tratamiento de numeros en la tabla de simbolos. */
  public static int GenericSemanticAction(String lexeme, int token, String tag) {
    LexemeInfo info = Compiler.table.get(lexeme);
    if (info == null) {
        Compiler.table.put(lexeme, new LexemeInfo(token, tag));
    }
    // yylval
    return token;
  }
%}

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

{DATE_AND_TIME}       { return GenericSemanticAction(yytext(), Parser.Lexer.DATE_AND_TIME_LITERAL, "date-and-time"); }
{DAYTIME}             { return GenericSemanticAction(yytext(), Parser.Lexer.DAYTIME_LITERAL, "daytime"); }
{DATE}                { return GenericSemanticAction(yytext(), Parser.Lexer.DATE_LITERAL, "date"); }
{INTERVAL}            { return GenericSemanticAction(yytext(), Parser.Lexer.INTERVAL_LITERAL, "interval"); }

{NATURAL_NUMBER}      { return GenericSemanticAction(yytext(), Parser.Lexer.NUMERIC_LITERAL, "natural"); }
{INTEGER_NUMBER}      { return GenericSemanticAction(yytext(), Parser.Lexer.NUMERIC_LITERAL, "integer"); }
{REAL_NUMBER}         { return GenericSemanticAction(yytext(), Parser.Lexer.NUMERIC_LITERAL, "real"); }
{BINARY}              { return GenericSemanticAction(yytext(), Parser.Lexer.NUMERIC_LITERAL, "binary"); }
{OCTAL}               { return GenericSemanticAction(yytext(), Parser.Lexer.NUMERIC_LITERAL, "octal"); }
{HEXADECIMAL}         { return GenericSemanticAction(yytext(), Parser.Lexer.NUMERIC_LITERAL, "hexadecimal"); }

{SINGLE_BYTE_STRING}  { return GenericSemanticAction(yytext(), Parser.Lexer.STRING_LITERAL, "single-byte-string"); }
{DOUBLE_BYTE_STRING}  { return GenericSemanticAction(yytext(), Parser.Lexer.STRING_LITERAL, "double-byte-string"); }

{IDENTIFIER}          { return IdentifierSemanticAction(yytext()); }

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

