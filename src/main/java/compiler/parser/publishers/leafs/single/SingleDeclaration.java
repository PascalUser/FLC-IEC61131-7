package compiler.parser.publishers.leafs.single;

import compiler.parser.publishers.leafs.Declaration;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Base declaration of a single symbol that is not grouped with other symbols.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class SingleDeclaration extends Declaration {
    /** Symbol table that receives the published symbol. */
    protected final SymbolTable symbolTable;
    /** Name of the declared symbol. */
    protected String identifier;

    /**
     * Creates a new declaration publisher.
     *
     * @param initTable   the symbol table to publish to
     */
    public SingleDeclaration(SymbolTable initTable) {
        super();
        symbolTable = initTable;
        identifier = "";
    }

    /**
     * Creates a new declaration publisher.
     *
     * @param initTable   the symbol table to publish to
     * @param initBuilder the lexeme info builder for constructing symbol metadata
     */
    public SingleDeclaration(SymbolTable initTable, LexemeInfoBuilder initBuilder) {
        super(initBuilder);
        symbolTable = initTable;
        identifier = "";
    }

    /**
     * Sets the name of the declared symbol.
     *
     * @param newIdentifier name of the symbol
     * @return this declaration
     */
    public SingleDeclaration identifier(String newIdentifier) {
        identifier = newIdentifier;
        return this;
    }

    /**
     * Returns the name of the declared symbol.
     *
     * @return the symbol name
     */
    public String identifier() {
        return identifier;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> keys() {
        return (identifier.isEmpty())
                ? Collections.emptyList()
                : Collections.singletonList(key());
    }

    /**
     * Returns the full key under which the symbol is published: scope plus identifier.
     *
     * @return the symbol table key
     */
    protected String key() {
        return super.scope() + identifier;
    }

    /** {@inheritDoc} */
    @Override
    public void publish() {
        if (identifier.isEmpty()) {
            return;
        }
        symbolTable.put(key(), builder.build());
    }
}
