%language "Java"
%define api.parser.class {Parser}
%define api.package {parser}
%define api.parser.public
%define api.parser.final

%code imports {
    import java.util.List;
    import java.util.ArrayList;
    import java.util.Collections;

    import utils.LexemeInfo;
    import utils.SymbolTable;
    import utils.enums.*;

    import parser.utils.*;
    import parser.internals.*;
    import parser.initializations.*;
}

%code {
    private ContextHandler contexts;
}

%parse-param { SymbolTable symbolTable  }

%code init {
    this.contexts = new ContextHandler();
}

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

%type <String>
    constant
    string_constant
    boolean_constant
    time_constant
    numeric_constant
    identifier_with_opt_mangling
    type_name_declaration
    structure_field_declaration
    field_name

%type <List<String>>
    identifier_list
    enumerated_values
    enumerated_values_list
    structure_field_declaration_list

%%

/* ---------------------------------- IEC61131-7 ---------------------------------------- */

program:
    opt_data_type_declaration
    function_block_declaration
;

/* -------------------------------- FUNCTION_BLOCK -------------------------------------- */

function_block_declaration:
    FUNCTION_BLOCK function_block_name
    opt_fb_io_var_declarations_list
    opt_other_var_declarations_list
    opt_function_block_body
    END_FUNCTION_BLOCK
    {
        /**
         * Pops the function block context
        **/

        this.contexts.pop();
    }
;

function_block_name:
    IDENTIFIER
    {
        /**
         * Builds a new context and adds the function block name to the outer scope
        **/

        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.outerScopes().addScope($1);

        this.contexts.add(ctx);
    }
;

opt_fb_io_var_declarations_list:
    /* empty */
    | opt_fb_io_var_declarations_list fb_io_var_declarations
;

fb_io_var_declarations:
    io_var_decl var_retain_spec var_init_decl_list ';' END_VAR
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

io_var_decl:
    VAR_INPUT
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.IN).use(Use.VARIABLE);
    }
    | VAR_OUTPUT
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.OUT).use(Use.VARIABLE);
    }
;

var_declarations:
    var_id_decl var_constant_spec var_init_decl_list ';' END_VAR
;

var_id_decl:
    VAR
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.INTERNAL).use(Use.VARIABLE);
    }
;

var_retain_spec:
    /* empty */
    | RETAIN
    {
        // TODO: ctx with retain spec
    }
    | NON_RETAIN
    {
        // TODO: ctx with non retain spec
    }
;

var_constant_spec:
    /* empty */
    {
        // TODO: ctx with non contant spec
    }
    | CONSTANT
    {
        // TODO: ctx with contant spec
    }
;

var_init_decl_list:
    var_init_decl
    | var_init_decl_list ';' var_init_decl
;

var_init_decl:
    identifier_list ':' var_spec_init
    {
        /**
         * Publishes the variables into the symbol table using the loaded context
        **/

        ParsingContext ctx = this.contexts.current();
        Publisher.publish(ctx);

        ctx.declaredIdentifiers().clear();
    }
    | identifier_list ':' standard_function_block_spec_init
;

// TODO: quitar los null
var_spec_init:
    custom_spec_init
    | boolean_spec_init
    | simple_spec_init
    | subrange_spec_init
    | enumerated_spec_init
    | array_spec_init
    | string_spec_init
;

boolean_spec_init:
    boolean_specification
    | initialized_boolean
;

boolean_specification:
    BOOL
    {
        /**
         * Loads the left identifiers type and subtype
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.BOOL)
            .initialValue(
                new BooleanInitialization(this.symbolTable)
            );
    }
;

initialized_boolean:
    boolean_specification edge
;

edge:
    R_EDGE
    | F_EDGE
;

custom_spec_init:
    custom_specification
    | initialized_custom
;

custom_specification:
    IDENTIFIER
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.CUSTOM)
            .customType($1)
            .initialValue(this.symbolTable.get($1).initialValue);
    }
;

initialized_custom:
    initialized_custom_with_constant
    | initialized_custom_with_identifier
    | initialized_custom_with_structure
;

initialized_custom_with_constant:
    custom_type_name ASSIGN_OP constant
    {
        /**
         * Adds to the context the left identifiers initial value and drops the search scope added
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue(new VariableInitialization($3));
        ctx.searchScope().popScope();
    }
;

initialized_custom_with_identifier:
    custom_type_name ASSIGN_OP identifier_with_opt_mangling
    {
        /**
         * Searches if the enumerated value is valid and in that cases loads the initial value and and drops the search
         * scope added
        **/

        ParsingContext ctx = this.contexts.current();

        int octothorpeIdx = $3.indexOf('#');
        String completeTypeName = (octothorpeIdx != -1)
            ? $3 // Has already name mangling
            : ctx.searchScope().getNameMangled($3);

        if (this.symbolTable.get(completeTypeName) == null) {
            // TODO: control de error. Enumerado literal inexistente
        }
        ctx.metadataBuilder().initialValue(new VariableInitialization(completeTypeName));
        ctx.searchScope().popScope();
    }
;

initialized_custom_with_structure:
    custom_type_name ASSIGN_OP structure_initialization
    {
        /**
         * Drops the search scope after reducing the assignment
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.searchScope().popScope();
    }
;

custom_type_name:
    IDENTIFIER
    {
        /**
         * Builds the LexemeInfo associated to the identifier and appends the underlying scope to the search scope.
         *
         * If an identifier is used and it's subtype is custom, then it's hiding information. That's why we search for
         * the underlying scope, so that we can look it up later.
        **/

        LexemeInfo metadata = this.symbolTable.get($1);

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.CUSTOM)
            .customType($1)
            .initialValue(metadata.initialValue);

        String underlyingScope = UnderlyingScopeSearcher.search(this.symbolTable, $1);
        ctx.searchScope().addScope(underlyingScope);
    }
;

simple_spec_init:
    simple_specification
    | initialized_simple
;

simple_specification:
    elementary_type_name
    {
        /**
         * Loads the left identifiers type and subtype
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype($1)
            .initialValue(
                Factory.createPrimitiveInitialization(this.symbolTable, $1)
            );
    }
;

initialized_simple:
    elementary_type_name ASSIGN_OP constant
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype($1)
            .initialValue($3);
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
        // TODO: hacer conversion de esta constante en codigo
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
        // TODO: accion semantica que verifica que prefix es del mismo tipo que time_literal
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
    subrange_specification
    | initialized_subrange
;

subrange_specification:
    subrange_type_decl '(' range ')'
;

subrange_type_decl:
    integer_type_name
    {
        /**
         * Loads the left identifiers subrange subtype
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().type(Type.SUBRANGE).subtype($1);
    }
;

initialized_subrange:
    subrange_specification ASSIGN_OP numeric_constant
    {
        /**
         * Loads the left identifiers subrange initialization
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue($3);
    }
;

range:
    numeric_constant RANGE_OP numeric_constant
    {
        /**
         * Loads the left identifiers subrange numeric range
        **/

        // TODO: hacer chequeo semantico de rangos
        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SUBRANGE)
            .inferiorLimit(Collections.singletonList($1))
            .superiorLimit(Collections.singletonList($3))
            .initialValue($1);
    }
;

enumerated_spec_init:
    enumerated_specification
    | initialized_enumerated
;

enumerated_specification:
    '(' enumerated_values ')' {
        /**
         * Loads the enumerated metadata to the current context.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.ENUMERATE)
            .subtype(Subtype.INT)
            .parameters($2)
            .initialValue(
                new VariableInitialization($2.get(0)
            ));
    }
;

initialized_enumerated:
    enumerated_specification ASSIGN_OP identifier_with_opt_mangling
    {
        /**
         * Verifies that the initializer value is correct and loads it to the context.
        **/

        ParsingContext ctx = this.contexts.current();

        if ($3.indexOf('#') != -1) {
            // TODO: control de error. El enumerado anonimo no puede ser inicializado con un mangling
        }
        List<String> enumeratedValues = ctx.metadataBuilder().build().parameters;
        Integer indexValue = enumeratedValues.indexOf($3);
        if (indexValue == -1) {
            // TODO: control de error. El enumerado anonimo no puede ser inicializado con un valor inexistente
        }
        // TODO: ver cómo verificar que el índice está en el léxico
        ctx.metadataBuilder().initialValue(new VariableInitialization(indexValue.toString()));
    }
;

enumerated_values:
    enumerated_values_list
    {
        /**
         * Just drops the context built for the enumerated list.
        **/

        this.contexts.pop();
    }
;

enumerated_values_list:
    IDENTIFIER
    {
        /**
         * Creates a new context with the data associated to the enums value list and then publish the identifier found
         * to the symbol table.
        **/

        ParsingContext oldCtx = this.contexts.current();
        String outerScopes = oldCtx.outerScopes().getCurrentScope();

        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.declaredIdentifiers().add($1);
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.NONE)
            .use(Use.MACRO)
            .source(Source.NONE)
            .initialValue(
                // TODO: ver interacción con lexer para la publicación de la constante
                new VariableInitialization("0")
            );
        ctx.outerScopes().addScope(outerScopes);

        Publisher.publish(ctx);
        this.contexts.add(ctx);

        List<String> enumeratedValues = new ArrayList<>();
        enumeratedValues.add($1);

        $$ = enumeratedValues;
    }
    | enumerated_values_list ',' IDENTIFIER
    {
        // TODO: ver interacción con el lexer nuevamente
        Integer newIndex = $1.size();

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().set(0, $3);
        ctx.metadataBuilder().initialValue(new VariableInitialization(newIndex.toString()));

        Publisher.publish(ctx);

        $1.add($3);
        $$ = $1;
    }
;

array_spec_init:
    array_specification
    | initialized_array
;

array_specification:
    ARRAY '[' range_list ']' OF IDENTIFIER
    | ARRAY '[' range_list ']' OF non_generic_type_name
;

initialized_array:
    array_specification ASSIGN_OP array_initialization
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
    | structure_initialization
    | identifier_with_opt_mangling
    | array_initialization
;

repeated_initial_element:
    numeric_constant '(' array_initial_element ')'
;

structure_initialization:
    struct_init_open_parenthesis structure_field_initialization_list ')'
;

struct_init_open_parenthesis:
    '('
    {
        /**
         * Copies the initialization of the custom type to overwrite the fields later if it's the first time
        **/

        ParsingContext ctx = this.contexts.current();

        // If there are no nested fields it means that it's the first time
        if (ctx.nestedFields().getCurrentScope() == "") {
            LexemeInfo metadata = ctx.metadataBuilder().build();
            String typeName = metadata.customType;
            Initialization typeInitialValues = (Initialization) this.symbolTable.get(typeName).initialValue;

            ctx.metadataBuilder().initialValue(typeInitialValues.copy());
        }
    }
;


structure_field_initialization_list:
    initialized_structure_field
    | structure_field_initialization_list ',' initialized_structure_field
;

initialized_structure_field:
    initialized_field_with_constant
    | initialized_field_with_identifier
    | initialized_field_with_array
    | initialized_field_with_structure
;

initialized_field_with_constant:
    nested_field ASSIGN_OP constant
    {
        /**
         * Overwrites the field with a constant.
        **/
        
        ParsingContext ctx = this.contexts.current();
        String completeFieldName = ctx.nestedFields().getCurrentScope();
        
        StructInitialization structValue = (StructInitialization) ctx.metadataBuilder().build().initialValue;
        if (structValue.selectVariable(completeFieldName).getVariableValue() == "") {
            // TODO: control de error. el campo no existe
        }
        structValue.setFieldInitialization(completeFieldName, new VariableInitialization($3));
        ctx.nestedFields().popScope();
    }
;

initialized_field_with_identifier:
    nested_field ASSIGN_OP identifier_with_opt_mangling
    {
        /**
         * Overwrites the field with an enumerated value.
        **/

        ParsingContext ctx = this.contexts.current();
        String completeFieldName = ctx.nestedFields().getCurrentScope();

        StructInitialization structValue = (StructInitialization) ctx.metadataBuilder().build().initialValue;
        if (structValue.selectVariable(completeFieldName).getVariableValue() == "") {
            // TODO: control de error. el campo no existe
        }
        // TODO: control de error, verificar que el enumerado es alcanzable
        structValue.setFieldInitialization(completeFieldName, new VariableInitialization($3));
        ctx.nestedFields().popScope();
    }
;

initialized_field_with_array:
    nested_field ASSIGN_OP array_initialization
    {
        /**
         * Overwrites the field with an array initialization.
        **/
        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().popScope();
    }
;

initialized_field_with_structure:
    nested_field ASSIGN_OP structure_initialization
    {
        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().popScope();
    }
;

nested_field:
    IDENTIFIER
    {
        /**
         * Needs to expand the nested scope to keep overwritting.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().addScope($1);
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

standard_function_block_spec_init:
    standard_function_block_specification
    | initialized_standard_function_block
;

initialized_standard_function_block:
    standard_function_block_specification ASSIGN_OP structure_initialization
;

identifier_list:
    IDENTIFIER
    {
        ParsingContext ctx = contexts.current();
        ctx.declaredIdentifiers().clear();
        ctx.declaredIdentifiers().add($1);
    }
    | identifier_list ',' IDENTIFIER
    {
        ParsingContext ctx = contexts.current();
        ctx.declaredIdentifiers().add($3);
    }
;

standard_function_block_specification:
    // TODO: No hay informacion en el estandar de este tipo de funciones
    STD_FB_IDENTIFIER
;

string_spec_init:
    string_specification
    | initialized_string

string_specification:
    type_string_specification
    | type_string_specification '[' numeric_constant ']'

initialized_string:
    type_string_specification  ASSIGN_OP string_constant
    | type_string_specification '[' numeric_constant ']' ASSIGN_OP string_constant
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
    type_id_decl type_declaration_list END_TYPE
    {
        /**
         * After reducing the whole block, the type block context needs to popped
        **/

        this.contexts.pop();
    }
;

type_id_decl:
    TYPE
    {
        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.metadataBuilder()
            .use(Use.TYPE)
            .source(Source.NONE);

        this.contexts.add(ctx);
    }
;

type_declaration_list:
    type_declaration ';'
    | type_declaration_list type_declaration ';'
;

type_declaration:
    type_name_declaration ':' type_spec_init
    {
        /**
         * The type declaration is published after being reduced and it's outerScopes unappended.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.outerScopes().popScope();
        Publisher.publish(ctx);

        ctx.declaredIdentifiers().clear();
    }
;

type_name_declaration:
    IDENTIFIER
    {
        /**
         * Adds the identifier as the current scope so that everything declared inside this scope belongs to the outer
         * scope.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add($1);
        ctx.outerScopes().addScope($1);
    }
;

type_spec_init:
    custom_spec_init
    | simple_spec_init
    | enumerated_spec_init
    | subrange_spec_init
    | array_spec_init
    | structure_specification
    | string_spec_init
;

structure_specification:
    STRUCT structure_field_declaration_list END_STRUCT
    {
        /**
         * Builds the declaration of the structure and publishes it to the symbol table. For that to happen every field
         * needs to be searched to get their initial values and appended to the structure initial value.
        **/

        ParsingContext ctx = this.contexts.current();

        List<String> structParameters = $2;
        StructInitialization initialization = new StructInitialization();

        for (String field : structParameters) {
            String fieldName = ctx.outerScopes().getNameMangled(field);
            LexemeInfo fieldMetadata = this.symbolTable.get(fieldName);
            initialization.setFieldInitialization(field, (Initialization) fieldMetadata.initialValue);
        }

        ctx.metadataBuilder()
            .type(Type.STRUCT)
            .subtype(Subtype.NONE)
            .parameters(structParameters)
            .initialValue(initialization);
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
    field_name ':' structure_field_spec_init
    {
        /**
         * Publishes the field to the symbol table.
        **/

        ParsingContext ctx = this.contexts.current();
        Publisher.publish(ctx);

        this.contexts.pop();
        $$ = $1;
    }
;

field_name:
    IDENTIFIER
    {
        /**
         * Builds the new field context
        **/

        ParsingContext ctx = this.contexts.current();
        String outerScopes = ctx.outerScopes().getCurrentScope();

        ctx = new ParsingContext(this.symbolTable);
        ctx.declaredIdentifiers().add($1);
        ctx.metadataBuilder().use(Use.FIELD).source(Source.NONE);

        // Copies the outer scope from before
        ctx.outerScopes().addScope(outerScopes);

        this.contexts.add(ctx);
        $$ = $1;
    }
;

structure_field_spec_init:
    custom_spec_init
    | boolean_spec_init
    | simple_spec_init
    | enumerated_spec_init
    | subrange_spec_init
    | array_spec_init
    | string_spec_init
;

%%