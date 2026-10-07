package parser.initializations.primitives;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

public final class RealInitialization extends AbstractPrimitiveInitialization {
    static LexemeInfoBuilder builder = new LexemeInfoBuilder();
    static String defaultReal = Director.makeDefaultReal(builder);

    public RealInitialization(SymbolTable symbolTable) {
        super(symbolTable, defaultReal, builder);
    }

    @Override
    public RealInitialization copy() {
        return new RealInitialization(symbolTable);
    }
}