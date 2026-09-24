package parser.utils;

import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.Subtype;

public final class UnderlyingScopeSearcher {
    public static String search(SymbolTable symbolTable, String scope) {
        String underlyingScope = scope;
        LexemeInfo underlyingMetadata = symbolTable.get(scope);

        while (underlyingMetadata.subtype == Subtype.CUSTOM) {
            underlyingScope = underlyingMetadata.customType;
            underlyingMetadata = symbolTable.get(underlyingScope);
        }
        return underlyingScope;
    }
}
