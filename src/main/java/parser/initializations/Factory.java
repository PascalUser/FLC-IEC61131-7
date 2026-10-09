package parser.initializations;

import parser.initializations.leafs.DefaultInitialization;
import utils.SymbolTable;
import utils.enums.Subtype;

/**
 * Factory for creating default primitive initializations.
 * <p>
 * Provides static methods to create {@link Initialization} objects for
 * primitive subtypes (BOOL, integer types, REAL/LREAL, STRING, WSTRING)
 * using the {@link Director} to generate appropriate default literal values
 * and registering them in the {@link SymbolTable}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see DefaultInitialization
 * @see Director
 */
public final class Factory {

    private Factory() {
        // Utility class - not instantiable
    }

    /**
     * Creates a default initialization for the given primitive subtype.
     *
     * @param symbolTable the symbol table (used for lazy default registration)
     * @param subtype     the primitive subtype
     * @return a default initialization object
     * @throws IllegalArgumentException if the subtype is not supported
     */
    public static Initialization createPrimitiveInitialization(SymbolTable symbolTable, Subtype subtype) {
        switch (subtype) {
            case BOOL:
                return DefaultInitialization.bool(symbolTable);
            case LREAL:
            case REAL:
                return DefaultInitialization.real(symbolTable);
            case STRING:
                return DefaultInitialization.string(symbolTable);
            case WSTRING:
                return DefaultInitialization.wstring(symbolTable);
            case SINT:
            case INT :
            case LINT:
            case DINT:
            case USINT:
            case UINT :
            case ULINT:
            case UDINT:
                return DefaultInitialization.integer(symbolTable);
            default:
                throw new IllegalArgumentException("Factory: could not find an object for " + subtype);
        }
    }
}
