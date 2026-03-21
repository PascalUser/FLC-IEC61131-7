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
    S_PRAGMA(47),                  /* PRAGMA  */
    S_interval(48),                /* interval  */
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
    S_INTERVAL_LITERAL(66),        /* INTERVAL_LITERAL  */
    S_TIME_OF_DAY(67),             /* TIME_OF_DAY  */
    S_DAYTIME_LITERAL(68),         /* DAYTIME_LITERAL  */
    S_DATE(69),                    /* DATE  */
    S_DATE_LITERAL(70),            /* DATE_LITERAL  */
    S_DATE_AND_TIME(71),           /* DATE_AND_TIME  */
    S_DATE_AND_TIME_LITERAL(72),   /* DATE_AND_TIME_LITERAL  */
    S_OF(73),                      /* OF  */
    S_SINT(74),                    /* SINT  */
    S_INT(75),                     /* INT  */
    S_DINT(76),                    /* DINT  */
    S_LINT(77),                    /* LINT  */
    S_USINT(78),                   /* USINT  */
    S_UINT(79),                    /* UINT  */
    S_UDINT(80),                   /* UDINT  */
    S_ULINT(81),                   /* ULINT  */
    S_REAL(82),                    /* REAL  */
    S_LREAL(83),                   /* LREAL  */
    S_TRUE(84),                    /* TRUE  */
    S_FALSE(85),                   /* FALSE  */
    S_STRING_LITERAL(86),          /* STRING_LITERAL  */
    S_ARRAY(87),                   /* ARRAY  */
    S_RANGE_OP(88),                /* RANGE_OP  */
    S_NUMERIC_LITERAL(89),         /* NUMERIC_LITERAL  */
    S_90_(90),                     /* ';'  */
    S_91_(91),                     /* '('  */
    S_92_(92),                     /* ','  */
    S_93_(93),                     /* ')'  */
    S_94_(94),                     /* ':'  */
    S_95_(95),                     /* '.'  */
    S_96_(96),                     /* '#'  */
    S_97_(97),                     /* '-'  */
    S_98_(98),                     /* '['  */
    S_99_(99),                     /* ']'  */
    S_YYACCEPT(100),               /* $accept  */
    S_program(101),                /* program  */
    S_function_block_declaration(102), /* function_block_declaration  */
    S_fb_io_var_declarations_list(103), /* fb_io_var_declarations_list  */
    S_fb_io_var_declarations(104), /* fb_io_var_declarations  */
    S_other_var_declarations_list(105), /* other_var_declarations_list  */
    S_other_var_declarations(106), /* other_var_declarations  */
    S_function_block_body(107),    /* function_block_body  */
    S_fuzzify_block_list(108),     /* fuzzify_block_list  */
    S_fuzzify_block(109),          /* fuzzify_block  */
    S_defuzzify_block_list(110),   /* defuzzify_block_list  */
    S_defuzzify_block(111),        /* defuzzify_block  */
    S_opt_linguistic_term_list(112), /* opt_linguistic_term_list  */
    S_linguistic_term_list(113),   /* linguistic_term_list  */
    S_linguistic_term(114),        /* linguistic_term  */
    S_membership_function(115),    /* membership_function  */
    S_singleton(116),              /* singleton  */
    S_point_list(117),             /* point_list  */
    S_point(118),                  /* point  */
    S_defuzzification_method(119), /* defuzzification_method  */
    S_defuzz_method(120),          /* defuzz_method  */
    S_default_value(121),          /* default_value  */
    S_default_val(122),            /* default_val  */
    S_opt_range(123),              /* opt_range  */
    S_rule_block_list(124),        /* rule_block_list  */
    S_rule_block(125),             /* rule_block  */
    S_operator_definition(126),    /* operator_definition  */
    S_opt_operator_or(127),        /* opt_operator_or  */
    S_operator_and_opt(128),       /* operator_and_opt  */
    S_or_type(129),                /* or_type  */
    S_and_type(130),               /* and_type  */
    S_activation_method_opt(131),  /* activation_method_opt  */
    S_activation_method(132),      /* activation_method  */
    S_act_type(133),               /* act_type  */
    S_accumulation_method(134),    /* accumulation_method  */
    S_accu_type(135),              /* accu_type  */
    S_rule_list(136),              /* rule_list  */
    S_rule(137),                   /* rule  */
    S_opt_weighting(138),          /* opt_weighting  */
    S_condition(139),              /* condition  */
    S_condition_tail(140),         /* condition_tail  */
    S_x(141),                      /* x  */
    S_subcondition(142),           /* subcondition  */
    S_conclusion_list(143),        /* conclusion_list  */
    S_option_block_list(144),      /* option_block_list  */
    S_option_block(145),           /* option_block  */
    S_pragma_list(146),            /* pragma_list  */
    S_pragma(147),                 /* pragma  */
    S_output_declarations(148),    /* output_declarations  */
    S_input_declarations(149),     /* input_declarations  */
    S_input_declaration_list(150), /* input_declaration_list  */
    S_input_declaration(151),      /* input_declaration  */
    S_var_declarations(152),       /* var_declarations  */
    S_var_retain_spec(153),        /* var_retain_spec  */
    S_var_constant_spec(154),      /* var_constant_spec  */
    S_var_init_decl_list(155),     /* var_init_decl_list  */
    S_var_init_decl(156),          /* var_init_decl  */
    S_type_specification(157),     /* type_specification  */
    S_opt_edge(158),               /* opt_edge  */
    S_var1_init_decl(159),         /* var1_init_decl  */
    S_spec_init_type(160),         /* spec_init_type  */
    S_simple_spec_init(161),       /* simple_spec_init  */
    S_simple_specification(162),   /* simple_specification  */
    S_elementary_type_name(163),   /* elementary_type_name  */
    S_numeric_type_name(164),      /* numeric_type_name  */
    S_integer_type_name(165),      /* integer_type_name  */
    S_signed_integer_type_name(166), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(167), /* unsigned_integer_type_name  */
    S_real_type_name(168),         /* real_type_name  */
    S_date_type_name(169),         /* date_type_name  */
    S_bit_string_type_name(170),   /* bit_string_type_name  */
    S_constant(171),               /* constant  */
    S_number(172),                 /* number  */
    S_integer(173),                /* integer  */
    S_real(174),                   /* real  */
    S_time(175),                   /* time  */
    S_duration(176),               /* duration  */
    S_time_of_day(177),            /* time_of_day  */
    S_date(178),                   /* date  */
    S_date_and_time(179),          /* date_and_time  */
    S_boolean(180),                /* boolean  */
    S_bit_string(181),             /* bit_string  */
    S_bit_string_type_name_without_bool(182), /* bit_string_type_name_without_bool  */
    S_subrange_spec_init(183),     /* subrange_spec_init  */
    S_subrange_specification(184), /* subrange_specification  */
    S_subrange(185),               /* subrange  */
    S_enumerated_spec_init(186),   /* enumerated_spec_init  */
    S_enumerated_specification(187), /* enumerated_specification  */
    S_enumerated_list(188),        /* enumerated_list  */
    S_array_spec_init(189),        /* array_spec_init  */
    S_array_specification(190),    /* array_specification  */
    S_subrange_list(191),          /* subrange_list  */
    S_non_generic_type_name(192),  /* non_generic_type_name  */
    S_array_initialization(193),   /* array_initialization  */
    S_array_initial_elements_list(194), /* array_initial_elements_list  */
    S_array_initial_elements(195), /* array_initial_elements  */
    S_array_initial_element(196),  /* array_initial_element  */
    S_structure_initialization(197), /* structure_initialization  */
    S_structure_element_initialization_list(198), /* structure_element_initialization_list  */
    S_structure_element_initialization(199), /* structure_element_initialization  */
    S_structure_element_type(200), /* structure_element_type  */
    S_initialized_variable(201),   /* initialized_variable  */
    S_initialized_constant(202),   /* initialized_constant  */
    S_initialized_enumerate(203),  /* initialized_enumerate  */
    S_initialized_structure(204),  /* initialized_structure  */
    S_fb_name_decl(205),           /* fb_name_decl  */
    S_identifier_list(206),        /* identifier_list  */
    S_standard_function_block_name(207), /* standard_function_block_name  */
    S_string_specification(208),   /* string_specification  */
    S_type_string(209),            /* type_string  */
    S_opt_data_type_declaration(210), /* opt_data_type_declaration  */
    S_data_type_declaration(211),  /* data_type_declaration  */
    S_type_declaration_list(212),  /* type_declaration_list  */
    S_type_declaration(213),       /* type_declaration  */
    S_specification_list(214),     /* specification_list  */
    S_structure_specification(215), /* structure_specification  */
    S_structure_declaration(216),  /* structure_declaration  */
    S_structure_element_declaration_list(217), /* structure_element_declaration_list  */
    S_structure_element_declaration(218), /* structure_element_declaration  */
    S_specification_sublist(219);  /* specification_sublist  */


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
      SymbolKind.S_INTERVAL_LITERAL,
      SymbolKind.S_TIME_OF_DAY,
      SymbolKind.S_DAYTIME_LITERAL,
      SymbolKind.S_DATE,
      SymbolKind.S_DATE_LITERAL,
      SymbolKind.S_DATE_AND_TIME,
      SymbolKind.S_DATE_AND_TIME_LITERAL,
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
      SymbolKind.S_STRING_LITERAL,
      SymbolKind.S_ARRAY,
      SymbolKind.S_RANGE_OP,
      SymbolKind.S_NUMERIC_LITERAL,
      SymbolKind.S_90_,
      SymbolKind.S_91_,
      SymbolKind.S_92_,
      SymbolKind.S_93_,
      SymbolKind.S_94_,
      SymbolKind.S_95_,
      SymbolKind.S_96_,
      SymbolKind.S_97_,
      SymbolKind.S_98_,
      SymbolKind.S_99_,
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
      SymbolKind.S_rule_block_list,
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
      SymbolKind.S_option_block_list,
      SymbolKind.S_option_block,
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
      SymbolKind.S_type_specification,
      SymbolKind.S_opt_edge,
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
      SymbolKind.S_number,
      SymbolKind.S_integer,
      SymbolKind.S_real,
      SymbolKind.S_time,
      SymbolKind.S_duration,
      SymbolKind.S_time_of_day,
      SymbolKind.S_date,
      SymbolKind.S_date_and_time,
      SymbolKind.S_boolean,
      SymbolKind.S_bit_string,
      SymbolKind.S_bit_string_type_name_without_bool,
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
      SymbolKind.S_string_specification,
      SymbolKind.S_type_string,
      SymbolKind.S_opt_data_type_declaration,
      SymbolKind.S_data_type_declaration,
      SymbolKind.S_type_declaration_list,
      SymbolKind.S_type_declaration,
      SymbolKind.S_specification_list,
      SymbolKind.S_structure_specification,
      SymbolKind.S_structure_declaration,
      SymbolKind.S_structure_element_declaration_list,
      SymbolKind.S_structure_element_declaration,
      SymbolKind.S_specification_sublist
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
  "ASSIGN_OP", "PRAGMA", "interval", "VAR_INPUT", "RETAIN", "NON_RETAIN",
  "END_VAR", "BOOL", "R_EDGE", "F_EDGE", "VAR_OUTPUT", "VAR", "CONSTANT",
  "STRING", "WSTRING", "BYTE", "WORD", "DWORD", "LWORD", "TIME",
  "INTERVAL_LITERAL", "TIME_OF_DAY", "DAYTIME_LITERAL", "DATE",
  "DATE_LITERAL", "DATE_AND_TIME", "DATE_AND_TIME_LITERAL", "OF", "SINT",
  "INT", "DINT", "LINT", "USINT", "UINT", "UDINT", "ULINT", "REAL",
  "LREAL", "TRUE", "FALSE", "STRING_LITERAL", "ARRAY", "RANGE_OP",
  "NUMERIC_LITERAL", "';'", "'('", "','", "')'", "':'", "'.'", "'#'",
  "'-'", "'['", "']'", "$accept", "program", "function_block_declaration",
  "fb_io_var_declarations_list", "fb_io_var_declarations",
  "other_var_declarations_list", "other_var_declarations",
  "function_block_body", "fuzzify_block_list", "fuzzify_block",
  "defuzzify_block_list", "defuzzify_block", "opt_linguistic_term_list",
  "linguistic_term_list", "linguistic_term", "membership_function",
  "singleton", "point_list", "point", "defuzzification_method",
  "defuzz_method", "default_value", "default_val", "opt_range",
  "rule_block_list", "rule_block", "operator_definition",
  "opt_operator_or", "operator_and_opt", "or_type", "and_type",
  "activation_method_opt", "activation_method", "act_type",
  "accumulation_method", "accu_type", "rule_list", "rule", "opt_weighting",
  "condition", "condition_tail", "x", "subcondition", "conclusion_list",
  "option_block_list", "option_block", "pragma_list", "pragma",
  "output_declarations", "input_declarations", "input_declaration_list",
  "input_declaration", "var_declarations", "var_retain_spec",
  "var_constant_spec", "var_init_decl_list", "var_init_decl",
  "type_specification", "opt_edge", "var1_init_decl", "spec_init_type",
  "simple_spec_init", "simple_specification", "elementary_type_name",
  "numeric_type_name", "integer_type_name", "signed_integer_type_name",
  "unsigned_integer_type_name", "real_type_name", "date_type_name",
  "bit_string_type_name", "constant", "number", "integer", "real", "time",
  "duration", "time_of_day", "date", "date_and_time", "boolean",
  "bit_string", "bit_string_type_name_without_bool", "subrange_spec_init",
  "subrange_specification", "subrange", "enumerated_spec_init",
  "enumerated_specification", "enumerated_list", "array_spec_init",
  "array_specification", "subrange_list", "non_generic_type_name",
  "array_initialization", "array_initial_elements_list",
  "array_initial_elements", "array_initial_element",
  "structure_initialization", "structure_element_initialization_list",
  "structure_element_initialization", "structure_element_type",
  "initialized_variable", "initialized_constant", "initialized_enumerate",
  "initialized_structure", "fb_name_decl", "identifier_list",
  "standard_function_block_name", "string_specification", "type_string",
  "opt_data_type_declaration", "data_type_declaration",
  "type_declaration_list", "type_declaration", "specification_list",
  "structure_specification", "structure_declaration",
  "structure_element_declaration_list", "structure_element_declaration",
  "specification_sublist", null
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
    /** Token PRAGMA, to be returned by the scanner.  */
    static final int PRAGMA = 302;
    /** Token interval, to be returned by the scanner.  */
    static final int interval = 303;
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
    /** Token INTERVAL_LITERAL, to be returned by the scanner.  */
    static final int INTERVAL_LITERAL = 321;
    /** Token TIME_OF_DAY, to be returned by the scanner.  */
    static final int TIME_OF_DAY = 322;
    /** Token DAYTIME_LITERAL, to be returned by the scanner.  */
    static final int DAYTIME_LITERAL = 323;
    /** Token DATE, to be returned by the scanner.  */
    static final int DATE = 324;
    /** Token DATE_LITERAL, to be returned by the scanner.  */
    static final int DATE_LITERAL = 325;
    /** Token DATE_AND_TIME, to be returned by the scanner.  */
    static final int DATE_AND_TIME = 326;
    /** Token DATE_AND_TIME_LITERAL, to be returned by the scanner.  */
    static final int DATE_AND_TIME_LITERAL = 327;
    /** Token OF, to be returned by the scanner.  */
    static final int OF = 328;
    /** Token SINT, to be returned by the scanner.  */
    static final int SINT = 329;
    /** Token INT, to be returned by the scanner.  */
    static final int INT = 330;
    /** Token DINT, to be returned by the scanner.  */
    static final int DINT = 331;
    /** Token LINT, to be returned by the scanner.  */
    static final int LINT = 332;
    /** Token USINT, to be returned by the scanner.  */
    static final int USINT = 333;
    /** Token UINT, to be returned by the scanner.  */
    static final int UINT = 334;
    /** Token UDINT, to be returned by the scanner.  */
    static final int UDINT = 335;
    /** Token ULINT, to be returned by the scanner.  */
    static final int ULINT = 336;
    /** Token REAL, to be returned by the scanner.  */
    static final int REAL = 337;
    /** Token LREAL, to be returned by the scanner.  */
    static final int LREAL = 338;
    /** Token TRUE, to be returned by the scanner.  */
    static final int TRUE = 339;
    /** Token FALSE, to be returned by the scanner.  */
    static final int FALSE = 340;
    /** Token STRING_LITERAL, to be returned by the scanner.  */
    static final int STRING_LITERAL = 341;
    /** Token ARRAY, to be returned by the scanner.  */
    static final int ARRAY = 342;
    /** Token RANGE_OP, to be returned by the scanner.  */
    static final int RANGE_OP = 343;
    /** Token NUMERIC_LITERAL, to be returned by the scanner.  */
    static final int NUMERIC_LITERAL = 344;

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
        
/* "src/main/java/lexer_parser/Parser.java":1018  */

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

  private static final short yypact_ninf_ = -348;
  private static final short yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      35,    38,   104,   103,  -348,    26,    13,    32,  -348,    77,
    -348,    80,  -348,    33,  -348,  -348,    81,    83,  -348,  -348,
    -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,
    -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,    28,    86,
    -348,  -348,   100,  -348,  -348,    36,  -348,  -348,  -348,  -348,
    -348,  -348,  -348,   102,  -348,   106,  -348,   107,  -348,  -348,
    -348,  -348,  -348,   -27,  -348,  -348,  -348,  -348,    21,    56,
      14,    74,   284,    79,    70,     0,   585,    79,    84,   121,
      71,    88,    87,    51,    51,  -348,   113,  -348,  -348,   367,
    -348,    82,  -348,    85,    90,    91,    92,    93,    94,  -348,
    -348,  -348,  -348,   131,    95,    97,    98,  -348,  -348,  -348,
    -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,    96,
     110,    78,   134,   135,  -348,  -348,   111,  -348,    99,   440,
    -348,  -348,   115,  -348,  -348,   136,   136,   124,  -348,   175,
     176,  -348,  -348,  -348,  -348,  -348,  -348,  -348,   152,   114,
     -45,   137,   146,   149,   172,    16,  -348,  -348,  -348,  -348,
     147,   151,   153,   154,    79,   165,  -348,   145,  -348,   201,
     129,  -348,  -348,   -18,  -348,  -348,  -348,   202,  -348,   157,
    -348,  -348,  -348,  -348,   -43,   159,  -348,  -348,   136,  -348,
     206,  -348,   240,  -348,  -348,  -348,   193,  -348,  -348,  -348,
     148,   131,  -348,  -348,  -348,  -348,  -348,  -348,   554,   213,
    -348,   191,   440,  -348,   177,   -15,   217,   327,    11,   174,
     262,   237,  -348,   270,  -348,  -348,  -348,  -348,  -348,  -348,
    -348,  -348,  -348,  -348,     7,   192,  -348,  -348,  -348,  -348,
    -348,    83,  -348,    60,  -348,  -348,  -348,  -348,   238,  -348,
    -348,  -348,    23,   243,    66,  -348,   266,   244,  -348,   275,
    -348,   247,  -348,  -348,  -348,  -348,   203,  -348,   246,  -348,
    -348,   205,   262,   263,   250,  -348,   207,  -348,   407,   619,
     280,   262,   208,   274,   272,   264,    12,  -348,  -348,   219,
     483,   220,  -348,   216,  -348,  -348,   218,   221,   292,    67,
     235,   286,  -348,   236,   224,    27,  -348,  -348,  -348,   241,
     248,  -348,  -348,   239,     1,   289,   320,  -348,  -348,  -348,
    -348,    54,   245,  -348,    31,  -348,   251,  -348,   619,   619,
     619,  -348,  -348,  -348,  -348,  -348,   252,   242,  -348,  -348,
    -348,   253,    10,    76,  -348,  -348,  -348,  -348,  -348,   257,
     259,   261,  -348,  -348,   254,  -348,  -348,  -348,  -348,  -348,
     267,  -348,   249,  -348,  -348,  -348,   287,  -348,  -348,   282,
    -348,   314,    -5,    -4,    69,    -5,   333,    89,  -348,   347,
    -348,     3,    -3,    -2,  -348,   285,   335,  -348,   337,  -348,
      69,    89,    69,    89,  -348,   355,    -8,  -348,  -348,  -348,
    -348,  -348,   339,   525,   340,   295,  -348,  -348,  -348,   366,
    -348,   351,  -348
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
     230,     0,     0,     0,   231,     0,     0,     0,     1,     0,
       2,     0,   232,     0,   233,     4,     0,     0,   228,   229,
     172,   173,   174,   175,   130,   146,   145,   147,   135,   136,
     137,   138,   139,   140,   141,   142,   143,   144,     0,     0,
     236,   120,   123,   126,   127,   131,   133,   134,   132,   128,
     129,   148,   121,   176,   122,     0,   237,   189,   180,   125,
     181,   240,   239,   224,   235,   238,   241,   234,     8,     0,
       0,     0,     0,     0,   186,     0,     0,     0,     0,     0,
       0,     0,     0,   101,   101,     5,    12,     7,     6,     0,
     242,     0,   243,   215,     0,     0,     0,     0,     0,   169,
     170,   149,   152,     0,     0,     0,     0,   216,   151,   153,
     154,   150,   159,   160,   161,   162,   156,   155,   218,     0,
     193,     0,     0,     0,   184,   124,     0,   177,   182,     0,
     190,   226,     0,   102,   103,     0,     0,   104,     9,     0,
      15,    10,   246,   247,   248,   249,   245,   244,     0,     0,
       0,     0,     0,     0,     0,     0,   207,   210,   209,   211,
       0,     0,     0,     0,     0,     0,   185,   188,   178,     0,
     152,   203,   205,     0,   197,   199,   204,   225,   221,     0,
      97,    99,   109,   110,     0,     0,   106,   105,     0,     3,
       0,    13,    42,   217,   168,   163,     0,   165,   166,   167,
       0,     0,   206,   157,   158,   171,   179,   194,     0,     0,
     183,     0,     0,   196,     0,     0,     0,     0,     0,     0,
       0,     0,    16,    88,   164,   213,   214,   212,   208,   191,
     195,   131,   192,   187,     0,     0,   198,   227,    96,    98,
     222,   111,   223,   116,   108,   119,   115,   114,   219,   113,
      95,   107,     0,     0,     0,    20,    40,     0,    43,    11,
     200,     0,   202,   117,   118,   112,     0,   100,     0,    14,
      21,     0,    18,    46,     0,    89,     0,   220,     0,     0,
       0,    19,     0,    56,    48,     0,     0,    91,   201,     0,
       0,     0,    24,    25,    27,    26,     0,     0,     0,     0,
       0,     0,    57,     0,     0,     0,    90,    92,    22,     0,
       0,    23,    28,     0,     0,     0,     0,    50,    51,    52,
      47,     0,     0,    65,     0,    45,     0,    93,     0,     0,
       0,    32,    33,    34,    35,    36,     0,     0,    17,    60,
      59,     0,     0,     0,    53,    54,    55,    49,    94,     0,
       0,     0,    31,    39,     0,    38,    58,    62,    63,    64,
       0,    44,     0,    66,    30,    29,     0,    37,    61,     0,
      41,     0,     0,     0,    73,     0,     0,    73,    80,    79,
      78,     0,     0,     0,    72,     0,     0,    71,     0,    82,
      73,    73,    73,    73,    81,    85,    68,    83,    74,    76,
      75,    77,     0,     0,     0,     0,    84,    70,    69,    87,
      67,     0,    86
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,
    -348,  -348,  -348,   125,  -227,  -348,  -348,  -348,   119,  -348,
    -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,
    -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,  -348,    24,
    -347,  -317,  -348,  -348,  -348,  -348,  -348,   127,  -348,  -348,
    -348,   185,  -348,   331,  -348,   228,  -124,  -348,  -348,  -348,
     -85,  -348,  -348,   209,  -348,   -11,  -348,  -348,   -10,  -348,
      -9,   -69,  -268,  -348,  -348,  -348,  -348,  -348,  -348,  -348,
    -348,  -348,  -348,  -348,  -348,   342,  -348,  -348,  -348,   -84,
    -348,   256,  -348,   -62,  -348,   210,   212,   -66,  -348,   223,
    -348,   -90,   -89,   -88,   -81,  -348,  -348,  -348,   -80,  -348,
    -348,  -348,  -348,   415,  -348,  -348,  -348,  -348,   363,  -348
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,    10,    68,    85,    86,   138,   139,   140,   191,
     192,   222,   280,   254,   255,   291,   292,   293,   294,   298,
     336,   316,   354,   272,   223,   258,   283,   284,   304,   320,
     347,   301,   302,   341,   323,   360,   343,   363,   405,   376,
     384,   377,   378,   396,   259,   275,   286,   287,    87,    88,
     179,   180,   141,   135,   188,   185,   181,   244,   265,   182,
      40,    41,    42,    43,    44,   104,    46,    47,   105,    49,
     106,   171,   108,   109,   110,   111,   112,   113,   114,   115,
     116,   117,    51,    52,    53,   120,    54,    55,    75,    56,
      57,   121,   232,   172,   173,   174,   175,   176,   155,   156,
     227,    58,    59,    60,    61,   183,   184,   248,    62,    63,
       3,     4,     6,     7,    64,    65,    66,    70,    71,   146
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
      45,    48,    50,   107,   142,   143,   118,   125,   144,   145,
     295,   296,   186,   157,   158,   159,   403,    12,   130,    81,
      90,   195,   310,   373,   373,   373,   373,   270,   306,   178,
     387,   388,   331,   332,   333,   334,   335,   238,     1,   374,
     379,   390,   392,   398,   399,   400,   401,   389,   357,   216,
     358,   217,   196,   359,   270,   178,   380,     5,    69,   285,
     349,   350,   351,   250,   186,   391,   393,   178,   344,   355,
      83,    82,   345,   346,   212,   267,   269,    84,    45,    48,
      50,   213,     5,   253,   404,    16,   375,   375,   375,   375,
     361,   339,   123,   124,   251,   340,   381,   362,   382,   383,
     260,   133,   134,   261,     8,   317,   318,   319,   201,   202,
       9,   157,   158,   159,   263,   264,   326,   327,   382,   383,
      11,    15,    14,    67,    17,    69,    73,    77,   251,    72,
      74,   107,   245,   246,   226,   408,   247,   249,   225,    18,
      19,    20,    21,    22,    23,    24,    76,    25,    78,    26,
      89,    27,    79,    80,    28,    29,    30,    31,    32,    33,
      34,    35,    36,    37,    92,   128,   122,    38,   119,   129,
     137,    39,   147,   127,   131,   154,   132,   165,   166,   167,
     178,   148,   187,   189,   163,   190,   149,   150,   151,   152,
     153,   160,    93,   161,   162,   169,   193,   231,    48,    50,
     277,    94,   164,   194,   168,   197,    45,    48,    50,    20,
      21,    22,    23,    95,   177,    96,   198,    97,   200,    98,
     211,   199,    28,    29,    30,    31,    32,    33,    34,    35,
      36,    37,    99,   100,   101,   234,   203,   102,   208,   103,
     204,   209,   205,   206,    94,   210,   129,   215,   214,   218,
     220,   221,    20,    21,    22,    23,    95,   233,    96,   224,
      97,   240,    98,   237,   252,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,    99,   100,   101,   353,   253,
     102,   256,   103,   257,   266,   262,   271,   268,   273,   129,
     274,   276,   278,   282,   103,    94,   279,   285,   297,   300,
     288,   303,   299,    20,    21,    22,    23,   290,   305,   308,
     311,   315,   322,   313,   325,   314,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,    99,   100,    93,   321,
     324,   102,   338,   328,   330,   337,   372,    94,   369,   342,
     329,   348,   352,   356,   367,    20,    21,    22,    23,    95,
     364,    96,   365,    97,   366,    98,   386,   368,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,    99,   100,
     101,   241,   242,   102,   381,   103,   371,   370,   394,   395,
     243,   397,   402,   406,   409,   410,    18,    19,    20,    21,
      22,    23,    24,   411,    25,   412,    26,   281,    27,   385,
     239,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    17,   312,   307,    38,   136,   219,   230,    39,   126,
     207,    13,   236,   235,   228,     0,    18,    19,    20,    21,
      22,    23,    24,    91,    25,     0,    26,     0,    27,     0,
       0,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,   289,     0,     0,    38,     0,     0,     0,    39,     0,
      94,     0,     0,     0,     0,     0,     0,     0,    20,    21,
      22,    23,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    99,   100,    94,     0,     0,   102,     0,   290,     0,
       0,    20,    21,    22,    23,    95,     0,    96,     0,    97,
       0,    98,     0,     0,    28,    29,    30,    31,    32,    33,
      34,    35,    36,    37,    99,   100,   101,   309,     0,   170,
       0,   103,     0,     0,     0,     0,    94,     0,   129,     0,
       0,     0,     0,     0,    20,    21,    22,    23,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    99,   100,   407,
       0,     0,   102,     0,     0,     0,     0,     0,    94,     0,
       0,     0,     0,     0,     0,     0,    20,    21,    22,    23,
       0,     0,     0,     0,     0,     0,     0,     0,   229,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    99,
     100,     0,     0,     0,   102,    20,    21,    22,    23,    24,
       0,    25,     0,    26,     0,    27,     0,     0,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,    94,     0,
       0,     0,     0,     0,     0,     0,    20,    21,    22,    23,
      95,     0,    96,     0,    97,     0,    98,     0,     0,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    99,
     100,   101,    94,     0,   102,     0,     0,     0,     0,     0,
      20,    21,    22,    23,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    99,   100,     0,     0,     0,   102
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      11,    11,    11,    72,    89,    89,    72,    76,    89,    89,
     278,   279,   136,   103,   103,   103,    24,     4,    80,    46,
       6,    66,   290,    28,    28,    28,    28,   254,    16,    44,
     377,    28,    31,    32,    33,    34,    35,    52,     3,    44,
      44,    44,    44,   390,   391,   392,   393,    44,    38,    92,
      40,    94,    97,    43,   281,    44,   373,    44,    44,    47,
     328,   329,   330,    52,   188,   382,   383,    44,    37,   337,
      49,    98,    41,    42,    92,    52,    10,    56,    89,    89,
      89,    99,    44,    17,    92,     5,    91,    91,    91,    91,
      14,    37,    92,    93,   218,    41,    27,    21,    29,    30,
      93,    50,    51,    96,     0,    38,    39,    40,    92,    93,
       7,   201,   201,   201,    54,    55,    89,    90,    29,    30,
      94,    44,    90,    90,    44,    44,    98,    91,   252,    46,
      44,   200,   217,   217,   200,   403,   217,   217,   200,    59,
      60,    61,    62,    63,    64,    65,    46,    67,    46,    69,
      94,    71,    46,    46,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    90,    44,    96,    87,    89,    98,
      57,    91,    90,    89,    86,    44,    89,    99,    44,    44,
      44,    96,    58,     8,    88,     9,    96,    96,    96,    96,
      96,    96,    44,    96,    96,    96,    44,   208,   208,   208,
     266,    53,    92,    89,    93,    68,   217,   217,   217,    61,
      62,    63,    64,    65,    99,    67,    70,    69,    46,    71,
      91,    72,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    84,    85,    86,    44,    89,    89,    73,    91,
      89,    96,    89,    89,    53,    44,    98,    90,    46,    90,
      44,    11,    61,    62,    63,    64,    65,    44,    67,    66,
      69,    44,    71,    86,    90,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    86,    36,    17,
      89,    44,    91,    13,    46,    93,    20,    44,    44,    98,
      15,    44,    46,    30,    91,    53,    91,    47,    18,    25,
      93,    29,    94,    61,    62,    63,    64,    91,    44,    90,
      90,    19,    26,    95,    90,    94,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    84,    85,    44,    94,
      94,    89,    12,    92,    95,    46,    22,    53,    89,    94,
      92,    90,    90,    90,    90,    61,    62,    63,    64,    65,
      93,    67,    93,    69,    93,    71,    23,    90,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    84,    85,
      86,    44,    45,    89,    27,    91,    94,    90,    93,    44,
      53,    44,    27,    44,    44,    90,    59,    60,    61,    62,
      63,    64,    65,    27,    67,    44,    69,   272,    71,   375,
     215,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    44,   293,   286,    87,    84,   188,   208,    91,    77,
     164,     6,   212,   211,   201,    -1,    59,    60,    61,    62,
      63,    64,    65,    70,    67,    -1,    69,    -1,    71,    -1,
      -1,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    44,    -1,    -1,    87,    -1,    -1,    -1,    91,    -1,
      53,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    61,    62,
      63,    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    84,    85,    53,    -1,    -1,    89,    -1,    91,    -1,
      -1,    61,    62,    63,    64,    65,    -1,    67,    -1,    69,
      -1,    71,    -1,    -1,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    83,    84,    85,    86,    44,    -1,    89,
      -1,    91,    -1,    -1,    -1,    -1,    53,    -1,    98,    -1,
      -1,    -1,    -1,    -1,    61,    62,    63,    64,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    44,
      -1,    -1,    89,    -1,    -1,    -1,    -1,    -1,    53,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    61,    62,    63,    64,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    44,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    -1,    -1,    -1,    89,    61,    62,    63,    64,    65,
      -1,    67,    -1,    69,    -1,    71,    -1,    -1,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    53,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    61,    62,    63,    64,
      65,    -1,    67,    -1,    69,    -1,    71,    -1,    -1,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    86,    53,    -1,    89,    -1,    -1,    -1,    -1,    -1,
      61,    62,    63,    64,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    84,    85,    -1,    -1,    -1,    89
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,   101,   210,   211,    44,   212,   213,     0,     7,
     102,    94,     4,   213,    90,    44,     5,    44,    59,    60,
      61,    62,    63,    64,    65,    67,    69,    71,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    87,    91,
     160,   161,   162,   163,   164,   165,   166,   167,   168,   169,
     170,   182,   183,   184,   186,   187,   189,   190,   201,   202,
     203,   204,   208,   209,   214,   215,   216,    90,   103,    44,
     217,   218,    46,    98,    44,   188,    46,    91,    46,    46,
      46,    46,    98,    49,    56,   104,   105,   148,   149,    94,
       6,   218,    90,    44,    53,    65,    67,    69,    71,    84,
      85,    86,    89,    91,   165,   168,   170,   171,   172,   173,
     174,   175,   176,   177,   178,   179,   180,   181,   197,    89,
     185,   191,    96,    92,    93,   171,   185,    89,    44,    98,
     193,    86,    89,    50,    51,   153,   153,    57,   106,   107,
     108,   152,   160,   189,   204,   208,   219,    90,    96,    96,
      96,    96,    96,    96,    44,   198,   199,   201,   202,   203,
      96,    96,    96,    88,    92,    99,    44,    44,    93,    96,
      89,   171,   193,   194,   195,   196,   197,    99,    44,   150,
     151,   156,   159,   205,   206,   155,   156,    58,   154,     8,
       9,   109,   110,    44,    89,    66,    97,    68,    70,    72,
      46,    92,    93,    89,    89,    89,    89,   191,    73,    96,
      44,    91,    92,    99,    46,    90,    92,    94,    90,   155,
      44,    11,   111,   124,    66,   193,   197,   200,   199,    44,
     163,   165,   192,    44,    44,   196,   195,    86,    52,   151,
      44,    44,    45,    53,   157,   160,   189,   204,   207,   208,
      52,   156,    90,    17,   113,   114,    44,    13,   125,   144,
      93,    96,    93,    54,    55,   158,    46,    52,    44,    10,
     114,    20,   123,    44,    15,   145,    44,   197,    46,    91,
     112,   113,    30,   126,   127,    47,   146,   147,    93,    44,
      91,   115,   116,   117,   118,   172,   172,    18,   119,    94,
      25,   131,   132,    29,   128,    44,    16,   147,    90,    44,
     172,    90,   118,    95,    94,    19,   121,    38,    39,    40,
     129,    94,    26,   134,    94,    90,    89,    90,    92,    92,
      95,    31,    32,    33,    34,    35,   120,    46,    12,    37,
      41,   133,    94,   136,    37,    41,    42,   130,    90,   172,
     172,   172,    90,    36,   122,   172,    90,    38,    40,    43,
     135,    14,    21,   137,    93,    93,    93,    90,    90,    89,
      90,    94,    22,    28,    44,    91,   139,   141,   142,    44,
     141,    27,    29,    30,   140,   139,    23,   140,    28,    44,
      44,   141,    44,   141,    93,    44,   143,    44,   140,   140,
     140,   140,    27,    24,    92,   138,    44,    44,   172,    44,
      90,    27,    44
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,   100,   101,   102,   103,   103,   104,   104,   105,   105,
     106,   107,   108,   108,   109,   110,   110,   111,   112,   112,
     113,   113,   114,   114,   115,   115,   116,   117,   117,   118,
     118,   119,   120,   120,   120,   120,   120,   121,   122,   122,
     123,   123,   124,   124,   125,   126,   127,   127,   128,   128,
     129,   129,   129,   130,   130,   130,   131,   131,   132,   133,
     133,   134,   135,   135,   135,   136,   136,   137,   138,   138,
     138,   139,   139,   140,   140,   140,   140,   140,   141,   141,
     141,   141,   142,   142,   143,   143,   143,   143,   144,   144,
     145,   146,   146,   147,   147,   148,   149,   150,   150,   151,
     152,   153,   153,   153,   154,   154,   155,   155,   156,   156,
     156,   157,   157,   157,   157,   157,   158,   158,   158,   159,
     160,   160,   160,   161,   161,   161,   162,   163,   163,   163,
     163,   164,   164,   165,   165,   166,   166,   166,   166,   167,
     167,   167,   167,   168,   168,   169,   169,   169,   170,   171,
     171,   171,   172,   172,   172,   172,   172,   173,   174,   175,
     175,   175,   175,   176,   176,   177,   178,   179,   180,   180,
     180,   181,   182,   182,   182,   182,   183,   183,   184,   185,
     186,   186,   186,   186,   187,   188,   188,   188,   188,   189,
     189,   190,   190,   191,   191,   192,   193,   194,   194,   195,
     195,   195,   195,   196,   196,   196,   197,   198,   198,   199,
     199,   199,   199,   200,   200,   201,   202,   203,   204,   205,
     205,   206,   206,   207,   208,   208,   208,   208,   209,   209,
     210,   210,   211,   212,   212,   213,   214,   214,   214,   214,
     215,   215,   216,   217,   217,   218,   219,   219,   219,   219
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     2,     6,     0,     2,     1,     1,     0,     2,
       1,     4,     0,     2,     4,     0,     2,     7,     0,     1,
       1,     2,     5,     5,     1,     1,     1,     1,     2,     5,
       5,     4,     1,     1,     1,     1,     1,     4,     1,     1,
       0,     8,     0,     2,     7,     3,     0,     3,     0,     3,
       1,     1,     1,     1,     1,     1,     0,     1,     4,     1,
       1,     4,     1,     1,     1,     0,     2,     9,     0,     2,
       2,     2,     2,     0,     3,     3,     3,     3,     2,     2,
       1,     3,     3,     4,     3,     1,     5,     3,     0,     2,
       3,     1,     2,     3,     4,     5,     5,     1,     3,     1,
       5,     0,     1,     1,     0,     1,     1,     3,     3,     1,
       1,     1,     2,     1,     1,     1,     0,     1,     1,     3,
       1,     1,     1,     1,     3,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     3,     3,     1,
       1,     1,     1,     3,     4,     3,     3,     3,     3,     1,
       1,     3,     1,     1,     1,     1,     1,     3,     4,     3,
       1,     1,     3,     5,     3,     3,     1,     5,     3,     1,
       3,     6,     6,     1,     3,     1,     3,     1,     3,     1,
       4,     6,     4,     1,     1,     1,     3,     1,     3,     1,
       1,     1,     3,     1,     1,     3,     3,     5,     3,     3,
       5,     1,     3,     1,     1,     4,     3,     6,     1,     1,
       0,     1,     3,     2,     3,     3,     1,     1,     1,     1,
       1,     1,     3,     2,     3,     3,     1,     1,     1,     1
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 344;
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
       2,     2,     2,     2,     2,    96,     2,     2,     2,     2,
      91,    93,     2,     2,    92,    97,    95,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    94,    90,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    98,     2,    99,     2,     2,     2,     2,     2,     2,
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
      85,    86,    87,    88,    89
    };
  }


  private static final int YYLAST_ = 708;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 8;
  private static final int YYNTOKENS_ = 100;


}
/* "src/main/java/lexer_parser/Parser.y":661  */
 /* --------------------------------- Código Java ------------------------------------- */
