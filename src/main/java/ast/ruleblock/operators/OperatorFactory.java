package ast.ruleblock.operators;

public final class OperatorFactory {
    private OperatorFactory() {
    }

    public static Operator orDefault() {
        return Operator.MAX;
    }

    public static Operator andDefault() {
        return Operator.MIN;
    }

    public static Operator actDefault() {
        return Operator.MIN;
    }

    public static Operator accuDefault() {
        return Operator.MAX;
    }
}
