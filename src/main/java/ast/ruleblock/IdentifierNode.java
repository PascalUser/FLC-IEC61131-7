package ast.ruleblock;

import ast.AbstractAstNode;

import java.util.Collections;
import java.util.Objects;

public final class IdentifierNode extends AbstractAstNode {
    private final String name;

    public IdentifierNode(final String name) {
        super(Collections.emptyList());
        this.name = Objects.requireNonNull(name, "name");
    }

    public String name() {
        return name;
    }

    @Override
    public String label() {
        return name;
    }
}
