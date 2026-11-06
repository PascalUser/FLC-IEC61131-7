package compiler.ast.ruleblock;

import compiler.ast.AbstractAstNode;

import java.util.Collections;
import java.util.Objects;

/**
 * With: a weight and the condition that selects the fuzzy set.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class WithNode extends AbstractAstNode {
    private final String weight;
    private final IfNode ifNode;

    /**
     * Creates a with node.
     *
     * @param weight weight of the fuzzy set
     * @param ifNode condition that selects the fuzzy set
     */
    public WithNode(final String weight, final IfNode ifNode) {
        super(Collections.singletonList(Objects.requireNonNull(ifNode, "ifNode")));
        this.weight = Objects.requireNonNull(weight, "weight");
        this.ifNode = ifNode;
    }

    /**
     * Returns the weight of this with node.
     *
     * @return the weight of the fuzzy set
     */
    public String weight() {
        return weight;
    }

    /**
     * Returns the if node of this with node.
     *
     * @return the condition that selects the fuzzy set
     */
    public IfNode ifNode() {
        return ifNode;
    }

    /**
     * Returns the label of this node: the with weight.
     *
     * @return the label of the with node
     */
    @Override
    public String label() {
        return "WITH " + weight;
    }
}
