package parser.publishers;

import parser.facades.MacrosPublisher;
import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;

import java.util.List;

public final class EnumeratedTypeDeclaration extends SingleDeclaration {
    public EnumeratedTypeDeclaration(SymbolTable symbolTable, LexemeInfoBuilder initBuilder) {
        super(symbolTable, initBuilder);
    }

    @Override
    public void publish() {
        String enumeratedName = identifier;
        List<String> macroNames = builder.parameters();
        String prefix = (declarationScope == null)
                ? enumeratedName + "#"
                : declarationScope + "#" + enumeratedName + "#";
        MacrosPublisher.publish(symbolTable, macroNames, prefix);
        super.publish();
    }
}
