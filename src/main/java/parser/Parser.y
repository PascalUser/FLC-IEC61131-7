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
    import parser.publishers.*;
    import parser.initializations.*;
    import parser.initializations.leafs.*;
    import parser.initializations.nodes.*;
}

%code {
    private String programName;
}

/**
 * Parser parameter: the symbol table shared between lexer and parser
 * for storing semantic information during parsing.
 */
%parse-param { SymbolTable symbolTable  }

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

%type <DeclarationBlock>
    var_init_decl_list

%type <StructureBlock>
    structure_field_init_decl_list

%type <SingleDeclaration>
    type_spec_init
    structure_specification
    structure_field_init_decl
    structure_field_spec_init

%type <MultipleDeclaration>
    var_init_decl
    var_spec_init

%type <LexemeInfoBuilder>
    simple_spec_init
    simple_specification
    initialized_simple
    boolean_spec_init
    boolean_specification
    initialized_boolean
    custom_spec_init
    custom_specification
    initialized_custom
    initialized_custom_with_constant
    initialized_custom_with_identifier
    initialized_custom_with_structure
    enumerated_spec_init
    enumerated_specification
    initialized_enumerated
    array_spec_init
    array_specification
    initialized_array


%type <Subtype>
    signed_integer_type
    unsigned_integer_type
    integer_type
    real_type
    numeric_type
    date_type
    bit_string_type
    elementary_type
    non_generic_type
    string_type
    prefix_type

%type <Source>
    io_var_decl

%type <Use>
    var_constant_spec

%type <List<String>>
    identifier_list
    linguistic_term_list
    range
    range_list

%type <String>
     // names
     type_name_declaration
     field_name
     // constants
     constant
     string_constant
     boolean_constant
     time_constant
     numeric_constant
     // other
     referenced_identifier
     linguistic_term

%type <List<PathInitializationInfo>>
    structure_initialization
    initialized_field_with_structure
    structure_field_init_list
    initialized_struct_field
    array_initialization
    array_initial_element
    initialized_field_with_constant
    initialized_field_with_identifier
    initialized_field_with_array

%type <ParsingRepeatedArrayContext>
    array_initial_elements_list
    array_initial_elements
    repeated_initial_element

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
;

function_block_name:
    IDENTIFIER
    {
        this.programName = $1;
    }
;

opt_fb_io_var_declarations_list:
    /* empty */
    | opt_fb_io_var_declarations_list fb_io_var_declarations
;

fb_io_var_declarations:
    io_var_decl var_retain_spec var_init_decl_list ';' END_VAR
    {
        $3.source($1).use(Use.VARIABLE).setDeclarationScope(programName);
        $3.publish();
    }
;

io_var_decl:
    VAR_INPUT
    {
        $$ = Source.IN;
    }
    | VAR_OUTPUT
    {
        $$ = Source.OUT;
    }
;

var_retain_spec:
    /* empty */
    | RETAIN
    {
        /**
         * Marks the variable declaration as RETAIN (persistent across power cycles).
         * TODO: Implement retain specification in context metadata.
        **/
    }
    | NON_RETAIN
    {
        /**
         * Marks the variable declaration as NON_RETAIN (not persistent).
         * TODO: Implement non-retain specification in context metadata.
        **/
    }
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
;

fuzzify_block_name:
    function_block_name
    {
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
;

defuzzify_block_name:
    function_block_name
    {
    }
;

opt_linguistic_term_list:
    /* empty */
    | linguistic_term_list
    {
    }
;

linguistic_term_list:
    linguistic_term
    {
        /**
         * Starts a new list of linguistic terms with the first term.
        **/

        List<String> terms = new ArrayList<>();
        terms.add($1);
        $$ = terms;
    }
    | linguistic_term_list linguistic_term
    {
        /**
         * Appends another linguistic term to the existing list.
        **/

        $1.add($2);
        $$ = $1;
    }
;

linguistic_term:
    TERM IDENTIFIER ASSIGN_OP linguistic_term_value
    {
        $$ = null;
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

var_declarations:
    var_id_decl var_constant_spec var_init_decl_list ';' END_VAR
    {
        $3.source(Source.INTERNAL).use($2).setDeclarationScope(programName);
        $3.publish();
    }
;

var_id_decl:
    VAR
;

var_constant_spec:
    /* empty */
    {
        $$ = Use.VARIABLE;
    }
    | CONSTANT
    {
        $$ = Use.CONSTANT;
    }
;

var_init_decl_list:
    var_init_decl
    {
        DeclarationBlock block = new DeclarationBlock();
        block.addDeclaration($1);
        $$ = block;
    }
    | var_init_decl_list ';' var_init_decl
    {
        $1.addDeclaration($3);
        $$ = $1;
    }
;

var_init_decl:
    identifier_list ':' var_spec_init
    {
        $3.identifiers($1);
        $$ = $3;
    }
    | identifier_list ':' standard_function_block_spec_init
    {
        // TODO: remove null
        $$ = null;
    }
;

identifier_list:
    IDENTIFIER
    {
        List<String> identifiers = new ArrayList<>();
        identifiers.add($1);
        $$ = identifiers;
    }
    | identifier_list ',' IDENTIFIER
    {
        $1.add($3);
        $$ = $1;
    }
;

var_spec_init:
    custom_spec_init
    {
        /** Wraps the custom type builder in a MultipleDeclaration for variable list handling. */

        $$ = new MultipleDeclaration(this.symbolTable, $1); }
    | boolean_spec_init
    {
        /** Wraps the boolean builder in a MultipleDeclaration for variable list handling. */

        $$ = new MultipleDeclaration(this.symbolTable, $1); }
    | simple_spec_init
    {
        /** Wraps the simple type builder in a MultipleDeclaration for variable list handling. */

        $$ = new MultipleDeclaration(this.symbolTable, $1); }
    | subrange_spec_init
    {
        /** Subrange not yet supported for variable lists; returns null. */

        $$ = null;
    }
    | enumerated_spec_init
    {
        /** Wraps the enumerated builder in an AnonymousEnumeratedVariable for variable list handling. */

        $$ = new AnnonymousEnumeratedVariable(this.symbolTable, $1);
    }
    | array_spec_init
    {
        /** Wraps the array builder in a MultipleDeclaration for variable list handling. */

        $$ = new MultipleDeclaration(this.symbolTable, $1);
    }
    | string_spec_init
    {
        /** String not yet supported for variable lists; returns null. */

        $$ = null;
    }
;

custom_spec_init:
    custom_specification
    {
        /** Propagates the custom type specification builder. */

        $$ = $1;
    }
    | initialized_custom
    {
        /** Propagates the initialized custom builder. */

        $$ = $1;
    }
;

custom_specification:
    IDENTIFIER
    {
        LexemeInfoBuilder customDeclaration = new LexemeInfoBuilder();
        Director.makeCustom(customDeclaration, this.symbolTable, $1);
        $$ = customDeclaration;
    }
;

initialized_custom:
    initialized_custom_with_constant
    {
        /** Propagates the custom initialization with constant value. */

        $$ = $1;
    }
    | initialized_custom_with_identifier
    {
        /** Propagates the custom initialization with identifier reference. */

        $$ = $1;
    }
    | initialized_custom_with_structure
    {
        /** Propagates the custom initialization with structure value. */

        $$ = $1;
    }
;

initialized_custom_with_constant:
    custom_specification ASSIGN_OP constant
    {
       $1.initialValue(new VariableInitialization($3));
       $$ = $1;
    }
;

initialized_custom_with_identifier:
    custom_specification ASSIGN_OP referenced_identifier
    {
        // TODO: check if it the referenced identifier is valid
        // In order to do that, the custom type from custom specification needs to be retrieved, then, the referenced
        // identifier needs to be in it's parameters list and the custom type has to be an enumerated.

        LexemeInfo metadata = this.symbolTable.get($1.customType());
        if (metadata.type.equals(Type.ENUMERATE) && metadata.parameters.contains($3)) {
            $1.initialValue(new VariableInitialization($1.customType() + "#" + $3));
        }

        $$ = $1;
    }
;

initialized_custom_with_structure:
    custom_specification ASSIGN_OP structure_initialization
    {
        StructInitialization structInit = (StructInitialization) $1.initialValue();

        for (PathInitializationInfo fieldInfo : $3) {
            if (structInit.find(fieldInfo.name()).isPresent()) {
                structInit.put(fieldInfo.name(), fieldInfo.initialValue());
            }
            // TODO: error handling
        }
        $$ = $1;
    }
;

boolean_spec_init:
    boolean_specification
    {
        /** Propagates the boolean type specification builder. */

        $$ = $1;
    }
    | initialized_boolean
    {
        /** Propagates the initialized boolean builder. */

        $$ = $1;
    }
;

boolean_specification:
    BOOL
    {
        LexemeInfoBuilder primitiveBuilder = new LexemeInfoBuilder();
        Director.makePrimitive(primitiveBuilder, symbolTable, Subtype.BOOL);
        $$ = primitiveBuilder;
    }
;

initialized_boolean:
    boolean_specification edge
    {
        // TODO: add the edge specification
        $$ = null;
    }
    | boolean_specification ASSIGN_OP boolean_constant
    {
        $1.initialValue(new VariableInitialization($3));
        $$ = $1;
    }
;

/* edge: R_EDGE | F_EDGE */
edge:
    R_EDGE
    | F_EDGE
;

simple_spec_init:
    simple_specification
    {
        /** Propagates the simple type specification builder. */

        $$ = $1;
    }
    | initialized_simple
    {
        /** Propagates the initialized simple builder. */

        $$ = $1;
    }
;

simple_specification:
    elementary_type
    {
        LexemeInfoBuilder primitiveBuilder = new LexemeInfoBuilder();
        Director.makePrimitive(primitiveBuilder, this.symbolTable, $1);
        $$ = primitiveBuilder;
    }
;

initialized_simple:
    simple_specification ASSIGN_OP constant
    {
        $1.initialValue(new VariableInitialization($3));
    }
;

/* -------------------------------- Elementary Types ------------------------------------ */

elementary_type:
    numeric_type
    {
        /** Propagates the numeric type name. */

        $$ = $1;
    }
    | date_type
    {
        /** Propagates the date type name. */

        $$ = $1;
    }
    | bit_string_type
    {
        /** Propagates the bit string type name. */

        $$ = $1;
    }
;

numeric_type:
    integer_type
    {
        /** Propagates the integer type name. */

        $$ = $1;
    }
    | real_type
    {
        /** Propagates the real type name. */

        $$ = $1;
    }
;

integer_type:
    signed_integer_type
    {
        /** Propagates the signed integer type name.   */

        $$ = $1;
    }
    | unsigned_integer_type
    {
        /** Propagates the unsigned integer type name. */

        $$ = $1;
    }
;

signed_integer_type:
    SINT
    {
        /** Sets the subtype to SINT. */

        $$ = Subtype.SINT;
    }
    | INT
    {
        /** Sets the subtype to INT.  */

        $$ = Subtype.INT;
    }
    | DINT
    {
        /** Sets the subtype to DINT. */

        $$ = Subtype.DINT;
    }
    | LINT
    {
        /** Sets the subtype to LINT. */

        $$ = Subtype.LINT;
    }

;

unsigned_integer_type:
    USINT
    {
        /** Sets the subtype to USINT. */

        $$ = Subtype.USINT;
    }
    | UINT
    {
        /** Sets the subtype to UINT.  */

        $$ = Subtype.UINT;
    }
    | UDINT
    {
        /** Sets the subtype to UDINT. */

        $$ = Subtype.UDINT;
    }
    | ULINT
    {
        /** Sets the subtype to ULINT. */

        $$ = Subtype.ULINT;
    }

;

real_type:
    REAL
    {
        /** Sets the subtype to REAL.  */

        $$ = Subtype.REAL;
    }
    | LREAL
    {
        /** Sets the subtype to LREAL. */

        $$ = Subtype.LREAL;
    }

;

date_type:
    TIME
    {
        /** Sets the subtype to TIME. */

        $$ = Subtype.TIME;
    }
    | DATE
    {
        /** Sets the subtype to DATE. */

        $$ = Subtype.DATE;
    }
    | TIME_OF_DAY
    {
        /** Sets the subtype to TIME_OF_DAY.   */

        $$ = Subtype.TIME_OF_DAY;
    }
    | DATE_AND_TIME
    {
        /** Sets the subtype to DATE_AND_TIME. */

        $$ = Subtype.DATE_AND_TIME;
    }
;

/* ----------------------------------- Literals ----------------------------------------- */

constant:
    string_constant
    {
        /** Propagates the string constant.  */

        $$ = $1;
    }
    | boolean_constant
    {
        /** Propagates the boolean constant. */

        $$ = $1;
    }
    | time_constant
    {
        /** Propagates the time constant.    */

        $$ = $1;
    }
    | numeric_constant
    {
        /** Propagates the numeric constant. */

        $$ = $1;
    }
;

string_constant:
    STRING_LITERAL
    {
        /** Propagates the string literal. */

        $$ = $1;
    }
;

boolean_constant:
    BOOLEAN_LITERAL
    {
        /** Propagates the boolean literal. */

        $$ = $1;
    }
;

numeric_constant:
    NUMERIC_LITERAL
    {
        /** Propagates the numeric literal. */

        $$ = $1;
    }
    | prefix_type '#' NUMERIC_LITERAL
    {
        /**
         * Combines a base prefix (e.g., 16#) with a numeric literal to form
         * a based numeric constant. The conversion is deferred to later processing.
        **/

        // TODO: convert this constant in code
        $$ = "";
    }
;

prefix_type:
    integer_type
    {
        /** Propagates the integer type name prefix. */

        $$ = $1;
    }
    | real_type
    {
        /** Propagates the real type name prefix.    */

        $$ = $1;
    }
    | bit_string_type
    {
        /** Propagates the bit string type name prefix. */

        $$ = $1;
    }
;

time_constant:
    date_type '#' TIME_LITERAL
    {
        /**
         * Combines a date/time type prefix with a time literal.
         * TODO: Add semantic action to verify prefix matches time_literal type.
        **/

        // TODO: semantic action to verify prefix matches time_literal type
    }
;

bit_string_type:
    BYTE
    {
        /** Sets the subtype to BYTE.  */

        $$ = Subtype.BYTE;
    }
    | WORD
    {
        /** Sets the subtype to WORD.  */

        $$ = Subtype.WORD;
    }
    | DWORD
    {
        /** Sets the subtype to DWORD. */

        $$ = Subtype.DWORD;
    }
    | LWORD
    {
        /** Sets the subtype to LWORD. */

        $$ = Subtype.LWORD;
    }
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

        // ParsingContext ctx = this.contexts.current();
        // LexemeInfo metadata = ctx.metadataBuilder().build();
        // String ilimit = metadata.inferiorLimits.get(0);
        // String slimit = metadata.superiorLimits.get(0);

        // ctx.metadataBuilder()
        //     .initialValue(new SubrangeInitialization(ilimit, slimit));
    }
;

subrange_type_decl:
    integer_type
    {
        /**
         * Loads the left identifiers subrange subtype
        **/

        // ParsingContext ctx = this.contexts.current();
        // ctx.metadataBuilder()
        //     .type(Type.SUBRANGE)
        //     .subtype($1)
        //     .inferiorLimits(new ArrayList<>())
        //     .superiorLimits(new ArrayList<>());
    }
;

initialized_subrange:
    subrange_specification ASSIGN_OP numeric_constant
    {
        /**
         * Loads the left identifiers subrange initialization
        **/

        // ParsingContext ctx = this.contexts.current();
        //  ctx.metadataBuilder().initialValue(new VariableInitialization($3));
    }
;

range:
    numeric_constant RANGE_OP numeric_constant
    {
        List<String> ranges = new ArrayList<>();
        ranges.add($1);
        ranges.add($3);
        $$ = ranges;
    }
;

enumerated_spec_init:
    enumerated_specification
    {
        /** Propagates the enumerated type specification builder. */

        $$ = $1;
    }
    | initialized_enumerated
    {
        /** Propagates the initialized enumerated builder. */

        $$ = $1;
    }
;

enumerated_specification:
    '(' identifier_list ')'
    {
        LexemeInfoBuilder enumBuilder = new LexemeInfoBuilder();
        Director.makeEnumerated(enumBuilder, $2);
        $$ = enumBuilder;
    }
;

initialized_enumerated:
    enumerated_specification ASSIGN_OP referenced_identifier
    {
        // TODO: semantic check over initial value
        // Note: it has to be in the enumerated_specification parameters. The initial value can't be global because
        // it's in a new enumerated specification declaration (type incompatible). However, another enumerated
        // declaration could have declared the same "<programName>#<enumeratedValue>", so, it's important to check that
        // there's no redeclaration.

        $1.initialValue(new VariableInitialization(programName + "#" + $3));
        $$ = $1;
    }
;

array_spec_init:
    array_specification
    {
        /** Propagates the array type specification builder. */

        $$ = $1;
    }
    | initialized_array
    {
        /** Propagates the initialized array builder. */

        $$ = $1;
    }
;

array_specification:
    ARRAY '[' range_list ']' OF IDENTIFIER
    {
        ArrayMetadata arrayMetadata = new ArrayMetadata(Subtype.CUSTOM, $6, $3);

        LexemeInfoBuilder arrayBuilder = new LexemeInfoBuilder();
        Director.makeArray(arrayBuilder, this.symbolTable, arrayMetadata);
        $$ = arrayBuilder;
    }
    | ARRAY '[' range_list ']' OF non_generic_type
    {
        ArrayMetadata arrayMetadata = new ArrayMetadata($6, null, $3);

        LexemeInfoBuilder arrayBuilder = new LexemeInfoBuilder();
        Director.makeArray(arrayBuilder, this.symbolTable, arrayMetadata);
        $$ = arrayBuilder;
    }
;

initialized_array:
    array_specification ASSIGN_OP array_initialization
    {
        RepeatedInitialization arrayInitialValue = (RepeatedInitialization) $1.initialValue();
        for (PathInitializationInfo rangeInfo : $3) {
            String range = rangeInfo.name();
            Initialization initialValue = rangeInfo.initialValue();
            arrayInitialValue.put(range, initialValue);
        }
        $$ = $1;
    }
;

range_list:
    range
    {
        $$ = $1;
    }
    | range_list ',' range
    {
        $1.addAll($3);
    }
;

non_generic_type:
    elementary_type
;

array_initialization:
    '[' array_initial_elements_list ']'
    {
        $$ = $2.getPathsInfo();
    }
;

array_initial_elements_list:
    array_initial_elements
    {
        int endIdx = $1.getMaxValue() - 1;

        List<PathInitializationInfo> newPathsInfo = new ArrayList<>();
        for (PathInitializationInfo oldPathInfo : $1.getPathsInfo()) {
            newPathsInfo.add(new PathInitializationInfo(
                "0.." + endIdx + "#" + oldPathInfo.name(),
                oldPathInfo.initialValue()
            ));
        }
        $$ = new ParsingRepeatedArrayContext(endIdx, newPathsInfo);
    }
    | array_initial_elements_list ',' array_initial_elements
    {
        int lastIdx = $1.getMaxValue();
        int startIdx = lastIdx + 1;
        int endIdx = lastIdx + $3.getMaxValue();

        List<PathInitializationInfo> newPathsInfo = $1.getPathsInfo();
        for (PathInitializationInfo oldPathInfo : $3.getPathsInfo()) {
            newPathsInfo.add(new PathInitializationInfo(
                startIdx + ".." + endIdx + "#" + oldPathInfo.name(),
                oldPathInfo.initialValue()
            ));
        }
        $$ = new ParsingRepeatedArrayContext(endIdx, newPathsInfo);
    }
;

array_initial_elements:
    array_initial_element
    {
        /** Creates a ParsingRepeatedArrayContext for a single array element. */

        $$ = new ParsingRepeatedArrayContext(1, $1);
    }
    | repeated_initial_element
    {
        /** Propagates the repeated element context. */

        $$ = $1;
    }
;

array_initial_element:
    constant
    {
        List<PathInitializationInfo> pathsInfo = new ArrayList<>();
        pathsInfo.add(new PathInitializationInfo("", new VariableInitialization($1)));
        $$ = pathsInfo;
    }
    | referenced_identifier
    {
        // TODO: see how to check semantics
        List<PathInitializationInfo> pathsInfo = new ArrayList<>();
        pathsInfo.add(new PathInitializationInfo("", new VariableInitialization($1)));
        $$ = pathsInfo;
    }
    | structure_initialization
    {
        /** Propagates the structure initialization paths. */

        $$ = $1;
    }
    | array_initialization
    {
        /** Propagates the array initialization paths. */

        $$ = $1;
    }
;

repeated_initial_element:
    numeric_constant '(' array_initial_element ')'
    {
        $$ = new ParsingRepeatedArrayContext(Integer.parseInt($1), $3);
    }
;

structure_initialization:
    '(' structure_field_init_list ')'
    {
        /** Propagates the structure field initialization list. */

        $$ = $2;
    }
;

structure_field_init_list:
    initialized_struct_field
    {
        $$ = $1;
    }
    | structure_field_init_list ',' initialized_struct_field
    {
        $1.addAll($3);
        $$ = $1;
    }
;

initialized_struct_field:
    initialized_field_with_constant
    {
        /** Propagates the field initialization with constant value. */

        $$ = $1;
    }
    | initialized_field_with_identifier
    {
        /** Propagates the field initialization with identifier reference. */

        $$ = $1;
    }
    | initialized_field_with_array
    {
        /** Propagates the field initialization with array value. */

        $$ = $1;
    }
    | initialized_field_with_structure
    {
        /** Propagates the field initialization with structure value. */

        $$ = $1;
    }
;

initialized_field_with_constant:
    field_name ASSIGN_OP constant
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        fieldsInfo.add(new PathInitializationInfo($1, new VariableInitialization($3)));
        $$ = fieldsInfo;
    }
;

initialized_field_with_identifier:
    field_name ASSIGN_OP referenced_identifier
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        fieldsInfo.add(new PathInitializationInfo($1, new VariableInitialization($3)));
        $$ = fieldsInfo;
    }
;

initialized_field_with_array:
    field_name ASSIGN_OP array_initialization
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        for (PathInitializationInfo fieldInfo : $3) {
            String fieldName = $1 + "#" + fieldInfo.name();
            fieldsInfo.add(new PathInitializationInfo(fieldName, fieldInfo.initialValue()));
        }
        $$ = fieldsInfo;
    }
;

initialized_field_with_structure:
    field_name ASSIGN_OP structure_initialization
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        for (PathInitializationInfo fieldInfo : $3) {
            String fieldName = $1 + "#" + fieldInfo.name();
            fieldsInfo.add(new PathInitializationInfo(fieldName, fieldInfo.initialValue()));
        }
        $$ = fieldsInfo;
    }
;

referenced_identifier:
    IDENTIFIER
    {
        /**
         * Simple identifier: keeps the lexeme as-is if it's global, else assumes it is local.
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
;

string_specification:
    string_type
    {
        /**
         * Configures the current context for a STRING/WSTRING type without
         * explicit length, using the default primitive initialization.
        **/

        // ParsingContext ctx = this.contexts.current();
        // ctx.metadataBuilder()
        //     .type(Type.SIMPLE)
        //     .subtype($1)
        //     .initialValue(
        //         Factory.createPrimitiveInitialization(this.symbolTable, $1)
        //     );
    }
    | string_type '[' numeric_constant ']'
    {
        /**
         * Stores the explicit string length as the superior limit and creates
         * a StringInitialization for the declared subtype.
        **/

        // ParsingContext ctx = this.contexts.current();
        // ctx.metadataBuilder()
        //     .type(Type.SIMPLE)
        //     .subtype($1)
        //     .superiorLimits(
        //         Collections.singletonList($3)
        //     ).initialValue(
        //         Factory.createPrimitiveInitialization(this.symbolTable, $1)
        //     );
    }
;

initialized_string:
    string_specification  ASSIGN_OP string_constant
    {
        /**
         * Wraps the assigned string literal as a VariableInitialization and
         * stores it in the current context metadata.
        **/

        // ParsingContext ctx = this.contexts.current();
        // ctx.metadataBuilder().initialValue(
        //     new VariableInitialization($3)
        // );
    }
;

string_type:
    STRING
    {
        /** Sets the subtype to STRING. */

        $$ = Subtype.STRING;
    }
    | WSTRING
    {
        /** Sets the subtype to WSTRING. */

        $$ = Subtype.WSTRING;
    }
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
        $3.identifier($1).use(Use.TYPE).source(Source.NONE);
        $3.publish();
    }
;

type_name_declaration:
    IDENTIFIER
    {
        $$ = $1;
    }
;

type_spec_init:
    custom_spec_init
    {
        /** Wraps the custom type builder in a SingleDeclaration for type declaration. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
    | simple_spec_init
    {
        /** Wraps the simple type builder in a SingleDeclaration for type declaration. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
    | enumerated_spec_init
    {
        /** Wraps the enumerated builder in an EnumeratedTypeDeclaration for type declaration. */

        $$ = new EnumeratedTypeDeclaration(this.symbolTable, $1);
    }
    | subrange_spec_init
    {
        /** Subrange not yet supported for type declarations; returns null. */

        $$ = null;
    }
    | array_spec_init
    {
        /** Wraps the array builder in a SingleDeclaration for type declaration. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
    | structure_specification
    {
        /** Propagates the structure declaration. */

        $$ = $1;
    }
    | string_spec_init
    {
        /** String not yet supported for type declarations; returns null. */

        $$ = null;
    }
;

structure_specification:
    STRUCT structure_field_init_decl_list END_STRUCT
    {
        StructureDeclaration structDeclaration = new StructureDeclaration(this.symbolTable, $2);
        Director.makeStructure(structDeclaration, $2);

        $$ = structDeclaration;
    }
;

structure_field_init_decl_list:
    structure_field_init_decl ';'
    {
        /**
         * Starts the structure block with the first declared field.
        **/

        StructureBlock structBlock = new StructureBlock();
        structBlock.addField($1);
        $$ = structBlock;
    }
    | structure_field_init_decl_list structure_field_init_decl ';'
    {
        /**
         * Appends the next declared field to the structure block.
        **/

        $1.addField($2);
        $$ = $1;
    }
;

structure_field_init_decl:
    field_name ':' structure_field_spec_init
    {
        $3.identifier($1).use(Use.FIELD).source(Source.NONE);
        $$ = $3;
    }
;

field_name:
    IDENTIFIER
    {
        $$ = $1;
    }
;

structure_field_spec_init:
    custom_spec_init
    {
        /** Wraps the custom type builder in a SingleDeclaration for structure field. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
    | boolean_spec_init
    {
        /** Wraps the boolean builder in a SingleDeclaration for structure field. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
    | simple_spec_init
    {
        /** Wraps the simple type builder in a SingleDeclaration for structure field. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
    | enumerated_spec_init
    {
        /** Wraps the enumerated builder in an AnonymousEnumeratedField for structure field. */

        $$ = new AnnonymousEnumeratedField(this.symbolTable, $1);
    }
    | subrange_spec_init
    {
        /** Subrange not yet supported for structure fields; returns null. */

        $$ = null;
    }
    | array_spec_init
    {
        /** Wraps the array builder in a SingleDeclaration for structure field. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
    | string_spec_init
    {
        /** String not yet supported for structure fields; returns null. */

        $$ = null;
    }
;

%%
