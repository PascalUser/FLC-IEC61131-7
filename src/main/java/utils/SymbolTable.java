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

    /** Creates an empty symbol table. */
    public SymbolTable() {
        table = new HashMap<>();
    }

    /**
     * Retrieves the metadata stored under a lexeme.
     *
     * @param lexeme the symbol name to look up
     * @return the stored metadata, or {@code null} if the name is absent
     */
    public LexemeInfo get(String lexeme) {
        return table.get(lexeme);
    }

    /**
     * Stores metadata under a lexeme, replacing any previous value.
     *
     * @param lexeme the symbol name to store
     * @param info the metadata to associate with the name
     * @return the previous metadata, or {@code null} if none was present
     */
    public LexemeInfo put(String lexeme, LexemeInfo info) {
        return table.put(lexeme, info);
    }

    /**
     * Stores metadata only if the lexeme is not already mapped.
     *
     * @param lexeme the symbol name to store
     * @param info the metadata to associate if absent
     * @return the existing metadata, or {@code null} if none was present
     */
    public LexemeInfo putIfAbsent(String lexeme, LexemeInfo info) {
        return table.putIfAbsent(lexeme, info);
    }

    /**
     * Counts the symbol names in the table.
     *
     * @return the number of entries
     */
    public int size() {
        return table.size();
    }
}