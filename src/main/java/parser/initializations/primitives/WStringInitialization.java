package parser.initializations.primitives;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

public final class WStringInitialization extends AbstractPrimitiveInitialization {
    static LexemeInfoBuilder builder = new LexemeInfoBuilder();
    static String defaultWString = Director.makeDefaultWString(builder);

    public WStringInitialization(SymbolTable symbolTable) {
        super(symbolTable, defaultWString, builder);
    }

    @Override
    public WStringInitialization copy() {
        return new WStringInitialization(symbolTable);
    }
}