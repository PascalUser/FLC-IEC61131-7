package parser.facades;

import utils.enums.Subtype;

public final class FunctionTypeCalculator {
    public static Subtype calculate(Integer numberOfPoints) {
        if (numberOfPoints < 0) {
            throw new IllegalArgumentException("FunctionTypeCalculator: numberOfPoints cannot be negative");
        }
        switch (numberOfPoints) {
            case 1:
                return Subtype.SINGLETON;
            case 2:
                return Subtype.LINEAR;
            case 3:
                return Subtype.TRIANGLE;
            case 4:
                return Subtype.TRAPEZOID;
            default:
                return Subtype.COMPLEX;
        }
    }
}
