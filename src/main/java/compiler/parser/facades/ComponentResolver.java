package compiler.parser.facades;

import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.enums.Source;
import compiler.repository.enums.Use;

public final class ComponentResolver {
    private ComponentResolver() {
        // Utility class
    }

    public static String resolve(SymbolTable symbolTable, String key) {
        LexemeInfo keyInfo = symbolTable.get(key);
        if (keyInfo == null) {
            return key;
        }
        if (keyInfo.use != Use.VARIABLE) {
            return key;
        }
        if (keyInfo.source == Source.IN) {
            return key + "@fuzzifier";
        }
        if (keyInfo.source == Source.OUT) {
            return key + "@defuzzifier";
        }
        return key;
    }
}
