package parser.utils;

import parser.initializations.Initialization;
import parser.initializations.RealInitialization;
import utils.SymbolTable;
import utils.enums.Subtype;

public final class Factory {
    public static Initialization createPrimitiveInitialization(SymbolTable symbolTable, Subtype subtype) {
        switch (subtype) {
            case REAL:
                return new RealInitialization(symbolTable);
            default:
                throw new IllegalArgumentException("Factory.java detected an unexpected subtype: " + subtype);
        }
    }
}
