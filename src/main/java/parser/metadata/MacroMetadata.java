package parser.metadata;

import parser.initializations.leafs.MacroInitialization;
import utils.SymbolTable;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

public final class MacroMetadata implements LexemeInfoView {
    private final MacroInitialization initialValue;

    public MacroMetadata(SymbolTable symbolTable, Integer replaceValue) {
        initialValue = new MacroInitialization(symbolTable, replaceValue);
    }

    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    @Override
    public Subtype subtype() {
        return Subtype.NONE;
    }

    @Override
    public String customType() {
        return null;
    }

    @Override
    public Source source() {
        return Source.NONE;
    }

    @Override
    public Use use() {
        return Use.MACRO;
    }

    @Override
    public List<String> inferiorLimits() {
        return null;
    }

    @Override
    public List<String> superiorLimits() {
        return null;
    }

    @Override
    public List<String> parameters() {
        return null;
    }

    public MacroInitialization initialValue() {
        return initialValue;
    }
}
