package compiler.parser.metadata;

import compiler.parser.initializations.nodes.StructInitialization;
import compiler.parser.publishers.nodes.StructureBlock;
import compiler.parser.utils.PathInitializationInfo;
import compiler.repository.builders.LexemeInfoView;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

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

    /**
     * Creates the metadata of a structure type from its field declarations.
     *
     * @param initBlock block with the fields of the structure
     */
    public StructureMetadata(StructureBlock initBlock) {
        fields = new ArrayList<>();
        initialValue = new StructInitialization();

        List<PathInitializationInfo> fieldsInfo = initBlock.getInitializationInfo();
        for (PathInitializationInfo fieldInfo : fieldsInfo) {
            initialValue.put(fieldInfo.name(), fieldInfo.initialValue());
            fields.add(fieldInfo.name());
        }
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.STRUCT;
    }

    /** {@inheritDoc} */
    @Override
    public Subtype subtype() {
        return Subtype.NONE;
    }

    /** {@inheritDoc} */
    @Override
    public String customType() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Source source() {
        return Source.UNKNOWN;
    }

    /** {@inheritDoc} */
    @Override
    public Use use() {
        return Use.UNKNOWN;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> inferiorLimits() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> superiorLimits() {
        return null;
    }

    /** {@inheritDoc} */
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
