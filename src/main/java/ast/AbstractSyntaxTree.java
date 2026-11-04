package ast;

import ast.ruleblock.RuleBlockNode;

import java.util.List;

/**
 * Syntax tree of a whole FCL program: a synthetic root that holds the rule blocks.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class AbstractSyntaxTree extends AbstractAstNode {
    private final String programName;

    /**
     * Creates the syntax tree of a program.
     *
     * @param programName name of the function block
     * @param ruleBlocks rule blocks of the program
     */
    public AbstractSyntaxTree(String programName, List<RuleBlockNode> ruleBlocks) {
        super(ruleBlocks);
        this.programName = programName;
    }

    /**
     * Returns the label of this syntax tree: the synthetic root label.
     *
     * @return the label of the root node
     */
    @Override
    public String label() {
        return programName;
    }
}
