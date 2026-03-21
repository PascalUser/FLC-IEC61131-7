%language "Java"
%define api.parser.class {Parser}
%define api.package {lexer_parser}
%define api.parser.public

%code imports {
  import java.io.*;
}

%debug

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
%token IDENTIFIER STD_FB_IDENTIFIER
%token ASSIGN_OP
%token PRAGMA interval

/* ----------------------- Tokens Anexo B --------------------------- */

%token VAR_INPUT RETAIN NON_RETAIN END_VAR BOOL R_EDGE F_EDGE VAR_OUTPUT VAR CONSTANT
%token STRING WSTRING BYTE WORD DWORD LWORD TIME TIME_OF_DAY TOD DATE DATE_AND_TIME DT 
%token OF
%token SINT INT DINT LINT USINT UINT UDINT ULINT REAL LREAL
%token TRUE FALSE
%token CHARACTER_STRING
%token ARRAY RANGE_OP
%token NUMERIC_LITERAL

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
    /* vacio */
    | fb_io_var_declarations_list fb_io_var_declarations
;

fb_io_var_declarations:
    input_declarations
    | output_declarations
;

other_var_declarations_list:
    /* vacio */
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
    /* vacio */
    | fuzzify_block_list fuzzify_block
;

fuzzify_block:
    FUZZIFY IDENTIFIER
    linguistic_term_list
    END_FUZZIFY
;

defuzzify_block_list:
    /* vacio */
    | defuzzify_block_list defuzzify_block
;

defuzzify_block:
    DEFUZZIFY IDENTIFIER
    range_opt
    opt_linguistic_term_list
    defuzzification_method
    default_value
    END_DEFUZZIFY
;

opt_linguistic_term_list:
    /* vacio */
    | linguistic_term_list
;

linguistic_term_list:
    linguistic_term
    | linguistic_term_list linguistic_term
;

linguistic_term:
    TERM IDENTIFIER ASSIGN_OP IDENTIFIER ';'
    | TERM IDENTIFIER ASSIGN_OP membership_function ';'
;

membership_function:
    singleton
    | point_list
;

singleton:
    numeric_constant
;

point_list:
    point
    | point_list point
;

point:
    '(' numeric_constant ',' numeric_constant ')'
    | '(' IDENTIFIER ',' numeric_constant ')'
;

defuzzification_method:
    METHOD ':' defuzz_method ';'
;

defuzz_method:
    COG | COGS | COA | LM | RM
;

default_value:
    DEFAULT ASSIGN_OP default_val ';'
;

default_val:
    numeric_constant
    | NC
;

range_opt:
    /* vacio */
    | RANGE '(' numeric_constant '.''.' numeric_constant ')' ';'
;

rule_block_list:
    /* vacio */
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
    /* vacio */
    | OR ':' or_type
;

operator_and_opt:
    /* vacio */
    | AND ':' and_type
;

or_type:
    MAX | ASUM | BSUM
;

and_type:
    MIN | PROD | BDIF
;

activation_method_opt:
    /* vacio */
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
    /* vacio */
    | rule_list rule
;

rule:
    RULE NUMERIC_LITERAL ':' IF condition THEN conclusion_list opt_weighting ';'
;

opt_weighting:
    /* vacio */
    | WITH numeric_constant
    | WITH IDENTIFIER
;

condition:
    x condition_tail
    | IDENTIFIER condition_tail
;

condition_tail:
    /* vacio */
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
    IDENTIFIER IS IDENTIFIER
    | IDENTIFIER
    | conclusion_list ',' IDENTIFIER IS IDENTIFIER
    | conclusion_list ',' IDENTIFIER
;

option_block_list:
    /* vacio */
    | option_block_list option_block
;

option_block:
    OPTION pragma_list END_OPTION
;

/* ------------------------------------- Pragmas ------------------------------------------ */

pragma_list:
    /* vacio */
    | pragma_list pragma
;

pragma:
    PRAGMA IDENTIFIER 
    | PRAGMA IDENTIFIER NUMERIC_LITERAL
;

/* ------------------------------- IEC61131-3 Annex B ------------------------------------ */

output_declarations:
    VAR_OUTPUT var_retain_spec var_init_decl_list ';' END_VAR
;

input_declarations:
    VAR_INPUT var_retain_spec input_declaration_list ';' END_VAR
;

input_declaration_list:
    input_declaration
    | input_declaration_list ';' input_declaration
;

input_declaration:
    var_init_decl
;

var_declarations:
    VAR var_constant_spec var_init_decl_list ';' END_VAR
;

var_retain_spec:
    RETAIN
    | NON_RETAIN
    | /* vacio */
;

var_constant_spec:
    CONSTANT
    | /* vacio */
;

var_init_decl_list:
    var_init_decl
    | var_init_decl_list ';' var_init_decl
;

var_init_decl:
    identifier_list ':' type_declaration
    | var1_init_decl
    | fb_name_decl
;

type_declaration:
    IDENTIFIER
    | BOOL opt_edge
    | string_specification
    | initialized_structure
    | array_spec_init
;

opt_edge:
    /* vacio */
    | R_EDGE 
    | F_EDGE
;

var1_init_decl:
    identifier_list ':' spec_init_type
;

spec_init_type:
    simple_spec_init
    | subrange_spec_init
    | enumerated_spec_init
;

simple_spec_init:
    simple_specification
    | simple_specification ASSIGN_OP constant
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
    CHARACTER_STRING
    | time_literal
    | numeric_constant
;

numeric_constant:
    NUMERIC_LITERAL
    | integer_literal
    | real_literal
    | bit_string_literal
    | boolean_literal
;

integer_literal:
    integer_type_name '#' NUMERIC_LITERAL
;

real_literal:
    real_type_name '#' NUMERIC_LITERAL
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

// Que pasa si comentamos esta seccion y detectamos el token el tipo de dato directamente?
// Ejemplo, detectamos con expresiones regulares 1d3h en lugar de tener todos los tokens.
/*
interval:
    days
    | hours 
    | minutes
    | seconds
    | milliseconds
;

days:
    fixed_point 'd'
    | NUMERIC_LITERAL 'd' hours
    | NUMERIC_LITERAL 'd' '_' hours
;
hours:
    fixed_point 'h'
    | NUMERIC_LITERAL 'h' minutes
    | NUMERIC_LITERAL 'h' '_' minutes
;

minutes:
    fixed_point 'm'
    | NUMERIC_LITERAL 'm' seconds
    | NUMERIC_LITERAL 'm' '_' seconds
;

seconds:
    fixed_point 's'
    | NUMERIC_LITERAL 's' milliseconds
    | NUMERIC_LITERAL 's' '_' milliseconds
;

milliseconds:
    fixed_point 'm''s'
;
*/

fixed_point:
    NUMERIC_LITERAL 
    | NUMERIC_LITERAL '.' NUMERIC_LITERAL
;

time_of_day_literal:
    TIME_OF_DAY '#' daytime
    | TOD '#' daytime
;

daytime:
    NUMERIC_LITERAL ':' NUMERIC_LITERAL ':' fixed_point
;

date_literal:
    DATE '#' date
    |'D' '#' date
;

date:
    NUMERIC_LITERAL '-' NUMERIC_LITERAL '-' NUMERIC_LITERAL
;

date_and_time_literal:
    DATE_AND_TIME '#' date_literal '-' daytime
;

boolean_literal:
    BOOL '#' NUMERIC_LITERAL
    | TRUE 
    | FALSE
;

bit_string_literal :
    bit_string_type_name '#' NUMERIC_LITERAL
;

bit_string_type_name_without_bool:
    BYTE 
    | WORD 
    | DWORD 
    | LWORD
;

/* ----------------------- What? --------------------------- */

subrange_spec_init:
    subrange_specification
    | subrange_specification ASSIGN_OP NUMERIC_LITERAL
;

subrange_specification:
    integer_type_name '(' subrange ')'
;

subrange:
    NUMERIC_LITERAL RANGE_OP NUMERIC_LITERAL
;

enumerated_spec_init:
    initialized_variable
    | initialized_enumerate
    | enumerated_specification ASSIGN_OP IDENTIFIER
    | enumerated_specification ASSIGN_OP IDENTIFIER '#' IDENTIFIER
;

enumerated_specification:
    '(' enumerated_list')'
;

enumerated_list:
    IDENTIFIER '#' IDENTIFIER
    | IDENTIFIER
    | enumerated_list ',' IDENTIFIER '#' IDENTIFIER
    | enumerated_list ',' IDENTIFIER
;

array_spec_init:
    array_specification
    | array_specification ASSIGN_OP array_initialization
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
    | NUMERIC_LITERAL '(' IDENTIFIER ')'
    | NUMERIC_LITERAL '(' IDENTIFIER '#' IDENTIFIER ')'
    | NUMERIC_LITERAL '(' array_initial_element ')'
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
    | IDENTIFIER ASSIGN_OP structure_element_type
;

structure_element_type:
    array_initialization 
    | structure_initialization
;

initialized_variable:
    IDENTIFIER ASSIGN_OP IDENTIFIER
;

initialized_constant:
    IDENTIFIER ASSIGN_OP constant 
;

initialized_enumerate:
    IDENTIFIER ASSIGN_OP IDENTIFIER '#' IDENTIFIER 
;

initialized_structure:
    IDENTIFIER ASSIGN_OP structure_initialization
;

fb_name_decl:
    identifier_list ':' standard_function_block_name
    | identifier_list ':' standard_function_block_name ASSIGN_OP structure_initialization
;

identifier_list:
    IDENTIFIER
    | identifier_list ',' IDENTIFIER
;

/* No hay informacion en el estandar de este tipo de funciones */
standard_function_block_name:
    STD_FB_IDENTIFIER
;

string_specification:
    type_string
    | type_string '[' NUMERIC_LITERAL ']'
    | type_string ASSIGN_OP CHARACTER_STRING
    | type_string '[' NUMERIC_LITERAL ']' ASSIGN_OP CHARACTER_STRING
;

type_string:
    STRING
    | WSTRING
;

%% /* ------------------------------- Código Java ------------------------------------ */