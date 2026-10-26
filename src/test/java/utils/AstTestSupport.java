package utils;

import ast.AbstractAstNode;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class AstTestSupport {
    private AstTestSupport() {
    }

    public static String render(final AbstractAstNode node) {
        StringBuilder result = new StringBuilder();
        render(node, 0, result);
        return result.toString();
    }

    public static String lines(final String... lines) {
        return String.join("\n", lines);
    }

    public static void assertChildrenUnmodifiable(final AbstractAstNode node) {
        //noinspection DataFlowIssue
        assertThrows(UnsupportedOperationException.class, () -> node.children().clear());
    }

    private static void render(final AbstractAstNode node, final int depth, final StringBuilder result) {
        for (int i = 0; i < depth; i++) {
            result.append("  ");
        }
        result.append(node.label());
        for (AbstractAstNode child : node.children()) {
            result.append('\n');
            render(child, depth + 1, result);
        }
    }
}
