package parser.metadata;

import parser.initializations.Factory;
import parser.initializations.leafs.DefaultInitialization;
import utils.SymbolTable;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

/**
 * Metadata of a primitive type.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class PrimitiveMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final DefaultInitialization initialValue;

    public PrimitiveMetadata(SymbolTable symbolTable, Subtype subtype) {
        this.subtype = subtype;
        this.initialValue  = Factory.createPrimitiveInitialization(symbolTable, subtype);
    }

    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    @Override
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
    public DefaultInitialization initialValue() {
        return initialValue;
    }
}
