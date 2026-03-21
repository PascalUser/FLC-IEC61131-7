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
/* "src/main/java/lexer_parser/Parser.y":6  */

  import java.io.*;

/* "src/main/java/lexer_parser/Parser.java":49  */

/**
 * A Bison parser, automatically generated from <tt>src/main/java/lexer_parser/Parser.y</tt>.
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
    S_ASSIGN_OP(42),               /* ASSIGN_OP  */
    S_PRAGMA(43),                  /* PRAGMA  */
    S_interval(44),                /* interval  */
    S_VAR_INPUT(45),               /* VAR_INPUT  */
    S_RETAIN(46),                  /* RETAIN  */
    S_NON_RETAIN(47),              /* NON_RETAIN  */
    S_END_VAR(48),                 /* END_VAR  */
    S_BOOL(49),                    /* BOOL  */
    S_R_EDGE(50),                  /* R_EDGE  */
    S_F_EDGE(51),                  /* F_EDGE  */
    S_VAR_OUTPUT(52),              /* VAR_OUTPUT  */
    S_VAR(53),                     /* VAR  */
    S_CONSTANT(54),                /* CONSTANT  */
    S_STRING(55),                  /* STRING  */
    S_WSTRING(56),                 /* WSTRING  */
    S_BYTE(57),                    /* BYTE  */
    S_WORD(58),                    /* WORD  */
    S_DWORD(59),                   /* DWORD  */
    S_LWORD(60),                   /* LWORD  */
    S_TIME(61),                    /* TIME  */
    S_TIME_OF_DAY(62),             /* TIME_OF_DAY  */
    S_TOD(63),                     /* TOD  */
    S_DATE(64),                    /* DATE  */
    S_DATE_AND_TIME(65),           /* DATE_AND_TIME  */
    S_DT(66),                      /* DT  */
    S_OF(67),                      /* OF  */
    S_SINT(68),                    /* SINT  */
    S_INT(69),                     /* INT  */
    S_DINT(70),                    /* DINT  */
    S_LINT(71),                    /* LINT  */
    S_USINT(72),                   /* USINT  */
    S_UINT(73),                    /* UINT  */
    S_UDINT(74),                   /* UDINT  */
    S_ULINT(75),                   /* ULINT  */
    S_REAL(76),                    /* REAL  */
    S_LREAL(77),                   /* LREAL  */
    S_TRUE(78),                    /* TRUE  */
    S_FALSE(79),                   /* FALSE  */
    S_BINARY_INTEGER(80),          /* BINARY_INTEGER  */
    S_OCTAL_INTEGER(81),           /* OCTAL_INTEGER  */
    S_HEX_INTEGER(82),             /* HEX_INTEGER  */
    S_CHARACTER_STRING(83),        /* CHARACTER_STRING  */
    S_ARRAY(84),                   /* ARRAY  */
    S_RANGE_OP(85),                /* RANGE_OP  */
    S_INTEGER_NUMBER(86),          /* INTEGER_NUMBER  */
    S_REAL_NUMBER(87),             /* REAL_NUMBER  */
    S_SIGNED_INTEGER(88),          /* SIGNED_INTEGER  */
    S_89_(89),                     /* ';'  */
    S_90_(90),                     /* '('  */
    S_91_(91),                     /* ','  */
    S_92_(92),                     /* ')'  */
    S_93_(93),                     /* ':'  */
    S_94_(94),                     /* '.'  */
    S_95_(95),                     /* '#'  */
    S_96_T_(96),                   /* 'T'  */
    S_97_(97),                     /* '-'  */
    S_98_D_(98),                   /* 'D'  */
    S_99_0_(99),                   /* '0'  */
    S_100_1_(100),                 /* '1'  */
    S_101_(101),                   /* '['  */
    S_102_(102),                   /* ']'  */
    S_YYACCEPT(103),               /* $accept  */
    S_program(104),                /* program  */
    S_function_block_declaration(105), /* function_block_declaration  */
    S_fb_io_var_declarations_list(106), /* fb_io_var_declarations_list  */
    S_fb_io_var_declarations(107), /* fb_io_var_declarations  */
    S_other_var_declarations_list(108), /* other_var_declarations_list  */
    S_other_var_declarations(109), /* other_var_declarations  */
    S_function_block_body(110),    /* function_block_body  */
    S_fuzzify_block_list(111),     /* fuzzify_block_list  */
    S_fuzzify_block(112),          /* fuzzify_block  */
    S_defuzzify_block_list(113),   /* defuzzify_block_list  */
    S_defuzzify_block(114),        /* defuzzify_block  */
    S_linguistic_term_list(115),   /* linguistic_term_list  */
    S_linguistic_term(116),        /* linguistic_term  */
    S_membership_function(117),    /* membership_function  */
    S_singleton(118),              /* singleton  */
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
      SymbolKind.S_ASSIGN_OP,
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
      SymbolKind.S_RANGE_OP,
      SymbolKind.S_INTEGER_NUMBER,
      SymbolKind.S_REAL_NUMBER,
      SymbolKind.S_SIGNED_INTEGER,
      SymbolKind.S_89_,
      SymbolKind.S_90_,
      SymbolKind.S_91_,
      SymbolKind.S_92_,
      SymbolKind.S_93_,
      SymbolKind.S_94_,
      SymbolKind.S_95_,
      SymbolKind.S_96_T_,
      SymbolKind.S_97_,
      SymbolKind.S_98_D_,
      SymbolKind.S_99_0_,
      SymbolKind.S_100_1_,
      SymbolKind.S_101_,
      SymbolKind.S_102_,
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
  "IDENTIFIER", "STD_FB_IDENTIFIER", "ASSIGN_OP", "PRAGMA", "interval",
  "VAR_INPUT", "RETAIN", "NON_RETAIN", "END_VAR", "BOOL", "R_EDGE",
  "F_EDGE", "VAR_OUTPUT", "VAR", "CONSTANT", "STRING", "WSTRING", "BYTE",
  "WORD", "DWORD", "LWORD", "TIME", "TIME_OF_DAY", "TOD", "DATE",
  "DATE_AND_TIME", "DT", "OF", "SINT", "INT", "DINT", "LINT", "USINT",
  "UINT", "UDINT", "ULINT", "REAL", "LREAL", "TRUE", "FALSE",
  "BINARY_INTEGER", "OCTAL_INTEGER", "HEX_INTEGER", "CHARACTER_STRING",
  "ARRAY", "RANGE_OP", "INTEGER_NUMBER", "REAL_NUMBER", "SIGNED_INTEGER",
  "';'", "'('", "','", "')'", "':'", "'.'", "'#'", "'T'", "'-'", "'D'",
  "'0'", "'1'", "'['", "']'", "$accept", "program",
  "function_block_declaration", "fb_io_var_declarations_list",
  "fb_io_var_declarations", "other_var_declarations_list",
  "other_var_declarations", "function_block_body", "fuzzify_block_list",
  "fuzzify_block", "defuzzify_block_list", "defuzzify_block",
  "linguistic_term_list", "linguistic_term", "membership_function",
  "singleton", "point_list", "point", "defuzzification_method",
  "defuzz_method", "default_value", "default_val", "range_opt",
  "rule_block_list", "rule_block", "operator_definition",
  "operator_or_opt", "operator_and_opt", "or_type", "and_type",
  "activation_method_opt", "activation_method", "act_type",
  "accumulation_method", "accu_type", "rule_list", "rule", "weighting_opt",
  "condition", "condition_tail", "x", "subcondition", "conclusion_list",
  "weighting_factor", "option_block_list", "option_block",
  "numeric_literal", "pragma_list", "pragma", "output_declarations",
  "input_declarations", "input_declaration_list", "input_declaration",
  "var_declarations", "var_retain_spec", "var_constant_spec",
  "var_init_decl_list", "var_init_decl", "type_declaration", "opt_edge",
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
    /** Token ASSIGN_OP, to be returned by the scanner.  */
    static final int ASSIGN_OP = 297;
    /** Token PRAGMA, to be returned by the scanner.  */
    static final int PRAGMA = 298;
    /** Token interval, to be returned by the scanner.  */
    static final int interval = 299;
    /** Token VAR_INPUT, to be returned by the scanner.  */
    static final int VAR_INPUT = 300;
    /** Token RETAIN, to be returned by the scanner.  */
    static final int RETAIN = 301;
    /** Token NON_RETAIN, to be returned by the scanner.  */
    static final int NON_RETAIN = 302;
    /** Token END_VAR, to be returned by the scanner.  */
    static final int END_VAR = 303;
    /** Token BOOL, to be returned by the scanner.  */
    static final int BOOL = 304;
    /** Token R_EDGE, to be returned by the scanner.  */
    static final int R_EDGE = 305;
    /** Token F_EDGE, to be returned by the scanner.  */
    static final int F_EDGE = 306;
    /** Token VAR_OUTPUT, to be returned by the scanner.  */
    static final int VAR_OUTPUT = 307;
    /** Token VAR, to be returned by the scanner.  */
    static final int VAR = 308;
    /** Token CONSTANT, to be returned by the scanner.  */
    static final int CONSTANT = 309;
    /** Token STRING, to be returned by the scanner.  */
    static final int STRING = 310;
    /** Token WSTRING, to be returned by the scanner.  */
    static final int WSTRING = 311;
    /** Token BYTE, to be returned by the scanner.  */
    static final int BYTE = 312;
    /** Token WORD, to be returned by the scanner.  */
    static final int WORD = 313;
    /** Token DWORD, to be returned by the scanner.  */
    static final int DWORD = 314;
    /** Token LWORD, to be returned by the scanner.  */
    static final int LWORD = 315;
    /** Token TIME, to be returned by the scanner.  */
    static final int TIME = 316;
    /** Token TIME_OF_DAY, to be returned by the scanner.  */
    static final int TIME_OF_DAY = 317;
    /** Token TOD, to be returned by the scanner.  */
    static final int TOD = 318;
    /** Token DATE, to be returned by the scanner.  */
    static final int DATE = 319;
    /** Token DATE_AND_TIME, to be returned by the scanner.  */
    static final int DATE_AND_TIME = 320;
    /** Token DT, to be returned by the scanner.  */
    static final int DT = 321;
    /** Token OF, to be returned by the scanner.  */
    static final int OF = 322;
    /** Token SINT, to be returned by the scanner.  */
    static final int SINT = 323;
    /** Token INT, to be returned by the scanner.  */
    static final int INT = 324;
    /** Token DINT, to be returned by the scanner.  */
    static final int DINT = 325;
    /** Token LINT, to be returned by the scanner.  */
    static final int LINT = 326;
    /** Token USINT, to be returned by the scanner.  */
    static final int USINT = 327;
    /** Token UINT, to be returned by the scanner.  */
    static final int UINT = 328;
    /** Token UDINT, to be returned by the scanner.  */
    static final int UDINT = 329;
    /** Token ULINT, to be returned by the scanner.  */
    static final int ULINT = 330;
    /** Token REAL, to be returned by the scanner.  */
    static final int REAL = 331;
    /** Token LREAL, to be returned by the scanner.  */
    static final int LREAL = 332;
    /** Token TRUE, to be returned by the scanner.  */
    static final int TRUE = 333;
    /** Token FALSE, to be returned by the scanner.  */
    static final int FALSE = 334;
    /** Token BINARY_INTEGER, to be returned by the scanner.  */
    static final int BINARY_INTEGER = 335;
    /** Token OCTAL_INTEGER, to be returned by the scanner.  */
    static final int OCTAL_INTEGER = 336;
    /** Token HEX_INTEGER, to be returned by the scanner.  */
    static final int HEX_INTEGER = 337;
    /** Token CHARACTER_STRING, to be returned by the scanner.  */
    static final int CHARACTER_STRING = 338;
    /** Token ARRAY, to be returned by the scanner.  */
    static final int ARRAY = 339;
    /** Token RANGE_OP, to be returned by the scanner.  */
    static final int RANGE_OP = 340;
    /** Token INTEGER_NUMBER, to be returned by the scanner.  */
    static final int INTEGER_NUMBER = 341;
    /** Token REAL_NUMBER, to be returned by the scanner.  */
    static final int REAL_NUMBER = 342;
    /** Token SIGNED_INTEGER, to be returned by the scanner.  */
    static final int SIGNED_INTEGER = 343;

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
        
/* "src/main/java/lexer_parser/Parser.java":1027  */

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

  private static final short yypact_ninf_ = -370;
  private static final short yytable_ninf_ = -196;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      32,     3,    52,  -370,  -370,  -370,   -15,    88,    88,  -370,
      -4,  -370,  -370,  -370,  -370,    28,    28,    23,  -370,    75,
      92,  -370,   105,    26,  -370,  -370,  -370,  -370,   106,    68,
    -370,  -370,    28,  -370,   149,  -370,   201,  -370,   154,    42,
     146,    46,   132,  -370,   196,  -370,   232,   203,  -370,    93,
     188,   221,  -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,
    -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,
    -370,  -370,   178,   244,  -370,  -370,  -370,   246,  -370,  -370,
     200,  -370,  -370,  -370,  -370,  -370,  -370,  -370,   278,  -370,
     284,  -370,   306,  -370,  -370,  -370,  -370,   307,  -370,  -370,
    -370,   154,  -370,  -370,    53,    89,   270,   323,  -370,   313,
     238,  -370,  -370,  -370,   -26,  -370,  -370,   -25,  -370,  -370,
     276,    90,   326,   327,    49,   276,   283,   330,   273,   285,
    -370,  -370,   334,  -370,  -370,   337,  -370,   289,  -370,   359,
    -370,  -370,   292,   293,   294,   315,   316,   317,   318,  -370,
    -370,  -370,   346,   319,   320,  -370,   321,   332,   353,  -370,
    -370,   -10,  -370,   362,  -370,  -370,  -370,  -370,  -370,  -370,
     160,  -370,   124,  -370,   367,   365,   369,   368,   371,   364,
     351,  -370,   418,   376,   190,  -370,   380,  -370,   378,   282,
    -370,  -370,   285,   417,   393,   291,   381,   454,   451,    21,
     437,  -370,   -23,   392,   392,   395,   -45,   440,   227,  -370,
    -370,  -370,  -370,   -16,   395,  -370,  -370,  -370,  -370,  -370,
    -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,
    -370,   377,  -370,   382,   397,   276,   416,   231,   446,  -370,
    -370,   447,   398,  -370,  -370,   -22,  -370,  -370,  -370,  -370,
     449,   396,   399,   476,   239,   400,   472,  -370,   402,   407,
    -370,   457,  -370,  -370,  -370,   455,   405,  -370,  -370,   403,
    -370,   404,   101,   346,  -370,  -370,   458,  -370,   461,   462,
    -370,  -370,   360,  -370,  -370,   241,  -370,   193,   282,  -370,
     419,   411,   125,   464,   499,  -370,  -370,  -370,  -370,   150,
     420,  -370,    67,  -370,   423,  -370,   424,   425,   392,  -370,
    -370,  -370,  -370,  -370,   429,   431,  -370,  -370,  -370,  -370,
    -370,  -370,   -61,   426,  -370,   502,   393,  -370,  -370,  -370,
    -370,  -370,   427,   -14,  -370,  -370,  -370,   428,   103,    86,
    -370,  -370,  -370,  -370,  -370,   430,   422,  -370,  -370,  -370,
    -370,   480,  -370,   481,   432,  -370,  -370,   433,  -370,  -370,
    -370,  -370,  -370,   436,  -370,   333,  -370,   441,   442,   434,
     487,   443,  -370,  -370,   438,   439,  -370,  -370,  -370,   370,
    -370,   512,   448,   393,   450,  -370,   445,  -370,  -370,    -2,
    -370,   452,  -370,  -370,    -1,   209,    -2,   517,   304,  -370,
     393,   514,  -370,     8,     1,     2,  -370,   453,   498,  -370,
     456,   500,  -370,   209,   304,   209,   304,  -370,    -8,   501,
     504,   459,  -370,  -370,  -370,  -370,  -370,   506,   519,    -7,
     507,   460,   510,   322,   463,   465,  -370,    -6,  -370,  -370,
    -370,  -370,   393,  -370,   466,   467,  -370,  -370
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
       0,     0,     0,     2,     4,     1,     8,   101,   101,     5,
      12,     7,     6,    99,   100,     0,     0,   103,     9,     0,
      15,    10,     0,     0,    95,    97,   108,   109,     0,     0,
     104,   102,     0,     3,     0,    13,    38,   253,     0,     0,
       0,     0,     0,    18,     0,    16,    84,   110,   255,   116,
     119,   121,   197,   198,   199,   200,   135,   151,   152,   150,
     153,   154,   140,   141,   142,   143,   144,   145,   146,   147,
     148,   149,     0,     0,   106,   123,   125,   128,   131,   132,
     136,   138,   139,   137,   133,   134,   155,   126,   205,   127,
       0,   115,   219,   209,   130,   210,   114,   249,    94,    96,
     254,     0,    93,   105,     0,     0,    36,     0,    39,    11,
     162,   117,   118,   111,     0,   112,   120,     0,   113,   122,
       0,     0,     0,     0,   162,     0,     0,     0,     0,     0,
     107,   124,   250,    98,    14,     0,    19,     0,    18,    42,
      89,    85,   245,     0,     0,     0,     0,     0,     0,   190,
     191,   157,     0,     0,     0,   156,     0,     0,     0,   246,
      87,     0,    88,     0,   158,   171,   172,   173,   174,   160,
       0,   159,   201,   248,     0,     0,     0,     0,     0,   223,
       0,   213,     0,     0,     0,   129,     0,   206,   211,   162,
     220,   251,     0,     0,   162,     0,     0,    52,    44,     0,
       0,   193,     0,     0,     0,     0,     0,     0,     0,   237,
     240,   239,   241,     0,     0,   163,   170,   196,   165,   166,
     167,   164,   161,   168,   188,   189,   202,   203,   204,   194,
     257,     0,   260,     0,     0,     0,     0,     0,     0,   215,
     207,     0,     0,   233,   235,     0,   227,   229,   234,   252,
       0,     0,     0,     0,     0,     0,     0,    53,     0,     0,
      86,     0,    90,   247,   176,     0,     0,   181,   182,     0,
     184,     0,   162,     0,   236,   175,     0,   185,   256,   259,
     208,   224,     0,   217,   214,     0,   212,   162,   162,   226,
       0,     0,     0,     0,     0,    46,    47,    48,    43,     0,
       0,    61,     0,    41,    91,   178,     0,     0,     0,   243,
     244,   242,   238,   177,     0,     0,   221,   225,   136,   222,
     218,   216,     0,     0,   228,     0,   162,    28,    29,    30,
      31,    32,     0,   162,    17,    56,    55,     0,     0,     0,
      49,    50,    51,    45,    92,     0,     0,   187,   258,   261,
     230,     0,   232,     0,     0,    27,    35,     0,    34,    54,
      58,    59,    60,     0,    40,   162,    62,     0,     0,     0,
       0,     0,    33,    57,     0,   179,   183,   186,   231,   162,
      37,     0,     0,   162,     0,    21,    22,    24,    23,     0,
     180,     0,    20,    25,     0,    70,     0,     0,    70,    77,
     162,    76,    75,     0,     0,     0,    69,     0,     0,    68,
       0,     0,    79,    70,    70,    70,    70,    78,     0,     0,
       0,     0,    80,    71,    73,    72,    74,     0,     0,    65,
       0,     0,     0,   162,     0,     0,    81,    65,    67,    66,
      83,    63,   162,    82,     0,     0,    64,    26
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,
    -370,  -370,   415,  -370,  -370,  -370,  -370,   168,  -370,  -370,
    -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,  -370,
    -370,  -370,  -370,  -370,  -370,  -370,  -370,   120,   162,  -369,
    -354,  -370,  -370,  -370,  -370,  -370,  -193,  -370,  -370,  -370,
    -370,  -370,   521,  -370,   553,  -370,   530,    -5,   468,  -370,
    -370,  -370,  -370,   469,  -370,  -370,   281,  -370,   -36,  -370,
    -370,   -35,  -370,   -34,  -104,   199,  -370,  -370,  -370,  -370,
    -370,  -370,  -370,  -370,  -192,   361,   352,  -370,  -370,  -370,
    -370,  -370,  -370,  -370,  -370,  -370,   470,  -370,  -370,  -370,
    -370,  -370,   336,  -370,  -121,  -370,   277,   286,  -105,  -370,
     295,  -370,  -144,  -143,  -142,  -370,  -370,   -73,   471,  -370,
    -370
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,     3,     6,     9,    10,    18,    19,    20,    35,
      36,    45,   105,   136,   384,   385,   386,   387,   253,   332,
     294,   357,   138,    46,   108,   197,   198,   259,   298,   343,
     256,   257,   337,   301,   363,   339,   366,   434,   397,   406,
     398,   399,   419,   439,   109,   141,   155,   199,   262,    11,
      12,    23,    24,    21,    15,    32,    29,    25,    74,   113,
     115,   118,    26,    75,    76,    77,    78,    79,   156,    81,
      82,   157,    84,   158,   243,   160,   161,   222,   162,   163,
     164,   165,   376,   166,   267,   167,   270,   168,   169,   170,
     171,   172,    86,   229,    87,    88,   179,    89,    90,   122,
      91,    92,   180,   319,   244,   245,   246,   247,   248,   208,
     209,   311,    93,    94,    95,    96,    27,    28,    97,   116,
     119
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
     123,   251,    80,    83,    85,   173,   159,   190,   210,   211,
     212,    30,   268,   433,   433,   427,   174,   176,   356,   147,
     185,   264,   394,   394,   191,   394,   394,    30,   275,   409,
       7,   350,   411,   260,   351,     1,   103,     8,   395,   401,
     402,   413,   415,     4,   423,   424,   425,   426,   412,    17,
     414,   416,     5,   154,    62,    63,    64,    65,    66,    67,
      68,    69,    70,    71,   261,    80,    83,    85,    22,   288,
     218,   219,   220,  -169,   265,   175,   177,    31,   221,    33,
     289,   276,    22,    37,   100,   443,    22,   249,   396,   396,
      98,   396,   396,    22,   102,   134,   364,    34,   143,   103,
     340,   133,   135,   365,   341,   342,    52,    53,    54,    55,
     144,   145,   146,   147,   148,    39,   347,    62,    63,    64,
      65,    66,    67,    68,    69,    70,    71,   149,   150,   210,
     211,   212,   151,   354,    13,    14,  -169,   360,  -195,   361,
     358,   142,   362,   111,   112,   153,    40,   154,  -192,  -192,
     143,   309,   327,   328,   329,   330,   331,    41,    52,    53,
      54,    55,   144,   145,   146,   147,   148,   310,   159,    62,
      63,    64,    65,    66,    67,    68,    69,    70,    71,   149,
     150,    37,   181,   335,   151,   182,   388,   336,  -169,    43,
     391,   152,  -195,  -195,    47,    48,    37,   153,    38,   154,
    -192,  -192,   189,    49,   226,   227,   228,   410,    44,    50,
      51,    52,    53,    54,    55,    56,    57,    58,    59,    60,
      61,   104,    62,    63,    64,    65,    66,    67,    68,    69,
      70,    71,   403,   322,   404,   405,   106,   100,    72,   101,
     440,   107,   143,   114,    73,   110,   318,    83,    85,   445,
      52,    53,    54,    55,   144,   145,   146,   147,   148,   224,
     225,    62,    63,    64,    65,    66,    67,    68,    69,    70,
      71,   149,   150,   295,   296,   297,   151,   117,   142,   120,
    -169,   100,   239,   152,   121,  -195,   137,   143,   124,   153,
     125,   154,  -192,  -192,   189,    52,    53,    54,    55,   144,
     145,   146,   147,   148,   135,   252,    62,    63,    64,    65,
      66,    67,    68,    69,    70,    71,   149,   150,   273,   274,
     126,   151,   283,   284,   140,  -169,   127,  -195,   152,   404,
     405,   143,   320,   321,   153,   420,   154,  -192,  -192,    52,
      53,    54,    55,   144,   145,   146,   147,   148,   128,   129,
      62,    63,    64,    65,    66,    67,    68,    69,    70,    71,
     149,   150,   438,   139,   178,   151,   183,   184,   242,  -169,
     188,   187,   152,  -195,   189,   152,   192,   193,   153,   194,
     154,  -192,  -192,   189,  -195,   196,   207,   200,   201,   202,
      62,    63,    64,    65,    66,    67,    68,    69,    70,    71,
     316,    62,    63,    64,    65,    66,    67,    68,    69,  -169,
     203,   204,   205,   206,   213,   214,   215,    52,    53,    54,
      55,    56,    57,    58,    59,    60,    61,   216,    62,    63,
      64,    65,    66,    67,    68,    69,    70,    71,    62,    63,
      64,    65,    66,    67,    68,    69,    70,    71,   217,   223,
     230,   231,   232,   236,   233,   235,   234,  -169,   237,   250,
     383,    62,    63,    64,    65,    66,    67,    68,    69,    70,
      71,   238,   240,   241,   254,   255,   258,   263,   266,   278,
    -169,   269,   272,   282,   279,   280,   285,   286,   287,   290,
     291,   293,   292,   299,   300,   302,   303,   304,   306,   305,
     307,   308,   313,   314,   315,   326,   333,   334,   325,   344,
     345,   346,   348,   338,   349,   353,   355,   359,   352,   368,
     369,   370,   372,   367,   371,   373,   378,   375,   377,   379,
     389,   381,   380,   382,   390,   383,   408,   403,   418,   392,
     422,   428,   432,   400,   429,   417,   431,   435,   421,   430,
     437,   436,   441,   195,   393,   446,   442,   444,   407,   447,
      99,    16,    42,   317,   374,   324,   277,   271,   312,   130,
     131,   281,   132,   323,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,   186
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      73,   194,    38,    38,    38,   110,   110,   128,   152,   152,
     152,    16,   204,    20,    20,    23,    42,    42,    32,    64,
     124,    44,    24,    24,   129,    24,    24,    32,    44,   398,
      45,    92,    24,    12,    95,     3,    41,    52,    40,    40,
     394,    40,    40,    40,   413,   414,   415,   416,    40,    53,
     404,   405,     0,    98,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    43,   101,   101,   101,    40,    91,
      80,    81,    82,    87,    97,   101,   101,    54,    88,     4,
     102,    97,    40,    91,    91,    91,    40,   192,    90,    90,
      48,    90,    90,    40,    48,     6,    10,     5,    49,   104,
      33,    48,    13,    17,    37,    38,    57,    58,    59,    60,
      61,    62,    63,    64,    65,    89,   308,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,   273,
     273,   273,    83,   326,    46,    47,    87,    34,    89,    36,
     333,    40,    39,    50,    51,    96,    40,    98,    99,   100,
      49,   272,    27,    28,    29,    30,    31,    89,    57,    58,
      59,    60,    61,    62,    63,    64,    65,   272,   272,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    91,    92,    33,    83,    95,   379,    37,    87,    40,
     383,    90,    91,    92,    40,    41,    91,    96,    93,    98,
      99,   100,   101,    49,    80,    81,    82,   400,     7,    55,
      56,    57,    58,    59,    60,    61,    62,    63,    64,    65,
      66,    89,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    23,    40,    25,    26,    40,    91,    84,    93,
     433,     9,    49,    55,    90,    42,   282,   282,   282,   442,
      57,    58,    59,    60,    61,    62,    63,    64,    65,    99,
     100,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    34,    35,    36,    83,    56,    40,   101,
      87,    91,    92,    90,    40,    92,    16,    49,    42,    96,
      90,    98,    99,   100,   101,    57,    58,    59,    60,    61,
      62,    63,    64,    65,    13,    14,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    91,    92,
      42,    83,    91,    92,    11,    87,    42,    89,    90,    25,
      26,    49,    91,    92,    96,   408,    98,    99,   100,    57,
      58,    59,    60,    61,    62,    63,    64,    65,    42,    42,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    40,    40,    88,    83,    40,    40,    86,    87,
      40,    88,    90,    91,   101,    90,    42,    40,    96,    90,
      98,    99,   100,   101,   102,    26,    40,    95,    95,    95,
      68,    69,    70,    71,    72,    73,    74,    75,    76,    77,
      40,    68,    69,    70,    71,    72,    73,    74,    75,    87,
      95,    95,    95,    95,    95,    95,    95,    57,    58,    59,
      60,    61,    62,    63,    64,    65,    66,    95,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    68,    69,
      70,    71,    72,    73,    74,    75,    76,    77,    95,    87,
      83,    86,    83,   102,    86,    91,    85,    87,    40,    42,
      90,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    95,    92,    95,    93,    21,    25,    40,    86,   102,
      87,    86,    42,    67,   102,    88,    40,    40,    90,    40,
      94,    15,    93,    93,    22,    93,    89,    40,    93,    44,
      97,    97,    44,    42,    42,    94,    42,     8,    89,    86,
      86,    86,    83,    93,    83,    13,    89,    89,    92,    97,
      40,    40,    89,    93,    92,    89,    92,    86,    86,    42,
      18,    93,    89,    94,    86,    90,    19,    23,    40,    89,
      40,    40,    23,    91,    40,    92,    40,    40,    92,    90,
      40,    91,    89,   138,   386,    89,    91,   437,   396,    92,
      39,     8,    32,   282,   365,   288,   214,   206,   273,   101,
     101,   235,   101,   287,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,   125
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,   104,   105,    40,     0,   106,    45,    52,   107,
     108,   152,   153,    46,    47,   157,   157,    53,   109,   110,
     111,   156,    40,   154,   155,   160,   165,   219,   220,   159,
     160,    54,   158,     4,     5,   112,   113,    91,    93,    89,
      40,    89,   159,    40,     7,   114,   126,    40,    41,    49,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    64,
      65,    66,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    84,    90,   161,   166,   167,   168,   169,   170,
     171,   172,   173,   174,   175,   176,   195,   197,   198,   200,
     201,   203,   204,   215,   216,   217,   218,   221,    48,   155,
      91,    93,    48,   160,    89,   115,    40,     9,   127,   147,
      42,    50,    51,   162,    55,   163,   222,    56,   164,   223,
     101,    40,   202,   220,    42,    90,    42,    42,    42,    42,
     161,   166,   221,    48,     6,    13,   116,    16,   125,    40,
      11,   148,    40,    49,    61,    62,    63,    64,    65,    78,
      79,    83,    90,    96,    98,   149,   171,   174,   176,   177,
     178,   179,   181,   182,   183,   184,   186,   188,   190,   191,
     192,   193,   194,   211,    42,   101,    42,   101,    88,   199,
     205,    92,    95,    40,    40,   177,   199,    88,    40,   101,
     207,   211,    42,    40,    90,   115,    26,   128,   129,   150,
      95,    95,    95,    95,    95,    95,    95,    40,   212,   213,
     215,   216,   217,    95,    95,    95,    95,    95,    80,    81,
      82,    88,   180,    87,    99,   100,    80,    81,    82,   196,
      83,    86,    83,    86,    85,    91,   102,    40,    95,    92,
      92,    95,    86,   177,   207,   208,   209,   210,   211,   211,
      42,   149,    14,   121,    93,    21,   133,   134,    25,   130,
      12,    43,   151,    40,    44,    97,    86,   187,   187,    86,
     189,   188,    42,    91,    92,    44,    97,   189,   102,   102,
      88,   205,    67,    91,    92,    40,    40,    90,    91,   102,
      40,    94,    93,    15,   123,    34,    35,    36,   131,    93,
      22,   136,    93,    89,    40,    44,    93,    97,    97,   207,
     211,   214,   213,    44,    42,    42,    40,   169,   171,   206,
      91,    92,    40,   210,   209,    89,    94,    27,    28,    29,
      30,    31,   122,    42,     8,    33,    37,   135,    93,   138,
      33,    37,    38,   132,    86,    86,    86,   187,    83,    83,
      92,    95,    92,    13,   149,    89,    32,   124,   149,    89,
      34,    36,    39,   137,    10,    17,   139,    93,    97,    40,
      40,    92,    89,    89,   178,    86,   185,    86,    92,    42,
      89,    93,    94,    90,   117,   118,   119,   120,   149,    18,
      86,   149,    89,   120,    24,    40,    90,   141,   143,   144,
      91,    40,   143,    23,    25,    26,   142,   141,    19,   142,
     149,    24,    40,    40,   143,    40,   143,    92,    40,   145,
     220,    92,    40,   142,   142,   142,   142,    23,    40,    40,
      90,    40,    23,    20,   140,    40,    91,    40,    40,   146,
     149,    89,    91,    91,   140,   149,    89,    92
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,   103,   104,   105,   106,   106,   107,   107,   108,   108,
     109,   110,   111,   111,   112,   113,   113,   114,   115,   115,
     116,   117,   117,   118,   119,   119,   120,   121,   122,   122,
     122,   122,   122,   123,   124,   124,   125,   125,   126,   126,
     127,   128,   129,   129,   130,   130,   131,   131,   131,   132,
     132,   132,   133,   133,   134,   135,   135,   136,   137,   137,
     137,   138,   138,   139,   139,   140,   140,   140,   141,   141,
     142,   142,   142,   142,   142,   143,   143,   143,   143,   144,
     144,   145,   145,   146,   147,   147,   148,   149,   149,   150,
     150,   151,   151,   152,   153,   154,   154,   155,   156,   157,
     157,   157,   158,   158,   159,   159,   160,   160,   160,   160,
     161,   161,   161,   161,   161,   161,   162,   162,   162,   163,
     163,   164,   164,   165,   165,   166,   166,   166,   167,   167,
     167,   168,   169,   169,   169,   169,   170,   170,   171,   171,
     172,   172,   172,   172,   173,   173,   173,   173,   174,   174,
     175,   175,   175,   175,   175,   176,   177,   177,   177,   177,
     177,   178,   179,   179,   180,   180,   180,   180,   181,   182,
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
      10,     1,     1,     1,     1,     2,    10,     4,     1,     1,
       1,     1,     1,     4,     1,     1,     0,     8,     0,     2,
       7,     3,     0,     3,     0,     3,     1,     1,     1,     1,
       1,     1,     0,     1,     4,     1,     1,     4,     1,     1,
       1,     0,     2,    10,    12,     0,     2,     2,     2,     2,
       0,     3,     3,     3,     3,     2,     2,     1,     3,     3,
       4,     4,     5,     1,     0,     2,     3,     1,     1,     0,
       2,     2,     3,     5,     5,     1,     3,     1,     5,     1,
       1,     0,     1,     0,     1,     3,     3,     4,     1,     1,
       1,     2,     2,     2,     1,     1,     0,     1,     1,     0,
       1,     0,     1,     3,     4,     1,     1,     1,     1,     3,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     2,     0,     2,     1,     1,     1,     1,     2,     0,
       2,     1,     1,     1,     1,     3,     3,     4,     4,     1,
       3,     3,     3,     5,     3,     3,     5,     5,     2,     2,
       1,     1,     0,     2,     2,     0,     2,     1,     1,     1,
       1,     0,     1,     1,     1,     1,     3,     4,     3,     1,
       1,     3,     5,     3,     5,     4,     6,     4,     5,     1,
       3,     6,     6,     1,     3,     1,     3,     1,     3,     1,
       4,     6,     4,     1,     1,     1,     3,     1,     3,     1,
       1,     1,     3,     1,     1,     3,     3,     5,     3,     3,
       4,     5,     6,     2,     3,     1,     4,     3,     6,     4,
       3,     6
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 343;
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
       2,     2,     2,     2,     2,    95,     2,     2,     2,     2,
      90,    92,     2,     2,    91,    97,    94,     2,    99,   100,
       2,     2,     2,     2,     2,     2,     2,     2,    93,    89,
       2,     2,     2,     2,     2,     2,     2,     2,    98,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,    96,     2,     2,     2,     2,     2,
       2,   101,     2,   102,     2,     2,     2,     2,     2,     2,
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
      85,    86,    87,    88
    };
  }


  private static final int YYLAST_ = 595;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 5;
  private static final int YYNTOKENS_ = 103;


}
/* "src/main/java/lexer_parser/Parser.y":706  */
 /* ------------------------------- Código Java ------------------------------------ */
