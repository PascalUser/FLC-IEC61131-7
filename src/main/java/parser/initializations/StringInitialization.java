package parser.initializations;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.builders.LexemeInfoSchema;
import utils.enums.Subtype;

/**
 * Represents the default initialization value of a STRING or WSTRING
 * variable. The default literal is lazily registered in the
 * {@link SymbolTable} on first use via
 * {@link Director#makeDefaultString(LexemeInfoSchema)} or
 * {@link Director#makeDefaultWString(LexemeInfoSchema)}.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 * @see Initialization
 * @see Director
 */
public class StringInitialization implements Initialization {
    private static String DEFAULT = null;
    private final SymbolTable symbolTable;
    private final Subtype subtype;

    public StringInitialization(SymbolTable symbolTable, Subtype subtype) {
        LexemeInfoBuilder builder = new LexemeInfoBuilder();
        if (DEFAULT == null) {
            String defaultValue = (subtype == Subtype.STRING)
                ? Director.makeDefaultString(builder)
                : Director.makeDefaultWString(builder);
            symbolTable.putIfAbsent(defaultValue, builder.build());
            DEFAULT = defaultValue;
        }
        this.symbolTable = symbolTable;
        this.subtype = subtype;
    }

    @Override
    public StringInitialization selectVariable(final String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return DEFAULT;
    }

    @Override
    public StringInitialization copy() {
        return new StringInitialization(this.symbolTable, this.subtype);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof StringInitialization;
    }

    @Override
    public int hashCode() {
        return StringInitialization.class.hashCode();
    }

    @Override
    public String toString() {
        return "StringInitialization{" + DEFAULT + "}";
    }
}
