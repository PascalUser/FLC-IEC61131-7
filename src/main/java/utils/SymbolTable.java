package utils;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
	private final Map<String, LexemeInfo> table;

	public SymbolTable() {
        table = new HashMap<>();
    }

    public LexemeInfo get(String lexeme) {
        return table.get(lexeme);
    }

    public LexemeInfo put(String lexeme, LexemeInfo info) {
        return table.put(lexeme, info);
    }
}