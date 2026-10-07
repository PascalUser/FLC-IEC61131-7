package parser.utils;

import parser.initializations.Initialization;
import parser.initializations.RealInitialization;
import parser.initializations.StringInitialization;
import utils.SymbolTable;
import utils.enums.Subtype;

/**
 * Factory for creating default initialization objects for primitive types.
 * <p>
 * Currently only handles {@link Subtype#REAL} via {@link RealInitialization}.
 * Other primitive types use their respective initialization classes directly
 * in the parser semantic actions (e.g., {@link parser.initializations.BooleanInitialization},
 * {@link parser.initializations.SubrangeInitialization}).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
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
            case WSTRING:
            case STRING:
                return new StringInitialization(symbolTable, subtype);
            case REAL:
                return new RealInitialization(symbolTable);
            default:
                throw new IllegalArgumentException("Factory detected an unexpected subtype: " + subtype);
        }
    }
}
