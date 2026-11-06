package compiler.parser.publishers.leafs.single;

import compiler.parser.facades.MacrosPublisher;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoBuilder;

import java.util.List;

/**
 * Declaration of an enumerated type.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class EnumeratedTypeDeclaration extends SingleDeclaration {
    /**
     * Creates the declaration of an enumerated type.
     *
     * @param symbolTable symbol table that receives the published symbols
     * @param initBuilder builder of the metadata of the type
     */
    public EnumeratedTypeDeclaration(SymbolTable symbolTable, LexemeInfoBuilder initBuilder) {
        super(symbolTable, initBuilder);
    }

    /** {@inheritDoc} */
    @Override
    public void publish() {
        String enumeratedName = identifier;
        List<String> macroNames = builder.parameters();
        String prefix = super.scope() + enumeratedName + "#";
        MacrosPublisher.publish(symbolTable, macroNames, prefix);
        super.publish();
    }
}
