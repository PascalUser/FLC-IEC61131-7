package compiler.parser.metadata;

import compiler.parser.facades.MembershipFunctionClassifier;
import compiler.parser.utils.MembershipFunctionShape;
import compiler.repository.builders.LexemeInfoView;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

import java.util.ArrayList;
import java.util.List;

/**
 * Symbol table entry of a linguistic term ({@code TERM name := points;} or a singleton).
 * <p>
 * The limits keep the points as written (x as inferior limits, degrees as superior limits); the subtype and
 * the parameters are the shape recognized by {@link MembershipFunctionClassifier}, e.g. {@code (0,1) (5,1)
 * (10,0)} is {@code LINEAR} with parameters {@code [5, 10]}.
 * </p>
 */
public final class TermMetadata implements LexemeInfoView {
    private final MembershipFunctionShape shape;
    private final List<String> inferiorLimits;
    private final List<String> superiorLimits;

    /**
     * Creates the metadata of a linguistic term from its points.
     *
     * @param points membership function points as written in the source
     */
    public TermMetadata(List<String> points) {
        inferiorLimits = new ArrayList<>();
        superiorLimits = new ArrayList<>();

        boolean toInferiorList = true;
        for (String point : points) {
            if (toInferiorList) {
                inferiorLimits.add(point);
            } else {
                superiorLimits.add(point);
            }
            toInferiorList = !toInferiorList;
        }
        shape = MembershipFunctionClassifier.classify(inferiorLimits, superiorLimits);
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.FUNCTION;
    }

    /**
     * Returns the subtype of this term, from its membership function shape.
     *
     * @return the subtype of the term
     */
    public Subtype subtype() {
        return shape.subtype();
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
        return Use.TERM;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> inferiorLimits() {
        return inferiorLimits;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> superiorLimits() {
        return superiorLimits;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> parameters() {
        return shape.parameters();
    }

    /** {@inheritDoc} */
    @Override
    public Object initialValue() {
        return null;
    }
}
