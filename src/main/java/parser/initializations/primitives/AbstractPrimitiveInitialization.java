package parser.initializations.primitives;

import parser.initializations.Initialization;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

public abstract class AbstractPrimitiveInitialization implements Initialization {
    private final String defaultValue;
    protected final SymbolTable symbolTable;

    protected AbstractPrimitiveInitialization(SymbolTable symbolTable, String defaultValue, LexemeInfoBuilder builder) {
        this.symbolTable = symbolTable;
        this.defaultValue = defaultValue;

        if (symbolTable != null && defaultValue != null && builder != null) {
            symbolTable.putIfAbsent(defaultValue, builder.build());
        }
    }

    @Override
    public Initialization selectVariable(String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + defaultValue + "}";
    }
}