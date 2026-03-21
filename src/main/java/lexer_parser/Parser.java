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
    S_CHARACTER_STRING(80),        /* CHARACTER_STRING  */
    S_ARRAY(81),                   /* ARRAY  */
    S_RANGE_OP(82),                /* RANGE_OP  */
    S_NUMERIC_LITERAL(83),         /* NUMERIC_LITERAL  */
    S_84_(84),                     /* ';'  */
    S_85_(85),                     /* '('  */
    S_86_(86),                     /* ','  */
    S_87_(87),                     /* ')'  */
    S_88_(88),                     /* ':'  */
    S_89_(89),                     /* '.'  */
    S_90_(90),                     /* '#'  */
    S_91_T_(91),                   /* 'T'  */
    S_92_(92),                     /* '-'  */
    S_93_D_(93),                   /* 'D'  */
    S_94_(94),                     /* '['  */
    S_95_(95),                     /* ']'  */
    S_YYACCEPT(96),                /* $accept  */
    S_program(97),                 /* program  */
    S_function_block_declaration(98), /* function_block_declaration  */
    S_fb_io_var_declarations_list(99), /* fb_io_var_declarations_list  */
    S_fb_io_var_declarations(100), /* fb_io_var_declarations  */
    S_other_var_declarations_list(101), /* other_var_declarations_list  */
    S_other_var_declarations(102), /* other_var_declarations  */
    S_function_block_body(103),    /* function_block_body  */
    S_fuzzify_block_list(104),     /* fuzzify_block_list  */
    S_fuzzify_block(105),          /* fuzzify_block  */
    S_defuzzify_block_list(106),   /* defuzzify_block_list  */
    S_defuzzify_block(107),        /* defuzzify_block  */
    S_opt_linguistic_term_list(108), /* opt_linguistic_term_list  */
    S_linguistic_term_list(109),   /* linguistic_term_list  */
    S_linguistic_term(110),        /* linguistic_term  */
    S_membership_function(111),    /* membership_function  */
    S_singleton(112),              /* singleton  */
    S_point_list(113),             /* point_list  */
    S_point(114),                  /* point  */
    S_defuzzification_method(115), /* defuzzification_method  */
    S_defuzz_method(116),          /* defuzz_method  */
    S_default_value(117),          /* default_value  */
    S_default_val(118),            /* default_val  */
    S_range_opt(119),              /* range_opt  */
    S_rule_block_list(120),        /* rule_block_list  */
    S_rule_block(121),             /* rule_block  */
    S_operator_definition(122),    /* operator_definition  */
    S_operator_or_opt(123),        /* operator_or_opt  */
    S_operator_and_opt(124),       /* operator_and_opt  */
    S_or_type(125),                /* or_type  */
    S_and_type(126),               /* and_type  */
    S_activation_method_opt(127),  /* activation_method_opt  */
    S_activation_method(128),      /* activation_method  */
    S_act_type(129),               /* act_type  */
    S_accumulation_method(130),    /* accumulation_method  */
    S_accu_type(131),              /* accu_type  */
    S_rule_list(132),              /* rule_list  */
    S_rule(133),                   /* rule  */
    S_opt_weighting(134),          /* opt_weighting  */
    S_condition(135),              /* condition  */
    S_condition_tail(136),         /* condition_tail  */
    S_x(137),                      /* x  */
    S_subcondition(138),           /* subcondition  */
    S_conclusion_list(139),        /* conclusion_list  */
    S_option_block_list(140),      /* option_block_list  */
    S_option_block(141),           /* option_block  */
    S_pragma_list(142),            /* pragma_list  */
    S_pragma(143),                 /* pragma  */
    S_output_declarations(144),    /* output_declarations  */
    S_input_declarations(145),     /* input_declarations  */
    S_input_declaration_list(146), /* input_declaration_list  */
    S_input_declaration(147),      /* input_declaration  */
    S_var_declarations(148),       /* var_declarations  */
    S_var_retain_spec(149),        /* var_retain_spec  */
    S_var_constant_spec(150),      /* var_constant_spec  */
    S_var_init_decl_list(151),     /* var_init_decl_list  */
    S_var_init_decl(152),          /* var_init_decl  */
    S_type_declaration(153),       /* type_declaration  */
    S_opt_edge(154),               /* opt_edge  */
    S_var1_init_decl(155),         /* var1_init_decl  */
    S_spec_init_type(156),         /* spec_init_type  */
    S_simple_spec_init(157),       /* simple_spec_init  */
    S_simple_specification(158),   /* simple_specification  */
    S_elementary_type_name(159),   /* elementary_type_name  */
    S_numeric_type_name(160),      /* numeric_type_name  */
    S_integer_type_name(161),      /* integer_type_name  */
    S_signed_integer_type_name(162), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(163), /* unsigned_integer_type_name  */
    S_real_type_name(164),         /* real_type_name  */
    S_date_type_name(165),         /* date_type_name  */
    S_bit_string_type_name(166),   /* bit_string_type_name  */
    S_constant(167),               /* constant  */
    S_numeric_constant(168),       /* numeric_constant  */
    S_integer_literal(169),        /* integer_literal  */
    S_real_literal(170),           /* real_literal  */
    S_time_literal(171),           /* time_literal  */
    S_duration_literal(172),       /* duration_literal  */
    S_fixed_point(173),            /* fixed_point  */
    S_time_of_day_literal(174),    /* time_of_day_literal  */
    S_daytime(175),                /* daytime  */
    S_date_literal(176),           /* date_literal  */
    S_date(177),                   /* date  */
    S_date_and_time_literal(178),  /* date_and_time_literal  */
    S_boolean_literal(179),        /* boolean_literal  */
    S_bit_string_literal(180),     /* bit_string_literal  */
    S_bit_string_type_name_without_bool(181), /* bit_string_type_name_without_bool  */
    S_subrange_spec_init(182),     /* subrange_spec_init  */
    S_subrange_specification(183), /* subrange_specification  */
    S_subrange(184),               /* subrange  */
    S_enumerated_spec_init(185),   /* enumerated_spec_init  */
    S_enumerated_specification(186), /* enumerated_specification  */
    S_enumerated_list(187),        /* enumerated_list  */
    S_array_spec_init(188),        /* array_spec_init  */
    S_array_specification(189),    /* array_specification  */
    S_subrange_list(190),          /* subrange_list  */
    S_non_generic_type_name(191),  /* non_generic_type_name  */
    S_array_initialization(192),   /* array_initialization  */
    S_array_initial_elements_list(193), /* array_initial_elements_list  */
    S_array_initial_elements(194), /* array_initial_elements  */
    S_array_initial_element(195),  /* array_initial_element  */
    S_structure_initialization(196), /* structure_initialization  */
    S_structure_element_initialization_list(197), /* structure_element_initialization_list  */
    S_structure_element_initialization(198), /* structure_element_initialization  */
    S_structure_element_type(199), /* structure_element_type  */
    S_initialized_variable(200),   /* initialized_variable  */
    S_initialized_constant(201),   /* initialized_constant  */
    S_initialized_enumerate(202),  /* initialized_enumerate  */
    S_initialized_structure(203),  /* initialized_structure  */
    S_fb_name_decl(204),           /* fb_name_decl  */
    S_identifier_list(205),        /* identifier_list  */
    S_standard_function_block_name(206), /* standard_function_block_name  */
    S_string_specification(207),   /* string_specification  */
    S_type_string(208);            /* type_string  */


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
      SymbolKind.S_CHARACTER_STRING,
      SymbolKind.S_ARRAY,
      SymbolKind.S_RANGE_OP,
      SymbolKind.S_NUMERIC_LITERAL,
      SymbolKind.S_84_,
      SymbolKind.S_85_,
      SymbolKind.S_86_,
      SymbolKind.S_87_,
      SymbolKind.S_88_,
      SymbolKind.S_89_,
      SymbolKind.S_90_,
      SymbolKind.S_91_T_,
      SymbolKind.S_92_,
      SymbolKind.S_93_D_,
      SymbolKind.S_94_,
      SymbolKind.S_95_,
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
      SymbolKind.S_type_declaration,
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
      SymbolKind.S_numeric_constant,
      SymbolKind.S_integer_literal,
      SymbolKind.S_real_literal,
      SymbolKind.S_time_literal,
      SymbolKind.S_duration_literal,
      SymbolKind.S_fixed_point,
      SymbolKind.S_time_of_day_literal,
      SymbolKind.S_daytime,
      SymbolKind.S_date_literal,
      SymbolKind.S_date,
      SymbolKind.S_date_and_time_literal,
      SymbolKind.S_boolean_literal,
      SymbolKind.S_bit_string_literal,
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
      SymbolKind.S_type_string
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
  "CHARACTER_STRING", "ARRAY", "RANGE_OP", "NUMERIC_LITERAL", "';'", "'('",
  "','", "')'", "':'", "'.'", "'#'", "'T'", "'-'", "'D'", "'['", "']'",
  "$accept", "program", "function_block_declaration",
  "fb_io_var_declarations_list", "fb_io_var_declarations",
  "other_var_declarations_list", "other_var_declarations",
  "function_block_body", "fuzzify_block_list", "fuzzify_block",
  "defuzzify_block_list", "defuzzify_block", "opt_linguistic_term_list",
  "linguistic_term_list", "linguistic_term", "membership_function",
  "singleton", "point_list", "point", "defuzzification_method",
  "defuzz_method", "default_value", "default_val", "range_opt",
  "rule_block_list", "rule_block", "operator_definition",
  "operator_or_opt", "operator_and_opt", "or_type", "and_type",
  "activation_method_opt", "activation_method", "act_type",
  "accumulation_method", "accu_type", "rule_list", "rule", "opt_weighting",
  "condition", "condition_tail", "x", "subcondition", "conclusion_list",
  "option_block_list", "option_block", "pragma_list", "pragma",
  "output_declarations", "input_declarations", "input_declaration_list",
  "input_declaration", "var_declarations", "var_retain_spec",
  "var_constant_spec", "var_init_decl_list", "var_init_decl",
  "type_declaration", "opt_edge", "var1_init_decl", "spec_init_type",
  "simple_spec_init", "simple_specification", "elementary_type_name",
  "numeric_type_name", "integer_type_name", "signed_integer_type_name",
  "unsigned_integer_type_name", "real_type_name", "date_type_name",
  "bit_string_type_name", "constant", "numeric_constant",
  "integer_literal", "real_literal", "time_literal", "duration_literal",
  "fixed_point", "time_of_day_literal", "daytime", "date_literal", "date",
  "date_and_time_literal", "boolean_literal", "bit_string_literal",
  "bit_string_type_name_without_bool", "subrange_spec_init",
  "subrange_specification", "subrange", "enumerated_spec_init",
  "enumerated_specification", "enumerated_list", "array_spec_init",
  "array_specification", "subrange_list", "non_generic_type_name",
  "array_initialization", "array_initial_elements_list",
  "array_initial_elements", "array_initial_element",
  "structure_initialization", "structure_element_initialization_list",
  "structure_element_initialization", "structure_element_type",
  "initialized_variable", "initialized_constant", "initialized_enumerate",
  "initialized_structure", "fb_name_decl", "identifier_list",
  "standard_function_block_name", "string_specification", "type_string", null
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
    /** Token CHARACTER_STRING, to be returned by the scanner.  */
    static final int CHARACTER_STRING = 335;
    /** Token ARRAY, to be returned by the scanner.  */
    static final int ARRAY = 336;
    /** Token RANGE_OP, to be returned by the scanner.  */
    static final int RANGE_OP = 337;
    /** Token NUMERIC_LITERAL, to be returned by the scanner.  */
    static final int NUMERIC_LITERAL = 338;

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


  private java.io.PrintStream yyDebugStream = System.err;

  /**
   * The <tt>PrintStream</tt> on which the debugging output is printed.
   */
  public final java.io.PrintStream getDebugStream() { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(java.io.PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }


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


  protected final void yycdebugNnl(String s) {
    if (0 < yydebug)
      yyDebugStream.print(s);
  }

  protected final void yycdebug(String s) {
    if (0 < yydebug)
      yyDebugStream.println(s);
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

    yyReducePrint(yyn, yystack);

    switch (yyn)
      {
        
/* "src/main/java/lexer_parser/Parser.java":1019  */

        default: break;
      }

    yySymbolPrint("-> $$ =", SymbolKind.get(yyr1_[yyn]), yyval);

    yystack.pop(yylen);
    yylen = 0;
    /* Shift the result of the reduction.  */
    int yystate = yyLRGotoState(yystack.stateAt(0), yyr1_[yyn]);
    yystack.push(yystate, yyval);
    return YYNEWSTATE;
  }


  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yySymbolPrint(String s, SymbolKind yykind,
                             Object yyvalue) {
      if (0 < yydebug) {
          yycdebug(s
                   + (yykind.getCode() < YYNTOKENS_ ? " token " : " nterm ")
                   + yykind.getName() + " ("
                   + (yyvalue == null ? "(null)" : yyvalue.toString()) + ")");
      }
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



    yycdebug ("Starting parse");
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
        yycdebug ("Entering state " + yystate);
        if (0 < yydebug)
          yystack.print (yyDebugStream);

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

            yycdebug ("Reading a token");
            yychar = yylexer.yylex ();
            yylval = yylexer.getLVal();

          }

        /* Convert token to internal form.  */
        yytoken = yytranslate_ (yychar);
        yySymbolPrint("Next token is", yytoken,
                      yylval);

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
                yySymbolPrint("Shifting", yytoken,
                              yylval);

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
            if (0 < yydebug)
              yystack.print (yyDebugStream);
          }

        if (label == YYABORT)
          /* Leave the switch.  */
          break;



        /* Shift the error token.  */
        yySymbolPrint("Shifting", SymbolKind.get(yystos_[yyn]),
                      yylval);

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

  private static final short yypact_ninf_ = -329;
  private static final short yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      73,    -1,    44,  -329,  -329,  -329,   -21,     3,     3,  -329,
      25,  -329,  -329,  -329,  -329,    52,    52,    78,  -329,   123,
     131,  -329,  -329,    63,  -329,  -329,  -329,  -329,    19,   102,
    -329,  -329,    52,  -329,   135,  -329,   176,    31,   147,   287,
      48,   104,   177,   149,  -329,   182,  -329,  -329,  -329,   151,
    -329,    95,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,
    -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,
    -329,  -329,  -329,  -329,    98,   154,  -329,  -329,  -329,   153,
    -329,  -329,   111,  -329,  -329,  -329,  -329,  -329,  -329,  -329,
     155,  -329,   156,  -329,   157,  -329,  -329,  -329,  -329,   158,
    -329,   -27,  -329,  -329,    55,   161,    59,  -329,   186,   163,
    -329,   193,   209,  -329,  -329,  -329,   122,   117,    36,   316,
     122,   126,   166,   118,   125,   133,   128,  -329,   172,  -329,
    -329,   130,   177,   190,  -329,  -329,   129,   144,   145,   160,
     162,   164,   165,  -329,  -329,  -329,  -329,   180,   167,   170,
     173,   174,   175,  -329,  -329,  -329,  -329,  -329,  -329,  -329,
    -329,  -329,  -329,  -329,  -329,   139,   189,   181,   216,   250,
    -329,  -329,   204,  -329,   203,   246,  -329,  -329,  -329,   201,
     -17,   453,   283,   177,   210,   278,   276,     5,   272,   230,
     -28,   247,   247,   249,   -30,   291,    86,  -329,  -329,  -329,
    -329,   -23,   249,   251,   252,   255,   258,   122,   299,  -329,
     264,  -329,   327,   284,  -329,  -329,     7,  -329,  -329,  -329,
     328,   298,   357,   314,  -329,   286,  -329,  -329,   294,   312,
     386,    -8,   315,   380,  -329,   317,   320,  -329,   368,  -329,
    -329,  -329,  -329,   366,   323,  -329,  -329,   321,  -329,   326,
      91,   180,  -329,  -329,   375,  -329,  -329,  -329,  -329,  -329,
    -329,   424,   372,  -329,   168,   246,  -329,   340,  -329,   335,
     336,  -329,  -329,   334,   150,   382,   430,  -329,  -329,  -329,
    -329,    71,   351,  -329,    88,  -329,   358,  -329,   359,   360,
     247,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,
    -329,     4,   361,  -329,  -329,   453,   453,   453,  -329,  -329,
    -329,  -329,  -329,   363,    41,  -329,  -329,  -329,   365,    99,
      72,  -329,  -329,  -329,  -329,  -329,   356,   353,  -329,  -329,
     410,  -329,   364,   371,   373,  -329,  -329,   369,  -329,  -329,
    -329,  -329,  -329,   377,  -329,   376,  -329,   379,   394,   391,
    -329,  -329,   395,  -329,  -329,   403,   374,  -329,  -329,  -329,
    -329,   434,   420,   -11,  -329,   -10,   116,   -11,   485,   132,
    -329,   482,  -329,    -2,    -5,    -4,  -329,   419,   467,  -329,
     468,  -329,   116,   132,   116,   132,  -329,   486,    -9,  -329,
    -329,  -329,  -329,  -329,   474,   397,   475,   432,  -329,  -329,
    -329,   494,  -329,   478,  -329
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
       0,     0,     0,     2,     4,     1,     8,   103,   103,     5,
      12,     7,     6,   101,   102,     0,     0,   105,     9,     0,
      15,    10,   231,     0,    97,    99,   109,   110,     0,     0,
     106,   104,     0,     3,     0,    13,    42,     0,     0,     0,
       0,     0,     0,     0,    16,    88,    96,    98,   232,   111,
     233,   116,   238,   239,   182,   183,   184,   185,   130,   146,
     147,   145,   148,   149,   135,   136,   137,   138,   139,   140,
     141,   142,   143,   144,     0,     0,   108,   119,   120,   123,
     126,   127,   131,   133,   134,   132,   128,   129,   150,   121,
     186,   122,     0,   115,   199,   190,   125,   191,   114,   229,
     113,   234,    95,   107,     0,     0,     0,    20,    40,     0,
      43,    11,     0,   117,   118,   112,     0,   196,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   100,     0,    14,
      21,     0,    18,    46,    91,    89,   225,     0,     0,     0,
       0,     0,     0,   179,   180,   151,   154,     0,     0,     0,
       0,     0,     0,   226,   153,   155,   156,   152,   161,   162,
     163,   164,   158,   157,   228,     0,   203,     0,     0,     0,
     194,   124,     0,   187,   192,     0,   200,   230,   236,     0,
       0,     0,     0,    19,     0,    56,    48,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,   217,   220,   219,
     221,     0,     0,     0,     0,     0,     0,     0,     0,   195,
     198,   188,     0,   154,   213,   215,     0,   207,   209,   214,
     235,     0,     0,     0,    24,    25,    27,    26,     0,     0,
       0,     0,     0,     0,    57,     0,     0,    90,     0,    92,
     227,   178,   166,     0,     0,   171,   172,     0,   174,     0,
       0,     0,   216,   165,     0,   175,   159,   160,   181,   189,
     204,     0,     0,   193,     0,     0,   206,     0,    22,     0,
       0,    23,    28,     0,     0,     0,     0,    50,    51,    52,
      47,     0,     0,    65,     0,    45,    93,   168,     0,     0,
       0,   223,   224,   222,   218,   167,   201,   205,   131,   202,
     197,     0,     0,   208,   237,     0,     0,     0,    32,    33,
      34,    35,    36,     0,     0,    17,    60,    59,     0,     0,
       0,    53,    54,    55,    49,    94,     0,     0,   177,   210,
       0,   212,     0,     0,     0,    31,    39,     0,    38,    58,
      62,    63,    64,     0,    44,     0,    66,     0,     0,     0,
      30,    29,     0,    37,    61,     0,   169,   173,   176,   211,
      41,     0,     0,     0,   170,     0,    73,     0,     0,    73,
      80,    79,    78,     0,     0,     0,    72,     0,     0,    71,
       0,    82,    73,    73,    73,    73,    81,    85,    68,    83,
      74,    76,    75,    77,     0,     0,     0,     0,    84,    70,
      69,    87,    67,     0,    86
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,
    -329,  -329,  -329,   387,   -96,  -329,  -329,  -329,   295,  -329,
    -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,
    -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,   171,
    -299,  -328,  -329,  -329,  -329,  -329,  -329,  -329,  -329,  -329,
    -329,   496,  -329,   526,  -329,   503,    -7,  -329,  -329,  -329,
    -329,  -329,  -329,   279,  -329,   -39,  -329,  -329,   -38,  -329,
     -37,  -107,  -177,  -329,  -329,  -329,  -329,  -329,  -329,  -184,
     343,   337,  -329,  -329,  -329,  -329,  -329,  -329,   421,  -329,
    -329,  -329,  -329,  -329,   338,  -329,  -116,  -329,   277,   280,
    -106,  -329,   292,  -329,   507,   508,   509,  -329,  -329,  -329,
    -329,  -329,  -329
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,     3,     6,     9,    10,    18,    19,    20,    35,
      36,    44,   182,   106,   107,   223,   224,   225,   226,   230,
     313,   276,   337,   132,    45,   110,   185,   186,   236,   280,
     324,   233,   234,   318,   283,   343,   320,   346,   397,   368,
     376,   369,   370,   388,   111,   135,   187,   239,    11,    12,
      23,    24,    21,    15,    32,    29,    25,    76,   115,    26,
      77,    78,    79,    80,    81,   150,    83,    84,   151,    86,
     152,   214,   154,   155,   156,   157,   158,   357,   159,   245,
     160,   248,   161,   162,   163,    88,    89,    90,   166,    91,
      92,   118,    93,    94,   167,   299,   215,   216,   217,   218,
     219,   196,   197,   293,   198,   199,   200,    98,    27,    28,
      99,   100,   101
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
      82,    85,    87,   227,   228,   153,   164,   176,   246,    30,
     130,   395,   171,   365,   365,   125,   242,   237,   177,   365,
     365,   253,   380,   221,     7,    30,   277,   278,   279,   366,
     371,     8,   137,   103,   141,   382,   384,   372,   381,     4,
      54,    55,    56,    57,     5,   270,   383,   385,   238,    13,
      14,    64,    65,    66,    67,    68,    69,    70,    71,    72,
      73,   143,   144,   149,   243,   129,   146,   126,   222,   254,
     379,    22,   105,   336,   367,   367,     1,   396,    17,    46,
     367,   367,   344,   390,   391,   392,   393,   130,    22,   345,
     137,   329,    22,   265,   330,    22,   102,   103,    54,    55,
      56,    57,   266,   127,   316,    38,   328,    39,   317,    64,
      65,    66,    67,    68,    69,    70,    71,    72,    73,   143,
     144,   321,   169,   170,   146,   322,   323,    33,   332,   333,
     334,   136,    31,   340,   291,   341,    34,   338,   342,   373,
     137,   374,   375,   153,   292,   113,   114,    37,    54,    55,
      56,    57,   138,   139,   140,   141,   142,   374,   375,    64,
      65,    66,    67,    68,    69,    70,    71,    72,    73,   143,
     144,   145,   251,   252,   146,    42,   147,   308,   309,   310,
     311,   312,   148,    43,   149,   175,    40,    48,   104,   108,
     105,   109,   116,   112,   117,   119,   120,   121,   122,   123,
     124,   128,   131,   133,   134,   165,   174,   168,   301,   173,
     147,   179,   175,   178,   180,   181,   184,   137,   400,   188,
     195,   206,   298,    85,    87,    54,    55,    56,    57,   138,
     139,   140,   141,   142,   189,   190,    64,    65,    66,    67,
      68,    69,    70,    71,    72,    73,   143,   144,   145,   136,
     191,   146,   192,   147,   193,   194,   209,   201,   137,   148,
     202,   149,   175,   203,   204,   205,    54,    55,    56,    57,
     138,   139,   140,   141,   142,   207,   208,    64,    65,    66,
      67,    68,    69,    70,    71,    72,    73,   143,   144,   145,
     210,   211,   146,   212,   147,   137,   220,   229,   231,   232,
     148,   235,   149,    54,    55,    56,    57,   138,   139,   140,
     141,   142,   240,   241,    64,    65,    66,    67,    68,    69,
      70,    71,    72,    73,   143,   144,   145,    49,    50,   213,
     244,   147,   247,   250,   256,   257,    51,   148,   258,   149,
     175,   259,    52,    53,    54,    55,    56,    57,    58,    59,
      60,    61,    62,    63,   262,    64,    65,    66,    67,    68,
      69,    70,    71,    72,    73,   137,   261,   263,    74,   264,
     267,   222,    75,    54,    55,    56,    57,   138,   139,   140,
     141,   142,   268,   273,    64,    65,    66,    67,    68,    69,
      70,    71,    72,    73,   143,   144,   145,   269,   271,   146,
     274,   275,   282,   281,   285,   284,   137,   148,   286,   149,
     287,   288,   300,   289,    54,    55,    56,    57,   290,   295,
     304,   305,   306,   307,   314,    64,    65,    66,    67,    68,
      69,    70,    71,    72,    73,   143,   144,   399,   315,   319,
     146,   325,   326,   327,   347,   348,   137,   335,   331,   339,
     349,   350,   363,   353,    54,    55,    56,    57,   351,   355,
     352,   354,   356,   362,   296,    64,    65,    66,    67,    68,
      69,    70,    71,    72,    73,   143,   144,   358,   359,   360,
     146,    54,    55,    56,    57,    58,    59,    60,    61,    62,
      63,   361,    64,    65,    66,    67,    68,    69,    70,    71,
      72,    73,   137,   364,   378,   373,   386,   387,   389,   394,
      54,    55,    56,    57,   398,   401,   402,   403,   404,   183,
     272,    64,    65,    66,    67,    68,    69,    70,    71,    72,
      73,   143,   144,    47,    16,    41,   146,   249,   377,   255,
     297,   172,   303,   294,   302,   260,    95,    96,    97
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      39,    39,    39,   180,   181,   112,   112,   123,   192,    16,
     106,    20,   119,    24,    24,    42,    44,    12,   124,    24,
      24,    44,    24,    40,    45,    32,    34,    35,    36,    40,
      40,    52,    49,    40,    64,    40,    40,   365,    40,    40,
      57,    58,    59,    60,     0,   222,   374,   375,    43,    46,
      47,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    93,    92,     6,    83,    94,    85,    92,
     369,    40,    13,    32,    85,    85,     3,    86,    53,    48,
      85,    85,    10,   382,   383,   384,   385,   183,    40,    17,
      49,    87,    40,    86,    90,    40,    48,   104,    57,    58,
      59,    60,    95,    48,    33,    86,   290,    88,    37,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    33,    86,    87,    83,    37,    38,     4,   305,   306,
     307,    40,    54,    34,   250,    36,     5,   314,    39,    23,
      49,    25,    26,   250,   250,    50,    51,    84,    57,    58,
      59,    60,    61,    62,    63,    64,    65,    25,    26,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    86,    87,    83,    40,    85,    27,    28,    29,
      30,    31,    91,     7,    93,    94,    84,    40,    84,    40,
      13,     9,    94,    42,    40,    42,    85,    42,    42,    42,
      42,    40,    16,    40,    11,    83,    40,    90,    40,    83,
      85,    83,    94,    80,    42,    85,    26,    49,   395,    90,
      40,    82,   261,   261,   261,    57,    58,    59,    60,    61,
      62,    63,    64,    65,    90,    90,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    40,
      90,    83,    90,    85,    90,    90,    40,    90,    49,    91,
      90,    93,    94,    90,    90,    90,    57,    58,    59,    60,
      61,    62,    63,    64,    65,    86,    95,    68,    69,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      40,    87,    83,    90,    85,    49,    95,    14,    88,    21,
      91,    25,    93,    57,    58,    59,    60,    61,    62,    63,
      64,    65,    40,    83,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    40,    41,    83,
      83,    85,    83,    42,    83,    83,    49,    91,    83,    93,
      94,    83,    55,    56,    57,    58,    59,    60,    61,    62,
      63,    64,    65,    66,    90,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    49,    67,    40,    81,    85,
      42,    85,    85,    57,    58,    59,    60,    61,    62,    63,
      64,    65,    84,    89,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    40,    84,    83,
      88,    15,    22,    88,    84,    88,    49,    91,    40,    93,
      44,    88,    40,    92,    57,    58,    59,    60,    92,    44,
      80,    86,    86,    89,    42,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    40,     8,    88,
      83,    83,    83,    83,    88,    92,    49,    84,    87,    84,
      40,    87,    18,    84,    57,    58,    59,    60,    87,    83,
      87,    84,    83,    89,    40,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    83,    87,    84,
      83,    57,    58,    59,    60,    61,    62,    63,    64,    65,
      66,    88,    68,    69,    70,    71,    72,    73,    74,    75,
      76,    77,    49,    83,    19,    23,    87,    40,    40,    23,
      57,    58,    59,    60,    40,    40,    84,    23,    40,   132,
     225,    68,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    37,     8,    32,    83,   194,   367,   202,
     261,   120,   265,   251,   264,   207,    39,    39,    39
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,    97,    98,    40,     0,    99,    45,    52,   100,
     101,   144,   145,    46,    47,   149,   149,    53,   102,   103,
     104,   148,    40,   146,   147,   152,   155,   204,   205,   151,
     152,    54,   150,     4,     5,   105,   106,    84,    86,    88,
      84,   151,    40,     7,   107,   120,    48,   147,    40,    40,
      41,    49,    55,    56,    57,    58,    59,    60,    61,    62,
      63,    64,    65,    66,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    81,    85,   153,   156,   157,   158,
     159,   160,   161,   162,   163,   164,   165,   166,   181,   182,
     183,   185,   186,   188,   189,   200,   201,   202,   203,   206,
     207,   208,    48,   152,    84,    13,   109,   110,    40,     9,
     121,   140,    42,    50,    51,   154,    94,    40,   187,    42,
      85,    42,    42,    42,    42,    42,    94,    48,    40,     6,
     110,    16,   119,    40,    11,   141,    40,    49,    61,    62,
      63,    64,    65,    78,    79,    80,    83,    85,    91,    93,
     161,   164,   166,   167,   168,   169,   170,   171,   172,   174,
     176,   178,   179,   180,   196,    83,   184,   190,    90,    86,
      87,   167,   184,    83,    40,    94,   192,   196,    80,    83,
      42,    85,   108,   109,    26,   122,   123,   142,    90,    90,
      90,    90,    90,    90,    90,    40,   197,   198,   200,   201,
     202,    90,    90,    90,    90,    90,    82,    86,    95,    40,
      40,    87,    90,    83,   167,   192,   193,   194,   195,   196,
      95,    40,    85,   111,   112,   113,   114,   168,   168,    14,
     115,    88,    21,   127,   128,    25,   124,    12,    43,   143,
      40,    83,    44,    92,    83,   175,   175,    83,   177,   176,
      42,    86,    87,    44,    92,   177,    83,    83,    83,    83,
     190,    67,    90,    40,    85,    86,    95,    42,    84,    40,
     168,    84,   114,    89,    88,    15,   117,    34,    35,    36,
     125,    88,    22,   130,    88,    84,    40,    44,    88,    92,
      92,   192,   196,   199,   198,    44,    40,   159,   161,   191,
      40,    40,   195,   194,    80,    86,    86,    89,    27,    28,
      29,    30,    31,   116,    42,     8,    33,    37,   129,    88,
     132,    33,    37,    38,   126,    83,    83,    83,   175,    87,
      90,    87,   168,   168,   168,    84,    32,   118,   168,    84,
      34,    36,    39,   131,    10,    17,   133,    88,    92,    40,
      87,    87,    87,    84,    84,    83,    83,   173,    83,    87,
      84,    88,    89,    18,    83,    24,    40,    85,   135,   137,
     138,    40,   137,    23,    25,    26,   136,   135,    19,   136,
      24,    40,    40,   137,    40,   137,    87,    40,   139,    40,
     136,   136,   136,   136,    23,    20,    86,   134,    40,    40,
     168,    40,    84,    23,    40
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,    96,    97,    98,    99,    99,   100,   100,   101,   101,
     102,   103,   104,   104,   105,   106,   106,   107,   108,   108,
     109,   109,   110,   110,   111,   111,   112,   113,   113,   114,
     114,   115,   116,   116,   116,   116,   116,   117,   118,   118,
     119,   119,   120,   120,   121,   122,   123,   123,   124,   124,
     125,   125,   125,   126,   126,   126,   127,   127,   128,   129,
     129,   130,   131,   131,   131,   132,   132,   133,   134,   134,
     134,   135,   135,   136,   136,   136,   136,   136,   137,   137,
     137,   137,   138,   138,   139,   139,   139,   139,   140,   140,
     141,   142,   142,   143,   143,   144,   145,   146,   146,   147,
     148,   149,   149,   149,   150,   150,   151,   151,   152,   152,
     152,   153,   153,   153,   153,   153,   154,   154,   154,   155,
     156,   156,   156,   157,   157,   157,   158,   159,   159,   159,
     159,   160,   160,   161,   161,   162,   162,   162,   162,   163,
     163,   163,   163,   164,   164,   165,   165,   165,   165,   165,
     166,   167,   167,   167,   168,   168,   168,   168,   168,   169,
     170,   171,   171,   171,   171,   172,   172,   172,   172,   173,
     173,   174,   174,   175,   176,   176,   177,   178,   179,   179,
     179,   180,   181,   181,   181,   181,   182,   182,   183,   184,
     185,   185,   185,   185,   186,   187,   187,   187,   187,   188,
     188,   189,   189,   190,   190,   191,   192,   193,   193,   194,
     194,   194,   194,   195,   195,   195,   196,   197,   197,   198,
     198,   198,   198,   199,   199,   200,   201,   202,   203,   204,
     204,   205,   205,   206,   207,   207,   207,   207,   208,   208
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     1,     6,     0,     2,     1,     1,     0,     2,
       1,     4,     0,     2,     4,     0,     2,     7,     0,     1,
       1,     2,     5,     5,     1,     1,     1,     1,     2,     5,
       5,     4,     1,     1,     1,     1,     1,     4,     1,     1,
       0,     8,     0,     2,     7,     3,     0,     3,     0,     3,
       1,     1,     1,     1,     1,     1,     0,     1,     4,     1,
       1,     4,     1,     1,     1,     0,     2,     9,     0,     2,
       2,     2,     2,     0,     3,     3,     3,     3,     2,     2,
       1,     3,     3,     4,     3,     1,     5,     3,     0,     2,
       3,     0,     2,     2,     3,     5,     5,     1,     3,     1,
       5,     1,     1,     0,     1,     0,     1,     3,     3,     1,
       1,     1,     2,     1,     1,     1,     0,     1,     1,     3,
       1,     1,     1,     1,     3,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     3,
       3,     1,     1,     1,     1,     3,     3,     4,     4,     1,
       3,     3,     3,     5,     3,     3,     5,     5,     3,     1,
       1,     3,     1,     1,     1,     1,     1,     3,     4,     3,
       1,     1,     3,     5,     3,     3,     1,     5,     3,     1,
       3,     6,     6,     1,     3,     1,     3,     1,     3,     1,
       4,     6,     4,     1,     1,     1,     3,     1,     3,     1,
       1,     1,     3,     1,     1,     3,     3,     5,     3,     3,
       5,     1,     3,     1,     1,     4,     3,     6,     1,     1
    };
  }



  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short[] yyrline_ = yyrline_init();
  private static final short[] yyrline_init()
  {
    return new short[]
    {
       0,    42,    42,    46,    53,    55,    59,    60,    63,    65,
      69,    73,    79,    81,    85,    90,    92,    96,   104,   106,
     110,   111,   115,   116,   120,   121,   125,   129,   130,   134,
     135,   139,   143,   143,   143,   143,   143,   147,   151,   152,
     155,   157,   160,   162,   166,   175,   178,   180,   183,   185,
     189,   189,   189,   193,   193,   193,   196,   198,   202,   206,
     206,   210,   214,   214,   214,   217,   219,   223,   226,   228,
     229,   233,   234,   237,   239,   240,   241,   242,   246,   247,
     248,   249,   253,   254,   258,   259,   260,   261,   264,   266,
     270,   275,   277,   281,   282,   288,   292,   296,   297,   301,
     305,   309,   310,   311,   315,   316,   320,   321,   325,   326,
     327,   331,   332,   333,   334,   335,   338,   340,   341,   345,
     349,   350,   351,   355,   356,   357,   361,   367,   368,   369,
     370,   374,   375,   379,   380,   384,   384,   384,   384,   388,
     388,   388,   388,   392,   392,   396,   396,   396,   396,   396,
     400,   406,   407,   408,   412,   413,   414,   415,   416,   420,
     424,   428,   429,   430,   431,   435,   436,   437,   438,   481,
     482,   486,   487,   491,   495,   496,   500,   504,   508,   509,
     510,   514,   518,   519,   520,   521,   527,   528,   532,   536,
     540,   541,   542,   543,   547,   551,   552,   553,   554,   558,
     559,   563,   564,   568,   569,   573,   577,   581,   582,   586,
     587,   588,   589,   593,   594,   595,   599,   603,   604,   608,
     609,   610,   611,   615,   616,   620,   624,   628,   632,   636,
     637,   641,   642,   647,   651,   652,   653,   654,   658,   659
    };
  }


  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yyReducePrint (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
              + " (line " + yylno + "):");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yySymbolPrint("   $" + (yyi + 1) + " =",
                    SymbolKind.get(yystos_[yystack.stateAt(yynrhs - (yyi + 1))]),
                    yystack.valueAt ((yynrhs) - (yyi + 1)));
  }

  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 338;
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
      85,    87,     2,     2,    86,    92,    89,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    88,    84,
       2,     2,     2,     2,     2,     2,     2,     2,    93,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,    91,     2,     2,     2,     2,     2,
       2,    94,     2,    95,     2,     2,     2,     2,     2,     2,
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
      75,    76,    77,    78,    79,    80,    81,    82,    83
    };
  }


  private static final int YYLAST_ = 548;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 5;
  private static final int YYNTOKENS_ = 96;


}
/* "src/main/java/lexer_parser/Parser.y":662  */
 /* ------------------------------- Código Java ------------------------------------ */
