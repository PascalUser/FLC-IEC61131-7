package lexer;

import java.util.Map;
import java.util.HashMap;

public final class ReservedWords {
    private final static Map<String, Integer> table;

    static {
        table = new HashMap<>();

        table.put("FUNCTION_BLOCK", Lexer.FUNCTION_BLOCK);
        table.put("END_FUNCTION_BLOCK", Lexer.END_FUNCTION_BLOCK);
        table.put("FUZZIFY", Lexer.FUZZIFY);
        table.put("END_FUZZIFY", Lexer.END_FUZZIFY);
        table.put("DEFUZZIFY", Lexer.DEFUZZIFY);
        table.put("END_DEFUZZIFY", Lexer.END_DEFUZZIFY);
        table.put("RULEBLOCK", Lexer.RULEBLOCK);
        table.put("END_RULEBLOCK", Lexer.END_RULEBLOCK);
        table.put("OPTION", Lexer.OPTION);
        table.put("END_OPTION", Lexer.END_OPTION);
        table.put("TERM", Lexer.TERM);
        table.put("METHOD", Lexer.METHOD);
        table.put("DEFAULT", Lexer.DEFAULT);
        table.put("RANGE", Lexer.RANGE);
        table.put("RULE", Lexer.RULE);
        table.put("IF", Lexer.IF);
        table.put("THEN", Lexer.THEN);
        table.put("WITH", Lexer.WITH);
        table.put("ACT", Lexer.ACT);
        table.put("ACCU", Lexer.ACCU);
        table.put("IS", Lexer.IS);
        table.put("NOT", Lexer.NOT);
        table.put("AND", Lexer.AND);
        table.put("OR", Lexer.OR);
        table.put("COG", Lexer.COG);
        table.put("COGS", Lexer.COGS);
        table.put("COA", Lexer.COA);
        table.put("LM", Lexer.LM);
        table.put("RM", Lexer.RM);
        table.put("NC", Lexer.NC);
        table.put("MIN", Lexer.MIN);
        table.put("MAX", Lexer.MAX);
        table.put("ASUM", Lexer.ASUM);
        table.put("BSUM", Lexer.BSUM);
        table.put("PROD", Lexer.PROD);
        table.put("BDIF", Lexer.BDIF);
        table.put("NSUM", Lexer.NSUM);
        table.put("VAR_INPUT", Lexer.VAR_INPUT);
        table.put("VAR_OUTPUT", Lexer.VAR_OUTPUT);
        table.put("VAR", Lexer.VAR);
        table.put("END_VAR", Lexer.END_VAR);
        table.put("RETAIN", Lexer.RETAIN);
        table.put("NON_RETAIN", Lexer.NON_RETAIN);
        table.put("CONSTANT", Lexer.CONSTANT);
        table.put("BOOL", Lexer.BOOL);
        table.put("SINT", Lexer.SINT);
        table.put("INT", Lexer.INT);
        table.put("DINT", Lexer.DINT);
        table.put("LINT", Lexer.LINT);
        table.put("USINT", Lexer.USINT);
        table.put("UINT", Lexer.UINT);
        table.put("UDINT", Lexer.UDINT);
        table.put("ULINT", Lexer.ULINT);
        table.put("REAL", Lexer.REAL);
        table.put("LREAL", Lexer.LREAL);
        table.put("STRING", Lexer.STRING);
        table.put("WSTRING", Lexer.WSTRING);
        table.put("BYTE", Lexer.BYTE);
        table.put("WORD", Lexer.WORD);
        table.put("DWORD", Lexer.DWORD);
        table.put("LWORD", Lexer.LWORD);
        table.put("TIME", Lexer.TIME);
        table.put("T", Lexer.TIME);
        table.put("TIME_OF_DAY", Lexer.TIME_OF_DAY);
        table.put("TOD", Lexer.TIME_OF_DAY);
        table.put("DATE", Lexer.DATE);
        table.put("D", Lexer.DATE);
        table.put("DATE_AND_TIME", Lexer.DATE_AND_TIME);
        table.put("DT", Lexer.DATE_AND_TIME);
        table.put("ARRAY", Lexer.ARRAY);
        table.put("OF", Lexer.OF);
        table.put("TRUE", Lexer.TRUE);
        table.put("FALSE", Lexer.FALSE);
        table.put("R_EDGE", Lexer.R_EDGE);
        table.put("F_EDGE", Lexer.F_EDGE);
        table.put("PRAGMA", Lexer.PRAGMA);
        table.put("TYPE", Lexer.TYPE);
        table.put("END_TYPE", Lexer.END_TYPE);
        table.put("STRUCT", Lexer.STRUCT);
        table.put("END_STRUCT", Lexer.END_STRUCT);
    }

    public static Integer isReserved(String identifier) {
        return table.get(identifier.toUpperCase());
    }
}