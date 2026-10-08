package parser.initializations.primitives;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

/**
 * Default initialization for STRING variables (IEC 61131-7).
 * <p>
 * Represents the default empty string value {@code ''} for uninitialized string variables.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 * @see parser.initializations.Initialization
 * @see AbstractPrimitiveInitialization
 */
public final class StringInitialization extends AbstractPrimitiveInitialization {
    static LexemeInfoBuilder builder = new LexemeInfoBuilder();
    static String defaultString = Director.makeDefaultString(builder);

    /**
     * Creates a string default initialization.
     *
     * @param symbolTable the symbol table for registration
     */
    public StringInitialization(SymbolTable symbolTable) {
        super(symbolTable, defaultString, builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringInitialization copy() {
        return new StringInitialization(symbolTable);
    }
}