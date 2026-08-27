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
    S_fb_io_var_declarations_list(96), /* fb_io_var_declarations_list  */
    S_fb_io_var_declarations(97),  /* fb_io_var_declarations  */
    S_other_var_declarations_list(98), /* other_var_declarations_list  */
    S_other_var_declarations(99),  /* other_var_declarations  */
    S_function_block_body(100),    /* function_block_body  */
    S_fuzzify_block_list(101),     /* fuzzify_block_list  */
    S_fuzzify_block(102),          /* fuzzify_block  */
    S_defuzzify_block_list(103),   /* defuzzify_block_list  */
    S_defuzzify_block(104),        /* defuzzify_block  */
    S_opt_linguistic_term_list(105), /* opt_linguistic_term_list  */
    S_linguistic_term_list(106),   /* linguistic_term_list  */
    S_linguistic_term(107),        /* linguistic_term  */
    S_membership_function(108),    /* membership_function  */
    S_singleton(109),              /* singleton  */
    S_point_list(110),             /* point_list  */
    S_point(111),                  /* point  */
    S_defuzzification_method(112), /* defuzzification_method  */
    S_defuzz_method(113),          /* defuzz_method  */
    S_default_value(114),          /* default_value  */
    S_default_val(115),            /* default_val  */
    S_opt_range(116),              /* opt_range  */
    S_rule_block_list(117),        /* rule_block_list  */
    S_rule_block(118),             /* rule_block  */
    S_operator_definition(119),    /* operator_definition  */
    S_opt_operator_or(120),        /* opt_operator_or  */
    S_operator_and_opt(121),       /* operator_and_opt  */
    S_or_type(122),                /* or_type  */
    S_and_type(123),               /* and_type  */
    S_activation_method_opt(124),  /* activation_method_opt  */
    S_activation_method(125),      /* activation_method  */
    S_act_type(126),               /* act_type  */
    S_accumulation_method(127),    /* accumulation_method  */
    S_accu_type(128),              /* accu_type  */
    S_rule_list(129),              /* rule_list  */
    S_rule(130),                   /* rule  */
    S_opt_weighting(131),          /* opt_weighting  */
    S_condition(132),              /* condition  */
    S_condition_tail(133),         /* condition_tail  */
    S_x(134),                      /* x  */
    S_subcondition(135),           /* subcondition  */
    S_conclusion_list(136),        /* conclusion_list  */
    S_option_block_list(137),      /* option_block_list  */
    S_option_block(138),           /* option_block  */
    S_pragma_list(139),            /* pragma_list  */
    S_pragma(140),                 /* pragma  */
    S_output_declarations(141),    /* output_declarations  */
    S_input_declarations(142),     /* input_declarations  */
    S_var_declarations(143),       /* var_declarations  */
    S_var_retain_spec(144),        /* var_retain_spec  */
    S_var_constant_spec(145),      /* var_constant_spec  */
    S_var_init_decl_list(146),     /* var_init_decl_list  */
    S_var_init_decl(147),          /* var_init_decl  */
    S_unified_specification(148),  /* unified_specification  */
    S_custom_spec_init(149),       /* custom_spec_init  */
    S_opt_edge(150),               /* opt_edge  */
    S_simple_spec_init(151),       /* simple_spec_init  */
    S_elementary_type_name(152),   /* elementary_type_name  */
    S_numeric_type_name(153),      /* numeric_type_name  */
    S_integer_type_name(154),      /* integer_type_name  */
    S_signed_integer_type_name(155), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(156), /* unsigned_integer_type_name  */
    S_real_type_name(157),         /* real_type_name  */
    S_date_type_name(158),         /* date_type_name  */
    S_constant(159),               /* constant  */
    S_number(160),                 /* number  */
    S_number_prefix(161),          /* number_prefix  */
    S_time(162),                   /* time  */
    S_bit_string_type_name(163),   /* bit_string_type_name  */
    S_subrange_spec_init(164),     /* subrange_spec_init  */
    S_subrange_specification(165), /* subrange_specification  */
    S_subrange(166),               /* subrange  */
    S_enumerated_spec_init(167),   /* enumerated_spec_init  */
    S_enumerated_specification(168), /* enumerated_specification  */
    S_enumerated_list(169),        /* enumerated_list  */
    S_array_spec_init(170),        /* array_spec_init  */
    S_array_specification(171),    /* array_specification  */
    S_subrange_list(172),          /* subrange_list  */
    S_non_generic_type_name(173),  /* non_generic_type_name  */
    S_array_initialization(174),   /* array_initialization  */
    S_array_initial_elements_list(175), /* array_initial_elements_list  */
    S_array_initial_elements(176), /* array_initial_elements  */
    S_array_initial_element(177),  /* array_initial_element  */
    S_structure_initialization(178), /* structure_initialization  */
    S_structure_element_initialization_list(179), /* structure_element_initialization_list  */
    S_structure_element_initialization(180), /* structure_element_initialization  */
    S_structure_element_type(181), /* structure_element_type  */
    S_initialized_constant(182),   /* initialized_constant  */
    S_initialized_identifier(183), /* initialized_identifier  */
    S_opt_scope_and_value(184),    /* opt_scope_and_value  */
    S_initialized_structure(185),  /* initialized_structure  */
    S_fb_name_decl(186),           /* fb_name_decl  */
    S_identifier_list(187),        /* identifier_list  */
    S_standard_function_block_name(188), /* standard_function_block_name  */
    S_string_spec_init(189),       /* string_spec_init  */
    S_type_string(190),            /* type_string  */
    S_opt_data_type_declaration(191), /* opt_data_type_declaration  */
    S_data_type_declaration(192),  /* data_type_declaration  */
    S_type_declaration_list(193),  /* type_declaration_list  */
    S_type_declaration(194),       /* type_declaration  */
    S_specification_list(195),     /* specification_list  */
    S_structure_declaration(196),  /* structure_declaration  */
    S_structure_element_declaration_list(197), /* structure_element_declaration_list  */
    S_structure_element_declaration(198), /* structure_element_declaration  */
    S_specification_sublist(199);  /* specification_sublist  */


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
  "LINT", "USINT", "UINT", "UDINT", "ULINT", "REAL", "LREAL",
  "NUMERIC_LITERAL", "STRING_LITERAL", "TIME_LITERAL", "BOOLEAN_LITERAL",
  "ARRAY", "';'", "'('", "','", "')'", "':'", "'#'", "'['", "']'",
  "$accept", "program", "function_block_declaration",
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
          case 95: /* output_declarations: VAR_OUTPUT var_retain_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 95)
    /* "src/main/java/parser/Parser.y":346  */
    { 
        Compound entries = new Compound(((List<Publisher>)(yystack.valueAt (2))));
        entries.source(Source.OUT).publish();
        yyval = entries;
    };
  break;


  case 96: /* input_declarations: VAR_INPUT var_retain_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 96)
    /* "src/main/java/parser/Parser.y":355  */
    { 
        Compound entries = new Compound(((List<Publisher>)(yystack.valueAt (2))));
        entries.source(Source.IN).publish();
        yyval = entries;
    };
  break;


  case 97: /* var_declarations: VAR var_constant_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 97)
    /* "src/main/java/parser/Parser.y":364  */
    { 
        Compound entries = new Compound(((List<Publisher>)(yystack.valueAt (2))));
        entries.source(Source.NONE).publish();
        yyval = entries;
    };
  break;


  case 103: /* var_init_decl_list: var_init_decl  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":384  */
    {
        List<Publisher> declarations = new ArrayList<>();
        declarations.add(((Publisher)(yystack.valueAt (0))));
        yyval = declarations;
    };
  break;


  case 104: /* var_init_decl_list: var_init_decl_list ';' var_init_decl  */
  if (yyn == 104)
    /* "src/main/java/parser/Parser.y":390  */
    { 
        ((List<Publisher>)(yystack.valueAt (2))).add(((Publisher)(yystack.valueAt (0))));
        yyval = ((List<Publisher>)(yystack.valueAt (2)));
    };
  break;


  case 105: /* var_init_decl: identifier_list ':' unified_specification  */
  if (yyn == 105)
    /* "src/main/java/parser/Parser.y":399  */
    {
        yyval = new Declaration(this.symbolTable, ((List<String>)(yystack.valueAt (2))), ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.VARIABLE));
    };
  break;


  case 106: /* var_init_decl: fb_name_decl  */
  if (yyn == 106)
    /* "src/main/java/parser/Parser.y":403  */
    {
        yyval = null;
    };
  break;


  case 107: /* unified_specification: custom_spec_init  */
  if (yyn == 107)
    /* "src/main/java/parser/Parser.y":411  */
    {
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).type(Type.SIMPLE);
    };
  break;


  case 108: /* unified_specification: BOOL opt_edge  */
  if (yyn == 108)
    /* "src/main/java/parser/Parser.y":414  */
                            { yyval = null; };
  break;


  case 109: /* unified_specification: simple_spec_init  */
  if (yyn == 109)
    /* "src/main/java/parser/Parser.y":415  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 110: /* unified_specification: subrange_spec_init  */
  if (yyn == 110)
    /* "src/main/java/parser/Parser.y":416  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 111: /* unified_specification: enumerated_spec_init  */
  if (yyn == 111)
    /* "src/main/java/parser/Parser.y":417  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 112: /* unified_specification: array_spec_init  */
  if (yyn == 112)
    /* "src/main/java/parser/Parser.y":418  */
                            { yyval = null; };
  break;


  case 113: /* unified_specification: string_spec_init  */
  if (yyn == 113)
    /* "src/main/java/parser/Parser.y":419  */
                            { yyval = null; };
  break;


  case 114: /* custom_spec_init: IDENTIFIER  */
  if (yyn == 114)
    /* "src/main/java/parser/Parser.y":425  */
    {
        yyval = new LexemeInfoBuilder()
                    .subtype(Subtype.CUSTOM)
                    .customType(((String)(yystack.valueAt (0))))
                    .initialValue(this.symbolTable.get(((String)(yystack.valueAt (0)))).initialValue);
    };
  break;


  case 115: /* custom_spec_init: initialized_constant  */
  if (yyn == 115)
    /* "src/main/java/parser/Parser.y":431  */
                                { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 116: /* custom_spec_init: initialized_structure  */
  if (yyn == 116)
    /* "src/main/java/parser/Parser.y":432  */
                                { yyval = null; };
  break;


  case 117: /* custom_spec_init: initialized_identifier  */
  if (yyn == 117)
    /* "src/main/java/parser/Parser.y":433  */
                                { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 121: /* simple_spec_init: elementary_type_name  */
  if (yyn == 121)
    /* "src/main/java/parser/Parser.y":444  */
    {
        yyval = new LexemeInfoBuilder().type(Type.SIMPLE).subtype(((Subtype)(yystack.valueAt (0))));
    };
  break;


  case 122: /* simple_spec_init: elementary_type_name ASSIGN_OP constant  */
  if (yyn == 122)
    /* "src/main/java/parser/Parser.y":448  */
    {
        yyval = new LexemeInfoBuilder().type(Type.SIMPLE).subtype(((Subtype)(yystack.valueAt (2)))).initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 123: /* elementary_type_name: numeric_type_name  */
  if (yyn == 123)
    /* "src/main/java/parser/Parser.y":456  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 124: /* elementary_type_name: date_type_name  */
  if (yyn == 124)
    /* "src/main/java/parser/Parser.y":457  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 125: /* elementary_type_name: bit_string_type_name  */
  if (yyn == 125)
    /* "src/main/java/parser/Parser.y":458  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 126: /* numeric_type_name: integer_type_name  */
  if (yyn == 126)
    /* "src/main/java/parser/Parser.y":462  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 127: /* numeric_type_name: real_type_name  */
  if (yyn == 127)
    /* "src/main/java/parser/Parser.y":463  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 128: /* integer_type_name: signed_integer_type_name  */
  if (yyn == 128)
    /* "src/main/java/parser/Parser.y":467  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 129: /* integer_type_name: unsigned_integer_type_name  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":468  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 130: /* signed_integer_type_name: SINT  */
  if (yyn == 130)
    /* "src/main/java/parser/Parser.y":472  */
           { yyval = Subtype.SINT; };
  break;


  case 131: /* signed_integer_type_name: INT  */
  if (yyn == 131)
    /* "src/main/java/parser/Parser.y":473  */
           { yyval = Subtype.INT;  };
  break;


  case 132: /* signed_integer_type_name: DINT  */
  if (yyn == 132)
    /* "src/main/java/parser/Parser.y":474  */
           { yyval = Subtype.DINT; };
  break;


  case 133: /* signed_integer_type_name: LINT  */
  if (yyn == 133)
    /* "src/main/java/parser/Parser.y":475  */
           { yyval = Subtype.LINT; };
  break;


  case 134: /* unsigned_integer_type_name: USINT  */
  if (yyn == 134)
    /* "src/main/java/parser/Parser.y":479  */
            { yyval = Subtype.USINT; };
  break;


  case 135: /* unsigned_integer_type_name: UINT  */
  if (yyn == 135)
    /* "src/main/java/parser/Parser.y":480  */
            { yyval = Subtype.UINT;  };
  break;


  case 136: /* unsigned_integer_type_name: UDINT  */
  if (yyn == 136)
    /* "src/main/java/parser/Parser.y":481  */
            { yyval = Subtype.UDINT; };
  break;


  case 137: /* unsigned_integer_type_name: ULINT  */
  if (yyn == 137)
    /* "src/main/java/parser/Parser.y":482  */
            { yyval = Subtype.ULINT; };
  break;


  case 138: /* real_type_name: REAL  */
  if (yyn == 138)
    /* "src/main/java/parser/Parser.y":486  */
            { yyval = Subtype.REAL;  };
  break;


  case 139: /* real_type_name: LREAL  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":487  */
            { yyval = Subtype.LREAL; };
  break;


  case 140: /* date_type_name: TIME  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":491  */
                    { yyval = Subtype.TIME;          };
  break;


  case 141: /* date_type_name: DATE  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":492  */
                    { yyval = Subtype.DATE;          };
  break;


  case 142: /* date_type_name: TIME_OF_DAY  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":493  */
                    { yyval = Subtype.TIME_OF_DAY;   };
  break;


  case 143: /* date_type_name: DATE_AND_TIME  */
  if (yyn == 143)
    /* "src/main/java/parser/Parser.y":494  */
                    { yyval = Subtype.DATE_AND_TIME; };
  break;


  case 144: /* constant: STRING_LITERAL  */
  if (yyn == 144)
    /* "src/main/java/parser/Parser.y":500  */
                    { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 145: /* constant: time  */
  if (yyn == 145)
    /* "src/main/java/parser/Parser.y":501  */
                    { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 146: /* constant: number  */
  if (yyn == 146)
    /* "src/main/java/parser/Parser.y":502  */
                    { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 149: /* number: number_prefix NUMERIC_LITERAL  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":509  */
    {
        // todo: hacer conversion de esta constante en codigo
        yyval = "";
    };
  break;


  case 153: /* time: date_type_name '#' TIME_LITERAL  */
  if (yyn == 153)
    /* "src/main/java/parser/Parser.y":522  */
                                    {
        //todo Accion semantica que verifica que prefix es del mismo tipo que time_literal
    };
  break;


  case 154: /* bit_string_type_name: BYTE  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":528  */
            { yyval = Subtype.BYTE;  };
  break;


  case 155: /* bit_string_type_name: WORD  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":529  */
            { yyval = Subtype.WORD;  };
  break;


  case 156: /* bit_string_type_name: DWORD  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":530  */
            { yyval = Subtype.DWORD; };
  break;


  case 157: /* bit_string_type_name: LWORD  */
  if (yyn == 157)
    /* "src/main/java/parser/Parser.y":531  */
            { yyval = Subtype.LWORD; };
  break;


  case 158: /* subrange_spec_init: subrange_specification  */
  if (yyn == 158)
    /* "src/main/java/parser/Parser.y":537  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 159: /* subrange_spec_init: subrange_specification ASSIGN_OP NUMERIC_LITERAL  */
  if (yyn == 159)
    /* "src/main/java/parser/Parser.y":538  */
                                                       { yyval = ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(((String)(yystack.valueAt (0)))); };
  break;


  case 160: /* subrange_specification: integer_type_name '(' subrange ')'  */
  if (yyn == 160)
    /* "src/main/java/parser/Parser.y":543  */
    { 
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (1))).subtype(((Subtype)(yystack.valueAt (3))));
    };
  break;


  case 161: /* subrange: NUMERIC_LITERAL RANGE_OP NUMERIC_LITERAL  */
  if (yyn == 161)
    /* "src/main/java/parser/Parser.y":550  */
    {
        // todo: hacer chequeo semantico y pasaje a valor (.value) en analisis lexico
        yyval = new LexemeInfoBuilder()
            .type(Type.SUBRANGE)
            .inferiorLimit(((String)(yystack.valueAt (2))))
            .superiorLimit(((String)(yystack.valueAt (0))))
            .initialValue(((String)(yystack.valueAt (2))));
    };
  break;


  case 162: /* enumerated_spec_init: enumerated_specification  */
  if (yyn == 162)
    /* "src/main/java/parser/Parser.y":562  */
    {
        yyval = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters(((List<String>)(yystack.valueAt (0))))
            .initialValue(((List<String>)(yystack.valueAt (0))).get(0));
    };
  break;


  case 163: /* enumerated_spec_init: enumerated_specification ASSIGN_OP opt_scope_and_value  */
  if (yyn == 163)
    /* "src/main/java/parser/Parser.y":569  */
    {
        yyval = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters(((List<String>)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 164: /* enumerated_specification: '(' enumerated_list ')'  */
  if (yyn == 164)
    /* "src/main/java/parser/Parser.y":578  */
                           { yyval = ((List<String>)(yystack.valueAt (1))); };
  break;


  case 165: /* enumerated_list: IDENTIFIER  */
  if (yyn == 165)
    /* "src/main/java/parser/Parser.y":583  */
    {
        List<String> enumerates = new ArrayList<String>();
        enumerates.add(((String)(yystack.valueAt (0))));
        yyval = enumerates;
    };
  break;


  case 166: /* enumerated_list: enumerated_list ',' IDENTIFIER  */
  if (yyn == 166)
    /* "src/main/java/parser/Parser.y":589  */
    {
        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 192: /* initialized_constant: IDENTIFIER ASSIGN_OP constant  */
  if (yyn == 192)
    /* "src/main/java/parser/Parser.y":658  */
    {
        yyval = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 193: /* initialized_identifier: IDENTIFIER ASSIGN_OP opt_scope_and_value  */
  if (yyn == 193)
    /* "src/main/java/parser/Parser.y":668  */
    {
        yyval = new LexemeInfoBuilder()
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 194: /* opt_scope_and_value: IDENTIFIER  */
  if (yyn == 194)
    /* "src/main/java/parser/Parser.y":678  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 195: /* opt_scope_and_value: IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 195)
    /* "src/main/java/parser/Parser.y":683  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 199: /* identifier_list: IDENTIFIER  */
  if (yyn == 199)
    /* "src/main/java/parser/Parser.y":700  */
    {
        List<String> identifiers = new ArrayList<String>();
        identifiers.add(((String)(yystack.valueAt (0))));
        yyval = identifiers;
    };
  break;


  case 200: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 200)
    /* "src/main/java/parser/Parser.y":705  */
                                     { ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0)))); yyval = ((List<String>)(yystack.valueAt (2))); };
  break;


  case 210: /* data_type_declaration: TYPE type_declaration_list END_TYPE  */
  if (yyn == 210)
    /* "src/main/java/parser/Parser.y":734  */
    {
        Publisher pub = new Compound(((List<Publisher>)(yystack.valueAt (1))));
        pub.publish();
        yyval = pub;
    };
  break;


  case 211: /* type_declaration_list: type_declaration ';'  */
  if (yyn == 211)
    /* "src/main/java/parser/Parser.y":743  */
    {
        List<Publisher> list = new ArrayList<>();
        list.add(((Publisher)(yystack.valueAt (1))));
        yyval = list;
    };
  break;


  case 212: /* type_declaration_list: type_declaration_list type_declaration ';'  */
  if (yyn == 212)
    /* "src/main/java/parser/Parser.y":749  */
    {
        ((List<Publisher>)(yystack.valueAt (2))).add(((Publisher)(yystack.valueAt (1))));
        yyval = ((List<Publisher>)(yystack.valueAt (2)));
    };
  break;


  case 213: /* type_declaration: IDENTIFIER ':' specification_list  */
  if (yyn == 213)
    /* "src/main/java/parser/Parser.y":757  */
    {
        List<String> element = new ArrayList<>();
        element.add(((String)(yystack.valueAt (2))));
        yyval = new Declaration(this.symbolTable, element, ((LexemeInfoBuilder)(yystack.valueAt (0))));
    };
  break;


  case 214: /* specification_list: custom_spec_init  */
  if (yyn == 214)
    /* "src/main/java/parser/Parser.y":765  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.TYPE); };
  break;


  case 215: /* specification_list: simple_spec_init  */
  if (yyn == 215)
    /* "src/main/java/parser/Parser.y":766  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.TYPE); };
  break;


  case 216: /* specification_list: enumerated_spec_init  */
  if (yyn == 216)
    /* "src/main/java/parser/Parser.y":767  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.TYPE); };
  break;


  case 217: /* specification_list: subrange_spec_init  */
  if (yyn == 217)
    /* "src/main/java/parser/Parser.y":768  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.TYPE); };
  break;


  case 218: /* specification_list: array_spec_init  */
  if (yyn == 218)
    /* "src/main/java/parser/Parser.y":769  */
                            { yyval = null; };
  break;


  case 219: /* specification_list: structure_declaration  */
  if (yyn == 219)
    /* "src/main/java/parser/Parser.y":770  */
                            { yyval = null; };
  break;


  case 220: /* specification_list: string_spec_init  */
  if (yyn == 220)
    /* "src/main/java/parser/Parser.y":771  */
                            { yyval = null; };
  break;



/* "src/main/java/parser/Parser.java":1602  */

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
      34,     9,    69,    64,  -311,     0,    18,    20,  -311,   115,
    -311,    59,  -311,   100,  -311,  -311,   159,   158,  -311,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,   114,   162,
    -311,  -311,   167,  -311,   128,  -311,  -311,  -311,  -311,  -311,
    -311,   190,  -311,   192,  -311,   193,  -311,  -311,  -311,  -311,
     -26,  -311,  -311,  -311,    23,   152,    17,   157,   260,   163,
    -311,    14,   467,   163,   164,   202,   156,   172,   174,   112,
     112,  -311,   195,  -311,  -311,   298,  -311,   170,  -311,   166,
    -311,  -311,  -311,   227,   182,   183,   184,  -311,  -311,   196,
    -311,   185,  -311,  -311,   231,   194,   188,   235,  -311,  -311,
     205,  -311,  -311,   436,  -311,  -311,   213,  -311,  -311,   238,
     238,   248,  -311,   299,   300,  -311,  -311,  -311,  -311,  -311,
    -311,  -311,  -311,  -311,   267,   266,    84,  -311,  -311,  -311,
    -311,  -311,   232,  -311,  -311,   233,   163,   246,  -311,  -311,
     230,  -311,  -311,   -19,  -311,  -311,  -311,   271,  -311,   234,
    -311,  -311,   -53,   244,  -311,   238,  -311,   274,  -311,   309,
    -311,   116,   227,  -311,  -311,  -311,  -311,   417,   154,   436,
    -311,   263,   -14,   301,   224,     7,   262,   331,   305,  -311,
     337,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,   -38,
     264,  -311,  -311,  -311,  -311,  -311,  -311,   110,  -311,  -311,
    -311,  -311,  -311,  -311,   307,  -311,  -311,    26,   310,    71,
    -311,   335,   312,  -311,   336,  -311,   323,  -311,  -311,  -311,
    -311,   293,  -311,   339,  -311,  -311,   294,   331,   351,   338,
    -311,   295,  -311,   334,    78,   369,   331,   302,   363,   360,
     346,    12,  -311,  -311,   308,   371,   314,  -311,   306,  -311,
    -311,   347,   311,   382,    77,   313,   377,  -311,   327,   333,
      10,  -311,  -311,  -311,   332,   340,  -311,  -311,    78,   177,
     375,   410,  -311,  -311,  -311,  -311,    63,   341,  -311,    50,
    -311,   343,  -311,    78,    78,   348,  -311,  -311,  -311,  -311,
    -311,   344,   187,  -311,  -311,  -311,   352,    56,    72,  -311,
    -311,  -311,  -311,  -311,   364,   365,   354,  -311,  -311,   355,
    -311,  -311,  -311,  -311,  -311,   374,  -311,   345,  -311,  -311,
    -311,  -311,  -311,  -311,   342,   401,    -4,    -3,   117,    -4,
     403,   171,  -311,   397,  -311,     5,    -2,    -1,  -311,   372,
     418,  -311,   419,  -311,   117,   171,   117,   171,  -311,   448,
      -7,  -311,  -311,  -311,  -311,  -311,   432,   394,   442,   420,
    -311,  -311,  -311,   491,  -311,   476,  -311
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
     208,     0,     0,     0,   209,     0,     0,     0,     1,     0,
       2,     0,   210,     0,   211,     4,     0,   114,   206,   207,
     154,   155,   156,   157,   140,   142,   141,   143,   130,   131,
     132,   133,   134,   135,   136,   137,   138,   139,     0,     0,
     214,   215,   121,   123,   126,   128,   129,   127,   124,   125,
     217,   158,   216,   162,   218,   167,   115,   117,   116,   220,
     202,   213,   219,   212,     8,     0,     0,     0,     0,     0,
     165,     0,     0,     0,     0,     0,     0,     0,     0,    98,
      98,     5,    12,     7,     6,     0,   221,     0,   222,   194,
     147,   144,   148,     0,     0,     0,     0,   192,   146,     0,
     145,     0,   196,   193,     0,   171,     0,     0,   164,   122,
       0,   159,   163,     0,   168,   204,     0,    99,   100,     0,
       0,   101,     9,     0,    15,    10,   225,   226,   228,   227,
     229,   230,   224,   223,     0,     0,     0,   185,   187,   188,
     150,   151,     0,   149,   152,     0,     0,     0,   166,   160,
     147,   181,   183,     0,   175,   177,   182,   203,   199,     0,
     103,   106,     0,     0,   102,     0,     3,     0,    13,    42,
     195,     0,     0,   184,   153,   161,   172,     0,     0,     0,
     174,     0,     0,     0,     0,     0,     0,     0,     0,    16,
      88,   190,   191,   189,   186,   169,   173,   126,   170,     0,
       0,   176,   205,    96,   104,   200,   201,   118,   105,   107,
     109,   110,   111,   112,   197,   113,    95,     0,     0,     0,
      20,    40,     0,    43,    11,   178,     0,   180,   119,   120,
     108,     0,    97,     0,    14,    21,     0,    18,    46,     0,
      89,     0,   198,     0,     0,     0,    19,     0,    56,    48,
       0,     0,    91,   179,     0,     0,     0,    24,    25,    27,
      26,     0,     0,     0,     0,     0,     0,    57,     0,     0,
       0,    90,    92,    22,     0,     0,    23,    28,     0,     0,
       0,     0,    50,    51,    52,    47,     0,     0,    65,     0,
      45,     0,    93,     0,     0,     0,    32,    33,    34,    35,
      36,     0,     0,    17,    60,    59,     0,     0,     0,    53,
      54,    55,    49,    94,     0,     0,     0,    31,    39,     0,
      38,    58,    62,    63,    64,     0,    44,     0,    66,    30,
      29,    41,    37,    61,     0,     0,     0,     0,    73,     0,
       0,    73,    80,    79,    78,     0,     0,     0,    72,     0,
       0,    71,     0,    82,    73,    73,    73,    73,    81,    85,
      68,    83,    74,    76,    75,    77,     0,     0,     0,     0,
      84,    70,    69,    87,    67,     0,    86
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,
    -311,  -311,  -311,   284,  -190,  -311,  -311,  -311,   265,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,
    -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,  -311,   186,
    -310,  -289,  -311,  -311,  -311,  -311,  -311,   273,  -311,  -311,
    -311,   446,  -311,   -99,  -150,  -311,   -77,  -311,   -76,   359,
    -311,   -11,  -311,  -311,   -10,    -9,   -62,  -239,  -311,  -311,
      -8,   -73,  -311,   478,   -72,  -311,  -311,   -71,  -311,   406,
    -311,   -65,  -311,   370,   376,   -61,  -311,   381,  -311,   -75,
     -74,   480,  -311,  -311,  -311,  -311,   -70,  -311,  -311,  -311,
    -311,   550,  -311,  -311,  -311,   492,  -311
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,    10,    64,    81,    82,   122,   123,   124,   168,
     169,   189,   245,   219,   220,   256,   257,   258,   259,   263,
     301,   281,   319,   237,   190,   223,   248,   249,   269,   285,
     312,   266,   267,   306,   288,   325,   308,   328,   369,   340,
     348,   341,   342,   360,   224,   240,   251,   252,    83,    84,
     125,   119,   165,   159,   160,   208,    40,   230,    41,    42,
      43,    94,    45,    46,    95,    96,   151,    98,    99,   100,
     101,    50,    51,   105,    52,    53,    71,    54,    55,   106,
     198,   152,   153,   154,   155,   156,   136,   137,   193,    56,
      57,   103,    58,   161,   162,   214,    59,    60,     3,     4,
       6,     7,    61,    62,    66,    67,   132
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
      44,    47,    48,    49,   260,   261,    97,   102,   126,   127,
     109,   114,   128,   129,   130,   131,   275,   367,   138,   139,
      77,   163,    12,    86,   337,   337,   337,   337,   271,   235,
     158,   351,   204,   352,   183,   204,   184,     1,   203,   295,
     338,   343,   354,   356,   362,   363,   364,   365,   344,   353,
     225,   158,   226,     5,   314,   315,   235,   355,   357,   216,
     250,    65,     5,   320,    16,    78,   186,   204,   179,     8,
     158,     9,    79,   180,    44,    47,    48,    49,   232,    80,
     368,   234,   339,   339,   339,   339,   326,   309,   218,    11,
     291,   310,   311,   327,   322,   292,   323,   138,   139,   324,
     304,   107,   108,    17,   305,    14,   191,   209,   210,    97,
     192,   211,   212,   213,   215,   282,   283,   284,    18,    19,
      20,    21,    22,    23,    24,    25,    26,    27,   372,    28,
      29,    30,    31,    32,    33,    34,    35,    36,    37,    20,
      21,    22,    23,    38,   345,    39,   346,   347,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,    90,    15,
      89,    92,   117,   118,   228,   229,   197,    47,    48,    49,
     242,   172,   173,    44,    47,    48,    49,    20,    21,    22,
      23,    24,    25,    26,    27,    63,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,    90,    91,   199,    92,
     346,   347,    93,    65,    68,    69,    70,   113,   296,   297,
     298,   299,   300,    72,    73,    20,    21,    22,    23,    24,
      25,    26,    27,   318,    28,    29,    30,    31,    32,    33,
      34,    35,    36,    37,    90,    91,    74,    92,    75,    76,
      93,    85,    88,   104,   111,   113,    89,   113,    20,    21,
      22,    23,   121,   115,   116,   133,   134,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    90,    17,   206,
      92,   135,   140,   141,   142,   144,   143,   207,   145,   148,
     147,   146,   158,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,   149,    28,    29,    30,    31,    32,    33,
      34,    35,    36,    37,    89,   157,   164,   166,    38,   167,
      39,   170,   171,   175,   174,   177,   178,   181,   187,   182,
     188,    20,    21,    22,    23,    24,    25,    26,    27,   185,
      28,    29,    30,    31,    32,    33,    34,    35,    36,    37,
      90,    91,    17,    92,   202,   205,    93,   217,   218,   221,
     222,   239,   227,   231,   233,   236,   238,    18,    19,    20,
      21,    22,    23,    24,    25,    26,    27,   241,    28,    29,
      30,    31,    32,    33,    34,    35,    36,    37,   254,    93,
     244,   247,    38,   253,    39,   243,   250,   262,   265,   268,
     270,   264,   255,   273,   278,    20,    21,    22,    23,   276,
     279,   280,   286,   287,    28,    29,    30,    31,    32,    33,
      34,    35,    36,    37,    90,   274,   289,    92,   290,   293,
     255,   302,   303,   336,   345,   334,   350,   294,   313,   317,
     307,   335,    20,    21,    22,    23,   316,   321,   371,   331,
     332,    28,    29,    30,    31,    32,    33,    34,    35,    36,
      37,    90,   329,   330,    92,    20,    21,    22,    23,   333,
     358,   195,   359,   361,    28,    29,    30,    31,    32,    33,
      34,    35,    36,    37,    90,   366,   370,    92,    20,    21,
      22,    23,    24,    25,    26,    27,   373,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    20,    21,    22,
      23,    24,    25,    26,    27,   374,    28,    29,    30,    31,
      32,    33,    34,    35,    36,    37,   150,    91,   375,    92,
     376,   246,    93,   277,   272,   349,   120,   113,    20,    21,
      22,    23,    24,    25,    26,    27,   196,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    90,    91,   201,
      92,   110,   176,   194,   200,   112,    13,     0,    87
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      11,    11,    11,    11,   243,   244,    68,    68,    85,    85,
      72,    76,    85,    85,    85,    85,   255,    24,    93,    93,
      46,   120,     4,     6,    28,    28,    28,    28,    16,   219,
      44,   341,   182,    28,    87,   185,    89,     3,    52,   278,
      44,    44,    44,    44,   354,   355,   356,   357,   337,    44,
      88,    44,    90,    44,   293,   294,   246,   346,   347,    52,
      48,    44,    44,   302,     5,    91,   165,   217,    87,     0,
      44,     7,    49,    92,    85,    85,    85,    85,    52,    56,
      87,    10,    86,    86,    86,    86,    14,    37,    17,    89,
      80,    41,    42,    21,    38,    85,    40,   172,   172,    43,
      37,    87,    88,    44,    41,    85,   171,   184,   184,   171,
     171,   184,   184,   184,   184,    38,    39,    40,    59,    60,
      61,    62,    63,    64,    65,    66,    67,    68,   367,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    61,
      62,    63,    64,    84,    27,    86,    29,    30,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    44,
      44,    83,    50,    51,    54,    55,   177,   177,   177,   177,
     231,    87,    88,   184,   184,   184,   184,    61,    62,    63,
      64,    65,    66,    67,    68,    85,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    44,    83,
      29,    30,    86,    44,    46,    91,    44,    91,    31,    32,
      33,    34,    35,    46,    86,    61,    62,    63,    64,    65,
      66,    67,    68,    36,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    81,    46,    83,    46,    46,
      86,    89,    85,    80,    80,    91,    44,    91,    61,    62,
      63,    64,    57,    81,    80,    85,    90,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    44,    45,
      83,    44,    90,    90,    90,    90,    80,    53,    47,    44,
      92,    87,    44,    59,    60,    61,    62,    63,    64,    65,
      66,    67,    68,    88,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    44,    92,    58,     8,    84,     9,
      86,    44,    46,    80,    82,    69,    86,    46,    44,    85,
      11,    61,    62,    63,    64,    65,    66,    67,    68,    85,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    44,    83,    81,    44,    86,    85,    17,    44,
      13,    15,    88,    46,    44,    20,    44,    59,    60,    61,
      62,    63,    64,    65,    66,    67,    68,    44,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    44,    86,
      86,    30,    84,    88,    86,    46,    48,    18,    25,    29,
      44,    89,    86,    85,    47,    61,    62,    63,    64,    85,
      89,    19,    89,    26,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    44,    89,    83,    85,    87,
      86,    46,    12,    22,    27,    80,    23,    87,    85,    85,
      89,    89,    61,    62,    63,    64,    88,    85,    44,    85,
      85,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    88,    88,    83,    61,    62,    63,    64,    85,
      88,    44,    44,    44,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    80,    27,    44,    83,    61,    62,
      63,    64,    65,    66,    67,    68,    44,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    61,    62,    63,
      64,    65,    66,    67,    68,    85,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    27,    83,
      44,   237,    86,   258,   251,   339,    80,    91,    61,    62,
      63,    64,    65,    66,    67,    68,   177,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,   179,
      83,    73,   146,   172,   178,    75,     6,    -1,    66
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,    94,   191,   192,    44,   193,   194,     0,     7,
      95,    89,     4,   194,    85,    44,     5,    44,    59,    60,
      61,    62,    63,    64,    65,    66,    67,    68,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    84,    86,
     149,   151,   152,   153,   154,   155,   156,   157,   158,   163,
     164,   165,   167,   168,   170,   171,   182,   183,   185,   189,
     190,   195,   196,    85,    96,    44,   197,   198,    46,    91,
      44,   169,    46,    86,    46,    46,    46,    46,    91,    49,
      56,    97,    98,   141,   142,    89,     6,   198,    85,    44,
      80,    81,    83,    86,   154,   157,   158,   159,   160,   161,
     162,   163,   178,   184,    80,   166,   172,    87,    88,   159,
     166,    80,   184,    91,   174,    81,    80,    50,    51,   144,
     144,    57,    99,   100,   101,   143,   149,   151,   164,   167,
     170,   189,   199,    85,    90,    44,   179,   180,   182,   183,
      90,    90,    90,    80,    90,    47,    87,    92,    44,    88,
      80,   159,   174,   175,   176,   177,   178,    92,    44,   146,
     147,   186,   187,   146,    58,   145,     8,     9,   102,   103,
      44,    46,    87,    88,    82,    80,   172,    69,    86,    87,
      92,    46,    85,    87,    89,    85,   146,    44,    11,   104,
     117,   174,   178,   181,   180,    44,   152,   154,   173,    44,
     177,   176,    81,    52,   147,    44,    45,    53,   148,   149,
     151,   164,   167,   170,   188,   189,    52,    85,    17,   106,
     107,    44,    13,   118,   137,    88,    90,    88,    54,    55,
     150,    46,    52,    44,    10,   107,    20,   116,    44,    15,
     138,    44,   178,    46,    86,   105,   106,    30,   119,   120,
      48,   139,   140,    88,    44,    86,   108,   109,   110,   111,
     160,   160,    18,   112,    89,    25,   124,   125,    29,   121,
      44,    16,   140,    85,    44,   160,    85,   111,    47,    89,
      19,   114,    38,    39,    40,   122,    89,    26,   127,    89,
      85,    80,    85,    87,    87,   160,    31,    32,    33,    34,
      35,   113,    46,    12,    37,    41,   126,    89,   129,    37,
      41,    42,   123,    85,   160,   160,    88,    85,    36,   115,
     160,    85,    38,    40,    43,   128,    14,    21,   130,    88,
      88,    85,    85,    85,    80,    89,    22,    28,    44,    86,
     132,   134,   135,    44,   134,    27,    29,    30,   133,   132,
      23,   133,    28,    44,    44,   134,    44,   134,    88,    44,
     136,    44,   133,   133,   133,   133,    27,    24,    87,   131,
      44,    44,   160,    44,    85,    27,    44
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,    93,    94,    95,    96,    96,    97,    97,    98,    98,
      99,   100,   101,   101,   102,   103,   103,   104,   105,   105,
     106,   106,   107,   107,   108,   108,   109,   110,   110,   111,
     111,   112,   113,   113,   113,   113,   113,   114,   115,   115,
     116,   116,   117,   117,   118,   119,   120,   120,   121,   121,
     122,   122,   122,   123,   123,   123,   124,   124,   125,   126,
     126,   127,   128,   128,   128,   129,   129,   130,   131,   131,
     131,   132,   132,   133,   133,   133,   133,   133,   134,   134,
     134,   134,   135,   135,   136,   136,   136,   136,   137,   137,
     138,   139,   139,   140,   140,   141,   142,   143,   144,   144,
     144,   145,   145,   146,   146,   147,   147,   148,   148,   148,
     148,   148,   148,   148,   149,   149,   149,   149,   150,   150,
     150,   151,   151,   152,   152,   152,   153,   153,   154,   154,
     155,   155,   155,   155,   156,   156,   156,   156,   157,   157,
     158,   158,   158,   158,   159,   159,   159,   160,   160,   160,
     161,   161,   161,   162,   163,   163,   163,   163,   164,   164,
     165,   166,   167,   167,   168,   169,   169,   170,   170,   171,
     171,   172,   172,   173,   174,   175,   175,   176,   176,   176,
     176,   177,   177,   177,   178,   179,   179,   180,   180,   180,
     181,   181,   182,   183,   184,   184,   185,   186,   186,   187,
     187,   188,   189,   189,   189,   189,   190,   190,   191,   191,
     192,   193,   193,   194,   195,   195,   195,   195,   195,   195,
     195,   196,   197,   197,   198,   199,   199,   199,   199,   199,
     199
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
       1,     1,     1,     1,     1,     1,     1,     1,     1,     2,
       2,     2,     2,     3,     1,     1,     1,     1,     1,     3,
       4,     3,     1,     3,     3,     1,     3,     1,     3,     6,
       6,     1,     3,     1,     3,     1,     3,     1,     4,     6,
       4,     1,     1,     1,     3,     1,     3,     1,     1,     3,
       1,     1,     3,     3,     1,     3,     3,     3,     5,     1,
       3,     1,     1,     4,     3,     6,     1,     1,     0,     1,
       3,     2,     3,     3,     1,     1,     1,     1,     1,     1,
       1,     3,     2,     3,     3,     1,     1,     1,     1,     1,
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


  private static final int YYLAST_ = 558;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 8;
  private static final int YYNTOKENS_ = 93;


}
/* "src/main/java/parser/Parser.y":796  */
