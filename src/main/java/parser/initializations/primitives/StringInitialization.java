package parser.initializations.primitives;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

public class StringInitialization extends AbstractPrimitiveInitialization {
    static LexemeInfoBuilder builder = new LexemeInfoBuilder();
    static String defaultString = Director.makeDefaultString(builder);

    public StringInitialization(SymbolTable symbolTable) {
        super(symbolTable, defaultString, builder);
    }

    @Override
    public StringInitialization copy() {
        return new StringInitialization(symbolTable);
    }
}