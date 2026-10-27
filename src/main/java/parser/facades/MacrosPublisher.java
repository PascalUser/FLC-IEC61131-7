package parser.facades;

import parser.metadata.MacroMetadata;
import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

import java.util.List;

public final class MacrosPublisher {

    public static void publish(SymbolTable symbolTable, List<String> macroNames, String prefix) {
        LexemeInfoBuilder macroBuilder = new LexemeInfoBuilder();
        for (int i = 0; i < macroNames.size(); i++) {
            String macroName = macroNames.get(i);
            Director.makeWithMetadata(macroBuilder, new MacroMetadata(symbolTable, i));
            symbolTable.put(prefix + macroName, macroBuilder.build());
        }
    }
}
