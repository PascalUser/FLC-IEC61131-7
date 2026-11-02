package parser.publishers.leafs.single;

import parser.facades.MacrosPublisher;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

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
    public EnumeratedTypeDeclaration(SymbolTable symbolTable, LexemeInfoBuilder initBuilder) {
        super(symbolTable, initBuilder);
    }

    @Override
    public void publish() {
        String enumeratedName = identifier;
        List<String> macroNames = builder.parameters();
        String prefix = super.scope() + enumeratedName + "#";
        MacrosPublisher.publish(symbolTable, macroNames, prefix);
        super.publish();
    }
}
