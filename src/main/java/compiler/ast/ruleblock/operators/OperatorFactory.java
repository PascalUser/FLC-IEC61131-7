package compiler.ast.ruleblock.operators;

/**
 * Factory that provides the default fuzzy operator used when none is configured.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class OperatorFactory {
    private OperatorFactory() {
    }

    /**
     * Returns the default operator for the or operation.
     *
     * @return the default or operator
     */
    public static Operator orDefault() {
        return Operator.MAX;
    }

    /**
     * Returns the default operator for the and operation.
     *
     * @return the default and operator
     */
    public static Operator andDefault() {
        return Operator.MIN;
    }

    /**
     * Returns the default operator for the act operation.
     *
     * @return the default act operator
     */
    public static Operator actDefault() {
        return Operator.MIN;
    }

    /**
     * Returns the default operator for the accu operation.
     *
     * @return the default accu operator
     */
    public static Operator accuDefault() {
        return Operator.MAX;
    }
}
