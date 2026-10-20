package parser.publishers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

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
 * @version 1.0
 * @since 1.0
 */
@SuppressFBWarnings(
    value = {"EI_EXPOSE_REP"},
    justification = "LexemeInfoBuilder is a builder of a DTO with direct collection references by design"
)
public class MultipleDeclaration extends Declaration {
    protected final SymbolTable symbolTable;
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

    public MultipleDeclaration(SymbolTable initTable, LexemeInfoBuilder initBuilder) {
        super(initBuilder);
        symbolTable = initTable;
        identifiers = new ArrayList<>();
    }

    public MultipleDeclaration identifiers(List<String> newIdentifiers) {
        identifiers.addAll(newIdentifiers);
        return this;
    }

    @Override
    public void publish() {
        for (String identifier : identifiers) {
            symbolTable.put(
                (declarationScope == null) ? identifier : declarationScope + "#" + identifier,
                builder.build()
            );
        }
    }
}
