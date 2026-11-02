package ast.ruleblock;

import ast.AbstractAstNode;
import ast.ruleblock.operators.OperatorNode;

import java.util.Collections;
import java.util.Objects;

/**
 * Rule block: a name, an accumulation operator and the body of the rule.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class RuleBlockNode extends AbstractAstNode {
    private final String name;
    private final OperatorNode accumulation;

    public RuleBlockNode(final String name, final OperatorNode accumulation) {
        super(Collections.singletonList(Objects.requireNonNull(accumulation, "accumulation")));
        this.name = Objects.requireNonNull(name, "name");
        this.accumulation = accumulation;
    }

    /**
     * Returns the name of this rule block.
     *
     * @return the rule block name
     */
    public String name() {
        return name;
    }

    /**
     * Returns the accumulation operator of this rule block.
     *
     * @return the operator that accumulates the rule
     */
    public OperatorNode accumulation() {
        return accumulation;
    }

    /**
     * Returns the label of this node: the rule block name.
     *
     * @return the label of the rule block
     */
    @Override
    public String label() {
        return name;
    }
}
