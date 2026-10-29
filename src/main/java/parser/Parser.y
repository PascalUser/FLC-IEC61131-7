%language "Java"
%define api.parser.class {Parser}
%define api.package {parser}
%define api.parser.public
%define api.parser.final

/**
 * Bison Grammar for IEC 61131-3/7 Parser
 * =======================================
 * 
 * This grammar defines the syntax for parsing IEC 61131-3 (Programmable Controllers -
 * Programming Languages) with IEC 61131-7 (Fuzzy Control Programming) extensions.
 * 
 * The parser generates an AST represented through {@link utils.LexemeInfo} objects
 * stored in a shared {@link utils.SymbolTable}. Semantic actions throughout the grammar
 * construct type declarations, variable declarations, function blocks, fuzzy blocks
 * (fuzzify/defuzzify/ruleblock), and data type definitions.
 * 
 * Grammar Structure:
 * ------------------
 * 1. IEC 61131-7 Fuzzy Extensions (lines 161-487)
 *    - Function Block Declaration
 *    - Fuzzify/Defuzzify Blocks
 *    - Rule Blocks with fuzzy operators
 *    - Option/Pragma Blocks
 * 
 * 2. IEC 61131-3 Annex B Variable Declarations (lines 489-1370)
 *    - Variable/Constant Declarations
 *    - Elementary Types (numeric, date/time, bit-string)
 *    - Derived Types (subrange, enumerated, array, string)
 *    - Structure Initialization
 *    - Standard Function Blocks
 * 
 * 3. Data Type Declarations (lines 1434-1600)
 *    - TYPE ... END_TYPE blocks
 *    - Structure Type Definitions
 *    - All elementary and derived type references
 * 
 * Semantic Actions:
 * -----------------
 * Embedded Java code (within { ... }) constructs {@link utils.builders.LexemeInfoBuilder}
 * instances via {@link utils.builders.Director} and publishes them to the symbol table.
 * Builders are wrapped in declaration containers:
 * - {@link parser.initializations.MultipleDeclaration} for variable lists
 * - {@link parser.initializations.SingleDeclaration} for single declarations
 * - {@link parser.initializations.nodes.StructureDeclaration} for structures
 * - {@link parser.initializations.nodes.EnumeratedTypeDeclaration} for enumerated types
 * - {@link parser.initializations.nodes.AnnonymousEnumeratedVariable/Field} for anonymous enums
 * 
 * @see utils.SymbolTable
 * @see utils.LexemeInfo
 * @see utils.builders.Director
 * @see parser.initializations
 * @see parser.facades
 */

%code imports {
    import java.util.List;
    import java.util.ArrayList;
    import java.util.Collections;

    import utils.LexemeInfo;
    import utils.SymbolTable;
    import utils.enums.*;
    import utils.builders.*;

    import parser.facades.*;
    import parser.initializations.*;
    import parser.initializations.leafs.*;
    import parser.initializations.nodes.*;
    import parser.metadata.*;
    import parser.publishers.*;
    import parser.tree.*;
    import parser.utils.*;

    import ast.AbstractSyntaxTree;
    import ast.ruleblock.RuleBlockNode;
    import ast.ruleblock.operators.Operator;
    import ast.ruleblock.operators.OperatorFactory;
}

%code {
    /**
     * Name of the current program/function block being parsed.
     * Used to scope identifiers in the symbol table.
     */
    private String programName;
    private AbstractSyntaxTree ast;

    public AbstractSyntaxTree getAST() {
        return ast;
    }

    /**
     * Returns the name of the function block, the scope of its symbols (e.g. MAIN in MAIN#X).
     *
     * @return the function block name as stored in the symbol table keys
     */
    public String getFunctionBlockName() {
        return programName;
    }
}

/**
 * Parser parameter: the symbol table shared between lexer and parser
 * for storing semantic information during parsing.
 */
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
%token <String> NUMERIC_LITERAL STRING_LITERAL TIME_LITERAL BOOLEAN_LITERAL
%token ARRAY

%type <DeclarationBlock>
    var_init_decl_list

%type <TermBlock>
    opt_linguistic_term_list
    linguistic_term_list

%type <StructureBlock>
    structure_field_init_decl_list

%type <SingleDeclaration>
    linguistic_term
    type_spec_init
    structure_specification
    structure_field_init_decl
    structure_field_spec_init

%type <MultipleDeclaration>
    var_init_decl
    var_spec_init

%type <LexemeInfoBuilder>
    linguistic_term_value
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
    subrange_spec_init
    subrange_specification
    initialized_subrange
    array_spec_init
    array_specification
    initialized_array
    string_spec_init
    string_specification
    initialized_string


%type <Subtype>
    defuzzification_method
    defuzz_method
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
    opt_fb_io_var_declarations_list
    fb_io_var_declarations
    identifier_list
    opt_range
    range
    range_list
    point
    point_list
    singleton
    membership_function

%type <String>
     fuzzify_block_name
     defuzzify_block_name
     rule_block_name
     opt_weighting
     type_name_declaration
     field_name
     constant
     string_constant
     boolean_constant
     time_constant
     numeric_constant
     referenced_identifier

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

%type <ArrayParsingContext>
    array_initial_elements_list
    array_initial_elements
    repeated_initial_element

%type <VariableInitialization>
    default_value
    default_val

%type <List<RuleBlockNode>>
    opt_rule_block_list

%type <RuleBlockNode>
    rule_block

%type <List<TreeNode>>
    rule_list
    conclusion_list

%type <TreeNode>
    rules_decl
    rule
    condition
    condition_head
    conclusions
    and_operand
    or_operand
    condition_operand
    subcondition
    identifier_operand
    conclusion

%type <ConditionParsingContext>
    condition_tail
    and_condition_tail

%type <List<Operator>>
    operator_definition

%type <Operator>
    opt_operator_or
    or_type
    opt_operator_and
    and_type
    opt_activation_method
    activation_method
    act_type
    accumulation_method
    accu_type
    
%%

/* ---------------------------------- IEC61131-7 ----------------------------------------
 * 
 * IEC 61131-7: Fuzzy Control Language Extensions
 * ------------------------------------------------
 * This section defines the grammar for fuzzy control programming constructs:
 * - FUNCTION_BLOCK: Main program unit containing fuzzy logic
 * - FUZZIFY/END_FUZZIFY: Input fuzzification definitions
 * - DEFUZZIFY/END_DEFUZZIFY: Output defuzzification definitions  
 * - RULEBLOCK/END_RULEBLOCK: Fuzzy rule bases with operators
 * - OPTION/END_OPTION: Implementation-specific pragmas
 * 
 * These constructs extend IEC 61131-3 with fuzzy logic capabilities per IEC 61131-7.
 */

program:
    opt_data_type_declaration
    function_block_declaration
;

/**
 * Function Block Declaration
 * --------------------------
 * The top-level container for a fuzzy control program. Contains:
 * - Optional data type declarations (TYPE ... END_TYPE)
 * - Function block with I/O variables, internal variables, and fuzzy blocks
 *
 * Semantic Actions:
 * The function block itself is published under its name (e.g. MAIN) with its inputs and outputs
 * in declaration order as parameters (see parser.metadata.FunctionBlockMetadata).
 *
 * @see #function_block_declaration
 * @see #opt_data_type_declaration
 */
function_block_declaration:
    FUNCTION_BLOCK function_block_name
    opt_fb_io_var_declarations_list
    opt_other_var_declarations_list
    opt_function_block_body
    END_FUNCTION_BLOCK
    {
        SingleDeclaration functionBlock = new SingleDeclaration(this.symbolTable);
        Director.makeWithMetadata(functionBlock, new FunctionBlockMetadata($3));
        functionBlock.identifier(programName).publish();
    }
;

function_block_name:
    IDENTIFIER
    {
        this.programName = $1;
    }
;

opt_fb_io_var_declarations_list:
    /* empty */
    {
        $$ = new ArrayList<>();
    }
    | opt_fb_io_var_declarations_list fb_io_var_declarations
    {
        $1.addAll($2);
        $$ = $1;
    }
;

/**
 * Function Block I/O Variable Declarations (IEC 61131-3)
 * ------------------------------------------------------
 * Parses VAR_INPUT ... END_VAR and VAR_OUTPUT ... END_VAR blocks
 * for function block interface variables.
 * 
 * Syntax:
 *   VAR_INPUT [RETAIN|NON_RETAIN] END_VAR
 *     <identifier_list> : <type_spec> [:= <initial_value>] ;
 *     ...
 *   END_VAR
 * 
 *   VAR_OUTPUT [RETAIN|NON_RETAIN] END_VAR
 *     <identifier_list> : <type_spec> [:= <initial_value>] ;
 *     ...
 *   END_VAR
 * 
 * Key Rules:
 * - io_var_decl: VAR_INPUT (Source.IN) or VAR_OUTPUT (Source.OUT)
 * - var_retain_spec: Optional RETAIN/NON_RETAIN (persistence across power cycles)
 * - var_init_decl_list: Same as internal variable declarations
 * 
 * Semantic Actions:
 * Declarations are published with Source.IN or Source.OUT, Use.VARIABLE,
 * and scoped to the current program/function block name. The value is the list
 * of their keys in declaration order.
 *
 * @see #io_var_decl
 * @see #var_retain_spec
 * @see #var_init_decl_list
 * @see utils.enums.Source
 * @see utils.enums.Use
 */
fb_io_var_declarations:
    io_var_decl var_retain_spec var_init_decl_list ';' END_VAR
    {
        $3.source($1).use(Use.VARIABLE).setDeclarationScope(programName);
        $3.publish();
        $$ = $3.keys();
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
    {
        this.ast = new AbstractSyntaxTree($3);
    }
;

/**
 * Fuzzy Block Declarations
 * ------------------------
 * Defines the four types of fuzzy blocks that can appear in a function block body:
 * 
 * 1. FUZZIFY ... END_FUZZIFY
 *    Maps crisp input variables to linguistic terms with membership functions.
 *    Each term defines a fuzzy set (singleton or piecewise-linear point list).
 * 
 * 2. DEFUZZIFY ... END_DEFUZZIFY
 *    Maps fuzzy output variables to crisp values using defuzzification methods:
 *    COG (Center of Gravity), COGS, COA, LM (Left Maximum), RM (Right Maximum).
 *    Optional RANGE and DEFAULT value specifications.
 * 
 * 3. RULEBLOCK ... END_RULEBLOCK
 *    Contains fuzzy inference rules with configurable operators:
 *    - OR operator: MAX, ASUM (Algebraic Sum), BSUM (Bounded Sum)
 *    - AND operator: MIN, PROD (Product), BDIF (Bounded Difference)
 *    - Activation method: PROD, MIN
 *    - Accumulation method: MAX, BSUM, NSUM (Normalized Sum)
 *    Rules: RULE <n> : IF <condition> THEN <conclusion> [WITH <weight>]
 * 
 * 4. OPTION ... END_OPTION
 *    Implementation-specific pragmas for compiler directives.
 * 
 * @see #fuzzify_block
 * @see #defuzzify_block
 * @see #rule_block
 * @see #option_block
 */
opt_fuzzify_block_list:
    /* empty */
    | opt_fuzzify_block_list fuzzify_block
;

/**
 * Fuzzify Block (IEC 61131-7)
 * ---------------------------
 * Defines input variable fuzzification: mapping crisp values to linguistic terms
 * with membership functions.
 *
 * Syntax:
 *   FUZZIFY <variable_name>
 *     TERM <term_name> := <membership_function> ;
 *     ...
 *   END_FUZZIFY
 *
 * Membership Functions:
 * - Singleton: TERM name := <numeric_constant> ;
 * - Piecewise-linear: TERM name := (x1,y1) (x2,y2) ... ;
 *
 * The variable_name must match a VAR_INPUT declared in the function block.
 * Each term defines a fuzzy set used in rule conditions.
 *
 * @see #linguistic_term_list
 * @see #membership_function
 * @see #singleton
 * @see #point_list
 */
fuzzify_block:
    FUZZIFY fuzzify_block_name
    linguistic_term_list
    {
        ComponentDeclaration fuzzifierDeclaration = new ComponentDeclaration(this.symbolTable, $3);
        Director.makeWithMetadata(fuzzifierDeclaration, new FuzzifierMetadata($3));
        fuzzifierDeclaration.identifier($2).publish();
    }
    END_FUZZIFY
;

fuzzify_block_name:
    IDENTIFIER
    {
        $$ = programName + '#' + $1;
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
         ComponentDeclaration defuzzifierDeclaration = new ComponentDeclaration(this.symbolTable, $4);
         Director.makeWithMetadata(defuzzifierDeclaration, new DefuzzifierMetadata($3, $4, $5, $6));
         defuzzifierDeclaration.identifier($2).publish();
    }
;

/**
 * Defuzzify Block (IEC 61131-7)
 * -----------------------------
 * Defines output variable defuzzification: mapping fuzzy values to crisp outputs.
 * 
 * Syntax:
 *   DEFUZZIFY <variable_name>
 *     [ RANGE ( <min> .. <max> ) ; ]
 *     [ <linguistic_term_list> ]
 *     METHOD : <defuzz_method> ;
 *     DEFAULT := <default_val> ;
 *   END_DEFUZZIFY
 * 
 * Defuzzification Methods:
 * - COG: Center of Gravity (centroid)
 * - COGS: Center of Gravity for Singletons
 * - COA: Center of Area
 * - LM: Left Maximum
 * - RM: Right Maximum
 * 
 * Default Value:
 * - Numeric constant or NC (No Change)
 * 
 * The variable_name must match a VAR_OUTPUT declared in the function block.
 * Optional linguistic_term_list can redefine output terms locally.
 * 
 * @see #defuzzification_method
 * @see #defuzz_method
 * @see #default_value
 * @see #opt_range
 * @see #opt_linguistic_term_list
 */
defuzzify_block_name:
    IDENTIFIER
    {
        $$ = programName + '#' + $1;
    }
;

opt_linguistic_term_list:
    /* empty */
    {
        $$ = null;
    }
    | linguistic_term_list
    {
        $$ = $1;
    }
;

linguistic_term_list:
    linguistic_term
    {
        TermBlock block = new TermBlock();
        block.addTerm($1);
        $$ = block;
    }
    | linguistic_term_list linguistic_term
    {
        $1.addTerm($2);
        $$ = $1;
    }
;

/**
 * Linguistic Term Definition (IEC 61131-7)
 * ----------------------------------------
 * Defines a named fuzzy set with a membership function for use in fuzzification
 * and defuzzification blocks.
 * 
 * Syntax:
 *   TERM <name> := <membership_function> ;
 * 
 * Membership Functions:
 * - Singleton: TERM name := <numeric_constant> ;
 *   Represents a crisp value (membership = 1 at that point, 0 elsewhere)
 * - Piecewise-linear: TERM name := (x1,y1) (x2,y2) ... ;
 *   Defines a polygon membership function through (input, membership) points
 *   Points can use numeric constants or identifiers for condition_operand-coordinates
 * 
 * Terms are collected in linguistic_term_list and associated with their
 * parent fuzzify/defuzzify block variable.
 * 
 * @see #linguistic_term_value
 * @see #membership_function
 * @see #singleton
 * @see #point_list
 * @see #point
 */
linguistic_term:
    TERM IDENTIFIER ASSIGN_OP linguistic_term_value
    {
        $$ = new SingleDeclaration(this.symbolTable, $4).identifier($2);
    }
;

/**
 * Linguistic Term Value Alternatives
 * ----------------------------------
 * - IDENTIFIER: Reference to a previously defined term (for defuzzify block)
 * - membership_function: Inline membership function definition
 * 
 * @see #membership_function
 */
linguistic_term_value:
    IDENTIFIER ';'
    {
        LexemeInfoBuilder customTermBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(customTermBuilder, new CustomMetadata(this.symbolTable, $1));
        $$ = customTermBuilder;
    }
    | membership_function ';'
    {
        LexemeInfoBuilder termBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(termBuilder, new TermMetadata($1));
        $$ = termBuilder;
    }
;

/**
 * Membership Function Alternatives
 * --------------------------------
 * - singleton: Single numeric constant (crisp singleton fuzzy set)
 * - point_list: Sequence of (condition_operand,y) points defining piecewise-linear function
 *
 * Point Formats:
 * - ( <numeric>, <numeric> ): Both coordinates as literals
 * - ( <identifier>, <numeric> ): X-coordinate as reference, Y as literal
 *
 * @see #singleton
 * @see #point_list
 * @see #point
 */
membership_function:
    singleton
    {
        $$ = $1;
    }
    | point_list
    {
        $$ = $1;
    }
;

singleton:
    numeric_constant
    {
        List<String> point = new ArrayList<>();
        point.add($1);
        // TODO: Make 1 constant
        point.add("1");
        $$ = point;
    }
;

point_list:
    point
    {
        $$ = $1;
    }
    | point_list point
    {
        $1.addAll($2);
        $$ = $1;
    }
;

point:
    '(' numeric_constant ',' numeric_constant ')'
    {
        List<String> point = new ArrayList<>();
        point.add($2);
        point.add($4);
        $$ = point;
    }
    | '(' IDENTIFIER ',' numeric_constant ')'
    {
        List<String> point = new ArrayList<>();
        point.add($2);
        point.add($4);
        $$ = point;
    }
;

defuzzification_method:
    METHOD ':' defuzz_method ';'
    {
        $$ = $3;
    }
;

defuzz_method:
    COG
    {
        $$ = Subtype.COG;
    }
    | COGS
    {
        $$ = Subtype.COGS;
    }
    | COA
    {
        $$ = Subtype.COA;
    }
    | LM
    {
        $$ = Subtype.LM;
    }
    | RM
    {
        $$ = Subtype.RM;
    }
;

default_value:
    DEFAULT ASSIGN_OP default_val ';'
    {
        $$ = $3;
    }
;

default_val:
    numeric_constant
    {
        $$ = new VariableInitialization($1);
    }
    | NC
    {
        $$ = null;
    }
;

opt_range:
    /* empty */
    {
        $$ = null;
    }
    | RANGE '(' numeric_constant RANGE_OP numeric_constant ')' ';'
    {
        List<String> range = new ArrayList<>();
        range.add($3);
        range.add($5);
        $$ = range;
    }

;

opt_rule_block_list:
    /* empty */
    {
        $$ = new ArrayList<>();
    }
    | opt_rule_block_list rule_block
    {
        $1.add($2);
        $$ = $1;
    }
;

rule_block:
    RULEBLOCK rule_block_name
    operator_definition
    opt_activation_method
    accumulation_method
    rules_decl
    END_RULEBLOCK
    {   
        List<Operator> configuration = $3;
        configuration.add($4);
        configuration.add($5);
        
        List<TreeNode> componentChild = Collections.singletonList($6);
        TreeNode componentNode = new TreeNode(Label.COMPONENT, $2, componentChild);

        $$ = RuleblockASTBuilder.build(componentNode, configuration);
    }
;

rule_block_name:
    IDENTIFIER
    {
        $$ = programName + '#' + $1;
    }
;

operator_definition:
    opt_operator_or opt_operator_and ';'
    {
        List<Operator> operators = new ArrayList<>();
        operators.add($1);
        operators.add($2);
        $$ = operators;
    }
;

opt_operator_or:
    /* empty */
    {
        $$ = OperatorFactory.orDefault();
    }
    | OR ':' or_type
    {
        $$ = $3;
    }
;

opt_operator_and:
    /* empty */
    {
        $$ = OperatorFactory.andDefault();
    }
    | AND ':' and_type
    {
        $$ = $3;
    }
;

or_type:
    MAX
    {
        $$ = Operator.MAX;
    }
    | ASUM
    {
        $$ = Operator.ASUM;
    }
    | BSUM
    {
        $$ = Operator.BSUM;
    }
;

and_type:
    MIN
    {
        $$ = Operator.MIN;
    }
    | PROD
    {
        $$ = Operator.PROD;
    }
    | BDIF
    {
        $$ = Operator.BDIF;
    }
;

opt_activation_method:
    /* empty */
    {
        $$ = OperatorFactory.actDefault();
    }
    | activation_method
    {
        $$ = $1;
    }
;

activation_method:
    ACT ':' act_type ';'
    {
        $$ = $3;
    }
;

act_type:
    PROD
    {
        $$ = Operator.PROD;
    }
    | MIN
    {
        $$ = Operator.MIN;
    }
;

accumulation_method:
    ACCU ':' accu_type ';'
    { 
        $$ = $3;
    }
;

accu_type:
    MAX    
    { 
        $$ = Operator.MAX;  
    }
    | BSUM 
    { 
        $$ = Operator.BSUM; 
    }
    | NSUM 
    { 
        $$ = Operator.NSUM; 
    }
;

rules_decl:
    rule_list
    { 
        $$ = new TreeNode(Label.ACCUMULATION, $1);
    }
;

rule_list:
    /* empty */
    { 
        $$ = Collections.emptyList();
    }
    | rule_list rule
    { 
        List<TreeNode> rules = $1;
        if (rules.isEmpty()) {
            rules = new ArrayList<>();
        }
        rules.add($2);
        $$ = rules;
    }
;

rule:
    RULE numeric_constant ':' IF condition THEN conclusions opt_weighting ';'
    {
        List<TreeNode> condChild = Collections.singletonList($5);
        List<TreeNode> bodyChild = Collections.singletonList($7);
        TreeNode condNode = new TreeNode(Label.CONDITION, condChild);
        TreeNode bodyNode = new TreeNode(Label.BODY, bodyChild);

        List<TreeNode> ifChildren = new ArrayList<>();
        ifChildren.add(condNode);
        ifChildren.add(bodyNode);
        TreeNode ifNode = new TreeNode(Label.IF, ifChildren);

        if ($8.isEmpty()) { 
            $$ = ifNode;
        } else { 
            List<TreeNode> withChild = Collections.singletonList(ifNode);
            $$ = new TreeNode(Label.WITH, $8, withChild);
        }
    }
;

opt_weighting:
    /* empty */
    {
        $$ = "";
    }
    | WITH numeric_constant
    {
        $$ = $2;
    }
    | WITH IDENTIFIER
    {
        $$ = programName + "#" + $2;
    }
;

/**
 * Rule Condition Grammar (IEC 61131-7)
 * ------------------------------------
 * Parses fuzzy rule antecedents (IF part) with boolean logic.
 * 
 * Grammar Structure:
 * condition ::= condition_operand condition_tail | IDENTIFIER condition_tail
 * condition_tail ::= ε | AND condition_operand condition_tail | OR condition_operand condition_tail
 *                  | AND IDENTIFIER condition_tail | OR IDENTIFIER condition_tail
 * condition_operand ::= NOT condition_operand | NOT IDENTIFIER | subcondition | '(' condition ')'
 * subcondition ::= IDENTIFIER IS IDENTIFIER | IDENTIFIER IS NOT IDENTIFIER
 * 
 * This grammar supports:
 * - Simple conditions: var IS term
 * - Negated conditions: var IS NOT term
 * - NOT operator: NOT var IS term, NOT (condition)
 * - AND/OR combinations with precedence via recursion
 * - Parenthesized sub-conditions
 * 
 * Note: The grammar uses left-recursive condition_tail for AND/OR chains.
 * The 'condition_operand' non-terminal handles NOT and atomic conditions.
 * 
 * @see #condition_tail
 * @see #condition_operand
 * @see #subcondition
 * @see #conclusion_list
 */
condition:
    condition_head condition_tail
    {
        $2.addOperand($1);
        $$ = $2.getConditionNode();
    }
;

condition_head:
    condition_operand
    {
        $$ = $1;
    }
    | identifier_operand
    {
        $$ = $1;
    }
;

/**
 * Condition Tail - AND/OR Chains
 * ------------------------------
 * Left-recursive rule for chaining AND/OR operations.
 * Supports both simple identifiers and complex 'condition_operand' expressions
 * as operands for the logical connectives.
 * 
 * @see #condition
 * @see #condition_operand
 */
condition_tail:
    and_condition_tail
    { 
        $$ = $1;
    }
    | OR or_operand condition_tail
    {
        $3.addOperand($2);
        $3.addOperator(Label.OR);
        $$ = $3;
    }
;

or_operand:
    condition_operand
    { 
        $$ = $1;
    }
    | identifier_operand
    { 
        $$ = $1;
    }
;

and_condition_tail:
    /* empty */
    {
        $$ = new ConditionParsingContext();
    }
    | AND and_operand and_condition_tail
    {
        $3.addOperand($2);
        $3.addOperator(Label.AND);
        $$ = $3;
    }
;

and_operand:
    condition_operand
    { 
        $$ = $1;
    }
    | identifier_operand
    { 
        $$ = $1;
    }
;

/**
 * Atomic Condition with Optional NOT
 * ----------------------------------
 * Handles the core atomic conditions with NOT operator support:
 * - NOT condition_operand: Recursive NOT (supports multiple NOTs)
 * - NOT IDENTIFIER: Negated simple identifier
 * - subcondition: IS/IS NOT relational condition
 * - '(' condition ')': Parenthesized sub-expression
 * 
 * @see #subcondition
 * @see #condition
 */
condition_operand:
    NOT condition_operand
    {
        List<TreeNode> notChild = Collections.singletonList($2);
        $$ = new TreeNode(Label.NOT, notChild);
    }
    | NOT identifier_operand
    {
        List<TreeNode> notChild = Collections.singletonList($2);
        $$ = new TreeNode(Label.NOT, notChild);
    }
    | subcondition
    {
        $$ = $1;
    }
    | '(' condition ')'
    {
        $$ = $2;
    }
;

/**
 * Simple Relational Condition
 * ---------------------------
 * Basic fuzzy relational expressions:
 * - IDENTIFIER IS IDENTIFIER: Variable matches linguistic term
 * - IDENTIFIER IS NOT IDENTIFIER: Variable does not match term
 * 
 * The first identifier is a variable (input), the second is a linguistic term.
 * 
 * @see #condition
 */
subcondition:
    identifier_operand IS identifier_operand
    {
        List<TreeNode> isChildren = new ArrayList<>();
        isChildren.add($1);
        isChildren.add($3);
        $$ = new TreeNode(Label.IS, isChildren);
    }
    | identifier_operand IS NOT identifier_operand
    {
        List<TreeNode> isChildren = new ArrayList<>();
        isChildren.add($1);
        isChildren.add($4);
        TreeNode isNode = new TreeNode(Label.IS, isChildren);

        List<TreeNode> notChild = Collections.singletonList(isNode);
        $$ = new TreeNode(Label.NOT, notChild);
    }
;

identifier_operand:
    IDENTIFIER
    {
        $$ = new TreeNode(Label.IDENTIFIER, $1, Collections.emptyList());
    }
;

/**
 * Rule Conclusion List (THEN Part)
 * --------------------------------
 * Parses the consequent of fuzzy rules (THEN part).
 * 
 * Syntax:
 *   IDENTIFIER IS IDENTIFIER          // Single assignment: output_var IS term
 *   IDENTIFIER                        // Implicit: output_var (uses default term)
 *   conclusion_list ',' ...           // Multiple conclusions separated by commas
 * 
 * Forms:
 * - Explicit assignment: output_var IS linguistic_term
 * - Implicit assignment: output_var (semantic meaning depends on context)
 * - Multiple outputs: var1 IS term1, var2 IS term2, var3
 * 
 * The grammar is left-recursive to handle comma-separated lists.
 * 
 * @see #rule
 * @see #subcondition (similar syntax for IS/IS NOT)
 */
 
conclusions:
    conclusion_list
    { 
        $$ = new TreeNode(Label.ACTIVATION, $1);
    }
;
 
conclusion_list:
    conclusion
    { 
        List<TreeNode> conclusions = new ArrayList<>();
        conclusions.add($1);
        $$ = conclusions; 
    }
    | conclusion_list ',' conclusion
    {  
        $1.add($3);
        $$ = $1;
    }
;

conclusion:
     identifier_operand IS identifier_operand
     { 
        List<TreeNode> isChildren = new ArrayList<>();
        isChildren.add($1);
        isChildren.add($3);
        $$ = new TreeNode(Label.IS, isChildren);
     }
     | identifier_operand
     { 
        $$ = $1;
     }
;

opt_option_block_list:
    /* empty */
    | opt_option_block_list option_block
;

option_block:
    OPTION pragma_list END_OPTION
;

/**
 * Option/Pragma Blocks (IEC 61131-7)
 * ----------------------------------
 * Implementation-specific compiler directives and annotations.
 * 
 * Syntax:
 *   OPTION
 *     PRAGMA <identifier> ;
 *     PRAGMA <identifier> <numeric_constant> ;
 *     ...
 *   END_OPTION
 * 
 * Pragmas can appear in OPTION blocks within function blocks.
 * They provide hints to the compiler/code generator (e.g., optimization flags,
 * target-specific settings, documentation annotations).
 *
 * Each pragma is published in the symbol table under <function_block>@option#<NAME>
 * (e.g. MAIN@option#BIT_WIDTH); a pragma without value is a flag equal to TRUE.
 *
 * @see #option_block
 */
pragma_list:
    pragma
    | pragma_list pragma
;

pragma:
    PRAGMA IDENTIFIER ';'
    {
        SingleDeclaration pragmaDeclaration = new SingleDeclaration(this.symbolTable);
        Director.makeWithMetadata(pragmaDeclaration, PragmaMetadata.flag(this.symbolTable));
        pragmaDeclaration.identifier($2).setDeclarationScope(programName + PragmaMetadata.SCOPE_SUFFIX);
        pragmaDeclaration.publish();
    }
    | PRAGMA IDENTIFIER numeric_constant ';'
    {
        SingleDeclaration pragmaDeclaration = new SingleDeclaration(this.symbolTable);
        Director.makeWithMetadata(pragmaDeclaration, PragmaMetadata.valued(this.symbolTable, $3));
        pragmaDeclaration.identifier($2).setDeclarationScope(programName + PragmaMetadata.SCOPE_SUFFIX);
        pragmaDeclaration.publish();
    }
;

/**
 * IEC 61131-3 Annex B: Variable Declaration Grammar
 * ---------------------------------------------------
 * This section implements the standard variable declaration syntax per IEC 61131-3.
 * 
 * Variable Declaration Structure:
 * -------------------------------
 * VAR [INPUT|OUTPUT] [RETAIN|NON_RETAIN] END_VAR
 *   <identifier_list> : <type_spec> [:= <initial_value>] ;
 * 
 * Supported Type Categories:
 * --------------------------
 * 1. Elementary Types (simple_specification)
 *    - Numeric: SINT, INT, DINT, LINT, USINT, UINT, UDINT, ULINT, REAL, LREAL
 *    - Date/Time: TIME, DATE, TIME_OF_DAY, DATE_AND_TIME
 *    - Bit Strings: BYTE, WORD, DWORD, LWORD
 *    - Boolean: BOOL (with optional R_EDGE/F_EDGE)
 * 
 * 2. Derived Types
 *    - Subrange: <integer_type> ( <lower> .. <upper> )
 *    - Enumerated: ( <identifier_list> )
 *    - Array: ARRAY [ <range_list> ] OF <element_type>
 *    - String: STRING | WSTRING [ <length> ]
 *    - Custom/User-defined: <type_identifier>
 * 
 * 3. Structure Types (STRUCT ... END_STRUCT)
 *    Nested field declarations with same type system.
 * 
 * Initialization:
 * ---------------
 * Constants and variables can be initialized with:
 * - Literals (numeric, string, boolean, time)
 * - Referenced identifiers (with optional scope#name mangling)
 * - Structure initializations: ( field := value, ... )
 * - Array initializations: [ value, value, ... ] with repetition: n(value)
 * 
 * Semantic Actions:
 * -----------------
 * Each declaration rule constructs a LexemeInfoBuilder via Director,
 * wraps it in an appropriate declaration container, and publishes to symbol table.
 * 
 * @see utils.builders.Director
 * @see parser.initializations.MultipleDeclaration
 * @see parser.initializations.SingleDeclaration
 * @see parser.initializations.nodes.StructureDeclaration
 */
var_declarations:
    var_id_decl var_constant_spec var_init_decl_list ';' END_VAR
    {
        $3.source(Source.INTERNAL).use($2).setDeclarationScope(programName);
        $3.publish();
    }
;

/**
 * Variable Declaration Grammar (IEC 61131-3 Annex B)
 * --------------------------------------------------
 * Parses VAR ... END_VAR blocks for internal variables and constants.
 * 
 * Syntax:
 *   VAR [CONSTANT] END_VAR
 *     <identifier_list> : <type_spec> [:= <initial_value>] ;
 *     ...
 *   END_VAR
 * 
 * Key Rules:
 * - var_id_decl: VAR keyword (starts declaration block)
 * - var_constant_spec: Optional CONSTANT keyword (defaults to VARIABLE)
 * - var_init_decl_list: Semicolon-separated list of variable declarations
 * - var_init_decl: identifier_list ':' type_specification [with initialization]
 * - identifier_list: Comma-separated identifiers sharing the same type
 * 
 * Semantic Actions:
 * Each var_init_decl creates a builder via var_spec_init, assigns identifiers,
 * and wraps in DeclarationBlock. The block is published with Source.INTERNAL
 * and appropriate Use (VARIABLE or CONSTANT).
 * 
 * @see #var_spec_init
 * @see #identifier_list
 * @see parser.initializations.DeclarationBlock
 * @see utils.enums.Use
 * @see utils.enums.Source
 */
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

/**
 * Variable Type Specification Dispatch
 * ------------------------------------
 * Selects the appropriate type specification parser for variable declarations
 * (VAR ... END_VAR, VAR_INPUT/OUTPUT ... END_VAR). Each alternative wraps the
 * builder in a declaration container for handling comma-separated identifier lists.
 * 
 * Alternatives:
 * - custom_spec_init: User-defined types (MultipleDeclaration)
 * - boolean_spec_init: BOOL type with optional edge/initialization (MultipleDeclaration)
 * - simple_spec_init: Elementary types (MultipleDeclaration)
 * - subrange_spec_init: Subrange types (MultipleDeclaration)
 * - enumerated_spec_init: Anonymous enumerated types (AnnonymousEnumeratedVariable)
 * - array_spec_init: Array types (MultipleDeclaration)
 * - string_spec_init: String types (MultipleDeclaration)
 * 
 * The MultipleDeclaration container handles publishing multiple identifiers
 * sharing the same type specification. Anonymous enumerated types use a
 * specialized container since they're defined inline.
 * 
 * @see #custom_spec_init
 * @see #boolean_spec_init
 * @see #simple_spec_init
 * @see #subrange_spec_init
 * @see #enumerated_spec_init
 * @see #array_spec_init
 * @see #string_spec_init
 * @see parser.initializations.MultipleDeclaration
 * @see parser.initializations.nodes.AnnonymousEnumeratedVariable
 */
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
        /** Wraps the subrange builder in an MultipleDeclaration for variable list handling. */

        $$ = new MultipleDeclaration(this.symbolTable, $1);
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
        /** Wraps the string builder in a MultipleDeclaration for variable list handling. */

        $$ = new MultipleDeclaration(this.symbolTable, $1);
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
        Director.makeWithMetadata(customDeclaration, new CustomMetadata(this.symbolTable, $1));
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

        ParsedIdentifier referencedIdentifier = IdentifierParser.parse($3, $1.customType());

        LexemeInfo metadata = this.symbolTable.get($1.customType());
        if (metadata.type.equals(Type.ENUMERATE) && metadata.parameters.contains(referencedIdentifier.name())) {
            $1.initialValue(new VariableInitialization(referencedIdentifier.mangled()));
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
        Director.makeWithMetadata(primitiveBuilder, new PrimitiveMetadata(symbolTable, Subtype.BOOL));
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
        Director.makeWithMetadata(primitiveBuilder, new PrimitiveMetadata(this.symbolTable, $1));
        $$ = primitiveBuilder;
    }
;

initialized_simple:
    simple_specification ASSIGN_OP constant
    {
        $1.initialValue(new VariableInitialization($3));
    }
;

/**
 * Elementary Type Definitions
 * ---------------------------
 * Core primitive types per IEC 61131-3. Each rule returns a {@link utils.enums.Subtype}
 * enum value used by {@link utils.builders.Director#makeWithMetadata} to create type metadata.
 * 
 * Categories:
 * - Signed Integers: SINT (8-bit), INT (16-bit), DINT (32-bit), LINT (64-bit)
 * - Unsigned Integers: USINT, UINT, UDINT, ULINT
 * - Real Numbers: REAL (32-bit), LREAL (64-bit)
 * - Date/Time: TIME, DATE, TIME_OF_DAY, DATE_AND_TIME
 * - Bit Strings: BYTE (8), WORD (16), DWORD (32), LWORD (64)
 * - Boolean: BOOL
 * 
 * @see utils.enums.Subtype
 * @see utils.builders.Director#makeWithMetadata
 */
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
    {
        $$ = $1;
    }
    | initialized_subrange
    {
        $$ = $1;
    }
;

subrange_specification:
    integer_type '(' range ')'
    {
        LexemeInfoBuilder subrangeBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(subrangeBuilder, new SubrangeMetadata($1, $3.get(0), $3.get(1)));
        $$ = subrangeBuilder;
    }
;

initialized_subrange:
    subrange_specification ASSIGN_OP numeric_constant
    {
        $1.initialValue(new VariableInitialization($3));
        $$ = $1;
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
        Director.makeWithMetadata(enumBuilder, new EnumeratedMetadata($2));
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

        ParsedIdentifier referencedIdentifier = IdentifierParser.parse($3, programName);
        if (!referencedIdentifier.scope().isEmpty()) {
            // TODO: throw error
        }

        $1.initialValue(new VariableInitialization(referencedIdentifier.mangled()));
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
        ArrayMetadata arrayMetadata = new ArrayMetadata(this.symbolTable, Subtype.CUSTOM, $6, $3);

        LexemeInfoBuilder arrayBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(arrayBuilder, arrayMetadata);
        $$ = arrayBuilder;
    }
    | ARRAY '[' range_list ']' OF non_generic_type
    {
        ArrayMetadata arrayMetadata = new ArrayMetadata(this.symbolTable, $6, null, $3);

        LexemeInfoBuilder arrayBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(arrayBuilder, arrayMetadata);
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

/**
 * Array Initialization Grammar
 * ----------------------------
 * Parses array literal initializations with support for:
 * - Simple values: [1, 2, 3, 4]
 * - Repeated elements: [3(0), 2(1)]  // 3 zeros, then 2 ones
 * - Nested arrays and structures
 * - Multi-dimensional arrays (via range_list in declaration)
 * 
 * Syntax:
 *   [ <element_list> ]
 *   <element_list> := <element> [, <element>]*
 *   <element> := <constant> | <identifier> | <structure_init> | <array_init> | <repeated_element>
 *   <repeated_element> := <count> '(' <element> ')'
 * 
 * Semantic Processing:
 * Uses {@link parser.utils.ArrayParsingContext} to track repeated elements
 * and {@link parser.utils.PathInitializationInfo} to map array indices to values.
 * Indices are computed as ranges (e.g., "0..2#value" for 3 repeated values).
 * 
 * @see parser.utils.ArrayParsingContext
 * @see parser.utils.PathInitializationInfo
 * @see #array_initial_elements_list
 * @see #array_initial_elements
 * @see #repeated_initial_element
 */
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
        $$ = new ArrayParsingContext(endIdx, newPathsInfo);
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
        $$ = new ArrayParsingContext(endIdx, newPathsInfo);
    }
;

array_initial_elements:
    array_initial_element
    {
        /** Creates a ArrayParsingContext for a single array element. */

        $$ = new ArrayParsingContext(1, $1);
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
        $$ = new ArrayParsingContext(Integer.parseInt($1), $3);
    }
;

/**
 * Structure Initialization Grammar
 * --------------------------------
 * Parses structure literal initializations with named field assignments.
 * 
 * Syntax:
 *   ( <field_init_list> )
 *   <field_init_list> := <field_init> [, <field_init>]*
 *   <field_init> := <field_name> := <value>
 *   <value> := <constant> | <identifier> | <structure_init> | <array_init>
 * 
 * Semantic Processing:
 * Each field initialization creates a {@link parser.utils.PathInitializationInfo}
 * with the field name as path. Nested structures/arrays use path mangling
 * (e.g., "outerField#innerField#0..2#value").
 * 
 * @see parser.utils.PathInitializationInfo
 * @see #structure_field_init_list
 * @see #initialized_struct_field
 * @see #array_initialization
 */
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
    {
        $$ = $1;
    }
    | initialized_string
    {
        $$ = $1;
    }
;

string_specification:
    string_type
    {
        LexemeInfoBuilder stringBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(stringBuilder, new PrimitiveMetadata(this.symbolTable, $1));
        $$ = stringBuilder;
    }
    | string_type '[' numeric_constant ']'
    {
        LexemeInfoBuilder stringBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(stringBuilder, new StringMetadata(this.symbolTable, $1, $3));
        $$ = stringBuilder;
    }
;

initialized_string:
    string_specification ASSIGN_OP string_constant
    {
        $1.initialValue(new VariableInitialization($3));
        $$ = $1;
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

/**
 * Data Type Declarations (TYPE ... END_TYPE)
 * ------------------------------------------
 * Defines user-defined data types that can be reused in variable declarations.
 * 
 * Syntax:
 *   TYPE
 *     <type_name> : <type_specification> ;
 *     ...
 *   END_TYPE
 * 
 * Supported Type Specifications:
 * - Custom/User-defined types (references to other TYPE definitions)
 * - Elementary types (simple_specification)
 * - Enumerated types (enumerated_specification)
 * - Subrange types (subrange_specification)
 * - Array types (array_specification)
 * - Structure types (STRUCT ... END_STRUCT)
 * - String types (string_specification)
 * 
 * Semantic Actions:
 * Each type_declaration creates a builder, sets its identifier, marks it as
 * Use.TYPE with Source.NONE, and publishes to the symbol table for later reference.
 * 
 * @see #type_spec_init
 * @see #structure_specification
 * @see utils.enums.Use#TYPE
 */
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

/**
 * Type Specification for TYPE Declarations
 * ----------------------------------------
 * Dispatches to the appropriate type specification parser based on the
 * type category. Each alternative wraps the builder in a declaration container
 * suitable for TYPE ... END_TYPE blocks.
 * 
 * Alternatives:
 * - custom_spec_init: User-defined type references (SingleDeclaration)
 * - simple_spec_init: Elementary types (SingleDeclaration)
 * - enumerated_spec_init: Enumerated types (EnumeratedTypeDeclaration)
 * - subrange_spec_init: Subrange types (SingleDeclaration)
 * - array_spec_init: Array types (SingleDeclaration)
 * - structure_specification: Structure types (StructureDeclaration, direct)
 * - string_spec_init: String types (SingleDeclaration)
 * 
 * All builders are configured with Use.TYPE and Source.NONE before publishing.
 * 
 * @see #custom_spec_init
 * @see #simple_spec_init
 * @see #enumerated_spec_init
 * @see #subrange_spec_init
 * @see #array_spec_init
 * @see #structure_specification
 * @see #string_spec_init
 * @see parser.initializations.SingleDeclaration
 * @see parser.initializations.nodes.EnumeratedTypeDeclaration
 * @see parser.initializations.nodes.StructureDeclaration
 */
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
        /** Wraps the subrange builder in an SingleDeclaration for type declaration. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
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
        /** Wraps the string builder in a SingleDeclaration for type declaration. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
;

/**
 * Derived Type: Structure (STRUCT ... END_STRUCT)
 * -----------------------------------------------
 * Defines a composite type with named fields, each having its own type specification.
 * Fields can be any supported type: elementary, derived, or nested structures.
 * 
 * Syntax:
 *   STRUCT
 *     <field_name> : <field_type_spec> ;
 *     ...
 *   END_STRUCT
 * 
 * Semantic Actions:
 * Creates a {@link parser.initializations.nodes.StructureDeclaration} with a
 * {@link parser.utils.metadata.StructureMetadata} containing all fields.
 * The StructureBlock accumulates fields during parsing.
 * 
 * @see parser.initializations.nodes.StructureDeclaration
 * @see parser.initializations.nodes.StructureBlock
 * @see parser.utils.metadata.StructureMetadata
 * @see #structure_field_init_decl_list
 * @see #structure_field_spec_init
 */
structure_specification:
    STRUCT structure_field_init_decl_list END_STRUCT
    {
        StructureDeclaration structDeclaration = new StructureDeclaration(this.symbolTable, $2);
        Director.makeWithMetadata(structDeclaration, new StructureMetadata($2));

        $$ = structDeclaration;
    }
;

structure_field_init_decl_list:
    structure_field_init_decl ';'
    {
        StructureBlock structBlock = new StructureBlock();
        structBlock.addField($1);
        $$ = structBlock;
    }
    | structure_field_init_decl_list structure_field_init_decl ';'
    {
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

/**
 * Structure Field Type Specification Dispatch
 * -------------------------------------------
 * Selects the appropriate type specification parser for structure field declarations
 * (STRUCT ... END_STRUCT). Similar to var_spec_init but uses SingleDeclaration
 * since each field has a single identifier (no comma-separated lists).
 * 
 * Alternatives:
 * - custom_spec_init: User-defined types (SingleDeclaration)
 * - boolean_spec_init: BOOL type (SingleDeclaration)
 * - simple_spec_init: Elementary types (SingleDeclaration)
 * - enumerated_spec_init: Anonymous enumerated types (AnnonymousEnumeratedField)
 * - subrange_spec_init: Subrange types (SingleDeclaration)
 * - array_spec_init: Array types (SingleDeclaration)
 * - string_spec_init: String types (SingleDeclaration)
 * 
 * Anonymous enumerated fields use a specialized container (AnnonymousEnumeratedField)
 * distinct from variable-level anonymous enums (AnnonymousEnumeratedVariable).
 * 
 * @see #custom_spec_init
 * @see #boolean_spec_init
 * @see #simple_spec_init
 * @see #enumerated_spec_init
 * @see #subrange_spec_init
 * @see #array_spec_init
 * @see #string_spec_init
 * @see parser.initializations.SingleDeclaration
 * @see parser.initializations.nodes.AnnonymousEnumeratedField
 * @see parser.initializations.nodes.AnnonymousEnumeratedVariable
 */
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
        /** Wraps the subrange builder in an SingleDeclaration for type declaration. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
    | array_spec_init
    {
        /** Wraps the array builder in a SingleDeclaration for structure field. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
    | string_spec_init
    {
        /** Wraps the string builder in a SingleDeclaration for structure field. */

        $$ = new SingleDeclaration(this.symbolTable, $1);
    }
;

%%
