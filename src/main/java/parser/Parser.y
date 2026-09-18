%language "Java"
%define api.parser.class {Parser}
%define api.package {parser}
%define api.parser.public

%code imports {
    import java.util.List;
    import java.util.ArrayList;
    import java.util.Collections;

    import utils.LexemeInfo;
    import utils.SymbolTable;
    import utils.enums.*;
    import utils.builders.LexemeInfoBuilder;

    import parser.initializations.*;
    import parser.publishers.*;
    import parser.utils.NameMangler;
}

%parse-param { SymbolTable symbolTable }
%parse-param { NameMangler nameMangler }

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
%token <String> NUMERIC_LITERAL STRING_LITERAL TIME_LITERAL BOOLEAN_LITERAL
%token ARRAY

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
    // spec_init: specification (right side) that could be initialized
    var_spec_init
    type_spec_init
    custom_spec_init
    simple_spec_init
    enumerated_spec_init
    subrange_spec_init
    array_spec_init
    string_spec_init
    structure_field_spec_init
    // initialized: initialized specification
    initialized_custom
    initialized_custom_with_constant
    initialized_custom_with_identifier
    initialized_custom_with_array
    initialized_custom_with_structure
    initialized_simple
    initialized_enumerated
    initialized_subrange
    initialized_array
    initialized_string
    initialized_structure_field
    initialized_structure
    // specification: not initialized specification
    custom_specification
    simple_specification
    enumerated_specification
    subrange_specification
    array_specification
    string_specification
    structure_specification
    // other
    range

%type <Publisher>
    var_init_decl
    type_declaration

%type <List<Publisher>>
    var_init_decl_list
    type_declaration_list

%type <String>
    constant
    string_constant
    boolean_constant
    time_constant
    numeric_constant
    identifier_with_opt_mangling
    type_name
    // Structures
    structure_field_declaration

%type <List<String>>
    identifier_list
    enumerated_list
    structure_field_declaration_list

%%

/* ---------------------------------- IEC61131-7 ---------------------------------------- */

program:
    opt_data_type_declaration
    function_block_declaration
;

/* -------------------------------- FUNCTION_BLOCK -------------------------------------- */

function_block_declaration:
    FUNCTION_BLOCK IDENTIFIER
    opt_fb_io_var_declarations_list
    opt_other_var_declarations_list
    opt_function_block_body
    END_FUNCTION_BLOCK
;

opt_fb_io_var_declarations_list:
    /* empty */
    | opt_fb_io_var_declarations_list fb_io_var_declarations
;

fb_io_var_declarations:
    input_declarations
    | output_declarations
;

opt_other_var_declarations_list:
    /* empty */
    | opt_other_var_declarations_list other_var_declarations
;

other_var_declarations:
    var_declarations
;

opt_function_block_body:
    opt_fuzzify_block_list
    opt_defuzzify_block_list
    opt_rule_block_list
    opt_option_block_list
;

opt_fuzzify_block_list:
    /* empty */
    | opt_fuzzify_block_list fuzzify_block
;

fuzzify_block:
    FUZZIFY IDENTIFIER
    linguistic_term_list
    END_FUZZIFY
;

opt_defuzzify_block_list:
    /* empty */
    | opt_defuzzify_block_list defuzzify_block
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

opt_range:
    /* empty */
    | RANGE '(' numeric_constant RANGE_OP numeric_constant ')' ';'
;

opt_rule_block_list:
    /* empty */
    | opt_rule_block_list rule_block
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
    RULE numeric_constant ':' IF condition THEN conclusion_list opt_weighting ';'
;

opt_weighting:
    /* empty */
    | WITH numeric_constant
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

opt_option_block_list:
    /* empty */
    | opt_option_block_list option_block
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
    | PRAGMA IDENTIFIER numeric_constant ';'
;

/* ------------------------------ IEC61131-3 Annex B ------------------------------------ */

// TODO: Ver que hacer con el retain
output_declarations:
    VAR_OUTPUT var_retain_spec var_init_decl_list ';' END_VAR
    { 
        Compound variables = new Compound($3);
        variables.source(Source.OUT).publish();
    }
;

input_declarations:
    VAR_INPUT var_retain_spec var_init_decl_list ';' END_VAR
    { 
        Compound variables = new Compound($3);
        variables.source(Source.IN).publish();
    }
;

var_declarations:
    VAR var_constant_spec var_init_decl_list ';' END_VAR
    { 
        Compound variables = new Compound($3);
        variables.source(Source.INTERNAL).publish();
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
        List<Publisher> declarations = new ArrayList<>();
        declarations.add($1);
        $$ = declarations;
    }
    | var_init_decl_list ';' var_init_decl 
    { 
        $1.add($3);
        $$ = $1;
    }
;

// todo: quitar los null
var_init_decl:
    identifier_list ':' var_spec_init
    {
        $$ = new Declaration(this.symbolTable, $1, $3.use(Use.VARIABLE));
    }
    | fb_name_decl
    {
        $$ = null;
    }
;

// todo: quitar los null
var_spec_init:
    custom_spec_init        { $$ = $1; }
    | boolean_spec_init     { $$ = null; }
    | simple_spec_init      { $$ = $1; }
    | subrange_spec_init    { $$ = $1; }
    | enumerated_spec_init  { $$ = $1; }
    | array_spec_init       { $$ = $1; }
    | string_spec_init      { $$ = $1; }
;

boolean_spec_init:
    boolean_specification
    | initialized_boolean
;
boolean_specification:
    BOOL
;

initialized_boolean:
    boolean_specification edge
;

edge:
    R_EDGE
    | F_EDGE
;

custom_spec_init:
    custom_specification { $$ = $1; }
    | initialized_custom   { $$ = $1; }
;

custom_specification:
    IDENTIFIER
    {
        $$ = new LexemeInfoBuilder()
                    .type(Type.SIMPLE)
                    .subtype(Subtype.CUSTOM)
                    .customType($1)
                    .initialValue(this.symbolTable.get($1).initialValue);
    }
;

initialized_custom:
    initialized_custom_with_constant     { $$ = $1.type(Type.SIMPLE); }
    | initialized_custom_with_structure  { $$ = $1.type(Type.SIMPLE); }
    | initialized_custom_with_identifier { $$ = $1.type(Type.SIMPLE); }
;

simple_spec_init:
    simple_specification { $$ = $1; }
    | initialized_simple  { $$ = $1; }

simple_specification:
    elementary_type_name
    {
        $$ = new LexemeInfoBuilder().type(Type.SIMPLE).subtype($1);
    }
;

initialized_simple:
    elementary_type_name ASSIGN_OP constant
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
    string_constant    { $$ = $1; }
    | boolean_constant { $$ = $1; }
    | time_constant    { $$ = $1; }
    | numeric_constant { $$ = $1; }
;

string_constant:
    STRING_LITERAL { $$ = $1; }
;

boolean_constant:
    BOOLEAN_LITERAL { $$ = $1; }

numeric_constant:
    NUMERIC_LITERAL
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

time_constant:
    date_type_name '#' TIME_LITERAL {
        // todo: Accion semantica que verifica que prefix es del mismo tipo que time_literal
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
    | initialized_subrange  { $$ = $1; }
;

subrange_specification:
    integer_type_name '(' range ')' 
    { 
        $$ = $3.subtype($1);
    }
;

initialized_subrange:
    subrange_specification ASSIGN_OP numeric_constant { $$ = $1.initialValue($3); }
;

range:
    numeric_constant RANGE_OP numeric_constant
    {
        // todo: hacer chequeo semantico de rangos
        $$ = new LexemeInfoBuilder()
            .type(Type.SUBRANGE)
            .inferiorLimit(Collections.singletonList($1))
            .superiorLimit(Collections.singletonList($3))
            .initialValue($1);
    }
;

enumerated_spec_init:
    enumerated_specification { $$ = $1; }
    | initialized_enumerated { $$ = $1; }
;

enumerated_specification:
    '(' enumerated_list')' {
        $$ = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters($2)
            .initialValue($2.get(0));
    }
;

initialized_enumerated:
    enumerated_specification ASSIGN_OP identifier_with_opt_mangling
    {
        $$ = $1.initialValue($3);
    }
;

enumerated_list:
    IDENTIFIER
    {
        List<String> enumerates = new ArrayList<String>();
        enumerates.add($1);
        $$ = enumerates;
    }
    | enumerated_list ',' IDENTIFIER
    {
        $1.add($3);
        $$ = $1;
    }
;

array_spec_init:
    array_specification { $$ = $1; }
    | initialized_array { $$ = $1; }
;

array_specification:
    ARRAY '[' range_list ']' OF IDENTIFIER              { $$ = null; }
    | ARRAY '[' range_list ']' OF non_generic_type_name { $$ = null; }
;

initialized_array:
    array_specification ASSIGN_OP array_initialization { $$ = null; }
;

range_list:
    range
    | range ',' range_list
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
    | repeated_initial_element
;

array_initial_element:
    constant
    | initialized_structure
    | identifier_with_opt_mangling
    | array_initialization
;

repeated_initial_element:
    numeric_constant '(' array_initial_element ')'
;

initialized_structure:
    '(' initialized_structure_field_list ')'
    {

    }
;

initialized_structure_field_list:
    initialized_structure_field
    | initialized_structure_field_list ',' initialized_structure_field
;

initialized_structure_field:
    initialized_custom_with_constant
    | initialized_custom_with_identifier
    | initialized_custom_with_array
    | initialized_custom_with_structure
;

initialized_custom_with_constant:
    IDENTIFIER ASSIGN_OP constant
    {
        $$ = new LexemeInfoBuilder()
                .subtype(Subtype.CUSTOM)
                .customType($1)
                .initialValue($3);
    }
;

initialized_custom_with_identifier:
    IDENTIFIER ASSIGN_OP identifier_with_opt_mangling
    {
        $$ = new LexemeInfoBuilder()
                .subtype(Subtype.CUSTOM)
                .customType($1)
                .initialValue($3);
    }
;

identifier_with_opt_mangling:
    IDENTIFIER
    {
        $$ = $1;
    }
    | IDENTIFIER '#' IDENTIFIER
    {
        $$ = $1 + "#" + $3;
    }
;

initialized_custom_with_array:
    IDENTIFIER ASSIGN_OP array_initialization { $$ = null; }
;

initialized_custom_with_structure:
    IDENTIFIER ASSIGN_OP initialized_structure { $$ = null; }
;

// Todo: ver que hacer con las funciones de la biblioteca estandar
fb_name_decl:
    identifier_list ':' standard_function_block_name
    | identifier_list ':' standard_function_block_name ASSIGN_OP initialized_structure
;

identifier_list:
    IDENTIFIER
    {
        // Left side identifers have a scope
        String mangledIdentifier = this.nameMangler.getNameMangled($1);

        List<String> identifiers = new ArrayList<String>();
        identifiers.add(mangledIdentifier);

        $$ = identifiers;
    }
    | identifier_list ',' IDENTIFIER
    {
        // Left side identifers have a scope
        String mangledIdentifier = this.nameMangler.getNameMangled($3);
        $1.add(mangledIdentifier);
        $$ = $1;
    }
;

/* TODO: No hay informacion en el estandar de este tipo de funciones */
standard_function_block_name:
    STD_FB_IDENTIFIER
;

string_spec_init:
    string_specification { $$ = $1; }
    | initialized_string { $$ = $1; }

string_specification:
    type_string_specification                            { $$ = null; }
    | type_string_specification '[' numeric_constant ']' { $$ = null; }

initialized_string:
    type_string_specification  ASSIGN_OP string_constant                            { $$ = null; }
    | type_string_specification '[' numeric_constant ']' ASSIGN_OP string_constant  { $$ = null; }
;

type_string_specification:
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
        Publisher declarations = new Compound($2);
        declarations.source(Source.NONE);
        declarations.publish();
    }
;

type_declaration_list:
    type_declaration ';'
    {
        List<Publisher> declarations = new ArrayList<>();
        declarations.add($1);
        $$ = declarations;
    }
    | type_declaration_list type_declaration ';'
    {
        $1.add($2);
        $$ = $1;
    }
;

type_declaration:
    type_name ':' type_spec_init
    {
        this.nameMangler.popScope();

        List<String> left_identifier = new ArrayList<>();
        left_identifier.add($1);

        $$ = new Declaration(
            this.symbolTable,
            left_identifier,
            $3.use(Use.TYPE)
        );
    }
;

type_name:
    IDENTIFIER
    {
        this.nameMangler.addScope("$1");
        $$ = $1;
    }
;

type_spec_init:
    custom_spec_init          { $$ = $1; }
    | simple_spec_init        { $$ = $1; }
    | enumerated_spec_init    { $$ = $1; }
    | subrange_spec_init      { $$ = $1; }
    | array_spec_init         { $$ = $1; }
    | structure_specification { $$ = $1; }
    | string_spec_init        { $$ = $1; }
;

structure_specification:
    STRUCT structure_field_declaration_list END_STRUCT
    {
        List<String> structParameters = $2;
        Struct structInitialValues = new Struct();

        for (String field : structParameters) {
            String fieldName = this.nameMangler.getNameMangled(field);
            LexemeInfo fieldMetadata = this.symbolTable.get(fieldName);
            structInitialValues.add(field, (Initialization) fieldMetadata.initialValue);
        }

        $$ = new LexemeInfoBuilder()
            .type(Type.STRUCT)
            .parameters(structParameters)
            .initialValue(structInitialValues);
    }
;

structure_field_declaration_list:
    structure_field_declaration ';'
    {
        List<String> structParameters = new ArrayList<>();
        structParameters.add($1);
        $$ = structParameters;
    }
    | structure_field_declaration_list structure_field_declaration ';'
    {
        $1.add($2);
        $$ = $1;
    }
;

structure_field_declaration:
    IDENTIFIER ':' structure_field_spec_init
    {
        List<String> left_identifier = new ArrayList<>();
        left_identifier.add(this.nameMangler.getNameMangled($1));

        // Builds the declaration of the structure field and publishes it
        Declaration fieldDeclaration = new Declaration(
            this.symbolTable,
            left_identifier,
            $3.use(Use.FIELD).source(Source.NONE)
        );

        $$ = $1;
    }
;

structure_field_spec_init:
    custom_spec_init        { $$ = $1; }
    | simple_spec_init      { $$ = $1; }
    | enumerated_spec_init  { $$ = $1; }
    | subrange_spec_init    { $$ = $1; }
    | array_spec_init       { $$ = $1; }
    | string_spec_init      { $$ = $1; }
;

%%