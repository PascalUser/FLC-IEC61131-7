package parser.metadata;

import parser.initializations.Factory;
import parser.initializations.Initialization;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Collections;
import java.util.List;

import static utils.builders.Director.makeIntegerZeroConstant;

public final class StringMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final String inferiorLimit;
    private final String superiorLimit;
    private final Initialization initialValue;

    public StringMetadata(SymbolTable symbolTable, Subtype initSubtype, String initLength) {
        subtype = initSubtype;
        superiorLimit = initLength;

        LexemeInfoBuilder limitMetadataBuilder = new LexemeInfoBuilder();
        inferiorLimit = makeIntegerZeroConstant(limitMetadataBuilder);
        symbolTable.putIfAbsent(inferiorLimit, limitMetadataBuilder.build());

        initialValue = Factory.createPrimitiveInitialization(symbolTable, subtype).copy();
    }

    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    public Subtype subtype() {
        return subtype;
    }

    @Override
    public String customType() {
        return null;
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
        return Collections.singletonList(inferiorLimit);
    }

    @Override
    public List<String> superiorLimits() {
        return Collections.singletonList(superiorLimit);
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
