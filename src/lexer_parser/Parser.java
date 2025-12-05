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

package lexer_parser;



import java.text.MessageFormat;
import java.util.ArrayList;
/* "%code imports" blocks.  */
/* "Parser.y":6  */

  import java.io.*;

/* "Parser.java":49  */

/**
 * A Bison parser, automatically generated from <tt>Parser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class Parser
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
    S_FUNCTION_BLOCK(3),           /* FUNCTION_BLOCK  */
    S_END_FUNCTION_BLOCK(4),       /* END_FUNCTION_BLOCK  */
    S_FUZZIFY(5),                  /* FUZZIFY  */
    S_END_FUZZIFY(6),              /* END_FUZZIFY  */
    S_DEFUZZIFY(7),                /* DEFUZZIFY  */
    S_END_DEFUZZIFY(8),            /* END_DEFUZZIFY  */
    S_RULEBLOCK(9),                /* RULEBLOCK  */
    S_END_RULEBLOCK(10),           /* END_RULEBLOCK  */
    S_OPTION(11),                  /* OPTION  */
    S_END_OPTION(12),              /* END_OPTION  */
    S_TERM(13),                    /* TERM  */
    S_METHOD(14),                  /* METHOD  */
    S_DEFAULT(15),                 /* DEFAULT  */
    S_RANGE(16),                   /* RANGE  */
    S_RULE(17),                    /* RULE  */
    S_IF(18),                      /* IF  */
    S_THEN(19),                    /* THEN  */
    S_WITH(20),                    /* WITH  */
    S_ACT(21),                     /* ACT  */
    S_ACCU(22),                    /* ACCU  */
    S_IS(23),                      /* IS  */
    S_NOT(24),                     /* NOT  */
    S_AND(25),                     /* AND  */
    S_OR(26),                      /* OR  */
    S_COG(27),                     /* COG  */
    S_COGS(28),                    /* COGS  */
    S_COA(29),                     /* COA  */
    S_LM(30),                      /* LM  */
    S_RM(31),                      /* RM  */
    S_NC(32),                      /* NC  */
    S_MIN(33),                     /* MIN  */
    S_MAX(34),                     /* MAX  */
    S_ASUM(35),                    /* ASUM  */
    S_BSUM(36),                    /* BSUM  */
    S_PROD(37),                    /* PROD  */
    S_BDIF(38),                    /* BDIF  */
    S_NSUM(39),                    /* NSUM  */
    S_IDENTIFIER(40),              /* IDENTIFIER  */
    S_STD_FB_IDENTIFIER(41),       /* STD_FB_IDENTIFIER  */
    S_PRAGMA(42),                  /* PRAGMA  */
    S_interval(43),                /* interval  */
    S_VAR_INPUT(44),               /* VAR_INPUT  */
    S_RETAIN(45),                  /* RETAIN  */
    S_NON_RETAIN(46),              /* NON_RETAIN  */
    S_END_VAR(47),                 /* END_VAR  */
    S_BOOL(48),                    /* BOOL  */
    S_R_EDGE(49),                  /* R_EDGE  */
    S_F_EDGE(50),                  /* F_EDGE  */
    S_VAR_OUTPUT(51),              /* VAR_OUTPUT  */
    S_VAR(52),                     /* VAR  */
    S_CONSTANT(53),                /* CONSTANT  */
    S_STRING(54),                  /* STRING  */
    S_WSTRING(55),                 /* WSTRING  */
    S_BYTE(56),                    /* BYTE  */
    S_WORD(57),                    /* WORD  */
    S_DWORD(58),                   /* DWORD  */
    S_LWORD(59),                   /* LWORD  */
    S_TIME(60),                    /* TIME  */
    S_TIME_OF_DAY(61),             /* TIME_OF_DAY  */
    S_TOD(62),                     /* TOD  */
    S_DATE(63),                    /* DATE  */
    S_DATE_AND_TIME(64),           /* DATE_AND_TIME  */
    S_DT(65),                      /* DT  */
    S_OF(66),                      /* OF  */
    S_SINT(67),                    /* SINT  */
    S_INT(68),                     /* INT  */
    S_DINT(69),                    /* DINT  */
    S_LINT(70),                    /* LINT  */
    S_USINT(71),                   /* USINT  */
    S_UINT(72),                    /* UINT  */
    S_UDINT(73),                   /* UDINT  */
    S_ULINT(74),                   /* ULINT  */
    S_REAL(75),                    /* REAL  */
    S_LREAL(76),                   /* LREAL  */
    S_TRUE(77),                    /* TRUE  */
    S_FALSE(78),                   /* FALSE  */
    S_BINARY_INTEGER(79),          /* BINARY_INTEGER  */
    S_OCTAL_INTEGER(80),           /* OCTAL_INTEGER  */
    S_HEX_INTEGER(81),             /* HEX_INTEGER  */
    S_CHARACTER_STRING(82),        /* CHARACTER_STRING  */
    S_ARRAY(83),                   /* ARRAY  */
    S_INTEGER_NUMBER(84),          /* INTEGER_NUMBER  */
    S_REAL_NUMBER(85),             /* REAL_NUMBER  */
    S_SIGNED_INTEGER(86),          /* SIGNED_INTEGER  */
    S_87_(87),                     /* ':'  */
    S_88_(88),                     /* '='  */
    S_89_(89),                     /* ';'  */
    S_90_(90),                     /* '('  */
    S_91_(91),                     /* ','  */
    S_92_(92),                     /* ')'  */
    S_93_(93),                     /* '.'  */
    S_94_(94),                     /* '#'  */
    S_95_T_(95),                   /* 'T'  */
    S_96_(96),                     /* '-'  */
    S_97_D_(97),                   /* 'D'  */
    S_98_0_(98),                   /* '0'  */
    S_99_1_(99),                   /* '1'  */
    S_100_(100),                   /* '['  */
    S_101_(101),                   /* ']'  */
    S_YYACCEPT(102),               /* $accept  */
    S_program(103),                /* program  */
    S_function_block_declaration(104), /* function_block_declaration  */
    S_fb_io_var_declarations_list(105), /* fb_io_var_declarations_list  */
    S_fb_io_var_declarations(106), /* fb_io_var_declarations  */
    S_other_var_declarations_list(107), /* other_var_declarations_list  */
    S_other_var_declarations(108), /* other_var_declarations  */
    S_function_block_body(109),    /* function_block_body  */
    S_fuzzify_block_list(110),     /* fuzzify_block_list  */
    S_fuzzify_block(111),          /* fuzzify_block  */
    S_defuzzify_block_list(112),   /* defuzzify_block_list  */
    S_defuzzify_block(113),        /* defuzzify_block  */
    S_linguistic_term_list(114),   /* linguistic_term_list  */
    S_linguistic_term(115),        /* linguistic_term  */
    S_membership_function(116),    /* membership_function  */
    S_singleton(117),              /* singleton  */
    S_points(118),                 /* points  */
    S_point_list(119),             /* point_list  */
    S_point(120),                  /* point  */
    S_defuzzification_method(121), /* defuzzification_method  */
    S_defuzz_method(122),          /* defuzz_method  */
    S_default_value(123),          /* default_value  */
    S_default_val(124),            /* default_val  */
    S_range_opt(125),              /* range_opt  */
    S_rule_block_list(126),        /* rule_block_list  */
    S_rule_block(127),             /* rule_block  */
    S_operator_definition(128),    /* operator_definition  */
    S_operator_or_opt(129),        /* operator_or_opt  */
    S_operator_and_opt(130),       /* operator_and_opt  */
    S_or_type(131),                /* or_type  */
    S_and_type(132),               /* and_type  */
    S_activation_method_opt(133),  /* activation_method_opt  */
    S_activation_method(134),      /* activation_method  */
    S_act_type(135),               /* act_type  */
    S_accumulation_method(136),    /* accumulation_method  */
    S_accu_type(137),              /* accu_type  */
    S_rule_list(138),              /* rule_list  */
    S_rule(139),                   /* rule  */
    S_weighting_opt(140),          /* weighting_opt  */
    S_condition(141),              /* condition  */
    S_condition_tail(142),         /* condition_tail  */
    S_x(143),                      /* x  */
    S_subcondition(144),           /* subcondition  */
    S_conclusion_list(145),        /* conclusion_list  */
    S_weighting_factor(146),       /* weighting_factor  */
    S_option_block_list(147),      /* option_block_list  */
    S_option_block(148),           /* option_block  */
    S_numeric_literal(149),        /* numeric_literal  */
    S_pragma_list(150),            /* pragma_list  */
    S_pragma(151),                 /* pragma  */
    S_output_declarations(152),    /* output_declarations  */
    S_input_declarations(153),     /* input_declarations  */
    S_input_declaration_list(154), /* input_declaration_list  */
    S_input_declaration(155),      /* input_declaration  */
    S_var_declarations(156),       /* var_declarations  */
    S_var_retain_spec(157),        /* var_retain_spec  */
    S_var_constant_spec(158),      /* var_constant_spec  */
    S_var_init_decl_list(159),     /* var_init_decl_list  */
    S_var_init_decl(160),          /* var_init_decl  */
    S_type_declaration(161),       /* type_declaration  */
    S_opt_edge(162),               /* opt_edge  */
    S_opt_single_byte_string_spec(163), /* opt_single_byte_string_spec  */
    S_opt_double_byte_string_spec(164), /* opt_double_byte_string_spec  */
    S_var1_init_decl(165),         /* var1_init_decl  */
    S_spec_init_type(166),         /* spec_init_type  */
    S_simple_spec_init(167),       /* simple_spec_init  */
    S_simple_specification(168),   /* simple_specification  */
    S_elementary_type_name(169),   /* elementary_type_name  */
    S_numeric_type_name(170),      /* numeric_type_name  */
    S_integer_type_name(171),      /* integer_type_name  */
    S_signed_integer_type_name(172), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(173), /* unsigned_integer_type_name  */
    S_real_type_name(174),         /* real_type_name  */
    S_date_type_name(175),         /* date_type_name  */
    S_bit_string_type_name(176),   /* bit_string_type_name  */
    S_constant(177),               /* constant  */
    S_integer_literal(178),        /* integer_literal  */
    S_integer_type_name_opt(179),  /* integer_type_name_opt  */
    S_type_integer_literal(180),   /* type_integer_literal  */
    S_real_literal(181),           /* real_literal  */
    S_real_type_name_opt(182),     /* real_type_name_opt  */
    S_time_literal(183),           /* time_literal  */
    S_duration_literal(184),       /* duration_literal  */
    S_fixed_point(185),            /* fixed_point  */
    S_time_of_day_literal(186),    /* time_of_day_literal  */
    S_daytime(187),                /* daytime  */
    S_date_literal(188),           /* date_literal  */
    S_date(189),                   /* date  */
    S_date_and_time_literal(190),  /* date_and_time_literal  */
    S_boolean_literal(191),        /* boolean_literal  */
    S_boolean_type_name_opt(192),  /* boolean_type_name_opt  */
    S_bit_string_literal(193),     /* bit_string_literal  */
    S_bit_string_type_name_opt(194), /* bit_string_type_name_opt  */
    S_bit_string_type_name_without_bool(195), /* bit_string_type_name_without_bool  */
    S_bit_string_integer_literals(196), /* bit_string_integer_literals  */
    S_subrange_spec_init(197),     /* subrange_spec_init  */
    S_subrange_specification(198), /* subrange_specification  */
    S_subrange(199),               /* subrange  */
    S_enumerated_spec_init(200),   /* enumerated_spec_init  */
    S_enumerated_specification(201), /* enumerated_specification  */
    S_enumerated_list(202),        /* enumerated_list  */
    S_array_spec_init(203),        /* array_spec_init  */
    S_array_specification(204),    /* array_specification  */
    S_subrange_list(205),          /* subrange_list  */
    S_non_generic_type_name(206),  /* non_generic_type_name  */
    S_array_initialization(207),   /* array_initialization  */
    S_array_initial_elements_list(208), /* array_initial_elements_list  */
    S_array_initial_elements(209), /* array_initial_elements  */
    S_array_initial_element(210),  /* array_initial_element  */
    S_structure_initialization(211), /* structure_initialization  */
    S_structure_element_initialization_list(212), /* structure_element_initialization_list  */
    S_structure_element_initialization(213), /* structure_element_initialization  */
    S_structure_element_type(214), /* structure_element_type  */
    S_initialized_variable(215),   /* initialized_variable  */
    S_initialized_constant(216),   /* initialized_constant  */
    S_initialized_enumerate(217),  /* initialized_enumerate  */
    S_initialized_structure(218),  /* initialized_structure  */
    S_fb_name_decl(219),           /* fb_name_decl  */
    S_identifier_list(220),        /* identifier_list  */
    S_standard_function_block_name(221), /* standard_function_block_name  */
    S_single_byte_string_spec(222), /* single_byte_string_spec  */
    S_double_byte_string_spec(223); /* double_byte_string_spec  */


    private final int yycode_;

    SymbolKind (int n) {
      this.yycode_ = n;
    }

    private static final SymbolKind[] values_ = {
      SymbolKind.S_YYEOF,
      SymbolKind.S_YYerror,
      SymbolKind.S_YYUNDEF,
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
      SymbolKind.S_PRAGMA,
      SymbolKind.S_interval,
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
      SymbolKind.S_TOD,
      SymbolKind.S_DATE,
      SymbolKind.S_DATE_AND_TIME,
      SymbolKind.S_DT,
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
      SymbolKind.S_TRUE,
      SymbolKind.S_FALSE,
      SymbolKind.S_BINARY_INTEGER,
      SymbolKind.S_OCTAL_INTEGER,
      SymbolKind.S_HEX_INTEGER,
      SymbolKind.S_CHARACTER_STRING,
      SymbolKind.S_ARRAY,
      SymbolKind.S_INTEGER_NUMBER,
      SymbolKind.S_REAL_NUMBER,
      SymbolKind.S_SIGNED_INTEGER,
      SymbolKind.S_87_,
      SymbolKind.S_88_,
      SymbolKind.S_89_,
      SymbolKind.S_90_,
      SymbolKind.S_91_,
      SymbolKind.S_92_,
      SymbolKind.S_93_,
      SymbolKind.S_94_,
      SymbolKind.S_95_T_,
      SymbolKind.S_96_,
      SymbolKind.S_97_D_,
      SymbolKind.S_98_0_,
      SymbolKind.S_99_1_,
      SymbolKind.S_100_,
      SymbolKind.S_101_,
      SymbolKind.S_YYACCEPT,
      SymbolKind.S_program,
      SymbolKind.S_function_block_declaration,
      SymbolKind.S_fb_io_var_declarations_list,
      SymbolKind.S_fb_io_var_declarations,
      SymbolKind.S_other_var_declarations_list,
      SymbolKind.S_other_var_declarations,
      SymbolKind.S_function_block_body,
      SymbolKind.S_fuzzify_block_list,
      SymbolKind.S_fuzzify_block,
      SymbolKind.S_defuzzify_block_list,
      SymbolKind.S_defuzzify_block,
      SymbolKind.S_linguistic_term_list,
      SymbolKind.S_linguistic_term,
      SymbolKind.S_membership_function,
      SymbolKind.S_singleton,
      SymbolKind.S_points,
      SymbolKind.S_point_list,
      SymbolKind.S_point,
      SymbolKind.S_defuzzification_method,
      SymbolKind.S_defuzz_method,
      SymbolKind.S_default_value,
      SymbolKind.S_default_val,
      SymbolKind.S_range_opt,
      SymbolKind.S_rule_block_list,
      SymbolKind.S_rule_block,
      SymbolKind.S_operator_definition,
      SymbolKind.S_operator_or_opt,
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
      SymbolKind.S_weighting_opt,
      SymbolKind.S_condition,
      SymbolKind.S_condition_tail,
      SymbolKind.S_x,
      SymbolKind.S_subcondition,
      SymbolKind.S_conclusion_list,
      SymbolKind.S_weighting_factor,
      SymbolKind.S_option_block_list,
      SymbolKind.S_option_block,
      SymbolKind.S_numeric_literal,
      SymbolKind.S_pragma_list,
      SymbolKind.S_pragma,
      SymbolKind.S_output_declarations,
      SymbolKind.S_input_declarations,
      SymbolKind.S_input_declaration_list,
      SymbolKind.S_input_declaration,
      SymbolKind.S_var_declarations,
      SymbolKind.S_var_retain_spec,
      SymbolKind.S_var_constant_spec,
      SymbolKind.S_var_init_decl_list,
      SymbolKind.S_var_init_decl,
      SymbolKind.S_type_declaration,
      SymbolKind.S_opt_edge,
      SymbolKind.S_opt_single_byte_string_spec,
      SymbolKind.S_opt_double_byte_string_spec,
      SymbolKind.S_var1_init_decl,
      SymbolKind.S_spec_init_type,
      SymbolKind.S_simple_spec_init,
      SymbolKind.S_simple_specification,
      SymbolKind.S_elementary_type_name,
      SymbolKind.S_numeric_type_name,
      SymbolKind.S_integer_type_name,
      SymbolKind.S_signed_integer_type_name,
      SymbolKind.S_unsigned_integer_type_name,
      SymbolKind.S_real_type_name,
      SymbolKind.S_date_type_name,
      SymbolKind.S_bit_string_type_name,
      SymbolKind.S_constant,
      SymbolKind.S_integer_literal,
      SymbolKind.S_integer_type_name_opt,
      SymbolKind.S_type_integer_literal,
      SymbolKind.S_real_literal,
      SymbolKind.S_real_type_name_opt,
      SymbolKind.S_time_literal,
      SymbolKind.S_duration_literal,
      SymbolKind.S_fixed_point,
      SymbolKind.S_time_of_day_literal,
      SymbolKind.S_daytime,
      SymbolKind.S_date_literal,
      SymbolKind.S_date,
      SymbolKind.S_date_and_time_literal,
      SymbolKind.S_boolean_literal,
      SymbolKind.S_boolean_type_name_opt,
      SymbolKind.S_bit_string_literal,
      SymbolKind.S_bit_string_type_name_opt,
      SymbolKind.S_bit_string_type_name_without_bool,
      SymbolKind.S_bit_string_integer_literals,
      SymbolKind.S_subrange_spec_init,
      SymbolKind.S_subrange_specification,
      SymbolKind.S_subrange,
      SymbolKind.S_enumerated_spec_init,
      SymbolKind.S_enumerated_specification,
      SymbolKind.S_enumerated_list,
      SymbolKind.S_array_spec_init,
      SymbolKind.S_array_specification,
      SymbolKind.S_subrange_list,
      SymbolKind.S_non_generic_type_name,
      SymbolKind.S_array_initialization,
      SymbolKind.S_array_initial_elements_list,
      SymbolKind.S_array_initial_elements,
      SymbolKind.S_array_initial_element,
      SymbolKind.S_structure_initialization,
      SymbolKind.S_structure_element_initialization_list,
      SymbolKind.S_structure_element_initialization,
      SymbolKind.S_structure_element_type,
      SymbolKind.S_initialized_variable,
      SymbolKind.S_initialized_constant,
      SymbolKind.S_initialized_enumerate,
      SymbolKind.S_initialized_structure,
      SymbolKind.S_fb_name_decl,
      SymbolKind.S_identifier_list,
      SymbolKind.S_standard_function_block_name,
      SymbolKind.S_single_byte_string_spec,
      SymbolKind.S_double_byte_string_spec
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
  "\"end of file\"", "error", "\"invalid token\"", "FUNCTION_BLOCK",
  "END_FUNCTION_BLOCK", "FUZZIFY", "END_FUZZIFY", "DEFUZZIFY",
  "END_DEFUZZIFY", "RULEBLOCK", "END_RULEBLOCK", "OPTION", "END_OPTION",
  "TERM", "METHOD", "DEFAULT", "RANGE", "RULE", "IF", "THEN", "WITH",
  "ACT", "ACCU", "IS", "NOT", "AND", "OR", "COG", "COGS", "COA", "LM",
  "RM", "NC", "MIN", "MAX", "ASUM", "BSUM", "PROD", "BDIF", "NSUM",
  "IDENTIFIER", "STD_FB_IDENTIFIER", "PRAGMA", "interval", "VAR_INPUT",
  "RETAIN", "NON_RETAIN", "END_VAR", "BOOL", "R_EDGE", "F_EDGE",
  "VAR_OUTPUT", "VAR", "CONSTANT", "STRING", "WSTRING", "BYTE", "WORD",
  "DWORD", "LWORD", "TIME", "TIME_OF_DAY", "TOD", "DATE", "DATE_AND_TIME",
  "DT", "OF", "SINT", "INT", "DINT", "LINT", "USINT", "UINT", "UDINT",
  "ULINT", "REAL", "LREAL", "TRUE", "FALSE", "BINARY_INTEGER",
  "OCTAL_INTEGER", "HEX_INTEGER", "CHARACTER_STRING", "ARRAY",
  "INTEGER_NUMBER", "REAL_NUMBER", "SIGNED_INTEGER", "':'", "'='", "';'",
  "'('", "','", "')'", "'.'", "'#'", "'T'", "'-'", "'D'", "'0'", "'1'",
  "'['", "']'", "$accept", "program", "function_block_declaration",
  "fb_io_var_declarations_list", "fb_io_var_declarations",
  "other_var_declarations_list", "other_var_declarations",
  "function_block_body", "fuzzify_block_list", "fuzzify_block",
  "defuzzify_block_list", "defuzzify_block", "linguistic_term_list",
  "linguistic_term", "membership_function", "singleton", "points",
  "point_list", "point", "defuzzification_method", "defuzz_method",
  "default_value", "default_val", "range_opt", "rule_block_list",
  "rule_block", "operator_definition", "operator_or_opt",
  "operator_and_opt", "or_type", "and_type", "activation_method_opt",
  "activation_method", "act_type", "accumulation_method", "accu_type",
  "rule_list", "rule", "weighting_opt", "condition", "condition_tail", "x",
  "subcondition", "conclusion_list", "weighting_factor",
  "option_block_list", "option_block", "numeric_literal", "pragma_list",
  "pragma", "output_declarations", "input_declarations",
  "input_declaration_list", "input_declaration", "var_declarations",
  "var_retain_spec", "var_constant_spec", "var_init_decl_list",
  "var_init_decl", "type_declaration", "opt_edge",
  "opt_single_byte_string_spec", "opt_double_byte_string_spec",
  "var1_init_decl", "spec_init_type", "simple_spec_init",
  "simple_specification", "elementary_type_name", "numeric_type_name",
  "integer_type_name", "signed_integer_type_name",
  "unsigned_integer_type_name", "real_type_name", "date_type_name",
  "bit_string_type_name", "constant", "integer_literal",
  "integer_type_name_opt", "type_integer_literal", "real_literal",
  "real_type_name_opt", "time_literal", "duration_literal", "fixed_point",
  "time_of_day_literal", "daytime", "date_literal", "date",
  "date_and_time_literal", "boolean_literal", "boolean_type_name_opt",
  "bit_string_literal", "bit_string_type_name_opt",
  "bit_string_type_name_without_bool", "bit_string_integer_literals",
  "subrange_spec_init", "subrange_specification", "subrange",
  "enumerated_spec_init", "enumerated_specification", "enumerated_list",
  "array_spec_init", "array_specification", "subrange_list",
  "non_generic_type_name", "array_initialization",
  "array_initial_elements_list", "array_initial_elements",
  "array_initial_element", "structure_initialization",
  "structure_element_initialization_list",
  "structure_element_initialization", "structure_element_type",
  "initialized_variable", "initialized_constant", "initialized_enumerate",
  "initialized_structure", "fb_name_decl", "identifier_list",
  "standard_function_block_name", "single_byte_string_spec",
  "double_byte_string_spec", null
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
    /** Token FUNCTION_BLOCK, to be returned by the scanner.  */
    static final int FUNCTION_BLOCK = 258;
    /** Token END_FUNCTION_BLOCK, to be returned by the scanner.  */
    static final int END_FUNCTION_BLOCK = 259;
    /** Token FUZZIFY, to be returned by the scanner.  */
    static final int FUZZIFY = 260;
    /** Token END_FUZZIFY, to be returned by the scanner.  */
    static final int END_FUZZIFY = 261;
    /** Token DEFUZZIFY, to be returned by the scanner.  */
    static final int DEFUZZIFY = 262;
    /** Token END_DEFUZZIFY, to be returned by the scanner.  */
    static final int END_DEFUZZIFY = 263;
    /** Token RULEBLOCK, to be returned by the scanner.  */
    static final int RULEBLOCK = 264;
    /** Token END_RULEBLOCK, to be returned by the scanner.  */
    static final int END_RULEBLOCK = 265;
    /** Token OPTION, to be returned by the scanner.  */
    static final int OPTION = 266;
    /** Token END_OPTION, to be returned by the scanner.  */
    static final int END_OPTION = 267;
    /** Token TERM, to be returned by the scanner.  */
    static final int TERM = 268;
    /** Token METHOD, to be returned by the scanner.  */
    static final int METHOD = 269;
    /** Token DEFAULT, to be returned by the scanner.  */
    static final int DEFAULT = 270;
    /** Token RANGE, to be returned by the scanner.  */
    static final int RANGE = 271;
    /** Token RULE, to be returned by the scanner.  */
    static final int RULE = 272;
    /** Token IF, to be returned by the scanner.  */
    static final int IF = 273;
    /** Token THEN, to be returned by the scanner.  */
    static final int THEN = 274;
    /** Token WITH, to be returned by the scanner.  */
    static final int WITH = 275;
    /** Token ACT, to be returned by the scanner.  */
    static final int ACT = 276;
    /** Token ACCU, to be returned by the scanner.  */
    static final int ACCU = 277;
    /** Token IS, to be returned by the scanner.  */
    static final int IS = 278;
    /** Token NOT, to be returned by the scanner.  */
    static final int NOT = 279;
    /** Token AND, to be returned by the scanner.  */
    static final int AND = 280;
    /** Token OR, to be returned by the scanner.  */
    static final int OR = 281;
    /** Token COG, to be returned by the scanner.  */
    static final int COG = 282;
    /** Token COGS, to be returned by the scanner.  */
    static final int COGS = 283;
    /** Token COA, to be returned by the scanner.  */
    static final int COA = 284;
    /** Token LM, to be returned by the scanner.  */
    static final int LM = 285;
    /** Token RM, to be returned by the scanner.  */
    static final int RM = 286;
    /** Token NC, to be returned by the scanner.  */
    static final int NC = 287;
    /** Token MIN, to be returned by the scanner.  */
    static final int MIN = 288;
    /** Token MAX, to be returned by the scanner.  */
    static final int MAX = 289;
    /** Token ASUM, to be returned by the scanner.  */
    static final int ASUM = 290;
    /** Token BSUM, to be returned by the scanner.  */
    static final int BSUM = 291;
    /** Token PROD, to be returned by the scanner.  */
    static final int PROD = 292;
    /** Token BDIF, to be returned by the scanner.  */
    static final int BDIF = 293;
    /** Token NSUM, to be returned by the scanner.  */
    static final int NSUM = 294;
    /** Token IDENTIFIER, to be returned by the scanner.  */
    static final int IDENTIFIER = 295;
    /** Token STD_FB_IDENTIFIER, to be returned by the scanner.  */
    static final int STD_FB_IDENTIFIER = 296;
    /** Token PRAGMA, to be returned by the scanner.  */
    static final int PRAGMA = 297;
    /** Token interval, to be returned by the scanner.  */
    static final int interval = 298;
    /** Token VAR_INPUT, to be returned by the scanner.  */
    static final int VAR_INPUT = 299;
    /** Token RETAIN, to be returned by the scanner.  */
    static final int RETAIN = 300;
    /** Token NON_RETAIN, to be returned by the scanner.  */
    static final int NON_RETAIN = 301;
    /** Token END_VAR, to be returned by the scanner.  */
    static final int END_VAR = 302;
    /** Token BOOL, to be returned by the scanner.  */
    static final int BOOL = 303;
    /** Token R_EDGE, to be returned by the scanner.  */
    static final int R_EDGE = 304;
    /** Token F_EDGE, to be returned by the scanner.  */
    static final int F_EDGE = 305;
    /** Token VAR_OUTPUT, to be returned by the scanner.  */
    static final int VAR_OUTPUT = 306;
    /** Token VAR, to be returned by the scanner.  */
    static final int VAR = 307;
    /** Token CONSTANT, to be returned by the scanner.  */
    static final int CONSTANT = 308;
    /** Token STRING, to be returned by the scanner.  */
    static final int STRING = 309;
    /** Token WSTRING, to be returned by the scanner.  */
    static final int WSTRING = 310;
    /** Token BYTE, to be returned by the scanner.  */
    static final int BYTE = 311;
    /** Token WORD, to be returned by the scanner.  */
    static final int WORD = 312;
    /** Token DWORD, to be returned by the scanner.  */
    static final int DWORD = 313;
    /** Token LWORD, to be returned by the scanner.  */
    static final int LWORD = 314;
    /** Token TIME, to be returned by the scanner.  */
    static final int TIME = 315;
    /** Token TIME_OF_DAY, to be returned by the scanner.  */
    static final int TIME_OF_DAY = 316;
    /** Token TOD, to be returned by the scanner.  */
    static final int TOD = 317;
    /** Token DATE, to be returned by the scanner.  */
    static final int DATE = 318;
    /** Token DATE_AND_TIME, to be returned by the scanner.  */
    static final int DATE_AND_TIME = 319;
    /** Token DT, to be returned by the scanner.  */
    static final int DT = 320;
    /** Token OF, to be returned by the scanner.  */
    static final int OF = 321;
    /** Token SINT, to be returned by the scanner.  */
    static final int SINT = 322;
    /** Token INT, to be returned by the scanner.  */
    static final int INT = 323;
    /** Token DINT, to be returned by the scanner.  */
    static final int DINT = 324;
    /** Token LINT, to be returned by the scanner.  */
    static final int LINT = 325;
    /** Token USINT, to be returned by the scanner.  */
    static final int USINT = 326;
    /** Token UINT, to be returned by the scanner.  */
    static final int UINT = 327;
    /** Token UDINT, to be returned by the scanner.  */
    static final int UDINT = 328;
    /** Token ULINT, to be returned by the scanner.  */
    static final int ULINT = 329;
    /** Token REAL, to be returned by the scanner.  */
    static final int REAL = 330;
    /** Token LREAL, to be returned by the scanner.  */
    static final int LREAL = 331;
    /** Token TRUE, to be returned by the scanner.  */
    static final int TRUE = 332;
    /** Token FALSE, to be returned by the scanner.  */
    static final int FALSE = 333;
    /** Token BINARY_INTEGER, to be returned by the scanner.  */
    static final int BINARY_INTEGER = 334;
    /** Token OCTAL_INTEGER, to be returned by the scanner.  */
    static final int OCTAL_INTEGER = 335;
    /** Token HEX_INTEGER, to be returned by the scanner.  */
    static final int HEX_INTEGER = 336;
    /** Token CHARACTER_STRING, to be returned by the scanner.  */
    static final int CHARACTER_STRING = 337;
    /** Token ARRAY, to be returned by the scanner.  */
    static final int ARRAY = 338;
    /** Token INTEGER_NUMBER, to be returned by the scanner.  */
    static final int INTEGER_NUMBER = 339;
    /** Token REAL_NUMBER, to be returned by the scanner.  */
    static final int REAL_NUMBER = 340;
    /** Token SIGNED_INTEGER, to be returned by the scanner.  */
    static final int SIGNED_INTEGER = 341;

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





  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public Parser(Lexer yylexer)
  {

    this.yylexer = yylexer;

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
        
/* "Parser.java":1023  */

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

  private static final short yypact_ninf_ = -376;
  private static final short yytable_ninf_ = -193;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      25,     8,    76,  -376,  -376,  -376,    37,   129,   129,  -376,
      57,  -376,  -376,  -376,  -376,    81,    81,    87,  -376,   138,
     140,  -376,    20,   -18,  -376,  -376,  -376,  -376,   107,   -16,
    -376,  -376,    81,  -376,   110,  -376,   157,   340,  -376,  -376,
      81,    61,  -376,    81,   -15,  -376,   150,  -376,   183,   108,
    -376,   151,   160,   143,  -376,  -376,  -376,  -376,  -376,  -376,
    -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,
    -376,  -376,  -376,  -376,   127,   190,  -376,  -376,  -376,   158,
    -376,  -376,   147,  -376,  -376,  -376,  -376,  -376,  -376,  -376,
     162,  -376,   172,  -376,   173,  -376,  -376,  -376,  -376,   186,
    -376,   340,  -376,  -376,  -376,    92,   223,   234,  -376,   266,
     192,  -376,  -376,  -376,   -62,  -376,  -376,   -53,  -376,  -376,
     195,   111,   238,   242,   197,   195,   198,   200,   202,   206,
    -376,  -376,   196,  -376,   255,  -376,   208,  -376,   270,  -376,
    -376,   194,   211,   216,   214,   219,   221,   213,   227,  -376,
     275,   235,   121,   294,   240,   244,   295,   236,   247,   250,
     253,   397,   212,   254,   322,   334,    21,   279,   280,   281,
     283,   284,   288,   289,  -376,  -376,  -376,   320,   290,   291,
    -376,   292,   293,   296,  -376,  -376,    11,  -376,   321,  -376,
    -376,  -376,  -376,  -376,  -376,   130,  -376,    -2,  -376,   336,
     318,   338,   323,   328,   195,   356,   144,   385,  -376,  -376,
    -376,  -376,   332,   249,  -376,  -376,   247,   339,   335,   342,
     416,    59,   345,   421,  -376,   388,   398,  -376,   434,  -376,
     436,  -376,   -27,   402,   402,   404,   -37,   403,   149,  -376,
    -376,  -376,  -376,   -26,   404,  -376,  -376,  -376,  -376,  -376,
    -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,
    -376,   405,  -376,   406,   408,  -376,   377,  -376,  -376,   152,
     449,   401,  -376,  -376,   -61,  -376,  -376,  -376,  -376,   455,
     407,   131,   409,   489,  -376,  -376,  -376,  -376,   156,   412,
    -376,   106,  -376,   414,  -376,  -376,   458,   415,  -376,  -376,
     410,  -376,   413,   417,   320,  -376,  -376,   460,  -376,   419,
     420,  -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,    56,
     249,  -376,   422,   397,  -376,  -376,  -376,  -376,  -376,   423,
     425,  -376,  -376,  -376,   426,    10,    93,  -376,  -376,  -376,
    -376,  -376,  -376,   430,   432,   402,   109,  -376,  -376,   428,
     435,    14,   427,  -376,   491,   429,  -376,   -17,  -376,  -376,
    -376,  -376,   431,  -376,   411,  -376,   437,   433,  -376,  -376,
    -376,  -376,  -376,  -376,  -376,   478,  -376,   482,   438,  -376,
     439,  -376,  -376,   443,   441,   442,   440,   444,  -376,  -376,
     505,   445,  -376,  -376,  -376,   446,    -5,   451,   387,    -4,
      39,    -5,   514,   222,  -376,  -376,   397,   447,  -376,  -376,
     450,  -376,  -376,   516,  -376,     3,    -3,    -1,  -376,   452,
     497,  -376,   454,  -376,  -376,   501,  -376,    39,   222,    39,
     222,  -376,    -9,   502,   503,   397,  -376,  -376,  -376,  -376,
    -376,   506,   524,    -8,   456,   459,   509,   148,   462,   463,
    -376,    -7,  -376,  -376,  -376,  -376,   512,  -376,   465,   464,
    -376,   397,   466,  -376
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
       0,     0,     0,     2,     4,     1,     8,   102,   102,     5,
      12,     7,     6,   100,   101,     0,     0,   104,     9,     0,
      15,    10,     0,     0,    96,    98,   109,   110,     0,     0,
     105,   103,     0,     3,     0,    13,    39,     0,   253,    95,
       0,     0,    94,     0,     0,    18,     0,    16,    85,   111,
     255,   117,   120,   122,   197,   198,   199,   200,   136,   152,
     153,   151,   154,   155,   141,   142,   143,   144,   145,   146,
     147,   148,   149,   150,     0,     0,   107,   124,   126,   129,
     132,   133,   137,   139,   140,   138,   134,   135,   156,   127,
     205,   128,     0,   116,   219,   209,   131,   210,   115,   249,
      97,     0,   254,   106,    99,     0,    37,     0,    40,    11,
       0,   118,   119,   112,     0,   113,   121,     0,   114,   123,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     108,   125,   250,    14,     0,    19,     0,    18,    43,    90,
      86,   195,     0,     0,     0,     0,     0,   223,     0,   213,
       0,     0,     0,   195,     0,     0,     0,     0,     0,     0,
       0,   169,     0,     0,    53,    45,     0,   245,     0,     0,
       0,     0,     0,     0,   190,   191,   158,     0,     0,     0,
     157,     0,     0,     0,   246,    88,     0,    89,     0,   159,
     171,   172,   173,   174,   161,     0,   160,   201,   248,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   215,   130,
     207,   206,   211,   195,   220,   251,     0,     0,     0,     0,
       0,     0,     0,     0,    54,     0,     0,    87,     0,    91,
       0,   193,     0,     0,     0,     0,     0,     0,     0,   237,
     240,   239,   241,     0,     0,   163,   170,   196,   165,   166,
     167,   164,   162,   168,   188,   189,   202,   203,   204,   194,
     257,   256,   260,   259,     0,   224,     0,   217,   214,     0,
       0,     0,   233,   235,     0,   227,   229,   234,   252,     0,
       0,     0,     0,     0,    47,    48,    49,    44,     0,     0,
      62,     0,    42,    92,   247,   176,     0,     0,   181,   182,
       0,   184,     0,     0,     0,   236,   175,     0,   185,     0,
       0,   208,   221,   225,   137,   222,   218,   216,   212,   195,
     195,   226,     0,   169,    29,    30,    31,    32,    33,     0,
       0,    17,    57,    56,     0,     0,     0,    50,    51,    52,
      46,    93,   178,     0,     0,     0,   195,   238,   177,     0,
       0,     0,     0,   228,     0,     0,    28,   169,    55,    59,
      60,    61,     0,    41,     0,    63,     0,     0,   187,   243,
     244,   242,   258,   261,   230,     0,   232,     0,     0,    36,
       0,    35,    58,     0,     0,     0,     0,     0,    38,    34,
       0,   179,   183,   186,   231,     0,     0,     0,   169,     0,
      71,     0,     0,    71,    78,   180,   169,     0,    21,    22,
      24,    25,    23,    77,    76,     0,     0,     0,    70,     0,
       0,    69,     0,    20,    26,     0,    80,    71,    71,    71,
      71,    79,     0,     0,     0,   169,    81,    72,    74,    73,
      75,     0,     0,    66,     0,     0,     0,   169,     0,     0,
      82,    66,    68,    67,    84,    64,     0,    83,     0,     0,
      65,   169,     0,    27
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,
    -376,  -376,   424,  -376,  -376,  -376,  -376,  -376,   146,  -376,
    -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,
    -376,  -376,  -376,  -376,  -376,  -376,  -376,  -376,   112,   159,
    -328,  -375,  -376,  -376,  -376,  -376,  -376,  -160,  -376,  -376,
    -376,  -376,  -376,   517,  -376,   551,  -376,   530,    29,   467,
    -376,  -376,  -376,  -376,   468,  -376,  -376,   298,  -376,   -35,
    -376,  -376,   -34,  -376,   -33,  -135,   201,  -376,  -376,  -376,
    -376,  -376,  -376,  -376,  -376,  -223,   330,   326,  -376,  -376,
    -376,  -376,  -376,  -376,  -376,  -376,  -376,   448,  -376,  -376,
    -376,  -376,  -376,   363,  -376,  -150,  -376,   251,   256,  -136,
    -376,   268,  -376,  -169,  -168,  -167,  -376,  -376,   -75,   473,
    -376,  -376
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,     3,     6,     9,    10,    18,    19,    20,    35,
      36,    47,   105,   135,   407,   408,   409,   410,   411,   220,
     329,   283,   380,   137,    48,   108,   164,   165,   226,   287,
     340,   223,   224,   334,   290,   362,   336,   365,   448,   402,
     418,   403,   404,   433,   453,   109,   140,   180,   166,   229,
      11,    12,    23,    24,    21,    15,    32,    29,    25,    76,
     113,   115,   118,    26,    77,    78,    79,    80,    81,   181,
      83,    84,   182,    86,   183,   272,   185,   186,   252,   187,
     188,   189,   190,   392,   191,   298,   192,   301,   193,   194,
     195,   196,   197,    88,   259,    89,    90,   147,    91,    92,
     122,    93,    94,   148,   315,   273,   274,   275,   276,   277,
     238,   239,   371,    95,    96,    97,    98,    27,    28,    99,
     116,   119
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
     123,   218,    82,    85,    87,   198,   184,   214,   240,   241,
     242,   299,   447,   447,   441,   379,   295,   306,   209,   399,
     399,   399,   215,   399,   414,   142,   172,   425,     1,    39,
     320,    42,   104,   227,   144,   400,   413,   427,   143,   429,
     321,   428,   430,   426,   359,    30,   360,   145,     4,   361,
      64,    65,    66,    67,    68,    69,    70,    71,    72,    73,
     179,    30,   415,   228,   416,   417,    82,    85,    87,   296,
     307,    40,   103,    43,    43,   421,     5,   256,   257,   258,
     278,     7,    38,   102,   457,   401,   401,   401,     8,   401,
     248,   249,   250,   284,   285,   286,   351,   251,   133,   437,
     438,   439,   440,   363,   168,   134,   374,    37,   375,    17,
     364,    38,    54,    55,    56,    57,   169,   170,   171,   172,
     173,    22,   368,    64,    65,    66,    67,    68,    69,    70,
      71,    72,    73,   174,   175,   240,   241,   242,   176,   337,
      31,  -169,    33,   338,   339,    34,   177,    41,   101,   167,
      45,   178,   102,   179,  -192,  -192,   213,   168,   324,   325,
     326,   327,   328,   355,    46,    54,    55,    56,    57,   169,
     170,   171,   172,   173,    13,    14,    64,    65,    66,    67,
      68,    69,    70,    71,    72,    73,   174,   175,   452,   332,
     106,   176,   107,   333,  -169,   110,   369,   381,   117,   177,
     111,   112,    38,   149,   178,   150,   179,  -192,  -192,   213,
     370,   184,   102,   208,   114,    64,    65,    66,    67,    68,
      69,    70,    71,    72,    73,   134,   219,   120,   254,   255,
     121,   314,    85,    87,   167,   267,   268,   125,   412,   136,
     304,   305,   168,   316,   317,   124,   422,   416,   417,   126,
      54,    55,    56,    57,   169,   170,   171,   172,   173,   127,
     128,    64,    65,    66,    67,    68,    69,    70,    71,    72,
      73,   174,   175,   129,   138,   444,   176,   139,   151,  -169,
     141,   146,   152,   159,   177,   153,   155,   454,   156,   178,
     157,   179,  -192,  -192,   158,   160,   163,   168,   161,   199,
     200,   462,   201,   202,   204,    54,    55,    56,    57,   169,
     170,   171,   172,   173,   203,   206,    64,    65,    66,    67,
      68,    69,    70,    71,    72,    73,   174,   175,   205,   207,
     211,   176,   210,   271,  -169,   212,   213,   177,   216,   177,
     217,   221,   168,   222,   178,   434,   179,  -192,  -192,   213,
      54,    55,    56,    57,   169,   170,   171,   172,   173,   225,
     237,    64,    65,    66,    67,    68,    69,    70,    71,    72,
      73,   174,   175,   230,   231,   232,   176,   233,   234,  -169,
      49,    50,   235,   236,   243,   244,   245,   246,    51,   178,
     247,   179,  -192,  -192,    52,    53,    54,    55,    56,    57,
      58,    59,    60,    61,    62,    63,   253,    64,    65,    66,
      67,    68,    69,    70,    71,    72,    73,   312,   260,   261,
     262,   264,   266,    74,   263,   269,   270,   279,   280,   281,
      75,   282,   288,    54,    55,    56,    57,    58,    59,    60,
      61,    62,    63,   289,    64,    65,    66,    67,    68,    69,
      70,    71,    72,    73,    64,    65,    66,    67,    68,    69,
      70,    71,    72,    73,    64,    65,    66,    67,    68,    69,
      70,    71,    72,    73,   293,   291,   294,   406,    64,    65,
      66,    67,    68,    69,    70,    71,   297,   292,   300,   318,
     303,   319,   309,   310,   311,   322,   330,   331,   341,   335,
     323,   342,   343,   348,   377,   346,   344,   349,   350,   345,
     372,   354,   356,   357,   366,   358,   367,   373,   386,   376,
     382,   378,   387,   396,   384,   391,   393,   388,   389,   385,
     390,   395,   394,   420,   398,   405,   423,   432,   397,   415,
     406,   436,   442,   443,   431,   435,   445,   446,   449,   451,
     450,   455,   459,   456,   460,   461,   424,   100,   463,    16,
     419,   162,    44,   458,   313,   383,   302,   265,   130,   131,
     308,   353,   347,   154,   132,   352
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      75,   161,    37,    37,    37,   141,   141,   157,   177,   177,
     177,   234,    20,    20,    23,    32,    43,    43,   153,    24,
      24,    24,   158,    24,   399,    87,    63,    24,     3,    47,
      91,    47,    47,    12,    87,    40,    40,    40,   100,    40,
     101,   416,   417,    40,    34,    16,    36,   100,    40,    39,
      67,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      97,    32,    23,    42,    25,    26,   101,   101,   101,    96,
      96,    89,    43,    89,    89,   403,     0,    79,    80,    81,
     216,    44,    91,    91,    91,    90,    90,    90,    51,    90,
      79,    80,    81,    34,    35,    36,    40,    86,     6,   427,
     428,   429,   430,    10,    48,    13,    92,    87,    94,    52,
      17,    91,    56,    57,    58,    59,    60,    61,    62,    63,
      64,    40,   345,    67,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    78,   304,   304,   304,    82,    33,
      53,    85,     4,    37,    38,     5,    90,    40,    87,    40,
      40,    95,    91,    97,    98,    99,   100,    48,    27,    28,
      29,    30,    31,   323,     7,    56,    57,    58,    59,    60,
      61,    62,    63,    64,    45,    46,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    40,    33,
      40,    82,     9,    37,    85,    87,   346,   357,    55,    90,
      49,    50,    91,    92,    95,    94,    97,    98,    99,   100,
     346,   346,    91,    92,    54,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    13,    14,   100,    98,    99,
      40,   266,   266,   266,    40,    91,    92,    90,   398,    16,
      91,    92,    48,    91,    92,    87,   406,    25,    26,    87,
      56,    57,    58,    59,    60,    61,    62,    63,    64,    87,
      87,    67,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    87,    40,   435,    82,    11,    40,    85,
      88,    86,    40,    87,    90,    88,    88,   447,    88,    95,
      88,    97,    98,    99,    88,    40,    26,    48,    90,    88,
      84,   461,    88,    84,    91,    56,    57,    58,    59,    60,
      61,    62,    63,    64,    93,    40,    67,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,   101,    94,
      86,    82,    92,    84,    85,    40,   100,    90,    88,    90,
      87,    87,    48,    21,    95,   420,    97,    98,    99,   100,
      56,    57,    58,    59,    60,    61,    62,    63,    64,    25,
      40,    67,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    94,    94,    94,    82,    94,    94,    85,
      40,    41,    94,    94,    94,    94,    94,    94,    48,    95,
      94,    97,    98,    99,    54,    55,    56,    57,    58,    59,
      60,    61,    62,    63,    64,    65,    85,    67,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    40,    82,   101,
      82,    93,    66,    83,   101,    40,    94,    88,    93,    87,
      90,    15,    87,    56,    57,    58,    59,    60,    61,    62,
      63,    64,    65,    22,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    40,    87,    40,    90,    67,    68,
      69,    70,    71,    72,    73,    74,    84,    89,    84,    40,
      87,    90,    87,    87,    86,    40,    87,     8,    84,    87,
      93,    43,    87,    43,    13,    88,    96,    88,    88,    96,
      82,    89,    89,    88,    84,    89,    84,    82,    40,    92,
      89,    92,    40,    18,    87,    84,    84,    89,    89,    96,
      87,    87,    92,    19,    88,    84,    89,    40,    93,    23,
      90,    40,    40,    40,    92,    91,    40,    23,    92,    40,
      91,    89,    40,    90,    89,    91,   410,    40,    92,     8,
     401,   137,    32,   451,   266,   364,   236,   204,   101,   101,
     244,   320,   304,   125,   101,   319
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,   103,   104,    40,     0,   105,    44,    51,   106,
     107,   152,   153,    45,    46,   157,   157,    52,   108,   109,
     110,   156,    40,   154,   155,   160,   165,   219,   220,   159,
     160,    53,   158,     4,     5,   111,   112,    87,    91,    47,
      89,    40,    47,    89,   159,    40,     7,   113,   126,    40,
      41,    48,    54,    55,    56,    57,    58,    59,    60,    61,
      62,    63,    64,    65,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    83,    90,   161,   166,   167,   168,
     169,   170,   171,   172,   173,   174,   175,   176,   195,   197,
     198,   200,   201,   203,   204,   215,   216,   217,   218,   221,
     155,    87,    91,   160,    47,   114,    40,     9,   127,   147,
      87,    49,    50,   162,    54,   163,   222,    55,   164,   223,
     100,    40,   202,   220,    87,    90,    87,    87,    87,    87,
     161,   166,   221,     6,    13,   115,    16,   125,    40,    11,
     148,    88,    87,   100,    87,   100,    86,   199,   205,    92,
      94,    40,    40,    88,   199,    88,    88,    88,    88,    87,
      40,    90,   114,    26,   128,   129,   150,    40,    48,    60,
      61,    62,    63,    64,    77,    78,    82,    90,    95,    97,
     149,   171,   174,   176,   177,   178,   179,   181,   182,   183,
     184,   186,   188,   190,   191,   192,   193,   194,   211,    88,
      84,    88,    84,    93,    91,   101,    40,    94,    92,   177,
      92,    86,    40,   100,   207,   211,    88,    87,   149,    14,
     121,    87,    21,   133,   134,    25,   130,    12,    42,   151,
      94,    94,    94,    94,    94,    94,    94,    40,   212,   213,
     215,   216,   217,    94,    94,    94,    94,    94,    79,    80,
      81,    86,   180,    85,    98,    99,    79,    80,    81,   196,
      82,   101,    82,   101,    93,   205,    66,    91,    92,    40,
      94,    84,   177,   207,   208,   209,   210,   211,   211,    88,
      93,    87,    15,   123,    34,    35,    36,   131,    87,    22,
     136,    87,    89,    40,    40,    43,    96,    84,   187,   187,
      84,   189,   188,    87,    91,    92,    43,    96,   189,    87,
      87,    86,    40,   169,   171,   206,    91,    92,    40,    90,
      91,   101,    40,    93,    27,    28,    29,    30,    31,   122,
      87,     8,    33,    37,   135,    87,   138,    33,    37,    38,
     132,    84,    43,    87,    96,    96,    88,   213,    43,    88,
      88,    40,   210,   209,    89,   149,    89,    88,    89,    34,
      36,    39,   137,    10,    17,   139,    84,    84,   187,   207,
     211,   214,    82,    82,    92,    94,    92,    13,    92,    32,
     124,   149,    89,   178,    87,    96,    40,    40,    89,    89,
      87,    84,   185,    84,    92,    87,    18,    93,    88,    24,
      40,    90,   141,   143,   144,    84,    90,   116,   117,   118,
     119,   120,   149,    40,   143,    23,    25,    26,   142,   141,
      19,   142,   149,    89,   120,    24,    40,    40,   143,    40,
     143,    92,    40,   145,   220,    91,    40,   142,   142,   142,
     142,    23,    40,    40,   149,    40,    23,    20,   140,    92,
      91,    40,    40,   146,   149,    89,    90,    91,   140,    40,
      89,    91,   149,    92
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,   102,   103,   104,   105,   105,   106,   106,   107,   107,
     108,   109,   110,   110,   111,   112,   112,   113,   114,   114,
     115,   116,   116,   117,   118,   119,   119,   120,   121,   122,
     122,   122,   122,   122,   123,   124,   124,   125,   125,   126,
     126,   127,   128,   129,   129,   130,   130,   131,   131,   131,
     132,   132,   132,   133,   133,   134,   135,   135,   136,   137,
     137,   137,   138,   138,   139,   139,   140,   140,   140,   141,
     141,   142,   142,   142,   142,   142,   143,   143,   143,   143,
     144,   144,   145,   145,   146,   147,   147,   148,   149,   149,
     150,   150,   151,   151,   152,   153,   154,   154,   155,   156,
     157,   157,   157,   158,   158,   159,   159,   160,   160,   160,
     160,   161,   161,   161,   161,   161,   161,   162,   162,   162,
     163,   163,   164,   164,   165,   165,   166,   166,   166,   167,
     167,   167,   168,   169,   169,   169,   169,   170,   170,   171,
     171,   172,   172,   172,   172,   173,   173,   173,   173,   174,
     174,   175,   175,   175,   175,   175,   176,   177,   177,   177,
     177,   177,   178,   179,   180,   180,   180,   180,   181,   182,
     182,   183,   183,   183,   183,   184,   184,   184,   184,   185,
     185,   186,   186,   187,   188,   188,   189,   190,   191,   191,
     191,   191,   192,   192,   193,   194,   194,   195,   195,   195,
     195,   196,   196,   196,   196,   197,   197,   198,   199,   200,
     200,   200,   200,   201,   201,   201,   201,   202,   202,   203,
     203,   204,   204,   205,   205,   206,   207,   208,   208,   209,
     209,   209,   209,   210,   210,   210,   211,   212,   212,   213,
     213,   213,   213,   214,   214,   215,   216,   217,   218,   219,
     219,   219,   219,   220,   220,   221,   222,   222,   222,   223,
     223,   223
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     1,     6,     0,     2,     1,     1,     0,     2,
       1,     4,     0,     2,     4,     0,     2,     7,     0,     2,
      12,     1,     1,     1,     1,     1,     2,    10,     4,     1,
       1,     1,     1,     1,     5,     1,     1,     0,     8,     0,
       2,     7,     3,     0,     3,     0,     3,     1,     1,     1,
       1,     1,     1,     0,     1,     4,     1,     1,     4,     1,
       1,     1,     0,     2,    10,    12,     0,     2,     2,     2,
       2,     0,     3,     3,     3,     3,     2,     2,     1,     3,
       3,     4,     4,     5,     1,     0,     2,     3,     1,     1,
       0,     2,     2,     3,     4,     4,     1,     3,     1,     4,
       1,     1,     0,     1,     0,     1,     3,     3,     4,     1,
       1,     1,     2,     2,     2,     1,     1,     0,     1,     1,
       0,     1,     0,     1,     3,     4,     1,     1,     1,     1,
       4,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     2,     2,     1,     1,     1,     1,     2,     0,
       2,     1,     1,     1,     1,     3,     3,     4,     4,     1,
       3,     3,     3,     5,     3,     3,     5,     5,     2,     2,
       1,     1,     0,     2,     2,     0,     2,     1,     1,     1,
       1,     0,     1,     1,     1,     1,     4,     4,     4,     1,
       1,     4,     6,     3,     5,     4,     6,     4,     5,     1,
       4,     6,     6,     1,     3,     1,     3,     1,     3,     1,
       4,     6,     4,     1,     1,     1,     3,     1,     3,     1,
       1,     1,     4,     1,     1,     4,     4,     6,     4,     3,
       4,     6,     7,     2,     3,     1,     4,     4,     7,     4,
       4,     7
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 341;
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
       2,     2,     2,     2,     2,    94,     2,     2,     2,     2,
      90,    92,     2,     2,    91,    96,    93,     2,    98,    99,
       2,     2,     2,     2,     2,     2,     2,     2,    87,    89,
       2,    88,     2,     2,     2,     2,     2,     2,    97,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,    95,     2,     2,     2,     2,     2,
       2,   100,     2,   101,     2,     2,     2,     2,     2,     2,
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
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    86
    };
  }


  private static final int YYLAST_ = 575;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 5;
  private static final int YYNTOKENS_ = 102;


}
/* "Parser.y":709  */
 /* ------------------------------- Código Java ------------------------------------ */

/*
int main() {
    return yyparse();
}

int yyerror(const char *s) {
    fprintf(stderr, "Error: %s\n", s);
    return 1;
}
*/
