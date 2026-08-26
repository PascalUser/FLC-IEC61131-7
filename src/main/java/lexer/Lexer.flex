package lexer;

import java.lang.Error;
import org.jspecify.annotations.NonNull;
import parser.Parser;
import lexer.semantics.*;
import lexer.transformers.*;
import lexer.semantics.SemanticAnalyzer.*;
import utils.SymbolTable;
import utils.diagnostics.*;
import utils.DiagnosticsHandler;import utils.enums.Subtype;

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
        System.err.println("Line " + (yyline + 1) + ": " + msg);
    }

    /**
     * @param  transformer the lexeme preprocessor that transforms the lexeme before is analyzed
     * @param  analyzer    the lexeme's semantic analyzer
     * @return the lexeme's corresponded token number
     */
    public int processAndSaveYylval(@NonNull Transformer transformer, @NonNull SemanticAnalyzer analyzer) {
        LexicalContext lexicalContext = new LexicalContext(
                transformer.transform(yytext()),
                yyline,
                this.symbolTable,
                this.diagnosticsHandler
            );
        Result result = analyzer.analyze(lexicalContext);
        this.yylval = result.lexeme();
        return result.tokenNumber();
    }
%}

%ctorarg SymbolTable symbolTable
%ctorarg DiagnosticsHandler diagnosticsHandler

%init{
    this.symbolTable = symbolTable;
    this.diagnosticsHandler = diagnosticsHandler;
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

// TODO: Qué pasa si +MAX_ULINT => Se rompe todo
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

{DATE_AND_TIME}       { return processAndSaveYylval(new Nothing(null), new Default(Subtype.DATE_AND_TIME)); }
{DAYTIME}             { return processAndSaveYylval(new Nothing(null), new Default(Subtype.TIME_OF_DAY)); }
{DATE}                { return processAndSaveYylval(new Nothing(null), new Default(Subtype.DATE)); }
{INTERVAL}            { return processAndSaveYylval(new UnderscoreRemover(null), new Default(Subtype.TIME)); }

// TODO: hacer que el lexico agregue los initialValue de las constantes literales. Por ejemplo
// para el lexema "50E1" el initialValue es 500.
{NATURAL_NUMBER}      { return processAndSaveYylval(new UnderscoreRemover(new StripLeadingZeros(null)), new Naturals()); }
{INTEGER_NUMBER}      { return processAndSaveYylval(new UnderscoreRemover(null), new Integers()); }
{REAL_NUMBER}         { return processAndSaveYylval(new UnderscoreRemover(null), new Reals()); }
{BINARY}              { return processAndSaveYylval(new UnderscoreRemover(new StripBaseNumberLeadingZeros(null)), new Binary()); }
{OCTAL}               { return processAndSaveYylval(new UnderscoreRemover(new StripBaseNumberLeadingZeros(null)), new Octal()); }
{HEXADECIMAL}         { return processAndSaveYylval(new UnderscoreRemover(new StripBaseNumberLeadingZeros(null)), new Hexadecimal()); }

{SINGLE_BYTE_STRING}  { return processAndSaveYylval(new UnderscoreRemover(null), new Default(Subtype.STRING)); }
{DOUBLE_BYTE_STRING}  { return processAndSaveYylval(new UnderscoreRemover(null), new Default(Subtype.WSTRING)); }

{IDENTIFIER}          { return processAndSaveYylval(new UpperCaseConverter(null), new Identifiers()); }

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

