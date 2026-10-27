package parser.metadata;

import parser.initializations.leafs.VariableInitialization;
import parser.publishers.TermBlock;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Collections;
import java.util.List;

public final class DefuzzifierMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final String inferiorLimit;
    private final String superiorLimit;
    private final List<String> terms;
    private final VariableInitialization initialValue;


    public DefuzzifierMetadata(
            List<String> range,
            TermBlock termBlock,
            Subtype subtype,
            VariableInitialization defaultValue
    ) {
        if (range != null) {
            inferiorLimit = range.get(0);
            superiorLimit = range.get(1);
        } else {
            inferiorLimit = superiorLimit = null;
        }

        this.subtype = subtype;
        this.terms = (termBlock != null) ? termBlock.getTermNames() : null;
        this.initialValue = defaultValue;
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
        return Source.DEFUZZIFY;
    }

    @Override
    public Use use() {
        return Use.COMPONENT;
    }

    @Override
    public List<String> inferiorLimits() {
        return (inferiorLimit == null) ? null : Collections.singletonList(inferiorLimit);
    }

    @Override
    public List<String> superiorLimits() {
        return (superiorLimit == null) ? null : Collections.singletonList(superiorLimit);
    }

    @Override
    public List<String> parameters() {
        return terms;
    }

    @Override
    public VariableInitialization initialValue() {
        return initialValue;
    }
}
