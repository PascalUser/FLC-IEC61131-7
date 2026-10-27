package parser.tree;

import java.util.List;

public final class TreeNode {
    private final Label label;
    private final String symbolReference;
    private final List<TreeNode> children;

    public TreeNode(Label label, List<TreeNode> children) {
        this(label, "", children);
    }

    public TreeNode(Label label, String symbolReference, List<TreeNode> children) {
        this.label = label;
        this.symbolReference = symbolReference;
        this.children = children;
    }

    public Label label() {
        return label;
    }

    public String symbolReference() {
        return symbolReference;
    }

    public List<TreeNode> children() {
        return children;
    }
}
