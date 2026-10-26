package ast.ruleblock;

import ast.AbstractAstNode;

import java.util.Arrays;
import java.util.Objects;

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

    public IdentifierNode variable() {
        return variable;
    }

    public IdentifierNode term() {
        return term;
    }

    @Override
    public String label() {
        return "IS";
    }
}
