package parser.initializations;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.builders.LexemeInfoSchema;
import utils.enums.*;

/**
 * Default initialization for BOOL variables (IEC 61131-7).
 * <p>
 * Represents the default value {@code FALSE} for uninitialized boolean variables.
 * The default value is lazily registered in the {@link SymbolTable} on first use
 * via {@link Director#makeDefaultReal(LexemeInfoSchema)} (which creates a
 * {@code LexemeInfo} with {@code type=SIMPLE, subtype=BOOL, value="FALSE"}).
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
public class BooleanInitialization implements Initialization {
    private static String DEFAULT = null;
    private final SymbolTable symbolTable;

    /**
     * Creates a boolean default initialization.
     * <p>
     * Registers the default value in the symbol table if not already present.
     * </p>
     *
     * @param symbolTable the symbol table for default value registration
     */
    public BooleanInitialization(SymbolTable symbolTable) {
        LexemeInfoBuilder builder = new LexemeInfoBuilder();
        if (DEFAULT == null) {
            String defaultValue = Director.makeDefaultBoolean(builder);
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
