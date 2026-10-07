package parser.initializations.primitives;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

public final class IntInitialization extends AbstractPrimitiveInitialization {
    static LexemeInfoBuilder builder = new LexemeInfoBuilder();
    static String defaultInt = Director.makeDefaultInteger(builder);

    public IntInitialization(SymbolTable symbolTable) {
        super(symbolTable, defaultInt, builder);
    }

    @Override
    public IntInitialization copy() {
        return new IntInitialization(symbolTable);
    }
}
