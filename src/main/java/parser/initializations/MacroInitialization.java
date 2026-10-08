package parser.initializations;

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
public final class MacroInitialization implements Initialization {
    private final SymbolTable symbolTable;
    private final String replaceValue;

    /**
     * Creates a macro initialization for an enum literal.
     *
     * @param symbolTable  the symbol table for literal registration
     * @param replaceValue the ordinal value as string (e.g., "0", "1", "2")
     */
    public MacroInitialization(SymbolTable symbolTable, String replaceValue) {
        try {
            LexemeInfoBuilder builder = new LexemeInfoBuilder();
            Director.makeLiteral(builder);
            symbolTable.putIfAbsent(replaceValue, builder
                    .subtype(Subtype.INT)
                    .initialValue(Integer.valueOf(replaceValue))
                    .build()
            );
            this.replaceValue = replaceValue;
            this.symbolTable = symbolTable;
        } catch (NumberFormatException e) {
            throw new RuntimeException("MacroInitialization: macro cannot be replaced with invalid number");
        }
    }

    @Override
    public Initialization selectVariable(String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return replaceValue;
    }

    @Override
    public Initialization copy() {
        return new MacroInitialization(symbolTable, replaceValue);
    }

    /**
     * Value-object equality based on the wrapped ordinal value.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MacroInitialization)) {
            return false;
        }
        MacroInitialization other = (MacroInitialization) o;
        return Objects.equals(this.replaceValue, other.replaceValue);
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

