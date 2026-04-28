%language "Java"
%define api.parser.class {Parser}
%define api.package {parser}
%define api.parser.public

%code imports {
    import java.util.List;
    import java.util.ArrayList;
    import utils.enums.*;
    import utils.SymbolTable;
    import utils.builders.LexemeInfoBuilder;
    import parser.publisher.*;
}

%parse-param { SymbolTable symbolTable }

%token TYPE END_TYPE STRUCT END_STRUCT
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
%token <String> IDENTIFIER STD_FB_IDENTIFIER
%token ASSIGN_OP RANGE_OP
%token PRAGMA

/* -------------------------------- Tokens Anexo B -------------------------------------- */

%token VAR_INPUT RETAIN NON_RETAIN END_VAR BOOL R_EDGE F_EDGE VAR_OUTPUT VAR CONSTANT
%token STRING WSTRING BYTE WORD DWORD LWORD
%token TIME TIME_OF_DAY DATE DATE_AND_TIME
%token OF
%token SINT INT DINT LINT USINT UINT UDINT ULINT REAL LREAL
%token TRUE FALSE
%token <String> NUMERIC_LITERAL STRING_LITERAL TIME_LITERAL
%token ARRAY

%type <List<Publisher>>
    var_init_decl_list

%type <Publisher>
    var_init_decl
    output_declarations

%type <List<String>>
    identifier_list
    enumerated_list
    enumerated_specification

%type <Subtype>
    signed_integer_type_name
    unsigned_integer_type_name
    integer_type_name
    real_type_name
    numeric_type_name
    date_type_name
    bit_string_type_name
    elementary_type_name

%type <LexemeInfoBuilder>
    spec_init_type
    simple_spec_init
    initialized_constant
    initialized_spec_custom_type
    initialized_custom_type
    subrange
    subrange_spec_init
    subrange_specification
    enumerated_spec_init

%type <String>
    constant
    number
    time

%%

/* ---------------------------------- IEC61131-7 ---------------------------------------- */

program:
    opt_data_type_declaration
    function_block_declaration
;

/* -------------------------------- FUNCTION_BLOCK -------------------------------------- */

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
    opt_range
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
    number
;

point_list:
    point
    | point_list point
;

point:
    '(' number ',' number ')'
    | '(' IDENTIFIER ',' number ')'
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
    number
    | NC
;

opt_range:
    /* vacio */
    | RANGE '(' number RANGE_OP number ')' ';'
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
    opt_operator_or operator_and_opt ';'
;

opt_operator_or:
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
    | WITH number
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

/* ------------------------------------ Pragmas ----------------------------------------- */

pragma_list:
    pragma
    | pragma_list pragma
;

pragma:
    PRAGMA IDENTIFIER ';'
    | PRAGMA IDENTIFIER NUMERIC_LITERAL ';'
;

/* ------------------------------ IEC61131-3 Annex B ------------------------------------ */

output_declarations:
    VAR_OUTPUT var_retain_spec var_init_decl_list ';' END_VAR
    { Compound c = new Compound($3); c.source(Source.OUT).publish(); $$ = c; }
;

// Todo completar los 2 tipos de declaraciones restantes
input_declarations:
    VAR_INPUT var_retain_spec var_init_decl_list ';' END_VAR
;

var_declarations:
    VAR var_constant_spec var_init_decl_list ';' END_VAR
;

var_retain_spec:
    /* vacio */
    | RETAIN
    | NON_RETAIN
;

var_constant_spec:
    /* vacio */
    | CONSTANT
;

var_init_decl_list:
    var_init_decl
    {
        List<Publisher> list = new ArrayList<>();
        list.add($1);
        $$ = list;
    }
    | var_init_decl_list ';' var_init_decl { (( List<Publisher>) $$).add($3); }
;

var_init_decl:
    // todo: quitar los null
    identifier_list ':' type_specification { $$ = null; }
    | identifier_list ':' spec_init_type { $$ = new Declaration(this.symbolTable, $1, $3.use(Use.VARIABLE)); }
    | fb_name_decl { $$ = null; }
;

type_specification:
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

spec_init_type:
    simple_spec_init { $$ = $1; }
    | subrange_spec_init { $$ = $1; }
    | enumerated_spec_init { $$ = $1; }
;

simple_spec_init:
    elementary_type_name
    {
        $$ = new LexemeInfoBuilder().type(Type.SIMPLE).subtype($1);
    }
    | elementary_type_name ASSIGN_OP constant
    {
        $$ = new LexemeInfoBuilder().type(Type.SIMPLE).subtype($1).initialValue($3);
    }
    | initialized_constant  
    { 
        $$ = $1.type(Type.SIMPLE); 
    }
;

/* -------------------------------- Elementary Types ------------------------------------ */

elementary_type_name:
    numeric_type_name               { $$ = $1; }
    | date_type_name                { $$ = $1; }
    | bit_string_type_name          { $$ = $1; }
;

numeric_type_name:
    integer_type_name               { $$ = $1; }
    | real_type_name                { $$ = $1; }
;

integer_type_name:
    signed_integer_type_name        { $$ = $1; }
    | unsigned_integer_type_name    { $$ = $1; }
;

signed_integer_type_name:
    SINT   { $$ = Subtype.SINT; }
    | INT  { $$ = Subtype.INT;  }
    | DINT { $$ = Subtype.DINT; }
    | LINT { $$ = Subtype.LINT; }
;

unsigned_integer_type_name:
    USINT   { $$ = Subtype.USINT; }
    | UINT  { $$ = Subtype.UINT;  }
    | UDINT { $$ = Subtype.UDINT; }
    | ULINT { $$ = Subtype.ULINT; }
;

real_type_name:
    REAL    { $$ = Subtype.REAL;  }
    | LREAL { $$ = Subtype.LREAL; }
;

date_type_name:
    TIME            { $$ = Subtype.TIME;          }
    | DATE          { $$ = Subtype.DATE;          }
    | TIME_OF_DAY   { $$ = Subtype.TIME_OF_DAY;   }
    | DATE_AND_TIME { $$ = Subtype.DATE_AND_TIME; }
;

/* ----------------------------------- Literals ----------------------------------------- */

constant:
    STRING_LITERAL
    | time
    | number
;

number:
    NUMERIC_LITERAL
    | TRUE
    {
        this.symbolTable.put(
            "TRUE" ,
            new LexemeInfoBuilder()
                .use(Use.LITERAL)
                .type(Type.SIMPLE)
                .subtype(Subtype.BOOL)
                .build()
        );
        $$ = "TRUE";
    }
    | FALSE
    {
        this.symbolTable.put(
            "FALSE" ,
            new LexemeInfoBuilder()
                .use(Use.LITERAL)
                .type(Type.SIMPLE)
                .subtype(Subtype.BOOL)
                .build()
        );
        $$ = "FALSE";
    }
    | number_prefix NUMERIC_LITERAL
    {
        // todo: hacer conversion de esta constante en codigo
        $$ = "";
    }
;

number_prefix:
    integer_type_name '#'
    | real_type_name '#'
    | bit_string_type_name '#'
;

time:
    date_type_name '#' TIME_LITERAL {
        // Accion semantica que verifica que prefix es del mismo tipo que time_literal
    }
;

bit_string_type_name:
    BYTE    { $$ = Subtype.BYTE;  }
    | WORD  { $$ = Subtype.WORD;  }
    | DWORD { $$ = Subtype.DWORD; }
    | LWORD { $$ = Subtype.LWORD; }
;

/* --------------------------------- Derived Types  ------------------------------------- */

subrange_spec_init:
    subrange_specification  { $$ = $1; }
    | subrange_specification ASSIGN_OP NUMERIC_LITERAL { $$ = $1.initialValue($3); }
;

subrange_specification:
    integer_type_name '(' subrange ')' 
    { 
        $$ = $3.subtype($1);
    }
;

subrange:
    NUMERIC_LITERAL RANGE_OP NUMERIC_LITERAL
    {
        // todo: hacer chequeo semantico y pasaje a valor (.value) en analisis lexico

        Integer ilim = (Integer) this.symbolTable.get($1).initialValue;
        Integer slim = (Integer) this.symbolTable.get($3).initialValue;

        $$ = new LexemeInfoBuilder()
            .type(Type.SUBRANGE)
            .inferiorLimit(ilim)
            .superiorLimit(slim);
    }
;

enumerated_spec_init:
    initialized_custom_type         
    { $$ = $1.type(Type.ENUMERATE); }
    | initialized_spec_custom_type  
    { $$ = $1.type(Type.ENUMERATE); }
    | enumerated_specification ASSIGN_OP IDENTIFIER
    {
        $$ = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters($1)
            .initialValue($3);
    }
    | enumerated_specification ASSIGN_OP IDENTIFIER '#' IDENTIFIER
    {
        $$ = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters($1)
            .initialValue($5);
            //TODO name mangling:
    }
;

enumerated_specification:
    '(' enumerated_list')' { $$ = $2; }
;

enumerated_list:
    IDENTIFIER
    {
        List<String> l = new ArrayList<String>();
        l.add($1);
        $$ = l;
    }
    | enumerated_list ',' IDENTIFIER
    {
        $1.add($3);
        $$ = $1;
    }
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
    | initialized_custom_type
    | initialized_spec_custom_type
    | IDENTIFIER ASSIGN_OP structure_element_type
;

structure_element_type:
    array_initialization 
    | structure_initialization
;

initialized_constant:
    IDENTIFIER ASSIGN_OP constant
    {
        $$ = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType($1)
            .initialValue($3);
    }
;

initialized_custom_type:
    IDENTIFIER ASSIGN_OP IDENTIFIER
    {
        $$ = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType($1)
            .initialValue($3);
    }
;

initialized_spec_custom_type:
    IDENTIFIER ASSIGN_OP IDENTIFIER '#' IDENTIFIER
    {
        $$ = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType($1)
            .initialValue($5);
            // TODO: name mangling
    }
;

initialized_structure:
    IDENTIFIER ASSIGN_OP structure_initialization
;

// Todo: ver que hacer con las funciones de la biblioteca estandar
fb_name_decl:
    identifier_list ':' standard_function_block_name
    | identifier_list ':' standard_function_block_name ASSIGN_OP structure_initialization
;

identifier_list:
    IDENTIFIER
    {
        List<String> l = new ArrayList<String>();
        l.add($1);
        $$ = l;
    }
    | identifier_list ',' IDENTIFIER { $1.add($3); $$ = $1; }
;

/* No hay informacion en el estandar de este tipo de funciones */
standard_function_block_name:
    STD_FB_IDENTIFIER
;

string_specification:
    type_string
    | type_string '[' NUMERIC_LITERAL ']'
    | type_string ASSIGN_OP STRING_LITERAL
    | type_string '[' NUMERIC_LITERAL ']' ASSIGN_OP STRING_LITERAL
;

type_string:
    STRING
    | WSTRING
;

/* ----------------------------------- Data Type ---------------------------------------- */

opt_data_type_declaration:
    /* vacio */
    | data_type_declaration
;

data_type_declaration:
    TYPE type_declaration_list END_TYPE
;

type_declaration_list:
    type_declaration ';'
    | type_declaration_list type_declaration ';'
;

type_declaration:
    IDENTIFIER ':' specification_list
;

specification_list:
    spec_init_type
    | array_spec_init
    | structure_specification
    | string_specification
;

structure_specification:
    initialized_structure
    | structure_declaration
;

structure_declaration:
    STRUCT structure_element_declaration_list END_STRUCT
;

structure_element_declaration_list:
    structure_element_declaration ';'
    | structure_element_declaration_list structure_element_declaration ';'
;

structure_element_declaration:
    IDENTIFIER ':' specification_sublist
;

specification_sublist:
    spec_init_type
    | array_spec_init
    | initialized_structure
    | string_specification
;

%%