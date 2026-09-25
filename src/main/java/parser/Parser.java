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
/* "src/main/java/parser/Parser.y":7  */

    import java.util.List;
    import java.util.ArrayList;
    import java.util.Collections;

    import utils.LexemeInfo;
    import utils.SymbolTable;
    import utils.enums.*;

    import parser.utils.*;
    import parser.internals.*;
    import parser.initializations.*;

/* "src/main/java/parser/Parser.java":59  */

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
    S_opt_other_var_declarations_list(99), /* opt_other_var_declarations_list  */
    S_other_var_declarations(100), /* other_var_declarations  */
    S_opt_function_block_body(101), /* opt_function_block_body  */
    S_opt_fuzzify_block_list(102), /* opt_fuzzify_block_list  */
    S_fuzzify_block(103),          /* fuzzify_block  */
    S_opt_defuzzify_block_list(104), /* opt_defuzzify_block_list  */
    S_defuzzify_block(105),        /* defuzzify_block  */
    S_opt_linguistic_term_list(106), /* opt_linguistic_term_list  */
    S_linguistic_term_list(107),   /* linguistic_term_list  */
    S_linguistic_term(108),        /* linguistic_term  */
    S_membership_function(109),    /* membership_function  */
    S_singleton(110),              /* singleton  */
    S_point_list(111),             /* point_list  */
    S_point(112),                  /* point  */
    S_defuzzification_method(113), /* defuzzification_method  */
    S_defuzz_method(114),          /* defuzz_method  */
    S_default_value(115),          /* default_value  */
    S_default_val(116),            /* default_val  */
    S_opt_range(117),              /* opt_range  */
    S_opt_rule_block_list(118),    /* opt_rule_block_list  */
    S_rule_block(119),             /* rule_block  */
    S_operator_definition(120),    /* operator_definition  */
    S_opt_operator_or(121),        /* opt_operator_or  */
    S_operator_and_opt(122),       /* operator_and_opt  */
    S_or_type(123),                /* or_type  */
    S_and_type(124),               /* and_type  */
    S_activation_method_opt(125),  /* activation_method_opt  */
    S_activation_method(126),      /* activation_method  */
    S_act_type(127),               /* act_type  */
    S_accumulation_method(128),    /* accumulation_method  */
    S_accu_type(129),              /* accu_type  */
    S_rule_list(130),              /* rule_list  */
    S_rule(131),                   /* rule  */
    S_opt_weighting(132),          /* opt_weighting  */
    S_condition(133),              /* condition  */
    S_condition_tail(134),         /* condition_tail  */
    S_x(135),                      /* x  */
    S_subcondition(136),           /* subcondition  */
    S_conclusion_list(137),        /* conclusion_list  */
    S_opt_option_block_list(138),  /* opt_option_block_list  */
    S_option_block(139),           /* option_block  */
    S_pragma_list(140),            /* pragma_list  */
    S_pragma(141),                 /* pragma  */
    S_io_var_decl(142),            /* io_var_decl  */
    S_var_declarations(143),       /* var_declarations  */
    S_var_id_decl(144),            /* var_id_decl  */
    S_var_retain_spec(145),        /* var_retain_spec  */
    S_var_constant_spec(146),      /* var_constant_spec  */
    S_var_init_decl_list(147),     /* var_init_decl_list  */
    S_var_init_decl(148),          /* var_init_decl  */
    S_var_spec_init(149),          /* var_spec_init  */
    S_boolean_spec_init(150),      /* boolean_spec_init  */
    S_boolean_specification(151),  /* boolean_specification  */
    S_initialized_boolean(152),    /* initialized_boolean  */
    S_edge(153),                   /* edge  */
    S_custom_spec_init(154),       /* custom_spec_init  */
    S_custom_specification(155),   /* custom_specification  */
    S_initialized_custom(156),     /* initialized_custom  */
    S_initialized_custom_with_constant(157), /* initialized_custom_with_constant  */
    S_initialized_custom_with_identifier(158), /* initialized_custom_with_identifier  */
    S_initialized_custom_with_structure(159), /* initialized_custom_with_structure  */
    S_custom_type_name(160),       /* custom_type_name  */
    S_simple_spec_init(161),       /* simple_spec_init  */
    S_simple_specification(162),   /* simple_specification  */
    S_initialized_simple(163),     /* initialized_simple  */
    S_elementary_type_name(164),   /* elementary_type_name  */
    S_numeric_type_name(165),      /* numeric_type_name  */
    S_integer_type_name(166),      /* integer_type_name  */
    S_signed_integer_type_name(167), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(168), /* unsigned_integer_type_name  */
    S_real_type_name(169),         /* real_type_name  */
    S_date_type_name(170),         /* date_type_name  */
    S_constant(171),               /* constant  */
    S_string_constant(172),        /* string_constant  */
    S_boolean_constant(173),       /* boolean_constant  */
    S_numeric_constant(174),       /* numeric_constant  */
    S_number_prefix(175),          /* number_prefix  */
    S_time_constant(176),          /* time_constant  */
    S_bit_string_type_name(177),   /* bit_string_type_name  */
    S_subrange_spec_init(178),     /* subrange_spec_init  */
    S_subrange_specification(179), /* subrange_specification  */
    S_subrange_type_decl(180),     /* subrange_type_decl  */
    S_initialized_subrange(181),   /* initialized_subrange  */
    S_range(182),                  /* range  */
    S_enumerated_spec_init(183),   /* enumerated_spec_init  */
    S_enumerated_specification(184), /* enumerated_specification  */
    S_initialized_enumerated(185), /* initialized_enumerated  */
    S_enumerated_values(186),      /* enumerated_values  */
    S_enumerated_values_list(187), /* enumerated_values_list  */
    S_array_spec_init(188),        /* array_spec_init  */
    S_array_specification(189),    /* array_specification  */
    S_initialized_array(190),      /* initialized_array  */
    S_range_list(191),             /* range_list  */
    S_non_generic_type_name(192),  /* non_generic_type_name  */
    S_array_initialization(193),   /* array_initialization  */
    S_array_init_open_square_bracket(194), /* array_init_open_square_bracket  */
    S_array_initial_elements_list(195), /* array_initial_elements_list  */
    S_array_initial_elements(196), /* array_initial_elements  */
    S_array_initial_element_routine(197), /* array_initial_element_routine  */
    S_array_initial_element(198),  /* array_initial_element  */
    S_repeated_initial_element(199), /* repeated_initial_element  */
    S_structure_initialization(200), /* structure_initialization  */
    S_struct_init_open_parenthesis(201), /* struct_init_open_parenthesis  */
    S_structure_field_initialization_list(202), /* structure_field_initialization_list  */
    S_initialized_structure_field(203), /* initialized_structure_field  */
    S_initialized_field_with_constant(204), /* initialized_field_with_constant  */
    S_initialized_field_with_identifier(205), /* initialized_field_with_identifier  */
    S_initialized_field_with_array(206), /* initialized_field_with_array  */
    S_initialized_field_with_structure(207), /* initialized_field_with_structure  */
    S_nested_field(208),           /* nested_field  */
    S_identifier_with_opt_mangling(209), /* identifier_with_opt_mangling  */
    S_standard_function_block_spec_init(210), /* standard_function_block_spec_init  */
    S_initialized_standard_function_block(211), /* initialized_standard_function_block  */
    S_identifier_list(212),        /* identifier_list  */
    S_standard_function_block_specification(213), /* standard_function_block_specification  */
    S_string_spec_init(214),       /* string_spec_init  */
    S_string_specification(215),   /* string_specification  */
    S_initialized_string(216),     /* initialized_string  */
    S_type_string_specification(217), /* type_string_specification  */
    S_opt_data_type_declaration(218), /* opt_data_type_declaration  */
    S_data_type_declaration(219),  /* data_type_declaration  */
    S_type_id_decl(220),           /* type_id_decl  */
    S_type_declaration_list(221),  /* type_declaration_list  */
    S_type_declaration(222),       /* type_declaration  */
    S_type_name_declaration(223),  /* type_name_declaration  */
    S_type_spec_init(224),         /* type_spec_init  */
    S_structure_specification(225), /* structure_specification  */
    S_structure_field_declaration_list(226), /* structure_field_declaration_list  */
    S_structure_field_declaration(227), /* structure_field_declaration  */
    S_field_name(228),             /* field_name  */
    S_structure_field_spec_init(229); /* structure_field_spec_init  */


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
      SymbolKind.S_opt_other_var_declarations_list,
      SymbolKind.S_other_var_declarations,
      SymbolKind.S_opt_function_block_body,
      SymbolKind.S_opt_fuzzify_block_list,
      SymbolKind.S_fuzzify_block,
      SymbolKind.S_opt_defuzzify_block_list,
      SymbolKind.S_defuzzify_block,
      SymbolKind.S_opt_linguistic_term_list,
      SymbolKind.S_linguistic_term_list,
      SymbolKind.S_linguistic_term,
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
      SymbolKind.S_operator_definition,
      SymbolKind.S_opt_operator_or,
      SymbolKind.S_operator_and_opt,
      SymbolKind.S_or_type,
      SymbolKind.S_and_type,
      SymbolKind.S_activation_method_opt,
      SymbolKind.S_activation_method,
      SymbolKind.S_act_type,
      SymbolKind.S_accumulation_method,
      SymbolKind.S_accu_type,
      SymbolKind.S_rule_list,
      SymbolKind.S_rule,
      SymbolKind.S_opt_weighting,
      SymbolKind.S_condition,
      SymbolKind.S_condition_tail,
      SymbolKind.S_x,
      SymbolKind.S_subcondition,
      SymbolKind.S_conclusion_list,
      SymbolKind.S_opt_option_block_list,
      SymbolKind.S_option_block,
      SymbolKind.S_pragma_list,
      SymbolKind.S_pragma,
      SymbolKind.S_io_var_decl,
      SymbolKind.S_var_declarations,
      SymbolKind.S_var_id_decl,
      SymbolKind.S_var_retain_spec,
      SymbolKind.S_var_constant_spec,
      SymbolKind.S_var_init_decl_list,
      SymbolKind.S_var_init_decl,
      SymbolKind.S_var_spec_init,
      SymbolKind.S_boolean_spec_init,
      SymbolKind.S_boolean_specification,
      SymbolKind.S_initialized_boolean,
      SymbolKind.S_edge,
      SymbolKind.S_custom_spec_init,
      SymbolKind.S_custom_specification,
      SymbolKind.S_initialized_custom,
      SymbolKind.S_initialized_custom_with_constant,
      SymbolKind.S_initialized_custom_with_identifier,
      SymbolKind.S_initialized_custom_with_structure,
      SymbolKind.S_custom_type_name,
      SymbolKind.S_simple_spec_init,
      SymbolKind.S_simple_specification,
      SymbolKind.S_initialized_simple,
      SymbolKind.S_elementary_type_name,
      SymbolKind.S_numeric_type_name,
      SymbolKind.S_integer_type_name,
      SymbolKind.S_signed_integer_type_name,
      SymbolKind.S_unsigned_integer_type_name,
      SymbolKind.S_real_type_name,
      SymbolKind.S_date_type_name,
      SymbolKind.S_constant,
      SymbolKind.S_string_constant,
      SymbolKind.S_boolean_constant,
      SymbolKind.S_numeric_constant,
      SymbolKind.S_number_prefix,
      SymbolKind.S_time_constant,
      SymbolKind.S_bit_string_type_name,
      SymbolKind.S_subrange_spec_init,
      SymbolKind.S_subrange_specification,
      SymbolKind.S_subrange_type_decl,
      SymbolKind.S_initialized_subrange,
      SymbolKind.S_range,
      SymbolKind.S_enumerated_spec_init,
      SymbolKind.S_enumerated_specification,
      SymbolKind.S_initialized_enumerated,
      SymbolKind.S_enumerated_values,
      SymbolKind.S_enumerated_values_list,
      SymbolKind.S_array_spec_init,
      SymbolKind.S_array_specification,
      SymbolKind.S_initialized_array,
      SymbolKind.S_range_list,
      SymbolKind.S_non_generic_type_name,
      SymbolKind.S_array_initialization,
      SymbolKind.S_array_init_open_square_bracket,
      SymbolKind.S_array_initial_elements_list,
      SymbolKind.S_array_initial_elements,
      SymbolKind.S_array_initial_element_routine,
      SymbolKind.S_array_initial_element,
      SymbolKind.S_repeated_initial_element,
      SymbolKind.S_structure_initialization,
      SymbolKind.S_struct_init_open_parenthesis,
      SymbolKind.S_structure_field_initialization_list,
      SymbolKind.S_initialized_structure_field,
      SymbolKind.S_initialized_field_with_constant,
      SymbolKind.S_initialized_field_with_identifier,
      SymbolKind.S_initialized_field_with_array,
      SymbolKind.S_initialized_field_with_structure,
      SymbolKind.S_nested_field,
      SymbolKind.S_identifier_with_opt_mangling,
      SymbolKind.S_standard_function_block_spec_init,
      SymbolKind.S_initialized_standard_function_block,
      SymbolKind.S_identifier_list,
      SymbolKind.S_standard_function_block_specification,
      SymbolKind.S_string_spec_init,
      SymbolKind.S_string_specification,
      SymbolKind.S_initialized_string,
      SymbolKind.S_type_string_specification,
      SymbolKind.S_opt_data_type_declaration,
      SymbolKind.S_data_type_declaration,
      SymbolKind.S_type_id_decl,
      SymbolKind.S_type_declaration_list,
      SymbolKind.S_type_declaration,
      SymbolKind.S_type_name_declaration,
      SymbolKind.S_type_spec_init,
      SymbolKind.S_structure_specification,
      SymbolKind.S_structure_field_declaration_list,
      SymbolKind.S_structure_field_declaration,
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
  "fb_io_var_declarations", "opt_other_var_declarations_list",
  "other_var_declarations", "opt_function_block_body",
  "opt_fuzzify_block_list", "fuzzify_block", "opt_defuzzify_block_list",
  "defuzzify_block", "opt_linguistic_term_list", "linguistic_term_list",
  "linguistic_term", "membership_function", "singleton", "point_list",
  "point", "defuzzification_method", "defuzz_method", "default_value",
  "default_val", "opt_range", "opt_rule_block_list", "rule_block",
  "operator_definition", "opt_operator_or", "operator_and_opt", "or_type",
  "and_type", "activation_method_opt", "activation_method", "act_type",
  "accumulation_method", "accu_type", "rule_list", "rule", "opt_weighting",
  "condition", "condition_tail", "x", "subcondition", "conclusion_list",
  "opt_option_block_list", "option_block", "pragma_list", "pragma",
  "io_var_decl", "var_declarations", "var_id_decl", "var_retain_spec",
  "var_constant_spec", "var_init_decl_list", "var_init_decl",
  "var_spec_init", "boolean_spec_init", "boolean_specification",
  "initialized_boolean", "edge", "custom_spec_init",
  "custom_specification", "initialized_custom",
  "initialized_custom_with_constant", "initialized_custom_with_identifier",
  "initialized_custom_with_structure", "custom_type_name",
  "simple_spec_init", "simple_specification", "initialized_simple",
  "elementary_type_name", "numeric_type_name", "integer_type_name",
  "signed_integer_type_name", "unsigned_integer_type_name",
  "real_type_name", "date_type_name", "constant", "string_constant",
  "boolean_constant", "numeric_constant", "number_prefix", "time_constant",
  "bit_string_type_name", "subrange_spec_init", "subrange_specification",
  "subrange_type_decl", "initialized_subrange", "range",
  "enumerated_spec_init", "enumerated_specification",
  "initialized_enumerated", "enumerated_values", "enumerated_values_list",
  "array_spec_init", "array_specification", "initialized_array",
  "range_list", "non_generic_type_name", "array_initialization",
  "array_init_open_square_bracket", "array_initial_elements_list",
  "array_initial_elements", "array_initial_element_routine",
  "array_initial_element", "repeated_initial_element",
  "structure_initialization", "struct_init_open_parenthesis",
  "structure_field_initialization_list", "initialized_structure_field",
  "initialized_field_with_constant", "initialized_field_with_identifier",
  "initialized_field_with_array", "initialized_field_with_structure",
  "nested_field", "identifier_with_opt_mangling",
  "standard_function_block_spec_init",
  "initialized_standard_function_block", "identifier_list",
  "standard_function_block_specification", "string_spec_init",
  "string_specification", "initialized_string",
  "type_string_specification", "opt_data_type_declaration",
  "data_type_declaration", "type_id_decl", "type_declaration_list",
  "type_declaration", "type_name_declaration", "type_spec_init",
  "structure_specification", "structure_field_declaration_list",
  "structure_field_declaration", "field_name", "structure_field_spec_init", null
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
/* "%code init" blocks.  */
/* "src/main/java/parser/Parser.y":27  */

    this.contexts = new ContextHandler();

/* "src/main/java/parser/Parser.java":902  */

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
          case 3: /* function_block_declaration: FUNCTION_BLOCK function_block_name opt_fb_io_var_declarations_list opt_other_var_declarations_list opt_function_block_body END_FUNCTION_BLOCK  */
  if (yyn == 3)
    /* "src/main/java/parser/Parser.y":101  */
    {
        /**
         * Pops the function block context
        **/

        this.contexts.pop();
    };
  break;


  case 4: /* function_block_name: IDENTIFIER  */
  if (yyn == 4)
    /* "src/main/java/parser/Parser.y":112  */
    {
        /**
         * Builds a new context and adds the function block name to the outer scope
        **/

        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.outerScopes().addScope(((String)(yystack.valueAt (0))));

        this.contexts.add(ctx);
    };
  break;


  case 95: /* io_var_decl: VAR_INPUT  */
  if (yyn == 95)
    /* "src/main/java/parser/Parser.y":359  */
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.IN).use(Use.VARIABLE);
    };
  break;


  case 96: /* io_var_decl: VAR_OUTPUT  */
  if (yyn == 96)
    /* "src/main/java/parser/Parser.y":368  */
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.OUT).use(Use.VARIABLE);
    };
  break;


  case 98: /* var_id_decl: VAR  */
  if (yyn == 98)
    /* "src/main/java/parser/Parser.y":384  */
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.INTERNAL).use(Use.VARIABLE);
    };
  break;


  case 100: /* var_retain_spec: RETAIN  */
  if (yyn == 100)
    /* "src/main/java/parser/Parser.y":397  */
    {
        // TODO: ctx with retain spec
    };
  break;


  case 101: /* var_retain_spec: NON_RETAIN  */
  if (yyn == 101)
    /* "src/main/java/parser/Parser.y":401  */
    {
        // TODO: ctx with non retain spec
    };
  break;


  case 102: /* var_constant_spec: %empty  */
  if (yyn == 102)
    /* "src/main/java/parser/Parser.y":408  */
    {
        // TODO: ctx with non contant spec
    };
  break;


  case 103: /* var_constant_spec: CONSTANT  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":412  */
    {
        // TODO: ctx with contant spec
    };
  break;


  case 106: /* var_init_decl: identifier_list ':' var_spec_init  */
  if (yyn == 106)
    /* "src/main/java/parser/Parser.y":424  */
    {
        /**
         * Publishes the variables into the symbol table using the loaded context
        **/

        ParsingContext ctx = this.contexts.current();
        Publisher.publish(ctx);

        ctx.declaredIdentifiers().clear();
    };
  break;


  case 117: /* boolean_specification: BOOL  */
  if (yyn == 117)
    /* "src/main/java/parser/Parser.y":455  */
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
    };
  break;


  case 123: /* custom_specification: IDENTIFIER  */
  if (yyn == 123)
    /* "src/main/java/parser/Parser.y":486  */
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (0))))
            .initialValue(this.symbolTable.get(((String)(yystack.valueAt (0)))).initialValue);
    };
  break;


  case 127: /* initialized_custom_with_constant: custom_type_name ASSIGN_OP constant  */
  if (yyn == 127)
    /* "src/main/java/parser/Parser.y":508  */
    {
        /**
         * Adds to the context the left identifiers initial value and drops the search scope added
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
        ctx.searchScope().popScope();
    };
  break;


  case 128: /* initialized_custom_with_identifier: custom_type_name ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 128)
    /* "src/main/java/parser/Parser.y":521  */
    {
        /**
         * Searches if the enumerated value is valid and in that cases loads the initial value and and drops the search
         * scope added
        **/

        ParsingContext ctx = this.contexts.current();

        int octothorpeIdx = ((String)(yystack.valueAt (0))).indexOf('#');
        String completeTypeName = (octothorpeIdx != -1)
            ? ((String)(yystack.valueAt (0))) // Has already name mangling
            : ctx.searchScope().getNameMangled(((String)(yystack.valueAt (0))));

        if (this.symbolTable.get(completeTypeName) == null) {
            // TODO: control de error. Enumerado literal inexistente
        }
        ctx.metadataBuilder().initialValue(new VariableInitialization(completeTypeName));
        ctx.searchScope().popScope();
    };
  break;


  case 129: /* initialized_custom_with_structure: custom_type_name ASSIGN_OP structure_initialization  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":544  */
    {
        /**
         * Drops the search scope after reducing the assignment
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.searchScope().popScope();
    };
  break;


  case 130: /* custom_type_name: IDENTIFIER  */
  if (yyn == 130)
    /* "src/main/java/parser/Parser.y":556  */
    {
        /**
         * Builds the LexemeInfo associated to the identifier and appends the underlying scope to the search scope.
         *
         * If an identifier is used and it's subtype is custom, then it's hiding information. That's why we search for
         * the underlying scope, so that we can look it up later.
        **/

        LexemeInfo metadata = this.symbolTable.get(((String)(yystack.valueAt (0))));

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (0))))
            .initialValue(metadata.initialValue);

        String underlyingScope = UnderlyingScopeSearcher.search(this.symbolTable, ((String)(yystack.valueAt (0))));
        ctx.searchScope().addScope(underlyingScope);
    };
  break;


  case 133: /* simple_specification: elementary_type_name  */
  if (yyn == 133)
    /* "src/main/java/parser/Parser.y":585  */
    {
        /**
         * Loads the left identifiers type and subtype
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(((Subtype)(yystack.valueAt (0))))
            .initialValue(
                Factory.createPrimitiveInitialization(this.symbolTable, ((Subtype)(yystack.valueAt (0))))
            );
    };
  break;


  case 134: /* initialized_simple: elementary_type_name ASSIGN_OP constant  */
  if (yyn == 134)
    /* "src/main/java/parser/Parser.y":602  */
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(((Subtype)(yystack.valueAt (2))))
            .initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
    };
  break;


  case 135: /* elementary_type_name: numeric_type_name  */
  if (yyn == 135)
    /* "src/main/java/parser/Parser.y":618  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 136: /* elementary_type_name: date_type_name  */
  if (yyn == 136)
    /* "src/main/java/parser/Parser.y":619  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 137: /* elementary_type_name: bit_string_type_name  */
  if (yyn == 137)
    /* "src/main/java/parser/Parser.y":620  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 138: /* numeric_type_name: integer_type_name  */
  if (yyn == 138)
    /* "src/main/java/parser/Parser.y":624  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 139: /* numeric_type_name: real_type_name  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":625  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 140: /* integer_type_name: signed_integer_type_name  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":629  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 141: /* integer_type_name: unsigned_integer_type_name  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":630  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 142: /* signed_integer_type_name: SINT  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":634  */
           { yyval = Subtype.SINT; };
  break;


  case 143: /* signed_integer_type_name: INT  */
  if (yyn == 143)
    /* "src/main/java/parser/Parser.y":635  */
           { yyval = Subtype.INT;  };
  break;


  case 144: /* signed_integer_type_name: DINT  */
  if (yyn == 144)
    /* "src/main/java/parser/Parser.y":636  */
           { yyval = Subtype.DINT; };
  break;


  case 145: /* signed_integer_type_name: LINT  */
  if (yyn == 145)
    /* "src/main/java/parser/Parser.y":637  */
           { yyval = Subtype.LINT; };
  break;


  case 146: /* unsigned_integer_type_name: USINT  */
  if (yyn == 146)
    /* "src/main/java/parser/Parser.y":641  */
            { yyval = Subtype.USINT; };
  break;


  case 147: /* unsigned_integer_type_name: UINT  */
  if (yyn == 147)
    /* "src/main/java/parser/Parser.y":642  */
            { yyval = Subtype.UINT;  };
  break;


  case 148: /* unsigned_integer_type_name: UDINT  */
  if (yyn == 148)
    /* "src/main/java/parser/Parser.y":643  */
            { yyval = Subtype.UDINT; };
  break;


  case 149: /* unsigned_integer_type_name: ULINT  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":644  */
            { yyval = Subtype.ULINT; };
  break;


  case 150: /* real_type_name: REAL  */
  if (yyn == 150)
    /* "src/main/java/parser/Parser.y":648  */
            { yyval = Subtype.REAL;  };
  break;


  case 151: /* real_type_name: LREAL  */
  if (yyn == 151)
    /* "src/main/java/parser/Parser.y":649  */
            { yyval = Subtype.LREAL; };
  break;


  case 152: /* date_type_name: TIME  */
  if (yyn == 152)
    /* "src/main/java/parser/Parser.y":653  */
                    { yyval = Subtype.TIME;          };
  break;


  case 153: /* date_type_name: DATE  */
  if (yyn == 153)
    /* "src/main/java/parser/Parser.y":654  */
                    { yyval = Subtype.DATE;          };
  break;


  case 154: /* date_type_name: TIME_OF_DAY  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":655  */
                    { yyval = Subtype.TIME_OF_DAY;   };
  break;


  case 155: /* date_type_name: DATE_AND_TIME  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":656  */
                    { yyval = Subtype.DATE_AND_TIME; };
  break;


  case 156: /* constant: string_constant  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":662  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 157: /* constant: boolean_constant  */
  if (yyn == 157)
    /* "src/main/java/parser/Parser.y":663  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 158: /* constant: time_constant  */
  if (yyn == 158)
    /* "src/main/java/parser/Parser.y":664  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 159: /* constant: numeric_constant  */
  if (yyn == 159)
    /* "src/main/java/parser/Parser.y":665  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 160: /* string_constant: STRING_LITERAL  */
  if (yyn == 160)
    /* "src/main/java/parser/Parser.y":669  */
                   { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 161: /* boolean_constant: BOOLEAN_LITERAL  */
  if (yyn == 161)
    /* "src/main/java/parser/Parser.y":673  */
                    { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 163: /* numeric_constant: number_prefix NUMERIC_LITERAL  */
  if (yyn == 163)
    /* "src/main/java/parser/Parser.y":678  */
    {
        // TODO: hacer conversion de esta constante en codigo
        yyval = "";
    };
  break;


  case 167: /* time_constant: date_type_name '#' TIME_LITERAL  */
  if (yyn == 167)
    /* "src/main/java/parser/Parser.y":691  */
                                    {
        // TODO: accion semantica que verifica que prefix es del mismo tipo que time_literal
    };
  break;


  case 168: /* bit_string_type_name: BYTE  */
  if (yyn == 168)
    /* "src/main/java/parser/Parser.y":697  */
            { yyval = Subtype.BYTE;  };
  break;


  case 169: /* bit_string_type_name: WORD  */
  if (yyn == 169)
    /* "src/main/java/parser/Parser.y":698  */
            { yyval = Subtype.WORD;  };
  break;


  case 170: /* bit_string_type_name: DWORD  */
  if (yyn == 170)
    /* "src/main/java/parser/Parser.y":699  */
            { yyval = Subtype.DWORD; };
  break;


  case 171: /* bit_string_type_name: LWORD  */
  if (yyn == 171)
    /* "src/main/java/parser/Parser.y":700  */
            { yyval = Subtype.LWORD; };
  break;


  case 174: /* subrange_specification: subrange_type_decl '(' range ')'  */
  if (yyn == 174)
    /* "src/main/java/parser/Parser.y":712  */
    {
        ParsingContext ctx = this.contexts.current();
        LexemeInfo metadata = ctx.metadataBuilder().build();
        String ilimit = metadata.inferiorLimits.get(0);
        String slimit = metadata.superiorLimits.get(0);

        ctx.metadataBuilder()
            .initialValue(new SubrangeInitialization(ilimit, slimit));
    };
  break;


  case 175: /* subrange_type_decl: integer_type_name  */
  if (yyn == 175)
    /* "src/main/java/parser/Parser.y":725  */
    {
        /**
         * Loads the left identifiers subrange subtype
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().type(Type.SUBRANGE).subtype(((Subtype)(yystack.valueAt (0))));
    };
  break;


  case 176: /* initialized_subrange: subrange_specification ASSIGN_OP numeric_constant  */
  if (yyn == 176)
    /* "src/main/java/parser/Parser.y":737  */
    {
        /**
         * Loads the left identifiers subrange initialization
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 177: /* range: numeric_constant RANGE_OP numeric_constant  */
  if (yyn == 177)
    /* "src/main/java/parser/Parser.y":749  */
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

        // TODO: hacer chequeo semantico de rangos
        metadata.inferiorLimits.add(((String)(yystack.valueAt (2))));
        metadata.superiorLimits.add(((String)(yystack.valueAt (0))));

        ctx.metadataBuilder()
            .inferiorLimits(metadata.inferiorLimits)
            .superiorLimits(metadata.superiorLimits);
    };
  break;


  case 180: /* enumerated_specification: '(' enumerated_values ')'  */
  if (yyn == 180)
    /* "src/main/java/parser/Parser.y":778  */
                              {
        /**
         * Loads the enumerated metadata to the current context.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.ENUMERATE)
            .subtype(Subtype.INT)
            .parameters(((List<String>)(yystack.valueAt (1))))
            .initialValue(
                new EnumeratedInitialization(((List<String>)(yystack.valueAt (1))))
            );
    };
  break;


  case 181: /* initialized_enumerated: enumerated_specification ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 181)
    /* "src/main/java/parser/Parser.y":796  */
    {
        /**
         * Verifies that the initializer value is correct and loads it to the context.
        **/

        ParsingContext ctx = this.contexts.current();

        if (((String)(yystack.valueAt (0))).indexOf('#') != -1) {
            // TODO: control de error. El enumerado anonimo no puede ser inicializado con un mangling
        }
        List<String> enumeratedValues = ctx.metadataBuilder().build().parameters;
        Integer indexValue = enumeratedValues.indexOf(((String)(yystack.valueAt (0))));
        if (indexValue == -1) {
            // TODO: control de error. El enumerado anonimo no puede ser inicializado con un valor inexistente
        }
        // TODO: ver cómo verificar que el índice está en el léxico
        ctx.metadataBuilder().initialValue(
            new VariableInitialization(
                ctx.outerScopes().getNameMangled(((String)(yystack.valueAt (0))))
            )
        );
    };
  break;


  case 182: /* enumerated_values: enumerated_values_list  */
  if (yyn == 182)
    /* "src/main/java/parser/Parser.y":822  */
    {
        /**
         * Just drops the context built for the enumerated list.
        **/

        this.contexts.pop();
    };
  break;


  case 183: /* enumerated_values_list: IDENTIFIER  */
  if (yyn == 183)
    /* "src/main/java/parser/Parser.y":833  */
    {
        /**
         * Creates a new context with the data associated to the enums value list and then publish the identifier found
         * to the symbol table.
        **/

        ParsingContext oldCtx = this.contexts.current();
        String outerScopes = oldCtx.outerScopes().getCurrentScope();

        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.NONE)
            .use(Use.MACRO)
            .source(Source.NONE)
            .initialValue(
                // TODO: ver interacción con lexer para la publicación de la constante
                new MacroInitialization(this.symbolTable, "0")
            );
        ctx.outerScopes().addScope(outerScopes);

        Publisher.publish(ctx);
        this.contexts.add(ctx);

        List<String> enumeratedValues = new ArrayList<>();
        enumeratedValues.add(((String)(yystack.valueAt (0))));

        yyval = enumeratedValues;
    };
  break;


  case 184: /* enumerated_values_list: enumerated_values_list ',' IDENTIFIER  */
  if (yyn == 184)
    /* "src/main/java/parser/Parser.y":864  */
    {
        // TODO: ver interacción con el lexer nuevamente
        Integer newIndex = ((List<String>)(yystack.valueAt (2))).size();

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().set(0, ((String)(yystack.valueAt (0))));
        ctx.metadataBuilder().initialValue(
            new MacroInitialization(this.symbolTable, newIndex.toString())
        );

        Publisher.publish(ctx);

        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 187: /* array_specification: ARRAY '[' range_list ']' OF IDENTIFIER  */
  if (yyn == 187)
    /* "src/main/java/parser/Parser.y":888  */
    {
        LexemeInfo typeMetadata = this.symbolTable.get(((String)(yystack.valueAt (0))));
        ParsingContext ctx = this.contexts.current();

        int dimension= DimensionCalculator.calculate(ctx);
        ctx.metadataBuilder()
            .type(Type.ARRAY)
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (0))))
            .initialValue(
                new RepeatedInitialization(dimension, (Initialization) typeMetadata.initialValue)
            );

        String underlyingScope = UnderlyingScopeSearcher.search(this.symbolTable, ((String)(yystack.valueAt (0))));
        ctx.searchScope().addScope(underlyingScope);
    };
  break;


  case 188: /* array_specification: ARRAY '[' range_list ']' OF non_generic_type_name  */
  if (yyn == 188)
    /* "src/main/java/parser/Parser.y":905  */
    {
        ParsingContext ctx = this.contexts.current();
        int dimension= DimensionCalculator.calculate(ctx);
        Initialization defaultInit = Factory.createPrimitiveInitialization(this.symbolTable, ((Subtype)(yystack.valueAt (0))));

        ctx.metadataBuilder()
            .type(Type.ARRAY)
            .subtype(((Subtype)(yystack.valueAt (0))))
            .initialValue(
                new RepeatedInitialization(dimension, defaultInit)
            );
    };
  break;


  case 193: /* array_initialization: array_init_open_square_bracket array_initial_elements_list ']'  */
  if (yyn == 193)
    /* "src/main/java/parser/Parser.y":934  */
    {
        /**
         * Drops the useless context generated at the end of the initialization list.
        **/

        this.contexts.pop();
    };
  break;


  case 194: /* array_init_open_square_bracket: '['  */
  if (yyn == 194)
    /* "src/main/java/parser/Parser.y":945  */
    {
        /**
         * Creates a new context so that it can be overwritten by the inner rules.
        **/

        ParsingContext oldCtx = this.contexts.current();
        ParsingContext newCtx = new ParsingContext(this.symbolTable);

        LexemeInfo oldCtxMetadata = oldCtx.metadataBuilder().build();

        // Copies the valuable info of the current (old) context
        newCtx.metadataBuilder()
            .subtype(oldCtxMetadata.subtype)
            .customType(oldCtxMetadata.customType);
        newCtx.searchScope().addScope(oldCtx.searchScope().getCurrentScope());

        this.contexts.add(newCtx);
    };
  break;


  case 197: /* array_initial_elements: array_initial_element_routine  */
  if (yyn == 197)
    /* "src/main/java/parser/Parser.y":972  */
    {
        /**
         * Retrieves the relevant information of the initialization and copies it into the current context
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

        // Set the index to zero to avoid creating a new empty context
        initContext.incrementIndex(-initContext.index());
        this.contexts.add(initContext);
    };
  break;


  case 200: /* array_initial_element: constant  */
  if (yyn == 200)
    /* "src/main/java/parser/Parser.y":1007  */
    {
        /**
         * Creates the initialization and adds the context counter by one
        **/

        ParsingContext initContext = this.contexts.current();
        initContext.incrementIndex(1);
        initContext.metadataBuilder().initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
    };
  break;


  case 201: /* array_initial_element: identifier_with_opt_mangling  */
  if (yyn == 201)
    /* "src/main/java/parser/Parser.y":1017  */
    {
        /**
         * Creates the initialization and adds the context counter by one
        **/

        ParsingContext initContext = this.contexts.current();
        initContext.incrementIndex(1);

        // TODO: verificar enumerado como en la regla de initialized_custom_with_identifier
        String completeEnumerateName = initContext.searchScope().getNameMangled(((String)(yystack.valueAt (0))));

        initContext.metadataBuilder().initialValue(new VariableInitialization(completeEnumerateName));
    };
  break;


  case 202: /* array_initial_element: structure_initialization  */
  if (yyn == 202)
    /* "src/main/java/parser/Parser.y":1031  */
    {
        /**
         * Only adds the context counter by one because the initialization is created inside the rule.
        **/

        ParsingContext initContext = this.contexts.current();
        initContext.incrementIndex(1);
    };
  break;


  case 203: /* array_initial_element: array_initialization  */
  if (yyn == 203)
    /* "src/main/java/parser/Parser.y":1040  */
    {
        /**
         * Only adds the context counter by one because the initialization is created inside the rule.
        **/

        ParsingContext initContext = this.contexts.current();
        initContext.incrementIndex(1);
    };
  break;


  case 204: /* repeated_initial_element: numeric_constant '(' array_initial_element ')'  */
  if (yyn == 204)
    /* "src/main/java/parser/Parser.y":1052  */
    {
        /**
         * Repeats N times the inner initialization.
        **/

        // TODO: verificar que numeric_constant es aditivo
        int multiplier = Integer.parseInt(((String)(yystack.valueAt (3))));

        ParsingContext initContext = this.contexts.current();
        initContext.incrementIndex(multiplier * initContext.index() - 1);
    };
  break;


  case 206: /* struct_init_open_parenthesis: '('  */
  if (yyn == 206)
    /* "src/main/java/parser/Parser.y":1071  */
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
    };
  break;


  case 213: /* initialized_field_with_constant: nested_field ASSIGN_OP constant  */
  if (yyn == 213)
    /* "src/main/java/parser/Parser.y":1104  */
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
        structValue.setFieldInitialization(completeFieldName, new VariableInitialization(((String)(yystack.valueAt (0)))));
        ctx.nestedFields().popScope();
    };
  break;


  case 214: /* initialized_field_with_identifier: nested_field ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 214)
    /* "src/main/java/parser/Parser.y":1123  */
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
        String completeEnumeratedValue = ctx.searchScope().getNameMangled(((String)(yystack.valueAt (0))));

        structValue.setFieldInitialization(completeFieldName, new VariableInitialization(completeEnumeratedValue));
        ctx.nestedFields().popScope();
    };
  break;


  case 215: /* initialized_field_with_array: nested_field ASSIGN_OP array_initialization  */
  if (yyn == 215)
    /* "src/main/java/parser/Parser.y":1145  */
    {
        /**
         * Overwrites the field with an array initialization.
        **/
        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().popScope();
    };
  break;


  case 216: /* initialized_field_with_structure: nested_field ASSIGN_OP structure_initialization  */
  if (yyn == 216)
    /* "src/main/java/parser/Parser.y":1156  */
    {
        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().popScope();
    };
  break;


  case 217: /* nested_field: IDENTIFIER  */
  if (yyn == 217)
    /* "src/main/java/parser/Parser.y":1164  */
    {
        /**
         * Needs to expand the nested scope to keep overwritting.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().addScope(((String)(yystack.valueAt (0))));
    };
  break;


  case 218: /* identifier_with_opt_mangling: IDENTIFIER  */
  if (yyn == 218)
    /* "src/main/java/parser/Parser.y":1176  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 219: /* identifier_with_opt_mangling: IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 219)
    /* "src/main/java/parser/Parser.y":1180  */
    {
        yyval = ((String)(yystack.valueAt (2))) + "#" + ((String)(yystack.valueAt (0)));
    };
  break;


  case 223: /* identifier_list: IDENTIFIER  */
  if (yyn == 223)
    /* "src/main/java/parser/Parser.y":1196  */
    {
        ParsingContext ctx = contexts.current();
        ctx.declaredIdentifiers().clear();
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
    };
  break;


  case 224: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 224)
    /* "src/main/java/parser/Parser.y":1202  */
    {
        ParsingContext ctx = contexts.current();
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
    };
  break;


  case 236: /* data_type_declaration: type_id_decl type_declaration_list END_TYPE  */
  if (yyn == 236)
    /* "src/main/java/parser/Parser.y":1240  */
    {
        /**
         * After reducing the whole block, the type block context needs to popped
        **/

        this.contexts.pop();
    };
  break;


  case 237: /* type_id_decl: TYPE  */
  if (yyn == 237)
    /* "src/main/java/parser/Parser.y":1251  */
    {
        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.metadataBuilder()
            .use(Use.TYPE)
            .source(Source.NONE);

        this.contexts.add(ctx);
    };
  break;


  case 240: /* type_declaration: type_name_declaration ':' type_spec_init  */
  if (yyn == 240)
    /* "src/main/java/parser/Parser.y":1268  */
    {
        /**
         * The type declaration is published after being reduced and it's outerScopes unappended.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.outerScopes().popScope();
        Publisher.publish(ctx);

        ctx.declaredIdentifiers().clear();
    };
  break;


  case 241: /* type_name_declaration: IDENTIFIER  */
  if (yyn == 241)
    /* "src/main/java/parser/Parser.y":1283  */
    {
        /**
         * Adds the identifier as the current scope so that everything declared inside this scope belongs to the outer
         * scope.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        ctx.outerScopes().addScope(((String)(yystack.valueAt (0))));
    };
  break;


  case 249: /* structure_specification: STRUCT structure_field_declaration_list END_STRUCT  */
  if (yyn == 249)
    /* "src/main/java/parser/Parser.y":1307  */
    {
        /**
         * Builds the declaration of the structure and publishes it to the symbol table. For that to happen every field
         * needs to be searched to get their initial values and appended to the structure initial value.
        **/

        ParsingContext ctx = this.contexts.current();

        List<String> structParameters = ((List<String>)(yystack.valueAt (1)));
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
    };
  break;


  case 250: /* structure_field_declaration_list: structure_field_declaration ';'  */
  if (yyn == 250)
    /* "src/main/java/parser/Parser.y":1334  */
    {
        List<String> structParameters = new ArrayList<>();
        structParameters.add(((String)(yystack.valueAt (1))));
        yyval = structParameters;
    };
  break;


  case 251: /* structure_field_declaration_list: structure_field_declaration_list structure_field_declaration ';'  */
  if (yyn == 251)
    /* "src/main/java/parser/Parser.y":1340  */
    {
        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (1))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 252: /* structure_field_declaration: field_name ':' structure_field_spec_init  */
  if (yyn == 252)
    /* "src/main/java/parser/Parser.y":1348  */
    {
        /**
         * Publishes the field to the symbol table.
        **/

        ParsingContext ctx = this.contexts.current();
        Publisher.publish(ctx);

        this.contexts.pop();
        yyval = ((String)(yystack.valueAt (2)));
    };
  break;


  case 253: /* field_name: IDENTIFIER  */
  if (yyn == 253)
    /* "src/main/java/parser/Parser.y":1363  */
    {
        /**
         * Builds the new field context
        **/

        ParsingContext ctx = this.contexts.current();
        String outerScopes = ctx.outerScopes().getCurrentScope();

        ctx = new ParsingContext(this.symbolTable);
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        ctx.metadataBuilder().use(Use.FIELD).source(Source.NONE);

        // Copies the outer scope from before
        ctx.outerScopes().addScope(outerScopes);

        this.contexts.add(ctx);
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;



/* "src/main/java/parser/Parser.java":2238  */

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

  private static final short yypact_ninf_ = -328;
  private static final short yytable_ninf_ = -176;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      28,  -328,    39,    45,  -328,    11,  -328,    49,  -328,  -328,
      23,    -8,    19,  -328,  -328,  -328,    13,  -328,    77,    24,
    -328,    86,    88,  -328,  -328,  -328,  -328,  -328,  -328,  -328,
    -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,
    -328,  -328,  -328,    55,   118,  -328,  -328,  -328,  -328,  -328,
    -328,   146,  -328,  -328,  -328,   150,  -328,    42,  -328,  -328,
    -328,  -328,  -328,  -328,   156,    91,  -328,  -328,   157,  -328,
    -328,   158,  -328,  -328,  -328,  -328,   -22,  -328,  -328,  -328,
    -328,  -328,   149,   114,  -328,    26,   122,   119,   441,  -328,
     121,   123,   319,   418,   441,   441,   167,   124,   133,   441,
    -328,  -328,   204,   207,  -328,   159,  -328,  -328,   174,  -328,
     134,  -328,   283,  -328,   130,   132,   176,   145,   136,   137,
     135,  -328,   186,   141,  -328,  -328,  -328,   142,  -328,  -328,
    -328,  -328,  -328,  -328,   189,  -328,  -328,  -328,   147,  -328,
    -328,  -328,   177,  -328,   169,  -328,   190,  -328,   225,  -328,
     174,  -328,   179,  -328,    33,  -328,  -328,  -328,   131,  -328,
    -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,   441,
    -328,  -328,   441,   196,  -328,   215,   184,  -328,   111,  -328,
    -328,  -328,  -328,  -328,   221,  -328,  -328,   183,  -328,   -52,
    -328,  -328,  -328,  -328,  -328,  -328,   224,   258,   232,  -328,
     264,   193,    -6,   235,   247,  -328,  -328,  -328,  -328,  -328,
     399,  -328,  -328,   189,  -328,   177,   177,   177,  -328,   133,
     249,    84,  -328,   274,   251,  -328,   281,    48,  -328,  -328,
    -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,
    -328,   252,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,
    -328,  -328,   209,  -328,  -328,   253,  -328,  -328,   217,   258,
     271,   256,  -328,  -328,   219,  -328,   -14,   441,   298,   258,
     239,   304,   301,   288,    20,  -328,  -328,   250,   357,   254,
    -328,   248,  -328,  -328,   290,   263,   321,   144,   275,   312,
    -328,   276,   285,    96,  -328,  -328,  -328,   279,   284,  -328,
    -328,   441,    83,   295,   356,  -328,  -328,  -328,  -328,    82,
     286,  -328,    37,  -328,  -328,   287,   441,   441,   289,  -328,
    -328,  -328,  -328,  -328,   291,   210,  -328,  -328,  -328,   293,
      64,    89,  -328,  -328,  -328,  -328,  -328,   300,   315,   294,
    -328,  -328,   322,  -328,  -328,  -328,  -328,  -328,   323,  -328,
     441,  -328,  -328,  -328,  -328,  -328,  -328,   317,   351,   -10,
      -3,    97,   -10,   381,   171,  -328,   347,  -328,     9,     0,
       1,  -328,   324,   365,  -328,   366,  -328,    97,   171,    97,
     171,  -328,   384,    -2,  -328,  -328,  -328,  -328,  -328,   369,
     378,   370,   330,  -328,  -328,  -328,   389,  -328,   373,  -328
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
     234,   237,     0,     0,   235,     0,     1,     0,     2,   241,
       0,     0,     0,     4,     5,   236,     0,   238,     0,     8,
     239,     0,   123,   232,   233,   168,   169,   170,   171,   152,
     154,   153,   155,   142,   143,   144,   145,   146,   147,   148,
     149,   150,   151,     0,     0,   242,   121,   122,   124,   125,
     126,     0,   243,   131,   132,   133,   135,   138,   140,   141,
     139,   136,   137,   245,   172,     0,   173,   244,   178,   179,
     246,   185,   186,   248,   226,   227,   228,   240,   247,    95,
      96,     6,    12,    99,   253,     0,     0,     0,     0,   183,
       0,   182,     0,     0,     0,     0,     0,     0,     0,     0,
      98,     9,     0,    15,    10,   102,   100,   101,     0,   249,
       0,   250,     0,   162,     0,     0,     0,     0,     0,   190,
       0,   180,     0,   218,   160,   161,   206,     0,   127,   156,
     157,   159,   158,   129,     0,   128,   134,   176,     0,   181,
     194,   189,     0,   230,     0,     3,     0,    13,    42,   103,
       0,   223,     0,   104,     0,   251,   117,   255,   115,   116,
     254,   256,   258,   257,   259,   260,   252,   164,   165,     0,
     163,   166,     0,     0,   184,     0,     0,   217,     0,   207,
     209,   210,   211,   212,     0,   174,   200,   159,   203,     0,
     195,   197,   198,   199,   202,   201,   229,     0,     0,    16,
      88,     0,     0,     0,     0,   119,   120,   118,   177,   191,
       0,   219,   167,     0,   205,     0,     0,     0,   193,     0,
       0,     0,    20,    40,     0,    43,    11,     0,     7,   105,
     224,   225,   106,   109,   108,   110,   111,   112,   113,   107,
     221,   220,   114,   187,   192,   138,   188,   208,   213,   215,
     216,   214,     0,   196,   231,     0,    14,    21,     0,    18,
      46,     0,    89,    97,     0,   204,     0,     0,     0,    19,
       0,    56,    48,     0,     0,    91,   222,     0,     0,     0,
      24,    25,    27,    26,     0,     0,     0,     0,     0,     0,
      57,     0,     0,     0,    90,    92,    22,     0,     0,    23,
      28,     0,     0,     0,     0,    50,    51,    52,    47,     0,
       0,    65,     0,    45,    93,     0,     0,     0,     0,    32,
      33,    34,    35,    36,     0,     0,    17,    60,    59,     0,
       0,     0,    53,    54,    55,    49,    94,     0,     0,     0,
      31,    39,     0,    38,    58,    62,    63,    64,     0,    44,
       0,    66,    30,    29,    41,    37,    61,     0,     0,     0,
       0,    73,     0,     0,    73,    80,    79,    78,     0,     0,
       0,    72,     0,     0,    71,     0,    82,    73,    73,    73,
      73,    81,    85,    68,    83,    74,    76,    75,    77,     0,
       0,     0,     0,    84,    70,    69,    87,    67,     0,    86
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,
    -328,  -328,  -328,  -328,   164,  -198,  -328,  -328,  -328,   143,
    -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,
    -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,  -328,
      63,  -289,  -327,  -328,  -328,  -328,  -328,  -328,   152,  -328,
    -328,  -328,  -328,  -328,   296,  -176,  -328,   234,  -328,  -328,
    -328,   -99,  -328,  -328,  -328,  -328,  -328,  -328,   -98,  -328,
    -328,   237,  -328,   -17,  -328,  -328,   -16,   -15,   -83,   -94,
    -328,   -88,  -328,  -328,   -13,   -95,  -328,  -328,  -328,   349,
     -93,  -328,  -328,  -328,  -328,   -92,  -328,  -328,   273,  -328,
     -82,  -328,  -328,   242,  -328,   292,  -328,   -84,  -328,  -328,
     255,  -328,  -328,  -328,  -328,  -328,   -80,  -328,  -328,  -328,
    -328,   -91,  -328,  -328,  -328,  -328,  -328,  -328,  -328,   477,
    -328,  -328,  -328,  -328,   415,  -328,  -328
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,     8,    14,    19,    81,    82,   101,   102,   103,
     147,   148,   199,   268,   221,   222,   279,   280,   281,   282,
     286,   324,   304,   342,   259,   200,   225,   271,   272,   292,
     308,   335,   289,   290,   329,   311,   348,   331,   351,   392,
     363,   371,   364,   365,   383,   226,   262,   274,   275,    83,
     104,   105,   108,   150,   152,   153,   232,   157,   158,   159,
     207,    45,    46,    47,    48,    49,    50,    51,    52,    53,
      54,    55,    56,   114,    58,    59,   115,   127,   186,   129,
     130,   131,   117,   132,   118,    63,    64,    65,    66,   119,
      67,    68,    69,    90,    91,    70,    71,    72,   120,   246,
     188,   142,   189,   190,   191,   192,   193,   194,   134,   178,
     179,   180,   181,   182,   183,   184,   195,   239,   240,   154,
     241,    73,    74,    75,    76,     3,     4,     5,    10,    11,
      12,    77,    78,    85,    86,    87,   166
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
     116,    57,    60,    61,   143,    62,   137,   116,   133,   128,
     136,   144,   135,   160,   161,   141,   139,   162,   360,   163,
     164,   165,   390,   257,    98,   360,   229,    15,   360,   360,
     277,     1,   109,   367,   361,   217,   294,   375,   151,     6,
     218,   366,   378,   380,   377,   379,   228,    25,    26,    27,
      28,   229,     7,   376,   187,     9,    33,    34,    35,    36,
      37,    38,    39,    40,    41,    42,   113,     9,   273,    99,
      84,   257,   278,    79,   332,   374,   362,    17,   333,   334,
      80,   208,    21,   362,   116,   391,   362,   362,   385,   386,
     387,   388,   151,    13,   256,    57,    60,    61,    20,    62,
     263,   220,   345,   349,   346,   234,   235,   347,    18,   236,
     350,   237,   238,   242,   319,   320,   321,   322,   323,   327,
     203,    22,   204,   328,   368,   254,   369,   370,  -175,   187,
      84,   250,   248,   249,  -130,   251,    23,    24,    25,    26,
      27,    28,    29,    30,    31,    32,    88,    33,    34,    35,
      36,    37,    38,    39,    40,    41,    42,    25,    26,    27,
      28,    43,    89,    44,   106,   107,    33,    34,    35,    36,
      37,    38,    39,    40,    41,    42,   113,    95,   283,   284,
     276,   314,   305,   306,   307,   205,   206,    57,    60,    61,
     298,    62,    92,   245,    60,    61,    93,    62,   213,   214,
     369,   370,    94,    96,    97,   315,   100,   111,   112,   121,
     122,   123,   145,   318,   124,   140,   146,   149,   151,   155,
     167,   123,   168,   169,   172,   170,   171,   173,   337,   338,
     174,   175,   176,   177,   197,   185,   198,   343,    25,    26,
      27,    28,    29,    30,    31,    32,   341,    33,    34,    35,
      36,    37,    38,    39,    40,    41,    42,   113,   124,   211,
     125,   196,   357,   126,   202,   210,   212,   215,   140,   216,
     219,    25,    26,    27,    28,   220,   223,   224,   227,   230,
      33,    34,    35,    36,    37,    38,    39,    40,    41,    42,
     113,    22,   231,   255,   258,   260,   261,   265,   264,   266,
     156,   270,   395,   267,   273,   126,    23,    24,    25,    26,
      27,    28,    29,    30,    31,    32,   285,    33,    34,    35,
      36,    37,    38,    39,    40,    41,    42,    22,   287,   288,
     291,    43,   293,    44,   278,   296,   156,   301,   310,   299,
     303,   325,    23,    24,    25,    26,    27,    28,    29,    30,
      31,    32,   302,    33,    34,    35,    36,    37,    38,    39,
      40,    41,    42,   123,   309,   312,   316,    43,   326,    44,
     313,   317,   336,   359,   368,   330,   340,   339,   344,   354,
      25,    26,    27,    28,    29,    30,    31,    32,   352,    33,
      34,    35,    36,    37,    38,    39,    40,    41,    42,   113,
     124,   297,   125,   353,   373,   126,   358,   355,   356,   382,
     384,   389,   381,   393,   396,   397,   398,   399,    25,    26,
      27,    28,   394,   269,   300,   372,   295,    33,    34,    35,
      36,    37,    38,    39,    40,    41,    42,   113,   233,    25,
      26,    27,    28,   243,   138,   209,   201,   244,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,   113,   253,
      25,    26,    27,    28,    29,    30,    31,    32,   247,    33,
      34,    35,    36,    37,    38,    39,    40,    41,    42,    25,
      26,    27,    28,    29,    30,    31,    32,    16,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,   113,   124,
     110,   125,    25,    26,    27,    28,     0,     0,   252,     0,
       0,    33,    34,    35,    36,    37,    38,    39,    40,    41,
      42,   113
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      88,    18,    18,    18,    98,    18,    94,    95,    92,    92,
      93,    99,    92,   112,   112,    97,    96,   112,    28,   112,
     112,   112,    24,   221,    46,    28,   202,     4,    28,    28,
      44,     3,     6,   360,    44,    87,    16,    28,    44,     0,
      92,    44,   369,   370,    44,    44,    52,    61,    62,    63,
      64,   227,     7,    44,   142,    44,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    44,    48,    91,
      44,   269,    86,    49,    37,   364,    86,    85,    41,    42,
      56,   169,     5,    86,   172,    87,    86,    86,   377,   378,
     379,   380,    44,    44,    10,   112,   112,   112,    85,   112,
      52,    17,    38,    14,    40,   204,   204,    43,    89,   204,
      21,   204,   204,   204,    31,    32,    33,    34,    35,    37,
      87,    44,    89,    41,    27,   219,    29,    30,    86,   217,
      44,   215,   215,   215,    46,   215,    59,    60,    61,    62,
      63,    64,    65,    66,    67,    68,    91,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    61,    62,    63,
      64,    84,    44,    86,    50,    51,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    86,   266,   267,
     264,    85,    38,    39,    40,    54,    55,   204,   204,   204,
     278,   204,    46,   210,   210,   210,    46,   210,    87,    88,
      29,    30,    46,    46,    46,   293,    57,    85,    89,    88,
      87,    44,     8,   301,    81,    91,     9,    58,    44,    85,
      90,    44,    90,    47,    87,    80,    90,    92,   316,   317,
      44,    90,    90,    44,    44,    88,    11,   325,    61,    62,
      63,    64,    65,    66,    67,    68,    36,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    44,
      83,    92,   350,    86,    85,    69,    82,    46,    91,    86,
      46,    61,    62,    63,    64,    17,    44,    13,    85,    44,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    44,    45,    44,    20,    44,    15,    88,    46,    46,
      53,    30,   390,    86,    48,    86,    59,    60,    61,    62,
      63,    64,    65,    66,    67,    68,    18,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    44,    89,    25,
      29,    84,    44,    86,    86,    85,    53,    47,    26,    85,
      19,    46,    59,    60,    61,    62,    63,    64,    65,    66,
      67,    68,    89,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    44,    89,    89,    87,    84,    12,    86,
      85,    87,    85,    22,    27,    89,    85,    88,    85,    85,
      61,    62,    63,    64,    65,    66,    67,    68,    88,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    44,    83,    88,    23,    86,    89,    85,    85,    44,
      44,    27,    88,    44,    44,    85,    27,    44,    61,    62,
      63,    64,    44,   259,   281,   362,   274,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,   204,    61,
      62,    63,    64,    44,    95,   172,   150,   210,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,   217,
      61,    62,    63,    64,    65,    66,    67,    68,   213,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    61,
      62,    63,    64,    65,    66,    67,    68,    10,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      85,    83,    61,    62,    63,    64,    -1,    -1,   216,    -1,
      -1,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,    94,   218,   219,   220,     0,     7,    95,    44,
     221,   222,   223,    44,    96,     4,   222,    85,    89,    97,
      85,     5,    44,    59,    60,    61,    62,    63,    64,    65,
      66,    67,    68,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    84,    86,   154,   155,   156,   157,   158,
     159,   160,   161,   162,   163,   164,   165,   166,   167,   168,
     169,   170,   177,   178,   179,   180,   181,   183,   184,   185,
     188,   189,   190,   214,   215,   216,   217,   224,   225,    49,
      56,    98,    99,   142,    44,   226,   227,   228,    91,    44,
     186,   187,    46,    46,    46,    86,    46,    46,    46,    91,
      57,   100,   101,   102,   143,   144,    50,    51,   145,     6,
     227,    85,    89,    80,   166,   169,   174,   175,   177,   182,
     191,    88,    87,    44,    81,    83,    86,   170,   171,   172,
     173,   174,   176,   200,   201,   209,   171,   174,   182,   209,
      91,   193,   194,   172,   174,     8,     9,   103,   104,    58,
     146,    44,   147,   148,   212,    85,    53,   150,   151,   152,
     154,   161,   178,   183,   188,   214,   229,    90,    90,    47,
      80,    90,    87,    92,    44,    90,    90,    44,   202,   203,
     204,   205,   206,   207,   208,    88,   171,   174,   193,   195,
     196,   197,   198,   199,   200,   209,    92,    44,    11,   105,
     118,   147,    85,    87,    89,    54,    55,   153,   174,   191,
      69,    44,    82,    87,    88,    46,    86,    87,    92,    46,
      17,   107,   108,    44,    13,   119,   138,    85,    52,   148,
      44,    45,   149,   150,   154,   161,   178,   183,   188,   210,
     211,   213,   214,    44,   164,   166,   192,   203,   171,   193,
     200,   209,   198,   196,   172,    44,    10,   108,    20,   117,
      44,    15,   139,    52,    46,    88,    46,    86,   106,   107,
      30,   120,   121,    48,   140,   141,   200,    44,    86,   109,
     110,   111,   112,   174,   174,    18,   113,    89,    25,   125,
     126,    29,   122,    44,    16,   141,    85,    44,   174,    85,
     112,    47,    89,    19,   115,    38,    39,    40,   123,    89,
      26,   128,    89,    85,    85,   174,    87,    87,   174,    31,
      32,    33,    34,    35,   114,    46,    12,    37,    41,   127,
      89,   130,    37,    41,    42,   124,    85,   174,   174,    88,
      85,    36,   116,   174,    85,    38,    40,    43,   129,    14,
      21,   131,    88,    88,    85,    85,    85,   174,    89,    22,
      28,    44,    86,   133,   135,   136,    44,   135,    27,    29,
      30,   134,   133,    23,   134,    28,    44,    44,   135,    44,
     135,    88,    44,   137,    44,   134,   134,   134,   134,    27,
      24,    87,   132,    44,    44,   174,    44,    85,    27,    44
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,    93,    94,    95,    96,    97,    97,    98,    99,    99,
     100,   101,   102,   102,   103,   104,   104,   105,   106,   106,
     107,   107,   108,   108,   109,   109,   110,   111,   111,   112,
     112,   113,   114,   114,   114,   114,   114,   115,   116,   116,
     117,   117,   118,   118,   119,   120,   121,   121,   122,   122,
     123,   123,   123,   124,   124,   124,   125,   125,   126,   127,
     127,   128,   129,   129,   129,   130,   130,   131,   132,   132,
     132,   133,   133,   134,   134,   134,   134,   134,   135,   135,
     135,   135,   136,   136,   137,   137,   137,   137,   138,   138,
     139,   140,   140,   141,   141,   142,   142,   143,   144,   145,
     145,   145,   146,   146,   147,   147,   148,   148,   149,   149,
     149,   149,   149,   149,   149,   150,   150,   151,   152,   153,
     153,   154,   154,   155,   156,   156,   156,   157,   158,   159,
     160,   161,   161,   162,   163,   164,   164,   164,   165,   165,
     166,   166,   167,   167,   167,   167,   168,   168,   168,   168,
     169,   169,   170,   170,   170,   170,   171,   171,   171,   171,
     172,   173,   174,   174,   175,   175,   175,   176,   177,   177,
     177,   177,   178,   178,   179,   180,   181,   182,   183,   183,
     184,   185,   186,   187,   187,   188,   188,   189,   189,   190,
     191,   191,   192,   193,   194,   195,   195,   196,   197,   197,
     198,   198,   198,   198,   199,   200,   201,   202,   202,   203,
     203,   203,   203,   204,   205,   206,   207,   208,   209,   209,
     210,   210,   211,   212,   212,   213,   214,   214,   215,   215,
     216,   216,   217,   217,   218,   218,   219,   220,   221,   221,
     222,   223,   224,   224,   224,   224,   224,   224,   224,   225,
     226,   226,   227,   228,   229,   229,   229,   229,   229,   229,
     229
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     2,     6,     1,     0,     2,     5,     0,     2,
       1,     4,     0,     2,     4,     0,     2,     7,     0,     1,
       1,     2,     5,     5,     1,     1,     1,     1,     2,     5,
       5,     4,     1,     1,     1,     1,     1,     4,     1,     1,
       0,     7,     0,     2,     7,     3,     0,     3,     0,     3,
       1,     1,     1,     1,     1,     1,     0,     1,     4,     1,
       1,     4,     1,     1,     1,     0,     2,     9,     0,     2,
       2,     2,     2,     0,     3,     3,     3,     3,     2,     2,
       1,     3,     3,     4,     3,     1,     5,     3,     0,     2,
       3,     1,     2,     3,     4,     1,     1,     5,     1,     0,
       1,     1,     0,     1,     1,     3,     3,     3,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     2,     1,
       1,     1,     1,     1,     1,     1,     1,     3,     3,     3,
       1,     1,     1,     1,     3,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     2,     2,     2,     2,     3,     1,     1,
       1,     1,     1,     1,     4,     1,     3,     3,     1,     1,
       3,     3,     1,     1,     3,     1,     1,     6,     6,     3,
       1,     3,     1,     3,     1,     1,     3,     1,     1,     1,
       1,     1,     1,     1,     4,     3,     1,     1,     3,     1,
       1,     1,     1,     3,     3,     3,     3,     1,     1,     3,
       1,     1,     3,     1,     3,     1,     1,     1,     1,     4,
       3,     6,     1,     1,     0,     1,     3,     1,     2,     3,
       3,     1,     1,     1,     1,     1,     1,     1,     1,     3,
       2,     3,     3,     1,     1,     1,     1,     1,     1,     1,
       1
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


  private static final int YYLAST_ = 521;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 6;
  private static final int YYNTOKENS_ = 93;

/* Unqualified %code blocks.  */
/* "src/main/java/parser/Parser.y":21  */

    private ContextHandler contexts;

/* "src/main/java/parser/Parser.java":3057  */

}
/* "src/main/java/parser/Parser.y":1393  */
