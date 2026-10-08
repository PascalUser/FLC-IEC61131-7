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
    import utils.builders.*;

    import parser.utils.*;
    import parser.facades.*;
    import parser.internals.*;
    import parser.initializations.*;
    import parser.initializations.primitives.*;

/* "src/main/java/parser/Parser.java":62  */

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
    S_fuzzify_block_name(104),     /* fuzzify_block_name  */
    S_opt_defuzzify_block_list(105), /* opt_defuzzify_block_list  */
    S_defuzzify_block(106),        /* defuzzify_block  */
    S_defuzzify_block_name(107),   /* defuzzify_block_name  */
    S_opt_linguistic_term_list(108), /* opt_linguistic_term_list  */
    S_linguistic_term_list(109),   /* linguistic_term_list  */
    S_linguistic_term(110),        /* linguistic_term  */
    S_linguistic_term_value(111),  /* linguistic_term_value  */
    S_membership_function(112),    /* membership_function  */
    S_singleton(113),              /* singleton  */
    S_point_list(114),             /* point_list  */
    S_point(115),                  /* point  */
    S_defuzzification_method(116), /* defuzzification_method  */
    S_defuzz_method(117),          /* defuzz_method  */
    S_default_value(118),          /* default_value  */
    S_default_val(119),            /* default_val  */
    S_opt_range(120),              /* opt_range  */
    S_opt_rule_block_list(121),    /* opt_rule_block_list  */
    S_rule_block(122),             /* rule_block  */
    S_operator_definition(123),    /* operator_definition  */
    S_opt_operator_or(124),        /* opt_operator_or  */
    S_operator_and_opt(125),       /* operator_and_opt  */
    S_or_type(126),                /* or_type  */
    S_and_type(127),               /* and_type  */
    S_activation_method_opt(128),  /* activation_method_opt  */
    S_activation_method(129),      /* activation_method  */
    S_act_type(130),               /* act_type  */
    S_accumulation_method(131),    /* accumulation_method  */
    S_accu_type(132),              /* accu_type  */
    S_rule_list(133),              /* rule_list  */
    S_rule(134),                   /* rule  */
    S_opt_weighting(135),          /* opt_weighting  */
    S_condition(136),              /* condition  */
    S_condition_tail(137),         /* condition_tail  */
    S_x(138),                      /* x  */
    S_subcondition(139),           /* subcondition  */
    S_conclusion_list(140),        /* conclusion_list  */
    S_opt_option_block_list(141),  /* opt_option_block_list  */
    S_option_block(142),           /* option_block  */
    S_pragma_list(143),            /* pragma_list  */
    S_pragma(144),                 /* pragma  */
    S_io_var_decl(145),            /* io_var_decl  */
    S_var_declarations(146),       /* var_declarations  */
    S_var_id_decl(147),            /* var_id_decl  */
    S_var_retain_spec(148),        /* var_retain_spec  */
    S_var_constant_spec(149),      /* var_constant_spec  */
    S_var_init_decl_list(150),     /* var_init_decl_list  */
    S_var_init_decl(151),          /* var_init_decl  */
    S_identifier_list(152),        /* identifier_list  */
    S_var_spec_init(153),          /* var_spec_init  */
    S_custom_spec_init(154),       /* custom_spec_init  */
    S_custom_specification(155),   /* custom_specification  */
    S_initialized_custom(156),     /* initialized_custom  */
    S_initialized_custom_with_constant(157), /* initialized_custom_with_constant  */
    S_initialized_custom_with_identifier(158), /* initialized_custom_with_identifier  */
    S_initialized_custom_with_structure(159), /* initialized_custom_with_structure  */
    S_custom_type_name(160),       /* custom_type_name  */
    S_boolean_spec_init(161),      /* boolean_spec_init  */
    S_boolean_specification(162),  /* boolean_specification  */
    S_initialized_boolean(163),    /* initialized_boolean  */
    S_edge(164),                   /* edge  */
    S_simple_spec_init(165),       /* simple_spec_init  */
    S_simple_specification(166),   /* simple_specification  */
    S_initialized_simple(167),     /* initialized_simple  */
    S_elementary_type_name(168),   /* elementary_type_name  */
    S_numeric_type_name(169),      /* numeric_type_name  */
    S_integer_type_name(170),      /* integer_type_name  */
    S_signed_integer_type_name(171), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(172), /* unsigned_integer_type_name  */
    S_real_type_name(173),         /* real_type_name  */
    S_date_type_name(174),         /* date_type_name  */
    S_constant(175),               /* constant  */
    S_string_constant(176),        /* string_constant  */
    S_boolean_constant(177),       /* boolean_constant  */
    S_numeric_constant(178),       /* numeric_constant  */
    S_number_prefix(179),          /* number_prefix  */
    S_time_constant(180),          /* time_constant  */
    S_bit_string_type_name(181),   /* bit_string_type_name  */
    S_subrange_spec_init(182),     /* subrange_spec_init  */
    S_subrange_specification(183), /* subrange_specification  */
    S_subrange_type_decl(184),     /* subrange_type_decl  */
    S_initialized_subrange(185),   /* initialized_subrange  */
    S_range(186),                  /* range  */
    S_enumerated_spec_init(187),   /* enumerated_spec_init  */
    S_enumerated_specification(188), /* enumerated_specification  */
    S_initialized_enumerated(189), /* initialized_enumerated  */
    S_enumerated_values(190),      /* enumerated_values  */
    S_array_spec_init(191),        /* array_spec_init  */
    S_array_specification(192),    /* array_specification  */
    S_array_type(193),             /* array_type  */
    S_initialized_array(194),      /* initialized_array  */
    S_range_list(195),             /* range_list  */
    S_non_generic_type_name(196),  /* non_generic_type_name  */
    S_array_initialization(197),   /* array_initialization  */
    S_array_initial_elements_list(198), /* array_initial_elements_list  */
    S_array_initial_elements(199), /* array_initial_elements  */
    S__actionAfterElement_(200),   /* _actionAfterElement_  */
    S_array_initial_element(201),  /* array_initial_element  */
    S_repeated_initial_element(202), /* repeated_initial_element  */
    S__actionBeforeNotSimpleInitialization_(203), /* _actionBeforeNotSimpleInitialization_  */
    S_structure_initialization(204), /* structure_initialization  */
    S__actionAfterParenthesis_(205), /* _actionAfterParenthesis_  */
    S_structure_field_initialization_list(206), /* structure_field_initialization_list  */
    S_initialized_structure_field(207), /* initialized_structure_field  */
    S_initialized_field_with_constant(208), /* initialized_field_with_constant  */
    S_initialized_field_with_identifier(209), /* initialized_field_with_identifier  */
    S_initialized_field_with_array(210), /* initialized_field_with_array  */
    S_initialized_field_with_structure(211), /* initialized_field_with_structure  */
    S_nested_field(212),           /* nested_field  */
    S_identifier_with_opt_mangling(213), /* identifier_with_opt_mangling  */
    S_standard_function_block_spec_init(214), /* standard_function_block_spec_init  */
    S_initialized_standard_function_block(215), /* initialized_standard_function_block  */
    S_standard_function_block_specification(216), /* standard_function_block_specification  */
    S_string_spec_init(217),       /* string_spec_init  */
    S_string_specification(218),   /* string_specification  */
    S_initialized_string(219),     /* initialized_string  */
    S_type_string_specification(220), /* type_string_specification  */
    S_opt_data_type_declaration(221), /* opt_data_type_declaration  */
    S_data_type_declaration(222),  /* data_type_declaration  */
    S_type_declaration_list(223),  /* type_declaration_list  */
    S_type_declaration(224),       /* type_declaration  */
    S_type_name_declaration(225),  /* type_name_declaration  */
    S_type_spec_init(226),         /* type_spec_init  */
    S_structure_specification(227), /* structure_specification  */
    S_structure_field_declaration_list(228), /* structure_field_declaration_list  */
    S_structure_field_declaration(229), /* structure_field_declaration  */
    S_field_name(230),             /* field_name  */
    S_structure_field_spec_init(231); /* structure_field_spec_init  */


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
      SymbolKind.S_identifier_list,
      SymbolKind.S_var_spec_init,
      SymbolKind.S_custom_spec_init,
      SymbolKind.S_custom_specification,
      SymbolKind.S_initialized_custom,
      SymbolKind.S_initialized_custom_with_constant,
      SymbolKind.S_initialized_custom_with_identifier,
      SymbolKind.S_initialized_custom_with_structure,
      SymbolKind.S_custom_type_name,
      SymbolKind.S_boolean_spec_init,
      SymbolKind.S_boolean_specification,
      SymbolKind.S_initialized_boolean,
      SymbolKind.S_edge,
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
      SymbolKind.S_array_spec_init,
      SymbolKind.S_array_specification,
      SymbolKind.S_array_type,
      SymbolKind.S_initialized_array,
      SymbolKind.S_range_list,
      SymbolKind.S_non_generic_type_name,
      SymbolKind.S_array_initialization,
      SymbolKind.S_array_initial_elements_list,
      SymbolKind.S_array_initial_elements,
      SymbolKind.S__actionAfterElement_,
      SymbolKind.S_array_initial_element,
      SymbolKind.S_repeated_initial_element,
      SymbolKind.S__actionBeforeNotSimpleInitialization_,
      SymbolKind.S_structure_initialization,
      SymbolKind.S__actionAfterParenthesis_,
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
      SymbolKind.S_standard_function_block_specification,
      SymbolKind.S_string_spec_init,
      SymbolKind.S_string_specification,
      SymbolKind.S_initialized_string,
      SymbolKind.S_type_string_specification,
      SymbolKind.S_opt_data_type_declaration,
      SymbolKind.S_data_type_declaration,
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
  "opt_fuzzify_block_list", "fuzzify_block", "fuzzify_block_name",
  "opt_defuzzify_block_list", "defuzzify_block", "defuzzify_block_name",
  "opt_linguistic_term_list", "linguistic_term_list", "linguistic_term",
  "linguistic_term_value", "membership_function", "singleton",
  "point_list", "point", "defuzzification_method", "defuzz_method",
  "default_value", "default_val", "opt_range", "opt_rule_block_list",
  "rule_block", "operator_definition", "opt_operator_or",
  "operator_and_opt", "or_type", "and_type", "activation_method_opt",
  "activation_method", "act_type", "accumulation_method", "accu_type",
  "rule_list", "rule", "opt_weighting", "condition", "condition_tail", "x",
  "subcondition", "conclusion_list", "opt_option_block_list",
  "option_block", "pragma_list", "pragma", "io_var_decl",
  "var_declarations", "var_id_decl", "var_retain_spec",
  "var_constant_spec", "var_init_decl_list", "var_init_decl",
  "identifier_list", "var_spec_init", "custom_spec_init",
  "custom_specification", "initialized_custom",
  "initialized_custom_with_constant", "initialized_custom_with_identifier",
  "initialized_custom_with_structure", "custom_type_name",
  "boolean_spec_init", "boolean_specification", "initialized_boolean",
  "edge", "simple_spec_init", "simple_specification", "initialized_simple",
  "elementary_type_name", "numeric_type_name", "integer_type_name",
  "signed_integer_type_name", "unsigned_integer_type_name",
  "real_type_name", "date_type_name", "constant", "string_constant",
  "boolean_constant", "numeric_constant", "number_prefix", "time_constant",
  "bit_string_type_name", "subrange_spec_init", "subrange_specification",
  "subrange_type_decl", "initialized_subrange", "range",
  "enumerated_spec_init", "enumerated_specification",
  "initialized_enumerated", "enumerated_values", "array_spec_init",
  "array_specification", "array_type", "initialized_array", "range_list",
  "non_generic_type_name", "array_initialization",
  "array_initial_elements_list", "array_initial_elements",
  "_actionAfterElement_", "array_initial_element",
  "repeated_initial_element", "_actionBeforeNotSimpleInitialization_",
  "structure_initialization", "_actionAfterParenthesis_",
  "structure_field_initialization_list", "initialized_structure_field",
  "initialized_field_with_constant", "initialized_field_with_identifier",
  "initialized_field_with_array", "initialized_field_with_structure",
  "nested_field", "identifier_with_opt_mangling",
  "standard_function_block_spec_init",
  "initialized_standard_function_block",
  "standard_function_block_specification", "string_spec_init",
  "string_specification", "initialized_string",
  "type_string_specification", "opt_data_type_declaration",
  "data_type_declaration", "type_declaration_list", "type_declaration",
  "type_name_declaration", "type_spec_init", "structure_specification",
  "structure_field_declaration_list", "structure_field_declaration",
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
/* "%code init" blocks.  */
/* "src/main/java/parser/Parser.y":30  */

    this.contexts = new ContextHandler();

/* "src/main/java/parser/Parser.java":911  */

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
    /* "src/main/java/parser/Parser.y":108  */
    {
        /**
         * Pops the function block context
        **/

        this.contexts.pop();
    };
  break;


  case 4: /* function_block_name: IDENTIFIER  */
  if (yyn == 4)
    /* "src/main/java/parser/Parser.y":119  */
    {
        /**
         * Builds a new context and adds the function block name to the outer scope
        **/

        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.outerScopes().addScope(((String)(yystack.valueAt (0))));
        this.contexts.add(ctx);
    };
  break;


  case 14: /* fuzzify_block: FUZZIFY fuzzify_block_name linguistic_term_list END_FUZZIFY  */
  if (yyn == 14)
    /* "src/main/java/parser/Parser.y":166  */
    {
        this.contexts.publish();
    };
  break;


  case 15: /* fuzzify_block_name: function_block_name  */
  if (yyn == 15)
    /* "src/main/java/parser/Parser.y":173  */
    {
        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add("@fuzzifier");
        ctx.metadataBuilder().source(Source.FUZZIFY);
    };
  break;


  case 18: /* defuzzify_block: DEFUZZIFY defuzzify_block_name opt_range opt_linguistic_term_list defuzzification_method default_value END_DEFUZZIFY  */
  if (yyn == 18)
    /* "src/main/java/parser/Parser.y":192  */
    {
        this.contexts.publish();
    };
  break;


  case 19: /* defuzzify_block_name: function_block_name  */
  if (yyn == 19)
    /* "src/main/java/parser/Parser.y":199  */
    {
        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add("@defuzzifier");
        ctx.metadataBuilder().source(Source.DEFUZZIFY);
    };
  break;


  case 21: /* opt_linguistic_term_list: linguistic_term_list  */
  if (yyn == 21)
    /* "src/main/java/parser/Parser.y":209  */
    {
        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().parameters(((List<String>)(yystack.valueAt (0))));
    };
  break;


  case 22: /* linguistic_term_list: linguistic_term  */
  if (yyn == 22)
    /* "src/main/java/parser/Parser.y":217  */
    {
        List<String> terms = new ArrayList<>();
        terms.add(((String)(yystack.valueAt (0))));
        yyval = terms;
    };
  break;


  case 23: /* linguistic_term_list: linguistic_term_list linguistic_term  */
  if (yyn == 23)
    /* "src/main/java/parser/Parser.y":223  */
    {
        ((List<String>)(yystack.valueAt (1))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (1)));
    };
  break;


  case 24: /* linguistic_term: TERM IDENTIFIER ASSIGN_OP linguistic_term_value  */
  if (yyn == 24)
    /* "src/main/java/parser/Parser.y":231  */
    {
        this.contexts.createSubcontext();
    };
  break;


  case 98: /* io_var_decl: VAR_INPUT  */
  if (yyn == 98)
    /* "src/main/java/parser/Parser.y":413  */
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.IN).use(Use.VARIABLE);
    };
  break;


  case 99: /* io_var_decl: VAR_OUTPUT  */
  if (yyn == 99)
    /* "src/main/java/parser/Parser.y":422  */
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.OUT).use(Use.VARIABLE);
    };
  break;


  case 101: /* var_id_decl: VAR  */
  if (yyn == 101)
    /* "src/main/java/parser/Parser.y":438  */
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.INTERNAL).use(Use.VARIABLE);
    };
  break;


  case 103: /* var_retain_spec: RETAIN  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":451  */
    {
        // TODO: ctx with retain spec
    };
  break;


  case 104: /* var_retain_spec: NON_RETAIN  */
  if (yyn == 104)
    /* "src/main/java/parser/Parser.y":455  */
    {
        // TODO: ctx with non retain spec
    };
  break;


  case 105: /* var_constant_spec: %empty  */
  if (yyn == 105)
    /* "src/main/java/parser/Parser.y":462  */
    {
        // TODO: ctx with non constant spec
    };
  break;


  case 106: /* var_constant_spec: CONSTANT  */
  if (yyn == 106)
    /* "src/main/java/parser/Parser.y":466  */
    {
        // TODO: ctx with constant spec
    };
  break;


  case 109: /* var_init_decl: identifier_list ':' var_spec_init  */
  if (yyn == 109)
    /* "src/main/java/parser/Parser.y":478  */
    {
        /**
         * Publishes the variables into the symbol table using the loaded context
        **/

        this.contexts.publish();
    };
  break;


  case 111: /* identifier_list: IDENTIFIER  */
  if (yyn == 111)
    /* "src/main/java/parser/Parser.y":490  */
    {
        /**
         * Starts the list of declared identifiers for the current context.
        **/

        this.contexts.createSubcontext();
        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
    };
  break;


  case 112: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 112)
    /* "src/main/java/parser/Parser.y":500  */
    {
        /**
         * Appends another identifier to the declared identifiers list.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
    };
  break;


  case 122: /* custom_specification: IDENTIFIER  */
  if (yyn == 122)
    /* "src/main/java/parser/Parser.y":527  */
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


  case 126: /* initialized_custom_with_constant: custom_type_name ASSIGN_OP constant  */
  if (yyn == 126)
    /* "src/main/java/parser/Parser.y":549  */
    {
        /**
         * Adds to the context the left identifiers initial value and drops the search scope added
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
        ctx.searchScope().popScope();
    };
  break;


  case 127: /* initialized_custom_with_identifier: custom_type_name ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 127)
    /* "src/main/java/parser/Parser.y":562  */
    {
        /**
         * Searches if the enumerated value is valid and in that cases loads the initial value and drops the search
         * scope added
        **/

        ParsingContext ctx = this.contexts.current();

        int octothorpeIdx = ((String)(yystack.valueAt (0))).indexOf('#');
        String completeTypeName = (octothorpeIdx != -1)
            ? ((String)(yystack.valueAt (0))) // Has already name mangling
            : ctx.searchScope().getNameMangled(((String)(yystack.valueAt (0))));

        if (this.symbolTable.get(completeTypeName) == null) {
            // TODO: error control. Enumerated literal does not exist
        }
        ctx.metadataBuilder().initialValue(new VariableInitialization(completeTypeName));
        ctx.searchScope().popScope();
    };
  break;


  case 128: /* initialized_custom_with_structure: custom_type_name ASSIGN_OP structure_initialization  */
  if (yyn == 128)
    /* "src/main/java/parser/Parser.y":585  */
    {
        /**
         * Drops the search scope after reducing the assignment
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.searchScope().popScope();
    };
  break;


  case 129: /* custom_type_name: IDENTIFIER  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":597  */
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


  case 132: /* boolean_specification: BOOL  */
  if (yyn == 132)
    /* "src/main/java/parser/Parser.y":628  */
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


  case 134: /* initialized_boolean: boolean_specification ASSIGN_OP boolean_constant  */
  if (yyn == 134)
    /* "src/main/java/parser/Parser.y":647  */
    {
        /**
         * Builds a boolean variable initialization from the assigned constant
         * and stores it in the current parsing context.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .initialValue(
                new VariableInitialization(((String)(yystack.valueAt (0))))
            );
    };
  break;


  case 139: /* simple_specification: elementary_type_name  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":674  */
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


  case 140: /* initialized_simple: simple_specification ASSIGN_OP constant  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":691  */
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .initialValue(new VariableInitialization(((String)(yystack.valueAt (0))))
        );
    };
  break;


  case 141: /* elementary_type_name: numeric_type_name  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":706  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 142: /* elementary_type_name: date_type_name  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":707  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 143: /* elementary_type_name: bit_string_type_name  */
  if (yyn == 143)
    /* "src/main/java/parser/Parser.y":708  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 144: /* numeric_type_name: integer_type_name  */
  if (yyn == 144)
    /* "src/main/java/parser/Parser.y":712  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 145: /* numeric_type_name: real_type_name  */
  if (yyn == 145)
    /* "src/main/java/parser/Parser.y":713  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 146: /* integer_type_name: signed_integer_type_name  */
  if (yyn == 146)
    /* "src/main/java/parser/Parser.y":717  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 147: /* integer_type_name: unsigned_integer_type_name  */
  if (yyn == 147)
    /* "src/main/java/parser/Parser.y":718  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 148: /* signed_integer_type_name: SINT  */
  if (yyn == 148)
    /* "src/main/java/parser/Parser.y":722  */
           { yyval = Subtype.SINT; };
  break;


  case 149: /* signed_integer_type_name: INT  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":723  */
           { yyval = Subtype.INT;  };
  break;


  case 150: /* signed_integer_type_name: DINT  */
  if (yyn == 150)
    /* "src/main/java/parser/Parser.y":724  */
           { yyval = Subtype.DINT; };
  break;


  case 151: /* signed_integer_type_name: LINT  */
  if (yyn == 151)
    /* "src/main/java/parser/Parser.y":725  */
           { yyval = Subtype.LINT; };
  break;


  case 152: /* unsigned_integer_type_name: USINT  */
  if (yyn == 152)
    /* "src/main/java/parser/Parser.y":729  */
            { yyval = Subtype.USINT; };
  break;


  case 153: /* unsigned_integer_type_name: UINT  */
  if (yyn == 153)
    /* "src/main/java/parser/Parser.y":730  */
            { yyval = Subtype.UINT;  };
  break;


  case 154: /* unsigned_integer_type_name: UDINT  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":731  */
            { yyval = Subtype.UDINT; };
  break;


  case 155: /* unsigned_integer_type_name: ULINT  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":732  */
            { yyval = Subtype.ULINT; };
  break;


  case 156: /* real_type_name: REAL  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":736  */
            { yyval = Subtype.REAL;  };
  break;


  case 157: /* real_type_name: LREAL  */
  if (yyn == 157)
    /* "src/main/java/parser/Parser.y":737  */
            { yyval = Subtype.LREAL; };
  break;


  case 158: /* date_type_name: TIME  */
  if (yyn == 158)
    /* "src/main/java/parser/Parser.y":741  */
                    { yyval = Subtype.TIME;          };
  break;


  case 159: /* date_type_name: DATE  */
  if (yyn == 159)
    /* "src/main/java/parser/Parser.y":742  */
                    { yyval = Subtype.DATE;          };
  break;


  case 160: /* date_type_name: TIME_OF_DAY  */
  if (yyn == 160)
    /* "src/main/java/parser/Parser.y":743  */
                    { yyval = Subtype.TIME_OF_DAY;   };
  break;


  case 161: /* date_type_name: DATE_AND_TIME  */
  if (yyn == 161)
    /* "src/main/java/parser/Parser.y":744  */
                    { yyval = Subtype.DATE_AND_TIME; };
  break;


  case 162: /* constant: string_constant  */
  if (yyn == 162)
    /* "src/main/java/parser/Parser.y":750  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 163: /* constant: boolean_constant  */
  if (yyn == 163)
    /* "src/main/java/parser/Parser.y":751  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 164: /* constant: time_constant  */
  if (yyn == 164)
    /* "src/main/java/parser/Parser.y":752  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 165: /* constant: numeric_constant  */
  if (yyn == 165)
    /* "src/main/java/parser/Parser.y":753  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 166: /* string_constant: STRING_LITERAL  */
  if (yyn == 166)
    /* "src/main/java/parser/Parser.y":757  */
                   { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 167: /* boolean_constant: BOOLEAN_LITERAL  */
  if (yyn == 167)
    /* "src/main/java/parser/Parser.y":761  */
                    { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 169: /* numeric_constant: number_prefix NUMERIC_LITERAL  */
  if (yyn == 169)
    /* "src/main/java/parser/Parser.y":766  */
    {
        // TODO: convert this constant in code
        yyval = "";
    };
  break;


  case 173: /* time_constant: date_type_name '#' TIME_LITERAL  */
  if (yyn == 173)
    /* "src/main/java/parser/Parser.y":779  */
                                    {
        // TODO: semantic action to verify prefix matches time_literal type
    };
  break;


  case 174: /* bit_string_type_name: BYTE  */
  if (yyn == 174)
    /* "src/main/java/parser/Parser.y":785  */
            { yyval = Subtype.BYTE;  };
  break;


  case 175: /* bit_string_type_name: WORD  */
  if (yyn == 175)
    /* "src/main/java/parser/Parser.y":786  */
            { yyval = Subtype.WORD;  };
  break;


  case 176: /* bit_string_type_name: DWORD  */
  if (yyn == 176)
    /* "src/main/java/parser/Parser.y":787  */
            { yyval = Subtype.DWORD; };
  break;


  case 177: /* bit_string_type_name: LWORD  */
  if (yyn == 177)
    /* "src/main/java/parser/Parser.y":788  */
            { yyval = Subtype.LWORD; };
  break;


  case 180: /* subrange_specification: subrange_type_decl '(' range ')'  */
  if (yyn == 180)
    /* "src/main/java/parser/Parser.y":800  */
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
    };
  break;


  case 181: /* subrange_type_decl: integer_type_name  */
  if (yyn == 181)
    /* "src/main/java/parser/Parser.y":818  */
    {
        /**
         * Loads the left identifiers subrange subtype
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().type(Type.SUBRANGE).subtype(((Subtype)(yystack.valueAt (0))));
    };
  break;


  case 182: /* initialized_subrange: subrange_specification ASSIGN_OP numeric_constant  */
  if (yyn == 182)
    /* "src/main/java/parser/Parser.y":830  */
    {
        /**
         * Loads the left identifiers subrange initialization
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
    };
  break;


  case 183: /* range: numeric_constant RANGE_OP numeric_constant  */
  if (yyn == 183)
    /* "src/main/java/parser/Parser.y":842  */
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
        metadata.inferiorLimits.add(((String)(yystack.valueAt (2))));
        metadata.superiorLimits.add(((String)(yystack.valueAt (0))));

        ctx.metadataBuilder()
            .inferiorLimits(metadata.inferiorLimits)
            .superiorLimits(metadata.superiorLimits);
    };
  break;


  case 186: /* enumerated_specification: '(' enumerated_values ')'  */
  if (yyn == 186)
    /* "src/main/java/parser/Parser.y":872  */
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


  case 187: /* initialized_enumerated: enumerated_specification ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 187)
    /* "src/main/java/parser/Parser.y":890  */
    {
        /**
         * Verifies that the initializer value is correct and loads it to the context.
        **/

        ParsingContext ctx = this.contexts.current();
        if (((String)(yystack.valueAt (0))).indexOf('#') != -1) {
            // TODO: error control. Anonymous enum cannot be initialized with mangling
        }
        List<String> enumeratedValues = ctx.metadataBuilder().build().parameters;
        Integer indexValue = enumeratedValues.indexOf(((String)(yystack.valueAt (0))));
        if (indexValue == -1) {
            // TODO: error control. Anonymous enum cannot be initialized with non-existent value
        }
        // TODO: verify how to check index is in lexicon
        ctx.metadataBuilder().initialValue(
            new VariableInitialization(
                ctx.outerScopes().getNameMangled(((String)(yystack.valueAt (0))))
            )
        );
    };
  break;


  case 188: /* enumerated_values: IDENTIFIER  */
  if (yyn == 188)
    /* "src/main/java/parser/Parser.y":915  */
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

        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        Director.makeMacro(ctx.metadataBuilder());
        ctx.metadataBuilder().initialValue(new MacroInitialization(this.symbolTable, "0"));
        this.contexts.publish();

        List<String> enumeratedValues = new ArrayList<>();
        enumeratedValues.add(((String)(yystack.valueAt (0))));
        yyval = enumeratedValues;
    };
  break;


  case 189: /* enumerated_values: enumerated_values ',' IDENTIFIER  */
  if (yyn == 189)
    /* "src/main/java/parser/Parser.y":942  */
    {
        Integer replaceValue = ((List<String>)(yystack.valueAt (2))).size();
        String declaredEnumerated = this.contexts.current().declaredIdentifiers().get(0);

        this.contexts.createSubcontext();
        ParsingContext ctx = this.contexts.current();

        // This is necessary so that it can be disambiguated
        Use currentUse = this.contexts.current().metadataBuilder().build().use;
        if (currentUse == Use.TYPE) {
            ctx.outerScopes().addScope(declaredEnumerated);
        }

        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        Director.makeMacro(ctx.metadataBuilder());
        ctx.metadataBuilder().initialValue(new MacroInitialization(this.symbolTable, replaceValue.toString()));
        this.contexts.publish();

        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 192: /* array_specification: ARRAY '[' range_list ']' OF array_type  */
  if (yyn == 192)
    /* "src/main/java/parser/Parser.y":972  */
    {
        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().type(Type.ARRAY);
    };
  break;


  case 193: /* array_type: IDENTIFIER  */
  if (yyn == 193)
    /* "src/main/java/parser/Parser.y":980  */
    {
        ParsingContext ctx = this.contexts.current();
        LexemeInfo typeMetadata = this.symbolTable.get(((String)(yystack.valueAt (0))));

        int dimension = DimensionCalculator.calculate(ctx);
        ctx.metadataBuilder()
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (0))))
            .initialValue(
                new RepeatedInitialization(dimension, (Initialization) typeMetadata.initialValue)
            );

        String underlyingScope = UnderlyingScopeSearcher.search(this.symbolTable, ((String)(yystack.valueAt (0))));
        ctx.searchScope().addScope(underlyingScope);
    };
  break;


  case 194: /* array_type: non_generic_type_name  */
  if (yyn == 194)
    /* "src/main/java/parser/Parser.y":996  */
    {
        ParsingContext ctx = this.contexts.current();

        int dimension = DimensionCalculator.calculate(ctx);
        Initialization defaultInit = Factory.createPrimitiveInitialization(this.symbolTable, ((Subtype)(yystack.valueAt (0))));
        ctx.metadataBuilder()
            .subtype(((Subtype)(yystack.valueAt (0))))
            .initialValue(
                new RepeatedInitialization(dimension, defaultInit)
            );
    };
  break;


  case 204: /* _actionAfterElement_: %empty  */
  if (yyn == 204)
    /* "src/main/java/parser/Parser.y":1038  */
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
    };
  break;


  case 205: /* array_initial_element: constant  */
  if (yyn == 205)
    /* "src/main/java/parser/Parser.y":1064  */
    {
        /**
         * Creates the initialization and adds the context counter by one
        **/

        ParsingContext constantContext = new ParsingContext(this.symbolTable);
        this.contexts.add(constantContext);
        constantContext.metadataBuilder().initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
        constantContext.incrementIndex(1);
    };
  break;


  case 206: /* array_initial_element: identifier_with_opt_mangling  */
  if (yyn == 206)
    /* "src/main/java/parser/Parser.y":1075  */
    {
        /**
         * Creates the initialization and adds the context counter by one
        **/

        ParsingContext constantContext = new ParsingContext(this.symbolTable);
        this.contexts.add(constantContext);
        constantContext.incrementIndex(1);

        // TODO: verify enum as in initialized_custom_with_identifier rule
        String completeEnumerateName = constantContext.searchScope().getNameMangled(((String)(yystack.valueAt (0))));
        constantContext.metadataBuilder().initialValue(new VariableInitialization(completeEnumerateName));
    };
  break;


  case 207: /* array_initial_element: _actionBeforeNotSimpleInitialization_ structure_initialization  */
  if (yyn == 207)
    /* "src/main/java/parser/Parser.y":1089  */
    {
        /**
         * Only adds the context counter by one because the initialization is created inside the rule.
        **/

        ParsingContext complexContext = this.contexts.current();
        complexContext.incrementIndex(1);
    };
  break;


  case 208: /* array_initial_element: _actionBeforeNotSimpleInitialization_ array_initialization  */
  if (yyn == 208)
    /* "src/main/java/parser/Parser.y":1098  */
    {
        /**
         * Only adds the context counter by one because the initialization is created inside the rule.
        **/

        ParsingContext complexContext = this.contexts.current();
        complexContext.incrementIndex(1);
    };
  break;


  case 209: /* repeated_initial_element: numeric_constant '(' array_initial_element ')'  */
  if (yyn == 209)
    /* "src/main/java/parser/Parser.y":1110  */
    {
        /**
         * Repeats N times the inner initialization.
        **/

        // TODO: verify that numeric_constant is additive (non negative)
        int multiplier = Integer.parseInt(((String)(yystack.valueAt (3))));

        ParsingContext initContext = this.contexts.current();
        initContext.incrementIndex(multiplier * initContext.index() - 1);
    };
  break;


  case 210: /* _actionBeforeNotSimpleInitialization_: %empty  */
  if (yyn == 210)
    /* "src/main/java/parser/Parser.y":1125  */
    {
        ParsingContext arrayCtx = this.contexts.current();
        LexemeInfo arrayMetadata = arrayCtx.metadataBuilder().build();

        this.contexts.createSubcontext();
        ParsingContext compxCtx = this.contexts.current();

        // So that a structure can find it's copy data
        compxCtx.metadataBuilder().customType(
            arrayMetadata.customType
        );
    };
  break;


  case 212: /* _actionAfterParenthesis_: %empty  */
  if (yyn == 212)
    /* "src/main/java/parser/Parser.y":1145  */
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


  case 219: /* initialized_field_with_constant: nested_field ASSIGN_OP constant  */
  if (yyn == 219)
    /* "src/main/java/parser/Parser.y":1177  */
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
        structValue.setFieldInitialization(completeFieldName, new VariableInitialization(((String)(yystack.valueAt (0)))));
        ctx.nestedFields().popScope();
    };
  break;


  case 220: /* initialized_field_with_identifier: nested_field ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 220)
    /* "src/main/java/parser/Parser.y":1196  */
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
        String completeEnumeratedValue = ctx.searchScope().getNameMangled(((String)(yystack.valueAt (0))));

        structValue.setFieldInitialization(completeFieldName, new VariableInitialization(completeEnumeratedValue));
        ctx.nestedFields().popScope();
    };
  break;


  case 221: /* initialized_field_with_array: nested_field ASSIGN_OP array_initialization  */
  if (yyn == 221)
    /* "src/main/java/parser/Parser.y":1218  */
    {
        /**
         * Overwrites the field with an array initialization.
        **/
        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().popScope();
    };
  break;


  case 222: /* initialized_field_with_structure: nested_field ASSIGN_OP structure_initialization  */
  if (yyn == 222)
    /* "src/main/java/parser/Parser.y":1229  */
    {
        /**
         * Closes the nested field scope that was opened while scanning the
         * field name, once its structure initialization is complete.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().popScope();
    };
  break;


  case 223: /* nested_field: IDENTIFIER  */
  if (yyn == 223)
    /* "src/main/java/parser/Parser.y":1242  */
    {
        /**
         * Needs to expand the nested scope to keep overwriting.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.nestedFields().addScope(((String)(yystack.valueAt (0))));
    };
  break;


  case 224: /* identifier_with_opt_mangling: IDENTIFIER  */
  if (yyn == 224)
    /* "src/main/java/parser/Parser.y":1254  */
    {
        /**
         * Simple identifier: keeps the lexeme as-is.
        **/

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 225: /* identifier_with_opt_mangling: IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 225)
    /* "src/main/java/parser/Parser.y":1262  */
    {
        /**
         * Mangled identifier: joins both identifiers with '#'.
        **/

        yyval = ((String)(yystack.valueAt (2))) + "#" + ((String)(yystack.valueAt (0)));
    };
  break;


  case 232: /* string_specification: type_string_specification  */
  if (yyn == 232)
    /* "src/main/java/parser/Parser.y":1291  */
    {
        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(((Subtype)(yystack.valueAt (0))))
            .initialValue(
                Factory.createPrimitiveInitialization(this.symbolTable, ((Subtype)(yystack.valueAt (0))))
            );
    };
  break;


  case 233: /* string_specification: type_string_specification '[' numeric_constant ']'  */
  if (yyn == 233)
    /* "src/main/java/parser/Parser.y":1301  */
    {
        /**
         * Stores the explicit string length as the superior limit and creates
         * a StringInitialization for the declared subtype.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(((Subtype)(yystack.valueAt (3))))
            .superiorLimits(
                Collections.singletonList(((String)(yystack.valueAt (1))))
            ).initialValue(
                Factory.createPrimitiveInitialization(this.symbolTable, ((Subtype)(yystack.valueAt (3))))
            );
    };
  break;


  case 234: /* initialized_string: string_specification ASSIGN_OP string_constant  */
  if (yyn == 234)
    /* "src/main/java/parser/Parser.y":1320  */
    {
        /**
         * Wraps the assigned string literal as a VariableInitialization and
         * stores it in the current context metadata.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue(
            new VariableInitialization(((String)(yystack.valueAt (0))))
        );
    };
  break;


  case 235: /* type_string_specification: STRING  */
  if (yyn == 235)
    /* "src/main/java/parser/Parser.y":1334  */
                { yyval = Subtype.STRING;  };
  break;


  case 236: /* type_string_specification: WSTRING  */
  if (yyn == 236)
    /* "src/main/java/parser/Parser.y":1335  */
                { yyval = Subtype.WSTRING; };
  break;


  case 242: /* type_declaration: type_name_declaration ':' type_spec_init  */
  if (yyn == 242)
    /* "src/main/java/parser/Parser.y":1356  */
    {
        /**
         * The type declaration is published after being reduced and it's outerScopes unappended.
        **/

        this.contexts.publish();
    };
  break;


  case 243: /* type_name_declaration: IDENTIFIER  */
  if (yyn == 243)
    /* "src/main/java/parser/Parser.y":1367  */
    {
        /**
         * Adds the identifier as the current scope so that everything declared inside this scope belongs to the outer
         * scope.
        **/

        ParsingContext ctx = new ParsingContext(this.symbolTable);
        Director.makeType(ctx.metadataBuilder());
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        this.contexts.add(ctx);
    };
  break;


  case 251: /* structure_specification: STRUCT structure_field_declaration_list END_STRUCT  */
  if (yyn == 251)
    /* "src/main/java/parser/Parser.y":1392  */
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
        List<String> structParameters = ((List<String>)(yystack.valueAt (1)));

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
    };
  break;


  case 252: /* structure_field_declaration_list: structure_field_declaration ';'  */
  if (yyn == 252)
    /* "src/main/java/parser/Parser.y":1426  */
    {
        /**
         * Starts the structure field list with the first declared field.
        **/

        List<String> structParameters = new ArrayList<>();
        structParameters.add(((String)(yystack.valueAt (1))));
        yyval = structParameters;
    };
  break;


  case 253: /* structure_field_declaration_list: structure_field_declaration_list structure_field_declaration ';'  */
  if (yyn == 253)
    /* "src/main/java/parser/Parser.y":1436  */
    {
        /**
         * Appends the next declared field to the structure field list.
        **/

        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (1))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 254: /* structure_field_declaration: field_name ':' structure_field_spec_init  */
  if (yyn == 254)
    /* "src/main/java/parser/Parser.y":1448  */
    {
        /**
         * Publishes the field to the symbol table.
        **/

        this.contexts.publish();

        yyval = ((String)(yystack.valueAt (2)));
    };
  break;


  case 255: /* field_name: IDENTIFIER  */
  if (yyn == 255)
    /* "src/main/java/parser/Parser.y":1461  */
    {
        /**
         * Builds the new field context
        **/

        String newScope = this.contexts.current().declaredIdentifiers().get(0);
        this.contexts.createSubcontext();

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        ctx.metadataBuilder().use(Use.FIELD);
        ctx.outerScopes().addScope(newScope);

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;



/* "src/main/java/parser/Parser.java":2387  */

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

  private static final short yypact_ninf_ = -334;
  private static final short yytable_ninf_ = -182;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      69,    58,   113,   111,  -334,  -334,    20,    36,    35,  -334,
      83,  -334,  -334,    62,  -334,    33,  -334,  -334,  -334,   114,
     103,  -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,
    -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,
    -334,    80,   129,  -334,  -334,  -334,  -334,  -334,  -334,   128,
    -334,   142,  -334,  -334,  -334,   106,  -334,  -334,  -334,  -334,
    -334,  -334,   143,   107,  -334,  -334,   145,  -334,  -334,   148,
    -334,  -334,   149,  -334,   108,  -334,  -334,    25,  -334,    21,
     120,   117,   473,  -334,   -55,   264,   450,   473,   473,   152,
     109,   116,   473,  -334,  -334,  -334,   151,    84,  -334,   122,
    -334,   228,  -334,   119,   121,   163,   132,   123,   133,   127,
     177,  -334,   153,  -334,  -334,  -334,   155,  -334,  -334,  -334,
    -334,  -334,  -334,  -334,  -334,  -334,   135,  -334,   339,  -334,
    -334,   147,  -334,  -334,   232,   233,  -334,   183,  -334,  -334,
     203,  -334,  -334,  -334,  -334,   -15,  -334,  -334,  -334,  -334,
    -334,  -334,  -334,  -334,  -334,   473,  -334,  -334,   473,   179,
    -334,   205,   216,   192,  -334,  -334,   187,    -1,  -334,  -334,
    -334,    -6,  -334,  -334,  -334,    83,  -334,   265,  -334,   203,
    -334,   193,  -334,   -44,   196,  -334,  -334,  -334,  -334,  -334,
     406,  -334,  -334,   -30,  -334,  -334,  -334,  -334,  -334,   234,
    -334,   339,   339,  -334,  -334,  -334,  -334,  -334,   266,    83,
    -334,   269,   199,    11,   241,   191,  -334,  -334,  -334,  -334,
    -334,  -334,   216,  -334,    89,   198,  -334,   253,   105,  -334,
    -334,   289,   271,  -334,   295,    15,  -334,  -334,  -334,  -334,
    -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,   267,
    -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,   270,  -334,
    -334,   231,   266,   288,   272,  -334,  -334,   235,   302,   473,
     301,   266,   244,   297,   294,   280,    13,  -334,  -334,   263,
     364,  -334,   268,  -334,   273,  -334,  -334,   304,   260,   333,
      30,   278,   328,  -334,   279,   275,   425,  -334,  -334,  -334,
     274,   282,  -334,  -334,   473,   150,   309,   344,  -334,  -334,
    -334,  -334,    19,   281,  -334,    95,  -334,  -334,   277,   473,
     473,   283,  -334,  -334,  -334,  -334,  -334,   299,   154,  -334,
    -334,  -334,   300,   100,   102,  -334,  -334,  -334,  -334,  -334,
     298,   303,   305,  -334,  -334,   307,  -334,  -334,  -334,  -334,
    -334,   308,  -334,   473,  -334,  -334,  -334,  -334,  -334,  -334,
     306,   335,    -8,    -7,   112,    -8,   366,   115,  -334,   331,
    -334,     7,    -3,    -2,  -334,   310,   343,  -334,   350,  -334,
     112,   115,   112,   115,  -334,   369,    -5,  -334,  -334,  -334,
    -334,  -334,   353,   385,   355,   336,  -334,  -334,  -334,   396,
    -334,   380,  -334
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
     122,   235,   236,   174,   175,   176,   177,   158,   160,   159,
     161,   148,   149,   150,   151,   152,   153,   154,   155,   156,
     157,     0,     0,   244,   120,   121,   123,   124,   125,     0,
     245,   137,   138,   139,   141,   144,   146,   147,   145,   142,
     143,   247,   178,     0,   179,   246,   184,   185,   248,   190,
     191,   250,   230,   231,   232,   242,   249,     8,   255,     0,
       0,     0,     0,   188,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    98,    99,     6,    12,   102,   251,     0,
     252,     0,   168,     0,     0,     0,     0,     0,   196,     0,
       0,   186,   224,   166,   167,   212,     0,   126,   162,   163,
     165,   164,   128,   127,   140,   182,     0,   187,   210,   195,
     234,     0,   101,     9,     0,    16,    10,   105,   103,   104,
       0,   253,   132,   256,   257,   130,   131,   258,   260,   259,
     261,   262,   254,   170,   171,     0,   169,   172,     0,     0,
     189,     0,     0,     0,   180,   205,   165,     0,   204,   202,
     203,     0,   206,   233,     3,     0,    13,    45,   106,     0,
     111,     0,   107,     0,     0,   135,   136,   133,   183,   197,
       0,   225,   223,     0,   213,   215,   216,   217,   218,     0,
     173,   210,   210,   199,   200,   208,   207,    15,     0,     0,
      17,    91,     0,     0,     0,     0,   134,   193,   198,   144,
     192,   194,     0,   211,     0,     0,   204,     0,     0,    22,
      19,    43,     0,    46,    11,     0,     7,   108,   112,   229,
     109,   113,   114,   115,   116,   117,   118,   110,   227,   226,
     119,   214,   219,   221,   222,   220,   209,   201,     0,    14,
      23,     0,    20,    49,     0,    92,   100,     0,     0,     0,
       0,    21,     0,    59,    51,     0,     0,    94,   228,     0,
       0,    24,     0,    27,    28,    30,    29,     0,     0,     0,
       0,     0,     0,    60,     0,     0,     0,    93,    95,    25,
       0,     0,    26,    31,     0,     0,     0,     0,    53,    54,
      55,    50,     0,     0,    68,     0,    48,    96,     0,     0,
       0,     0,    35,    36,    37,    38,    39,     0,     0,    18,
      63,    62,     0,     0,     0,    56,    57,    58,    52,    97,
       0,     0,     0,    34,    42,     0,    41,    61,    65,    66,
      67,     0,    47,     0,    69,    33,    32,    44,    40,    64,
       0,     0,     0,     0,    76,     0,     0,    76,    83,    82,
      81,     0,     0,     0,    75,     0,     0,    74,     0,    85,
      76,    76,    76,    76,    84,    88,    71,    86,    77,    79,
      78,    80,     0,     0,     0,     0,    87,    73,    72,    90,
      70,     0,    89
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -334,  -334,  -334,  -147,  -334,  -334,  -334,  -334,  -334,  -334,
    -334,  -334,  -334,  -334,  -334,  -334,   168,  -205,  -334,  -334,
    -334,  -334,   161,  -334,  -334,  -334,  -334,  -334,  -334,  -334,
    -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,  -334,
    -334,  -334,  -334,    66,  -333,  -319,  -334,  -334,  -334,  -334,
    -334,   156,  -334,  -334,  -334,  -334,  -334,   254,  -183,  -334,
    -334,   -90,  -334,  -334,  -334,  -334,  -334,  -334,   236,  -334,
    -334,  -334,   -89,  -334,  -334,   262,  -334,   -14,  -334,  -334,
     -13,   -12,   -78,   362,   291,   -82,  -334,  -334,   -11,   -87,
    -334,  -334,  -334,   378,   -86,  -334,  -334,  -334,   -85,  -334,
    -334,  -334,   296,  -334,  -149,  -334,   290,   293,   292,  -334,
    -334,  -153,  -334,  -334,   284,  -334,  -334,  -334,  -334,  -334,
     -76,  -334,  -334,  -334,   -84,  -334,  -334,  -334,  -334,  -334,
    -334,   484,  -334,  -334,  -334,  -334,   412,  -334,  -334
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,    11,    17,    77,    95,    96,   133,   134,   135,
     176,   208,   177,   210,   231,   270,   228,   229,   281,   282,
     283,   284,   285,   289,   327,   307,   345,   262,   211,   233,
     273,   274,   295,   311,   338,   292,   293,   332,   314,   351,
     334,   354,   395,   366,   374,   367,   368,   386,   234,   265,
     276,   277,    97,   136,   137,   140,   179,   181,   182,   183,
     240,    43,    44,    45,    46,    47,    48,    49,   144,   145,
     146,   187,    50,    51,    52,    53,    54,   103,    56,    57,
     104,   116,   165,   118,   119,   120,   106,   121,   107,    61,
      62,    63,    64,   108,    65,    66,    67,    84,    68,    69,
     220,    70,   109,   221,   129,   167,   168,   204,   169,   170,
     171,   122,   162,   193,   194,   195,   196,   197,   198,   199,
     172,   247,   248,   249,    71,    72,    73,    74,     3,     4,
       6,     7,     8,    75,    76,    79,    80,    81,   152
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
     105,    55,    58,    59,    60,   125,   105,   117,   124,   123,
     131,   143,   147,   127,   148,   149,   150,   151,   206,   393,
     363,   363,   205,   260,    12,   363,   363,    98,   207,   297,
     237,   184,   110,   111,   377,   378,   364,   369,    19,   185,
     186,   380,   382,   214,   370,   215,   166,   388,   389,   390,
     391,   379,   237,   381,   383,   180,   330,   222,   223,   180,
     331,   275,   230,   236,     5,    78,   260,   266,   308,   309,
     310,   254,     1,   188,    93,   253,   105,    20,   365,   365,
     115,    94,   394,   365,   365,   128,   202,    55,    58,    59,
      60,   203,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,     5,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,     9,   278,   259,   352,    41,    10,    42,
     166,    14,   227,   353,    15,   241,   243,    16,   244,   245,
     246,   250,   335,   112,   138,   139,   336,   337,   348,   371,
     349,   372,   373,   350,   372,   373,   252,    18,   255,  -129,
      23,    24,    25,    26,    27,    28,    29,    30,    78,    31,
      32,    33,    34,    35,    36,    37,    38,    39,    40,   102,
     113,    82,   114,    83,    85,   115,   219,    58,    59,    60,
     128,   322,   323,   324,   325,   326,   286,   287,    86,    87,
     344,    89,  -181,    88,    90,    91,   112,   113,   301,    92,
     128,    55,    58,    59,    60,   100,   101,   141,   132,   153,
     155,   154,   156,   157,   318,    23,    24,    25,    26,   159,
     158,   160,   321,   164,    31,    32,    33,    34,    35,    36,
      37,    38,    39,    40,   102,    20,   239,   340,   341,   173,
     174,   178,   175,   161,   142,   163,   346,   180,   190,   191,
      21,    22,    23,    24,    25,    26,    27,    28,    29,    30,
     192,    31,    32,    33,    34,    35,    36,    37,    38,    39,
      40,   360,    20,   201,   200,    41,   209,    42,   213,   114,
     224,   142,   232,   227,   235,   238,   256,    21,    22,    23,
      24,    25,    26,    27,    28,    29,    30,   258,    31,    32,
      33,    34,    35,    36,    37,    38,    39,    40,   112,   261,
     264,   398,    41,   267,    42,   263,   268,   269,   272,   288,
     275,   115,   291,   294,   296,    23,    24,    25,    26,    27,
      28,    29,    30,   290,    31,    32,    33,    34,    35,    36,
      37,    38,    39,    40,   102,   113,   279,   114,   299,   305,
     115,   304,   306,   302,   313,   328,   329,   362,   371,   280,
     316,   319,   339,    23,    24,    25,    26,   312,   315,   320,
     333,   342,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,   102,   112,   343,   347,   355,   385,   280,   376,
     357,   356,   358,   359,   387,   361,   392,   396,   384,   399,
      23,    24,    25,    26,    27,    28,    29,    30,   300,    31,
      32,    33,    34,    35,    36,    37,    38,    39,    40,   102,
     113,   400,   114,   401,   402,    23,    24,    25,    26,   397,
     271,   375,   298,   212,    31,    32,    33,    34,    35,    36,
      37,    38,    39,    40,   102,   303,    23,    24,    25,    26,
     217,   242,   218,   130,   189,    31,    32,    33,    34,    35,
      36,    37,    38,    39,    40,   102,   126,    23,    24,    25,
      26,    27,    28,    29,    30,   216,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    23,    24,    25,    26,
      13,    99,   226,   225,     0,    31,    32,    33,    34,    35,
      36,    37,    38,    39,    40,   102,   251,     0,     0,     0,
     317,    23,    24,    25,    26,    27,    28,    29,    30,   257,
      31,    32,    33,    34,    35,    36,    37,    38,    39,    40,
     102,   113,     0,   114,    23,    24,    25,    26,     0,     0,
       0,     0,     0,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,   102
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      82,    15,    15,    15,    15,    87,    88,    85,    86,    85,
      92,   101,   101,    89,   101,   101,   101,   101,   171,    24,
      28,    28,   171,   228,     4,    28,    28,     6,   175,    16,
     213,    46,    87,    88,   367,    28,    44,    44,     5,    54,
      55,    44,    44,    87,   363,    89,   128,   380,   381,   382,
     383,    44,   235,   372,   373,    44,    37,    87,    88,    44,
      41,    48,   209,    52,    44,    44,   271,    52,    38,    39,
      40,   224,     3,   155,    49,   224,   158,    44,    86,    86,
      86,    56,    87,    86,    86,    91,    87,   101,   101,   101,
     101,    92,    59,    60,    61,    62,    63,    64,    65,    66,
      67,    68,    44,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,     0,   267,    10,    14,    84,     7,    86,
     202,    85,    17,    21,    89,   215,   215,    44,   215,   215,
     215,   215,    37,    44,    50,    51,    41,    42,    38,    27,
      40,    29,    30,    43,    29,    30,   224,    85,   224,    46,
      61,    62,    63,    64,    65,    66,    67,    68,    44,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    91,    83,    44,    46,    86,   190,   190,   190,   190,
      91,    31,    32,    33,    34,    35,   268,   269,    46,    46,
      36,    46,    86,    86,    46,    46,    44,    81,   280,    91,
      91,   215,   215,   215,   215,    85,    89,    85,    57,    90,
      47,    90,    80,    90,   296,    61,    62,    63,    64,    92,
      87,    44,   304,    88,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    44,    45,   319,   320,    92,
       8,    58,     9,    90,    53,    90,   328,    44,    69,    44,
      59,    60,    61,    62,    63,    64,    65,    66,    67,    68,
      44,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,   353,    44,    86,    82,    84,    11,    86,    85,    83,
      46,    53,    13,    17,    85,    44,    88,    59,    60,    61,
      62,    63,    64,    65,    66,    67,    68,    44,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    44,    20,
      15,   393,    84,    46,    86,    44,    46,    86,    30,    18,
      48,    86,    25,    29,    44,    61,    62,    63,    64,    65,
      66,    67,    68,    89,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    44,    83,    85,    89,
      86,    47,    19,    85,    26,    46,    12,    22,    27,    86,
      85,    87,    85,    61,    62,    63,    64,    89,    89,    87,
      89,    88,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    44,    85,    85,    88,    44,    86,    23,
      85,    88,    85,    85,    44,    89,    27,    44,    88,    44,
      61,    62,    63,    64,    65,    66,    67,    68,    44,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    85,    83,    27,    44,    61,    62,    63,    64,    44,
     262,   365,   276,   179,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,   284,    61,    62,    63,    64,
      44,   215,   190,    91,   158,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    88,    61,    62,    63,
      64,    65,    66,    67,    68,   184,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    61,    62,    63,    64,
       6,    79,   202,   201,    -1,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,   222,    -1,    -1,    -1,
      85,    61,    62,    63,    64,    65,    66,    67,    68,   226,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    -1,    83,    61,    62,    63,    64,    -1,    -1,
      -1,    -1,    -1,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,    94,   221,   222,    44,   223,   224,   225,     0,
       7,    95,     4,   224,    85,    89,    44,    96,    85,     5,
      44,    59,    60,    61,    62,    63,    64,    65,    66,    67,
      68,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    84,    86,   154,   155,   156,   157,   158,   159,   160,
     165,   166,   167,   168,   169,   170,   171,   172,   173,   174,
     181,   182,   183,   184,   185,   187,   188,   189,   191,   192,
     194,   217,   218,   219,   220,   226,   227,    97,    44,   228,
     229,   230,    91,    44,   190,    46,    46,    46,    86,    46,
      46,    46,    91,    49,    56,    98,    99,   145,     6,   229,
      85,    89,    80,   170,   173,   178,   179,   181,   186,   195,
      87,    88,    44,    81,    83,    86,   174,   175,   176,   177,
     178,   180,   204,   213,   175,   178,   186,   213,    91,   197,
     176,   178,    57,   100,   101,   102,   146,   147,    50,    51,
     148,    85,    53,   154,   161,   162,   163,   165,   182,   187,
     191,   217,   231,    90,    90,    47,    80,    90,    87,    92,
      44,    90,   205,    90,    88,   175,   178,   198,   199,   201,
     202,   203,   213,    92,     8,     9,   103,   105,    58,   149,
      44,   150,   151,   152,    46,    54,    55,   164,   178,   195,
      69,    44,    44,   206,   207,   208,   209,   210,   211,   212,
      82,    86,    87,    92,   200,   197,   204,    96,   104,    11,
     106,   121,   150,    85,    87,    89,   177,    44,   168,   170,
     193,   196,    87,    88,    46,   201,   199,    17,   109,   110,
      96,   107,    13,   122,   141,    85,    52,   151,    44,    45,
     153,   154,   161,   165,   182,   187,   191,   214,   215,   216,
     217,   207,   175,   197,   204,   213,    88,   200,    44,    10,
     110,    20,   120,    44,    15,   142,    52,    46,    46,    86,
     108,   109,    30,   123,   124,    48,   143,   144,   204,    44,
      86,   111,   112,   113,   114,   115,   178,   178,    18,   116,
      89,    25,   128,   129,    29,   125,    44,    16,   144,    85,
      44,   178,    85,   115,    47,    89,    19,   118,    38,    39,
      40,   126,    89,    26,   131,    89,    85,    85,   178,    87,
      87,   178,    31,    32,    33,    34,    35,   117,    46,    12,
      37,    41,   130,    89,   133,    37,    41,    42,   127,    85,
     178,   178,    88,    85,    36,   119,   178,    85,    38,    40,
      43,   132,    14,    21,   134,    88,    88,    85,    85,    85,
     178,    89,    22,    28,    44,    86,   136,   138,   139,    44,
     138,    27,    29,    30,   137,   136,    23,   137,    28,    44,
      44,   138,    44,   138,    88,    44,   140,    44,   137,   137,
     137,   137,    27,    24,    87,   135,    44,    44,   178,    44,
      85,    27,    44
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,    93,    94,    95,    96,    97,    97,    98,    99,    99,
     100,   101,   102,   102,   103,   104,   105,   105,   106,   107,
     108,   108,   109,   109,   110,   111,   111,   112,   112,   113,
     114,   114,   115,   115,   116,   117,   117,   117,   117,   117,
     118,   119,   119,   120,   120,   121,   121,   122,   123,   124,
     124,   125,   125,   126,   126,   126,   127,   127,   127,   128,
     128,   129,   130,   130,   131,   132,   132,   132,   133,   133,
     134,   135,   135,   135,   136,   136,   137,   137,   137,   137,
     137,   138,   138,   138,   138,   139,   139,   140,   140,   140,
     140,   141,   141,   142,   143,   143,   144,   144,   145,   145,
     146,   147,   148,   148,   148,   149,   149,   150,   150,   151,
     151,   152,   152,   153,   153,   153,   153,   153,   153,   153,
     154,   154,   155,   156,   156,   156,   157,   158,   159,   160,
     161,   161,   162,   163,   163,   164,   164,   165,   165,   166,
     167,   168,   168,   168,   169,   169,   170,   170,   171,   171,
     171,   171,   172,   172,   172,   172,   173,   173,   174,   174,
     174,   174,   175,   175,   175,   175,   176,   177,   178,   178,
     179,   179,   179,   180,   181,   181,   181,   181,   182,   182,
     183,   184,   185,   186,   187,   187,   188,   189,   190,   190,
     191,   191,   192,   193,   193,   194,   195,   195,   196,   197,
     198,   198,   199,   199,   200,   201,   201,   201,   201,   202,
     203,   204,   205,   206,   206,   207,   207,   207,   207,   208,
     209,   210,   211,   212,   213,   213,   214,   214,   215,   216,
     217,   217,   218,   218,   219,   220,   220,   221,   221,   222,
     223,   223,   224,   225,   226,   226,   226,   226,   226,   226,
     226,   227,   228,   228,   229,   230,   231,   231,   231,   231,
     231,   231,   231
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     2,     6,     1,     0,     2,     5,     0,     2,
       1,     4,     0,     2,     4,     1,     0,     2,     7,     1,
       0,     1,     1,     2,     4,     2,     2,     1,     1,     1,
       1,     2,     5,     5,     4,     1,     1,     1,     1,     1,
       4,     1,     1,     0,     7,     0,     2,     7,     3,     0,
       3,     0,     3,     1,     1,     1,     1,     1,     1,     0,
       1,     4,     1,     1,     4,     1,     1,     1,     0,     2,
       9,     0,     2,     2,     2,     2,     0,     3,     3,     3,
       3,     2,     2,     1,     3,     3,     4,     3,     1,     5,
       3,     0,     2,     3,     1,     2,     3,     4,     1,     1,
       5,     1,     0,     1,     1,     0,     1,     1,     3,     3,
       3,     1,     3,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     3,     3,     3,     1,
       1,     1,     1,     2,     3,     1,     1,     1,     1,     1,
       3,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     2,
       2,     2,     2,     3,     1,     1,     1,     1,     1,     1,
       4,     1,     3,     3,     1,     1,     3,     3,     1,     3,
       1,     1,     6,     1,     1,     3,     1,     3,     1,     3,
       2,     4,     1,     1,     0,     1,     1,     2,     2,     4,
       0,     4,     0,     1,     3,     1,     1,     1,     1,     3,
       3,     3,     3,     1,     1,     3,     1,     1,     3,     1,
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


  private static final int YYLAST_ = 553;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 9;
  private static final int YYNTOKENS_ = 93;

/* Unqualified %code blocks.  */
/* "src/main/java/parser/Parser.y":24  */

    private ContextHandler contexts;

/* "src/main/java/parser/Parser.java":3215  */

}
/* "src/main/java/parser/Parser.y":1488  */
