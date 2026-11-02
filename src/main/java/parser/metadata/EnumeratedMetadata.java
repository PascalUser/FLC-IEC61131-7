package parser.metadata;

import parser.initializations.leafs.EnumeratedInitialization;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

/**
 * Metadata of an enumerated type.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class EnumeratedMetadata implements LexemeInfoView {
    private final List<String> values;
    private final EnumeratedInitialization initialValue;

    public EnumeratedMetadata(List<String> initValues) {
        values = initValues;
        initialValue = new EnumeratedInitialization(initValues);
    }

    @Override
    public Type type() {
        return Type.ENUMERATE;
    }

    @Override
    public Subtype subtype() {
        return Subtype.INT;
    }

    @Override
    public String customType() {
        return null;
    }

    @Override
    public Source source() {
        return null;
    }

    @Override
    public Use use() {
        return null;
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
        return values;
    }

    /**
     * Returns the initialization of this enumerated type.
     *
     * @return the initialization of the enumerated type
     */
    public EnumeratedInitialization initialValue() {
        return initialValue;
    }
}
