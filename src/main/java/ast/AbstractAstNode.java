package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Base class of the syntax tree: it stores the child nodes and computes the label of any node.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
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

    /**
     * Returns the label of this node.
     *
     * @return the diagnostic label of the node
     */
    public abstract String label();

    /**
     * Returns the child nodes of this node.
     *
     * @return the unmodifiable list of child nodes
     */
    public final List<AbstractAstNode> children() {
        return children;
    }
}
