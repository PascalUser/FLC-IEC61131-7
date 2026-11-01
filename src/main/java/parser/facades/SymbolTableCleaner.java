package parser.facades;

import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.Use;

import java.util.Iterator;
import java.util.Map;

public final class SymbolTableCleaner {

    private SymbolTableCleaner() {
        // Utility class - not instantiable
    }

    public static void clean(SymbolTable symbolTable) {
        Iterator<Map.Entry<String, LexemeInfo>> it = symbolTable.iterator();
        while (it.hasNext()) {
            Map.Entry<String, LexemeInfo> entry = it.next();
            if (entry.getValue().use == Use.UNKNOWN) {
                it.remove();
            }
        }
    }
}
