package parser.utils;

import parser.initializations.*;
import parser.initializations.primitives.*;
import utils.SymbolTable;
import utils.enums.Subtype;

/**
 * Factory for creating default initialization objects for primitive types.
 * <p>
 * Currently only handles {@link Subtype#REAL} via {@link RealInitialization}.
 * Other primitive types use their respective initialization classes directly
 * in the parser semantic actions (e.g., {@link BooleanInitialization},
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
            case BOOL:
                return new BooleanInitialization(symbolTable);
            case LREAL:
            case REAL:
                return new RealInitialization(symbolTable);
            case STRING:
                return new StringInitialization(symbolTable);
            case WSTRING:
                return new WStringInitialization(symbolTable);
            case SINT:
            case INT :
            case LINT:
            case DINT:
            case USINT:
            case UINT :
            case ULINT:
            case UDINT:
                return new IntInitialization(symbolTable);
            default:
                throw new IllegalArgumentException("Subtipo primitivo no soportado por Factory: " + subtype);
        }
    }
}
