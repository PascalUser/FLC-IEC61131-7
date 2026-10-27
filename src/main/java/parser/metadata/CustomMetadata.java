package parser.metadata;

import parser.initializations.Initialization;
import utils.SymbolTable;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

public final class CustomMetadata implements LexemeInfoView {
    private final String customType;
    private final Initialization initialValue;

    public CustomMetadata(SymbolTable symbolTable, String customType) {
        this.customType = customType;
        this.initialValue = ((Initialization) symbolTable.get(customType).initialValue).copy();
    }

    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    @Override
    public Subtype subtype() {
        return Subtype.CUSTOM;
    }

    @Override
    public String customType() {
        return customType;
    }

    @Override
    public Source source() {
        return Source.UNKNOWN;
    }

    @Override
    public Use use() {
        return Use.UNKNOWN;
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

    @Override
    public Initialization initialValue() {
        return initialValue;
    }
}
