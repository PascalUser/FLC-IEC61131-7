package parser.internals;

import parser.facades.Publisher;
import parser.utils.NameMangler;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all contextual state during parsing of a declaration scope.
 * <p>
 * A {@code ParsingContext} is created for each major syntactic scope:
 * function block, type declaration, struct field list, array initialization,
 * and enumeration value list. It aggregates:
 * </p>
 * <ul>
 *   <li>The {@link SymbolTable} for symbol lookup/storage</li>
 *   <li>Identifiers declared in this scope</li>
 *   <li>A {@link LexemeInfoBuilder} for fluent attribute configuration</li>
 *   <li>Three {@link NameMangler} instances for different mangling purposes</li>
 *   <li>An index counter for array initialization position tracking</li>
 * </ul>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see ContextHandler
 * @see NameMangler
 * @see LexemeInfoBuilder
 */
public final class ParsingContext {
    private final SymbolTable symbolTable;

    private final List<String> declaredIdentifiers;
    private final LexemeInfoBuilder metadataBuilder;

    private final NameMangler outerScopes;
    private final NameMangler searchScope;
    private final NameMangler nestedFields;

    private int index;

    /**
     * Creates a new parsing context with an empty state.
     *
     * @param symbolTable the shared symbol table
     */
    public ParsingContext(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.declaredIdentifiers = new ArrayList<>();
        this.metadataBuilder = new LexemeInfoBuilder();
        this.outerScopes = new NameMangler();
        this.searchScope = new NameMangler();
        this.nestedFields = new NameMangler();
        this.index = 0;
    }

    /**
     * Returns the shared symbol table.
     *
     * @return the symbol table
     */
    public SymbolTable symbolTable() {
        return symbolTable;
    }

    /**
     * Returns the list of identifiers declared in this context.
     * <p>
     * Used by {@link Publisher} to know which identifiers
     * to publish with the configured metadata.
     * </p>
     *
     * @return mutable list of declared identifiers
     */
    public List<String> declaredIdentifiers() {
        return declaredIdentifiers;
    }

    /**
     * Returns the metadata builder for configuring semantic attributes.
     * <p>
     * Implements {@link utils.builders.LexemeInfoSchema} for fluent API.
     * </p>
     *
     * @return the metadata builder
     */
    public LexemeInfoBuilder metadataBuilder() {
        return metadataBuilder;
    }

    /**
     * Returns the name mangler for outer/enclosing scopes.
     * <p>
     * Used to generate mangled keys for identifiers declared in this scope
     * (e.g., {@code TYPE_NAME#FIELD_NAME}).
     * </p>
     *
     * @return the outer scopes mangler
     */
    public NameMangler outerScopes() {
        return outerScopes;
    }

    /**
     * Returns the name mangler for searching referenced types.
     * <p>
     * Used when a variable references a user-defined type (e.g., {@code x: MyType})
     * to find the type's definition scope for nested field/enum lookup.
     * </p>
     *
     * @return the search scope mangler
     */
    public NameMangler searchScope() {
        return searchScope;
    }

    /**
     * Returns the name mangler for nested struct field initialization.
     * <p>
     * Tracks the current field path during struct initialization
     * (e.g., {@code MOTOR#SPEED#UNITS}).
     * </p>
     *
     * @return the nested fields mangler
     */
    public NameMangler nestedFields() {
        return nestedFields;
    }

    /**
     * Returns the current array initialization index.
     * <p>
     * Used by {@link parser.initializations.RepeatedInitialization} to track
     * which array element is being initialized.
     * </p>
     *
     * @return the current index
     */
    public int index() {
        return index;
    }

    /**
     * Increments the array initialization index.
     *
     * @param increment the amount to increment (typically 1 or repetition count)
     */
    public void incrementIndex(int increment) {
        this.index += increment;
    }
}
