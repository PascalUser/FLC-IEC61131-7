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
    import utils.builders.*;

    import parser.utils.*;
    import parser.facades.*;
    import parser.internals.*;
    import parser.initializations.*;
    import parser.initializations.primitives.*;
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
    non_generic_type_name
    type_string_specification
    number_prefix

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
    linguistic_term

%type <List<String>>
    identifier_list
    enumerated_values
    structure_field_declaration_list
    linguistic_term_list

%%

/* ---------------------------------- IEC61131-7 ---------------------------------------- */

program:
    opt_data_type_declaration
    function_block_declaration
;

/* -------------------------------- FUNCTION_BLOCK -------------------------------------- */

/* Root: program = opt_data_type_declaration function_block_declaration */

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

/* -------------------------------- Fuzzy Blocks ---------------------------------------- */

opt_fuzzify_block_list:
    /* empty */
    | opt_fuzzify_block_list fuzzify_block
;

fuzzify_block:
    FUZZIFY fuzzify_block_name
    linguistic_term_list
    END_FUZZIFY
    {
        this.contexts.publish();
    }
;

fuzzify_block_name:
    function_block_name
    {
        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add("@fuzzifier");
        ctx.metadataBuilder().source(Source.FUZZIFY);
    }
;

opt_defuzzify_block_list:
    /* empty */
    | opt_defuzzify_block_list defuzzify_block
;

defuzzify_block:
    DEFUZZIFY defuzzify_block_name
    opt_range
    opt_linguistic_term_list
    defuzzification_method
    default_value
    END_DEFUZZIFY
    {
        this.contexts.publish();
    }
;

defuzzify_block_name:
    function_block_name
    {
        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add("@defuzzifier");
        ctx.metadataBuilder().source(Source.DEFUZZIFY);
    }
;

opt_linguistic_term_list:
    /* empty */
    | linguistic_term_list
    {
        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().parameters($1);
    }
;

linguistic_term_list:
    linguistic_term
    {
        List<String> terms = new ArrayList<>();
        terms.add($1);
        $$ = terms;
    }
    | linguistic_term_list linguistic_term
    {
        $1.add($2);
        $$ = $1;
    }
;

linguistic_term:
    TERM IDENTIFIER ASSIGN_OP linguistic_term_value
    {
        this.contexts.createSubcontext();
    }
;

linguistic_term_value:
    IDENTIFIER ';'
    | membership_function ';'
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

/* ------------------------------ Variable Declarations --------------------------------- */

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
        // TODO: ctx with non constant spec
    }
    | CONSTANT
    {
        // TODO: ctx with constant spec
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

        this.contexts.publish();
    }
    | identifier_list ':' standard_function_block_spec_init
;

identifier_list:
    IDENTIFIER
    {
        /**
         * Starts the list of declared identifiers for the current context.
        **/

        this.contexts.createSubcontext();
        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add($1);
    }
    | identifier_list ',' IDENTIFIER
    {
        /**
         * Appends another identifier to the declared identifiers list.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add($3);
    }
;

var_spec_init:
    custom_spec_init
    | boolean_spec_init
    | simple_spec_init
    | subrange_spec_init
    | enumerated_spec_init
    | array_spec_init
    | string_spec_init
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
         * Searches if the enumerated value is valid and in that cases loads the initial value and drops the search
         * scope added
        **/

        ParsingContext ctx = this.contexts.current();

        int octothorpeIdx = $3.indexOf('#');
        String completeTypeName = (octothorpeIdx != -1)
            ? $3 // Has already name mangling
            : ctx.searchScope().getNameMangled($3);

        if (this.symbolTable.get(completeTypeName) == null) {
            // TODO: error control. Enumerated literal does not exist
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

/* boolean_spec_init: boolean_specification | initialized_boolean */
boolean_spec_init:
    boolean_specification
    | initialized_boolean
;

/* boolean_specification: BOOL with default BooleanInitialization */
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

/* initialized_boolean: boolean_specification edge */
initialized_boolean:
    boolean_specification edge
    | boolean_specification ASSIGN_OP boolean_constant
    {
        /**
         * Builds a boolean variable initialization from the assigned constant
         * and stores it in the current parsing context.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .initialValue(
                new VariableInitialization($3)
            );
    }
;

/* edge: R_EDGE | F_EDGE */
edge:
    R_EDGE
    | F_EDGE
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
    simple_specification ASSIGN_OP constant
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .initialValue(new VariableInitialization($3)
        );
    }
;

/* -------------------------------- Elementary Types ------------------------------------ */

elementary_type_name:
    numeric_type_name               { /** Propagates the numeric type name. */    $$ = $1; }
    | date_type_name                { /** Propagates the date type name. */       $$ = $1; }
    | bit_string_type_name          { /** Propagates the bit string type name. */ $$ = $1; }
;

numeric_type_name:
    integer_type_name               { /** Propagates the integer type name. */ $$ = $1; }
    | real_type_name                { /** Propagates the real type name. */    $$ = $1; }
;

integer_type_name:
    signed_integer_type_name        { /** Propagates the signed integer type name.   */ $$ = $1; }
    | unsigned_integer_type_name    { /** Propagates the unsigned integer type name. */ $$ = $1; }
;

signed_integer_type_name:
    SINT   { /** Sets the subtype to SINT. */ $$ = Subtype.SINT; }
    | INT  { /** Sets the subtype to INT.  */ $$ = Subtype.INT;  }
    | DINT { /** Sets the subtype to DINT. */ $$ = Subtype.DINT; }
    | LINT { /** Sets the subtype to LINT. */ $$ = Subtype.LINT; }
;

unsigned_integer_type_name:
    USINT   { /** Sets the subtype to USINT. */ $$ = Subtype.USINT; }
    | UINT  { /** Sets the subtype to UINT.  */ $$ = Subtype.UINT;  }
    | UDINT { /** Sets the subtype to UDINT. */ $$ = Subtype.UDINT; }
    | ULINT { /** Sets the subtype to ULINT. */ $$ = Subtype.ULINT; }
;

real_type_name:
    REAL    { /** Sets the subtype to REAL.  */ $$ = Subtype.REAL;  }
    | LREAL { /** Sets the subtype to LREAL. */ $$ = Subtype.LREAL; }
;

date_type_name:
    TIME            { /** Sets the subtype to TIME. */ $$ = Subtype.TIME; }
    | DATE          { /** Sets the subtype to DATE. */ $$ = Subtype.DATE; }
    | TIME_OF_DAY   { /** Sets the subtype to TIME_OF_DAY.   */ $$ = Subtype.TIME_OF_DAY;   }
    | DATE_AND_TIME { /** Sets the subtype to DATE_AND_TIME. */ $$ = Subtype.DATE_AND_TIME; }
;

/* ----------------------------------- Literals ----------------------------------------- */

constant:
    string_constant    { /** Propagates the string constant.  */ $$ = $1; }
    | boolean_constant { /** Propagates the boolean constant. */ $$ = $1; }
    | time_constant    { /** Propagates the time constant.    */ $$ = $1; }
    | numeric_constant { /** Propagates the numeric constant. */ $$ = $1; }
;

string_constant:
    STRING_LITERAL { /** Propagates the string literal. */   $$ = $1; }
;

boolean_constant:
    BOOLEAN_LITERAL { /** Propagates the boolean literal. */ $$ = $1; }

numeric_constant:
    NUMERIC_LITERAL { /** Propagates the numeric literal. */ $$ = $1; }
    | number_prefix NUMERIC_LITERAL
    {
        // TODO: convert this constant in code
        $$ = "";
    }
;

number_prefix:
    integer_type_name '#' { /** Propagates the integer type name prefix. */ $$ = $1; }
    | real_type_name '#'  { /** Propagates the real type name prefix.    */ $$ = $1; }
    | bit_string_type_name '#' { /** Propagates the bit string type name prefix. */ $$ = $1; }
;

time_constant:
    date_type_name '#' TIME_LITERAL {
        // TODO: semantic action to verify prefix matches time_literal type
    }
;

bit_string_type_name:
    BYTE    { /** Sets the subtype to BYTE.  */ $$ = Subtype.BYTE;  }
    | WORD  { /** Sets the subtype to WORD.  */ $$ = Subtype.WORD;  }
    | DWORD { /** Sets the subtype to DWORD. */ $$ = Subtype.DWORD; }
    | LWORD { /** Sets the subtype to LWORD. */ $$ = Subtype.LWORD; }
;

/* --------------------------------- Derived Types  ------------------------------------- */

subrange_spec_init:
    subrange_specification
    | initialized_subrange
;

subrange_specification:
    subrange_type_decl '(' range ')'
    {
        /**
         * Creates a SubrangeInitialization from the lower and upper limits already
         * collected in the current context metadata and stores it there.
        **/

        ParsingContext ctx = this.contexts.current();
        LexemeInfo metadata = ctx.metadataBuilder().build();
        String ilimit = metadata.inferiorLimits.get(0);
        String slimit = metadata.superiorLimits.get(0);

        ctx.metadataBuilder()
            .initialValue(new SubrangeInitialization(ilimit, slimit));
    }
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
        ctx.metadataBuilder().initialValue(new VariableInitialization($3));
    }
;

range:
    numeric_constant RANGE_OP numeric_constant
    {
        /**
         * Loads the left identifiers subrange numeric range
        **/

        ParsingContext ctx = this.contexts.current();
        LexemeInfo metadata = ctx.metadataBuilder().build();

        if (metadata.inferiorLimits == null) {
            metadata.inferiorLimits = new ArrayList<>();
            metadata.superiorLimits = new ArrayList<>();
        }

        // TODO: semantic check of ranges
        metadata.inferiorLimits.add($1);
        metadata.superiorLimits.add($3);

        ctx.metadataBuilder()
            .inferiorLimits(metadata.inferiorLimits)
            .superiorLimits(metadata.superiorLimits);
    }
;

enumerated_spec_init:
    enumerated_specification
    | initialized_enumerated
;

enumerated_specification:
    '(' enumerated_values ')'
    {
        /**
         * Loads the enumerated metadata to the current context.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.ENUMERATE)
            .subtype(Subtype.INT)
            .parameters($2)
            .initialValue(
                new EnumeratedInitialization($2)
            );
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
            // TODO: error control. Anonymous enum cannot be initialized with mangling
        }
        List<String> enumeratedValues = ctx.metadataBuilder().build().parameters;
        Integer indexValue = enumeratedValues.indexOf($3);
        if (indexValue == -1) {
            // TODO: error control. Anonymous enum cannot be initialized with non-existent value
        }
        // TODO: verify how to check index is in lexicon
        ctx.metadataBuilder().initialValue(
            new VariableInitialization(
                ctx.outerScopes().getNameMangled($3)
            )
        );
    }
;

enumerated_values:
    IDENTIFIER
    {
        /**
         * Creates a new context with the data associated to the enums value list and then publish the identifier found
         * to the symbol table.
        **/

        String declaredEnumerated = this.contexts.current().declaredIdentifiers().get(0);

        this.contexts.createSubcontext();
        ParsingContext ctx = this.contexts.current();

        // This is necessary so that it can be disambiguated
        Use currentUse = this.contexts.current().metadataBuilder().build().use;
        if (currentUse == Use.TYPE) {
            ctx.outerScopes().addScope(declaredEnumerated);
        }

        ctx.declaredIdentifiers().add($1);
        Director.makeMacro(ctx.metadataBuilder());
        ctx.metadataBuilder().initialValue(new MacroInitialization(this.symbolTable, "0"));
        this.contexts.publish();

        List<String> enumeratedValues = new ArrayList<>();
        enumeratedValues.add($1);
        $$ = enumeratedValues;
    }
    | enumerated_values ',' IDENTIFIER
    {
        Integer replaceValue = $1.size();
        String declaredEnumerated = this.contexts.current().declaredIdentifiers().get(0);

        this.contexts.createSubcontext();
        ParsingContext ctx = this.contexts.current();

        // This is necessary so that it can be disambiguated
        Use currentUse = this.contexts.current().metadataBuilder().build().use;
        if (currentUse == Use.TYPE) {
            ctx.outerScopes().addScope(declaredEnumerated);
        }

        ctx.declaredIdentifiers().add($3);
        Director.makeMacro(ctx.metadataBuilder());
        ctx.metadataBuilder().initialValue(new MacroInitialization(this.symbolTable, replaceValue.toString()));
        this.contexts.publish();

        $1.add($3);
        $$ = $1;
    }
;

array_spec_init:
    array_specification
    | initialized_array
;

array_specification:
    ARRAY '[' range_list ']' OF array_type
    {
        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().type(Type.ARRAY);
    }
;

array_type:
    IDENTIFIER
    {
        ParsingContext ctx = this.contexts.current();
        LexemeInfo typeMetadata = this.symbolTable.get($1);

        int dimension = DimensionCalculator.calculate(ctx);
        ctx.metadataBuilder()
            .subtype(Subtype.CUSTOM)
            .customType($1)
            .initialValue(
                new RepeatedInitialization(dimension, (Initialization) typeMetadata.initialValue)
            );

        String underlyingScope = UnderlyingScopeSearcher.search(this.symbolTable, $1);
        ctx.searchScope().addScope(underlyingScope);
    }
    | non_generic_type_name
    {
        ParsingContext ctx = this.contexts.current();

        int dimension = DimensionCalculator.calculate(ctx);
        Initialization defaultInit = Factory.createPrimitiveInitialization(this.symbolTable, $1);
        ctx.metadataBuilder()
            .subtype($1)
            .initialValue(
                new RepeatedInitialization(dimension, defaultInit)
            );
    }
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
    array_initial_elements _actionAfterElement_
    | array_initial_elements_list ',' array_initial_elements _actionAfterElement_
;

array_initial_elements:
    array_initial_element
    | repeated_initial_element
;

_actionAfterElement_:
    /* empty */
    {
        /**
         * Retrieves the relevant information of the initialization and copies it into the array declaration context
         * (arrayContext)
        **/

        ParsingContext initContext = this.contexts.pop();
        ParsingContext arrayContext = this.contexts.current();

        int start = arrayContext.index();
        arrayContext.incrementIndex(initContext.index());

        LexemeInfo initMetadata = initContext.metadataBuilder().build();
        LexemeInfo arrayMetadata = arrayContext.metadataBuilder().build();

        RepeatedInitialization arrayInitialValue = (RepeatedInitialization) arrayMetadata.initialValue;
        arrayInitialValue.addInterval(
            start,
            arrayContext.index() - 1,
            (Initialization) initMetadata.initialValue
        );
    }
;

array_initial_element:
    constant
    {
        /**
         * Creates the initialization and adds the context counter by one
        **/

        ParsingContext constantContext = new ParsingContext(this.symbolTable);
        this.contexts.add(constantContext);
        constantContext.metadataBuilder().initialValue(new VariableInitialization($1));
        constantContext.incrementIndex(1);
    }
    | identifier_with_opt_mangling
    {
        /**
         * Creates the initialization and adds the context counter by one
        **/

        ParsingContext constantContext = new ParsingContext(this.symbolTable);
        this.contexts.add(constantContext);
        constantContext.incrementIndex(1);

        // TODO: verify enum as in initialized_custom_with_identifier rule
        String completeEnumerateName = constantContext.searchScope().getNameMangled($1);
        constantContext.metadataBuilder().initialValue(new VariableInitialization(completeEnumerateName));
    }
    | _actionBeforeNotSimpleInitialization_ structure_initialization
    {
        /**
         * Only adds the context counter by one because the initialization is created inside the rule.
        **/

        ParsingContext complexContext = this.contexts.current();
        complexContext.incrementIndex(1);
    }
    | _actionBeforeNotSimpleInitialization_ array_initialization
    {
        /**
         * Only adds the context counter by one because the initialization is created inside the rule.
        **/

        ParsingContext complexContext = this.contexts.current();
        complexContext.incrementIndex(1);
    }
;

repeated_initial_element:
    numeric_constant '(' array_initial_element ')'
    {
        /**
         * Repeats N times the inner initialization.
        **/

        // TODO: verify that numeric_constant is additive (non negative)
        int multiplier = Integer.parseInt($1);

        ParsingContext initContext = this.contexts.current();
        initContext.incrementIndex(multiplier * initContext.index() - 1);
    }
;

_actionBeforeNotSimpleInitialization_:
    /* empty */
    {
        ParsingContext arrayCtx = this.contexts.current();
        LexemeInfo arrayMetadata = arrayCtx.metadataBuilder().build();

        this.contexts.createSubcontext();
        ParsingContext compxCtx = this.contexts.current();

        // So that a structure can find it's copy data
        compxCtx.metadataBuilder().customType(
            arrayMetadata.customType
        );
    }
;

structure_initialization:
    '(' _actionAfterParenthesis_ structure_field_initialization_list ')'
;

_actionAfterParenthesis_:
    /* empty */
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
            // TODO: error control. field does not exist
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
            // TODO: error control. field does not exist
        }
        // TODO: error control, verify enum is reachable
        String completeEnumeratedValue = ctx.searchScope().getNameMangled($3);

        structValue.setFieldInitialization(completeFieldName, new VariableInitialization(completeEnumeratedValue));
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
        /**
         * Closes the nested field scope that was opened while scanning the
         * field name, once its structure initialization is complete.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().popScope();
    }
;

nested_field:
    IDENTIFIER
    {
        /**
         * Needs to expand the nested scope to keep overwriting.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().addScope($1);
    }
;

identifier_with_opt_mangling:
    IDENTIFIER
    {
        /**
         * Simple identifier: keeps the lexeme as-is.
        **/

        $$ = $1;
    }
    | IDENTIFIER '#' IDENTIFIER
    {
        /**
         * Mangled identifier: joins both identifiers with '#'.
        **/

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

standard_function_block_specification:
    // TODO: No information in standard for this function type
    STD_FB_IDENTIFIER
;

string_spec_init:
    string_specification
    | initialized_string

string_specification:
    type_string_specification
    {
        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype($1)
            .initialValue(
                Factory.createPrimitiveInitialization(this.symbolTable, $1)
            );
    }
    | type_string_specification '[' numeric_constant ']'
    {
        /**
         * Stores the explicit string length as the superior limit and creates
         * a StringInitialization for the declared subtype.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype($1)
            .superiorLimits(
                Collections.singletonList($3)
            ).initialValue(
                Factory.createPrimitiveInitialization(this.symbolTable, $1)
            );
    }

initialized_string:
    string_specification  ASSIGN_OP string_constant
    {
        /**
         * Wraps the assigned string literal as a VariableInitialization and
         * stores it in the current context metadata.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue(
            new VariableInitialization($3)
        );
    }
;

type_string_specification:
    STRING      { $$ = Subtype.STRING;  }
    | WSTRING   { $$ = Subtype.WSTRING; }
;

/* ----------------------------------- Data Type ---------------------------------------- */

opt_data_type_declaration:
    /* empty */
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
    type_name_declaration ':' type_spec_init
    {
        /**
         * The type declaration is published after being reduced and it's outerScopes unappended.
        **/

        this.contexts.publish();
    }
;

type_name_declaration:
    IDENTIFIER
    {
        /**
         * Adds the identifier as the current scope so that everything declared inside this scope belongs to the outer
         * scope.
        **/

        ParsingContext ctx = new ParsingContext(this.symbolTable);
        Director.makeType(ctx.metadataBuilder());
        ctx.declaredIdentifiers().add($1);
        this.contexts.add(ctx);
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

        // Builds the search scope to look up his fields
        String structName = ctx.declaredIdentifiers().get(0);
        ctx.searchScope().addScope(structName);

        // Builds the structure parameters
        List<String> structParameters = $2;

        // Builds the structure initial value
        StructInitialization initialization = new StructInitialization();
        for (String field : structParameters) {
            String fieldName = ctx.searchScope().getNameMangled(field);
            LexemeInfo fieldMetadata = this.symbolTable.get(fieldName);
            initialization.setFieldInitialization(field, (Initialization) fieldMetadata.initialValue);
        }

        // Modifies the context
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
        /**
         * Starts the structure field list with the first declared field.
        **/

        List<String> structParameters = new ArrayList<>();
        structParameters.add($1);
        $$ = structParameters;
    }
    | structure_field_declaration_list structure_field_declaration ';'
    {
        /**
         * Appends the next declared field to the structure field list.
        **/

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

        this.contexts.publish();

        $$ = $1;
    }
;

field_name:
    IDENTIFIER
    {
        /**
         * Builds the new field context
        **/

        String newScope = this.contexts.current().declaredIdentifiers().get(0);
        this.contexts.createSubcontext();

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add($1);
        ctx.metadataBuilder().use(Use.FIELD);
        ctx.outerScopes().addScope(newScope);

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