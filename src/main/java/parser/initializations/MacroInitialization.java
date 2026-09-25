package parser.initializations;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.enums.*;

import java.util.Objects;

public class MacroInitialization implements Initialization {
    private final SymbolTable symbolTable;
    private final String replaceValue;

    public MacroInitialization(SymbolTable symbolTable, String replaceValue) {
        LexemeInfoBuilder builder = new LexemeInfoBuilder();
        Director.makeLiteral(builder);
        symbolTable.putIfAbsent(replaceValue, builder
                .subtype(Subtype.INT)
                .initialValue(Integer.valueOf(replaceValue))
                .build()
        );
        this.replaceValue = replaceValue;
        this.symbolTable = symbolTable;
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
     * Value-object equality based on the wrapped value. Unlike
     * {@link MacroInitialization} (which has no state of its own and is
     * always "the default value"), a {@code MacroInitialization}'s
     * identity as a value *is* {@code ilimit} and {@code ulimit} — two instances
     * are equal only when they hold the same valueS.
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

