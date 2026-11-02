package parser.tree;

import java.util.List;

/**
 * Node of the parser tree: its label, symbol reference and children.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class ParserTreeNode {
    private final ParserLabel label;
    private final String symbolReference;
    private final List<ParserTreeNode> children;

    /**
     * Creates a parser tree node that holds the given label and children.
     *
     * @param label    the label of the node
     * @param children the children of the node
     */
    public ParserTreeNode(final ParserLabel label, final List<ParserTreeNode> children) {
        this(label, "", children);
    }

    /**
     * Creates a parser tree node that holds the given label, symbol reference and children.
     *
     * @param label          the label of the node
     * @param symbolReference the symbol the node refers to, empty when it is not a symbol
     * @param children       the children of the node
     */
    public ParserTreeNode(final ParserLabel label, final String symbolReference, final List<ParserTreeNode> children) {
        this.label = label;
        this.symbolReference = symbolReference;
        this.children = children;
    }

    /**
     * Returns the label of this node.
     *
     * @return the label of the node
     */
    public ParserLabel label() {
        return label;
    }

    /**
     * Returns the symbol reference of this node, empty when the node is not a symbol.
     *
     * @return the symbol reference of the node
     */
    public String symbolReference() {
        return symbolReference;
    }

    /**
     * Returns the children of this node.
     *
     * @return the unmodifiable list of children
     */
    public List<ParserTreeNode> children() {
        return children;
    }
}
