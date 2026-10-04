package parser.initializations;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.builders.LexemeInfoSchema;

/**
 * Default initialization for REAL/LREAL variables (IEC 61131-7).
 * <p>
 * Represents the default value {@code 0.0} for uninitialized real variables.
 * The default value is lazily registered in the {@link SymbolTable} on first use
 * via {@link Director#makeDefaultReal(LexemeInfoSchema)}.
 * </p>
 * <p>
 * All instances are considered equal regardless of the {@code SymbolTable}
 * they were created with, as they represent the same semantic default.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see Initialization
 * @see Director
 */
public final class RealInitialization implements Initialization {
    private static String DEFAULT = null;
    private final SymbolTable symbolTable;

    /**
     * Creates a real default initialization.
     * <p>
     * Registers the default value in the symbol table if not already present.
     * </p>
     *
     * @param symbolTable the symbol table for default value registration
     */
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