package parser.initializations;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.enums.*;

public class BooleanInitialization implements Initialization {
    private static String DEFAULT = null;
    private final SymbolTable symbolTable;

    public BooleanInitialization(SymbolTable symbolTable) {
        LexemeInfoBuilder builder = new LexemeInfoBuilder();
        if (DEFAULT == null) {
            String defaultValue = Director.makeDefaultReal(builder);
            symbolTable.putIfAbsent(defaultValue, builder.build());
            DEFAULT = defaultValue;
        }
        this.symbolTable = symbolTable;
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

    /**
     * Value-object equality: every {@code BooleanInitialization} represents the
     * same "default boolean value" regardless of which {@link SymbolTable}
     * instance it was built with, so {@code symbolTable} deliberately does
     * not participate in equality (it's infrastructure, not state).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof BooleanInitialization;
    }

    @Override
    public int hashCode() {
        return BooleanInitialization.class.hashCode();
    }

    @Override
    public String toString() {
        return "BooleanInitialization{" + DEFAULT + "}";
    }
}
