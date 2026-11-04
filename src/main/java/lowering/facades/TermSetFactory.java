package lowering.facades;

import codegen.fuzzysets.*;
import utils.LexemeInfo;
import utils.enums.Subtype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TermSetFactory {
    public static Optional<FuzzySet> create(String termName, LexemeInfo termInfo) {
        List<String> membershipFunctionXValues = termInfo.inferiorLimits;
        List<Integer> X = toIntegerCollection(membershipFunctionXValues);
        Subtype termSubtype = termInfo.subtype;
        switch (termSubtype) {
            case SINGLETON:
                return Optional.of(new SingletonFuzzySet(termName, X.get(0)));
            case LINEAR:
                return Optional.of(membershipFunctionStartsAtOne(termInfo.superiorLimits)
                        ? new ZShapeFuzzySet(termName, X.get(0), X.get(1))
                        : new SShapeFuzzySet(termName, X.get(0), X.get(1)));
            case TRIANGLE:
                return Optional.of(new TriangularFuzzySet(termName, X.get(0), X.get(1), X.get(2)));
            case TRAPEZOID:
                return Optional.of(new TrapezoidFuzzySet(termName, X.get(0), X.get(1), X.get(2), X.get(3)));
            default:
                throw new IllegalArgumentException("TermSetFactory: there's no class for the given subtype");
        }
    }

    private static List<Integer> toIntegerCollection(final List<String> membershipFunctionXValues) {
        List<Integer> castedXValues = new ArrayList<>();
        for (String xValue : membershipFunctionXValues) {
            BigDecimal intValue = new BigDecimal(xValue);
            try {
                castedXValues.add(intValue.intValueExact());
            } catch (Exception e) {
                throw new RuntimeException("TermSetFactory: illegal membership function x values");
            }
        }
        return castedXValues;
    }

    private static boolean membershipFunctionStartsAtOne(final List<String> membershipFunctionYValues) {
        return new BigDecimal(membershipFunctionYValues.get(0)).compareTo(BigDecimal.ONE) == 0;
    }
}
