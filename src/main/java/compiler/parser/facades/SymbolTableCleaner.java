package compiler.parser.facades;

import compiler.repository.SymbolTable;
import compiler.repository.enums.Use;

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
        Iterator<Map.Entry<String, SymbolTable.Entry>> it = symbolTable.iterator();
        while (it.hasNext()) {
            Map.Entry<String, SymbolTable.Entry> entry = it.next();
            if (entry.getValue().symbol.use == Use.UNKNOWN) {
                it.remove();
            }
        }
    }
}
