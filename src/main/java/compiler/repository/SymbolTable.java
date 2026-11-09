package compiler.repository;

import java.util.*;
import java.util.stream.Collectors;

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
    public static class Entry {
        public final int momentOfInsertion;
        public final LexemeInfo symbol;

        Entry(int momentOfInsertion, LexemeInfo symbolInfo) {
            this.momentOfInsertion = momentOfInsertion;
            this.symbol = symbolInfo;
        }
    }
    private final Map<String, Entry> table;
    private int serial;

    /** Creates an empty symbol table. */
    public SymbolTable() {
        table = new HashMap<>();
        serial = 0;
    }

    /**
     * Retrieves the metadata stored under a lexeme.
     *
     * @param lexeme the symbol name to look up
     * @return the stored metadata, or {@code null} if the name is absent
     */
    public LexemeInfo get(String lexeme) {
        Entry entry = table.get(lexeme);
        return (entry == null) ? null : entry.symbol;
    }

    /**
     * Stores metadata under a lexeme, replacing any previous value.
     *
     * @param lexeme the symbol name to store
     * @param info the metadata to associate with the name
     * @return the previous metadata, or {@code null} if none was present
     */
    public LexemeInfo put(String lexeme, LexemeInfo info) {
        Entry oldEntry = table.get(lexeme);
        if (oldEntry == null) {
            table.put(lexeme, new Entry(++serial, info));
            return null;
        } else {
            table.put(lexeme, new Entry(oldEntry.momentOfInsertion, info));
            return oldEntry.symbol;
        }
    }

    /**
     * Stores metadata only if the lexeme is not already mapped.
     *
     * @param lexeme the symbol name to store
     * @param info the metadata to associate if absent
     * @return the existing metadata, or {@code null} if none was present
     */
    public LexemeInfo putIfAbsent(String lexeme, LexemeInfo info) {
        Entry currentEntry = table.get(lexeme);
        if (currentEntry == null) {
            table.put(lexeme, new Entry(++serial, info));
            return null;
        }
        return currentEntry.symbol;
    }

    /**
     * Returns the iterator over all the symbol names of the table.
     *
     * @return an iterator over the entries of the table
     */
    public Iterator<Map.Entry<String, Entry>> iterator() {
        return table.entrySet().iterator();
    }

    public List<String> orderedKeysBasedOnInsertion() {
        return table.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue().momentOfInsertion))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
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