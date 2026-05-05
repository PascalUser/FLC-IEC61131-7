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
/* "src/main/java/parser/Parser.y":6  */

    import java.util.List;
    import java.util.ArrayList;
    import utils.enums.*;
    import utils.SymbolTable;
    import utils.builders.LexemeInfoBuilder;
    import parser.publisher.*;

/* "src/main/java/parser/Parser.java":54  */

/**
 * A Bison parser, automatically generated from <tt>src/main/java/parser/Parser.y</tt>.
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
    S_TRUE(80),                    /* TRUE  */
    S_FALSE(81),                   /* FALSE  */
    S_NUMERIC_LITERAL(82),         /* NUMERIC_LITERAL  */
    S_STRING_LITERAL(83),          /* STRING_LITERAL  */
    S_TIME_LITERAL(84),            /* TIME_LITERAL  */
    S_ARRAY(85),                   /* ARRAY  */
    S_86_(86),                     /* ';'  */
    S_87_(87),                     /* '('  */
    S_88_(88),                     /* ','  */
    S_89_(89),                     /* ')'  */
    S_90_(90),                     /* ':'  */
    S_91_(91),                     /* '#'  */
    S_92_(92),                     /* '['  */
    S_93_(93),                     /* ']'  */
    S_YYACCEPT(94),                /* $accept  */
    S_program(95),                 /* program  */
    S_function_block_declaration(96), /* function_block_declaration  */
    S_fb_io_var_declarations_list(97), /* fb_io_var_declarations_list  */
    S_fb_io_var_declarations(98),  /* fb_io_var_declarations  */
    S_other_var_declarations_list(99), /* other_var_declarations_list  */
    S_other_var_declarations(100), /* other_var_declarations  */
    S_function_block_body(101),    /* function_block_body  */
    S_fuzzify_block_list(102),     /* fuzzify_block_list  */
    S_fuzzify_block(103),          /* fuzzify_block  */
    S_defuzzify_block_list(104),   /* defuzzify_block_list  */
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
    S_rule_block_list(118),        /* rule_block_list  */
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
    S_option_block_list(138),      /* option_block_list  */
    S_option_block(139),           /* option_block  */
    S_pragma_list(140),            /* pragma_list  */
    S_pragma(141),                 /* pragma  */
    S_output_declarations(142),    /* output_declarations  */
    S_input_declarations(143),     /* input_declarations  */
    S_var_declarations(144),       /* var_declarations  */
    S_var_retain_spec(145),        /* var_retain_spec  */
    S_var_constant_spec(146),      /* var_constant_spec  */
    S_var_init_decl_list(147),     /* var_init_decl_list  */
    S_var_init_decl(148),          /* var_init_decl  */
    S_unified_specification(149),  /* unified_specification  */
    S_custom_spec_init(150),       /* custom_spec_init  */
    S_opt_edge(151),               /* opt_edge  */
    S_simple_spec_init(152),       /* simple_spec_init  */
    S_elementary_type_name(153),   /* elementary_type_name  */
    S_numeric_type_name(154),      /* numeric_type_name  */
    S_integer_type_name(155),      /* integer_type_name  */
    S_signed_integer_type_name(156), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(157), /* unsigned_integer_type_name  */
    S_real_type_name(158),         /* real_type_name  */
    S_date_type_name(159),         /* date_type_name  */
    S_constant(160),               /* constant  */
    S_number(161),                 /* number  */
    S_number_prefix(162),          /* number_prefix  */
    S_time(163),                   /* time  */
    S_bit_string_type_name(164),   /* bit_string_type_name  */
    S_subrange_spec_init(165),     /* subrange_spec_init  */
    S_subrange_specification(166), /* subrange_specification  */
    S_subrange(167),               /* subrange  */
    S_enumerated_spec_init(168),   /* enumerated_spec_init  */
    S_enumerated_specification(169), /* enumerated_specification  */
    S_enumerated_list(170),        /* enumerated_list  */
    S_array_spec_init(171),        /* array_spec_init  */
    S_array_specification(172),    /* array_specification  */
    S_subrange_list(173),          /* subrange_list  */
    S_non_generic_type_name(174),  /* non_generic_type_name  */
    S_array_initialization(175),   /* array_initialization  */
    S_array_initial_elements_list(176), /* array_initial_elements_list  */
    S_array_initial_elements(177), /* array_initial_elements  */
    S_array_initial_element(178),  /* array_initial_element  */
    S_structure_initialization(179), /* structure_initialization  */
    S_structure_element_initialization_list(180), /* structure_element_initialization_list  */
    S_structure_element_initialization(181), /* structure_element_initialization  */
    S_structure_element_type(182), /* structure_element_type  */
    S_initialized_constant(183),   /* initialized_constant  */
    S_initialized_identifier(184), /* initialized_identifier  */
    S_opt_scope_and_value(185),    /* opt_scope_and_value  */
    S_initialized_structure(186),  /* initialized_structure  */
    S_fb_name_decl(187),           /* fb_name_decl  */
    S_identifier_list(188),        /* identifier_list  */
    S_standard_function_block_name(189), /* standard_function_block_name  */
    S_string_spec_init(190),       /* string_spec_init  */
    S_type_string(191),            /* type_string  */
    S_opt_data_type_declaration(192), /* opt_data_type_declaration  */
    S_data_type_declaration(193),  /* data_type_declaration  */
    S_type_declaration_list(194),  /* type_declaration_list  */
    S_type_declaration(195),       /* type_declaration  */
    S_specification_list(196),     /* specification_list  */
    S_structure_declaration(197),  /* structure_declaration  */
    S_structure_element_declaration_list(198), /* structure_element_declaration_list  */
    S_structure_element_declaration(199), /* structure_element_declaration  */
    S_specification_sublist(200);  /* specification_sublist  */


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
      SymbolKind.S_TRUE,
      SymbolKind.S_FALSE,
      SymbolKind.S_NUMERIC_LITERAL,
      SymbolKind.S_STRING_LITERAL,
      SymbolKind.S_TIME_LITERAL,
      SymbolKind.S_ARRAY,
      SymbolKind.S_86_,
      SymbolKind.S_87_,
      SymbolKind.S_88_,
      SymbolKind.S_89_,
      SymbolKind.S_90_,
      SymbolKind.S_91_,
      SymbolKind.S_92_,
      SymbolKind.S_93_,
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
      SymbolKind.S_var_declarations,
      SymbolKind.S_var_retain_spec,
      SymbolKind.S_var_constant_spec,
      SymbolKind.S_var_init_decl_list,
      SymbolKind.S_var_init_decl,
      SymbolKind.S_unified_specification,
      SymbolKind.S_custom_spec_init,
      SymbolKind.S_opt_edge,
      SymbolKind.S_simple_spec_init,
      SymbolKind.S_elementary_type_name,
      SymbolKind.S_numeric_type_name,
      SymbolKind.S_integer_type_name,
      SymbolKind.S_signed_integer_type_name,
      SymbolKind.S_unsigned_integer_type_name,
      SymbolKind.S_real_type_name,
      SymbolKind.S_date_type_name,
      SymbolKind.S_constant,
      SymbolKind.S_number,
      SymbolKind.S_number_prefix,
      SymbolKind.S_time,
      SymbolKind.S_bit_string_type_name,
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
      SymbolKind.S_initialized_constant,
      SymbolKind.S_initialized_identifier,
      SymbolKind.S_opt_scope_and_value,
      SymbolKind.S_initialized_structure,
      SymbolKind.S_fb_name_decl,
      SymbolKind.S_identifier_list,
      SymbolKind.S_standard_function_block_name,
      SymbolKind.S_string_spec_init,
      SymbolKind.S_type_string,
      SymbolKind.S_opt_data_type_declaration,
      SymbolKind.S_data_type_declaration,
      SymbolKind.S_type_declaration_list,
      SymbolKind.S_type_declaration,
      SymbolKind.S_specification_list,
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
  "ASSIGN_OP", "RANGE_OP", "PRAGMA", "VAR_INPUT", "RETAIN", "NON_RETAIN",
  "END_VAR", "BOOL", "R_EDGE", "F_EDGE", "VAR_OUTPUT", "VAR", "CONSTANT",
  "STRING", "WSTRING", "BYTE", "WORD", "DWORD", "LWORD", "TIME",
  "TIME_OF_DAY", "DATE", "DATE_AND_TIME", "OF", "SINT", "INT", "DINT",
  "LINT", "USINT", "UINT", "UDINT", "ULINT", "REAL", "LREAL", "TRUE",
  "FALSE", "NUMERIC_LITERAL", "STRING_LITERAL", "TIME_LITERAL", "ARRAY",
  "';'", "'('", "','", "')'", "':'", "'#'", "'['", "']'", "$accept",
  "program", "function_block_declaration", "fb_io_var_declarations_list",
  "fb_io_var_declarations", "other_var_declarations_list",
  "other_var_declarations", "function_block_body", "fuzzify_block_list",
  "fuzzify_block", "defuzzify_block_list", "defuzzify_block",
  "opt_linguistic_term_list", "linguistic_term_list", "linguistic_term",
  "membership_function", "singleton", "point_list", "point",
  "defuzzification_method", "defuzz_method", "default_value",
  "default_val", "opt_range", "rule_block_list", "rule_block",
  "operator_definition", "opt_operator_or", "operator_and_opt", "or_type",
  "and_type", "activation_method_opt", "activation_method", "act_type",
  "accumulation_method", "accu_type", "rule_list", "rule", "opt_weighting",
  "condition", "condition_tail", "x", "subcondition", "conclusion_list",
  "option_block_list", "option_block", "pragma_list", "pragma",
  "output_declarations", "input_declarations", "var_declarations",
  "var_retain_spec", "var_constant_spec", "var_init_decl_list",
  "var_init_decl", "unified_specification", "custom_spec_init", "opt_edge",
  "simple_spec_init", "elementary_type_name", "numeric_type_name",
  "integer_type_name", "signed_integer_type_name",
  "unsigned_integer_type_name", "real_type_name", "date_type_name",
  "constant", "number", "number_prefix", "time", "bit_string_type_name",
  "subrange_spec_init", "subrange_specification", "subrange",
  "enumerated_spec_init", "enumerated_specification", "enumerated_list",
  "array_spec_init", "array_specification", "subrange_list",
  "non_generic_type_name", "array_initialization",
  "array_initial_elements_list", "array_initial_elements",
  "array_initial_element", "structure_initialization",
  "structure_element_initialization_list",
  "structure_element_initialization", "structure_element_type",
  "initialized_constant", "initialized_identifier", "opt_scope_and_value",
  "initialized_structure", "fb_name_decl", "identifier_list",
  "standard_function_block_name", "string_spec_init", "type_string",
  "opt_data_type_declaration", "data_type_declaration",
  "type_declaration_list", "type_declaration", "specification_list",
  "structure_declaration", "structure_element_declaration_list",
  "structure_element_declaration", "specification_sublist", null
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
    /** Token TRUE, to be returned by the scanner.  */
    static final int TRUE = 335;
    /** Token FALSE, to be returned by the scanner.  */
    static final int FALSE = 336;
    /** Token NUMERIC_LITERAL, to be returned by the scanner.  */
    static final int NUMERIC_LITERAL = 337;
    /** Token STRING_LITERAL, to be returned by the scanner.  */
    static final int STRING_LITERAL = 338;
    /** Token TIME_LITERAL, to be returned by the scanner.  */
    static final int TIME_LITERAL = 339;
    /** Token ARRAY, to be returned by the scanner.  */
    static final int ARRAY = 340;

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
          case 95: /* output_declarations: VAR_OUTPUT var_retain_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 95)
    /* "src/main/java/parser/Parser.y":347  */
    { 
        Compound c = new Compound(((List<Publisher>)(yystack.valueAt (2)))); 
        c.source(Source.OUT).publish(); 
        yyval = c; 
    };
  break;


  case 96: /* input_declarations: VAR_INPUT var_retain_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 96)
    /* "src/main/java/parser/Parser.y":356  */
    { 
        Compound c = new Compound(((List<Publisher>)(yystack.valueAt (2)))); 
        c.source(Source.IN).publish();
        yyval = c; 
    };
  break;


  case 97: /* var_declarations: VAR var_constant_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 97)
    /* "src/main/java/parser/Parser.y":365  */
    { 
        Compound c = new Compound(((List<Publisher>)(yystack.valueAt (2)))); 
        c.source(Source.NONE).publish(); 
        yyval = c; 
    };
  break;


  case 103: /* var_init_decl_list: var_init_decl  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":385  */
    {
        List<Publisher> list = new ArrayList<>();
        list.add(((Publisher)(yystack.valueAt (0))));
        yyval = list;
    };
  break;


  case 104: /* var_init_decl_list: var_init_decl_list ';' var_init_decl  */
  if (yyn == 104)
    /* "src/main/java/parser/Parser.y":391  */
    { 
        ((List<Publisher>)(yystack.valueAt (2))).add(((Publisher)(yystack.valueAt (0))));
        yyval = ((List<Publisher>)(yystack.valueAt (2)));
    };
  break;


  case 105: /* var_init_decl: identifier_list ':' unified_specification  */
  if (yyn == 105)
    /* "src/main/java/parser/Parser.y":400  */
    {
        yyval = new Declaration(this.symbolTable, ((List<String>)(yystack.valueAt (2))), ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.VARIABLE));
    };
  break;


  case 106: /* var_init_decl: fb_name_decl  */
  if (yyn == 106)
    /* "src/main/java/parser/Parser.y":404  */
    {
        yyval = null;
    };
  break;


  case 107: /* unified_specification: custom_spec_init  */
  if (yyn == 107)
    /* "src/main/java/parser/Parser.y":412  */
    {
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).type(Type.SIMPLE);
    };
  break;


  case 108: /* unified_specification: BOOL opt_edge  */
  if (yyn == 108)
    /* "src/main/java/parser/Parser.y":415  */
                            { yyval = null; };
  break;


  case 109: /* unified_specification: simple_spec_init  */
  if (yyn == 109)
    /* "src/main/java/parser/Parser.y":416  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 110: /* unified_specification: subrange_spec_init  */
  if (yyn == 110)
    /* "src/main/java/parser/Parser.y":417  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 111: /* unified_specification: enumerated_spec_init  */
  if (yyn == 111)
    /* "src/main/java/parser/Parser.y":418  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 112: /* unified_specification: array_spec_init  */
  if (yyn == 112)
    /* "src/main/java/parser/Parser.y":419  */
                            { yyval = null; };
  break;


  case 113: /* unified_specification: string_spec_init  */
  if (yyn == 113)
    /* "src/main/java/parser/Parser.y":420  */
                            { yyval = null; };
  break;


  case 114: /* custom_spec_init: IDENTIFIER  */
  if (yyn == 114)
    /* "src/main/java/parser/Parser.y":426  */
    {
        yyval = new LexemeInfoBuilder()
                    .subtype(Subtype.CUSTOM)
                    .customType(((String)(yystack.valueAt (0))))
                    .initialValue(this.symbolTable.get(((String)(yystack.valueAt (0)))).initialValue);
    };
  break;


  case 115: /* custom_spec_init: initialized_constant  */
  if (yyn == 115)
    /* "src/main/java/parser/Parser.y":432  */
                                { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 116: /* custom_spec_init: initialized_structure  */
  if (yyn == 116)
    /* "src/main/java/parser/Parser.y":433  */
                                { yyval = null; };
  break;


  case 117: /* custom_spec_init: initialized_identifier  */
  if (yyn == 117)
    /* "src/main/java/parser/Parser.y":434  */
                                { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 121: /* simple_spec_init: elementary_type_name  */
  if (yyn == 121)
    /* "src/main/java/parser/Parser.y":445  */
    {
        yyval = new LexemeInfoBuilder().type(Type.SIMPLE).subtype(((Subtype)(yystack.valueAt (0))));
    };
  break;


  case 122: /* simple_spec_init: elementary_type_name ASSIGN_OP constant  */
  if (yyn == 122)
    /* "src/main/java/parser/Parser.y":449  */
    {
        yyval = new LexemeInfoBuilder().type(Type.SIMPLE).subtype(((Subtype)(yystack.valueAt (2)))).initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 123: /* elementary_type_name: numeric_type_name  */
  if (yyn == 123)
    /* "src/main/java/parser/Parser.y":457  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 124: /* elementary_type_name: date_type_name  */
  if (yyn == 124)
    /* "src/main/java/parser/Parser.y":458  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 125: /* elementary_type_name: bit_string_type_name  */
  if (yyn == 125)
    /* "src/main/java/parser/Parser.y":459  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 126: /* numeric_type_name: integer_type_name  */
  if (yyn == 126)
    /* "src/main/java/parser/Parser.y":463  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 127: /* numeric_type_name: real_type_name  */
  if (yyn == 127)
    /* "src/main/java/parser/Parser.y":464  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 128: /* integer_type_name: signed_integer_type_name  */
  if (yyn == 128)
    /* "src/main/java/parser/Parser.y":468  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 129: /* integer_type_name: unsigned_integer_type_name  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":469  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 130: /* signed_integer_type_name: SINT  */
  if (yyn == 130)
    /* "src/main/java/parser/Parser.y":473  */
           { yyval = Subtype.SINT; };
  break;


  case 131: /* signed_integer_type_name: INT  */
  if (yyn == 131)
    /* "src/main/java/parser/Parser.y":474  */
           { yyval = Subtype.INT;  };
  break;


  case 132: /* signed_integer_type_name: DINT  */
  if (yyn == 132)
    /* "src/main/java/parser/Parser.y":475  */
           { yyval = Subtype.DINT; };
  break;


  case 133: /* signed_integer_type_name: LINT  */
  if (yyn == 133)
    /* "src/main/java/parser/Parser.y":476  */
           { yyval = Subtype.LINT; };
  break;


  case 134: /* unsigned_integer_type_name: USINT  */
  if (yyn == 134)
    /* "src/main/java/parser/Parser.y":480  */
            { yyval = Subtype.USINT; };
  break;


  case 135: /* unsigned_integer_type_name: UINT  */
  if (yyn == 135)
    /* "src/main/java/parser/Parser.y":481  */
            { yyval = Subtype.UINT;  };
  break;


  case 136: /* unsigned_integer_type_name: UDINT  */
  if (yyn == 136)
    /* "src/main/java/parser/Parser.y":482  */
            { yyval = Subtype.UDINT; };
  break;


  case 137: /* unsigned_integer_type_name: ULINT  */
  if (yyn == 137)
    /* "src/main/java/parser/Parser.y":483  */
            { yyval = Subtype.ULINT; };
  break;


  case 138: /* real_type_name: REAL  */
  if (yyn == 138)
    /* "src/main/java/parser/Parser.y":487  */
            { yyval = Subtype.REAL;  };
  break;


  case 139: /* real_type_name: LREAL  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":488  */
            { yyval = Subtype.LREAL; };
  break;


  case 140: /* date_type_name: TIME  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":492  */
                    { yyval = Subtype.TIME;          };
  break;


  case 141: /* date_type_name: DATE  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":493  */
                    { yyval = Subtype.DATE;          };
  break;


  case 142: /* date_type_name: TIME_OF_DAY  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":494  */
                    { yyval = Subtype.TIME_OF_DAY;   };
  break;


  case 143: /* date_type_name: DATE_AND_TIME  */
  if (yyn == 143)
    /* "src/main/java/parser/Parser.y":495  */
                    { yyval = Subtype.DATE_AND_TIME; };
  break;


  case 148: /* number: TRUE  */
  if (yyn == 148)
    /* "src/main/java/parser/Parser.y":509  */
    {
        this.symbolTable.put(
            "TRUE" ,
            new LexemeInfoBuilder()
                .use(Use.LITERAL)
                .type(Type.SIMPLE)
                .subtype(Subtype.BOOL)
                .build()
        );
        yyval = "TRUE";
    };
  break;


  case 149: /* number: FALSE  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":521  */
    {
        this.symbolTable.put(
            "FALSE" ,
            new LexemeInfoBuilder()
                .use(Use.LITERAL)
                .type(Type.SIMPLE)
                .subtype(Subtype.BOOL)
                .build()
        );
        yyval = "FALSE";
    };
  break;


  case 150: /* number: number_prefix NUMERIC_LITERAL  */
  if (yyn == 150)
    /* "src/main/java/parser/Parser.y":533  */
    {
        // todo: hacer conversion de esta constante en codigo
        yyval = "";
    };
  break;


  case 154: /* time: date_type_name '#' TIME_LITERAL  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":546  */
                                    {
        // Accion semantica que verifica que prefix es del mismo tipo que time_literal
    };
  break;


  case 155: /* bit_string_type_name: BYTE  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":552  */
            { yyval = Subtype.BYTE;  };
  break;


  case 156: /* bit_string_type_name: WORD  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":553  */
            { yyval = Subtype.WORD;  };
  break;


  case 157: /* bit_string_type_name: DWORD  */
  if (yyn == 157)
    /* "src/main/java/parser/Parser.y":554  */
            { yyval = Subtype.DWORD; };
  break;


  case 158: /* bit_string_type_name: LWORD  */
  if (yyn == 158)
    /* "src/main/java/parser/Parser.y":555  */
            { yyval = Subtype.LWORD; };
  break;


  case 159: /* subrange_spec_init: subrange_specification  */
  if (yyn == 159)
    /* "src/main/java/parser/Parser.y":561  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 160: /* subrange_spec_init: subrange_specification ASSIGN_OP NUMERIC_LITERAL  */
  if (yyn == 160)
    /* "src/main/java/parser/Parser.y":562  */
                                                       { yyval = ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(((String)(yystack.valueAt (0)))); };
  break;


  case 161: /* subrange_specification: integer_type_name '(' subrange ')'  */
  if (yyn == 161)
    /* "src/main/java/parser/Parser.y":567  */
    { 
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (1))).subtype(((Subtype)(yystack.valueAt (3))));
    };
  break;


  case 162: /* subrange: NUMERIC_LITERAL RANGE_OP NUMERIC_LITERAL  */
  if (yyn == 162)
    /* "src/main/java/parser/Parser.y":574  */
    {
        // todo: hacer chequeo semantico y pasaje a valor (.value) en analisis lexico

        Integer ilim = (Integer) this.symbolTable.get(((String)(yystack.valueAt (2)))).initialValue;
        Integer slim = (Integer) this.symbolTable.get(((String)(yystack.valueAt (0)))).initialValue;

        yyval = new LexemeInfoBuilder()
            .type(Type.SUBRANGE)
            .inferiorLimit(ilim)
            .superiorLimit(slim);
    };
  break;


  case 163: /* enumerated_spec_init: enumerated_specification  */
  if (yyn == 163)
    /* "src/main/java/parser/Parser.y":589  */
    {
        yyval = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters(((List<String>)(yystack.valueAt (0))))
            .initialValue(((List<String>)(yystack.valueAt (0))).get(0));
    };
  break;


  case 164: /* enumerated_spec_init: enumerated_specification ASSIGN_OP opt_scope_and_value  */
  if (yyn == 164)
    /* "src/main/java/parser/Parser.y":596  */
    {
        yyval = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters(((List<String>)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 165: /* enumerated_specification: '(' enumerated_list ')'  */
  if (yyn == 165)
    /* "src/main/java/parser/Parser.y":605  */
                           { yyval = ((List<String>)(yystack.valueAt (1))); };
  break;


  case 166: /* enumerated_list: IDENTIFIER  */
  if (yyn == 166)
    /* "src/main/java/parser/Parser.y":610  */
    {
        List<String> l = new ArrayList<String>();
        l.add(((String)(yystack.valueAt (0))));
        yyval = l;
    };
  break;


  case 167: /* enumerated_list: enumerated_list ',' IDENTIFIER  */
  if (yyn == 167)
    /* "src/main/java/parser/Parser.y":616  */
    {
        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 193: /* initialized_constant: IDENTIFIER ASSIGN_OP constant  */
  if (yyn == 193)
    /* "src/main/java/parser/Parser.y":685  */
    {
        yyval = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 194: /* initialized_identifier: IDENTIFIER ASSIGN_OP opt_scope_and_value  */
  if (yyn == 194)
    /* "src/main/java/parser/Parser.y":695  */
    {
        yyval = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 195: /* opt_scope_and_value: IDENTIFIER  */
  if (yyn == 195)
    /* "src/main/java/parser/Parser.y":705  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 196: /* opt_scope_and_value: IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 196)
    /* "src/main/java/parser/Parser.y":710  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 200: /* identifier_list: IDENTIFIER  */
  if (yyn == 200)
    /* "src/main/java/parser/Parser.y":727  */
    {
        List<String> l = new ArrayList<String>();
        l.add(((String)(yystack.valueAt (0))));
        yyval = l;
    };
  break;


  case 201: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 201)
    /* "src/main/java/parser/Parser.y":732  */
                                     { ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0)))); yyval = ((List<String>)(yystack.valueAt (2))); };
  break;


  case 211: /* data_type_declaration: TYPE type_declaration_list END_TYPE  */
  if (yyn == 211)
    /* "src/main/java/parser/Parser.y":761  */
    {
        Publisher pub = new Compound(((List<Publisher>)(yystack.valueAt (1))));
        pub.publish();
        yyval = pub;
    };
  break;


  case 212: /* type_declaration_list: type_declaration ';'  */
  if (yyn == 212)
    /* "src/main/java/parser/Parser.y":770  */
    {
        List<Publisher> list = new ArrayList<>();
        list.add(((Publisher)(yystack.valueAt (1))));
        yyval = list;
    };
  break;


  case 213: /* type_declaration_list: type_declaration_list type_declaration ';'  */
  if (yyn == 213)
    /* "src/main/java/parser/Parser.y":776  */
    {
        ((List<Publisher>)(yystack.valueAt (2))).add(((Publisher)(yystack.valueAt (1))));
        yyval = ((List<Publisher>)(yystack.valueAt (2)));
    };
  break;


  case 214: /* type_declaration: IDENTIFIER ':' specification_list  */
  if (yyn == 214)
    /* "src/main/java/parser/Parser.y":784  */
    {
        List<String> element = new ArrayList<>();
        element.add(((String)(yystack.valueAt (2))));
        yyval = new Declaration(this.symbolTable, element, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 215: /* specification_list: custom_spec_init  */
  if (yyn == 215)
    /* "src/main/java/parser/Parser.y":792  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.TYPE); };
  break;


  case 216: /* specification_list: simple_spec_init  */
  if (yyn == 216)
    /* "src/main/java/parser/Parser.y":793  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.TYPE); };
  break;


  case 217: /* specification_list: enumerated_spec_init  */
  if (yyn == 217)
    /* "src/main/java/parser/Parser.y":794  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.TYPE); };
  break;


  case 218: /* specification_list: subrange_spec_init  */
  if (yyn == 218)
    /* "src/main/java/parser/Parser.y":795  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.TYPE); };
  break;


  case 219: /* specification_list: array_spec_init  */
  if (yyn == 219)
    /* "src/main/java/parser/Parser.y":796  */
                            { yyval = null; };
  break;


  case 220: /* specification_list: structure_declaration  */
  if (yyn == 220)
    /* "src/main/java/parser/Parser.y":797  */
                            { yyval = null; };
  break;


  case 221: /* specification_list: string_spec_init  */
  if (yyn == 221)
    /* "src/main/java/parser/Parser.y":798  */
                            { yyval = null; };
  break;



/* "src/main/java/parser/Parser.java":1621  */

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

  private static final short yypact_ninf_ = -312;
  private static final short yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      49,    25,    80,    75,  -312,    12,    14,     3,  -312,    52,
    -312,    59,  -312,   100,  -312,  -312,    99,   101,  -312,  -312,
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,    53,   158,
    -312,  -312,   169,  -312,   129,  -312,  -312,  -312,  -312,  -312,
    -312,   171,  -312,   195,  -312,   196,  -312,  -312,  -312,  -312,
     -25,  -312,  -312,  -312,    17,   155,    16,   160,   265,   161,
    -312,   -53,   458,   161,   165,   204,   163,   167,   174,    66,
      66,  -312,   200,  -312,  -312,   305,  -312,   173,  -312,   185,
    -312,  -312,  -312,  -312,   214,   186,   187,   188,  -312,  -312,
     193,  -312,   189,  -312,  -312,   234,   197,   191,   239,  -312,
    -312,   198,  -312,  -312,   426,  -312,  -312,   205,  -312,  -312,
     242,   242,   252,  -312,   303,   304,  -312,  -312,  -312,  -312,
    -312,  -312,  -312,  -312,  -312,   268,   269,    84,  -312,  -312,
    -312,  -312,  -312,   233,  -312,  -312,   236,   161,   250,  -312,
    -312,   235,  -312,  -312,     0,  -312,  -312,  -312,   274,  -312,
     237,  -312,  -312,   -37,   238,  -312,   242,  -312,   277,  -312,
     314,  -312,   117,   214,  -312,  -312,  -312,  -312,   407,   157,
     426,  -312,   251,     4,   306,   229,     5,   267,   334,   310,
    -312,   342,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,
      15,   270,  -312,  -312,  -312,  -312,  -312,  -312,   153,  -312,
    -312,  -312,  -312,  -312,  -312,   311,  -312,  -312,    18,   312,
      61,  -312,   338,   316,  -312,   346,  -312,   318,  -312,  -312,
    -312,  -312,   276,  -312,   328,  -312,  -312,   299,   334,   357,
     340,  -312,   300,  -312,   341,    78,   373,   334,   307,   368,
     365,   351,    13,  -312,  -312,   313,   363,   315,  -312,   309,
    -312,  -312,   353,   308,   387,   172,   319,   382,  -312,   320,
     344,     8,  -312,  -312,  -312,   343,   362,  -312,  -312,    78,
     131,   386,   440,  -312,  -312,  -312,  -312,    64,   364,  -312,
      50,  -312,   367,  -312,    78,    78,   406,  -312,  -312,  -312,
    -312,  -312,   390,   190,  -312,  -312,  -312,   424,    57,    58,
    -312,  -312,  -312,  -312,  -312,   422,   423,   428,  -312,  -312,
     429,  -312,  -312,  -312,  -312,  -312,   430,  -312,   435,  -312,
    -312,  -312,  -312,  -312,  -312,   437,   520,    -4,    -3,   176,
      -4,   521,   184,  -312,   516,  -312,     6,    -2,    -1,  -312,
     456,   502,  -312,   503,  -312,   176,   184,   176,   184,  -312,
     522,    -7,  -312,  -312,  -312,  -312,  -312,   504,   385,   506,
     465,  -312,  -312,  -312,   525,  -312,   509,  -312
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
     209,     0,     0,     0,   210,     0,     0,     0,     1,     0,
       2,     0,   211,     0,   212,     4,     0,   114,   207,   208,
     155,   156,   157,   158,   140,   142,   141,   143,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,     0,     0,
     215,   216,   121,   123,   126,   128,   129,   127,   124,   125,
     218,   159,   217,   163,   219,   168,   115,   117,   116,   221,
     203,   214,   220,   213,     8,     0,     0,     0,     0,     0,
     166,     0,     0,     0,     0,     0,     0,     0,     0,    98,
      98,     5,    12,     7,     6,     0,   222,     0,   223,   195,
     148,   149,   147,   144,     0,     0,     0,     0,   193,   146,
       0,   145,     0,   197,   194,     0,   172,     0,     0,   165,
     122,     0,   160,   164,     0,   169,   205,     0,    99,   100,
       0,     0,   101,     9,     0,    15,    10,   226,   227,   229,
     228,   230,   231,   225,   224,     0,     0,     0,   186,   188,
     189,   151,   152,     0,   150,   153,     0,     0,     0,   167,
     161,   147,   182,   184,     0,   176,   178,   183,   204,   200,
       0,   103,   106,     0,     0,   102,     0,     3,     0,    13,
      42,   196,     0,     0,   185,   154,   162,   173,     0,     0,
       0,   175,     0,     0,     0,     0,     0,     0,     0,     0,
      16,    88,   191,   192,   190,   187,   170,   174,   126,   171,
       0,     0,   177,   206,    96,   104,   201,   202,   118,   105,
     107,   109,   110,   111,   112,   198,   113,    95,     0,     0,
       0,    20,    40,     0,    43,    11,   179,     0,   181,   119,
     120,   108,     0,    97,     0,    14,    21,     0,    18,    46,
       0,    89,     0,   199,     0,     0,     0,    19,     0,    56,
      48,     0,     0,    91,   180,     0,     0,     0,    24,    25,
      27,    26,     0,     0,     0,     0,     0,     0,    57,     0,
       0,     0,    90,    92,    22,     0,     0,    23,    28,     0,
       0,     0,     0,    50,    51,    52,    47,     0,     0,    65,
       0,    45,     0,    93,     0,     0,     0,    32,    33,    34,
      35,    36,     0,     0,    17,    60,    59,     0,     0,     0,
      53,    54,    55,    49,    94,     0,     0,     0,    31,    39,
       0,    38,    58,    62,    63,    64,     0,    44,     0,    66,
      30,    29,    41,    37,    61,     0,     0,     0,     0,    73,
       0,     0,    73,    80,    79,    78,     0,     0,     0,    72,
       0,     0,    71,     0,    82,    73,    73,    73,    73,    81,
      85,    68,    83,    74,    76,    75,    77,     0,     0,     0,
       0,    84,    70,    69,    87,    67,     0,    86
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,
    -312,  -312,  -312,   317,  -188,  -312,  -312,  -312,   295,  -312,
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,   216,
    -311,  -310,  -312,  -312,  -312,  -312,  -312,   321,  -312,  -312,
    -312,   477,  -312,   -98,  -153,  -312,   -77,  -312,   -76,   380,
    -312,   -11,  -312,  -312,   -10,    -9,   -62,  -240,  -312,  -312,
      -8,   -73,  -312,   486,   -72,  -312,  -312,   -71,  -312,   413,
    -312,   -65,  -312,   381,   383,   -61,  -312,   391,  -312,   -75,
     -74,   488,  -312,  -312,  -312,  -312,   -70,  -312,  -312,  -312,
    -312,   559,  -312,  -312,  -312,   500,  -312
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,    10,    64,    81,    82,   123,   124,   125,   169,
     170,   190,   246,   220,   221,   257,   258,   259,   260,   264,
     302,   282,   320,   238,   191,   224,   249,   250,   270,   286,
     313,   267,   268,   307,   289,   326,   309,   329,   370,   341,
     349,   342,   343,   361,   225,   241,   252,   253,    83,    84,
     126,   120,   166,   160,   161,   209,    40,   231,    41,    42,
      43,    95,    45,    46,    96,    97,   152,    99,   100,   101,
     102,    50,    51,   106,    52,    53,    71,    54,    55,   107,
     199,   153,   154,   155,   156,   157,   137,   138,   194,    56,
      57,   104,    58,   162,   163,   215,    59,    60,     3,     4,
       6,     7,    61,    62,    66,    67,   133
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
      44,    47,    48,    49,   261,   262,    98,   103,   127,   128,
     110,   115,   129,   130,   131,   132,   276,   368,    12,   139,
     140,    77,    86,   164,   338,   338,   338,   338,   345,   272,
     205,   352,   236,   205,   353,   108,   109,   356,   358,   296,
     339,   344,   355,   357,   363,   364,   365,   366,   159,   159,
     354,   184,     1,   185,   315,   316,   204,   217,     5,   236,
      65,   251,   159,   321,    16,   205,    79,    78,   187,     5,
     233,   235,   327,    80,    44,    47,    48,    49,   219,   328,
       8,   369,     9,   340,   340,   340,   340,   310,   180,    14,
     292,   311,   312,   181,   293,   323,    15,   324,   139,   140,
     325,   305,    11,    17,   226,   306,   227,   192,   210,   211,
      98,   193,   212,   213,   214,   216,   118,   119,    18,    19,
      20,    21,    22,    23,    24,    25,    26,    27,   373,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    20,
      21,    22,    23,    65,    38,    69,    39,    68,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,    90,    91,
      92,    89,   297,   298,   299,   300,   301,   198,    47,    48,
      49,   243,   173,   174,    44,    47,    48,    49,    20,    21,
      22,    23,    24,    25,    26,    27,    63,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    90,    91,    92,
      93,   200,    70,   346,    94,   347,   348,   229,   230,   114,
     283,   284,   285,   347,   348,    72,    73,    74,    20,    21,
      22,    23,    24,    25,    26,    27,   319,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    90,    91,    92,
      93,    75,    76,   105,    94,    85,    88,   112,    89,   114,
     116,    20,    21,    22,    23,   114,   117,   122,   136,   134,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      90,    91,    92,    17,   207,   144,   135,   141,   142,   143,
     145,   146,   208,   149,   148,   147,   159,   150,    18,    19,
      20,    21,    22,    23,    24,    25,    26,    27,   158,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    89,
     165,   167,   171,   168,    38,   172,    39,   175,   176,   178,
     182,   188,   179,   183,   186,   189,    20,    21,    22,    23,
      24,    25,    26,    27,   203,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,    90,    91,    92,    93,    17,
     206,   219,    94,   218,   222,   223,   234,   232,   237,   228,
     239,   240,   242,    94,    18,    19,    20,    21,    22,    23,
      24,    25,    26,    27,   244,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,   255,   245,   248,   251,   254,
      38,   263,    39,   266,   269,   271,   256,   265,   280,   274,
     279,   277,    20,    21,    22,    23,   281,   275,   288,   287,
     290,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    90,    91,    92,    20,    21,    22,    23,   256,   372,
     291,   294,   303,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    90,    91,    92,    20,    21,    22,    23,
     295,   196,   304,   314,   308,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,    90,    91,    92,    20,    21,
      22,    23,    24,    25,    26,    27,   318,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    20,    21,    22,
      23,    24,    25,    26,    27,   317,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,    90,    91,   151,    93,
     322,   330,   331,    94,   332,   333,   334,   335,   114,    20,
      21,    22,    23,    24,    25,    26,    27,   336,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,    90,    91,
      92,    93,   337,   346,   351,   359,   360,   362,   371,   367,
     374,   375,   376,   377,   278,   247,   350,   121,   197,   111,
     177,   202,   201,   113,   195,    13,    87,     0,     0,     0,
       0,     0,     0,   273
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      11,    11,    11,    11,   244,   245,    68,    68,    85,    85,
      72,    76,    85,    85,    85,    85,   256,    24,     4,    94,
      94,    46,     6,   121,    28,    28,    28,    28,   338,    16,
     183,   342,   220,   186,    28,    88,    89,   347,   348,   279,
      44,    44,    44,    44,   355,   356,   357,   358,    44,    44,
      44,    88,     3,    90,   294,   295,    52,    52,    44,   247,
      44,    48,    44,   303,     5,   218,    49,    92,   166,    44,
      52,    10,    14,    56,    85,    85,    85,    85,    17,    21,
       0,    88,     7,    87,    87,    87,    87,    37,    88,    86,
      82,    41,    42,    93,    86,    38,    44,    40,   173,   173,
      43,    37,    90,    44,    89,    41,    91,   172,   185,   185,
     172,   172,   185,   185,   185,   185,    50,    51,    59,    60,
      61,    62,    63,    64,    65,    66,    67,    68,   368,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    61,
      62,    63,    64,    44,    85,    92,    87,    46,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    44,    31,    32,    33,    34,    35,   178,   178,   178,
     178,   232,    88,    89,   185,   185,   185,   185,    61,    62,
      63,    64,    65,    66,    67,    68,    86,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    44,    44,    27,    87,    29,    30,    54,    55,    92,
      38,    39,    40,    29,    30,    46,    87,    46,    61,    62,
      63,    64,    65,    66,    67,    68,    36,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,    46,    46,    82,    87,    90,    86,    82,    44,    92,
      83,    61,    62,    63,    64,    92,    82,    57,    44,    86,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    82,    44,    45,    82,    91,    91,    91,    91,
      91,    47,    53,    44,    93,    88,    44,    89,    59,    60,
      61,    62,    63,    64,    65,    66,    67,    68,    93,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    44,
      58,     8,    44,     9,    85,    46,    87,    84,    82,    69,
      46,    44,    87,    86,    86,    11,    61,    62,    63,    64,
      65,    66,    67,    68,    83,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    44,
      44,    17,    87,    86,    44,    13,    44,    46,    20,    89,
      44,    15,    44,    87,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68,    46,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    44,    87,    30,    48,    89,
      85,    18,    87,    25,    29,    44,    87,    90,    90,    86,
      47,    86,    61,    62,    63,    64,    19,    44,    26,    90,
      90,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    61,    62,    63,    64,    87,    44,
      86,    88,    46,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    61,    62,    63,    64,
      88,    44,    12,    86,    90,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    61,    62,
      63,    64,    65,    66,    67,    68,    86,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    61,    62,    63,
      64,    65,    66,    67,    68,    89,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    83,
      86,    89,    89,    87,    86,    86,    86,    82,    92,    61,
      62,    63,    64,    65,    66,    67,    68,    90,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    22,    27,    23,    89,    44,    44,    44,    27,
      44,    86,    27,    44,   259,   238,   340,    80,   178,    73,
     147,   180,   179,    75,   173,     6,    66,    -1,    -1,    -1,
      -1,    -1,    -1,   252
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,    95,   192,   193,    44,   194,   195,     0,     7,
      96,    90,     4,   195,    86,    44,     5,    44,    59,    60,
      61,    62,    63,    64,    65,    66,    67,    68,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    85,    87,
     150,   152,   153,   154,   155,   156,   157,   158,   159,   164,
     165,   166,   168,   169,   171,   172,   183,   184,   186,   190,
     191,   196,   197,    86,    97,    44,   198,   199,    46,    92,
      44,   170,    46,    87,    46,    46,    46,    46,    92,    49,
      56,    98,    99,   142,   143,    90,     6,   199,    86,    44,
      80,    81,    82,    83,    87,   155,   158,   159,   160,   161,
     162,   163,   164,   179,   185,    82,   167,   173,    88,    89,
     160,   167,    82,   185,    92,   175,    83,    82,    50,    51,
     145,   145,    57,   100,   101,   102,   144,   150,   152,   165,
     168,   171,   190,   200,    86,    91,    44,   180,   181,   183,
     184,    91,    91,    91,    82,    91,    47,    88,    93,    44,
      89,    82,   160,   175,   176,   177,   178,   179,    93,    44,
     147,   148,   187,   188,   147,    58,   146,     8,     9,   103,
     104,    44,    46,    88,    89,    84,    82,   173,    69,    87,
      88,    93,    46,    86,    88,    90,    86,   147,    44,    11,
     105,   118,   175,   179,   182,   181,    44,   153,   155,   174,
      44,   178,   177,    83,    52,   148,    44,    45,    53,   149,
     150,   152,   165,   168,   171,   189,   190,    52,    86,    17,
     107,   108,    44,    13,   119,   138,    89,    91,    89,    54,
      55,   151,    46,    52,    44,    10,   108,    20,   117,    44,
      15,   139,    44,   179,    46,    87,   106,   107,    30,   120,
     121,    48,   140,   141,    89,    44,    87,   109,   110,   111,
     112,   161,   161,    18,   113,    90,    25,   125,   126,    29,
     122,    44,    16,   141,    86,    44,   161,    86,   112,    47,
      90,    19,   115,    38,    39,    40,   123,    90,    26,   128,
      90,    86,    82,    86,    88,    88,   161,    31,    32,    33,
      34,    35,   114,    46,    12,    37,    41,   127,    90,   130,
      37,    41,    42,   124,    86,   161,   161,    89,    86,    36,
     116,   161,    86,    38,    40,    43,   129,    14,    21,   131,
      89,    89,    86,    86,    86,    82,    90,    22,    28,    44,
      87,   133,   135,   136,    44,   135,    27,    29,    30,   134,
     133,    23,   134,    28,    44,    44,   135,    44,   135,    89,
      44,   137,    44,   134,   134,   134,   134,    27,    24,    88,
     132,    44,    44,   161,    44,    86,    27,    44
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,    94,    95,    96,    97,    97,    98,    98,    99,    99,
     100,   101,   102,   102,   103,   104,   104,   105,   106,   106,
     107,   107,   108,   108,   109,   109,   110,   111,   111,   112,
     112,   113,   114,   114,   114,   114,   114,   115,   116,   116,
     117,   117,   118,   118,   119,   120,   121,   121,   122,   122,
     123,   123,   123,   124,   124,   124,   125,   125,   126,   127,
     127,   128,   129,   129,   129,   130,   130,   131,   132,   132,
     132,   133,   133,   134,   134,   134,   134,   134,   135,   135,
     135,   135,   136,   136,   137,   137,   137,   137,   138,   138,
     139,   140,   140,   141,   141,   142,   143,   144,   145,   145,
     145,   146,   146,   147,   147,   148,   148,   149,   149,   149,
     149,   149,   149,   149,   150,   150,   150,   150,   151,   151,
     151,   152,   152,   153,   153,   153,   154,   154,   155,   155,
     156,   156,   156,   156,   157,   157,   157,   157,   158,   158,
     159,   159,   159,   159,   160,   160,   160,   161,   161,   161,
     161,   162,   162,   162,   163,   164,   164,   164,   164,   165,
     165,   166,   167,   168,   168,   169,   170,   170,   171,   171,
     172,   172,   173,   173,   174,   175,   176,   176,   177,   177,
     177,   177,   178,   178,   178,   179,   180,   180,   181,   181,
     181,   182,   182,   183,   184,   185,   185,   186,   187,   187,
     188,   188,   189,   190,   190,   190,   190,   191,   191,   192,
     192,   193,   194,   194,   195,   196,   196,   196,   196,   196,
     196,   196,   197,   198,   198,   199,   200,   200,   200,   200,
     200,   200
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
       0,     7,     0,     2,     7,     3,     0,     3,     0,     3,
       1,     1,     1,     1,     1,     1,     0,     1,     4,     1,
       1,     4,     1,     1,     1,     0,     2,     9,     0,     2,
       2,     2,     2,     0,     3,     3,     3,     3,     2,     2,
       1,     3,     3,     4,     3,     1,     5,     3,     0,     2,
       3,     1,     2,     3,     4,     5,     5,     5,     0,     1,
       1,     0,     1,     1,     3,     3,     1,     1,     2,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     0,     1,
       1,     1,     3,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       2,     2,     2,     2,     3,     1,     1,     1,     1,     1,
       3,     4,     3,     1,     3,     3,     1,     3,     1,     3,
       6,     6,     1,     3,     1,     3,     1,     3,     1,     4,
       6,     4,     1,     1,     1,     3,     1,     3,     1,     1,
       3,     1,     1,     3,     3,     1,     3,     3,     3,     5,
       1,     3,     1,     1,     4,     3,     6,     1,     1,     0,
       1,     3,     2,     3,     3,     1,     1,     1,     1,     1,
       1,     1,     3,     2,     3,     3,     1,     1,     1,     1,
       1,     1
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 340;
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
       2,     2,     2,     2,     2,    91,     2,     2,     2,     2,
      87,    89,     2,     2,    88,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    90,    86,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    92,     2,    93,     2,     2,     2,     2,     2,     2,
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
      85
    };
  }


  private static final int YYLAST_ = 573;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 8;
  private static final int YYNTOKENS_ = 94;


}
/* "src/main/java/parser/Parser.y":823  */
