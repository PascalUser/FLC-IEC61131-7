package parser.publishers;

import parser.facades.MacrosPublisher;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;

import java.util.List;


public final class AnnonymousEnumeratedVariable extends MultipleDeclaration {

    public AnnonymousEnumeratedVariable(SymbolTable symbolTable, LexemeInfoBuilder initBuilder) {
        super(symbolTable, initBuilder);
    }

    @Override
    public void publish() {
        List<String> macroNames = builder.parameters();
        String prefix = (declarationScope == null)
                ? ""
                : declarationScope + "#";
        MacrosPublisher.publish(symbolTable, macroNames, prefix);
        super.publish();
    }
}
