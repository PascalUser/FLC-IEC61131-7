package parser.initializations.leafs;

import parser.initializations.Initialization;
import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.enums.*;

import java.util.Objects;

/**
 * Initialization for enumerated literal values (MACRO use, IEC 61131-7).
 * <p>
 * Represents an explicit enum value reference (e.g., {@code color := RED}).
 * The ordinal value is registered in the symbol table as a literal with
 * {@code Subtype.INT} for semantic analysis purposes.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see Initialization
 * @see EnumeratedInitialization
 * @see Director
 */
public final class MacroInitialization extends LeafInitialization {
    private final Integer replaceValue;

    /**
     * Creates a macro initialization for an enum literal.
     *
     * @param symbolTable  the symbol table for literal registration
     * @param replaceValue the ordinal value as string (e.g., "0", "1", "2")
     */
    public MacroInitialization(SymbolTable symbolTable, Integer replaceValue) {
        if (replaceValue < 0) {
            throw new RuntimeException("MacroInitialization: macro cannot be replaced with invalid number");
        }
        LexemeInfoBuilder builder = new LexemeInfoBuilder();
        Director.makeLiteral(builder);
        symbolTable.putIfAbsent(replaceValue.toString(), builder
                .subtype(Subtype.INT)
                .initialValue(replaceValue)
                .build()
        );
        this.replaceValue = replaceValue;
    }

    @Override
    public String variableValue() {
        return replaceValue.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MacroInitialization)) return false;
        MacroInitialization other = (MacroInitialization) o;
        return replaceValue.equals(other.replaceValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(replaceValue);
    }

    @Override
    public String toString() {
        return "MacroInitialization{" + replaceValue + "}";
    }
}

