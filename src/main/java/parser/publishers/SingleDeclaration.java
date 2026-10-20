package parser.publishers;

import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

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
    public void publish() {
        if (identifier == null) {
            return;
        }
        symbolTable.put(
                (declarationScope == null)
                        ? identifier
                        : declarationScope + "#" + identifier,
                builder.build()
        );
    }
}
