/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java

   Copyright (C) 2007-2015, 2018-2021 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

package parser;



import java.text.MessageFormat;
import java.util.ArrayList;
/* "%code imports" blocks.  */
/* "src/main/java/parser/Parser.y":57  */

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

/* "src/main/java/parser/Parser.java":70  */

/**
 * A Bison parser, automatically generated from <tt>src/main/java/parser/Parser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public final class Parser
{
  /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "3.8.2";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";






  public enum SymbolKind
  {
    S_YYEOF(0),                    /* "end of file"  */
    S_YYerror(1),                  /* error  */
    S_YYUNDEF(2),                  /* "invalid token"  */
    S_TYPE(3),                     /* TYPE  */
    S_END_TYPE(4),                 /* END_TYPE  */
    S_STRUCT(5),                   /* STRUCT  */
    S_END_STRUCT(6),               /* END_STRUCT  */
    S_FUNCTION_BLOCK(7),           /* FUNCTION_BLOCK  */
    S_END_FUNCTION_BLOCK(8),       /* END_FUNCTION_BLOCK  */
    S_FUZZIFY(9),                  /* FUZZIFY  */
    S_END_FUZZIFY(10),             /* END_FUZZIFY  */
    S_DEFUZZIFY(11),               /* DEFUZZIFY  */
    S_END_DEFUZZIFY(12),           /* END_DEFUZZIFY  */
    S_RULEBLOCK(13),               /* RULEBLOCK  */
    S_END_RULEBLOCK(14),           /* END_RULEBLOCK  */
    S_OPTION(15),                  /* OPTION  */
    S_END_OPTION(16),              /* END_OPTION  */
    S_TERM(17),                    /* TERM  */
    S_METHOD(18),                  /* METHOD  */
    S_DEFAULT(19),                 /* DEFAULT  */
    S_RANGE(20),                   /* RANGE  */
    S_RULE(21),                    /* RULE  */
    S_IF(22),                      /* IF  */
    S_THEN(23),                    /* THEN  */
    S_WITH(24),                    /* WITH  */
    S_ACT(25),                     /* ACT  */
    S_ACCU(26),                    /* ACCU  */
    S_IS(27),                      /* IS  */
    S_NOT(28),                     /* NOT  */
    S_AND(29),                     /* AND  */
    S_OR(30),                      /* OR  */
    S_COG(31),                     /* COG  */
    S_COGS(32),                    /* COGS  */
    S_COA(33),                     /* COA  */
    S_LM(34),                      /* LM  */
    S_RM(35),                      /* RM  */
    S_NC(36),                      /* NC  */
    S_MIN(37),                     /* MIN  */
    S_MAX(38),                     /* MAX  */
    S_ASUM(39),                    /* ASUM  */
    S_BSUM(40),                    /* BSUM  */
    S_PROD(41),                    /* PROD  */
    S_BDIF(42),                    /* BDIF  */
    S_NSUM(43),                    /* NSUM  */
    S_IDENTIFIER(44),              /* IDENTIFIER  */
    S_STD_FB_IDENTIFIER(45),       /* STD_FB_IDENTIFIER  */
    S_ASSIGN_OP(46),               /* ASSIGN_OP  */
    S_RANGE_OP(47),                /* RANGE_OP  */
    S_PRAGMA(48),                  /* PRAGMA  */
    S_VAR_INPUT(49),               /* VAR_INPUT  */
    S_RETAIN(50),                  /* RETAIN  */
    S_NON_RETAIN(51),              /* NON_RETAIN  */
    S_END_VAR(52),                 /* END_VAR  */
    S_BOOL(53),                    /* BOOL  */
    S_R_EDGE(54),                  /* R_EDGE  */
    S_F_EDGE(55),                  /* F_EDGE  */
    S_VAR_OUTPUT(56),              /* VAR_OUTPUT  */
    S_VAR(57),                     /* VAR  */
    S_CONSTANT(58),                /* CONSTANT  */
    S_STRING(59),                  /* STRING  */
    S_WSTRING(60),                 /* WSTRING  */
    S_BYTE(61),                    /* BYTE  */
    S_WORD(62),                    /* WORD  */
    S_DWORD(63),                   /* DWORD  */
    S_LWORD(64),                   /* LWORD  */
    S_TIME(65),                    /* TIME  */
    S_TIME_OF_DAY(66),             /* TIME_OF_DAY  */
    S_DATE(67),                    /* DATE  */
    S_DATE_AND_TIME(68),           /* DATE_AND_TIME  */
    S_OF(69),                      /* OF  */
    S_SINT(70),                    /* SINT  */
    S_INT(71),                     /* INT  */
    S_DINT(72),                    /* DINT  */
    S_LINT(73),                    /* LINT  */
    S_USINT(74),                   /* USINT  */
    S_UINT(75),                    /* UINT  */
    S_UDINT(76),                   /* UDINT  */
    S_ULINT(77),                   /* ULINT  */
    S_REAL(78),                    /* REAL  */
    S_LREAL(79),                   /* LREAL  */
    S_NUMERIC_LITERAL(80),         /* NUMERIC_LITERAL  */
    S_STRING_LITERAL(81),          /* STRING_LITERAL  */
    S_TIME_LITERAL(82),            /* TIME_LITERAL  */
    S_BOOLEAN_LITERAL(83),         /* BOOLEAN_LITERAL  */
    S_ARRAY(84),                   /* ARRAY  */
    S_85_(85),                     /* ';'  */
    S_86_(86),                     /* '('  */
    S_87_(87),                     /* ','  */
    S_88_(88),                     /* ')'  */
    S_89_(89),                     /* ':'  */
    S_90_(90),                     /* '#'  */
    S_91_(91),                     /* '['  */
    S_92_(92),                     /* ']'  */
    S_YYACCEPT(93),                /* $accept  */
    S_program(94),                 /* program  */
    S_function_block_declaration(95), /* function_block_declaration  */
    S_function_block_name(96),     /* function_block_name  */
    S_opt_fb_io_var_declarations_list(97), /* opt_fb_io_var_declarations_list  */
    S_fb_io_var_declarations(98),  /* fb_io_var_declarations  */
    S_io_var_decl(99),             /* io_var_decl  */
    S_var_retain_spec(100),        /* var_retain_spec  */
    S_opt_other_var_declarations_list(101), /* opt_other_var_declarations_list  */
    S_other_var_declarations(102), /* other_var_declarations  */
    S_opt_function_block_body(103), /* opt_function_block_body  */
    S_opt_fuzzify_block_list(104), /* opt_fuzzify_block_list  */
    S_fuzzify_block(105),          /* fuzzify_block  */
    S_106_1(106),                  /* $@1  */
    S_fuzzify_block_name(107),     /* fuzzify_block_name  */
    S_opt_defuzzify_block_list(108), /* opt_defuzzify_block_list  */
    S_defuzzify_block(109),        /* defuzzify_block  */
    S_defuzzify_block_name(110),   /* defuzzify_block_name  */
    S_opt_linguistic_term_list(111), /* opt_linguistic_term_list  */
    S_linguistic_term_list(112),   /* linguistic_term_list  */
    S_linguistic_term(113),        /* linguistic_term  */
    S_linguistic_term_value(114),  /* linguistic_term_value  */
    S_membership_function(115),    /* membership_function  */
    S_singleton(116),              /* singleton  */
    S_point_list(117),             /* point_list  */
    S_point(118),                  /* point  */
    S_defuzzification_method(119), /* defuzzification_method  */
    S_defuzz_method(120),          /* defuzz_method  */
    S_default_value(121),          /* default_value  */
    S_default_val(122),            /* default_val  */
    S_opt_range(123),              /* opt_range  */
    S_opt_rule_block_list(124),    /* opt_rule_block_list  */
    S_rule_block(125),             /* rule_block  */
    S_rule_block_name(126),        /* rule_block_name  */
    S_operator_definition(127),    /* operator_definition  */
    S_opt_operator_or(128),        /* opt_operator_or  */
    S_opt_operator_and(129),       /* opt_operator_and  */
    S_or_type(130),                /* or_type  */
    S_and_type(131),               /* and_type  */
    S_opt_activation_method(132),  /* opt_activation_method  */
    S_activation_method(133),      /* activation_method  */
    S_act_type(134),               /* act_type  */
    S_accumulation_method(135),    /* accumulation_method  */
    S_accu_type(136),              /* accu_type  */
    S_rules_decl(137),             /* rules_decl  */
    S_rule_list(138),              /* rule_list  */
    S_rule(139),                   /* rule  */
    S_opt_weighting(140),          /* opt_weighting  */
    S_condition(141),              /* condition  */
    S_condition_head(142),         /* condition_head  */
    S_condition_tail(143),         /* condition_tail  */
    S_or_operand(144),             /* or_operand  */
    S_and_condition_tail(145),     /* and_condition_tail  */
    S_and_operand(146),            /* and_operand  */
    S_condition_operand(147),      /* condition_operand  */
    S_subcondition(148),           /* subcondition  */
    S_identifier_operand(149),     /* identifier_operand  */
    S_conclusions(150),            /* conclusions  */
    S_conclusion_list(151),        /* conclusion_list  */
    S_conclusion(152),             /* conclusion  */
    S_opt_option_block_list(153),  /* opt_option_block_list  */
    S_option_block(154),           /* option_block  */
    S_pragma_list(155),            /* pragma_list  */
    S_pragma(156),                 /* pragma  */
    S_var_declarations(157),       /* var_declarations  */
    S_var_id_decl(158),            /* var_id_decl  */
    S_var_constant_spec(159),      /* var_constant_spec  */
    S_var_init_decl_list(160),     /* var_init_decl_list  */
    S_var_init_decl(161),          /* var_init_decl  */
    S_identifier_list(162),        /* identifier_list  */
    S_var_spec_init(163),          /* var_spec_init  */
    S_custom_spec_init(164),       /* custom_spec_init  */
    S_custom_specification(165),   /* custom_specification  */
    S_initialized_custom(166),     /* initialized_custom  */
    S_initialized_custom_with_constant(167), /* initialized_custom_with_constant  */
    S_initialized_custom_with_identifier(168), /* initialized_custom_with_identifier  */
    S_initialized_custom_with_structure(169), /* initialized_custom_with_structure  */
    S_boolean_spec_init(170),      /* boolean_spec_init  */
    S_boolean_specification(171),  /* boolean_specification  */
    S_initialized_boolean(172),    /* initialized_boolean  */
    S_edge(173),                   /* edge  */
    S_simple_spec_init(174),       /* simple_spec_init  */
    S_simple_specification(175),   /* simple_specification  */
    S_initialized_simple(176),     /* initialized_simple  */
    S_elementary_type(177),        /* elementary_type  */
    S_numeric_type(178),           /* numeric_type  */
    S_integer_type(179),           /* integer_type  */
    S_signed_integer_type(180),    /* signed_integer_type  */
    S_unsigned_integer_type(181),  /* unsigned_integer_type  */
    S_real_type(182),              /* real_type  */
    S_date_type(183),              /* date_type  */
    S_constant(184),               /* constant  */
    S_string_constant(185),        /* string_constant  */
    S_boolean_constant(186),       /* boolean_constant  */
    S_numeric_constant(187),       /* numeric_constant  */
    S_prefix_type(188),            /* prefix_type  */
    S_time_constant(189),          /* time_constant  */
    S_bit_string_type(190),        /* bit_string_type  */
    S_subrange_spec_init(191),     /* subrange_spec_init  */
    S_subrange_specification(192), /* subrange_specification  */
    S_initialized_subrange(193),   /* initialized_subrange  */
    S_range(194),                  /* range  */
    S_enumerated_spec_init(195),   /* enumerated_spec_init  */
    S_enumerated_specification(196), /* enumerated_specification  */
    S_initialized_enumerated(197), /* initialized_enumerated  */
    S_array_spec_init(198),        /* array_spec_init  */
    S_array_specification(199),    /* array_specification  */
    S_initialized_array(200),      /* initialized_array  */
    S_range_list(201),             /* range_list  */
    S_non_generic_type(202),       /* non_generic_type  */
    S_array_initialization(203),   /* array_initialization  */
    S_array_initial_elements_list(204), /* array_initial_elements_list  */
    S_array_initial_elements(205), /* array_initial_elements  */
    S_array_initial_element(206),  /* array_initial_element  */
    S_repeated_initial_element(207), /* repeated_initial_element  */
    S_structure_initialization(208), /* structure_initialization  */
    S_structure_field_init_list(209), /* structure_field_init_list  */
    S_initialized_struct_field(210), /* initialized_struct_field  */
    S_initialized_field_with_constant(211), /* initialized_field_with_constant  */
    S_initialized_field_with_identifier(212), /* initialized_field_with_identifier  */
    S_initialized_field_with_array(213), /* initialized_field_with_array  */
    S_initialized_field_with_structure(214), /* initialized_field_with_structure  */
    S_referenced_identifier(215),  /* referenced_identifier  */
    S_standard_function_block_spec_init(216), /* standard_function_block_spec_init  */
    S_initialized_standard_function_block(217), /* initialized_standard_function_block  */
    S_standard_function_block_specification(218), /* standard_function_block_specification  */
    S_string_spec_init(219),       /* string_spec_init  */
    S_string_specification(220),   /* string_specification  */
    S_initialized_string(221),     /* initialized_string  */
    S_string_type(222),            /* string_type  */
    S_opt_data_type_declaration(223), /* opt_data_type_declaration  */
    S_data_type_declaration(224),  /* data_type_declaration  */
    S_type_declaration_list(225),  /* type_declaration_list  */
    S_type_declaration(226),       /* type_declaration  */
    S_type_name_declaration(227),  /* type_name_declaration  */
    S_type_spec_init(228),         /* type_spec_init  */
    S_structure_specification(229), /* structure_specification  */
    S_structure_field_init_decl_list(230), /* structure_field_init_decl_list  */
    S_structure_field_init_decl(231), /* structure_field_init_decl  */
    S_field_name(232),             /* field_name  */
    S_structure_field_spec_init(233); /* structure_field_spec_init  */


    private final int yycode_;

    SymbolKind (int n) {
      this.yycode_ = n;
    }

    private static final SymbolKind[] values_ = {
      SymbolKind.S_YYEOF,
      SymbolKind.S_YYerror,
      SymbolKind.S_YYUNDEF,
      SymbolKind.S_TYPE,
      SymbolKind.S_END_TYPE,
      SymbolKind.S_STRUCT,
      SymbolKind.S_END_STRUCT,
      SymbolKind.S_FUNCTION_BLOCK,
      SymbolKind.S_END_FUNCTION_BLOCK,
      SymbolKind.S_FUZZIFY,
      SymbolKind.S_END_FUZZIFY,
      SymbolKind.S_DEFUZZIFY,
      SymbolKind.S_END_DEFUZZIFY,
      SymbolKind.S_RULEBLOCK,
      SymbolKind.S_END_RULEBLOCK,
      SymbolKind.S_OPTION,
      SymbolKind.S_END_OPTION,
      SymbolKind.S_TERM,
      SymbolKind.S_METHOD,
      SymbolKind.S_DEFAULT,
      SymbolKind.S_RANGE,
      SymbolKind.S_RULE,
      SymbolKind.S_IF,
      SymbolKind.S_THEN,
      SymbolKind.S_WITH,
      SymbolKind.S_ACT,
      SymbolKind.S_ACCU,
      SymbolKind.S_IS,
      SymbolKind.S_NOT,
      SymbolKind.S_AND,
      SymbolKind.S_OR,
      SymbolKind.S_COG,
      SymbolKind.S_COGS,
      SymbolKind.S_COA,
      SymbolKind.S_LM,
      SymbolKind.S_RM,
      SymbolKind.S_NC,
      SymbolKind.S_MIN,
      SymbolKind.S_MAX,
      SymbolKind.S_ASUM,
      SymbolKind.S_BSUM,
      SymbolKind.S_PROD,
      SymbolKind.S_BDIF,
      SymbolKind.S_NSUM,
      SymbolKind.S_IDENTIFIER,
      SymbolKind.S_STD_FB_IDENTIFIER,
      SymbolKind.S_ASSIGN_OP,
      SymbolKind.S_RANGE_OP,
      SymbolKind.S_PRAGMA,
      SymbolKind.S_VAR_INPUT,
      SymbolKind.S_RETAIN,
      SymbolKind.S_NON_RETAIN,
      SymbolKind.S_END_VAR,
      SymbolKind.S_BOOL,
      SymbolKind.S_R_EDGE,
      SymbolKind.S_F_EDGE,
      SymbolKind.S_VAR_OUTPUT,
      SymbolKind.S_VAR,
      SymbolKind.S_CONSTANT,
      SymbolKind.S_STRING,
      SymbolKind.S_WSTRING,
      SymbolKind.S_BYTE,
      SymbolKind.S_WORD,
      SymbolKind.S_DWORD,
      SymbolKind.S_LWORD,
      SymbolKind.S_TIME,
      SymbolKind.S_TIME_OF_DAY,
      SymbolKind.S_DATE,
      SymbolKind.S_DATE_AND_TIME,
      SymbolKind.S_OF,
      SymbolKind.S_SINT,
      SymbolKind.S_INT,
      SymbolKind.S_DINT,
      SymbolKind.S_LINT,
      SymbolKind.S_USINT,
      SymbolKind.S_UINT,
      SymbolKind.S_UDINT,
      SymbolKind.S_ULINT,
      SymbolKind.S_REAL,
      SymbolKind.S_LREAL,
      SymbolKind.S_NUMERIC_LITERAL,
      SymbolKind.S_STRING_LITERAL,
      SymbolKind.S_TIME_LITERAL,
      SymbolKind.S_BOOLEAN_LITERAL,
      SymbolKind.S_ARRAY,
      SymbolKind.S_85_,
      SymbolKind.S_86_,
      SymbolKind.S_87_,
      SymbolKind.S_88_,
      SymbolKind.S_89_,
      SymbolKind.S_90_,
      SymbolKind.S_91_,
      SymbolKind.S_92_,
      SymbolKind.S_YYACCEPT,
      SymbolKind.S_program,
      SymbolKind.S_function_block_declaration,
      SymbolKind.S_function_block_name,
      SymbolKind.S_opt_fb_io_var_declarations_list,
      SymbolKind.S_fb_io_var_declarations,
      SymbolKind.S_io_var_decl,
      SymbolKind.S_var_retain_spec,
      SymbolKind.S_opt_other_var_declarations_list,
      SymbolKind.S_other_var_declarations,
      SymbolKind.S_opt_function_block_body,
      SymbolKind.S_opt_fuzzify_block_list,
      SymbolKind.S_fuzzify_block,
      SymbolKind.S_106_1,
      SymbolKind.S_fuzzify_block_name,
      SymbolKind.S_opt_defuzzify_block_list,
      SymbolKind.S_defuzzify_block,
      SymbolKind.S_defuzzify_block_name,
      SymbolKind.S_opt_linguistic_term_list,
      SymbolKind.S_linguistic_term_list,
      SymbolKind.S_linguistic_term,
      SymbolKind.S_linguistic_term_value,
      SymbolKind.S_membership_function,
      SymbolKind.S_singleton,
      SymbolKind.S_point_list,
      SymbolKind.S_point,
      SymbolKind.S_defuzzification_method,
      SymbolKind.S_defuzz_method,
      SymbolKind.S_default_value,
      SymbolKind.S_default_val,
      SymbolKind.S_opt_range,
      SymbolKind.S_opt_rule_block_list,
      SymbolKind.S_rule_block,
      SymbolKind.S_rule_block_name,
      SymbolKind.S_operator_definition,
      SymbolKind.S_opt_operator_or,
      SymbolKind.S_opt_operator_and,
      SymbolKind.S_or_type,
      SymbolKind.S_and_type,
      SymbolKind.S_opt_activation_method,
      SymbolKind.S_activation_method,
      SymbolKind.S_act_type,
      SymbolKind.S_accumulation_method,
      SymbolKind.S_accu_type,
      SymbolKind.S_rules_decl,
      SymbolKind.S_rule_list,
      SymbolKind.S_rule,
      SymbolKind.S_opt_weighting,
      SymbolKind.S_condition,
      SymbolKind.S_condition_head,
      SymbolKind.S_condition_tail,
      SymbolKind.S_or_operand,
      SymbolKind.S_and_condition_tail,
      SymbolKind.S_and_operand,
      SymbolKind.S_condition_operand,
      SymbolKind.S_subcondition,
      SymbolKind.S_identifier_operand,
      SymbolKind.S_conclusions,
      SymbolKind.S_conclusion_list,
      SymbolKind.S_conclusion,
      SymbolKind.S_opt_option_block_list,
      SymbolKind.S_option_block,
      SymbolKind.S_pragma_list,
      SymbolKind.S_pragma,
      SymbolKind.S_var_declarations,
      SymbolKind.S_var_id_decl,
      SymbolKind.S_var_constant_spec,
      SymbolKind.S_var_init_decl_list,
      SymbolKind.S_var_init_decl,
      SymbolKind.S_identifier_list,
      SymbolKind.S_var_spec_init,
      SymbolKind.S_custom_spec_init,
      SymbolKind.S_custom_specification,
      SymbolKind.S_initialized_custom,
      SymbolKind.S_initialized_custom_with_constant,
      SymbolKind.S_initialized_custom_with_identifier,
      SymbolKind.S_initialized_custom_with_structure,
      SymbolKind.S_boolean_spec_init,
      SymbolKind.S_boolean_specification,
      SymbolKind.S_initialized_boolean,
      SymbolKind.S_edge,
      SymbolKind.S_simple_spec_init,
      SymbolKind.S_simple_specification,
      SymbolKind.S_initialized_simple,
      SymbolKind.S_elementary_type,
      SymbolKind.S_numeric_type,
      SymbolKind.S_integer_type,
      SymbolKind.S_signed_integer_type,
      SymbolKind.S_unsigned_integer_type,
      SymbolKind.S_real_type,
      SymbolKind.S_date_type,
      SymbolKind.S_constant,
      SymbolKind.S_string_constant,
      SymbolKind.S_boolean_constant,
      SymbolKind.S_numeric_constant,
      SymbolKind.S_prefix_type,
      SymbolKind.S_time_constant,
      SymbolKind.S_bit_string_type,
      SymbolKind.S_subrange_spec_init,
      SymbolKind.S_subrange_specification,
      SymbolKind.S_initialized_subrange,
      SymbolKind.S_range,
      SymbolKind.S_enumerated_spec_init,
      SymbolKind.S_enumerated_specification,
      SymbolKind.S_initialized_enumerated,
      SymbolKind.S_array_spec_init,
      SymbolKind.S_array_specification,
      SymbolKind.S_initialized_array,
      SymbolKind.S_range_list,
      SymbolKind.S_non_generic_type,
      SymbolKind.S_array_initialization,
      SymbolKind.S_array_initial_elements_list,
      SymbolKind.S_array_initial_elements,
      SymbolKind.S_array_initial_element,
      SymbolKind.S_repeated_initial_element,
      SymbolKind.S_structure_initialization,
      SymbolKind.S_structure_field_init_list,
      SymbolKind.S_initialized_struct_field,
      SymbolKind.S_initialized_field_with_constant,
      SymbolKind.S_initialized_field_with_identifier,
      SymbolKind.S_initialized_field_with_array,
      SymbolKind.S_initialized_field_with_structure,
      SymbolKind.S_referenced_identifier,
      SymbolKind.S_standard_function_block_spec_init,
      SymbolKind.S_initialized_standard_function_block,
      SymbolKind.S_standard_function_block_specification,
      SymbolKind.S_string_spec_init,
      SymbolKind.S_string_specification,
      SymbolKind.S_initialized_string,
      SymbolKind.S_string_type,
      SymbolKind.S_opt_data_type_declaration,
      SymbolKind.S_data_type_declaration,
      SymbolKind.S_type_declaration_list,
      SymbolKind.S_type_declaration,
      SymbolKind.S_type_name_declaration,
      SymbolKind.S_type_spec_init,
      SymbolKind.S_structure_specification,
      SymbolKind.S_structure_field_init_decl_list,
      SymbolKind.S_structure_field_init_decl,
      SymbolKind.S_field_name,
      SymbolKind.S_structure_field_spec_init
    };

    static final SymbolKind get(int code) {
      return values_[code];
    }

    public final int getCode() {
      return this.yycode_;
    }

    /* Return YYSTR after stripping away unnecessary quotes and
       backslashes, so that it's suitable for yyerror.  The heuristic is
       that double-quoting is unnecessary unless the string contains an
       apostrophe, a comma, or backslash (other than backslash-backslash).
       YYSTR is taken from yytname.  */
    private static String yytnamerr_(String yystr)
    {
      if (yystr.charAt (0) == '"')
        {
          StringBuffer yyr = new StringBuffer();
          strip_quotes: for (int i = 1; i < yystr.length(); i++)
            switch (yystr.charAt(i))
              {
              case '\'':
              case ',':
                break strip_quotes;

              case '\\':
                if (yystr.charAt(++i) != '\\')
                  break strip_quotes;
                /* Fall through.  */
              default:
                yyr.append(yystr.charAt(i));
                break;

              case '"':
                return yyr.toString();
              }
        }
      return yystr;
    }

    /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
       First, the terminals, then, starting at \a YYNTOKENS_, nonterminals.  */
    private static final String[] yytname_ = yytname_init();
  private static final String[] yytname_init()
  {
    return new String[]
    {
  "\"end of file\"", "error", "\"invalid token\"", "TYPE", "END_TYPE",
  "STRUCT", "END_STRUCT", "FUNCTION_BLOCK", "END_FUNCTION_BLOCK",
  "FUZZIFY", "END_FUZZIFY", "DEFUZZIFY", "END_DEFUZZIFY", "RULEBLOCK",
  "END_RULEBLOCK", "OPTION", "END_OPTION", "TERM", "METHOD", "DEFAULT",
  "RANGE", "RULE", "IF", "THEN", "WITH", "ACT", "ACCU", "IS", "NOT", "AND",
  "OR", "COG", "COGS", "COA", "LM", "RM", "NC", "MIN", "MAX", "ASUM",
  "BSUM", "PROD", "BDIF", "NSUM", "IDENTIFIER", "STD_FB_IDENTIFIER",
  "ASSIGN_OP", "RANGE_OP", "PRAGMA", "VAR_INPUT", "RETAIN", "NON_RETAIN",
  "END_VAR", "BOOL", "R_EDGE", "F_EDGE", "VAR_OUTPUT", "VAR", "CONSTANT",
  "STRING", "WSTRING", "BYTE", "WORD", "DWORD", "LWORD", "TIME",
  "TIME_OF_DAY", "DATE", "DATE_AND_TIME", "OF", "SINT", "INT", "DINT",
  "LINT", "USINT", "UINT", "UDINT", "ULINT", "REAL", "LREAL",
  "NUMERIC_LITERAL", "STRING_LITERAL", "TIME_LITERAL", "BOOLEAN_LITERAL",
  "ARRAY", "';'", "'('", "','", "')'", "':'", "'#'", "'['", "']'",
  "$accept", "program", "function_block_declaration",
  "function_block_name", "opt_fb_io_var_declarations_list",
  "fb_io_var_declarations", "io_var_decl", "var_retain_spec",
  "opt_other_var_declarations_list", "other_var_declarations",
  "opt_function_block_body", "opt_fuzzify_block_list", "fuzzify_block",
  "$@1", "fuzzify_block_name", "opt_defuzzify_block_list",
  "defuzzify_block", "defuzzify_block_name", "opt_linguistic_term_list",
  "linguistic_term_list", "linguistic_term", "linguistic_term_value",
  "membership_function", "singleton", "point_list", "point",
  "defuzzification_method", "defuzz_method", "default_value",
  "default_val", "opt_range", "opt_rule_block_list", "rule_block",
  "rule_block_name", "operator_definition", "opt_operator_or",
  "opt_operator_and", "or_type", "and_type", "opt_activation_method",
  "activation_method", "act_type", "accumulation_method", "accu_type",
  "rules_decl", "rule_list", "rule", "opt_weighting", "condition",
  "condition_head", "condition_tail", "or_operand", "and_condition_tail",
  "and_operand", "condition_operand", "subcondition", "identifier_operand",
  "conclusions", "conclusion_list", "conclusion", "opt_option_block_list",
  "option_block", "pragma_list", "pragma", "var_declarations",
  "var_id_decl", "var_constant_spec", "var_init_decl_list",
  "var_init_decl", "identifier_list", "var_spec_init", "custom_spec_init",
  "custom_specification", "initialized_custom",
  "initialized_custom_with_constant", "initialized_custom_with_identifier",
  "initialized_custom_with_structure", "boolean_spec_init",
  "boolean_specification", "initialized_boolean", "edge",
  "simple_spec_init", "simple_specification", "initialized_simple",
  "elementary_type", "numeric_type", "integer_type", "signed_integer_type",
  "unsigned_integer_type", "real_type", "date_type", "constant",
  "string_constant", "boolean_constant", "numeric_constant", "prefix_type",
  "time_constant", "bit_string_type", "subrange_spec_init",
  "subrange_specification", "initialized_subrange", "range",
  "enumerated_spec_init", "enumerated_specification",
  "initialized_enumerated", "array_spec_init", "array_specification",
  "initialized_array", "range_list", "non_generic_type",
  "array_initialization", "array_initial_elements_list",
  "array_initial_elements", "array_initial_element",
  "repeated_initial_element", "structure_initialization",
  "structure_field_init_list", "initialized_struct_field",
  "initialized_field_with_constant", "initialized_field_with_identifier",
  "initialized_field_with_array", "initialized_field_with_structure",
  "referenced_identifier", "standard_function_block_spec_init",
  "initialized_standard_function_block",
  "standard_function_block_specification", "string_spec_init",
  "string_specification", "initialized_string", "string_type",
  "opt_data_type_declaration", "data_type_declaration",
  "type_declaration_list", "type_declaration", "type_name_declaration",
  "type_spec_init", "structure_specification",
  "structure_field_init_decl_list", "structure_field_init_decl",
  "field_name", "structure_field_spec_init", null
    };
  }

    /* The user-facing name of this symbol.  */
    public final String getName() {
      return yytnamerr_(yytname_[yycode_]);
    }

  };


  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>Parser</tt>.
   */
  public interface Lexer {
    /* Token kinds.  */
    /** Token "end of file", to be returned by the scanner.  */
    static final int YYEOF = 0;
    /** Token error, to be returned by the scanner.  */
    static final int YYerror = 256;
    /** Token "invalid token", to be returned by the scanner.  */
    static final int YYUNDEF = 257;
    /** Token TYPE, to be returned by the scanner.  */
    static final int TYPE = 258;
    /** Token END_TYPE, to be returned by the scanner.  */
    static final int END_TYPE = 259;
    /** Token STRUCT, to be returned by the scanner.  */
    static final int STRUCT = 260;
    /** Token END_STRUCT, to be returned by the scanner.  */
    static final int END_STRUCT = 261;
    /** Token FUNCTION_BLOCK, to be returned by the scanner.  */
    static final int FUNCTION_BLOCK = 262;
    /** Token END_FUNCTION_BLOCK, to be returned by the scanner.  */
    static final int END_FUNCTION_BLOCK = 263;
    /** Token FUZZIFY, to be returned by the scanner.  */
    static final int FUZZIFY = 264;
    /** Token END_FUZZIFY, to be returned by the scanner.  */
    static final int END_FUZZIFY = 265;
    /** Token DEFUZZIFY, to be returned by the scanner.  */
    static final int DEFUZZIFY = 266;
    /** Token END_DEFUZZIFY, to be returned by the scanner.  */
    static final int END_DEFUZZIFY = 267;
    /** Token RULEBLOCK, to be returned by the scanner.  */
    static final int RULEBLOCK = 268;
    /** Token END_RULEBLOCK, to be returned by the scanner.  */
    static final int END_RULEBLOCK = 269;
    /** Token OPTION, to be returned by the scanner.  */
    static final int OPTION = 270;
    /** Token END_OPTION, to be returned by the scanner.  */
    static final int END_OPTION = 271;
    /** Token TERM, to be returned by the scanner.  */
    static final int TERM = 272;
    /** Token METHOD, to be returned by the scanner.  */
    static final int METHOD = 273;
    /** Token DEFAULT, to be returned by the scanner.  */
    static final int DEFAULT = 274;
    /** Token RANGE, to be returned by the scanner.  */
    static final int RANGE = 275;
    /** Token RULE, to be returned by the scanner.  */
    static final int RULE = 276;
    /** Token IF, to be returned by the scanner.  */
    static final int IF = 277;
    /** Token THEN, to be returned by the scanner.  */
    static final int THEN = 278;
    /** Token WITH, to be returned by the scanner.  */
    static final int WITH = 279;
    /** Token ACT, to be returned by the scanner.  */
    static final int ACT = 280;
    /** Token ACCU, to be returned by the scanner.  */
    static final int ACCU = 281;
    /** Token IS, to be returned by the scanner.  */
    static final int IS = 282;
    /** Token NOT, to be returned by the scanner.  */
    static final int NOT = 283;
    /** Token AND, to be returned by the scanner.  */
    static final int AND = 284;
    /** Token OR, to be returned by the scanner.  */
    static final int OR = 285;
    /** Token COG, to be returned by the scanner.  */
    static final int COG = 286;
    /** Token COGS, to be returned by the scanner.  */
    static final int COGS = 287;
    /** Token COA, to be returned by the scanner.  */
    static final int COA = 288;
    /** Token LM, to be returned by the scanner.  */
    static final int LM = 289;
    /** Token RM, to be returned by the scanner.  */
    static final int RM = 290;
    /** Token NC, to be returned by the scanner.  */
    static final int NC = 291;
    /** Token MIN, to be returned by the scanner.  */
    static final int MIN = 292;
    /** Token MAX, to be returned by the scanner.  */
    static final int MAX = 293;
    /** Token ASUM, to be returned by the scanner.  */
    static final int ASUM = 294;
    /** Token BSUM, to be returned by the scanner.  */
    static final int BSUM = 295;
    /** Token PROD, to be returned by the scanner.  */
    static final int PROD = 296;
    /** Token BDIF, to be returned by the scanner.  */
    static final int BDIF = 297;
    /** Token NSUM, to be returned by the scanner.  */
    static final int NSUM = 298;
    /** Token IDENTIFIER, to be returned by the scanner.  */
    static final int IDENTIFIER = 299;
    /** Token STD_FB_IDENTIFIER, to be returned by the scanner.  */
    static final int STD_FB_IDENTIFIER = 300;
    /** Token ASSIGN_OP, to be returned by the scanner.  */
    static final int ASSIGN_OP = 301;
    /** Token RANGE_OP, to be returned by the scanner.  */
    static final int RANGE_OP = 302;
    /** Token PRAGMA, to be returned by the scanner.  */
    static final int PRAGMA = 303;
    /** Token VAR_INPUT, to be returned by the scanner.  */
    static final int VAR_INPUT = 304;
    /** Token RETAIN, to be returned by the scanner.  */
    static final int RETAIN = 305;
    /** Token NON_RETAIN, to be returned by the scanner.  */
    static final int NON_RETAIN = 306;
    /** Token END_VAR, to be returned by the scanner.  */
    static final int END_VAR = 307;
    /** Token BOOL, to be returned by the scanner.  */
    static final int BOOL = 308;
    /** Token R_EDGE, to be returned by the scanner.  */
    static final int R_EDGE = 309;
    /** Token F_EDGE, to be returned by the scanner.  */
    static final int F_EDGE = 310;
    /** Token VAR_OUTPUT, to be returned by the scanner.  */
    static final int VAR_OUTPUT = 311;
    /** Token VAR, to be returned by the scanner.  */
    static final int VAR = 312;
    /** Token CONSTANT, to be returned by the scanner.  */
    static final int CONSTANT = 313;
    /** Token STRING, to be returned by the scanner.  */
    static final int STRING = 314;
    /** Token WSTRING, to be returned by the scanner.  */
    static final int WSTRING = 315;
    /** Token BYTE, to be returned by the scanner.  */
    static final int BYTE = 316;
    /** Token WORD, to be returned by the scanner.  */
    static final int WORD = 317;
    /** Token DWORD, to be returned by the scanner.  */
    static final int DWORD = 318;
    /** Token LWORD, to be returned by the scanner.  */
    static final int LWORD = 319;
    /** Token TIME, to be returned by the scanner.  */
    static final int TIME = 320;
    /** Token TIME_OF_DAY, to be returned by the scanner.  */
    static final int TIME_OF_DAY = 321;
    /** Token DATE, to be returned by the scanner.  */
    static final int DATE = 322;
    /** Token DATE_AND_TIME, to be returned by the scanner.  */
    static final int DATE_AND_TIME = 323;
    /** Token OF, to be returned by the scanner.  */
    static final int OF = 324;
    /** Token SINT, to be returned by the scanner.  */
    static final int SINT = 325;
    /** Token INT, to be returned by the scanner.  */
    static final int INT = 326;
    /** Token DINT, to be returned by the scanner.  */
    static final int DINT = 327;
    /** Token LINT, to be returned by the scanner.  */
    static final int LINT = 328;
    /** Token USINT, to be returned by the scanner.  */
    static final int USINT = 329;
    /** Token UINT, to be returned by the scanner.  */
    static final int UINT = 330;
    /** Token UDINT, to be returned by the scanner.  */
    static final int UDINT = 331;
    /** Token ULINT, to be returned by the scanner.  */
    static final int ULINT = 332;
    /** Token REAL, to be returned by the scanner.  */
    static final int REAL = 333;
    /** Token LREAL, to be returned by the scanner.  */
    static final int LREAL = 334;
    /** Token NUMERIC_LITERAL, to be returned by the scanner.  */
    static final int NUMERIC_LITERAL = 335;
    /** Token STRING_LITERAL, to be returned by the scanner.  */
    static final int STRING_LITERAL = 336;
    /** Token TIME_LITERAL, to be returned by the scanner.  */
    static final int TIME_LITERAL = 337;
    /** Token BOOLEAN_LITERAL, to be returned by the scanner.  */
    static final int BOOLEAN_LITERAL = 338;
    /** Token ARRAY, to be returned by the scanner.  */
    static final int ARRAY = 339;

    /** Deprecated, use YYEOF instead.  */
    public static final int EOF = YYEOF;


    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.
     */
    Object getLVal();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token.
     */
    int yylex() throws java.io.IOException;

    /**
     * Emit an errorin a user-defined way.
     *
     *
     * @param msg The string for the error message.
     */
     void yyerror(String msg);


  }


  /**
   * The object doing lexical analysis for us.
   */
  private Lexer yylexer;


    /* User arguments.  */
    protected final SymbolTable symbolTable;



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public Parser(Lexer yylexer, SymbolTable symbolTable)
  {

    this.yylexer = yylexer;
this.symbolTable = symbolTable;
          
  }



  private int yynerrs = 0;

  /**
   * The number of syntax errors so far.
   */
  public final int getNumberOfErrors() { return yynerrs; }

  /**
   * Print an error message via the lexer.
   *
   * @param msg The error message.
   */
  public final void yyerror(String msg) {
      yylexer.yyerror(msg);
  }



  private final class YYStack {
    private int[] stateStack = new int[16];
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push(int state, Object value) {
      height++;
      if (size == height) {
        int[] newStateStack = new int[size * 2];
        System.arraycopy(stateStack, 0, newStateStack, 0, height);
        stateStack = newStateStack;

        Object[] newValueStack = new Object[size * 2];
        System.arraycopy(valueStack, 0, newValueStack, 0, height);
        valueStack = newValueStack;

        size *= 2;
      }

      stateStack[height] = state;
      valueStack[height] = value;
    }

    public final void pop() {
      pop(1);
    }

    public final void pop(int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (0 < num) {
        java.util.Arrays.fill(valueStack, height - num + 1, height + 1, null);
      }
      height -= num;
    }

    public final int stateAt(int i) {
      return stateStack[height - i];
    }

    public final Object valueAt(int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print(java.io.PrintStream out) {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++) {
        out.print(' ');
        out.print(stateStack[i]);
      }
      out.println();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).
   */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).
   */
  public static final int YYABORT = 1;



  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.
   */
  public static final int YYERROR = 2;

  /**
   * Internal return codes that are not supported for user semantic
   * actions.
   */
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;


  private int yyerrstatus_ = 0;


  /**
   * Whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.
   */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  /** Compute post-reduction state.
   * @param yystate   the current state
   * @param yysym     the nonterminal to push on the stack
   */
  private int yyLRGotoState(int yystate, int yysym) {
    int yyr = yypgoto_[yysym - YYNTOKENS_] + yystate;
    if (0 <= yyr && yyr <= YYLAST_ && yycheck_[yyr] == yystate)
      return yytable_[yyr];
    else
      return yydefgoto_[yysym - YYNTOKENS_];
  }

  private int yyaction(int yyn, YYStack yystack, int yylen)
  {
    /* If YYLEN is nonzero, implement the default value of the action:
       '$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    Object yyval = (0 < yylen) ? yystack.valueAt(yylen - 1) : yystack.valueAt(0);

    switch (yyn)
      {
          case 4: /* function_block_name: IDENTIFIER  */
  if (yyn == 4)
    /* "src/main/java/parser/Parser.y":322  */
    {
        this.programName = ((String)(yystack.valueAt (0)));
    };
  break;


  case 7: /* fb_io_var_declarations: io_var_decl var_retain_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 7)
    /* "src/main/java/parser/Parser.y":366  */
    {
        ((DeclarationBlock)(yystack.valueAt (2))).source(((Source)(yystack.valueAt (4)))).use(Use.VARIABLE).setDeclarationScope(programName);
        ((DeclarationBlock)(yystack.valueAt (2))).publish();
    };
  break;


  case 8: /* io_var_decl: VAR_INPUT  */
  if (yyn == 8)
    /* "src/main/java/parser/Parser.y":374  */
    {
        yyval = Source.IN;
    };
  break;


  case 9: /* io_var_decl: VAR_OUTPUT  */
  if (yyn == 9)
    /* "src/main/java/parser/Parser.y":378  */
    {
        yyval = Source.OUT;
    };
  break;


  case 11: /* var_retain_spec: RETAIN  */
  if (yyn == 11)
    /* "src/main/java/parser/Parser.y":386  */
    {
        /**
         * Marks the variable declaration as RETAIN (persistent across power cycles).
         * TODO: Implement retain specification in context metadata.
        **/
    };
  break;


  case 12: /* var_retain_spec: NON_RETAIN  */
  if (yyn == 12)
    /* "src/main/java/parser/Parser.y":393  */
    {
        /**
         * Marks the variable declaration as NON_RETAIN (not persistent).
         * TODO: Implement non-retain specification in context metadata.
        **/
    };
  break;


  case 16: /* opt_function_block_body: opt_fuzzify_block_list opt_defuzzify_block_list opt_rule_block_list opt_option_block_list  */
  if (yyn == 16)
    /* "src/main/java/parser/Parser.y":415  */
    {
        this.ast = new AbstractSyntaxTree(((List<RuleBlockNode>)(yystack.valueAt (1))));
    };
  break;


  case 19: /* $@1: %empty  */
  if (yyn == 19)
    /* "src/main/java/parser/Parser.y":482  */
    {
        ComponentDeclaration fuzzifierDeclaration = new ComponentDeclaration(this.symbolTable, ((TermBlock)(yystack.valueAt (0))));
        Director.makeWithMetadata(fuzzifierDeclaration, new FuzzifierMetadata(((TermBlock)(yystack.valueAt (0)))));
        fuzzifierDeclaration.identifier(((String)(yystack.valueAt (1)))).publish();
    };
  break;


  case 21: /* fuzzify_block_name: IDENTIFIER  */
  if (yyn == 21)
    /* "src/main/java/parser/Parser.y":492  */
    {
        yyval = programName + '#' + ((String)(yystack.valueAt (0)));
    };
  break;


  case 24: /* defuzzify_block: DEFUZZIFY defuzzify_block_name opt_range opt_linguistic_term_list defuzzification_method default_value END_DEFUZZIFY  */
  if (yyn == 24)
    /* "src/main/java/parser/Parser.y":509  */
    {
         ComponentDeclaration defuzzifierDeclaration = new ComponentDeclaration(this.symbolTable, ((TermBlock)(yystack.valueAt (3))));
         Director.makeWithMetadata(defuzzifierDeclaration, new DefuzzifierMetadata(((List<String>)(yystack.valueAt (4))), ((TermBlock)(yystack.valueAt (3))), ((Subtype)(yystack.valueAt (2))), ((VariableInitialization)(yystack.valueAt (1)))));
         defuzzifierDeclaration.identifier(((String)(yystack.valueAt (5)))).publish();
    };
  break;


  case 25: /* defuzzify_block_name: IDENTIFIER  */
  if (yyn == 25)
    /* "src/main/java/parser/Parser.y":550  */
    {
        yyval = programName + '#' + ((String)(yystack.valueAt (0)));
    };
  break;


  case 26: /* opt_linguistic_term_list: %empty  */
  if (yyn == 26)
    /* "src/main/java/parser/Parser.y":557  */
    {
        yyval = null;
    };
  break;


  case 27: /* opt_linguistic_term_list: linguistic_term_list  */
  if (yyn == 27)
    /* "src/main/java/parser/Parser.y":561  */
    {
        yyval = ((TermBlock)(yystack.valueAt (0)));
    };
  break;


  case 28: /* linguistic_term_list: linguistic_term  */
  if (yyn == 28)
    /* "src/main/java/parser/Parser.y":568  */
    {
        TermBlock block = new TermBlock();
        block.addTerm(((SingleDeclaration)(yystack.valueAt (0))));
        yyval = block;
    };
  break;


  case 29: /* linguistic_term_list: linguistic_term_list linguistic_term  */
  if (yyn == 29)
    /* "src/main/java/parser/Parser.y":574  */
    {
        ((TermBlock)(yystack.valueAt (1))).addTerm(((SingleDeclaration)(yystack.valueAt (0))));
        yyval = ((TermBlock)(yystack.valueAt (1)));
    };
  break;


  case 30: /* linguistic_term: TERM IDENTIFIER ASSIGN_OP linguistic_term_value  */
  if (yyn == 30)
    /* "src/main/java/parser/Parser.y":607  */
    {
        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))).identifier(((String)(yystack.valueAt (2))));
    };
  break;


  case 31: /* linguistic_term_value: IDENTIFIER ';'  */
  if (yyn == 31)
    /* "src/main/java/parser/Parser.y":622  */
    {
        LexemeInfoBuilder customTermBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(customTermBuilder, new CustomMetadata(this.symbolTable, ((String)(yystack.valueAt (1)))));
        yyval = customTermBuilder;
    };
  break;


  case 32: /* linguistic_term_value: membership_function ';'  */
  if (yyn == 32)
    /* "src/main/java/parser/Parser.y":628  */
    {
        LexemeInfoBuilder termBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(termBuilder, new TermMetadata(((List<String>)(yystack.valueAt (1)))));
        yyval = termBuilder;
    };
  break;


  case 33: /* membership_function: singleton  */
  if (yyn == 33)
    /* "src/main/java/parser/Parser.y":651  */
    {
        yyval = ((List<String>)(yystack.valueAt (0)));
    };
  break;


  case 34: /* membership_function: point_list  */
  if (yyn == 34)
    /* "src/main/java/parser/Parser.y":655  */
    {
        yyval = ((List<String>)(yystack.valueAt (0)));
    };
  break;


  case 35: /* singleton: numeric_constant  */
  if (yyn == 35)
    /* "src/main/java/parser/Parser.y":662  */
    {
        List<String> point = new ArrayList<>();
        point.add(((String)(yystack.valueAt (0))));
        // TODO: Make 1 constant
        point.add("1");
        yyval = point;
    };
  break;


  case 36: /* point_list: point  */
  if (yyn == 36)
    /* "src/main/java/parser/Parser.y":673  */
    {
        yyval = ((List<String>)(yystack.valueAt (0)));
    };
  break;


  case 37: /* point_list: point_list point  */
  if (yyn == 37)
    /* "src/main/java/parser/Parser.y":677  */
    {
        ((List<String>)(yystack.valueAt (1))).addAll(((List<String>)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (1)));
    };
  break;


  case 38: /* point: '(' numeric_constant ',' numeric_constant ')'  */
  if (yyn == 38)
    /* "src/main/java/parser/Parser.y":685  */
    {
        List<String> point = new ArrayList<>();
        point.add(((String)(yystack.valueAt (3))));
        point.add(((String)(yystack.valueAt (1))));
        yyval = point;
    };
  break;


  case 39: /* point: '(' IDENTIFIER ',' numeric_constant ')'  */
  if (yyn == 39)
    /* "src/main/java/parser/Parser.y":692  */
    {
        List<String> point = new ArrayList<>();
        point.add(((String)(yystack.valueAt (3))));
        point.add(((String)(yystack.valueAt (1))));
        yyval = point;
    };
  break;


  case 40: /* defuzzification_method: METHOD ':' defuzz_method ';'  */
  if (yyn == 40)
    /* "src/main/java/parser/Parser.y":702  */
    {
        yyval = ((Subtype)(yystack.valueAt (1)));
    };
  break;


  case 41: /* defuzz_method: COG  */
  if (yyn == 41)
    /* "src/main/java/parser/Parser.y":709  */
    {
        yyval = Subtype.COG;
    };
  break;


  case 42: /* defuzz_method: COGS  */
  if (yyn == 42)
    /* "src/main/java/parser/Parser.y":713  */
    {
        yyval = Subtype.COGS;
    };
  break;


  case 43: /* defuzz_method: COA  */
  if (yyn == 43)
    /* "src/main/java/parser/Parser.y":717  */
    {
        yyval = Subtype.COA;
    };
  break;


  case 44: /* defuzz_method: LM  */
  if (yyn == 44)
    /* "src/main/java/parser/Parser.y":721  */
    {
        yyval = Subtype.LM;
    };
  break;


  case 45: /* defuzz_method: RM  */
  if (yyn == 45)
    /* "src/main/java/parser/Parser.y":725  */
    {
        yyval = Subtype.RM;
    };
  break;


  case 46: /* default_value: DEFAULT ASSIGN_OP default_val ';'  */
  if (yyn == 46)
    /* "src/main/java/parser/Parser.y":732  */
    {
        yyval = ((VariableInitialization)(yystack.valueAt (1)));
    };
  break;


  case 47: /* default_val: numeric_constant  */
  if (yyn == 47)
    /* "src/main/java/parser/Parser.y":739  */
    {
        yyval = new VariableInitialization(((String)(yystack.valueAt (0))));
    };
  break;


  case 48: /* default_val: NC  */
  if (yyn == 48)
    /* "src/main/java/parser/Parser.y":743  */
    {
        yyval = null;
    };
  break;


  case 49: /* opt_range: %empty  */
  if (yyn == 49)
    /* "src/main/java/parser/Parser.y":750  */
    {
        yyval = null;
    };
  break;


  case 50: /* opt_range: RANGE '(' numeric_constant RANGE_OP numeric_constant ')' ';'  */
  if (yyn == 50)
    /* "src/main/java/parser/Parser.y":754  */
    {
        List<String> range = new ArrayList<>();
        range.add(((String)(yystack.valueAt (4))));
        range.add(((String)(yystack.valueAt (2))));
        yyval = range;
    };
  break;


  case 51: /* opt_rule_block_list: %empty  */
  if (yyn == 51)
    /* "src/main/java/parser/Parser.y":765  */
    {
        yyval = new ArrayList<>();
    };
  break;


  case 52: /* opt_rule_block_list: opt_rule_block_list rule_block  */
  if (yyn == 52)
    /* "src/main/java/parser/Parser.y":769  */
    {
        ((List<RuleBlockNode>)(yystack.valueAt (1))).add(((RuleBlockNode)(yystack.valueAt (0))));
        yyval = ((List<RuleBlockNode>)(yystack.valueAt (1)));
    };
  break;


  case 53: /* rule_block: RULEBLOCK rule_block_name operator_definition opt_activation_method accumulation_method rules_decl END_RULEBLOCK  */
  if (yyn == 53)
    /* "src/main/java/parser/Parser.y":782  */
    {   
        List<Operator> configuration = ((List<Operator>)(yystack.valueAt (4)));
        configuration.add(((Operator)(yystack.valueAt (3))));
        configuration.add(((Operator)(yystack.valueAt (2))));
        
        List<TreeNode> componentChild = Collections.singletonList(((TreeNode)(yystack.valueAt (1))));
        TreeNode componentNode = new TreeNode(Label.COMPONENT, ((String)(yystack.valueAt (5))), componentChild);

        yyval = RuleblockASTBuilder.build(componentNode, configuration);
    };
  break;


  case 54: /* rule_block_name: IDENTIFIER  */
  if (yyn == 54)
    /* "src/main/java/parser/Parser.y":796  */
    {
        yyval = programName + '#' + ((String)(yystack.valueAt (0)));
    };
  break;


  case 55: /* operator_definition: opt_operator_or opt_operator_and ';'  */
  if (yyn == 55)
    /* "src/main/java/parser/Parser.y":803  */
    {
        List<Operator> operators = new ArrayList<>();
        operators.add(((Operator)(yystack.valueAt (2))));
        operators.add(((Operator)(yystack.valueAt (1))));
        yyval = operators;
    };
  break;


  case 56: /* opt_operator_or: %empty  */
  if (yyn == 56)
    /* "src/main/java/parser/Parser.y":813  */
    {
        yyval = OperatorFactory.orDefault();
    };
  break;


  case 57: /* opt_operator_or: OR ':' or_type  */
  if (yyn == 57)
    /* "src/main/java/parser/Parser.y":817  */
    {
        yyval = ((Operator)(yystack.valueAt (0)));
    };
  break;


  case 58: /* opt_operator_and: %empty  */
  if (yyn == 58)
    /* "src/main/java/parser/Parser.y":824  */
    {
        yyval = OperatorFactory.andDefault();
    };
  break;


  case 59: /* opt_operator_and: AND ':' and_type  */
  if (yyn == 59)
    /* "src/main/java/parser/Parser.y":828  */
    {
        yyval = ((Operator)(yystack.valueAt (0)));
    };
  break;


  case 60: /* or_type: MAX  */
  if (yyn == 60)
    /* "src/main/java/parser/Parser.y":835  */
    {
        yyval = Operator.MAX;
    };
  break;


  case 61: /* or_type: ASUM  */
  if (yyn == 61)
    /* "src/main/java/parser/Parser.y":839  */
    {
        yyval = Operator.ASUM;
    };
  break;


  case 62: /* or_type: BSUM  */
  if (yyn == 62)
    /* "src/main/java/parser/Parser.y":843  */
    {
        yyval = Operator.BSUM;
    };
  break;


  case 63: /* and_type: MIN  */
  if (yyn == 63)
    /* "src/main/java/parser/Parser.y":850  */
    {
        yyval = Operator.MIN;
    };
  break;


  case 64: /* and_type: PROD  */
  if (yyn == 64)
    /* "src/main/java/parser/Parser.y":854  */
    {
        yyval = Operator.PROD;
    };
  break;


  case 65: /* and_type: BDIF  */
  if (yyn == 65)
    /* "src/main/java/parser/Parser.y":858  */
    {
        yyval = Operator.BDIF;
    };
  break;


  case 66: /* opt_activation_method: %empty  */
  if (yyn == 66)
    /* "src/main/java/parser/Parser.y":865  */
    {
        yyval = OperatorFactory.actDefault();
    };
  break;


  case 67: /* opt_activation_method: activation_method  */
  if (yyn == 67)
    /* "src/main/java/parser/Parser.y":869  */
    {
        yyval = ((Operator)(yystack.valueAt (0)));
    };
  break;


  case 68: /* activation_method: ACT ':' act_type ';'  */
  if (yyn == 68)
    /* "src/main/java/parser/Parser.y":876  */
    {
        yyval = ((Operator)(yystack.valueAt (1)));
    };
  break;


  case 69: /* act_type: PROD  */
  if (yyn == 69)
    /* "src/main/java/parser/Parser.y":883  */
    {
        yyval = Operator.PROD;
    };
  break;


  case 70: /* act_type: MIN  */
  if (yyn == 70)
    /* "src/main/java/parser/Parser.y":887  */
    {
        yyval = Operator.MIN;
    };
  break;


  case 71: /* accumulation_method: ACCU ':' accu_type ';'  */
  if (yyn == 71)
    /* "src/main/java/parser/Parser.y":894  */
    { 
        yyval = ((Operator)(yystack.valueAt (1)));
    };
  break;


  case 72: /* accu_type: MAX  */
  if (yyn == 72)
    /* "src/main/java/parser/Parser.y":901  */
    { 
        yyval = Operator.MAX;  
    };
  break;


  case 73: /* accu_type: BSUM  */
  if (yyn == 73)
    /* "src/main/java/parser/Parser.y":905  */
    { 
        yyval = Operator.BSUM; 
    };
  break;


  case 74: /* accu_type: NSUM  */
  if (yyn == 74)
    /* "src/main/java/parser/Parser.y":909  */
    { 
        yyval = Operator.NSUM; 
    };
  break;


  case 75: /* rules_decl: rule_list  */
  if (yyn == 75)
    /* "src/main/java/parser/Parser.y":916  */
    { 
        yyval = new TreeNode(Label.ACCUMULATION, ((List<TreeNode>)(yystack.valueAt (0))));
    };
  break;


  case 76: /* rule_list: %empty  */
  if (yyn == 76)
    /* "src/main/java/parser/Parser.y":923  */
    { 
        yyval = Collections.emptyList();
    };
  break;


  case 77: /* rule_list: rule_list rule  */
  if (yyn == 77)
    /* "src/main/java/parser/Parser.y":927  */
    { 
        List<TreeNode> rules = ((List<TreeNode>)(yystack.valueAt (1)));
        if (rules == null) { 
            rules = new ArrayList<>();
        }
        rules.add(((TreeNode)(yystack.valueAt (0))));
    };
  break;


  case 78: /* rule: RULE numeric_constant ':' IF condition THEN conclusions opt_weighting ';'  */
  if (yyn == 78)
    /* "src/main/java/parser/Parser.y":938  */
    {
        List<TreeNode> condChild = Collections.singletonList(((TreeNode)(yystack.valueAt (4))));
        List<TreeNode> bodyChild = Collections.singletonList(((TreeNode)(yystack.valueAt (2))));
        TreeNode condNode = new TreeNode(Label.CONDITION, condChild);
        TreeNode bodyNode = new TreeNode(Label.BODY, bodyChild);

        List<TreeNode> ifChildren = new ArrayList<>();
        ifChildren.add(condNode);
        ifChildren.add(bodyNode);
        TreeNode ifNode = new TreeNode(Label.IF, ifChildren);

        if (((String)(yystack.valueAt (1))).isEmpty()) { 
            yyval = ifNode;
        } else { 
            List<TreeNode> withChild = Collections.singletonList(ifNode);
            yyval = new TreeNode(Label.WITH, ((String)(yystack.valueAt (1))), withChild);
        }
    };
  break;


  case 79: /* opt_weighting: %empty  */
  if (yyn == 79)
    /* "src/main/java/parser/Parser.y":960  */
    {
        yyval = "";
    };
  break;


  case 80: /* opt_weighting: WITH numeric_constant  */
  if (yyn == 80)
    /* "src/main/java/parser/Parser.y":964  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 81: /* opt_weighting: WITH IDENTIFIER  */
  if (yyn == 81)
    /* "src/main/java/parser/Parser.y":968  */
    {
        yyval = programName + "#" + ((String)(yystack.valueAt (0)));
    };
  break;


  case 82: /* condition: condition_head condition_tail  */
  if (yyn == 82)
    /* "src/main/java/parser/Parser.y":1002  */
    {
        ((ConditionParsingContext)(yystack.valueAt (0))).addOperand(((TreeNode)(yystack.valueAt (1))));
        yyval = ((ConditionParsingContext)(yystack.valueAt (0))).getConditionNode();
    };
  break;


  case 83: /* condition_head: condition_operand  */
  if (yyn == 83)
    /* "src/main/java/parser/Parser.y":1010  */
    {
        yyval = ((TreeNode)(yystack.valueAt (0)));
    };
  break;


  case 84: /* condition_head: identifier_operand  */
  if (yyn == 84)
    /* "src/main/java/parser/Parser.y":1014  */
    {
        yyval = ((TreeNode)(yystack.valueAt (0)));
    };
  break;


  case 85: /* condition_tail: and_condition_tail  */
  if (yyn == 85)
    /* "src/main/java/parser/Parser.y":1031  */
    { 
        yyval = ((ConditionParsingContext)(yystack.valueAt (0)));
    };
  break;


  case 86: /* condition_tail: OR or_operand condition_tail  */
  if (yyn == 86)
    /* "src/main/java/parser/Parser.y":1035  */
    {
        ((ConditionParsingContext)(yystack.valueAt (0))).addOperand(((TreeNode)(yystack.valueAt (1))));
        ((ConditionParsingContext)(yystack.valueAt (0))).addOperator(Label.OR);
        yyval = ((ConditionParsingContext)(yystack.valueAt (0)));
    };
  break;


  case 87: /* or_operand: condition_operand  */
  if (yyn == 87)
    /* "src/main/java/parser/Parser.y":1044  */
    { 
        yyval = ((TreeNode)(yystack.valueAt (0)));
    };
  break;


  case 88: /* or_operand: identifier_operand  */
  if (yyn == 88)
    /* "src/main/java/parser/Parser.y":1048  */
    { 
        yyval = ((TreeNode)(yystack.valueAt (0)));
    };
  break;


  case 89: /* and_condition_tail: %empty  */
  if (yyn == 89)
    /* "src/main/java/parser/Parser.y":1055  */
    {
        yyval = new ConditionParsingContext();
    };
  break;


  case 90: /* and_condition_tail: AND and_operand and_condition_tail  */
  if (yyn == 90)
    /* "src/main/java/parser/Parser.y":1059  */
    {
        ((ConditionParsingContext)(yystack.valueAt (0))).addOperand(((TreeNode)(yystack.valueAt (1))));
        ((ConditionParsingContext)(yystack.valueAt (0))).addOperator(Label.AND);
        yyval = ((ConditionParsingContext)(yystack.valueAt (0)));
    };
  break;


  case 91: /* and_operand: condition_operand  */
  if (yyn == 91)
    /* "src/main/java/parser/Parser.y":1068  */
    { 
        yyval = ((TreeNode)(yystack.valueAt (0)));
    };
  break;


  case 92: /* and_operand: identifier_operand  */
  if (yyn == 92)
    /* "src/main/java/parser/Parser.y":1072  */
    { 
        yyval = ((TreeNode)(yystack.valueAt (0)));
    };
  break;


  case 93: /* condition_operand: NOT condition_operand  */
  if (yyn == 93)
    /* "src/main/java/parser/Parser.y":1091  */
    {
        List<TreeNode> notChild = Collections.singletonList(((TreeNode)(yystack.valueAt (0))));
        yyval = new TreeNode(Label.NOT, notChild);
    };
  break;


  case 94: /* condition_operand: NOT identifier_operand  */
  if (yyn == 94)
    /* "src/main/java/parser/Parser.y":1096  */
    {
        List<TreeNode> notChild = Collections.singletonList(((TreeNode)(yystack.valueAt (0))));
        yyval = new TreeNode(Label.NOT, notChild);
    };
  break;


  case 95: /* condition_operand: subcondition  */
  if (yyn == 95)
    /* "src/main/java/parser/Parser.y":1101  */
    {
        yyval = ((TreeNode)(yystack.valueAt (0)));
    };
  break;


  case 96: /* condition_operand: '(' condition ')'  */
  if (yyn == 96)
    /* "src/main/java/parser/Parser.y":1105  */
    {
        yyval = ((TreeNode)(yystack.valueAt (1)));
    };
  break;


  case 97: /* subcondition: identifier_operand IS identifier_operand  */
  if (yyn == 97)
    /* "src/main/java/parser/Parser.y":1123  */
    {
        List<TreeNode> isChildren = new ArrayList<>();
        isChildren.add(((TreeNode)(yystack.valueAt (2))));
        isChildren.add(((TreeNode)(yystack.valueAt (0))));
        yyval = new TreeNode(Label.IS, isChildren);
    };
  break;


  case 98: /* subcondition: identifier_operand IS NOT identifier_operand  */
  if (yyn == 98)
    /* "src/main/java/parser/Parser.y":1130  */
    {
        List<TreeNode> isChildren = new ArrayList<>();
        isChildren.add(((TreeNode)(yystack.valueAt (3))));
        isChildren.add(((TreeNode)(yystack.valueAt (0))));
        TreeNode isNode = new TreeNode(Label.IS, isChildren);

        List<TreeNode> notChild = Collections.singletonList(isNode);
        yyval = new TreeNode(Label.NOT, notChild);
    };
  break;


  case 99: /* identifier_operand: IDENTIFIER  */
  if (yyn == 99)
    /* "src/main/java/parser/Parser.y":1143  */
    {
        yyval = new TreeNode(Label.IDENTIFIER, ((String)(yystack.valueAt (0))), null);
    };
  break;


  case 100: /* conclusions: conclusion_list  */
  if (yyn == 100)
    /* "src/main/java/parser/Parser.y":1171  */
    { 
        yyval = new TreeNode(Label.ACTIVATION, ((List<TreeNode>)(yystack.valueAt (0))));
    };
  break;


  case 101: /* conclusion_list: conclusion  */
  if (yyn == 101)
    /* "src/main/java/parser/Parser.y":1178  */
    { 
        List<TreeNode> conclusions = new ArrayList<>();
        conclusions.add(((TreeNode)(yystack.valueAt (0))));
        yyval = conclusions; 
    };
  break;


  case 102: /* conclusion_list: conclusion_list ',' conclusion  */
  if (yyn == 102)
    /* "src/main/java/parser/Parser.y":1184  */
    {  
        ((List<TreeNode>)(yystack.valueAt (2))).add(((TreeNode)(yystack.valueAt (0))));
        yyval = ((List<TreeNode>)(yystack.valueAt (2)));
    };
  break;


  case 103: /* conclusion: identifier_operand IS identifier_operand  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":1192  */
     { 
        List<TreeNode> isChildren = new ArrayList<>();
        isChildren.add(((TreeNode)(yystack.valueAt (2))));
        isChildren.add(((TreeNode)(yystack.valueAt (0))));
        yyval = new TreeNode(Label.IS, isChildren);
     };
  break;


  case 104: /* conclusion: identifier_operand  */
  if (yyn == 104)
    /* "src/main/java/parser/Parser.y":1199  */
     { 
        yyval = ((TreeNode)(yystack.valueAt (0)));
     };
  break;


  case 112: /* var_declarations: var_id_decl var_constant_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 112)
    /* "src/main/java/parser/Parser.y":1289  */
    {
        ((DeclarationBlock)(yystack.valueAt (2))).source(Source.INTERNAL).use(((Use)(yystack.valueAt (3)))).setDeclarationScope(programName);
        ((DeclarationBlock)(yystack.valueAt (2))).publish();
    };
  break;


  case 114: /* var_constant_spec: %empty  */
  if (yyn == 114)
    /* "src/main/java/parser/Parser.y":1330  */
    {
        yyval = Use.VARIABLE;
    };
  break;


  case 115: /* var_constant_spec: CONSTANT  */
  if (yyn == 115)
    /* "src/main/java/parser/Parser.y":1334  */
    {
        yyval = Use.CONSTANT;
    };
  break;


  case 116: /* var_init_decl_list: var_init_decl  */
  if (yyn == 116)
    /* "src/main/java/parser/Parser.y":1341  */
    {
        DeclarationBlock block = new DeclarationBlock();
        block.addDeclaration(((MultipleDeclaration)(yystack.valueAt (0))));
        yyval = block;
    };
  break;


  case 117: /* var_init_decl_list: var_init_decl_list ';' var_init_decl  */
  if (yyn == 117)
    /* "src/main/java/parser/Parser.y":1347  */
    {
        ((DeclarationBlock)(yystack.valueAt (2))).addDeclaration(((MultipleDeclaration)(yystack.valueAt (0))));
        yyval = ((DeclarationBlock)(yystack.valueAt (2)));
    };
  break;


  case 118: /* var_init_decl: identifier_list ':' var_spec_init  */
  if (yyn == 118)
    /* "src/main/java/parser/Parser.y":1355  */
    {
        ((MultipleDeclaration)(yystack.valueAt (0))).identifiers(((List<String>)(yystack.valueAt (2))));
        yyval = ((MultipleDeclaration)(yystack.valueAt (0)));
    };
  break;


  case 119: /* var_init_decl: identifier_list ':' standard_function_block_spec_init  */
  if (yyn == 119)
    /* "src/main/java/parser/Parser.y":1360  */
    {
        // TODO: remove null
        yyval = null;
    };
  break;


  case 120: /* identifier_list: IDENTIFIER  */
  if (yyn == 120)
    /* "src/main/java/parser/Parser.y":1368  */
    {
        List<String> identifiers = new ArrayList<>();
        identifiers.add(((String)(yystack.valueAt (0))));
        yyval = identifiers;
    };
  break;


  case 121: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 121)
    /* "src/main/java/parser/Parser.y":1374  */
    {
        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 122: /* var_spec_init: custom_spec_init  */
  if (yyn == 122)
    /* "src/main/java/parser/Parser.y":1412  */
    {
        /** Wraps the custom type builder in a MultipleDeclaration for variable list handling. */

        yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 123: /* var_spec_init: boolean_spec_init  */
  if (yyn == 123)
    /* "src/main/java/parser/Parser.y":1417  */
    {
        /** Wraps the boolean builder in a MultipleDeclaration for variable list handling. */

        yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 124: /* var_spec_init: simple_spec_init  */
  if (yyn == 124)
    /* "src/main/java/parser/Parser.y":1422  */
    {
        /** Wraps the simple type builder in a MultipleDeclaration for variable list handling. */

        yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 125: /* var_spec_init: subrange_spec_init  */
  if (yyn == 125)
    /* "src/main/java/parser/Parser.y":1427  */
    {
        /** Wraps the subrange builder in an MultipleDeclaration for variable list handling. */

        yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 126: /* var_spec_init: enumerated_spec_init  */
  if (yyn == 126)
    /* "src/main/java/parser/Parser.y":1433  */
    {
        /** Wraps the enumerated builder in an AnonymousEnumeratedVariable for variable list handling. */

        yyval = new AnnonymousEnumeratedVariable(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 127: /* var_spec_init: array_spec_init  */
  if (yyn == 127)
    /* "src/main/java/parser/Parser.y":1439  */
    {
        /** Wraps the array builder in a MultipleDeclaration for variable list handling. */

        yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 128: /* var_spec_init: string_spec_init  */
  if (yyn == 128)
    /* "src/main/java/parser/Parser.y":1445  */
    {
        /** Wraps the string builder in a MultipleDeclaration for variable list handling. */

        yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 129: /* custom_spec_init: custom_specification  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":1454  */
    {
        /** Propagates the custom type specification builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 130: /* custom_spec_init: initialized_custom  */
  if (yyn == 130)
    /* "src/main/java/parser/Parser.y":1460  */
    {
        /** Propagates the initialized custom builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 131: /* custom_specification: IDENTIFIER  */
  if (yyn == 131)
    /* "src/main/java/parser/Parser.y":1469  */
    {
        LexemeInfoBuilder customDeclaration = new LexemeInfoBuilder();
        Director.makeWithMetadata(customDeclaration, new CustomMetadata(this.symbolTable, ((String)(yystack.valueAt (0)))));
        yyval = customDeclaration;
    };
  break;


  case 132: /* initialized_custom: initialized_custom_with_constant  */
  if (yyn == 132)
    /* "src/main/java/parser/Parser.y":1478  */
    {
        /** Propagates the custom initialization with constant value. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 133: /* initialized_custom: initialized_custom_with_identifier  */
  if (yyn == 133)
    /* "src/main/java/parser/Parser.y":1484  */
    {
        /** Propagates the custom initialization with identifier reference. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 134: /* initialized_custom: initialized_custom_with_structure  */
  if (yyn == 134)
    /* "src/main/java/parser/Parser.y":1490  */
    {
        /** Propagates the custom initialization with structure value. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 135: /* initialized_custom_with_constant: custom_specification ASSIGN_OP constant  */
  if (yyn == 135)
    /* "src/main/java/parser/Parser.y":1499  */
    {
       ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
       yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 136: /* initialized_custom_with_identifier: custom_specification ASSIGN_OP referenced_identifier  */
  if (yyn == 136)
    /* "src/main/java/parser/Parser.y":1507  */
    {
        // TODO: check if it the referenced identifier is valid
        // In order to do that, the custom type from custom specification needs to be retrieved, then, the referenced
        // identifier needs to be in it's parameters list and the custom type has to be an enumerated.

        ParsedIdentifier referencedIdentifier = IdentifierParser.parse(((String)(yystack.valueAt (0))), ((LexemeInfoBuilder)(yystack.valueAt (2))).customType());

        LexemeInfo metadata = this.symbolTable.get(((LexemeInfoBuilder)(yystack.valueAt (2))).customType());
        if (metadata.type.equals(Type.ENUMERATE) && metadata.parameters.contains(referencedIdentifier.name())) {
            ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(referencedIdentifier.mangled()));
        }

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 137: /* initialized_custom_with_structure: custom_specification ASSIGN_OP structure_initialization  */
  if (yyn == 137)
    /* "src/main/java/parser/Parser.y":1525  */
    {
        StructInitialization structInit = (StructInitialization) ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue();

        for (PathInitializationInfo fieldInfo : ((List<PathInitializationInfo>)(yystack.valueAt (0)))) {
            if (structInit.find(fieldInfo.name()).isPresent()) {
                structInit.put(fieldInfo.name(), fieldInfo.initialValue());
            }
            // TODO: error handling
        }
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 138: /* boolean_spec_init: boolean_specification  */
  if (yyn == 138)
    /* "src/main/java/parser/Parser.y":1540  */
    {
        /** Propagates the boolean type specification builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 139: /* boolean_spec_init: initialized_boolean  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":1546  */
    {
        /** Propagates the initialized boolean builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 140: /* boolean_specification: BOOL  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":1555  */
    {
        LexemeInfoBuilder primitiveBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(primitiveBuilder, new PrimitiveMetadata(symbolTable, Subtype.BOOL));
        yyval = primitiveBuilder;
    };
  break;


  case 141: /* initialized_boolean: boolean_specification edge  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":1564  */
    {
        // TODO: add the edge specification
        yyval = null;
    };
  break;


  case 142: /* initialized_boolean: boolean_specification ASSIGN_OP boolean_constant  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":1569  */
    {
        ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 145: /* simple_spec_init: simple_specification  */
  if (yyn == 145)
    /* "src/main/java/parser/Parser.y":1582  */
    {
        /** Propagates the simple type specification builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 146: /* simple_spec_init: initialized_simple  */
  if (yyn == 146)
    /* "src/main/java/parser/Parser.y":1588  */
    {
        /** Propagates the initialized simple builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 147: /* simple_specification: elementary_type  */
  if (yyn == 147)
    /* "src/main/java/parser/Parser.y":1597  */
    {
        LexemeInfoBuilder primitiveBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(primitiveBuilder, new PrimitiveMetadata(this.symbolTable, ((Subtype)(yystack.valueAt (0)))));
        yyval = primitiveBuilder;
    };
  break;


  case 148: /* initialized_simple: simple_specification ASSIGN_OP constant  */
  if (yyn == 148)
    /* "src/main/java/parser/Parser.y":1606  */
    {
        ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
    };
  break;


  case 149: /* elementary_type: numeric_type  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":1630  */
    {
        /** Propagates the numeric type name. */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 150: /* elementary_type: date_type  */
  if (yyn == 150)
    /* "src/main/java/parser/Parser.y":1636  */
    {
        /** Propagates the date type name. */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 151: /* elementary_type: bit_string_type  */
  if (yyn == 151)
    /* "src/main/java/parser/Parser.y":1642  */
    {
        /** Propagates the bit string type name. */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 152: /* numeric_type: integer_type  */
  if (yyn == 152)
    /* "src/main/java/parser/Parser.y":1651  */
    {
        /** Propagates the integer type name. */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 153: /* numeric_type: real_type  */
  if (yyn == 153)
    /* "src/main/java/parser/Parser.y":1657  */
    {
        /** Propagates the real type name. */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 154: /* integer_type: signed_integer_type  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":1666  */
    {
        /** Propagates the signed integer type name.   */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 155: /* integer_type: unsigned_integer_type  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":1672  */
    {
        /** Propagates the unsigned integer type name. */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 156: /* signed_integer_type: SINT  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":1681  */
    {
        /** Sets the subtype to SINT. */

        yyval = Subtype.SINT;
    };
  break;


  case 157: /* signed_integer_type: INT  */
  if (yyn == 157)
    /* "src/main/java/parser/Parser.y":1687  */
    {
        /** Sets the subtype to INT.  */

        yyval = Subtype.INT;
    };
  break;


  case 158: /* signed_integer_type: DINT  */
  if (yyn == 158)
    /* "src/main/java/parser/Parser.y":1693  */
    {
        /** Sets the subtype to DINT. */

        yyval = Subtype.DINT;
    };
  break;


  case 159: /* signed_integer_type: LINT  */
  if (yyn == 159)
    /* "src/main/java/parser/Parser.y":1699  */
    {
        /** Sets the subtype to LINT. */

        yyval = Subtype.LINT;
    };
  break;


  case 160: /* unsigned_integer_type: USINT  */
  if (yyn == 160)
    /* "src/main/java/parser/Parser.y":1709  */
    {
        /** Sets the subtype to USINT. */

        yyval = Subtype.USINT;
    };
  break;


  case 161: /* unsigned_integer_type: UINT  */
  if (yyn == 161)
    /* "src/main/java/parser/Parser.y":1715  */
    {
        /** Sets the subtype to UINT.  */

        yyval = Subtype.UINT;
    };
  break;


  case 162: /* unsigned_integer_type: UDINT  */
  if (yyn == 162)
    /* "src/main/java/parser/Parser.y":1721  */
    {
        /** Sets the subtype to UDINT. */

        yyval = Subtype.UDINT;
    };
  break;


  case 163: /* unsigned_integer_type: ULINT  */
  if (yyn == 163)
    /* "src/main/java/parser/Parser.y":1727  */
    {
        /** Sets the subtype to ULINT. */

        yyval = Subtype.ULINT;
    };
  break;


  case 164: /* real_type: REAL  */
  if (yyn == 164)
    /* "src/main/java/parser/Parser.y":1737  */
    {
        /** Sets the subtype to REAL.  */

        yyval = Subtype.REAL;
    };
  break;


  case 165: /* real_type: LREAL  */
  if (yyn == 165)
    /* "src/main/java/parser/Parser.y":1743  */
    {
        /** Sets the subtype to LREAL. */

        yyval = Subtype.LREAL;
    };
  break;


  case 166: /* date_type: TIME  */
  if (yyn == 166)
    /* "src/main/java/parser/Parser.y":1753  */
    {
        /** Sets the subtype to TIME. */

        yyval = Subtype.TIME;
    };
  break;


  case 167: /* date_type: DATE  */
  if (yyn == 167)
    /* "src/main/java/parser/Parser.y":1759  */
    {
        /** Sets the subtype to DATE. */

        yyval = Subtype.DATE;
    };
  break;


  case 168: /* date_type: TIME_OF_DAY  */
  if (yyn == 168)
    /* "src/main/java/parser/Parser.y":1765  */
    {
        /** Sets the subtype to TIME_OF_DAY.   */

        yyval = Subtype.TIME_OF_DAY;
    };
  break;


  case 169: /* date_type: DATE_AND_TIME  */
  if (yyn == 169)
    /* "src/main/java/parser/Parser.y":1771  */
    {
        /** Sets the subtype to DATE_AND_TIME. */

        yyval = Subtype.DATE_AND_TIME;
    };
  break;


  case 170: /* constant: string_constant  */
  if (yyn == 170)
    /* "src/main/java/parser/Parser.y":1782  */
    {
        /** Propagates the string constant.  */

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 171: /* constant: boolean_constant  */
  if (yyn == 171)
    /* "src/main/java/parser/Parser.y":1788  */
    {
        /** Propagates the boolean constant. */

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 172: /* constant: time_constant  */
  if (yyn == 172)
    /* "src/main/java/parser/Parser.y":1794  */
    {
        /** Propagates the time constant.    */

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 173: /* constant: numeric_constant  */
  if (yyn == 173)
    /* "src/main/java/parser/Parser.y":1800  */
    {
        /** Propagates the numeric constant. */

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 174: /* string_constant: STRING_LITERAL  */
  if (yyn == 174)
    /* "src/main/java/parser/Parser.y":1809  */
    {
        /** Propagates the string literal. */

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 175: /* boolean_constant: BOOLEAN_LITERAL  */
  if (yyn == 175)
    /* "src/main/java/parser/Parser.y":1818  */
    {
        /** Propagates the boolean literal. */

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 176: /* numeric_constant: NUMERIC_LITERAL  */
  if (yyn == 176)
    /* "src/main/java/parser/Parser.y":1827  */
    {
        /** Propagates the numeric literal. */

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 177: /* numeric_constant: prefix_type '#' NUMERIC_LITERAL  */
  if (yyn == 177)
    /* "src/main/java/parser/Parser.y":1833  */
    {
        /**
         * Combines a base prefix (e.g., 16#) with a numeric literal to form
         * a based numeric constant. The conversion is deferred to later processing.
        **/

        // TODO: convert this constant in code
        yyval = "";
    };
  break;


  case 178: /* prefix_type: integer_type  */
  if (yyn == 178)
    /* "src/main/java/parser/Parser.y":1846  */
    {
        /** Propagates the integer type name prefix. */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 179: /* prefix_type: real_type  */
  if (yyn == 179)
    /* "src/main/java/parser/Parser.y":1852  */
    {
        /** Propagates the real type name prefix.    */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 180: /* prefix_type: bit_string_type  */
  if (yyn == 180)
    /* "src/main/java/parser/Parser.y":1858  */
    {
        /** Propagates the bit string type name prefix. */

        yyval = ((Subtype)(yystack.valueAt (0)));
    };
  break;


  case 181: /* time_constant: date_type '#' TIME_LITERAL  */
  if (yyn == 181)
    /* "src/main/java/parser/Parser.y":1867  */
    {
        /**
         * Combines a date/time type prefix with a time literal.
         * TODO: Add semantic action to verify prefix matches time_literal type.
        **/

        // TODO: semantic action to verify prefix matches time_literal type
    };
  break;


  case 182: /* bit_string_type: BYTE  */
  if (yyn == 182)
    /* "src/main/java/parser/Parser.y":1879  */
    {
        /** Sets the subtype to BYTE.  */

        yyval = Subtype.BYTE;
    };
  break;


  case 183: /* bit_string_type: WORD  */
  if (yyn == 183)
    /* "src/main/java/parser/Parser.y":1885  */
    {
        /** Sets the subtype to WORD.  */

        yyval = Subtype.WORD;
    };
  break;


  case 184: /* bit_string_type: DWORD  */
  if (yyn == 184)
    /* "src/main/java/parser/Parser.y":1891  */
    {
        /** Sets the subtype to DWORD. */

        yyval = Subtype.DWORD;
    };
  break;


  case 185: /* bit_string_type: LWORD  */
  if (yyn == 185)
    /* "src/main/java/parser/Parser.y":1897  */
    {
        /** Sets the subtype to LWORD. */

        yyval = Subtype.LWORD;
    };
  break;


  case 186: /* subrange_spec_init: subrange_specification  */
  if (yyn == 186)
    /* "src/main/java/parser/Parser.y":1908  */
    {
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 187: /* subrange_spec_init: initialized_subrange  */
  if (yyn == 187)
    /* "src/main/java/parser/Parser.y":1912  */
    {
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 188: /* subrange_specification: integer_type '(' range ')'  */
  if (yyn == 188)
    /* "src/main/java/parser/Parser.y":1919  */
    {
        LexemeInfoBuilder subrangeBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(subrangeBuilder, new SubrangeMetadata(((Subtype)(yystack.valueAt (3))), ((List<String>)(yystack.valueAt (1))).get(0), ((List<String>)(yystack.valueAt (1))).get(1)));
        yyval = subrangeBuilder;
    };
  break;


  case 189: /* initialized_subrange: subrange_specification ASSIGN_OP numeric_constant  */
  if (yyn == 189)
    /* "src/main/java/parser/Parser.y":1928  */
    {
        ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 190: /* range: numeric_constant RANGE_OP numeric_constant  */
  if (yyn == 190)
    /* "src/main/java/parser/Parser.y":1936  */
    {
        List<String> ranges = new ArrayList<>();
        ranges.add(((String)(yystack.valueAt (2))));
        ranges.add(((String)(yystack.valueAt (0))));
        yyval = ranges;
    };
  break;


  case 191: /* enumerated_spec_init: enumerated_specification  */
  if (yyn == 191)
    /* "src/main/java/parser/Parser.y":1946  */
    {
        /** Propagates the enumerated type specification builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 192: /* enumerated_spec_init: initialized_enumerated  */
  if (yyn == 192)
    /* "src/main/java/parser/Parser.y":1952  */
    {
        /** Propagates the initialized enumerated builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 193: /* enumerated_specification: '(' identifier_list ')'  */
  if (yyn == 193)
    /* "src/main/java/parser/Parser.y":1961  */
    {
        LexemeInfoBuilder enumBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(enumBuilder, new EnumeratedMetadata(((List<String>)(yystack.valueAt (1)))));
        yyval = enumBuilder;
    };
  break;


  case 194: /* initialized_enumerated: enumerated_specification ASSIGN_OP referenced_identifier  */
  if (yyn == 194)
    /* "src/main/java/parser/Parser.y":1970  */
    {
        // TODO: semantic check over initial value
        // Note: it has to be in the enumerated_specification parameters. The initial value can't be global because
        // it's in a new enumerated specification declaration (type incompatible). However, another enumerated
        // declaration could have declared the same "<programName>#<enumeratedValue>", so, it's important to check that
        // there's no redeclaration.

        ParsedIdentifier referencedIdentifier = IdentifierParser.parse(((String)(yystack.valueAt (0))), programName);
        if (!referencedIdentifier.scope().isEmpty()) {
            // TODO: throw error
        }

        ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(referencedIdentifier.mangled()));
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 195: /* array_spec_init: array_specification  */
  if (yyn == 195)
    /* "src/main/java/parser/Parser.y":1989  */
    {
        /** Propagates the array type specification builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 196: /* array_spec_init: initialized_array  */
  if (yyn == 196)
    /* "src/main/java/parser/Parser.y":1995  */
    {
        /** Propagates the initialized array builder. */

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 197: /* array_specification: ARRAY '[' range_list ']' OF IDENTIFIER  */
  if (yyn == 197)
    /* "src/main/java/parser/Parser.y":2004  */
    {
        ArrayMetadata arrayMetadata = new ArrayMetadata(this.symbolTable, Subtype.CUSTOM, ((String)(yystack.valueAt (0))), ((List<String>)(yystack.valueAt (3))));

        LexemeInfoBuilder arrayBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(arrayBuilder, arrayMetadata);
        yyval = arrayBuilder;
    };
  break;


  case 198: /* array_specification: ARRAY '[' range_list ']' OF non_generic_type  */
  if (yyn == 198)
    /* "src/main/java/parser/Parser.y":2012  */
    {
        ArrayMetadata arrayMetadata = new ArrayMetadata(this.symbolTable, ((Subtype)(yystack.valueAt (0))), null, ((List<String>)(yystack.valueAt (3))));

        LexemeInfoBuilder arrayBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(arrayBuilder, arrayMetadata);
        yyval = arrayBuilder;
    };
  break;


  case 199: /* initialized_array: array_specification ASSIGN_OP array_initialization  */
  if (yyn == 199)
    /* "src/main/java/parser/Parser.y":2023  */
    {
        RepeatedInitialization arrayInitialValue = (RepeatedInitialization) ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue();
        for (PathInitializationInfo rangeInfo : ((List<PathInitializationInfo>)(yystack.valueAt (0)))) {
            String range = rangeInfo.name();
            Initialization initialValue = rangeInfo.initialValue();
            arrayInitialValue.put(range, initialValue);
        }
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 200: /* range_list: range  */
  if (yyn == 200)
    /* "src/main/java/parser/Parser.y":2036  */
    {
        yyval = ((List<String>)(yystack.valueAt (0)));
    };
  break;


  case 201: /* range_list: range_list ',' range  */
  if (yyn == 201)
    /* "src/main/java/parser/Parser.y":2040  */
    {
        ((List<String>)(yystack.valueAt (2))).addAll(((List<String>)(yystack.valueAt (0))));
    };
  break;


  case 203: /* array_initialization: '[' array_initial_elements_list ']'  */
  if (yyn == 203)
    /* "src/main/java/parser/Parser.y":2077  */
    {
        yyval = ((ArrayParsingContext)(yystack.valueAt (1))).getPathsInfo();
    };
  break;


  case 204: /* array_initial_elements_list: array_initial_elements  */
  if (yyn == 204)
    /* "src/main/java/parser/Parser.y":2084  */
    {
        int endIdx = ((ArrayParsingContext)(yystack.valueAt (0))).getMaxValue() - 1;

        List<PathInitializationInfo> newPathsInfo = new ArrayList<>();
        for (PathInitializationInfo oldPathInfo : ((ArrayParsingContext)(yystack.valueAt (0))).getPathsInfo()) {
            newPathsInfo.add(new PathInitializationInfo(
                "0.." + endIdx + "#" + oldPathInfo.name(),
                oldPathInfo.initialValue()
            ));
        }
        yyval = new ArrayParsingContext(endIdx, newPathsInfo);
    };
  break;


  case 205: /* array_initial_elements_list: array_initial_elements_list ',' array_initial_elements  */
  if (yyn == 205)
    /* "src/main/java/parser/Parser.y":2097  */
    {
        int lastIdx = ((ArrayParsingContext)(yystack.valueAt (2))).getMaxValue();
        int startIdx = lastIdx + 1;
        int endIdx = lastIdx + ((ArrayParsingContext)(yystack.valueAt (0))).getMaxValue();

        List<PathInitializationInfo> newPathsInfo = ((ArrayParsingContext)(yystack.valueAt (2))).getPathsInfo();
        for (PathInitializationInfo oldPathInfo : ((ArrayParsingContext)(yystack.valueAt (0))).getPathsInfo()) {
            newPathsInfo.add(new PathInitializationInfo(
                startIdx + ".." + endIdx + "#" + oldPathInfo.name(),
                oldPathInfo.initialValue()
            ));
        }
        yyval = new ArrayParsingContext(endIdx, newPathsInfo);
    };
  break;


  case 206: /* array_initial_elements: array_initial_element  */
  if (yyn == 206)
    /* "src/main/java/parser/Parser.y":2115  */
    {
        /** Creates a ArrayParsingContext for a single array element. */

        yyval = new ArrayParsingContext(1, ((List<PathInitializationInfo>)(yystack.valueAt (0))));
    };
  break;


  case 207: /* array_initial_elements: repeated_initial_element  */
  if (yyn == 207)
    /* "src/main/java/parser/Parser.y":2121  */
    {
        /** Propagates the repeated element context. */

        yyval = ((ArrayParsingContext)(yystack.valueAt (0)));
    };
  break;


  case 208: /* array_initial_element: constant  */
  if (yyn == 208)
    /* "src/main/java/parser/Parser.y":2130  */
    {
        List<PathInitializationInfo> pathsInfo = new ArrayList<>();
        pathsInfo.add(new PathInitializationInfo("", new VariableInitialization(((String)(yystack.valueAt (0))))));
        yyval = pathsInfo;
    };
  break;


  case 209: /* array_initial_element: referenced_identifier  */
  if (yyn == 209)
    /* "src/main/java/parser/Parser.y":2136  */
    {
        // TODO: see how to check semantics
        List<PathInitializationInfo> pathsInfo = new ArrayList<>();
        pathsInfo.add(new PathInitializationInfo("", new VariableInitialization(((String)(yystack.valueAt (0))))));
        yyval = pathsInfo;
    };
  break;


  case 210: /* array_initial_element: structure_initialization  */
  if (yyn == 210)
    /* "src/main/java/parser/Parser.y":2143  */
    {
        /** Propagates the structure initialization paths. */

        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0)));
    };
  break;


  case 211: /* array_initial_element: array_initialization  */
  if (yyn == 211)
    /* "src/main/java/parser/Parser.y":2149  */
    {
        /** Propagates the array initialization paths. */

        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0)));
    };
  break;


  case 212: /* repeated_initial_element: numeric_constant '(' array_initial_element ')'  */
  if (yyn == 212)
    /* "src/main/java/parser/Parser.y":2158  */
    {
        yyval = new ArrayParsingContext(Integer.parseInt(((String)(yystack.valueAt (3)))), ((List<PathInitializationInfo>)(yystack.valueAt (1))));
    };
  break;


  case 213: /* structure_initialization: '(' structure_field_init_list ')'  */
  if (yyn == 213)
    /* "src/main/java/parser/Parser.y":2186  */
    {
        /** Propagates the structure field initialization list. */

        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (1)));
    };
  break;


  case 214: /* structure_field_init_list: initialized_struct_field  */
  if (yyn == 214)
    /* "src/main/java/parser/Parser.y":2195  */
    {
        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0)));
    };
  break;


  case 215: /* structure_field_init_list: structure_field_init_list ',' initialized_struct_field  */
  if (yyn == 215)
    /* "src/main/java/parser/Parser.y":2199  */
    {
        ((List<PathInitializationInfo>)(yystack.valueAt (2))).addAll(((List<PathInitializationInfo>)(yystack.valueAt (0))));
        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (2)));
    };
  break;


  case 216: /* initialized_struct_field: initialized_field_with_constant  */
  if (yyn == 216)
    /* "src/main/java/parser/Parser.y":2207  */
    {
        /** Propagates the field initialization with constant value. */

        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0)));
    };
  break;


  case 217: /* initialized_struct_field: initialized_field_with_identifier  */
  if (yyn == 217)
    /* "src/main/java/parser/Parser.y":2213  */
    {
        /** Propagates the field initialization with identifier reference. */

        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0)));
    };
  break;


  case 218: /* initialized_struct_field: initialized_field_with_array  */
  if (yyn == 218)
    /* "src/main/java/parser/Parser.y":2219  */
    {
        /** Propagates the field initialization with array value. */

        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0)));
    };
  break;


  case 219: /* initialized_struct_field: initialized_field_with_structure  */
  if (yyn == 219)
    /* "src/main/java/parser/Parser.y":2225  */
    {
        /** Propagates the field initialization with structure value. */

        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0)));
    };
  break;


  case 220: /* initialized_field_with_constant: field_name ASSIGN_OP constant  */
  if (yyn == 220)
    /* "src/main/java/parser/Parser.y":2234  */
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        fieldsInfo.add(new PathInitializationInfo(((String)(yystack.valueAt (2))), new VariableInitialization(((String)(yystack.valueAt (0))))));
        yyval = fieldsInfo;
    };
  break;


  case 221: /* initialized_field_with_identifier: field_name ASSIGN_OP referenced_identifier  */
  if (yyn == 221)
    /* "src/main/java/parser/Parser.y":2243  */
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        fieldsInfo.add(new PathInitializationInfo(((String)(yystack.valueAt (2))), new VariableInitialization(((String)(yystack.valueAt (0))))));
        yyval = fieldsInfo;
    };
  break;


  case 222: /* initialized_field_with_array: field_name ASSIGN_OP array_initialization  */
  if (yyn == 222)
    /* "src/main/java/parser/Parser.y":2252  */
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        for (PathInitializationInfo fieldInfo : ((List<PathInitializationInfo>)(yystack.valueAt (0)))) {
            String fieldName = ((String)(yystack.valueAt (2))) + "#" + fieldInfo.name();
            fieldsInfo.add(new PathInitializationInfo(fieldName, fieldInfo.initialValue()));
        }
        yyval = fieldsInfo;
    };
  break;


  case 223: /* initialized_field_with_structure: field_name ASSIGN_OP structure_initialization  */
  if (yyn == 223)
    /* "src/main/java/parser/Parser.y":2264  */
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        for (PathInitializationInfo fieldInfo : ((List<PathInitializationInfo>)(yystack.valueAt (0)))) {
            String fieldName = ((String)(yystack.valueAt (2))) + "#" + fieldInfo.name();
            fieldsInfo.add(new PathInitializationInfo(fieldName, fieldInfo.initialValue()));
        }
        yyval = fieldsInfo;
    };
  break;


  case 224: /* referenced_identifier: IDENTIFIER  */
  if (yyn == 224)
    /* "src/main/java/parser/Parser.y":2276  */
    {
        /**
         * Simple identifier: keeps the lexeme as-is if it's global, else assumes it is local.
        **/

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 225: /* referenced_identifier: IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 225)
    /* "src/main/java/parser/Parser.y":2284  */
    {
        /**
         * Mangled identifier: joins both identifiers with '#'.
        **/

        yyval = ((String)(yystack.valueAt (2))) + "#" + ((String)(yystack.valueAt (0)));
    };
  break;


  case 230: /* string_spec_init: string_specification  */
  if (yyn == 230)
    /* "src/main/java/parser/Parser.y":2309  */
    {
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 231: /* string_spec_init: initialized_string  */
  if (yyn == 231)
    /* "src/main/java/parser/Parser.y":2313  */
    {
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0)));
    };
  break;


  case 232: /* string_specification: string_type  */
  if (yyn == 232)
    /* "src/main/java/parser/Parser.y":2320  */
    {
        LexemeInfoBuilder stringBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(stringBuilder, new PrimitiveMetadata(this.symbolTable, ((Subtype)(yystack.valueAt (0)))));
        yyval = stringBuilder;
    };
  break;


  case 233: /* string_specification: string_type '[' numeric_constant ']'  */
  if (yyn == 233)
    /* "src/main/java/parser/Parser.y":2326  */
    {
        LexemeInfoBuilder stringBuilder = new LexemeInfoBuilder();
        Director.makeWithMetadata(stringBuilder, new StringMetadata(this.symbolTable, ((Subtype)(yystack.valueAt (3))), ((String)(yystack.valueAt (1)))));
        yyval = stringBuilder;
    };
  break;


  case 234: /* initialized_string: string_specification ASSIGN_OP string_constant  */
  if (yyn == 234)
    /* "src/main/java/parser/Parser.y":2335  */
    {
        ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 235: /* string_type: STRING  */
  if (yyn == 235)
    /* "src/main/java/parser/Parser.y":2343  */
    {
        /** Sets the subtype to STRING. */

        yyval = Subtype.STRING;
    };
  break;


  case 236: /* string_type: WSTRING  */
  if (yyn == 236)
    /* "src/main/java/parser/Parser.y":2349  */
    {
        /** Sets the subtype to WSTRING. */

        yyval = Subtype.WSTRING;
    };
  break;


  case 242: /* type_declaration: type_name_declaration ':' type_spec_init  */
  if (yyn == 242)
    /* "src/main/java/parser/Parser.y":2400  */
    {
        ((SingleDeclaration)(yystack.valueAt (0))).identifier(((String)(yystack.valueAt (2)))).use(Use.TYPE).source(Source.NONE);
        ((SingleDeclaration)(yystack.valueAt (0))).publish();
    };
  break;


  case 243: /* type_name_declaration: IDENTIFIER  */
  if (yyn == 243)
    /* "src/main/java/parser/Parser.y":2408  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 244: /* type_spec_init: custom_spec_init  */
  if (yyn == 244)
    /* "src/main/java/parser/Parser.y":2444  */
    {
        /** Wraps the custom type builder in a SingleDeclaration for type declaration. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 245: /* type_spec_init: simple_spec_init  */
  if (yyn == 245)
    /* "src/main/java/parser/Parser.y":2450  */
    {
        /** Wraps the simple type builder in a SingleDeclaration for type declaration. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 246: /* type_spec_init: enumerated_spec_init  */
  if (yyn == 246)
    /* "src/main/java/parser/Parser.y":2456  */
    {
        /** Wraps the enumerated builder in an EnumeratedTypeDeclaration for type declaration. */

        yyval = new EnumeratedTypeDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 247: /* type_spec_init: subrange_spec_init  */
  if (yyn == 247)
    /* "src/main/java/parser/Parser.y":2462  */
    {
        /** Wraps the subrange builder in an SingleDeclaration for type declaration. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 248: /* type_spec_init: array_spec_init  */
  if (yyn == 248)
    /* "src/main/java/parser/Parser.y":2468  */
    {
        /** Wraps the array builder in a SingleDeclaration for type declaration. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 249: /* type_spec_init: structure_specification  */
  if (yyn == 249)
    /* "src/main/java/parser/Parser.y":2474  */
    {
        /** Propagates the structure declaration. */

        yyval = ((SingleDeclaration)(yystack.valueAt (0)));
    };
  break;


  case 250: /* type_spec_init: string_spec_init  */
  if (yyn == 250)
    /* "src/main/java/parser/Parser.y":2480  */
    {
        /** Wraps the string builder in a SingleDeclaration for type declaration. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 251: /* structure_specification: STRUCT structure_field_init_decl_list END_STRUCT  */
  if (yyn == 251)
    /* "src/main/java/parser/Parser.y":2512  */
    {
        StructureDeclaration structDeclaration = new StructureDeclaration(this.symbolTable, ((StructureBlock)(yystack.valueAt (1))));
        Director.makeWithMetadata(structDeclaration, new StructureMetadata(((StructureBlock)(yystack.valueAt (1)))));

        yyval = structDeclaration;
    };
  break;


  case 252: /* structure_field_init_decl_list: structure_field_init_decl ';'  */
  if (yyn == 252)
    /* "src/main/java/parser/Parser.y":2522  */
    {
        StructureBlock structBlock = new StructureBlock();
        structBlock.addField(((SingleDeclaration)(yystack.valueAt (1))));
        yyval = structBlock;
    };
  break;


  case 253: /* structure_field_init_decl_list: structure_field_init_decl_list structure_field_init_decl ';'  */
  if (yyn == 253)
    /* "src/main/java/parser/Parser.y":2528  */
    {
        ((StructureBlock)(yystack.valueAt (2))).addField(((SingleDeclaration)(yystack.valueAt (1))));
        yyval = ((StructureBlock)(yystack.valueAt (2)));
    };
  break;


  case 254: /* structure_field_init_decl: field_name ':' structure_field_spec_init  */
  if (yyn == 254)
    /* "src/main/java/parser/Parser.y":2536  */
    {
        ((SingleDeclaration)(yystack.valueAt (0))).identifier(((String)(yystack.valueAt (2)))).use(Use.FIELD).source(Source.NONE);
        yyval = ((SingleDeclaration)(yystack.valueAt (0)));
    };
  break;


  case 255: /* field_name: IDENTIFIER  */
  if (yyn == 255)
    /* "src/main/java/parser/Parser.y":2544  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 256: /* structure_field_spec_init: custom_spec_init  */
  if (yyn == 256)
    /* "src/main/java/parser/Parser.y":2581  */
    {
        /** Wraps the custom type builder in a SingleDeclaration for structure field. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 257: /* structure_field_spec_init: boolean_spec_init  */
  if (yyn == 257)
    /* "src/main/java/parser/Parser.y":2587  */
    {
        /** Wraps the boolean builder in a SingleDeclaration for structure field. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 258: /* structure_field_spec_init: simple_spec_init  */
  if (yyn == 258)
    /* "src/main/java/parser/Parser.y":2593  */
    {
        /** Wraps the simple type builder in a SingleDeclaration for structure field. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 259: /* structure_field_spec_init: enumerated_spec_init  */
  if (yyn == 259)
    /* "src/main/java/parser/Parser.y":2599  */
    {
        /** Wraps the enumerated builder in an AnonymousEnumeratedField for structure field. */

        yyval = new AnnonymousEnumeratedField(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 260: /* structure_field_spec_init: subrange_spec_init  */
  if (yyn == 260)
    /* "src/main/java/parser/Parser.y":2605  */
    {
        /** Wraps the subrange builder in an SingleDeclaration for type declaration. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 261: /* structure_field_spec_init: array_spec_init  */
  if (yyn == 261)
    /* "src/main/java/parser/Parser.y":2611  */
    {
        /** Wraps the array builder in a SingleDeclaration for structure field. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 262: /* structure_field_spec_init: string_spec_init  */
  if (yyn == 262)
    /* "src/main/java/parser/Parser.y":2617  */
    {
        /** Wraps the string builder in a SingleDeclaration for structure field. */

        yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;



/* "src/main/java/parser/Parser.java":3505  */

        default: break;
      }

    yystack.pop(yylen);
    yylen = 0;
    /* Shift the result of the reduction.  */
    int yystate = yyLRGotoState(yystack.stateAt(0), yyr1_[yyn]);
    yystack.push(yystate, yyval);
    return YYNEWSTATE;
  }




  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse() throws java.io.IOException

  {


    /* Lookahead token kind.  */
    int yychar = YYEMPTY_;
    /* Lookahead symbol kind.  */
    SymbolKind yytoken = null;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;
    YYStack yystack = new YYStack ();
    int label = YYNEWSTATE;



    /* Semantic value of the lookahead.  */
    Object yylval = null;



    yyerrstatus_ = 0;
    yynerrs = 0;

    /* Initialize the stack.  */
    yystack.push (yystate, yylval);



    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
           pushed when we come here.  */
      case YYNEWSTATE:

        /* Accept?  */
        if (yystate == YYFINAL_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyPactValueIsDefault (yyn))
          {
            label = YYDEFAULT;
            break;
          }

        /* Read a lookahead token.  */
        if (yychar == YYEMPTY_)
          {

            yychar = yylexer.yylex ();
            yylval = yylexer.getLVal();

          }

        /* Convert token to internal form.  */
        yytoken = yytranslate_ (yychar);

        if (yytoken == SymbolKind.S_YYerror)
          {
            // The scanner already issued an error message, process directly
            // to error recovery.  But do not keep the error token as
            // lookahead, it is too special and may lead us to an endless
            // loop in error recovery. */
            yychar = Lexer.YYUNDEF;
            yytoken = SymbolKind.S_YYUNDEF;
            label = YYERRLAB1;
          }
        else
          {
            /* If the proper action on seeing token YYTOKEN is to reduce or to
               detect an error, take that action.  */
            yyn += yytoken.getCode();
            if (yyn < 0 || YYLAST_ < yyn || yycheck_[yyn] != yytoken.getCode()) {
              label = YYDEFAULT;
            }

            /* <= 0 means reduce or error.  */
            else if ((yyn = yytable_[yyn]) <= 0)
              {
                if (yyTableValueIsError(yyn)) {
                  label = YYERRLAB;
                } else {
                  yyn = -yyn;
                  label = YYREDUCE;
                }
              }

            else
              {
                /* Shift the lookahead token.  */
                /* Discard the token being shifted.  */
                yychar = YYEMPTY_;

                /* Count tokens shifted since error; after three, turn off error
                   status.  */
                if (yyerrstatus_ > 0)
                  --yyerrstatus_;

                yystate = yyn;
                yystack.push(yystate, yylval);
                label = YYNEWSTATE;
              }
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction(yyn, yystack, yylen);
        yystate = yystack.stateAt(0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs;
            if (yychar == YYEMPTY_)
              yytoken = null;
            yyreportSyntaxError(new Context(this, yystack, yytoken));
          }

        if (yyerrstatus_ == 3)
          {
            /* If just tried and failed to reuse lookahead token after an
               error, discard it.  */

            if (yychar <= Lexer.YYEOF)
              {
                /* Return failure if at end of input.  */
                if (yychar == Lexer.YYEOF)
                  return false;
              }
            else
              yychar = YYEMPTY_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `-------------------------------------------------*/
      case YYERROR:
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt(0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;       /* Each real token shifted decrements this.  */

        // Pop stack until we find a state that shifts the error token.
        for (;;)
          {
            yyn = yypact_[yystate];
            if (!yyPactValueIsDefault (yyn))
              {
                yyn += SymbolKind.S_YYerror.getCode();
                if (0 <= yyn && yyn <= YYLAST_
                    && yycheck_[yyn] == SymbolKind.S_YYerror.getCode())
                  {
                    yyn = yytable_[yyn];
                    if (0 < yyn)
                      break;
                  }
              }

            /* Pop the current state because it cannot handle the
             * error token.  */
            if (yystack.height == 0)
              return false;


            yystack.pop ();
            yystate = yystack.stateAt(0);
          }

        if (label == YYABORT)
          /* Leave the switch.  */
          break;



        /* Shift the error token.  */

        yystate = yyn;
        yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
}




  /**
   * Information needed to get the list of expected tokens and to forge
   * a syntax error diagnostic.
   */
  public static final class Context {
    Context(Parser parser, YYStack stack, SymbolKind token) {
      yyparser = parser;
      yystack = stack;
      yytoken = token;
    }

    private Parser yyparser;
    private YYStack yystack;


    /**
     * The symbol kind of the lookahead token.
     */
    public final SymbolKind getToken() {
      return yytoken;
    }

    private SymbolKind yytoken;
    static final int NTOKENS = Parser.YYNTOKENS_;

    /**
     * Put in YYARG at most YYARGN of the expected tokens given the
     * current YYCTX, and return the number of tokens stored in YYARG.  If
     * YYARG is null, return the number of expected tokens (guaranteed to
     * be less than YYNTOKENS).
     */
    int getExpectedTokens(SymbolKind yyarg[], int yyargn) {
      return getExpectedTokens (yyarg, 0, yyargn);
    }

    int getExpectedTokens(SymbolKind yyarg[], int yyoffset, int yyargn) {
      int yycount = yyoffset;
      int yyn = yypact_[this.yystack.stateAt(0)];
      if (!yyPactValueIsDefault(yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative
             indexes in YYCHECK.  In other words, skip the first
             -YYN actions for this state because they are default
             actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST_ - yyn + 1;
          int yyxend = yychecklim < NTOKENS ? yychecklim : NTOKENS;
          for (int yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck_[yyx + yyn] == yyx && yyx != SymbolKind.S_YYerror.getCode()
                && !yyTableValueIsError(yytable_[yyx + yyn]))
              {
                if (yyarg == null)
                  yycount += 1;
                else if (yycount == yyargn)
                  return 0; // FIXME: this is incorrect.
                else
                  yyarg[yycount++] = SymbolKind.get(yyx);
              }
        }
      if (yyarg != null && yycount == yyoffset && yyoffset < yyargn)
        yyarg[yycount] = null;
      return yycount - yyoffset;
    }
  }





  /**
   * Build and emit a "syntax error" message in a user-defined way.
   *
   * @param ctx  The context of the error.
   */
  private void yyreportSyntaxError(Context yyctx) {
      yyerror("syntax error");
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yyPactValueIsDefault(int yyvalue) {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code>
   * value indicates a syntax error.
   * @param yyvalue the value to check
   */
  private static boolean yyTableValueIsError(int yyvalue) {
    return yyvalue == yytable_ninf_;
  }

  private static final short yypact_ninf_ = -254;
  private static final short yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      66,   -15,    75,    76,  -254,  -254,    20,    -4,     7,  -254,
      88,  -254,  -254,    29,  -254,    74,  -254,  -254,  -254,   126,
    -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,
    -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,
    -254,    81,   139,  -254,   136,  -254,  -254,  -254,  -254,  -254,
     140,  -254,  -254,  -254,    99,  -254,  -254,  -254,  -254,  -254,
    -254,   141,  -254,  -254,   146,  -254,  -254,   147,  -254,  -254,
     154,  -254,   110,  -254,  -254,   -22,  -254,    17,   117,   114,
     421,  -254,    11,   252,   398,   421,   421,   160,   115,   127,
     421,  -254,  -254,  -254,    80,   150,  -254,   124,  -254,   216,
    -254,  -254,  -254,   163,   121,  -254,  -254,    -3,   168,  -254,
     125,  -254,  -254,   126,   129,  -254,  -254,  -254,  -254,  -254,
    -254,  -254,  -254,   130,  -254,  -254,   -23,  -254,  -254,   122,
    -254,  -254,   139,  -254,  -254,   208,   211,  -254,   159,  -254,
    -254,  -254,  -254,   -18,  -254,  -254,  -254,  -254,  -254,  -254,
    -254,   421,   142,   421,   152,  -254,   179,    87,  -254,  -254,
    -254,  -254,  -254,   181,   144,  -254,  -254,   145,  -254,    70,
    -254,  -254,  -254,  -254,  -254,  -254,   149,  -254,   -13,  -254,
     186,  -254,   221,  -254,   139,   153,  -254,  -254,  -254,  -254,
    -254,  -254,   354,  -254,   126,  -254,   -23,  -254,   -23,   -23,
    -254,    26,   180,  -254,   218,   194,  -254,   236,   176,  -254,
    -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,   174,
    -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,
    -254,  -254,  -254,   219,  -254,   223,   218,  -254,  -254,   248,
     226,  -254,   256,    28,  -254,   187,   228,   262,  -254,   199,
     218,  -254,   267,   250,  -254,  -254,  -254,   290,  -254,   421,
     281,   218,   212,   278,   276,   263,    14,  -254,   224,   311,
    -254,   225,  -254,   220,  -254,  -254,   261,   222,   293,    -7,
     232,   310,  -254,   251,   254,   373,  -254,  -254,  -254,   255,
     257,  -254,  -254,   421,   164,   291,   329,  -254,  -254,  -254,
    -254,   128,   258,  -254,    85,  -254,  -254,   260,   421,   421,
     268,  -254,  -254,  -254,  -254,  -254,   264,    31,  -254,  -254,
    -254,   265,   123,   332,   322,  -254,  -254,  -254,  -254,  -254,
     269,   270,   274,  -254,  -254,   286,  -254,  -254,  -254,  -254,
    -254,   294,  -254,   421,  -254,  -254,  -254,  -254,  -254,  -254,
     259,   356,    -9,    -9,  -254,    -9,   357,   138,  -254,  -254,
     365,  -254,   365,   305,   355,    -9,    -9,  -254,  -254,    38,
    -254,   374,   376,   315,  -254,   385,  -254,   365,   138,  -254,
     365,   355,  -254,   355,   333,   338,   355,  -254,  -254,  -254,
    -254,  -254,  -254,  -254,  -254
    };
  }

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
  private static final short[] yydefact_ = yydefact_init();
  private static final short[] yydefact_init()
  {
    return new short[]
    {
     237,     0,     0,     0,   238,   243,     0,     0,     0,     1,
       0,     2,   239,     0,   240,     0,     4,     5,   241,     0,
     131,   235,   236,   182,   183,   184,   185,   166,   168,   167,
     169,   156,   157,   158,   159,   160,   161,   162,   163,   164,
     165,     0,     0,   244,   129,   130,   132,   133,   134,   245,
     145,   146,   147,   149,   152,   154,   155,   153,   150,   151,
     247,   186,   187,   246,   191,   192,   248,   195,   196,   250,
     230,   231,   232,   242,   249,    13,   255,     0,     0,     0,
       0,   120,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     8,     9,     6,    10,    17,   251,     0,   252,     0,
     176,   178,   179,     0,     0,   180,   200,     0,     0,   193,
     224,   174,   175,     0,     0,   135,   170,   171,   173,   172,
     137,   136,   148,     0,   189,   194,     0,   199,   234,     0,
      11,    12,     0,   113,    14,     0,    22,    15,   114,   253,
     140,   256,   257,   138,   139,   258,   260,   259,   261,   262,
     254,     0,     0,     0,     0,   121,     0,     0,   214,   216,
     217,   218,   219,     0,     0,   188,   208,   173,   211,     0,
     204,   206,   207,   210,   209,   233,     0,   116,     0,     3,
       0,    18,    51,   115,     0,     0,   143,   144,   141,   190,
     177,   201,     0,   225,     0,   213,     0,   181,     0,     0,
     203,     0,     0,    21,     0,     0,    23,   105,     0,   142,
     197,   202,   152,   198,   215,   220,   222,   223,   221,     0,
     205,     7,   117,   229,   118,   122,   123,   124,   125,   126,
     127,   119,   227,   226,   128,     0,    19,    28,    25,    49,
       0,    52,    16,     0,   212,     0,     0,     0,    29,     0,
      26,    54,    56,     0,   106,   112,   228,     0,    20,     0,
       0,    27,     0,    66,    58,     0,     0,   108,     0,     0,
      30,     0,    33,    34,    36,    35,     0,     0,     0,     0,
       0,     0,    67,     0,     0,     0,   107,   109,    31,     0,
       0,    32,    37,     0,     0,     0,     0,    60,    61,    62,
      57,     0,     0,    76,     0,    55,   110,     0,     0,     0,
       0,    41,    42,    43,    44,    45,     0,     0,    24,    70,
      69,     0,     0,     0,    75,    63,    64,    65,    59,   111,
       0,     0,     0,    40,    48,     0,    47,    68,    72,    73,
      74,     0,    53,     0,    77,    39,    38,    50,    46,    71,
       0,     0,     0,     0,    99,     0,     0,    89,    83,    95,
      84,    93,    94,     0,     0,     0,     0,    82,    85,     0,
      96,   104,    79,   100,   101,    89,    91,    92,    89,    87,
      88,     0,    97,     0,     0,     0,     0,    90,    86,    98,
     103,    81,    80,    78,   102
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,
    -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,   188,
    -171,  -254,  -254,  -254,  -254,   166,  -254,  -254,  -254,  -254,
    -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,
    -254,  -254,  -254,  -254,  -254,  -254,  -254,  -254,    86,  -254,
      62,  -254,    67,  -254,  -253,  -254,  -210,  -254,  -254,    68,
    -254,  -254,  -254,   189,  -254,  -254,  -254,   272,  -184,   415,
    -254,   -86,  -254,  -254,  -254,  -254,  -254,   284,  -254,  -254,
    -254,   -85,  -254,  -254,   275,  -254,   -14,  -254,  -254,   -12,
     -11,   -75,   391,   302,   -80,  -254,  -254,    -8,   -79,  -254,
    -254,   -67,   -77,  -254,  -254,   -74,  -254,  -254,  -254,  -254,
     -76,  -254,   289,   292,  -254,   -81,  -254,   295,  -254,  -254,
    -254,  -254,   -72,  -254,  -254,  -254,   -73,  -254,  -254,  -254,
    -254,  -254,  -254,   496,  -254,  -254,  -254,  -254,   426,   -97,
    -254
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,    11,    17,    75,    93,    94,   132,    95,   134,
     135,   136,   181,   247,   204,   182,   206,   239,   260,   236,
     237,   270,   271,   272,   273,   274,   278,   316,   296,   335,
     250,   207,   241,   252,   263,   264,   284,   300,   328,   281,
     282,   321,   303,   341,   323,   324,   344,   385,   356,   357,
     367,   378,   368,   375,   358,   359,   360,   372,   373,   374,
     242,   254,   266,   267,   137,   138,   184,   176,   177,   178,
     224,    43,    44,    45,    46,    47,    48,   142,   143,   144,
     188,    49,    50,    51,    52,    53,   101,    55,    56,   102,
     114,   166,   116,   117,   118,   104,   119,   105,    60,    61,
      62,   106,    63,    64,    65,    66,    67,    68,   107,   213,
     168,   169,   170,   171,   172,   173,   157,   158,   159,   160,
     161,   162,   174,   231,   232,   233,    69,    70,    71,    72,
       3,     4,     6,     7,     8,    73,    74,    77,    78,    79,
     150
    };
  }

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
  private static final short[] yytable_ = yytable_init();
  private static final short[] yytable_init()
  {
    return new short[]
    {
     103,    54,   120,    57,    58,   103,   124,    59,   115,   122,
     129,   121,   127,   141,   145,   125,   163,   222,   123,   353,
     146,   110,   147,    96,    12,   148,   149,    91,   185,     5,
     286,   297,   298,   299,    92,   354,   186,   187,    23,    24,
      25,    26,    27,    28,    29,    30,   167,    31,    32,    33,
      34,    35,    36,    37,    38,    39,    40,   100,   111,   222,
     112,    76,   265,   113,     5,   248,   381,   334,   126,     1,
      81,   189,    81,   103,   108,     9,   202,   355,   221,    19,
     255,    14,   354,    10,   153,    54,   191,    57,    58,   154,
     248,    59,    23,    24,    25,    26,    15,   163,   108,   109,
     361,    31,    32,    33,    34,    35,    36,    37,    38,    39,
      40,   100,   376,   379,    18,   217,   225,   227,    20,   167,
     216,   215,   325,   228,   218,   229,   326,   327,   230,   234,
     130,   131,    16,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,   362,    31,    32,    33,    34,    35,    36,
      37,    38,    39,    40,   371,   377,   380,   199,    41,   382,
      42,   338,   200,   339,   256,   319,   340,   365,   366,   320,
      76,   389,    80,   390,   194,   195,   371,   275,   212,   276,
      57,    58,    83,    81,    59,    85,    84,    86,    54,   290,
      57,    58,    87,    88,    59,   311,   312,   313,   314,   315,
      89,    90,    98,    99,   110,   307,   126,   133,   111,   139,
     151,   152,   155,   310,   175,   156,   179,   183,   165,   164,
     180,   192,   190,   193,    20,   223,   197,   196,   330,   331,
     203,   198,   205,   140,   201,   235,   112,   336,   238,    21,
      22,    23,    24,    25,    26,    27,    28,    29,    30,   240,
      31,    32,    33,    34,    35,    36,    37,    38,    39,    40,
      20,   243,   244,   350,    41,   245,    42,   246,   249,   140,
     251,   253,   258,   113,   257,    21,    22,    23,    24,    25,
      26,    27,    28,    29,    30,   259,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,   110,   262,   265,   277,
      41,   279,    42,   280,   392,   283,   269,   285,   293,   288,
     291,   294,   295,    23,    24,    25,    26,    27,    28,    29,
      30,   301,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,   100,   111,   268,   112,   302,   317,   113,   305,
     304,   318,   308,   343,   309,   329,   342,   322,   351,   333,
     337,    23,    24,    25,    26,   289,   332,   345,   346,   347,
      31,    32,    33,    34,    35,    36,    37,    38,    39,    40,
     100,   348,    23,    24,    25,    26,   269,   391,   352,   349,
     364,    31,    32,    33,    34,    35,    36,    37,    38,    39,
      40,   100,   369,   370,    23,    24,    25,    26,   210,   354,
     384,   383,   386,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,   100,   365,    23,    24,    25,    26,    27,
      28,    29,    30,   393,    31,    32,    33,    34,    35,    36,
      37,    38,    39,    40,    23,    24,    25,    26,   261,   292,
     388,   363,   387,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,   100,   394,   287,   208,    82,   306,    23,
      24,    25,    26,    27,    28,    29,    30,   211,    31,    32,
      33,    34,    35,    36,    37,    38,    39,    40,   100,   111,
     128,   112,    23,    24,    25,    26,   226,   209,   220,   214,
     219,    31,    32,    33,    34,    35,    36,    37,    38,    39,
      40,   100,    13,    97
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      80,    15,    83,    15,    15,    85,    86,    15,    83,    84,
      90,    83,    88,    99,    99,    87,   113,   201,    85,    28,
      99,    44,    99,     6,     4,    99,    99,    49,    46,    44,
      16,    38,    39,    40,    56,    44,    54,    55,    61,    62,
      63,    64,    65,    66,    67,    68,   126,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,   243,
      83,    44,    48,    86,    44,   236,    28,    36,    91,     3,
      44,   151,    44,   153,    87,     0,    89,    86,    52,     5,
      52,    85,    44,     7,    87,    99,   153,    99,    99,    92,
     261,    99,    61,    62,    63,    64,    89,   194,    87,    88,
     353,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,   365,   366,    85,   196,   202,   202,    44,   199,
     196,   196,    37,   202,   196,   202,    41,    42,   202,   202,
      50,    51,    44,    59,    60,    61,    62,    63,    64,    65,
      66,    67,    68,   353,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,   364,   365,   366,    87,    84,   369,
      86,    38,    92,    40,   245,    37,    43,    29,    30,    41,
      44,   381,    91,   383,    87,    88,   386,   257,   192,   259,
     192,   192,    46,    44,   192,    86,    46,    46,   202,   269,
     202,   202,    46,    46,   202,    31,    32,    33,    34,    35,
      46,    91,    85,    89,    44,   285,    91,    57,    81,    85,
      47,    90,    44,   293,    92,    90,     8,    58,    88,    90,
       9,    69,    80,    44,    44,    45,    82,    46,   308,   309,
      44,    86,    11,    53,    85,    17,    83,   317,    44,    59,
      60,    61,    62,    63,    64,    65,    66,    67,    68,    13,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      44,    85,    88,   343,    84,    46,    86,    44,    20,    53,
      44,    15,    10,    86,    46,    59,    60,    61,    62,    63,
      64,    65,    66,    67,    68,    86,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    44,    30,    48,    18,
      84,    89,    86,    25,   384,    29,    86,    44,    47,    85,
      85,    89,    19,    61,    62,    63,    64,    65,    66,    67,
      68,    89,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    44,    83,    26,    46,    86,    85,
      89,    12,    87,    21,    87,    85,    14,    89,    89,    85,
      85,    61,    62,    63,    64,    44,    88,    88,    88,    85,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    85,    61,    62,    63,    64,    86,    44,    22,    85,
      23,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    27,    88,    61,    62,    63,    64,    44,    44,
      24,    27,    87,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    29,    61,    62,    63,    64,    65,
      66,    67,    68,    85,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    61,    62,    63,    64,   250,   273,
     378,   355,   375,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,   386,   266,   184,    42,    85,    61,
      62,    63,    64,    65,    66,    67,    68,   192,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      89,    83,    61,    62,    63,    64,   202,   185,   199,   194,
     198,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,     6,    77
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,    94,   223,   224,    44,   225,   226,   227,     0,
       7,    95,     4,   226,    85,    89,    44,    96,    85,     5,
      44,    59,    60,    61,    62,    63,    64,    65,    66,    67,
      68,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    84,    86,   164,   165,   166,   167,   168,   169,   174,
     175,   176,   177,   178,   179,   180,   181,   182,   183,   190,
     191,   192,   193,   195,   196,   197,   198,   199,   200,   219,
     220,   221,   222,   228,   229,    97,    44,   230,   231,   232,
      91,    44,   162,    46,    46,    86,    46,    46,    46,    46,
      91,    49,    56,    98,    99,   101,     6,   231,    85,    89,
      80,   179,   182,   187,   188,   190,   194,   201,    87,    88,
      44,    81,    83,    86,   183,   184,   185,   186,   187,   189,
     208,   215,   184,   194,   187,   215,    91,   203,   185,   187,
      50,    51,   100,    57,   102,   103,   104,   157,   158,    85,
      53,   164,   170,   171,   172,   174,   191,   195,   198,   219,
     233,    47,    90,    87,    92,    44,    90,   209,   210,   211,
     212,   213,   214,   232,    90,    88,   184,   187,   203,   204,
     205,   206,   207,   208,   215,    92,   160,   161,   162,     8,
       9,   105,   108,    58,   159,    46,    54,    55,   173,   187,
      80,   194,    69,    44,    87,    88,    46,    82,    86,    87,
      92,    85,    89,    44,   107,    11,   109,   124,   160,   186,
      44,   177,   179,   202,   210,   184,   203,   208,   215,   206,
     205,    52,   161,    45,   163,   164,   170,   174,   191,   195,
     198,   216,   217,   218,   219,    17,   112,   113,    44,   110,
      13,   125,   153,    85,    88,    46,    44,   106,   113,    20,
     123,    44,   126,    15,   154,    52,   208,    46,    10,    86,
     111,   112,    30,   127,   128,    48,   155,   156,    44,    86,
     114,   115,   116,   117,   118,   187,   187,    18,   119,    89,
      25,   132,   133,    29,   129,    44,    16,   156,    85,    44,
     187,    85,   118,    47,    89,    19,   121,    38,    39,    40,
     130,    89,    26,   135,    89,    85,    85,   187,    87,    87,
     187,    31,    32,    33,    34,    35,   120,    46,    12,    37,
      41,   134,    89,   137,   138,    37,    41,    42,   131,    85,
     187,   187,    88,    85,    36,   122,   187,    85,    38,    40,
      43,   136,    14,    21,   139,    88,    88,    85,    85,    85,
     187,    89,    22,    28,    44,    86,   141,   142,   147,   148,
     149,   147,   149,   141,    23,    29,    30,   143,   145,    27,
      88,   149,   150,   151,   152,   146,   147,   149,   144,   147,
     149,    28,   149,    27,    24,   140,    87,   145,   143,   149,
     149,    44,   187,    85,   152
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,    93,    94,    95,    96,    97,    97,    98,    99,    99,
     100,   100,   100,   101,   101,   102,   103,   104,   104,   106,
     105,   107,   108,   108,   109,   110,   111,   111,   112,   112,
     113,   114,   114,   115,   115,   116,   117,   117,   118,   118,
     119,   120,   120,   120,   120,   120,   121,   122,   122,   123,
     123,   124,   124,   125,   126,   127,   128,   128,   129,   129,
     130,   130,   130,   131,   131,   131,   132,   132,   133,   134,
     134,   135,   136,   136,   136,   137,   138,   138,   139,   140,
     140,   140,   141,   142,   142,   143,   143,   144,   144,   145,
     145,   146,   146,   147,   147,   147,   147,   148,   148,   149,
     150,   151,   151,   152,   152,   153,   153,   154,   155,   155,
     156,   156,   157,   158,   159,   159,   160,   160,   161,   161,
     162,   162,   163,   163,   163,   163,   163,   163,   163,   164,
     164,   165,   166,   166,   166,   167,   168,   169,   170,   170,
     171,   172,   172,   173,   173,   174,   174,   175,   176,   177,
     177,   177,   178,   178,   179,   179,   180,   180,   180,   180,
     181,   181,   181,   181,   182,   182,   183,   183,   183,   183,
     184,   184,   184,   184,   185,   186,   187,   187,   188,   188,
     188,   189,   190,   190,   190,   190,   191,   191,   192,   193,
     194,   195,   195,   196,   197,   198,   198,   199,   199,   200,
     201,   201,   202,   203,   204,   204,   205,   205,   206,   206,
     206,   206,   207,   208,   209,   209,   210,   210,   210,   210,
     211,   212,   213,   214,   215,   215,   216,   216,   217,   218,
     219,   219,   220,   220,   221,   222,   222,   223,   223,   224,
     225,   225,   226,   227,   228,   228,   228,   228,   228,   228,
     228,   229,   230,   230,   231,   232,   233,   233,   233,   233,
     233,   233,   233
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     2,     6,     1,     0,     2,     5,     1,     1,
       0,     1,     1,     0,     2,     1,     4,     0,     2,     0,
       5,     1,     0,     2,     7,     1,     0,     1,     1,     2,
       4,     2,     2,     1,     1,     1,     1,     2,     5,     5,
       4,     1,     1,     1,     1,     1,     4,     1,     1,     0,
       7,     0,     2,     7,     1,     3,     0,     3,     0,     3,
       1,     1,     1,     1,     1,     1,     0,     1,     4,     1,
       1,     4,     1,     1,     1,     1,     0,     2,     9,     0,
       2,     2,     2,     1,     1,     1,     3,     1,     1,     0,
       3,     1,     1,     2,     2,     1,     3,     3,     4,     1,
       1,     1,     3,     3,     1,     0,     2,     3,     1,     2,
       3,     4,     5,     1,     0,     1,     1,     3,     3,     3,
       1,     3,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     3,     3,     3,     1,     1,
       1,     2,     3,     1,     1,     1,     1,     1,     3,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     3,     1,     1,
       1,     3,     1,     1,     1,     1,     1,     1,     4,     3,
       3,     1,     1,     3,     3,     1,     1,     6,     6,     3,
       1,     3,     1,     3,     1,     3,     1,     1,     1,     1,
       1,     1,     4,     3,     1,     3,     1,     1,     1,     1,
       3,     3,     3,     3,     1,     3,     1,     1,     3,     1,
       1,     1,     1,     4,     3,     1,     1,     0,     1,     3,
       2,     3,     3,     1,     1,     1,     1,     1,     1,     1,
       1,     3,     2,     3,     3,     1,     1,     1,     1,     1,
       1,     1,     1
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 339;
    if (t <= 0)
      return SymbolKind.S_YYEOF;
    else if (t <= code_max)
      return SymbolKind.get(yytranslate_table_[t]);
    else
      return SymbolKind.S_YYUNDEF;
  }
  private static final byte[] yytranslate_table_ = yytranslate_table_init();
  private static final byte[] yytranslate_table_init()
  {
    return new byte[]
    {
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,    90,     2,     2,     2,     2,
      86,    88,     2,     2,    87,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    89,    85,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    91,     2,    92,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84
    };
  }


  private static final int YYLAST_ = 503;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 9;
  private static final int YYNTOKENS_ = 93;

/* Unqualified %code blocks.  */
/* "src/main/java/parser/Parser.y":82  */

    /**
     * Name of the current program/function block being parsed.
     * Used to scope identifiers in the symbol table.
     */
    private String programName;
    private AbstractSyntaxTree ast;

    public AbstractSyntaxTree getAST() {
        return ast;
    }

/* "src/main/java/parser/Parser.java":4331  */

}
/* "src/main/java/parser/Parser.y":2624  */

