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
    import java.util.Collections;

    import utils.LexemeInfo;
    import utils.SymbolTable;
    import utils.enums.*;
    import utils.builders.LexemeInfoBuilder;

    import parser.initializations.*;
    import parser.publishers.*;
    import parser.utils.NameMangler;

/* "src/main/java/parser/Parser.java":60  */

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
    S_opt_fb_io_var_declarations_list(96), /* opt_fb_io_var_declarations_list  */
    S_fb_io_var_declarations(97),  /* fb_io_var_declarations  */
    S_opt_other_var_declarations_list(98), /* opt_other_var_declarations_list  */
    S_other_var_declarations(99),  /* other_var_declarations  */
    S_opt_function_block_body(100), /* opt_function_block_body  */
    S_opt_fuzzify_block_list(101), /* opt_fuzzify_block_list  */
    S_fuzzify_block(102),          /* fuzzify_block  */
    S_opt_defuzzify_block_list(103), /* opt_defuzzify_block_list  */
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
    S_opt_rule_block_list(117),    /* opt_rule_block_list  */
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
    S_opt_option_block_list(137),  /* opt_option_block_list  */
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
    S_var_spec_init(148),          /* var_spec_init  */
    S_boolean_spec_init(149),      /* boolean_spec_init  */
    S_boolean_specification(150),  /* boolean_specification  */
    S_initialized_boolean(151),    /* initialized_boolean  */
    S_edge(152),                   /* edge  */
    S_custom_spec_init(153),       /* custom_spec_init  */
    S_custom_specification(154),   /* custom_specification  */
    S_initialized_custom(155),     /* initialized_custom  */
    S_simple_spec_init(156),       /* simple_spec_init  */
    S_simple_specification(157),   /* simple_specification  */
    S_initialized_simple(158),     /* initialized_simple  */
    S_elementary_type_name(159),   /* elementary_type_name  */
    S_numeric_type_name(160),      /* numeric_type_name  */
    S_integer_type_name(161),      /* integer_type_name  */
    S_signed_integer_type_name(162), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(163), /* unsigned_integer_type_name  */
    S_real_type_name(164),         /* real_type_name  */
    S_date_type_name(165),         /* date_type_name  */
    S_constant(166),               /* constant  */
    S_string_constant(167),        /* string_constant  */
    S_boolean_constant(168),       /* boolean_constant  */
    S_numeric_constant(169),       /* numeric_constant  */
    S_number_prefix(170),          /* number_prefix  */
    S_time_constant(171),          /* time_constant  */
    S_bit_string_type_name(172),   /* bit_string_type_name  */
    S_subrange_spec_init(173),     /* subrange_spec_init  */
    S_subrange_specification(174), /* subrange_specification  */
    S_initialized_subrange(175),   /* initialized_subrange  */
    S_range(176),                  /* range  */
    S_enumerated_spec_init(177),   /* enumerated_spec_init  */
    S_enumerated_specification(178), /* enumerated_specification  */
    S_initialized_enumerated(179), /* initialized_enumerated  */
    S_enumerated_list(180),        /* enumerated_list  */
    S_array_spec_init(181),        /* array_spec_init  */
    S_array_specification(182),    /* array_specification  */
    S_initialized_array(183),      /* initialized_array  */
    S_range_list(184),             /* range_list  */
    S_non_generic_type_name(185),  /* non_generic_type_name  */
    S_array_initialization(186),   /* array_initialization  */
    S_array_initial_elements_list(187), /* array_initial_elements_list  */
    S_array_initial_elements(188), /* array_initial_elements  */
    S_array_initial_element(189),  /* array_initial_element  */
    S_repeated_initial_element(190), /* repeated_initial_element  */
    S_initialized_structure(191),  /* initialized_structure  */
    S_initialized_structure_field_list(192), /* initialized_structure_field_list  */
    S_initialized_structure_field(193), /* initialized_structure_field  */
    S_initialized_custom_with_constant(194), /* initialized_custom_with_constant  */
    S_initialized_custom_with_identifier(195), /* initialized_custom_with_identifier  */
    S_identifier_with_opt_mangling(196), /* identifier_with_opt_mangling  */
    S_initialized_custom_with_array(197), /* initialized_custom_with_array  */
    S_initialized_custom_with_structure(198), /* initialized_custom_with_structure  */
    S_fb_name_decl(199),           /* fb_name_decl  */
    S_identifier_list(200),        /* identifier_list  */
    S_standard_function_block_name(201), /* standard_function_block_name  */
    S_string_spec_init(202),       /* string_spec_init  */
    S_string_specification(203),   /* string_specification  */
    S_initialized_string(204),     /* initialized_string  */
    S_type_string_specification(205), /* type_string_specification  */
    S_opt_data_type_declaration(206), /* opt_data_type_declaration  */
    S_data_type_declaration(207),  /* data_type_declaration  */
    S_type_declaration_list(208),  /* type_declaration_list  */
    S_type_declaration(209),       /* type_declaration  */
    S_type_name(210),              /* type_name  */
    S_type_spec_init(211),         /* type_spec_init  */
    S_structure_specification(212), /* structure_specification  */
    S_structure_field_declaration_list(213), /* structure_field_declaration_list  */
    S_structure_field_declaration(214), /* structure_field_declaration  */
    S_structure_field_spec_init(215); /* structure_field_spec_init  */


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
      SymbolKind.S_opt_fb_io_var_declarations_list,
      SymbolKind.S_fb_io_var_declarations,
      SymbolKind.S_opt_other_var_declarations_list,
      SymbolKind.S_other_var_declarations,
      SymbolKind.S_opt_function_block_body,
      SymbolKind.S_opt_fuzzify_block_list,
      SymbolKind.S_fuzzify_block,
      SymbolKind.S_opt_defuzzify_block_list,
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
      SymbolKind.S_output_declarations,
      SymbolKind.S_input_declarations,
      SymbolKind.S_var_declarations,
      SymbolKind.S_var_retain_spec,
      SymbolKind.S_var_constant_spec,
      SymbolKind.S_var_init_decl_list,
      SymbolKind.S_var_init_decl,
      SymbolKind.S_var_spec_init,
      SymbolKind.S_boolean_spec_init,
      SymbolKind.S_boolean_specification,
      SymbolKind.S_initialized_boolean,
      SymbolKind.S_edge,
      SymbolKind.S_custom_spec_init,
      SymbolKind.S_custom_specification,
      SymbolKind.S_initialized_custom,
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
      SymbolKind.S_initialized_subrange,
      SymbolKind.S_range,
      SymbolKind.S_enumerated_spec_init,
      SymbolKind.S_enumerated_specification,
      SymbolKind.S_initialized_enumerated,
      SymbolKind.S_enumerated_list,
      SymbolKind.S_array_spec_init,
      SymbolKind.S_array_specification,
      SymbolKind.S_initialized_array,
      SymbolKind.S_range_list,
      SymbolKind.S_non_generic_type_name,
      SymbolKind.S_array_initialization,
      SymbolKind.S_array_initial_elements_list,
      SymbolKind.S_array_initial_elements,
      SymbolKind.S_array_initial_element,
      SymbolKind.S_repeated_initial_element,
      SymbolKind.S_initialized_structure,
      SymbolKind.S_initialized_structure_field_list,
      SymbolKind.S_initialized_structure_field,
      SymbolKind.S_initialized_custom_with_constant,
      SymbolKind.S_initialized_custom_with_identifier,
      SymbolKind.S_identifier_with_opt_mangling,
      SymbolKind.S_initialized_custom_with_array,
      SymbolKind.S_initialized_custom_with_structure,
      SymbolKind.S_fb_name_decl,
      SymbolKind.S_identifier_list,
      SymbolKind.S_standard_function_block_name,
      SymbolKind.S_string_spec_init,
      SymbolKind.S_string_specification,
      SymbolKind.S_initialized_string,
      SymbolKind.S_type_string_specification,
      SymbolKind.S_opt_data_type_declaration,
      SymbolKind.S_data_type_declaration,
      SymbolKind.S_type_declaration_list,
      SymbolKind.S_type_declaration,
      SymbolKind.S_type_name,
      SymbolKind.S_type_spec_init,
      SymbolKind.S_structure_specification,
      SymbolKind.S_structure_field_declaration_list,
      SymbolKind.S_structure_field_declaration,
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
  "opt_fb_io_var_declarations_list", "fb_io_var_declarations",
  "opt_other_var_declarations_list", "other_var_declarations",
  "opt_function_block_body", "opt_fuzzify_block_list", "fuzzify_block",
  "opt_defuzzify_block_list", "defuzzify_block",
  "opt_linguistic_term_list", "linguistic_term_list", "linguistic_term",
  "membership_function", "singleton", "point_list", "point",
  "defuzzification_method", "defuzz_method", "default_value",
  "default_val", "opt_range", "opt_rule_block_list", "rule_block",
  "operator_definition", "opt_operator_or", "operator_and_opt", "or_type",
  "and_type", "activation_method_opt", "activation_method", "act_type",
  "accumulation_method", "accu_type", "rule_list", "rule", "opt_weighting",
  "condition", "condition_tail", "x", "subcondition", "conclusion_list",
  "opt_option_block_list", "option_block", "pragma_list", "pragma",
  "output_declarations", "input_declarations", "var_declarations",
  "var_retain_spec", "var_constant_spec", "var_init_decl_list",
  "var_init_decl", "var_spec_init", "boolean_spec_init",
  "boolean_specification", "initialized_boolean", "edge",
  "custom_spec_init", "custom_specification", "initialized_custom",
  "simple_spec_init", "simple_specification", "initialized_simple",
  "elementary_type_name", "numeric_type_name", "integer_type_name",
  "signed_integer_type_name", "unsigned_integer_type_name",
  "real_type_name", "date_type_name", "constant", "string_constant",
  "boolean_constant", "numeric_constant", "number_prefix", "time_constant",
  "bit_string_type_name", "subrange_spec_init", "subrange_specification",
  "initialized_subrange", "range", "enumerated_spec_init",
  "enumerated_specification", "initialized_enumerated", "enumerated_list",
  "array_spec_init", "array_specification", "initialized_array",
  "range_list", "non_generic_type_name", "array_initialization",
  "array_initial_elements_list", "array_initial_elements",
  "array_initial_element", "repeated_initial_element",
  "initialized_structure", "initialized_structure_field_list",
  "initialized_structure_field", "initialized_custom_with_constant",
  "initialized_custom_with_identifier", "identifier_with_opt_mangling",
  "initialized_custom_with_array", "initialized_custom_with_structure",
  "fb_name_decl", "identifier_list", "standard_function_block_name",
  "string_spec_init", "string_specification", "initialized_string",
  "type_string_specification", "opt_data_type_declaration",
  "data_type_declaration", "type_declaration_list", "type_declaration",
  "type_name", "type_spec_init", "structure_specification",
  "structure_field_declaration_list", "structure_field_declaration",
  "structure_field_spec_init", null
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
    protected final NameMangler nameMangler;



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public Parser(Lexer yylexer, SymbolTable symbolTable,NameMangler nameMangler)
  {

    this.yylexer = yylexer;
this.symbolTable = symbolTable;
          this.nameMangler = nameMangler;
          
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
    /* "src/main/java/parser/Parser.y":374  */
    { 
        Compound variables = new Compound(((List<Publisher>)(yystack.valueAt (2))));
        variables.source(Source.OUT).publish();
    };
  break;


  case 96: /* input_declarations: VAR_INPUT var_retain_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 96)
    /* "src/main/java/parser/Parser.y":382  */
    { 
        Compound variables = new Compound(((List<Publisher>)(yystack.valueAt (2))));
        variables.source(Source.IN).publish();
    };
  break;


  case 97: /* var_declarations: VAR var_constant_spec var_init_decl_list ';' END_VAR  */
  if (yyn == 97)
    /* "src/main/java/parser/Parser.y":390  */
    { 
        Compound variables = new Compound(((List<Publisher>)(yystack.valueAt (2))));
        variables.source(Source.INTERNAL).publish();
    };
  break;


  case 103: /* var_init_decl_list: var_init_decl  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":409  */
    {
        List<Publisher> declarations = new ArrayList<>();
        declarations.add(((Publisher)(yystack.valueAt (0))));
        yyval = declarations;
    };
  break;


  case 104: /* var_init_decl_list: var_init_decl_list ';' var_init_decl  */
  if (yyn == 104)
    /* "src/main/java/parser/Parser.y":415  */
    { 
        ((List<Publisher>)(yystack.valueAt (2))).add(((Publisher)(yystack.valueAt (0))));
        yyval = ((List<Publisher>)(yystack.valueAt (2)));
    };
  break;


  case 105: /* var_init_decl: identifier_list ':' var_spec_init  */
  if (yyn == 105)
    /* "src/main/java/parser/Parser.y":424  */
    {
        yyval = new Declaration(this.symbolTable, ((List<String>)(yystack.valueAt (2))), ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.VARIABLE));
    };
  break;


  case 106: /* var_init_decl: fb_name_decl  */
  if (yyn == 106)
    /* "src/main/java/parser/Parser.y":428  */
    {
        yyval = null;
    };
  break;


  case 107: /* var_spec_init: custom_spec_init  */
  if (yyn == 107)
    /* "src/main/java/parser/Parser.y":435  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 108: /* var_spec_init: boolean_spec_init  */
  if (yyn == 108)
    /* "src/main/java/parser/Parser.y":436  */
                            { yyval = null; };
  break;


  case 109: /* var_spec_init: simple_spec_init  */
  if (yyn == 109)
    /* "src/main/java/parser/Parser.y":437  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 110: /* var_spec_init: subrange_spec_init  */
  if (yyn == 110)
    /* "src/main/java/parser/Parser.y":438  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 111: /* var_spec_init: enumerated_spec_init  */
  if (yyn == 111)
    /* "src/main/java/parser/Parser.y":439  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 112: /* var_spec_init: array_spec_init  */
  if (yyn == 112)
    /* "src/main/java/parser/Parser.y":440  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 113: /* var_spec_init: string_spec_init  */
  if (yyn == 113)
    /* "src/main/java/parser/Parser.y":441  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 120: /* custom_spec_init: custom_specification  */
  if (yyn == 120)
    /* "src/main/java/parser/Parser.y":462  */
                         { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 121: /* custom_spec_init: initialized_custom  */
  if (yyn == 121)
    /* "src/main/java/parser/Parser.y":463  */
                           { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 122: /* custom_specification: IDENTIFIER  */
  if (yyn == 122)
    /* "src/main/java/parser/Parser.y":468  */
    {
        yyval = new LexemeInfoBuilder()
                    .type(Type.SIMPLE)
                    .subtype(Subtype.CUSTOM)
                    .customType(((String)(yystack.valueAt (0))))
                    .initialValue(this.symbolTable.get(((String)(yystack.valueAt (0)))).initialValue);
    };
  break;


  case 123: /* initialized_custom: initialized_custom_with_constant  */
  if (yyn == 123)
    /* "src/main/java/parser/Parser.y":478  */
                                         { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).type(Type.SIMPLE); };
  break;


  case 124: /* initialized_custom: initialized_custom_with_structure  */
  if (yyn == 124)
    /* "src/main/java/parser/Parser.y":479  */
                                         { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).type(Type.SIMPLE); };
  break;


  case 125: /* initialized_custom: initialized_custom_with_identifier  */
  if (yyn == 125)
    /* "src/main/java/parser/Parser.y":480  */
                                         { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))).type(Type.SIMPLE); };
  break;


  case 126: /* simple_spec_init: simple_specification  */
  if (yyn == 126)
    /* "src/main/java/parser/Parser.y":484  */
                         { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 127: /* simple_spec_init: initialized_simple  */
  if (yyn == 127)
    /* "src/main/java/parser/Parser.y":485  */
                          { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 128: /* simple_specification: elementary_type_name  */
  if (yyn == 128)
    /* "src/main/java/parser/Parser.y":489  */
    {
        yyval = new LexemeInfoBuilder().type(Type.SIMPLE).subtype(((Subtype)(yystack.valueAt (0))));
    };
  break;


  case 129: /* initialized_simple: elementary_type_name ASSIGN_OP constant  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":496  */
    {
        yyval = new LexemeInfoBuilder().type(Type.SIMPLE).subtype(((Subtype)(yystack.valueAt (2)))).initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 130: /* elementary_type_name: numeric_type_name  */
  if (yyn == 130)
    /* "src/main/java/parser/Parser.y":504  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 131: /* elementary_type_name: date_type_name  */
  if (yyn == 131)
    /* "src/main/java/parser/Parser.y":505  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 132: /* elementary_type_name: bit_string_type_name  */
  if (yyn == 132)
    /* "src/main/java/parser/Parser.y":506  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 133: /* numeric_type_name: integer_type_name  */
  if (yyn == 133)
    /* "src/main/java/parser/Parser.y":510  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 134: /* numeric_type_name: real_type_name  */
  if (yyn == 134)
    /* "src/main/java/parser/Parser.y":511  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 135: /* integer_type_name: signed_integer_type_name  */
  if (yyn == 135)
    /* "src/main/java/parser/Parser.y":515  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 136: /* integer_type_name: unsigned_integer_type_name  */
  if (yyn == 136)
    /* "src/main/java/parser/Parser.y":516  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 137: /* signed_integer_type_name: SINT  */
  if (yyn == 137)
    /* "src/main/java/parser/Parser.y":520  */
           { yyval = Subtype.SINT; };
  break;


  case 138: /* signed_integer_type_name: INT  */
  if (yyn == 138)
    /* "src/main/java/parser/Parser.y":521  */
           { yyval = Subtype.INT;  };
  break;


  case 139: /* signed_integer_type_name: DINT  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":522  */
           { yyval = Subtype.DINT; };
  break;


  case 140: /* signed_integer_type_name: LINT  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":523  */
           { yyval = Subtype.LINT; };
  break;


  case 141: /* unsigned_integer_type_name: USINT  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":527  */
            { yyval = Subtype.USINT; };
  break;


  case 142: /* unsigned_integer_type_name: UINT  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":528  */
            { yyval = Subtype.UINT;  };
  break;


  case 143: /* unsigned_integer_type_name: UDINT  */
  if (yyn == 143)
    /* "src/main/java/parser/Parser.y":529  */
            { yyval = Subtype.UDINT; };
  break;


  case 144: /* unsigned_integer_type_name: ULINT  */
  if (yyn == 144)
    /* "src/main/java/parser/Parser.y":530  */
            { yyval = Subtype.ULINT; };
  break;


  case 145: /* real_type_name: REAL  */
  if (yyn == 145)
    /* "src/main/java/parser/Parser.y":534  */
            { yyval = Subtype.REAL;  };
  break;


  case 146: /* real_type_name: LREAL  */
  if (yyn == 146)
    /* "src/main/java/parser/Parser.y":535  */
            { yyval = Subtype.LREAL; };
  break;


  case 147: /* date_type_name: TIME  */
  if (yyn == 147)
    /* "src/main/java/parser/Parser.y":539  */
                    { yyval = Subtype.TIME;          };
  break;


  case 148: /* date_type_name: DATE  */
  if (yyn == 148)
    /* "src/main/java/parser/Parser.y":540  */
                    { yyval = Subtype.DATE;          };
  break;


  case 149: /* date_type_name: TIME_OF_DAY  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":541  */
                    { yyval = Subtype.TIME_OF_DAY;   };
  break;


  case 150: /* date_type_name: DATE_AND_TIME  */
  if (yyn == 150)
    /* "src/main/java/parser/Parser.y":542  */
                    { yyval = Subtype.DATE_AND_TIME; };
  break;


  case 151: /* constant: string_constant  */
  if (yyn == 151)
    /* "src/main/java/parser/Parser.y":548  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 152: /* constant: boolean_constant  */
  if (yyn == 152)
    /* "src/main/java/parser/Parser.y":549  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 153: /* constant: time_constant  */
  if (yyn == 153)
    /* "src/main/java/parser/Parser.y":550  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 154: /* constant: numeric_constant  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":551  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 155: /* string_constant: STRING_LITERAL  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":555  */
                   { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 156: /* boolean_constant: BOOLEAN_LITERAL  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":559  */
                    { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 158: /* numeric_constant: number_prefix NUMERIC_LITERAL  */
  if (yyn == 158)
    /* "src/main/java/parser/Parser.y":564  */
    {
        // todo: hacer conversion de esta constante en codigo
        yyval = "";
    };
  break;


  case 162: /* time_constant: date_type_name '#' TIME_LITERAL  */
  if (yyn == 162)
    /* "src/main/java/parser/Parser.y":577  */
                                    {
        // todo: Accion semantica que verifica que prefix es del mismo tipo que time_literal
    };
  break;


  case 163: /* bit_string_type_name: BYTE  */
  if (yyn == 163)
    /* "src/main/java/parser/Parser.y":583  */
            { yyval = Subtype.BYTE;  };
  break;


  case 164: /* bit_string_type_name: WORD  */
  if (yyn == 164)
    /* "src/main/java/parser/Parser.y":584  */
            { yyval = Subtype.WORD;  };
  break;


  case 165: /* bit_string_type_name: DWORD  */
  if (yyn == 165)
    /* "src/main/java/parser/Parser.y":585  */
            { yyval = Subtype.DWORD; };
  break;


  case 166: /* bit_string_type_name: LWORD  */
  if (yyn == 166)
    /* "src/main/java/parser/Parser.y":586  */
            { yyval = Subtype.LWORD; };
  break;


  case 167: /* subrange_spec_init: subrange_specification  */
  if (yyn == 167)
    /* "src/main/java/parser/Parser.y":592  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 168: /* subrange_spec_init: initialized_subrange  */
  if (yyn == 168)
    /* "src/main/java/parser/Parser.y":593  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 169: /* subrange_specification: integer_type_name '(' range ')'  */
  if (yyn == 169)
    /* "src/main/java/parser/Parser.y":598  */
    { 
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (1))).subtype(((Subtype)(yystack.valueAt (3))));
    };
  break;


  case 170: /* initialized_subrange: subrange_specification ASSIGN_OP numeric_constant  */
  if (yyn == 170)
    /* "src/main/java/parser/Parser.y":604  */
                                                      { yyval = ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(((String)(yystack.valueAt (0)))); };
  break;


  case 171: /* range: numeric_constant RANGE_OP numeric_constant  */
  if (yyn == 171)
    /* "src/main/java/parser/Parser.y":609  */
    {
        // todo: hacer chequeo semantico de rangos
        yyval = new LexemeInfoBuilder()
            .type(Type.SUBRANGE)
            .inferiorLimit(Collections.singletonList(((String)(yystack.valueAt (2)))))
            .superiorLimit(Collections.singletonList(((String)(yystack.valueAt (0)))))
            .initialValue(((String)(yystack.valueAt (2))));
    };
  break;


  case 172: /* enumerated_spec_init: enumerated_specification  */
  if (yyn == 172)
    /* "src/main/java/parser/Parser.y":620  */
                             { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 173: /* enumerated_spec_init: initialized_enumerated  */
  if (yyn == 173)
    /* "src/main/java/parser/Parser.y":621  */
                             { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 174: /* enumerated_specification: '(' enumerated_list ')'  */
  if (yyn == 174)
    /* "src/main/java/parser/Parser.y":625  */
                           {
        yyval = new LexemeInfoBuilder()
            .type(Type.ENUMERATE)
            .parameters(((List<String>)(yystack.valueAt (1))))
            .initialValue(((List<String>)(yystack.valueAt (1))).get(0));
    };
  break;


  case 175: /* initialized_enumerated: enumerated_specification ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 175)
    /* "src/main/java/parser/Parser.y":635  */
    {
        yyval = ((LexemeInfoBuilder)(yystack.valueAt (2))).initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 176: /* enumerated_list: IDENTIFIER  */
  if (yyn == 176)
    /* "src/main/java/parser/Parser.y":642  */
    {
        List<String> enumerates = new ArrayList<String>();
        enumerates.add(((String)(yystack.valueAt (0))));
        yyval = enumerates;
    };
  break;


  case 177: /* enumerated_list: enumerated_list ',' IDENTIFIER  */
  if (yyn == 177)
    /* "src/main/java/parser/Parser.y":648  */
    {
        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 178: /* array_spec_init: array_specification  */
  if (yyn == 178)
    /* "src/main/java/parser/Parser.y":655  */
                        { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 179: /* array_spec_init: initialized_array  */
  if (yyn == 179)
    /* "src/main/java/parser/Parser.y":656  */
                        { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 180: /* array_specification: ARRAY '[' range_list ']' OF IDENTIFIER  */
  if (yyn == 180)
    /* "src/main/java/parser/Parser.y":660  */
                                                        { yyval = null; };
  break;


  case 181: /* array_specification: ARRAY '[' range_list ']' OF non_generic_type_name  */
  if (yyn == 181)
    /* "src/main/java/parser/Parser.y":661  */
                                                        { yyval = null; };
  break;


  case 182: /* initialized_array: array_specification ASSIGN_OP array_initialization  */
  if (yyn == 182)
    /* "src/main/java/parser/Parser.y":665  */
                                                       { yyval = null; };
  break;


  case 196: /* initialized_structure: '(' initialized_structure_field_list ')'  */
  if (yyn == 196)
    /* "src/main/java/parser/Parser.y":704  */
    {

    };
  break;


  case 203: /* initialized_custom_with_constant: IDENTIFIER ASSIGN_OP constant  */
  if (yyn == 203)
    /* "src/main/java/parser/Parser.y":723  */
    {
        yyval = new LexemeInfoBuilder()
                .subtype(Subtype.CUSTOM)
                .customType(((String)(yystack.valueAt (2))))
                .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 204: /* initialized_custom_with_identifier: IDENTIFIER ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 204)
    /* "src/main/java/parser/Parser.y":733  */
    {
        yyval = new LexemeInfoBuilder()
                .subtype(Subtype.CUSTOM)
                .customType(((String)(yystack.valueAt (2))))
                .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 205: /* identifier_with_opt_mangling: IDENTIFIER  */
  if (yyn == 205)
    /* "src/main/java/parser/Parser.y":743  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 206: /* identifier_with_opt_mangling: IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 206)
    /* "src/main/java/parser/Parser.y":747  */
    {
        yyval = ((String)(yystack.valueAt (2))) + "#" + ((String)(yystack.valueAt (0)));
    };
  break;


  case 207: /* initialized_custom_with_array: IDENTIFIER ASSIGN_OP array_initialization  */
  if (yyn == 207)
    /* "src/main/java/parser/Parser.y":753  */
                                              { yyval = null; };
  break;


  case 208: /* initialized_custom_with_structure: IDENTIFIER ASSIGN_OP initialized_structure  */
  if (yyn == 208)
    /* "src/main/java/parser/Parser.y":757  */
                                               { yyval = null; };
  break;


  case 211: /* identifier_list: IDENTIFIER  */
  if (yyn == 211)
    /* "src/main/java/parser/Parser.y":768  */
    {
        // Left side identifers have a scope
        String mangledIdentifier = this.nameMangler.getNameMangled(((String)(yystack.valueAt (0))));

        List<String> identifiers = new ArrayList<String>();
        identifiers.add(mangledIdentifier);

        yyval = identifiers;
    };
  break;


  case 212: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 212)
    /* "src/main/java/parser/Parser.y":778  */
    {
        // Left side identifers have a scope
        String mangledIdentifier = this.nameMangler.getNameMangled(((String)(yystack.valueAt (0))));
        ((List<String>)(yystack.valueAt (2))).add(mangledIdentifier);
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 214: /* string_spec_init: string_specification  */
  if (yyn == 214)
    /* "src/main/java/parser/Parser.y":792  */
                         { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 215: /* string_spec_init: initialized_string  */
  if (yyn == 215)
    /* "src/main/java/parser/Parser.y":793  */
                         { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 216: /* string_specification: type_string_specification  */
  if (yyn == 216)
    /* "src/main/java/parser/Parser.y":796  */
                                                         { yyval = null; };
  break;


  case 217: /* string_specification: type_string_specification '[' numeric_constant ']'  */
  if (yyn == 217)
    /* "src/main/java/parser/Parser.y":797  */
                                                         { yyval = null; };
  break;


  case 218: /* initialized_string: type_string_specification ASSIGN_OP string_constant  */
  if (yyn == 218)
    /* "src/main/java/parser/Parser.y":800  */
                                                                                    { yyval = null; };
  break;


  case 219: /* initialized_string: type_string_specification '[' numeric_constant ']' ASSIGN_OP string_constant  */
  if (yyn == 219)
    /* "src/main/java/parser/Parser.y":801  */
                                                                                    { yyval = null; };
  break;


  case 224: /* data_type_declaration: TYPE type_declaration_list END_TYPE  */
  if (yyn == 224)
    /* "src/main/java/parser/Parser.y":818  */
    {
        Publisher declarations = new Compound(((List<Publisher>)(yystack.valueAt (1))));
        declarations.source(Source.NONE);
        declarations.publish();
    };
  break;


  case 225: /* type_declaration_list: type_declaration ';'  */
  if (yyn == 225)
    /* "src/main/java/parser/Parser.y":827  */
    {
        List<Publisher> declarations = new ArrayList<>();
        declarations.add(((Publisher)(yystack.valueAt (1))));
        yyval = declarations;
    };
  break;


  case 226: /* type_declaration_list: type_declaration_list type_declaration ';'  */
  if (yyn == 226)
    /* "src/main/java/parser/Parser.y":833  */
    {
        ((List<Publisher>)(yystack.valueAt (2))).add(((Publisher)(yystack.valueAt (1))));
        yyval = ((List<Publisher>)(yystack.valueAt (2)));
    };
  break;


  case 227: /* type_declaration: type_name ':' type_spec_init  */
  if (yyn == 227)
    /* "src/main/java/parser/Parser.y":841  */
    {
        this.nameMangler.popScope();

        List<String> left_identifier = new ArrayList<>();
        left_identifier.add(((String)(yystack.valueAt (2))));

        yyval = new Declaration(
            this.symbolTable,
            left_identifier,
            ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.TYPE)
        );
    };
  break;


  case 228: /* type_name: IDENTIFIER  */
  if (yyn == 228)
    /* "src/main/java/parser/Parser.y":857  */
    {
        this.nameMangler.addScope("$1");
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 229: /* type_spec_init: custom_spec_init  */
  if (yyn == 229)
    /* "src/main/java/parser/Parser.y":864  */
                              { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 230: /* type_spec_init: simple_spec_init  */
  if (yyn == 230)
    /* "src/main/java/parser/Parser.y":865  */
                              { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 231: /* type_spec_init: enumerated_spec_init  */
  if (yyn == 231)
    /* "src/main/java/parser/Parser.y":866  */
                              { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 232: /* type_spec_init: subrange_spec_init  */
  if (yyn == 232)
    /* "src/main/java/parser/Parser.y":867  */
                              { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 233: /* type_spec_init: array_spec_init  */
  if (yyn == 233)
    /* "src/main/java/parser/Parser.y":868  */
                              { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 234: /* type_spec_init: structure_specification  */
  if (yyn == 234)
    /* "src/main/java/parser/Parser.y":869  */
                              { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 235: /* type_spec_init: string_spec_init  */
  if (yyn == 235)
    /* "src/main/java/parser/Parser.y":870  */
                              { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 236: /* structure_specification: STRUCT structure_field_declaration_list END_STRUCT  */
  if (yyn == 236)
    /* "src/main/java/parser/Parser.y":875  */
    {
        List<String> structParameters = ((List<String>)(yystack.valueAt (1)));
        Struct structInitialValues = new Struct();

        for (String field : structParameters) {
            String fieldName = this.nameMangler.getNameMangled(field);
            LexemeInfo fieldMetadata = this.symbolTable.get(fieldName);
            structInitialValues.add(field, (Initialization) fieldMetadata.initialValue);
        }

        yyval = new LexemeInfoBuilder()
            .type(Type.STRUCT)
            .parameters(structParameters)
            .initialValue(structInitialValues);
    };
  break;


  case 237: /* structure_field_declaration_list: structure_field_declaration ';'  */
  if (yyn == 237)
    /* "src/main/java/parser/Parser.y":894  */
    {
        List<String> structParameters = new ArrayList<>();
        structParameters.add(((String)(yystack.valueAt (1))));
        yyval = structParameters;
    };
  break;


  case 238: /* structure_field_declaration_list: structure_field_declaration_list structure_field_declaration ';'  */
  if (yyn == 238)
    /* "src/main/java/parser/Parser.y":900  */
    {
        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (1))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 239: /* structure_field_declaration: IDENTIFIER ':' structure_field_spec_init  */
  if (yyn == 239)
    /* "src/main/java/parser/Parser.y":908  */
    {
        List<String> left_identifier = new ArrayList<>();
        left_identifier.add(this.nameMangler.getNameMangled(((String)(yystack.valueAt (2)))));

        // Builds the declaration of the structure field and publishes it
        Declaration fieldDeclaration = new Declaration(
            this.symbolTable,
            left_identifier,
            ((LexemeInfoBuilder)(yystack.valueAt (0))).use(Use.FIELD).source(Source.NONE)
        );

        yyval = ((String)(yystack.valueAt (2)));
    };
  break;


  case 240: /* structure_field_spec_init: custom_spec_init  */
  if (yyn == 240)
    /* "src/main/java/parser/Parser.y":924  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 241: /* structure_field_spec_init: simple_spec_init  */
  if (yyn == 241)
    /* "src/main/java/parser/Parser.y":925  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 242: /* structure_field_spec_init: enumerated_spec_init  */
  if (yyn == 242)
    /* "src/main/java/parser/Parser.y":926  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 243: /* structure_field_spec_init: subrange_spec_init  */
  if (yyn == 243)
    /* "src/main/java/parser/Parser.y":927  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 244: /* structure_field_spec_init: array_spec_init  */
  if (yyn == 244)
    /* "src/main/java/parser/Parser.y":928  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;


  case 245: /* structure_field_spec_init: string_spec_init  */
  if (yyn == 245)
    /* "src/main/java/parser/Parser.y":929  */
                            { yyval = ((LexemeInfoBuilder)(yystack.valueAt (0))); };
  break;



/* "src/main/java/parser/Parser.java":1933  */

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

  private static final short yypact_ninf_ = -317;
  private static final short yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      36,    -2,    72,    57,  -317,  -317,    26,   -10,    18,  -317,
      77,  -317,  -317,    52,  -317,    68,  -317,  -317,   109,    65,
    -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,
    -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,
      78,   135,  -317,  -317,  -317,  -317,  -317,  -317,   136,  -317,
      95,  -317,  -317,  -317,  -317,  -317,  -317,   137,  -317,  -317,
     143,  -317,  -317,   144,  -317,  -317,  -317,  -317,  -317,  -317,
    -317,   -22,  -317,  -317,     9,   110,    23,   115,   281,   461,
    -317,    83,   438,   461,   461,   157,   111,   123,   461,    64,
      64,  -317,   148,  -317,  -317,   319,  -317,   121,  -317,   117,
    -317,  -317,  -317,   164,   119,   120,   122,  -317,  -317,  -317,
    -317,   133,  -317,   124,  -317,  -317,   168,   129,   125,   174,
    -317,  -317,   132,  -317,  -317,   175,  -317,  -317,   130,  -317,
    -317,   177,   177,   165,  -317,   216,   219,  -317,  -317,  -317,
    -317,  -317,  -317,  -317,  -317,  -317,   181,   183,    86,  -317,
    -317,  -317,  -317,  -317,  -317,  -317,   149,  -317,  -317,   461,
     461,   161,  -317,  -317,  -317,   147,  -317,     6,  -317,  -317,
    -317,  -317,  -317,   186,  -317,   172,  -317,  -317,    16,   178,
    -317,   177,  -317,   190,  -317,   248,  -317,   175,   164,  -317,
    -317,  -317,  -317,   419,   175,   175,  -317,   123,    11,   218,
     245,    27,   179,   250,   221,  -317,   255,  -317,  -317,  -317,
    -317,  -317,  -317,   185,  -317,  -317,  -317,  -317,  -317,  -317,
    -317,  -317,  -317,   101,  -317,  -317,  -317,  -317,  -317,  -317,
     228,  -317,  -317,    32,   231,    82,  -317,   256,   233,  -317,
     276,  -317,  -317,  -317,  -317,   206,  -317,   247,  -317,  -317,
     209,   250,   264,   249,  -317,  -317,   355,   461,   278,   250,
     210,   277,   272,   259,    20,  -317,   229,   376,   241,  -317,
     242,  -317,  -317,   280,   243,   311,    22,   244,   308,  -317,
     246,   251,    87,  -317,  -317,  -317,   252,   253,  -317,  -317,
     461,   163,   291,   326,  -317,  -317,  -317,  -317,    37,   261,
    -317,     3,  -317,  -317,   283,   461,   461,   282,  -317,  -317,
    -317,  -317,  -317,   284,   208,  -317,  -317,  -317,   286,    66,
      81,  -317,  -317,  -317,  -317,  -317,   285,   287,   289,  -317,
    -317,   292,  -317,  -317,  -317,  -317,  -317,   303,  -317,   461,
    -317,  -317,  -317,  -317,  -317,  -317,   312,   343,    -3,    -1,
      67,    -3,   318,   146,  -317,   339,  -317,    10,     4,     5,
    -317,   288,   328,  -317,   356,  -317,    67,   146,    67,   146,
    -317,   375,     2,  -317,  -317,  -317,  -317,  -317,   360,   398,
     362,   322,  -317,  -317,  -317,   381,  -317,   365,  -317
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
     222,     0,     0,     0,   223,   228,     0,     0,     0,     1,
       0,     2,   224,     0,   225,     0,     4,   226,     0,   122,
     220,   221,   163,   164,   165,   166,   147,   149,   148,   150,
     137,   138,   139,   140,   141,   142,   143,   144,   145,   146,
       0,     0,   229,   120,   121,   230,   126,   127,   128,   130,
     133,   135,   136,   134,   131,   132,   232,   167,   168,   231,
     172,   173,   233,   178,   179,   123,   125,   124,   235,   214,
     215,   216,   227,   234,     8,     0,     0,     0,     0,     0,
     176,     0,     0,     0,     0,     0,     0,     0,     0,    98,
      98,     5,    12,     7,     6,     0,   236,     0,   237,   205,
     157,   155,   156,     0,     0,     0,     0,   203,   151,   152,
     154,     0,   153,     0,   208,   204,     0,   183,     0,     0,
     174,   129,     0,   170,   175,     0,   182,   218,     0,    99,
     100,     0,     0,   101,     9,     0,    15,    10,   240,   241,
     243,   242,   244,   245,   239,   238,     0,     0,     0,   197,
     199,   200,   201,   202,   159,   160,     0,   158,   161,     0,
       0,     0,   177,   169,   191,   154,   194,     0,   187,   189,
     190,   192,   193,   217,   211,     0,   103,   106,     0,     0,
     102,     0,     3,     0,    13,    42,   206,     0,     0,   196,
     162,   171,   184,     0,     0,     0,   186,     0,     0,     0,
       0,     0,     0,     0,     0,    16,    88,   207,   198,   180,
     185,   133,   181,     0,   188,   219,    96,   104,   212,   213,
     116,   105,   108,   114,   115,   107,   109,   110,   111,   112,
     209,   113,    95,     0,     0,     0,    20,    40,     0,    43,
      11,   195,   118,   119,   117,     0,    97,     0,    14,    21,
       0,    18,    46,     0,    89,   210,     0,     0,     0,    19,
       0,    56,    48,     0,     0,    91,     0,     0,     0,    24,
      25,    27,    26,     0,     0,     0,     0,     0,     0,    57,
       0,     0,     0,    90,    92,    22,     0,     0,    23,    28,
       0,     0,     0,     0,    50,    51,    52,    47,     0,     0,
      65,     0,    45,    93,     0,     0,     0,     0,    32,    33,
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
    -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,
    -317,  -317,  -317,   159,  -200,  -317,  -317,  -317,   141,  -317,
    -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,
    -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,  -317,    61,
    -316,  -302,  -317,  -317,  -317,  -317,  -317,   150,  -317,  -317,
    -317,   323,  -317,  -104,  -167,  -317,  -317,  -317,  -317,  -317,
     -83,  -317,  -317,   -82,  -317,  -317,   222,  -317,   -13,  -317,
    -317,    -9,    -8,   -68,   -84,  -317,   -79,  -317,  -317,    -7,
     -78,  -317,  -317,   338,   -76,  -317,  -317,  -317,   -75,  -317,
    -317,   262,  -317,   -64,  -317,   263,   230,  -317,   -77,  -317,
     235,   -88,   -87,   -67,  -317,   -80,  -317,  -317,  -317,   -74,
    -317,  -317,  -317,  -317,  -317,  -317,   430,  -317,  -317,  -317,
    -317,   367,  -317
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,    11,    74,    91,    92,   134,   135,   136,   184,
     185,   205,   258,   235,   236,   268,   269,   270,   271,   275,
     313,   293,   331,   251,   206,   239,   261,   262,   281,   297,
     324,   278,   279,   318,   300,   337,   320,   340,   381,   352,
     360,   353,   354,   372,   240,   254,   264,   265,    93,    94,
     137,   131,   181,   175,   176,   221,   222,   223,   224,   244,
      42,    43,    44,    45,    46,    47,    48,    49,   104,    51,
      52,   105,   106,   164,   108,   109,   110,   111,   112,   113,
      56,    57,    58,   117,    59,    60,    61,    81,    62,    63,
      64,   118,   212,   166,   167,   168,   169,   170,   171,   148,
     149,    65,    66,   172,   152,    67,   177,   178,   230,    68,
      69,    70,    71,     3,     4,     6,     7,     8,    72,    73,
      76,    77,   144
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
     116,   114,    50,   127,   116,   123,    53,    54,    55,   128,
     107,   115,   138,   139,   121,   150,   151,   140,   124,   141,
     142,   143,   126,   153,    87,   349,   379,   349,   179,    96,
      12,   217,   349,   349,   217,   249,   283,   363,   364,     1,
     321,   350,     5,   355,   322,   323,   165,   356,   366,   368,
     374,   375,   376,   377,   365,   174,   367,   369,    89,   249,
     294,   295,   296,   216,    10,    90,   217,    75,   263,    88,
       5,   174,     9,    18,   316,    14,   174,   202,   317,   232,
     191,   116,    50,   351,   246,   351,    53,    54,    55,   380,
     351,   351,   248,   195,   357,   338,   358,   359,   196,   234,
     150,   151,   339,   199,   334,   200,   335,    15,   153,   336,
     114,    78,    19,   215,   129,   130,   165,   225,   226,   107,
     115,    16,   227,   207,   228,   229,   231,    20,    21,    22,
      23,    24,    25,    26,    27,    28,    29,    17,    30,    31,
      32,    33,    34,    35,    36,    37,    38,    39,    22,    23,
      24,    25,    40,    75,    41,   242,   243,    30,    31,    32,
      33,    34,    35,    36,    37,    38,    39,   100,   255,    79,
     119,   120,   303,   188,   189,   358,   359,   272,   273,    80,
     211,    83,    82,    84,    53,    54,    55,    50,   287,    85,
      86,    53,    54,    55,   308,   309,   310,   311,   312,    95,
      98,    99,   125,   304,   101,   133,   145,   146,   147,   154,
     155,   307,   156,   157,   158,   159,   160,   161,   162,    99,
     163,   174,   173,   180,   182,   186,   326,   327,   183,   187,
     193,   190,   197,   194,   203,   332,    22,    23,    24,    25,
      26,    27,    28,    29,   330,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,   100,   101,   198,   102,   204,
     346,   103,   218,   201,   233,   237,   125,   234,   238,    22,
      23,    24,    25,   241,   245,   247,   250,   252,    30,    31,
      32,    33,    34,    35,    36,    37,    38,    39,   100,    19,
     219,   253,   103,   256,   260,   257,   274,   263,   220,   276,
     384,   280,   277,   282,    20,    21,    22,    23,    24,    25,
      26,    27,    28,    29,   285,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    99,   288,   290,   267,    40,
     292,    41,   291,   298,   299,   301,   302,   314,   315,   305,
     306,   362,    22,    23,    24,    25,    26,    27,    28,    29,
     319,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      39,   100,   101,    19,   102,   348,   357,   103,   325,   329,
     328,   333,   371,   341,   343,   342,   370,   344,    20,    21,
      22,    23,    24,    25,    26,    27,    28,    29,   345,    30,
      31,    32,    33,    34,    35,    36,    37,    38,    39,   266,
     373,   347,   378,    40,   382,    41,   385,   386,   387,   388,
     259,   289,   361,   132,   284,   210,    22,    23,    24,    25,
     286,   122,   192,   208,   213,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,   100,    13,    22,    23,    24,
      25,   267,   383,    97,     0,     0,    30,    31,    32,    33,
      34,    35,    36,    37,    38,    39,   100,     0,   214,    22,
      23,    24,    25,   209,     0,     0,     0,     0,    30,    31,
      32,    33,    34,    35,    36,    37,    38,    39,   100,     0,
      22,    23,    24,    25,    26,    27,    28,    29,     0,    30,
      31,    32,    33,    34,    35,    36,    37,    38,    39,    22,
      23,    24,    25,    26,    27,    28,    29,     0,    30,    31,
      32,    33,    34,    35,    36,    37,    38,    39,   100,   101,
       0,   102,    22,    23,    24,    25,     0,     0,     0,     0,
       0,    30,    31,    32,    33,    34,    35,    36,    37,    38,
      39,   100
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      79,    78,    15,    87,    83,    84,    15,    15,    15,    88,
      78,    78,    95,    95,    82,   103,   103,    95,    85,    95,
      95,    95,    86,   103,    46,    28,    24,    28,   132,     6,
       4,   198,    28,    28,   201,   235,    16,   353,    28,     3,
      37,    44,    44,    44,    41,    42,   125,   349,    44,    44,
     366,   367,   368,   369,    44,    44,   358,   359,    49,   259,
      38,    39,    40,    52,     7,    56,   233,    44,    48,    91,
      44,    44,     0,     5,    37,    85,    44,   181,    41,    52,
     159,   160,    95,    86,    52,    86,    95,    95,    95,    87,
      86,    86,    10,    87,    27,    14,    29,    30,    92,    17,
     188,   188,    21,    87,    38,    89,    40,    89,   188,    43,
     187,    46,    44,   197,    50,    51,   195,   200,   200,   187,
     187,    44,   200,   187,   200,   200,   200,    59,    60,    61,
      62,    63,    64,    65,    66,    67,    68,    85,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    61,    62,
      63,    64,    84,    44,    86,    54,    55,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,   245,    91,
      87,    88,    85,    87,    88,    29,    30,   256,   257,    44,
     193,    86,    46,    46,   193,   193,   193,   200,   267,    46,
      46,   200,   200,   200,    31,    32,    33,    34,    35,    89,
      85,    44,    91,   282,    81,    57,    85,    90,    44,    90,
      90,   290,    90,    80,    90,    47,    87,    92,    44,    44,
      88,    44,    92,    58,     8,    44,   305,   306,     9,    46,
      69,    82,    46,    86,    44,   314,    61,    62,    63,    64,
      65,    66,    67,    68,    36,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    85,    83,    11,
     339,    86,    44,    85,    85,    44,    91,    17,    13,    61,
      62,    63,    64,    88,    46,    44,    20,    44,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    44,
      45,    15,    86,    46,    30,    86,    18,    48,    53,    89,
     379,    29,    25,    44,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68,    85,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    44,    85,    47,    86,    84,
      19,    86,    89,    89,    26,    89,    85,    46,    12,    87,
      87,    23,    61,    62,    63,    64,    65,    66,    67,    68,
      89,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    44,    83,    22,    27,    86,    85,    85,
      88,    85,    44,    88,    85,    88,    88,    85,    59,    60,
      61,    62,    63,    64,    65,    66,    67,    68,    85,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    44,
      44,    89,    27,    84,    44,    86,    44,    85,    27,    44,
     251,   270,   351,    90,   264,   193,    61,    62,    63,    64,
      44,    83,   160,   188,   194,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,     6,    61,    62,    63,
      64,    86,    44,    76,    -1,    -1,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    -1,   195,    61,
      62,    63,    64,    44,    -1,    -1,    -1,    -1,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    -1,
      61,    62,    63,    64,    65,    66,    67,    68,    -1,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    61,
      62,    63,    64,    65,    66,    67,    68,    -1,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      -1,    83,    61,    62,    63,    64,    -1,    -1,    -1,    -1,
      -1,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final short[] yystos_ = yystos_init();
  private static final short[] yystos_init()
  {
    return new short[]
    {
       0,     3,    94,   206,   207,    44,   208,   209,   210,     0,
       7,    95,     4,   209,    85,    89,    44,    85,     5,    44,
      59,    60,    61,    62,    63,    64,    65,    66,    67,    68,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      84,    86,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,   172,   173,   174,   175,   177,
     178,   179,   181,   182,   183,   194,   195,   198,   202,   203,
     204,   205,   211,   212,    96,    44,   213,   214,    46,    91,
      44,   180,    46,    86,    46,    46,    46,    46,    91,    49,
      56,    97,    98,   141,   142,    89,     6,   214,    85,    44,
      80,    81,    83,    86,   161,   164,   165,   166,   167,   168,
     169,   170,   171,   172,   191,   196,   169,   176,   184,    87,
      88,   166,   176,   169,   196,    91,   186,   167,   169,    50,
      51,   144,   144,    57,    99,   100,   101,   143,   153,   156,
     173,   177,   181,   202,   215,    85,    90,    44,   192,   193,
     194,   195,   197,   198,    90,    90,    90,    80,    90,    47,
      87,    92,    44,    88,   166,   169,   186,   187,   188,   189,
     190,   191,   196,    92,    44,   146,   147,   199,   200,   146,
      58,   145,     8,     9,   102,   103,    44,    46,    87,    88,
      82,   169,   184,    69,    86,    87,    92,    46,    85,    87,
      89,    85,   146,    44,    11,   104,   117,   186,   193,    44,
     159,   161,   185,   189,   188,   167,    52,   147,    44,    45,
      53,   148,   149,   150,   151,   153,   156,   173,   177,   181,
     201,   202,    52,    85,    17,   106,   107,    44,    13,   118,
     137,    88,    54,    55,   152,    46,    52,    44,    10,   107,
      20,   116,    44,    15,   138,   191,    46,    86,   105,   106,
      30,   119,   120,    48,   139,   140,    44,    86,   108,   109,
     110,   111,   169,   169,    18,   112,    89,    25,   124,   125,
      29,   121,    44,    16,   140,    85,    44,   169,    85,   111,
      47,    89,    19,   114,    38,    39,    40,   122,    89,    26,
     127,    89,    85,    85,   169,    87,    87,   169,    31,    32,
      33,    34,    35,   113,    46,    12,    37,    41,   126,    89,
     129,    37,    41,    42,   123,    85,   169,   169,    88,    85,
      36,   115,   169,    85,    38,    40,    43,   128,    14,    21,
     130,    88,    88,    85,    85,    85,   169,    89,    22,    28,
      44,    86,   132,   134,   135,    44,   134,    27,    29,    30,
     133,   132,    23,   133,    28,    44,    44,   134,    44,   134,
      88,    44,   136,    44,   133,   133,   133,   133,    27,    24,
      87,   131,    44,    44,   169,    44,    85,    27,    44
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
     148,   148,   148,   148,   149,   149,   150,   151,   152,   152,
     153,   153,   154,   155,   155,   155,   156,   156,   157,   158,
     159,   159,   159,   160,   160,   161,   161,   162,   162,   162,
     162,   163,   163,   163,   163,   164,   164,   165,   165,   165,
     165,   166,   166,   166,   166,   167,   168,   169,   169,   170,
     170,   170,   171,   172,   172,   172,   172,   173,   173,   174,
     175,   176,   177,   177,   178,   179,   180,   180,   181,   181,
     182,   182,   183,   184,   184,   185,   186,   187,   187,   188,
     188,   189,   189,   189,   189,   190,   191,   192,   192,   193,
     193,   193,   193,   194,   195,   196,   196,   197,   198,   199,
     199,   200,   200,   201,   202,   202,   203,   203,   204,   204,
     205,   205,   206,   206,   207,   208,   208,   209,   210,   211,
     211,   211,   211,   211,   211,   211,   212,   213,   213,   214,
     215,   215,   215,   215,   215,   215
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
       1,     0,     1,     1,     3,     3,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     2,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     3,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     2,     2,
       2,     2,     3,     1,     1,     1,     1,     1,     1,     4,
       3,     3,     1,     1,     3,     3,     1,     3,     1,     1,
       6,     6,     3,     1,     3,     1,     3,     1,     3,     1,
       1,     1,     1,     1,     1,     4,     3,     1,     3,     1,
       1,     1,     1,     3,     3,     1,     3,     3,     3,     3,
       5,     1,     3,     1,     1,     1,     1,     4,     3,     6,
       1,     1,     0,     1,     3,     2,     3,     3,     1,     1,
       1,     1,     1,     1,     1,     1,     3,     2,     3,     3,
       1,     1,     1,     1,     1,     1
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


  private static final int YYLAST_ = 541;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 9;
  private static final int YYNTOKENS_ = 93;


}
/* "src/main/java/parser/Parser.y":932  */
