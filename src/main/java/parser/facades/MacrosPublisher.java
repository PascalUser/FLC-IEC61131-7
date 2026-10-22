package parser.facades;

import parser.utils.metadata.MacroMetadata;
import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

import java.util.List;

public final class MacrosPublisher {

    public static void publish(SymbolTable symbolTable, List<String> macroNames, String prefix) {
        LexemeInfoBuilder macroBuilder = new LexemeInfoBuilder();
        for (int i = 0; i < macroNames.size(); i++) {
            String macroName = macroNames.get(i);
            Director.makeMacro(macroBuilder, symbolTable, new MacroMetadata(i));
            symbolTable.put(prefix + macroName, macroBuilder.build());
        }
    }
}
