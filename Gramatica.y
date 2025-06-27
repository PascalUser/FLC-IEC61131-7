%{
#include <stdio.h>
#include <stdlib.h>
%}

%token FUNCTION_BLOCK END_FUNCTION_BLOCK
%token FUZZIFY END_FUZZIFY
%token DEFUZZIFY END_DEFUZZIFY
%token RULEBLOCK END_RULEBLOCK
%token OPTION END_OPTION
%token TERM METHOD DEFAULT RANGE
%token RULE IF THEN WITH ACT ACCU
%token IS NOT AND OR
%token COG COGS COA LM RM NC
%token MIN MAX ASUM BSUM PROD BDIF NSUM
%token IDENTIFIER INTEGER_LITERAL REAL_LITERAL
%token PRAGMA

%%

program:
    function_block_declaration
;

function_block_declaration:
    FUNCTION_BLOCK function_block_name
    fb_io_var_declarations_list
    other_var_declarations_list
    function_block_body
    END_FUNCTION_BLOCK
;

fb_io_var_declarations_list:
    /* vacío */
  | fb_io_var_declarations_list fb_io_var_declarations
;

fb_io_var_declarations:
    input_declarations
  | output_declarations
;

other_var_declarations_list:
    /* vacío */
  | other_var_declarations_list other_var_declarations
;

other_var_declarations:
    var_declarations
;

function_block_body:
    fuzzify_block_list
    defuzzify_block_list
    rule_block_list
    option_block_list
;

fuzzify_block_list:
    /* vacío */
  | fuzzify_block_list fuzzify_block
;

fuzzify_block:
    FUZZIFY variable_name
    linguistic_term_list
    END_FUZZIFY
;

defuzzify_block_list:
    /* vacío */
  | defuzzify_block_list defuzzify_block
;

defuzzify_block:
    DEFUZZIFY f_variable_name
    range_opt
    linguistic_term_list
    defuzzification_method
    default_value
    END_DEFUZZIFY
;

linguistic_term_list:
    /* vacío */
  | linguistic_term_list linguistic_term
;

linguistic_term:
    TERM term_name ':=' membership_function ';'
;

membership_function:
    singleton
  | points
;

singleton:
    numeric_literal
  | variable_name
;

points:
    point_list
;

point_list:
    point
  | point_list point
;

point:
    '(' point_x ',' numeric_literal ')'
;

point_x:
    numeric_literal
  | variable_name
;

defuzzification_method:
    METHOD ':' defuzz_method ';'
;

defuzz_method:
    COG | COGS | COA | LM | RM
;

default_value:
    DEFAULT ':=' default_val ';'
;

default_val:
    numeric_literal
  | NC
;

range_opt:
    /* vacío */
  | RANGE '(' numeric_literal '.''.' numeric_literal ')' ';'
;

rule_block_list:
    /* vacío */
  | rule_block_list rule_block
;

rule_block:
    RULEBLOCK rule_block_name
    operator_definition
    activation_method_opt
    accumulation_method
    rule_list
    END_RULEBLOCK
;

operator_definition:
    operator_or_opt operator_and_opt ';'
;

operator_or_opt:
    /* vacío */
  | OR ':' or_type
;

operator_and_opt:
    /* vacío */
  | AND ':' and_type
;

or_type:
    MAX | ASUM | BSUM
;

and_type:
    MIN | PROD | BDIF
;

activation_method_opt:
    /* vacío */
  | activation_method
;

activation_method:
    ACT ':' act_type ';'
;

act_type:
    PROD | MIN
;

accumulation_method:
    ACCU ':' accu_type ';'
;

accu_type:
    MAX | BSUM | NSUM
;

rule_list:
    /* vacío */
  | rule_list rule
;

rule:
    RULE INTEGER_LITERAL ':' IF condition THEN conclusion weighting_opt ';'
;

weighting_opt:
    /* vacío */
  | WITH weighting_factor
;

condition:
    x condition_tail
;

condition_tail:
    /* vacío */
  | AND x condition_tail
  | OR x condition_tail
;

x:
    NOT x
  | subcondition
  | '(' condition ')'
;

subcondition:
    variable_name
  | variable_name IS term_name
  | variable_name IS NOT term_name
;

conclusion:
    conclusion_elements
;

conclusion_elements:
    conclusion_elements ',' conclusion_element
  | conclusion_element
;

conclusion_element:
    variable_name
  | variable_name IS term_name
;

weighting_factor:
    variable_name
  | numeric_literal
;

option_block_list:
    /* vacío */
  | option_block_list option_block
;

option_block:
    OPTION pragma_list END_OPTION
;

/* Terminal rules (identifiers y literales) */

function_block_name:
    IDENTIFIER
;

rule_block_name:
    IDENTIFIER
;

term_name:
    IDENTIFIER
;

f_variable_name:
    IDENTIFIER
;

variable_name:
    IDENTIFIER
;

/* PRAGMAS */

pragma_list:
    /* vacío */
    | pragma_list pragma
;

pragma:
    PRAGMA IDENTIFIER 
  | PRAGMA IDENTIFIER numeric_literal
;

/* TOKENS */

numeric_literal:
    INTEGER_LITERAL
  | REAL_LITERAL
;

input_declarations:
    /* VAR_INPUT variables END_VAR */ /* Placeholder - Refer to IEC 61131-3 Annex B */
;

output_declarations:
    /* VAR_OUTPUT
    IDENTIFIER */ /* Placeholder - Refer to IEC 61131-3 Annex B */  
;

var_declarations:
    IDENTIFIER /* Placeholder - Refer to IEC 61131-3 Annex B */
;

%%

int main() {
    return yyparse();
}

int yyerror(const char *s) {
    fprintf(stderr, "Error: %s\n", s);
    return 1;
}
