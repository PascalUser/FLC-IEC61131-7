package utils;

import java.util.Map;
import java.util.HashMap;

/**
 * Symbol table for the IEC 61131-7 compiler.
 * <p>
 * Stores information about lexemes (identifiers, variables, functions, etc.)
 * found during lexical and syntactic analysis, allowing for later
 * consultation and semantic validation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class SymbolTable {
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

    public LexemeInfo putIfAbsent(String lexeme, LexemeInfo info) {
        return table.putIfAbsent(lexeme, info);
    }

    public int size() {
        return table.size();
    }
}