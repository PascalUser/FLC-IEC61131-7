package ast.ruleblock;

import ast.AbstractAstNode;

import java.util.Collections;
import java.util.Objects;

/**
 * Identifier: a variable or field name used by the program.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class IdentifierNode extends AbstractAstNode {
    private final String name;

    /**
     * Creates an identifier node.
     *
     * @param name identifier name
     */
    public IdentifierNode(final String name) {
        super(Collections.emptyList());
        this.name = Objects.requireNonNull(name, "name");
    }

    /**
     * Returns the name of this identifier.
     *
     * @return the identifier name
     */
    public String name() {
        return name;
    }

    /**
     * Returns the label of this node: the identifier name.
     *
     * @return the label of the node
     */
    @Override
    public String label() {
        return name;
    }
}
