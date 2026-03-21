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
    LexemeInfo info = Compiler.table.get(ID);
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

NATURAL_NUMBER     = {DIGIT}(_?{DIGIT})*
INTEGER_NUMBER     = [\+\-]{NATURAL_NUMBER}
REAL_NUMBER        = -?{NATURAL_NUMBER}\.{NATURAL_NUMBER}
BINARY             = 2#{BIT}+
OCTAL              = 8#[0-7]+
HEXADECIMAL        = 16#{HEX_DIGIT}+

// TODO: Hacer el tipo date con expresiones regulares para poder tokenizarlo
// TODO: Hacer el manejo de errores para mostrar error/warning en el Anexo B
// TODO: Hacer el casting de tipos a numeros naturales (number de Luca) para poder hacer
// las funciones fuzzificadoras con entrada positiva (los conversores manejan rangos personalizados)

SINGLE_BYTE_STRING = \'({COMMON_CHARACTER}*|\"|\$\'|\${HEX_DIGIT}{2})*\'
DOUBLE_BYTE_STRING = \"({COMMON_CHARACTER}*|\'|\$\"|\${HEX_DIGIT}{4})*\"

%%

{NATURAL_NUMBER}      { return GenericSemanticAction(yytext(), Parser.Lexer.INTEGER_NUMBER, "natural"); }
{INTEGER_NUMBER}      { return GenericSemanticAction(yytext(), Parser.Lexer.SIGNED_INTEGER, "integer"); }
{REAL_NUMBER}         { return GenericSemanticAction(yytext(), Parser.Lexer.REAL_NUMBER, "real"); }
{BINARY}              { return GenericSemanticAction(yytext(), Parser.Lexer.BINARY_INTEGER, "binary"); }
{OCTAL}               { return GenericSemanticAction(yytext(), Parser.Lexer.OCTAL_INTEGER, "octal"); }
{HEXADECIMAL}         { return GenericSemanticAction(yytext(), Parser.Lexer.HEX_INTEGER, "hexadecimal"); }

{SINGLE_BYTE_STRING}  { return GenericSemanticAction(yytext(), Parser.Lexer.CHARACTER_STRING, "single-byte-string"); }
{DOUBLE_BYTE_STRING}  { return GenericSemanticAction(yytext(), Parser.Lexer.CHARACTER_STRING, "double-byte-string"); }

{IDENTIFIER}          { return IdentifierSemanticAction(yytext()); }

":"                   { return ':'; }
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

