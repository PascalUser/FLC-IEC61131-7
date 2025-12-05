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
  /* Variable auxiliar para guardar el valor del token (el equivalente a yylval) */
  private Object yylval;

  /* Implementación obligatoria de getLVal para Bison */
  public Object getLVal() {
    return this.yylval;
  }

  /* Implementación obligatoria de yyerror */
  public void yyerror(String msg) {
    System.err.println("Error lexico: " + msg);
  }

  public static int IdentifierSemanticAction(String ID) {
    LexemeInfo info = Compiler.table.get(ID);
    if (info != null) {
        if (info.tokenType.equals("reserved")) {
            return info.tokenNumber;
        } else {
            // yylval
            return Lexer.IDENTIFIER;
        }
    } else {
        // yylval
        Compiler.table.put(ID, new LexemeInfo(Lexer.IDENTIFIER, "id"));
        return Lexer.IDENTIFIER;
    }
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
REAL_NUMBER        = {DIGIT}+\.{DIGIT}+
BINARY             = 2#{BIT}+
OCTAL              = 8#[0-7]+
HEXADECIMAL        = 16#{HEX_DIGIT}+
SINGLE_BYTE_STRING = \'({COMMON_CHARACTER}*|\"|\$\'|\${HEX_DIGIT}{2})*\'
DOUBLE_BYTE_STRING = \"({COMMON_CHARACTER}*|\'|\$\"|\${HEX_DIGIT}{4})*\"
CHARACTER_STRING   = {SINGLE_BYTE_STRING}|{DOUBLE_BYTE_STRING}

%%

{NATURAL_NUMBER}     {  }
{INTEGER_NUMBER}     {  }
{REAL_NUMBER}        {  }


{IDENTIFIER}         { return IdentifierSemanticAction(yytext()); }

":"                  { return ':'; }
";"                  { return ';'; }
","                  { return ','; }
"("                  { return '('; }
")"                  { return ')'; }
"["                  { return '['; }
"]"                  { return ']'; }
"="                  { return '='; }
"."                  { return '.'; }
"'"                  { return '\''; }
"\""                 { return '\"'; }

[ \t\r\n]+           { }

.                    { System.err.println("Line: " + (yyline + 1) + ", Caracter no permitido: " + yytext()); }

