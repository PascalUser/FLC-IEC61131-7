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
    import parser.publishers.*;
    import parser.initializations.*;
    import parser.initializations.leafs.*;
    import parser.initializations.nodes.*;
    import parser.initializations.nodes.RepeatedInitialization.Interval;

/* "src/main/java/parser/Parser.java":64  */

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
    S_fuzzify_block_name(106),     /* fuzzify_block_name  */
    S_opt_defuzzify_block_list(107), /* opt_defuzzify_block_list  */
    S_defuzzify_block(108),        /* defuzzify_block  */
    S_defuzzify_block_name(109),   /* defuzzify_block_name  */
    S_opt_linguistic_term_list(110), /* opt_linguistic_term_list  */
    S_linguistic_term_list(111),   /* linguistic_term_list  */
    S_linguistic_term(112),        /* linguistic_term  */
    S_linguistic_term_value(113),  /* linguistic_term_value  */
    S_membership_function(114),    /* membership_function  */
    S_singleton(115),              /* singleton  */
    S_point_list(116),             /* point_list  */
    S_point(117),                  /* point  */
    S_defuzzification_method(118), /* defuzzification_method  */
    S_defuzz_method(119),          /* defuzz_method  */
    S_default_value(120),          /* default_value  */
    S_default_val(121),            /* default_val  */
    S_opt_range(122),              /* opt_range  */
    S_opt_rule_block_list(123),    /* opt_rule_block_list  */
    S_rule_block(124),             /* rule_block  */
    S_operator_definition(125),    /* operator_definition  */
    S_opt_operator_or(126),        /* opt_operator_or  */
    S_operator_and_opt(127),       /* operator_and_opt  */
    S_or_type(128),                /* or_type  */
    S_and_type(129),               /* and_type  */
    S_activation_method_opt(130),  /* activation_method_opt  */
    S_activation_method(131),      /* activation_method  */
    S_act_type(132),               /* act_type  */
    S_accumulation_method(133),    /* accumulation_method  */
    S_accu_type(134),              /* accu_type  */
    S_rule_list(135),              /* rule_list  */
    S_rule(136),                   /* rule  */
    S_opt_weighting(137),          /* opt_weighting  */
    S_condition(138),              /* condition  */
    S_condition_tail(139),         /* condition_tail  */
    S_x(140),                      /* x  */
    S_subcondition(141),           /* subcondition  */
    S_conclusion_list(142),        /* conclusion_list  */
    S_opt_option_block_list(143),  /* opt_option_block_list  */
    S_option_block(144),           /* option_block  */
    S_pragma_list(145),            /* pragma_list  */
    S_pragma(146),                 /* pragma  */
    S_var_declarations(147),       /* var_declarations  */
    S_var_id_decl(148),            /* var_id_decl  */
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
    S_boolean_spec_init(160),      /* boolean_spec_init  */
    S_boolean_specification(161),  /* boolean_specification  */
    S_initialized_boolean(162),    /* initialized_boolean  */
    S_edge(163),                   /* edge  */
    S_simple_spec_init(164),       /* simple_spec_init  */
    S_simple_specification(165),   /* simple_specification  */
    S_initialized_simple(166),     /* initialized_simple  */
    S_elementary_type(167),        /* elementary_type  */
    S_numeric_type(168),           /* numeric_type  */
    S_integer_type(169),           /* integer_type  */
    S_signed_integer_type(170),    /* signed_integer_type  */
    S_unsigned_integer_type(171),  /* unsigned_integer_type  */
    S_real_type(172),              /* real_type  */
    S_date_type(173),              /* date_type  */
    S_constant(174),               /* constant  */
    S_string_constant(175),        /* string_constant  */
    S_boolean_constant(176),       /* boolean_constant  */
    S_numeric_constant(177),       /* numeric_constant  */
    S_prefix_type(178),            /* prefix_type  */
    S_time_constant(179),          /* time_constant  */
    S_bit_string_type(180),        /* bit_string_type  */
    S_subrange_spec_init(181),     /* subrange_spec_init  */
    S_subrange_specification(182), /* subrange_specification  */
    S_subrange_type_decl(183),     /* subrange_type_decl  */
    S_initialized_subrange(184),   /* initialized_subrange  */
    S_range(185),                  /* range  */
    S_enumerated_spec_init(186),   /* enumerated_spec_init  */
    S_enumerated_specification(187), /* enumerated_specification  */
    S_initialized_enumerated(188), /* initialized_enumerated  */
    S_array_spec_init(189),        /* array_spec_init  */
    S_array_specification(190),    /* array_specification  */
    S_initialized_array(191),      /* initialized_array  */
    S_range_list(192),             /* range_list  */
    S_non_generic_type(193),       /* non_generic_type  */
    S_array_initialization(194),   /* array_initialization  */
    S_array_initial_elements_list(195), /* array_initial_elements_list  */
    S_array_initial_elements(196), /* array_initial_elements  */
    S_array_initial_element(197),  /* array_initial_element  */
    S_repeated_initial_element(198), /* repeated_initial_element  */
    S_structure_initialization(199), /* structure_initialization  */
    S_structure_field_init_list(200), /* structure_field_init_list  */
    S_initialized_struct_field(201), /* initialized_struct_field  */
    S_initialized_field_with_constant(202), /* initialized_field_with_constant  */
    S_initialized_field_with_identifier(203), /* initialized_field_with_identifier  */
    S_initialized_field_with_array(204), /* initialized_field_with_array  */
    S_initialized_field_with_structure(205), /* initialized_field_with_structure  */
    S_referenced_identifier(206),  /* referenced_identifier  */
    S_standard_function_block_spec_init(207), /* standard_function_block_spec_init  */
    S_initialized_standard_function_block(208), /* initialized_standard_function_block  */
    S_standard_function_block_specification(209), /* standard_function_block_specification  */
    S_string_spec_init(210),       /* string_spec_init  */
    S_string_specification(211),   /* string_specification  */
    S_initialized_string(212),     /* initialized_string  */
    S_string_type(213),            /* string_type  */
    S_opt_data_type_declaration(214), /* opt_data_type_declaration  */
    S_data_type_declaration(215),  /* data_type_declaration  */
    S_type_declaration_list(216),  /* type_declaration_list  */
    S_type_declaration(217),       /* type_declaration  */
    S_type_name_declaration(218),  /* type_name_declaration  */
    S_type_spec_init(219),         /* type_spec_init  */
    S_structure_specification(220), /* structure_specification  */
    S_structure_field_init_decl_list(221), /* structure_field_init_decl_list  */
    S_structure_field_init_decl(222), /* structure_field_init_decl  */
    S_field_name(223),             /* field_name  */
    S_structure_field_spec_init(224); /* structure_field_spec_init  */


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
      SymbolKind.S_subrange_type_decl,
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
  "fuzzify_block_name", "opt_defuzzify_block_list", "defuzzify_block",
  "defuzzify_block_name", "opt_linguistic_term_list",
  "linguistic_term_list", "linguistic_term", "linguistic_term_value",
  "membership_function", "singleton", "point_list", "point",
  "defuzzification_method", "defuzz_method", "default_value",
  "default_val", "opt_range", "opt_rule_block_list", "rule_block",
  "operator_definition", "opt_operator_or", "operator_and_opt", "or_type",
  "and_type", "activation_method_opt", "activation_method", "act_type",
  "accumulation_method", "accu_type", "rule_list", "rule", "opt_weighting",
  "condition", "condition_tail", "x", "subcondition", "conclusion_list",
  "opt_option_block_list", "option_block", "pragma_list", "pragma",
  "var_declarations", "var_id_decl", "var_constant_spec",
  "var_init_decl_list", "var_init_decl", "identifier_list",
  "var_spec_init", "custom_spec_init", "custom_specification",
  "initialized_custom", "initialized_custom_with_constant",
  "initialized_custom_with_identifier",
  "initialized_custom_with_structure", "boolean_spec_init",
  "boolean_specification", "initialized_boolean", "edge",
  "simple_spec_init", "simple_specification", "initialized_simple",
  "elementary_type", "numeric_type", "integer_type", "signed_integer_type",
  "unsigned_integer_type", "real_type", "date_type", "constant",
  "string_constant", "boolean_constant", "numeric_constant", "prefix_type",
  "time_constant", "bit_string_type", "subrange_spec_init",
  "subrange_specification", "subrange_type_decl", "initialized_subrange",
  "range", "enumerated_spec_init", "enumerated_specification",
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
    /* "src/main/java/parser/Parser.y":174  */
    {
        this.programName = ((String)(yystack.valueAt (0)));
    };
  break;


  case 7: /* fb_io_var_declarations: io_var_decl var_retain_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 7)
    /* "src/main/java/parser/Parser.y":186  */
    {
        ((DeclarationBlock)(yystack.valueAt (2))).source(((Source)(yystack.valueAt (4)))).use(Use.VARIABLE).setDeclarationScope(programName);
        ((DeclarationBlock)(yystack.valueAt (2))).publish();
    };
  break;


  case 8: /* io_var_decl: VAR_INPUT  */
  if (yyn == 8)
    /* "src/main/java/parser/Parser.y":194  */
    {
        yyval = Source.IN;
    };
  break;


  case 9: /* io_var_decl: VAR_OUTPUT  */
  if (yyn == 9)
    /* "src/main/java/parser/Parser.y":198  */
    {
        yyval = Source.OUT;
    };
  break;


  case 11: /* var_retain_spec: RETAIN  */
  if (yyn == 11)
    /* "src/main/java/parser/Parser.y":206  */
    {
        /**
         * Marks the variable declaration as RETAIN (persistent across power cycles).
         * TODO: Implement retain specification in context metadata.
        **/

        // TODO: ctx with retain spec
    };
  break;


  case 12: /* var_retain_spec: NON_RETAIN  */
  if (yyn == 12)
    /* "src/main/java/parser/Parser.y":215  */
    {
        /**
         * Marks the variable declaration as NON_RETAIN (not persistent).
         * TODO: Implement non-retain specification in context metadata.
        **/

        // TODO: ctx with non retain spec
    };
  break;


  case 20: /* fuzzify_block_name: function_block_name  */
  if (yyn == 20)
    /* "src/main/java/parser/Parser.y":256  */
    {
    };
  break;


  case 24: /* defuzzify_block_name: function_block_name  */
  if (yyn == 24)
    /* "src/main/java/parser/Parser.y":276  */
    {
    };
  break;


  case 26: /* opt_linguistic_term_list: linguistic_term_list  */
  if (yyn == 26)
    /* "src/main/java/parser/Parser.y":283  */
    {
    };
  break;


  case 27: /* linguistic_term_list: linguistic_term  */
  if (yyn == 27)
    /* "src/main/java/parser/Parser.y":289  */
    {
        /**
         * Starts a new list of linguistic terms with the first term.
        **/

        List<String> terms = new ArrayList<>();
        terms.add(((String)(yystack.valueAt (0))));
        yyval = terms;
    };
  break;


  case 28: /* linguistic_term_list: linguistic_term_list linguistic_term  */
  if (yyn == 28)
    /* "src/main/java/parser/Parser.y":299  */
    {
        /**
         * Appends another linguistic term to the existing list.
        **/

        ((List<String>)(yystack.valueAt (1))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (1)));
    };
  break;


  case 29: /* linguistic_term: TERM IDENTIFIER ASSIGN_OP linguistic_term_value  */
  if (yyn == 29)
    /* "src/main/java/parser/Parser.y":311  */
    {
        yyval = null;
    };
  break;


  case 103: /* var_declarations: var_id_decl var_constant_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":493  */
    {
        ((DeclarationBlock)(yystack.valueAt (2))).source(Source.INTERNAL).use(((Use)(yystack.valueAt (3)))).setDeclarationScope(programName);
        ((DeclarationBlock)(yystack.valueAt (2))).publish();
    };
  break;


  case 105: /* var_constant_spec: %empty  */
  if (yyn == 105)
    /* "src/main/java/parser/Parser.y":505  */
    {
        /**
         * Default variable specification (non-constant).
         * TODO: Implement non-constant specification in context metadata.
        **/

        yyval = Use.VARIABLE;
    };
  break;


  case 106: /* var_constant_spec: CONSTANT  */
  if (yyn == 106)
    /* "src/main/java/parser/Parser.y":514  */
    {
        /**
         * Marks the variable declaration as CONSTANT (immutable value).
         * TODO: Implement constant specification in context metadata.
        **/

        yyval = Use.CONSTANT;
    };
  break;


  case 107: /* var_init_decl_list: var_init_decl  */
  if (yyn == 107)
    /* "src/main/java/parser/Parser.y":526  */
    {
        DeclarationBlock block = new DeclarationBlock();
        block.addDeclaration(((MultipleDeclaration)(yystack.valueAt (0))));
        yyval = block;
    };
  break;


  case 108: /* var_init_decl_list: var_init_decl_list ';' var_init_decl  */
  if (yyn == 108)
    /* "src/main/java/parser/Parser.y":532  */
    {
        ((DeclarationBlock)(yystack.valueAt (2))).addDeclaration(((MultipleDeclaration)(yystack.valueAt (0))));
        yyval = ((DeclarationBlock)(yystack.valueAt (2)));
    };
  break;


  case 109: /* var_init_decl: identifier_list ':' var_spec_init  */
  if (yyn == 109)
    /* "src/main/java/parser/Parser.y":540  */
    {
        ((MultipleDeclaration)(yystack.valueAt (0))).identifiers(((List<String>)(yystack.valueAt (2))));
        yyval = ((MultipleDeclaration)(yystack.valueAt (0)));
    };
  break;


  case 110: /* var_init_decl: identifier_list ':' standard_function_block_spec_init  */
  if (yyn == 110)
    /* "src/main/java/parser/Parser.y":545  */
    {
        // TODO: remove null
        yyval = null;
    };
  break;


  case 111: /* identifier_list: IDENTIFIER  */
  if (yyn == 111)
    /* "src/main/java/parser/Parser.y":553  */
    {
        List<String> identifiers = new ArrayList<>();
        identifiers.add(((String)(yystack.valueAt (0))));
        yyval = identifiers;
    };
  break;


  case 112: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 112)
    /* "src/main/java/parser/Parser.y":559  */
    {
        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 113: /* var_spec_init: custom_spec_init  */
  if (yyn == 113)
    /* "src/main/java/parser/Parser.y":566  */
                            { /** Wraps the custom type builder in a MultipleDeclaration for variable list handling. */ yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 114: /* var_spec_init: boolean_spec_init  */
  if (yyn == 114)
    /* "src/main/java/parser/Parser.y":567  */
                            { /** Wraps the boolean builder in a MultipleDeclaration for variable list handling. */ yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 115: /* var_spec_init: simple_spec_init  */
  if (yyn == 115)
    /* "src/main/java/parser/Parser.y":568  */
                            { /** Wraps the simple type builder in a MultipleDeclaration for variable list handling. */ yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 116: /* var_spec_init: subrange_spec_init  */
  if (yyn == 116)
    /* "src/main/java/parser/Parser.y":569  */
                            { /** Subrange not yet supported for variable lists; returns null. */ yyval = null; };
  break;


  case 117: /* var_spec_init: enumerated_spec_init  */
  if (yyn == 117)
    /* "src/main/java/parser/Parser.y":570  */
                            { /** Wraps the enumerated builder in an AnonymousEnumeratedVariable for variable list handling. */ yyval = new AnnonymousEnumeratedVariable(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 118: /* var_spec_init: array_spec_init  */
  if (yyn == 118)
    /* "src/main/java/parser/Parser.y":571  */
                            { /** Wraps the array builder in a MultipleDeclaration for variable list handling. */ yyval = new MultipleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 119: /* var_spec_init: string_spec_init  */
  if (yyn == 119)
    /* "src/main/java/parser/Parser.y":572  */
                            { /** String not yet supported for variable lists; returns null. */ yyval = null; };
  break;


  case 120: /* custom_spec_init: custom_specification  */
  if (yyn == 120)
    /* "src/main/java/parser/Parser.y":576  */
                            { /** Propagates the custom type specification builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 121: /* custom_spec_init: initialized_custom  */
  if (yyn == 121)
    /* "src/main/java/parser/Parser.y":577  */
                            { /** Propagates the initialized custom builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 122: /* custom_specification: IDENTIFIER  */
  if (yyn == 122)
    /* "src/main/java/parser/Parser.y":582  */
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        LexemeInfoBuilder customDeclaration = new LexemeInfoBuilder();
        Director.makeCustom(customDeclaration, this.symbolTable, ((String)(yystack.valueAt (0))));
        yyval = customDeclaration;
    };
  break;


  case 123: /* initialized_custom: initialized_custom_with_constant  */
  if (yyn == 123)
    /* "src/main/java/parser/Parser.y":594  */
                                            { /** Propagates the custom initialization with constant value. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 124: /* initialized_custom: initialized_custom_with_identifier  */
  if (yyn == 124)
    /* "src/main/java/parser/Parser.y":595  */
                                            { /** Propagates the custom initialization with identifier reference. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 125: /* initialized_custom: initialized_custom_with_structure  */
  if (yyn == 125)
    /* "src/main/java/parser/Parser.y":596  */
                                            { /** Propagates the custom initialization with structure value. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 126: /* initialized_custom_with_constant: custom_specification ASSIGN_OP constant  */
  if (yyn == 126)
    /* "src/main/java/parser/Parser.y":601  */
    {
       ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
       yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 127: /* initialized_custom_with_identifier: custom_specification ASSIGN_OP referenced_identifier  */
  if (yyn == 127)
    /* "src/main/java/parser/Parser.y":609  */
    {
        /**
         * Searches if the enumerated value is valid and in that cases loads the initial value and drops the search
         * scope added
        **/

        // TODO: check if it the referenced identifier is valid
        // In order to do that, the custom type from custom specification needs to be retrieved, then, the referenced
        // identifier needs to be in it's parameters list and the custom type has to be an enumerated.

        LexemeInfo metadata = this.symbolTable.get(((LexemeInfoBuilder)(yystack.valueAt (2))).customType());
        if (metadata.type.equals(Type.ENUMERATE) && metadata.parameters.contains(((String)(yystack.valueAt (0))))) {
            ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(((LexemeInfoBuilder)(yystack.valueAt (2))).customType() + "#" + ((String)(yystack.valueAt (0)))));
        }

        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 128: /* initialized_custom_with_structure: custom_specification ASSIGN_OP structure_initialization  */
  if (yyn == 128)
    /* "src/main/java/parser/Parser.y":630  */
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


  case 129: /* boolean_spec_init: boolean_specification  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":644  */
                            { /** Propagates the boolean type specification builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 130: /* boolean_spec_init: initialized_boolean  */
  if (yyn == 130)
    /* "src/main/java/parser/Parser.y":645  */
                            { /** Propagates the initialized boolean builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 131: /* boolean_specification: BOOL  */
  if (yyn == 131)
    /* "src/main/java/parser/Parser.y":650  */
    {
        /**
         * Loads the left identifiers type and subtype
        **/

        LexemeInfoBuilder primitiveBuilder = new LexemeInfoBuilder();
        Director.makePrimitive(primitiveBuilder, symbolTable, Subtype.BOOL);
        yyval = primitiveBuilder;
    };
  break;


  case 132: /* initialized_boolean: boolean_specification edge  */
  if (yyn == 132)
    /* "src/main/java/parser/Parser.y":663  */
    {
        // TODO: add the edge specification
        yyval = null;
    };
  break;


  case 133: /* initialized_boolean: boolean_specification ASSIGN_OP boolean_constant  */
  if (yyn == 133)
    /* "src/main/java/parser/Parser.y":668  */
    {
        /**
         * Builds a boolean variable initialization from the assigned constant
         * and stores it in the current parsing context.
        **/

        ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 136: /* simple_spec_init: simple_specification  */
  if (yyn == 136)
    /* "src/main/java/parser/Parser.y":686  */
                            { /** Propagates the simple type specification builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 137: /* simple_spec_init: initialized_simple  */
  if (yyn == 137)
    /* "src/main/java/parser/Parser.y":687  */
                            { /** Propagates the initialized simple builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 138: /* simple_specification: elementary_type  */
  if (yyn == 138)
    /* "src/main/java/parser/Parser.y":692  */
    {
        /**
         * Loads the left identifiers type and subtype
        **/

        LexemeInfoBuilder primitiveBuilder = new LexemeInfoBuilder();
        Director.makePrimitive(primitiveBuilder, this.symbolTable, ((Subtype)(yystack.valueAt (0))));
        yyval = primitiveBuilder;
    };
  break;


  case 139: /* initialized_simple: simple_specification ASSIGN_OP constant  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":705  */
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
    };
  break;


  case 140: /* elementary_type: numeric_type  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":717  */
                        { /** Propagates the numeric type name. */    yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 141: /* elementary_type: date_type  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":718  */
                        { /** Propagates the date type name. */       yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 142: /* elementary_type: bit_string_type  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":719  */
                        { /** Propagates the bit string type name. */ yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 143: /* numeric_type: integer_type  */
  if (yyn == 143)
    /* "src/main/java/parser/Parser.y":723  */
                    { /** Propagates the integer type name. */ yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 144: /* numeric_type: real_type  */
  if (yyn == 144)
    /* "src/main/java/parser/Parser.y":724  */
                    { /** Propagates the real type name. */    yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 145: /* integer_type: signed_integer_type  */
  if (yyn == 145)
    /* "src/main/java/parser/Parser.y":728  */
                                { /** Propagates the signed integer type name.   */ yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 146: /* integer_type: unsigned_integer_type  */
  if (yyn == 146)
    /* "src/main/java/parser/Parser.y":729  */
                                { /** Propagates the unsigned integer type name. */ yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 147: /* signed_integer_type: SINT  */
  if (yyn == 147)
    /* "src/main/java/parser/Parser.y":733  */
            { /** Sets the subtype to SINT. */ yyval = Subtype.SINT; };
  break;


  case 148: /* signed_integer_type: INT  */
  if (yyn == 148)
    /* "src/main/java/parser/Parser.y":734  */
            { /** Sets the subtype to INT.  */ yyval = Subtype.INT;  };
  break;


  case 149: /* signed_integer_type: DINT  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":735  */
            { /** Sets the subtype to DINT. */ yyval = Subtype.DINT; };
  break;


  case 150: /* signed_integer_type: LINT  */
  if (yyn == 150)
    /* "src/main/java/parser/Parser.y":736  */
            { /** Sets the subtype to LINT. */ yyval = Subtype.LINT; };
  break;


  case 151: /* unsigned_integer_type: USINT  */
  if (yyn == 151)
    /* "src/main/java/parser/Parser.y":740  */
            { /** Sets the subtype to USINT. */ yyval = Subtype.USINT; };
  break;


  case 152: /* unsigned_integer_type: UINT  */
  if (yyn == 152)
    /* "src/main/java/parser/Parser.y":741  */
            { /** Sets the subtype to UINT.  */ yyval = Subtype.UINT;  };
  break;


  case 153: /* unsigned_integer_type: UDINT  */
  if (yyn == 153)
    /* "src/main/java/parser/Parser.y":742  */
            { /** Sets the subtype to UDINT. */ yyval = Subtype.UDINT; };
  break;


  case 154: /* unsigned_integer_type: ULINT  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":743  */
            { /** Sets the subtype to ULINT. */ yyval = Subtype.ULINT; };
  break;


  case 155: /* real_type: REAL  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":747  */
            { /** Sets the subtype to REAL.  */ yyval = Subtype.REAL;  };
  break;


  case 156: /* real_type: LREAL  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":748  */
            { /** Sets the subtype to LREAL. */ yyval = Subtype.LREAL; };
  break;


  case 157: /* date_type: TIME  */
  if (yyn == 157)
    /* "src/main/java/parser/Parser.y":752  */
                    { /** Sets the subtype to TIME. */ yyval = Subtype.TIME; };
  break;


  case 158: /* date_type: DATE  */
  if (yyn == 158)
    /* "src/main/java/parser/Parser.y":753  */
                    { /** Sets the subtype to DATE. */ yyval = Subtype.DATE; };
  break;


  case 159: /* date_type: TIME_OF_DAY  */
  if (yyn == 159)
    /* "src/main/java/parser/Parser.y":754  */
                    { /** Sets the subtype to TIME_OF_DAY.   */ yyval = Subtype.TIME_OF_DAY;   };
  break;


  case 160: /* date_type: DATE_AND_TIME  */
  if (yyn == 160)
    /* "src/main/java/parser/Parser.y":755  */
                    { /** Sets the subtype to DATE_AND_TIME. */ yyval = Subtype.DATE_AND_TIME; };
  break;


  case 161: /* constant: string_constant  */
  if (yyn == 161)
    /* "src/main/java/parser/Parser.y":761  */
                       { /** Propagates the string constant.  */ yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 162: /* constant: boolean_constant  */
  if (yyn == 162)
    /* "src/main/java/parser/Parser.y":762  */
                       { /** Propagates the boolean constant. */ yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 163: /* constant: time_constant  */
  if (yyn == 163)
    /* "src/main/java/parser/Parser.y":763  */
                       { /** Propagates the time constant.    */ yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 164: /* constant: numeric_constant  */
  if (yyn == 164)
    /* "src/main/java/parser/Parser.y":764  */
                       { /** Propagates the numeric constant. */ yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 165: /* string_constant: STRING_LITERAL  */
  if (yyn == 165)
    /* "src/main/java/parser/Parser.y":768  */
                    { /** Propagates the string literal. */   yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 166: /* boolean_constant: BOOLEAN_LITERAL  */
  if (yyn == 166)
    /* "src/main/java/parser/Parser.y":772  */
                    { /** Propagates the boolean literal. */ yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 167: /* numeric_constant: NUMERIC_LITERAL  */
  if (yyn == 167)
    /* "src/main/java/parser/Parser.y":777  */
    {
        /** Propagates the numeric literal. */

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 168: /* numeric_constant: prefix_type '#' NUMERIC_LITERAL  */
  if (yyn == 168)
    /* "src/main/java/parser/Parser.y":783  */
    {
        /**
         * Combines a base prefix (e.g., 16#) with a numeric literal to form
         * a based numeric constant. The conversion is deferred to later processing.
        **/

        // TODO: convert this constant in code
        yyval = "";
    };
  break;


  case 169: /* prefix_type: integer_type  */
  if (yyn == 169)
    /* "src/main/java/parser/Parser.y":795  */
                        { /** Propagates the integer type name prefix. */ yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 170: /* prefix_type: real_type  */
  if (yyn == 170)
    /* "src/main/java/parser/Parser.y":796  */
                        { /** Propagates the real type name prefix.    */ yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 171: /* prefix_type: bit_string_type  */
  if (yyn == 171)
    /* "src/main/java/parser/Parser.y":797  */
                        { /** Propagates the bit string type name prefix. */ yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 172: /* time_constant: date_type '#' TIME_LITERAL  */
  if (yyn == 172)
    /* "src/main/java/parser/Parser.y":802  */
    {
        /**
         * Combines a date/time type prefix with a time literal.
         * TODO: Add semantic action to verify prefix matches time_literal type.
        **/

        // TODO: semantic action to verify prefix matches time_literal type
    };
  break;


  case 173: /* bit_string_type: BYTE  */
  if (yyn == 173)
    /* "src/main/java/parser/Parser.y":813  */
            { /** Sets the subtype to BYTE.  */ yyval = Subtype.BYTE;  };
  break;


  case 174: /* bit_string_type: WORD  */
  if (yyn == 174)
    /* "src/main/java/parser/Parser.y":814  */
            { /** Sets the subtype to WORD.  */ yyval = Subtype.WORD;  };
  break;


  case 175: /* bit_string_type: DWORD  */
  if (yyn == 175)
    /* "src/main/java/parser/Parser.y":815  */
            { /** Sets the subtype to DWORD. */ yyval = Subtype.DWORD; };
  break;


  case 176: /* bit_string_type: LWORD  */
  if (yyn == 176)
    /* "src/main/java/parser/Parser.y":816  */
            { /** Sets the subtype to LWORD. */ yyval = Subtype.LWORD; };
  break;


  case 179: /* subrange_specification: subrange_type_decl '(' range ')'  */
  if (yyn == 179)
    /* "src/main/java/parser/Parser.y":828  */
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
    };
  break;


  case 180: /* subrange_type_decl: integer_type  */
  if (yyn == 180)
    /* "src/main/java/parser/Parser.y":846  */
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
    };
  break;


  case 181: /* initialized_subrange: subrange_specification ASSIGN_OP numeric_constant  */
  if (yyn == 181)
    /* "src/main/java/parser/Parser.y":862  */
    {
        /**
         * Loads the left identifiers subrange initialization
        **/

        // ParsingContext ctx = this.contexts.current();
        //  ctx.metadataBuilder().initialValue(new VariableInitialization($3));
    };
  break;


  case 182: /* range: numeric_constant RANGE_OP numeric_constant  */
  if (yyn == 182)
    /* "src/main/java/parser/Parser.y":874  */
    {
        List<String> ranges = new ArrayList<>();
        ranges.add(((String)(yystack.valueAt (2))));
        ranges.add(((String)(yystack.valueAt (0))));
        yyval = ranges;
    };
  break;


  case 183: /* enumerated_spec_init: enumerated_specification  */
  if (yyn == 183)
    /* "src/main/java/parser/Parser.y":883  */
                                { /** Propagates the enumerated type specification builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 184: /* enumerated_spec_init: initialized_enumerated  */
  if (yyn == 184)
    /* "src/main/java/parser/Parser.y":884  */
                                { /** Propagates the initialized enumerated builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 185: /* enumerated_specification: '(' identifier_list ')'  */
  if (yyn == 185)
    /* "src/main/java/parser/Parser.y":889  */
    {
        /**
         * Loads the enumerated metadata to the current context.
        **/

        LexemeInfoBuilder enumBuilder = new LexemeInfoBuilder();
        Director.makeEnumerated(enumBuilder, ((List<String>)(yystack.valueAt (1))));
        yyval = enumBuilder;
    };
  break;


  case 186: /* initialized_enumerated: enumerated_specification ASSIGN_OP referenced_identifier  */
  if (yyn == 186)
    /* "src/main/java/parser/Parser.y":902  */
    {
        // TODO: semantic check over initial value
        // Note: it has to be in the enumerated_specification parameters. The initial value can't be global because
        // it's in a new enumerated specification declaration (type incompatible). However, another enumerated
        // declaration could have declared the same "<programName>#<enumeratedValue>", so, it's important to check that
        // there's no redeclaration.

        ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(new VariableInitialization(programName + "#" + ((String)(yystack.valueAt (0)))));
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2)));
    };
  break;


  case 187: /* array_spec_init: array_specification  */
  if (yyn == 187)
    /* "src/main/java/parser/Parser.y":915  */
                        { /** Propagates the array type specification builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 188: /* array_spec_init: initialized_array  */
  if (yyn == 188)
    /* "src/main/java/parser/Parser.y":916  */
                        { /** Propagates the initialized array builder. */ yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 189: /* array_specification: ARRAY '[' range_list ']' OF IDENTIFIER  */
  if (yyn == 189)
    /* "src/main/java/parser/Parser.y":921  */
    {
        /**
         * Sets the current context type to ARRAY, preparing for array type
         * and dimension processing in the array_type rule.
        **/

        ArrayMetadata arrayMetadata = new ArrayMetadata(Subtype.CUSTOM, ((String)(yystack.valueAt (0))), ((List<String>)(yystack.valueAt (3))));

        LexemeInfoBuilder arrayBuilder = new LexemeInfoBuilder();
        Director.makeArray(arrayBuilder, this.symbolTable, arrayMetadata);
        yyval = arrayBuilder;
    };
  break;


  case 190: /* array_specification: ARRAY '[' range_list ']' OF non_generic_type  */
  if (yyn == 190)
    /* "src/main/java/parser/Parser.y":934  */
    {
        ArrayMetadata arrayMetadata = new ArrayMetadata(((Subtype)(yystack.valueAt (0))), null, ((List<String>)(yystack.valueAt (3))));

        LexemeInfoBuilder arrayBuilder = new LexemeInfoBuilder();
        Director.makeArray(arrayBuilder, this.symbolTable, arrayMetadata);
        yyval = arrayBuilder;
    };
  break;


  case 191: /* initialized_array: array_specification ASSIGN_OP array_initialization  */
  if (yyn == 191)
    /* "src/main/java/parser/Parser.y":945  */
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


  case 192: /* range_list: range  */
  if (yyn == 192)
    /* "src/main/java/parser/Parser.y":958  */
    {
        yyval = ((List<String>)(yystack.valueAt (0)));
    };
  break;


  case 193: /* range_list: range_list ',' range  */
  if (yyn == 193)
    /* "src/main/java/parser/Parser.y":962  */
    {
        ((List<String>)(yystack.valueAt (2))).addAll(((List<String>)(yystack.valueAt (0))));
    };
  break;


  case 195: /* array_initialization: '[' array_initial_elements_list ']'  */
  if (yyn == 195)
    /* "src/main/java/parser/Parser.y":972  */
                                        { yyval = ((ParsingRepeatedArrayContext)(yystack.valueAt (1))).getPathsInfo(); };
  break;


  case 196: /* array_initial_elements_list: array_initial_elements  */
  if (yyn == 196)
    /* "src/main/java/parser/Parser.y":977  */
    {
        int endIdx = ((ParsingRepeatedArrayContext)(yystack.valueAt (0))).getMaxValue() - 1;

        List<PathInitializationInfo> newPathsInfo = new ArrayList<>();
        for (PathInitializationInfo oldPathInfo : ((ParsingRepeatedArrayContext)(yystack.valueAt (0))).getPathsInfo()) {
            newPathsInfo.add(new PathInitializationInfo(
                "0.." + endIdx + "#" + oldPathInfo.name(),
                oldPathInfo.initialValue()
            ));
        }
        yyval = new ParsingRepeatedArrayContext(endIdx, newPathsInfo);
    };
  break;


  case 197: /* array_initial_elements_list: array_initial_elements_list ',' array_initial_elements  */
  if (yyn == 197)
    /* "src/main/java/parser/Parser.y":990  */
    {
        int lastIdx = ((ParsingRepeatedArrayContext)(yystack.valueAt (2))).getMaxValue();
        int startIdx = lastIdx + 1;
        int endIdx = lastIdx + ((ParsingRepeatedArrayContext)(yystack.valueAt (0))).getMaxValue();

        List<PathInitializationInfo> newPathsInfo = ((ParsingRepeatedArrayContext)(yystack.valueAt (2))).getPathsInfo();
        for (PathInitializationInfo oldPathInfo : ((ParsingRepeatedArrayContext)(yystack.valueAt (0))).getPathsInfo()) {
            newPathsInfo.add(new PathInitializationInfo(
                startIdx + ".." + endIdx + "#" + oldPathInfo.name(),
                oldPathInfo.initialValue()
            ));
        }
        yyval = new ParsingRepeatedArrayContext(endIdx, newPathsInfo);
    };
  break;


  case 198: /* array_initial_elements: array_initial_element  */
  if (yyn == 198)
    /* "src/main/java/parser/Parser.y":1007  */
                                { /** Creates a ParsingRepeatedArrayContext for a single array element. */ yyval = new ParsingRepeatedArrayContext(1, ((List<PathInitializationInfo>)(yystack.valueAt (0)))); };
  break;


  case 199: /* array_initial_elements: repeated_initial_element  */
  if (yyn == 199)
    /* "src/main/java/parser/Parser.y":1008  */
                                { /** Propagates the repeated element context. */ yyval = ((ParsingRepeatedArrayContext)(yystack.valueAt (0))); };
  break;


  case 200: /* array_initial_element: constant  */
  if (yyn == 200)
    /* "src/main/java/parser/Parser.y":1013  */
    {
        /**
         * Creates the initialization and adds the context counter by one
        **/

        List<PathInitializationInfo> pathsInfo = new ArrayList<>();
        pathsInfo.add(new PathInitializationInfo("", new VariableInitialization(((String)(yystack.valueAt (0))))));
        yyval = pathsInfo;
    };
  break;


  case 201: /* array_initial_element: referenced_identifier  */
  if (yyn == 201)
    /* "src/main/java/parser/Parser.y":1023  */
    {
        // TODO: see how to check semantics
        List<PathInitializationInfo> pathsInfo = new ArrayList<>();
        pathsInfo.add(new PathInitializationInfo("", new VariableInitialization(((String)(yystack.valueAt (0))))));
        yyval = pathsInfo;
    };
  break;


  case 202: /* array_initial_element: structure_initialization  */
  if (yyn == 202)
    /* "src/main/java/parser/Parser.y":1029  */
                                { /** Propagates the structure initialization paths. */ yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0))); };
  break;


  case 203: /* array_initial_element: array_initialization  */
  if (yyn == 203)
    /* "src/main/java/parser/Parser.y":1030  */
                                { /** Propagates the array initialization paths. */ yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0))); };
  break;


  case 204: /* repeated_initial_element: numeric_constant '(' array_initial_element ')'  */
  if (yyn == 204)
    /* "src/main/java/parser/Parser.y":1035  */
    {
        yyval = new ParsingRepeatedArrayContext(Integer.parseInt(((String)(yystack.valueAt (3)))), ((List<PathInitializationInfo>)(yystack.valueAt (1))));
    };
  break;


  case 205: /* structure_initialization: '(' structure_field_init_list ')'  */
  if (yyn == 205)
    /* "src/main/java/parser/Parser.y":1041  */
                                        { /** Propagates the structure field initialization list. */ yyval = ((List<PathInitializationInfo>)(yystack.valueAt (1))); };
  break;


  case 206: /* structure_field_init_list: initialized_struct_field  */
  if (yyn == 206)
    /* "src/main/java/parser/Parser.y":1046  */
    {
        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0)));
    };
  break;


  case 207: /* structure_field_init_list: structure_field_init_list ',' initialized_struct_field  */
  if (yyn == 207)
    /* "src/main/java/parser/Parser.y":1050  */
    {
        ((List<PathInitializationInfo>)(yystack.valueAt (2))).addAll(((List<PathInitializationInfo>)(yystack.valueAt (0))));
        yyval = ((List<PathInitializationInfo>)(yystack.valueAt (2)));
    };
  break;


  case 208: /* initialized_struct_field: initialized_field_with_constant  */
  if (yyn == 208)
    /* "src/main/java/parser/Parser.y":1057  */
                                        { /** Propagates the field initialization with constant value. */ yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0))); };
  break;


  case 209: /* initialized_struct_field: initialized_field_with_identifier  */
  if (yyn == 209)
    /* "src/main/java/parser/Parser.y":1058  */
                                        { /** Propagates the field initialization with identifier reference. */ yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0))); };
  break;


  case 210: /* initialized_struct_field: initialized_field_with_array  */
  if (yyn == 210)
    /* "src/main/java/parser/Parser.y":1059  */
                                        { /** Propagates the field initialization with array value. */ yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0))); };
  break;


  case 211: /* initialized_struct_field: initialized_field_with_structure  */
  if (yyn == 211)
    /* "src/main/java/parser/Parser.y":1060  */
                                        { /** Propagates the field initialization with structure value. */ yyval = ((List<PathInitializationInfo>)(yystack.valueAt (0))); };
  break;


  case 212: /* initialized_field_with_constant: field_name ASSIGN_OP constant  */
  if (yyn == 212)
    /* "src/main/java/parser/Parser.y":1065  */
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        fieldsInfo.add(new PathInitializationInfo(((String)(yystack.valueAt (2))), new VariableInitialization(((String)(yystack.valueAt (0))))));
        yyval = fieldsInfo;
    };
  break;


  case 213: /* initialized_field_with_identifier: field_name ASSIGN_OP referenced_identifier  */
  if (yyn == 213)
    /* "src/main/java/parser/Parser.y":1074  */
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        fieldsInfo.add(new PathInitializationInfo(((String)(yystack.valueAt (2))), new VariableInitialization(((String)(yystack.valueAt (0))))));
        yyval = fieldsInfo;
    };
  break;


  case 214: /* initialized_field_with_array: field_name ASSIGN_OP array_initialization  */
  if (yyn == 214)
    /* "src/main/java/parser/Parser.y":1083  */
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        for (PathInitializationInfo fieldInfo : ((List<PathInitializationInfo>)(yystack.valueAt (0)))) {
            String fieldName = ((String)(yystack.valueAt (2))) + "#" + fieldInfo.name();
            fieldsInfo.add(new PathInitializationInfo(fieldName, fieldInfo.initialValue()));
        }
        yyval = fieldsInfo;
    };
  break;


  case 215: /* initialized_field_with_structure: field_name ASSIGN_OP structure_initialization  */
  if (yyn == 215)
    /* "src/main/java/parser/Parser.y":1095  */
    {
        List<PathInitializationInfo> fieldsInfo = new ArrayList<>();
        for (PathInitializationInfo fieldInfo : ((List<PathInitializationInfo>)(yystack.valueAt (0)))) {
            String fieldName = ((String)(yystack.valueAt (2))) + "#" + fieldInfo.name();
            fieldsInfo.add(new PathInitializationInfo(fieldName, fieldInfo.initialValue()));
        }
        yyval = fieldsInfo;
    };
  break;


  case 216: /* referenced_identifier: IDENTIFIER  */
  if (yyn == 216)
    /* "src/main/java/parser/Parser.y":1107  */
    {
        /**
         * Simple identifier: keeps the lexeme as-is if it's global, else assumes it is local.
        **/

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 217: /* referenced_identifier: IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 217)
    /* "src/main/java/parser/Parser.y":1115  */
    {
        /**
         * Mangled identifier: joins both identifiers with '#'.
        **/

        yyval = ((String)(yystack.valueAt (2))) + "#" + ((String)(yystack.valueAt (0)));
    };
  break;


  case 224: /* string_specification: string_type  */
  if (yyn == 224)
    /* "src/main/java/parser/Parser.y":1145  */
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
    };
  break;


  case 225: /* string_specification: string_type '[' numeric_constant ']'  */
  if (yyn == 225)
    /* "src/main/java/parser/Parser.y":1160  */
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
    };
  break;


  case 226: /* initialized_string: string_specification ASSIGN_OP string_constant  */
  if (yyn == 226)
    /* "src/main/java/parser/Parser.y":1180  */
    {
        /**
         * Wraps the assigned string literal as a VariableInitialization and
         * stores it in the current context metadata.
        **/

        // ParsingContext ctx = this.contexts.current();
        // ctx.metadataBuilder().initialValue(
        //     new VariableInitialization($3)
        // );
    };
  break;


  case 227: /* string_type: STRING  */
  if (yyn == 227)
    /* "src/main/java/parser/Parser.y":1194  */
                { /** Sets the subtype to STRING. */  yyval = Subtype.STRING;  };
  break;


  case 228: /* string_type: WSTRING  */
  if (yyn == 228)
    /* "src/main/java/parser/Parser.y":1195  */
                { /** Sets the subtype to WSTRING. */ yyval = Subtype.WSTRING; };
  break;


  case 234: /* type_declaration: type_name_declaration ':' type_spec_init  */
  if (yyn == 234)
    /* "src/main/java/parser/Parser.y":1216  */
    {
        ((SingleDeclaration)(yystack.valueAt (0))).identifier(((String)(yystack.valueAt (2)))).use(Use.TYPE).source(Source.NONE);
        ((SingleDeclaration)(yystack.valueAt (0))).publish();
    };
  break;


  case 235: /* type_name_declaration: IDENTIFIER  */
  if (yyn == 235)
    /* "src/main/java/parser/Parser.y":1224  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 236: /* type_spec_init: custom_spec_init  */
  if (yyn == 236)
    /* "src/main/java/parser/Parser.y":1230  */
                                { /** Wraps the custom type builder in a SingleDeclaration for type declaration. */ yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 237: /* type_spec_init: simple_spec_init  */
  if (yyn == 237)
    /* "src/main/java/parser/Parser.y":1231  */
                                { /** Wraps the simple type builder in a SingleDeclaration for type declaration. */ yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 238: /* type_spec_init: enumerated_spec_init  */
  if (yyn == 238)
    /* "src/main/java/parser/Parser.y":1232  */
                                { /** Wraps the enumerated builder in an EnumeratedTypeDeclaration for type declaration. */ yyval = new EnumeratedTypeDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0))));  };
  break;


  case 239: /* type_spec_init: subrange_spec_init  */
  if (yyn == 239)
    /* "src/main/java/parser/Parser.y":1233  */
                                { /** Subrange not yet supported for type declarations; returns null. */ yyval = null; };
  break;


  case 240: /* type_spec_init: array_spec_init  */
  if (yyn == 240)
    /* "src/main/java/parser/Parser.y":1234  */
                                { /** Wraps the array builder in a SingleDeclaration for type declaration. */ yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 241: /* type_spec_init: structure_specification  */
  if (yyn == 241)
    /* "src/main/java/parser/Parser.y":1235  */
                                { /** Propagates the structure declaration. */ yyval = ((SingleDeclaration)(yystack.valueAt (0))); };
  break;


  case 242: /* type_spec_init: string_spec_init  */
  if (yyn == 242)
    /* "src/main/java/parser/Parser.y":1236  */
                                { /** String not yet supported for type declarations; returns null. */ yyval = null; };
  break;


  case 243: /* structure_specification: STRUCT structure_field_init_decl_list END_STRUCT  */
  if (yyn == 243)
    /* "src/main/java/parser/Parser.y":1241  */
    {
        /**
         * Builds the declaration of the structure and publishes it to the symbol table. For that to happen every field
         * needs to be searched to get their initial values and appended to the structure initial value.
        **/

        StructureDeclaration structDeclaration = new StructureDeclaration(this.symbolTable, ((StructureBlock)(yystack.valueAt (1))));
        Director.makeStructure(structDeclaration, ((StructureBlock)(yystack.valueAt (1))));

        yyval = structDeclaration;
    };
  break;


  case 244: /* structure_field_init_decl_list: structure_field_init_decl ';'  */
  if (yyn == 244)
    /* "src/main/java/parser/Parser.y":1256  */
    {
        /**
         * Starts the structure field list with the first declared field.
        **/

        StructureBlock structBlock = new StructureBlock();
        structBlock.addField(((SingleDeclaration)(yystack.valueAt (1))));
        yyval = structBlock;
    };
  break;


  case 245: /* structure_field_init_decl_list: structure_field_init_decl_list structure_field_init_decl ';'  */
  if (yyn == 245)
    /* "src/main/java/parser/Parser.y":1266  */
    {
        /**
         * Appends the next declared field to the structure field list.
        **/

        ((StructureBlock)(yystack.valueAt (2))).addField(((SingleDeclaration)(yystack.valueAt (1))));
        yyval = ((StructureBlock)(yystack.valueAt (2)));
    };
  break;


  case 246: /* structure_field_init_decl: field_name ':' structure_field_spec_init  */
  if (yyn == 246)
    /* "src/main/java/parser/Parser.y":1278  */
    {
        ((SingleDeclaration)(yystack.valueAt (0))).identifier(((String)(yystack.valueAt (2)))).use(Use.FIELD).source(Source.NONE);
        yyval = ((SingleDeclaration)(yystack.valueAt (0)));
    };
  break;


  case 247: /* field_name: IDENTIFIER  */
  if (yyn == 247)
    /* "src/main/java/parser/Parser.y":1286  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 248: /* structure_field_spec_init: custom_spec_init  */
  if (yyn == 248)
    /* "src/main/java/parser/Parser.y":1292  */
                            { /** Wraps the custom type builder in a SingleDeclaration for structure field. */ yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 249: /* structure_field_spec_init: boolean_spec_init  */
  if (yyn == 249)
    /* "src/main/java/parser/Parser.y":1293  */
                            { /** Wraps the boolean builder in a SingleDeclaration for structure field. */ yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 250: /* structure_field_spec_init: simple_spec_init  */
  if (yyn == 250)
    /* "src/main/java/parser/Parser.y":1294  */
                            { /** Wraps the simple type builder in a SingleDeclaration for structure field. */ yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 251: /* structure_field_spec_init: enumerated_spec_init  */
  if (yyn == 251)
    /* "src/main/java/parser/Parser.y":1295  */
                            { /** Wraps the enumerated builder in an AnonymousEnumeratedField for structure field. */ yyval = new AnnonymousEnumeratedField(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 252: /* structure_field_spec_init: subrange_spec_init  */
  if (yyn == 252)
    /* "src/main/java/parser/Parser.y":1296  */
                            { /** Subrange not yet supported for structure fields; returns null. */ yyval = null; };
  break;


  case 253: /* structure_field_spec_init: array_spec_init  */
  if (yyn == 253)
    /* "src/main/java/parser/Parser.y":1297  */
                            { /** Wraps the array builder in a SingleDeclaration for structure field. */ yyval = new SingleDeclaration(this.symbolTable, ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 254: /* structure_field_spec_init: string_spec_init  */
  if (yyn == 254)
    /* "src/main/java/parser/Parser.y":1298  */
                            { /** String not yet supported for structure fields; returns null. */ yyval = null; };
  break;



/* "src/main/java/parser/Parser.java":2474  */

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

  private static final short yypact_ninf_ = -322;
  private static final short yytable_ninf_ = -181;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      55,    36,    38,    78,  -322,  -322,    19,    13,    25,  -322,
      86,  -322,  -322,    42,  -322,    73,  -322,  -322,  -322,    98,
    -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,
    -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,
    -322,    79,   134,  -322,   129,  -322,  -322,  -322,  -322,  -322,
     137,  -322,  -322,  -322,    99,  -322,  -322,  -322,  -322,  -322,
    -322,   138,   100,  -322,  -322,   142,  -322,  -322,   147,  -322,
    -322,   148,  -322,   104,  -322,  -322,    62,  -322,    23,   111,
     108,   459,  -322,    67,   290,   436,   459,   459,   154,   109,
     120,   459,  -322,  -322,  -322,   117,   145,  -322,   114,  -322,
     254,  -322,  -322,  -322,   157,   115,  -322,  -322,   -48,   162,
    -322,   119,  -322,  -322,    98,   122,  -322,  -322,  -322,  -322,
    -322,  -322,  -322,  -322,  -322,   125,  -322,   175,  -322,  -322,
     116,  -322,  -322,   134,  -322,  -322,   199,   201,  -322,   156,
    -322,  -322,  -322,  -322,    -1,  -322,  -322,  -322,  -322,  -322,
    -322,  -322,   459,   135,   459,   149,  -322,   172,    84,  -322,
    -322,  -322,  -322,  -322,   171,   140,  -322,  -322,   143,  -322,
     -26,  -322,  -322,  -322,  -322,  -322,  -322,   136,  -322,    39,
    -322,    86,  -322,   209,  -322,   134,   141,  -322,  -322,  -322,
    -322,  -322,  -322,   392,  -322,    98,  -322,   175,  -322,   175,
     175,  -322,     7,   218,  -322,   206,    86,  -322,   212,   146,
    -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,
     169,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,
    -322,  -322,  -322,  -322,   182,  -322,   186,    47,  -322,  -322,
     213,   188,  -322,   219,    16,  -322,   158,   221,  -322,  -322,
     173,   206,   234,   217,  -322,  -322,  -322,   328,   459,   250,
     206,   180,   245,   243,   229,    14,  -322,   189,   349,  -322,
     190,  -322,   215,  -322,  -322,   240,   187,   280,    56,   214,
     279,  -322,   220,   223,   411,  -322,  -322,  -322,   224,   225,
    -322,  -322,   459,   131,   260,   298,  -322,  -322,  -322,  -322,
      35,   246,  -322,    33,  -322,  -322,   238,   459,   459,   248,
    -322,  -322,  -322,  -322,  -322,   252,    29,  -322,  -322,  -322,
     256,   118,   139,  -322,  -322,  -322,  -322,  -322,   251,   255,
     257,  -322,  -322,   259,  -322,  -322,  -322,  -322,  -322,   261,
    -322,   459,  -322,  -322,  -322,  -322,  -322,  -322,   258,   323,
      -9,    -7,    83,    -9,   325,   144,  -322,   322,  -322,     8,
      -4,    -3,  -322,   262,   315,  -322,   330,  -322,    83,   144,
      83,   144,  -322,   348,    -6,  -322,  -322,  -322,  -322,  -322,
     333,   371,   334,   294,  -322,  -322,  -322,   353,  -322,   337,
    -322
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
     229,     0,     0,     0,   230,   235,     0,     0,     0,     1,
       0,     2,   231,     0,   232,     0,     4,     5,   233,     0,
     122,   227,   228,   173,   174,   175,   176,   157,   159,   158,
     160,   147,   148,   149,   150,   151,   152,   153,   154,   155,
     156,     0,     0,   236,   120,   121,   123,   124,   125,   237,
     136,   137,   138,   140,   143,   145,   146,   144,   141,   142,
     239,   177,     0,   178,   238,   183,   184,   240,   187,   188,
     242,   222,   223,   224,   234,   241,    13,   247,     0,     0,
       0,     0,   111,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     8,     9,     6,    10,    17,   243,     0,   244,
       0,   167,   169,   170,     0,     0,   171,   192,     0,     0,
     185,   216,   165,   166,     0,     0,   126,   161,   162,   164,
     163,   128,   127,   139,   181,     0,   186,     0,   191,   226,
       0,    11,    12,     0,   104,    14,     0,    21,    15,   105,
     245,   131,   248,   249,   129,   130,   250,   252,   251,   253,
     254,   246,     0,     0,     0,     0,   112,     0,     0,   206,
     208,   209,   210,   211,     0,     0,   179,   200,   164,   203,
       0,   196,   198,   199,   202,   201,   225,     0,   107,     0,
       3,     0,    18,    50,   106,     0,     0,   134,   135,   132,
     182,   168,   193,     0,   217,     0,   205,     0,   172,     0,
       0,   195,     0,     0,    20,     0,     0,    22,    96,     0,
     133,   189,   194,   143,   190,   207,   212,   214,   215,   213,
       0,   197,     7,   108,   221,   109,   113,   114,   115,   116,
     117,   118,   110,   219,   218,   119,     0,     0,    27,    24,
      48,     0,    51,    16,     0,   204,     0,     0,    19,    28,
       0,    25,    54,     0,    97,   103,   220,     0,     0,     0,
      26,     0,    64,    56,     0,     0,    99,     0,     0,    29,
       0,    32,    33,    35,    34,     0,     0,     0,     0,     0,
       0,    65,     0,     0,     0,    98,   100,    30,     0,     0,
      31,    36,     0,     0,     0,     0,    58,    59,    60,    55,
       0,     0,    73,     0,    53,   101,     0,     0,     0,     0,
      40,    41,    42,    43,    44,     0,     0,    23,    68,    67,
       0,     0,     0,    61,    62,    63,    57,   102,     0,     0,
       0,    39,    47,     0,    46,    66,    70,    71,    72,     0,
      52,     0,    74,    38,    37,    49,    45,    69,     0,     0,
       0,     0,    81,     0,     0,    81,    88,    87,    86,     0,
       0,     0,    80,     0,     0,    79,     0,    90,    81,    81,
      81,    81,    89,    93,    76,    91,    82,    84,    83,    85,
       0,     0,     0,     0,    92,    78,    77,    95,    75,     0,
      94
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -322,  -322,  -322,  -150,  -322,  -322,  -322,  -322,  -322,  -322,
    -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,   132,  -205,
    -322,  -322,  -322,  -322,   110,  -322,  -322,  -322,  -322,  -322,
    -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,  -322,
    -322,  -322,  -322,  -322,  -322,    31,  -321,  -318,  -322,  -322,
    -322,  -322,  -322,   121,  -322,  -322,  -322,   200,  -175,   345,
    -322,   -88,  -322,  -322,  -322,  -322,  -322,   185,  -322,  -322,
    -322,   -87,  -322,  -322,   202,  -322,   -14,  -322,  -322,   -13,
     -12,   -76,   304,   210,   -81,  -322,  -322,   -11,   -80,  -322,
    -322,  -322,   -70,   -78,  -322,  -322,   -74,  -322,  -322,  -322,
    -322,   -75,  -322,   197,   231,  -322,   -77,  -322,   222,  -322,
    -322,  -322,  -322,   -73,  -322,  -322,  -322,   -72,  -322,  -322,
    -322,  -322,  -322,  -322,   403,  -322,  -322,  -322,  -322,   338,
     -98,  -322
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,    11,    17,    76,    94,    95,   133,    96,   135,
     136,   137,   182,   205,   183,   207,   240,   259,   237,   238,
     269,   270,   271,   272,   273,   277,   315,   295,   333,   251,
     208,   242,   262,   263,   283,   299,   326,   280,   281,   320,
     302,   339,   322,   342,   383,   354,   362,   355,   356,   374,
     243,   254,   265,   266,   138,   139,   185,   177,   178,   179,
     225,    43,    44,    45,    46,    47,    48,   143,   144,   145,
     189,    49,    50,    51,    52,    53,   102,    55,    56,   103,
     115,   167,   117,   118,   119,   105,   120,   106,    60,    61,
      62,    63,   107,    64,    65,    66,    67,    68,    69,   108,
     214,   169,   170,   171,   172,   173,   174,   158,   159,   160,
     161,   162,   163,   175,   232,   233,   234,    70,    71,    72,
      73,     3,     4,     6,     7,     8,    74,    75,    78,    79,
      80,   151
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
     104,    54,    57,    58,    59,   124,   104,   121,   116,   123,
     130,   122,   142,   146,   128,   126,   164,   125,   381,   351,
     147,   351,   148,    12,   351,   351,   149,   223,   150,    97,
     285,   204,   249,   358,   365,   352,   366,   357,     9,   154,
     368,   370,   369,   371,   155,   186,   168,   376,   377,   378,
     379,    82,   367,   187,   188,   249,   239,   248,     1,   222,
      82,   200,   264,     5,   236,   332,   201,    77,   255,   223,
     323,   190,   318,   104,   324,   325,   319,   353,    19,   353,
       5,   382,   353,   353,   192,    10,    54,    57,    58,    59,
      23,    24,    25,    26,   296,   297,   298,   164,    14,    31,
      32,    33,    34,    35,    36,    37,    38,    39,    40,   101,
     359,    92,   360,   361,    15,   226,   228,    20,    93,   168,
     218,   216,   217,   229,   219,   230,   109,    18,   203,   231,
      16,   235,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,    77,    31,    32,    33,    34,    35,    36,    37,
      38,    39,    40,   340,   109,   110,   336,    41,   337,    42,
     341,   338,   310,   311,   312,   313,   314,   131,   132,   256,
      81,   195,   196,   360,   361,    84,   274,   275,    82,   213,
      57,    58,    59,    85,    86,  -180,    87,   289,    88,    54,
      57,    58,    59,    89,    90,    91,    99,   100,   111,   140,
     127,   112,   134,   306,   152,   153,   156,   180,   176,   157,
     181,   309,   165,   166,   184,   191,   194,   197,   193,   111,
     206,   202,   198,   236,   113,   241,   328,   329,   246,   199,
     247,   244,   252,   250,   253,   334,    23,    24,    25,    26,
      27,    28,    29,    30,   114,    31,    32,    33,    34,    35,
      36,    37,    38,    39,    40,   101,   112,   245,   113,   258,
     348,   114,    20,   224,   261,   264,   127,   257,   276,   278,
     279,   141,   282,   284,   287,   290,   293,    21,    22,    23,
      24,    25,    26,    27,    28,    29,    30,   292,    31,    32,
      33,    34,    35,    36,    37,    38,    39,    40,    20,   294,
     386,   268,    41,   300,    42,   301,   316,   141,   304,   303,
     317,   307,   308,    21,    22,    23,    24,    25,    26,    27,
      28,    29,    30,   327,    31,    32,    33,    34,    35,    36,
      37,    38,    39,    40,   111,   321,   330,   331,    41,   343,
      42,   335,   345,   344,   346,   350,   347,   349,   364,   359,
     372,    23,    24,    25,    26,    27,    28,    29,    30,   373,
      31,    32,    33,    34,    35,    36,    37,    38,    39,    40,
     101,   112,   267,   113,   375,   380,   114,   384,   387,   388,
     389,   390,   291,   260,   363,   209,   286,    83,   227,    23,
      24,    25,    26,   288,   129,   212,   210,   221,    31,    32,
      33,    34,    35,    36,    37,    38,    39,    40,   101,    13,
      23,    24,    25,    26,   268,   385,    98,   215,     0,    31,
      32,    33,    34,    35,    36,    37,    38,    39,    40,   101,
     220,     0,    23,    24,    25,    26,   211,     0,     0,     0,
       0,    31,    32,    33,    34,    35,    36,    37,    38,    39,
      40,   101,     0,    23,    24,    25,    26,    27,    28,    29,
      30,     0,    31,    32,    33,    34,    35,    36,    37,    38,
      39,    40,    23,    24,    25,    26,     0,     0,     0,     0,
       0,    31,    32,    33,    34,    35,    36,    37,    38,    39,
      40,   101,     0,     0,     0,     0,   305,    23,    24,    25,
      26,    27,    28,    29,    30,     0,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,   101,   112,     0,   113,
      23,    24,    25,    26,     0,     0,     0,     0,     0,    31,
      32,    33,    34,    35,    36,    37,    38,    39,    40,   101
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      81,    15,    15,    15,    15,    86,    87,    84,    84,    85,
      91,    84,   100,   100,    89,    88,   114,    87,    24,    28,
     100,    28,   100,     4,    28,    28,   100,   202,   100,     6,
      16,   181,   237,   351,   355,    44,    28,    44,     0,    87,
      44,    44,   360,   361,    92,    46,   127,   368,   369,   370,
     371,    44,    44,    54,    55,   260,   206,    10,     3,    52,
      44,    87,    48,    44,    17,    36,    92,    44,    52,   244,
      37,   152,    37,   154,    41,    42,    41,    86,     5,    86,
      44,    87,    86,    86,   154,     7,   100,   100,   100,   100,
      61,    62,    63,    64,    38,    39,    40,   195,    85,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      27,    49,    29,    30,    89,   203,   203,    44,    56,   200,
     197,   197,   197,   203,   197,   203,    87,    85,    89,   203,
      44,   203,    59,    60,    61,    62,    63,    64,    65,    66,
      67,    68,    44,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    14,    87,    88,    38,    84,    40,    86,
      21,    43,    31,    32,    33,    34,    35,    50,    51,   246,
      91,    87,    88,    29,    30,    46,   257,   258,    44,   193,
     193,   193,   193,    46,    46,    86,    86,   268,    46,   203,
     203,   203,   203,    46,    46,    91,    85,    89,    44,    85,
      91,    81,    57,   284,    47,    90,    44,     8,    92,    90,
       9,   292,    90,    88,    58,    80,    44,    46,    69,    44,
      11,    85,    82,    17,    83,    13,   307,   308,    46,    86,
      44,    85,    44,    20,    15,   316,    61,    62,    63,    64,
      65,    66,    67,    68,    86,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    88,    83,    86,
     341,    86,    44,    45,    30,    48,    91,    46,    18,    89,
      25,    53,    29,    44,    85,    85,    89,    59,    60,    61,
      62,    63,    64,    65,    66,    67,    68,    47,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    44,    19,
     381,    86,    84,    89,    86,    26,    46,    53,    85,    89,
      12,    87,    87,    59,    60,    61,    62,    63,    64,    65,
      66,    67,    68,    85,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    44,    89,    88,    85,    84,    88,
      86,    85,    85,    88,    85,    22,    85,    89,    23,    27,
      88,    61,    62,    63,    64,    65,    66,    67,    68,    44,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    44,    83,    44,    27,    86,    44,    44,    85,
      27,    44,   272,   251,   353,   185,   265,    42,   203,    61,
      62,    63,    64,    44,    90,   193,   186,   200,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,     6,
      61,    62,    63,    64,    86,    44,    78,   195,    -1,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
     199,    -1,    61,    62,    63,    64,    44,    -1,    -1,    -1,
      -1,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    -1,    61,    62,    63,    64,    65,    66,    67,
      68,    -1,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    61,    62,    63,    64,    -1,    -1,    -1,    -1,
      -1,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    -1,    -1,    -1,    -1,    85,    61,    62,    63,
      64,    65,    66,    67,    68,    -1,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    -1,    83,
      61,    62,    63,    64,    -1,    -1,    -1,    -1,    -1,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,    94,   214,   215,    44,   216,   217,   218,     0,
       7,    95,     4,   217,    85,    89,    44,    96,    85,     5,
      44,    59,    60,    61,    62,    63,    64,    65,    66,    67,
      68,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    84,    86,   154,   155,   156,   157,   158,   159,   164,
     165,   166,   167,   168,   169,   170,   171,   172,   173,   180,
     181,   182,   183,   184,   186,   187,   188,   189,   190,   191,
     210,   211,   212,   213,   219,   220,    97,    44,   221,   222,
     223,    91,    44,   152,    46,    46,    46,    86,    46,    46,
      46,    91,    49,    56,    98,    99,   101,     6,   222,    85,
      89,    80,   169,   172,   177,   178,   180,   185,   192,    87,
      88,    44,    81,    83,    86,   173,   174,   175,   176,   177,
     179,   199,   206,   174,   177,   185,   206,    91,   194,   175,
     177,    50,    51,   100,    57,   102,   103,   104,   147,   148,
      85,    53,   154,   160,   161,   162,   164,   181,   186,   189,
     210,   224,    47,    90,    87,    92,    44,    90,   200,   201,
     202,   203,   204,   205,   223,    90,    88,   174,   177,   194,
     195,   196,   197,   198,   199,   206,    92,   150,   151,   152,
       8,     9,   105,   107,    58,   149,    46,    54,    55,   163,
     177,    80,   185,    69,    44,    87,    88,    46,    82,    86,
      87,    92,    85,    89,    96,   106,    11,   108,   123,   150,
     176,    44,   167,   169,   193,   201,   174,   194,   199,   206,
     197,   196,    52,   151,    45,   153,   154,   160,   164,   181,
     186,   189,   207,   208,   209,   210,    17,   111,   112,    96,
     109,    13,   124,   143,    85,    88,    46,    44,    10,   112,
      20,   122,    44,    15,   144,    52,   199,    46,    86,   110,
     111,    30,   125,   126,    48,   145,   146,    44,    86,   113,
     114,   115,   116,   117,   177,   177,    18,   118,    89,    25,
     130,   131,    29,   127,    44,    16,   146,    85,    44,   177,
      85,   117,    47,    89,    19,   120,    38,    39,    40,   128,
      89,    26,   133,    89,    85,    85,   177,    87,    87,   177,
      31,    32,    33,    34,    35,   119,    46,    12,    37,    41,
     132,    89,   135,    37,    41,    42,   129,    85,   177,   177,
      88,    85,    36,   121,   177,    85,    38,    40,    43,   134,
      14,    21,   136,    88,    88,    85,    85,    85,   177,    89,
      22,    28,    44,    86,   138,   140,   141,    44,   140,    27,
      29,    30,   139,   138,    23,   139,    28,    44,    44,   140,
      44,   140,    88,    44,   142,    44,   139,   139,   139,   139,
      27,    24,    87,   137,    44,    44,   177,    44,    85,    27,
      44
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,    93,    94,    95,    96,    97,    97,    98,    99,    99,
     100,   100,   100,   101,   101,   102,   103,   104,   104,   105,
     106,   107,   107,   108,   109,   110,   110,   111,   111,   112,
     113,   113,   114,   114,   115,   116,   116,   117,   117,   118,
     119,   119,   119,   119,   119,   120,   121,   121,   122,   122,
     123,   123,   124,   125,   126,   126,   127,   127,   128,   128,
     128,   129,   129,   129,   130,   130,   131,   132,   132,   133,
     134,   134,   134,   135,   135,   136,   137,   137,   137,   138,
     138,   139,   139,   139,   139,   139,   140,   140,   140,   140,
     141,   141,   142,   142,   142,   142,   143,   143,   144,   145,
     145,   146,   146,   147,   148,   149,   149,   150,   150,   151,
     151,   152,   152,   153,   153,   153,   153,   153,   153,   153,
     154,   154,   155,   156,   156,   156,   157,   158,   159,   160,
     160,   161,   162,   162,   163,   163,   164,   164,   165,   166,
     167,   167,   167,   168,   168,   169,   169,   170,   170,   170,
     170,   171,   171,   171,   171,   172,   172,   173,   173,   173,
     173,   174,   174,   174,   174,   175,   176,   177,   177,   178,
     178,   178,   179,   180,   180,   180,   180,   181,   181,   182,
     183,   184,   185,   186,   186,   187,   188,   189,   189,   190,
     190,   191,   192,   192,   193,   194,   195,   195,   196,   196,
     197,   197,   197,   197,   198,   199,   200,   200,   201,   201,
     201,   201,   202,   203,   204,   205,   206,   206,   207,   207,
     208,   209,   210,   210,   211,   211,   212,   213,   213,   214,
     214,   215,   216,   216,   217,   218,   219,   219,   219,   219,
     219,   219,   219,   220,   221,   221,   222,   223,   224,   224,
     224,   224,   224,   224,   224
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     2,     6,     1,     0,     2,     5,     1,     1,
       0,     1,     1,     0,     2,     1,     4,     0,     2,     4,
       1,     0,     2,     7,     1,     0,     1,     1,     2,     4,
       2,     2,     1,     1,     1,     1,     2,     5,     5,     4,
       1,     1,     1,     1,     1,     4,     1,     1,     0,     7,
       0,     2,     7,     3,     0,     3,     0,     3,     1,     1,
       1,     1,     1,     1,     0,     1,     4,     1,     1,     4,
       1,     1,     1,     0,     2,     9,     0,     2,     2,     2,
       2,     0,     3,     3,     3,     3,     2,     2,     1,     3,
       3,     4,     3,     1,     5,     3,     0,     2,     3,     1,
       2,     3,     4,     5,     1,     0,     1,     1,     3,     3,
       3,     1,     3,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     3,     3,     3,     1,
       1,     1,     2,     3,     1,     1,     1,     1,     1,     3,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     3,     1,
       1,     1,     3,     1,     1,     1,     1,     1,     1,     4,
       1,     3,     3,     1,     1,     3,     3,     1,     1,     6,
       6,     3,     1,     3,     1,     3,     1,     3,     1,     1,
       1,     1,     1,     1,     4,     3,     1,     3,     1,     1,
       1,     1,     3,     3,     3,     3,     1,     3,     1,     1,
       3,     1,     1,     1,     1,     4,     3,     1,     1,     0,
       1,     3,     2,     3,     3,     1,     1,     1,     1,     1,
       1,     1,     1,     3,     2,     3,     3,     1,     1,     1,
       1,     1,     1,     1,     1
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


  private static final int YYLAST_ = 539;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 9;
  private static final int YYNTOKENS_ = 93;

/* Unqualified %code blocks.  */
/* "src/main/java/parser/Parser.y":26  */

    private String programName;

/* "src/main/java/parser/Parser.java":3293  */

}
/* "src/main/java/parser/Parser.y":1301  */

