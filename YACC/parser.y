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
%token IDENTIFIER
%token STD_FB_IDENTIFIER
%token PRAGMA

/* ----------------------- Tokens Anexo B --------------------------- */

%token VAR_INPUT RETAIN NON_RETAIN END_VAR BOOL R_EDGE F_EDGE VAR_OUTPUT VAR CONSTANT
%token STRING WSTRING BYTE WORD DWORD LWORD TIME TIME_OF_DAY TOD DATE DATE_AND_TIME DT 
%token OF
%token SINT INT DINT LINT USINT UINT UDINT ULINT REAL LREAL
%token TRUE FALSE
%token BINARY_INTEGER OCTAL_INTEGER HEX_INTEGER 
%token CHARACTER_STRING 
%token ARRAY
%token INTEGER_NUMBER REAL_NUMBER SIGNED_INTEGER

%%
/* ---------------------------------- IEC61131-7 ---------------------------------------- */

program:
    function_block_declaration
;

function_block_declaration:
    FUNCTION_BLOCK IDENTIFIER
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
    FUZZIFY IDENTIFIER
    linguistic_term_list
    END_FUZZIFY
;

defuzzify_block_list:
    /* vacío */
    | defuzzify_block_list defuzzify_block
;

defuzzify_block:
    DEFUZZIFY IDENTIFIER
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
    TERM IDENTIFIER ':' '=' IDENTIFIER ';'
    TERM IDENTIFIER ':' '=' membership_function ';'
;

membership_function:
    singleton
    | points
;

singleton:
    numeric_literal
;

points:
    point_list
;

point_list:
    point
    | point_list point
;

point:
    '(' numeric_literal ',' numeric_literal ')'
    '(' IDENTIFIER ',' numeric_literal ')'
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
    RULEBLOCK IDENTIFIER
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
    RULE integer_literal ':' IF condition THEN identifier_list IDENTIFIER weighting_opt ';'
    | RULE integer_literal ':' IF condition THEN conclusion_list IDENTIFIER IS IDENTIFIER weighting_opt ';'
;

weighting_opt:
    /* vacío */
    | WITH weighting_factor
    | WITH IDENTIFIER
;

condition:
    x condition_tail
    | IDENTIFIER condition_tail
;

condition_tail:
    /* vacío */
    | AND IDENTIFIER condition_tail
    | OR IDENTIFIER condition_tail
    | AND x condition_tail
    | OR x condition_tail
;

x:
    NOT x
    | NOT IDENTIFIER
    | subcondition
    | '(' condition ')'
;

subcondition:
    IDENTIFIER IS IDENTIFIER
    | IDENTIFIER IS NOT IDENTIFIER
;

conclusion_list:
    IDENTIFIER IS IDENTIFIER ','
    | conclusion_list IDENTIFIER IS IDENTIFIER ','
;

weighting_factor:
    numeric_literal
;

option_block_list:
    /* vacío */
    | option_block_list option_block
;

option_block:
    OPTION pragma_list END_OPTION
;

numeric_literal:
    integer_literal
    | real_literal
;

/* ------------------------------------- Pragmas ------------------------------------------ */

pragma_list:
    /* vacío */
    | pragma_list pragma
;

pragma:
    PRAGMA IDENTIFIER 
    | PRAGMA IDENTIFIER INTEGER_NUMBER
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

var_init_decl_list:
    var_init_decl
    | var_init_decl_list ';' var_init_decl
;

var_init_decl:
    IDENTIFIER ':' type_declaration
    | identifier_list IDENTIFIER ':' type_declaration
    | var1_init_decl
    | fb_name_decl
;

type_declaration:
    IDENTIFIER
    | BOOL opt_edge
    | STRING opt_single_byte_string_spec
    | WSTRING opt_double_byte_string_spec
    | initialized_structure
    | array_spec_init
;

opt_edge:
    /* vacio */
    | R_EDGE 
    | F_EDGE
;

opt_single_byte_string_spec:
    /* vacio */
    | single_byte_string_spec
;

opt_double_byte_string_spec:
    /* vacio */
    | double_byte_string_spec
;

var1_init_decl:
    IDENTIFIER ':' spec_init_type
    | identifier_list IDENTIFIER ':' spec_init_type
;

spec_init_type:
    simple_spec_init
    | subrange_spec_init
    | enumerated_spec_init
;

simple_spec_init:
    simple_specification
    | simple_specification ':' '=' constant
    | initialized_constant
;

simple_specification:
    elementary_type_name
;

/* ----------------------- Elementary Types --------------------------- */

elementary_type_name:
    numeric_type_name
    | date_type_name
    | bit_string_type_name
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
    bit_string_type_name_without_bool
;

/* ----------------------- Literals --------------------------- */

constant:
    numeric_literal
    | CHARACTER_STRING
    | time_literal
    | bit_string_literal
    | boolean_literal
;

integer_literal:
    integer_type_name_opt type_integer_literal
;

integer_type_name_opt:
    /* vacio */
    integer_type_name '#'
;

type_integer_literal:
    SIGNED_INTEGER
    | BINARY_INTEGER
    | OCTAL_INTEGER
    | HEX_INTEGER
;

real_literal:
    real_type_name_opt REAL_NUMBER
;

real_type_name_opt:
    /* vacio */
    | real_type_name '#'
;

time_literal:
    duration_literal
    | time_of_day_literal
    | date_literal
    | date_and_time_literal
;

duration_literal:
    'T' '#' interval
    | TIME '#' interval
    | 'T' '#' '-' interval
    | TIME '#' '-' interval
;

interval:
    days
    | hours 
    | minutes
    | seconds
    | milliseconds
;

days:
    fixed_point 'd'
    | INTEGER_NUMBER 'd' hours
    | INTEGER_NUMBER 'd' '_' hours
;
hours:
    fixed_point 'h'
    | INTEGER_NUMBER 'h' minutes
    | INTEGER_NUMBER 'h' '_' minutes
;

minutes:
    fixed_point 'm'
    | INTEGER_NUMBER 'm' seconds
    | INTEGER_NUMBER 'm' '_' seconds
;

seconds:
    fixed_point 's'
    | INTEGER_NUMBER 's' milliseconds
    | INTEGER_NUMBER 's' '_' milliseconds
;

milliseconds:
    fixed_point 'm''s'
;

fixed_point:
    INTEGER_NUMBER 
    | INTEGER_NUMBER '.' INTEGER_NUMBER;
;

time_of_day_literal:
    TIME_OF_DAY '#' daytime
    | TOD '#' daytime
;

daytime:
    INTEGER_NUMBER ':' INTEGER_NUMBER ':' fixed_point
;

date_literal:
    DATE '#' date
    |'D' '#' date
;

date:
    INTEGER_NUMBER '-' INTEGER_NUMBER '-' INTEGER_NUMBER
;

date_and_time_literal:
    DATE_AND_TIME '#' date_literal '-' daytime
;

boolean_literal:
    boolean_type_name_opt '0'
    | boolean_type_name_opt '1'
    | TRUE 
    | FALSE
;

boolean_type_name_opt:
    /* vacio */
    | BOOL '#'
;

bit_string_literal :
    bit_string_type_name_opt bit_string_integer_literals
;

bit_string_type_name_opt:
    /* vacio */
    | bit_string_type_name '#'
;

bit_string_type_name_without_bool:
    BYTE 
    | WORD 
    | DWORD 
    | LWORD
;

bit_string_integer_literals:
    INTEGER_NUMBER
    | BINARY_INTEGER
    | OCTAL_INTEGER
    | HEX_INTEGER
;

/* ----------------------- What? --------------------------- */

subrange_spec_init:
    subrange_specification
    | subrange_specification ':' '=' SIGNED_INTEGER
;

subrange_specification:
    integer_type_name '(' subrange ')'
;

subrange:
    SIGNED_INTEGER '.' '.' SIGNED_INTEGER
;

enumerated_spec_init:
    initialized_variable
    | initialized_enumerate
    | enumerated_specification ':' '=' IDENTIFIER
    | enumerated_specification ':' '=' IDENTIFIER '#' IDENTIFIER
;

enumerated_specification:
    '(' IDENTIFIER ')'
    | '(' IDENTIFIER '#' IDENTIFIER ')'
    | '(' identifier_list IDENTIFIER ')'
    | '(' enumerated_list IDENTIFIER '#' IDENTIFIER ')'
;
enumerated_list:
    IDENTIFIER '#' IDENTIFIER ','
    | enumerated_list IDENTIFIER '#' IDENTIFIER ','
;

array_spec_init:
    array_specification
    | array_specification ':' '=' array_initialization
;

array_specification:
    ARRAY '[' subrange_list ']' OF IDENTIFIER
    | ARRAY '[' subrange_list ']' OF non_generic_type_name
;

subrange_list:
    subrange
    | subrange ',' subrange_list
;

non_generic_type_name:
    elementary_type_name
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
    | INTEGER_NUMBER '(' IDENTIFIER ')'
    | INTEGER_NUMBER '(' IDENTIFIER '#' IDENTIFIER ')'
    | INTEGER_NUMBER '(' array_initial_element ')'
;

array_initial_element:
    constant
    | structure_initialization
    | array_initialization
;

structure_initialization:
    '(' structure_element_initialization_list ')'
;

structure_element_initialization_list:
    structure_element_initialization
    | structure_element_initialization_list ',' structure_element_initialization
;

structure_element_initialization:
    initialized_constant
    | initialized_variable
    | initialized_enumerate
    | IDENTIFIER ':' '=' structure_element_type
;

structure_element_type:
    array_initialization 
    | structure_initialization
;

initialized_variable:
    IDENTIFIER ':' '=' IDENTIFIER 
;

initialized_constant:
    IDENTIFIER ':' '=' constant 
;

initialized_enumerate:
    IDENTIFIER ':' '=' IDENTIFIER '#' IDENTIFIER 
;

initialized_structure:
    IDENTIFIER ':' '=' structure_initialization
;

fb_name_decl:
    IDENTIFIER ':' standard_function_block_name
    | identifier_list IDENTIFIER ':' standard_function_block_name
    | IDENTIFIER ':' standard_function_block_name ':' '=' structure_initialization
    | identifier_list IDENTIFIER ':' standard_function_block_name ':' '=' structure_initialization
;

identifier_list:
    IDENTIFIER ','
    | identifier_list IDENTIFIER ','
;

standard_function_block_name:
    // VER QUE HACEMOS
    STD_FB_IDENTIFIER
;

single_byte_string_spec:
    STRING '[' INTEGER_NUMBER ']'
    | STRING ':' '=' CHARACTER_STRING
    | STRING '[' INTEGER_NUMBER ']' ':' '=' CHARACTER_STRING
;

double_byte_string_spec:
    WSTRING '[' INTEGER_NUMBER ']'
    | WSTRING ':' '=' CHARACTER_STRING
    | WSTRING '[' INTEGER_NUMBER ']' ':' '=' CHARACTER_STRING
;

%% /* ------------------------------- Código C ------------------------------------ */

int main() {
    return yyparse();
}

int yyerror(const char *s) {
    fprintf(stderr, "Error: %s\n", s);
    return 1;
}