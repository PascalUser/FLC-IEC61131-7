package parser.publishers.leafs.single;

import parser.publishers.leafs.Declaration;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

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
    protected final SymbolTable symbolTable;
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

    public SingleDeclaration identifier(String newIdentifier) {
        identifier = newIdentifier;
        return this;
    }

    public String identifier() {
        return identifier;
    }

    @Override
    public List<String> keys() {
        return (identifier.isEmpty())
                ? Collections.emptyList()
                : Collections.singletonList(key());
    }

    protected String key() {
        return super.scope() + identifier;
    }

    @Override
    public void publish() {
        if (identifier.isEmpty()) {
            return;
        }
        symbolTable.put(key(), builder.build());
    }
}
