package parser.metadata;

import parser.facades.FunctionTypeCalculator;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.ArrayList;
import java.util.List;

public final class TermMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final List<String> inferiorLimits;
    private final List<String> superiorLimits;

    public TermMetadata(List<String> points) {
        inferiorLimits = new ArrayList<>();
        superiorLimits = new ArrayList<>();

        int numberOfPoints = 0;
        boolean toInferiorList = true;
        for (String point : points) {
            if (toInferiorList) {
                inferiorLimits.add(point);
            } else {
                numberOfPoints++;
                superiorLimits.add(point);
            }
            toInferiorList = !toInferiorList;
        }
        subtype = FunctionTypeCalculator.calculate(numberOfPoints);
    }

    @Override
    public Type type() {
        return Type.FUNCTION;
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
        return null;
    }

    @Override
    public Object initialValue() {
        return null;
    }
}
