package parser.publishers.leafs.single;

import parser.facades.MacrosPublisher;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

import java.util.List;

/**
 * Anonymous enumerated field of a structure.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class AnnonymousEnumeratedField extends SingleDeclaration {
    /**
     * Creates the declaration of an anonymous enumerated field.
     *
     * @param symbolTable symbol table that receives the published symbols
     * @param initBuilder builder of the metadata of the field
     */
    public AnnonymousEnumeratedField(SymbolTable symbolTable, LexemeInfoBuilder initBuilder) {
        super(symbolTable, initBuilder);
    }

    /** {@inheritDoc} */
    @Override
    public void publish() {
        List<String> macroNames = builder.parameters();
        MacrosPublisher.publish(symbolTable, macroNames, super.scope());
        super.publish();
    }
}
