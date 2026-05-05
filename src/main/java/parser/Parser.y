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
    type_declaration_list

%type <Publisher>
    var_init_decl
    output_declarations
    input_declarations
    var_declarations
    data_type_declaration
    type_declaration

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
    unified_specification
    custom_spec_init
    simple_spec_init
    enumerated_spec_init
    subrange_spec_init
    // array_spec_init
    // string_spec_init
    // structure_declaration
    initialized_constant
    initialized_identifier
    subrange
    subrange_specification
    specification_list

%type <String>
    opt_scope_and_value
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
    /* empty */
    | fb_io_var_declarations_list fb_io_var_declarations
;

fb_io_var_declarations:
    input_declarations
    | output_declarations
;

other_var_declarations_list:
    /* empty */
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
    /* empty */
    | fuzzify_block_list fuzzify_block
;

fuzzify_block:
    FUZZIFY IDENTIFIER
    linguistic_term_list
    END_FUZZIFY
;

defuzzify_block_list:
    /* empty */
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
    /* empty */
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
    /* empty */
    | RANGE '(' number RANGE_OP number ')' ';'
;

rule_block_list:
    /* empty */
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
    /* empty */
    | OR ':' or_type
;

operator_and_opt:
    /* empty */
    | AND ':' and_type
;

or_type:
    MAX | ASUM | BSUM
;

and_type:
    MIN | PROD | BDIF
;

activation_method_opt:
    /* empty */
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
    /* empty */
    | rule_list rule
;

rule:
    RULE NUMERIC_LITERAL ':' IF condition THEN conclusion_list opt_weighting ';'
;

opt_weighting:
    /* empty */
    | WITH number
    | WITH IDENTIFIER
;

condition:
    x condition_tail
    | IDENTIFIER condition_tail
;

condition_tail:
    /* empty */
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
    /* empty */
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

// TODO: Ver que hacer con el retain
output_declarations:
    VAR_OUTPUT var_retain_spec var_init_decl_list ';' END_VAR
    { 
        Compound c = new Compound($3); 
        c.source(Source.OUT).publish(); 
        $$ = c; 
    }
;

input_declarations:
    VAR_INPUT var_retain_spec var_init_decl_list ';' END_VAR
    { 
        Compound c = new Compound($3); 
        c.source(Source.IN).publish();
        $$ = c; 
    }
;

var_declarations:
    VAR var_constant_spec var_init_decl_list ';' END_VAR
    { 
        Compound c = new Compound($3); 
        c.source(Source.NONE).publish(); 
        $$ = c; 
    }
;

var_retain_spec:
    /* empty */
    | RETAIN
    | NON_RETAIN
;

var_constant_spec:
    /* empty */
    | CONSTANT
;

var_init_decl_list:
    var_init_decl
    {
        List<Publisher> list = new ArrayList<>();
        list.add($1);
        $$ = list;
    }
    | var_init_decl_list ';' var_init_decl 
    { 
        $1.add($3);
        $$ = $1;
    }
;

// todo: quitar los null
var_init_decl:
    identifier_list ':' unified_specification
    {
        $$ = new Declaration(this.symbolTable, $1, $3.use(Use.VARIABLE));
    }
    | fb_name_decl
    {
        $$ = null;
    }
;

// todo: quitar los null
unified_specification:
    custom_spec_init
    {
        $$ = $1.type(Type.SIMPLE);
    }
    | BOOL opt_edge         { $$ = null; }
    | simple_spec_init      { $$ = $1; }
    | subrange_spec_init    { $$ = $1; }
    | enumerated_spec_init  { $$ = $1; }
    | array_spec_init       { $$ = null; }
    | string_spec_init      { $$ = null; }
;

// todo: quitar los null
custom_spec_init:
    IDENTIFIER
    {
        $$ = new LexemeInfoBuilder()
                    .subtype(Subtype.CUSTOM)
                    .customType($1)
                    .initialValue(this.symbolTable.get($1).initialValue);
    }
    | initialized_constant      { $$ = $1; }
    | initialized_structure     { $$ = null; }
    | initialized_identifier    { $$ = $1; }
;

opt_edge:
    /* empty */
    | R_EDGE
    | F_EDGE
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
    STRING_LITERAL  { $$ = $1; }
    | time          { $$ = $1; }
    | number        { $$ = $1; }
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
    enumerated_specification
    {
        $$ = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters($1)
            .initialValue($1.get(0));
    }
    | enumerated_specification ASSIGN_OP opt_scope_and_value
    {
        $$ = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters($1)
            .initialValue($3);
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
    | initialized_identifier
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
            .initialValue(this.symbolTable($3).initialValue);
    }
;

initialized_identifier:
    IDENTIFIER ASSIGN_OP opt_scope_and_value
    {
        $$ = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType($1)
            .initialValue($3);
    }
;

opt_scope_and_value:
    IDENTIFIER
    {
        $$ = $1;
    }
    // TODO name mangling para opt_scope
    | IDENTIFIER '#' IDENTIFIER
    {
        $$ = $3;
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

string_spec_init:
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
    /* empty */
    | data_type_declaration
;

data_type_declaration:
    TYPE type_declaration_list END_TYPE
    {
        Publisher pub = new Compound($2);
        pub.publish();
        $$ = pub;
    }
;

type_declaration_list:
    type_declaration ';'
    {
        List<Publisher> list = new ArrayList<>();
        list.add($1);
        $$ = list;
    }
    | type_declaration_list type_declaration ';'
    {
        $1.add($2);
        $$ = $1;
    }
;

type_declaration:
    IDENTIFIER ':' specification_list
    {
        List<String> element = new ArrayList<>();
        element.add($1);
        $$ = new Declaration(this.symbolTable, element, $3);
    }
;

specification_list:
    custom_spec_init        { $$ = $1.use(Use.TYPE); }
    | simple_spec_init      { $$ = $1.use(Use.TYPE); }
    | enumerated_spec_init  { $$ = $1.use(Use.TYPE); }
    | subrange_spec_init    { $$ = $1.use(Use.TYPE); }
    | array_spec_init       { $$ = null; }
    | structure_declaration { $$ = null; }
    | string_spec_init      { $$ = null; }
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
    custom_spec_init
    | simple_spec_init
    | enumerated_spec_init
    | subrange_spec_init
    | array_spec_init
    | string_spec_init
;

%%