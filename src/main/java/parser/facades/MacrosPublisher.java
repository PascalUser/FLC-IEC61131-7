package parser.facades;

import parser.metadata.MacroMetadata;
import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

import java.util.List;

/**
 * Publisher that registers macro names in the symbol table.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class MacrosPublisher {

    private MacrosPublisher() {
        // Utility class - not instantiable
    }

    /**
     * Publishes the given macro names into the symbol table.
     *
     * @param symbolTable   the symbol table of the compilation
     * @param macroNames    the names of the macros to publish
     * @param prefix        the prefix of the macro names in the symbol table
     */
    public static void publish(final SymbolTable symbolTable, final List<String> macroNames, final String prefix) {
        LexemeInfoBuilder macroBuilder = new LexemeInfoBuilder();
        for (int i = 0; i < macroNames.size(); i++) {
            String macroName = macroNames.get(i);
            Director.makeWithMetadata(macroBuilder, new MacroMetadata(symbolTable, i));
            symbolTable.put(prefix + macroName, macroBuilder.build());
        }
    }
}
