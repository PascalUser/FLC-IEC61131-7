package utils;

import java.util.HashMap;
import lexer_parser.Parser.Lexer;

public class Compiler {
	
	public static HashMap<String, LexemeInfo> table;

    static {
        initTable();
    }
	
	private static void initTable() {
		table = new  HashMap<String, LexemeInfo>();
		
		table.put("FUNCTION_BLOCK",     new LexemeInfo(Lexer.FUNCTION_BLOCK, "reserved"));
        table.put("END_FUNCTION_BLOCK", new LexemeInfo(Lexer.END_FUNCTION_BLOCK, "reserved"));
        table.put("FUZZIFY",            new LexemeInfo(Lexer.FUZZIFY, "reserved"));
        table.put("END_FUZZIFY",        new LexemeInfo(Lexer.END_FUZZIFY, "reserved"));
        table.put("DEFUZZIFY",          new LexemeInfo(Lexer.DEFUZZIFY, "reserved"));
        table.put("END_DEFUZZIFY",      new LexemeInfo(Lexer.END_DEFUZZIFY, "reserved"));
        table.put("RULEBLOCK",          new LexemeInfo(Lexer.RULEBLOCK, "reserved"));
        table.put("END_RULEBLOCK",      new LexemeInfo(Lexer.END_RULEBLOCK, "reserved"));
        table.put("OPTION",             new LexemeInfo(Lexer.OPTION, "reserved"));
        table.put("END_OPTION",         new LexemeInfo(Lexer.END_OPTION, "reserved"));
        table.put("TERM",               new LexemeInfo(Lexer.TERM, "reserved"));
        table.put("METHOD",             new LexemeInfo(Lexer.METHOD, "reserved"));
        table.put("DEFAULT",            new LexemeInfo(Lexer.DEFAULT, "reserved"));
        table.put("RANGE",              new LexemeInfo(Lexer.RANGE, "reserved"));
        table.put("RULE",               new LexemeInfo(Lexer.RULE, "reserved"));
        table.put("IF",                 new LexemeInfo(Lexer.IF, "reserved"));
        table.put("THEN",               new LexemeInfo(Lexer.THEN, "reserved"));
        table.put("WITH",               new LexemeInfo(Lexer.WITH, "reserved"));
        table.put("ACT",                new LexemeInfo(Lexer.ACT, "reserved"));
        table.put("ACCU",               new LexemeInfo(Lexer.ACCU, "reserved"));
        table.put("IS",                 new LexemeInfo(Lexer.IS, "reserved"));
        table.put("NOT",                new LexemeInfo(Lexer.NOT, "reserved"));
        table.put("AND",                new LexemeInfo(Lexer.AND, "reserved"));
        table.put("OR",                 new LexemeInfo(Lexer.OR, "reserved"));
        table.put("CoG",                new LexemeInfo(Lexer.COG, "reserved"));
        table.put("CoGS",               new LexemeInfo(Lexer.COGS, "reserved"));
        table.put("CoA",                new LexemeInfo(Lexer.COA, "reserved"));
        table.put("LM",                 new LexemeInfo(Lexer.LM, "reserved"));
        table.put("RM",                 new LexemeInfo(Lexer.RM, "reserved"));
        table.put("NC",                 new LexemeInfo(Lexer.NC, "reserved"));
        table.put("MIN",                new LexemeInfo(Lexer.MIN, "reserved"));
        table.put("MAX",                new LexemeInfo(Lexer.MAX, "reserved"));
        table.put("ASUM",               new LexemeInfo(Lexer.ASUM, "reserved"));
        table.put("BSUM",               new LexemeInfo(Lexer.BSUM, "reserved"));
        table.put("PROD",               new LexemeInfo(Lexer.PROD, "reserved"));
        table.put("BDIF",               new LexemeInfo(Lexer.BDIF, "reserved"));
        table.put("NSUM",               new LexemeInfo(Lexer.NSUM, "reserved"));
        table.put("VAR_INPUT",          new LexemeInfo(Lexer.VAR_INPUT, "reserved"));
        table.put("VAR_OUTPUT",         new LexemeInfo(Lexer.VAR_OUTPUT, "reserved"));
        table.put("VAR",                new LexemeInfo(Lexer.VAR, "reserved"));
        table.put("END_VAR",            new LexemeInfo(Lexer.END_VAR, "reserved"));
        table.put("RETAIN",             new LexemeInfo(Lexer.RETAIN, "reserved"));
        table.put("NON_RETAIN",         new LexemeInfo(Lexer.NON_RETAIN, "reserved"));
        table.put("CONSTANT",           new LexemeInfo(Lexer.CONSTANT, "reserved"));
        table.put("BOOL",               new LexemeInfo(Lexer.BOOL, "reserved"));
        table.put("SINT",               new LexemeInfo(Lexer.SINT, "reserved"));
        table.put("INT",                new LexemeInfo(Lexer.INT, "reserved"));
        table.put("DINT",               new LexemeInfo(Lexer.DINT, "reserved"));
        table.put("LINT",               new LexemeInfo(Lexer.LINT, "reserved"));
        table.put("USINT",              new LexemeInfo(Lexer.USINT, "reserved"));
        table.put("UINT",               new LexemeInfo(Lexer.UINT, "reserved"));
        table.put("UDINT",              new LexemeInfo(Lexer.UDINT, "reserved"));
        table.put("ULINT",              new LexemeInfo(Lexer.ULINT, "reserved"));
        table.put("REAL",               new LexemeInfo(Lexer.REAL, "reserved"));
        table.put("LREAL",              new LexemeInfo(Lexer.LREAL, "reserved"));
        table.put("STRING",             new LexemeInfo(Lexer.STRING, "reserved"));
        table.put("WSTRING",            new LexemeInfo(Lexer.WSTRING, "reserved"));
        table.put("BYTE",               new LexemeInfo(Lexer.BYTE, "reserved"));
        table.put("WORD",               new LexemeInfo(Lexer.WORD, "reserved"));
        table.put("DWORD",              new LexemeInfo(Lexer.DWORD, "reserved"));
        table.put("LWORD",              new LexemeInfo(Lexer.LWORD, "reserved"));
        table.put("TIME",               new LexemeInfo(Lexer.TIME, "reserved"));
        table.put("TIME_OF_DAY",        new LexemeInfo(Lexer.TIME_OF_DAY, "reserved"));
        table.put("TOD",                new LexemeInfo(Lexer.TOD, "reserved"));
        table.put("DATE",               new LexemeInfo(Lexer.DATE, "reserved"));
        table.put("DATE_AND_TIME",      new LexemeInfo(Lexer.DATE_AND_TIME, "reserved"));
        table.put("DT",                 new LexemeInfo(Lexer.DT, "reserved"));
        table.put("ARRAY",              new LexemeInfo(Lexer.ARRAY, "reserved"));
        table.put("OF",                 new LexemeInfo(Lexer.OF, "reserved"));
        table.put("TRUE",               new LexemeInfo(Lexer.TRUE, "reserved"));
        table.put("FALSE",              new LexemeInfo(Lexer.FALSE, "reserved"));
        table.put("R_EDGE",             new LexemeInfo(Lexer.R_EDGE, "reserved"));
        table.put("F_EDGE",             new LexemeInfo(Lexer.F_EDGE, "reserved"));
        table.put("PRAGMA",             new LexemeInfo(Lexer.PRAGMA, "reserved"));   
    }
}