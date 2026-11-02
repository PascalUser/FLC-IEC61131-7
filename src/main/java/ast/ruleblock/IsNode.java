package ast.ruleblock;

import ast.AbstractAstNode;

import java.util.Arrays;
import java.util.Objects;

/**
 * Is: a variable compared against a fuzzy term.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class IsNode extends AbstractAstNode {
    private final IdentifierNode variable;
    private final IdentifierNode term;

    public IsNode(final IdentifierNode variable, final IdentifierNode term) {
        super(Arrays.asList(
                Objects.requireNonNull(variable, "variable"),
                Objects.requireNonNull(term, "term")
        ));
        this.variable = variable;
        this.term = term;
    }

    /**
     * Returns the variable of this is node.
     *
     * @return the variable being tested
     */
    public IdentifierNode variable() {
        return variable;
    }

    /**
     * Returns the term of this is node.
     *
     * @return the term the variable is compared against
     */
    public IdentifierNode term() {
        return term;
    }

    /**
     * Returns the label of this node: the is condition.
     *
     * @return the label of the is node
     */
    @Override
    public String label() {
        return "IS";
    }
}
