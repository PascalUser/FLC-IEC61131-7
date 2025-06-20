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
%token ASSIGN COLON SEMICOLON COMMA DOTDOT LPAREN RPAREN
%token COG COGS COA LM RM NC
%token MAX ASUM BSUM PROD BDIF NSUM
%token ID INTEGER_LITERAL REAL_LITERAL
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
    TERM term_name ASSIGN membership_function SEMICOLON
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
    LPAREN point_x COMMA numeric_literal RPAREN
;

point_x:
    numeric_literal
  | variable_name
;

defuzzification_method:
    METHOD COLON defuzz_method SEMICOLON
;

defuzz_method:
    COG | COGS | COA | LM | RM
;

default_value:
    DEFAULT ASSIGN default_val SEMICOLON
;

default_val:
    numeric_literal
  | NC
;

range_opt:
    /* vacío */
  | RANGE LPAREN numeric_literal DOTDOT numeric_literal RPAREN SEMICOLON
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
    operator_or_opt operator_and_opt SEMICOLON
;

operator_or_opt:
    /* vacío */
  | OR COLON or_type
;

operator_and_opt:
    /* vacío */
  | AND COLON and_type
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
    ACT COLON act_type SEMICOLON
;

act_type:
    PROD | MIN
;

accumulation_method:
    ACCU COLON accu_type SEMICOLON
;

accu_type:
    MAX | BSUM | NSUM
;

rule_list:
    /* vacío */
  | rule_list rule
;

rule:
    RULE INTEGER_LITERAL COLON IF condition THEN conclusion weighting_opt SEMICOLON
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
  | LPAREN condition RPAREN
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
    conclusion_elements COMMA conclusion_element
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
    identifier
;

rule_block_name:
    identifier
;

term_name:
    identifier
;

f_variable_name:
    identifier
;

variable_name:
    identifier
;

/* PRAGMAS */

pragma_list:
    pragma_list pragma
;

pragma:
    PRAGMA ID 
  | PRAGMA ID NUMBER
;

/* TOKENS */

numeric_literal:
    INTEGER_LITERAL
  | REAL_LITERAL
;

identifier:
    ID
;

input_declarations:
    ID /* Aca hay que ver el tema del estandar IEC 61131-3 Annex B y elegir que hacer */
;

output_declarations:
    ID /* Aca hay que ver el tema del estandar IEC 61131-3 Annex B y elegir que hacer */
    
;

var_declarations:
    ID /* Aca hay que ver el tema del estandar IEC 61131-3 Annex B y elegir que hacer */
;

%%
