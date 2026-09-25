package parser.initializations;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

public final class RealInitialization implements Initialization {
    private static String DEFAULT = null;
    private final SymbolTable symbolTable;

    public RealInitialization(SymbolTable symbolTable) {
        LexemeInfoBuilder builder = new LexemeInfoBuilder();
        if (DEFAULT == null) {
            String defaultValue = Director.makeDefaultReal(builder);
            symbolTable.putIfAbsent(defaultValue, builder.build());
            DEFAULT = defaultValue;
        }
        this.symbolTable = symbolTable;
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

    /**
     * Value-object equality: every {@code RealInitialization} represents the
     * same "default real value" regardless of which {@link SymbolTable}
     * instance it was built with, so {@code symbolTable} deliberately does
     * not participate in equality (it's infrastructure, not state).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof RealInitialization;
    }

    @Override
    public int hashCode() {
        return RealInitialization.class.hashCode();
    }

    @Override
    public String toString() {
        return "RealInitialization{" + DEFAULT + "}";
    }
}