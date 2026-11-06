package utils.supports;

import compiler.ast.AbstractAstNode;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Rendering and assertion helpers for AST tests.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public abstract class AstTestSupport extends ParserTestSupport {

    /**
     * Renders a subtree as indented labels, one node per line.
     *
     * @param node root of the subtree
     * @return the rendered tree
     */
    public static String render(final AbstractAstNode node) {
        StringBuilder result = new StringBuilder();
        render(node, 0, result);
        return result.toString();
    }

    /**
     * Joins lines with the platform-independent line separator used by the assertions.
     *
     * @param lines lines to join
     * @return the joined text
     */
    public static String lines(final String... lines) {
        return String.join("\n", lines);
    }

    /**
     * Asserts that the children of a node cannot be modified.
     *
     * @param node node whose children are checked
     */
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
