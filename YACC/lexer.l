%{

#include "y.tab.h"
#include <string.h>

%}

POINT := "(" INTEGER "," INTEGER ")" 

fuzz = (0, 10), (0, 20); => bit

LETTER              [a-zA-Z]
DIGIT               [0-9]

HEX_DIGIT           [a-fA-F0-9]
BIT                 0|1

/*  Tipos Numericos */
INTEGER             [{DIGIT}]+
REAL                DIGIT+\.DIGIT+

IDENTIFIER          ({LETTER}|_({LETTER}|{DIGIT}))(_?[a-zA-Z0-9])*

BINARY              2#{{BIT}}+
OCTAL               8#[0-7]+
HEXADECIMAL         16#[{HEX_DIGIT}]+

COMMON_CHARACTER    ([^'$"]|\$\$|\$L|\$N|\$P|\$R|\$T|\$l|\$n|\$p|\$r|\$t)*

SINGLE_BYTE_STRING  '({COMMON_CHARACTER}|"|\$'|\${HEX_DIGIT}{2})*'
DOUBLE_BYTE_STRING  "({COMMON_CHARACTER}|'|\$"|\${HEX_DIGIT}{4})*"
CHARACTER_STRING    {SINGLE_BYTE_STRING}|{DOUBLE_BYTE_STRING}


OCTAL_INTEGER 
HEX_INTEGER
BIT_STRING_LITERAL
DURATION_LITERAL
DATE_LITERAL
TIME_OF_DAY_LITERAL
DATE_AND_TIME_LITERAL

%%

/*  ----------------------------------- Macros  ----------------------------------- */

INTEGER              { yyval.integer = atoi(yytext); return INTEGER_LITERAL; }
REAL                 { yyval.real    = atoi(yytext); return REAL_LITERAL; }

/*  ---------------------------- IEC 61131-7 Keywords  ---------------------------- */

"FUNCTION_BLOCK"     { return FUNCTION_BLOCK; }
"END_FUNCTION_BLOCK" { return END_FUNCTION_BLOCK; }
"FUZZIFY"            { return FUZZIFY; }
"END_FUZZIFY"        { return END_FUZZIFY; }
"DEFUZZIFY"          { return DEFUZZIFY; }
"END_DEFUZZIFY"      { return END_DEFUZZIFY; }
"RULEBLOCK"          { return RULEBLOCK; }
"END_RULEBLOCK"      { return END_RULEBLOCK; }
"OPTION"             { return OPTION; }
"END_OPTION"         { return END_OPTION; }
"TERM"               { return TERM; }
"METHOD"             { return METHOD; }
"DEFAULT"            { return DEFAULT; }
"RANGE"              { return RANGE; }
"RULE"               { return RULE; }
"IF"                 { return IF; }
"THEN"               { return THEN; }
"WITH"               { return WITH; }
"ACT"                { return ACT; }
"ACCU"               { return ACCU; }
"IS"                 { return IS; }
"NOT"                { return NOT; }
"AND"                { return AND; }
"OR"                 { return OR; }

/* Métodos de defuzzificación */
"COG"                { return COG; }
"COGS"               { return COGS; }
"COA"                { return COA; }
"LM"                 { return LM; }
"RM"                 { return RM; }
"NC"                 { return NC; }

/* Operadores difusos */
"MIN"                { return MIN; }
"MAX"                { return MAX; }
"ASUM"               { return ASUM; }
"BSUM"               { return BSUM; }
"PROD"               { return PROD; }
"BDIF"               { return BDIF; }
"NSUM"               { return NSUM; }

/*  ---------------------------- IEC 61131-3 Keywords  ---------------------------- */

"VAR_INPUT"          { return VAR_INPUT; }
"VAR_OUTPUT"         { return VAR_OUTPUT; }
"VAR"                { return VAR; }
"END_VAR"            { return END_VAR; }
"RETAIN"             { return RETAIN; }
"NON_RETAIN"         { return NON_RETAIN; }
"CONSTANT"           { return CONSTANT; }

/* Tipos de datos */
"BOOL"               { return BOOL; }
"SINT"               { return SINT; }
"INT"                { return INT; }
"DINT"               { return DINT; }
"LINT"               { return LINT; }
"USINT"              { return USINT; }
"UINT"               { return UINT; }
"UDINT"              { return UDINT; }
"ULINT"              { return ULINT; }
"REAL"               { return REAL; }
"LREAL"              { return LREAL; }
"STRING"             { return STRING; }
"WSTRING"            { return WSTRING; }
"BYTE"               { return BYTE; }
"WORD"               { return WORD; }
"DWORD"              { return DWORD; }
"LWORD"              { return LWORD; }
"TIME"               { return TIME; }
"TIME_OF_DAY"        { return TIME_OF_DAY; }
"TOD"                { return TOD; }
"DATE"               { return DATE; }
"DATE_AND_TIME"      { return DATE_AND_TIME; }
"DT"                 { return DT; }
"ARRAY"              { return ARRAY; }
"OF"                 { return OF; }

/* Valores booleanos */
"TRUE"               { return TRUE; }
"FALSE"              { return FALSE; }

/* Detección de flancos */
"R_EDGE"             { return R_EDGE; }
"F_EDGE"             { return F_EDGE; }

%%

int yywrap(void) {
    return 1;
}