package parser.facades;

import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.Use;

import java.util.Iterator;
import java.util.Map;

/**
 * Cleaner that removes the symbol table entries whose use is unknown.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class SymbolTableCleaner {

    private SymbolTableCleaner() {
        // Utility class - not instantiable
    }

    /**
     * Removes the symbol table entries whose use is unknown.
     *
     * @param symbolTable the symbol table to clean
     */
    public static void clean(final SymbolTable symbolTable) {
        Iterator<Map.Entry<String, LexemeInfo>> it = symbolTable.iterator();
        while (it.hasNext()) {
            Map.Entry<String, LexemeInfo> entry = it.next();
            if (entry.getValue().use == Use.UNKNOWN) {
                it.remove();
            }
        }
    }
}
