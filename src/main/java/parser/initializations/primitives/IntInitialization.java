package parser.initializations.primitives;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

/**
 * Default initialization for signed integer variables (SINT, INT, DINT, LINT)
 * and unsigned integer variables (USINT, UINT, UDINT, ULINT).
 * <p>
 * Represents the default value {@code 0} for uninitialized integer variables.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 * @see parser.initializations.Initialization
 * @see AbstractPrimitiveInitialization
 */
public final class IntInitialization extends AbstractPrimitiveInitialization {
    static LexemeInfoBuilder builder = new LexemeInfoBuilder();
    static String defaultInt = Director.makeDefaultInteger(builder);

    /**
     * Creates an integer default initialization.
     *
     * @param symbolTable the symbol table for registration
     */
    public IntInitialization(SymbolTable symbolTable) {
        super(symbolTable, defaultInt, builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntInitialization copy() {
        return new IntInitialization(symbolTable);
    }
}
