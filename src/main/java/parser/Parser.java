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
    S_type_specification(149),     /* type_specification  */
    S_opt_edge(150),               /* opt_edge  */
    S_spec_init_type(151),         /* spec_init_type  */
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
    S_initialized_custom_type(184), /* initialized_custom_type  */
    S_initialized_spec_custom_type(185), /* initialized_spec_custom_type  */
    S_initialized_structure(186),  /* initialized_structure  */
    S_fb_name_decl(187),           /* fb_name_decl  */
    S_identifier_list(188),        /* identifier_list  */
    S_standard_function_block_name(189), /* standard_function_block_name  */
    S_string_specification(190),   /* string_specification  */
    S_type_string(191),            /* type_string  */
    S_opt_data_type_declaration(192), /* opt_data_type_declaration  */
    S_data_type_declaration(193),  /* data_type_declaration  */
    S_type_declaration_list(194),  /* type_declaration_list  */
    S_type_declaration(195),       /* type_declaration  */
    S_specification_list(196),     /* specification_list  */
    S_structure_specification(197), /* structure_specification  */
    S_structure_declaration(198),  /* structure_declaration  */
    S_structure_element_declaration_list(199), /* structure_element_declaration_list  */
    S_structure_element_declaration(200), /* structure_element_declaration  */
    S_specification_sublist(201);  /* specification_sublist  */


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
      SymbolKind.S_type_specification,
      SymbolKind.S_opt_edge,
      SymbolKind.S_spec_init_type,
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
      SymbolKind.S_initialized_custom_type,
      SymbolKind.S_initialized_spec_custom_type,
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
  "var_init_decl", "type_specification", "opt_edge", "spec_init_type",
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
  "initialized_constant", "initialized_custom_type",
  "initialized_spec_custom_type", "initialized_structure", "fb_name_decl",
  "identifier_list", "standard_function_block_name",
  "string_specification", "type_string", "opt_data_type_declaration",
  "data_type_declaration", "type_declaration_list", "type_declaration",
  "specification_list", "structure_specification", "structure_declaration",
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
    /* "src/main/java/parser/Parser.y":336  */
    { Compound c = new Compound(((List<Publisher>)(yystack.valueAt (2)))); c.source(Source.OUT).publish(); yyval = c; };
  break;


  case 103: /* var_init_decl_list: var_init_decl  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":361  */
    {
        List<Publisher> list = new ArrayList<>();
        list.add(((Publisher)(yystack.valueAt (0))));
        yyval = list;
    };
  break;


  case 104: /* var_init_decl_list: var_init_decl_list ';' var_init_decl  */
  if (yyn == 104)
    /* "src/main/java/parser/Parser.y":366  */
                                           { (( List<Publisher>) yyval).add(((Publisher)(yystack.valueAt (0)))); };
  break;


  case 105: /* var_init_decl: identifier_list ':' type_specification  */
  if (yyn == 105)
    /* "src/main/java/parser/Parser.y":371  */
                                           { yyval = null; };
  break;


  case 106: /* var_init_decl: identifier_list ':' spec_init_type  */
  if (yyn == 106)
    /* "src/main/java/parser/Parser.y":372  */
                                         { yyval = new Declaration(this.symbolTable, ((List<String>)(yystack.valueAt (2))), ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.VARIABLE)); };
  break;


  case 107: /* var_init_decl: fb_name_decl  */
  if (yyn == 107)
    /* "src/main/java/parser/Parser.y":373  */
                   { yyval = null; };
  break;


  case 116: /* spec_init_type: simple_spec_init  */
  if (yyn == 116)
    /* "src/main/java/parser/Parser.y":391  */
                     { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 117: /* spec_init_type: subrange_spec_init  */
  if (yyn == 117)
    /* "src/main/java/parser/Parser.y":392  */
                         { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 118: /* spec_init_type: enumerated_spec_init  */
  if (yyn == 118)
    /* "src/main/java/parser/Parser.y":393  */
                           { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 119: /* simple_spec_init: elementary_type_name  */
  if (yyn == 119)
    /* "src/main/java/parser/Parser.y":398  */
    {
        yyval = new LexemeInfoBuilder().type(Type.SIMPLE).subtype(((Subtype)(yystack.valueAt (0))));
    };
  break;


  case 120: /* simple_spec_init: elementary_type_name ASSIGN_OP constant  */
  if (yyn == 120)
    /* "src/main/java/parser/Parser.y":402  */
    {
        yyval = new LexemeInfoBuilder().type(Type.SIMPLE).subtype(((Subtype)(yystack.valueAt (2)))).initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 121: /* simple_spec_init: initialized_constant  */
  if (yyn == 121)
    /* "src/main/java/parser/Parser.y":406  */
    { 
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).type(Type.SIMPLE); 
    };
  break;


  case 122: /* elementary_type_name: numeric_type_name  */
  if (yyn == 122)
    /* "src/main/java/parser/Parser.y":414  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 123: /* elementary_type_name: date_type_name  */
  if (yyn == 123)
    /* "src/main/java/parser/Parser.y":415  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 124: /* elementary_type_name: bit_string_type_name  */
  if (yyn == 124)
    /* "src/main/java/parser/Parser.y":416  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 125: /* numeric_type_name: integer_type_name  */
  if (yyn == 125)
    /* "src/main/java/parser/Parser.y":420  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 126: /* numeric_type_name: real_type_name  */
  if (yyn == 126)
    /* "src/main/java/parser/Parser.y":421  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 127: /* integer_type_name: signed_integer_type_name  */
  if (yyn == 127)
    /* "src/main/java/parser/Parser.y":425  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 128: /* integer_type_name: unsigned_integer_type_name  */
  if (yyn == 128)
    /* "src/main/java/parser/Parser.y":426  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 129: /* signed_integer_type_name: SINT  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":430  */
           { yyval = Subtype.SINT; };
  break;


  case 130: /* signed_integer_type_name: INT  */
  if (yyn == 130)
    /* "src/main/java/parser/Parser.y":431  */
           { yyval = Subtype.INT;  };
  break;


  case 131: /* signed_integer_type_name: DINT  */
  if (yyn == 131)
    /* "src/main/java/parser/Parser.y":432  */
           { yyval = Subtype.DINT; };
  break;


  case 132: /* signed_integer_type_name: LINT  */
  if (yyn == 132)
    /* "src/main/java/parser/Parser.y":433  */
           { yyval = Subtype.LINT; };
  break;


  case 133: /* unsigned_integer_type_name: USINT  */
  if (yyn == 133)
    /* "src/main/java/parser/Parser.y":437  */
            { yyval = Subtype.USINT; };
  break;


  case 134: /* unsigned_integer_type_name: UINT  */
  if (yyn == 134)
    /* "src/main/java/parser/Parser.y":438  */
            { yyval = Subtype.UINT;  };
  break;


  case 135: /* unsigned_integer_type_name: UDINT  */
  if (yyn == 135)
    /* "src/main/java/parser/Parser.y":439  */
            { yyval = Subtype.UDINT; };
  break;


  case 136: /* unsigned_integer_type_name: ULINT  */
  if (yyn == 136)
    /* "src/main/java/parser/Parser.y":440  */
            { yyval = Subtype.ULINT; };
  break;


  case 137: /* real_type_name: REAL  */
  if (yyn == 137)
    /* "src/main/java/parser/Parser.y":444  */
            { yyval = Subtype.REAL;  };
  break;


  case 138: /* real_type_name: LREAL  */
  if (yyn == 138)
    /* "src/main/java/parser/Parser.y":445  */
            { yyval = Subtype.LREAL; };
  break;


  case 139: /* date_type_name: TIME  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":449  */
                    { yyval = Subtype.TIME;          };
  break;


  case 140: /* date_type_name: DATE  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":450  */
                    { yyval = Subtype.DATE;          };
  break;


  case 141: /* date_type_name: TIME_OF_DAY  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":451  */
                    { yyval = Subtype.TIME_OF_DAY;   };
  break;


  case 142: /* date_type_name: DATE_AND_TIME  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":452  */
                    { yyval = Subtype.DATE_AND_TIME; };
  break;


  case 147: /* number: TRUE  */
  if (yyn == 147)
    /* "src/main/java/parser/Parser.y":466  */
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


  case 148: /* number: FALSE  */
  if (yyn == 148)
    /* "src/main/java/parser/Parser.y":478  */
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


  case 149: /* number: number_prefix NUMERIC_LITERAL  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":490  */
    {
        // todo: hacer conversion de esta constante en codigo
        yyval = "";
    };
  break;


  case 153: /* time: date_type_name '#' TIME_LITERAL  */
  if (yyn == 153)
    /* "src/main/java/parser/Parser.y":503  */
                                    {
        // Accion semantica que verifica que prefix es del mismo tipo que time_literal
    };
  break;


  case 154: /* bit_string_type_name: BYTE  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":509  */
            { yyval = Subtype.BYTE;  };
  break;


  case 155: /* bit_string_type_name: WORD  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":510  */
            { yyval = Subtype.WORD;  };
  break;


  case 156: /* bit_string_type_name: DWORD  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":511  */
            { yyval = Subtype.DWORD; };
  break;


  case 157: /* bit_string_type_name: LWORD  */
  if (yyn == 157)
    /* "src/main/java/parser/Parser.y":512  */
            { yyval = Subtype.LWORD; };
  break;


  case 158: /* subrange_spec_init: subrange_specification  */
  if (yyn == 158)
    /* "src/main/java/parser/Parser.y":518  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 159: /* subrange_spec_init: subrange_specification ASSIGN_OP NUMERIC_LITERAL  */
  if (yyn == 159)
    /* "src/main/java/parser/Parser.y":519  */
                                                       { yyval = ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(((String)(yystack.valueAt (0)))); };
  break;


  case 160: /* subrange_specification: integer_type_name '(' subrange ')'  */
  if (yyn == 160)
    /* "src/main/java/parser/Parser.y":524  */
    { 
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (1))).subtype(((Subtype)(yystack.valueAt (3))));
    };
  break;


  case 161: /* subrange: NUMERIC_LITERAL RANGE_OP NUMERIC_LITERAL  */
  if (yyn == 161)
    /* "src/main/java/parser/Parser.y":531  */
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


  case 162: /* enumerated_spec_init: initialized_custom_type  */
  if (yyn == 162)
    /* "src/main/java/parser/Parser.y":546  */
    { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).type(Type.ENUMERATE); };
  break;


  case 163: /* enumerated_spec_init: initialized_spec_custom_type  */
  if (yyn == 163)
    /* "src/main/java/parser/Parser.y":548  */
    { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).type(Type.ENUMERATE); };
  break;


  case 164: /* enumerated_spec_init: enumerated_specification ASSIGN_OP IDENTIFIER  */
  if (yyn == 164)
    /* "src/main/java/parser/Parser.y":550  */
    {
        yyval = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters(((List<String>)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 165: /* enumerated_spec_init: enumerated_specification ASSIGN_OP IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 165)
    /* "src/main/java/parser/Parser.y":557  */
    {
        yyval = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters(((List<String>)(yystack.valueAt (4))))
            .initialValue(((String)(yystack.valueAt (0))));
            //TODO name mangling:
    };
  break;


  case 166: /* enumerated_specification: '(' enumerated_list ')'  */
  if (yyn == 166)
    /* "src/main/java/parser/Parser.y":567  */
                           { yyval = ((List<String>)(yystack.valueAt (1))); };
  break;


  case 167: /* enumerated_list: IDENTIFIER  */
  if (yyn == 167)
    /* "src/main/java/parser/Parser.y":572  */
    {
        List<String> l = new ArrayList<String>();
        l.add(((String)(yystack.valueAt (0))));
        yyval = l;
    };
  break;


  case 168: /* enumerated_list: enumerated_list ',' IDENTIFIER  */
  if (yyn == 168)
    /* "src/main/java/parser/Parser.y":578  */
    {
        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 195: /* initialized_constant: IDENTIFIER ASSIGN_OP constant  */
  if (yyn == 195)
    /* "src/main/java/parser/Parser.y":648  */
    {
        yyval = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 196: /* initialized_custom_type: IDENTIFIER ASSIGN_OP IDENTIFIER  */
  if (yyn == 196)
    /* "src/main/java/parser/Parser.y":658  */
    {
        yyval = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 197: /* initialized_spec_custom_type: IDENTIFIER ASSIGN_OP IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 197)
    /* "src/main/java/parser/Parser.y":668  */
    {
        yyval = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (4))))
            .initialValue(((String)(yystack.valueAt (0))));
            // TODO: name mangling
    };
  break;


  case 201: /* identifier_list: IDENTIFIER  */
  if (yyn == 201)
    /* "src/main/java/parser/Parser.y":689  */
    {
        List<String> l = new ArrayList<String>();
        l.add(((String)(yystack.valueAt (0))));
        yyval = l;
    };
  break;


  case 202: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 202)
    /* "src/main/java/parser/Parser.y":694  */
                                     { ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0)))); yyval = ((List<String>)(yystack.valueAt (2))); };
  break;



/* "src/main/java/parser/Parser.java":1462  */

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
      31,    10,    57,    96,  -312,    21,    15,    62,  -312,    74,
    -312,    60,  -312,   102,  -312,  -312,   165,   164,  -312,  -312,
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,   120,   169,
    -312,  -312,   168,  -312,   128,  -312,  -312,  -312,  -312,  -312,
    -312,   170,  -312,   171,  -312,   172,  -312,  -312,  -312,  -312,
    -312,   -26,  -312,  -312,  -312,  -312,     4,   129,    17,   142,
     235,   163,  -312,    77,   428,   163,   166,   203,   157,   167,
     173,   122,   122,  -312,   196,  -312,  -312,   275,  -312,   182,
    -312,   189,  -312,  -312,  -312,  -312,   210,   190,   191,   192,
    -312,  -312,   174,  -312,   194,  -312,   240,   200,   197,   213,
    -312,  -312,   202,  -312,   198,   396,  -312,  -312,   201,  -312,
    -312,   248,   248,   237,  -312,   285,   295,  -312,  -312,  -312,
    -312,  -312,  -312,  -312,   276,   277,    86,  -312,  -312,  -312,
    -312,  -312,  -312,   241,  -312,  -312,   239,   163,   255,  -312,
    -312,   282,   242,  -312,  -312,   -58,  -312,  -312,  -312,   281,
    -312,   244,  -312,  -312,    56,   245,  -312,   248,  -312,   284,
    -312,   321,  -312,   119,   210,  -312,  -312,  -312,  -312,   377,
    -312,   159,   396,  -312,   250,     0,   300,   199,     6,   270,
     340,   314,  -312,   346,  -312,  -312,  -312,  -312,  -312,  -312,
    -312,  -312,    73,   272,  -312,  -312,  -312,  -312,  -312,   164,
    -312,   150,  -312,  -312,  -312,  -312,   317,  -312,  -312,    19,
     320,    64,  -312,   345,   322,  -312,   352,  -312,   324,  -312,
    -312,  -312,  -312,   283,  -312,   323,  -312,  -312,   289,   340,
     341,   330,  -312,   290,  -312,   311,   451,   362,   340,   310,
     376,   373,   378,    20,  -312,  -312,   334,   333,   337,  -312,
     359,  -312,  -312,   418,   390,   405,    49,   391,   456,  -312,
     394,   399,   -13,  -312,  -312,  -312,   398,   409,  -312,  -312,
     451,    66,   441,   504,  -312,  -312,  -312,  -312,    65,   427,
    -312,    54,  -312,   432,  -312,   451,   451,   430,  -312,  -312,
    -312,  -312,  -312,   434,    79,  -312,  -312,  -312,   448,    32,
      69,  -312,  -312,  -312,  -312,  -312,   446,   447,   452,  -312,
    -312,   453,  -312,  -312,  -312,  -312,  -312,   454,  -312,   455,
    -312,  -312,  -312,  -312,  -312,  -312,   457,   519,    -7,    -3,
      87,    -7,   520,   178,  -312,   515,  -312,     5,    -2,    -1,
    -312,   459,   500,  -312,   501,  -312,    87,   178,    87,   178,
    -312,   522,    -6,  -312,  -312,  -312,  -312,  -312,   502,   355,
     506,   465,  -312,  -312,  -312,   525,  -312,   509,  -312
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
     210,     0,     0,     0,   211,     0,     0,     0,     1,     0,
       2,     0,   212,     0,   213,     4,     0,     0,   208,   209,
     154,   155,   156,   157,   139,   141,   140,   142,   129,   130,
     131,   132,   133,   134,   135,   136,   137,   138,     0,     0,
     216,   116,   119,   122,   125,   127,   128,   126,   123,   124,
     117,   158,   118,     0,   217,   169,   121,   162,   163,   220,
     219,   204,   215,   218,   221,   214,     8,     0,     0,     0,
       0,     0,   167,     0,     0,     0,     0,     0,     0,     0,
       0,    98,    98,     5,    12,     7,     6,     0,   222,     0,
     223,   196,   147,   148,   146,   143,     0,     0,     0,     0,
     195,   145,     0,   144,     0,   198,     0,   173,     0,     0,
     166,   120,     0,   159,   164,     0,   170,   206,     0,    99,
     100,     0,     0,   101,     9,     0,    15,    10,   226,   227,
     228,   229,   225,   224,     0,     0,     0,   187,   189,   190,
     191,   150,   151,     0,   149,   152,     0,     0,     0,   168,
     160,     0,   146,   183,   185,     0,   177,   179,   184,   205,
     201,     0,   103,   107,     0,     0,   102,     0,     3,     0,
      13,    42,   197,     0,     0,   186,   153,   161,   174,     0,
     165,     0,     0,   176,     0,     0,     0,     0,     0,     0,
       0,     0,    16,    88,   193,   194,   192,   188,   171,   175,
     125,   172,     0,     0,   178,   207,    96,   104,   202,   108,
     203,   113,   105,   106,   112,   111,   199,   110,    95,     0,
       0,     0,    20,    40,     0,    43,    11,   180,     0,   182,
     114,   115,   109,     0,    97,     0,    14,    21,     0,    18,
      46,     0,    89,     0,   200,     0,     0,     0,    19,     0,
      56,    48,     0,     0,    91,   181,     0,     0,     0,    24,
      25,    27,    26,     0,     0,     0,     0,     0,     0,    57,
       0,     0,     0,    90,    92,    22,     0,     0,    23,    28,
       0,     0,     0,     0,    50,    51,    52,    47,     0,     0,
      65,     0,    45,     0,    93,     0,     0,     0,    32,    33,
      34,    35,    36,     0,     0,    17,    60,    59,     0,     0,
       0,    53,    54,    55,    49,    94,     0,     0,     0,    31,
      39,     0,    38,    58,    62,    63,    64,     0,    44,     0,
      66,    30,    29,    41,    37,    61,     0,     0,     0,     0,
      73,     0,     0,    73,    80,    79,    78,     0,     0,     0,
      72,     0,     0,    71,     0,    82,    73,    73,    73,    73,
      81,    85,    68,    83,    74,    76,    75,    77,     0,     0,
       0,     0,    84,    70,    69,    87,    67,     0,    86
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,
    -312,  -312,  -312,   315,  -197,  -312,  -312,  -312,   296,  -312,
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,
    -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,  -312,   214,
    -311,  -310,  -312,  -312,  -312,  -312,  -312,   304,  -312,  -312,
    -312,   476,  -312,  -100,  -157,  -312,  -312,   -79,  -312,   380,
    -312,   -11,  -312,  -312,   -10,    -9,   -63,  -240,  -312,  -312,
      -8,  -312,  -312,   485,  -312,  -312,  -312,   -78,  -312,   414,
    -312,   -68,  -312,   381,   383,   -66,  -312,   388,  -312,   -82,
     -81,   -80,   -75,  -312,  -312,  -312,   -74,  -312,  -312,  -312,
    -312,   559,  -312,  -312,  -312,  -312,   498,  -312
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,    10,    66,    83,    84,   124,   125,   126,   170,
     171,   192,   247,   221,   222,   258,   259,   260,   261,   265,
     303,   283,   321,   239,   193,   225,   250,   251,   271,   287,
     314,   268,   269,   308,   290,   327,   310,   330,   371,   342,
     350,   343,   344,   362,   226,   242,   253,   254,    85,    86,
     127,   121,   167,   161,   162,   212,   232,    40,    41,    42,
      43,    97,    45,    46,    98,    99,   153,   101,   102,   103,
     104,    50,    51,   107,    52,    53,    73,    54,    55,   108,
     201,   154,   155,   156,   157,   158,   136,   137,   196,    56,
      57,    58,    59,   163,   164,   216,    60,    61,     3,     4,
       6,     7,    62,    63,    64,    68,    69,   132
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
      44,    47,    48,    49,   105,   262,   263,   100,   128,   129,
     116,   111,   130,   131,   138,   139,   140,   277,   369,    12,
      79,   339,   165,    88,   237,   339,   339,   339,   207,   346,
     182,   207,   353,   354,     1,   183,   273,   340,   357,   359,
     297,   345,   356,   358,   160,   364,   365,   366,   367,   355,
     160,   237,   206,    81,     5,   316,   317,     8,   218,     5,
      82,    67,   207,   160,   322,    16,    80,   189,   252,   293,
     324,   234,   325,   294,   236,   326,    44,    47,    48,    49,
     341,   220,   370,   328,   341,   341,   341,   284,   285,   286,
     329,   311,   138,   139,   140,   312,   313,   298,   299,   300,
     301,   302,   306,     9,    17,   194,   307,   195,   213,   214,
     100,    11,   215,   217,   347,   320,   348,   349,    15,    18,
      19,    20,    21,    22,    23,    24,    25,    26,    27,   374,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      20,    21,    22,    23,   186,    38,   187,    39,    14,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    92,
      93,    94,   227,    91,   228,   109,   110,   244,   200,    47,
      48,    49,   119,   120,   174,   175,    44,    47,    48,    49,
      20,    21,    22,    23,    24,    25,    26,    27,    65,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    92,
      93,    94,    95,   202,   230,   231,    96,   348,   349,    67,
      70,   115,    71,    72,    74,    75,    76,    77,    78,    87,
      20,    21,    22,    23,    24,    25,    26,    27,    90,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    92,
      93,    94,    95,   209,   210,   106,    96,   114,   113,   115,
     117,   115,   211,   123,   135,   118,   144,   149,    18,    19,
      20,    21,    22,    23,    24,    25,    26,    27,   133,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    91,
     134,   141,   142,   143,    38,   145,    39,   146,   147,   151,
     148,   150,   160,   168,   159,   166,    20,    21,    22,    23,
      24,    25,    26,    27,   169,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,    92,    93,    94,    95,    17,
     172,   177,    96,   173,   179,   176,   180,   184,   190,   181,
     185,   188,   191,   205,    18,    19,    20,    21,    22,    23,
      24,    25,    26,    27,   208,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,   256,   219,   220,   223,   224,
      38,   229,    39,   233,   235,   238,   240,   241,   243,   245,
      96,   249,    20,    21,    22,    23,   246,   276,   252,   255,
     264,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    92,    93,    94,    20,    21,    22,    23,   257,   373,
     266,   267,   270,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    92,    93,    94,    20,    21,    22,    23,
     275,   198,   272,   278,   282,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,    92,    93,    94,    20,    21,
      22,    23,    24,    25,    26,    27,   257,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    20,    21,    22,
      23,    24,    25,    26,    27,   280,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,    92,    93,   152,    95,
     281,   288,   289,    96,   291,   292,   295,   304,   115,    20,
      21,    22,    23,    24,    25,    26,    27,   296,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,    92,    93,
      94,    95,    20,    21,    22,    23,   305,   309,   315,   318,
     319,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    92,    93,    94,   323,   331,   332,   336,   333,   334,
     335,   338,   347,   352,   361,   363,   372,   337,   360,   368,
     375,   376,   377,   378,   248,   351,   279,   274,   122,   199,
     112,   178,   197,   204,   203,    13,    89
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      11,    11,    11,    11,    70,   245,   246,    70,    87,    87,
      78,    74,    87,    87,    96,    96,    96,   257,    24,     4,
      46,    28,   122,     6,   221,    28,    28,    28,   185,   339,
      88,   188,   343,    28,     3,    93,    16,    44,   348,   349,
     280,    44,    44,    44,    44,   356,   357,   358,   359,    44,
      44,   248,    52,    49,    44,   295,   296,     0,    52,    44,
      56,    44,   219,    44,   304,     5,    92,   167,    48,    82,
      38,    52,    40,    86,    10,    43,    87,    87,    87,    87,
      87,    17,    88,    14,    87,    87,    87,    38,    39,    40,
      21,    37,   174,   174,   174,    41,    42,    31,    32,    33,
      34,    35,    37,     7,    44,   173,    41,   173,   187,   187,
     173,    90,   187,   187,    27,    36,    29,    30,    44,    59,
      60,    61,    62,    63,    64,    65,    66,    67,    68,   369,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      61,    62,    63,    64,    88,    85,    90,    87,    86,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    89,    44,    91,    88,    89,   233,   179,   179,
     179,   179,    50,    51,    88,    89,   187,   187,   187,   187,
      61,    62,    63,    64,    65,    66,    67,    68,    86,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    44,    54,    55,    87,    29,    30,    44,
      46,    92,    92,    44,    46,    87,    46,    46,    46,    90,
      61,    62,    63,    64,    65,    66,    67,    68,    86,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    83,    44,    45,    82,    87,    44,    82,    92,
      83,    92,    53,    57,    44,    82,    82,    44,    59,    60,
      61,    62,    63,    64,    65,    66,    67,    68,    86,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    44,
      91,    91,    91,    91,    85,    91,    87,    47,    88,    91,
      93,    89,    44,     8,    93,    58,    61,    62,    63,    64,
      65,    66,    67,    68,     9,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    44,
      44,    82,    87,    46,    69,    84,    44,    46,    44,    87,
      86,    86,    11,    83,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68,    44,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    44,    86,    17,    44,    13,
      85,    89,    87,    46,    44,    20,    44,    15,    44,    46,
      87,    30,    61,    62,    63,    64,    87,    44,    48,    89,
      18,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    61,    62,    63,    64,    87,    44,
      90,    25,    29,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    61,    62,    63,    64,
      86,    44,    44,    86,    19,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    61,    62,
      63,    64,    65,    66,    67,    68,    87,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    61,    62,    63,
      64,    65,    66,    67,    68,    47,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    83,
      90,    90,    26,    87,    90,    86,    88,    46,    92,    61,
      62,    63,    64,    65,    66,    67,    68,    88,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82,    83,    61,    62,    63,    64,    12,    90,    86,    89,
      86,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    86,    89,    89,    82,    86,    86,
      86,    22,    27,    23,    44,    44,    44,    90,    89,    27,
      44,    86,    27,    44,   239,   341,   260,   253,    82,   179,
      75,   147,   174,   182,   181,     6,    68
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
     151,   152,   153,   154,   155,   156,   157,   158,   159,   164,
     165,   166,   168,   169,   171,   172,   183,   184,   185,   186,
     190,   191,   196,   197,   198,    86,    97,    44,   199,   200,
      46,    92,    44,   170,    46,    87,    46,    46,    46,    46,
      92,    49,    56,    98,    99,   142,   143,    90,     6,   200,
      86,    44,    80,    81,    82,    83,    87,   155,   158,   159,
     160,   161,   162,   163,   164,   179,    82,   167,   173,    88,
      89,   160,   167,    82,    44,    92,   175,    83,    82,    50,
      51,   145,   145,    57,   100,   101,   102,   144,   151,   171,
     186,   190,   201,    86,    91,    44,   180,   181,   183,   184,
     185,    91,    91,    91,    82,    91,    47,    88,    93,    44,
      89,    91,    82,   160,   175,   176,   177,   178,   179,    93,
      44,   147,   148,   187,   188,   147,    58,   146,     8,     9,
     103,   104,    44,    46,    88,    89,    84,    82,   173,    69,
      44,    87,    88,    93,    46,    86,    88,    90,    86,   147,
      44,    11,   105,   118,   175,   179,   182,   181,    44,   153,
     155,   174,    44,   178,   177,    83,    52,   148,    44,    44,
      45,    53,   149,   151,   171,   186,   189,   190,    52,    86,
      17,   107,   108,    44,    13,   119,   138,    89,    91,    89,
      54,    55,   150,    46,    52,    44,    10,   108,    20,   117,
      44,    15,   139,    44,   179,    46,    87,   106,   107,    30,
     120,   121,    48,   140,   141,    89,    44,    87,   109,   110,
     111,   112,   161,   161,    18,   113,    90,    25,   125,   126,
      29,   122,    44,    16,   141,    86,    44,   161,    86,   112,
      47,    90,    19,   115,    38,    39,    40,   123,    90,    26,
     128,    90,    86,    82,    86,    88,    88,   161,    31,    32,
      33,    34,    35,   114,    46,    12,    37,    41,   127,    90,
     130,    37,    41,    42,   124,    86,   161,   161,    89,    86,
      36,   116,   161,    86,    38,    40,    43,   129,    14,    21,
     131,    89,    89,    86,    86,    86,    82,    90,    22,    28,
      44,    87,   133,   135,   136,    44,   135,    27,    29,    30,
     134,   133,    23,   134,    28,    44,    44,   135,    44,   135,
      89,    44,   137,    44,   134,   134,   134,   134,    27,    24,
      88,   132,    44,    44,   161,    44,    86,    27,    44
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
     145,   146,   146,   147,   147,   148,   148,   148,   149,   149,
     149,   149,   149,   150,   150,   150,   151,   151,   151,   152,
     152,   152,   153,   153,   153,   154,   154,   155,   155,   156,
     156,   156,   156,   157,   157,   157,   157,   158,   158,   159,
     159,   159,   159,   160,   160,   160,   161,   161,   161,   161,
     162,   162,   162,   163,   164,   164,   164,   164,   165,   165,
     166,   167,   168,   168,   168,   168,   169,   170,   170,   171,
     171,   172,   172,   173,   173,   174,   175,   176,   176,   177,
     177,   177,   177,   178,   178,   178,   179,   180,   180,   181,
     181,   181,   181,   182,   182,   183,   184,   185,   186,   187,
     187,   188,   188,   189,   190,   190,   190,   190,   191,   191,
     192,   192,   193,   194,   194,   195,   196,   196,   196,   196,
     197,   197,   198,   199,   199,   200,   201,   201,   201,   201
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
       1,     0,     1,     1,     3,     3,     3,     1,     1,     2,
       1,     1,     1,     0,     1,     1,     1,     1,     1,     1,
       3,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     2,
       2,     2,     2,     3,     1,     1,     1,     1,     1,     3,
       4,     3,     1,     1,     3,     5,     3,     1,     3,     1,
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


  private static final int YYLAST_ = 566;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 8;
  private static final int YYNTOKENS_ = 94;


}
/* "src/main/java/parser/Parser.y":766  */
