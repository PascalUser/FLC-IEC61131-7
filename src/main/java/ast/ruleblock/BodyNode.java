package ast.ruleblock;

import ast.AbstractAstNode;
import ast.ruleblock.operators.OperatorNode;

import java.util.Collections;
import java.util.Objects;

/**
 * Rule body: the fuzzy operator that is applied over the operands of a rule block.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class BodyNode extends AbstractAstNode {
    private final OperatorNode act;

    public BodyNode(final OperatorNode act) {
        super(Collections.singletonList(Objects.requireNonNull(act, "act")));
        this.act = act;
    }

    /**
     * Returns the operator that is applied in the body of this rule block.
     *
     * @return the operator of the body
     */
    public OperatorNode act() {
        return act;
    }

    /**
     * Returns the label of this node: the operator name of the body.
     *
     * @return the label of the body
     */
    @Override
    public String label() {
        return "BODY";
    }
}
