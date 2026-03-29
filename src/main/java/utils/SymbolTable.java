package utils;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
	private final Map<String, LexemeInfo> table;

	public SymbolTable() {
		this.table = new  HashMap<>();

		table.put("FUNCTION_BLOCK",     new LexemeInfo(Use.RESERVED));
        table.put("END_FUNCTION_BLOCK", new LexemeInfo(Use.RESERVED));
        table.put("FUZZIFY",            new LexemeInfo(Use.RESERVED));
        table.put("END_FUZZIFY",        new LexemeInfo(Use.RESERVED));
        table.put("DEFUZZIFY",          new LexemeInfo(Use.RESERVED));
        table.put("END_DEFUZZIFY",      new LexemeInfo(Use.RESERVED));
        table.put("RULEBLOCK",          new LexemeInfo(Use.RESERVED));
        table.put("END_RULEBLOCK",      new LexemeInfo(Use.RESERVED));
        table.put("OPTION",             new LexemeInfo(Use.RESERVED));
        table.put("END_OPTION",         new LexemeInfo(Use.RESERVED));
        table.put("TERM",               new LexemeInfo(Use.RESERVED));
        table.put("METHOD",             new LexemeInfo(Use.RESERVED));
        table.put("DEFAULT",            new LexemeInfo(Use.RESERVED));
        table.put("RANGE",              new LexemeInfo(Use.RESERVED));
        table.put("RULE",               new LexemeInfo(Use.RESERVED));
        table.put("IF",                 new LexemeInfo(Use.RESERVED));
        table.put("THEN",               new LexemeInfo(Use.RESERVED));
        table.put("WITH",               new LexemeInfo(Use.RESERVED));
        table.put("ACT",                new LexemeInfo(Use.RESERVED));
        table.put("ACCU",               new LexemeInfo(Use.RESERVED));
        table.put("IS",                 new LexemeInfo(Use.RESERVED));
        table.put("NOT",                new LexemeInfo(Use.RESERVED));
        table.put("AND",                new LexemeInfo(Use.RESERVED));
        table.put("OR",                 new LexemeInfo(Use.RESERVED));
        table.put("COG",                new LexemeInfo(Use.RESERVED));
        table.put("COGS",               new LexemeInfo(Use.RESERVED));
        table.put("COA",                new LexemeInfo(Use.RESERVED));
        table.put("LM",                 new LexemeInfo(Use.RESERVED));
        table.put("RM",                 new LexemeInfo(Use.RESERVED));
        table.put("NC",                 new LexemeInfo(Use.RESERVED));
        table.put("MIN",                new LexemeInfo(Use.RESERVED));
        table.put("MAX",                new LexemeInfo(Use.RESERVED));
        table.put("ASUM",               new LexemeInfo(Use.RESERVED));
        table.put("BSUM",               new LexemeInfo(Use.RESERVED));
        table.put("PROD",               new LexemeInfo(Use.RESERVED));
        table.put("BDIF",               new LexemeInfo(Use.RESERVED));
        table.put("NSUM",               new LexemeInfo(Use.RESERVED));
        table.put("VAR_INPUT",          new LexemeInfo(Use.RESERVED));
        table.put("VAR_OUTPUT",         new LexemeInfo(Use.RESERVED));
        table.put("VAR",                new LexemeInfo(Use.RESERVED));
        table.put("END_VAR",            new LexemeInfo(Use.RESERVED));
        table.put("RETAIN",             new LexemeInfo(Use.RESERVED));
        table.put("NON_RETAIN",         new LexemeInfo(Use.RESERVED));
        table.put("CONSTANT",           new LexemeInfo(Use.RESERVED));
        table.put("BOOL",               new LexemeInfo(Use.RESERVED));
        table.put("SINT",               new LexemeInfo(Use.RESERVED));
        table.put("INT",                new LexemeInfo(Use.RESERVED));
        table.put("DINT",               new LexemeInfo(Use.RESERVED));
        table.put("LINT",               new LexemeInfo(Use.RESERVED));
        table.put("USINT",              new LexemeInfo(Use.RESERVED));
        table.put("UINT",               new LexemeInfo(Use.RESERVED));
        table.put("UDINT",              new LexemeInfo(Use.RESERVED));
        table.put("ULINT",              new LexemeInfo(Use.RESERVED));
        table.put("REAL",               new LexemeInfo(Use.RESERVED));
        table.put("LREAL",              new LexemeInfo(Use.RESERVED));
        table.put("STRING",             new LexemeInfo(Use.RESERVED));
        table.put("WSTRING",            new LexemeInfo(Use.RESERVED));
        table.put("BYTE",               new LexemeInfo(Use.RESERVED));
        table.put("WORD",               new LexemeInfo(Use.RESERVED));
        table.put("DWORD",              new LexemeInfo(Use.RESERVED));
        table.put("LWORD",              new LexemeInfo(Use.RESERVED));
        table.put("TIME",               new LexemeInfo(Use.RESERVED));
        table.put("T",                  new LexemeInfo(Use.RESERVED));
        table.put("TIME_OF_DAY",        new LexemeInfo(Use.RESERVED));
        table.put("TOD",                new LexemeInfo(Use.RESERVED));
        table.put("DATE",               new LexemeInfo(Use.RESERVED));
        table.put("D",                  new LexemeInfo(Use.RESERVED));
        table.put("DATE_AND_TIME",      new LexemeInfo(Use.RESERVED));
        table.put("DT",                 new LexemeInfo(Use.RESERVED));
        table.put("ARRAY",              new LexemeInfo(Use.RESERVED));
        table.put("OF",                 new LexemeInfo(Use.RESERVED));
        table.put("TRUE",               new LexemeInfo(Use.RESERVED));
        table.put("FALSE",              new LexemeInfo(Use.RESERVED));
        table.put("R_EDGE",             new LexemeInfo(Use.RESERVED));
        table.put("F_EDGE",             new LexemeInfo(Use.RESERVED));
        table.put("PRAGMA",             new LexemeInfo(Use.RESERVED));
        table.put("TYPE",               new LexemeInfo(Use.RESERVED));
        table.put("END_TYPE",           new LexemeInfo(Use.RESERVED));
        table.put("STRUCT",             new LexemeInfo(Use.RESERVED));
        table.put("END_STRUCT",         new LexemeInfo(Use.RESERVED));
    }

    public LexemeInfo get(String lexeme) {
        return table.get(lexeme);
    }

    public LexemeInfo put(String lexeme, LexemeInfo info) {
        return table.put(lexeme, info);
    }
}