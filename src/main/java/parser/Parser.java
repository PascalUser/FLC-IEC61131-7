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
    import parser.internals.*;

/* "src/main/java/parser/Parser.java":59  */

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
    S_function_block_name(96),     /* function_block_name  */
    S_opt_fb_io_var_declarations_list(97), /* opt_fb_io_var_declarations_list  */
    S_fb_io_var_declarations(98),  /* fb_io_var_declarations  */
    S_opt_other_var_declarations_list(99), /* opt_other_var_declarations_list  */
    S_other_var_declarations(100), /* other_var_declarations  */
    S_opt_function_block_body(101), /* opt_function_block_body  */
    S_opt_fuzzify_block_list(102), /* opt_fuzzify_block_list  */
    S_fuzzify_block(103),          /* fuzzify_block  */
    S_opt_defuzzify_block_list(104), /* opt_defuzzify_block_list  */
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
    S_opt_rule_block_list(118),    /* opt_rule_block_list  */
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
    S_opt_option_block_list(138),  /* opt_option_block_list  */
    S_option_block(139),           /* option_block  */
    S_pragma_list(140),            /* pragma_list  */
    S_pragma(141),                 /* pragma  */
    S_io_var_decl(142),            /* io_var_decl  */
    S_var_declarations(143),       /* var_declarations  */
    S_var_id_decl(144),            /* var_id_decl  */
    S_var_retain_spec(145),        /* var_retain_spec  */
    S_var_constant_spec(146),      /* var_constant_spec  */
    S_var_init_decl_list(147),     /* var_init_decl_list  */
    S_var_init_decl(148),          /* var_init_decl  */
    S_var_spec_init(149),          /* var_spec_init  */
    S_boolean_spec_init(150),      /* boolean_spec_init  */
    S_boolean_specification(151),  /* boolean_specification  */
    S_initialized_boolean(152),    /* initialized_boolean  */
    S_edge(153),                   /* edge  */
    S_custom_spec_init(154),       /* custom_spec_init  */
    S_custom_specification(155),   /* custom_specification  */
    S_initialized_custom(156),     /* initialized_custom  */
    S_simple_spec_init(157),       /* simple_spec_init  */
    S_simple_specification(158),   /* simple_specification  */
    S_initialized_simple(159),     /* initialized_simple  */
    S_elementary_type_name(160),   /* elementary_type_name  */
    S_numeric_type_name(161),      /* numeric_type_name  */
    S_integer_type_name(162),      /* integer_type_name  */
    S_signed_integer_type_name(163), /* signed_integer_type_name  */
    S_unsigned_integer_type_name(164), /* unsigned_integer_type_name  */
    S_real_type_name(165),         /* real_type_name  */
    S_date_type_name(166),         /* date_type_name  */
    S_constant(167),               /* constant  */
    S_string_constant(168),        /* string_constant  */
    S_boolean_constant(169),       /* boolean_constant  */
    S_numeric_constant(170),       /* numeric_constant  */
    S_number_prefix(171),          /* number_prefix  */
    S_time_constant(172),          /* time_constant  */
    S_bit_string_type_name(173),   /* bit_string_type_name  */
    S_subrange_spec_init(174),     /* subrange_spec_init  */
    S_subrange_specification(175), /* subrange_specification  */
    S_subrange_type_decl(176),     /* subrange_type_decl  */
    S_initialized_subrange(177),   /* initialized_subrange  */
    S_range(178),                  /* range  */
    S_enumerated_spec_init(179),   /* enumerated_spec_init  */
    S_enumerated_specification(180), /* enumerated_specification  */
    S_initialized_enumerated(181), /* initialized_enumerated  */
    S_enumerated_values(182),      /* enumerated_values  */
    S_enumerated_values_list(183), /* enumerated_values_list  */
    S_array_spec_init(184),        /* array_spec_init  */
    S_array_specification(185),    /* array_specification  */
    S_initialized_array(186),      /* initialized_array  */
    S_range_list(187),             /* range_list  */
    S_non_generic_type_name(188),  /* non_generic_type_name  */
    S_array_initialization(189),   /* array_initialization  */
    S_array_initial_elements_list(190), /* array_initial_elements_list  */
    S_array_initial_elements(191), /* array_initial_elements  */
    S_array_initial_element(192),  /* array_initial_element  */
    S_repeated_initial_element(193), /* repeated_initial_element  */
    S_structure_initialization(194), /* structure_initialization  */
    S_structure_field_initialization_list(195), /* structure_field_initialization_list  */
    S_initialized_structure_field(196), /* initialized_structure_field  */
    S_initialized_custom_with_constant(197), /* initialized_custom_with_constant  */
    S_initialized_custom_with_identifier(198), /* initialized_custom_with_identifier  */
    S_identifier_with_opt_mangling(199), /* identifier_with_opt_mangling  */
    S_initialized_custom_with_array(200), /* initialized_custom_with_array  */
    S_initialized_custom_with_structure(201), /* initialized_custom_with_structure  */
    S_custom_type_name(202),       /* custom_type_name  */
    S_standard_function_block_spec_init(203), /* standard_function_block_spec_init  */
    S_initialized_standard_function_block(204), /* initialized_standard_function_block  */
    S_identifier_list(205),        /* identifier_list  */
    S_standard_function_block_specification(206), /* standard_function_block_specification  */
    S_string_spec_init(207),       /* string_spec_init  */
    S_string_specification(208),   /* string_specification  */
    S_initialized_string(209),     /* initialized_string  */
    S_type_string_specification(210), /* type_string_specification  */
    S_opt_data_type_declaration(211), /* opt_data_type_declaration  */
    S_data_type_declaration(212),  /* data_type_declaration  */
    S_type_id_decl(213),           /* type_id_decl  */
    S_type_declaration_list(214),  /* type_declaration_list  */
    S_type_declaration(215),       /* type_declaration  */
    S_type_name_declaration(216),  /* type_name_declaration  */
    S_type_spec_init(217),         /* type_spec_init  */
    S_structure_specification(218), /* structure_specification  */
    S_structure_field_declaration_list(219), /* structure_field_declaration_list  */
    S_structure_field_declaration(220), /* structure_field_declaration  */
    S_field_name(221),             /* field_name  */
    S_structure_field_spec_init(222); /* structure_field_spec_init  */


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
      SymbolKind.S_io_var_decl,
      SymbolKind.S_var_declarations,
      SymbolKind.S_var_id_decl,
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
      SymbolKind.S_subrange_type_decl,
      SymbolKind.S_initialized_subrange,
      SymbolKind.S_range,
      SymbolKind.S_enumerated_spec_init,
      SymbolKind.S_enumerated_specification,
      SymbolKind.S_initialized_enumerated,
      SymbolKind.S_enumerated_values,
      SymbolKind.S_enumerated_values_list,
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
      SymbolKind.S_structure_initialization,
      SymbolKind.S_structure_field_initialization_list,
      SymbolKind.S_initialized_structure_field,
      SymbolKind.S_initialized_custom_with_constant,
      SymbolKind.S_initialized_custom_with_identifier,
      SymbolKind.S_identifier_with_opt_mangling,
      SymbolKind.S_initialized_custom_with_array,
      SymbolKind.S_initialized_custom_with_structure,
      SymbolKind.S_custom_type_name,
      SymbolKind.S_standard_function_block_spec_init,
      SymbolKind.S_initialized_standard_function_block,
      SymbolKind.S_identifier_list,
      SymbolKind.S_standard_function_block_specification,
      SymbolKind.S_string_spec_init,
      SymbolKind.S_string_specification,
      SymbolKind.S_initialized_string,
      SymbolKind.S_type_string_specification,
      SymbolKind.S_opt_data_type_declaration,
      SymbolKind.S_data_type_declaration,
      SymbolKind.S_type_id_decl,
      SymbolKind.S_type_declaration_list,
      SymbolKind.S_type_declaration,
      SymbolKind.S_type_name_declaration,
      SymbolKind.S_type_spec_init,
      SymbolKind.S_structure_specification,
      SymbolKind.S_structure_field_declaration_list,
      SymbolKind.S_structure_field_declaration,
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
  "fb_io_var_declarations", "opt_other_var_declarations_list",
  "other_var_declarations", "opt_function_block_body",
  "opt_fuzzify_block_list", "fuzzify_block", "opt_defuzzify_block_list",
  "defuzzify_block", "opt_linguistic_term_list", "linguistic_term_list",
  "linguistic_term", "membership_function", "singleton", "point_list",
  "point", "defuzzification_method", "defuzz_method", "default_value",
  "default_val", "opt_range", "opt_rule_block_list", "rule_block",
  "operator_definition", "opt_operator_or", "operator_and_opt", "or_type",
  "and_type", "activation_method_opt", "activation_method", "act_type",
  "accumulation_method", "accu_type", "rule_list", "rule", "opt_weighting",
  "condition", "condition_tail", "x", "subcondition", "conclusion_list",
  "opt_option_block_list", "option_block", "pragma_list", "pragma",
  "io_var_decl", "var_declarations", "var_id_decl", "var_retain_spec",
  "var_constant_spec", "var_init_decl_list", "var_init_decl",
  "var_spec_init", "boolean_spec_init", "boolean_specification",
  "initialized_boolean", "edge", "custom_spec_init",
  "custom_specification", "initialized_custom", "simple_spec_init",
  "simple_specification", "initialized_simple", "elementary_type_name",
  "numeric_type_name", "integer_type_name", "signed_integer_type_name",
  "unsigned_integer_type_name", "real_type_name", "date_type_name",
  "constant", "string_constant", "boolean_constant", "numeric_constant",
  "number_prefix", "time_constant", "bit_string_type_name",
  "subrange_spec_init", "subrange_specification", "subrange_type_decl",
  "initialized_subrange", "range", "enumerated_spec_init",
  "enumerated_specification", "initialized_enumerated",
  "enumerated_values", "enumerated_values_list", "array_spec_init",
  "array_specification", "initialized_array", "range_list",
  "non_generic_type_name", "array_initialization",
  "array_initial_elements_list", "array_initial_elements",
  "array_initial_element", "repeated_initial_element",
  "structure_initialization", "structure_field_initialization_list",
  "initialized_structure_field", "initialized_custom_with_constant",
  "initialized_custom_with_identifier", "identifier_with_opt_mangling",
  "initialized_custom_with_array", "initialized_custom_with_structure",
  "custom_type_name", "standard_function_block_spec_init",
  "initialized_standard_function_block", "identifier_list",
  "standard_function_block_specification", "string_spec_init",
  "string_specification", "initialized_string",
  "type_string_specification", "opt_data_type_declaration",
  "data_type_declaration", "type_id_decl", "type_declaration_list",
  "type_declaration", "type_name_declaration", "type_spec_init",
  "structure_specification", "structure_field_declaration_list",
  "structure_field_declaration", "field_name", "structure_field_spec_init", null
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
/* "%code init" blocks.  */
/* "src/main/java/parser/Parser.y":26  */

    this.contexts = new ContextHandler();

/* "src/main/java/parser/Parser.java":884  */

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
          case 3: /* function_block_declaration: FUNCTION_BLOCK function_block_name opt_fb_io_var_declarations_list opt_other_var_declarations_list opt_function_block_body END_FUNCTION_BLOCK  */
  if (yyn == 3)
    /* "src/main/java/parser/Parser.y":103  */
    {
        /**
         * Pops the function block context
        **/

        this.contexts.pop();
    };
  break;


  case 4: /* function_block_name: IDENTIFIER  */
  if (yyn == 4)
    /* "src/main/java/parser/Parser.y":114  */
    {
        /**
         * Builds a new context and adds the function block name to the outer scope
        **/

        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.outerScopes().addScope(((String)(yystack.valueAt (0))));

        this.contexts.add(ctx);
    };
  break;


  case 95: /* io_var_decl: VAR_INPUT  */
  if (yyn == 95)
    /* "src/main/java/parser/Parser.y":361  */
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.IN).use(Use.VARIABLE);
    };
  break;


  case 96: /* io_var_decl: VAR_OUTPUT  */
  if (yyn == 96)
    /* "src/main/java/parser/Parser.y":370  */
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.OUT).use(Use.VARIABLE);
    };
  break;


  case 98: /* var_id_decl: VAR  */
  if (yyn == 98)
    /* "src/main/java/parser/Parser.y":386  */
    {
        /**
         * Adds to the current context the source and use of the inner block declared identifiers
        **/

        ParsingContext ctx = contexts.current();
        ctx.metadataBuilder().source(Source.INTERNAL).use(Use.VARIABLE);
    };
  break;


  case 100: /* var_retain_spec: RETAIN  */
  if (yyn == 100)
    /* "src/main/java/parser/Parser.y":399  */
    {
        // TODO: ctx with retain spec
    };
  break;


  case 101: /* var_retain_spec: NON_RETAIN  */
  if (yyn == 101)
    /* "src/main/java/parser/Parser.y":403  */
    {
        // TODO: ctx with non retain spec
    };
  break;


  case 102: /* var_constant_spec: %empty  */
  if (yyn == 102)
    /* "src/main/java/parser/Parser.y":410  */
    {
        // TODO: ctx with non contant spec
    };
  break;


  case 103: /* var_constant_spec: CONSTANT  */
  if (yyn == 103)
    /* "src/main/java/parser/Parser.y":414  */
    {
        // TODO: ctx with contant spec
    };
  break;


  case 106: /* var_init_decl: identifier_list ':' var_spec_init  */
  if (yyn == 106)
    /* "src/main/java/parser/Parser.y":426  */
    {
        /**
         * Publishes the variables into the symbol table using the loaded context
        **/

        ParsingContext ctx = this.contexts.current();
        Publisher.publish(ctx);

        ctx.declaredIdentifiers().clear();
    };
  break;


  case 123: /* custom_specification: IDENTIFIER  */
  if (yyn == 123)
    /* "src/main/java/parser/Parser.y":475  */
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (0))))
            .initialValue(this.symbolTable.get(((String)(yystack.valueAt (0)))).initialValue);
    };
  break;


  case 129: /* simple_specification: elementary_type_name  */
  if (yyn == 129)
    /* "src/main/java/parser/Parser.y":502  */
    {
        /**
         * Loads the left identifiers type and subtype
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(((Subtype)(yystack.valueAt (0))));
    };
  break;


  case 130: /* initialized_simple: elementary_type_name ASSIGN_OP constant  */
  if (yyn == 130)
    /* "src/main/java/parser/Parser.y":516  */
    {
        /**
         * Loads the left identifiers type, subtype and initialValue
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(((Subtype)(yystack.valueAt (2))))
            .initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 131: /* elementary_type_name: numeric_type_name  */
  if (yyn == 131)
    /* "src/main/java/parser/Parser.y":532  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 132: /* elementary_type_name: date_type_name  */
  if (yyn == 132)
    /* "src/main/java/parser/Parser.y":533  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 133: /* elementary_type_name: bit_string_type_name  */
  if (yyn == 133)
    /* "src/main/java/parser/Parser.y":534  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 134: /* numeric_type_name: integer_type_name  */
  if (yyn == 134)
    /* "src/main/java/parser/Parser.y":538  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 135: /* numeric_type_name: real_type_name  */
  if (yyn == 135)
    /* "src/main/java/parser/Parser.y":539  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 136: /* integer_type_name: signed_integer_type_name  */
  if (yyn == 136)
    /* "src/main/java/parser/Parser.y":543  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 137: /* integer_type_name: unsigned_integer_type_name  */
  if (yyn == 137)
    /* "src/main/java/parser/Parser.y":544  */
                                    { yyval = ((Subtype)(yystack.valueAt (0))); };
  break;


  case 138: /* signed_integer_type_name: SINT  */
  if (yyn == 138)
    /* "src/main/java/parser/Parser.y":548  */
           { yyval = Subtype.SINT; };
  break;


  case 139: /* signed_integer_type_name: INT  */
  if (yyn == 139)
    /* "src/main/java/parser/Parser.y":549  */
           { yyval = Subtype.INT;  };
  break;


  case 140: /* signed_integer_type_name: DINT  */
  if (yyn == 140)
    /* "src/main/java/parser/Parser.y":550  */
           { yyval = Subtype.DINT; };
  break;


  case 141: /* signed_integer_type_name: LINT  */
  if (yyn == 141)
    /* "src/main/java/parser/Parser.y":551  */
           { yyval = Subtype.LINT; };
  break;


  case 142: /* unsigned_integer_type_name: USINT  */
  if (yyn == 142)
    /* "src/main/java/parser/Parser.y":555  */
            { yyval = Subtype.USINT; };
  break;


  case 143: /* unsigned_integer_type_name: UINT  */
  if (yyn == 143)
    /* "src/main/java/parser/Parser.y":556  */
            { yyval = Subtype.UINT;  };
  break;


  case 144: /* unsigned_integer_type_name: UDINT  */
  if (yyn == 144)
    /* "src/main/java/parser/Parser.y":557  */
            { yyval = Subtype.UDINT; };
  break;


  case 145: /* unsigned_integer_type_name: ULINT  */
  if (yyn == 145)
    /* "src/main/java/parser/Parser.y":558  */
            { yyval = Subtype.ULINT; };
  break;


  case 146: /* real_type_name: REAL  */
  if (yyn == 146)
    /* "src/main/java/parser/Parser.y":562  */
            { yyval = Subtype.REAL;  };
  break;


  case 147: /* real_type_name: LREAL  */
  if (yyn == 147)
    /* "src/main/java/parser/Parser.y":563  */
            { yyval = Subtype.LREAL; };
  break;


  case 148: /* date_type_name: TIME  */
  if (yyn == 148)
    /* "src/main/java/parser/Parser.y":567  */
                    { yyval = Subtype.TIME;          };
  break;


  case 149: /* date_type_name: DATE  */
  if (yyn == 149)
    /* "src/main/java/parser/Parser.y":568  */
                    { yyval = Subtype.DATE;          };
  break;


  case 150: /* date_type_name: TIME_OF_DAY  */
  if (yyn == 150)
    /* "src/main/java/parser/Parser.y":569  */
                    { yyval = Subtype.TIME_OF_DAY;   };
  break;


  case 151: /* date_type_name: DATE_AND_TIME  */
  if (yyn == 151)
    /* "src/main/java/parser/Parser.y":570  */
                    { yyval = Subtype.DATE_AND_TIME; };
  break;


  case 152: /* constant: string_constant  */
  if (yyn == 152)
    /* "src/main/java/parser/Parser.y":576  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 153: /* constant: boolean_constant  */
  if (yyn == 153)
    /* "src/main/java/parser/Parser.y":577  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 154: /* constant: time_constant  */
  if (yyn == 154)
    /* "src/main/java/parser/Parser.y":578  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 155: /* constant: numeric_constant  */
  if (yyn == 155)
    /* "src/main/java/parser/Parser.y":579  */
                       { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 156: /* string_constant: STRING_LITERAL  */
  if (yyn == 156)
    /* "src/main/java/parser/Parser.y":583  */
                   { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 157: /* boolean_constant: BOOLEAN_LITERAL  */
  if (yyn == 157)
    /* "src/main/java/parser/Parser.y":587  */
                    { yyval = ((String)(yystack.valueAt (0))); };
  break;


  case 159: /* numeric_constant: number_prefix NUMERIC_LITERAL  */
  if (yyn == 159)
    /* "src/main/java/parser/Parser.y":592  */
    {
        // TODO: hacer conversion de esta constante en codigo
        yyval = "";
    };
  break;


  case 163: /* time_constant: date_type_name '#' TIME_LITERAL  */
  if (yyn == 163)
    /* "src/main/java/parser/Parser.y":605  */
                                    {
        // TODO: accion semantica que verifica que prefix es del mismo tipo que time_literal
    };
  break;


  case 164: /* bit_string_type_name: BYTE  */
  if (yyn == 164)
    /* "src/main/java/parser/Parser.y":611  */
            { yyval = Subtype.BYTE;  };
  break;


  case 165: /* bit_string_type_name: WORD  */
  if (yyn == 165)
    /* "src/main/java/parser/Parser.y":612  */
            { yyval = Subtype.WORD;  };
  break;


  case 166: /* bit_string_type_name: DWORD  */
  if (yyn == 166)
    /* "src/main/java/parser/Parser.y":613  */
            { yyval = Subtype.DWORD; };
  break;


  case 167: /* bit_string_type_name: LWORD  */
  if (yyn == 167)
    /* "src/main/java/parser/Parser.y":614  */
            { yyval = Subtype.LWORD; };
  break;


  case 171: /* subrange_type_decl: integer_type_name  */
  if (yyn == 171)
    /* "src/main/java/parser/Parser.y":630  */
    {
        /**
         * Loads the left identifiers subrange subtype
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().type(Type.SUBRANGE).subtype(((Subtype)(yystack.valueAt (0))));
    };
  break;


  case 172: /* initialized_subrange: subrange_specification ASSIGN_OP numeric_constant  */
  if (yyn == 172)
    /* "src/main/java/parser/Parser.y":642  */
    {
        /**
         * Loads the left identifiers subrange initialization
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue(((String)(yystack.valueAt (0))));
    };
  break;


  case 173: /* range: numeric_constant RANGE_OP numeric_constant  */
  if (yyn == 173)
    /* "src/main/java/parser/Parser.y":654  */
    {
        /**
         * Loads the left identifiers subrange numeric range
        **/

        // TODO: hacer chequeo semantico de rangos
        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SUBRANGE)
            .inferiorLimit(Collections.singletonList(((String)(yystack.valueAt (2)))))
            .superiorLimit(Collections.singletonList(((String)(yystack.valueAt (0)))))
            .initialValue(((String)(yystack.valueAt (2))));
    };
  break;


  case 176: /* enumerated_specification: '(' enumerated_values ')'  */
  if (yyn == 176)
    /* "src/main/java/parser/Parser.y":675  */
                              {
        /**
         * Loads the enumerated metadata to the current context.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.ENUMERATE)
            .subtype(Subtype.INT)
            .parameters(((List<String>)(yystack.valueAt (1))))
            .initialValue(
                new VariableInitialization(((List<String>)(yystack.valueAt (1))).get(0)
            ));
    };
  break;


  case 177: /* initialized_enumerated: enumerated_specification ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 177)
    /* "src/main/java/parser/Parser.y":693  */
    {
        /**
         * Verifies that the initializer value is correct and loads it to the context.
        **/

        ParsingContext ctx = this.contexts.current();

        if (((String)(yystack.valueAt (0))).indexOf('#') != -1) {
            // TODO: control de error. El enumerado anonimo no puede ser inicializado con un mangling
        }
        List<String> enumeratedValues = ctx.metadataBuilder().build().parameters;
        Integer indexValue = enumeratedValues.indexOf(((String)(yystack.valueAt (0))));
        if (indexValue == -1) {
            // TODO: control de error. El enumerado anonimo no puede ser inicializado con un valor inexistente
        }
        // TODO: ver cómo verificar que el índice está en el léxico
        ctx.metadataBuilder().initialValue(new VariableInitialization(indexValue.toString()));
    };
  break;


  case 178: /* enumerated_values: enumerated_values_list  */
  if (yyn == 178)
    /* "src/main/java/parser/Parser.y":715  */
    {
        /**
         * Just drops the context built for the enumerated list.
        **/

        this.contexts.pop();
    };
  break;


  case 179: /* enumerated_values_list: IDENTIFIER  */
  if (yyn == 179)
    /* "src/main/java/parser/Parser.y":726  */
    {
        /**
         * Creates a new context with the data associated to the enums value list and then publish the identifier found
         * to the symbol table.
        **/

        ParsingContext oldCtx = this.contexts.current();
        String outerScopes = oldCtx.outerScopes().getCurrentScope();

        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.NONE)
            .use(Use.MACRO)
            .source(Source.NONE)
            .initialValue(
                // TODO: ver interacción con lexer para la publicación de la constante
                new VariableInitialization("0")
            );
        ctx.outerScopes().addScope(outerScopes);

        Publisher.publish(ctx);
        this.contexts.add(ctx);

        List<String> enumeratedValues = new ArrayList<>();
        enumeratedValues.add(((String)(yystack.valueAt (0))));

        yyval = enumeratedValues;
    };
  break;


  case 180: /* enumerated_values_list: enumerated_values_list ',' IDENTIFIER  */
  if (yyn == 180)
    /* "src/main/java/parser/Parser.y":757  */
    {
        // TODO: ver interacción con el lexer nuevamente
        Integer newIndex = ((List<String>)(yystack.valueAt (2))).size();

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().set(0, ((String)(yystack.valueAt (0))));
        ctx.metadataBuilder().initialValue(new VariableInitialization(newIndex.toString()));

        Publisher.publish(ctx);

        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (0))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 199: /* structure_initialization: '(' structure_field_initialization_list ')'  */
  if (yyn == 199)
    /* "src/main/java/parser/Parser.y":821  */
                                                { yyval = ((StructInitialization)(yystack.valueAt (1))); };
  break;


  case 200: /* structure_field_initialization_list: initialized_structure_field  */
  if (yyn == 200)
    /* "src/main/java/parser/Parser.y":826  */
    {
        /**
         * Realizes that the custom type was a field actually. Then gets it's name out of the search scope and it's
         * initial value from the LexemeInfo built.
        **/

        ParsingContext ctx = this.contexts.current();

        String fieldName = ctx.searchScope().popScope();
        StructInitialization initialization = new StructInitialization();
        initialization.addFieldInitialization(fieldName, (Initialization) ctx.metadataBuilder().build().initialValue);
        yyval = initialization;
    };
  break;


  case 201: /* structure_field_initialization_list: structure_field_initialization_list ',' initialized_structure_field  */
  if (yyn == 201)
    /* "src/main/java/parser/Parser.y":840  */
    {
        ParsingContext ctx = this.contexts.current();
        String fieldName = ctx.searchScope().popScope();
        ((StructInitialization)(yystack.valueAt (2))).addFieldInitialization(fieldName, (Initialization) ctx.metadataBuilder().build().initialValue);
        yyval = ((StructInitialization)(yystack.valueAt (2)));
    };
  break;


  case 206: /* initialized_custom_with_constant: custom_type_name ASSIGN_OP constant  */
  if (yyn == 206)
    /* "src/main/java/parser/Parser.y":857  */
    {
        /**
         * Adds to the context the left identifiers initial value.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder().initialValue(new VariableInitialization(((String)(yystack.valueAt (0)))));
    };
  break;


  case 207: /* initialized_custom_with_identifier: custom_type_name ASSIGN_OP identifier_with_opt_mangling  */
  if (yyn == 207)
    /* "src/main/java/parser/Parser.y":869  */
    {
        ParsingContext ctx = this.contexts.current();

        int octothorpeIdx = ((String)(yystack.valueAt (0))).indexOf('#');
        String mangledIdentifier = (octothorpeIdx != -1)
            ? ((String)(yystack.valueAt (0)))
            : ctx.searchScope().getNameMangled(((String)(yystack.valueAt (0))));

        if (this.symbolTable.get(mangledIdentifier) == null) {
            // TODO: control de error. Enumerado literal inexistente
        }
        ctx.metadataBuilder().initialValue(new VariableInitialization(mangledIdentifier));
    };
  break;


  case 208: /* identifier_with_opt_mangling: IDENTIFIER  */
  if (yyn == 208)
    /* "src/main/java/parser/Parser.y":886  */
    {
        yyval = ((String)(yystack.valueAt (0)));
    };
  break;


  case 209: /* identifier_with_opt_mangling: IDENTIFIER '#' IDENTIFIER  */
  if (yyn == 209)
    /* "src/main/java/parser/Parser.y":890  */
    {
        yyval = ((String)(yystack.valueAt (2))) + "#" + ((String)(yystack.valueAt (0)));
    };
  break;


  case 211: /* initialized_custom_with_structure: custom_type_name ASSIGN_OP structure_initialization  */
  if (yyn == 211)
    /* "src/main/java/parser/Parser.y":901  */
    {
        /**
         * Builds the LexemeInfo and it's initialization with the the custom type associated and the structure
         * initialization.
        **/

        ParsingContext ctx = this.contexts.current();

        String structScope = ctx.searchScope().popScope();
        LexemeInfo structMetadata = this.symbolTable.get(structScope);
        Initialization typeInitialValues = (Initialization) ctx.metadataBuilder().build().initialValue;

        StructInitialization initialization = new StructInitialization();
        for (String field : structMetadata.parameters) {
            String fieldInitialValue = ((StructInitialization)(yystack.valueAt (0))).selectVariable(field).getVariableValue();
            if (fieldInitialValue.isEmpty()) {
                fieldInitialValue = typeInitialValues.selectVariable(field).getVariableValue();
                continue;
            }
            initialization.addFieldInitialization(field, new VariableInitialization(fieldInitialValue));
        }
        ctx.metadataBuilder().initialValue(initialization);
    };
  break;


  case 212: /* custom_type_name: IDENTIFIER  */
  if (yyn == 212)
    /* "src/main/java/parser/Parser.y":928  */
    {
        /**
         * Builds the LexemeInfo associated to the identifier and appends the underlying scope to the search scope.
         *
         * If an identifier is used and it's subtype is custom, then it's hiding information. That's why we search for
         * the underlying scope, so that we can look it up later.
        **/

        String underlyingScope = ((String)(yystack.valueAt (0)));
        LexemeInfo underlyingMetadata = this.symbolTable.get(((String)(yystack.valueAt (0))));

        ParsingContext ctx = this.contexts.current();
        ctx.metadataBuilder()
            .type(Type.SIMPLE)
            .subtype(Subtype.CUSTOM)
            .customType(((String)(yystack.valueAt (0))))
            .initialValue(underlyingMetadata.initialValue);

        while (underlyingMetadata.subtype == Subtype.CUSTOM) {
            underlyingScope = underlyingMetadata.customType;
            underlyingMetadata = this.symbolTable.get(underlyingScope);
        }
        ctx.searchScope().addScope(underlyingScope);
    };
  break;


  case 216: /* identifier_list: IDENTIFIER  */
  if (yyn == 216)
    /* "src/main/java/parser/Parser.y":965  */
    {
        ParsingContext ctx = contexts.current();
        ctx.declaredIdentifiers().clear();
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
    };
  break;


  case 217: /* identifier_list: identifier_list ',' IDENTIFIER  */
  if (yyn == 217)
    /* "src/main/java/parser/Parser.y":971  */
    {
        ParsingContext ctx = contexts.current();
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
    };
  break;


  case 229: /* data_type_declaration: type_id_decl type_declaration_list END_TYPE  */
  if (yyn == 229)
    /* "src/main/java/parser/Parser.y":1009  */
    {
        /**
         * After reducing the whole block, the type block context needs to popped
        **/

        this.contexts.pop();
    };
  break;


  case 230: /* type_id_decl: TYPE  */
  if (yyn == 230)
    /* "src/main/java/parser/Parser.y":1020  */
    {
        ParsingContext ctx = new ParsingContext(this.symbolTable);
        ctx.metadataBuilder()
            .use(Use.TYPE)
            .source(Source.NONE);

        this.contexts.add(ctx);
    };
  break;


  case 233: /* type_declaration: type_name_declaration ':' type_spec_init  */
  if (yyn == 233)
    /* "src/main/java/parser/Parser.y":1037  */
    {
        /**
         * The type declaration is published after being reduced and it's outerScopes unappended.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.outerScopes().popScope();
        Publisher.publish(ctx);

        ctx.declaredIdentifiers().clear();
    };
  break;


  case 234: /* type_name_declaration: IDENTIFIER  */
  if (yyn == 234)
    /* "src/main/java/parser/Parser.y":1052  */
    {
        /**
         * Adds the identifier as the current scope so that everything declared inside this scope belongs to the outer
         * scope.
        **/

        ParsingContext ctx = this.contexts.current();
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        ctx.outerScopes().addScope(((String)(yystack.valueAt (0))));
    };
  break;


  case 242: /* structure_specification: STRUCT structure_field_declaration_list END_STRUCT  */
  if (yyn == 242)
    /* "src/main/java/parser/Parser.y":1076  */
    {
        /**
         * Builds the declaration of the structure and publishes it to the symbol table. For that to happen every field
         * needs to be searched to get their initial values and appended to the structure initial value.
        **/

        ParsingContext ctx = this.contexts.current();

        List<String> structParameters = ((List<String>)(yystack.valueAt (1)));
        StructInitialization initialization = new StructInitialization();

        for (String field : structParameters) {
            String fieldName = ctx.outerScopes().getNameMangled(field);
            LexemeInfo fieldMetadata = this.symbolTable.get(fieldName);
            initialization.addFieldInitialization(field, (Initialization) fieldMetadata.initialValue);
        }

        ctx.metadataBuilder()
            .type(Type.STRUCT)
            .parameters(structParameters)
            .initialValue(initialization);
    };
  break;


  case 243: /* structure_field_declaration_list: structure_field_declaration ';'  */
  if (yyn == 243)
    /* "src/main/java/parser/Parser.y":1102  */
    {
        List<String> structParameters = new ArrayList<>();
        structParameters.add(((String)(yystack.valueAt (1))));
        yyval = structParameters;
    };
  break;


  case 244: /* structure_field_declaration_list: structure_field_declaration_list structure_field_declaration ';'  */
  if (yyn == 244)
    /* "src/main/java/parser/Parser.y":1108  */
    {
        ((List<String>)(yystack.valueAt (2))).add(((String)(yystack.valueAt (1))));
        yyval = ((List<String>)(yystack.valueAt (2)));
    };
  break;


  case 245: /* structure_field_declaration: field_name ':' structure_field_spec_init  */
  if (yyn == 245)
    /* "src/main/java/parser/Parser.y":1116  */
    {
        /**
         * Publishes the field to the symbol table.
        **/

        ParsingContext ctx = this.contexts.current();
        Publisher.publish(ctx);

        this.contexts.pop();
        yyval = ((String)(yystack.valueAt (2)));
    };
  break;


  case 246: /* field_name: IDENTIFIER  */
  if (yyn == 246)
    /* "src/main/java/parser/Parser.y":1131  */
    {
        /**
         * Builds the new field context
        **/

        ParsingContext ctx = this.contexts.current();
        String outerScopes = ctx.outerScopes().getCurrentScope();

        ctx = new ParsingContext(this.symbolTable);
        ctx.declaredIdentifiers().add(((String)(yystack.valueAt (0))));
        ctx.metadataBuilder().use(Use.FIELD).source(Source.NONE);
        ctx.outerScopes().addScope(outerScopes);

        yyval = ((String)(yystack.valueAt (0)));
    };
  break;



/* "src/main/java/parser/Parser.java":1923  */

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

  private static final short yypact_ninf_ = -314;
  private static final short yytable_ninf_ = -213;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
      53,  -314,    80,    76,  -314,    51,  -314,    59,  -314,  -314,
      32,    41,    49,  -314,  -314,  -314,    75,  -314,    69,    33,
    -314,   110,   122,  -314,  -314,  -314,  -314,  -314,  -314,  -314,
    -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,
    -314,  -314,  -314,    88,   136,  -314,  -314,  -314,  -314,  -314,
    -314,   142,  -314,   105,  -314,  -314,  -314,  -314,  -314,  -314,
     146,   107,  -314,  -314,   149,  -314,  -314,   150,  -314,  -314,
    -314,  -314,   151,  -314,  -314,  -314,   -19,  -314,  -314,  -314,
    -314,  -314,   137,   108,  -314,    37,   114,   112,   459,  -314,
     116,   113,   436,   459,   459,   158,   117,   276,   124,   459,
    -314,  -314,   195,   198,  -314,   152,  -314,  -314,   165,  -314,
     126,  -314,   314,  -314,   123,   125,   169,   132,   127,   133,
     134,  -314,   174,  -314,  -314,   129,  -314,  -314,  -314,  -314,
    -314,  -314,   135,   138,  -314,   170,  -314,   180,  -314,  -314,
    -314,  -314,   160,  -314,   181,  -314,   216,  -314,   165,  -314,
     144,  -314,   -49,  -314,  -314,  -314,  -314,  -314,  -314,  -314,
    -314,  -314,  -314,   459,  -314,  -314,   459,   185,  -314,   175,
    -314,   214,  -314,   173,  -314,     7,  -314,  -314,  -314,  -314,
    -314,  -314,    86,  -314,  -314,  -314,  -314,  -314,   217,   222,
     243,   218,  -314,   256,   186,   -13,   226,   240,  -314,  -314,
     392,  -314,  -314,   170,   170,  -314,   180,  -314,   170,   124,
     228,   101,  -314,   266,   244,  -314,   272,    -7,  -314,  -314,
    -314,  -314,  -314,  -314,  -314,   121,  -314,  -314,  -314,  -314,
    -314,  -314,  -314,  -314,   245,  -314,  -314,  -314,  -314,  -314,
     201,  -314,  -314,  -314,  -314,   246,  -314,  -314,   204,   243,
     264,   248,  -314,  -314,  -314,  -314,  -314,   211,  -314,    -9,
     459,   280,   243,   220,   296,   293,   279,    25,  -314,  -314,
     242,   350,   247,  -314,   239,  -314,  -314,   281,   241,   310,
     111,   271,   305,  -314,   274,   249,   411,  -314,  -314,  -314,
     258,   277,  -314,  -314,   459,   131,   287,   323,  -314,  -314,
    -314,  -314,    83,   278,  -314,   115,  -314,  -314,   251,   459,
     459,   273,  -314,  -314,  -314,  -314,  -314,   283,   203,  -314,
    -314,  -314,   284,    79,   100,  -314,  -314,  -314,  -314,  -314,
     282,   295,   286,  -314,  -314,   311,  -314,  -314,  -314,  -314,
    -314,   312,  -314,   459,  -314,  -314,  -314,  -314,  -314,  -314,
     306,   343,    -2,     4,   140,    -2,   349,   148,  -314,   339,
    -314,     2,     5,     6,  -314,   313,   355,  -314,   358,  -314,
     140,   148,   140,   148,  -314,   376,     1,  -314,  -314,  -314,
    -314,  -314,   360,   371,   361,   321,  -314,  -314,  -314,   380,
    -314,   364,  -314
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
     227,   230,     0,     0,   228,     0,     1,     0,     2,   234,
       0,     0,     0,     4,     5,   229,     0,   231,     0,     8,
     232,     0,   123,   225,   226,   164,   165,   166,   167,   148,
     150,   149,   151,   138,   139,   140,   141,   142,   143,   144,
     145,   146,   147,     0,     0,   235,   121,   122,   236,   127,
     128,   129,   131,   134,   136,   137,   135,   132,   133,   238,
     168,     0,   169,   237,   174,   175,   239,   181,   182,   124,
     126,   125,     0,   241,   219,   220,   221,   233,   240,    95,
      96,     6,    12,    99,   246,     0,     0,     0,     0,   179,
       0,   178,     0,     0,     0,     0,     0,     0,     0,     0,
      98,     9,     0,    15,    10,   102,   100,   101,     0,   242,
       0,   243,     0,   158,     0,     0,     0,     0,     0,   186,
       0,   176,     0,   156,   157,     0,   130,   152,   153,   155,
     154,   172,     0,   208,   177,     0,   185,     0,   206,   211,
     207,   223,     0,     3,     0,    13,    42,   103,     0,   216,
       0,   104,     0,   244,   247,   248,   250,   249,   251,   252,
     245,   160,   161,     0,   159,   162,     0,     0,   180,     0,
     170,     0,   194,   155,   197,     0,   190,   192,   193,   195,
     196,   212,     0,   200,   202,   203,   204,   205,     0,   222,
       0,     0,    16,    88,     0,     0,     0,     0,   173,   187,
       0,   163,   209,     0,     0,   189,     0,   199,     0,     0,
       0,     0,    20,    40,     0,    43,    11,     0,     7,   105,
     217,   218,   117,   106,   109,   115,   116,   108,   110,   111,
     112,   113,   107,   214,   213,   114,   183,   188,   134,   184,
       0,   191,   201,   210,   224,     0,    14,    21,     0,    18,
      46,     0,    89,    97,   119,   120,   118,     0,   198,     0,
       0,     0,    19,     0,    56,    48,     0,     0,    91,   215,
       0,     0,     0,    24,    25,    27,    26,     0,     0,     0,
       0,     0,     0,    57,     0,     0,     0,    90,    92,    22,
       0,     0,    23,    28,     0,     0,     0,     0,    50,    51,
      52,    47,     0,     0,    65,     0,    45,    93,     0,     0,
       0,     0,    32,    33,    34,    35,    36,     0,     0,    17,
      60,    59,     0,     0,     0,    53,    54,    55,    49,    94,
       0,     0,     0,    31,    39,     0,    38,    58,    62,    63,
      64,     0,    44,     0,    66,    30,    29,    41,    37,    61,
       0,     0,     0,     0,    73,     0,     0,    73,    80,    79,
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
    -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,
    -314,  -314,  -314,  -314,   161,  -183,  -314,  -314,  -314,   143,
    -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,
    -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,  -314,
      54,  -313,  -253,  -314,  -314,  -314,  -314,  -314,   164,  -314,
    -314,  -314,  -314,  -314,   268,  -166,  -314,  -314,  -314,  -314,
    -314,   -99,  -314,  -314,   -93,  -314,  -314,   219,  -314,   -16,
    -314,  -314,   -15,   -11,   -83,   -94,  -314,   -88,  -314,  -314,
     -10,   -92,  -314,  -314,  -314,   324,   -91,  -314,  -314,  -314,
    -314,   -90,  -314,  -314,   326,  -314,   -81,  -314,   233,   235,
    -314,   -96,  -314,   234,  -121,  -120,   -85,  -314,  -119,  -113,
    -314,  -314,  -314,  -314,   -89,  -314,  -314,  -314,  -314,  -314,
    -314,  -314,   429,  -314,  -314,  -314,  -314,   367,  -314,  -314
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,     2,     8,    14,    19,    81,    82,   101,   102,   103,
     145,   146,   192,   261,   211,   212,   272,   273,   274,   275,
     279,   317,   297,   335,   249,   193,   215,   264,   265,   285,
     301,   328,   282,   283,   322,   304,   341,   324,   344,   385,
     356,   364,   357,   358,   376,   216,   252,   267,   268,    83,
     104,   105,   108,   148,   150,   151,   223,   224,   225,   226,
     256,    45,    46,    47,    48,    49,    50,    51,    52,   114,
      54,    55,   115,   125,   172,   127,   128,   129,   117,   130,
     118,    59,    60,    61,    62,   119,    63,    64,    65,    90,
      91,    66,    67,    68,   120,   239,   174,   175,   176,   177,
     178,   179,   182,   183,    69,    70,   180,   186,    71,    72,
     232,   233,   152,   234,    73,    74,    75,    76,     3,     4,
       5,    10,    11,    12,    77,    78,    85,    86,    87,   160
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
     116,   139,    53,    56,   141,   131,   116,    57,    58,   126,
     134,   142,   140,   154,   138,   136,   184,   185,   187,   155,
     156,   157,   158,   159,   188,   383,   353,    98,   247,   219,
     368,   149,   353,   353,   353,   270,    15,   149,   196,   218,
     197,   287,   354,   109,   367,   253,   369,   173,   359,   370,
     372,   219,    25,    26,    27,    28,     1,   378,   379,   380,
     381,    33,    34,    35,    36,    37,    38,    39,    40,    41,
      42,   113,    99,   266,    21,   198,     9,   271,   116,   247,
       6,    84,    79,     7,   355,   184,   185,   187,   384,    80,
     355,   355,   355,   188,   204,     9,    53,    56,   227,   205,
     360,    57,    58,    13,   228,   229,   230,   231,   235,   371,
     373,   246,   139,    22,   342,   244,   173,   338,   210,   339,
     320,   343,   340,   140,   321,   138,    17,   243,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    18,    33,
      34,    35,    36,    37,    38,    39,    40,    41,    42,   298,
     299,   300,   325,    43,    84,    44,   326,   327,   106,   107,
      20,   269,   312,   313,   314,   315,   316,   361,  -212,   362,
     363,   276,   277,   206,   207,   254,   255,   362,   363,    88,
      89,    53,    56,   291,   238,    56,    57,    58,    92,    57,
      58,  -171,    93,    94,   100,    95,    96,    97,   308,   111,
     122,   112,   133,   143,   121,   123,   311,   144,   135,   149,
     147,   153,   164,   161,   133,   162,   163,   165,   168,   169,
     166,   330,   331,   170,   181,   190,   167,   191,   171,   195,
     336,    25,    26,    27,    28,    29,    30,    31,    32,   334,
      33,    34,    35,    36,    37,    38,    39,    40,    41,    42,
     113,   123,   189,   124,   200,   350,   137,   201,   202,   203,
     210,   135,   213,   208,    25,    26,    27,    28,   209,   214,
     220,   217,   245,    33,    34,    35,    36,    37,    38,    39,
      40,    41,    42,   113,    22,   221,   248,   251,   250,   258,
     260,   257,   259,   222,   263,   388,   266,   137,   278,    23,
      24,    25,    26,    27,    28,    29,    30,    31,    32,   280,
      33,    34,    35,    36,    37,    38,    39,    40,    41,    42,
     133,   281,   284,   286,    43,   271,    44,   289,   294,   296,
     295,   303,   292,   318,   306,   319,   329,    25,    26,    27,
      28,    29,    30,    31,    32,   309,    33,    34,    35,    36,
      37,    38,    39,    40,    41,    42,   113,   123,    22,   124,
     302,   332,   137,   305,   310,   352,   361,   323,   333,   337,
     345,   347,   366,    23,    24,    25,    26,    27,    28,    29,
      30,    31,    32,   346,    33,    34,    35,    36,    37,    38,
      39,    40,    41,    42,   290,   351,   348,   349,    43,   375,
      44,   374,   377,   382,   386,   389,   390,   391,   392,   365,
     262,    25,    26,    27,    28,   387,   194,   293,   132,   237,
      33,    34,    35,    36,    37,    38,    39,    40,    41,    42,
     113,   288,    25,    26,    27,    28,   236,   241,   240,    16,
     242,    33,    34,    35,    36,    37,    38,    39,    40,    41,
      42,   113,   110,    25,    26,    27,    28,    29,    30,    31,
      32,     0,    33,    34,    35,    36,    37,    38,    39,    40,
      41,    42,    25,    26,    27,    28,     0,     0,     0,     0,
       0,    33,    34,    35,    36,    37,    38,    39,    40,    41,
      42,   113,   199,     0,     0,     0,   307,    25,    26,    27,
      28,    29,    30,    31,    32,     0,    33,    34,    35,    36,
      37,    38,    39,    40,    41,    42,   113,   123,     0,   124,
      25,    26,    27,    28,     0,     0,     0,     0,     0,    33,
      34,    35,    36,    37,    38,    39,    40,    41,    42,   113
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
      88,    97,    18,    18,    98,    93,    94,    18,    18,    92,
      95,    99,    97,   112,    97,    96,   137,   137,   137,   112,
     112,   112,   112,   112,   137,    24,    28,    46,   211,   195,
      28,    44,    28,    28,    28,    44,     4,    44,    87,    52,
      89,    16,    44,     6,   357,    52,    44,   135,    44,    44,
      44,   217,    61,    62,    63,    64,     3,   370,   371,   372,
     373,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    91,    48,     5,   163,    44,    86,   166,   262,
       0,    44,    49,     7,    86,   206,   206,   206,    87,    56,
      86,    86,    86,   206,    87,    44,   112,   112,   197,    92,
     353,   112,   112,    44,   197,   197,   197,   197,   197,   362,
     363,    10,   208,    44,    14,   209,   204,    38,    17,    40,
      37,    21,    43,   208,    41,   208,    85,   208,    59,    60,
      61,    62,    63,    64,    65,    66,    67,    68,    89,    70,
      71,    72,    73,    74,    75,    76,    77,    78,    79,    38,
      39,    40,    37,    84,    44,    86,    41,    42,    50,    51,
      85,   257,    31,    32,    33,    34,    35,    27,    46,    29,
      30,   259,   260,    87,    88,    54,    55,    29,    30,    91,
      44,   197,   197,   271,   200,   200,   197,   197,    46,   200,
     200,    86,    46,    86,    57,    46,    46,    46,   286,    85,
      87,    89,    44,     8,    88,    81,   294,     9,    91,    44,
      58,    85,    80,    90,    44,    90,    47,    90,    44,    90,
      87,   309,   310,    88,    44,    44,    92,    11,    90,    85,
     318,    61,    62,    63,    64,    65,    66,    67,    68,    36,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,    81,    92,    83,    69,   343,    86,    82,    44,    86,
      17,    91,    44,    46,    61,    62,    63,    64,    46,    13,
      44,    85,    44,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    44,    45,    20,    15,    44,    88,
      86,    46,    46,    53,    30,   383,    48,    86,    18,    59,
      60,    61,    62,    63,    64,    65,    66,    67,    68,    89,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      44,    25,    29,    44,    84,    86,    86,    85,    47,    19,
      89,    26,    85,    46,    85,    12,    85,    61,    62,    63,
      64,    65,    66,    67,    68,    87,    70,    71,    72,    73,
      74,    75,    76,    77,    78,    79,    80,    81,    44,    83,
      89,    88,    86,    89,    87,    22,    27,    89,    85,    85,
      88,    85,    23,    59,    60,    61,    62,    63,    64,    65,
      66,    67,    68,    88,    70,    71,    72,    73,    74,    75,
      76,    77,    78,    79,    44,    89,    85,    85,    84,    44,
      86,    88,    44,    27,    44,    44,    85,    27,    44,   355,
     249,    61,    62,    63,    64,    44,   148,   274,    94,   200,
      70,    71,    72,    73,    74,    75,    76,    77,    78,    79,
      80,   267,    61,    62,    63,    64,    44,   204,   203,    10,
     206,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    85,    61,    62,    63,    64,    65,    66,    67,
      68,    -1,    70,    71,    72,    73,    74,    75,    76,    77,
      78,    79,    61,    62,    63,    64,    -1,    -1,    -1,    -1,
      -1,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,   166,    -1,    -1,    -1,    85,    61,    62,    63,
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
       0,     3,    94,   211,   212,   213,     0,     7,    95,    44,
     214,   215,   216,    44,    96,     4,   215,    85,    89,    97,
      85,     5,    44,    59,    60,    61,    62,    63,    64,    65,
      66,    67,    68,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    84,    86,   154,   155,   156,   157,   158,
     159,   160,   161,   162,   163,   164,   165,   166,   173,   174,
     175,   176,   177,   179,   180,   181,   184,   185,   186,   197,
     198,   201,   202,   207,   208,   209,   210,   217,   218,    49,
      56,    98,    99,   142,    44,   219,   220,   221,    91,    44,
     182,   183,    46,    46,    86,    46,    46,    46,    46,    91,
      57,   100,   101,   102,   143,   144,    50,    51,   145,     6,
     220,    85,    89,    80,   162,   165,   170,   171,   173,   178,
     187,    88,    87,    81,    83,   166,   167,   168,   169,   170,
     172,   170,   178,    44,   199,    91,   189,    86,   167,   194,
     199,   168,   170,     8,     9,   103,   104,    58,   146,    44,
     147,   148,   205,    85,   154,   157,   174,   179,   184,   207,
     222,    90,    90,    47,    80,    90,    87,    92,    44,    90,
      88,    90,   167,   170,   189,   190,   191,   192,   193,   194,
     199,    44,   195,   196,   197,   198,   200,   201,   202,    92,
      44,    11,   105,   118,   147,    85,    87,    89,   170,   187,
      69,    82,    44,    86,    87,    92,    87,    88,    46,    46,
      17,   107,   108,    44,    13,   119,   138,    85,    52,   148,
      44,    45,    53,   149,   150,   151,   152,   154,   157,   174,
     179,   184,   203,   204,   206,   207,    44,   160,   162,   188,
     192,   191,   196,   189,   168,    44,    10,   108,    20,   117,
      44,    15,   139,    52,    54,    55,   153,    46,    88,    46,
      86,   106,   107,    30,   120,   121,    48,   140,   141,   194,
      44,    86,   109,   110,   111,   112,   170,   170,    18,   113,
      89,    25,   125,   126,    29,   122,    44,    16,   141,    85,
      44,   170,    85,   112,    47,    89,    19,   115,    38,    39,
      40,   123,    89,    26,   128,    89,    85,    85,   170,    87,
      87,   170,    31,    32,    33,    34,    35,   114,    46,    12,
      37,    41,   127,    89,   130,    37,    41,    42,   124,    85,
     170,   170,    88,    85,    36,   116,   170,    85,    38,    40,
      43,   129,    14,    21,   131,    88,    88,    85,    85,    85,
     170,    89,    22,    28,    44,    86,   133,   135,   136,    44,
     135,    27,    29,    30,   134,   133,    23,   134,    28,    44,
      44,   135,    44,   135,    88,    44,   137,    44,   134,   134,
     134,   134,    27,    24,    87,   132,    44,    44,   170,    44,
      85,    27,    44
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final short[] yyr1_ = yyr1_init();
  private static final short[] yyr1_init()
  {
    return new short[]
    {
       0,    93,    94,    95,    96,    97,    97,    98,    99,    99,
     100,   101,   102,   102,   103,   104,   104,   105,   106,   106,
     107,   107,   108,   108,   109,   109,   110,   111,   111,   112,
     112,   113,   114,   114,   114,   114,   114,   115,   116,   116,
     117,   117,   118,   118,   119,   120,   121,   121,   122,   122,
     123,   123,   123,   124,   124,   124,   125,   125,   126,   127,
     127,   128,   129,   129,   129,   130,   130,   131,   132,   132,
     132,   133,   133,   134,   134,   134,   134,   134,   135,   135,
     135,   135,   136,   136,   137,   137,   137,   137,   138,   138,
     139,   140,   140,   141,   141,   142,   142,   143,   144,   145,
     145,   145,   146,   146,   147,   147,   148,   148,   149,   149,
     149,   149,   149,   149,   149,   150,   150,   151,   152,   153,
     153,   154,   154,   155,   156,   156,   156,   157,   157,   158,
     159,   160,   160,   160,   161,   161,   162,   162,   163,   163,
     163,   163,   164,   164,   164,   164,   165,   165,   166,   166,
     166,   166,   167,   167,   167,   167,   168,   169,   170,   170,
     171,   171,   171,   172,   173,   173,   173,   173,   174,   174,
     175,   176,   177,   178,   179,   179,   180,   181,   182,   183,
     183,   184,   184,   185,   185,   186,   187,   187,   188,   189,
     190,   190,   191,   191,   192,   192,   192,   192,   193,   194,
     195,   195,   196,   196,   196,   196,   197,   198,   199,   199,
     200,   201,   202,   203,   203,   204,   205,   205,   206,   207,
     207,   208,   208,   209,   209,   210,   210,   211,   211,   212,
     213,   214,   214,   215,   216,   217,   217,   217,   217,   217,
     217,   217,   218,   219,   219,   220,   221,   222,   222,   222,
     222,   222,   222
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     2,     6,     1,     0,     2,     5,     0,     2,
       1,     4,     0,     2,     4,     0,     2,     7,     0,     1,
       1,     2,     5,     5,     1,     1,     1,     1,     2,     5,
       5,     4,     1,     1,     1,     1,     1,     4,     1,     1,
       0,     7,     0,     2,     7,     3,     0,     3,     0,     3,
       1,     1,     1,     1,     1,     1,     0,     1,     4,     1,
       1,     4,     1,     1,     1,     0,     2,     9,     0,     2,
       2,     2,     2,     0,     3,     3,     3,     3,     2,     2,
       1,     3,     3,     4,     3,     1,     5,     3,     0,     2,
       3,     1,     2,     3,     4,     1,     1,     5,     1,     0,
       1,     1,     0,     1,     1,     3,     3,     3,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     2,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       3,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     2,
       2,     2,     2,     3,     1,     1,     1,     1,     1,     1,
       4,     1,     3,     3,     1,     1,     3,     3,     1,     1,
       3,     1,     1,     6,     6,     3,     1,     3,     1,     3,
       1,     3,     1,     1,     1,     1,     1,     1,     4,     3,
       1,     3,     1,     1,     1,     1,     3,     3,     1,     3,
       3,     3,     1,     1,     1,     3,     1,     3,     1,     1,
       1,     1,     4,     3,     6,     1,     1,     0,     1,     3,
       1,     2,     3,     3,     1,     1,     1,     1,     1,     1,
       1,     1,     3,     2,     3,     3,     1,     1,     1,     1,
       1,     1,     1
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
  private static final int YYFINAL_ = 6;
  private static final int YYNTOKENS_ = 93;

/* Unqualified %code blocks.  */
/* "src/main/java/parser/Parser.y":20  */

    private ContextHandler contexts;

/* "src/main/java/parser/Parser.java":2740  */

}
/* "src/main/java/parser/Parser.y":1157  */
