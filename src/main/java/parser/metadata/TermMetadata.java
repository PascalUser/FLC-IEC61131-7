package parser.metadata;

import parser.facades.MembershipFunctionClassifier;
import parser.utils.MembershipFunctionShape;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

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

    @Override
    public Type type() {
        return Type.FUNCTION;
    }

    public Subtype subtype() {
        return shape.subtype();
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
        return Use.TERM;
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
        return shape.parameters();
    }

    @Override
    public Object initialValue() {
        return null;
    }
}
