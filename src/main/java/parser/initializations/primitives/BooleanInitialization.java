package parser.initializations.primitives;

import parser.initializations.Initialization;
import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.builders.LexemeInfoSchema;

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
public final class BooleanInitialization extends AbstractPrimitiveInitialization {
    static LexemeInfoBuilder builder = new LexemeInfoBuilder();
    static String defaultBool = Director.makeDefaultBoolean(builder);

    public BooleanInitialization(SymbolTable symbolTable) {
        super(symbolTable, defaultBool, builder);
    }

    @Override
    public BooleanInitialization copy() {
        return new BooleanInitialization(symbolTable);
    }
}
