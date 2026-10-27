package parser.metadata;

import parser.facades.DimensionCalculator;
import parser.facades.RangesParser;
import parser.initializations.Factory;
import parser.initializations.Initialization;
import parser.initializations.nodes.RepeatedInitialization;
import utils.SymbolTable;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.ArrayList;
import java.util.List;

public final class ArrayMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final String customType;
    private final List<String> inferiorLimits;
    private final List<String> superiorLimits;
    private final RepeatedInitialization initialValue;

    public ArrayMetadata(SymbolTable symbolTable, Subtype subtype, String customType, List<String> ranges) {
        this.subtype = subtype;
        this.customType = customType;

        Initialization defaultValue = (subtype.equals(Subtype.CUSTOM))
                ? (Initialization) symbolTable.get(customType).initialValue
                : Factory.createPrimitiveInitialization(symbolTable, subtype);

        inferiorLimits = new ArrayList<>();
        superiorLimits = new ArrayList<>();
        RangesParser.parse(ranges, inferiorLimits, superiorLimits);

        int dimension = DimensionCalculator.calculate(inferiorLimits, superiorLimits);
        initialValue = new RepeatedInitialization(dimension, defaultValue);
    }


    @Override
    public Type type() {
        return Type.ARRAY;
    }

    public Subtype subtype() {
        return subtype;
    }

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
        return inferiorLimits;
    }

    @Override
    public List<String> superiorLimits() {
        return superiorLimits;
    }

    @Override
    public List<String> parameters() {
        return null;
    }

    public RepeatedInitialization initialValue() {
        return initialValue;
    }
}
