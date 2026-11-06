package compiler.parser.metadata;

import compiler.parser.initializations.leafs.VariableInitialization;
import compiler.parser.publishers.nodes.FuzzyBlock;
import compiler.repository.builders.LexemeInfoView;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

import java.util.Collections;
import java.util.List;

/**
 * Metadata of a defuzzifier.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class DefuzzifierMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final String inferiorLimit;
    private final String superiorLimit;
    private final List<String> terms;
    private final VariableInitialization initialValue;


    /**
     * Creates the metadata of a defuzzifier.
     *
     * @param range optional range of the output variable
     * @param fuzzyBlock optional block with the terms of the defuzzifier
     * @param subtype subtype of the output variable
     * @param defaultValue default value of the output variable
     */
    public DefuzzifierMetadata(
            List<String> range,
            FuzzyBlock fuzzyBlock,
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
        this.terms = (fuzzyBlock != null) ? fuzzyBlock.getTermNames() : null;
        this.initialValue = defaultValue;
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.FUNCTION;
    }

    /** {@inheritDoc} */
    @Override
    public Subtype subtype() {
        return subtype;
    }

    /** {@inheritDoc} */
    @Override
    public String customType() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Source source() {
        return Source.DEFUZZIFY;
    }

    /** {@inheritDoc} */
    @Override
    public Use use() {
        return Use.COMPONENT;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> inferiorLimits() {
        return (inferiorLimit == null) ? null : Collections.singletonList(inferiorLimit);
    }

    /** {@inheritDoc} */
    @Override
    public List<String> superiorLimits() {
        return (superiorLimit == null) ? null : Collections.singletonList(superiorLimit);
    }

    /** {@inheritDoc} */
    @Override
    public List<String> parameters() {
        return terms;
    }

    /** {@inheritDoc} */
    @Override
    public VariableInitialization initialValue() {
        return initialValue;
    }
}
