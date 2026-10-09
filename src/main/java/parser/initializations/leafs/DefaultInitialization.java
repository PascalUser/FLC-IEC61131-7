package parser.initializations.leafs;

import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.builders.LexemeInfoSchema;

import java.util.Objects;
import java.util.function.Function;

/**
 * Default literal initialization for primitive types.
 * <p>
 * Provides factory methods for creating default initializations of
 * primitive IEC 61131-7 types: BOOL ({@code FALSE}), integer types ({@code 0}),
 * REAL/LREAL ({@code 0.0}), STRING ({@code ''}), and WSTRING ({@code ""}).
 * The literal is registered in the {@link SymbolTable} via the {@link Director}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see Director
 * @see SymbolTable
 */
public final class DefaultInitialization extends LeafInitialization {
    private final String literal;

    private DefaultInitialization(final String literal) {
        this.literal = literal;
    }

    /**
     * Default BOOL initialization ({@code FALSE}).
     *
     * @param symbolTable the table where the literal is registered
     * @return the default initialization
     */
    public static DefaultInitialization bool(final SymbolTable symbolTable) {
        return create(symbolTable, Director::makeDefaultBoolean);
    }

    /**
     * Default REAL/LREAL initialization ({@code 0.0}).
     *
     * @param symbolTable the table where the literal is registered
     * @return the default initialization
     */
    public static DefaultInitialization real(final SymbolTable symbolTable) {
        return create(symbolTable, Director::makeDefaultReal);
    }

    /**
     * Default initialization of any integer type ({@code 0}).
     *
     * @param symbolTable the table where the literal is registered
     * @return the default initialization
     */
    public static DefaultInitialization integer(final SymbolTable symbolTable) {
        return create(symbolTable, Director::makeDefaultInteger);
    }

    /**
     * Default STRING initialization ({@code ''}).
     *
     * @param symbolTable the table where the literal is registered
     * @return the default initialization
     */
    public static DefaultInitialization string(final SymbolTable symbolTable) {
        return create(symbolTable, Director::makeDefaultString);
    }

    /**
     * Default WSTRING initialization ({@code ""}).
     *
     * @param symbolTable the table where the literal is registered
     * @return the default initialization
     */
    public static DefaultInitialization wstring(final SymbolTable symbolTable) {
        return create(symbolTable, Director::makeDefaultWString);
    }

    private static DefaultInitialization create(
            final SymbolTable symbolTable, final Function<LexemeInfoSchema, String> recipe
    ) {
        Objects.requireNonNull(symbolTable, "symbolTable");
        LexemeInfoBuilder builder = new LexemeInfoBuilder();
        String literal = recipe.apply(builder);
        symbolTable.putIfAbsent(literal, builder.build());
        return new DefaultInitialization(literal);
    }

    @Override
    public String variableValue() {
        return literal;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultInitialization)) return false;
        DefaultInitialization other = (DefaultInitialization) o;
        return literal.equals(other.literal);
    }

    @Override
    public int hashCode() {
        return literal.hashCode();
    }

    @Override
    public String toString() {
        return "DefaultInitialization{" + literal + "}";
    }
}
