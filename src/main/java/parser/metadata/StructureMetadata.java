package parser.metadata;

import parser.initializations.nodes.StructInitialization;
import parser.publishers.nodes.StructureBlock;
import parser.utils.PathInitializationInfo;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.ArrayList;
import java.util.List;

/**
 * Metadata of a structure type.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class StructureMetadata implements LexemeInfoView {
    private final List<String> fields;
    private final StructInitialization initialValue;

    public StructureMetadata(StructureBlock initBlock) {
        fields = new ArrayList<>();
        initialValue = new StructInitialization();

        List<PathInitializationInfo> fieldsInfo = initBlock.getInitializationInfo();
        for (PathInitializationInfo fieldInfo : fieldsInfo) {
            initialValue.put(fieldInfo.name(), fieldInfo.initialValue());
            fields.add(fieldInfo.name());
        }
    }

    @Override
    public Type type() {
        return Type.STRUCT;
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
        return fields;
    }

    /**
     * Returns the initialization of this structure.
     *
     * @return the initialization of the structure
     */
    public StructInitialization initialValue() {
        return initialValue;
    }
}
