package parser.initializations;

import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

public final class RealInitialization implements Initialization {
    private static String DEFAULT = null;
    private final SymbolTable symbolTable;

    public RealInitialization(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        if (DEFAULT == null) {
            String defaultValue = "0.0";
            this.symbolTable.putIfAbsent(defaultValue, new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.LITERAL)
                .initialValue(0D)
                .build()
            );
            DEFAULT = defaultValue;
        }
    }

    @Override
    public RealInitialization selectVariable(final String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return DEFAULT;
    }

    @Override
    public RealInitialization copy() {
        return new RealInitialization(this.symbolTable);
    }
}
