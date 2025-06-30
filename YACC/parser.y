%{
#include <stdio.h>
#include <stdlib.h>
%}

%glr-parser

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

/* ----------------------- Tokens Anexo B --------------------------- */

%token VAR_INPUT RETAIN NON_RETAIN END_VAR BOOL R_EDGE F_EDGE VAR_OUTPUT VAR CONSTANT
%token STRING WSTRING BYTE WORD DWORD LWORD TIME TIME_OF_DAY TOD DATE DATE_AND_TIME DT 
%token OF
%token SINT INT DINT LINT USINT UINT UDINT ULINT REAL LREAL
%token TRUE FALSE
%token BINARY_INTEGER OCTAL_INTEGER HEX_INTEGER BIT_STRING_LITERAL 
%token CHARACTER_STRING DURATION_LITERAL DATE_LITERAL TIME_OF_DAY_LITERAL DATE_AND_TIME_LITERAL
%token ARRAY

%%
/* ---------------------------------- IEC61131-7 ---------------------------------------- */
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
    TERM term_name ':' '=' membership_function ';'
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
    DEFAULT ':' '=' default_val ';'
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
  | PRAGMA IDENTIFIER integer_literal
;

/* TOKENS */

numeric_literal:
    INTEGER_LITERAL
  | REAL_LITERAL
;

/* ------------------------------- IEC61131-3 Annex B ------------------------------------ */

output_declarations:
    VAR_OUTPUT var_retain_spec var_init_decl_list END_VAR
;

input_declarations:
    VAR_INPUT var_retain_spec input_declaration_list END_VAR
;

input_declaration_list:
    input_declaration
    | input_declaration_list ';' input_declaration
;

input_declaration:
    var_init_decl
    | edge_declaration
;

edge_declaration:
    var1_list ':' BOOL edge_type
;

edge_type:
    R_EDGE 
    | F_EDGE
;

var_declarations:
    VAR var_constant_spec var_init_decl_list END_VAR
;

var_retain_spec:
    RETAIN
    | NON_RETAIN
    | /* vacío */
;

var_constant_spec:
    CONSTANT
    | /* vacío */
;

/*  var_init_decl */

var_init_decl_list:
    var_init_decl
    | var_init_decl_list ';' var_init_decl
;

var_init_decl:
    var1_init_decl
    | array_var_init_decl
    | structured_var_init_decl
    | fb_name_decl
    | string_var_declaration
;

/*  var1  */

var1_init_decl:
    var1_list ':' spec_init_type
;

spec_init_type:
    simple_spec_init
    | subrange_spec_init
    | enumerated_spec_init
;

var1_list:
    variable_name
    | var1_list ',' variable_name
;

simple_spec_init:
    simple_specification
    | simple_specification ':' '=' constant
;

simple_specification:
    elementary_type_name
    | simple_type_name
;

/*  elementary types */

elementary_type_name:
    numeric_type_name
    | date_type_name
    | bit_string_type_name
    | STRING
    | WSTRING
    | TIME
;

numeric_type_name:
    integer_type_name
    | real_type_name
;

integer_type_name:
    signed_integer_type_name
    | unsigned_integer_type_name
;

signed_integer_type_name:
    SINT | INT | DINT | LINT
;

unsigned_integer_type_name:
    USINT | UINT | UDINT | ULINT
;

real_type_name:
    REAL | LREAL
;

date_type_name:
    DATE | TIME_OF_DAY | TOD | DATE_AND_TIME | DT
;

bit_string_type_name:
    BOOL | BYTE | WORD | DWORD | LWORD
;

simple_type_name:
    IDENTIFIER
;

constant:
    numeric_literal
    | CHARACTER_STRING
    | time_literal
    | BIT_STRING_LITERAL
    | boolean_literal
;

integer_literal:
    signed_integer
    | BINARY_INTEGER
    | OCTAL_INTEGER
    | HEX_INTEGER
;

signed_integer:
    INTEGER_LITERAL
    | '+' INTEGER_LITERAL
    | '-' INTEGER_LITERAL
;



/* ----------------- codigo 2 ---------- */

time_literal:
    DURATION_LITERAL
    | TIME_OF_DAY_LITERAL
    | DATE_LITERAL
    | DATE_AND_TIME_LITERAL
;

boolean_literal:
    TRUE
    | FALSE
;

subrange_spec_init:
    subrange_specification
    | subrange_specification ':' '=' signed_integer
;

subrange_specification:
    integer_type_name '(' subrange ')'
    | subrange_type_name
;

subrange_type_name:
    IDENTIFIER
;

subrange:
    signed_integer '.' '.' signed_integer
;

enumerated_spec_init:
    enumerated_specification
    | enumerated_specification ':' '=' enumerated_value
;

enumerated_specification:
    '(' enumerated_value_list ')'
    | enumerated_type_name
;

enumerated_value_list:
    enumerated_value
    | enumerated_value_list ',' enumerated_value
;

enumerated_value:
    IDENTIFIER
    | enumerated_type_name '#' IDENTIFIER
;

enumerated_type_name:
    IDENTIFIER
;

array_var_init_decl:
    var1_list ':' array_spec_init
;

array_spec_init:
    array_specification
    | array_specification ':' '=' array_initialization
;

array_specification:
    array_type_name
    | ARRAY '[' subrange_list ']' OF non_generic_type_name
;

array_type_name:
    IDENTIFIER
;

subrange_list:
    subrange
    | subrange ',' subrange_list
;

non_generic_type_name:
    elementary_type_name
    | derived_type_name
;

derived_type_name:
    single_element_type_name
    | array_type_name
    | structure_type_name
    | string_type_name
;

single_element_type_name:
    simple_type_name
    | subrange_type_name
    | enumerated_type_name
;

structure_type_name:
    IDENTIFIER
;

string_type_name:
    IDENTIFIER
;

array_initialization:
    '[' array_initial_elements_list ']'
;

array_initial_elements_list:
    array_initial_elements
    | array_initial_elements_list ',' array_initial_elements
;

array_initial_elements:
    array_initial_element
    | INTEGER_LITERAL '(' array_initial_element ')'
;

array_initial_element:
    constant
    | enumerated_value
    | structure_initialization
    | array_initialization
;

structured_var_init_decl:
    var1_list ':' initialized_structure
;

initialized_structure:
    structure_type_name
    | structure_type_name ':' '=' structure_initialization
;

structure_initialization:
    '(' structure_element_initialization_list ')'
;

structure_element_initialization_list:
    structure_element_initialization
    | structure_element_initialization_list ',' structure_element_initialization
;

structure_element_initialization:
    structure_element_name ':' '=' structure_element_type
;

structure_element_type:
    constant 
    | enumerated_value 
    | array_initialization 
    | structure_initialization
;

structure_element_name:
    IDENTIFIER
;

fb_name_decl:
    fb_name_list ':' function_block_type_name
    | fb_name_list ':' function_block_type_name ':' '=' structure_initialization
;

fb_name_list:
    fb_name
    | fb_name_list ',' fb_name
;

fb_name:
    IDENTIFIER
;

function_block_type_name:
    standard_function_block_name
    | derived_function_block_name
;

standard_function_block_name:
    // VER QUE HACEMOS
    IDENTIFIER
;

derived_function_block_name:
    IDENTIFIER
;

string_var_declaration:
    single_byte_string_var_declaration
    | double_byte_string_var_declaration
;

single_byte_string_var_declaration:
    var1_list ':' single_byte_string_spec
;

single_byte_string_spec:
    STRING
    | STRING '[' INTEGER_LITERAL ']'
    | STRING ':' '=' CHARACTER_STRING
    | STRING '[' INTEGER_LITERAL ']' ':' '=' CHARACTER_STRING
;

double_byte_string_var_declaration:
    var1_list ':' double_byte_string_spec
;

double_byte_string_spec:
    WSTRING
    | WSTRING '[' INTEGER_LITERAL ']'
    | WSTRING ':' '=' CHARACTER_STRING
    | WSTRING '[' INTEGER_LITERAL ']' ':' '=' CHARACTER_STRING
;

%% /* ------------------------------- Código C ------------------------------------ */

int main() {
    return yyparse();
}

int yyerror(const char *s) {
    fprintf(stderr, "Error: %s\n", s);
    return 1;
}