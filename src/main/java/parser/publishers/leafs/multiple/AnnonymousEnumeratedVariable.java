package parser.publishers.leafs.multiple;

import parser.facades.MacrosPublisher;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

import java.util.List;


/**
 * Anonymous enumerated variable of a multiple declaration.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class AnnonymousEnumeratedVariable extends MultipleDeclaration {

    /**
     * Creates the declaration of an anonymous enumerated variable.
     *
     * @param symbolTable symbol table that receives the published symbols
     * @param initBuilder builder of the metadata of the variable
     */
    public AnnonymousEnumeratedVariable(SymbolTable symbolTable, LexemeInfoBuilder initBuilder) {
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
