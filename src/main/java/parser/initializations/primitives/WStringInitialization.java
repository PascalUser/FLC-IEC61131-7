package parser.initializations.primitives;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

/**
 * Default initialization for WSTRING variables (IEC 61131-7).
 * <p>
 * Represents the default empty wide string value {@code ""} for uninitialized wide string variables.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 * @see parser.initializations.Initialization
 * @see AbstractPrimitiveInitialization
 */
public final class WStringInitialization extends AbstractPrimitiveInitialization {
    static LexemeInfoBuilder builder = new LexemeInfoBuilder();
    static String defaultWString = Director.makeDefaultWString(builder);

    /**
     * Creates a wide string default initialization.
     *
     * @param symbolTable the symbol table for registration
     */
    public WStringInitialization(SymbolTable symbolTable) {
        super(symbolTable, defaultWString, builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WStringInitialization copy() {
        return new WStringInitialization(symbolTable);
    }
}