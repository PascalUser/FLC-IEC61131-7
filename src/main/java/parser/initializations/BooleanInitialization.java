package parser.initializations;

import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

public class BooleanInitialization implements Initialization {
    private static String DEFAULT = null;
    private final SymbolTable symbolTable;

    public BooleanInitialization(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        if (DEFAULT == null) {
            String defaultValue = "FALSE";
            this.symbolTable.putIfAbsent(defaultValue, new LexemeInfoBuilder()
                    .type(Type.SIMPLE)
                    .subtype(Subtype.REAL)
                    .use(Use.LITERAL)
                    .initialValue(false)
                    .build()
            );
            DEFAULT = defaultValue;
        }
    }

    @Override
    public BooleanInitialization selectVariable(final String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return DEFAULT;
    }

    @Override
    public BooleanInitialization copy() {
        return new BooleanInitialization(this.symbolTable);
    }
}
