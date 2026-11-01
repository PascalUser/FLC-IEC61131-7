package parser.tree;

import java.util.List;

public final class ParserTreeNode {
    private final ParserLabel label;
    private final String symbolReference;
    private final List<ParserTreeNode> children;

    public ParserTreeNode(ParserLabel label, List<ParserTreeNode> children) {
        this(label, "", children);
    }

    public ParserTreeNode(ParserLabel label, String symbolReference, List<ParserTreeNode> children) {
        this.label = label;
        this.symbolReference = symbolReference;
        this.children = children;
    }

    public ParserLabel label() {
        return label;
    }

    public String symbolReference() {
        return symbolReference;
    }

    public List<ParserTreeNode> children() {
        return children;
    }
}
