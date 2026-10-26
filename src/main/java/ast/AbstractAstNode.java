package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractAstNode {
    private final List<AbstractAstNode> children;

    protected AbstractAstNode(final List<? extends AbstractAstNode> children) {
        Objects.requireNonNull(children, "children");
        List<AbstractAstNode> copy = new ArrayList<>(children.size());
        for (AbstractAstNode child : children) {
            copy.add(Objects.requireNonNull(child, "child"));
        }
        this.children = Collections.unmodifiableList(copy);
    }

    public abstract String label();

    public final List<AbstractAstNode> children() {
        return children;
    }
}
