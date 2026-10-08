package parser.initializations.primitives;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

/**
 * Default initialization for REAL and LREAL variables (IEC 61131-7).
 * <p>
 * Represents the default value {@code 0.0} for uninitialized real variables.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 * @see parser.initializations.Initialization
 * @see AbstractPrimitiveInitialization
 */
public final class RealInitialization extends AbstractPrimitiveInitialization {
    static LexemeInfoBuilder builder = new LexemeInfoBuilder();
    static String defaultReal = Director.makeDefaultReal(builder);

    /**
     * Creates a real default initialization.
     *
     * @param symbolTable the symbol table for registration
     */
    public RealInitialization(SymbolTable symbolTable) {
        super(symbolTable, defaultReal, builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealInitialization copy() {
        return new RealInitialization(symbolTable);
    }
}