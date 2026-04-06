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

    import java.source.*;
    import utils.enums.*;
    import utils.SymbolTable;

/* "src/main/java/parser/Parser.java":51  */

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
    S_simple_specification(153),   /* simple_specification  */
    S_elementary_type_name(154),   /* elementary_type_name  */
    S_numeric_type_name(155),      /* numeric_type_name  */
    S_integer_type_name(156),      /* integer_type_name  */
    S_signed_integer_type_name(157), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(158), /* unsigned_integer_type_name  */
    S_real_type_name(159),         /* real_type_name  */
    S_date_type_name(160),         /* date_type_name  */
    S_constant(161),               /* constant  */
    S_number(162),                 /* number  */
    S_number_prefix(163),          /* number_prefix  */
    S_time(164),                   /* time  */
    S_bit_string_type_name(165),   /* bit_string_type_name  */
    S_subrange_spec_init(166),     /* subrange_spec_init  */
    S_subrange_specification(167), /* subrange_specification  */
    S_subrange(168),               /* subrange  */
    S_enumerated_spec_init(169),   /* enumerated_spec_init  */
    S_enumerated_specification(170), /* enumerated_specification  */
    S_enumerated_list(171),        /* enumerated_list  */
    S_array_spec_init(172),        /* array_spec_init  */
    S_array_specification(173),    /* array_specification  */
    S_subrange_list(174),          /* subrange_list  */
    S_non_generic_type_name(175),  /* non_generic_type_name  */
    S_array_initialization(176),   /* array_initialization  */
    S_array_initial_elements_list(177), /* array_initial_elements_list  */
    S_array_initial_elements(178), /* array_initial_elements  */
    S_array_initial_element(179),  /* array_initial_element  */
    S_structure_initialization(180), /* structure_initialization  */
    S_structure_element_initialization_list(181), /* structure_element_initialization_list  */
    S_structure_element_initialization(182), /* structure_element_initialization  */
    S_structure_element_type(183), /* structure_element_type  */
    S_initialized_variable(184),   /* initialized_variable  */
    S_initialized_constant(185),   /* initialized_constant  */
    S_initialized_enumerate(186),  /* initialized_enumerate  */
    S_initialized_structure(187),  /* initialized_structure  */
    S_fb_name_decl(188),           /* fb_name_decl  */
    S_identifier_list(189),        /* identifier_list  */
    S_standard_function_block_name(190), /* standard_function_block_name  */
    S_string_specification(191),   /* string_specification  */
    S_type_string(192),            /* type_string  */
    S_opt_data_type_declaration(193), /* opt_data_type_declaration  */
    S_data_type_declaration(194),  /* data_type_declaration  */
    S_type_declaration_list(195),  /* type_declaration_list  */
    S_type_declaration(196),       /* type_declaration  */
    S_specification_list(197),     /* specification_list  */
    S_structure_specification(198), /* structure_specification  */
    S_structure_declaration(199),  /* structure_declaration  */
    S_structure_element_declaration_list(200), /* structure_element_declaration_list  */
    S_structure_element_declaration(201), /* structure_element_declaration  */
    S_specification_sublist(202);  /* specification_sublist  */


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
      SymbolKind.S_simple_specification,
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
  "simple_spec_init", "simple_specification", "elementary_type_name",
  "numeric_type_name", "integer_type_name", "signed_integer_type_name",
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
    /* "src/main/java/parser/Parser.y":327  */
    { ((List<Publisher>)(yystack.valueAt (2))).publish() };
  break;


  case 103: /* var_init_decl_list: var_init_decl  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":350  */
                  { yyval = new ArrayList<>(); yyval.add(((Publisher)(yystack.valueAt (0)))); };
  break;


  case 104: /* var_init_decl_list: var_init_decl_list ';' var_init_decl  */
  if (yyn == 104)
    /* "src/main/java/parser/Parser.y":351  */
                                           { yyval.add(((Publisher)(yystack.valueAt (0)))); };
  break;


  case 105: /* var_init_decl: identifier_list ':' type_specification  */
  if (yyn == 105)
    /* "src/main/java/parser/Parser.y":356  */
                                           { yyval = null; };
  break;


  case 106: /* var_init_decl: identifier_list ':' spec_init_type  */
  if (yyn == 106)
    /* "src/main/java/parser/Parser.y":357  */
                                         {  yyval = new VariableDeclaration(this.symbolTable, ((List<String>)(yystack.valueAt (2))), ((LexemeInfoBuilder)(yystack.valueAt (0)))); };
  break;


  case 107: /* var_init_decl: fb_name_decl  */
  if (yyn == 107)
    /* "src/main/java/parser/Parser.y":358  */
                   { yyval = null; };
  break;


  case 116: /* spec_init_type: simple_spec_init  */
  if (yyn == 116)
    /* "src/main/java/parser/Parser.y":377  */
                     { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 117: /* spec_init_type: subrange_spec_init  */
  if (yyn == 117)
    /* "src/main/java/parser/Parser.y":378  */
                         { yyval = null; };
  break;


  case 118: /* spec_init_type: enumerated_spec_init  */
  if (yyn == 118)
    /* "src/main/java/parser/Parser.y":379  */
                           { yyval = null; };
  break;


  case 119: /* simple_spec_init: simple_specification  */
  if (yyn == 119)
    /* "src/main/java/parser/Parser.y":384  */
    {
        yyval = new LexemeInfoBuilder();
        yyval.use(Use.VARIABLE).type(((ElementaryType)(yystack.valueAt (0))));
    };
  break;


  case 120: /* simple_spec_init: simple_specification ASSIGN_OP constant  */
  if (yyn == 120)
    /* "src/main/java/parser/Parser.y":389  */
    {
        yyval = new LexemeInfoBuilder();
        yyval.use(Use.VARIABLE).type(((ElementaryType)(yystack.valueAt (2)))).value(((String)(yystack.valueAt (0))));
    };
  break;


  case 121: /* simple_spec_init: initialized_constant  */
  if (yyn == 121)
    /* "src/main/java/parser/Parser.y":393  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 122: /* simple_specification: elementary_type_name  */
  if (yyn == 122)
    /* "src/main/java/parser/Parser.y":397  */
                                    { yyval = ((ElementaryType)(yystack.valueAt (0))) };
  break;


  case 123: /* elementary_type_name: numeric_type_name  */
  if (yyn == 123)
    /* "src/main/java/parser/Parser.y":403  */
                                    { yyval = ((ElementaryType)(yystack.valueAt (0))) };
  break;


  case 124: /* elementary_type_name: date_type_name  */
  if (yyn == 124)
    /* "src/main/java/parser/Parser.y":404  */
                                    { yyval = ((ElementaryType)(yystack.valueAt (0))) };
  break;


  case 125: /* elementary_type_name: bit_string_type_name  */
  if (yyn == 125)
    /* "src/main/java/parser/Parser.y":405  */
                                    { yyval = ((ElementaryType)(yystack.valueAt (0))) };
  break;


  case 126: /* numeric_type_name: integer_type_name  */
  if (yyn == 126)
    /* "src/main/java/parser/Parser.y":409  */
                                    { yyval = ((ElementaryType)(yystack.valueAt (0))) };
  break;


  case 127: /* numeric_type_name: real_type_name  */
  if (yyn == 127)
    /* "src/main/java/parser/Parser.y":410  */
                                    { yyval = ((ElementaryType)(yystack.valueAt (0))) };
  break;


  case 128: /* integer_type_name: signed_integer_type_name  */
  if (yyn == 128)
    /* "src/main/java/parser/Parser.y":414  */
                                    { yyval = ((ElementaryType)(yystack.valueAt (0))) };
  break;


  case 129: /* integer_type_name: unsigned_integer_type_name  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":415  */
                                    { yyval = ((ElementaryType)(yystack.valueAt (0))) };
  break;


  case 130: /* signed_integer_type_name: SINT  */
  if (yyn == 130)
    /* "src/main/java/parser/Parser.y":419  */
           { yyval = ElementaryType.SINT };
  break;


  case 131: /* signed_integer_type_name: INT  */
  if (yyn == 131)
    /* "src/main/java/parser/Parser.y":420  */
           { yyval = ElementaryType.INT  };
  break;


  case 132: /* signed_integer_type_name: DINT  */
  if (yyn == 132)
    /* "src/main/java/parser/Parser.y":421  */
           { yyval = ElementaryType.DINT };
  break;


  case 133: /* signed_integer_type_name: LINT  */
  if (yyn == 133)
    /* "src/main/java/parser/Parser.y":422  */
           { yyval = ElementaryType.LINT };
  break;


  case 134: /* unsigned_integer_type_name: USINT  */
  if (yyn == 134)
    /* "src/main/java/parser/Parser.y":426  */
            { yyval = ElementaryType.USINT };
  break;


  case 135: /* unsigned_integer_type_name: UINT  */
  if (yyn == 135)
    /* "src/main/java/parser/Parser.y":427  */
            { yyval = ElementaryType.UINT  };
  break;


  case 136: /* unsigned_integer_type_name: UDINT  */
  if (yyn == 136)
    /* "src/main/java/parser/Parser.y":428  */
            { yyval = ElementaryType.UDINT };
  break;


  case 137: /* unsigned_integer_type_name: ULINT  */
  if (yyn == 137)
    /* "src/main/java/parser/Parser.y":429  */
            { yyval = ElementaryType.ULINT };
  break;


  case 138: /* real_type_name: REAL  */
  if (yyn == 138)
    /* "src/main/java/parser/Parser.y":433  */
            { yyval = ElementaryType.REAL  };
  break;


  case 139: /* real_type_name: LREAL  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":434  */
            { yyval = ElementaryType.LREAL };
  break;


  case 140: /* date_type_name: TIME  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":438  */
                    { yyval = ElementaryType.TIME          };
  break;


  case 141: /* date_type_name: DATE  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":439  */
                    { yyval = ElementaryType.DATE          };
  break;


  case 142: /* date_type_name: TIME_OF_DAY  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":440  */
                    { yyval = ElementaryType.TIME_OF_DAY   };
  break;


  case 143: /* date_type_name: DATE_AND_TIME  */
  if (yyn == 143)
    /* "src/main/java/parser/Parser.y":441  */
                    { yyval = ElementaryType.DATE_AND_TIME };
  break;


  case 148: /* number: TRUE  */
  if (yyn == 148)
    /* "src/main/java/parser/Parser.y":455  */
    {
        this.symbolTable("TRUE" , new LexemeInfoBuilder().use(LITERAL).type(BOOL).build());
        yyval = "TRUE";
    };
  break;


  case 149: /* number: FALSE  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":460  */
    {
        this.symbolTable("FALSE" , new LexemeInfoBuilder().use(LITERAL).type(BOOL).build());
        yyval = "FALSE";
    };
  break;


  case 150: /* number: number_prefix NUMERIC_LITERAL  */
  if (yyn == 150)
    /* "src/main/java/parser/Parser.y":465  */
    {
        // todo: hacer conversion de esta constante en codigo
        yyval = "";
    };
  break;


  case 154: /* time: date_type_name '#' TIME_LITERAL  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":478  */
                                    {
        // Accion semantica que verifica que prefix es del mismo tipo que time_literal
    };
  break;


  case 155: /* bit_string_type_name: BYTE  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":484  */
            { yyval = ElementaryType.BYTE  };
  break;


  case 156: /* bit_string_type_name: WORD  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":485  */
            { yyval = ElementaryType.WORD  };
  break;


  case 157: /* bit_string_type_name: DWORD  */
  if (yyn == 157)
    /* "src/main/java/parser/Parser.y":486  */
            { yyval = ElementaryType.DWORD };
  break;


  case 158: /* bit_string_type_name: LWORD  */
  if (yyn == 158)
    /* "src/main/java/parser/Parser.y":487  */
            { yyval = ElementaryType.LWORD };
  break;


  case 199: /* initialized_constant: IDENTIFIER ASSIGN_OP constant  */
  if (yyn == 199)
    /* "src/main/java/parser/Parser.y":591  */
    {
        yyval = new LexemeInfoBuilder();
        yyval.use(Use.VARIABLE).type(ElementaryType.CUSTOM).customType(((String)(yystack.valueAt (2)))).value(((String)(yystack.valueAt (0))));
    };
  break;


  case 204: /* identifier_list: IDENTIFIER  */
  if (yyn == 204)
    /* "src/main/java/parser/Parser.y":612  */
               { yyval = new ArrayList<String>(); };
  break;


  case 205: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 205)
    /* "src/main/java/parser/Parser.y":613  */
                                     { yyval.add(); };
  break;



/* "src/main/java/parser/Parser.java":1312  */

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

  private static final short yypact_ninf_ = -311;
  private static final short yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      32,    26,   108,   111,  -311,    52,    13,    76,  -311,   122,
    -311,    22,  -311,    78,  -311,  -311,   163,   167,  -311,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,   149,   170,
    -311,  -311,   196,  -311,  -311,    83,  -311,  -311,  -311,  -311,
    -311,  -311,   197,  -311,   198,  -311,   199,  -311,  -311,  -311,
    -311,  -311,   -28,  -311,  -311,  -311,  -311,     3,   157,    14,
     130,   231,   164,   158,    80,   424,   164,   168,   207,   160,
     181,   171,   126,   126,  -311,   219,  -311,  -311,   271,  -311,
     191,  -311,   187,  -311,  -311,  -311,  -311,   235,   192,   193,
     194,  -311,  -311,   204,  -311,   200,  -311,   234,   201,   223,
     243,   244,  -311,  -311,   211,  -311,   226,   392,  -311,  -311,
     227,  -311,  -311,   246,   246,   261,  -311,   313,   314,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,   278,   279,   120,  -311,
    -311,  -311,  -311,  -311,  -311,   240,  -311,  -311,   245,   164,
     257,  -311,   237,  -311,   285,   253,  -311,  -311,   -37,  -311,
    -311,  -311,   306,  -311,   267,  -311,  -311,    35,   268,  -311,
     246,  -311,   311,  -311,   346,  -311,    73,   235,  -311,  -311,
    -311,  -311,   373,   315,  -311,   123,   392,  -311,   277,    -7,
     317,   195,    -6,   276,   347,   319,  -311,   352,  -311,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,  -311,    72,   283,  -311,
    -311,  -311,  -311,  -311,   167,  -311,   124,  -311,  -311,  -311,
    -311,   320,  -311,  -311,    -2,   323,    30,  -311,   354,   331,
    -311,   361,  -311,   353,  -311,  -311,  -311,  -311,   309,  -311,
     370,  -311,  -311,   332,   347,   368,   372,  -311,   387,  -311,
     307,   447,   400,   347,   371,   417,   448,   434,    20,  -311,
    -311,   394,   329,   395,  -311,   396,  -311,  -311,   435,   403,
     493,   119,   423,   488,  -311,   425,   430,   -21,  -311,  -311,
    -311,   442,   443,  -311,  -311,   447,   190,   486,   521,  -311,
    -311,  -311,  -311,    90,   444,  -311,    34,  -311,   449,  -311,
     447,   447,   450,  -311,  -311,  -311,  -311,  -311,   451,   156,
    -311,  -311,  -311,   452,    86,    48,  -311,  -311,  -311,  -311,
    -311,   453,   454,   455,  -311,  -311,   458,  -311,  -311,  -311,
    -311,  -311,   459,  -311,   464,  -311,  -311,  -311,  -311,  -311,
    -311,   446,   518,   -15,   -14,   103,   -15,   524,   182,  -311,
     522,  -311,     5,   -13,     4,  -311,   461,   504,  -311,   507,
    -311,   103,   182,   103,   182,  -311,   525,    17,  -311,  -311,
    -311,  -311,  -311,   509,   351,   510,   469,  -311,  -311,  -311,
     529,  -311,   513,  -311
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
     213,     0,     0,     0,   214,     0,     0,     0,     1,     0,
       2,     0,   215,     0,   216,     4,     0,     0,   211,   212,
     155,   156,   157,   158,   140,   142,   141,   143,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,     0,     0,
     219,   116,   119,   122,   123,   126,   128,   129,   127,   124,
     125,   117,   159,   118,     0,   220,   172,   163,   121,   164,
     223,   222,   207,   218,   221,   224,   217,     8,     0,     0,
       0,     0,     0,   169,     0,     0,     0,     0,     0,     0,
       0,     0,    98,    98,     5,    12,     7,     6,     0,   225,
       0,   226,   198,   148,   149,   147,   144,     0,     0,     0,
       0,   199,   146,     0,   145,     0,   201,     0,   176,     0,
       0,     0,   167,   120,     0,   160,   165,     0,   173,   209,
       0,    99,   100,     0,     0,   101,     9,     0,    15,    10,
     229,   230,   231,   232,   228,   227,     0,     0,     0,   190,
     193,   192,   194,   151,   152,     0,   150,   153,     0,     0,
       0,   168,   171,   161,     0,   147,   186,   188,     0,   180,
     182,   187,   208,   204,     0,   103,   107,     0,     0,   102,
       0,     3,     0,    13,    42,   200,     0,     0,   189,   154,
     162,   177,     0,     0,   166,     0,     0,   179,     0,     0,
       0,     0,     0,     0,     0,     0,    16,    88,   196,   197,
     195,   191,   174,   178,   126,   175,   170,     0,     0,   181,
     210,    96,   104,   205,   108,   206,   113,   105,   106,   112,
     111,   202,   110,    95,     0,     0,     0,    20,    40,     0,
      43,    11,   183,     0,   185,   114,   115,   109,     0,    97,
       0,    14,    21,     0,    18,    46,     0,    89,     0,   203,
       0,     0,     0,    19,     0,    56,    48,     0,     0,    91,
     184,     0,     0,     0,    24,    25,    27,    26,     0,     0,
       0,     0,     0,     0,    57,     0,     0,     0,    90,    92,
      22,     0,     0,    23,    28,     0,     0,     0,     0,    50,
      51,    52,    47,     0,     0,    65,     0,    45,     0,    93,
       0,     0,     0,    32,    33,    34,    35,    36,     0,     0,
      17,    60,    59,     0,     0,     0,    53,    54,    55,    49,
      94,     0,     0,     0,    31,    39,     0,    38,    58,    62,
      63,    64,     0,    44,     0,    66,    30,    29,    41,    37,
      61,     0,     0,     0,     0,    73,     0,     0,    73,    80,
      79,    78,     0,     0,     0,    72,     0,     0,    71,     0,
      82,    73,    73,    73,    73,    81,    85,    68,    83,    74,
      76,    75,    77,     0,     0,     0,     0,    84,    70,    69,
      87,    67,     0,    86
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,
    -311,  -311,  -311,   316,  -200,  -311,  -311,  -311,   293,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,   213,
    -242,  -310,  -311,  -311,  -311,  -311,  -311,   303,  -311,  -311,
    -311,   479,  -311,  -103,  -164,  -311,  -311,   -81,  -311,  -311,
     381,  -311,   -11,  -311,  -311,   -10,    -9,   -65,  -246,  -311,
    -311,    -8,  -311,  -311,   489,  -311,  -311,  -311,   -79,  -311,
     415,  -311,   -60,  -311,   380,   382,   -63,  -311,   391,  -311,
     -75,   -74,   -73,   -77,  -311,  -311,  -311,   -76,  -311,  -311,
    -311,  -311,   563,  -311,  -311,  -311,  -311,   501,  -311
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,    10,    67,    84,    85,   126,   127,   128,   173,
     174,   196,   252,   226,   227,   263,   264,   265,   266,   270,
     308,   288,   326,   244,   197,   230,   255,   256,   276,   292,
     319,   273,   274,   313,   295,   332,   315,   335,   376,   347,
     355,   348,   349,   367,   231,   247,   258,   259,    86,    87,
     129,   123,   170,   164,   165,   217,   237,    40,    41,    42,
      43,    44,    98,    46,    47,    99,   100,   156,   102,   103,
     104,   105,    51,    52,   108,    53,    54,    74,    55,    56,
     109,   205,   157,   158,   159,   160,   161,   138,   139,   200,
      57,    58,    59,    60,   166,   167,   221,    61,    62,     3,
       4,     6,     7,    63,    64,    65,    69,    70,   134
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
      45,    48,    49,    50,   267,   268,   101,   130,   106,   131,
     113,   132,   133,   344,   344,   344,   282,    12,    80,   118,
      89,   168,   140,   141,   142,   212,   242,    16,   212,   345,
     350,   361,   344,   359,   351,     1,   278,   163,   163,   302,
     241,   374,   163,   362,   364,   211,   223,   225,   363,   360,
     239,   186,    82,   242,   321,   322,   187,     5,    68,    83,
     212,   298,   333,   327,    81,   299,    17,   193,   257,   334,
       5,   316,   346,   346,   346,   317,   318,    45,    48,    49,
      50,    18,    19,    20,    21,    22,    23,    24,    25,    26,
      27,   346,    28,    29,    30,    31,    32,    33,    34,    35,
      36,    37,   140,   141,   142,   375,   358,    38,     8,    39,
     218,   101,   219,   199,   220,   222,   198,    92,     9,   369,
     370,   371,   372,   190,   329,   191,   330,   311,   379,   331,
     352,   312,   353,   354,    20,    21,    22,    23,    24,    25,
      26,    27,    11,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    93,    94,    95,    96,   289,   290,   291,
      97,   232,    14,   233,    66,   117,    15,   207,   111,   112,
      76,   204,    48,    49,    50,   249,   121,   122,   235,   236,
      45,    48,    49,    50,    20,    21,    22,    23,    24,    25,
      26,    27,   325,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    93,    94,    95,    96,    68,   177,   178,
      97,   353,   354,    71,    73,   117,    91,    20,    21,    22,
      23,   303,   304,   305,   306,   307,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,    93,    94,    95,   214,
     215,    72,    75,    77,    78,    79,   107,    88,   216,   110,
     115,   116,   117,   120,    18,    19,    20,    21,    22,    23,
      24,    25,    26,    27,   119,    28,    29,    30,    31,    32,
      33,    34,    35,    36,    37,    92,   125,   135,   136,   137,
      38,   148,    39,   143,   144,   145,   146,   151,   152,   149,
     163,   147,    20,    21,    22,    23,    24,    25,    26,    27,
     153,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    93,    94,    95,    96,    17,   150,   154,    97,   169,
     162,   171,   175,   172,   179,   176,   182,   180,   183,   184,
      18,    19,    20,    21,    22,    23,    24,    25,    26,    27,
     185,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,   261,   188,   189,   192,   194,    38,   195,    39,   206,
     210,   213,   224,   228,   225,   229,   238,   240,    20,    21,
      22,    23,   234,   281,   243,   245,   246,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    93,    94,    95,
      20,    21,    22,    23,   262,   378,    97,   248,   254,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    93,
      94,    95,    20,    21,    22,    23,   250,   202,   269,   251,
     257,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    93,    94,    95,    20,    21,    22,    23,    24,    25,
      26,    27,   272,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    20,    21,    22,    23,    24,    25,    26,
      27,   271,    28,    29,    30,    31,    32,    33,    34,    35,
      36,    37,    93,    94,   155,    96,   260,   275,   277,    97,
     280,   283,   285,   262,   117,    20,    21,    22,    23,    24,
      25,    26,    27,   286,    28,    29,    30,    31,    32,    33,
      34,    35,    36,    37,    93,    94,    95,    96,    20,    21,
      22,    23,   287,   293,   294,   296,   297,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    93,    94,    95,
     300,   301,   309,   310,   314,   320,   342,   324,   328,   323,
     343,   338,   336,   337,   339,   340,   341,   357,   366,   352,
     365,   368,   373,   377,   380,   381,   382,   383,   284,   356,
     253,   279,   124,   203,   181,   114,   209,   208,   201,    13,
      90
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      11,    11,    11,    11,   250,   251,    71,    88,    71,    88,
      75,    88,    88,    28,    28,    28,   262,     4,    46,    79,
       6,   124,    97,    97,    97,   189,   226,     5,   192,    44,
      44,    44,    28,    28,   344,     3,    16,    44,    44,   285,
      10,    24,    44,   353,   354,    52,    52,    17,    44,    44,
      52,    88,    49,   253,   300,   301,    93,    44,    44,    56,
     224,    82,    14,   309,    92,    86,    44,   170,    48,    21,
      44,    37,    87,    87,    87,    41,    42,    88,    88,    88,
      88,    59,    60,    61,    62,    63,    64,    65,    66,    67,
      68,    87,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,   177,   177,   177,    88,   348,    85,     0,    87,
     191,   176,   191,   176,   191,   191,   176,    44,     7,   361,
     362,   363,   364,    88,    38,    90,    40,    37,   374,    43,
      27,    41,    29,    30,    61,    62,    63,    64,    65,    66,
      67,    68,    90,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    38,    39,    40,
      87,    89,    86,    91,    86,    92,    44,    44,    88,    89,
      87,   182,   182,   182,   182,   238,    50,    51,    54,    55,
     191,   191,   191,   191,    61,    62,    63,    64,    65,    66,
      67,    68,    36,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    44,    88,    89,
      87,    29,    30,    46,    44,    92,    86,    61,    62,    63,
      64,    31,    32,    33,    34,    35,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    82,    44,
      45,    92,    46,    46,    46,    46,    82,    90,    53,    91,
      82,    44,    92,    82,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68,    83,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    44,    57,    86,    91,    44,
      85,    47,    87,    91,    91,    91,    82,    44,    44,    88,
      44,    91,    61,    62,    63,    64,    65,    66,    67,    68,
      89,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    44,    93,    91,    87,    58,
      93,     8,    44,     9,    84,    46,    69,    82,    91,    44,
      59,    60,    61,    62,    63,    64,    65,    66,    67,    68,
      87,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    44,    46,    86,    86,    44,    85,    11,    87,    44,
      83,    44,    86,    44,    17,    13,    46,    44,    61,    62,
      63,    64,    89,    44,    20,    44,    15,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      61,    62,    63,    64,    87,    44,    87,    44,    30,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    80,
      81,    82,    61,    62,    63,    64,    46,    44,    18,    87,
      48,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    61,    62,    63,    64,    65,    66,
      67,    68,    25,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    61,    62,    63,    64,    65,    66,    67,
      68,    90,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    80,    81,    82,    83,    89,    29,    44,    87,
      86,    86,    47,    87,    92,    61,    62,    63,    64,    65,
      66,    67,    68,    90,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    82,    83,    61,    62,
      63,    64,    19,    90,    26,    90,    86,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      88,    88,    46,    12,    90,    86,    90,    86,    86,    89,
      22,    86,    89,    89,    86,    86,    82,    23,    44,    27,
      89,    44,    27,    44,    44,    86,    27,    44,   265,   346,
     244,   258,    83,   182,   149,    76,   186,   185,   177,     6,
      69
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,    95,   193,   194,    44,   195,   196,     0,     7,
      96,    90,     4,   196,    86,    44,     5,    44,    59,    60,
      61,    62,    63,    64,    65,    66,    67,    68,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    85,    87,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     165,   166,   167,   169,   170,   172,   173,   184,   185,   186,
     187,   191,   192,   197,   198,   199,    86,    97,    44,   200,
     201,    46,    92,    44,   171,    46,    87,    46,    46,    46,
      46,    92,    49,    56,    98,    99,   142,   143,    90,     6,
     201,    86,    44,    80,    81,    82,    83,    87,   156,   159,
     160,   161,   162,   163,   164,   165,   180,    82,   168,   174,
      91,    88,    89,   161,   168,    82,    44,    92,   176,    83,
      82,    50,    51,   145,   145,    57,   100,   101,   102,   144,
     151,   172,   187,   191,   202,    86,    91,    44,   181,   182,
     184,   185,   186,    91,    91,    91,    82,    91,    47,    88,
      93,    44,    44,    89,    91,    82,   161,   176,   177,   178,
     179,   180,    93,    44,   147,   148,   188,   189,   147,    58,
     146,     8,     9,   103,   104,    44,    46,    88,    89,    84,
      82,   174,    69,    91,    44,    87,    88,    93,    46,    86,
      88,    90,    86,   147,    44,    11,   105,   118,   176,   180,
     183,   182,    44,   154,   156,   175,    44,    44,   179,   178,
      83,    52,   148,    44,    44,    45,    53,   149,   151,   172,
     187,   190,   191,    52,    86,    17,   107,   108,    44,    13,
     119,   138,    89,    91,    89,    54,    55,   150,    46,    52,
      44,    10,   108,    20,   117,    44,    15,   139,    44,   180,
      46,    87,   106,   107,    30,   120,   121,    48,   140,   141,
      89,    44,    87,   109,   110,   111,   112,   162,   162,    18,
     113,    90,    25,   125,   126,    29,   122,    44,    16,   141,
      86,    44,   162,    86,   112,    47,    90,    19,   115,    38,
      39,    40,   123,    90,    26,   128,    90,    86,    82,    86,
      88,    88,   162,    31,    32,    33,    34,    35,   114,    46,
      12,    37,    41,   127,    90,   130,    37,    41,    42,   124,
      86,   162,   162,    89,    86,    36,   116,   162,    86,    38,
      40,    43,   129,    14,    21,   131,    89,    89,    86,    86,
      86,    82,    90,    22,    28,    44,    87,   133,   135,   136,
      44,   135,    27,    29,    30,   134,   133,    23,   134,    28,
      44,    44,   135,    44,   135,    89,    44,   137,    44,   134,
     134,   134,   134,    27,    24,    88,   132,    44,    44,   162,
      44,    86,    27,    44
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
     152,   152,   153,   154,   154,   154,   155,   155,   156,   156,
     157,   157,   157,   157,   158,   158,   158,   158,   159,   159,
     160,   160,   160,   160,   161,   161,   161,   162,   162,   162,
     162,   163,   163,   163,   164,   165,   165,   165,   165,   166,
     166,   167,   168,   169,   169,   169,   169,   170,   171,   171,
     171,   171,   172,   172,   173,   173,   174,   174,   175,   176,
     177,   177,   178,   178,   178,   178,   179,   179,   179,   180,
     181,   181,   182,   182,   182,   182,   183,   183,   184,   185,
     186,   187,   188,   188,   189,   189,   190,   191,   191,   191,
     191,   192,   192,   193,   193,   194,   195,   195,   196,   197,
     197,   197,   197,   198,   198,   199,   200,   200,   201,   202,
     202,   202,   202
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
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       2,     2,     2,     2,     3,     1,     1,     1,     1,     1,
       3,     4,     3,     1,     1,     3,     5,     3,     3,     1,
       5,     3,     1,     3,     6,     6,     1,     3,     1,     3,
       1,     3,     1,     4,     6,     4,     1,     1,     1,     3,
       1,     3,     1,     1,     1,     3,     1,     1,     3,     3,
       5,     3,     3,     5,     1,     3,     1,     1,     4,     3,
       6,     1,     1,     0,     1,     3,     2,     3,     3,     1,
       1,     1,     1,     1,     1,     3,     2,     3,     3,     1,
       1,     1,     1
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


  private static final int YYLAST_ = 570;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 8;
  private static final int YYNTOKENS_ = 94;


}
/* "src/main/java/parser/Parser.y":685  */
