package compiler.parser.facades;

import compiler.ast.AbstractAstNode;
import compiler.ast.ruleblock.*;
import compiler.ast.ruleblock.operators.Operator;
import compiler.ast.ruleblock.operators.OperatorNode;
import compiler.parser.tree.ParserTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder that creates the rule block syntax tree from the parser tree and the operator configuration.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class RuleblockASTBuilder {
    private RuleblockASTBuilder() {
        // Utility class - not instantiable
    }

    /**
     * Builds the rule block syntax tree from the parser tree and the operator configuration.
     *
     * @param ruleblockTree the parser tree of the rule block
     * @param configuration the operator configuration of the rule block
     * @return the built rule block syntax tree
     * @throws IllegalArgumentException if the configuration is not valid or the tree is invalid
     */
    public static RuleBlockNode build(final ParserTreeNode ruleblockTree, final List<Operator> configuration) {
        if (configuration.size() != 4) {
            throw new IllegalArgumentException("ASTBuilder: missing configuration parameters.");
        }
        AbstractAstNode ruleblockAST = getASTWithTreeTraversal(ruleblockTree, configuration);
        if (!(ruleblockAST instanceof RuleBlockNode)) {
            throw new IllegalArgumentException("ASTBuilder: invalid ruleblock tree.");
        }
        return (RuleBlockNode) ruleblockAST;
    }

    private static AbstractAstNode getASTWithTreeTraversal(ParserTreeNode current, List<Operator> configuration) {
        List<ParserTreeNode> children = current.children();
        if (children.isEmpty()) {
            return factory(current, Collections.emptyList(), configuration);
        }
        List<AbstractAstNode> astChildren = new ArrayList<>();
        for (ParserTreeNode child : children) {
            AbstractAstNode astChild = getASTWithTreeTraversal(child, configuration);
            astChildren.add(astChild);
        }
        return factory(current, astChildren, configuration);
    }

    private static AbstractAstNode factory(ParserTreeNode node, List<AbstractAstNode> children, List<Operator> configuration) {
        switch (node.label()) {
            case IDENTIFIER:
                if (!children.isEmpty()) {
                    throw new RuntimeException("ASTBUilder: found an IDENTIFIER with children.");
                }
                return new IdentifierNode(node.symbolReference());
            case IS:
                if (children.size() != 2) {
                    throw new RuntimeException("ASTBUilder: found an IS without two children.");
                }
                if (!(children.get(0) instanceof IdentifierNode)) {
                    throw new RuntimeException("ASTBUilder: found an IS without a left identifier.");
                }
                if (!(children.get(1) instanceof IdentifierNode)) {
                    throw new RuntimeException("ASTBUilder: found an IS without a right identifier.");
                }
                return new IsNode((IdentifierNode) children.get(0), (IdentifierNode) children.get(1));
            case ACTIVATION:
                Operator actSet = configuration.get(2);
                return new OperatorNode(actSet, children);
            case BODY:
                if (children.size() != 1) {
                    throw new RuntimeException("ASTBUilder: found a BODY without one child.");
                }
                if (!(children.get(0) instanceof OperatorNode)) {
                    throw new RuntimeException("ASTBUilder: found a BODY without an activation method.");
                }
                return new BodyNode((OperatorNode) children.get(0));
            case NOT:
                if (children.size() != 1) {
                    throw new RuntimeException("ASTBUilder: found a NOT without one child.");
                }
                return new NotNode(children.get(0));
            case OR:
                if (children.size() != 2) {
                    throw new RuntimeException("ASTBUilder: found an OR without two children.");
                }
                Operator orSet = configuration.get(0);
                return new OperatorNode(orSet, children);
            case AND:
                if (children.size() != 2) {
                    throw new RuntimeException("ASTBUilder: found an AND without two children.");
                }
                Operator andSet = configuration.get(1);
                return new OperatorNode(andSet, children);
            case CONDITION:
                if (children.size() != 1) {
                    throw new RuntimeException("ASTBUilder: found a CONDITION without one child.");
                }
                return new CondNode(children.get(0));
            case IF:
                if (children.size() != 2) {
                    throw new RuntimeException("ASTBUilder: found an IF without two child.");
                }
                if (!(children.get(0) instanceof CondNode)) {
                    throw new RuntimeException("ASTBUilder: found an IS without a left identifier.");
                }
                if (!(children.get(1) instanceof BodyNode)) {
                    throw new RuntimeException("ASTBUilder: found an IS without a right identifier.");
                }
                return new IfNode((CondNode) children.get(0), (BodyNode) children.get(1));
            case WITH:
                if (children.size() != 1) {
                    throw new RuntimeException("ASTBUilder: found a WITH without one child.");
                }
                if (!(children.get(0) instanceof IfNode)) {
                    throw new RuntimeException("ASTBUilder: found an WITH without a rule.");
                }
                return new WithNode(node.symbolReference(), (IfNode) children.get(0));
            case ACCUMULATION:
                Operator accuSet = configuration.get(3);
                return new OperatorNode(accuSet, children);
            case COMPONENT:
                if (children.size() != 1) {
                    throw new RuntimeException("ASTBUilder: found a COMPONENT without one child.");
                }
                if (!(children.get(0) instanceof OperatorNode)) {
                    throw new RuntimeException("ASTBUilder: found a COMPONENT without an accumulation method.");
                }
                return new RuleBlockNode(node.symbolReference(), (OperatorNode) children.get(0));
        }
        return null;
    }
}
