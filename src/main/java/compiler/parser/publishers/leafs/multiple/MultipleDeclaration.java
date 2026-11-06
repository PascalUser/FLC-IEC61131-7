package compiler.parser.publishers.leafs.multiple;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import compiler.parser.publishers.leafs.Declaration;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Publisher for variable and constant declarations.
 * <p>
 * Collects a list of identifiers sharing the same type and attributes,
 * and publishes them to the symbol table when {@link #publish()} is called.
 * Used by the parser to handle declarations like {@code VAR x, y : INT;}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
@SuppressFBWarnings(
    value = {"EI_EXPOSE_REP"},
    justification = "LexemeInfoBuilder is a builder of a DTO with direct collection references by design"
)
public class MultipleDeclaration extends Declaration {
    /** Symbol table that receives the published symbols. */
    protected final SymbolTable symbolTable;
    /** Names of the symbols declared together. */
    protected final List<String> identifiers;

    /**
     * Creates a new declaration publisher.
     *
     * @param initTable   the symbol table to publish to
     */
    public MultipleDeclaration(SymbolTable initTable) {
        super();
        symbolTable = initTable;
        identifiers = new ArrayList<>();
    }

    /**
     * Creates a grouped declaration that uses the given metadata builder.
     *
     * @param initTable symbol table that receives the published symbols
     * @param initBuilder builder of the metadata of every symbol
     */
    public MultipleDeclaration(SymbolTable initTable, LexemeInfoBuilder initBuilder) {
        super(initBuilder);
        symbolTable = initTable;
        identifiers = new ArrayList<>();
    }

    /**
     * Adds identifiers to the group.
     *
     * @param newIdentifiers names of the symbols to add
     * @return this declaration
     */
    public MultipleDeclaration identifiers(List<String> newIdentifiers) {
        identifiers.addAll(newIdentifiers);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        for (String identifier : identifiers) {
            keys.add(keyOf(identifier));
        }
        return keys;
    }

    private String keyOf(String identifier) {
        return super.scope() + identifier;
    }

    /** {@inheritDoc} */
    @Override
    public void publish() {
        for (String identifier : identifiers) {
            symbolTable.put(keyOf(identifier), builder.build());
        }
    }
}
